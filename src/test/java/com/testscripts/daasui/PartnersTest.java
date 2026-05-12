package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.PartnerVariables;
import com.daasui.constants.SalesVariables;
import com.daasui.constants.UserVariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.LicenseOrdersPage;
import com.daasui.pages.PartnerDetailsPage;
import com.daasui.pages.PartnerPage;
import com.daasui.pages.SalesTeamPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.UserDetailsPage;
import com.daasui.pages.UserPage;

public class PartnersTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(SupportTeamTest.class);
	public int activePageNumber = 0, firstPageNumber = 0, lastPageNumber = 0, selectedOption = 0, totalCount = 0;
	public boolean previousButtonStatus = false, nextButtonStatus = false;
	ArrayList<String> partnerInfo = new ArrayList<String>();
	public static String cataLystURL = getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL");
	public static String billing_partner_name = getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_NAME");
	public static String billing_admin_email = getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_ADMIN_EMAIL");

	// This test case validates title on partners list page
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "STABILIZATION_STAGING" }, description = "NFR 214738")
	public final void verifyPartnerTitle() {
		try {
			login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
			LOGGER.info("Redirected to companies list page");
			gotoPartnersTab();
			LOGGER.info("Redirected to partner list page");
			sleeper(3000);
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();

			partnerPage.waitForElementsOfPartnerPage("partnersTitle");
			Assert.assertTrue(partnerPage.matchTextOfPartnerPage("partnersTitle", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.partners")), "Title on partner list page is incorrect");
			LOGGER.info("Title on partner list page is correct");

		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyPartnerTitle " + e.getMessage());
		}
	}

	// This test case validates pagination functionality on partner list page
	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "STABILIZATION_STAGING" }, description = "NFR 214738, Test Case 217254")
	public final void verifyPaginationOnPartnerPage() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
			LOGGER.info("Redirected to companies list page");
			gotoPartnersTab();
			LOGGER.info("Redirected to partner list page");
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			resetTableConfiguration();
			partnerPage.waitForElementsOfPartnerPage("paginationDropdownMenu");
			softAssert.assertTrue(partnerPage.verifyElementIsEnableOfPartnerPage("paginationDropdownMenu"), "Pagination Dropdown not availble");
			softAssert.assertTrue(partnerPage.verifyElementIsClickableOfPartnerPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
			softAssert.assertTrue(partnerPage.waitForElementsOfPartnerPage("navigationItemDiv"), "Navigation Item are not available");
			getPaginationInfo();
			LOGGER.info("get Pagination Information ");
			softAssert.assertTrue(partnerPage.verifyElementIsEnableOfPartnerPage("navigationItemActivePage"), "The active page navigation link is not disabled");
			softAssert.assertTrue(partnerPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
			if (!verifySelectedOptionForNewSelection(selectedOption, CommonVariables.SELETEDFIFTY)) {
				if (changeSelectedOption(totalCount, CommonVariables.SELETEDTWENTYFIVE)) {
					partnerPage.selectElementFromDropDownOfPartnerPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDTWENTYFIVE);
					partnerPage.waitForElementsOfPartnerPage("tableOverlay");
					getPaginationInfo();
					softAssert.assertTrue(partnerPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
					softAssert.assertTrue(partnerPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
				} else {

					LOGGER.info("Selected user has less than " + CommonVariables.SELETEDTWENTYFIVE + " records");
				}
			} else {
				if (changeSelectedOption(totalCount, CommonVariables.SELETEDFIFTY)) {
					partnerPage.selectElementFromDropDownOfPartnerPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDFIFTY));
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDFIFTY);
					partnerPage.waitForElementsOfPartnerPage("tableOverlay");
					getPaginationInfo();
					softAssert.assertTrue(partnerPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
					softAssert.assertTrue(partnerPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
				} else {

					LOGGER.info("Selected user has less than " + CommonVariables.SELETEDFIFTY + " records");
				}
			}
			partnerPage.waitForElementsOfPartnerPage("tableOverlay");
			if (partnerPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
				softAssert.assertTrue(partnerPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
				partnerPage.waitForElementsOfPartnerPage("navigationItemNext");
				partnerPage.clickOnElementsOfPartnerPage("navigationItemNext");
				partnerPage.waitForElementsOfPartnerPage("tableOverlay");
				getPaginationInfo();
				partnerPage.waitForElementsOfPartnerPage("navigationItemPrevious");
				if (partnerPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
					softAssert.assertTrue(partnerPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
					partnerPage.clickOnElementsOfPartnerPage("navigationItemPrevious");
				} else {
					LOGGER.info("Previous button is disabled");
				}
			} else if (partnerPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
				softAssert.assertTrue(partnerPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
				partnerPage.waitForElementsOfPartnerPage("navigationItemPrevious");
				partnerPage.clickOnElementsOfPartnerPage("navigationItemPrevious");
				partnerPage.waitForElementsOfPartnerPage("tableOverlay");
				getPaginationInfo();
				partnerPage.waitForElementsOfPartnerPage("navigationItemNext");
				if (partnerPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
					softAssert.assertTrue(partnerPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
					partnerPage.clickOnElementsOfPartnerPage("navigationItemNext");
				} else {
					LOGGER.info("Next button is disabled");
				}
			} else {
				LOGGER.info("Previous and Next button both are disabled");
			}
			softAssert.assertAll();
			LOGGER.info("Verification of pagination functionality on partner list page done successfully ");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyPaginationOnPartnerPage " + e.getMessage());
		}
	}

	// This test case validates all filters' functionality on partner list page
	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "STABILIZATION_STAGING" }, description = "NFR 214738, Test Case 217309")
	public final void verifyFilterFunctionality() {
		int attempt;
		try {
			SoftAssert softAssert = new SoftAssert();
			login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
			LOGGER.info("Redirected to companies list page");
			gotoPartnersTab();
			LOGGER.info("Redirected to partner list page");
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			resetTableConfiguration();
			partnerPage.waitForElementsOfPartnerPage("tableOverlay");

			// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on name column
			attempt=0;
			while(!partnerPage.verifyElementIsEnableOfPartnerPage("nameSearchBox")&& attempt<10){
			sleeper(1000);
			attempt++; 
			}
			
//			partnerPage.verifyElementIsEnableOfPartnerPage("nameSearchBox");
//			sleeper(2000);
			softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBox", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "nameList"), "Search functionality with incorrect search string is not working for name column on partner list page");
			softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBox", PartnerVariables.NAME_SEARCH, "noItemsDisplayText", "nameList"), "Search functionality is not working for name column on partner list page");
			LOGGER.info("Verified filter functionality for name column");

			/*
			 * // Test Case 2 - This test case validates if the filter functionality is working properly for the dropdown on type column
			 * partnerPage.clickOnElementsOfPartnerPage("typeDropdown"); softAssert.assertTrue(partnerPage.verifyFilterSingleSelect(LanguageCode, "dropdownCheckBoxes",
			 * "dropdownElementListLabels", "typeList", "noItemsDisplayText"), "Listbox not working for roles on support team list page");
			 * partnerPage.clickOnElementsOfPartnerPage("clearFilter"); partnerPage.waitForElementsOfPartnerPage("tableOverlay"); LOGGER.info("Verified filter functionality for type column");
			 */

			// Test Case 3 - This test case validates if the filter functionality is working properly for the dropdown on the primary adiminstrator column
			partnerPage.waitForElementsOfPartnerPage("primaryAdministratorDropdown");
			partnerPage.clickOnElementsOfPartnerPage("primaryAdministratorDropdown");
			
			/*
			 * attempt=0; while(!partnerPage.verifyElementIsEnableOfPartnerPage(
			 * "primaryAdministratorSearchBox")&& attempt<10){ sleeper(1000); attempt++; }
			 */
			partnerPage.verifyElementIsEnableOfPartnerPage("primaryAdministratorSearchBox");
			sleeper(2000);
			softAssert.assertTrue(partnerPage.verifySingleSelectionFilterFunctionalityFromDynamicDropdownOnPartnerPage(LanguageCode, "primaryAdministratorSearchBox", PartnerVariables.PRIMARY_ADMINISTRATOR_SEARCH, "noElementsDisplayTextForComboBoxColumn", "primaryAdministratorListOnDropdown", "primaryAdministratorList", "noItemsDisplayText"), "Filter functionality on selecting single option from primary adiminstrator column on partner list page is not working---->1");
			pressKey(Keys.ESCAPE);
			if (partnerPage.verifyElementsOfPartnerPage("clearFilter"))
				partnerPage.clickOnElementsOfPartnerPage("clearFilter");

			partnerPage.waitForElementsOfPartnerPage("primaryAdministratorDropdown");
			partnerPage.clickOnElementsOfPartnerPage("primaryAdministratorDropdown");
			
			partnerPage.waitForElementsOfPartnerPage("primaryAdministratorSearchBox");
			
			sleeper(5000);
			partnerPage.waitForElementsOfPartnerPage("primaryAdministratorSearchBox");
		    sleeper(2000);
			softAssert.assertTrue(partnerPage.verifyMultiSelectionFilterFunctionalityFromDynamicDropdownOnPartnerPage(LanguageCode, "primaryAdministratorSearchBox", PartnerVariables.PRIMARY_ADMINISTRATOR_SEARCH, "noElementsDisplayTextForComboBoxColumn", "primaryAdministratorListOnDropdown", "primaryAdministratorList", "noItemsDisplayText"), "Filter functionality on selecting multiple options from primary adiminstrator column on partner list page is not working---->2");
			pressKey(Keys.ESCAPE);
			if (partnerPage.verifyElementsOfPartnerPage("clearFilter"))
				partnerPage.clickOnElementsOfPartnerPage("clearFilter");
			partnerPage.waitForElementsOfPartnerPage("tableOverlay");
			LOGGER.info("Verified filter functionality for primary administrator column");
			
			/*
			 * // Test Case 4 - This test case validates if the filter functionality is working properly for the searchbox on email column
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "emailSearchBox", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "emailList"),
			 * "Search functionality with incorrect search string is not working for email column on partner list page");
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "emailSearchBox", PartnerVariables.EMAIL_SEARCH, "noItemsDisplayText", "emailList"),
			 * "Search functionality is not working for email column on partner list page"); LOGGER.info("Verified filter functionality for email column");
			 * 
			 * // Test Case 5 - This test case validates if the filter functionality is working properly for the searchbox on phone number column
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "phoneSearchBox", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "phoneList"),
			 * "Search functionality with incorrect search string is not working for phone number column on partner list page");
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "phoneSearchBox", PartnerVariables.MOBILE_PHONE_SEARCH, "noItemsDisplayText", "phoneList"),
			 * "Search functionality is not working for phone number column on partner list page"); LOGGER.info("Verified filter functionality for phone number column");
			 * 
			 * // Test Case 6 - This test case validates if the filter functionality is working properly for the searchbox on address column
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "addressSearchBox", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "addressList"),
			 * "Search functionality with incorrect search string is not working for address column on partner list page");
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "addressSearchBox", PartnerVariables.ADDRESS_SEARCH, "noItemsDisplayText", "addressList"),
			 * "Search functionality is not working for address column on partner list page"); LOGGER.info("Verified filter functionality for address column");
			 */
			partnerPage.waitForElementsOfPartnerPage("authorizationDropDown");
			sleeper(3000);
			 
			partnerPage.clickOnElementsOfPartnerPage("authorizationDropDown");
			sleeper(3000);
			partnerPage.waitForElementsOfPartnerPage("authorizationDropDownOptions");
		    sleeper(2000);
			softAssert.assertTrue(partnerPage.verifyFilterSingleSelect(LanguageCode, "authorizationDropDownOptions", "dropDownElementListLabels", "authorizationList", "noItemsDisplayText"), "Filter functionality on selecting single option from certified column on partner list page is not working---->3");
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			partnerPage.clickOnElementsOfPartnerPage("clearFilter");
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			//partnerPage.waitUntilElementIsVisible("tableOverlay");
			partnerPage.waitForElementsOfPartnerPage("authorizationDropDown");
		
			partnerPage.clickOnElementsOfPartnerPage("authorizationDropDown");			
			sleeper(3000);
			partnerPage.waitForElementsOfPartnerPage("authorizationDropDownOptions");
		    sleeper(2000);
			softAssert.assertTrue(partnerPage.verifyFilterMultiSelect(LanguageCode, "authorizationDropDownOptions", "dropDownElementListLabels", "authorizationList", "noItemsDisplayText"), "Filter functionality on selecting multiple options from certified column on partner list page is not working---->4");
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			if (partnerPage.verifyElementsOfPartnerPage("clearFilter"))
				partnerPage.clickOnElementsOfPartnerPage("clearFilter");
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			LOGGER.info("Verified filter functionality for certified column");

			softAssert.assertAll();
			LOGGER.info("All test cases of filter functionality for partner page have passed");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyFilterFunctionality " + e.getMessage());
		}
	}

	/*
	 * This test case validates add partner functionality on partner list page
	 */
	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING" }, description = "813965, 813945, 813953, 830258, 823758")
	public final void verifyAddPartnerFunctionality() throws Exception {
		login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
		String currentUrl, tenantID;
		String actualMailContent = null, actualMailContentPBM=null , actualMailContentOBM=null;
		SoftAssert softAssert = new SoftAssert();
		String partnerID = generateRandomString(11);
		LOGGER.info("Redirected to companies list page");
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> partnerDetails = new ArrayList<String>();
		String partner_email = generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
		int attempt; 
		try {	
			gotoPartnersTab();
			resetTableConfiguration();
			LOGGER.info("Redirected to partner list page");
			partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
			
			partnerPage.clickOnElementsOfPartnerPage("addPartnerButton");
			LOGGER.info("Clicked on add partner button");
			partnerPage.enterTextForPartnerPage("addPartnerID", partnerID);
			partnerPage.enterTextForPartnerPage("compPartnerNameSearch", PartnerVariables.NAME);
	
			partnerPage.clickOnElementsOfPartnerPage("countryDropdown");
			partnerPage.enterTextForPartnerPage("countrySearchBox", PartnerVariables.EDITPROFILE_COUNTRY);
			sleeper(2000);
			partnerPage.selectFirstOptionFromDropdownOnPartnersPage("countryList");
	
			partnerPage.enterTextForPartnerPage("partnerFirstNameSearchBox", PartnerVariables.FIRST_NAME);
			partnerPage.enterTextForPartnerPage("partnerLastNameSearchBox", PartnerVariables.LAST_NAME);
			partnerPage.enterTextForPartnerPage("partnerEmailSearchBox", partner_email);
			partnerPage.enterTextForPartnerPage("partnerPhoneSearchBox", PartnerVariables.PHONE_NUMBER);
			LOGGER.info("Filled all form fields");
			
			partnerPage.clickOnElementsOfPartnerPage("next");
			//Verify Partner Business Manager Labels 
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("pbmTitle", getTextLanguage(LanguageCode, "daas_ui", "partners.list.partner_business_manager")), "Partner Business Manager Title on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("pbmFirstNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "Partner Business Manager First Name Label on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("pbmLastNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "Partner Business Managet Last Name Label on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("pbmEmailLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.email_address")), "Partner Business Managet Email Address Label on add partner popup is incorrect");
			
			//Enter data on Partner Business Manager fields
			partnerPage.enterTextForPartnerPage("pbmFirstNameSearchBox", PartnerVariables.PBM_FIRST_NAME);
			partnerPage.enterTextForPartnerPage("pbmLastNameSearchBox", PartnerVariables.PBM_LAST_NAME);
			partnerPage.enterTextForPartnerPage("pbmEmailSearchBox", getCredentials(environment,"PBM_MANGER_PARTNER_EMAIL"));
			
			//Verify Onboarding Business Manager Labels
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("obmTitle", getTextLanguage(LanguageCode, "daas_ui", "partners.list.onboarding_business_manager")), "Onboarding Business Manager Text on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("obmFirstNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "Onboarding Business Manager First Name Label on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("obmLastNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "Onboarding Business Manager Last Name Label on add partner popup is incorrect");
			softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("obmEmailLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.email_address")), "Onboarding Business Manager Email Address Label on add partner popup is incorrect");
			
			//Enter data on Onboarding Business Manager fields
			partnerPage.enterTextForPartnerPage("obmFirstNameSearchBox", PartnerVariables.OBM_FIRST_NAME);
			partnerPage.enterTextForPartnerPage("obmLastNameSearchBox", PartnerVariables.OBM_LAST_NAME);
			partnerPage.enterTextForPartnerPage("obmEmailSearchBox", getCredentials(environment,"OBM_MANGER_PARTNER_EMAIL"));
			partnerPage.clickByJavaScriptOnPartnerPage("pbmobmCheckBox");
			softAssert.assertTrue(partnerPage.getTextOfPartnerPage("notifyUserText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.business_manager_send_email")), "Send notification to users text is not present on popup");
			
			//Verify Cancel, Previous and Save buttons are available
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("previous"),"Previous button is not available");
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("save"),"Save button is not available");
			softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("cancel"),"Cancel button is not available");
			sleeper(2000);
			partnerPage.clickOnElementsOfPartnerPage("save");
			LOGGER.info("Clicked on save button");
			attempt=0;
			while(!partnerPage.waitForElementsOfPartnerPage("toastNotification")&& attempt<10){
			sleeper(1000);
			attempt++;
			}
			//partnerPage.waitForElementsOfPartnerPage("toastNotification");
			//Thread.sleep(4000);
			String partnerToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
			softAssert.assertTrue(partnerToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "partner.add.successful")), "Toast notification is not generated after adding a partner");
			LOGGER.info("Verified add partner functionality");
			Thread.sleep(4000);
			//Navigate to Partner details page.
			if(partnerPage.verifyElementIsPresent("clearSearchBoxFilter")) {
				partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
			}
			partnerPage.clickByJavaScriptOnPartnerPage("emailSearchBox");
			partnerPage.enterTextForPartnerPage("emailSearchBox", partner_email);
			Thread.sleep(5000);
			Assert.assertTrue(partnerPage.matchTextOfPartnerPage("emailList", partner_email), "Add partner functionality is not working");
			partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
			waitForPageLoaded();
			LOGGER.info("Redirected to details page");
			
			
			//Verify Partner Business Manager
			partnerPage.verifyElementIsEnableOfPartnerPage("partnerBusinessManagerLabel");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerBusinessManagerLabel",getTextLanguage(LanguageCode, "daas_ui", "partners.list.partner_business_manager")), "Partner Business Manager Text on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerBusinessManagerName", PartnerVariables.PBM_FIRST_NAME + " " + PartnerVariables.PBM_LAST_NAME), "Partner Business Manager Name of partner on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerBusinessManagerEmail", getCredentials(environment,"PBM_MANGER_PARTNER_EMAIL")), "Partner Business Manager Email of partner on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("parnterBusinessManagerEdit"), "Partner Business Manager field is not editable on Settings overview tab of Partner. ");
			
			//Verify OnBoarding Business Manager
			partnerPage.verifyElementIsEnableOfPartnerPage("onboardBusinessManagerLabel");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("onboardBusinessManagerLabel",getTextLanguage(LanguageCode, "daas_ui", "partners.list.onboarding_business_manager")), "Onboarding Business Manager Text on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("onboardBusinessManagerName", PartnerVariables.OBM_FIRST_NAME + " " + PartnerVariables.OBM_LAST_NAME), "Onboarding Business Manager Name of partner on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("onboardBusinessManagerEmail", getCredentials(environment,"OBM_MANGER_PARTNER_EMAIL")), "Onboarding Business Manager Email of partner on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("onboardingBusinessManagerEdit"), "Onboarding Business Manager field is not editable on Settings overview tab of Partner. ");
			
			// Get all the Admin names
			partnerDetails.add(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorName"));
			partnerDetails.add(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerBusinessManagerName"));
			partnerDetails.add(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardBusinessManagerName"));
			partnerPage.scrollOnPartnerPage("onboardPartnerLabel");
			
			//Enable Onboard authorized Toggle
			if (partnerDetailsPage.getAttributesOfPartnerDetailsPage("onboardPartnerToggle", "aria-checked").equals(CommonVariables.TRUE)) {
			LOGGER.info("Onboard Authorized toggle is already ON");
				//partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			}
			else{
			LOGGER.info("Onboard Authorized toggle is OFF.");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
				partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("onboardToggleLoader");
			}
			
			partnerPage.waitForElementsOfPartnerPage("onboardToggleLoader");
			sleeper(2000);
			 
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardPartnerValue").equalsIgnoreCase(CommonVariables.YES), "Status of onboard authorized partner on partner tile is incorrect after switching the toggle ON");

			//Enable Subscription Authorized toggle
			if(partnerDetailsPage.getAttributesOfPartnerDetailsPage("subscriptionAuthorizedToggle", "aria-checked").equals(CommonVariables.TRUE)) {
				LOGGER.info("Subscription Authorized toggle is already ON");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			}
			else{
				LOGGER.info("Subscription Authorized toggle is OFF.");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
			}
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
			sleeper(2000);
			  
			 
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("emailNotificationHeader", getTextLanguage(LanguageCode, "daas_ui", "partners.list.confirmation.modal.title")), "Email notification heading on popup is incorrect");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("emailNotificationText", getTextLanguage(LanguageCode, "daas_ui", "partners.list.confirmation.modal.content")), "Text on Email notification heading popup is incorrect");
			LOGGER.info("Verified email popup functionality");
			
			//Click on Allow button to receive Email
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("allowEmailNotification");
			String emailToastNotification = partnerDetailsPage.getTextOfPartnerDetailsPage("emailToastnotification");
			softAssert.assertTrue(emailToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "partners.list.email.update.success").replace("{name}", PartnerVariables.NAME)), "Toast notification is not generated after email notification is allowed");
			LOGGER.info("Verified toast message functionality");
			
			String expectedMailContentOBM = ( getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.subject")+ " "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.subject")+ " " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.callback_email.greeting").replace("%{team_name}", partnerDetails.get(2)) +" " + getTextLanguage(LanguageCode, "lhserver", "loe4.partner.contract.setup.mail.body.1").replace("{0}", partnerDetails.get(0))+
					" "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.thank.you")
					+ " "+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear())).replace("<b>", " ").replace("</b>", " ").replace("<b >", " ").replace(" ", " ");

			actualMailContentOBM = verifyEmailContent(environment, "OBM_MANGER_PARTNER_EMAIL", "PBM_MANGER_EMAIL_SUBJECT", "", true);

			softAssert.assertTrue(actualMailContentOBM.equalsIgnoreCase(expectedMailContentOBM), "OBM Mail content does not match");
			
			String expectedMailContent = ( getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.complete.mail.body.3")+ " "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.complete.mail.body.3")+ " " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.callback_email.greeting").replace("%{team_name}", partnerDetails.get(0)) + " "+ getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.complete.mail.body.1") +
					" "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.complete.mail.body.2") + " "+ getTextLanguage(LanguageCode, "idm", "sign_in.btn.sign_in") + " "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.thank.you")
					+ " "+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear())).replace("<b>", " ").replace("</b>", " ").replace("<b >", " ").replace(" ", " ");

			actualMailContent = partnerPage.verifyEmailContent("PBM_MANGER_EMAIL_SUBJECT", environment, partner_email,true);

			softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Partner Mail content does not match");

			String expectedMailContentPBM = ( getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.subject")+ " "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.subject")+ " " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.callback_email.greeting").replace("%{team_name}", partnerDetails.get(1)) +" " + getTextLanguage(LanguageCode, "lhserver", "loe4.partner.contract.setup.mail.body.1").replace("{0}", partnerDetails.get(0))+
					" "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.thank.you")
					+ " "+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear())).replace("<b>", " ").replace("</b>", " ").replace("<b >", " ").replace(" ", " ");

			actualMailContentPBM = verifyEmailContent(environment, "PBM_MANGER_PARTNER_EMAIL", "PBM_MANGER_EMAIL_SUBJECT", "", true);

			softAssert.assertTrue(actualMailContentPBM.equalsIgnoreCase(expectedMailContentPBM), "PBM Mail content does not match");
			/*logout();
			  
			login("PBM_PARTNER_EMAIL", "PBM_PARTNER_PASSWORD");
			gotoSettingsTab();
			LOGGER.info("Redirected to Settings page");
			waitForPageLoaded();
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			sleeper(2000); 
			
			//Verify Partner on summary tile
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorNameIdentity", PartnerVariables.NAME), "Partner name on summary tile does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorEmailIdentity", getCredentials(environment,"PBM_PARTNER_EMAIL")), "Partner Business Manager Email of partner on details page does not match with partner Settings page");
			
			//Verify Partner Business Manager
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerBusinessManagerLabel",getTextLanguage(LanguageCode, "daas_ui", "partners.list.partner_business_manager").toUpperCase()), "Partner Business Manager Text on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerBusinessManagerName", PartnerVariables.PBM_FIRST_NAME + " " + PartnerVariables.PBM_LAST_NAME), "Partner Business Manager Name of partner on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerBusinessManagerEmail", getCredentials(environment,"PBM_MANGER_PARTNER_EMAIL")), "Partner Business Manager Email of partner on details page does not match with partner Settings page");
			softAssert.assertFalse(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("parnterBusinessManagerEdit"), "Partner Business Manager field is not editable on Settings overview tab of Partner. ");
			
			//Verify OnBoarding Business Manager
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("onboardBusinessManagerLabel",getTextLanguage(LanguageCode, "daas_ui", "partners.list.onboarding_business_manager").toUpperCase()), "Onboarding Business Manager Text on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("onboardBusinessManagerName", PartnerVariables.OBM_FIRST_NAME + " " + PartnerVariables.OBM_LAST_NAME), "Onboarding Business Manager Name of partner on details page does not match with partner Settings page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("onboardBusinessManagerEmail", getCredentials(environment,"OBM_MANGER_PARTNER_EMAIL")), "Onboarding Business Manager Email of partner on details page does not match with partner Settings page");
			softAssert.assertFalse(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("onboardingBusinessManagerEdit"), "Onboarding Business Manager field is not editable on Settings overview tab of Partner. ");
			*/
		}catch(Exception e) {				
			Assert.fail("Exception occured in method verifyAddPartnerFunctionality " + e.getMessage());
		}finally {
			
			// Remove added partner
			//partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			partnerPage.clickByJavaScriptOnPartnerPage("navOnPartnerListPage");
			waitForPageLoaded();
			resetTableConfiguration();
			LOGGER.info("Redirected to partner list page");
			
			if(partnerPage.verifyElementIsPresent("clearSearchBoxFilter")) {
				partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
			}
			
			partnerPage.clickByJavaScriptOnPartnerPage("emailSearchBox");
			partnerPage.enterTextForPartnerPage("emailSearchBox", partner_email);
			//Thread.sleep(2000);
			partnerPage.waitForElementsOfPartnerPage("emailList");
			/*
			 * attempt=0; while(!partnerPage.waitForElementsOfPartnerPage("emailList")&&
			 * attempt<10){ sleeper(1000); attempt++; }
			 */
			Assert.assertTrue(partnerPage.matchTextOfPartnerPage("emailList", partner_email), "Add partner functionality is not working");
			partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
			waitForPageLoaded();
			LOGGER.info("Redirected to details page");
			
			currentUrl = partnerDetailsPage.getUrlOfCurrentPage();
			tenantID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
			partnerPage.removePartnerUsingAPI(tenantID);
			LOGGER.info("Removed Partner through API");
			softAssert.assertAll();
		}
	}

	// This test case validates details tile present on partner details page
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 249717")
	public final void verifyDetailsTile() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		String partner_email = generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
		String partnerID = generateRandomString(11);
		String partnerName = generateRandomString(11);
		String partnerIDs = generateRandomString(11);
		long partnerPhoneNumber = generatePhoneNumber();
		int attempt;
		try {
			LOGGER.info("Redirected to companies list page");
			gotoPartnersTab();
			LOGGER.info("Redirected to partner page");
			String currentPrimaryAdministrator = null, country = null, address1 = null, address2 = null, state = null, city = null, zipcode = null;
			ArrayList<String> address = new ArrayList<String>();
			waitForPageLoaded();
			resetTableConfiguration();
			
			
			partnerPage.clickOnElementsOfPartnerPage("addPartnerButton");
			LOGGER.info("Clicked on add partner button");
			partnerPage.enterTextForPartnerPage("addPartnerID", partnerID);
			partnerPage.enterTextForPartnerPage("compPartnerNameSearch", PartnerVariables.PARTNER_DETAILS_TEST_USER);
	
			partnerPage.clickOnElementsOfPartnerPage("countryDropdown");
			partnerPage.enterTextForPartnerPage("countrySearchBox", PartnerVariables.EDITPROFILE_COUNTRY);
			sleeper(2000);
			partnerPage.selectFirstOptionFromDropdownOnPartnersPage("countryList");
	
			partnerPage.enterTextForPartnerPage("partnerFirstNameSearchBox", PartnerVariables.FIRST_NAME);
			partnerPage.enterTextForPartnerPage("partnerLastNameSearchBox", PartnerVariables.LAST_NAME);
			partnerPage.enterTextForPartnerPage("partnerEmailSearchBox", partner_email);
			partnerPage.enterTextForPartnerPage("partnerPhoneSearchBox", PartnerVariables.PHONE_NUMBER);
			LOGGER.info("Filled all form fields");
			
			partnerPage.clickOnElementsOfPartnerPage("next");
			 
			//Enter data on Partner Business Manager fields
			partnerPage.enterTextForPartnerPage("pbmFirstNameSearchBox", PartnerVariables.PBM_FIRST_NAME);
			partnerPage.enterTextForPartnerPage("pbmLastNameSearchBox", PartnerVariables.PBM_LAST_NAME);
			partnerPage.enterTextForPartnerPage("pbmEmailSearchBox", getCredentials(environment,"PBM_MANGER_PARTNER_EMAIL"));
				
			//Enter data on Onboarding Business Manager fields
			partnerPage.enterTextForPartnerPage("obmFirstNameSearchBox", PartnerVariables.OBM_FIRST_NAME);
			partnerPage.enterTextForPartnerPage("obmLastNameSearchBox", PartnerVariables.OBM_LAST_NAME);
			partnerPage.enterTextForPartnerPage("obmEmailSearchBox", getCredentials(environment,"OBM_MANGER_PARTNER_EMAIL"));
			partnerPage.clickByJavaScriptOnPartnerPage("pbmobmCheckBox");
			softAssert.assertTrue(partnerPage.getTextOfPartnerPage("notifyUserText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.business_manager_send_email")), "Send notification to users text is not present on popup");
			
			partnerPage.clickOnElementsOfPartnerPage("save");
			LOGGER.info("Clicked on save button");
			attempt=0;
			while(!partnerPage.waitForElementsOfPartnerPage("toastNotification")&& attempt<10){
			sleeper(1000);
			attempt++;
			}
			String partnerToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
			softAssert.assertTrue(partnerToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "partner.add.successful")), "Toast notification is not generated after adding a partner");
			LOGGER.info("Verified add partner functionality");
			Thread.sleep(4000);
			//Navigate to Partner details page.
			if(partnerPage.verifyElementIsPresent("clearSearchBoxFilter")) {
				partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
			}
			partnerPage.clickByJavaScriptOnPartnerPage("emailSearchBox");
			
			//partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.PARTNER_DETAILS_TEST_USER);
			partnerPage.enterTextForPartnerPage("emailSearchBox", partner_email);
			Thread.sleep(5000);
			partnerInfo = partnerPage.getPartnerInfo();
			//partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
			attempt=0;
			while(!partnerPage.waitForElementsOfPartnerPage("firstRowPartner")&& attempt<10){
			sleeper(1000);
			attempt++;
			}
			partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
			LOGGER.info("Clicked on first partner in the list");
			LOGGER.info("Redirected to partner details page");
			partnerDetailsPage.verifyElementsOfPartnerDetailsPage("detailsTitle");
			

			// Test Case 1 - This test case validates edit partner ID functionality
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.id")), "Partner ID field for partner is not present on details tile");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerIDEdit");
			LOGGER.info("Clicked on edit partner ID button");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerIDTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.id")), "Title on edit partner ID popup is incorrect");
			partnerDetailsPage.enterTextForPartnerDetailsPage("partnerIDTextBox", partnerIDs);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerIDCancelButton");
			LOGGER.info("Clicked on cancel button");
			softAssert.assertFalse(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerIDValue").equals(partnerIDs), "Cancel functionality on partner ID popup is not working");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerIDEdit");
			LOGGER.info("Clicked on edit partner ID button");
			partnerDetailsPage.enterTextForPartnerDetailsPage("partnerIDTextBox", partnerIDs);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerIDSaveButton");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
			sleeper(2000);
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"), "Toast notification is not generated after changing partner ID of partner");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("toastNotification");

			// Test Case 2 - This test case validates partner name edit functionality
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("nameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name")), "Name field for partner is not present on details tile");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("nameValue", partnerInfo.get(1)), "Name of partner on details page does not match with list page");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("nameEdit");
			LOGGER.info("Clicked on edit name button");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("nameTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name")), "Title on edit partner name popup is incorrect");
			partnerDetailsPage.enterTextForPartnerDetailsPage("nameTextBox", partnerName);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("cancelName");
			LOGGER.info("Clicked on cancel button");
			softAssert.assertFalse(partnerDetailsPage.getTextOfPartnerDetailsPage("nameValue").equals(partnerName), "Cancel functionality on partner name popup is not working");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("nameEdit");
			LOGGER.info("Clicked on edit name button");
			partnerDetailsPage.enterTextForPartnerDetailsPage("nameTextBox", partnerName);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("saveName");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"), "Toast notification is not generated after changing name of partner");
			LOGGER.info("Clicked on save button");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			attempt=0;
			partnerPage.waitForElementsOfPartnerPage("nameValue");
			sleeper(2000);
		 
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("nameValue").equals(partnerName), "Save functionality on partner name popup is not working");

			// Test Case 3 - This test case validates partner phone number edit functionality
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("phoneLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.phone")), "Phone number field for partner is not present on details tile");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("phoneValue", partnerInfo.get(5)), "Phone number of partner on details page does not match with list page");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("phoneEdit");
			LOGGER.info("Clicked on edit phone button");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("phoneTitle");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("phoneTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.phone")), "Title on edit partner phone number popup is incorrect");
			partnerDetailsPage.enterTextForPartnerDetailsPage("phoneTextBox", String.valueOf(partnerPhoneNumber));
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("cancelPhone");
			LOGGER.info("Clicked on cancel button");
			softAssert.assertFalse(partnerDetailsPage.getTextOfPartnerDetailsPage("phoneValue").equals(String.valueOf(partnerPhoneNumber)), "Cancel functionality on partner phone number popup is not working");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("phoneEdit");
			LOGGER.info("Clicked on edit phone button");
			partnerDetailsPage.enterTextForPartnerDetailsPage("phoneTextBox", String.valueOf(partnerPhoneNumber));
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("savePhone");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"), "Toast notification is not generated after changing phone number of partner");
			LOGGER.info("Clicked on save button");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("phoneValue");
			sleeper(2000);
			 
			
			
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("phoneValue").equals(String.valueOf(partnerPhoneNumber)), "Save functionality on partner phone number popup is not working");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("toastNotification");

			// Test Case 4 - This test case validates if address field is present under details tile on partner details page
			partnerDetailsPage.scrollOnPartnerDetailsPage("addressLabel");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("addressLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.address")), "Address field for partner is not present on details tile");
			country = partnerDetailsPage.getTextOfPartnerDetailsPage("currentAddressCountry");
			//address = partnerDetailsPage.getTextOfListOfPartnerDetailsPage("currentAddress");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("addressEditButton");
			LOGGER.info("Clicked on Address edit button");
			address1 = partnerDetailsPage.generateRandomString(11);
			address2 = partnerDetailsPage.generateRandomString(11);
			state = partnerDetailsPage.generateRandomString(11);
			city = partnerDetailsPage.generateRandomString(11);
			zipcode = partnerDetailsPage.generateRandomNumber();
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("addressPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "global.address")), "Header on address popup of partner is incorrect");
			partnerDetailsPage.enterTextForPartnerDetailsPage("addressStreetLine1TextBox", address1);

			partnerDetailsPage.enterTextForPartnerDetailsPage("addressStreetLine2TextBox", address2);
			partnerDetailsPage.enterTextForPartnerDetailsPage("stateTextBox", state);
			partnerDetailsPage.enterTextForPartnerDetailsPage("cityTextBox", city);
			partnerDetailsPage.enterTextForPartnerDetailsPage("zipCodeTextBox", zipcode);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("countryDropDown");
			partnerDetailsPage.selectValueFromDropdownOnPartnerDetailspage("countryList", country);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("addressCancelButton");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("currentAddressCountry", country), "Cancel functionality on address popup is not working");
			//softAssert.assertTrue(partnerDetailsPage.getTextOfListOfPartnerDetailsPage("currentAddress").equals(address), "Cancel functionality on address popup is not working");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("addressEditButton");
			partnerDetailsPage.enterTextForPartnerDetailsPage("addressStreetLine1TextBox", address1);
			partnerDetailsPage.enterTextForPartnerDetailsPage("addressStreetLine2TextBox", address2);
			partnerDetailsPage.enterTextForPartnerDetailsPage("stateTextBox", state);
			partnerDetailsPage.enterTextForPartnerDetailsPage("cityTextBox", city);
			partnerDetailsPage.enterTextForPartnerDetailsPage("zipCodeTextBox", zipcode);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("countryDropDown");
			country = partnerDetailsPage.selectValueFromDropdownOnPartnerDetailspage("countryList", country);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("addressSaveButton");
