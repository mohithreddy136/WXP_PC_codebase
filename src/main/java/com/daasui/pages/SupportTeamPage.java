package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.SupportVariables;

/**
 * This class includes all the methods related to support team list page test cases
 */
public class SupportTeamPage extends CommonMethod {
	private ObjectReader supportTeamPagePropertiesReader = new ObjectReader();
	private Properties supportTeamPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private SupportTeamPage instance;

	public SupportTeamPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SupportTeamPage.class) {
				if (instance == null) {
					instance = new SupportTeamPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public SupportTeamPage(WebDriver driver) throws IOException {
		supportTeamPageProperties = supportTeamPagePropertiesReader.getObjectRepository("SupportTeamPageV3");
	}

	/**
	 * This method is the method to verify if an element is present on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is present
	 * @throws Exception
	 */
	public final boolean verifyElementsOfSupportTeamPage(String key) throws Exception {
		return verifyElementIsPresent(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is the method to wait for any element on the support team list page untill it is visible
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible
	 * @throws Exception
	 */
	public final boolean waitForElementsOfSupportTeamPage(String key) throws Exception {
		return verifyElementIsVisible(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to match text on an element ehich is present on support team list page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be compared
	 * @return - boolean value of whether both the texts match
	 * @throws Exception
	 */
	public final boolean matchTextOfSupportTeamPage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(supportTeamPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * THis is a method to verify if an element on support team list page is enabled
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled
	 * @throws Exception
	 */
	public final boolean verifyElementIsEnableOfSupportTeamPage(String key) throws Exception {
		return verifyElementIsEnable(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify if an element on support team list page is selected
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is selected
	 * @throws Exception
	 */
	public final boolean verifyElementIsSelectedOfSupportTeamPage(String key) throws Exception {
		return verifyElementIsSelected(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get text present on an element on support team list page
	 * 
	 * @param key - locator of the element
	 * @return - string value of the text present on the element
	 * @throws Exception
	 */
	public final String getTextOfSupportTeamPage(String key) throws Exception {
		return getTextBy(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a metod to get attribute of an element present on support team list page
	 * 
	 * @param key - locator of the element
	 * @param desiredValue - desired attribute name
	 * @return - value of the attribute as a string
	 * @throws Exception
	 */
	public final String getAttributesOfSupportTeamPage(String key, String desiredValue) throws Exception {
		return getAttribute(supportTeamPageProperties.getProperty(key), desiredValue);
	}

	/**
	 * This is a method to click on element present on support team list page
	 * 
	 * @param key - locator of the element
	 * @throws Exception
	 */
	public final void clickOnElementsOfSupportTeamPage(String key) throws Exception {
		click(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to click on an element present on support team list page using javascript
	 * 
	 * @param key - locator of the element
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnSupportTeamPage(String key) throws Exception {
		clickByJavaScript(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on a text field present on support team list page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be entered
	 * @throws Exception
	 */
	public final void enterTextForSupportTeamPage(String key, String textToMatch) throws Exception {
		enterText(supportTeamPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is a method to verify if an element on support team list page is clickable
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is clickable
	 * @throws Exception
	 */
	public final boolean verifyElementIsClickableOfSupportTeamPage(String key) throws Exception {
		return verifyElementIsClickable(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get a list of elements present on the support team list page
	 * 
	 * @param key - locator of the list of elements
	 * @return - list of webelements
	 * @throws Exception
	 */
	public final List<WebElement> getElementsOfSupportTeamPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This method will verify Roles under a partner
	 * 
	 * @param languageCode: This is language code used for multiple languages.
	 * @param partnerUserRolesList: Dropdown list of roles
	 * @param localeFolder: Localisation folder
	 * @param partnerTeamActualRoles: Roles specified under Partner
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyRolesUnderPartnerTeam(String languageCode, String partnerUserRolesList, String localeFolder, String[] partnerTeamActualRoles) throws Exception {
		boolean flag = false;
		List<WebElement> listOfRoles = getElementsOfSupportTeamPage(partnerUserRolesList);
		if (listOfRoles.size() > 0) {
			for (int listOfRolesCounter = 0; listOfRolesCounter < listOfRoles.size(); listOfRolesCounter++) {
				String role = listOfRoles.get(listOfRolesCounter).getText().trim();
				if (role.equals(getTextLanguage(languageCode, localeFolder, partnerTeamActualRoles[listOfRolesCounter]))) {
					LOGGER.info("Role " + role + " is matched");
					flag = true;
				} else {
					LOGGER.error("Role " + role + " is not matched");
					flag = false;
					break;
				}
			}
		} else {
			flag = false;
			LOGGER.error("No roles are displayed");
		}
		return flag;
	}

	/**
	 * This is a method to hover mouse on an element present on support team list page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void mousehoverOnSupportTeamPage(String key) throws Exception {
		mouseHover(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to clear text from an element present on support team list page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clearTextFromSupportTeamPageTextField(String key) throws Exception {
		clearTextRefresh(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to add a single support team member
	 * 
	 * @param languageCode - language code
	 * @return - boolean value of whether the member is added ssuccessfully
	 * @throws Exception
	 */
	public final boolean addSingleMember(String languageCode, String email) throws Exception {
		String notificationMessage = null;
		clickByJavaScriptOnSupportTeamPage("supportAddButton");
		enterTextForSupportTeamPage("firstNameAddMember", SupportVariables.FIRST_NAME);
		enterTextForSupportTeamPage("lastNameAddMember", SupportVariables.LAST_NAME);
		enterTextForSupportTeamPage("emailAddMember", email);
		enterTextForSupportTeamPage("mobileAddMember", SupportVariables.MOBILE_PHONE);
		clickOnElementsOfSupportTeamPage("addMember");
		waitForElementsOfSupportTeamPage("toastNotification");
		notificationMessage = getTextOfSupportTeamPage("toastNotification");
		if (notificationMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "user.created.success"))) {
			waitForElementsOfSupportTeamPage("tableOverlay");
			clearTextFromSupportTeamPageTextField("emailTextbox");
			LOGGER.info("Support team member added successfully");
			return true;
		} else {
			LOGGER.error("Support team member addition failed");
			return false;
		}
	}

	/** This is a method to remove a single support team member
	 * 
	 * @param email - mail id of the support team member to be removed
	 * @param languageCode - language code
	 * @return - boolean value of whether the member is removed successfully
	 * @throws Exception */
	public final boolean removeSingleMember(String email, String languageCode) throws Exception {

		waitUntilElementIsInvisibleOfSupportTeamPage("tableOverlay");
		if (waitForElementsOfSupportTeamPage("removeAllSearchFilters")) {
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			clickOnElementsOfSupportTeamPage("removeAllSearchFilters");
			sleeper(4000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			waitUntilElementIsInvisibleOfSupportTeamPage("tableOverlay");
			LOGGER.info("All the filters of Support team list page has been removed successfully.");
			sleeper(4000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
		}
		enterTextForSupportTeamPage("emailTextbox", email);
		sleeper(4000); // after entering value in search box it take time to load the search result
		if (verifyElementsOfSupportTeamPage("noItemsDisplayText")) {
			LOGGER.info("Support team member with this email-id does not exist");
			return false;
		} else {
			mousehoverOnSupportTeamPage("firstIdLogo");
			clickByJavaScriptOnSupportTeamPage("firstIdCheckbox");
			if(verifyElementsOfSupportTeamPage("btnClose")==true) {
				clickOnElementsOfSupportTeamPage("btnClose");
			}
			clickOnElementsOfSupportTeamPage("supportRemoveButton");
			clickOnElementsOfSupportTeamPage("submitRemove");
			String notificationMessage = getTextOfSupportTeamPage("toastNotification");
			if (notificationMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(languageCode, "daas_ui", "roles.employee")))) {
				getElementsOfSupportTeamPage("emailTextbox").clear();
				LOGGER.info("Support team member removed successfully");
				return true;
			} else {
				LOGGER.error("Support team member removal failed");
				return false;
			}
		}
	}

	/**
	 * This is a method to verify search functionality of search filters present on support team page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifySearchValueOnSupportTeam(String languageCode, String textKey, String text, String emptyTextKey, String listKey) throws Exception {
		return verifySearchFunctionality(languageCode, supportTeamPageProperties.getProperty(textKey), text, supportTeamPageProperties.getProperty(emptyTextKey), supportTeamPageProperties.getProperty(listKey));
	}

	/**
	 * This is a method to scroll on support team page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void scrollOnSupportTeamPage(String key) throws Exception {
		scrollTillView(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify the filter functionality when a single option is selected from a dynamically changing list of options
	 *
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message in popup
	 * @param listKey - Locator for list of items filtered
	 * @param checkboxKey - Locator of checkboxes
	 * @param columnListKey - Locator of list of all the items in column
	 * @param emptyTextColumnKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifyFilterSingleSelectDynamic(String languageCode, String textKey, String text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) throws Exception {
		return verifyFilterFunctionalityForSingleSelectFromDynamicDropdown(languageCode, supportTeamPageProperties.getProperty(textKey), text, supportTeamPageProperties.getProperty(emptyTextKey), supportTeamPageProperties.getProperty(listKey), supportTeamPageProperties.getProperty(checkboxKey), supportTeamPageProperties.getProperty(columnListKey), supportTeamPageProperties.getProperty(emptyTextColumnKey));
	}

	/**
	 * This is a method to verify the filter functionality when a single option is selected from a static list of options
	 * 
	 * @param languageCode - Language code
	 * @param checkboxKey - Locator for the checkboxes in dropdown
	 * @param listOfElementKey - Locator for list of items in dropdown
	 * @param columnListKey - Locator for list of all items in the column
	 * @param emptyTextKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifyFilterSingleSelect(String languageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		return verifyFilterFunctionalityForSingleSelect(languageCode, supportTeamPageProperties.getProperty(checkboxKey), supportTeamPageProperties.getProperty(listOfElementKey), supportTeamPageProperties.getProperty(columnListKey), supportTeamPageProperties.getProperty(emptyTextKey));
	}

	/**
	 * This is a method to select option from dropdown by dynamically fetching the xpath
	 *
	 * @param dropdownId - locator of the dropdown arrow
	 * @param key - locator of list of elements on dropdown
	 * @param text - option which is to be selected
	 * @return - boolean value of whether the desired option is selected
	 * @throws Exception
	 */
	public final boolean selectElementFromDropDownofSupportTeamPage(String dropdownId, String key, String text) throws Exception {
		click(supportTeamPageProperties.getProperty(dropdownId));
		return selectFromDropdown(supportTeamPageProperties.getProperty(dropdownId), supportTeamPageProperties.getProperty(key), text);
	}

	/**
	 * This is the method to get the enability status for link/button on support team list page
	 *
	 * @param navigationItemPreviouskey - locator for the button/link to be tested
	 * @return - boolean value of the enability status
	 * @throws Exception
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviouskey) throws Exception {
		return !getElement(supportTeamPageProperties.getProperty(navigationItemPreviouskey)).getAttribute("class").contains("disabled");

	}

	/**
	 * This is the method to get support team member details
	 * 
	 * @return - list of all details of a support team member
	 * @throws Exception
	 */
	public final ArrayList<String> getSupportTeamMemberInfo() throws Exception {
		ArrayList<String> supportTeamMemberInfo = new ArrayList<String>();
		List<WebElement> detailsList = getElementsTillAllElementsPresent(supportTeamPageProperties.getProperty("allDetailsOfSupportTeamMember"));
		for (int i = 1; i < detailsList.size(); i++) {
			String value = detailsList.get(i).getText();
			supportTeamMemberInfo.add(value);
		}

		for (int i = 0; i < supportTeamMemberInfo.size(); i++) {
			if (supportTeamMemberInfo.get(i).equals("N/A")) {
				supportTeamMemberInfo.set(i, "-");
			}
		}

		return supportTeamMemberInfo;
	}

	/**
	 * This is a method to get the count of total number of records on pagination
	 * 
	 * @param key - locator of the pagination element
	 * @return - integer vlue of total number of records
	 * @throws Exception
	 */
	public final int getTotalRecordCount(String key) throws Exception {
		int totalRecord = 0;
		String[] allText = getTextBy(supportTeamPageProperties.getProperty(key)).split(" |/");
		for (int i = allText.length - 1; i > 0; i--) {
			if (isInt(allText[i])) {
				totalRecord = Integer.parseInt(allText[i].trim());
				break;
			}
		}
		return totalRecord;
	}

	/**
	 * This is a method to get the current pagination count on support team list page
	 * 
	 * @param dropdownIdKey - locator of the dropdown arrow
	 * @param dropdownOptionlistKey - locator of the list of options on pagination dropdown
	 * @return - int value of current pagination count
	 * @throws Exception
	 */
	public final int getSelectedOptionTextofPaginationSupportTeamPage(String dropdownIdKey, String dropdownOptionlistKey) throws Exception {
		click(supportTeamPageProperties.getProperty(dropdownIdKey));
		return getSelectedDropdownOptionOnPagination(supportTeamPageProperties.getProperty(dropdownOptionlistKey));
	}

	/**
	 * This is a method to wait untill an element is invisible on support team list page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void waitUntilElementIsInvisibleOfSupportTeamPage(String key) throws Exception {
		verifyElementIsinvisibile(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get an element present on support team list page
	 * 
	 * @param key - locator of the element
	 * @return - webelement
	 * @throws Exception
	 */
	public final WebElement getElementOfSupportTeamPage(String key) throws Exception {
		return getElement(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to click on 1 element in list of web elements
	 * 
	 * @param uiList - List of elements of column on UI and click on 1st element
	 * @throws Exception
	 */

	public final void clickElementsOfSupportTeamPage(List<WebElement> uiList) throws Exception {

		Iterator<WebElement> uiColumnListIterator = uiList.iterator();
		uiColumnListIterator.next().click();
	}

	/**
	 * This is a method to get a sequence of selected columns
	 * 
	 * @param key - Locator of the list of selected columns
	 * @return - arraylist of the text present on the list of elements
	 * @throws Exception
	 */
	public final ArrayList<String> getSequenceOfSelectedColumnsOnSupportTeamPage(String key) throws Exception {
		return getTextOfList(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key - locator of element
	 * @return - arraylist of the text of all elements present in the list
	 */

	public final ArrayList<String> getTextOfListOfSupportTeamPage(String key) {
		try {
			return getTextOfList(supportTeamPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfSupportTeamPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This method set attribute to block to be used in import
	 * 
	 * @param key - Key of the locator
	 */
	public final void setAttributeOfSupportTeam(String key) {
		setAttributeForImport(supportTeamPageProperties.getProperty(key));
	}

	/**
	 * This method is used for clearing any filters applied on support team list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfSupportTeamListPage(String clearFilterKey) throws Exception {
		clearFilters(supportTeamPageProperties.getProperty(clearFilterKey));
	}
	
	/** Remove Support team user
	 * @param languageCode 
	 * @param supportTeamUserEmail- Email id of support team user
	 * @param environment
	 * @return */
	public final boolean removeSupportTeamUser(String LanguageCode, String supportTeamUserEmail, String environment) {
		boolean flag = false;
		String currentUrl, userID;
		try {
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters")) {
				companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
				companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
				LOGGER.info("All the filters of company list page has been removed successfully.");
				sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			}
			userPage.clickOnElementsOfUserPage("emailSearchBox");
			userPage.enterTextForUserPage("emailSearchBox", supportTeamUserEmail);
			sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)

			if (userPage.verifyElementsOfUserPage("firstRowUser")) {
				waitForPageLoaded();
				userPage.waitForElementsOfUserPage("selectCheckbox");
				userPage.clickOnElementsOfUserPage("firstRowUser");
				currentUrl = userPage.getUrlOfCurrentPage();
				userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
				removeUserWithAPI(environment, userID, environment);
				flag = true;
			} else {
				LOGGER.info("Support Team User is already removed.");
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeSupportTeamUser" + e.getMessage());
		}
		return flag;
	}
	
	/** This method will remove support team user using API
	 * @param environmentURL - url of the environment
	 * @param userID - Id of the user to remove
	 * @return Boolean value return either true or false
	 * @throws Exception */
	public final boolean removeUserWithAPI(String environment, String userID, String environmentURL) {
		try {
			boolean flag = false;
			int code = getStatusCode(environment + ConstantURL.DELETE_API_USER, "[{\"id\":\"" + userID + "\"}]", "POST", environment);
			if (code != CommonVariables.CODESUCCESS && code != CommonVariables.CODEDELETE) {
				flag = false;
				LOGGER.error("Delete API got failed while removing support team user.");
			} else
				LOGGER.info("Support team user is removed successfully.");
			flag = true;
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeUserWithAPI: " + e.getMessage());
			return false;
		}
	}
}
