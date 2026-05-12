package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.SalesVariables;

/**
 * This class implements all the methods related to sales team list page related test cases
 */
public class SalesTeamPage extends CommonMethod {

	private ObjectReader salesTeamPagePropertiesReader = new ObjectReader();
	private Properties salesTeamPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private SalesTeamPage instance;

	public SalesTeamPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SalesTeamPage.class) {
				if (instance == null) {
					instance = new SalesTeamPage(DRIVER);
				}
			}
		}
		return instance;
	}

	/**
	 * This is the method to get properties on sales team page
	 * 
	 * @param driver
	 * @throws IOException
	 */
	public SalesTeamPage(WebDriver driver) throws IOException {
		salesTeamPageProperties = salesTeamPagePropertiesReader.getObjectRepository("SalesTeamPageV3");
	}

	/**
	 * This is the method to verify if an element is present on sales team list page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is present or not
	 * @throws Exception
	 */
	public final boolean verifyElementsOfSalesTeamPage(String key) throws Exception {
		return verifyElementIsPresent(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is the method to wait for an element on sales team list page until it is visible on sales team list page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible or not
	 * @throws Exception
	 */
	public final boolean waitForElementsOfSalesTeamPage(String key) throws Exception {
		return verifyElementIsVisible(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is the method to match text present on an element with given text on sales team list page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be matched
	 * @return - boolean value of whether both the text match
	 * @throws Exception
	 */
	public final boolean matchTextOfSalesTeamPage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(salesTeamPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is the method to verify if any element is enabled on sales team list page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsEnableOfSalesTeamPage(String key) throws Exception {
		return verifyElementIsEnable(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is the method to verify if any element is selected on sales team list page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is selected or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsSelectedOfSalesTeamPage(String key) throws Exception {
		return verifyElementIsSelected(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get text of an element on sales team list page
	 * 
	 * @param key - locator of the element
	 * @return - string value of text on the element
	 * @throws Exception
	 */
	public final String getTextOfSalesTeamPage(String key) throws Exception {
		return getTextBy(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get attributes of an element on sales team list page
	 * 
	 * @param key - locator of the element
	 * @param desiredValue - desired attribute name
	 * @return - string value of the attribute
	 * @throws Exception
	 */
	public final String getAttributesOfSalesTeamPage(String key, String desiredValue) throws Exception {
		return getAttribute(salesTeamPageProperties.getProperty(key), desiredValue);
	}

	/**
	 * This is a method to click on element of sales team list page
	 * 
	 * @param key - locator of the element
	 * @throws Exception
	 */
	public final void clickOnElementsOfSalesTeamPage(String key) throws Exception {
		click(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text in a textbox present on sales team list page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be entered
	 * @throws Exception
	 */
	public final void enterTextForSalesTeamPage(String key, String textToMatch) throws Exception {
		enterText(salesTeamPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is a method to verify if an element is clickable on sales team list page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the lement is clickable or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsClickableOfSalesTeamPage(String key) throws Exception {
		return verifyElementIsClickable(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is the method to get a list of elements on sales team list page
	 * 
	 * @param key - locator of the element
	 * @return - list of web elements
	 * @throws Exception
	 */
	public final List<WebElement> getElementsOfSalesTeamPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(salesTeamPageProperties.getProperty(key));
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
		List<WebElement> listOfRoles = getElementsOfSalesTeamPage(partnerUserRolesList);
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
	 * This is a method to click on an element by javascript on sales team list page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnSalesTeamPage(String key) throws Exception {
		clickByJavaScript(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to hover mouse on an element of sales team list page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void mousehoverOnSalesTeamPage(String key) throws Exception {
		mouseHover(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to clear text from an element on sales team list page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clearTextFromSalesTeamPageTextField(String key) throws Exception {
		clearTextRefresh(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to add a single sales team member on sales team list page
	 * 
	 * @param languageCode - language code in which test case is being executed
	 * @return - boolean value of whether the member is added successfully
	 * @throws Exception
	 */
	public final boolean addSingleMember(String languageCode, String email) throws Exception {
		String notificationMessage = null;
		clickOnElementsOfSalesTeamPage("salesAddButton");
		enterTextForSalesTeamPage("firstNameAddMember", SalesVariables.FIRST_NAME);
		enterTextForSalesTeamPage("lastNameAddMember", SalesVariables.LAST_NAME);
		enterTextForSalesTeamPage("emailAddMember", email);
		enterTextForSalesTeamPage("mobileAddMember", SalesVariables.MOBILE_PHONE);
		clickOnElementsOfSalesTeamPage("addMember");
		waitForElementsOfSalesTeamPage("toastNotification");
		notificationMessage = getTextOfSalesTeamPage("toastNotification");
		if (notificationMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "user.created.success"))) {
			waitForElementsOfSalesTeamPage("tableOverlay");
			clearTextFromSalesTeamPageTextField("emailTextbox");
			LOGGER.info("Sales team member added successfully");
			return true;
		} else {
			LOGGER.error("Sales team member addition failed");
			return false;
		}
	}

	/**
	 * This is a method to remove a single sales team member
	 * 
	 * @param email - mail id of the member to be removed
	 * @param languageCode - language code in which test case is being executed
	 * @return - boolean value of whether the member is removed successfully
	 * @throws Exception
	 */
	public final boolean removeSingleMember(String email, String languageCode) throws Exception {
		enterTextForSalesTeamPage("emailTextbox", email);
		if (verifyElementsOfSalesTeamPage("noItemsDisplayText")) {
			LOGGER.info("Sales team member with this email-id does not exist");
			return true;
		} else {
			mousehoverOnSalesTeamPage("firstIdLogo");
			clickByJavaScriptOnSalesTeamPage("firstIdCheckbox");
			clickOnElementsOfSalesTeamPage("salesRemoveButton");
			clickOnElementsOfSalesTeamPage("submitRemove");
			String notificationMessage = getTextOfSalesTeamPage("toastNotification");
			if (notificationMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "global.messages.record_remove_success"))) {
				LOGGER.info("Sales team member removed successfully");
				return true;
			} else {
				LOGGER.error("Sales team member removal failed");
				return false;
			}
		}
	}

	/**
	 * This is a method to verify search functionality of search filters present on sales team page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifySearchValueOnSalesTeam(String languageCode, String textKey, String text, String emptyTextKey, String listKey) throws Exception {
		return verifySearchFunctionality(languageCode, salesTeamPageProperties.getProperty(textKey), text, salesTeamPageProperties.getProperty(emptyTextKey), salesTeamPageProperties.getProperty(listKey));
	}

	/**
	 * This is a method to scroll on sales team page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void scrollOnSalesTeamPage(String key) throws Exception {
		scrollTillView(salesTeamPageProperties.getProperty(key));
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
		return verifyFilterFunctionalityForSingleSelectFromDynamicDropdown(languageCode, salesTeamPageProperties.getProperty(textKey), text, salesTeamPageProperties.getProperty(emptyTextKey), salesTeamPageProperties.getProperty(listKey), salesTeamPageProperties.getProperty(checkboxKey), salesTeamPageProperties.getProperty(columnListKey), salesTeamPageProperties.getProperty(emptyTextColumnKey));
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
		return verifyFilterFunctionalityForSingleSelect(languageCode, salesTeamPageProperties.getProperty(checkboxKey), salesTeamPageProperties.getProperty(listOfElementKey), salesTeamPageProperties.getProperty(columnListKey), salesTeamPageProperties.getProperty(emptyTextKey));
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
	public final boolean selectElementFromDropDownOfSalesTeamPage(String dropdownId, String key, String text) throws Exception {
		click(salesTeamPageProperties.getProperty(dropdownId));
		return selectFromDropdown(salesTeamPageProperties.getProperty(dropdownId), salesTeamPageProperties.getProperty(key), text);
	}

	/**
	 * This is the method to get the enability status for link/button
	 * 
	 * @param navigationItemPreviouskey - locator for the button/link to be tested
	 * @return - boolean value of the enability status
	 * @throws Exception
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviouskey) throws Exception {
		return !getElement(salesTeamPageProperties.getProperty(navigationItemPreviouskey)).getAttribute("class").contains("disabled");

	}

	/**
	 * This is the method to get sales team member details
	 * 
	 * @return - arraylist of member details
	 * @throws Exception
	 */
	public final ArrayList<String> getSalesTeamMemberInfo() throws Exception {
		ArrayList<String> salesTeamMemberInfo = new ArrayList<String>();
		List<WebElement> detailsList = getElementsTillAllElementsPresent(salesTeamPageProperties.getProperty("allDetailsOfSalesTeamMember"));
		for (int i = 1; i < detailsList.size(); i++) {
			String value = detailsList.get(i).getText();
			salesTeamMemberInfo.add(value);
		}

		for (int i = 0; i < salesTeamMemberInfo.size(); i++) {
			if (salesTeamMemberInfo.get(i).equals("N/A")) {
				salesTeamMemberInfo.set(i, "-");
			}
		}

		return salesTeamMemberInfo;
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
		String[] allText = getTextBy(salesTeamPageProperties.getProperty(key)).split(" |/");
		for (int i = allText.length - 1; i > 0; i--) {
			if (isInt(allText[i])) {
				totalRecord = Integer.parseInt(allText[i].trim());
				break;
			}
		}
		return totalRecord;
	}

	/**
	 * This is a method to get the current pagination count on sales team list page
	 * 
	 * @param dropdownIdKey - locator of the dropdown arrow
	 * @param dropdownOptionlistKey - locator of the list of options on pagination dropdown
	 * @return - int value of current pagination count
	 * @throws Exception
	 */
	public final int getSelectedOptionTextofPaginationSalesTeamPage(String dropdownIdKey, String dropdownOptionlistKey) throws Exception {
		click(salesTeamPageProperties.getProperty(dropdownIdKey));
		return getSelectedDropdownOptionOnPagination(salesTeamPageProperties.getProperty(dropdownOptionlistKey));
	}

	/**
	 * This is a method to wait untill an element is invisible on sales team list page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void waitUntilElementIsInvisibleOfSalesTeamPage(String key) throws Exception {
		verifyElementIsinvisibile(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This is a method to click on 1 element in list of web elements
	 * 
	 * @param uiList - List of elements of column on UI and click on 1st element
	 * @throws Exception
	 */

	public final void clickElementsOfSalesTeamPage(List<WebElement> uiList) throws Exception {

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
	public final ArrayList<String> getSequenceOfSelectedColumnsOnSalesTeamPage(String key) throws Exception {
		return getTextOfList(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This method set attribute to block to be used in import
	 * 
	 * @param key - Key of the locator
	 */
	public final void setAttributeOfSalesTeam(String key) {
		setAttributeForImport(salesTeamPageProperties.getProperty(key));
	}

	/**
	 * This method is used for clearing any filters applied on sales list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfSalesListPage(String clearFilterKey) throws Exception {
		clearFilters(salesTeamPageProperties.getProperty(clearFilterKey));
	}	
	
	/** This method will remove sales team member
	 * @param environmentURL - url of the environment
	 * @param salesTeamMemberID - Id of the team member to remove
	 * @return Boolean value return either true or false
	 * @throws Exception */
	public final boolean removeSalesTeamMember(String environment, String salesTeamMemberID, String environmentURL) {
		try {
			boolean flag = false;
			int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_USER, "[{\"id\":\"" + salesTeamMemberID + "\"}]", "POST", environment);
			if (code != CommonVariables.CODESUCCESS && code != CommonVariables.CODEDELETE) {
				flag = false;
				LOGGER.error("Delete API got failed while removing team member.");
			} else
				flag = true;
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeSalesTeamMember: " + e.getMessage());
			return false;
		}
	}
	
	

}