//			attempt=0;
//			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
//			 
//			sleeper(2000);
			 
			attempt=0;
			while(!partnerPage.waitForElementsOfPartnerPage("toastNotification")&& attempt<10){
			sleeper(1000);
			attempt++;
			}
			
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"), "Toast notification is not generated after changing address of partner");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("toastNotification");
			address = partnerDetailsPage.getTextOfListOfPartnerDetailsPage("currentAddress");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("currentAddressCountry", country), "Partner address edit functionality is not working");
			softAssert.assertTrue(partnerDetailsPage.getTextOfListOfPartnerDetailsPage("currentAddress").equals(address), "Partner address edit functionality is not working");

			// Test Case 5 - This test case validates if the primary administrator of partner on list and details page is the same
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorLabelMSP").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin")), "Primary administrator field for partner is not present on details tile");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorNameMSP", partnerInfo.get(2)), "Primary administrator name of partner on details page does not match with list page");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorEmailMSP", partnerInfo.get(3)), "Primary administrator email of partner on details page does not match with list page");

			// Test Case 6 - This test case validates if the primary administrator of partner can be updated successfully
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorEditMSP");
//			LOGGER.info("Clicked on edit primary administrator button");
//			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
//			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
//			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorHeaderMSP", getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_admin")), "Title on primary administrator popup is incorrect");
//			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorSubTitleMSP", getTextLanguage(LanguageCode, "daas_ui", "partners.list.name")), "Subtitle on primary administrator popup is incorrect");
//			partnerDetailsPage.selectValueFromDropdownOnPartnerDetailspage("primaryAdministratorListMSP", partnerInfo.get(3));
//			LOGGER.info("Selected value on dropdown");
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorCancelButtonMSP");
//			LOGGER.info("Clicked on cancel button");
//			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorNameMSP").equals(partnerInfo.get(2)), "Cancel functionality for primary administrator is not working");
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorEditMSP");
//			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
//			LOGGER.info("Clicked on edit primary administrator button");
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
//			currentPrimaryAdministrator = partnerDetailsPage.selectValueFromDropdownOnPartnerDetailspage("primaryAdministratorListMSP", partnerInfo.get(2));
//			LOGGER.info("Selected value on dropdown");
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorSaveButtonMSP");
//			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
//			LOGGER.info("Clicked on save button");
//			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("primaryAdministratorNameMSP");
			 
			sleeper(2000);
		 
		//	softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorNameMSP").equals(currentPrimaryAdministrator), "Primary administrator is not getting updated");
		//	LOGGER.info("Verified edit functionality for primary administrator on partner details page");
			softAssert.assertAll();
			LOGGER.info("Verification of support team member details on details tile of support team details page done successfully");

		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyDetailsTile " + e.getMessage());
		}

		finally {
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorEditMSP");
//			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
//			LOGGER.info("Clicked on edit primary administrator button");
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
//			partnerDetailsPage.selectSpecificValueFromDropdownOnPartnerDetailspage("primaryAdministratorListMSP", PartnerVariables.PARTNER_DETAILS_TEST_PRIMARY_ADMINISTRATOR, "primaryAdministratorDropdownMSP");
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorSaveButtonMSP");
//			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
//			LOGGER.info("Updated the primary administrator to its default state");
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("nameEdit");
//			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("nameTextBox");
//			partnerDetailsPage.enterTextForPartnerDetailsPage("nameTextBox", PartnerVariables.PARTNER_DETAILS_TEST_USER);
//			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("saveName");
//			LOGGER.info("Updated the partner name to its original state");
//			attempt=0;
//			while(!partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification")&& attempt<10){
//			sleeper(1000);
//			attempt++;
//			}
//			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("toastNotification");
		}
	}

	// This test case validates identity tile present on partner details page
	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "STABILIZATION_STAGING" }, description = "NFR 249717, BUG 318168")
	public final void verifyIdentityTile() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		LOGGER.info("Redirected to partner page");
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		waitForPageLoaded();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
		partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.PARTNER_DETAILS_TEST_USER2);
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerInfo = partnerPage.getPartnerInfo();
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		LOGGER.info("Clicked on first partner in the list");
		LOGGER.info("Redirected to partner details page");
		waitForPageLoaded();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerDetailsTitle", partnerInfo.get(1)), "Title on partner details page is incorrect");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("breadcrumbText", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.partner_details")), "Text on breadcrumb on partner details page is incorrect");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("breadcrumbLink"), "Link on breadcrumb of partner details page is missing");
		LOGGER.info("Verified breadcrumb on partner details page");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerNameIdentity").equalsIgnoreCase(partnerInfo.get(1)), "Name of partner on identity tile of partner details page is incorrect");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorNameIdentity", partnerInfo.get(2)), "Primary adminstrator name on identity tile of partner details page is incorrect");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerAdministratorEmail", partnerInfo.get(3)), "Primary administrator email on identity tile of partner details page is incorrect");
		LOGGER.info("Verified partner details on identity tile");
		softAssert.assertAll();
		LOGGER.info("All testcases of verifyIdentityTile passed successfully");
	}

	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "STABILIZATION_STAGING" }, description = "Test Case 294097")
	public final void verifyOverviewTabSettingsPartner() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		LOGGER.info("Redirected to companies list page");
		waitForPageLoaded();
		gotoPartnersTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to partner page");
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		waitForPageLoaded();
		partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.PARTNER_DETAILS_AC_TEST_USER);
		sleeper(4000);
		partnerPage.scrollOnPartnerPage("authorizationColumnHeader");
		waitForPageLoaded();
		partnerInfo = partnerPage.getPartnerInfo();
		logout();
		login("AC_PARTNERUSER_EMAIL", "AC_PARTNERUSER_PASSWORD");
		waitForPageLoaded();
		gotoSettingsTab();
		LOGGER.info("Redirected to Settings page");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");

		// Test Case 1 - This test case validates partner name(Title) on Identity tile
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerNameIdentity").equalsIgnoreCase(partnerInfo.get(1)), "Name of partner "+partnerInfo.get(1)+" on identity tile of partner Settings page is incorrect");
		LOGGER.info("Name of partner "+partnerInfo.get(1)+" on identity tile of partner Settings page is correct");
		// Test Case 2 - This test case validates partner name on Identity tile
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorNameIdentity", partnerInfo.get(2)), "Primary adminstrator name "+partnerInfo.get(2)+" on identity tile of partner Settings page is incorrect");
		LOGGER.info("Primary adminstrator name "+partnerInfo.get(2)+" on identity tile of partner Settings page is correct");
		// Test Case 3 - This test case validates email on Identity tile
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorEmailIdentity", partnerInfo.get(3)), "Primary administrator email "+partnerInfo.get(3)+" on identity tile of partner Settings page is incorrect");

		// Test Case 4 - This test case validates partner name on overview tab.
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("nameValue", partnerInfo.get(1)), "Name of partner "+partnerInfo.get(1)+" on details page does not match with partner Settings page");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("editName"), "Name field is not editable on Settings overview tab of Partner. ");

		// Test Case 5 - This test case validates partner phone number on overview tab.
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("phoneValue", partnerInfo.get(5)), "Phone number "+partnerInfo.get(5)+" of partner on details page does not match with list page");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("editPhoneNumber"), "Phone number field is not editable on Settings overview tab of Partner. ");

		// Test Case 6 - This test case validates partner address on overview tab.
		partnerDetailsPage.scrollOnPartnerDetailsPage("addressLabel");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("addressLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.address")), "Address field for partner is not present on Settings page.");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("editAddress"), "Addess field is not editable on Settings overview tab of Partner. ");

		// Test Case 7 - This test case validates partner administrator dropdown on overview tab.
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorLabelMSP").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin")), "Primary administrator field for partner is not present on Settings tile");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("editPrimaryAdministrator"), "Primary Administrator dropdown is not editable on Settings overview tab of Partner. ");

		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorNameMSP", partnerInfo.get(2)), "Primary administrator name of partner on details page does not match with Settings page");

		partnerDetailsPage.scrollOnPartnerDetailsPage("primaryAdministratorEmailMSP");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorEmailMSP", partnerInfo.get(3)), "Primary administrator email of partner on details page does not match with Settings page");

		// Test Case 8 - This test case validates authorized status on overview tab.
		partnerDetailsPage.scrollOnPartnerDetailsPage("onboardPartnerValue");
		if(partnerInfo.get(8).contains(partnerDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "roles.onboard_authorized"))) {
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("onboardPartnerValue", CommonVariables.YES), "Status of onboard authorized partner on Settings page is incorrect");	
		}else{
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("onboardPartnerValue", CommonVariables.NO), "Status of onboard authorized partner on Settings page is incorrect");
		}

		//Verify preferences tab
		partnerDetailsPage.scrollOnPartnerDetailsPage("preferencesTab");
		partnerDetailsPage.clickByJavaScriptOnPartnerDetailsPage("preferencesTab");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("serviceNowTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.service_now").toUpperCase()), "Service Now tile under preferences tab is not loaded successfully");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("authenticationTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.sign_in.title").toUpperCase()), "Authentication tile under preferences tab is not loaded successfully");
		LOGGER.info("Verified tiles under preferences tab");

		//Verify roles and permissions tab
		settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("rolesTileHeader"), "Roles tile not loaded successfully");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("rolesTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.form.roles").toUpperCase()), "Roles title did not match on partner settings page");
		LOGGER.info("Verified tiles under Roles and Permission tab");
		//Verify notifications tab
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("notificationsTab");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("deviceNotificationsTItle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications").toUpperCase()), "Notifications tab not loaded successfully");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("deviceNotificationDescription"), "Device notifications description not present");
		LOGGER.info("Verified tiles under Notification tab");
		//Verify help and support tab
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("helpAndSupportTab");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("callBackTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.callback.title").toUpperCase()), "Callback tile under help and support tab of partner settings page not loaded successfully");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("directSupportTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.direct_support.title").toUpperCase()), "Direct support tile under help and support tab of partner settings page not loaded successfully");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("emailSupportTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "settings.email_support.title").toUpperCase()), "Email support tile under help and support tab of partner settings page is not loaded successfully");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("hoursOfOperationTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.title").toUpperCase()), "Hours of operation tile under help and support tab of partner settings page is not loaded successfully");
		LOGGER.info("Verified tiles under help and support tab");
		softAssert.assertAll();
		LOGGER.info("All testcases of verifyOverviewTabSettingsPartner passed successfully");
	}

	//Marked this as false since this tile is removed from partner details page
	// This test case validates companies managed by the partner tile present on partner details page
	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "STABILIZATION_STAGING" }, description = "NFR 214738", enabled = false)
	public final void verifyCompaniesManagedByThePartnerTile() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to partner page");
		resetTableConfiguration();
		waitForPageLoaded();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.PARTNER_DETAILS_TEST_USER);
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		sleeper(3000);
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		LOGGER.info("Clicked on first partner in the list");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		LOGGER.info("Redirected to partner details page");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("companiesTitle", getTextLanguage(LanguageCode, "daas_ui", "partners.section.company_managed_partner")), "Title on companies managed by partner tile is incorrect");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("companyNameColumn", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name").toUpperCase()), "Company name column is not present under companies managed by partner tile");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAccountNameColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.details.section.primary_account_name").toUpperCase()), "Primary administrator name column is not present under companies managed by partner tile");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAccountEmailColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.details.section.primary_account_email").toUpperCase()), "Primary administrator email column is not present under companies managed by partner tile");
		if (partnerDetailsPage.verifyElementsOfPartnerDetailsPage("primaryAccountPhoneColumn"))
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAccountPhoneColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.details.section.primary_account_phone").toUpperCase()), "Primary administrator phone number column is not present under companies managed by partner tile");
		LOGGER.info("Verified columns under companies managed by the partner tile");
		gotoRootCompaniesTab();
		LOGGER.info("Clicked on companies tab");
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		sleeper(5000);
		companiesPage.clickOnElementsOfCompaniesPage("partnerFilterDropdown");
		LOGGER.info("Clicked on partner dropdown");
		companiesPage.enterTextForCompaniesPage("partnerFilterSearchBox", PartnerVariables.PARTNER_DETAILS_TEST_USER);
		sleeper(4000);
		companiesPage.selectFirstOptionFromDropdownOnCompaniesPage("partnerListOnPopup");
		LOGGER.info("Selected partner on companies list page");
		pressKey(Keys.ESCAPE);
		softAssert.assertTrue(partnerPage.verifyCompaniesOnCompaniesManagedByThePartnerTile("noItemsDisplayText"), "List of companies on companies managed by partner tile on company details page is incorrect");
		softAssert.assertAll();
		LOGGER.info("All test cases of verifyCompaniesManagedByThePartnerTile passed successfully");
	}

	/**
	 * This method will verify the table configuration test cases of partners list page
	 */
	@Test(priority = 9, description = "NFR 214738, 217312", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING"})
	public final void verifyTableConfigurationTestCases() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		String tenantID = getValueFromToken("tenant");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		waitForPageLoaded();
		Thread.sleep(3000);
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, PartnerVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for partners list page");
		ArrayList<String> id = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name")));
		ArrayList<String> columnName = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name"), getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin"), getTextLanguage(LanguageCode, "daas_ui", "partners.list.email"), getTextLanguage(LanguageCode, "daas_ui", "global.form.status"), getTextLanguage(LanguageCode, "daas_ui", "partners.list.phone"), getTextLanguage(LanguageCode, "daas_ui", "partners.list.address"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.auth")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "false", "false"));

		verifyTableConfigurationTests(columnName, checkboxValue, id);
	}

	@Test(priority = 10, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI" }, description = "Test Case ID : 280835")
	public final void verifyPartnersListPage() throws Exception {
		login("ROOT_ADMIN_USER", "ROOT_ADMIN_PASSWORD");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to partners list page");
		waitForPageLoaded();
		partnerPage.clearFiltersOfPartnersListPage("clearfilter");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("listTable"), "Table on list page is not loaded successfully");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("nameHeader"), "Default column is not present on list page");
		softAssert.assertAll();
		LOGGER.info("Partners list page is loaded successfully");
	}

	@Test(priority = 11, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" }, description = "Test Case ID : 280836")
	public final void verifyPartnersDetailsPage() throws Exception {
		login("ROOT_ADMIN_USER", "ROOT_ADMIN_PASSWORD");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to partners list page");
		waitForPageLoaded();
		partnerPage.clearFiltersOfPartnersListPage("clearfilter");
		partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		waitForPageLoaded();
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
		LOGGER.info("Redirected to details page");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("detailsTitle"), "Details tile on details page is not loaded successfully");
		softAssert.assertAll();
		LOGGER.info("Partners details page is loaded successfully");
	}

	@Test(priority = 12, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID : 280836", enabled = false)
	public final void verifyPartnerAdminRemoval() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String resellerEmail = generateRandomString(11) + "@mailinator.com";
		String rootemail = getCredentials(environment,"ROOT_ADMIN_NEW_USER_COMPANIES");
		LOGGER.info("Redirected to partners list page");
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		Assert.assertTrue(partnerPage.addSingleMember(LanguageCode, resellerEmail , rootemail), "Partner admin not added successfully, cannot proceed further");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.enterTextForPartnerPage("emailSearchBox", resellerEmail);
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		waitForPageLoaded();
		LOGGER.info("Redirected to details page");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("removeButton");
		LOGGER.info("Clicked on remove button");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("removeTitle");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("removeTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.details.modal.remove.title")), "Title on remove partner popup is incorrect");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("removeSubTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "users.remove.message").replace("{name}", PartnerVariables.NAME)), "Subtitle on remove partner popup is incorrect");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("no");
		LOGGER.info("Clicked on no");
		partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("removeButton"), "Cancel functionality on partner admin deletion popup is not working");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("removeButton");
		LOGGER.info("Clicked on remove button");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("yes");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("yes");
		LOGGER.info("Clicked on yes");
		partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("noItemsDisplayText"), "Partner admin deletion functionality is not working");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.clearFiltersOfPartnersListPage("clearfilter");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.RESELLER_REMOVAL_TEST_USER);
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.clickOnElementsOfPartnerPage("firstRowPartner");
		waitForPageLoaded();
		partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
		LOGGER.info("Redirected to details page");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("removeButton");
		LOGGER.info("Clicked on remove button");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("removeTitle");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("removeTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.details.modal.remove.title")), "Title on remove partner popup is incorrect");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("no");
		LOGGER.info("Clicked on no");
		partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("removeButton"), "Cancel functionality on partner admin deletion popup is not working");
		softAssert.assertAll();
		LOGGER.info("Test case for partner admin deletion functionality passed successfully");
	}

	@Test(priority = 13, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "User Story : 287385")
	public final void verifyOnboardAuthorizedPartnerTile() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to partner page");
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.waitForElementsOfPartnerPage("nameSearchBox");
		partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.PARTNER_DETAILS_TEST_USER);
		sleeper(3000); // required to search the partner in the list
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.scrollOnPartnerPage("authorizationColumnHeader");
		partnerInfo = partnerPage.getPartnerInfo();
		partnerPage.clickOnElementsOfPartnerPage("firstRowPartner");
		LOGGER.info("Clicked on first partner in the list");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		LOGGER.info("Redirected to partner details page");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardPartnerTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.authorized.title")), "Title on onboard authorized partner tile is incorrect");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardPartnerSubTitle").contains(getTextLanguage(LanguageCode, "daas_ui", "partner.authorized.description")), "Subtitle on onboard authorized partner tile is incorrect");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardPartnerSubTitle").contains(getTextLanguage(LanguageCode, "daas_ui", "partner.authorized.link")), "Subtitle on onboard authorized partner tile is incorrect");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardPartnerLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "roles.onboard_authorized")), "Label on onboard authorized partner tile is incorrect");
		
		if(partnerInfo.get(7).contains(partnerDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "roles.onboard_authorized"))) {
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardPartnerValue").equalsIgnoreCase(CommonVariables.YES), "Status of onboard authorized partner on partner tile is incorrect");	
		}else{
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardPartnerValue").equalsIgnoreCase(CommonVariables.NO), "Status of onboard authorized partner on partner tile is incorrect");
		}

		if (partnerDetailsPage.getAttributesOfPartnerDetailsPage("onboardPartnerToggle", "aria-checked").equals(CommonVariables.TRUE)) {
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
		}

		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardPartnerValue").equalsIgnoreCase(CommonVariables.YES), "Status of onboard authorized partner on partner tile is incorrect after switching the toggle ON");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("authorizationLegend").equalsIgnoreCase(PartnerVariables.AUTHORIZED_LEGEND), "Authorization legend on identity tile of partner details page is incorrect");
		gotoPartnersTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to partner page");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
		partnerPage.scrollOnPartnerPage("authorizationColumnHeader");
		softAssert.assertTrue(partnerPage.getTextOfPartnerPage("authorizationValue").contains(getTextLanguage(LanguageCode, "daas_ui", "roles.onboard_authorized")), "Updated status of onboard authorized partner on partner list page is incorrect");
		softAssert.assertAll();
		LOGGER.info("All test cases of OnboardAuthorizedPartnerTile have passed");
	}

	// This test case validates pagination functionality on partner list page
	@Test(priority = 14, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, enabled = false)
	public final void verifyPaginationOnMSPPage() throws Exception{
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		try {
			SoftAssert softAssert = new SoftAssert();
			LOGGER.info("Redirected to companies list page");
			gotoMSPTab();
			LOGGER.info("Redirected to MSP list page");
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();

			partnerPage.waitForElementsOfPartnerPage("paginationDropdownMenu");
			softAssert.assertTrue(partnerPage.verifyElementIsEnableOfPartnerPage("paginationDropdownMenu"), "Pagination Dropdown not availble");
			softAssert.assertTrue(partnerPage.verifyElementIsClickableOfPartnerPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
			softAssert.assertTrue(partnerPage.waitForElementsOfPartnerPage("navigationItemDiv"), "Navigation Item are not available");
			getPaginationInfo();
			LOGGER.info("get Pagination Information ");
			softAssert.assertTrue(partnerPage.verifyElementIsEnableOfPartnerPage("navigationItemActivePage"), "The active page navigation link is not disabled");
			softAssert.assertTrue(partnerPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
			if (!verifySelectedOptionForNewSelection(selectedOption, CommonVariables.SELETEDFIFTY)) {
				if (changeSelectedOption(totalCount, CommonVariables.SELETEDTWENTYFIVE)) {
					partnerPage.selectElementFromDropDownOfPartnerPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDTWENTYFIVE);
					partnerPage.waitForElementsOfPartnerPage("tableOverlay");
					getPaginationInfo();
					softAssert.assertTrue(partnerPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
					softAssert.assertTrue(partnerPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
				} else {

					LOGGER.info("Selected user has less than " + CommonVariables.SELETEDTWENTYFIVE + " records");
				}
			} else {
				if (changeSelectedOption(totalCount, CommonVariables.SELETEDFIFTY)) {
					partnerPage.selectElementFromDropDownOfPartnerPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDFIFTY));
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDFIFTY);
					partnerPage.waitForElementsOfPartnerPage("tableOverlay");
					getPaginationInfo();
					softAssert.assertTrue(partnerPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
					softAssert.assertTrue(partnerPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
				} else {

					LOGGER.info("Selected user has less than " + CommonVariables.SELETEDFIFTY + " records");
				}
			}
			partnerPage.waitForElementsOfPartnerPage("tableOverlay");
			if (partnerPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
				softAssert.assertTrue(partnerPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
				partnerPage.waitForElementsOfPartnerPage("navigationItemNext");
				partnerPage.clickOnElementsOfPartnerPage("navigationItemNext");
				partnerPage.waitForElementsOfPartnerPage("tableOverlay");
				getPaginationInfo();
				partnerPage.waitForElementsOfPartnerPage("navigationItemPrevious");
				if (partnerPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
					softAssert.assertTrue(partnerPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
					partnerPage.clickOnElementsOfPartnerPage("navigationItemPrevious");
				} else {
					LOGGER.info("Previous button is disabled");
				}
			} else if (partnerPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
				softAssert.assertTrue(partnerPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
				partnerPage.waitForElementsOfPartnerPage("navigationItemPrevious");
				partnerPage.clickOnElementsOfPartnerPage("navigationItemPrevious");
				partnerPage.waitForElementsOfPartnerPage("tableOverlay");
				getPaginationInfo();
				partnerPage.waitForElementsOfPartnerPage("navigationItemNext");
				if (partnerPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
					softAssert.assertTrue(partnerPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
					partnerPage.clickOnElementsOfPartnerPage("navigationItemNext");
				} else {
					LOGGER.info("Next button is disabled");
				}
			} else {
				LOGGER.info("Previous and Next button both are disabled");
			}
			softAssert.assertAll();
			LOGGER.info("Verification of pagination functionality on MSP list page done successfully ");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyPaginationOnMSPPage " + e.getMessage());
		}
	}

	// This test case validates all filters' functionality on partner list page
	@Test(priority = 15, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 214738", enabled = false)
	public final void verifyFilterFunctionalityOnMSPListPage() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
			LOGGER.info("Redirected to partner list page");
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			gotoMSPTab();
			resetTableConfiguration();
			waitForPageLoaded();
			partnerPage.waitForElementsOfPartnerPage("tableOverlay");
			sleeper(2000);

			// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on name column
			softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBox", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "nameList"), "Search functionality with incorrect search string is not working for name column on partner list page");
			softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBox", PartnerVariables.NAME_SEARCH, "noItemsDisplayText", "nameList"), "Search functionality is not working for name column on partner list page");
			LOGGER.info("Verified filter functionality for name column");

			// Test Case 3 - This test case validates if the filter functionality is working properly for the dropdown on the primary adiminstrator column
			partnerPage.waitForElementsOfPartnerPage("primaryAdministratorDropdown");
			partnerPage.clickByJavaScriptOnPartnerPage("primaryAdministratorDropdown");
			partnerPage.waitForElementsOfPartnerPage("primaryAdministratorSearchBox");
			softAssert.assertTrue(partnerPage.verifySingleSelectionFilterFunctionalityFromDynamicDropdownOnPartnerPage(LanguageCode, "primaryAdministratorSearchBox", PartnerVariables.PRIMARY_ADMINISTRATOR_SEARCH, "noElementsDisplayTextForComboBoxColumn", "primaryAdministratorListOnDropdown", "primaryAdministratorList", "noItemsDisplayText"), "Filter functionality on selecting single option from primary adiminstrator column on partner list page is not working");
			if (partnerPage.verifyElementsOfPartnerPage("clearFilter"))
				partnerPage.clickOnElementsOfPartnerPage("clearFilter");

			partnerPage.waitForElementsOfPartnerPage("primaryAdministratorDropdown");
			partnerPage.clickByJavaScriptOnPartnerPage("primaryAdministratorDropdown");
			partnerPage.waitForElementsOfPartnerPage("primaryAdministratorSearchBox");
			softAssert.assertTrue(partnerPage.verifyMultiSelectionFilterFunctionalityFromDynamicDropdownOnPartnerPage(LanguageCode, "primaryAdministratorSearchBox", PartnerVariables.PRIMARY_ADMINISTRATOR_SEARCH, "noElementsDisplayTextForComboBoxColumn", "primaryAdministratorListOnDropdown", "primaryAdministratorList", "noItemsDisplayText"), "Filter functionality on selecting multiple options from primary adiminstrator column on partner list page is not working");
			if (partnerPage.verifyElementsOfPartnerPage("clearFilter"))
				partnerPage.clickOnElementsOfPartnerPage("clearFilter");
			partnerPage.waitForElementsOfPartnerPage("tableOverlay");
			LOGGER.info("Verified filter functionality for primary administrator column");
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");

			// Test Case 4 - This test case validates if the filter functionality is working properly for the searchbox on email column
			softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "emailSearchBox", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "emailList"), "Search functionality with incorrect search string is not working for email column on partner list page");
			softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "emailSearchBox", PartnerVariables.EMAIL_SEARCH, "noItemsDisplayText", "emailList"), "Search functionality is not working for email column on partner list page");
			LOGGER.info("Verified filter functionality for email column");

			/*
			 * // Test Case 5 - This test case validates if the filter functionality is working properly for the searchbox on phone number column
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "phoneSearchBox", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "phoneList"),
			 * "Search functionality with incorrect search string is not working for phone number column on partner list page");
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "phoneSearchBox", PartnerVariables.MOBILE_PHONE_SEARCH, "noItemsDisplayText", "phoneList"),
			 * "Search functionality is not working for phone number column on partner list page"); LOGGER.info("Verified filter functionality for phone number column");
			 * 
			 * // Test Case 6 - This test case validates if the filter functionality is working properly for the searchbox on address column
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "addressSearchBox", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "addressList"),
			 * "Search functionality with incorrect search string is not working for address column on partner list page");
			 * softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "addressSearchBox", PartnerVariables.ADDRESS_SEARCH, "noItemsDisplayText", "addressList"),
			 * "Search functionality is not working for address column on partner list page"); LOGGER.info("Verified filter functionality for address column");
			 */

			softAssert.assertAll();
			LOGGER.info("All test cases of filter functionality for MSP page have passed");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyFilterFunctionalityOnMSPListPage " + e.getMessage());
		}
	}

	// This test case validates details tile present on partner details page
	@Test(priority = 16, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "STABILIZATION_STAGING" })
	public final void verifyDetailsTileOnMSPDetailsPage() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		String currentPrimaryAdministrator = null, country = null, address1 = null, address2 = null, state = null, city = null, zipcode = null;
		ArrayList<String> address = new ArrayList<String>();
		try {
			gotoMSPTab();
			LOGGER.info("Redirected to MSP page");
			waitForPageLoaded();
			resetTableConfiguration();
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			partnerPage.waitForElementsOfPartnerPage("nameSearchBox");
			partnerPage.enterTextForPartnerPage("emailSearchBox", getCredentials(environment, "MSP_DETAILS_USER_EMAIL"));
			Thread.sleep(3000);
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			partnerInfo = partnerPage.getPartnerInfo();
			partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
			partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
			LOGGER.info("Clicked on first MSP in the list");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
			LOGGER.info("Redirected to MSP details page");
			partnerDetailsPage.verifyElementsOfPartnerDetailsPage("partnerDetailsTitle");
			String MSPName = generateRandomString(11);
			long MSPPhoneNumber = generatePhoneNumber();

			// Test Case 1 - This test case validates partner name edit functionality
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("nameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name")), "Name field for MSP is not present on details tile");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("nameValue", partnerInfo.get(1)), "Name of MSP on details page does not match with list page");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("nameEdit");
			LOGGER.info("Clicked on edit name button");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("nameTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name")), "Title on edit MSP name popup is incorrect");
			partnerDetailsPage.enterTextForPartnerDetailsPage("nameTextBox", MSPName);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("cancelName");
			LOGGER.info("Clicked on cancel button");
			softAssert.assertFalse(partnerDetailsPage.getTextOfPartnerDetailsPage("nameValue").equals(MSPName), "Cancel functionality on MSP name popup is not working");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("nameEdit");
			LOGGER.info("Clicked on edit name button");
			partnerDetailsPage.enterTextForPartnerDetailsPage("nameTextBox", MSPName);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("saveName");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"), "Toast notification is not generated after changing name of MSP");
			LOGGER.info("Clicked on save button");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("nameValue").equals(MSPName), "Save functionality on MSP name popup is not working");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");

			// Test Case 2 - This test case validates partner phone number edit functionality
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("phoneLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.phone")), "Phone number field for MSP is not present on details tile");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("phoneValue", partnerInfo.get(5)), "Phone number of MSP on details page does not match with list page");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("phoneEdit");
			LOGGER.info("Clicked on edit phone button");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("phoneTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.phone")), "Title on edit MSP phone number popup is incorrect");
			partnerDetailsPage.enterTextForPartnerDetailsPage("phoneTextBox", String.valueOf(MSPPhoneNumber));
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("cancelPhone");
			LOGGER.info("Clicked on cancel button");
			softAssert.assertFalse(partnerDetailsPage.getTextOfPartnerDetailsPage("phoneValue").equals(String.valueOf(MSPPhoneNumber)), "Cancel functionality on MSP phone number popup is not working");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("phoneEdit");
			LOGGER.info("Clicked on edit phone button");
			partnerDetailsPage.enterTextForPartnerDetailsPage("phoneTextBox", String.valueOf(MSPPhoneNumber));
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("savePhone");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"), "Toast notification is not generated after changing phone number of MSP");
			LOGGER.info("Clicked on save button");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("phoneValue").equals(String.valueOf(MSPPhoneNumber)), "Save functionality on MSP phone number popup is not working");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("toastNotification");

			// Test Case 3 - This test case validates if address field is present under details tile on partner details page
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("addressLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.address")), "Address field for MSP is not present on details tile");
			country = partnerDetailsPage.getTextOfPartnerDetailsPage("currentAddressCountry");
			address = partnerDetailsPage.getTextOfListOfPartnerDetailsPage("currentAddress");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("addressEditButton");
			LOGGER.info("Clicked on Address edit button");
			address1 = partnerDetailsPage.generateRandomString(11);
			address2 = partnerDetailsPage.generateRandomString(11);
			state = partnerDetailsPage.generateRandomString(11);
			city = partnerDetailsPage.generateRandomString(11);
			zipcode = partnerDetailsPage.generateRandomNumber();
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("addressPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "global.address")), "Header on address popup of MSP is incorrect");
			partnerDetailsPage.enterTextForPartnerDetailsPage("addressStreetLine1TextBox", address1);
			LOGGER.info("Entered Address 1");
			partnerDetailsPage.enterTextForPartnerDetailsPage("addressStreetLine2TextBox", address2);
			LOGGER.info("Entered Address 2");
			partnerDetailsPage.enterTextForPartnerDetailsPage("stateTextBox", state);
			LOGGER.info("Entered State");
			partnerDetailsPage.enterTextForPartnerDetailsPage("cityTextBox", city);
			LOGGER.info("Entered City");
			partnerDetailsPage.enterTextForPartnerDetailsPage("zipCodeTextBox", zipcode);
			LOGGER.info("Entered Zip Code");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("countryDropDown");
			partnerDetailsPage.selectValueFromDropdownOnPartnerDetailspage("countryList", country);
			LOGGER.info("Selected Country");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("addressCancelButton");
			LOGGER.info("Clicked cancel button");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("currentAddressCountry").equals(country), "Cancel functionality on address popup is not working");
			softAssert.assertTrue(partnerDetailsPage.getTextOfListOfPartnerDetailsPage("currentAddress").equals(address), "Cancel functionality on address popup is not working");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("addressEditButton");
			partnerDetailsPage.enterTextForPartnerDetailsPage("addressStreetLine1TextBox", address1);
			partnerDetailsPage.enterTextForPartnerDetailsPage("addressStreetLine2TextBox", address2);
			partnerDetailsPage.enterTextForPartnerDetailsPage("stateTextBox", state);
			partnerDetailsPage.enterTextForPartnerDetailsPage("cityTextBox", city);
			partnerDetailsPage.enterTextForPartnerDetailsPage("zipCodeTextBox", zipcode);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("countryDropDown");
			country = partnerDetailsPage.selectValueFromDropdownOnPartnerDetailspage("countryList", country);
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("addressSaveButton");
			LOGGER.info("Clicked save button");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"), "Toast notification is not generated after changing address of MSP");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");

			address = partnerDetailsPage.getTextOfListOfPartnerDetailsPage("currentAddress");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("currentAddressCountry", country), "MSP address edit functionality is not working");
			softAssert.assertTrue(partnerDetailsPage.getTextOfListOfPartnerDetailsPage("currentAddress").equals(address), "MSP address edit functionality is not working");
			LOGGER.info("Verified edit functionality for address on partner details page");

			// Test Case 4 - This test case validates if the primary administrator of partner on list and details page is the same
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorLabelMSP").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin")), "Primary administrator field for MSP is not present on details tile");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorNameMSP", partnerInfo.get(2)), "Primary administrator name of MSP on details page does not match with list page");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorEmailMSP").equalsIgnoreCase(partnerInfo.get(3)), "Primary administrator email of MSP on details page does not match with list page");

			// Test Case 5 - This test case validates if the primary administrator of partner can be updated successfully
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorEditMSP");
			LOGGER.info("Clicked on edit primary administrator button");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorHeaderMSP").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_admin")), "Title on primary administrator popup is incorrect");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorSubTitleMSP").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name")), "Subtitle on primary administrator popup is incorrect");
			partnerDetailsPage.selectValueFromDropdownOnPartnerDetailspage("primaryAdministratorListMSP", partnerInfo.get(3));
			LOGGER.info("Selected MSP on dropdown");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorCancelButtonMSP");
			LOGGER.info("Clicked on cancel button");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorNameMSP").equals(partnerInfo.get(2)), "Cancel functionality for primary administrator is not working");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorEditMSP");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
			LOGGER.info("Clicked on edit primary administrator button");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
			LOGGER.info("Selected MSP on dropdown");
			currentPrimaryAdministrator = partnerDetailsPage.selectValueFromDropdownOnPartnerDetailspage("primaryAdministratorListMSP", partnerInfo.get(3));
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorSaveButtonMSP");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
			LOGGER.info("Clicked on save button");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorNameMSP").equals(currentPrimaryAdministrator), "Primary administrator is not getting updated");
			LOGGER.info("Verified edit functionality for primary administrator on partner details page");
			softAssert.assertAll();
			LOGGER.info("Verification of MSP details on details tile of MSP details page done successfully");

			softAssert.assertAll();
			LOGGER.info("All test cases of verifyDetailsTileOnMSPDetailsPage passed");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyDetailsTileOnMSPDetailsPage " + e.getMessage());
		} finally {
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorEditMSP");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
			LOGGER.info("Clicked on edit primary administrator button");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorDropdownMSP");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
			partnerDetailsPage.selectSpecificValueFromDropdownOnPartnerDetailspage("primaryAdministratorListMSP", PartnerVariables.MSP_DETAILS_TEST_PRIMARY_ADMINISTRATOR, "primaryAdministratorDropdownMSP");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("primaryAdministratorSaveButtonMSP");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("tableOverlay");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("nameEdit");
			partnerDetailsPage.enterTextForPartnerDetailsPage("nameTextBox", PartnerVariables.MSP_DETAILS_TEST_USER);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("saveName");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			LOGGER.info("Updated the primary administrator to its default state");
		}
	}

	// This test case validates identity tile present on partner details page
	@Test(priority = 17, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 249717, BUG 318168", enabled = false)
	public final void verifyIdentityTileOnMSPDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to partner page");
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		gotoMSPTab();
		LOGGER.info("Redirected to MSP list page");
		resetTableConfiguration();
		waitForPageLoaded();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.MSP_DETAILS_TEST_USER);
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		sleeper(2000);
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerInfo = partnerPage.getPartnerInfo();
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		LOGGER.info("Clicked on first MSP in the list");
		LOGGER.info("Redirected to MSP details page");
		waitForPageLoaded();
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerDetailsTitle", partnerInfo.get(1)), "Title on MSP details page is incorrect");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("breadcrumbText", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.msps_details")), "Text on breadcrumb on MSP details page is incorrect");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("breadcrumbLink"), "Link on breadcrumb of MSP details page is missing");
		LOGGER.info("Verified breadcrumb on MSP details page");

		// verifying if impersonate icon is visible under identity tile
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("impersonateIcon"), "Impersonate icon is invisible on MSP details page");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("partnerNameIdentity", partnerInfo.get(1).toUpperCase()), "Name of MSP on identity tile of MSP details page is incorrect");

		if (partnerDetailsPage.verifyElementsOfPartnerDetailsPage("primaryAdministratorContactNumberIdentity") && partnerDetailsPage.verifyElementsOfPartnerDetailsPage("primaryAdministratorPhoneNumber"))
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorContactNumberIdentity").equals(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorPhoneNumber")), "Contact number of primary adminstrator on identity tile of MSP details page is incorrect");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorNameIdentity").equals(partnerInfo.get(2)), "Primary adminstrator name on identity tile of MSP details page is incorrect");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorEmailIdentity").equals(partnerInfo.get(3)), "Primary administrator email on identity tile of MSP details page is incorrect");
		LOGGER.info("Verified MSP details on identity tile");

		softAssert.assertAll();
		LOGGER.info("All test cases of verifyIdentityTileOnMSPDetailsPage passed successfully");
	}

	@Test(priority = 18, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "Test Case 294097")
	public final void verifyOverviewTabSettingsMSP() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to companies list page");
		gotoMSPTab();
		LOGGER.info("Redirected to MSP page");
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.enterTextForPartnerPage("emailSearchBox", getCredentials(environment, "MSP_DETAILS_USER_EMAIL"));
		Thread.sleep(3000);
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerInfo = partnerPage.getPartnerInfo();
		logout();

		login("MSP_DETAILS_USER_EMAIL", "MSP_DETAILS_USER_PASSWORD");
		gotoSettingsTab();
		LOGGER.info("Redirected to Settings page");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
		// Test Case 1 - This test case validates MSP name(Title) on Identity tile
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerNameIdentity").equalsIgnoreCase(partnerInfo.get(1).toUpperCase()), "Name of partner on identity tile of MSP Settings page is incorrect");

		// Test Case 2 - This test case validates MSP name on Identity tile
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorNameIdentity", partnerInfo.get(2)), "Primary adminstrator name on identity tile of MSP Settings page is incorrect");

		// Test Case 3 - This test case validates email on Identity tile
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorEmailIdentity", partnerInfo.get(3)), "Primary administrator email on identity tile of MSP Settings page is incorrect");

		// Test Case 4 - This test case validates MSP name on overview tab.
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("nameValue", partnerInfo.get(1)), "Name of partner on MSP Settings page does not match with list page");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("editName"), "Name field is not editable on Settings overview tab of MSP. ");

		// Test Case 5 - This test case validates MSP phone number on overview tab.
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("phoneValue", partnerInfo.get(5)), "Phone number of partner on MSP Settings page does not match with list page");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("editPhoneNumber"), "Phone number field is not editable on Settings overview tab of MSP. ");

		// Test Case 6 - This test case validates MSP address on overview tab.
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("addressLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.address")), "Address field for partner is not present on MSP Settings tile");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("editAddress"), "Addess field is not editable on Settings overview tab of MSP. ");

		// Test Case 7 - This test case validates MSP administrator dropdown on overview tab.
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("primaryAdministratorLabelMSP").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin")), "Primary administrator field for partner is not present on MSP Settings tile");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("editPrimaryAdministrator"), "Primary Administrator dropdown is not editable on Settings overview tab of MSP. ");

		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorNameMSP", partnerInfo.get(2)), "Primary administrator name of partner on details page does not match with MSP Settings page");

		partnerDetailsPage.scrollOnPartnerDetailsPage("primaryAdministratorEmailMSP");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAdministratorEmailMSP", partnerInfo.get(3)), "Primary administrator email of partner on details page does not match with MSP Settings page");
		LOGGER.info("Verified partner details on identity tile and overview tab present on Partner Settings page.");

		softAssert.assertAll();
		LOGGER.info("All testcases of verifyOverviewTabSettingsMSP passed successfully");
	}

	/*// This test case validates companies managed by the partner tile present on partner details page
	@Test(priority = 19, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE" })
	public final void verifyCompaniesManagedByTheMSPTileOnMSPDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		gotoMSPTab();
		LOGGER.info("Redirected to MSP list page");
		resetTableConfiguration();
		waitForPageLoaded();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		partnerPage.enterTextForPartnerPage("emailSearchBox", getCredentials(environment, "MSP_DETAILS_USER_EMAIL"));
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		sleeper(3000);
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		LOGGER.info("Clicked on first MSP in the list");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		LOGGER.info("Redirected to MSP details page");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("companiesTitle", getTextLanguage(LanguageCode, "daas_ui", "msp.section.company_managed_msp").toUpperCase()), "Title on companies managed by partner tile is incorrect");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("companyNameColumn", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name").toUpperCase()), "Company name column is not present under companies managed by partner tile");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAccountNameColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.details.section.primary_account_name").toUpperCase()), "Primary administrator name column is not present under companies managed by partner tile");
		softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAccountEmailColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.details.section.primary_account_email").toUpperCase()), "Primary administrator email column is not present under companies managed by partner tile");
		if (partnerDetailsPage.verifyElementsOfPartnerDetailsPage("primaryAccountPhoneColumn"))
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("primaryAccountPhoneColumn", getTextLanguage(LanguageCode, "daas_ui", "partner.details.section.primary_account_phone").toUpperCase()), "Primary administrator phone number column is not present under companies managed by partner tile");
		LOGGER.info("Verified columns under companies managed by the MSP tile");
		gotoCompaniesTab();
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("companiesTab");
		LOGGER.info("Clicked on companies tab");
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("mspFilterDropdown");
		LOGGER.info("Clicked on MSP dropdown");
		companiesPage.enterTextForCompaniesPage("mspFilterSearchBox", PartnerVariables.MSP_DETAILS_TEST_USER.toLowerCase());
		sleeper(3000);
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.selectFirstOptionFromDropdownOnCompaniesPage("mspListOnPopup");
		LOGGER.info("Selected MSP on companies list page");
		pressKey(Keys.ESCAPE);
		softAssert.assertTrue(partnerPage.verifyCompaniesOnCompaniesManagedByTheMSPTile("noItemsDisplayText"), "List of companies on companies managed by partner tile on company details page is incorrect");
		softAssert.assertAll();
		LOGGER.info("All test cases of verifyCompaniesManagedByTheMSPTileOnMSPDetailsPage passed successfully");
	}*/

	/**
	 * This method will verify the table configuration test cases of MSP list page
	 */
	@Test(priority = 20, description = "NFR 214738", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyTableConfigurationTestCaseForMSPListPage() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		gotoMSPTab();
		LOGGER.info("Redirected to MSP list page");
		ArrayList<String> id = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name")));
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "partners.list.name"), getTextLanguage(LanguageCode, "daas_ui", "partners.list.primary_admin"), getTextLanguage(LanguageCode, "daas_ui", "partners.list.email"), getTextLanguage(LanguageCode, "daas_ui", "global.form.status"), getTextLanguage(LanguageCode, "daas_ui", "partners.list.phone"), getTextLanguage(LanguageCode, "daas_ui", "partners.list.address")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "false"));

		verifyTableConfigurationTests(columnName, checkboxValue, id);
	}

	// This test verfies if MSP list page is loaded successfully
	@Test(priority = 21, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 288513")
	public final void verifyMSPListPage() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		gotoMSPTab();
		LOGGER.info("Redirected to MSP list page");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("listTable"), "Table on MSP list page is not loaded successfully");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("nameHeader"), "Default column(name) is not present on MSP list page");
		softAssert.assertAll();
		LOGGER.info("Test Case 288513: MSP list page is loaded successfully");
	}

	// This test verifies if MSP details page is loaded successfully
	@Test(priority = 22, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 288513")
	public final void verifyMSPDetailsPage() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoMSPTab();
		LOGGER.info("Redirected to MSP list page");
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		waitForPageLoaded();
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
		LOGGER.info("Redirected to MSP details page");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("detailsTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.company_profile_title")), "Profile tile under Overview tab of MSP details page is not loaded successfully");
		LOGGER.info("Verified tiles under overview tab");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("preferencesTab");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("serviceNowTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.service_now")), "Service Now tile under preferences tab of MSP details page is not loaded successfully");
		LOGGER.info("Verified tiles under preferences tab");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("helpAndSupportTab");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("callBackTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.callback.title")), "Callback tile under help and support tab of MSP details page is not loaded successfully");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("directSupportTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.direct_support.title")), "Direct support tile under help and support tab of MSP details page is not loaded successfully");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("emailSupportTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.email_support.title")), "Email support tile under help and support tab of MSP details page is not loaded successfully");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("hoursOfOperationTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.title")), "Hours of operation tile under help and support tab of MSP details page is not loaded successfully");
		LOGGER.info("Verified tiles under help and support tab");
		softAssert.assertAll();
		LOGGER.info("Test Case 288513: MSP details page is loaded successfully");
	}

	/*
	 * This test case validates add MSP functionality on msp list page
	 */
	@Test(priority = 23, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "286656")
	public final void verifyAddMSPFunctionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		String msp_email = generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
		String mspID = generateRandomString(11);
		gotoMSPTab();
		LOGGER.info("Redirected to msp list page");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		resetTableConfiguration();
		partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
		partnerPage.clickOnElementsOfPartnerPage("addPartnerButton");
		LOGGER.info("Clicked on add msp button");
