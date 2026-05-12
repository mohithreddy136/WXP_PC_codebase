package com.daasui.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.PartnerVariables;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class includes all the methods related to partner list page test cases
 */
public class PartnerPage extends CommonMethod {
	private ObjectReader partnerPagePropertiesReader = new ObjectReader();
	private Properties partnerPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private PartnerPage instance;

	public PartnerPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (PartnerPage.class) {
				if (instance == null) {
					instance = new PartnerPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public PartnerPage(WebDriver driver) throws IOException {
		partnerPageProperties = partnerPagePropertiesReader.getObjectRepository("PartnerPageV3");
	}

	/**
	 * This method is the method to verify if an element is present on partner list page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is present
	 */
	public final boolean verifyElementsOfPartnerPage(String key) {
		try {
			return verifyElementIsPresent(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfPartnerPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to wait for any element on the partner list page untill it is visible
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible
	 */
	public final boolean waitForElementsOfPartnerPage(String key) {
		try {
			return verifyElementIsVisible(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfPartnerPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to match text on an element ehich is present on partner list page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be compared
	 * @return - boolean value of whether both the texts match
	 */
	public final boolean matchTextOfPartnerPage(String key, String textToMatch) {
		try {
			return verifyTextPresentOnElement(partnerPageProperties.getProperty(key), textToMatch);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextOfPartnerPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * THis is a method to verify if an element on partner list page is enabled
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled
	 */
	public final boolean verifyElementIsEnableOfPartnerPage(String key) {
		try {
			return verifyElementIsEnable(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfPartnerPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if an element on partner list page is selected
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is selected
	 */
	public final boolean verifyElementIsSelectedOfPartnerPage(String key) {
		try {
			return verifyElementIsSelected(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsSelectedOfPartnerPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get text present on an element on partner list page
	 * 
	 * @param key - locator of the element
	 * @return - string value of the text present on the element
	 */
	public final String getTextOfPartnerPage(String key) {
		try {
			return getTextBy(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfPartnerPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a metod to get attribute of an element present on partner list page
	 * 
	 * @param key - locator of the element
	 * @param desiredValue - desired attribute name
	 * @return - value of the attribute as a string
	 */
	public final String getAttributesOfPartnerPage(String key, String desiredValue) {
		try {
			return getAttribute(partnerPageProperties.getProperty(key), desiredValue);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfPartnerPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to click on element present on partner list page
	 * 
	 * @param key - locator of the element
	 */
	public final void clickOnElementsOfPartnerPage(String key) {
		try {
			click(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementsOfPartnerPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to enter text on a text field present on partner list page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be entered
	 */
	public final void enterTextForPartnerPage(String key, String textToMatch) {
		try {
			enterText(partnerPageProperties.getProperty(key), textToMatch);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForPartnerPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to verify if an element on partner list page is clickable
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is clickable
	 */
	public final boolean verifyElementIsClickableOfPartnerPage(String key) {
		try {
			return verifyElementIsClickable(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsClickableOfPartnerPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if an element on partner list page is clickable
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is clickable
	 */
	public final void clearTextFromPartnerPageTextField(String key) {
		try {
			clearTextRefresh(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsClickableOfPartnerPage " + e.getMessage()));
		}
	}

	/**
	 * This is the method to get the enability status for link/button on partner list page
	 *
	 * @param navigationItemPreviouskey - locator for the button/link to be tested
	 * @return - boolean value of the enability status
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviouskey) {
		try {
			return !getElement(partnerPageProperties.getProperty(navigationItemPreviouskey)).getAttribute("class").contains("disabled");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getButtonEnabilityStatus " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This is a method to get the count of total number of records on pagination
	 * 
	 * @param key - locator of the pagination element
	 * @return - integer vlue of total number of records
	 */
	public final int getTotalRecordCount(String key) {
		try {
			int totalRecord = 0;
			String[] allText = getTextBy(partnerPageProperties.getProperty(key)).split(" |/");
			for (int i = allText.length - 1; i > 0; i--) {
				if (isInt(allText[i])) {
					totalRecord = Integer.parseInt(allText[i].trim());
					break;
				}
			}
			return totalRecord;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTotalRecordCount " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * This is a method to get the current pagination count on partner list page
	 * 
	 * @param dropdownIdKey - locator of the dropdown arrow
	 * @param dropdownOptionlistKey - locator of the list of options on pagination dropdown
	 * @return - int value of current pagination count
	 */
	public final int getSelectedOptionTextofPagination(String dropdownIdKey, String dropdownOptionlistKey) {
		try {
			click(partnerPageProperties.getProperty(dropdownIdKey));
			return getSelectedDropdownOptionOnPagination(partnerPageProperties.getProperty(dropdownOptionlistKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getSelectedOptionTextofPagination " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * This is a method to select option from dropdown by dynamically fetching the xpath
	 *
	 * @param dropdownId - locator of the dropdown arrow
	 * @param key - locator of list of elements on dropdown
	 * @param text - option which is to be selected
	 * @return - boolean value of whether the desired option is selected
	 */
	public final boolean selectElementFromDropDownOfPartnerPage(String dropdownId, String key, String text) {
		try {
			click(partnerPageProperties.getProperty(dropdownId));
			LOGGER.info("Clicked on dropdown");
			return selectFromDropdown(partnerPageProperties.getProperty(dropdownId), partnerPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectElementFromDropDownOfPartnerPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify search functionality of search filters present on partner page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOnPartner(String languageCode, String textKey, String text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionality(languageCode, partnerPageProperties.getProperty(textKey), text, partnerPageProperties.getProperty(emptyTextKey), partnerPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOnPartner " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify the filter functionality when a single option is selected from a static list of options
	 * 
	 * @param languageCode - Language code
	 * @param checkboxKey - Locator for the checkboxes in dropdown
	 * @param listOfElementKey - Locator for list of items in dropdown
	 * @param columnListKey - Locator for list of all items in the column
	 * @param emptyTextKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctlyn
	 */
	public final boolean verifyFilterSingleSelect(String languageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) {
		try {
			return verifyFilterFunctionalityForSingleSelect(languageCode, partnerPageProperties.getProperty(checkboxKey), partnerPageProperties.getProperty(listOfElementKey), partnerPageProperties.getProperty(columnListKey), partnerPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterSingleSelect " + e.getMessage()));
			return false;
		}
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
	 */
	public final boolean verifySingleSelectionFilterFunctionalityFromDynamicDropdownOnPartnerPage(String languageCode, String searchTextKey, String searchText, String emptyTextKey, String listKey, String columnListKey, String emptyTextColumnKey) {
		try {
			return verifyFilterFunctionalityForSingleSelectionFromDynamicDropdown(languageCode, partnerPageProperties.getProperty(searchTextKey), searchText, partnerPageProperties.getProperty(emptyTextKey), partnerPageProperties.getProperty(listKey), partnerPageProperties.getProperty(columnListKey), partnerPageProperties.getProperty(emptyTextColumnKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySingleSelectionFilterFunctionalityFromDynamicDropdownOnPartnerPage " + e.getMessage()));
			return false;
		}
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
	 */
	public final boolean verifyMultiSelectionFilterFunctionalityFromDynamicDropdownOnPartnerPage(String languageCode, String searchTextKey, String searchText, String emptyTextKey, String listKey, String columnListKey, String emptyTextColumnKey) {
		try {
			return verifyMultiSelectionFilterFunctionalityFromDynamicDropdown(languageCode, partnerPageProperties.getProperty(searchTextKey), searchText, partnerPageProperties.getProperty(emptyTextKey), partnerPageProperties.getProperty(listKey), partnerPageProperties.getProperty(columnListKey), partnerPageProperties.getProperty(emptyTextColumnKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyMultiSelectionFilterFunctionalityFromDynamicDropdownOnPartnerPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait untill an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfPartnerPage(String key) {
		try {
			verifyElementIsinvisibile(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfPartnerPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on incident page
	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnPartnerPage(String key) {
		try {
			scrollTillView(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnPartnerPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to click on an element present on partner page using javascript
	 * 
	 * @param key - locator of the element
	 */
	public final void clickByJavaScriptOnPartnerPage(String key) {
		try {
			clickByJavaScript(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnPartnerPage " + e.getMessage()));
		}
	}

	/**
	 * This is the method to get partner details
	 * 
	 * @return - list of all details of a partner
	 */
	public final ArrayList<String> getPartnerInfo() {
		try {
			ArrayList<String> partnerInfo = new ArrayList<String>();
			List<WebElement> detailsList = getElementsTillAllElementsPresent(partnerPageProperties.getProperty("partnerAllDetails"));
				for (int i = 0; i < detailsList.size(); i++) {
					String value = detailsList.get(i).getText();
					partnerInfo.add(value);
				}
			for (int i = 0; i < partnerInfo.size(); i++) {
				if (partnerInfo.get(i).equals("N/A")) {
					partnerInfo.set(i, "-");
				}
			}

			return partnerInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnPartnerPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get a sequence of selected columns
	 * 
	 * @param key - Locator of the list of selected columns
	 * @return - arraylist of the text present on the list of elements
	 */
	public final ArrayList<String> getSequenceOfSelectedColumnsOnPartnersPage(String key) {
		try {
			return getTextOfList(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getSequenceOfSelectedColumnsOnPartnersPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is the method to verify companies managed by the partners tile
	 * 
	 * @param emptyTextKey - Locator of the no items available element
	 * @return - boolean value
	 */
	public final boolean verifyCompaniesOnCompaniesManagedByThePartnerTile(String emptyTextKey) {
		try {
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
			if (companiesPage.verifyElementsOfCompaniesPage(emptyTextKey)) {
				clickOnElementsOfPartnerPage("partnersTab");
				sleeper(2000);
				clickOnElementsOfPartnerPage("partnersListTab");
				LOGGER.info("Clicked on partners tab");
				waitForPageLoaded();
				waitForElementsOfPartnerPage("tableOverlay");
				clickByJavaScriptOnPartnerPage("firstPartnerName");
				waitForPageLoaded();
				LOGGER.info("Clicked on first partner from list");
				return companiesPage.verifyElementsOfCompaniesPage(emptyTextKey);
			} else {
				ArrayList<String> nameList = companiesPage.getSequenceOfSelectedColumnsOnCompanyPage("nameList");
				clickOnElementsOfPartnerPage("partnersTab");
				sleeper(2000);
				clickOnElementsOfPartnerPage("partnersListTab");
				LOGGER.info("Clicked on partners tab");
				waitForPageLoaded();
				waitForElementsOfPartnerPage("tableOverlay");
				clickByJavaScriptOnPartnerPage("firstPartnerName");
				LOGGER.info("Clicked on first partner from list");
				sleeper(3000);
				waitForPageLoaded();
				ArrayList<String> companiesList = partnerDetailsPage.getTextOfListOfPartnerDetailsPage("companiesList");
				if (nameList.equals(companiesList))
					return true;
				else
					return false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyCompaniesOnCompaniesManagedByThePartnerTile " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to verify companies managed by the partners tile
	 * 
	 * @param emptyTextKey - Locator of the no items available element
	 * @return - boolean value
	 */
	public final boolean verifyCompaniesOnCompaniesManagedByTheMSPTile(String emptyTextKey) {
		try {
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
			if (companiesPage.verifyElementsOfCompaniesPage(emptyTextKey)) {
				clickOnElementsOfPartnerPage("mspTab");
				sleeper(2000);
				clickOnElementsOfPartnerPage("mspListTab");
				LOGGER.info("Clicked on MSP tab");
				waitForPageLoaded();
				waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
				clickByJavaScriptOnPartnerPage("firstPartnerName");
				waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
				waitForPageLoaded();
				LOGGER.info("Clicked on first MSP from list");
				return companiesPage.verifyElementsOfCompaniesPage(emptyTextKey);
			} else {
				ArrayList<String> nameList = companiesPage.getSequenceOfSelectedColumnsOnCompanyPage("nameList");
				clickOnElementsOfPartnerPage("mspTab");
				sleeper(2000);
				clickOnElementsOfPartnerPage("mspListTab");
				LOGGER.info("Clicked on MSP tab");
				waitForPageLoaded();
				waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
				clickByJavaScriptOnPartnerPage("firstPartnerName");
				LOGGER.info("Clicked on first MSP from list");
				waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
				waitForPageLoaded();

				ArrayList<String> companiesList = partnerDetailsPage.getTextOfListOfPartnerDetailsPage("companiesList");
				if (nameList.equals(companiesList))
					return true;
				else
					return false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyCompaniesOnCompaniesManagedByTheMSPTile " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to select first option from any dropdown
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectFirstOptionFromDropdownOnPartnersPage(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(partnerPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method  " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to add a single support team member
	 * 
	 * @param email - mailid of the partner which is to be added
	 * @param languageCode - language code
	 * @return - boolean value of whether the member is added ssuccessfully
	 * @throws Exception
	 */
	public final boolean addSingleMember(String languageCode, String email , String rootemail) throws Exception {
		String notificationMessage = null, partner_id = generateRandomString(11);
		String pbmobm_manager_email = generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
		clickOnElementsOfPartnerPage("addPartnerButton");
		LOGGER.info("Clicked on add partner button");
		enterTextForPartnerPage("addPartnerID", partner_id);
		enterTextForPartnerPage("compPartnerNameSearch", PartnerVariables.NAME);
		enterTextForPartnerPage("partnerFirstNameSearchBox", PartnerVariables.FIRST_NAME);
		enterTextForPartnerPage("partnerLastNameSearchBox", PartnerVariables.LAST_NAME);
		enterTextForPartnerPage("partnerEmailSearchBox", email);
		enterTextForPartnerPage("partnerPhoneSearchBox", PartnerVariables.PHONE_NUMBER);
		clickOnElementsOfPartnerPage("countryDropdown");
		enterTextForPartnerPage("countrySearchBox", PartnerVariables.EDITPROFILE_COUNTRY);
		sleeper(2000);
		selectFirstOptionFromDropdownOnPartnersPage("countryList");
		LOGGER.info("Filled all form fields");
		if (toggleVerification(PartnerVariables.PBM_OBM_TOOGLE, rootemail)) {
			clickOnElementsOfPartnerPage("next");		
			enterTextForPartnerPage("pbmFirstNameSearchBox", PartnerVariables.PBM_FIRST_NAME);
			enterTextForPartnerPage("pbmLastNameSearchBox", PartnerVariables.PBM_LAST_NAME);
			enterTextForPartnerPage("pbmEmailSearchBox", pbmobm_manager_email);
			enterTextForPartnerPage("obmFirstNameSearchBox", PartnerVariables.OBM_FIRST_NAME);
			enterTextForPartnerPage("obmLastNameSearchBox", PartnerVariables.OBM_LAST_NAME);
			enterTextForPartnerPage("obmEmailSearchBox", pbmobm_manager_email);
			clickByJavaScriptOnPartnerPage("pbmobmCheckBox");	
		}
		clickOnElementsOfPartnerPage("save");
		LOGGER.info("Clicked on save button");
		waitForElementsOfPartnerPage("toastNotification");
		notificationMessage = getTextOfPartnerPage("toastNotification");
		if (notificationMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "partner.add.successful"))) {
			waitForElementsOfPartnerPage("tableOverlay");
			LOGGER.info("Partner admin added successfully");
			return true;
		} else {
			LOGGER.error("Partner admin addition failed");
			return false;
		}
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
	 */
	public final boolean verifyFilterMultiSelect(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) {
		try {
			return verifyFilterFunctionalityForMultiSelectForDyanmicList(LanguageCode, partnerPageProperties.getProperty(checkboxKey), partnerPageProperties.getProperty(listOfElementKey), partnerPageProperties.getProperty(columnListKey), partnerPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterMultiSelect " + e.getMessage()));
			return false;
		}
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
	public final boolean verifyFilterMultiSelectOnPartnerPage(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		return verifyFilterFunctionalityForMultiSelectForDyanmicList(LanguageCode, partnerPageProperties.getProperty(checkboxKey), partnerPageProperties.getProperty(listOfElementKey), partnerPageProperties.getProperty(columnListKey), partnerPageProperties.getProperty(emptyTextKey));
	}

	/**
	 * This method is used for clearing any filters applied on partner list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfPartnersListPage(String clearFilterKey) throws Exception {
		clearFilters(partnerPageProperties.getProperty(clearFilterKey));
	}
	
	/** This is the method to verify unauthorized status on partner detail page */
	public final boolean verifyUnauthorizedStatus() {
		boolean flag = false;
		try {
			List<WebElement> detailsList = getElementsTillAllElementsPresent(partnerPageProperties.getProperty("authorizationLegend"));
			for (int i = 0; i < detailsList.size(); i++) {
				if (detailsList.get(i).getText().equals("Active") && (detailsList.size() == 1))
					flag = true;
				LOGGER.info("Unauthorized legend is removed successfully");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyUnauthorizedStatus " + e.getMessage()));
			return flag;
		}
	}
	
	/**
	 * @param tenantID - tenant id of a partner which is to be removed
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean removePartnerUsingAPI(String tenantID) throws Exception {
		Response response = null;
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");

		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body("{}");
		response = httpRequest.delete(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + CommonVariables.COMPANYNAMEUPDATEAPI + tenantID + PartnerVariables.REMOVE_PARTNER_URL );

		if (response.getStatusCode() == CommonVariables.CODESUCCESS) {
			return true;
		} else {
			LOGGER.error("Failed to remove partner " + response.asString());
			return false;
		}
	}
	

	/**This is the method to get a list of elements present on Partner page
	 * @param locator of the element
	 * @return list of webelements
	 * @throws Exception
	 */
	public final List<WebElement> getElementsOfPartnerPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(partnerPageProperties.getProperty(key));
	}
	
	/**
	 * This method is used to verify the description of downloading Partner Report which appears on logs page.
	 * @return boolean if description matches on lo0gs page returns true else false
	 * @throws Exception
	 */
	public boolean verifyDescriptionOnLogsPage() throws Exception {
		boolean flag = true;
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(3000);
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver())
				.getInstance();
		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.waitForInvisibilityOfElementOfTableConfigurationPage("spinner");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		if (tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("resettodefault"))
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
		sleeper(500);
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		sleeper(3000);
		//tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableOverlay");
		waitForPageLoaded();
		sleeper(5000);// Table takes time to load, hence rendering buttonun-clickable.
		logPage.waitForElementsOfLogPage("firstCheckbox");
		logPage.mousehoverOnLogsPage("firstCheckbox");
		logPage.clickOnElementsOfLogPage("selectFirstCheckbox");
		logPage.waitForElementsOfLogPage("expandedLogDesc");
		logPage.verifyElementsOfLogPage("expandedLogDesc");

		if (logPage.getTextOfLogPage("logsPageDescription")
				.contains("has downloaded the billing reconciliation report for month"))
			flag = true;
		else {
			LOGGER.error("Description on logs page is incorrect when billing reconciliation report is downloaded.");
			flag = false;
		}
		return flag;

	}
	
	public final boolean verifydropdownSearchonPartnerPage(String LanguageCode, String textKey, String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey) throws Exception {
		return verifySearchFunctionalityUsingSingleSelectDropdown(LanguageCode, partnerPageProperties.getProperty(textKey), companySearchText, partnerPageProperties.getProperty(emptyTextKey), partnerPageProperties.getProperty(listKey), partnerPageProperties.getProperty(dropdownBoxKey));
	}
  
  /**
	 * This is a method to hover mouse on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void mousehoverOnPartnerPage(String key) {
		try {
			mouseHover(partnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnPartnerPage " + e.getMessage()));
		}
	}
	
	/**
	 * This method is used to validate the notification flow on Partners Page
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean postNotificationCheck() {
		try {
			boolean flag = false;
			String notificationText = null;
			//sleeper(15000); // time required for the notification message to pop on notification window
			waitForElementsOfPartnerPage("notificationBellIcon");
			clickByJavaScriptOnPartnerPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			sleeper(60000); // cannot avoid sleeper as the notification takes time to load
			if (verifyElementsOfPartnerPage("unreadNotificationText")) {
				notificationText = getTextOfPartnerPage("unreadNotificationinsideText");
				if (notificationText.equalsIgnoreCase("You have setup a new Partner account.")) {
					mousehoverOnPartnerPage("unreadNotificationText");
					clickOnElementsOfPartnerPage("hamburgerOnNotifications");
					clickOnElementsOfPartnerPage("openPartnerPage");
					flag = true;
				} else {
					LOGGER.error("Message on notification window is incorrect");
				}
			} else {
				LOGGER.error("Notification for partner setup is taking more than 30 seconds");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheck " + e.getMessage()));
			return false;
		}
	}
	
	/**
	* This method verifies email content to add space
	* @param environment -environment
	* @param inboxEmailAddress -email address of user who receives the mail
	* @param incidentID - subject of the mail
	* @param privateDomain - domain
	* @return
	* @throws Exception
	*/
	public String verifyEmailContent (String subject, String environment, String inboxEmailAddress, boolean privateDomain) throws Exception {
		String mailContent;
		Mailinator objMailinator = new Mailinator("",inboxEmailAddress.split("@")[0]);
		sleeper(5000);//required wait because script is breaking over here
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(getEnvironmentSpecificData(System.getProperty("environment"), subject), inboxEmailAddress.split("@")[0], privateDomain);
		if (objMailinatorEmail != null) {
			mailContent = objMailinatorEmail.getBody();
			return getHtmlParserMailSpaceThree(mailContent);
		} else {
			LOGGER.error("Mail not found");
			return "";
		}
	}

	/**
	* method to get html parser for email to replace Three spaces
	* @param mailContent - mailinator mail content
	* @return
	* @throws Exception
	*/
	public final String getHtmlParserMailSpaceThree(String mailContent) throws Exception {
		String actualMailContent;
		InputStream in = org.apache.commons.io.IOUtils.toInputStream(mailContent, "UTF-8");
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		HtmlParser htmlparser = new HtmlParser();
		htmlparser.parse(in, handler, metadata, pcontext);
		actualMailContent = handler.toString().replaceAll("\\s{3,}", " ").trim();
		return actualMailContent;
	}
	
	/**
	 * This is a method to verify the default value of all the checboxes present on an popup
	 * 
	 * @param key - locator of element
	 * @param checkboxValue - value of checkbox
	 * @throws Exception
	 */
	public final void verifyDefaultValueOfCheckboxOfPopup(String key,String CheckboxValue) throws Exception {
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		List<WebElement> elements = getElementsTillAllElementsPresent(partnerPageProperties.getProperty(key));

		for (WebElement webElement : elements) {
			if (webElement.isSelected()) {
				LOGGER.info("PBM/OBM Email Notification Checkbox already checked.");
			} else {
				LOGGER.info("PBM/OBM Email Notification Checkbox is not checked.");
				partnerDetailsPage.clickOnElementsOfPartnerDetailsPage(CheckboxValue);
			}
		}

	}
	
	public final boolean waitForElementsOfPartnerPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(partnerPageProperties.getProperty(key), waitTime);
	}

	/**
	 * This is a method to wait until an element is invisible return boolean
	 * 
	 * @param key - Locator of element
	 */
	public final boolean waitUntilElementInvisibleOfPartnerPage(String key) {
		return verifyElementIsinvisibile(partnerPageProperties.getProperty(key));
	}

}
