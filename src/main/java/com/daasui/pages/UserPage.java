package com.daasui.pages;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.PreferenceVariables;
import com.daasui.constants.UserVariables;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader userPagePropertiesReader = new ObjectReader();
	private Properties userPageProperties;
	private UserPage instance;
	public static String mailIdForMailinator, mailIdforVerification, selUserAutomationLastName;
	public static ArrayList<String> listOfFirstName = new ArrayList<String>();
	public static ArrayList<String> listOfEmail = new ArrayList<String>();
	public static ArrayList<String> listOfRoles = new ArrayList<String>();
	public static ArrayList<String> listOfLastName = new ArrayList<String>();
	public static ArrayList<String> displayName = new ArrayList<String>();
	public static ArrayList<String> status = new ArrayList<String>();
	public static String azureEmail = getEnvironmentSpecificData(System.getProperty("environment"), "AZURE_IMPORT_EMAIL");
	public static String azurePassword = getEnvironmentSpecificData(System.getProperty("environment"), "AZURE_IMPORT_PASSWORD");
	public static String emailSearch = null;
	public static String tenantIDUser = getEnvironmentSpecificData(System.getProperty("environment"), "USER_COMPANY_TENANT_ID");
	public static ArrayList<String> userDetails = new ArrayList<String>();
	public static String uiVersion = System.getProperty("uiVersion");
	
	public UserPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (UserPage.class) {
				if (instance == null) {
					instance = new UserPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public UserPage(WebDriver driver) throws IOException {
		userPageProperties = userPagePropertiesReader.getObjectRepository("UserPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = userPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfUserPage(String key) throws Exception {
		return verifyElementIsPresent(userPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfUserPage(String key) throws Exception {
		return verifyElementIsVisible(userPageProperties.getProperty(key));
	}

	public final boolean matchTextOfUserPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(userPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfUserPage(String key) throws Exception {
		return verifyElementIsEnable(userPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfUserPage(String key) throws Exception {
		return verifyElementIsSelected(userPageProperties.getProperty(key));
	}

	public final String getTextOfUserPage(String key) throws Exception {
		return getTextBy(userPageProperties.getProperty(key));
	}

	public final String getAttributesOfUserPage(String key, String value) throws Exception {
		return getAttribute(userPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfUserPage(String key) throws Exception {
		click(userPageProperties.getProperty(key));
	}

	public final void enterTextForUserPage(String key, String Text) throws Exception {
		enterText(userPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfUserPage(String key) throws Exception {
		return verifyElementIsClickable(userPageProperties.getProperty(key));
	}

	public final WebElement getElementOfUserPage(String key) throws Exception {
		return getElement(userPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfUserPageForClick(String key) throws Exception {
		return verifyElementIsClickable(userPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfUserPageForinvisibile(String key) throws Exception {
		return verifyElementIsinvisibile(userPageProperties.getProperty(key));
	}

	public final void selectValueFromDropDownOfUserPage(String key, String value) throws Exception {
		selecValueFromDropdown(userPageProperties.getProperty(key), value);
	}

	public final List<WebElement> getElementsOfUserPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(userPageProperties.getProperty(key));
	}

	public final void scrollTillViewUserPage(String locator) throws Exception {
		scrollTillView(userPageProperties.getProperty(locator));
	}

	public final ArrayList<String> getTextOfListOfUserPage(String key) throws Exception {
		return getTextOfList(userPageProperties.getProperty(key));
	}

	/**
	 * This method verify searching user on users tab using user email
	 * 
	 * @param userEmail
	 * @return
	 */
	public final boolean redirectToUserDetails(String userEmail) {
		boolean flag = false;
		try {
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			resetTableConfiguration();
			userPage.waitForElementsOfUserPage("userEmailSearchBox");
			userPage.enterTextForUserPage("userEmailSearchBox", userEmail);
			if (!userPage.verifyElementsOfUserPage("noUserDisplayText")) {
				if (userPage.matchTextOfUserPage("emailListValues", userEmail)) {
					userPage.clickOnElementsOfUserPage("firstRowUser");
					if (userDetailsPage.matchTextOfUserDetailsPage("emailValue", userEmail)) {
						LOGGER.info("Redirected to " + userEmail + " details");
						flag = true;
					} else {
						return flag;
					}
				} else {
					LOGGER.error("Device " + userEmail + " does not match ");
					return flag;
				}
			} else {
				LOGGER.error("Device " + userEmail + " not found ");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in redirectToUserDetails" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method verify clicking on edit button for editing daas user role
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean clickOnUserRoleEditButton() throws Exception {
		waitForElementsOfUserPage("scrollRoleAssignment");
		scrollTillViewUserPage("scrollRoleAssignment");
		if (verifyElementsOfUserPage("editUserRole")) {
			clickOnElementsOfUserPage("editUserRole");
			return true;
		} else {
			LOGGER.error("Error: Edit button is not present");
			return false;
		}
	}

	/**
	 * This method verify localization matches for Emm Admin role tabs
	 * 
	 * @param roleMap
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyEmmAdminRoleTabs(HashMap<String, String> roleMap, String languageCode) {
		boolean flag = false;
		try {
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			Set<Entry<String, String>> set = roleMap.entrySet();
			Iterator<Entry<String, String>> roleIterator = set.iterator();
			while (roleIterator.hasNext()) {
				Map.Entry<String, String> roleEntry = (Map.Entry<String, String>) roleIterator.next();
				if (userDetailsPage.getTextOfUserDetailsPage(roleEntry.getKey().toString()).equalsIgnoreCase(getTextLanguage(languageCode, "lhserver", roleEntry.getValue().toString()))) {
					LOGGER.info(getTextLanguage(languageCode, "lhserver", roleEntry.getValue().toString()) + " is Displyed");
					flag = true;
				} else {
					LOGGER.error("Error : " + roleEntry.getValue() + "Tab is not Displyed");
					return flag;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyEmmAdminRoleTabs" + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method verify supported tabs for EMM_Admin user role login
	 * 
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyEMMAdminRoleLoginTabs(String languageCode) throws Exception {
		HashMap<String, String> daasRoles = new HashMap<String, String>();
		daasRoles.put("settingsTab", "navigation.settings");
		daasRoles.put("help&SupportTab", "navigation.help");
		daasRoles.put("EMMTooltab", "navigation.emm_tool");
		if (verifyEmmAdminRoleTabs(daasRoles, languageCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method verify currently assigned roles count and removes all the Daas user roles
	 * 
	 * @return
	 */
	public final boolean removeAllDaasRoles() {
		boolean flag = false;
		try {
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			userDetailsPage.waitForElementsOfUserDetailsPage("userRoleEditButton");
			userDetailsPage.verifyElementsOfUserDetailsPage("userRoleEditButton");
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			userDetailsPage.clickOnElementsOfUserDetailsPage("userRoleEditButton");
			userDetailsPage.waitForElementsOfUserDetailsPage("daasrolescheck");
			userDetailsPage.verifyElementsOfUserDetailsPage("daasrolescheck");
			List<WebElement> daasRoles = userDetailsPage.getElementsOfUserDetailsPage("daasrolescheck");
			int daasRoleCount = daasRoles.size();
			LOGGER.info(daasRoleCount + " Daas User Roles are present ");
			List<WebElement> daasRolesRemove = userDetailsPage.getElementsOfUserDetailsPage("daasrolescheck");
			Iterator<WebElement> roleIterator = daasRolesRemove.iterator();
			if (daasRoleCount > 0) {
				while (roleIterator.hasNext()) {
					WebElement roleElement = roleIterator.next();
					roleElement.click();
				}
				LOGGER.info("removed all the daas roles");

			} else {
				LOGGER.error("Error : Atleast one daas role must be present to remove the role ");
				return false;
			}
			if (!userDetailsPage.verifyElementsOfUserDetailsPage("daasrolescheck")) {
				LOGGER.info("All Daas User Roles Are Removed ");
				return true;
			} else {
				LOGGER.error("Error : All Daas User Roles are not removed ");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in addEMMAdminUserRole" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method verify adding daas ui roles matching with user provided role elements
	 * 
	 * @param daasUserRole
	 * @throws Exception
	 */
	public final boolean addDaasUserRoles(String daasUserRole[]) throws Exception {
		boolean flag = false;
		List<WebElement> rolelabelcheck = getElementsOfUserPage("daasRoleLabels");
		for (String roleCheck : daasUserRole) {
			for (WebElement roleCheckbox : rolelabelcheck) {
				if (roleCheck.equals(roleCheckbox.getAttribute("value"))) {
					roleCheckbox.click();
					flag = true;
					LOGGER.info(roleCheck + " user role matched");
				}
			}
		}
		if (flag == false) {
			LOGGER.error("Provided daas user roles does not matched");
		}
		return flag;
	}

	/**
	 * This method verify if daas user role is assigned to user after adding it
	 * 
	 * @param userRole
	 * @throws Exception
	 */
	public final boolean verifyIsDaasUserRoleAdded(String userRole[]) throws Exception {
		boolean flag = false;
		List<WebElement> daasRoleCheck = getElementsOfUserPage("daasUserRoleCheck");
		for (String roleCheck : userRole) {
			for (WebElement roleLabels : daasRoleCheck) {
				if (roleCheck.equals(roleLabels.getAttribute("value"))) {
					LOGGER.info(roleCheck + " daas user role validated successfully");
					flag = true;
				}
			}
		}
		if (flag == false) {
			LOGGER.error("Provided daas user roles does not matched");
		}
		return flag;
	}

	/**
	 * This method verify Emm admin role label and description
	 * 
	 * @param daasUserRole
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyEmmAdminRoleDescription(String daasUserRole[], String languageCode) throws Exception {
		scrollTillViewUserPage("emmAdminRole");
		verifyElementsOfUserPage("emmAdminRole");
		waitForElementsOfUserPage("emmAdminRole");
		verifyElementsOfUserPage("emmAdminRoleDescription");
		if (getTextOfUserPage("emmAdminRoleDescription").equalsIgnoreCase(getTextLanguage(languageCode, "lhserver", "users.roles.emm_admin_description"))) {
			if (getTextOfUserPage("emmAdminRole").equalsIgnoreCase(getTextLanguage(languageCode, "lhserver", "users.roles.emm_admin"))) {
				LOGGER.info("Emm Admin Role label matched");
			} else {
				LOGGER.error("Error : EMM Admin role label does not match");
			}
			LOGGER.info("Emm Admin Role description matched");
			return true;
		} else {
			LOGGER.error("Error : EMM Admin role description does not match");
			return false;
		}
	}

	/**
	 *  This method verify emmtool tab redirecting new window on Emm portal
	 * 
	 * @param emmType
	 * @return */
	public final boolean verifyEMMToolRedirectingEMMPortal(String emmType) {
		boolean flag = false;
		try {
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			if (userDetailsPage.verifyElementsOfUserDetailsPage("EMMTooltab")) {
				userDetailsPage.clickByJavaScriptOnUserDetailsPage("EMMTooltab");
				if (emmType.toUpperCase().equalsIgnoreCase(PreferenceVariables.AIRWATCH)) {
					userDetailsPage.clickByJavaScriptOnUserDetailsPage("airwatchTab");
					sleeper(5000); // Takes time for redirecting on new tab and fetching third party url
					waitForPageLoaded();
					switchToDifferentTab();
					if (getUrlOfCurrentPage().toString().contains(PreferenceVariables.AIRWATCH_URL_EMMADMIN)) {
						LOGGER.info("Redirected to Airwatch Portal: " + getUrlOfCurrentPage());
						flag = true;
					} else {
						LOGGER.error("Error : Redirection to Airwatch Portal failed : " + getUrlOfCurrentPage());
						return flag;
					}
				} else if (emmType.toUpperCase().equalsIgnoreCase(PreferenceVariables.INTUNE)) {
					userDetailsPage.clickByJavaScriptOnUserDetailsPage("intuneTab");
					sleeper(5000); // Takes time for redirecting on new tab and fetching third party url
					waitForPageLoaded();
					switchToDifferentTab();
					if (getUrlOfCurrentPage().contains(ConstantURL.AZURE_PORTAL_URL) || getUrlOfCurrentPage().contains(ConstantURL.INTUNE_URL)) {
						LOGGER.info("Redirected to Inutne Portal: " + getDriver().getCurrentUrl());
						flag = true;
					} else {
						LOGGER.error("Error : Redirection to Intune Portal failed : " + getUrlOfCurrentPage());
						return flag;
					}
				} else {
					LOGGER.error("Emm Type can be AIRWATCH , INTUNE  only");
					return flag;
				}
			} else {
				LOGGER.error("Emm Tool Tab is not present for roles except Emm admin role...Please check assigned user role");
				return flag;
			}
			switchBackToPreviousTab();
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyEMMToolRedirectingEMMPortal : " + e.getMessage());
		}
		return flag;
	}

	public final boolean verifyChromeToolRedirectingGoogleAdminPortal() {
		boolean flag = false;
		try {
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			if (userDetailsPage.verifyElementsOfUserDetailsPage("chromeEnterpriseTab")) {
				userDetailsPage.clickByJavaScriptOnUserDetailsPage("chromeEnterpriseTab");
				sleeper(5000); // Takes time for redirecting on new tab and fetching third party url
				waitForPageLoaded();
				switchToDifferentTab();
				if (getUrlOfCurrentPage().contains(PreferenceVariables.CHROME_PORTAL_URL) || getUrlOfCurrentPage().contains(PreferenceVariables.CHROME_ACCOUNT_PORTAL_URL)) {
					LOGGER.info("Redirected to the Google Admin Portal");
					flag = true;
				} else {
					LOGGER.error("Error : Redirection to Google Admin Portal failed : " + getUrlOfCurrentPage());
					return flag;
				}
			} else {
				LOGGER.error("Chrome Enterprise Tab is not present for roles except Emm admin role...Please check assigned user role");
				return flag;
			}
			switchBackToPreviousTab();
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyChromeToolRedirectingGoogleAdminPortal : " + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method verify removing all Daas user roles and add an Emm_Admin daas user role
	 * 
	 * @param languageCode
	 * @return
	 */
	public final boolean setEMMAdminUserRole(String languageCode) {
		boolean flag = false;
		try {
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			removeAllDaasRoles();
			userDetailsPage.scrollOnUserPage("emmAdminRole");
			userDetailsPage.verifyElementsOfUserDetailsPage("emmAdminRole");
			userDetailsPage.waitForElementsOfUserDetailsPage("emmAdminRole");
			userDetailsPage.verifyElementsOfUserDetailsPage("emmAdminRoleDescription");
			if (userDetailsPage.getTextOfUserDetailsPage("emmAdminRoleDescription").equalsIgnoreCase(getTextLanguage(languageCode, "roles_meta_service", "daas_test.customer.default.roles.description.emm_admin"))) {
				if (userDetailsPage.getTextOfUserDetailsPage("emmAdminRole").equalsIgnoreCase(getTextLanguage(languageCode, "lhserver", "users.roles.emm_admin"))) {
					userDetailsPage.clickOnElementsOfUserDetailsPage("emmAdminRoleCheckbox");
					userDetailsPage.clickOnElementsOfUserDetailsPage("roleSaveButtonOnPopup");
					LOGGER.info("Emm Admin Role is assigned");
				} else {
					LOGGER.error("Error : EMM Admin role label does not match");
				}
				LOGGER.info("Emm Admin Role description matched");
				flag = true;
			} else {
				LOGGER.error("Error : EMM Admin role description does not match");
				return flag;
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured in setEMMAdminUserRole" + e.getMessage());
		}
		return flag;
	}

	/**
	 * @param navigationItemPreviousKey
	 * @return
	 * @throws Exception
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviousKey) throws Exception {
		return !getElement(userPageProperties.getProperty(navigationItemPreviousKey)).getAttribute("class").contains("disabled");
	}

	/**
	 * This is a method to get total records
	 * 
	 * @param key - Locator of element pagination total count.
	 * @return true - Boolean value return either true or false
	 * @throws Exception
	 */
	public final int getTotalRecordCount(String key) throws Exception {
		int totalRecord = 0;
		String[] allText = getTextBy(userPageProperties.getProperty(key)).split(" |/");
		for (int i = allText.length - 1; i > 0; i--) {
			if (isInt(allText[i])) {
				totalRecord = Integer.parseInt(allText[i].trim());
				break;
			}
		}
		return totalRecord;
	}

	/**
	 * This is a method to get text of an element
	 * 
	 * @param dropdownIdKey - Id of pagination dropDown
	 * @param dropdownOptionlistKey - This is the key for values on dropDown
	 * @return true - Boolean value return either true or false
	 * @throws Exception
	 */
	public final int getSelectedOptionTextofPaginationOnUserPage(String dropdownIdKey, String dropdownOptionListKey) throws Exception {
		click(userPageProperties.getProperty(dropdownIdKey));
		sleeper(1000);
		return getSelectedDropdownOptionOnPagination(userPageProperties.getProperty(dropdownOptionListKey));
	}

	/**
	 * This is a method to select option from drop down of pagination
	 * 
	 * @param dropdownId - Id of pagination dropdown
	 * @param key - This is the key for values on dropdown
	 * @param text - This is the values text to select from dropdown
	 * @return true
	 * @throws Exception
	 */
	public final boolean selectElementFromDropDownOfUserPage(String dropdownId, String key, String text) throws Exception {
		click(userPageProperties.getProperty(dropdownId));
		return selectFromDropdown(userPageProperties.getProperty(dropdownId), userPageProperties.getProperty(key), text);
	}

	/**
	 * This is a method to verify search functionality of search filters present on Users page
	 * 
	 * @param languageCode - This is used as code for multiple languages.
	 * @param textKey - Locator of text box in which text to be entered.
	 * @param text - Text which is to be searched.
	 * @param emptyTextKey - Locator for empty text message.
	 * @param listKey - Locator for list of elements.
	 * @return true - Boolean value return either true or false
	 * @throws Exception
	 */
	public final boolean verifySearchValueOnUserPage(String languageCode, String textKey, String text, String emptyTextKey, String listKey) throws Exception {
		return verifySearchFunctionality(languageCode, userPageProperties.getProperty(textKey), text, userPageProperties.getProperty(emptyTextKey), userPageProperties.getProperty(listKey));
	}

	/**
	 * @param key :Locator of element.
	 * @throws Exception
	 */
	public final void scrollOnUserPage(String key) throws Exception {
		scrollTillView(userPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify the filter functionality when a single options are selected from a static list of options
	 * 
	 * @param checkboxKey
	 * @return true
	 * @throws Exception
	 */
	public final boolean verifyFilterSingleSelectOnUserPage(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		return verifyFilterFunctionalityForSingleSelect(LanguageCode, userPageProperties.getProperty(checkboxKey), userPageProperties.getProperty(listOfElementKey), userPageProperties.getProperty(columnListKey), userPageProperties.getProperty(emptyTextKey));
	}

	/**
	 * This is a method to verify the filter functionality when multiple options are selected from a static list of options
	 * 
	 * @param LanguageCode - Language code
	 * @param checkboxKey - Locator for the checkboxes in dropdown
	 * @param listOfElementKey - Locator for list of items in dropdown
	 * @param columnListKey - Locator for list of all items in the column
	 * @param emptyTextKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifyFilterMultiSelectOnUserPage(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		return verifyFilterFunctionalityForMultiSelectForDyanmicList(LanguageCode, userPageProperties.getProperty(checkboxKey), userPageProperties.getProperty(listOfElementKey), userPageProperties.getProperty(columnListKey), userPageProperties.getProperty(emptyTextKey));
	}

	/**
	 * This is a method to click on an element using java script
	 * 
	 * @param key - locator of element
	 * @throws Exception
	 */

	public final void clickByJavaScriptOnElementsOfUserPage(String key) throws Exception {
		clickByJavaScript(userPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait Until element is visible
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean waitUntilElementsOfUserPage(String key) throws Exception {
		return waitUntillElementIsPresent(userPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify filter functionality for single select assigned to column
	 * 
	 * @param LanguageCode - language code
	 * @param searchTextKey - locator of searchbox
	 * @param searchText - text to be entered
	 * @param emptyTextKey -locator for "no items available" message
	 * @param listKey - locator of options filtered on popup
	 * @param checkboxKey - locator of checboxes of available options
	 * @param columnListKey - locator of all items filtered in column
	 * @param emptyTextColumnKey - locator of "no items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifySingleSelectionFilterFunctionalityFromDynamicDropdownOnUserPage(String languageCode, String searchTextKey, String searchText, String emptyTextKey, String listKey, String columnListKey, String emptyTextColumnKey) throws Exception {
		return verifyFilterFunctionalityForSingleSelectionFromDynamicDropdown(languageCode, userPageProperties.getProperty(searchTextKey), searchText, userPageProperties.getProperty(emptyTextKey), userPageProperties.getProperty(listKey), userPageProperties.getProperty(columnListKey), userPageProperties.getProperty(emptyTextColumnKey));
	}

	/**
	 * This is a method to verify filter functionality for assigned to column when multiple options are selected
	 * 
	 * @param LanguageCode - language code
	 * @param searchTextKey - locator of searchbox
	 * @param searchText - search Text to be entered
	 * @param emptyTextKey -locator for "no items available" message
	 * @param listKey - locator of options filtered on popup
	 * @param columnListKey - locator of all items filtered in column
	 * @param emptyTextColumnKey - locator of "no items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifyMultiSelectionFilterFunctionalityFromDynamicDropdownOnUserPage(String languageCode, String searchTextKey, String searchText, String emptyTextKey, String listKey, String columnListKey, String emptyTextColumnKey) throws Exception {
		return verifyMultiSelectionFilterFunctionalityFromDynamicDropdown(languageCode, userPageProperties.getProperty(searchTextKey), searchText, userPageProperties.getProperty(emptyTextKey), userPageProperties.getProperty(listKey), userPageProperties.getProperty(columnListKey), userPageProperties.getProperty(emptyTextColumnKey));
	}

	/**
	 * This is the method to get user details
	 * 
	 * @return - list of all details of a user
	 */
	public final ArrayList<String> getUserInfo() {
		try {
			ArrayList<String> userInfo = new ArrayList<String>();
			List<WebElement> detailsList = getElementsTillAllElementsPresent(userPageProperties.getProperty("userListRowAllValueKey"));
			if (detailsList.size() > 0) {
					for (int i = 1; i < detailsList.size(); i++) {
						String value = detailsList.get(i).getText();
						userInfo.add(value);
					}
		
				for (int i = 0; i < userInfo.size(); i++) {
					if (userInfo.get(i).equals("N/A")) {
						userInfo.set(i, "-");
					}
				}
			} else {
				LOGGER.error("Size issue occured in list detailsList ");
			}
			return userInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getUserInfo " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to click on an element present on user page using javascript
	 * 
	 * @param key - locator of the element
	 */
	public final void clickByJavaScriptOnUserPage(String key) {
		try {
			clickByJavaScript(userPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnUserPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get a list of elements of device list page
	 * 
	 * @param key - Locator of list
	 * @return - list of webelements
	 */
	public final List<WebElement> getElementsTillAllElementsVisibleofUserPage(String key) {
		try {
			return getAllElements(userPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofUserPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * Used to select values from dropdown based on index.
	 * 
	 * @param key: List of values.
	 * @param index: Index of value to be selected.
	 * @return
	 */
	public final String singleSelectDropDownUserPage(String key, int index) {
		try {
			return selectAnyValueFromDropdown(userPageProperties.getProperty(key), index);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method singleSelectDropDownUserPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This method is used to define parameters of company selection flow.
	 * 
	 * @return - hashmap of company change details
	 */
	public final HashMap<String, String> getCompanyChangeDetails() {
		try {
			HashMap<String, String> companyChangeInfo = new HashMap<String, String>();
			companyChangeInfo.put("addButtonKey", "addButton");
			companyChangeInfo.put("dropDownArrowKey", "dropDownArrow");
			companyChangeInfo.put("searchBoxKey", "searchBox");
			companyChangeInfo.put("listOfElementsKey", "listOfElements");
			companyChangeInfo.put("dropDownBoxKey", "dropDownBox");
			companyChangeInfo.put("nextButtonKey", "nextButton");
			companyChangeInfo.put("addUsersTextCheckKey", "addUsersTextCheck");
			companyChangeInfo.put("noCompanyPresentKey", "noCompanyPresent");
			return companyChangeInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getCompanyChangeDetails " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This method will select company for devices activities
	 * 
	 * @param companyChangeInfo - Locators required to change the company
	 * @param text - Company to be selected
	 * @param languageCode - Language code
	 * @return - boolean value of hether the company was selected successfully
	 */
	public final boolean selectCompanyOfUserPage(HashMap<String, String> companyChangeInfo, String text, String languageCode) {
		try {
			boolean flag = false;
			waitForPageLoaded();
			waitForElementsOfUserPage(companyChangeInfo.get("addButtonKey"));
			clickOnElementsOfUserPage(companyChangeInfo.get("addButtonKey"));
			LOGGER.info("Clicked on add button");
			waitForElementsOfUserPage(companyChangeInfo.get("dropDownArrowKey"));
			waitForPageLoaded();
			sleeper(3000);
			clickOnElementsOfUserPage(companyChangeInfo.get("dropDownArrowKey"));
			LOGGER.info("Clicked on dropdown arrow");
			flag = verifySingleSelectDropdownSearchBox(userPageProperties.getProperty(companyChangeInfo.get("listOfElementsKey")), text, userPageProperties.getProperty(companyChangeInfo.get("searchBoxKey")), userPageProperties.getProperty(companyChangeInfo.get("dropDownBoxKey")), userPageProperties.getProperty(companyChangeInfo.get("noCompanyPresentKey")));

			if (flag) {
				clickOnElementsOfUserPage(companyChangeInfo.get("nextButtonKey"));
				LOGGER.info("Clicked on next button");
				if (getTextOfUserPage(companyChangeInfo.get("addUsersTextCheckKey")).equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "users.add.how_to"))) {
					LOGGER.info("Company got selected successfully.");
					flag = true;
				} else {
					LOGGER.error("Company did not get selected successfully.");
				}
			} else {
				LOGGER.error("Company did not get selected successfully.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectCompanyOfDevicePage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to define parameters of add device manually.
	 * 
	 * @return - hashmap of locators required for adding device manually
	 */
	public final HashMap<String, String> getAddUserManuallyDetails() {
		try {
			HashMap<String, String> getAddUserManuallyInfo = new HashMap<String, String>();
			getAddUserManuallyInfo.put("addManualButtonKey", "addManualButton");
			getAddUserManuallyInfo.put("firstNameBoxKey", "firstNameBox");
			getAddUserManuallyInfo.put("lastNameBoxKey", "lastNameBox");
			getAddUserManuallyInfo.put("emailBoxKey", "emailBox");
			getAddUserManuallyInfo.put("roleBoxKey", "roleBox");
			getAddUserManuallyInfo.put("addAnotherKey", "addAnother");
			getAddUserManuallyInfo.put("dropdownArrowRoleKey", "dropdownArrowRole");
			getAddUserManuallyInfo.put("roleDropdownListKey", "roleDropdownList");
			getAddUserManuallyInfo.put("submitButtonKey", "submitButton");
			getAddUserManuallyInfo.put("cancelButtonIndPopUpKey", "cancelButtonIndPopUp");
			getAddUserManuallyInfo.put("toastNotificationKey", "toastNotification");
			getAddUserManuallyInfo.put("emailSearchBoxKey", "emailSearchBox");
			getAddUserManuallyInfo.put("emailListValuesKey", "emailListValues");
			getAddUserManuallyInfo.put("nameListValuesKey", "nameListValues");
			getAddUserManuallyInfo.put("roleListValuesKey", "roleListValues");
			getAddUserManuallyInfo.put("statusListValuesKey", "statusListValues");
			getAddUserManuallyInfo.put("selectAllCheckBoxKey", "selectAllCheckBox");
			getAddUserManuallyInfo.put("removeButtonListPageKey", "removeButtonListPage");
			getAddUserManuallyInfo.put("removeButtonModalKey", "removeButtonModal");
			getAddUserManuallyInfo.put("noElementsDisplayTextKey", "noElementsDisplayText");
			getAddUserManuallyInfo.put("resendInvitationButtonKey", "resendInvitationButton");
			getAddUserManuallyInfo.put("resendModalButtonKey", "resendModalButton");
			getAddUserManuallyInfo.put("goBackPreviousLinkKey", "goBackPreviousLink");
			getAddUserManuallyInfo.put("notifyUserByEmailKey", "notifyUserByEmail");
			getAddUserManuallyInfo.put("nextButtonAddUserMethodKey", "nextButtonAddUserMethod");
			return getAddUserManuallyInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAddDeviceManuallyDetails " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the add user manually functionality
	 * 
	 * @param getAddUserManuallyInfo : Details of add user manually locators.
	 * @param languageCode : Language code
	 * @param userCount: Number of user to be added.
	 * @return
	 */

	public final boolean verifyAddManuallyUsers(HashMap<String, String> getAddUserManuallyInfo, String languageCode, int userCount) {
		try {
			boolean flag = false;
			int randomNumber = 0;
			byte[] array = new byte[5];
			new Random().nextBytes(array);
			Random random = new Random();
			clickOnElementsOfUserPage(getAddUserManuallyInfo.get("nextButtonAddUserMethodKey"));
			waitForPageLoaded();
			if (!(userCount == 1))
				for (int i = 0; i < userCount - 1; i++) {
					clickOnElementsOfUserPage(getAddUserManuallyInfo.get("addAnotherKey"));
				}
			if (userCount == 1) {
				randomNumber = random.nextInt(1000);
				String generatedString = new String(array, Charset.forName("UTF-8"));
				String randomString = String.valueOf(randomNumber);

				getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("firstNameBoxKey")).get(0).sendKeys("SelUserAutomationFirst");
				getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("lastNameBoxKey")).get(0).sendKeys("SelUserAutomationLast" + randomString);
				displayName.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("firstNameBoxKey")).get(0).getAttribute("value") + " " + getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("lastNameBoxKey")).get(0).getAttribute("value"));
				LOGGER.info("Name is entered.");
				getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailBoxKey")).get(0).sendKeys(generatedString+"SelUser" + randomString + "@hpmsqa.mailinator.com");
				listOfEmail.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailBoxKey")).get(0).getAttribute("value"));
				LOGGER.info("Email is entered.");
				getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("dropdownArrowRoleKey")).get(0).click();
				singleSelectDropDownUserPage(getAddUserManuallyInfo.get("roleDropdownListKey"), 0);
				listOfRoles.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("dropdownArrowRoleKey")).get(0).getText());
				LOGGER.info("Role is entered.");
			} else if (userCount < 5 && !(userCount == 1)) {
				for (int i = 0; i < userCount; i++) {
					randomNumber = random.nextInt(1000);
					String generatedString = RandomStringUtils.random(5, true, false);
					String randomString = String.valueOf(randomNumber);
					selUserAutomationLastName = "SelUserAutomationLast"+randomString;

					getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("firstNameBoxKey")).get(i).click();
					getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("firstNameBoxKey")).get(i).sendKeys("SelUserAutomationFirst");
					getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("lastNameBoxKey")).get(i).sendKeys(selUserAutomationLastName);
					displayName.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("firstNameBoxKey")).get(i).getAttribute("value") + " " + getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("lastNameBoxKey")).get(i).getAttribute("value"));
					LOGGER.info("Name is entered.");
					getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailBoxKey")).get(i).sendKeys(generatedString+"SelUser" + randomString + "@hpmsqa.mailinator.com");
					listOfEmail.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailBoxKey")).get(i).getAttribute("value").toLowerCase());
					mailIdForMailinator = generatedString+"SelUser" + randomString;
					mailIdforVerification = generatedString+"SelUser" + randomString + "@hpmsqa.mailinator.com";
					LOGGER.info("Email is entered.");
//					getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("dropdownArrowRoleKey")).get(i).click();
//					String userRole = singleSelectDropDownUserPage(getAddUserManuallyInfo.get("roleDropdownListKey"), i);
//					listOfRoles.add(userRole);
//					LOGGER.info("Role is entered.");
//					clickByJavaScriptOnUserPage("dropdownClose");
					status.add("Inactive");
				}
				Collections.sort(displayName);
				Collections.sort(listOfEmail);
			} else {
				LOGGER.error("More than five Users cannot be added");
			}
			waitForElementsOfUserPage(getAddUserManuallyInfo.get("notifyUserByEmailKey"));
			clickOnElementsOfUserPage(getAddUserManuallyInfo.get("notifyUserByEmailKey"));
			clickOnElementsOfUserPage(getAddUserManuallyInfo.get("submitButtonKey"));
			LOGGER.info("Clicked on add button");
			sleeper(5000);
			String successMessage = getTextOfUserPage(getAddUserManuallyInfo.get("toastNotificationKey"));
			if (successMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "users.created.success"))) {
				flag = true;
				LOGGER.info("All the Users added successfully!! ");
			} else {
				LOGGER.error("Users did not add successfully!!");
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyAddManuallyUsers " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to validate "Go back to previous link"
	 * 
	 * @param getAddUserManuallyInfo: Details of add user manually locators.
	 * @param languageCode: language code.
	 * @param companyChangeInfo: Details of company change locators.
	 * @return
	 */
	public final boolean verifyGoBackPreviousLink(HashMap<String, String> getAddUserManuallyInfo, String languageCode, HashMap<String, String> companyChangeInfo) {
		try {
			boolean flag = false;
			clickOnElementsOfUserPage(getAddUserManuallyInfo.get("addManualButtonKey"));
			LOGGER.info("Clicked on add button");
			if (waitForElementsOfUserPage(getAddUserManuallyInfo.get("goBackPreviousLinkKey"))) {
				clickOnElementsOfUserPage(getAddUserManuallyInfo.get("goBackPreviousLinkKey"));
				if (getTextOfUserPage(companyChangeInfo.get("addUsersTextCheckKey")).equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "users.add.how_to"))) {
					flag = true;
				}
				return flag;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception occurred in verifyGoBackPreviousLink:" + e.getMessage());
			return false;
		}
	}

	/**
	 * This is a method to verify the add user manually functionality
	 * 
	 * @param getAddUserManuallyInfo: Details of add user manually locators.
	 * @param languageCode
	 * @param searchString: Search string of User email,
	 * @return
	 */
	public final boolean verifyResendInvitation(HashMap<String, String> getAddUserManuallyInfo, String languageCode, String searchString) {
		try {
			boolean flag = false;
			String successMessage = null;
			enterTextForUserPage("emailSearchBox",searchString);
			sleeper(3000);
			int rows = getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailListValuesKey")).size();
			clickByJavaScriptOnUserPage(getAddUserManuallyInfo.get("selectAllCheckBoxKey"));
			LOGGER.info("All the newly added devices are selected.");
			waitForElementsOfUserPage(getAddUserManuallyInfo.get("resendInvitationButtonKey"));
			clickByJavaScriptOnUserPage(getAddUserManuallyInfo.get("resendInvitationButtonKey"));
			LOGGER.info("Clicked on resend invitation button");
			waitForElementsOfUserPage(getAddUserManuallyInfo.get("resendModalButtonKey"));
			clickByJavaScriptOnUserPage(getAddUserManuallyInfo.get("resendModalButtonKey"));
			LOGGER.info("Clicked on Resend");
			sleeper(1000);
			if (rows == 1) {
				successMessage = getTextOfUserPage(getAddUserManuallyInfo.get("toastNotificationKey"));
				if (successMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "users.list.resend.success"))) {
					flag = true;
					LOGGER.info("Invitation was sent successfully!! ");
				}
			} else if (rows > 1) {
				successMessage = getTextOfUserPage(getAddUserManuallyInfo.get("toastNotificationKey"));
				if (successMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "users.list.resend.success_plural"))) {
					flag = true;
					LOGGER.info("Invitations were sent successfully!! ");
				}
			} else {
				LOGGER.error("Failed to send invitations!!");
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyResendInvitation " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify users added manually on list page.
	 * 
	 * @param getAddUserManuallyInfo - Locators required to test add user functionality
	 * @return
	 */
	public boolean verifyUsersOnListPage(HashMap<String, String> getAddUserManuallyInfo) {
		try {
			boolean flag = false;
			waitForPageLoaded();
			enterTextForUserPage(getAddUserManuallyInfo.get("emailSearchBoxKey"), "SelUser");
			waitForPageLoaded();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			waitForElementsOfUserPageForinvisibile("tableOverlay");
			//sleeper(5000);
			waitForElementsOfUserPage(getAddUserManuallyInfo.get("emailListValuesKey"));
			waitForPageLoaded();
			List<String> nameList = new ArrayList<String>();
			List<String> emailList = new ArrayList<String>();
//			List<String> roleList = new ArrayList<String>();
//			List<String> statusList = new ArrayList<String>();
			for (int i = 0; i < getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailListValuesKey")).size(); i++) {
				nameList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("nameListValuesKey")).get(i).getText());
				emailList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailListValuesKey")).get(i).getText());
				//scrollOnUserPage(getAddUserManuallyInfo.get("statusListValuesKey"));
				//statusList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("statusListValuesKey")).get(i).getText());
				Thread.sleep(2000);
				//scrollOnUserPage(getAddUserManuallyInfo.get("nameListValuesKey"));
			}
			Collections.sort(nameList);
			Collections.sort(emailList);
			if (nameList.equals(displayName) && emailList.equals(listOfEmail)) {
				LOGGER.info("Newly added users are displayed on list correctly.");
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyUsersOnListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * @param getAddUserManuallyInfo: Locators required to test remove Users functionality
	 * @param languageCode: language code
	 * @param searchString: Search string to remove user.
	 * @return
	 */
	public boolean verifyRemovalOfNewlyAddedUser(HashMap<String, String> getAddUserManuallyInfo, String languageCode, String searchString) {
		try {
			boolean flag = false;
			waitForPageLoaded();
			getElementOfUserPage(getAddUserManuallyInfo.get("emailSearchBoxKey")).clear();
			sleeper(3000);
			getElementOfUserPage(getAddUserManuallyInfo.get("emailSearchBoxKey")).sendKeys(searchString);
			waitForElementsOfUserPage("tableOverlay");
			waitForElementsOfUserPageForinvisibile("tableOverlay");
			if (!verifyElementsOfUserPage(getAddUserManuallyInfo.get("noElementsDisplayTextKey"))) {
				clickByJavaScriptOnUserPage(getAddUserManuallyInfo.get("selectAllCheckBoxKey"));
				List<WebElement> recordCountOnListPage = getElementsOfUserPage("recordCountOnListPage");
				LOGGER.info("All the newly added users are selected.");
				waitForElementsOfUserPage(getAddUserManuallyInfo.get("removeButtonListPageKey"));
				waitForPageLoaded();
				clickByJavaScriptOnUserPage(getAddUserManuallyInfo.get("removeButtonListPageKey"));
				LOGGER.info("Clicked on remove button");
				waitForElementsOfUserPage(getAddUserManuallyInfo.get("removeButtonModalKey"));
				clickByJavaScriptOnUserPage(getAddUserManuallyInfo.get("removeButtonModalKey"));
				LOGGER.info("Clicked on remove");
				waitForElementsOfUserPage(getAddUserManuallyInfo.get("toastNotificationKey"));
				String successMessage = getTextOfUserPage(getAddUserManuallyInfo.get("toastNotificationKey"));
				if(recordCountOnListPage.size()==1){
					if (successMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(languageCode, "daas_ui", "asset_details_user")))) {
						flag = true;
						getElementOfUserPage(getAddUserManuallyInfo.get("emailSearchBoxKey")).clear();
						LOGGER.info("Newly added user removed successfully.");
					} else {
						LOGGER.error("User removal failed.");
					}
				} else{
					if (successMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(languageCode, "daas_ui", "assets.import.users.title")))) {
						flag = true;
						getElementOfUserPage(getAddUserManuallyInfo.get("emailSearchBoxKey")).clear();
						LOGGER.info("All the newly added users are removed successfully.");
					} else {
						LOGGER.error("User removal failed.");
					}	
				}				
			} else {
				LOGGER.info("User list is already empty, There are no newly added devices");
				flag = true;
			}
			
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyRemovalOfNewlyAddedUser " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to validate the flow before the notification is generated
	 * 
	 * @return - integer value of unread notifications
	 */
	public int preNotificationCheck() {
		try {
			int countUnreadNotification = 0;
			String count = null;
			if (verifyElementsOfUserPage("notificationCount")) {
				count = getTextOfUserPage("notificationCount");
				countUnreadNotification = Integer.valueOf(count);
				waitForElementsOfUserPage("notificationBellIcon");
				clickOnElementsOfUserPage("notificationBellIcon");
				LOGGER.info("Clicked on notification bell icon");
				if (verifyElementsOfUserPage("unreadNotification")) {
					Actions action = new Actions(getDriver());
					action.moveToElement(getElementOfUserPage("unreadNotification")).build().perform();
					sleeper(5000);
					if (verifyElementsOfUserPage("notificationCount")) {
						count = getTextOfUserPage("notificationCount");
						countUnreadNotification = Integer.valueOf(count);
					}
				} else {
					LOGGER.info("First Notification is already read.");
				}
			} else {
				countUnreadNotification = 0;
			}
			LOGGER.info("Unread notification count is fetched.");
			return countUnreadNotification;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method preNotificationCheck " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * This method is used to define parameters of azure import.
	 * 
	 * @return - hashmap of locators required for azure import
	 */
	public final HashMap<String, String> getAzureImportDetails() {
		try {
			HashMap<String, String> getAzureImportInfo = new HashMap<String, String>();
			getAzureImportInfo.put("userNameAzureKey", "userNameAzure");
			getAzureImportInfo.put("passwordAzureKey", "passwordAzure");
			getAzureImportInfo.put("nextButtonAzureKey", "nextButtonAzure");
			getAzureImportInfo.put("importAzureKey", "importAzure");
			getAzureImportInfo.put("importAzureSyncKey", "importAzureSync");
			getAzureImportInfo.put("azureModalTextKey", "azureModalText");
			getAzureImportInfo.put("azureIconTextKey", "azureIconText");
			getAzureImportInfo.put("nextButtonAddUserMethodKey", "nextButtonAddUserMethod");
			return getAzureImportInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAzureImportDetails " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This method is used to navigate to Azure portal for import.
	 * 
	 * @param getAzureImportInfo: details of azure import functionality.
	 * @param numberOfUsers : number of user to get imported from Azure.
	 * @param countUnread :Count of unread notifications.
	 * @return
	 * @throws Exception
	 */
	public boolean verifyAzureImport(HashMap<String, String> getAzureImportInfo, int numberOfUsers, int countUnread) throws Exception {
		try {
			boolean flag = false;
			waitForPageLoaded();
			clickOnElementsOfUserPage(getAzureImportInfo.get("importAzureKey"));
			LOGGER.info("Clicked on Import Azure button present on Add User modal.");
			clickOnElementsOfUserPage(getAzureImportInfo.get("nextButtonAddUserMethodKey"));
			clickOnElementsOfUserPage(getAzureImportInfo.get("importAzureSyncKey"));
			LOGGER.info("Clicked on Import Azure button present on Azure Import modal.");
			waitForPageLoaded();
			getDriver().findElement(By.xpath("//*[@name='loginfmt']")).sendKeys(azureEmail);
			LOGGER.info("Email is provided for Azure.");
			waitForPageLoaded();
			sleeper(3000);
			getDriver().findElement(By.xpath("//*[@id='idSIButton9']")).click();
			waitForPageLoaded();
			sleeper(3000);
			getDriver().findElement(By.xpath("//*[@name='passwd']")).sendKeys(azurePassword);
			LOGGER.info("Password is provided for Azure.");
			getDriver().findElement(By.xpath("//*[@id='idSIButton9']")).click();
			waitForPageLoaded();
			sleeper(3000);
			getDriver().findElement(By.id("idBtn_Back")).click();
			waitForPageLoaded();
			LOGGER.info("Back to user list page.");
			flag = postNotificationCheckUser(numberOfUsers, countUnread);
			LOGGER.info("Users from Azure imported successfully.");
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyAzureImport " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method used to check notification after azure import
	 * 
	 * @param numberOfUsers: number of user to get imported from Azure.
	 * @param countUnread:Count of unread notifications.
	 * @return
	 */
	public boolean postNotificationCheckUser(int numberOfUsers, int countUnread) {
		try {
			boolean flag = false;
			String notificationText = null;
			String hyperLinkText = null;
			String notificationCountString = null;
			int notificationCount = 0;
			sleeper(10000);// sleeper needed for notification to get displayed.
			waitForElementsOfUserPage("notificationBellIcon");
			clickOnElementsOfUserPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfUserPage("unreadNotificationHyperLink")) {
				notificationText = getTextOfUserPage("unreadNotificationText");
				hyperLinkText = getTextOfUserPage("unreadNotificationHyperLink");
				notificationCountString = getTextOfUserPage("notificationCount");
				notificationCount = Integer.valueOf(notificationCountString);
				if (hyperLinkText.equalsIgnoreCase("OPEN LOGS") && notificationText.equalsIgnoreCase(UserVariables.USER_AZURE_IMPORT_SUCCESS_NOTIFICATION1 + numberOfUsers + UserVariables.USER_AZURE_IMPORT_SUCCESS_NOTIFICATION2) && notificationCount == (countUnread + 1)) {
					flag = true;
					LOGGER.info("Notification of Azure Import received.");
				} else {
					LOGGER.error("Notification for Azure Import has failed");
				}
			}
			sleeper(3000);
			clickOnElementsOfUserPage("notificationBellIcon");
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckUser " + e.getMessage()));
			return false;
		}
	}

	/**
	 * @param getAddUserManuallyInfoLocators required to test import user functionality
	 * @param Users- List of Azure users.
	 * @return
	 */
	public boolean verifyAzureUsersOnListPage(HashMap<String, String> getAddUserManuallyInfo, String Users) {
		try {
			boolean flag = false;
			waitForPageLoaded();
			getElementOfUserPage(getAddUserManuallyInfo.get("emailSearchBoxKey")).sendKeys("Staging");
			waitForPageLoaded();
			sleeper(2000);
			waitForElementsOfUserPage(getAddUserManuallyInfo.get("emailListValuesKey"));
			waitForPageLoaded();
			String[] emailArray = Users.split(",");
			List<String> emailDefined = Arrays.asList(emailArray);
			List<String> emailList = new ArrayList<String>();
			List<String> roleList = new ArrayList<String>();
			List<String> rolesDefined = new ArrayList<String>();
			List<String> statusList = new ArrayList<String>();
			List<String> statusDefined = new ArrayList<String>();
			for (int i = 0; i < getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailListValuesKey")).size(); i++) {
				emailList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailListValuesKey")).get(i).getText());
				roleList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("roleListValuesKey")).get(i).getText());
				rolesDefined.add("User");
				statusList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("statusListValuesKey")).get(i).getText());
				statusDefined.add("Active");
			}
			if (emailList.equals(emailDefined) && roleList.equals(rolesDefined) && statusList.equals(statusDefined)) {
				LOGGER.info("Users from Azure portal reflected back to list page successfully.");
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyAzureUsersOnListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to define parameters of add user manually.
	 * 
	 * @return - hashmap of locators required for adding user manually
	 */
	public final HashMap<String, String> getImportDetails() {
		try {
			HashMap<String, String> getImportInfo = new HashMap<String, String>();
			getImportInfo.put("importMulitipleButtonKey", "importMulitipleButton");
			getImportInfo.put("importModalTextKey", "importModalText");
			getImportInfo.put("uploadButtonKey", "uploadButton");
			getImportInfo.put("cancelButtonKey", "cancelButton");
			getImportInfo.put("browseButtonKey", "browseButton");
			getImportInfo.put("sampleDownloadKey", "sampleDownload");
			getImportInfo.put("nextButtonAddUserMethodKey", "nextButtonAddUserMethod");
			return getImportInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAzureImportDetails " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This method is used to validate flow of importing the users
	 * 
	 * @param getImportInfo: Details of import user functionality.
	 * @param languageCode: language code
	 * @param fileName: file name of users.
	 */
	public void verifyImportUser(HashMap<String, String> getImportInfo, String languageCode, String fileName) {
		try {
			CSVFileReader csv = new CSVFileReader();
			List<String[]> userImportData = new ArrayList<String[]>();
			File file = new File(ConstantPath.IMPORT_PATH + fileName);
			String[] header = {"Full Name (mandatory)","Email Id (mandatory)","Role"};
			userImportData.add(header);
			for(int i=0; i<3; i++){
				int randomNumber = 0;
				Random random = new Random();
				randomNumber = random.nextInt(100);
				String randomString = String.valueOf(randomNumber);
				String[] values = {"SelUser","SelUser" + randomString + "@mailinator.com","it_admin"};
				userImportData.add(values);
			}
			csv.writeMultipleDataToCSV(file,userImportData);

			waitForPageLoaded();
			waitForElementsOfUserPage(getImportInfo.get("importMulitipleButtonKey"));
			clickOnElementsOfUserPage(getImportInfo.get("importMulitipleButtonKey"));
			LOGGER.info("Clicked on import button");
			clickOnElementsOfUserPage(getImportInfo.get("nextButtonAddUserMethodKey"));
			//waitForElementsOfUserPage(getImportInfo.get("browseButtonKey"));
			sleeper(2000);
			fileImportInV3(ConstantPath.IMPORT_PATH + fileName);
			sleeper(1000);// sleeper needed for execution of Auto IT exe file execution.
			waitForElementsOfUserPage(getImportInfo.get("uploadButtonKey"));
			clickOnElementsOfUserPage(getImportInfo.get("uploadButtonKey"));
			LOGGER.info("Clicked on submit import button");
			sleeper(5000);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportUser " + e.getMessage()));
		}
	}

	/**
	 * This method is used to validate flow of importing invalid users
	 * 
	 * @param getImportInfo: Details of import user functionality.
	 * @param languageCode: language code
	 */
	public void verifyImportInvalidUser(HashMap<String, String> getImportInfo, String languageCode, String fileName) {
		try {
			waitForPageLoaded();
			waitForElementsOfUserPage(getImportInfo.get("importMulitipleButtonKey"));
			clickOnElementsOfUserPage(getImportInfo.get("importMulitipleButtonKey"));
			LOGGER.info("Clicked on import button");
			clickOnElementsOfUserPage(getImportInfo.get("nextButtonAddUserMethodKey"));
			waitForElementsOfUserPage(getImportInfo.get("browseButtonKey"));
				sleeper(2000);
				fileImportInV3(ConstantPath.IMPORT_PATH + fileName);
			sleeper(2000);
			waitForElementsOfUserPage(getImportInfo.get("uploadButtonKey"));
			clickOnElementsOfUserPage(getImportInfo.get("uploadButtonKey"));
			LOGGER.info("Clicked on submit import button");
			sleeper(5000);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportUser " + e.getMessage()));
		}
	}

	/**
	 * This method is used to validate flow of importing invalid users
	 * 
	 * @param getImportInfo: Details of import user functionality.
	 * @param languageCode: language code
	 */
	public void verifyImportInvalidUserBlankFile(HashMap<String, String> getImportInfo, String languageCode, String fileName) {
		try {
			waitForPageLoaded();
			waitForElementsOfUserPage(getImportInfo.get("importMulitipleButtonKey"));
			clickOnElementsOfUserPage(getImportInfo.get("importMulitipleButtonKey"));
			LOGGER.info("Clicked on import button");
			clickOnElementsOfUserPage(getImportInfo.get("nextButtonAddUserMethodKey"));
			waitForElementsOfUserPage(getImportInfo.get("browseButtonKey"));
			sleeper(2000);
			fileImportInV3(ConstantPath.IMPORT_PATH + fileName);	
			sleeper(2000);
			waitForElementsOfUserPage(getImportInfo.get("uploadButtonKey"));
			clickOnElementsOfUserPage(getImportInfo.get("uploadButtonKey"));
			LOGGER.info("Clicked on submit import button");
			sleeper(5000);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportInvalidUserBlankFile " + e.getMessage()));
		}
	}

	/**
	 * This method is used to validate whether the imported users are reflected on the list page
	 * 
	 * @param getAddUserManuallyInfo: Details of add user manually functionality.
	 * @param fileName: file name of users.
	 * @return
	 */
	public boolean verifyImportedUsersOnListPage(HashMap<String, String> getAddUserManuallyInfo, String fileName) throws Exception {
		try {
			boolean flag = false;
			CSVFileReader csv = new CSVFileReader();
			List<String> emailList = new ArrayList<String>();
			List<String> roleList = new ArrayList<String>();
			List<String> statusList = new ArrayList<String>();
			List<String> emailListDefined = new ArrayList<String>();
			List<String> nameListDefined = new ArrayList<String>();
			List<String> statusListDefined = new ArrayList<String>();
			List<String> roleListDefined = new ArrayList<String>();
			sleeper(3000);

			File file = new File(ConstantPath.IMPORT_PATH + fileName);
			for (String[] mapping : csv.getDataWithoutHeader(file)) {
				nameListDefined.add(mapping[0]);
				emailListDefined.add(mapping[1].toLowerCase());
			}
			enterTextForUserPage(getAddUserManuallyInfo.get("emailSearchBoxKey"), nameListDefined.get(1));
			sleeper(7000);
			for (int i = 0; i < getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailListValuesKey")).size(); i++) {
				emailList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("emailListValuesKey")).get(i).getText());
//				roleList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("roleListValuesKey")).get(i).getText());
//				roleListDefined.add("IT Admin");
//				scrollOnUserPage(getAddUserManuallyInfo.get("statusListValuesKey"));
//				statusList.add(getElementsTillAllElementsVisibleofUserPage(getAddUserManuallyInfo.get("statusListValuesKey")).get(i).getText());
//				statusListDefined.add("Inactive");
//				scrollOnUserPage(getAddUserManuallyInfo.get("emailListValuesKey"));
			}
			if (emailList.containsAll(emailListDefined)) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportedUsersOnListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to validate the notification flow after the users have been imported
	 * 
	 * @param fileName: file name of users.
	 * @param countUnread: Count of unread notifications
	 * @return
	 */
	public boolean postNotificationCheckImportForSuccessfullImport(String fileName, int countUnread) {
		try {
			boolean flag = false;
			String notificationText = null;
			String hyperLinkText = null;
			String notificationCountString = null;
			int notificationCount = 0;
			waitForElementsOfUserPage("notificationBellIcon");
			clickOnElementsOfUserPage("notificationBellIcon");
			sleeper(50000);// need to wait until notification is displayed.
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfUserPage("unreadNotificationHyperLink")) {
				notificationText = getTextOfUserPage("unreadNotificationText");
				hyperLinkText = getTextOfUserPage("openLogsHyperlink");
				notificationCountString = getTextOfUserPage("notificationCount");
				notificationCount = Integer.valueOf(notificationCountString);
				if (hyperLinkText.equalsIgnoreCase("OPEN LOGS") && notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_SUCCESS_NOTIFICATION3 + fileName + UserVariables.USER_IMPORT_SUCCESS_NOTIFICATION2) && notificationCount == (countUnread + 1)) {
					LOGGER.info("Notification text for valid import is matched successfully.");
					flag = true;
				} else {
					LOGGER.error("Notification for import has failed");
				}
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImportForSuccessfullImport " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to verify the description of importing the users which appears on logs page
	 * 
	 * @param fileName: file name of users
	 * @return
	 */
	public boolean verifyDescriptionOnLogsPage(String fileName) {
		try {
			String notificationText = null;
			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			waitForElementsOfUserPage("notificationBellIcon");
			clickByJavaScriptOnUserPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			notificationText = getTextOfUserPage("unreadNotificationText");
				mousehoverOnUserPage("unreadNotificationText");
				clickOnElementsOfUserPage("hamburgerOnNotifications");
				clickOnElementsOfUserPage("openLogsAction");
			LOGGER.info("Clicked on open logs hyperlink");
			sleeper(5000);
			//resetTableConfiguration();
			waitForPageLoaded();
				if (notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_SUCCESS_NOTIFICATION3 + fileName + UserVariables.USER_IMPORT_SUCCESS_NOTIFICATION2)) {
					logPage.clickOnElementsOfLogPage("firstCheckbox");
				}else {
					logPage.clickOnElementsOfLogPage("firstCheckboxFail");
				}
		
			LOGGER.info("Clicked on first record on logs page");
			if (notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_SUCCESS_NOTIFICATION3 + fileName + UserVariables.USER_IMPORT_SUCCESS_NOTIFICATION2)) {
				if (logPage.getTextOfLogPage("logsPageDescription").contains("users have been imported and/or updated."))
					return true;
				else {
					LOGGER.error("Description on logs page is incorrect when users are imported successfully");
					return false;
				}
			} else if (notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_FAILURE_NOTIFICATION1 + fileName + UserVariables.USER_IMPORT_FAILURE_NOTIFICATION2)) {
				if (logPage.getTextOfLogPage("logsPageDescription").contains(" could not be added during the import process of file"))
					return true;
				else {
					LOGGER.error("Description on logs page is incorrect when invalid users are imported.");
					return false;
				}
			} else if (notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_FAILURE_BLANKFILE_NOTIFICATION1 + fileName + UserVariables.USER_IMPORT_FAILURE_BLANKFILE_NOTIFICATION2)) {
				if (logPage.getTextOfLogPage("logsPageDescription").contains("There was an error during the import process"))
					return true;
				else {
					LOGGER.error("Description on logs page is incorrect when blank file of users are imported.");
					return false;
				}
			}

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyDescriptionOnLogsPage " + e.getMessage()));
			return false;
		}
		return false;
	}

	/**
	 * This method is used to validate the notification flow after the invalid users have been imported
	 * 
	 * @param fileName: file name of users
	 * @param countUnread: Count of unread notifications
	 * @return
	 */
	public boolean postNotificationCheckImportForInvalidImport(String fileName, int countUnread) {
		try {
			boolean flag = false;
			String notificationText = null;
			String hyperLinkText = null;
			String notificationCountString = null;
			int notificationCount = 0;
			waitForElementsOfUserPage("notificationBellIcon");
			clickOnElementsOfUserPage("notificationBellIcon");
			sleeper(60000);// need to wait until notification is displayed.
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfUserPage("unreadNotificationHyperLink")) {
				notificationText = getTextOfUserPage("unreadNotificationText");
				hyperLinkText = getTextOfUserPage("unreadNotificationHyperLink");
				notificationCountString = getTextOfUserPage("notificationCount");
				notificationCount = Integer.valueOf(notificationCountString);
				if (fileName.equalsIgnoreCase("userInValid.csv") || fileName.equalsIgnoreCase("userInValidBlank.csv") ) {
					if (hyperLinkText.equalsIgnoreCase("ERRORS") && notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_FAILURE_NOTIFICATION1 + fileName + UserVariables.USER_IMPORT_FAILURE_NOTIFICATION2) && notificationCount == (countUnread + 1)) {
						LOGGER.info("Notification text matched successfully for Invalid Import.");
						flag = true;
					} else {
						LOGGER.error("Notification for import has failed");
					}
				} else if (fileName.equalsIgnoreCase("userInValidBlank.csv")) {
					if (hyperLinkText.equalsIgnoreCase("OPEN LOGS") && notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_FAILURE_BLANKFILE_NOTIFICATION1 + fileName + UserVariables.USER_IMPORT_FAILURE_BLANKFILE_NOTIFICATION2) && notificationCount == (countUnread + 1)) {
						LOGGER.info("Notification text matched successfully for BLANK file Import.");
						flag = true;
					} else {
						LOGGER.error("Notification for import has failed");
					}
				}
			}
			clickOnElementsOfUserPage("notificationBellIcon");
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImportForInvalidImport " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method basically verify cancel functionality through hamburger
	 * 
	 * @param LanguageCode - language code.
	 * @return return boolean value either true or false
	 */
	public final boolean verifyCancelFunctionalityWithHamburgerSelection(String languageCode) {
		boolean flag = false;
		try {
			// This is use to verify cancel button functionality on Popup through hamburger.

			LOGGER.info("Navigated to User list page");
			String userName = getTextOfUserPage("userNameSearchList");
			// Remove on list page with hamburger
			waitForPageLoaded();
			waitForElementsOfUserPage("selectCheckbox");
			clickOnElementsOfUserPage("selectCheckbox");
			waitForElementsOfUserPage("firstRowUser");
			clickOnElementsOfUserPage("hamburgerIcon");
			clickOnElementsOfUserPage("hamburgerRemove");
			if (getTextLanguage(languageCode, "daas_ui", "users.list.confirmation_modal.delete.title").contains(getTextBy(userPageProperties.getProperty("RemovePopupMessageHeader"))) && (getTextLanguage(languageCode, "lhserver", "support_admin.users.delete_dialog.message.one").toString()).replace("%{user_name}", userName).contains(getTextBy(userPageProperties.getProperty("RemovePopupMessageConfirmation")))
					&& (getTextLanguage(languageCode, "daas_ui", "users.remove.detail_message").contains(getTextBy(userPageProperties.getProperty("RemovePopupMessage"))))) {
				sleeper(3000);
				clickByJavaScriptOnElementsOfUserPage("cancelButtonOnPopup");
				clickByJavaScriptOnElementsOfUserPage("selectAllCheckBox");
				flag = true;
			} else {
				LOGGER.error("Cancel button functionality does not work on popup !!!");
				flag = false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyCancelFunctionalityWithHamburgerSelection " + e.getMessage()));
		}
		return flag;
	}

	/**
	 * This method basically verify Details functionality through hamburger
	 * 
	 * @param LanguageCode - language code.
	 * @return return boolean value either true or false
	 */
	public final boolean verifyDetailsFunctionalityWithHamburgerSelection(String languageCode) {
		boolean flag = false;
		try {
			// This is use to verify Details functionality through hamburger.
			waitForPageLoaded();
			waitForElementsOfUserPage("selectCheckbox");
			clickOnElementsOfUserPage("selectCheckbox");
			waitForPageLoaded();
			scrollOnUserPage("userNameSearchList");
			waitForElementsOfUserPage("firstRowUser");
			mousehoverOnUserPage("firstRowUser");
			clickOnElementsOfUserPage("hamburgerIcon");
			clickOnElementsOfUserPage("hamburgerDetails");
			LOGGER.info("Redirected to user details page");
			waitForPageLoaded();
			sleeper(5000);
			flag = true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyDetailsFunctionalityWithHamburgerSelection " + e.getMessage()));
			flag = false;
		}
		return flag;
	}

	/**
	 * This method basically verify remove functionality through hamburger
	 * 
	 * @param LanguageCode - language code.
	 * @return return boolean value either true or false
	 */
	public final boolean verifyRemoveFunctionalityWithHamburgerSelection(String languageCode) {
		boolean flag = false;
		try {

			LOGGER.info("Navigated to User list page");
			String userName = getTextOfUserPage("userNameSearchList");
			// Remove on list page with hamburger
			waitForPageLoaded();
			waitForElementsOfUserPage("selectCheckbox");
			clickOnElementsOfUserPage("selectCheckbox");
			scrollOnUserPage("userNameSearchList");
			waitForElementsOfUserPage("hamburgerIcon");
			clickOnElementsOfUserPage("hamburgerIcon");
			clickOnElementsOfUserPage("hamburgerRemove");
			if (getTextLanguage(languageCode, "daas_ui", "users.list.confirmation_modal.delete.title").contains(getTextBy(userPageProperties.getProperty("RemovePopupMessageHeader"))) && (getTextLanguage(languageCode, "lhserver", "support_admin.users.delete_dialog.message.one").toString()).replace("%{user_name}", userName).contains(getTextBy(userPageProperties.getProperty("RemovePopupMessageConfirmation")))
					&& (getTextLanguage(languageCode, "daas_ui", "users.remove.detail_message").contains(getTextBy(userPageProperties.getProperty("RemovePopupMessage"))))) {
				clickOnElementsOfUserPage("removeButtonOnPopup");
			} else {
				LOGGER.error("Single remove popup message does not match !!!");
				flag = false;
			}
			// Wait for toast Notification					
			if (getTextOfUserPage("toastNotification").equals(getTextLanguage(languageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(languageCode, "daas_ui", "asset_details_user")))) {
				LOGGER.info("Single remove successfully with hamburger on User list page");
				flag = true;
			} else {
				flag = false;
				LOGGER.error("Single remove does not working with hamburger on User list page");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyRemoveFunctionalityWithHamburgerSelection " + e.getMessage()));
		}
		return flag;
	}

	/**
	 * This method basically verify and search email on user list page.
	 * 
	 * @return return boolean value either true or false
	 */
	public final boolean verifySearchEmail() {
		boolean flag = false;
		try {
			waitForPageLoaded();
			enterTextForUserPage("userEmailSearchBox", emailSearch);
			waitForElementsOfUserPageForinvisibile("tableOverlay");
			//sleeper(2000);
			if(verifyElementsOfUserPage("userEmailSearchList")){
				LOGGER.info("Email user is present on the list page");
				if (getTextOfUserPage("userEmailSearchList").equalsIgnoreCase(emailSearch)){
					flag = true;
					LOGGER.info("Expected searched email user is available on user list page");
				}
				else{
					LOGGER.info("Expected searched email user is not available on user list page");
				}
			}
			else{
				LOGGER.info("Email user is not present on the list page");
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchEmail " + e.getMessage()));
		}
		return flag;
	}

	/**
	 * This method will remove all USers
	 * 
	 * @param environment
	 * @return
	 * @throws Exception
	 */
	public final boolean removeAllUsers(String environment, String tenantID, String body, String environmentURL) {
		try {
			boolean flag = false;
			List<String> listOfIds = getAllUsers(getSearchServiceApi(environment) + ConstantURL.GET_API_USER + tenantID + ConstantURL.GET_API_USER_SECOND, body, 0, "id");
			if (listOfIds.size() > 0) {
				for (int i = 0; i < listOfIds.size(); i++) {
					int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_USER, "[{\"id\":\"" + listOfIds.get(i) + "\"}]", "POST", environment);
					if (code != CommonVariables.CODESUCCESS && code != CommonVariables.CODEDELETE) {
						flag = false;
						LOGGER.error("Delete API got failed while removing Users.");
						break;
					}
					flag = true;
				}
			} else {
				LOGGER.info("There are no Users present.");
				flag = true;
			}
			refreshPage();
			waitForPageLoaded();
			LOGGER.info("All the users got removed successfully");
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeAllUsers: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method will add User via API
	 * 
	 * @param environment
	 * @param environmentURL
	 * @return
	 */
	public final boolean addUsersApi(String environment, String environmentURL, String tenantIdCompany) {
		try {
			boolean flag = false;
			int randomNumber = 0;
			Random random = new Random();
			randomNumber = random.nextInt(1000);
			String randomString = String.valueOf(randomNumber);
			emailSearch = "SelUser" + randomString + "@hpmsqa.mailinator.com";
			String body = "{\"userRequest\":{\"resources\":[{\"userName\":\""+ emailSearch + "\",\"name\":{\"" + "familyName" +"\":\"" + "SelUserAutomationLast" + randomString + "\",\"givenName\":\"" + "SelUserAutomationFirst" + "\""+"},\"displayName\":\""+ "SelUserAutomationFirst" + " " + "SelUserAutomationLast" + randomString +"\",\"role\":\"it_admin\"}]},\"notifyUsers\":true}";
			int code = getStatusCode(environmentURL + ConstantURL.ADD_API_USER+tenantIdCompany+ConstantURL.ADD_API_BULK_CREATE, body, "POST", environment);
			if (code != CommonVariables.CODEOK && code != CommonVariables.CODEDELETE) {
				flag = false;
				LOGGER.error("Add Employee API got failed while adding Users.");
			}else{
			flag = true;
			userDetails.add(emailSearch);
			userDetails.add("SelUserAutomationFirst" + " " + "SelUserAutomationLast" + randomString);
			}
			refreshPage();
			waitForPageLoaded();
			waitForElementsOfUserPageForinvisibile("tableOverlay");
			LOGGER.info("User got added successfully via API");
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in addUsersApi: " + e.getMessage());
			return false;
		}
	}

	public ArrayList<String> getUserDetails(){
		return userDetails;	
	}
	
	/**
	 * This method is used to read api data for the users
	 * 
	 * @param api - URL from you which you want the data
	 * @param body - request body
	 * @param index - label index
	 * @param id - event name required
	 */
	public final List<String> getAllUsers(String api, String body, int index, String id) {
		try {
			List<String> listOfIds = new ArrayList<String>();
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			String expected = response.asString();
			JSONObject jsonObject, obj;
			JSONArray hitsArray;
			jsonObject = new JSONObject(expected);
			obj = (JSONObject) jsonObject.get("hits");
			hitsArray = (JSONArray) obj.get("hits");
			for (int i = 0; i < hitsArray.length(); i++) {
				JSONObject jsonObject1 = hitsArray.getJSONObject(i);
				listOfIds.add(jsonObject1.get("_id").toString());
			}
			return listOfIds;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllUsers: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method set attribute to block to be used in import
	 * 
	 * @param key - Key of the locator
	 */
	public final void setAttributeOfUser(String key) {
		setAttributeForImport(userPageProperties.getProperty(key));
	}

	/**
	 * This method lets you navigate to details page of a specific user
	 * 
	 * @param userEmail - email of the user
	 */
	public final void goToSpecificUserDetailsPage(String userEmail) {
		try {
			enterTextForUserPage("userEmailSearchBox", userEmail);
			LOGGER.info("Applied filter for given user");
			sleeper(3000);
			clickByJavaScriptOnElementsOfUserPage("firstRowUser");
			LOGGER.info("Redirected to user details page");
		} catch (Exception e) {
			LOGGER.error("Exception occured in goToSpecificUserDetailsPage: " + e.getMessage());
		}
	}

	/**
	 * This method is used to impersonate a user
	 * 
	 * @param userRole - Role of the user to be impersonated
	 * @param userEmail - Mail of the user to be impersonated
	 * @param languageCode - language code
	 */
	public final void impersonateUser(String userRole, String userEmail, String languageCode) {
		try {
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			if (userRole.equals(UserVariables.PARTNER_ADMINISTRATOR) || userRole.equals(UserVariables.SALES_SPECIALIST)) {
				gotoPartnerUsersTab();
				LOGGER.info("Redirected to partner users page");
			} else if (userRole.equals(UserVariables.MANAGED_SERVICE_PROVIDER_ADMINISTRATOR) || userRole.equals(UserVariables.SUPPORT_SPECIALIST)) {
				gotoMSPUsersTab();
				LOGGER.info("Redirected to MSP users page");
			} else {
				gotoCompaniesUsersTab();
				LOGGER.info("Redirected to company users page");
			}
			waitForPageLoaded();
			resetTableConfiguration();
			goToSpecificUserDetailsPage(userEmail);
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

		} catch (Exception e) {
			LOGGER.error("Exception occured in impersonateUser: " + e.getMessage() + ". Could not imperosnate user.");
		}

	}

	/**
	 * This is a method to wait untill an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfUserPage(String key) {
		try {
			verifyElementIsinvisibile(userPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfUserPage " + e.getMessage()));
		}
	}

	/**
	 * This method is used to hover on a particular element.
	 * 
	 * @param key
	 */
	public final void mousehoverOnUserPage(String key) {
		try {
			mouseHover(userPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnUserPage " + e.getMessage()));
		}
	}

	/**
	 * This method deletes the user created.
	 * 
	 * @param emailid - emaild of user
	 * @param languageCode -- Language code
	 * @return boolean
	 * @throws Exception
	 */

	public boolean deleteRecentUser(String emailid, String languageCode) throws Exception {
		boolean flag = false;
		try {

			waitForElementsOfUserPage("adminUserEmailSearchBox");
			enterTextForUserPage("adminUserEmailSearchBox", emailid);
			mousehoverOnUserPage("userFirstRow");
			clickOnElementsOfUserPage("userMenuList");
			clickOnElementsOfUserPage("userRemoveBtn");
			clickOnElementsOfUserPage("userConfirmRemoveBtn");

			// Wait for toast Notification
			sleeper(2000);
			String successMessage = getTextOfUserPage("toastNotification");
			if (successMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(languageCode, "daas_ui", "assets.import.users.title")))) {
				LOGGER.info("Single remove successfully with hamburger on User list page");
			} else {
				LOGGER.error("Single remove does not working with hamburger on User list page");
			}

			// Verify User is deleted
			waitForElementsOfUserPage("adminUserEmailSearchBox");
			enterTextForUserPage("adminUserEmailSearchBox", emailid);
			// wait until search text area is displayed
			sleeper(3000);
			waitForElementsOfUserPage("userSearchText");
			if (getTextOfUserPage("userSearchText").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "list.no_items"))) {
				LOGGER.info("Admin User is deleted from admin user list page");
				flag = true;
			} else {
				LOGGER.info("Admin User is not deleted from admin user list page");
				flag = false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while deleting user " + e.getMessage());
			return false;
		}

		return flag;
	}

	/**
	 * This method is used for clearing any filters applied on users list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfUsersListPage(String clearFilterKey) throws Exception {
		clearFilters(userPageProperties.getProperty(clearFilterKey));
	}

	
	/** This method will remove single non-company user
	 * @param environment - environment to add users to
	 * @param environmentURL - url of the environment
	 * @param userID - Id of the user to remove
	 * @return Boolean value return either true or false
	 * @throws Exception */

	public final boolean removeNonCompanyUser(String environment, String userID, String environmentURL) {
		try {
			boolean flag = false;
			int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_USER, "[{\"id\":\"" + userID + "\"}]", "POST", environment);
			if (code != CommonVariables.CODESUCCESS && code != CommonVariables.CODEDELETE) {
				flag = false;
				LOGGER.error("Delete API got failed while removing Users.");
			} else
				flag = true;

			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeNonCompanyUser: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used for clearing notification message
	 * @throws Exception
	 */
	public final boolean verifyNotificationDismiss() throws Exception {
		boolean flag = true;
		try {
			waitForElementsOfUserPage("notificationBellIcon");
			clickOnElementsOfUserPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			verifyElementsOfUserPage("notificationMassageTile");
			mousehoverOnUserPage("notificationMassageTile");
			clickOnElementsOfUserPage("hamburgerOnNotifications");
			if(waitForElementsOfUserPage("notificationDismissIcon")){
			clickOnElementsOfUserPage("notificationDismissIcon");
			LOGGER.info("Clicked on notification Dismiss icon");
			}else{
				LOGGER.error("Notification got stuck hence unable to clear.");
			}

			return flag;
		} catch (Exception e) {
			flag = false;
			LOGGER.error(("Exception occured in method verifyNotificationDismiss " + e.getMessage()));
			return flag;
		}
	}

	/**
	 * This method is used for verifying the rows count options available on pagination
	 * @param expectedRowCount
	 * @throws Exception
	 */
	public final boolean verifyPaginationlistTablerowsCount(ArrayList<String> expectedRowCount, String key) throws Exception {
	boolean flag = true;

	ArrayList<String> rowCount = getTextOfListOfUserPage(key);

	for(int i =0; i<rowCount.size();i++){
	if(!expectedRowCount.contains(rowCount.get(i)))
		flag = false;
	}
	return flag;
}

	/**
	 * This method is used for clearing all filters applied on users list page
	 *
	 * @throws Exception
	 */
	public final void clearAllFiltersOfUsersListPage() throws Exception {
		if (verifyElementsOfUserPage("clearAllFilterField"))
			clickOnElementsOfUserPage("clearAllFilterField");
		    waitForElementsOfUserPage("tableOverlay");
		    LOGGER.info("All filters cleared successfully.");
		    }
	
	/**
	 * This method add new LDP user
	 * 
	 * @param environment
	 * @param environmentURL
	 * @param tenantIdCompany
	 * @return
	 */
	public final boolean addLDPUsersApi(String environment, String environmentURL, String tenantIdCompany) {
		try {
			boolean flag = false;
			int randomNumber = 0;
			Random random = new Random();
			randomNumber = random.nextInt(1000);
			String randomString = String.valueOf(randomNumber);
			emailSearch = "SelUser" + randomString + "@hpmsqa.mailinator.com";
			String body = "{\"userRequest\":{\"resources\":[{\"userName\":\"" + emailSearch + "\",\"name\":{\""
					+ "familyName" + "\":\"" + "SelUserAutomationLast" + randomString + "\",\"givenName\":\""
					+ "SelUserAutomationFirst" + "\"" + "},\"displayName\":\"" + "SelUserAutomationFirst" + " "
					+ "SelUserAutomationLast" + randomString + "\",\"role\":\"ldp_admin\"}]},\"notifyUsers\":true}";
			int code = getStatusCode(
					environmentURL + ConstantURL.ADD_API_USER + tenantIdCompany + ConstantURL.ADD_API_BULK_CREATE, body,
					"POST", environment);
			if (code != CommonVariables.CODEOK && code != CommonVariables.CODEDELETE) {
				flag = false;
				LOGGER.error("Add Employee API got failed while adding Users.");
			} else {
				flag = true;
				userDetails.add(emailSearch);
				userDetails.add("SelUserAutomationFirst" + " " + "SelUserAutomationLast" + randomString);
			}
			waitForElementsOfUserPage("tableOverlay");
			refreshPage();
			waitForPageLoaded();
			waitForElementsOfUserPage("tableOverlay");
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in addUsersApi: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This method is used to validate the notification flow after the users have been imported for veneer version 3
	 * 
	 * @param fileName: file name of users.
	 * @return
	 */
	public boolean postNotificationCheckImportForSuccessfullImportInV3(String fileName) {
		try {
			boolean flag = false;
			String notificationText = null;
			
			waitForElementsOfUserPage("notificationBellIcon");
			clickOnElementsOfUserPage("notificationBellIcon");
			sleeper(46000);//
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfUserPage("unreadNotificationText")) {
				notificationText = getTextOfUserPage("unreadNotificationText");
				if (notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_SUCCESS_NOTIFICATION3 + fileName + UserVariables.USER_IMPORT_SUCCESS_NOTIFICATION2)) {
					LOGGER.info("Notification text for valid import is matched successfully.");
					flag = true;
					clickOnElementsOfUserPage("notificationBellIcon");
				} else {
					LOGGER.error("Notification for import has failed");
				}
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImportForSuccessfullImportInV3 " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This method is used to validate the notification flow after the invalid users have been imported for veneer version 3
	 * 
	 * @param fileName: file name of users
	 * @return
	 */
	public boolean postNotificationCheckImportForInvalidImportInV3(String fileName) {
		try {
			boolean flag = false;
			String notificationText = null;
			waitForElementsOfUserPage("notificationBellIcon");
			clickOnElementsOfUserPage("notificationBellIcon");
			sleeper(70000);
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfUserPage("unreadNotificationHyperLink")) {
				notificationText = getTextOfUserPage("unreadNotificationText");
				if (fileName.equalsIgnoreCase("userInValid.csv") || fileName.equalsIgnoreCase("userInValidBlank.csv") ) {
					if (notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_FAILURE_NOTIFICATION1 + fileName + UserVariables.USER_IMPORT_FAILURE_NOTIFICATION2)) {
						LOGGER.info("Notification text matched successfully for Invalid Import.");
						flag = true;
					} else {
						LOGGER.error("Notification for import has failed");
					}
				} else if (fileName.equalsIgnoreCase("userInValidBlank.csv")) {
					if (notificationText.equalsIgnoreCase(UserVariables.USER_IMPORT_FAILURE_BLANKFILE_NOTIFICATION1 + fileName + UserVariables.USER_IMPORT_FAILURE_BLANKFILE_NOTIFICATION2)) {
						LOGGER.info("Notification text matched successfully for BLANK file Import.");
						flag = true;
					} else {
						LOGGER.error("Notification for import has failed");
					}
				}
			}
			clickOnElementsOfUserPage("notificationBellIcon");
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImportForInvalidImportInV3 " + e.getMessage()));
			return false;
		}
	}

	// Method to compare contents of a list with a string
	public final boolean matchTextOfListOfUserPage(String key, String text) throws Exception {
		String value = null;
		List<WebElement> menuList = getElementsTillAllElementsPresent(userPageProperties.getProperty(key));
		for (WebElement listItem : menuList) {
			value = listItem.getText();
		}
		if (value.equalsIgnoreCase(text))
			return true;

		return false;

	}

	//Method to select a specific value from a dropdown
	public final boolean updateValueOfDropdown(String dropdownListKey, String elementText, String dropdownBox)
			throws Exception {
		return selectTextValueFromDropdown(userPageProperties.getProperty(dropdownListKey), elementText,
				userPageProperties.getProperty(dropdownBox));
	}

	/**
	 * This method is used for file imported  for veneer version 3
	 * @param fileName this is the name of file which was imported
	 * @throws Exception
	 */
	public void fileImportInV3(String fileName) throws Exception {
		sleeper(2000);
		WebElement addFile = getElementOfUserPage("browseInput");
		addFile.sendKeys(fileName);
		sleeper(3000);
	}
}