//		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("addPartnerTitle", getTextLanguage(LanguageCode, "daas_ui", "msp.add")), "Title on add msp popup is incorrect");
		partnerPage.enterTextForPartnerPage("addPartnerID", mspID);
		partnerPage.enterTextForPartnerPage("compPartnerNameSearch", PartnerVariables.NAME);

		partnerPage.clickOnElementsOfPartnerPage("countryDropdown");
		partnerPage.enterTextForPartnerPage("countrySearchBox", PartnerVariables.EDITPROFILE_COUNTRY);
		partnerPage.waitForElementsOfPartnerPage("countryList");
		partnerPage.selectFirstOptionFromDropdownOnPartnersPage("countryList");

		partnerPage.enterTextForPartnerPage("partnerFirstNameSearchBox", PartnerVariables.FIRST_NAME);
		partnerPage.enterTextForPartnerPage("partnerLastNameSearchBox", PartnerVariables.LAST_NAME);
		partnerPage.enterTextForPartnerPage("partnerEmailSearchBox", msp_email);
		partnerPage.enterTextForPartnerPage("partnerPhoneSearchBox", PartnerVariables.PHONE_NUMBER);
		LOGGER.info("Filled all form fields");
		partnerPage.clickOnElementsOfPartnerPage("cancel");
		LOGGER.info("Clicked on cancel button");
		partnerPage.enterTextForPartnerPage("emailSearchBox", msp_email);
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		softAssert.assertTrue(partnerPage.verifyElementsOfPartnerPage("noItemsDisplayText"), "Cancel functionality while adding msp is not working");
		LOGGER.info("Verified cancel functionality for add reseller");
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");

		partnerPage.clickOnElementsOfPartnerPage("addPartnerButton");
		LOGGER.info("Clicked on add partner button");
		partnerPage.enterTextForPartnerPage("addPartnerID", mspID);
		partnerPage.enterTextForPartnerPage("compPartnerNameSearch", PartnerVariables.NAME);

		partnerPage.clickOnElementsOfPartnerPage("countryDropdown");
		partnerPage.enterTextForPartnerPage("countrySearchBox", PartnerVariables.EDITPROFILE_COUNTRY);
		sleeper(2000);
		partnerPage.selectFirstOptionFromDropdownOnPartnersPage("countryList");

		partnerPage.enterTextForPartnerPage("partnerFirstNameSearchBox", PartnerVariables.FIRST_NAME);
		partnerPage.enterTextForPartnerPage("partnerLastNameSearchBox", PartnerVariables.LAST_NAME);
		partnerPage.enterTextForPartnerPage("partnerEmailSearchBox", msp_email);
		partnerPage.enterTextForPartnerPage("partnerPhoneSearchBox", PartnerVariables.PHONE_NUMBER);
		LOGGER.info("Filled all form fields");
		partnerPage.clickOnElementsOfPartnerPage("save");
		LOGGER.info("Clicked on save button");
		sleeper(2000);
		partnerPage.waitForElementsOfPartnerPage("toastNotification");
		String mspToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
		softAssert.assertTrue(mspToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "msp.add.successful")), "Toast notification is not generated after adding a msp");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("firstRowName", PartnerVariables.NAME), "Add msp functionality is not working");

		softAssert.assertAll();
		LOGGER.info("Verified add msp functionality");
	}

	// This test case validates Bug 295441: [DaaS][Partner] User is not deleted from Users tab after removing partner
	@Test(priority = 24, groups = { "REGRESSION", "REGRESSION_CI" }, description = "BUG 295441")
	public final void verifyUserRemovalPostPartnerRemoval() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		String currentUrl, tenantID;
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		String rootemail = getCredentials(environment,"ROOT_ADMIN_NEW_USER_COMPANIES");
		gotoPartnersTab();
		LOGGER.info("Redirected to partners page");
		waitForPageLoaded();
		resetTableConfiguration();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		String email = generateRandomString(11) + "@hpmsqa.mailinator.com";
		partnerPage.addSingleMember(LanguageCode, email , rootemail);
		// Added new partner
		//gotoPartnerUsersTab();
		partnerPage.clickOnElementsOfPartnerPage("partnerUsersTab");
		LOGGER.info("Redirected to partner users page");
		waitForPageLoaded();
		resetTableConfiguration();
		userPage.enterTextForUserPage("userEmailSearchBox", email);
		sleeper(3000);
		userPage.waitForElementsOfUserPage("tableOverlay");
		softAssert.assertTrue(userPage.matchTextOfUserPage("firstRowUserEmailList", email.toLowerCase()), "Added partner is not visible on users page");
		// Verified the presence of added partner on partner users page
		//gotoPartnersTab();
		partnerPage.clickOnElementsOfPartnerPage("partnersListTab");
		LOGGER.info("Redirected to partners page");
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.enterTextForPartnerPage("emailSearchBox", email);
		waitForPageLoaded();
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		waitForPageLoaded();
		LOGGER.info("Redirected to details page");
		currentUrl = partnerDetailsPage.getUrlOfCurrentPage();
		tenantID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
		partnerPage.removePartnerUsingAPI(tenantID);
		LOGGER.info("Removed Partner through API");
		// Removed the newly added partner through API
		//gotoPartnerUsersTab();
		partnerPage.clickOnElementsOfPartnerPage("partnerUsersTab");
		LOGGER.info("Redirected to partner users page");
		waitForPageLoaded();
		resetTableConfiguration();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		userPage.enterTextForUserPage("userEmailSearchBox", email);
		//sleeper(2000);
		userPage.waitForElementsOfUserPage("tableOverlay");
		int attempt=0;
		while(!partnerPage.waitForElementsOfPartnerPage("noItemsDisplayText")&& attempt<10){
		sleeper(1000);
		attempt++;
		}
		softAssert.assertTrue(userPage.verifyElementsOfUserPage("noItemsDisplayText"), "Removed partner is displayed on users list page");
		// Verified the removal of the same partner from users page
		softAssert.assertAll();
		LOGGER.info("All test cases of verifyUserRemovalPostPartnerRemoval have passed");
	}

	/*
	 * This test case validates invitation to company from partner
	 */
	@Test(priority = 25, groups = { "REGRESSIONCOMPANIES1" })
	public final void verifyPartnerToCompanyInviteFunctionality() throws Exception {
		login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
		String expectedMailContent, actualMailContent;
		String notificationMessage = null;
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		// Remove Pending Invitations from Company
		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		String tenantID = jsonAuthToken.get("tenant").toString();
		
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Company Invitation removal failed");
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Partner Invitation removal failed");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		waitForPageLoaded();
		logout();

		// Remove Pending Invitations from Partner
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();

		// Invite Company from Partner
		companiesPage.clickOnElementsOfCompaniesPage("addButton");
		companiesPage.verifyElementsOfCompaniesPage("addPopUpHeader");
		companiesPage.verifyElementsOfCompaniesPage("companyInvite");
		companiesPage.clickOnElementsOfCompaniesPage("companyInvite");
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		companiesPage.verifyElementsOfCompaniesPage("addPopUpHeader");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("companySendInviationHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.subheading")), "Send Invitation heading is not matching.");
		companiesPage.enterTextForCompaniesPage("customerID", getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_ID_RESEND"));
		sleeper(2000); // Need to use hard code wait
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("inviteCompanyName").equals(getEnvironmentSpecificData(environment, "COMPANY_NAME")), "Company Name of Customer is not matching.");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("inviteCountryName").equals(getEnvironmentSpecificData(environment, "COMPANY_COUNTRY")), "Country Name of Customer is not matching.");
		companiesPage.verifyElementsOfCompaniesPage("companyInviteBtn");
		companiesPage.clickOnElementsOfCompaniesPage("companyInviteBtn");
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		notificationMessage = companiesPage.getTextOfCompaniesPage("toastNotification");
		softAssert.assertTrue(notificationMessage.equals(getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.success")), "Toast notification is not generated after inviting company");
		LOGGER.info("Company Invite Sent succcessfully");
		logout();

		// Decline Partner Invite
		login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		Thread.sleep(2000);
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		companiesPage.clickOnElementsOfCompaniesPage("declineInvitationBtn");
		companiesPage.verifyElementsOfCompaniesPage("declineLabel");
		companiesPage.verifyElementsOfCompaniesPage("declineBtn");
		companiesPage.clickOnElementsOfCompaniesPage("declineBtn");
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after declining invitation from Partner.");
		LOGGER.info("Invitation declined from Partner");
		logout();

		// Invite Company from Partner
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("addButton");
		companiesPage.verifyElementsOfCompaniesPage("addPopUpHeader");
		companiesPage.verifyElementsOfCompaniesPage("companyInvite");
		companiesPage.clickOnElementsOfCompaniesPage("companyInvite");
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		companiesPage.verifyElementsOfCompaniesPage("addPopUpHeader");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("companySendInviationHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.subheading")), "Send Invitation heading is not matching.");
		companiesPage.enterTextForCompaniesPage("customerID", getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_ID_RESEND"));
		sleeper(2000); // Need to use hard code wait
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("inviteCompanyName").equals(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME")), "Company Name of Customer is not matching.");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("inviteCountryName").equals(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_COUNTRY")), "Country Name of Customer is not matching.");
		companiesPage.verifyElementsOfCompaniesPage("companyInviteBtn");
		companiesPage.clickOnElementsOfCompaniesPage("companyInviteBtn");
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.success")), "Toast notification is not generated after inviting company");
		LOGGER.info("Company Invite Sent succcessfully");

		// Verify Email Sent to Company
		expectedMailContent = ((((getTextLanguage(LanguageCode, "idm", "association.partner.invitation.header") + " " + getTextLanguage(LanguageCode, "idm", "association.partner.invitation.header") + " " + getTextLanguage(LanguageCode, "idm", "workspace.mail.common.greeting").replace("{0}", getEnvironmentSpecificData(environment, "PARTNERTEST")) + " " + getTextLanguage(LanguageCode, "idm", "association.partner.invitation.message.v2") + " "
				+ (getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME_EMAIL")) + " " + (getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME")) + " " + (getTextLanguage(LanguageCode, "daas_ui", "partner.id")) + " " + (getEnvironmentSpecificData(environment, "PARTNER_ID")) + " " + (getTextLanguage(LanguageCode, "idm", "association.creation.respond.message")) + " " + getTextLanguage(LanguageCode, "idm", "sign_in.btn.sign_in").toUpperCase() + " " + getTextLanguage(LanguageCode, "idm", "workspace.mail.common.thank_you") + " " + (getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()))).replace("<b>", "")).replace("</b>", "")).replace("<b >", "")).replace("  ", " ");
		actualMailContent = verifyEmailContent(environment, "IT_ADMIN_PARTNERS_EMAIL", "ASSIGNED_PARTNER_NAME_EMAIL", " wants to be your partner", true);
		softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match");
		LOGGER.info("Email Verification for Company Invitation is done succesfully");

		// Verify Invitation Tab
		companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
		Thread.sleep(2000);
		companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_customer"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
		pressKey(Keys.ESCAPE);
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(environment, "COMPANY_NAME_EMAIL"));
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		companiesPage.mouseHoverOnCompanyPage("companySearchResult");
		companiesPage.clickByJavaScriptOnCompaniesPage("companyCheckbox");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("resendInviteButton"), "Resend invite button is not present on invitation");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("recallInvitationButton"), "Recall invitation button is not present on invitation");
		logout();

		// Approve Partner Invite
		login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
		companiesPage.waitForElementsOfCompaniesPage("notificationBellIcon");
		// Verify Notification Icon for invitation
		companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationTitle").contains(getTextLanguage(LanguageCode, "daas_ui", "partner.invitations").toUpperCase()),"Partner Invitation title is not matching.");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDescription").equals(getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME") + " (" + getTextLanguage(LanguageCode, "idm", "partner.id") + " " + getEnvironmentSpecificData(environment, "PARTNER_ID") + ") " + CompaniesVariables.PARTNER_INVITATION_NOTIFICATION), "Partner Invitation description is not matching.");
			companiesPage.mouseHoverOnCompanyPage("firstNotification");
			companiesPage.clickOnElementsOfCompaniesPage("hamburgerMenuOnNotification");
		Thread.sleep(2000);
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationApprove").equals(getTextLanguage(LanguageCode, "daas_ui", "global.approve")), "Approve button is not matching.");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDecline").equals(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Decline button is not matching.");
		companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
		LOGGER.info("Notification Icon is verified successfully");

		// Approve Invitation from Partner
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
		softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after accepting invitation from Partner");
		LOGGER.info("Invitation accepted from Partner");
		// Verify Email Approved from Company
		Thread.sleep(30000);// Mail takes time to arrive
		expectedMailContent = ((((getTextLanguage(LanguageCode, "idm", "association.customer.accept.header")+  " " +getTextLanguage(LanguageCode, "idm", "association.customer.accept.header")+" "+ getTextLanguage(LanguageCode, "idm", "workspace.mail.common.greeting").replace("{0}", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME_EMAIL")) + " " + getTextLanguage(LanguageCode, "idm", "association.customer.accept.message.v2") + " " + (getEnvironmentSpecificData(System.getProperty("environment"), "PARTNERTEST_INVITE")) + " " + (getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_COUNTRY")) + " "
				+ (getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.label")) + " " + (getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_ID_RESEND")) + " " + (getTextLanguage(LanguageCode, "idm", "association.accept.respond.message.v2")) + " " + getTextLanguage(LanguageCode, "idm", "sign_in.btn.sign_in") + " "
				+ getTextLanguage(LanguageCode, "idm", "workspace.mail.common.thank_you") + " " + (getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()))).replace("<b>", "")).replace("</b>", "")).replace("<b >", "")).replace("  ", " ");
		actualMailContent = verifyEmailContent(environment, "RESELLER_PARTNERS_EMAIL", "COMPANY_INVITE_NAME", " has accepted your invitation", true);
		softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match for approve invitation from partner");
		LOGGER.info("Email Verification for Company Invitation is done succesfully");

		softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("assignedPartnerIcon"), "Partner icon is present on company details page");
		logout();

		// Verify Company on Company list page
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		resetTableConfiguration();
		// test case validates invited company on company list
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		companiesPage.waitForElementsOfCompaniesPage("companyNameSearch");
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
		companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.accepted"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
		
		pressKey(Keys.ESCAPE);
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("companySearchResult");
		LOGGER.info("Redirected to company details page");
		waitForPageLoaded();
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedpartnerTab");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		LOGGER.info("Redirected to assigned partner tab");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyPartner");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("unassignPartnerButtonCompanyDetailsPage");
		
		softAssert.assertAll();
		LOGGER.info("Partner to company invite flow completed successfully");
	}

	@Test(priority = 26, groups = { "REGRESSIONCOMPANIES1", "STABILIZATION_STAGING" }, description = "Test Case : 366042")
	public final void verifyPartnerToCompanyResendInviteFunctionality() throws Exception {
		login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		// Remove Pending Invitations from Company
		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		String tenantID = jsonAuthToken.get("tenant").toString();
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Company Invitation removal failed");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		logout();

		// Remove Pending Invitations from Partner
		login("PARTNER_WITH_MULTIINVITE", "PARTNER_WITH_MULTIINVITE_PASSWORD");
		waitForPageLoaded();
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		resetTableConfiguration();
		companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
		companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_customer"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
		pressKey(Keys.ESCAPE);

		if(companiesPage.verifyElementsOfCompaniesPage("companySearchResult")){
			List<WebElement> companiesList = companiesPage.getElementsOfCompanyListPage("companyCheckbox");
				List<WebElement> companyCheckboxList = companiesPage.getElementsOfCompanyListPage("companySearchResult");
				companiesPage.clickElementsOfCompaniesPage(companiesList, companyCheckboxList);
			companiesPage.clickOnElementsOfCompaniesPage("recallInvitationButton");
			companiesPage.clickOnElementsOfCompaniesPage("dialogResend");
			companiesPage.waitForElementsOfCompaniesPage("toastNotification");
			softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification",getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")),"Toast notification is not generated after invite resend");
			LOGGER.info("Removed all pending invitations from partner");
		} else {
			LOGGER.info("There are no pending invitations present from partner");
		}
		//Invite Company from partner
		companiesPage.waitForElementsOfCompaniesPage("addButton");
		companiesPage.clickOnElementsOfCompaniesPage("addButton");
		companiesPage.verifyElementsOfCompaniesPage("addPopUpHeader");
		companiesPage.verifyElementsOfCompaniesPage("companyInvite");
		companiesPage.clickOnElementsOfCompaniesPage("companyInvite");
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		companiesPage.verifyElementsOfCompaniesPage("addPopUpHeader");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("companySendInviationHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.subheading")), "Send Invitation heading is not matching.");
		companiesPage.enterTextForCompaniesPage("customerID", getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_ID_RESEND"));
		sleeper(2000); // Need to use hard code wait
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("inviteCompanyName").equals(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME")), "Company Name of Customer is not matching.");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("inviteCountryName").equals(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_COUNTRY")), "Country Name of Customer is not matching.");
		companiesPage.verifyElementsOfCompaniesPage("companyInviteBtn");
		companiesPage.clickOnElementsOfCompaniesPage("companyInviteBtn");
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.success")), "Toast notification is not generated after inviting company");
		LOGGER.info("Company Invite Sent succcessfully");
		
		//Resend invite to company
		companiesPage.waitForElementsOfCompaniesPage("clearFilter");
		companiesPage.clickOnElementsOfCompaniesPage("clearFilter");
		companiesPage.waitForElementsOfCompaniesPage("companyNameSearch");
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"));
		companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
		companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_customer"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
		waitForPageLoaded();
		pressKey(Keys.ESCAPE);
		companiesPage.mouseHoverOnCompanyPage("companySearchResult");
		companiesPage.clickByJavaScriptOnCompaniesPage("companyCheckbox");
		companiesPage.clickOnElementsOfCompaniesPage("resendInviteButton");
		companiesPage.clickByJavaScriptOnCompaniesPage("dialogResend");
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after invite resend");
		LOGGER.info("Verification of partner to company resend invite functionality completed.");
		logout();

		login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		// Verify Notification Icon for invitation
		companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationTitle").contains(getTextLanguage(LanguageCode, "daas_ui", "partner.invitations").toUpperCase()),"Partner Invitation title is not matching.");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDescription").equals(getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL") + " ("+ getTextLanguage(LanguageCode, "idm", "partner.id") + " "+ getEnvironmentSpecificData(environment, "PARTNER_MULTI_INVITE_ID") + ") "+ CompaniesVariables.PARTNER_INVITATION_NOTIFICATION),"Partner Invitation description is not matching.");
			companiesPage.mouseHoverOnCompanyPage("firstNotification");
			companiesPage.clickOnElementsOfCompaniesPage("hamburgerMenuOnNotification");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationApprove").equals(getTextLanguage(LanguageCode, "daas_ui", "global.approve")), "Approve button is not matching.");
		softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDecline").equals(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Decline button is not matching.");
		companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
		LOGGER.info("Notification Icon is verified successfully");
		logout();

		//Recall invitation from partner
		login("PARTNER_WITH_MULTIINVITE", "PARTNER_WITH_MULTIINVITE_PASSWORD");
		waitForPageLoaded();
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		resetTableConfiguration();
		companiesPage.waitForElementsOfCompaniesPage("companyNameSearch");
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME"));
		companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
		companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_customer"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
		waitForPageLoaded();
		pressKey(Keys.ESCAPE);
		companiesPage.mouseHoverOnCompanyPage("companySearchResult");
		companiesPage.clickByJavaScriptOnCompaniesPage("companyCheckbox");
		companiesPage.clickOnElementsOfCompaniesPage("recallInvitationButton");
		companiesPage.clickByJavaScriptOnCompaniesPage("dialogResend");
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after invite resend");
		logout();
		
		//Verify partner invitation notification is removed from company after clicking on Recall button
		login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("acceptInvitationBtn"), "Accept invitation button is available even after clicking on Recall button from partner");
		
		// Verify Notification Icon for invitation
		companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
		softAssert.assertFalse(companiesPage.getTextOfCompanyPage("invitationNotificationTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.invitations").toUpperCase()),"Partner Invitation did not removed even after clicking on Recall button.");
		companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
		
		softAssert.assertAll();
		LOGGER.info("verify Recall functionality and Partner To Company Resend Invite Functionality completed");
	}

	// This test case validates name and role filter's functionality on MSPs User list page
	@Test(priority = 27, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 295186", enabled = false)
	public final void verifyFilterFunctionalityOnMSPUsersListPage() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to Companies list page");
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		gotoMSPUsersTab();
		resetTableConfiguration();
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		sleeper(2000);

		// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on name column
		softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBoxUser", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "nameList"), "Search functionality with incorrect search string is not working for name column on MSPs user list page");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		// This sleeper is added as it should wait for proper list refresh before entering text in name search box
		sleeper(3000);
		softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBoxUser", PartnerVariables.NAME_SEARCH, "noItemsDisplayText", "nameList"), "Search functionality is not working for name column on MSPs user list page");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		LOGGER.info("Verified filter functionality for name column in MSPs User list page.");
		// This sleeper is added as it should wait for proper list refresh before clicking on role filter dropdown
		sleeper(3000);
		// Test Case 2 - This test case validates if the filter functionality is working properly for the dropdown on roles column
		partnerPage.scrollOnPartnerPage("userRolesBoxTitle");
		partnerPage.waitForElementsOfPartnerPage("userRolesBox");
		partnerPage.waitForElementsOfPartnerPage("dropdownRolesCheckboxes");
		partnerPage.clickOnElementsOfPartnerPage("dropdownRolesCheckboxes");
		softAssert.assertTrue(partnerPage.verifyFilterMultiSelectOnPartnerPage(LanguageCode, "dropdownRolesElementListLabels", "dropdownRolesElementListLabels", "userRolesList", "noItemsDisplayText"), "Listbox not working for Roles dropdown");
		partnerPage.waitForElementsOfPartnerPage("clearRolesField");
		partnerPage.clickOnElementsOfPartnerPage("clearRolesField");
		if (partnerPage.verifyElementsOfPartnerPage("clearFilter"))
			partnerPage.clickOnElementsOfPartnerPage("clearFilter");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		LOGGER.info("Verified filter functionality for roles column");

		softAssert.assertAll();
		LOGGER.info("All test cases of filter functionality for MSPs user page have passed");

	}

	// This test case validates name and role filter's functionality on Partners User list page
	@Test(priority = 28, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 295186", enabled = false)
	public final void verifyFilterFunctionalityOnPartnerUsersListPage() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to Companies list page");
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		gotoPartnerUsersTab();
		resetTableConfiguration();
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		sleeper(3000);

		// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on name column
		softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBoxUser", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "nameList"), "Search functionality with incorrect search string is not working for name column on partners user list page");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		// This sleeper is added as it should wait for proper list refresh before entering text in name search box
		sleeper(3000);
		softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBoxUser", PartnerVariables.NAME_SEARCH, "noItemsDisplayText", "nameList"), "Search functionality is not working for name column on partners user list page");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		LOGGER.info("Verified filter functionality for name column in Partners User list page.");
		// This sleeper is added as it should wait for proper list refresh before clicking on role filter dropdown
		sleeper(3000);
		// Test Case 2 - This test case validates if the filter functionality is working properly for the dropdown on roles column
		partnerPage.scrollOnPartnerPage("userRolesBoxTitle");
		partnerPage.waitForElementsOfPartnerPage("userRolesBox");
		partnerPage.waitForElementsOfPartnerPage("dropdownRolesCheckboxes");
		partnerPage.clickOnElementsOfPartnerPage("dropdownRolesCheckboxes");
		softAssert.assertTrue(partnerPage.verifyFilterMultiSelectOnPartnerPage(LanguageCode, "dropdownRolesElementListLabels", "dropdownRolesElementListLabels", "userRolesList", "noItemsDisplayText"), "Listbox not working for Roles dropdown");
		partnerPage.waitForElementsOfPartnerPage("clearRolesField");
		partnerPage.clickOnElementsOfPartnerPage("clearRolesField");
		if (partnerPage.verifyElementsOfPartnerPage("clearFilter"))
			partnerPage.clickOnElementsOfPartnerPage("clearFilter");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		LOGGER.info("Verified filter functionality for roles column");

		softAssert.assertAll();
		LOGGER.info("All test cases of filter functionality for Partners user page have passed");

	}

	// This test case validates name and role filter's functionality on Customers User list page
	@Test(priority = 29, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 295186", enabled = false)
	public final void verifyFilterFunctionalityOnCompanyUsersListPage() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		LOGGER.info("Redirected to Companies list page");
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesUsersTab();
		resetTableConfiguration();
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		// This sleeper is added as it should wait for proper list refresh before entering text in name search box
		sleeper(3000);
		// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on name column
		softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBoxUser", PartnerVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "nameList"), "Search functionality with incorrect search string is not working for name column on customers user list page");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		// This sleeper is added as it should wait for proper list refresh before entering text in name search box
		sleeper(3000);
		softAssert.assertTrue(partnerPage.verifySearchValueOnPartner(LanguageCode, "nameSearchBoxUser", PartnerVariables.NAME_SEARCH, "noItemsDisplayText", "nameList"), "Search functionality is not working for name column on customers user list page");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		LOGGER.info("Verified filter functionality for name column in Customers User list page.");
		// This sleeper is added as it should wait for proper list refresh before clicking on role filter dropdown
		sleeper(4000);
		// Test Case 2 - This test case validates if the filter functionality is working properly for the dropdown on roles column
		partnerPage.scrollOnPartnerPage("userRolesBoxTitle");
		partnerPage.waitForElementsOfPartnerPage("userRolesBox");
		partnerPage.waitForElementsOfPartnerPage("dropdownRolesCheckboxes");
		partnerPage.clickOnElementsOfPartnerPage("dropdownRolesCheckboxes");
		softAssert.assertTrue(partnerPage.verifyFilterMultiSelectOnPartnerPage(LanguageCode, "dropdownRolesElementListLabels", "dropdownRolesElementListLabels", "userRolesList", "noItemsDisplayText"), "Listbox not working for Roles dropdown");
		partnerPage.waitForElementsOfPartnerPage("clearRolesField");
		partnerPage.clickOnElementsOfPartnerPage("clearRolesField");
		if (partnerPage.verifyElementsOfPartnerPage("clearFilter"))
			partnerPage.clickOnElementsOfPartnerPage("clearFilter");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		LOGGER.info("Verified filter functionality for roles column");

		softAssert.assertAll();
		LOGGER.info("All test cases of filter functionality for Customers user page have passed");
	}
	
	/**
	 * This test case validates License order page title 
	 * 
	 * @throws Exception
	 */
	@Test(priority = 30, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verifyLicenseOrdersTitle() throws Exception {
		login("PARTNER_COMPANY_HYPERLINK_EMAIL", "PARTNER_COMPANY_HYPERLINK_PASSWORD");
		gotosubscriptionOrdersTab();
		LOGGER.info("Redirected to License orders tab");
		LicenseOrdersPage licenseOrdersPage = new LicenseOrdersPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		Assert.assertTrue(licenseOrdersPage.getTextOfSubscriptionOrdersPage("subscriptionOrdersTitleBreadcrumb").equals(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.subscription.orders")), "Title on License orders list page is incorrect");
		LOGGER.info("Title on License orders list page is matched");
	}
	
	/**
	 * This test case validates accepting multiple partner invitations 
	 * 
	 * @throws Exception
	 */
	@Test(priority = 31, groups = { "REGRESSIONCOMPANIES1", "STABILIZATION_STAGING" })
	public final void verifyAcceptMultipleInvitationsFromPartner() throws Exception {
		login("IT_ADMIN_COMPANY", "IT_ADMIN_COMPANY_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		// Remove Pending Invitations from First Company
		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		String tenantID = jsonAuthToken.get("tenant").toString();
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Company Invitation removal failed");
		//Remove receieved pending invitations from partner
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Partner Invitation removal failed");
		LOGGER.info("Removed all pending invitations from company view");

		//Send invite to partner
		companiesPage.mouseHoverOnCompanyPage("assignedPartnerSettings");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		waitForPageLoaded();
		settingsPage.waitUntilElementIsVisibleOfSettingsPage("partnerDetailsOnAssignedPartnerTab");
		settingsPage.waitForElementsOfSettingsPage("editAssignedpartnerButton");
		settingsPage.clickOnElementsOfSettingsPage("editAssignedpartnerButton");
		LOGGER.info("Clicked on edit assigned partner button");
		settingsPage.waitForElementsOfSettingsPage("assignPartnerDD");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerDD");
		LOGGER.info("Clicked on edit assigned partner dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.enterTextForSettingsPage("assignPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL"));
		Thread.sleep(2000);
		settingsPage.selectFirstOptionFromDropdownOnSettingsPage("partnersList");
		LOGGER.info("Selected partner on dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerInvite");
		LOGGER.info("Clicked on save assigned partner button");
		Thread.sleep(8000);
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"), "Toast notification is not generated after inviting partner");
		logout();

		// Remove Pending Invitations from Second Company
		login("IT_ADMIN_PARTNERS_EMAIL2", "IT_ADMIN_PARTNERS_PASSWORD2");
		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		tenantID = jsonAuthToken.get("tenant").toString();
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)),"Pending Company Invitation removal failed");
		//Remove receieved pending invitations from partner
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Partner Invitation removal failed");
		LOGGER.info("Removed all pending invitations from company view");

		// Send invite to partner
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		waitForPageLoaded();
		settingsPage.waitUntilElementIsVisibleOfSettingsPage("partnerDetailsOnAssignedPartnerTab");
		settingsPage.waitForElementsOfSettingsPage("editAssignedpartnerButton");
		settingsPage.clickOnElementsOfSettingsPage("editAssignedpartnerButton");
		LOGGER.info("Clicked on edit assigned partner button");
		Thread.sleep(6000);
		settingsPage.waitForElementsOfSettingsPage("assignPartnerDD");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerDD");
		LOGGER.info("Clicked on edit assigned partner dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.enterTextForSettingsPage("assignPartnerSearchBox",getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL"));
		waitForPageLoaded();
		settingsPage.selectFirstOptionFromDropdownOnSettingsPage("partnersList");
		LOGGER.info("Selected partner on dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerInvite");
		LOGGER.info("Clicked on save assigned partner button");
		Thread.sleep(8000);
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"),"Toast notification is not generated after inviting partner");
		logout();

		//Accept multiple invitations from reseller
		login("PARTNER_WITH_MULTIINVITE", "PARTNER_WITH_MULTIINVITE_PASSWORD");
		waitForPageLoaded();
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies tab");
		resetTableConfiguration();
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
		companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_partner"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
		pressKey(Keys.ESCAPE);
		companiesPage.waitForElementsOfCompaniesPage("companySearchResult");
		waitForPageLoaded();
		List<WebElement> companiesList = companiesPage.getElementsOfCompanyListPage("companyCheckbox");
			List<WebElement> companyCheckboxList = companiesPage.getElementsOfCompanyListPage("companySearchResult");
			companiesPage.clickElementsOfCompaniesPage(companiesList, companyCheckboxList);
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("acceptCustomerButton"), "Accept button is not present on invitation");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("declineCustomerButton"), "Decline button is not present on invitation");
		companiesPage.clickOnElementsOfCompaniesPage("acceptCustomerButton");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("declinePopupHeader");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("declinePopupHeader",getTextLanguage(LanguageCode, "daas_ui", "companies.list.accept_customers")),"Title on decline popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("declineDescription",getTextLanguage(LanguageCode, "daas_ui","companies.list.accept_customers.message").replace("{count}", "2")),"Description on accept popup is incorrect");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("dialogResend"),"Save button is not present on accept popup");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("cancelButtonOnPopup"),"Cancel button is not present on accept popup");
		companiesPage.clickOnElementsOfCompaniesPage("dialogResend");
		LOGGER.info("Clicked on Accept Customer button");
		Thread.sleep(8000);//Accept takes time
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("acceptCustomerLoaderButton");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification",getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")),"Toast notification is not generated after accepting invite");
		companiesPage.clickOnElementsOfCompaniesPage("clearSearchButton");
		waitForPageLoaded();
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(environment, "COMPANY_NAME_MULTIINVITE"));
		waitForPageLoaded();
		Thread.sleep(4000);
		companiesPage.scrollOnCompaniesPage("partnerAssignmentStatus");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerAssignmentStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.accepted")), "Partner assignment status did not changed to Accepted after accepting the invitation.");
		softAssert.assertAll();
		LOGGER.info("Test case to accept multiple invitations from partner passed successfully.");
	}
	
	/**
	 * Verify Add/Edit/Delete Subscription Contract on partner details page.
	 * @throws Exception
	 * Disabled since Feature toggle is ON related to User Story 934683: [Subscription] Auto-renewal of Partner Subscription Contract, Once toggle OFF will enable it again.
	 */
	@Test(priority = 32, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "This test validates Add/Edit/Delete of Subscription Contract on partner details page. Test Cases: 286670,818497,648261",enabled= true)
	public final void verifyAddEditDeleteSubscriptionContract() throws Exception {
		login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String contractID = "AutoContractID" + generateRandomString(5);
		String NewContractID = "AutoContractID" + generateRandomString(5);
		try {
			LOGGER.info("Logged in with root user");
			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/partners/" + getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME_ID"));
			LOGGER.info("Redirected to partner details page");
			partnerDetailsPage.verifyElementsOfPartnerDetailsPage("detailsTitle");

			// Add Subscription Contract
			partnerDetailsPage.verifySubscriptionContractToggle();
		   sleeper(2000);
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("subscriptionContractsTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.contracts")), "Subscription Contracts Title does not match.");
			Assert.assertTrue(partnerDetailsPage.createSubscriptionContract(LanguageCode, contractID,getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CONTRACT")), "Subscription contract did not created successfully.");
			LOGGER.info("Addition of subscription contract for Partner completed successfully");

			// Edit Subscription Contract
			partnerDetailsPage.mouseHoverOnPartnerDetailPage("firstContract");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("hamburgerIcon");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("editContract");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("editContractLabel");
			//Verify Currency field is editable in edit Subscription Contract
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("btnCurrencyDropdown");
			if (partnerDetailsPage.verifyElementIsEnableOfPartnerDetailsPage("btnCurrencyDropdown"))
			{
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("btnCurrencyDropdown");
				softAssert.assertTrue(partnerDetailsPage.verifyElementIsClickableOfPartnerDetailsPage("currencyDropdownOption"),"Currency option not visible in dropdown");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("currencyDropdownOption");
			}else 
			{	LOGGER.info("Currency dropdown not editable");
			}
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("expiresDatepicker");
			sleeper(5000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("monthKeyCurrent");
			partnerDetailsPage.selectDateFromCalenderOnPartnerDetailpage(addDaysToCurrentDate(1), "monthKeyCurrent", "endDateDialogRightArrow", "daysOnCurrentMonthKey");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("contractSaveBtn");
			partnerDetailsPage.enterTextForPartnerDetailsPage("editMonthlyCostperSeat", "12");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("contractSaveBtn");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");	
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("toastNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_update_successful").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.contract"))), "Subscription Contract not updated.");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("closeToastNotification");
			
			// Verify Contract Columns
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("contractIDColumn").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.contract.id").toUpperCase()), "Contract ID column missing under subscription contract table");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("countryColumn").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.country").toUpperCase()), "Country column missing under subscription contract table");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("startDateColumn").equals(getTextLanguage(LanguageCode, "daas_ui", "global.start_date").toUpperCase()), "Start date column missing under subscription contract table");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("expiresColumn").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.expires").toUpperCase()), "Expires column missing under subscription contract table");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("planColumn").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans").toUpperCase()), "Plan column missing under subscription contract table");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("paymentTermColumn").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.contracts.paymentTerms").toUpperCase()), "Payment term column missing under subscription contract table");
			LOGGER.info("Verified contract columns after adding subscription contract");

			// Verify Contract removal after re-enbaling subscription Authorized Toggle
			scrollByCoordinatesofAPage(0, -350);
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
			partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("subscriptionContractsTitle");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
			sleeper(1000);
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionContractAllow");
			sleeper(1000);
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("subscriptionContractsTitle");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("emptyContractMsg").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.no_contracts_data")), "Empty contract message does not match.");
			LOGGER.info("Contract removal after re-enbaling verified successfully");
			
			partnerDetailsPage.createSubscriptionContract(LanguageCode, NewContractID, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CONTRACT"));

			softAssert.assertAll();
			LOGGER.info("Verification of add/edit/remove Subscription Contract verified successfully");

		} catch (Exception e) {
			LOGGER.info("Exception occured in method verifyAddEditDeleteSubscriptionContract " + e.getMessage());

		} finally {
			// Delete Contract
			Assert.assertTrue(partnerDetailsPage.removeContractUsingAPI(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME_ID"), NewContractID), "Subscription contract not deleted through API.");
		}
	}

	/**
	 * Will enable the test case once the toggle will be available on staging
	 * This test case verifies the Subscription Authorized Configuration on partner details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 33, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case: 286670, 440226")
	public final void verifySubscriptionAuthorizedToggle() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String msp_email = generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
		String mspID = generateRandomString(11);
			gotoPartnersTab();
			waitForPageLoaded();
			LOGGER.info("Redirected to partner list page");
			resetTableConfiguration();
			
			partnerPage.clickOnElementsOfPartnerPage("addPartnerButton");
			LOGGER.info("Clicked on add partner button");
			partnerPage.enterTextForPartnerPage("addPartnerID", mspID);
			partnerPage.enterTextForPartnerPage("compPartnerNameSearch", PartnerVariables.PARTNER_DETAILS_TEST_USER);

			partnerPage.clickOnElementsOfPartnerPage("countryDropdown");
			partnerPage.enterTextForPartnerPage("countrySearchBox", PartnerVariables.EDITPROFILE_COUNTRY);
			sleeper(2000);
			partnerPage.selectFirstOptionFromDropdownOnPartnersPage("countryList");

			partnerPage.enterTextForPartnerPage("partnerFirstNameSearchBox", PartnerVariables.FIRST_NAME);
			partnerPage.enterTextForPartnerPage("partnerLastNameSearchBox", PartnerVariables.LAST_NAME);
			partnerPage.enterTextForPartnerPage("partnerEmailSearchBox", msp_email);
			partnerPage.enterTextForPartnerPage("partnerPhoneSearchBox", PartnerVariables.PHONE_NUMBER);
			LOGGER.info("Filled all form fields");
			
			
			partnerPage.clickOnElementsOfPartnerPage("next");
		 	
			//Enter data on Partner Business Manager fields
			partnerPage.enterTextForPartnerPage("pbmFirstNameSearchBox", PartnerVariables.PBM_FIRST_NAME);
			partnerPage.enterTextForPartnerPage("pbmLastNameSearchBox", PartnerVariables.PBM_LAST_NAME);
			partnerPage.enterTextForPartnerPage("pbmEmailSearchBox", getCredentials(environment,"PBM_MANGER_PARTNER_EMAIL"));
			
			 
			//Enter data on Onboarding Business Manager fields
			partnerPage.enterTextForPartnerPage("obmFirstNameSearchBox", PartnerVariables.OBM_FIRST_NAME);
			partnerPage.enterTextForPartnerPage("obmLastNameSearchBox", PartnerVariables.OBM_LAST_NAME);
			partnerPage.enterTextForPartnerPage("obmEmailSearchBox", getCredentials(environment,"OBM_MANGER_PARTNER_EMAIL"));
			partnerPage.clickByJavaScriptOnPartnerPage("pbmobmCheckBox");
			softAssert.assertTrue(partnerPage.getTextOfPartnerPage("notifyUserText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.business_manager_send_email")), "Send notification to users text is not present on popup");
			
			//Verify Cancel, Previous and Save buttons are available
		 	partnerPage.clickOnElementsOfPartnerPage("save");
			LOGGER.info("Clicked on save button");
			
			partnerPage.waitForElementsOfPartnerPage("toastNotification");
			String partnerToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
			softAssert.assertTrue(partnerToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "partner.add.successful")), "Toast notification is not generated after adding a partner");
			LOGGER.info("Verified add partner functionality");
			Thread.sleep(4000);
			//Navigate to Partner details page.
			if(partnerPage.verifyElementIsPresent("clearSearchBoxFilter")) {
				partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
			}
		 
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.PARTNER_DETAILS_TEST_USER);
			Thread.sleep(3000);
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
			partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
			LOGGER.info("Clicked on first partner in the list");
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			LOGGER.info("Redirected to partner details page");

			//Disable the Onboard Authorized toggle
			if(partnerDetailsPage.getAttributesOfPartnerDetailsPage("onboardPartnerToggle", "aria-checked").equals(CommonVariables.TRUE)) {
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			}
			LOGGER.info("Disabled the Onboared authorized toggle");

			softAssert.assertFalse(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle"), "Subscription Authorized toggle visible even if Onboard AUthorized toggle is disabled");
			softAssert.assertFalse(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("subscriptionContractTile"), "Subscription contracts tile is visible even if Onboaard authorized toggle is OFF");

			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			Assert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle"),"Subscription Authorized Toggle did not appeared even if Onboard Authorized toggle is ON, cannot proceed further");	
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("subscriptionAuthorizedLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.authorization.subscription")), "Subscription Authorized label did not match");

			//Disable Subscription Authorized toggle
			if(partnerDetailsPage.getAttributesOfPartnerDetailsPage("subscriptionAuthorizedToggle", "aria-checked").equals(CommonVariables.TRUE)) {
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			}
			LOGGER.info("Disabled Subscription authorized toggle");

			softAssert.assertFalse(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("subscriptionContractTile"), "Subscription contracts tile is visible even if Subscription authorized toggle is OFF");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			LOGGER.info("Subscription Authorized toggle is ON.");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("emailNotificationHeader");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("allowEmailNotification");
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("subscriptionAuthorizedPartnerValue", CommonVariables.YES), "Status of Subscription Authorized Partner did not changed to Yes after switching the toggle ON");
			softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("subscriptionContractTile"), "Subscription contracts tile is not visible even if Subscription authorized toggle is ON");

			//Verify the updated Subscription Authorized status is visible on partner list page
			partnerPage.clickOnElementsOfPartnerPage("partnersListTab");
			//gotoPartnersTab();
			LOGGER.info("Redirected to partner page");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			partnerPage.scrollOnPartnerPage("authorizationColumnHeader");
			softAssert.assertTrue(partnerPage.getTextOfPartnerPage("authorizationValue").contains(getTextLanguage(LanguageCode, "daas_ui", "partners.list.authorization.subscription")), "Updated status of Subscription authorizad partner on list page is incorrect");
			softAssert.assertAll();
			LOGGER.info("All test cases for verifySubscriptionAuthorizedToggle successfully");
	}
	
	/**
	 * This Test Case will add and delete the MSP Users from root
	 * @throws Exception
	 */
	@Test(priority = 34, groups = { "REGRESSION", "REGRESSION_CI"}, description = "NFR 522924 , TestCase= 528448 ,528452")
	public final void verifyAddMSPUserFunctionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_USER_ADMIN_OEMA", "ROOT_PASSWORD_OEMA");
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		String msp_email = generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
		gotoMSPUserTab();
		LOGGER.info("Redirected to msp user list page");
		if (toggleVerification(PartnerVariables.MULTIPLE_ACCOUNT_TOOGLE, getCredentials(System.getProperty("environment"), "ROOT_ADMIN_USER_US"))) {		
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		resetTableConfiguration();
		partnerPage.clickOnElementsOfPartnerPage("addMSPUserButton");
		LOGGER.info("Clicked on add msp user button");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("chooseMSPUserTitle", getTextLanguage(LanguageCode, "daas_ui", "user.add.choose.msps")), "Title on Choose msp popup is incorrect");
		partnerPage.clickOnElementsOfPartnerPage("mspdropdown");
		List<WebElement> listOfMSPs = partnerPage.getElementsOfPartnerPage("listOfMSPs");
		selectMSPs(listOfMSPs);
		partnerPage.clickByJavaScriptOnPartnerPage("mspdropdownafterclick");
		partnerPage.clickOnElementsOfPartnerPage("mspNext");
		partnerPage.clickOnElementsOfPartnerPage("roledropdown");
		partnerPage.selectFirstOptionFromDropdownOnPartnersPage("mspRoleList");
		partnerPage.clickByJavaScriptOnPartnerPage("mspdropdownafterclick");
		partnerPage.enterTextForPartnerPage("mspFirstNameSearchBox", PartnerVariables.FIRSTNAME);
		partnerPage.enterTextForPartnerPage("mspLastNameSearchBox", PartnerVariables.LASTNAME);
		partnerPage.enterTextForPartnerPage("mspEmailSearchBox", msp_email);
		partnerPage.enterTextForPartnerPage("mspPhoneSearchBox", PartnerVariables.PHONE_NUMBER);
		LOGGER.info("Filled all form fields");
		partnerPage.clickOnElementsOfPartnerPage("addUserButton");
		LOGGER.info("Clicked on add button");
		partnerPage.waitForElementsOfPartnerPage("toastNotification");
		String mspToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
		softAssert.assertTrue(mspToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "user.created.success")), "Failed to create MSP user successfully");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		partnerPage.enterTextForPartnerPage("mspEmailSearch", msp_email);
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		List<WebElement> listOfUsers = partnerPage.getElementsOfPartnerPage("listOfUsers");
		for(int i=0;i<listOfUsers.size();i++)
		{
		softAssert.assertEquals(listOfUsers.get(i).getText().toString(),PartnerVariables.FIRSTLASTNAME, "Add msp user functionality is not working");
		}
		LOGGER.info("Verified add msp user functionality");
		sleeper(2000);
		
		//remove added MSP Users
		resetTableConfiguration();
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		partnerPage.enterTextForPartnerPage("mspEmailSearch", msp_email);
		Thread.sleep(2000);
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		partnerPage.clickOnElementsOfPartnerPage("listPageCheckbox");
		partnerPage.clickOnElementsOfPartnerPage("deleteUsers");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("removeUserTitle", getTextLanguage(LanguageCode, "daas_ui", "users.remove.users")), "Title on remove msp user popup is incorrect");
		Assert.assertTrue(partnerPage.matchTextOfPartnerPage("removeUserMessage", getTextLanguage(LanguageCode, "daas_ui", "users.remove.multipleAccounts").replace("{count}", "2")), "Delete message on Popup is incorrect");
		partnerPage.clickOnElementsOfPartnerPage("deleteButton");
		partnerPage.waitForElementsOfPartnerPage("toastNotification");
		Assert.assertTrue(partnerPage.getTextOfPartnerPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}","Users")), "User removal failed");
		softAssert.assertAll();
		}
		else
			LOGGER.info("One Email Multiple Account Toggle is not enabled");
	}
	
	    // This test case validates Billing History tab for Root
		@Test(priority = 35, groups = { "REGRESSIONCOMPANIES1"}, description = "NFR 782337 , TestCase = 777632,777670,777666,777632")
		public final void verifyBillingHistoryTabforRoot() throws Exception 
		{
			login("ROOT_ADMIN_USER_US_BILLING", "ROOT_PASSWORD_US_BILLING_PASSWORD");
			gotoPartnersTab();
			SoftAssert softAssert = new SoftAssert();
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to partner page");
			waitForPageLoaded();
			resetTableConfiguration();
			partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			partnerPage.waitForElementsOfPartnerPage("nameSearchBox");
			partnerPage.enterTextForPartnerPage("nameSearchBox",billing_partner_name);
			sleeper(3000); // required to search the partner in the list
			partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
			partnerPage.clickOnElementsOfPartnerPage("firstRowPartner");
			LOGGER.info("Clicked on first partner in the list");
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			LOGGER.info("Redirected to partner details page");
			
			partnerDetailsPage.verifySubscriptionContractToggle();
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("pbmObmtoastNotificationCloseBtn");
			
			//Verify Billing history tab
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingHistoryTab").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing.title")), "Billing History tab not enabled");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("billingHistoryTab");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingPanelHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing.title")), "Billing History panel header is incorrect.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingPanelWarning").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing.info")), "Billing Panel warning info is incorrect.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingYearLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billing.dropdown.title")), "Billing History YEar label is incorrect.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingPeroidLable").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billing.period.title")), "Billing Peroid title is incorrect.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingAnchorMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing.title")), "Billing History anchor menu is incorrect.");
			
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("billingPeroidMonth");
			if(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"))
			{
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("toastNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billing.error.404")), "No Partner Report Found toast message is incorrect.");
				LOGGER.info("No partner reconciliation report found");
			}else{
				if(countFolderFile(ConstantPath.DOWNLOAD_PATH) >= 1){
					LOGGER.info("File is downloaded Successfully");
				}else{
					LOGGER.error("File is not downloaded");
				}
			}	
			LOGGER.info("Billing History Tab is verified for Root");
			
			//Verify Only Subscription Authorozed Partner Billing history tab is displayed.
			//Disable Subscription Authorized toggle
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("overviewTab");
			waitForPageLoaded();
			partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
			
			if (partnerDetailsPage.getAttributesOfPartnerDetailsPage("subscriptionAuthorizedToggle", "aria-checked").equals(CommonVariables.FALSE)) {
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("subscriptionContractsTitle");
				LOGGER.info("Subscription Authorized Toggle is enabled");
			} else {
				LOGGER.info("Subscription Authorized Toggle is already enabled");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
				partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("subscriptionContractsTitle");
				LOGGER.info("Subscription Authorized Toggle is disabled");
				
			sleeper(3000);
			softAssert.assertFalse(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("billingHistoryTab"), "Billing History tab displayed even when subscription authorized toggle is OFF");
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			LOGGER.info("Enabled Subscription authorized toggle");
			
			LOGGER.info("Billing History Tab is verified for Subscription Authorized Partner.");
			
			softAssert.assertAll();
		}
		}
		
		 // This test case validates Billing History tab for Billing Admin
			@Test(priority = 36, groups = { "REGRESSIONCOMPANIES1"}, description = "NFR 782337 , TestCase = 777632,777653,777671")
		public final void verifyBillingHistoryTabforBillingAdmin() throws Exception 
		{	
			try {
			// Verify Billing Admin User creation.
			login("BILLING_ADMIN_PARTNER", "BILLING_ADMIN_PARTNER_PASSWORD");
			waitForPageLoaded();
			SoftAssert softAssert = new SoftAssert();
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to Partner team list page");
			resetTableConfiguration();
			waitForPageLoaded();
			salesTeamPage.clickOnElementsOfSalesTeamPage("salesAddButton");
			salesTeamPage.clickOnElementsOfSalesTeamPage("rolesDropdown");
			salesTeamPage.clickOnElementsOfSalesTeamPage("unselectRolesOption");
			salesTeamPage.enterTextForSalesTeamPage("roleSearchBox", SalesVariables.PARTNER_USER);
			sleeper(2000);
			salesTeamPage.clickOnElementsOfSalesTeamPage("firstOptionRoleDropdown");
			sleeper(1000);
			salesTeamPage.clickByJavaScriptOnSalesTeamPage("closeRoleDropdown");
			salesTeamPage.enterTextForSalesTeamPage("firstNameAddMember", SalesVariables.FIRST_NAME);
			salesTeamPage.enterTextForSalesTeamPage("lastNameAddMember", SalesVariables.LAST_NAME);
			salesTeamPage.enterTextForSalesTeamPage("emailAddMember",billing_admin_email);
			salesTeamPage.enterTextForSalesTeamPage("mobileAddMember", SalesVariables.MOBILE_PHONE);
			salesTeamPage.clickOnElementsOfSalesTeamPage("addMember");
			LOGGER.info("Verified add functionality on add sales team member popup");
			salesTeamPage.waitForElementsOfSalesTeamPage("toastSuccessNotification");
			String notificationMessage = salesTeamPage.getTextOfSalesTeamPage("toastSuccessNotification");
			softAssert.assertTrue(notificationMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.created.success")),"Incorrect notification message is generated after adding sales team member");
			LOGGER.info("Billing Admin User created successfully.");
			softAssert.assertAll();
			LOGGER.info("All test cases of add functionality for Partner team page have passed");

			//Verify Billing Admin User.
			logout();
			login("BILLING_ADMIN_USER", "BILLING_ADMIN_PASSWORD");
			waitForPageLoaded();
			expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
			sleeper(5000);
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingSideMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing")), "Billing Side Menu is not displayed for billling admin user.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("settingsSideMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")), "Settings Side Menu is not displayed for billling admin user.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("logsSideMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.logs")), "Logs Side Menu is not displayed for billling admin user.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("helpSupportSideMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help & Support Menu is not displayed for billling admin user.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("whatsNewSideMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.whatsNew")), "Whats New Menu is not displayed for billling admin user.");
			
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingAdminReportHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.billing")), "Billing Admin Report Header is incorrect.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingAdminPanelHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing.title")), "Billing History panel header on billing admin is incorrect.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingAdminPanelWarning").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing.info")), "Billing Panel warning info on billing admin is incorrect.");
			softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingAdminYearLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billing.dropdown.title")), "Billing History Year label on billing admin is incorrect.");
					
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("billingAdminPeroidMonth");
			if(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("toastNotification"))
			{
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("toastNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billing.error.404")), "No Partner Report Found toast message is incorrect.");
				LOGGER.info("No partner reconciliation report found");
			}else{
				if(countFolderFile(ConstantPath.DOWNLOAD_PATH) >= 1){
					LOGGER.info("File is downloaded Successfully");
				}else{
					LOGGER.error("File is not downloaded");
				}
			}
			//Verify logs tab for report download logs
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("logsSideMenu");
			softAssert.assertTrue(partnerPage.verifyDescriptionOnLogsPage()==true, "Description on logs page is incorrect when billing reconciliation report is downloaded.");
			softAssert.assertAll();
		} catch (Exception e) {
					Assert.fail("Exception occured in method verifyBillingHistoryTabforBillingAdmin " + e.getMessage());
				} 
			finally {
				logout();
				login("BILLING_ADMIN_PARTNER", "BILLING_ADMIN_PARTNER_PASSWORD");
				SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();
				String currentUrl, salesTeamMemberID;
				gotoPartnerTeamTab();
				salesTeamPage.enterTextForSalesTeamPage("emailTextbox",billing_admin_email);
				sleeper(2000);
				salesTeamPage.clickOnElementsOfSalesTeamPage("userFirstIdEmail");
				currentUrl = salesTeamPage.getUrlOfCurrentPage();
				salesTeamMemberID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
				salesTeamPage.removeSalesTeamMember(environment, salesTeamMemberID, getEnvironment(environment));
			}
		}
			
			/*
			 * This test case validates edit PBM and OBM fields on partner overview functionality for Existing Partner.
			 */
			@Test(priority = 37, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING" }, description = "813975,813965,829221")
			public final void verifyEditPBMExistingPartnerFunctionality() throws Exception {
				login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
				String actualMailContent = null, actualMailContentPBM=null,actualMailContentOBM=null;
				SoftAssert softAssert = new SoftAssert();
				LOGGER.info("Redirected to companies list page");
				PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
				PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
				String pbm_manager_email = "pbmmanager."+generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
				String obm_manager_email = "obmmanager."+generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
				String pbm_firstname = "PBM Manager"+generateRandomString(7).toLowerCase();
				String pbm_lastname = "Testlast"+generateRandomString(7).toLowerCase();
				String obm_firstname = "OBM Manager"+generateRandomString(7).toLowerCase();
				String obm_lastname = "TestLast"+generateRandomString(7).toLowerCase();
				ArrayList<String> partnerDetails = new ArrayList<String>();
				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				
				gotoPartnersTab();
				waitForPageLoaded();
				LOGGER.info("Redirected to partner list page");

				resetTableConfiguration();
				partnerPage.enterTextForPartnerPage("emailSearchBox",getCredentials(environment, "PARTNER_DETAILS_USER_EMAIL"));
				Thread.sleep(3000);
				partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
				partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
				LOGGER.info("Clicked on first partner in the list");
				waitForPageLoaded();
				LOGGER.info("Redirected to partner details page");
				sleeper(3000);
				partnerPage.scrollOnPartnerPage("partnerBusinessManagerLabel");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerBusinessManagerLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.partner_business_manager").toUpperCase()), "Title of Partner Business Manager label is incorrect on partner overview page");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardBusinessManagerLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.onboarding_business_manager").toUpperCase()), "Title of Onboard Business Manager label is incorrect on partner overview page");
				
				partnerPage.scrollOnPartnerPage("onboardPartnerLabel");
				//Enable Onboard authorized Toggle
				if (partnerDetailsPage.getAttributesOfPartnerDetailsPage("onboardPartnerToggle", "aria-checked").equals(CommonVariables.TRUE)) {
					LOGGER.info("Onboard Authorized toggle is already ON");
					partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
				}
				else{
					LOGGER.info("Onboard Authorized toggle is OFF.");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
				}
			
				//Enable Subscription Authorized toggle
				if(partnerDetailsPage.getAttributesOfPartnerDetailsPage("subscriptionAuthorizedToggle", "aria-checked").equals(CommonVariables.TRUE)) {
					LOGGER.info("Subscription  Authorized toggle is already ON");
					partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
				}
				else{
					LOGGER.info("Subscription Authorized toggle is OFF.");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("subscriptionAuthorizedToggle");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
				LOGGER.info("Subscription Authorized toggle is ON.");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("emailNotificationHeader");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("allowEmailNotification");
				//Partner email verification
				String expectedMailContent = ( getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.complete.mail.body.3")+ "  "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.complete.mail.body.3")+ "  " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.callback_email.greeting").replace("%{team_name}", partnerDetails.get(0)) + "  "+ getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.complete.mail.body.1") +
						 "  "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.complete.mail.body.2") + "  "+ getTextLanguage(LanguageCode, "idm", "sign_in.btn.sign_in") + "  "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.thank.you")
						 + "  "+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear())).replace("<b>", " ").replace("</b>", " ").replace("<b >", " ").replace("  ", " ");
				
				actualMailContent = verifyEmailContent(environment, "PARTNER_DETAILS_USER_EMAIL", "PBM_EMAIL_SUBJECT", "", true);
				softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Partner Mail content does not match");
				}
				
				//Verify Edit PBM/OBM fields
				softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("parnterBusinessManagerEdit"), "Edit icon is not present for PBM field on overview page.");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("parnterBusinessManagerEdit");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerBusinessManagerHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.partner_business_manager")), "Header of Partner Business Manager label is incorrect on PBM edit page");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("pbmFirstNameEditSearchBoxLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "First Name on Partner Business Manager pop-up is incorrect");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("pbmLastNameEditSearchBoxLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "Last Name on Partner Business Manager pop-up is incorrect");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("pbmEmailEditSearchBoxLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.form.email_address")), "Email Address on Partner Business Manager pop-up is incorrect");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("pbmEmailNotificationMsg").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.business_manager_send_email")), "Email notification msg on Partner Business Manager pop-up is incorrect");
				partnerDetailsPage.enterTextForPartnerDetailsPage("pbmFirstNameEditSearchBox", pbm_firstname);
				partnerDetailsPage.enterTextForPartnerDetailsPage("pbmLastNameEditSearchBox", pbm_lastname);
				partnerDetailsPage.enterTextForPartnerDetailsPage("pbmEmailEditSearchBox",pbm_manager_email);
				partnerPage.verifyDefaultValueOfCheckboxOfPopup("pbmEmailNotificationCheckbox","pbmEmailNotificationCheckboxValue");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("pbmObmEditSaveBtn");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("pbmObmtoastNotification");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("pbmObmtoastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "global.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "partners.list.partner_business_manager"))), "Toast notification is not generated after editing a PBM manager");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("pbmObmtoastNotificationCloseBtn");
				
				//OBM fields
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardBusinessManagerEdit");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardBusinessManagerHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.onboarding_business_manager")), "Header of Onboard Business Manager label is incorrect on OBM edit page");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("obmFirstNameEditSearchBoxLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "First Name on Onboard Business Manager pop-up is incorrect");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("obmLastNameEditSearchBoxLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "Last Name on Onboard Business Manager pop-up is incorrect");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("obmEmailEditSearchBoxLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.form.email_address")), "Email Address on Onboard Business Manager pop-up is incorrect");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("obmEmailNotificationMsg").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.business_manager_send_email")), "Email notification msg on Onboard Business Manager pop-up is incorrect");
				partnerDetailsPage.enterTextForPartnerDetailsPage("obmFirstNameEditSearchBox", obm_firstname);
				partnerDetailsPage.enterTextForPartnerDetailsPage("obmLastNameEditSearchBox", obm_lastname);
				partnerDetailsPage.enterTextForPartnerDetailsPage("obmEmailEditSearchBox",obm_manager_email);
				partnerPage.verifyDefaultValueOfCheckboxOfPopup("obmEmailNotificationCheckbox","obmEmailNotificationCheckboxValue");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("pbmObmEditSaveBtn");
				partnerDetailsPage.waitForElementsOfPartnerDetailsPage("pbmObmtoastNotification");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("pbmObmtoastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "global.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "partners.list.onboarding_business_manager"))), "Toast notification is not generated after editing a OBM manager");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("pbmObmtoastNotificationCloseBtn");
				partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorName"));
				partnerDetails.add(settingsPage.getTextOfSettingsPage("partnerBusinessManagerName"));
				partnerDetails.add(settingsPage.getTextOfSettingsPage("onboardBusinessManagerName"));
				
				//PBM email verification
				String expectedMailContentPBM = ( getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.subject")+ "  "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.subject")+ "  " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.callback_email.greeting").replace("%{team_name}", partnerDetails.get(1)) +"  " + getTextLanguage(LanguageCode, "lhserver", "loe4.partner.contract.setup.mail.body.1").replace("{0}", partnerDetails.get(0))+
						  "  "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.thank.you")
						 + "  "+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear())).replace("<b>", " ").replace("</b>", " ").replace("<b >", " ").replace("  ", " ");
				actualMailContentPBM =partnerPage.verifyEmailContent("PBM_MANGER_EMAIL_SUBJECT", environment, pbm_manager_email,true);
				softAssert.assertTrue(actualMailContentPBM.equalsIgnoreCase(expectedMailContentPBM), "PBM Mail content does not match");
				
				//OBM email verification
				String expectedMailContentOBM = ( getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.subject")+ "  "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.setup.subject")+ "  " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.callback_email.greeting").replace("%{team_name}", partnerDetails.get(2)) +"  " + getTextLanguage(LanguageCode, "lhserver", "loe4.partner.contract.setup.mail.body.1").replace("{0}", partnerDetails.get(0))+
						  "  "+getTextLanguage(LanguageCode, "idm", "loe4.partner.contract.thank.you")
						 + "  "+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear())).replace("<b>", " ").replace("</b>", " ").replace("<b >", " ").replace("  ", " ");
				actualMailContentOBM =partnerPage.verifyEmailContent("PBM_MANGER_EMAIL_SUBJECT", environment, obm_manager_email,true);
				softAssert.assertTrue(actualMailContentOBM.equalsIgnoreCase(expectedMailContentOBM), "OBM Mail content does not match");
				logout();
				
				/*//Verify Edited values in Partner Login
				login("PARTNER_DETAILS_USER_EMAIL", "PARTNER_DETAILS_USER_PASSWORD");
				gotoSettingsTab();
				LOGGER.info("Redirected to Settings page");
				waitForPageLoaded();
				partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
				sleeper(2000); // time required for components on overview tile to load completely
				partnerPage.scrollOnPartnerPage("partnerBusinessManagerLabel");
				
				//This test case validates PBM firstname, lastname  and email on  Partner overview page
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerBusinessManagerLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.partner_business_manager").toUpperCase()), "Title of Partner Business Manager label is incorrect on partner overview page");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerBusinessManagerName").equalsIgnoreCase(partnerDetails.get(1)), "PBM firstname and lastname on overview page of partner Settings page is incorrect");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("partnerBusinessManagerEmail").equalsIgnoreCase(pbm_manager_email), "PBM email on overview page of partner Settings page is incorrect");
				
				//This test case validates OBM firstname, lastname  and email on  Partner overview page
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardBusinessManagerLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partners.list.onboarding_business_manager").toUpperCase()), "Title of Onboard Business Manager label is incorrect on partner overview page");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardBusinessManagerName").equalsIgnoreCase(partnerDetails.get(2)), "OBM firstname and lastname on overview page of partner Settings page is incorrect");
				softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("onboardBusinessManagerEmail").equalsIgnoreCase(obm_manager_email), "OBM email on overview page of partner Settings page is incorrect");
				LOGGER.info("Verification of Edit PBM/OBM fields for Partner completed successfully.");
*/
				softAssert.assertAll();
			}
			
			// This test case validates Billing Banner for existing user
			@Test(priority = 38, groups = { "REGRESSIONCOMPANIES1"}, description = "TestCase = 879677,879672,889227,879669")
			public final void verifyBillingBannerForExistingUser() throws Exception 
			{
				try{
				/*//Verify Partner is Subscription Authorized
				login("ROOT_ADMIN_USER_US_BILLING", "ROOT_PASSWORD_US_BILLING_PASSWORD");
				gotoPartnersTab();
				SoftAssert softAssert = new SoftAssert();
				PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
				PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				LOGGER.info("Redirected to partner page");
				waitForPageLoaded();
				resetTableConfiguration();
				partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
				partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
				partnerPage.waitForElementsOfPartnerPage("nameSearchBox");
				partnerPage.enterTextForPartnerPage("nameSearchBox",getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_BANNER_PARTNER_NAME"));
				sleeper(3000); // required to search the partner in the list
				partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
				partnerPage.clickOnElementsOfPartnerPage("firstRowPartner");
				LOGGER.iverifyServiceDeliveryPartnerTogglenfo("Clicked on first partner in the list");
				partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
				LOGGER.info("Redirected to partner details page");
				partnerDetailsPage.verifySubscriptionContractToggle();*/
				
				//Verify Billing banner for Existing user
				//logout();
				login("BILLING_BANNER_PARTNER", "BILLING_BANNER_PARTNER_PASSWORD");
				SoftAssert softAssert = new SoftAssert();
				PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
				PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingBannerHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.banner_title")), "Billing Banner dashboard Title does not match.");
				partnerPage.clickOnElementsOfPartnerPage("billingSetupButton");
				partnerPage.waitForElementsOfPartnerPageDynamic("billingWelcomeTitle1", 10);
				
					// Verify Billing Default Pop-up
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcomeTitle1").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.welcome.title")), "Billing Banner Welcome Title1 does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcometext1").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.welcome_text1")), "Billing banner text1 does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcomeTitle2").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.welcome_title2")), "Billing Banner Welcome Title2 does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcometext2").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.welcome_text2")), "Billing banner text2 does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcomeTitle3").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.welcome_title3")), "Billing Banner Welcome Title3 does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("assignAdminTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.assign_admin")), "Assign Admin title does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("welcomeStepDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "global.welcome")), "Welcome step name does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("assignStepDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "global.modal.assign")), "Assign step name does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("verifyStepDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "global.verify")), "Verify step name does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcomeTitle4").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.welcome_title4")), "Billing Banner Welcome Title4 does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcometext4").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.welcome_text4")), "Billing Banner Welcome text4 does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingCancelbtn").equals(getTextLanguage(LanguageCode, "daas_ui", "table.configuration.cancel")), "Cancel button does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingStartBtn").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.action.start")), "Start button name does not match.");
					partnerPage.clickOnElementsOfPartnerPage("billingStartBtn");
					sleeper(3000);// Due to inconsistent UI response we have to put// wait here.(Tried many ways but this is the// final solution which works as expected.)

					// Verify Assign Billing Administrator Step
					Assert.assertTrue(partnerPage.getTextOfPartnerPage("assignBillingStepTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.assign_title")), "Assign Billing Administrator Step did not open successfully.");
					LOGGER.info("Assign Billing Administrator Step open successfully");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("addAdminStepDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.assign_text")), "Assign Billing Administrator Desc does not match on Billing modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("userTypeTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.assign_text3")), "User Selection Type Text does not match on Billing Setup modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("existingUserRadioBtnText").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.existing_users")), "Existing User radio button text does not match on Billing Setup modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("newUserRadioBtnText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.new_users")), "Add user radio button text does not match on Billing Setup modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("existingUserMsg").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.assign_text2")), "Existing User Message text does not match on Billing Setup modal pop-up.");
					partnerPage.clickOnElementsOfPartnerPage("userSelectionDropdwn");
					partnerPage.enterTextForPartnerPage("userSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_EXISTING_USER"));
					sleeper(2000);
					partnerPage.clickOnElementsOfPartnerPage("firstOptionUserDropdown");
					sleeper(1000);
					partnerPage.clickByJavaScriptOnPartnerPage("closeUserDropdown");
					partnerPage.clickOnElementsOfPartnerPage("billingStartBtn");
					
					// Verify Billing Administrator Step
					Assert.assertTrue(partnerPage.getTextOfPartnerPage("assignBillingStepTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.verify_title")), "Verify Billing Administrator Step did not open successfully.");
					LOGGER.info("Verify Billing Administrator Step open successfully");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("addAdminStepDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.verify_text")), "Verify Billing Administrator Desc does not match on Billing modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("userNameCol").equals(getTextLanguage(LanguageCode, "daas_ui", "contents.tableColumns.name")), "Existing User Name does not match on Billing modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("userEmailCol").equals(getTextLanguage(LanguageCode, "daas_ui", "global.email")), "Existing User Email does not match on Billing modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("nameValue").equalsIgnoreCase(getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_EXISTING_USER_NAME")), "Existing User Name does not match on Billing modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("emailValue").equals(getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_EXISTING_USER_EMAIL")), "Existing User Email does not match on Billing modal pop-up.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingCancelbtn").equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.button.back")), "Back button does not match.");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingStartBtn").equals(getTextLanguage(LanguageCode, "daas_ui", "global.confirm")), "Confirm button name does not match.");
					partnerPage.clickOnElementsOfPartnerPage("billingStartBtn");
					partnerPage.waitForElementsOfPartnerPage("toastNotification");
					String billingAdminToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
					softAssert.assertTrue(billingAdminToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.assigned")), "Toast notification is not generated after assigning Billing Admin to existing user.");
					LOGGER.info("Billing admin is assigned for existing user.");
					Assert.assertTrue(partnerPage.waitUntilElementInvisibleOfPartnerPage(("billingBanner")), "Billing Admin is not assigned to User.");
					LOGGER.info("Billing Banner Widget removed from dashboard.");
					
					
					//Verify Billing Admin role assigned to existing user.
					gotoPartnerTeamTab();
					LOGGER.info("Redirected to Partner team list page");
					resetTableConfiguration();
					waitForPageLoaded();
					userPage.enterTextForUserPage("userEmailSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_EXISTING_USER_EMAIL"));
					sleeper(5000);
					partnerPage.waitForElementsOfPartnerPage("userRoleSearchValue");
					softAssert.assertTrue(partnerPage.getTextOfPartnerPage("userRoleSearchValue").contains(("Billing Admin")), "Billing admin role is assigned to user on user list page.");
					
					//logout();
					//login("BILLING_ADMIN_PARTNER_EXISTING_USER", "BILLING_ADMIN_PARTNER_EXISTING_USER_PASSWORD");
