package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ProductCatalogVariables;

/**
 * This class contains methods related Product Catalog functions
 * 
 */
public class ProductCatalogPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader productCatalogPagePropertiesReader = new ObjectReader();
	private Properties productCatalogPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private ProductCatalogPage instance;

	public ProductCatalogPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (ProductCatalogPage.class) {
				if (instance == null) {
					instance = new ProductCatalogPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public ProductCatalogPage(WebDriver driver) throws IOException {
		productCatalogPageProperties = productCatalogPagePropertiesReader.getObjectRepository("ProductCatalogPageV3");
	}

	/**
	 * @param language -language code
	 * @param localeFolder - locale location
	 * @param key -key in respective locale file
	 * @return string -text
	 * @throws Exception
	 */
	public final String getTextLanguage(String language, String localeFolder, String key) throws Exception {
		selectedLanguageProperties = productCatalogPagePropertiesReader.getLanguageObjectRepository(localeFolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean verifyElementsOfProductCatalogPage(String key) throws Exception {
		return verifyElementIsPresent(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean waitForElementsOfProductCatalogPage(String key) throws Exception {
		return verifyElementIsVisible(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait Until element is visible
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean waitUntilElementsOfProductCatalogPage(String key) throws Exception {
		return waitUntillElementIsPresent(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of text element
	 * @param textToMatch - Locator of match text element
	 * @return true - Boolean value return either true or false.
	 * @throws Exception
	 */
	public final boolean matchTextOfProductCatalogPage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(productCatalogPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is a method to verify if the element is enable
	 * 
	 * @param key - Locator of enable element.
	 * @return true - Boolean value return either true or false.
	 * @throws Exception
	 */

	public final boolean verifyElementIsEnableOfProductCatalogPage(String key) throws Exception {
		return verifyElementIsEnable(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify if the element is selected
	 * 
	 * @param key - Locator of Selected element.
	 * @return true - Boolean value return either true or false
	 * @throws Exception
	 */

	public final boolean verifyElementIsSelectedOfProductCatalogPage(String key) throws Exception {
		return verifyElementIsSelected(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get text of an element
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final String getTextOfProductCatalogPage(String key) throws Exception {
		return getTextBy(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get attribute of an element
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public final String getAttributesOfProductCatalogPage(String key, String value) throws Exception {
		return getAttribute(productCatalogPageProperties.getProperty(key), value);
	}

	/**
	 * This is a method to to click on an element
	 * 
	 * @param key
	 * @throws Exception
	 */

	public final void clickOnElementsOfProductCatalogPage(String key) throws Exception {
		click(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 * 
	 * @param key
	 * @throws Exception
	 */

	public final void enterTextForProductCatalogPage(String key, String textToenter) throws Exception {
		enterText(productCatalogPageProperties.getProperty(key), textToenter);
	}

	/**
	 * This is a method to click on an element using java script
	 * 
	 * @param key - locator of element
	 * @throws Exception
	 */

	public final void clickByJavaScriptOnElementsOfProductCatalogPage(String key) throws Exception {
		clickByJavaScript(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify element is click_able
	 * 
	 * @param key - Locator of click element
	 * @return true - Boolean value return either true or false
	 * @throws Exception
	 */

	public final boolean verifyElementIsClickableOfProductCatalogPage(String key) throws Exception {
		return verifyElementIsClickable(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify search functionality of search filters present on Logs page
	 * 
	 * @param languageCode - This is used as code for multiple languages.
	 * @param textKey - Locator of text box in which text to be entered.
	 * @param text - Text which is to be searched.
	 * @param emptyTextKey - Locator for empty text message.
	 * @param listKey - Locator for list of elements.
	 * @return true - Boolean value return either true or false
	 * @throws Exception
	 */
	public final boolean verifySearchValueOnProductCatalogPage(String languageCode, String textKey, String text, String emptyTextKey, String listKey) throws Exception {
		return verifySearchFunctionality(languageCode, productCatalogPageProperties.getProperty(textKey), text, productCatalogPageProperties.getProperty(emptyTextKey), productCatalogPageProperties.getProperty(listKey));
	}

	/**
	 * This is a method to select date from calendar filter
	 * 
	 * @param date - current date
	 * @param calenderKey - locator for calendar filter
	 * @param monthKeyLeft - locator to go to months on left side
	 * @param monthKeyRight - locator to go to months on right side
	 * @param leftArrowKey - locator for left arrow key on calendar
	 * @param daysOnLeftSideKey - locator for days on left side
	 * @param daysOnRightSideKey - locator for days on right side
	 * @throws Exception
	 */
	public final void selectDateFromCalenderOnProductCatalogPage(String date, String calenderKey, String monthKeyLeft, String monthKeyRight, String leftArrowKey, String daysOnLeftSideKey, String daysOnRightSideKey) throws Exception {
		selectDateFromCalender(date, productCatalogPageProperties.getProperty(calenderKey), productCatalogPageProperties.getProperty(monthKeyLeft), productCatalogPageProperties.getProperty(monthKeyRight), productCatalogPageProperties.getProperty(leftArrowKey), productCatalogPageProperties.getProperty(daysOnLeftSideKey), productCatalogPageProperties.getProperty(daysOnRightSideKey));
	}

	/**
	 * This is a method to select last one week range on calendar
	 * 
	 * @param LanguageCode - language code
	 * @param calenderKey- locator for calendar column
	 * @param monthKeyLeft locator to go to months on left side
	 * @param monthKeyRight - locator to go to months on right side
	 * @param leftArrowKey - locator for left arrow key on calendar
	 * @param daysKeyLeft - locator for days on left side
	 * @param daysKeyRight - locator for days on right side
	 * @param emptyTextKey - locator for "no items available" message on column
	 * @param columnListKey - locator for all items filtered on column
	 * @return - boolean value of whether the dates are selected properly
	 * @throws Exception
	 */
	public final boolean selectLastOneWeekRangeOnProductCatalogPage(String LanguageCode, String calenderKey, String monthKeyLeft, String monthKeyRight, String leftArrowKey, String daysKeyLeft, String daysKeyRight, String emptyTextKey, String columnListKey) throws Exception {
		return verifyCalendarWithDateFormat(LanguageCode, productCatalogPageProperties.getProperty(calenderKey), productCatalogPageProperties.getProperty(monthKeyLeft), productCatalogPageProperties.getProperty(monthKeyRight), productCatalogPageProperties.getProperty(leftArrowKey), productCatalogPageProperties.getProperty(daysKeyLeft), productCatalogPageProperties.getProperty(daysKeyRight),
				productCatalogPageProperties.getProperty(emptyTextKey), productCatalogPageProperties.getProperty(columnListKey));
	}

	/**
	 * This is a method to verify the filter functionality when a single options are selected from a static list of options
	 * 
	 * @param checkboxKey
	 * @return true
	 * @throws Exception
	 */
	public final boolean verifyFilterSingleSelectOnProductCatalogPage(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		return verifyFilterFunctionalityForSingleSelect(LanguageCode, productCatalogPageProperties.getProperty(checkboxKey), productCatalogPageProperties.getProperty(listOfElementKey), productCatalogPageProperties.getProperty(columnListKey), productCatalogPageProperties.getProperty(emptyTextKey));
	}

	/**
	 * This is a method to verify the filter functionality when a single option is selected from a dynamically changing list of options
	 * 
	 * @param LanguageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param Text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message in popup
	 * @param listKey - Locator for list of items filtered
	 * @param checkboxKey - Locator of checkboxes
	 * @param columnListKey - Locator of list of all the items in column
	 * @param emptyTextColumnKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifyFilterForCatalogCompanyListSingleSelectDynamic(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) throws Exception {
		return verifyFilterFunctionalityForSingleSelectFromDynamicDropdown(LanguageCode, productCatalogPageProperties.getProperty(textKey), Text, productCatalogPageProperties.getProperty(emptyTextKey), productCatalogPageProperties.getProperty(listKey), productCatalogPageProperties.getProperty(checkboxKey), productCatalogPageProperties.getProperty(columnListKey),
				productCatalogPageProperties.getProperty(emptyTextColumnKey));
	}

	/**
	 * This is a method to verify the filter functionality when multiple options are selected from a dynamically changing list of options
	 * 
	 * @param LanguageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param Text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message in popup
	 * @param listKey - Locator for list of items filtered
	 * @param checkboxKey - Locator of checkboxes
	 * @param columnListKey - Locator of list of all the items in column
	 * @param emptyTextColumnKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifyFilterForCatalogCompanyListMultiSelectDynamic(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) throws Exception {
		return verifyFilterFunctionalityForMultiSelectFromDynamicDropdown(LanguageCode, productCatalogPageProperties.getProperty(textKey), Text, productCatalogPageProperties.getProperty(emptyTextKey), productCatalogPageProperties.getProperty(listKey), productCatalogPageProperties.getProperty(checkboxKey), productCatalogPageProperties.getProperty(columnListKey),
				productCatalogPageProperties.getProperty(emptyTextColumnKey));
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
	public final boolean verifyFilterMultiSelectOnProductCatalogPage(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		return verifyFilterFunctionalityForMultiSelectForDyanmicList(LanguageCode, productCatalogPageProperties.getProperty(checkboxKey), productCatalogPageProperties.getProperty(listOfElementKey), productCatalogPageProperties.getProperty(columnListKey), productCatalogPageProperties.getProperty(emptyTextKey));
	}

	/** This is a method to verify searched value for a search box present on a popup
	 * 
	 * @param LanguageCode - Language code
	 * @param textKey - Locator for searchbox
	 * @param Text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for the list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 * @throws Exception */
	public final boolean verifySearchValueOnProductCatalogCompanyListInsidePopup(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey) throws Exception {
		enterText(productCatalogPageProperties.getProperty(textKey), Text);
		boolean flag = false;
		sleeper(3000);
		String empty_text = null;
		if (verifyElementIsVisible(productCatalogPageProperties.getProperty(emptyTextKey))) {
			empty_text = getTextBy(productCatalogPageProperties.getProperty(emptyTextKey));
			if (empty_text.contains(getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").replace("{searchedValue}", Text))) {
				flag = true;
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(listKey));

			for (WebElement webElement : elements) {
				flag = false;
				if (webElement.getText().toLowerCase().contains(Text.toLowerCase())) {
					flag = true;
				} else {
					return flag;

				}
			}
		}

		clearText(productCatalogPageProperties.getProperty(textKey));

		return flag;
	}

	/**
	 * @param navigationItemPreviousKey
	 * @return
	 * @throws Exception
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviousKey) throws Exception {
		return !getElement(productCatalogPageProperties.getProperty(navigationItemPreviousKey)).getAttribute("class").contains("disabled");
	}

	/**
	 * @param key
	 * @throws Exception
	 */
	public final void scrollOnProductCatalogPage(String key) throws Exception {
		scrollTillView(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final ArrayList<String> getTextOfListOfProductCatalogPage(String key) throws Exception {
		return getTextOfList(productCatalogPageProperties.getProperty(key));
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
	public final ArrayList<String> getAllProductCatalogs(String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String requiredField) throws Exception {
		scrollOnProductCatalogPage(firstPageNavigationKey);
		clickOnElementsOfProductCatalogPage(firstPageNavigationKey);
		ArrayList<String> unsortedList = getTextOfListOfProductCatalogPage(listKey);
		while (getButtonEnabilityStatus(nextPaginationLinkKey)) {
			scrollOnProductCatalogPage(firstPageNavigationKey);
			clickOnElementsOfProductCatalogPage(nextPaginationLinkKey);
			unsortedList.addAll(getTextOfListOfProductCatalogPage(listKey));
		}
		scrollOnProductCatalogPage(requiredField);
		return unsortedList;
	}

	/**
	 * This is a method to get text of an element
	 * 
	 * @param dropdownIdKey - Id of pagination dropDown
	 * @param dropdownOptionlistKey - This is the key for values on dropDown
	 * @return true - Boolean value return either true or false
	 * @throws Exception
	 */
	public final int getSelectedOptionTextofPaginationProductCatalogPage(String dropdownIdKey, String dropdownOptionListKey) throws Exception {
		click(productCatalogPageProperties.getProperty(dropdownIdKey));
		sleeper(1000);
		return getSelectedDropdownOptionOnPagination(productCatalogPageProperties.getProperty(dropdownOptionListKey));
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
	public final boolean selectElementFromDropDownOfProductCatalogPage(String dropdownId, String key, String text) throws Exception {
		click(productCatalogPageProperties.getProperty(dropdownId));
		return selectFromDropdown(productCatalogPageProperties.getProperty(dropdownId), productCatalogPageProperties.getProperty(key), text);
	}

	/**
	 * This is a method to get total records
	 * 
	 * @param key - Locator of element pagination total count.
	 * @return true - Boolean value return either true or flase
	 * @throws Exception
	 */
	public final int getTotalRecordCount(String key) throws Exception {
		int totalRecord = 0;
		String[] allText = getTextBy(productCatalogPageProperties.getProperty(key)).split(" |/");
		for (int i = allText.length - 1; i > 0; i--) {
			if (isInt(allText[i])) {
				totalRecord = Integer.parseInt(allText[i].trim());
				break;
			}
		}
		return totalRecord;
	}

	/**
	 * This method is used to validate the notification flow after the devices have been imported
	 * 
	 * @param fileName this is the name of file which was imported
	 * @param countUnread this is total unread count of notifications
	 * @return true if the notification text matches else false
	 * @throws Exception
	 */
	public boolean postNotificationCheckImport(String fileName, int countUnread) throws Exception {
		boolean flag = false;
		String notificationText = null;
		String hyperLinkText = null;
		String notificationCountString = null;
		int notificationCount = 0;
		waitForElementsOfProductCatalogPage("tableOverlay");
		waitForElementsOfProductCatalogPage("notificationBellIcon");
		clickOnElementsOfProductCatalogPage("notificationBellIcon");
		verifyElementsOfProductCatalogPage("unreadNotificationHyperLink");
		if (waitForElementsOfProductCatalogPage("unreadNotificationHyperLink")) {
			sleeper(3000);
			notificationText = getTextOfProductCatalogPage("unreadNotificationText");
			hyperLinkText = getTextOfProductCatalogPage("unreadNotificationHyperLink");
			notificationCountString = getTextOfProductCatalogPage("notificationCount");
			notificationCount = Integer.valueOf(notificationCountString);
			if (notificationText.contains("successfully")) {
				if (hyperLinkText.equalsIgnoreCase("OPEN LOGS") && notificationText.equalsIgnoreCase("Your 15 products were imported successfully. Please check the logs for more details.") && notificationCount == (countUnread + 1)) {
					flag = true;
				} else {
					LOGGER.error("Notification for import has failed");
				}

			} else if (notificationText.contains("problem while importing")) {
				if (hyperLinkText.equalsIgnoreCase("OPEN LOGS") && notificationText.equalsIgnoreCase("There was a problem while importing your products from catalogInvalid.csv. Please check the logs for more information") && notificationCount == (countUnread + 1)) {
					flag = true;
				} else {
					LOGGER.error("Notification for import has failed");
				}

			}

		}
		return flag;

	}

	/**
	 * This is a method to get element
	 * 
	 * @param key - Locator of element
	 * @return - web element that contains elements
	 * @throws Exception
	 */
	public final WebElement getElementOfProductCatalogPage(String key) throws Exception {
		return getElement(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This method is used to validate the flow and get the notification count before the notification is generated
	 * 
	 * @return int unread notification count
	 * @throws Exception
	 */
	public int preNotificationCheck() throws Exception {
		int countUnreadNotification = 0;
		String count = null;

		if (verifyElementsOfProductCatalogPage("notificationCount")) {
			count = getTextOfProductCatalogPage("notificationCount");
			countUnreadNotification = Integer.valueOf(count);
			waitForElementsOfProductCatalogPage("notificationBellIcon");
			clickOnElementsOfProductCatalogPage("notificationBellIcon");
			if (verifyElementsOfProductCatalogPage("unreadNotification")) {
				Actions action = new Actions(getDriver());
				action.moveToElement(getElementOfProductCatalogPage("unreadNotification")).build().perform();
				sleeper(5000);
				if (verifyElementsOfProductCatalogPage("notificationCount")) {
					count = getTextOfProductCatalogPage("notificationCount");
					countUnreadNotification = Integer.valueOf(count);
				}
			} else {
				LOGGER.info("First Notification is already read.");
			}
		} else {
			countUnreadNotification = 0;
		}
		return countUnreadNotification;
	}

	/**
	 * This method is used to verify the description of importing the devices which appears on logs page
	 * 
	 * @param fileName name of file that was imported
	 * @return boolean if description matches on lo0gs page returns true else false
	 * @throws Exception
	 */
	public boolean verifyDescriptionOnLogsPage() throws Exception {
		String notificationText = null;
		boolean flag = true;
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		waitForElementsOfProductCatalogPage("notificationBellIcon");
		notificationText = getTextOfProductCatalogPage("unreadNotificationText");
			mouseHoverOnProductCatalogListPage("unreadNotificationText");
			clickOnElementsOfProductCatalogPage("hamburgerOnNotifications");
			waitForElementsOfProductCatalogPage("openLogsAction");
			clickOnElementsOfProductCatalogPage("openLogsAction");
		sleeper(3000);
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.waitForInvisibilityOfElementOfTableConfigurationPage("spinner");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		if(tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("resettodefault"))
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
			sleeper(5000);//Table takes time to load, hence rendering button un-clickable.
			logPage.waitForElementsOfLogPage("firstCheckbox");
			logPage.mousehoverOnLogsPage("firstCheckbox");
			logPage.clickOnElementsOfLogPage("selectFirstCheckbox");
			logPage.waitForElementsOfLogPage("expandedLogDesc");
			logPage.verifyElementsOfLogPage("expandedLogDesc");
			notificationText = getTextOfProductCatalogPage("expandedLogDesc");
		if (notificationText.equalsIgnoreCase("There was a problem while importing your products from catalogInvalid.csv. Please check the logs for more information")) {
			if (logPage.getTextOfLogPage("logsPageDescription").contains("10 out of 15 products imported. Few products failed to import due to:"))
				flag = true;
			else {
				LOGGER.error("Description on logs page is incorrect when products are imported successfully");
				flag = false;
			}
		} else if (notificationText.equalsIgnoreCase("Your 15 products were imported successfully. Please check the logs for more details.")) {
			if (logPage.getTextOfLogPage("logsPageDescription").equalsIgnoreCase("Your 15 products were imported successfully."))
				flag = true;
			else {
				LOGGER.error("Description on logs page is incorrect when devices are imported successfully");
				flag = false;
			}
		}
		return flag;

	}

	/**
	 * This is the method to verify filter functionality when dynamic multiple filters are selected. It selects the first and last filter check box from the drop down. It has
	 * functionality same as the method verifyFilterFunctionalityForSingleSelect only difference being for dynamically changing multiple filters
	 * 
	 * @param LanguageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param Text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message in popup
	 * @param listKey - Locator for list of items filtered
	 * @param checkboxKey - Locator of checkboxes
	 * @param columnListKey - Locator of list of all the items in column
	 * @param emptyTextColumnKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public boolean verifyProductCatalogCompanyFilterFunctionalityForMultiSelect(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) throws Exception {
		enterText(productCatalogPageProperties.getProperty(textKey), Text);
		boolean flag = false;
		sleeper(4000);
		String empty_text = null, text1 = null, text2 = null;
		if (waitUntillElementIsPresent(productCatalogPageProperties.getProperty(emptyTextKey))) {
			empty_text = getTextBy(productCatalogPageProperties.getProperty(emptyTextKey));
			if (empty_text.contains(getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").replace("{searchedValue}", Text))) {
				flag = true;
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(listKey));
			for (WebElement webElement : elements) {
				flag = false;
				if ((webElement.getText().toLowerCase().contains(Text.toLowerCase()))) {
					flag = true;
				} else {
					flag = false;
					LOGGER.info("Give string content does not match at company field");
					return flag;
				}
			}

			List<WebElement> listOfitems = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(checkboxKey));
			if (listOfitems.size() > 1) {
				listOfitems.get(0).click();
				sleeper(5000);
				List<WebElement> listOfitems1 = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(checkboxKey));
				listOfitems1.get(listOfitems1.size() - 1).click();
				sleeper(5000); // Will be adding page refresh code
				List<WebElement> listOfitems2 = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(listKey));
				text1 = listOfitems2.get(0).getText();
				List<WebElement> listOfitems3 = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(listKey));
				text2 = listOfitems3.get(listOfitems3.size() - 1).getText();
				pressKey(Keys.ESCAPE);

				if (waitUntillElementIsPresent(productCatalogPageProperties.getProperty(emptyTextColumnKey))) {
					empty_text = getTextBy(productCatalogPageProperties.getProperty(emptyTextColumnKey));
					if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
						flag = true;
					}
				} else {
					List<WebElement> columnlist = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(columnListKey));
					for (WebElement webElement : columnlist) {
						flag = false;
						if (webElement.getText().contains(text1) || webElement.getText().contains(text2)) {
							flag = true;
						}
					}
				}
			} else {
				LOGGER.info("Unable to proceed with this dropdown, since this dropdown contains only single elements");
				return true;
			}
		}
		return flag;
	}

	/**
	 * This is a method to verify the filter functionality when a single option is selected from a dynamically changing list of options
	 * 
	 * @param LanguageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param Text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message in popup
	 * @param listKey - Locator for list of items filtered
	 * @param checkboxKey - Locator of checkboxes
	 * @param columnListKey - Locator of list of all the items in column
	 * @param emptyTextColumnKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public boolean verifyProductCatalogCompanyFilterFunctionalityForSingleSelect(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) throws Exception {
		enterText(productCatalogPageProperties.getProperty(textKey), Text);
		boolean flag = false;
		sleeper(3000);
		String empty_text = null, text = null;
		if (waitUntillElementIsPresent(productCatalogPageProperties.getProperty(emptyTextKey))) {
			empty_text = getTextBy(productCatalogPageProperties.getProperty(emptyTextKey));
			if (empty_text.contains(getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").replace("{searchedValue}", Text))) {
				flag = true;
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(listKey));
			List<WebElement> listOfCheckbox = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(checkboxKey));
			for (WebElement webElement : elements) {
				flag = false;
				if (webElement.getText().toLowerCase().contains(Text.toLowerCase())) {
					flag = true;
				} else {
					flag = false;
					LOGGER.info("Give string content does not match at company field");
					return flag;
				}
			}
			if (!listOfCheckbox.get(0).isSelected()) {
				text = elements.get(0).getText();
				listOfCheckbox.get(0).click();
				pressKey(Keys.ESCAPE);
			}
			sleeper(2000);
			if (waitUntillElementIsPresent(productCatalogPageProperties.getProperty(emptyTextColumnKey))) {
				empty_text = getTextBy(productCatalogPageProperties.getProperty(emptyTextColumnKey));
				if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
					flag = true;
				}
			} else {
				List<WebElement> columnList = getElementsTillAllElementsVisible(productCatalogPageProperties.getProperty(columnListKey));
				for (WebElement webElement : columnList) {
					if (webElement.getText().contains(text)) {
						flag = true;
					} else {
						flag = false;
						return flag;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * This is a method to get all the elements
	 * 
	 * @param key locator of element
	 * @return List<WebElement>
	 * @throws Exception
	 */

	public final List<WebElement> getElementsOfProductCatalogPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to navigate to catalog details page
	 * 
	 * @param catalogList - List of all available catalogs
	 * @return - boolean true if navigated to details page else false
	 * @throws Exception
	 */

	public final boolean navigateToProductCatalogDetailsPage(List<WebElement> catalogList) throws Exception {
		boolean flag=false;
		waitForPageLoaded();
		Iterator<WebElement> catalogListIterator = catalogList.iterator();
		if (catalogList.size() > 1) {
			while (catalogListIterator.hasNext()) {
				WebElement catalogListItem = catalogListIterator.next();
				if (!catalogListItem.getText().contains("Default")) {
					catalogListItem.click();
					break;
				}
			}

			flag = true;
		} else {
			LOGGER.info("Only default catalog available");
		}
		return flag;

	}

	/**
	 * This is a method to navigate to catalog details page
	 * 
	 * @param catalogList - List of all available catalogs
	 * @param LanguageCode - Language code
	 * @return - boolean true if navigated to details page else false
	 */

	public final boolean checkCatalogListStatus(List<WebElement> catalogStatusList, String languageCode) {
		boolean flag = true;

		waitForPageLoaded();
		Iterator<WebElement> catalogListIterator = catalogStatusList.iterator();
		while (catalogListIterator.hasNext()) {
			WebElement catalogListItem = catalogListIterator.next();
			try {
				if (catalogListItem.getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "product.active"))) {
					flag = false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return flag;

	}

	/**
	 * This is a method to hover on element
	 * 
	 * @param key locator of element
	 */
	public final void moveToElementOnProductCatalogPage(String key) {

		try {
			mouseHover(productCatalogPageProperties.getProperty(key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method basically checks the cancel functionality.
	 * 
	 * @param languageCode - Language code
	 * @param columnListText - List of catalog string
	 * @return return boolean value either true or false
	 */
	public final boolean verifyCancelFunctionalityWithCheckboxSelection(String languageCode, List<String> columnListText) {
		boolean flag = false;
		try {
			enterText(productCatalogPageProperties.getProperty("cNameSearchBox"), columnListText.get(columnListText.size() - 2));
			String empty_text = null;
			if (verifyElementIsPresent(productCatalogPageProperties.getProperty("noProductCatalogDisplayText"))) {
				empty_text = getTextBy(productCatalogPageProperties.getProperty("noProductCatalogDisplayText"));
				if (empty_text.equals(getTextLanguage(languageCode, "daas_ui", "incidents.list.no_items"))) {
					flag = false;
					clickOnElementsOfProductCatalogPage("clearAllFilterField");
					LOGGER.info("Search catalog list does not find at Product catalog list!!!");
				}
			} else {
				// Cancel for single selection.
				waitForElementsOfProductCatalogPage("selectCheckbox");
				clickOnElementsOfProductCatalogPage("selectCheckbox");
				clickOnElementsOfProductCatalogPage("removeButton");
				waitForElementsOfProductCatalogPage("cancelButtonOnPopup");
				clickOnElementsOfProductCatalogPage("cancelButtonOnPopup");

				// Cancel for multiple selection
				clickOnElementsOfProductCatalogPage("clearAllFilterField");
				waitForElementsOfProductCatalogPage("tableOverlay");
				clickByJavaScriptOnElementsOfProductCatalogPage("selectAllCheckBox");
				waitForElementsOfProductCatalogPage("removeButton");
				clickOnElementsOfProductCatalogPage("removeButton");
				waitForElementsOfProductCatalogPage("cancelButtonOnPopup");
				clickOnElementsOfProductCatalogPage("cancelButtonOnPopup");
				clickByJavaScriptOnElementsOfProductCatalogPage("selectAllCheckBox");
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			LOGGER.error("Selected list does not cancel succssfully!!!" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method basically verify remove functionality through hamburger
	 * 
	 * @return return boolean value either true or false
	 */
	public final boolean verifyRemoveFunctionalityWithHamburgerSelectionOnListPage(String languageCode, List<String> columnList) {
		boolean flag = false;
		try {
			// Remove on list page with hamburger
			waitForPageLoaded();
			for (int listCounter = 0; listCounter < columnList.size(); listCounter++) {
				if (!columnList.get(listCounter).equalsIgnoreCase("Default Product Catalog")) {
					waitForElementsOfProductCatalogPage("catalogNameSearchBox");
					enterText(productCatalogPageProperties.getProperty("catalogNameSearchBox"), columnList.get(listCounter));

					sleeper(4000);
					mouseHoverOnProductCatalogListPage("catalogNameSearchList");
					sleeper(1000);
					clickOnElementsOfProductCatalogPage("hamburger");
					sleeper(1000);
					clickOnElementsOfProductCatalogPage("hamburgerRemove");
					sleeper(2000);
					clickOnElementsOfProductCatalogPage("removeButtonOnPopup");
					sleeper(2000);
					// Wait for toast Notification
					waitForElementsOfProductCatalogPage("toastNotification");
					if (getTextOfProductCatalogPage("toastNotification").equals(getTextLanguage(languageCode, "daas_ui", "catalogs.list.toast.remove.success"))) {
						if (verifyElementIsPresent(productCatalogPageProperties.getProperty("clearAllFilterField")))
							clickOnElementsOfProductCatalogPage("clearAllFilterField");
						LOGGER.info("Single remove successfully on Productcatalog list page");
						flag = true;
						break;
					} else {
						flag = false;
						LOGGER.info("Single remove does not working on Product Catalog list page");
						return flag;
					}
				} else {
					LOGGER.info("Default Product Catalog list found in catalog list.");
					flag = true;
				}
			}
		} catch (Exception e) {
			flag = false;
			LOGGER.error("Remove functionality does not work through hamburger on list. !!!");
		}
		return flag;
	}

	/**
	 * This method basically verify remove functionality through hamburger
	 * 
	 * @return return boolean value either true or false
	 */
	public final boolean verifyRemoveFunctionalityWithHamburgerSelectionOnDetailsPage(String languageCode, List<String> columnList) {
		boolean flag = false;
		List<String> nameColumnlistOnDetailsPage = null;
		try {
			// Remove on list page with hamburger
			waitForPageLoaded();
			for (int listCounter = 0; listCounter < columnList.size(); listCounter++) {
				if (!columnList.get(listCounter).equalsIgnoreCase("Default Product Catalog")) {
					waitForElementsOfProductCatalogPage("catalogNameSearchBox");
					enterText(productCatalogPageProperties.getProperty("catalogNameSearchBox"), columnList.get(listCounter));

					sleeper(4000);
					// Navigate on details page with hamburger
					mouseHoverOnProductCatalogListPage("catalogNameSearchList");
					sleeper(1000);
					clickOnElementsOfProductCatalogPage("hamburger");
					sleeper(1000);
					waitForElementsOfProductCatalogPage("hamburgerDetails");
					clickOnElementsOfProductCatalogPage("hamburgerDetails");

					// Remove on details page with hamburger
					waitForPageLoaded();
					waitForElementsOfProductCatalogPage("selectAllCheckBox");
					moveToElementOnProductCatalogPage("prodProdNameList");
					//Vineer 3 changes for Hamburger on details pending due to bug 609547
					waitForElementsOfProductCatalogPage("hamburger");
					clickOnElementsOfProductCatalogPage("hamburger");
					clickOnElementsOfProductCatalogPage("hamburgerRemoveonDetailsPage");
					sleeper(2000);
					waitForElementsOfProductCatalogPage("removeButtonOnDetailsPopup");
					clickOnElementsOfProductCatalogPage("removeButtonOnDetailsPopup");
					waitForElementsOfProductCatalogPage("toastNotification");
					if (getTextOfProductCatalogPage("toastNotification").equals(getTextLanguage(languageCode, "daas_ui", "catalogs.list.toast.remove.success"))) {
						LOGGER.info("Single remove successfully with hambuger on Product Catalog details page");
						flag = true;
					} else {
						flag = false;
						LOGGER.info("Single remove does not working with hambuger on Product Catalog details page");
						return flag;
					}

					// Remove on details page with remove button
					waitForPageLoaded();
					waitForElementsOfProductCatalogPage("tableOverlay");
					nameColumnlistOnDetailsPage = getTextOfListOfProductCatalogPage("prodProdNameList");
					clickByJavaScriptOnElementsOfProductCatalogPage("selectAllCheckBox");
					waitForElementsOfProductCatalogPage("removeButtonOnDetails");
					clickByJavaScriptOnElementsOfProductCatalogPage("removeButtonOnDetails");
					if (getTextLanguage(languageCode, "daas_ui", "catalogs.remove_catalogs").contains(getTextBy(productCatalogPageProperties.getProperty("removeHeaderPopupText")))
							&& ((getTextLanguage(languageCode, "daas_ui", "catalogs.list.modal.remove_catalog.warning.plural").toString()).replace("{catalogCount}", String.valueOf(nameColumnlistOnDetailsPage.size())).toString()).contains(getTextBy(productCatalogPageProperties.getProperty("removeContentMessageText")))) {
						waitForElementsOfProductCatalogPage("removeButtonOnDetailsPopup");
						clickOnElementsOfProductCatalogPage("removeButtonOnDetailsPopup");
						waitForElementsOfProductCatalogPage("toastNotification");
						if (getTextOfProductCatalogPage("toastNotification").equals(getTextLanguage(languageCode, "daas_ui", "catalogs.list.toast.remove.success"))) {
							LOGGER.info("Multiple remove successfully with hambuger on Product Catalog details page");
							flag = true;
						} else {
							flag = false;
							LOGGER.info("Multiple remove does not working with hambuger on Product Catalog details page");
							return flag;
						}
					} else {
						waitForElementsOfProductCatalogPage("cancelButtonOnPopup");
						clickOnElementsOfProductCatalogPage("cancelButtonOnPopup");
						LOGGER.error("Message does not match on Remove popup !!!");
						flag = false;
						return flag;
					}
					// Back to the list page.
					waitForElementsOfProductCatalogPage("productCatalogLink");
					waitForPageLoaded();
					clickByJavaScriptOnElementsOfProductCatalogPage("productCatalogLink");
					flag = true;
					waitForPageLoaded();
					break;
				} else {
					LOGGER.info("Default Product Catalog list found in catalog list.");
					flag = true;
				}

			}
		} catch (Exception e) {
			flag = false;
			LOGGER.error("Remove functionality does not work through hamburger on Details page !!!");
		}
		return flag;
	}

	/**
	 * This function checks remove functionality for single/multiple selection.
	 * 
	 * @param languageCode - Language code
	 * @param columnListText List of catalog string
	 * @return return boolean value either true or false
	 */
	public final boolean verifyRemoveFunctionalityWithCheckboxSelection(String languageCode, List<String> columnListText) {
		boolean flag = false;
		List<String> catalogListName = null;
		List<WebElement> checkboxList = null;
		String catalogName = null;
		int listNo = 0;
		try {
			waitForPageLoaded();
			for (int listCounter = 0; listCounter < columnListText.size(); listCounter++) {
				if (!columnListText.get(listCounter).equalsIgnoreCase("Default Product Catalog")) {
					waitForElementsOfProductCatalogPage("catalogNameSearchBox");
					enterText(productCatalogPageProperties.getProperty("catalogNameSearchBox"), columnListText.get(listCounter));
					sleeper(4000);

					String empty_text = null;
					if (verifyElementIsPresent(productCatalogPageProperties.getProperty("noProductCatalogDisplayText"))) {
						empty_text = getTextBy(productCatalogPageProperties.getProperty("noProductCatalogDisplayText"));
						if (empty_text.equals(getTextLanguage(languageCode, "daas_ui", "incidents.list.no_items"))) {
							flag = false;
							clickOnElementsOfProductCatalogPage("clearAllFilterField");
							LOGGER.error("Search catalog list does not find at Product catalog list!!!");
						}
					} else {
						mouseHoverOnProductCatalogListPage("selectAllCheckBox");
						sleeper(1000);
						clickByJavaScriptOnElementsOfProductCatalogPage("selectAllCheckBox");
						sleeper(1000);
						clickOnElementsOfProductCatalogPage("removeButton");
						String prodName = getTextOfProductCatalogPage("firstSelectedText");
						if (getTextLanguage(languageCode, "daas_ui", "catalogs.remove_catalog.singular").contains(getTextBy(productCatalogPageProperties.getProperty("deleteHeaderPopupText"))) && (getTextLanguage(languageCode, "daas_ui", "catalogs.list.modal.remove_catalog.warning").toString().replace("{catalogName}", prodName).toString()).contains(getTextBy(productCatalogPageProperties.getProperty("removeContentMessageText")))) {
							waitForElementsOfProductCatalogPage("removeButtonOnPopup");
							clickOnElementsOfProductCatalogPage("removeButtonOnPopup");

							// Wait for toast Notification
							waitForElementsOfProductCatalogPage("toastNotification");
							if (getTextOfProductCatalogPage("toastNotification").equals(getTextLanguage(languageCode, "daas_ui", "catalogs.list.toast.remove.success"))) {
								LOGGER.info("Single remove successfully on Product Catalog list page");
								flag = true;
							} else {
								flag = false;
								LOGGER.info("Single remove does not working on Product Catalog list page");
								return flag;
							}
							empty_text = getTextBy(productCatalogPageProperties.getProperty("noProductCatalogDisplayText"));
							if (empty_text.equals(getTextLanguage(languageCode, "daas_ui", "incidents.list.no_items"))) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error("Select single product catalog does not remove successfully!!!");
							}
							clickOnElementsOfProductCatalogPage("clearAllFilterField");
							waitForElementsOfProductCatalogPage("tableOverlay");
							catalogListName = getTextOfListOfProductCatalogPage("catalogNameSearchList");
							checkboxList = getElementsOfProductCatalogPage("listOfCheckbox");
							clickByJavaScriptOnElementsOfProductCatalogPage("selectAllCheckBox");
							listNo = catalogListName.size() - 1;
							
							for (int listCount = 0; listCount < catalogListName.size(); listCount++) {
								if (catalogListName.get(listCount).equalsIgnoreCase("Default Product Catalog")) {
									checkboxList.get(listCount).isSelected();
									sleeper(500);
									checkboxList.get(listCount).click();
									catalogName = catalogListName.get(listCount + 1);
									break;
								}
							}
							clickOnElementsOfProductCatalogPage("removeButton");
							if (listNo > 1) {
								if (getTextLanguage(languageCode, "daas_ui", "catalogs.remove_catalogs").contains(getTextBy(productCatalogPageProperties.getProperty("deleteHeaderPopupText"))) && ((getTextLanguage(languageCode, "daas_ui", "catalogs.list.modal.remove_catalog.warning.plural").toString()).replace("{catalogCount}", String.valueOf(listNo).toString())).contains(getTextBy(productCatalogPageProperties.getProperty("removeContentMessageText")))) {
									waitForElementsOfProductCatalogPage("removeButtonOnPopup");
									clickOnElementsOfProductCatalogPage("removeButtonOnPopup");
									waitForElementsOfProductCatalogPage("toastNotification");
									if (getTextOfProductCatalogPage("toastNotification").equals(getTextLanguage(languageCode, "daas_ui", "catalogs.list.toast.remove.success"))) {
										LOGGER.info("Multiple remove successfully on Product Catalog list page");
										flag = true;
										break;
									} else {
										flag = false;
										LOGGER.info("Multiple remove does not working on Product Catalog list page");
										return flag;
									}
								} else {
									flag = false;
									LOGGER.error("Select all product catalog does not remove successfully!!!");
								}
							} else if (getTextLanguage(languageCode, "daas_ui", "catalogs.remove_catalog.singular").contains(getTextBy(productCatalogPageProperties.getProperty("deleteHeaderPopupText"))) && (getTextLanguage(languageCode, "daas_ui", "catalogs.list.modal.remove_catalog.warning").toString().replace("{catalogName}", catalogName).toString()).equalsIgnoreCase(getTextBy(productCatalogPageProperties.getProperty("removeContentMessageText")))) {
								waitForElementsOfProductCatalogPage("removeButtonOnPopup");
								clickOnElementsOfProductCatalogPage("removeButtonOnPopup");
								waitForElementsOfProductCatalogPage("toastNotification");
								if (getTextOfProductCatalogPage("toastNotification").equals(getTextLanguage(languageCode, "daas_ui", "catalogs.list.toast.remove.success"))) {
									LOGGER.info("Single remove successfully on Product Catalog list page");
									flag = true;
									break;
								} else {
									flag = false;
									LOGGER.info("Single remove does not working on Product Catalog list page");
									return flag;
								}
							}
						} else {
							flag = false;
							LOGGER.error("Message does not match on remove popup!!!");
						}
					}
				} else {
					LOGGER.info("Default Product Catalog list found in catalog list.");
					flag = true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Product catalog list does not removed succssfully!!!" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method function basically used for remove the product catalog list page and details page.
	 * 
	 * @param languageCode - Language code
	 * @param columnList Return List of column string
	 * @param companySearchKey companySearch element key
	 * @return return boolean value either true or false
	 */
	public final boolean verifyImportValidCSVRemoveFunctionalityOnProductListPage(String languageCode, List<String> columnList) {
		boolean flag = false;
		try {
			for (int i = 0; i < columnList.size(); i++) {
				clickOnElementsOfProductCatalogPage("addButton");
				waitForElementsOfProductCatalogPage("dropDownOpen");
				waitForPageLoaded();
				clickOnElementsOfProductCatalogPage("dropDownOpen");
				LOGGER.info("Clicked on dropdown arrow");
				waitForElementsOfProductCatalogPage("companySearch");
				enterTextForProductCatalogPage("companySearch",columnList.get(i));
				sleeper(5000);
				waitForElementsOfProductCatalogPage("selectedSearchCompany");
				clickOnElementsOfProductCatalogPage("selectedSearchCompany");
				verifyElementsOfProductCatalogPage("browseButton");
					//clickOnElementsOfProductCatalogPage("browseButton");
					fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_INVALID);
					sleeper(2000);
				waitForElementsOfProductCatalogPage("importButton");
				clickOnElementsOfProductCatalogPage("importButton");
				waitForElementsOfProductCatalogPage("toastNotification");
				if (getTextOfProductCatalogPage("toastNotification").equals(getTextLanguage(languageCode, "daas_ui", "assets.import.asset.import_in_progress"))) {
					LOGGER.info("Toast Notification text match");
					flag = true;
				} else {
					LOGGER.error("Toast Notification text does not match");
					flag = false;
					return flag;
				}
			}
		} catch (Exception e) {
			flag = false;
			LOGGER.error("DropDown selection does not happen during the import CSV file!!!" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method set attribute to block to be used in import
	 * 
	 * @param key - Key of the locator
	 */
	public final void setAttributeOfProductCatalog(String key) {
		setAttributeForImport(productCatalogPageProperties.getProperty(key));
	}

	/**
	 * This method is used for clearing any filters applied on product catalog list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfProductCatalogListPage(String clearFilterKey) throws Exception {
		clearFilters(productCatalogPageProperties.getProperty(clearFilterKey));
	}
	
	/**
	 * This is a method to wait Until element has gone invisible
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final void waitUntilElementIsInvisibleOfProductCatalogPage(String key) throws Exception {
		waitUntilElementIsVisible(productCatalogPageProperties.getProperty(key));
	}
	
	/**
	 * This method is used to validate the notification flow after the catalogs have been imported for veneer version 3
	 * 
	 * @param fileName this is the name of file which was imported
	 * @param countUnread this is total unread count of notifications
	 * @return true if the notification text matches else false
	 * @throws Exception
	 */
	public boolean postNotificationCheckImportInV3(String fileName) throws Exception {
		boolean flag = false;
		String notificationText = null;
		waitForElementsOfProductCatalogPage("tableOverlay");
		waitForElementsOfProductCatalogPage("notificationBellIcon");
		clickOnElementsOfProductCatalogPage("notificationBellIcon");
		if (waitForElementsOfProductCatalogPage("unreadNotificationText")) {
			sleeper(3000);
			notificationText = getTextOfProductCatalogPage("unreadNotificationText");
			if (notificationText.contains("successfully")) {
				if (notificationText.equalsIgnoreCase("Your 15 products were imported successfully. Please check the logs for more details.")) {
					flag = true;
				} else {
					LOGGER.error("Notification for import has failed");
				}

			} else if (notificationText.contains("problem while importing")) {
				if (notificationText.equalsIgnoreCase("There was a problem while importing your products from catalogInvalid.csv. Please check the logs for more information")) {
					flag = true;
				} else {
					LOGGER.error("Notification for import has failed");
				}

			}

		}
		return flag;

	}
	
	public final void mouseHoverOnProductCatalogListPage(String key) throws Exception {
		mouseHover(productCatalogPageProperties.getProperty(key));
	}
	
	
	/**
	 * This method is used for file imported using Robot class for veneer version 3
	 * @param fileName this is the name of file which was imported
	 * @throws Exception 
	 */
	public void fileImportInV3(String fileName) throws Exception {
		sleeper(2000);
		WebElement addFile = getElementOfProductCatalogPage("browseInput");
	    addFile.sendKeys(fileName);
	    sleeper(3000);
	}
	 
	
}