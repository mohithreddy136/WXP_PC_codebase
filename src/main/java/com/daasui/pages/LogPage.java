package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.LogsVariables;
import org.testng.Assert;

public class LogPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader logPagePropertiesReader = new ObjectReader();
	private Properties logPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private LogPage instance;

	public LogPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (LogPage.class) {
				if (instance == null) {
					instance = new LogPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public LogPage(WebDriver driver) throws IOException {
		logPageProperties = logPagePropertiesReader.getObjectRepository("LogPageV3");
	}

	/**
	 * @param language-langugae code
	 * @param localefolder -locale folder name
	 * @param key - key of text
	 * @return string
	 * @throws Exception
	 */
	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = logPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean verifyElementsOfLogPage(String key) throws Exception {
		return verifyElementIsPresent(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean waitForElementsOfLogPage(String key) throws Exception {
		return verifyElementIsVisible(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean matchTextOfLogPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(logPageProperties.getProperty(key), Text);
	}

	/**
	 * This is a method to verify if the element is enable
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifyElementIsEnableOfLogPage(String key) throws Exception {
		return verifyElementIsEnable(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify if the element is selected
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifyElementIsSelectedOfLogPage(String key) throws Exception {
		return verifyElementIsSelected(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get text of an element
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final String getTextOfLogPage(String key) throws Exception {
		return getTextBy(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get attribute of an element
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final String getAttributesOfLogPage(String key, String value) throws Exception {
		return getAttribute(logPageProperties.getProperty(key), value);
	}

	/**
	 * This is a method to to click on an element
	 * 
	 * @param key
	 * @throws Exception
	 */

	public final void clickOnElementsOfLogPage(String key) throws Exception {
		click(logPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnLogPage(String key) throws Exception {
		clickByJavaScript(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 * 
	 * @param key
	 * @throws Exception
	 */

	public final void enterTextForLogPage(String key, String Text) throws Exception {
		enterText(logPageProperties.getProperty(key), Text);
	}

	/**
	 * This is a method to verify element is clickable
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifyElementIsClickableOfLogPage(String key) throws Exception {
		return verifyElementIsClickable(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify search functionality of search filters present on Logs page
	 * 
	 * @param textKey
	 * @param emptyTextKey
	 * @param text
	 * @param listKey
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifySearchValueOnLogs(String LanguageCode, String textKey, String text, String emptyTextKey, String listKey) throws Exception {
		return verifySearchFunctionalityofColumn(LanguageCode, logPageProperties.getProperty(textKey), text, logPageProperties.getProperty(emptyTextKey), logPageProperties.getProperty(listKey));
	}

	/**
	 * This is a method to verify the filter functionality when a single options are selected from a static list of options
	 * 
	 * @param checkboxKey
	 * @return true
	 * @throws Exception
	 */
	public final boolean verifyFilterSingleSelectOnLogs(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		return verifyFilterFunctionalityForSingleSelect(LanguageCode, logPageProperties.getProperty(checkboxKey), logPageProperties.getProperty(listOfElementKey), logPageProperties.getProperty(columnListKey), logPageProperties.getProperty(emptyTextKey));
	}

	/**
	 * This is a method to get a sequence of selected columns
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final ArrayList<String> getSequenceOfSelectedColumnsOnLogPage(String key) throws Exception {
		return getTextOfList(logPageProperties.getProperty(key));
	}

	public final void selectLogTypeOfLogPage(String type) throws Exception {
		click(logPageProperties.getProperty("typeBox"));
		enterTextForLogPage("typeListSearch", type);
		selectTextValueFromDropdown(logPageProperties.getProperty("typeDropdownOptions"),type,logPageProperties.getProperty("typeBox"));

		pressKey(Keys.ESCAPE);
	}
	public final void selectLogSubTypeOfLogPage(String subType) throws Exception {
		click(logPageProperties.getProperty("subTypeBox"));
		enterTextForLogPage("subTypeListSearch", subType);
		selectTextValueFromDropdown(logPageProperties.getProperty("subtypeDropdownOptions"),subType,logPageProperties.getProperty("subTypeBox"));

		pressKey(Keys.ESCAPE);
	}
	public final void selectCompanyOfLogPage(String company) throws Exception {
		scrollOnLogPage("companyBox");
		click(logPageProperties.getProperty("companyBox"));
		waitForElementsOfLogPage(logPageProperties.getProperty("companyDropdownOptions"));
		enterTextForLogPage("companySearch", company);
		sleeper(2000);
		waitForElementsOfLogPage(logPageProperties.getProperty("companyDropdownOptions"));
		selectTextValueFromDropdown(logPageProperties.getProperty("companyDropdownOptions"),company,logPageProperties.getProperty("companyBox"));

		pressKey(Keys.ESCAPE);
	}
	/**
	 * This is a method to clear all filters on logs
	 * 
	 * @param key
	 * @throws Exception
	 */
	public final void clearAllFiltersOfLogsPage(String key) throws Exception {
		clearAllFilters(logPageProperties.getProperty(key));

	}

	/**
	 * This is a method to get button disable status
	 * 
	 * @param navigationItemPreviouskey
	 * @return true
	 * @throws Exception
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviouskey) throws Exception {
		return !getElement(logPageProperties.getProperty(navigationItemPreviouskey)).getAttribute("class").contains("disabled");
	}

	/**
	 * @param key
	 * @throws Exception
	 */
	public final void scrollOnLogPage(String key) throws Exception {
		scrollTillView(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final ArrayList<String> getTextOfListOfLogPage(String key) throws Exception {
		return getTextOfList(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get a list of all the logs present on all the pages
	 * 
	 * @param listKey - This is the key for the list of values on column
	 * @param nextPaginationLinkKey - This is the key for the next page link on pagination
	 * @param firstPageNavigationKey - This is the key for the next page link on pagination
	 * @param requiredField - This is the key for the element locator to scroll up on page
	 * @return true - This method returns a arraylist of all the logs present on all the pages
	 * @throws Exception
	 */
	public final ArrayList<String> getAllLogs(String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String requiredField) throws Exception {
		scrollOnLogPage(firstPageNavigationKey);
		clickOnElementsOfLogPage(firstPageNavigationKey);
		ArrayList<String> unsortedList = getTextOfListOfLogPage(listKey);
		while (getButtonEnabilityStatus(nextPaginationLinkKey)) {
			scrollOnLogPage(firstPageNavigationKey);
			clickOnElementsOfLogPage(nextPaginationLinkKey);
			unsortedList.addAll(getTextOfListOfLogPage(listKey));
		}
		scrollOnLogPage(requiredField);
		return unsortedList;
	}

	/**
	 * This is a method to get total records
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final int getTotalRecordCount(String key) throws Exception {
		int totalRecord = 0;
		String[] allText = getTextBy(logPageProperties.getProperty(key)).split(" |/");
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
	 * @param dropdownIdKey - Id of pagination dropdown
	 * @param dropdownOptionlistKey - This is the key for values on dropdown
	 * @return true
	 * @throws Exception
	 */
	public final int getSelectedOptionTextofPaginationLogPage(String dropdownIdKey, String dropdownOptionlistKey) throws Exception {
		click(logPageProperties.getProperty(dropdownIdKey));
		sleeper(1000);
		return getSelectedDropdownOptionOnPagination(logPageProperties.getProperty(dropdownOptionlistKey));
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
	public final boolean selectElementFromDropDownOfLogPage(String dropdownId, String key, String text) throws Exception {
		click(logPageProperties.getProperty(dropdownId));
		return selectFromDropdown(logPageProperties.getProperty(dropdownId), logPageProperties.getProperty(key), text);
	}

	/**
	 * This is a method to verify the sequence of column present inside a popup
	 * 
	 * @param key - locator of element expectedDropdownOptionList
	 * @param expectedDropdownOptionList - List of expected drop down option
	 * @return - the boolean value of whether the sequence matches or not
	 * @throws Exception
	 */
	public final boolean verifyDropdownOptionOnLogPage(String key, ArrayList<String> expectedDropdownOptionList) throws Exception {
		ArrayList<String> dropdownOptionList = new ArrayList<String>();
		List<WebElement> elements = getElementsTillAllElementsPresent(logPageProperties.getProperty(key));
		for (WebElement webElement : elements) {
			dropdownOptionList.add(webElement.getText().toLowerCase().trim().replaceAll("-|_", ""));
		}
		if (dropdownOptionList.equals(expectedDropdownOptionList)) {
			return true;
		}
		return false;
	}

	/**
	 * @param listKey - column which list want to fetch
	 * @return list
	 * @throws Exception
	 */
	public final List<String> getAllRecordListOfLogPage(String listKey) throws Exception {
		return getTextOfPresentElementListOfLogPage(listKey);
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final List<String> getTextOfPresentElementListOfLogPage(String key) throws Exception {
		return getTextOfPresentElementList(logPageProperties.getProperty(key));
	}

	/**
	 * @param locatorkey - column locator
	 * @param oFlag -order flag either true or flase
	 * @throws Exception
	 */
	public final void clickSortColumnOfLogPage(String locatorkey, boolean oFlag) throws Exception {
		clickSortIconColumn(logPageProperties.getProperty(locatorkey), oFlag);
	}

	/**
	 * This is a method to get all the elements
	 * 
	 * @param key locator of element
	 * @return List<WebElement>
	 * @throws Exception
	 */

	public final List<WebElement> getElementsOfLogPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(logPageProperties.getProperty(key));
	}

	/**
	 * This is a method to check if all the elements in list are clickable
	 * 
	 * @param uiList - List of elements of column on UI
	 * @return flag
	 * @throws Exception
	 */
	public final Boolean verifyElementsAreClickableOfLogPage(WebElement uiListElement) throws Exception {
		return verifyElementsAreClickable(uiListElement);
	}

	/**
	 * This is a method to click on 1 element in list of web elements
	 * 
	 * @param uiList - List of elements of column on UI and click on 1st element
	 * @throws Exception
	 */

	public final void clickElementsOfLogPage(List<WebElement> uiList) throws Exception {

		Iterator<WebElement> uiColumnListIterator = uiList.iterator();
		while (uiColumnListIterator.hasNext()) {
			WebElement element = uiColumnListIterator.next();
			if ((!element.getText().equals("")) && verifyElementsAreClickable(element)) {
				waitForPageLoaded();
				element.click();
				break;
			}

		}

	}

	/**
	 * This is a method to switch back to previous tab of log page
	 * 
	 */
	public final void switchbackToPreviousTabOfLogsPage() {
		switchBackToPreviousTab();
	}

	/**
	 * This is a method to switch to different tab of log page
	 * 
	 */
	public final void switchToDifferentTabOfLogsPage() {
		switchToDifferentTab();
	}

	/**
	 * This method is used for clearing any filters applied on log list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfLogsListPage(String clearFilterKey) throws Exception {
		clearFilters(logPageProperties.getProperty(clearFilterKey));
	}

	/**
	 * This is a method to wait until an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfLogPage(String key) {
		try {
			verifyElementIsinvisibile(logPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfLogPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnLogsPage(String key) {
		try {
			mouseHover(logPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnLogsPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method contains expected List of Type and respective subtypes in Keyvalue Pair 
	 *
	 * @return returns expected type and subtypes where type is key and subtypes as value 
	 */
	public final LinkedHashMap<String, ArrayList<String>> expectedSubtypeTypeValues() {
		LinkedHashMap <String, ArrayList<String>> expectedTypeSubTypePair=new LinkedHashMap<String,ArrayList<String>>();  
		 ArrayList<String> accountSubTypes = new ArrayList<String>();
		 accountSubTypes.add(LogsVariables.SUBTYPES_ADD); 
		 accountSubTypes.add(LogsVariables.SUBTYPES_BLOCKED);
		 accountSubTypes.add(LogsVariables.SUBTYPES_BOUNCE);
		 accountSubTypes.add(LogsVariables.SUBTYPES_CHANGE); 
		 accountSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 accountSubTypes.add(LogsVariables.SUBTYPES_PARTNER_ASSIGNMENT); 
		 ArrayList <String> assetSubType = new ArrayList<String>();
		 assetSubType.add(LogsVariables.SUBTYPES_ADD); 
		 assetSubType.add(LogsVariables.SUBTYPES_CHANGE); 
		 assetSubType.add(LogsVariables.SUBTYPES_DELETE); 
		 assetSubType.add(LogsVariables.SUBTYPES_IMPORT); 
		 ArrayList <String> deviceLocationSubTypes = new ArrayList<String>();
		 deviceLocationSubTypes.add(LogsVariables.SUBTYPES_UPDATE); 
		 ArrayList <String> devicesSubTypes = new ArrayList<String>();
		 devicesSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 devicesSubTypes.add(LogsVariables.SUBTYPES_AUTO_ENROLLMENT);
		 devicesSubTypes.add(LogsVariables.SUBTYPES_BIOS); 
		 devicesSubTypes.add(LogsVariables.SUBTYPES_CHANGE);
		 devicesSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 devicesSubTypes.add(LogsVariables.SUBTYPES_ENROLLMENT); 
		 devicesSubTypes.add(LogsVariables.SUBTYPES_EXPORT);
		 devicesSubTypes.add(LogsVariables.SUBTYPES_IMPORT); 
		 devicesSubTypes.add(LogsVariables.SUBTYPES_OWNERSHIP_CHANGE);
		 ArrayList <String> emmProviderSubTypes = new ArrayList<String>(); 
		 emmProviderSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 emmProviderSubTypes.add(LogsVariables.SUBTYPES_REMOVE); 
		 emmProviderSubTypes.add(LogsVariables.SUBTYPES_UPDATE);
		 ArrayList <String> hpWolfProtectAndTraceSubTypes = new ArrayList<String>();
		 hpWolfProtectAndTraceSubTypes.add(LogsVariables.SUBTYPES_CHANGE);
		 hpWolfProtectAndTraceSubTypes.add(LogsVariables.SUBTYPES_ERASE);
		 hpWolfProtectAndTraceSubTypes.add(LogsVariables.SUBTYPES_LOCK);
		 ArrayList <String> hardwareSubTypes = new ArrayList<String>(); 
		 hardwareSubTypes.add(LogsVariables.SUBTYPES_BATTERY);
		 hardwareSubTypes.add(LogsVariables.SUBTYPES_HARD_DRIVE);
		 hardwareSubTypes.add(LogsVariables.SUBTYPES_WARRANTY);
		 ArrayList <String> itmsConfigSubTypes = new ArrayList<String>(); 
		 itmsConfigSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 itmsConfigSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 itmsConfigSubTypes.add(LogsVariables.SUBTYPES_UPDATE);
		 ArrayList <String> itmsGlobalConfigSubTypes = new ArrayList<String>(); 
		 itmsGlobalConfigSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 itmsGlobalConfigSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 itmsGlobalConfigSubTypes.add(LogsVariables.SUBTYPES_ENABLED_DISABLE);
		 itmsGlobalConfigSubTypes.add(LogsVariables.SUBTYPES_UPDATE);
		 ArrayList <String> logsSubTypes = new ArrayList<String>(); 
		 logsSubTypes.add(LogsVariables.SUBTYPES_EXPORT);		 
		 ArrayList <String> rolesSubTypes = new ArrayList<String>(); 
		 rolesSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 rolesSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 rolesSubTypes.add(LogsVariables.SUBTYPES_EDIT);
		 ArrayList <String> securitySubTypes = new ArrayList<String>(); 
		 securitySubTypes.add(LogsVariables.SUBTYPES_ANTI_THEFT);
		 securitySubTypes.add(LogsVariables.SUBTYPES_ANTI_VIRUS);
		 securitySubTypes.add(LogsVariables.SUBTYPES_FIREWALL);
		 securitySubTypes.add(LogsVariables.SUBTYPES_LOCATION);
		 securitySubTypes.add(LogsVariables.SUBTYPES_POLICY);
		 securitySubTypes.add(LogsVariables.SUBTYPES_SECUREBOOT);
		 ArrayList <String> subscriptionSubTypes = new ArrayList<String>(); 
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_CHANGE);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_DOWNGRADE);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_EXPIRATION);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_EXPORT);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPE_EXTENSION);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_REACTIVATE);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_TRIAL);
		 subscriptionSubTypes.add(LogsVariables.SUBTYPES_UPGRADE);
		 ArrayList <String> supportContact = new ArrayList<String>(); 
		 supportContact.add(LogsVariables.SUBTYPES_ADD);
		 supportContact.add(LogsVariables.SUBTYPES_CHANGE);
		 supportContact.add(LogsVariables.SUBTYPES_DELETE);
		 ArrayList <String> tenantConfigSubTypes = new ArrayList<String>(); 
		 tenantConfigSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 tenantConfigSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 tenantConfigSubTypes.add(LogsVariables.SUBTYPES_UPDATE);
		 ArrayList <String> tenantsSubTypes = new ArrayList<String>(); 
		 tenantsSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 tenantsSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 tenantsSubTypes.add(LogsVariables.SUBTYPES_UPDATE);
		 ArrayList <String> usersSubTypes = new ArrayList<String>(); 
		 usersSubTypes.add(LogsVariables.SUBTYPES_ADD);
		 usersSubTypes.add(LogsVariables.SUBTYPES_CHANGE);
		 usersSubTypes.add(LogsVariables.SUBTYPES_DELETE);
		 usersSubTypes.add(LogsVariables.SUBTYPES_IMPORT);
		 usersSubTypes.add(LogsVariables.SUBTYPES_LOGIN);
		 ArrayList <String> warrantySubTypes = new ArrayList<String>(); 
		 warrantySubTypes.add(LogsVariables.SUBTYPES_WARRANTYCRITICALALERT);
		 warrantySubTypes.add(LogsVariables.SUBTYPES_WARRANTYEXPIREDALERT);
		 warrantySubTypes.add(LogsVariables.SUBTYPES_WARRANTYINFOALERT);
		 warrantySubTypes.add(LogsVariables.SUBTYPES_WARRANTYWARNINGALERT); 
		 expectedTypeSubTypePair.put("account", accountSubTypes);
		 expectedTypeSubTypePair.put("assets", assetSubType);
		 expectedTypeSubTypePair.put("device location", deviceLocationSubTypes );
		 expectedTypeSubTypePair.put("devices", devicesSubTypes);
		 expectedTypeSubTypePair.put("emm provider", emmProviderSubTypes);
		 expectedTypeSubTypePair.put("hp wolf protect and trace", hpWolfProtectAndTraceSubTypes);
		 expectedTypeSubTypePair.put("hardware", hardwareSubTypes);
		 expectedTypeSubTypePair.put("itsm config", itmsConfigSubTypes);
		 expectedTypeSubTypePair.put("itsm global config", itmsGlobalConfigSubTypes);
		 expectedTypeSubTypePair.put("logs", logsSubTypes);
		 expectedTypeSubTypePair.put("roles", rolesSubTypes);
		 expectedTypeSubTypePair.put("security", securitySubTypes);
		 expectedTypeSubTypePair.put("subscription", subscriptionSubTypes);
		 expectedTypeSubTypePair.put("support contact", supportContact);
		 expectedTypeSubTypePair.put("tenant config", tenantConfigSubTypes);
		 expectedTypeSubTypePair.put("tenants", tenantsSubTypes);
		 expectedTypeSubTypePair.put("users", usersSubTypes);
		 expectedTypeSubTypePair.put("warranty", warrantySubTypes);	
		 return expectedTypeSubTypePair;
	}

	/**
	 * This is a method contains expected List of Type and respective subtypes in Keyvalue Pair 
	 * 
	 * @param expectedSubtypeTypeValuesForPartnerDetailsUser: List of expected type and subtype values in key and value pair. 
	 * @return returns expected type and subtypes where type is key and subtypes as value 
	 * 
	 */	
	public final LinkedHashMap<String, ArrayList<String>> expectedSubtypeTypeValuesForPartnerDetailsUser(LinkedHashMap<String, ArrayList<String>> SubtypeTypeValue) {
		LinkedHashMap <String, ArrayList<String>> expectedTypeSubTypePair = SubtypeTypeValue;  
		 ArrayList<String> productCatalogSubTypes = new ArrayList<String>();
		 productCatalogSubTypes.add(LogsVariables.SUBTYPES_IMPORT);
		 productCatalogSubTypes.add(LogsVariables.SUBTYPES_RECOMMENDATIONS);
		 expectedTypeSubTypePair.put("product catalog", productCatalogSubTypes);
		 return expectedTypeSubTypePair;
	}

	/**
	 * This is the method to validate the type dropdown options on type dropdown for log page
	 * 
	 * @param expectedTypeSubType: List of expected type and subtype values in key and value pair. 
	 * @param actualTypeDropdownList: Type Drop down option locator.
	 * @return return true if actual type options on dropdown matches with expected type option defined as key of expectedTypeSubtype Hashma
	 */	
	public final boolean verifyTypeDropdownsValues(LinkedHashMap <String, ArrayList<String>> expectedTypeSubType,String actualTypeDropdownList) throws Exception {
		ArrayList<String> actualType=getTextOfList(logPageProperties.getProperty(actualTypeDropdownList));
		ArrayList<String> expectedType = new ArrayList<String>();
		for(Entry<String, ArrayList<String>> entry: expectedTypeSubType.entrySet()) {
		      expectedType.add(entry.getKey());
		      }
		if (actualType.equals(expectedType)) {
			LOGGER.info(("Correct TypeDropdown value displayed, Actual type values: "+actualType+" Expected Type values "+expectedType));
			return true;
		}else {
			LOGGER.error(("Incorrect Type dropdown values, Actual type values: "+actualType+" Expected Type values "+expectedType));
			return false;
		}

	}	      

	/**
	 * This is the method to validate the subtypes of each type present on type dropdown or log page 
	 * 
	 * @param expectedTypeSubType: List of expected type and subtype values in key and value pair. 
	 * @param actualTypeDropdownList: Type Drop down option locator.
	 * @param actualSubTypeDropdownList: Subtype Drop down option locator.
	 * @return return true if all subtype value on dropdown  matches with expected subtypes values defined on expectedTypeSubType hashmap else false
	 */	
	public final boolean verifySubTypeDropdownvaluesForEachTypeForBillingAndPartnerAdmin(LinkedHashMap <String, ArrayList<String>> expectedTypeSubType,String actualTypeDropdownList, String actualSubTypeDropdownList) throws Exception {
		return verifySubTypeDropdownvaluesForEachType(expectedTypeSubType,actualTypeDropdownList,actualSubTypeDropdownList);
	}	

	/**
	 * This is the method to validate the subtypes of each type present on type dropdown or log page 
	 * 
	 * @param expectedTypeSubType: List of expected type and subtype values in key and value pair. 
	 * @param actualTypeDropdownList: Type Drop down option locator.
	 * @param actualSubTypeDropdownList: Subtype Drop down option locator.
	 * @return return true if all subtype value on dropdown  matches with expected subtypes values defined on expectedTypeSubType hashmap else false
	 */	
	public final boolean verifySubTypeDropdownvaluesForEachType(LinkedHashMap <String, ArrayList<String>> expectedTypeSubType,String actualTypeDropdownList, String actualSubTypeDropdownList) throws Exception {
		List<WebElement> listOfActualType = getElementsTillAllElementsPresent(logPageProperties.getProperty(actualTypeDropdownList));	
		ArrayList<String> actualTexts=new ArrayList<String>();		
		for (WebElement element : listOfActualType) {
			actualTexts.add(element.getText());
		}		
		Actions action = new Actions(getDriver());
		List<Boolean> flag = new ArrayList<Boolean>();
		int i=0;
		for (String typeValue : actualTexts) {
			List<WebElement> listOfActualTypeElement = getElementsTillAllElementsPresent(logPageProperties.getProperty(actualTypeDropdownList));	
			String type=listOfActualTypeElement.get(i).getText().toLowerCase();
			WebElement ele=listOfActualTypeElement.get(i);
			action.moveToElement(ele).click().build().perform();
			sleeper(1000);
			pressKey(Keys.ESCAPE);
			clickOnElementsOfLogPage("subTypeBox");
			waitForElementsOfLogPage("dropdownCheckboxes");
			ArrayList<String> actual_SubTypes=getTextOfList(logPageProperties.getProperty(actualSubTypeDropdownList));	
			
			Boolean f = actual_SubTypes.equals(expectedTypeSubType.get(type));
			if(f==false) {
				flag.add(f);
				LOGGER.error("Subtype values for "+typeValue+ " Type doesn't match : Actual subtype - "+actual_SubTypes+" \n Expected SubType - "+expectedTypeSubType.get(type));
			}else {
				LOGGER.info("Subtype values for "+typeValue+ " Type match : Actual subtype - "+actual_SubTypes+" \n Expected SubType - "+expectedTypeSubType.get(type));
			}							
			pressKey(Keys.ESCAPE);
			sleeper(2000);
			//clearAllFiltersOfLogsPage("clearSelectionOfFilters");
			clickByJavaScriptOnLogPage("clearSelectionOfFilters");
			sleeper(1000);
			clickOnElementsOfLogPage("typeBox");		
			sleeper(1000);
			i++;
		}
		if(flag.size()>=1) {
			return false;
		}else {
			return true;
		}	
	}	
}