//					waitForPageLoaded();
//					partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
//					partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
//					LOGGER.info("Clicked on first partner in the list");
//					waitForPageLoaded();
//					LOGGER.info("Redirected to partner details page");
//					sleeper(3000);
					
					/*
					 * expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui",
					 * "sidemenu.myOrganization")); sleeper(5000);
					 * softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage(
					 * "billingSideMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
					 * "sidemenu.billing")),
					 * "Billing Side Menu is not displayed for billling admin user.");
					 * partnerDetailsPage.clickByJavaScriptOnPartnerDetailsPage("billingSideMenu");
					 * softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage(
					 * "billingAdminPanelHeader").equalsIgnoreCase(getTextLanguage(LanguageCode,
					 * "daas_ui", "sidemenu.billing.title")),
					 * "Billing History panel header on billing admin is incorrect.");
					 * LOGGER.info("Billing Admin role is assigned to existing user.");
					 */
					softAssert.assertAll();
				}
				catch(Exception e) {
					Assert.fail("Billing Banner pop-up did not open successfully");
				}
				finally{
					//Remove Billing Admin role from existing user
					logout();
					login("BILLING_BANNER_PARTNER", "BILLING_BANNER_PARTNER_PASSWORD");
					SoftAssert softAssert = new SoftAssert();
					UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
					UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
					PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
					gotoPartnerTeamTab();
					LOGGER.info("Redirected to Partner team list page");
					resetTableConfiguration();
					waitForPageLoaded();
					userPage.enterTextForUserPage("userEmailSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_EXISTING_USER_EMAIL"));
					userPage.waitForElementsOfUserPage("tableOverlay");
					userPage.clickByJavaScriptOnElementsOfUserPage("userFirstRow");
					userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
					partnerPage.scrollOnPartnerPage("accountsPanelHeader");
					sleeper(2000);
					userDetailsPage.clickOnElementsOfUserDetailsPage("userRoleEditButton");
					partnerPage.enterTextForPartnerPage("roleSearchBar", "Billing Admin");
					sleeper(2000);
					partnerPage.clickByJavaScriptOnPartnerPage("billingCheckbox");
					userDetailsPage.clickOnElementsOfUserDetailsPage("rolesSave");
					partnerPage.waitForElementsOfPartnerPage("toastNotification");
					String userRoleToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
					softAssert.assertTrue(userRoleToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "user.update.success").replace("{section}", "User's").replace("{field}", "roles")), "Toast notification is not generated after removing Billing Admin from existing user.");
					LOGGER.info("Billing admin role is removed from existing user.");
					softAssert.assertAll();	
				}}
			
						// This test case validates Billing Banner for New user
						@Test(priority = 39, groups = { "REGRESSION", "REGRESSION_CI"}, description = "TestCase = 879681,889223,889227")
						public final void verifyBillingBannerForNewUser() throws Exception 
						{
							String currentUrl;
							try{
							//Verify Billing banner for new user
							
							login("BILLING_BANNER_PARTNER", "BILLING_BANNER_PARTNER_PASSWORD");
							SoftAssert softAssert = new SoftAssert();
							PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
							PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
							UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
							
							//Verify Billing banner on dashboard
							softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingBannerHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.banner_title")), "Billing Banner dashboard Title does not match.");
							softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingBannerDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.banner_text")), "Billing Banner dashboard desc does not match.");
							softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingSetupButtonText").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.setupAccount")), "Billing Banner Setup Account button text does not match.");
							partnerPage.clickOnElementsOfPartnerPage("billingSetupButton");
							partnerPage.waitForElementsOfPartnerPageDynamic("billingWelcomeTitle1", 10);
							
								// Verify Billing Default Pop-up
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcomeTitle1").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.welcome.title")), "Billing Banner Welcome Title1 does not match.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcometext1").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.welcome_text1")), "Billing banner text1 does not match.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingWelcomeTitle2").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.welcome_title2")), "Billing Banner Welcome Title2 does not match.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingCancelbtn").equals(getTextLanguage(LanguageCode, "daas_ui", "table.configuration.cancel")), "Cancel button does not match.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingStartBtn").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.action.start")), "Start button name does not match.");
								partnerPage.clickOnElementsOfPartnerPage("billingStartBtn");
								sleeper(3000);// Due to inconsistent UI response we have to put// wait here.(Tried many ways but this is the// final solution which works as expected.)

								// Verify Assign Billing Administrator Step
								Assert.assertTrue(partnerPage.getTextOfPartnerPage("assignBillingStepTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.assign_title")), "Assign Billing Administrator Step did not open successfully.");
								LOGGER.info("Assign Billing Administrator Step open successfully");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("existingUserRadioBtnText").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.existing_users")), "Existing User radio button text does not match on Billing Setup modal pop-up.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("newUserRadioBtnText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.new_users")), "Add user radio button text does not match on Billing Setup modal pop-up.");
								partnerPage.clickOnElementsOfPartnerPage("newUserRadioBtn");
								partnerPage.enterTextForPartnerPage("newUserFirstname", PartnerVariables.BILLING_USER_FIRST_NAME);
								partnerPage.enterTextForPartnerPage("newUserLastname", PartnerVariables.BILLING_USER_LAST_NAME);
								partnerPage.enterTextForPartnerPage("newUserEmailname",getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_NEW_USER_EMAIL"));
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("addAnotherUserLink").equals(getTextLanguage(LanguageCode, "daas_ui", "users.add.add_another")), "Add Another User Link on Billing Setup modal pop-up is not proper.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("notifyUserByEmailLink").equals(getTextLanguage(LanguageCode, "daas_ui", "users.notify_by_email")), "Notify Users by email link on Billing Setup modal pop-up is not proper.");
								partnerPage.clickByJavaScriptOnPartnerPage("notifyUserByEmailCheckbox");
								partnerPage.clickOnElementsOfPartnerPage("billingStartBtn");
								sleeper(3000);
								
								// Verify Billing Administrator Step
								Assert.assertTrue(partnerPage.getTextOfPartnerPage("assignBillingStepTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.verify_title")), "Verify Billing Administrator Step did not open successfully.");
								LOGGER.info("Verify Billing Administrator Step open successfully");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("addAdminStepDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.verify_text")), "Verify Billing Administrator Desc does not match on Billing modal pop-up.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("userNameCol").equals(getTextLanguage(LanguageCode, "daas_ui", "contents.tableColumns.name")), "New User Name does not match on Billing modal pop-up.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("userEmailCol").equals(getTextLanguage(LanguageCode, "daas_ui", "global.email")), "New User Email does not match on Billing modal pop-up.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("nameValue").equals(PartnerVariables.BILLING_USER_FIRST_NAME +" " +PartnerVariables.BILLING_USER_LAST_NAME), "New User Name does not match on Billing modal pop-up.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("emailValue").equals(getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_NEW_USER_EMAIL")), "New User Email does not match on Billing modal pop-up.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingCancelbtn").equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.button.back")), "Back button does not match.");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingStartBtn").equals(getTextLanguage(LanguageCode, "daas_ui", "global.confirm")), "Confirm button name does not match.");
								partnerPage.clickOnElementsOfPartnerPage("billingStartBtn");
								partnerPage.waitForElementsOfPartnerPage("toastNotification");
								String billingAdminToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
								//softAssert.assertTrue(billingAdminToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.assigned")), "Toast notification is not generated after assigning Billing Admin to existing user.");
								LOGGER.info("Billing admin is assigned for existing user.");
								Assert.assertTrue(partnerPage.waitUntilElementInvisibleOfPartnerPage(("billingBanner")), "Billing Admin is not assigned to User.");
								LOGGER.info("Billing Banner Widget removed from dashboard.");
								
								
								//Verify Billing Admin role assigned to new user.
								gotoPartnerTeamTab();
								LOGGER.info("Redirected to Partner team list page");
								resetTableConfiguration();
								waitForPageLoaded();
								userPage.enterTextForUserPage("userEmailSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_NEW_USER_EMAIL"));
								sleeper(4000);
								partnerPage.waitForElementsOfPartnerPage("userRoleSearchValue");
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("userRoleSearchValue").contains(("Billing Admin")), "Billing admin role isassigned to user on user list page.");
								
								/*
								 * logout(); login("BILLING_ADMIN_PARTNER_NEW_USER",
								 * "BILLING_ADMIN_PARTNER_NEW_USER_PASSWORD"); waitForPageLoaded();
								 * expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui",
								 * "sidemenu.myOrganization")); sleeper(5000);
								 * softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage(
								 * "billingSideMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
								 * "sidemenu.billing")),
								 * "Billing Side Menu is not displayed for billling admin user.");
								 * partnerDetailsPage.clickByJavaScriptOnPartnerDetailsPage("billingSideMenu");
								 * softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage(
								 * "billingAdminPanelHeader").equalsIgnoreCase(getTextLanguage(LanguageCode,
								 * "daas_ui", "sidemenu.billing.title")),
								 * "Billing History panel header on billing admin is incorrect.");
								 * LOGGER.info("Billing Admin role is assigned to new user.");
								 */
								
								softAssert.assertAll();
							}
							catch(Exception e) {
								Assert.fail("Billing pop-up did not open successfully.");
							}
							finally{
								//Remove New billing admin user
								logout();
								login("BILLING_BANNER_PARTNER", "BILLING_BANNER_PARTNER_PASSWORD");
								SoftAssert softAssert = new SoftAssert();
								UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
								PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
								
								gotoPartnerTeamTab();
								LOGGER.info("Redirected to Partner team list page");
								resetTableConfiguration();
								waitForPageLoaded();
								userPage.enterTextForUserPage("userEmailSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_PARTNER_NEW_USER_EMAIL"));
								userPage.waitForElementsOfUserPage("tableOverlay");
								userPage.clickByJavaScriptOnElementsOfUserPage("userFirstRow");
								
								currentUrl = userPage.getUrlOfCurrentPage();
								String tenantID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
								navigateToBack();
								softAssert.assertTrue(userPage.removeNonCompanyUser(environment, tenantID, getEnvironment(environment)), "User removal failed for billing admin");
								LOGGER.info("Billing Admin User is removed.");
								gotoDashboardTab();
								softAssert.assertTrue(partnerPage.getTextOfPartnerPage("billingBannerHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "billingAdmin.add.banner_title")), "Billing Banner dashboard not displayed after removing the user.");
								LOGGER.info("Billing Banner re-appeared once Biiling Role/User is removed");
								softAssert.assertAll();	
							}}
						
						// This test case validates Root Admin can assign Billing admin role to Partner Owner.
						@Test(priority = 40, groups = { "REGRESSIONCOMPANIES1"}, description = "TestCase = 888566,879689")
						public final void verifyBillingAdminAssignToPartnerThroughRoot() throws Exception 
						{
							try{
					
							login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
							SoftAssert softAssert = new SoftAssert();
							PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
							PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
							UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
							UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
							CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
							
							gotoPartnerUsersTab();
							LOGGER.info("Redirected to partner users page");
							waitForPageLoaded();
							resetTableConfiguration();
							userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
							userPage.enterTextForUserPage("userEmailSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_BANNER_EMAIL"));
							userPage.waitForElementsOfUserPage("tableOverlay");
							sleeper(4000);
							partnerPage.waitForElementsOfPartnerPage("firstRowPartnerUser");
							partnerPage.clickOnElementsOfPartnerPage("firstRowPartnerUser");
							LOGGER.info("Clicked on first partner user in the list");
							partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
							LOGGER.info("Redirected to partner user details page");
							partnerPage.scrollOnPartnerPage("accountsPanelHeader");
							sleeper(2000);
							userDetailsPage.clickOnElementsOfUserDetailsPage("userRoleEditButtonThroughRoot");
							partnerPage.enterTextForPartnerPage("roleSearchBar", "Billing Admin");
							sleeper(2000);
							partnerPage.clickByJavaScriptOnPartnerPage("billingCheckboxSelect");
							userDetailsPage.clickOnElementsOfUserDetailsPage("rolesSave");
							partnerPage.waitForElementsOfPartnerPage("toastNotification");
							String userRoleToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
							softAssert.assertTrue(userRoleToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "user.update.success").replace("{section}", "User's").replace("{field}", "roles")), "Toast notification is not generated after removing Billing Admin from existing user.");
							LOGGER.info("Billing admin role is Assigned to Partner Owner through root.");
							
							//Verify Billing Role assigned to Partner Owner.
							logout();
							login("BILLING_BANNER_PARTNER", "BILLING_BANNER_PARTNER_PASSWORD");
							waitForPageLoaded();
							
							//Verify bell icon notification
							companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
							companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
							softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDescription").contains(getTextLanguage(LanguageCode, "idm", "com.hp.lh.idme.billing.admin.role.addedd.notification.title")),"Notification is not displayed when Billing admin role is assigned.");
							LOGGER.info("Billing Admin notification received to Partner owner.");
							expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.myOrganization"));
							sleeper(5000);
							softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingSideMenu").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing")), "Billing Side Menu is not displayed for billling admin user.");
							partnerDetailsPage.clickByJavaScriptOnPartnerDetailsPage("billingSideMenu");
							softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("billingAdminPanelHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.billing.title")), "Billing History panel header on billing admin is incorrect.");
							LOGGER.info("Billing Admin role is assigned to Partner owner.");
							softAssert.assertAll();
							}
							catch(Exception e) {
								Assert.fail("Billing Role is not assigned to Partenr Owner through Root Admin.");
							}
							finally{
								//Remove Billing admin role
								logout();
								login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
								SoftAssert softAssert = new SoftAssert();
								UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
								PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
								UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
								gotoPartnerUsersTab();
								LOGGER.info("Redirected to partner users page");
								waitForPageLoaded();
								resetTableConfiguration();
								userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
								userPage.enterTextForUserPage("userEmailSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "BILLING_BANNER_EMAIL"));
								userPage.waitForElementsOfUserPage("tableOverlay");
								sleeper(4000);
								partnerPage.waitForElementsOfPartnerPage("firstRowPartnerUser");
								partnerPage.clickOnElementsOfPartnerPage("firstRowPartnerUser");
								LOGGER.info("Clicked on first partner user in the list");
								partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
								LOGGER.info("Redirected to partner user details page");
								partnerPage.scrollOnPartnerPage("accountsPanelHeader");
								sleeper(2000);
								userDetailsPage.clickOnElementsOfUserDetailsPage("userRoleEditButtonThroughRoot");
								partnerPage.enterTextForPartnerPage("roleSearchBar", "Billing Admin");
								sleeper(2000);
								partnerPage.clickByJavaScriptOnPartnerPage("billingCheckbox");
								userDetailsPage.clickOnElementsOfUserDetailsPage("rolesSave");
								partnerPage.waitForElementsOfPartnerPage("toastNotification");
								String userRoleToastNotification = partnerPage.getTextOfPartnerPage("toastNotification");
								softAssert.assertTrue(userRoleToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "user.update.success").replace("{section}", "User's").replace("{field}", "roles")), "Toast notification is not generated after removing Billing Admin from existing user.");
								LOGGER.info("Billing admin role is removed from Partner Owner through root.");
								softAssert.assertAll();	
							}}

						
						// This test case validates service delivery partner toggle functionality
						@Test(priority = 41, groups = { "REGRESSIONCOMPANIES1"}, description = "TestCase = 913026")
						public final void verifyServiceDeliveryPartnerToggle() throws Exception 
						{
							//Verify Partner is service delivery Authorized
							login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US"); gotoPartnersTab();
							PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
							CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
							PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
							SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
							CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
							SoftAssert softAssert = new SoftAssert();
							LOGGER.info("Redirected to partner page"); waitForPageLoaded();
							resetTableConfiguration();
							partnerPage.clearFiltersOfPartnersListPage("clearSearchBoxFilter");
							partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
							partnerPage.waitForElementsOfPartnerPage("nameSearchBox");
							partnerPage.enterTextForPartnerPage("nameSearchBox",getEnvironmentSpecificData(System.getProperty("environment"),"SERVICE_DELIVERY_PARTNER"));
							sleeper(3000); // required to search the partner in the list
							partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
							partnerPage.clickOnElementsOfPartnerPage("firstRowPartner");
							LOGGER.info("Clicked on first partner in the list");
							partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
							LOGGER.info("Redirected to partner details page");
							partnerDetailsPage.verifyServiceDeliveryPartnerToggle();
							logout();

							login("SERVICE_DELIVERY_PARTNER_USERNAME", "SERVICE_DELIVERY_PARTNER_PASSWORD");
							gotoNonMSPSettingsTab();
							LOGGER.info("Redirected to settings page");
							waitForPageLoaded();
							companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
							companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
							companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedpartnerButton");
							LOGGER.info("Clicked on edit assigned partner button");
							sleeper(6000);
							waitForPageLoaded();
							companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerDropdown");
							companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedPartnerDropdown");
							LOGGER.info("Clicked on edit assigned partner dropdown");
							sleeper(2000);
							companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerSearchBox");
							companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedPartnerSearchBox");
							companyDetailsPage.enterTextForCompaniesDetailsPage("assignedPartnerSearchText",getEnvironmentSpecificData(System.getProperty("environment"),"SERVICE_DELIVERY_PARTNER"));
							waitForPageLoaded();
							companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("selectedPartner");
							softAssert.assertTrue(settingsPage.getTextOfSettingsPage("selectedPartnerID").equals(getEnvironmentSpecificData(System.getProperty("environment"),"SERVICE_DELIVERY_PARTNER_ID")),
									"Service delivery partner ID not present on assign partner dropdown");
							//softAssert.assertAll();
							companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("invitePartner");
						}
						
						
						/**
						 * Verify Add Subscription Contract on partner details page with Auto Renew Checkbox.
						 * @throws Exception
						 * Disabled it since User story is postponed for future release date.
						 */
						@Test(priority = 42, groups = { "REGRESSIONCOMPANIES1"}, description = "Bug 965316: [DaaS][UI] Calendar is not fully visible on Add Subscription Contract screen. Test Cases: 948368,961776,942382,962531,961776,948985",enabled=false)
						public final void verifyAddSubscriptionContractAutoRenew() throws Exception {
							login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");
							SoftAssert softAssert = new SoftAssert();
							PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
							DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
							CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
							CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
							String contractID = "AutoContractID" + generateRandomString(5);
							try {
								LOGGER.info("Logged in with root user");
								getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/partners/" + getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID_AUTO_RENEW"));
								LOGGER.info("Redirected to partner details page");
								partnerDetailsPage.verifyElementsOfPartnerDetailsPage("detailsTitle");

								// Add Subscription Contract with Auto Renew checkbox
								partnerDetailsPage.verifySubscriptionContractToggle();
								sleeper(2000);
								softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("subscriptionContractsTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.contracts")), "Subscription Contracts Title does not match.");
								Assert.assertTrue(partnerDetailsPage.createSubscriptionContractAutoRenew(LanguageCode, contractID,getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CONTRACT")), "Subscription contract did not created successfully.");
								LOGGER.info("Addition of subscription contract with Auto Renew for Partner completed successfully");

								// Verify Expires Contract Columns
								softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("expiresColumn").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.subscription.expires").toUpperCase()), "Expires column missing under subscription contract table");
								softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("expiredColValue").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.auto")), "Expires column is not changed to Auto under subscription contract table");
								LOGGER.info("Verified Expires contract columns after adding subscription contract as Auto renew.");
								
								//Verify Add Subsciption with Auto renew 
								logout();
								login("PARTNER_ADMIN_SUBSCRIPTION_AUTO_RENEW", "PARTNER_ADMIN_SUBSCRIPTION_AUTO_RENEW_PASSWORD");
								LOGGER.info("Logged in with Partner");
								getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/companies/" + getEnvironmentSpecificData(System.getProperty("environment"), "AUTO_RENEW_COMPANY"));
								companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
								LOGGER.info("Click subcription tab");
								Thread.sleep(3000);
								
								companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addSubscriptionButton");
								companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("planDropdownButton");
								companiesDetailsPage.selectDropdownSingleValueTextFromCompaniesDetailsPage("planDropdownList",CommonVariables.PLAN_PROACTIVE,"valueinDropdown");
								companiesDetailsPage.enterTextForCompaniesDetailsPage("seats", CompaniesVariables.SEATCOUNT_TWO);
								companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("startdate");
								companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("todayDate");
								String date = companiesDetailsPage.getAttributesOfCompaniesDetailsPage("startdate","value");
								companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addButton");
								
								String toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
								softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}",
										getTextLanguage(LanguageCode, "daas_ui", "product.list.daasPlan"))), "Subscriptions converted message is not shown");
								LOGGER.info("Added Subscription to the company");
								Thread.sleep(3000);
								companiesDetailsPage.scrollOnCompaniesDetailsPage("planOnPlanDetailsCards");
								softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnPlanDetailsCards").equals(CompaniesVariables.PLAN_ON_DETAILS), "plan did not match");
								softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("startDateOnPlanDetailsCards").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "campaign.details.startDate") + " : " + date) , "Start Date did't match");
								softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("renewalTextOnPlanDetailsCards").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.renewal") + " : " +getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.auto") ) , "Renewal Date did't match");
								LOGGER.info("Verified Renewal Date as Auto on Company detail page.");
								Thread.sleep(2000);
								softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("planRemainingDays"), "Plan Rmeaining days field is present even after adding contract as Auto.");
								LOGGER.info("Verified Remaining days value on Plans detail page after adding subscription as Auto.");
								
								/*//Verify Edit Subsciption with Enddate  Bug 965316: [DaaS][UI] Calendar is not fully visible on Add Subscription Contract screen.
								Thread.sleep(2000);
								companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editIcon");
								companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("expirationDate");
								companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("expirationDate");
								sleeper(2000);
								companiesPage.selectDateFromCalenderOnCompanyPage(addDaysToCurrentDate(2), "monthKeyCurrent",
										"endDateDialogRightArrow", "daysOnCurrentMonthKey");
								sleeper(2000);
								companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editSubscriptionSaveKey1");
								sleeper(5000);
								softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("renewalTextOnPlanDetailsCards").toLowerCase().contains(getTextLanguage(LanguageCode, "daas_ui", "global.end_date").toLowerCase()) , "End Date is not present after editing Subscription with enddate.");
								LOGGER.info("Verified Enddate on Company detail page.");*/
								
								LOGGER.info("Verification of Auto Renew for Subscription contract completed successfully.");
								softAssert.assertAll();
								
							} catch (Exception e) {
								LOGGER.info("Exception occured in method verifyAddEditDeleteSubscriptionContract " + e.getMessage());

							} finally {
								// Delete Contract
								logout();
								login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");
								Thread.sleep(2000);
								Assert.assertTrue(partnerDetailsPage.removeContractUsingAPI(getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID_AUTO_RENEW"), contractID), "Subscription contract not deleted through API.");
							}
						}
						
						
						
						

/**
	 * Verify different States of the Partner CRS ID  -- Connected/Error/No CRS ID
	 * Variables -->[PARTNER_DETAILS_CSDP_TEST_USER : Partner id , CSDP_PARTNER_EMAIL : Partner User Email]
	 * @throws Exception
	 */
	@Test(priority = 43, groups = { "DRYRUNTEST","REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "NFR 1170368 ,TC : 1153803,1161657")
	public final void verifyCreateUpdateCRSID() throws Exception {
		SoftAssert softAssert = new SoftAssert();

		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String email = getEnvironmentSpecificData(System.getProperty("environment"), "CSDP_PARTNER_EMAIL");

		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		LOGGER.info("Redirected to partner page");
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.enterTextForPartnerPage("nameSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_DETAILS_CSDP_TEST_USER"));
		sleeper(5000);
		partnerInfo = partnerPage.getPartnerInfo();
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		waitForPageLoaded();
		LOGGER.info("Clicked on first partner in the list");
		LOGGER.info("Redirected to partner details page");
		partnerDetailsPage.verifyElementsOfPartnerDetailsPage("detailsTitle");

		// Enter Wrong CRS ID and Verify the Status and Error Reason. Also
		// Verify on PartnerList Page

		String randomCRSID = generateRandomString(15);
		partnerDetailsPage.enterCrsIdOnPartnerDetailsPage(randomCRSID);

		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("crsIdRefreshIcon"),
				"Refresh Icon is not present");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdText"), randomCRSID,
				"Customer Reference Id is not present");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdStateText"), "Error",
				"Error State is not present  - " + partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdStateText"));
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdErrorReasonText"),
				"CRS_ID not found", "Error Reason is not present - "
						+ partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdErrorReasonText"));

		// impersonate Partner via email from Root Admin

		//userPage.impersonateUser(UserVariables.PARTNER_ADMINISTRATOR, email, LanguageCode);
		
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerDetailsEmailLink");
		waitForPageLoaded();
		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
		LOGGER.info("Clicked on sign in button of impersonation popup");
		Thread.sleep(2000);
		if (userDetailsPage.verifyElementsOfUserDetailsPage("impersonatedLink")) {
			LOGGER.info("User impersonation successfull");
		} else
			LOGGER.error("User impersonation unsucessfull");
		gotoSettingsTab();
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerDetailsUpArrowIcon");
		partnerDetailsPage.scrollToBottom();
		partnerDetailsPage.mouseHoverOnPartnerDetailPage("crsIdLabelText");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdLabelText"),
				"Customer Reference ID", "Customer Reference Id is present");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("dashCRSIdText").equals("-"),
				"CRS Id is not present");

		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
		waitForPageLoaded();
		
		
		// The End
		partnerPage.waitForElementsOfPartnerPage("partnersTab");
		gotoPartnersTab();
		partnerPage.enterTextForPartnerPage("nameSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_DETAILS_CSDP_TEST_USER"));
		sleeper(5000);

		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.scrollOnPartnerPage("partnerCsdpStatusText");
		softAssert.assertEquals(partnerPage.getTextOfPartnerPage("partnerCsdpStatusText"), "Error",
				"Partner List Page does not display CSDP Status as - Error");

		
		// Enter Correct CRS ID and Verify the Status. Also Verify on
				// PartnerList Page
		
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		waitForPageLoaded();
		LOGGER.info("Clicked on first partner in the list");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("crsIdEditIcon");
		sleeper(2000);

		
		randomCRSID = getEnvironmentSpecificData(System.getProperty("environment"), "VALID_CRS_ID");
		partnerDetailsPage.enterCrsIdOnPartnerDetailsPage(randomCRSID);
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("crsIdRefreshIcon"),
				"Refresh Icon is not present");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdText"), randomCRSID,
				"Customer Reference Id is not present");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdStateText"), "Connected",
				"Connected State is not present  - "
						+ partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdStateText"));

		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerDetailsEmailLink");
		waitForPageLoaded();
		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
		LOGGER.info("Clicked on sign in button of impersonation popup");
		Thread.sleep(5000);
		if (userDetailsPage.verifyElementsOfUserDetailsPage("impersonatedLink")) {
			LOGGER.info("User impersonation successfull");
		} else
			LOGGER.error("User impersonation unsucessfull");
		gotoSettingsTab();
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("partnerDetailsUpArrowIcon");
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerDetailsUpArrowIcon");
		partnerDetailsPage.scrollToBottom();
		partnerDetailsPage.mouseHoverOnPartnerDetailPage("crsIdLabelText");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdLabelText"),
				"Customer Reference ID", "Customer Reference Id is present");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdText"), randomCRSID,
				"Customer Reference Id is not present");

		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
		waitForPageLoaded();
		

		
		partnerPage.waitForElementsOfPartnerPage("partnersTab");
		gotoPartnersTab();
		partnerPage.enterTextForPartnerPage("nameSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_DETAILS_CSDP_TEST_USER"));
		sleeper(5000);

		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.scrollOnPartnerPage("partnerCsdpStatusText");
		softAssert.assertEquals(partnerPage.getTextOfPartnerPage("partnerCsdpStatusText"), "Connected",
				"Partner List Page does not display CSDP Status as - Connected");

		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		waitForPageLoaded();
		LOGGER.info("Clicked on first partner in the list");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("crsIdEditIcon");
		sleeper(2000);
		partnerDetailsPage.scrollToBottom();

		// Remove the CRS ID and validate it becomes empty
		randomCRSID = "";
		partnerDetailsPage.enterCrsIdOnPartnerDetailsPage(randomCRSID);
		sleeper(1000);
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("dashCRSIdText").equals("-"),
				"CRS Id is not present");

		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerDetailsEmailLink");
		waitForPageLoaded();
		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
		LOGGER.info("Clicked on sign in button of impersonation popup");
		Thread.sleep(2000);
		if (userDetailsPage.verifyElementsOfUserDetailsPage("impersonatedLink")) {
			LOGGER.info("User impersonation successfull");
		} else
			LOGGER.error("User impersonation unsucessfull");
		gotoSettingsTab();
		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerDetailsUpArrowIcon");
		partnerDetailsPage.scrollToBottom();
		partnerDetailsPage.mouseHoverOnPartnerDetailPage("crsIdLabelText");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdLabelText"),
				"Customer Reference ID", "Customer Reference Id is present");
		softAssert.assertTrue(partnerDetailsPage.getTextOfPartnerDetailsPage("dashCRSIdText").equals("-"),
				"CRS Id is not present");

		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
		waitForPageLoaded();
		partnerPage.waitForElementsOfPartnerPage("partnersTab");
		gotoPartnersTab();
		partnerPage.enterTextForPartnerPage("nameSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_DETAILS_CSDP_TEST_USER"));
		sleeper(5000);

		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		waitForPageLoaded();
		partnerPage.scrollOnPartnerPage("partnerCsdpStatusText");
		softAssert.assertEquals(partnerPage.getTextOfPartnerPage("partnerCsdpStatusText"), "N/A",
				"Partner List Page does not display CSDP Status as N/A");

		softAssert.assertAll();

	}

	/**
	 * Verify the CRS ID UI [labels/texts/button/modal window] via Root Admin for a Partner.
	 * Also Verify Max length Error Message. Partner List has CSDP Status column with values - Error/Pending/Connected
	 * Variables -->[PARTNER_DETAILS_CSDP_TEST_USER : Partner id]
	 * @throws Exception
	 */
	@Test(priority = 44, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI",
			"ALL" }, description = "NFR 1170368 , TC:1153533,1154748")
	public final void verifyCrsIdUIAndErrorMessage() throws Exception {
		SoftAssert softAssert = new SoftAssert();

		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		LOGGER.info("Redirected to partner page");
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.enterTextForPartnerPage("nameSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_DETAILS_CSDP_TEST_USER"));
		sleeper(5000);
		partnerInfo = partnerPage.getPartnerInfo();
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		waitForPageLoaded();
		LOGGER.info("Clicked on first partner in the list");
		LOGGER.info("Redirected to partner details page");
		partnerDetailsPage.verifyElementsOfPartnerDetailsPage("detailsTitle");

		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("partnerDetailsUpArrowIcon");
		partnerDetailsPage.scrollToBottom();
		partnerDetailsPage.scrollOnPartnerDetailsPage("crsIdEditIcon");
		String randomCRSID = generateRandomString(41);

		// CRS ID field UI Validation
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdLabelText"),
				"Customer Reference ID", "Customer Reference Id is present");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("crsIdEditIcon"),
				"Edit Icon is present");

		partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("crsIdEditIcon");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("crsIdAddModalWindowTextbox");

		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("crsIdAddModalWindowTextbox"),
				"Customer Reference Id Modal window textbox is not present");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("crsIdAddModalWindowSaveButton"),
				"Customer Reference Id Modal window Save Button is not present");
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("crsIdAddModalWindowCancelButton"),
				"Customer Reference Id Modal window Cancel Button is not present");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdAddModalWindowHeaderText"),
				"Customer Reference ID", "Modal Window Header Text did not match "
						+ partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdAddModalWindowHeaderText"));
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdAddModalWindowLabelText"),
				"Find your CRS_ID from your Channel Services Delivery Platform.",
				"Modal Window Description Text did not match "
						+ partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdAddModalWindowLabelText"));

		// CRS ID maximum length allowed Error--> 40 Chars
		partnerDetailsPage.enterTextForPartnerDetailsPage("crsIdAddModalWindowTextbox", randomCRSID);
		partnerDetailsPage.clickByJavaScriptOnPartnerDetailsPage("crsIdAddModalWindowSaveButton");
		sleeper(1000);
		softAssert.assertTrue(
				partnerDetailsPage.verifyElementsOfPartnerDetailsPage("crsIdStringLengthErrorMessageLabel"),
				"Error Message did not match -  "
						+ partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdStringLengthErrorMessageLabel"));

		randomCRSID = generateRandomString(15);
		partnerDetailsPage.enterTextForPartnerDetailsPage("crsIdAddModalWindowTextbox", randomCRSID);

		partnerDetailsPage.clickByJavaScriptOnPartnerDetailsPage("crsIdAddModalWindowSaveButton");

		partnerDetailsPage.waitUntilElementIsInvisibleOfPartnerDetailsPage("crsIdAddModalWindowTextbox");
		partnerDetailsPage.waitForElementsOfPartnerDetailsPage("crsIdEditIcon");
		sleeper(2000);
		partnerDetailsPage.scrollToBottom();
		softAssert.assertTrue(partnerDetailsPage.verifyElementsOfPartnerDetailsPage("crsIdRefreshIcon"),
				"Refresh Icon is not present");
		softAssert.assertEquals(partnerDetailsPage.getTextOfPartnerDetailsPage("crsIdText"), randomCRSID,
				"Customer Reference Id is not present");

		gotoPartnersTab();
		partnerPage.enterTextForPartnerPage("nameSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_DETAILS_CSDP_TEST_USER"));
		sleeper(5000);
		partnerPage.scrollOnPartnerPage("csdpStatusFilterDropdown");
		partnerPage.clickOnElementsOfPartnerPage("csdpStatusFilterDropdown");
		String[] arrCsdpStatus = { "Connected", "Pending", "Error" };
		softAssert.assertEqualsNoOrder(partnerPage.getTextOfList("csdpStatusFilterDropdownOptions").toArray(),
				arrCsdpStatus, "CSDP Status filter value does not match");

		softAssert.assertAll();

	}
}
					