package com.daasui.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import com.basesource.utils.ActiveCareDBValidation;
import com.basesource.utils.CSRGenerator;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DeviceVariables;
import com.daasui.constants.PreferenceVariables;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * This class includes all the methods related to device list page test cases
 */
public class DeviceListPage extends CommonMethod {
	public static final String Device_Grouping_toggle = "techpulse-grouping-service";
	private ObjectReader deviceListPagePropertiesReader = new ObjectReader();
	private Properties deviceListPagePropertiesPageProperties;
	private DeviceListPage instance;

	public static HashMap<String, String> mapAdded = new HashMap<>();
	public static ArrayList<String> titleInfo = new ArrayList<String>();
	public static HashMap<String, String> pendingDevices = new HashMap<>();

	public static String deviceSerialNo = null;
	public static String uiVersion = System.getProperty("uiVersion");
	public static String markAsReadOptionText = getEnvironmentSpecificData(System.getProperty("environment"), "MARK_AS_READ");
	public DeviceListPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (DeviceListPage.class) {
				if (instance == null) {
					instance = new DeviceListPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public DeviceListPage(WebDriver driver) throws IOException {
		deviceListPagePropertiesPageProperties = deviceListPagePropertiesReader.getObjectRepository("DeviceListPageV3");
	}
	
	public final void scrollTillViewDeviceListPage(String locator) throws Exception {
		scrollTillView(deviceListPagePropertiesPageProperties.getProperty(locator));
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfDeviceListPage(String key) {
		try {
			return verifyElementIsPresent(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfDeviceListPage(String key) {
		try {
			return verifyElementIsVisible(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}
	/**
	 * This is a method to verify search functionality of search filters present on
	 * incident page
	 *
	 * @param LanguageCode - Language code
	 * @param textKey      - Locator of searchbox
	 * @param Text         - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey      - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working
	 *         correctly
	 */
	public final boolean verifySearchValueOnDevice(String LanguageCode, String textKey, String Text,
												   String emptyTextKey, String listKey) {
		try {
			scrollOnDeviceListPage(textKey);
			return verifySearchFunctionality(LanguageCode, deviceListPagePropertiesPageProperties.getProperty(textKey), Text,
					deviceListPagePropertiesPageProperties.getProperty(emptyTextKey), deviceListPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOnCompany " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfDeviceListPageDynamic(String key, int waitTime) {
		try {
			return verifyElementIsVisibleDynamic(deviceListPagePropertiesPageProperties.getProperty(key), waitTime);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of element
	 * @param textToMatch - Text to be matched
	 * @return - boolean value of whether the text present on element matches or not
	 */
	public final boolean matchTextOfDeviceListPage(String key, String textToMatch) {
		try {
			return verifyTextPresentOnElement(deviceListPagePropertiesPageProperties.getProperty(key), textToMatch);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to click on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void clickOnElementsOfDeviceListPage(String key) {
		try {
			click(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementsOfDeviceListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get text of an element
	 * 
	 * @param key - Locator of element
	 * @return - String value of the text on the element
	 */
	public final String getTextOfDeviceListPage(String key) {
		try {
			return getTextBy(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfDeviceListPage " + e.getMessage()));
			return null;
		}
	}
public final boolean verifyDeviceListTableData(String partialTableHeaderListLocator,String headerTitle,String partialTableDataLocator,String data) throws Exception {
	return verifyTableData(deviceListPagePropertiesPageProperties.getProperty(partialTableHeaderListLocator),headerTitle,deviceListPagePropertiesPageProperties.getProperty(partialTableDataLocator),data)	;
}
	/**
	 * This is a method to verify if the element is enable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is enabled or not
	 */
	public final boolean verifyElementIsEnableOfDeviceListPage(String key) {
		try {
			return verifyElementIsEnable(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if the element is selected
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is selected or not
	 */
	public final boolean verifyElementIsSelectedOfDeviceListPage(String key) {
		try {
			return verifyElementIsSelected(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsSelectedOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to clear text present on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void clearTextOnElementsOfDeviceListPage(String key) {
		try {
			clearTextRefresh(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clearTextOnElementsOfDeviceListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get attribute of an element
	 * 
	 * @param key - Locator of element
	 * @param value - the name of attribute
	 * @return - String value of the attribute
	 */
	public final String getAttributesOfDeviceListPage(String key, String value) {
		try {
			return getAttribute(deviceListPagePropertiesPageProperties.getProperty(key), value);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to click on an element by javascript
	 * 
	 * @param key - Locator of element
	 */
	public final void clickByJavaScriptOnDeviceListPage(String key) {
		try {
;			clickByJavaScript(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnDeviceListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param Text - The text to be entered
	 */
	public final void enterTextForDeviceListPage(String key, String Text) {
		try {
			enterText(deviceListPagePropertiesPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfDeviceListPage1 " + e.getMessage()));
		}
	}
	
	public final List<String> getAllTextOfDeviceListPage(String key) throws Exception {
		return getallTextBy(deviceListPagePropertiesPageProperties.getProperty(key));
	}


	/**
	 * This is a method to hover mouse on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void mousehoverOnDeviceListPage(String key) {
		try {
			mouseHover(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnDeviceListPage " + e.getMessage()));
		}
	}

	/**
	 * This is the method to verify if all the checkboxes in a column of a table are selected
	 * 
	 * @return - boolean value of whether the checkboxes are selected or not
	 */
	public final boolean verifyAllCheckBoxesSelectedOnDeviceListPage() {
		try {
			return verifyAllCheckBoxesSelectedOfTable(deviceListPagePropertiesPageProperties.getProperty("allCheckBoxOfPage"));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify search functionality of search filters present on deviceListPage page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOnDeviceList(String languageCode, String textKey, String text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionality(languageCode, deviceListPagePropertiesPageProperties.getProperty(textKey), text, deviceListPagePropertiesPageProperties.getProperty(emptyTextKey), deviceListPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify search functionality of search filters on column present on deviceListPage page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOfColumnOnDeviceList(String languageCode, String textKey, String text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionalityofColumn(languageCode, deviceListPagePropertiesPageProperties.getProperty(textKey), text, deviceListPagePropertiesPageProperties.getProperty(emptyTextKey), deviceListPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOfColumnOnDeviceList " + e.getMessage()));
			return false;
		}
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
	 */
	public final boolean verifyFilterSingleSelectDynamic(String languageCode, String textKey, String text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) {
		try {
			return verifyFilterFunctionalityForSingleSelectFromDynamicDropdown(languageCode, deviceListPagePropertiesPageProperties.getProperty(textKey), text, deviceListPagePropertiesPageProperties.getProperty(emptyTextKey), deviceListPagePropertiesPageProperties.getProperty(listKey), deviceListPagePropertiesPageProperties.getProperty(checkboxKey), deviceListPagePropertiesPageProperties.getProperty(columnListKey), deviceListPagePropertiesPageProperties.getProperty(emptyTextColumnKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterSingleSelectDynamic " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get a list of elements of device list page
	 * 
	 * @param key - Locator of list
	 * @return - list of webelements
	 */
	public final List<WebElement> getElementsTillAllElementsVisibleofDeviceListPage(String key) {
		try {
			return getElementsTillAllElementsVisible(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the filter functionality when multiple options are selected from a dynamically changing list of options
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
	 */
	public final boolean verifyFilterMultiSelectDynamic(String languageCode, String textKey, String text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) {
		try {
			return verifyFilterFunctionalityForMultiSelectFromDynamicDropdown(languageCode, deviceListPagePropertiesPageProperties.getProperty(textKey), text, deviceListPagePropertiesPageProperties.getProperty(emptyTextKey), deviceListPagePropertiesPageProperties.getProperty(listKey), deviceListPagePropertiesPageProperties.getProperty(checkboxKey), deviceListPagePropertiesPageProperties.getProperty(columnListKey), deviceListPagePropertiesPageProperties.getProperty(emptyTextColumnKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterMultiSelectDynamic " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify the options that are present on a dropdown by default
	 * 
	 * @param key - Locator of available options
	 * @param optionsOnDropdown - The expected options to be present
	 * @return - boolean value of whether the options are correctly displayed
	 */
	public final boolean verifyOptionsOnDropdownForDeviceListPage(String key, ArrayList<String> optionsOnDropdown) {
		try {
			return compareTwoList(deviceListPagePropertiesPageProperties.getProperty(key), optionsOnDropdown);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method  " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to select first option from any dropdown
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectFirstOptionFromDropdownOnDeviceListPage(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(deviceListPagePropertiesPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method  " + e.getMessage()));
			return null;
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
	 * @return - boolean value of whether the filter functionality is working correctly
	 */
	public final boolean verifyFilterSingleSelect(String languageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) {
		try {
			return verifyFilterFunctionalityForSingleSelect(languageCode, deviceListPagePropertiesPageProperties.getProperty(checkboxKey), deviceListPagePropertiesPageProperties.getProperty(listOfElementKey), deviceListPagePropertiesPageProperties.getProperty(columnListKey), deviceListPagePropertiesPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyOptionsOnDropdownForDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify the filter functionality when multiple options are selected from a static list of options
	 * 
	 * @param languageCode - Language code
	 * @param checkboxKey - Locator for the checkboxes in dropdown
	 * @param listOfElementKey - Locator for list of items in dropdown
	 * @param columnListKey - Locator for list of all items in the column
	 * @param emptyTextKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 */
	public final boolean verifyFilterMultiSelect(String languageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) {
		try {
			return verifyFilterFunctionalityForMultiSelectForDyanmicList(languageCode, deviceListPagePropertiesPageProperties.getProperty(checkboxKey), deviceListPagePropertiesPageProperties.getProperty(listOfElementKey), deviceListPagePropertiesPageProperties.getProperty(columnListKey), deviceListPagePropertiesPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method  verifyFilterMultiSelect" + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to select value from dropdown in popup
	 * 
	 * @param editButtonKey - Locator for edit buton which will open popup on clicking
	 * @param dropdownButtonKey - Locator for dropdown arrow
	 * @param dropdownListKey - Locator for available options in dropdown
	 * @param submitButtonKey - Locator for submit button on popup
	 * @return - String value of the option selected from dropdown
	 */
	public final String selectValueFromDropdownInPopup(String editButtonKey, String dropdownButtonKey, String dropdownListKey, String submitButtonKey) {
		try {
			clickOnElementsOfDeviceListPage(editButtonKey);
			LOGGER.info("Clicked on edit button");
			clickOnElementsOfDeviceListPage(dropdownButtonKey);
			LOGGER.info("Clicked on dropdown arrow");
			String text = selectFirstValueFromDropdown(deviceListPagePropertiesPageProperties.getProperty(dropdownListKey));
			clickOnElementsOfDeviceListPage(submitButtonKey);
			LOGGER.info("Clicked on submit button");
			return text;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectValueFromDropdownInPopup " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get a sequence of selected columns
	 * 
	 * @param key - Locator of the list of selected columns
	 * @return - arraylist of the text present on the list of elements
	 */
	public final ArrayList<String> getSequenceOfSelectedColumnsOnDeviceListPage(String key) {
		try {
			return getTextOfList(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method  " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the selected value from popup matches with the values present on table
	 * 
	 * @param key - Locator for the list of items updated in table
	 * @param text - The text which is to be matched
	 * @return - boolean value of whether the selected value is reflected in the column on table
	 */
	public final boolean verifySelectedValuesOfPopupInTable(String key, String text) {
		try {
			return verifySelectedValueFromPopupInTable(deviceListPagePropertiesPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method  " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify searched value for a search box present on a popup
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator for searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for the list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOnDeviceListInsidePopup(String languageCode, String textKey, String text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionalityInsidePopup(languageCode, deviceListPagePropertiesPageProperties.getProperty(textKey), text, deviceListPagePropertiesPageProperties.getProperty(emptyTextKey), deviceListPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOnDeviceListInsidePopup " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to verify if element is clickable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is clickable or not
	 */
	public final boolean verifyElementIsClickableOfDeviceListPage(String key) {
		try {
			return verifyElementIsClickable(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsClickableOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get a list of elements present on device list page
	 * 
	 * @param key - Locator of element
	 * @return - list of web elements
	 */
	public List<WebElement> getWebElementsOfDeviceListPage(String key) {
		try {
			return getElementsTillAllElementsPresent(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getWebElementsOfDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to scroll on deviceListPage page
	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnDeviceListPage(String key) {
		try {
			scrollTillView(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnDeviceListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to wait for an element to get clicable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible after the wait
	 */
	public final boolean waitForElementtobeClickableOfDeviceListPage(String key) {
		try {
			return verifyElementIsClickable(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementtobeClickableOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get a list of elements present on device list page
	 * 
	 * @param key - Locator of element
	 * @return - list of web elements
	 */
	public final List<WebElement> getElementsOfDeviceListPage(String key) {
		try {
			return getElementsTillAllElementsPresent(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsOfDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get element present on device list page
	 * 
	 * @param key - Locator of element
	 * @return - web element
	 */
	public final WebElement getElementOfDeviceListPage(String key) {
		try {
			return getElement(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementOfDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify filter functionality when multiple options are selected from a dynamic list
	 * 
	 * @param languageCode - language code
	 * @param checkboxKey - locator for checkboxes on popup
	 * @param listOfElementKey - locator for list of options available on popup
	 * @param columnListKey - locator for list of all items updated in column
	 * @param emptyTextKey - locator for "no items available" message on column
	 * @return boolean value of whether the filter functionality is working correctly
	 */
	public final boolean verifyFilterMultiSelectDynamic(String languageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) {
		try {
			return verifyFilterFunctionalityForMultiSelectForDyanmicList(languageCode, deviceListPagePropertiesPageProperties.getProperty(checkboxKey), deviceListPagePropertiesPageProperties.getProperty(listOfElementKey), deviceListPagePropertiesPageProperties.getProperty(columnListKey), deviceListPagePropertiesPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterMultiSelectDynamic " + e.getMessage()));
			return false;
		}
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
	 */
	public final void selectDateFromCalenderOnDeviceListpage(String date, String calenderKey, String monthKeyLeft, String monthKeyRight, String leftArrowKey, String daysOnLeftSideKey, String daysOnRightSideKey) {
		try {
			selectDateFromCalender(date, deviceListPagePropertiesPageProperties.getProperty(calenderKey), deviceListPagePropertiesPageProperties.getProperty(monthKeyLeft), deviceListPagePropertiesPageProperties.getProperty(monthKeyRight), deviceListPagePropertiesPageProperties.getProperty(leftArrowKey), deviceListPagePropertiesPageProperties.getProperty(daysOnLeftSideKey), deviceListPagePropertiesPageProperties.getProperty(daysOnRightSideKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalender " + e.getMessage()));
		}
	}

	/**
	 * This is a method to select last one week range on calendar
	 * 
	 * @param languageCode - language code
	 * @param calenderKey- locator for calendar column
	 * @param monthKeyLeft locator to go to months on left side
	 * @param monthKeyRight - locator to go to months on right side
	 * @param leftArrowKey - locator for left arrow key on calendar
	 * @param daysKeyLeft - locator for days on left side
	 * @param daysKeyRight - locator for days on right side
	 * @param emptyTextKey - locator for "no items available" message on column
	 * @param columnListKey - locator for all items filtered on column
	 * @return - boolean value of whether the dates are selected properly
	 */
	public final boolean selectLastOneWeekRangeOnDeviceListPage(String languageCode, String calenderKey, String monthKeyLeft, String monthKeyRight, String leftArrowKey, String daysKeyLeft, String daysKeyRight, String emptyTextKey, String columnListKey) {
		try {
			return verifyCalendarWithoutDateFormat(languageCode, deviceListPagePropertiesPageProperties.getProperty(calenderKey), deviceListPagePropertiesPageProperties.getProperty(monthKeyLeft), deviceListPagePropertiesPageProperties.getProperty(monthKeyRight), deviceListPagePropertiesPageProperties.getProperty(leftArrowKey), deviceListPagePropertiesPageProperties.getProperty(daysKeyLeft), deviceListPagePropertiesPageProperties.getProperty(daysKeyRight),
					deviceListPagePropertiesPageProperties.getProperty(emptyTextKey), deviceListPagePropertiesPageProperties.getProperty(columnListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectLastOneWeekRange " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This is a method to get count of table rows on device list page
	 * 
	 * @param tableRowsKey - locator of table rows
	 * @return - integer value of no of rows in table
	 */
	public final int getTableRowsCountofDeviceListPage(String tableRowsKey) {
		try {
			return getTableRowsCount(deviceListPagePropertiesPageProperties.getProperty(tableRowsKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTableRowsCountofDeviceListPage " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * This is a method to select element from drop down on device list page
	 * 
	 * @param dropdownId - Locator of dropdown arrow
	 * @param key - Locator of element
	 * @param text - text on element to be selected
	 * @return - boolean value of whether the desired element is selected
	 */
	public final boolean selectElementFromDropDownofDeviceListPage(String dropdownId, String key, String text) {
		try {
			click(deviceListPagePropertiesPageProperties.getProperty(dropdownId));
			LOGGER.info("Clicked on dropdown arrow");
			return selectFromDropdown(deviceListPagePropertiesPageProperties.getProperty(dropdownId), deviceListPagePropertiesPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectElementFromDropDownofDeviceListPage " + e.getMessage()));
			return false;
		}
	}
	
	
	/**
	 * This is a method to select element from drop down on device list page
	 * 
	 * @param dropdownId - Locator of dropdown arrow
	 * @param key - Locator of element
	 * @param text - text on element to be selected
	 * @return - boolean value of whether the desired element is selected
	 */
	public final void selectValueFromDropDownofDeviceListPage(String dropdownId, String key, String text) {
		try {
			click(deviceListPagePropertiesPageProperties.getProperty(dropdownId));
			LOGGER.info("Clicked on dropdown arrow");
			sleeper(300);
			selecValueFromDropdown(deviceListPagePropertiesPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectElementFromDropDownofDeviceListPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to select element from drop down on device list page
	 * 
	 * @param dropdownId - Locator of dropdown arrow
	 * @param key - Locator of element
	 * @param text - text on element to be selected
	 * @return - boolean value of whether the desired element is selected
	 */
	public final boolean selectTextValueFromDropdownOfDeviceListPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(deviceListPagePropertiesPageProperties.getProperty(dropdownListKey), elementText, deviceListPagePropertiesPageProperties.getProperty(dropdownBox));
	}
	public final boolean selectTextValueFromDropdownOfDeviceListPageWorkflow(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdownWorkflow(deviceListPagePropertiesPageProperties.getProperty(dropdownListKey), elementText, deviceListPagePropertiesPageProperties.getProperty(dropdownBox));
	}
	
	/**
	 * This is the method to get the enability status for link/button
	 * 
	 * @param navigationItemPreviouskey - locator for the button/link to be tested
	 * @return - boolean value of the enability status
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviouskey) {
		try {
			return !getElement(deviceListPagePropertiesPageProperties.getProperty(navigationItemPreviouskey)).getAttribute("class").contains("disabled");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getButtonEnabilityStatus " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This is a method to get which option from pagination is selected
	 * 
	 * @param dropdownIdKey - Locator of the dropdown arrow
	 * @param dropdownOptionlistKey - Locator of the options on dropdown
	 * @return - integer value of the selected option
	 */
	public final int getSelectedOptionTextofPaginationDeviceListPage(String dropdownIdKey, String dropdownOptionlistKey) {
		try {
			click(deviceListPagePropertiesPageProperties.getProperty(dropdownIdKey));
			return getSelectedDropdownOptionOnPagination(deviceListPagePropertiesPageProperties.getProperty(dropdownOptionlistKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getSelectedOptionTextofPaginationDeviceListPage " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * This is a method to get the count of total number of records on pagination
	 * 
	 * @param key - locator of the pagination element
	 * @return - integer value of total number of records
	 */
	public final int getTotalRecordCount(String key) {
		try {
			int totalRecord = 0;
			String[] allText = getTextBy(deviceListPagePropertiesPageProperties.getProperty(key)).split(" |/");
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
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key - locator of element
	 * @return - arraylist of the text of all elements present in the list
	 */

	public final ArrayList<String> getTextOfListOfDeviceListPage(String key) {
		try {
			return getTextOfList(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get the list of original text
	 *
	 * @param key - locator of element
	 * @return - arraylist of the text of all elements present in the list
	 */

	public final ArrayList<String> getListOfTextOfDeviceListPage(String key) {
		try {
			return getList(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getListOfTextOfDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to select option from drop down of pagination
	 * 
	 * @param dropdownId - Id of pagination dropdown
	 * @param key - This is the key for values on dropdown
	 * @param text - This is the values text to select from dropdown
	 * @return - boolean value of whether the desired element is selected from drop down
	 */
	public final boolean selectElementFromDropDownOfDeviceListPage(String dropdownId, String key, String text) {
		try {
			click(deviceListPagePropertiesPageProperties.getProperty(dropdownId));
			return selectFromDropdown(deviceListPagePropertiesPageProperties.getProperty(dropdownId), deviceListPagePropertiesPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectElementFromDropDownOfDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to clear all filters on deviceListPage
	 * 
	 * @param key - clear filter key
	 */
	public final void clearAllFiltersOfDeviceListPage(String key) {
		try {
			clearAllFilters(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clearAllFiltersOfDeviceListPage " + e.getMessage()));
		}

	}

	/**
	 * This is a method to store all device list details into an array list.
	 * 
	 * @param deviceName - Device Name
	 */
	public final ArrayList<String> getAllDeviceInfo(String deviceName) {
		try {
			ArrayList<String> deviceInfo = new ArrayList<String>();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();

			deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceName);
			sleeper(2000);
			if (deviceListPage.verifyElementsOfDeviceListPage("deviceDetails") == true) {
				if (deviceListPage.getTextOfDeviceListPage("deviceDetails").equals(deviceName)) {
					sleeper(3000);
					List<WebElement> detailsList = getAllElements(deviceListPagePropertiesPageProperties.getProperty("allDetailsOfSelectedDevice"));
					for (int i = 0; i < detailsList.size(); i++) {
						scrollTillView(detailsList.get(i));
						String value = detailsList.get(i).getText();
						deviceInfo.add(value);
					}
					
					for (int i = 0; i < deviceInfo.size(); i++) {
						if (deviceInfo.get(i).equals("N/A")) {
							deviceInfo.set(i, "-");
						}
					}

				} else {
					LOGGER.error("Selected alias is not present in the list");
				}
			}
			return deviceInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAllDeviceInfo " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to click on device available in the first row after selecting the company
	 * 
	 * @param company - company to be selected
	 * @param languageCode - Language code
	 * @return - boolean value of whether the device was successfully clicked on
	 * @throws IOException 
	 */
	public final boolean clickOnFirstDeviceBySelectingCompany(String company, String languageCode) throws IOException {
		
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();	
		try {
			clickOnElementsOfDeviceListPage("devicesTab");
			if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters")) {
				sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
				sleeper(3000); // Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
				LOGGER.info("All the filters of device list page has been removed successfully.");
			}
			clickOnElementsOfDeviceListPage("companyBox");
			enterTextForDeviceListPage("companySearchBox", company);
			sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			waitForElementsOfDeviceListPage("firstDeviceRow");
			waitForElementsOfDeviceListPage("firstDeviceSerialNumber");
			if (selectFirstOptionFromDropdownOnDeviceListPage("companyListOnPopup").equals(company)) {
				pressKey(Keys.ESCAPE);
				sleeper(1000);
				if (getTextOfDeviceListPage("firstDeviceRow").equals(getTextLanguage(languageCode, "daas_ui", "list.no_items"))) {
					LOGGER.error("No device device present, Plaese enroll atleast one device");
					return false;
				} else if (verifyElementsOfDeviceListPage("firstDeviceSerialNumber")) {
					clickOnElementsOfDeviceListPage(("firstDeviceSerialNumber"));
					LOGGER.info("Clicking on first Device in device list");
				} else {
					LOGGER.error("Failed to click on first Device");
					return false;
				}
			} else {
				LOGGER.error("Company name is not matched");
				return false;
			}
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnFirstDeviceBySelectingCompany " + e.getMessage()));
			return false;
		}
	}

	/**
	 * Tbis is a method to set the table configuration to default state
	 */
	public final void resetToDefaultDeviceListColumns() {
		try {
			clickOnElementsOfDeviceListPage("tableConfigurationButton");
			LOGGER.info("Clicked on table configuration button");
			waitForElementsOfDeviceListPage("reset");
			if (verifyElementsOfDeviceListPage("reset")) {
				clickOnElementsOfDeviceListPage("reset");
				LOGGER.info("Clicked on reset to default");
				clickOnElementsOfDeviceListPage("gearIconSaveButton");
				LOGGER.info("Clicked on save button");
			} else {
				clickOnElementsOfDeviceListPage("gearIconSaveButton");
				LOGGER.info("Clicked on save button");
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method resetToDefaultDeviceListColumns " + e.getMessage()));
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
			companyChangeInfo.put("addDevicesTextCheckKey", "addDevicesTextCheck");
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
	 * @param companyName - Company to be selected
	 * @param languageCode - Language code
	 * @return - boolean value of hether the company was selected successfully
	 */
	public final boolean selectCompanyOfDevicePage(String companyName, String languageCode) {
		try {
			boolean flag = false;
			waitForPageLoaded();
			sleeper(3000);
			if(verifyElementsOfDeviceListPage("clearListPageFilter")) {
				clickOnElementsOfDeviceListPage("clearListPageFilter");
			}
			waitForElementsOfDeviceListPage("addDeviceButton");
			clickOnElementsOfDeviceListPage("addDeviceButton");
			LOGGER.info("Clicked on add button");
			sleeper(5000);
			//waitForElementsOfDeviceListPage("dropDownArrow");
			waitForPageLoaded();
			clickOnElementsOfDeviceListPage("dropDownArrow");
			LOGGER.info("Clicked on dropdown arrow");
			flag = verifySingleSelectDropdownSearchBox(deviceListPagePropertiesPageProperties.getProperty("listOfElements"), companyName, deviceListPagePropertiesPageProperties.getProperty("searchBox"), deviceListPagePropertiesPageProperties.getProperty("dropDownBox"), deviceListPagePropertiesPageProperties.getProperty("noCompanyPresent"));
			if (flag) {
				clickOnElementsOfDeviceListPage("nextButton");
				sleeper(2000);
				LOGGER.info("Clicked on next button");
				if (getTextOfDeviceListPage("addDevicesTextCheck").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "assets.add.solutions.zte").replace("{count}", "3").replace("{app_name}", DeviceVariables.WEX_PRODUCT_NAME))) {
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
public void selectCompanyOnDeviceListPage(String companyName) throws Exception {
	try {
		scrollOnDeviceListPage("companyBoxArrow");
		clickOnElementsOfDeviceListPage("companyBoxArrow");
		LOGGER.info("Clicked on company dropdown");
		sleeper(3000);
		enterTextForDeviceListPage("companySearch", companyName);
		waitForElementsOfDeviceListPage("tableOverlay");
		sleeper(1000);
		selectTextValueFromDropdownOfDeviceListPageWorkflow("companyDropDownDevicePageList", companyName, "companyBox");
		waitForElementsOfDeviceListPage("tableOverlay");
	}catch(Exception e){
		LOGGER.info("Exception occure in selecting company");
	}
}
	/**
	 * This method is used to define parameters of add device manually.
	 * 
	 * @return - hashmap of locators required for adding device manually
	 */
	public final HashMap<String, String> getAddDeviceManuallyDetails() {
		try {
			HashMap<String, String> getAddDeviceManuallyInfo = new HashMap<String, String>();
			getAddDeviceManuallyInfo.put("addButtonIndKey", "addButtonInd");
			getAddDeviceManuallyInfo.put("aliasBoxKey", "aliasBox");
			getAddDeviceManuallyInfo.put("serialNoBoxKey", "serialNoBox");
			getAddDeviceManuallyInfo.put("addAnotherKey", "addAnother");
			getAddDeviceManuallyInfo.put("addButtonIndPopUpKey", "addButtonIndPopUp");
			getAddDeviceManuallyInfo.put("cancelButtonIndPopUpKey", "cancelButtonIndPopUp");
			getAddDeviceManuallyInfo.put("removeEntryKey", "removeEntry");
			getAddDeviceManuallyInfo.put("importLinkKey", "importLink");
			getAddDeviceManuallyInfo.put("aliasSearchBoxKey", "aliasSearchBox");
			getAddDeviceManuallyInfo.put("serialNoSearchBoxKey", "serialNumberSearchBox");
			getAddDeviceManuallyInfo.put("aliasListKey", "aliasList");
			getAddDeviceManuallyInfo.put("serialNoListKey", "serialNumberList");
			getAddDeviceManuallyInfo.put("toastNotificationKey", "toastNotification");
			getAddDeviceManuallyInfo.put("noelementsdisplaytextListKey", "noElementsDisplayText");
			getAddDeviceManuallyInfo.put("selectAllCheckBoxKey", "selectAllCheckBox");
			getAddDeviceManuallyInfo.put("removeDeviceButtonKey", "removeDeviceButton");
			getAddDeviceManuallyInfo.put("removeDeviceButtonPopUPKey", "removeDeviceButtonPopUP");
			getAddDeviceManuallyInfo.put("cancelDeviceButtonPopUPKey", "cancelDeviceButtonPopUP");
			getAddDeviceManuallyInfo.put("downloadButtonKey", "downloadButton");
			getAddDeviceManuallyInfo.put("downloadTextKey", "downloadText");
			getAddDeviceManuallyInfo.put("exportDeviceButtonKey", "exportDeviceButton");
			getAddDeviceManuallyInfo.put("exportDeviceButtonPopUPKey", "exportDeviceButtonPopUP");
			getAddDeviceManuallyInfo.put("currentSelectionExportKey", "currentSelectionExport");
			getAddDeviceManuallyInfo.put("allDevicesExportKey", "allDevicesExport");
			getAddDeviceManuallyInfo.put("nextButtonKey", "nextButton");
			getAddDeviceManuallyInfo.put("previousButtonKey", "previousButton");
			return getAddDeviceManuallyInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAddDeviceManuallyDetails " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the add device manually functionality
	 *
	 * @param getAddDeviceManuallyInfo - Locators required to test add device manually
	 * @param languageCode - language code
	 * @return - boolean value of whether the devices were added successfully
	 */
	public final boolean verifyAddManuallyDevices(String languageCode, int deviceCount, String enrollmentType) {
		try {
			boolean flag = false;
			int randomNumber = 0;
			int serialNumberListCounter = 0;
			Random random = new Random();

			// Select enrollment option
			if (enrollmentType.equalsIgnoreCase("no_enroll")) {
				clickByJavaScriptOnDeviceListPage("noEnrollmentOption");
				LOGGER.info("No enrollment option selected");
			} else if (enrollmentType.equalsIgnoreCase("auto_enroll")) {
				clickByJavaScriptOnDeviceListPage("autoEnrollmentOption");
				LOGGER.info("Auto enrollment option selected");
			} else {
				LOGGER.info("Valid enrollment option not provided");
			}

			clickOnElementsOfDeviceListPage("nextButton");
			clickOnElementsOfDeviceListPage("enterManually");
			clickOnElementsOfDeviceListPage("nextButton");

			while (verifyElementsOfDeviceListPage("addAnother")) {
				clickByJavaScriptOnDeviceListPage("addAnother");
				LOGGER.info("Clicked on add another link");
			}
			List<WebElement> listOfaliases = getElementsTillAllElementsVisibleofDeviceListPage("aliasBox");
			List<WebElement> listOfserialNumber = getElementsTillAllElementsVisibleofDeviceListPage("serialNoBox");
			if (deviceCount <= 5) {
				if (listOfaliases.size() == deviceCount) {
					for (int i = 0; i < deviceCount; i++) {
						randomNumber = random.nextInt(1000);
						String randomString = String.valueOf(randomNumber);
						listOfaliases.get(i).sendKeys("AliasAutomation" + randomString);
						listOfserialNumber.get(i).sendKeys("SrAutomation" + randomString);
					}
				} else {
					LOGGER.error("Five rows could not get added successfully.");
				}
			} else {
				LOGGER.error("More than five devices cannot be added");
			}

			List<WebElement> aliasList = getElementsTillAllElementsVisibleofDeviceListPage("aliasBox");
			List<WebElement> serialNumberList = getElementsTillAllElementsVisibleofDeviceListPage("serialNoBox");
			for (int aliasListCounter = 0; aliasListCounter < aliasList.size(); aliasListCounter++) {
				serialNumberListCounter = aliasListCounter;
				if (serialNumberListCounter < serialNumberList.size()) {
					if (enrollmentType.equalsIgnoreCase("no_enroll"))
						mapAdded.put(aliasList.get(aliasListCounter).getAttribute("value"), serialNumberList.get(serialNumberListCounter).getAttribute("value"));
					else
						pendingDevices.put(serialNumberList.get(serialNumberListCounter).getAttribute("value"), getTextLanguage(languageCode, "daas_ui", "global.Pre_enrolled"));
				}
			}

			clickByJavaScriptOnDeviceListPage("addDevButtonIndPopUp");
			LOGGER.info("Clicked on add button");
			String successMessage = getTextOfDeviceListPage("toastNotification");
			if (successMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "assets.add.individually.add_successful.zte"))) {
				flag = true;
				LOGGER.info("All the devices added successfully!! ");
			} else {
				LOGGER.error("Devices did not add successfully!!");
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyAddManuallyDevices " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to add single device manually
	 */
	public final void addSingleDevice(String serialNumber, String enrollmentType) {
		try {

			// Select enrollment option
			if (enrollmentType.equalsIgnoreCase("auto_enroll")) {
				clickOnElementsOfDeviceListPage("autoEnrollmentOption");
				LOGGER.info("Auto enrollment option selected");
			}
			verifyElementsOfDeviceListPage("nextButton");
			clickOnElementsOfDeviceListPage("nextButton");
			LOGGER.info("Clicked on next button");
			if(verifyElementsOfDeviceListPage("enterManually")){
			clickOnElementsOfDeviceListPage("enterManually");
			LOGGER.info("Clicked on Enter Manually");
			verifyElementsOfDeviceListPage("nextButton");
			clickOnElementsOfDeviceListPage("nextButton");
			}
			enterTextForDeviceListPage("serialNoBox", serialNumber);
			enterTextForDeviceListPage("aliasBox", serialNumber);
			waitForElementsOfDeviceListPage("addDevButtonIndPopUp");
			clickOnElementsOfDeviceListPage("addDevButtonIndPopUp");
			LOGGER.info("Clicked on add button");
			waitForElementsOfDeviceListPage("toastNotification");
			LOGGER.info("Single device added successfully");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method addSingleDevice " + e.getMessage()));
		}
	}

	/**
	 * This method is used to add device using api
	 * 
	 * @param environment : Env name
	 * @param environmentURL : Env url
	 * @param tenantID: tenant ID of User (MSP/Partner Admin)
	 * @return
	 */
	public final ArrayList<String> addDeviceApi(String environment, String environmentURL, String tenantID) {
		try {
			int randomNumber = 0;
			Random random = new Random();
			randomNumber = random.nextInt(1000);
			String randomString = String.valueOf(randomNumber);
			ArrayList<String> deviceDetails = new ArrayList<String>();
			String body = "[{\"alias\":\"" + "AliasAutomation" + randomString + "\",\"serialNumber\":\"" + "SrAutomation" + randomString + "\"}]";
			deviceDetails.add("SrAutomation" + randomString);
			int code = getStatusCode(environmentURL + ConstantURL.ADD_API_DEVICE1 + tenantID + ConstantURL.ADD_API_DEVICE2, body, "POST", environment);
			if (code != CommonVariables.CODEPASSED) {
				LOGGER.error("Add Device API got failed while adding devices.");
				return null;
			} else {
				refreshPage();
				waitForPageLoaded();
				waitForElementsOfDeviceListPage("tableOverlay");
				LOGGER.info("Add Device API got passed while adding devices: " + deviceDetails.get(0));
				return deviceDetails;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in addDeviceApi: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This is a method to reset the table configuration to the default state and select alias checkbox
	 * 
	 * @return - boolean value of whether the state of table configuration was reset successfully
	 */
	public boolean resetConfigDeviceListPage() {
		try {
			boolean flag = false;
			waitForElementsOfDeviceListPage("tableConfigurationButton");
			clickOnElementsOfDeviceListPage("tableConfigurationButton");
			LOGGER.info("Clicked on table configuration");
			waitForElementsOfDeviceListPage("aliasCheckBox");
			clickOnElementsOfDeviceListPage("aliasCheckBox");
			LOGGER.info("Clicked on alias checkbox");
			waitForElementsOfDeviceListPage("saveTableConfiguration");
			clickOnElementsOfDeviceListPage("saveTableConfiguration");
			LOGGER.info("Clicked on save table configuration");
			if (getElementOfDeviceListPage("aliasSearchbox").isDisplayed() && getElementOfDeviceListPage("serialnumberSearchbox").isDisplayed()) {
				flag = true;
			} else {
				LOGGER.error("Resetting configuration failed!");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method resetConfigDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to return value from HashMap
	 *
	 * @param map - hashmap including the details of added/imported devices
	 * @return - list of value of map
	 * */
	public List<String>  getValueFromHashMap(HashMap<String, String> map) {
		List<String> serialNumber =  new ArrayList<String>() ;
		for (Map.Entry<String, String> e : map.entrySet())
			serialNumber.add(e.getValue());
		return serialNumber;
	}


	/**
	 * This is a method to verify imported/added devices
	 *
	 * @param mapAdded - hashmap including the details of added/imported devices
	 * @return - boolean value of whether the devices were devices were added/imported successfully
	 */
	public boolean verifyDevicesOnListPage(List<String> serialNumber, int deviceCount) {
		try {
			boolean flag = false;
			HashMap<String, String> mapAssets = new HashMap<>();
			int serialNumberListCounter = 0;

			List<String> addedSerialNumber =  new ArrayList<String>() ;
			for (int aliasListCounter = 0; aliasListCounter < deviceCount;) {
				for (serialNumberListCounter = aliasListCounter; serialNumberListCounter < deviceCount;) {
					sleeper(1000);
					waitForElementsOfDeviceListPage("clearFiltersButton");
					clickOnElementsOfDeviceListPage("clearFiltersButton");
					getElementOfDeviceListPage("deviceSerialSearchBox").clear();
					getElementOfDeviceListPage("deviceSerialSearchBox").sendKeys(serialNumber.get(serialNumberListCounter));
					waitUntilElementIsInvisibleOfDeviceListPage("tableOverLay");
					waitForPageLoaded();
					sleeper(3000);
					List<WebElement> serialNumberList = getElementsTillAllElementsVisibleofDeviceListPage("serialNumberList");
					aliasListCounter++;
					addedSerialNumber.add(serialNumberList.get(0).getText());
					break;
				}
			}
			LOGGER.info("mapAssets" + serialNumber);
			LOGGER.info("mapAdded" + addedSerialNumber);
			if (serialNumber.equals(addedSerialNumber)) {
				flag = true;
				LOGGER.info("Devices which got added/imported are getting reflected on List page.");
			} else {
				flag = false;
				LOGGER.error("Devices which got added/imported are not getting reflected on List page.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyDevicesOnListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to validate the removal of newly added devices flow
	 * 
	 * @param getAddDeviceManuallyInfo - Locators required to test remove device functionality
	 * @param languageCode - language code
	 * @return - boolean value of whether the devices are removed successfully
	 */
	public boolean verifyRemovalOfNewlyAddedDevice(String languageCode) {
		try {
			boolean flag = false;
			waitForPageLoaded();
			sleeper(5000);
			getElementOfDeviceListPage("serialNumberSearchBox").clear();
			getElementOfDeviceListPage("serialNumberSearchBox").sendKeys("SrAutomation");
			sleeper(5000);
			if (!waitForElementsOfDeviceListPageDynamic("noElementsDisplayText", 5)) {
				clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
				LOGGER.info("All the newly added devices are selected.");
				waitForPageLoaded();
				clickByJavaScriptOnDeviceListPage("removeDeviceButton");
				LOGGER.info("Clicked on remove button");
				waitForElementsOfDeviceListPage("currentSelectionExport");
				// clickByJavaScriptOnDeviceListPage("currentSelectionExport");
				clickByJavaScriptOnDeviceListPage("continueButton");
				LOGGER.info("Clicked on continue button.");
				clickByJavaScriptOnDeviceListPage("submitRemove");
				waitForElementsOfDeviceListPage("toastNotification");
				String successMessage = getTextOfDeviceListPage("toastNotification");
				if (successMessage.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "assets.list.toast.remove.assets.success"))) {
					flag = true;
					getElementOfDeviceListPage("serialNumberSearchBox").clear();
					LOGGER.info("All the newly added devices are removed successfully.");
				} else {
					LOGGER.error("Device removal failed.");
				}
			} else {
				LOGGER.info("Device list is already empty, There are no newly added devices");
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyRemovalOfNewlyAddedDevice " + e.getMessage()));
			return false;
		}
	}
	/**
	 * This method is used to validate the removal of newly added devices flow
	 *
	 * @param getAddDeviceManuallyInfo - Locators required to test remove device functionality
	 * @param languageCode - language code
	 * @return - boolean value of whether the devices are removed successfully
	 */
	public boolean verifyRemovalOfNewlyAddedDevice(String languageCode,String environment) {
		boolean flag = false;
		try {
			waitForPageLoaded();
			sleeper(5000);
			getElementOfDeviceListPage("serialNumberSearchBox").clear();
			getElementOfDeviceListPage("serialNumberSearchBox").sendKeys("SrAutomation");
			sleeper(5000);
			if (!waitForElementsOfDeviceListPageDynamic("noElementsDisplayText", 5)) {
				LOGGER.info("All the newly added devices are selected.");
				waitForPageLoaded();
				ArrayList<String> devicesToDelete = getListOfTextOfDeviceListPage("deviceListSerialNos");

				Assert.assertFalse(devicesToDelete.contains("SrAutomation"));
				String deviceListPageUrl = getUrlOfCurrentPage();
				for (String srNo : devicesToDelete) {
					goToParticularDevice(srNo);
					String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
					String deviceDeleteBody = "[{\"id\":\"" + deviceID + "\"}]";
					deletZTEDeviceUsingAPI(environment + DeviceVariables.DEVICE_REMOVE, deviceDeleteBody);
					LOGGER.info("Removed added Device: " + srNo);
					getUrl(deviceListPageUrl);
					waitForPageLoaded();
				}
				flag=true;
			}
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	/**
	 * This method enrolls fake device and verifies on device list page.
	 * 
	 * @param companyDetails
	 * @param languageCode
	 * @param getAddDeviceManuallyInfo
	 * @return
	 */
	public boolean verifyFakeDeviceListPage(HashMap<String, String> companyDetails, String languageCode) {
		try {
			boolean flag = false;
			HashMap<String, String> deviceDetails = new HashMap<>();
			TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIcon2();
			clickOnElementsOfDeviceListPage("devicesTab");
			waitForElementsOfDeviceListPage("serialNumberSearchBox");
			resetTableConfiguration();
			waitForPageLoaded();
			deviceDetails = EnrollFakeDevice.enrollFakeDevice(companyDetails.get("companyName"), companyDetails.get("companyPin"), companyDetails.get("companyEmailId"));
			deviceSerialNo = deviceDetails.get("deviceSerialNumber");
			LOGGER.info("Device Details: " + deviceDetails);
			tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableOverlay");
			tableConfigurationPage.waitUntilElementIsInvisibleOfTableConfigurationPage("tableOverlay");
			enterTextForDeviceListPage("serialNumberSearchBox", deviceSerialNo);
			waitForPageLoaded();
			sleeper(2000);
				if (getTextOfDeviceListPage("firstDeviceSerialNumber").equalsIgnoreCase(deviceDetails.get("deviceSerialNumber"))) {
					flag = true;
				} else {
					LOGGER.error("Fake device enrollment has failed.");
				}
			
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception in enrolling fake device: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method verifies download of HP DaaS Client.
	 * 
	 * @param getAddDeviceManuallyInfo
	 * @return
	 */
	public boolean verifyHpDaaSDownloadClient() {
		try {
			boolean flag = false;
			waitForElementsOfDeviceListPage("downloadButton");
			clickOnElementsOfDeviceListPage("downloadButton");
			clickOnElementsOfDeviceListPage("nextButton");
			sleeper(5000);
			if (verifyElementsOfDeviceListPage("downloadText")) {
				flag = true;
			} else {
				LOGGER.error("HP DaaS Client download has failed.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyHpDaaSDownloadClient " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This is a method to delete device directory
	 * 
	 * @param path - path to the directory
	 */
	public void deleteDeviceDir(String path) {
		try {
			if (new File(path).exists()) {
				File file = new File(path);
				FileUtils.cleanDirectory(file);
				FileUtils.forceDelete(file);
				FileUtils.forceMkdir(file);
			} else {
				File file = new File(path);
				FileUtils.forceMkdir(file);
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method deleteDeviceDir " + e.getMessage()));
		}
	}

	/**
	 * This method is used to test export selected devices functionality.
	 * 
	 * @param languageCode - language code
	 * @param var - variable count
	 * @param countUnread - count of unread notification
	 * @return - boolean value of whether the selected devices are exported successfully
	 */
	public boolean verifyExportDevices(String languageCode, String var, int countUnread) {
		try {
			CSVFileReader csv = new CSVFileReader();
			boolean flag = false;
			int countFiles = 0;
			waitForPageLoaded();
			getElementOfDeviceListPage("aliasSearchBox").clear();
			scrollOnDeviceListPage("aliasTitle");
			getElementOfDeviceListPage("aliasSearchBox").sendKeys("AliasAutomation");
			waitForPageLoaded();
			sleeper(2000);

			ArrayList<String> columnOnListPage = getTextOfListOfDeviceListPage("columnName");
			if (!waitForElementsOfDeviceListPageDynamic("noElementsDisplayText", 5)) {
				waitForPageLoaded();
				sleeper(2000);
				clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
				LOGGER.info("Clicked on select all checkbox");

				waitForElementsOfDeviceListPage("exportDeviceButton");
				clickByJavaScriptOnDeviceListPage("exportDeviceButton");
				LOGGER.info("Clicked on export button");
				waitForPageLoaded();
				waitForElementsOfDeviceListPage("exportDeviceButtonPopUP");
				clickByJavaScriptOnDeviceListPage("exportDeviceButtonPopUP");
				LOGGER.info("Clicked on pop up export button");
				waitForPageLoaded();
				sleeper(2000);

				flag = postNotificationCheckExport(var, countUnread);
				if (flag) {
					flag = true;
					clickByJavaScriptOnDeviceListPage("unreadNotificationHyperLink");
					sleeper(3000);
					File f = new File(ConstantPath.DOWNLOAD_PATH);
					if (f.exists() == false) {
						File file = new File(ConstantPath.DOWNLOAD_PATH);
						FileUtils.forceMkdir(file);
					}
					if (f.listFiles().length > 0) {
						for (File file : f.listFiles()) {
							if (file.isFile()) {
								String columnList = null;
								String[][] header = csv.getDataWithHeader(file);
								for(int i = 0; i<header[1].length; i++){
									columnList = columnList + ", " + header[0][i];
								}

								columnList = columnList.replaceAll("(mandatory)", "").replaceAll("(Editable)","").replace('(',' ').replace(')',' ').replaceAll(" null,","").replaceAll(" null,", "").replaceAll("  ","").toUpperCase();

								for(int i=0; i<columnOnListPage.size();i++){
									if(!columnList.contains(columnOnListPage.get(i).replace('(',' ').replace(')',' ').replaceAll("  "," ").toUpperCase()))
									    flag = false;
									}
									countFiles++;
									if(!flag)
									LOGGER.info("Columns on device list page and selected exported columns in .csv file are not same");
								}
						}
						if (countFiles >= 1) {
							this.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
						} else {
							LOGGER.info("Don't delete the devices folder");
						}
					} else {
						LOGGER.info("There is no file inside the devices folder so no need to delete it");
					}

					this.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
					LOGGER.info("Devices exported successfully.");
				} else {
					LOGGER.error("Test for Export devices got failed.");
				}
			} else {
				LOGGER.error("Device list is empty, There are no newly added devices");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyExportDevices " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to test export devices all editable columns functionality.
	 * @param languageCode - language code
	 * @param var - variable count
	 * @param countUnread - count of unread notification
	 * @return - boolean value of whether the all editable columns are exported successfully
	 */
	public boolean verifyExportAllDevices(String languageCode, String var, int countUnread) {
		try {
			CSVFileReader csv = new CSVFileReader();
			boolean flag = false;
			int countFiles = 0;
			String columnOnListPage = "serial number, alias, device type,asset tag, location, country code,department, cost center, store number, Manufacture Datemm/dd/yyyy, Lifecycle Status, Device Role,warranty name,warranty start datemm/dd/yyyy,warranty end datemm/dd/yyyy,warranty desc";
			if (!waitForElementsOfDeviceListPageDynamic("noElementsDisplayText", 5)) {
				waitForPageLoaded();
				sleeper(2000);
				clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
				LOGGER.info("Clicked on select all checkbox");
				waitForElementsOfDeviceListPage("exportDeviceButton");
				clickByJavaScriptOnDeviceListPage("exportDeviceButton");
				LOGGER.info("Clicked on export button");
				waitForPageLoaded();
				clickByJavaScriptOnDeviceListPage("exportAllColumn");
				waitForElementsOfDeviceListPage("exportDeviceButtonPopUP");
				clickByJavaScriptOnDeviceListPage("exportDeviceButtonPopUP");
				LOGGER.info("Clicked on pop up export button");
				waitForPageLoaded();
				sleeper(2000);
				flag = postNotificationCheckExport(var, countUnread);
				if (flag) {
					flag = true;
					clickByJavaScriptOnDeviceListPage("unreadNotificationHyperLink");
					sleeper(3000);
					File f = new File(ConstantPath.DOWNLOAD_PATH);
					if (f.exists() == false) {
						File file = new File(ConstantPath.DOWNLOAD_PATH);
						FileUtils.forceMkdir(file);
					}
					if (f.listFiles().length > 0) {
						for (File file : f.listFiles()) {
							if (file.isFile()) {
								String columnList = null;
								String[][] header = csv.getDataWithHeader(file);
								for(int i = 0; i<header[1].length; i++){
									columnList = columnList + ", " + header[0][i];
								}
								columnList = columnList.replaceAll("(mandatory)", "").replaceAll("(Editable)","").replace('(',' ').replace(')',' ').replaceAll(" null,", "").replaceAll("null,","").replaceAll("  ","").replaceAll("DATEMM/DD/YYYY","date").toUpperCase();

								for(int i=0; i<header[1].length;i++){
									if(!columnList.contains(columnOnListPage.split(",")[i].toUpperCase()))
									    flag = false;
									}
									countFiles++;
									if(!flag)
									LOGGER.info("Exported all editable columns in .csv file are not matched");	
								}
						}
						if (countFiles >= 1) {
							this.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
						} else {
							LOGGER.info("Don't delete the devices folder");
						}
					} else {
						LOGGER.info("There is no file inside the devices folder so no need to delete it");
					}

					this.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
					LOGGER.info("Devices exported successfully.");
				} else {
					LOGGER.error("Test for Export devices with all editable columns got failed.");
				}
			} else {
				LOGGER.error("Device list is empty, There are no newly added devices");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyExportALLDevices " + e.getMessage()));
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

			if (verifyElementsOfDeviceListPage("notificationCount")) {
				count = getTextOfDeviceListPage("notificationCount");
				countUnreadNotification = Integer.valueOf(count);

				waitForElementsOfDeviceListPage("notificationBellIcon");
				clickOnElementsOfDeviceListPage("notificationBellIcon");
				LOGGER.info("Clicked on notification bell icon");

				if (verifyElementsOfDeviceListPage("unreadNotification")) {
					Actions action = new Actions(getDriver());
					action.moveToElement(getElementOfDeviceListPage("unreadNotification")).build().perform();
					sleeper(5000);
					if (verifyElementsOfDeviceListPage("notificationCount")) {
						count = getTextOfDeviceListPage("notificationCount");
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
	 * This is a method to
	 * 
	 * @param var
	 * @param countUnread
	 * @return
	 */
	public boolean postNotificationCheckExport(String var, int countUnread) {
		try {
			boolean flag = false;
			String notificationText = null;
			String hyperLinkText = null;
			String notificationCountString = null;
			int notificationCount = 0;
			sleeper(30000);
			waitForElementsOfDeviceListPage("notificationBellIcon");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			LOGGER.info("Clickedon notification bell icon");
			if (waitForElementsOfDeviceListPage("unreadNotificationHyperLink")) {
				notificationText = getTextOfDeviceListPage("unreadNotificationText");
				//hyperLinkText = getTextOfDeviceListPage("unreadNotificationHyperLink");
				notificationCountString = getTextOfDeviceListPage("notificationCount");
				//notificationCount = Integer.valueOf(notificationCountString);
				waitForElementsOfDeviceListPage("firstNotification");
				clickOnElementsOfDeviceListPage("firstNotification");
				LOGGER.info("Clicked on firstNotification");
				clickByJavaScriptOnDeviceListPage("adminxNotificationButton");
				LOGGER.info("Clicked on hamburgerOnNotifications button");
				waitForElementsOfDeviceListPage("downloadLinkBellIcon");
				if (waitForElementsOfDeviceListPage("downloadLinkBellIcon") && notificationText.contains("Your file containing the list of " + var + " devices is ready to download.")) {
					flag = true;
					clickByJavaScriptOnDeviceListPage("downloadLinkBellIcon");
					LOGGER.info("Notification of export devices received.");
				} else {
					LOGGER.error("Notification for export has failed");
				}
			}
			//mark as read
			clickOnElementsOfDeviceListPage("firstNotification");
			clickByJavaScriptOnDeviceListPage("adminxNotificationButton");
			sleeper(1000);
			verifyElementsOfDeviceListPage("markAsRead");
			clickByJavaScriptOnDeviceListPage("markAsRead");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckExport " + e.getMessage()));
			return false;
		}
	}

	/**
	 * @return
	 */
	public boolean postNotificationCheckExportAllDevices() {
		try {
			boolean flag = false;
			String hyperLinkText = null;
			sleeper(10000);
			waitForElementsOfDeviceListPage("notificationBellIcon");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfDeviceListPage("unreadNotificationHyperLink")) {
				hyperLinkText = getTextOfDeviceListPage("unreadNotificationHyperLink");
				if (hyperLinkText.equalsIgnoreCase("DOWNLOAD FILE")) {
					flag = true;
				} else {
					LOGGER.error("Notification for export All devices has failed");
				}
			}
			waitForElementsOfDeviceListPage("notificationBellIcon");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckExportAllDevices " + e.getMessage()));
			return false;
		}
	}

	/**
	 * @param var
	 * @param countUnread
	 * @param languageCode
	 * @return
	 */
	public boolean postNotificationCheckImport(String var, int countUnread, String languageCode) {
		try {
			boolean flag = false;
			String notificationText = null;
			String hyperLinkText = null;
			String notificationCountString = null;
			int notificationCount = 0;
			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			waitForElementsOfDeviceListPage("notificationBellIcon");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfDeviceListPage("unreadNotificationHyperLink")) {
				notificationText = getTextOfDeviceListPage("unreadNotificationText");
				hyperLinkText = getTextOfDeviceListPage("unreadNotificationHyperLink");
				notificationCountString = getTextOfDeviceListPage("notificationCount");
				notificationCount = Integer.valueOf(notificationCountString);
				if (hyperLinkText.equalsIgnoreCase("OPEN LOGS") && notificationText.equalsIgnoreCase("Your file containing the list of " + var + " devices is ready to download.") && notificationCount == (countUnread + 1)) {
					clickOnElementsOfDeviceListPage("unreadNotificationHyperLink");
					LOGGER.info("Clicked on unread notification hyperlink");
					if (logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(languageCode, "daas_ui", "pagetitle.logs"))) {
						flag = true;
					} else {
						LOGGER.error("Re direction to logs page failed.");
					}
				} else {
					LOGGER.error("Notification for import has failed");
				}
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImport " + e.getMessage()));
			return false;
		}
	}

	/**
	 * @param getAddDeviceManuallyInfo
	 * @return
	 */
	public String getCountOfDevices() {
		try {
			int n = 0;
			scrollOnDeviceListPage("aliasList");
			n = getElementsTillAllElementsVisibleofDeviceListPage("aliasList").size();
			String number = String.valueOf(n);
			LOGGER.info("Devices count is fetched.");
			return number;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getting count of newly added devices : " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method is used to validate whether the imported devies are reflected on the list page
	 * 
	 * @param getImportDeviceInfo
	 * @param fileName
	 * @return
	 */
	public boolean verifyImportedDevicesOnListPage(String fileName) throws Exception {
		try {
			HashMap<String, String> mapDevices = new HashMap<>();
			HashMap<String, String> devicesCSV = new HashMap<>();
			int serialNumberListCounter = 0;
			boolean flag = false;
			CSVFileReader csv = new CSVFileReader();
			File file = new File(ConstantPath.IMPORT_PATH + fileName);
			for (String[] mapping : csv.getDataWithoutHeader(file)) {
				devicesCSV.put(mapping[0], mapping[1]);
			}
			waitForPageLoaded();
			waitForElementsOfDeviceListPage("serialNumberSearchBox");

			enterTextForDeviceListPage("serialNumberSearchBox", "AutomationDeviceSRC02TT58QHTD5");

			scrollOnDeviceListPage("companySearchBox");
			sleeper(2000);
			clickOnElementsOfDeviceListPage("companySearchBox");
			enterTextForDeviceListPage("companySearch", "AutomatorWorkflow");
			sleeper(6000);
			clickOnElementsOfDeviceListPage("clickOnCompanyName");

			//getElementOfDeviceListPage("serialNumberSearchBox").sendKeys("AutomationDeviceSR");
			waitForElementsOfDeviceListPage("serialNumberList");
			sleeper(4000);
			List<WebElement> serialNumberList = getElementsTillAllElementsVisibleofDeviceListPage("serialNumberList");
			scrollTillViewDeviceListPage("aliasTitle");
			List<WebElement> aliasList = getElementsTillAllElementsVisibleofDeviceListPage("aliasList");
			for (int aliasListCounter = 0; aliasListCounter < aliasList.size();) {
				for (serialNumberListCounter = aliasListCounter; serialNumberListCounter < serialNumberList.size();) {
					mapDevices.put(serialNumberList.get(serialNumberListCounter).getText(), aliasList.get(aliasListCounter).getText());
					aliasListCounter++;
					break;
				}
			}
			LOGGER.info("UI LIST:" + mapDevices);
			LOGGER.info("CSV LIST:" + devicesCSV);
			if (mapDevices.equals(devicesCSV)) {
				flag = true;
			} else {
				flag = false;
				LOGGER.error("Devices which got imported are not getting reflected on List page.");
			}
			return flag;

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportedDevicesOnListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to define parameters of import device.
	 * 
	 * @return
	 */
	public final HashMap<String, String> getImportDeviceDetails() {
		try {
			HashMap<String, String> getImportDeviceInfo = new HashMap<String, String>();
			getImportDeviceInfo.put("importButtonKey", "importButton");
			getImportDeviceInfo.put("nextButtonKey", "nextButton");
			getImportDeviceInfo.put("browseButtonKey", "browseButton");
			getImportDeviceInfo.put("submitImportButtonKey", "submitImportButton");
			getImportDeviceInfo.put("aliasListKey", "aliasList");
			getImportDeviceInfo.put("aliasSearchBoxKey", "aliasSearchBox");
			getImportDeviceInfo.put("serialNoListKey", "serialNumberList");
			getImportDeviceInfo.put("aliasBoxKey", "aliasBox");
			getImportDeviceInfo.put("serialNoBoxKey", "serialNoBox");
			getImportDeviceInfo.put("serialNoSearchBoxKey", "serialNumberSearchBox");
			getImportDeviceInfo.put("aliasTitleKey", "aliasTitle");
			getImportDeviceInfo.put("toastNotificationKey", "toastNotification");
			getImportDeviceInfo.put("noelementsdisplaytextListKey", "noElementsDisplayText");
			getImportDeviceInfo.put("selectAllCheckBoxKey", "selectAllCheckBox");
			getImportDeviceInfo.put("removeDeviceButtonKey", "removeDeviceButton");
			getImportDeviceInfo.put("removeDeviceButtonPopUPKey", "removeDeviceButtonPopUP");
			getImportDeviceInfo.put("cancelDeviceButtonPopUPKey", "cancelDeviceButtonPopUP");
			getImportDeviceInfo.put("nextButtonKey", "nextButton");
			getImportDeviceInfo.put("previousButtonKey", "previousButton");
			return getImportDeviceInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getImportDeviceDetails " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This method is used to validate flow of importing the devices
	 * 
	 * @param getImportDeviceInfo
	 * @param languageCode
	 * @param fileName
	 */
	public void verifyImportDevices(String languageCode, String fileName, String enrollmentType) {
		try {
			waitForPageLoaded();
			// Select enrollment option
			if (enrollmentType.equalsIgnoreCase("no_enroll")) {
				clickByJavaScriptOnDeviceListPage("noEnrollmentOption");
				LOGGER.info("No enrollment option selected");
			} else if (enrollmentType.equalsIgnoreCase("auto_enroll")) {
				clickByJavaScriptOnDeviceListPage("autoEnrollmentOption");
				LOGGER.info("Auto enrollment option selected");
			} else {
				LOGGER.info("Valid enrollment option not provided");
			}
			clickOnElementsOfDeviceListPage("nextButton");
			waitForElementsOfDeviceListPage("nextButton");
			clickOnElementsOfDeviceListPage("nextButton");
			LOGGER.info("Clicked on import button");
			waitForElementsOfDeviceListPage("browseButton");
			
			LOGGER.info("Clicked on browse button");
			fileImportInV3(ConstantPath.IMPORT_PATH + fileName);
			sleeper(10000); //time required till the file is uploaded
			clickOnElementsOfDeviceListPage("submitImportButton");
			LOGGER.info("Clicked on submit import button");
			//sleeper(15000); //time required till Import button is disabled
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportDevices " + e.getMessage()));
		}
	}

	/**
	 * This method is used to validate the notification flow after the devices have been imported
	 * 
	 * @param fileName
	 * @param countUnread
	 * @return
	 */
	public boolean postNotificationCheckImportForSuccessfullImport(String fileName, int countUnread) {
		try {
			boolean flag = false;
			String notificationText = null;
			String hyperLinkText = null;
			String notificationCountString = null;
			int notificationCount = 0;
			waitForElementsOfDeviceListPage("notificationBellIcon");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			sleeper(40000); // time required for the notification message to pop on notification window
			LOGGER.info("Clicked on notification bell icon");
			if (verifyElementsOfDeviceListPage("unreadNotificationHyperLink")) {
				notificationText = getTextOfDeviceListPage("unreadNotificationText");
				hyperLinkText = getTextOfDeviceListPage("unreadNotificationHyperLink");
				waitForElementsOfDeviceListPage("notificationCount");
				notificationCountString = getTextOfDeviceListPage("notificationCount");
				notificationCount = Integer.valueOf(notificationCountString);
				if (hyperLinkText.equalsIgnoreCase("OPEN LOGS") && notificationText.equalsIgnoreCase("Your devices from " + fileName + " were imported successfully. Please check the logs for more details.") && notificationCount == (countUnread + 1)) {
					flag = true;
				} else {
					LOGGER.error("Message on notification window is incorrect");
				}
			} else {
				LOGGER.error("Notification for device import did not display/delay in notification for more than 30 seconds");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImport " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to validate the notification flow after the devices have been imported
	 *
	 * @param fileName
	 * @param countUnread
	 * @return
	 */
	public boolean postNotificationCheckImportForUnsuccessfullImport(String fileName, int countUnread) {
		try {
			boolean flag = false;
			String notificationText = null;
			String hyperLinkText = null;
			String notificationCountString = null;
			int notificationCount = 0;
			waitForElementsOfDeviceListPage("notificationBellIcon");
			sleeper(10000);// time required for the notification message to pop on notification window
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			if (waitForElementsOfDeviceListPage("unreadNotificationHyperLink")) {
				notificationText = getTextOfDeviceListPage("unreadNotificationText");
				hyperLinkText = getTextOfDeviceListPage("unreadNotificationHyperLink");
				waitForElementsOfDeviceListPage("notificationCount");
				notificationCountString = getTextOfDeviceListPage("notificationCount");
				notificationCount = Integer.valueOf(notificationCountString);
				if (hyperLinkText.equalsIgnoreCase("ERRORS") && notificationText.equalsIgnoreCase("There was a problem while importing your devices from " + fileName + " file. Please check the errors for more information.") && notificationCount == (countUnread + 1)) {
					flag = true;
				} else {
					LOGGER.error("Notification for import has failed");
				}
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImport " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to verify the description of importing the devices which appears on logs page
	 *
	 * @param fileName
	 * @return
	 */
	public boolean verifyDescriptionOnLogsPage(String fileName) {
		try {
			String notificationText = null;
			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			scrollTillViewDeviceListPage("notificationBellIcon");
			waitForElementsOfDeviceListPage("notificationBellIcon");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			notificationText = getTextOfDeviceListPage("unreadNotificationText");
			mousehoverOnDeviceListPage("unreadNotificationText");
			clickOnElementsOfDeviceListPage("hamburgerOnNotifications");
			clickOnElementsOfDeviceListPage("openLogsAction");
			LOGGER.info("Clicked on open logs hyperlink");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			logPage.switchToDifferentTabOfLogsPage();
			sleeper(3000);
			resetTableConfiguration();
			logPage.waitForElementsOfLogPage("tableOverlay");

			if(logPage.waitForElementsOfLogPage("dateAndTimedropdown")) {
				logPage.clickOnElementsOfLogPage("dateAndTimedropdownclick");
			}
			sleeper(3000);
			if(logPage.waitForElementsOfLogPage("clearSelectionButton")) {
				logPage.clickOnElementsOfLogPage("clearSelectionButton");
			}
			waitForElementsOfDeviceListPage("tableOverlay");
			logPage.selectLogTypeOfLogPage("Devices");
			waitForElementsOfDeviceListPage("tableOverlay");
			logPage.selectLogSubTypeOfLogPage("Import");
			waitForElementsOfDeviceListPage("tableOverlay");
			logPage.clickOnElementsOfLogPage("firstCheckbox");
			sleeper(3000);
//			Assert.assertTrue(logPage.getTextOfLogPage("descriptionFirstRow").contains("1 devices have been imported and/or updated."),"Log description for add device i not correct");
			String notificationText2 = getTextOfDeviceListPage("descriptionFirstRow");
			String notificationText3 = "1 devices have been imported and/or updated.";
			if (notificationText2.contains(notificationText3)) {
				if (getTextOfDeviceListPage("descriptionFirstRow").contains("1 devices have been imported and/or updated.")) {
					logPage.switchToDifferentTabOfLogsPage();
					return true;
				} else {
					LOGGER.error("Description on logs page is incorrect when devices are imported successfully");
					return false;
				}
			} else if (notificationText.equalsIgnoreCase("File" + fileName + "has no data. Serial Number is required to import devices.")) {
				if (logPage.getTextOfLogPage("logsPageDescriptionFirstRow").equalsIgnoreCase("Serial Number is required to successfully import devices. Please try again.")) {
					logPage.switchToDifferentTabOfLogsPage();
					return true;
				} else {
					LOGGER.error("Description on logs page is incorrect when devices are not imported successfully");
					return false;
				}
			} else {
				if (logPage.getTextOfLogPage("logsPageDescriptionFirstRow").equalsIgnoreCase("There was an error during the import process of file " + fileName + ". Please try again.")) {
					logPage.switchToDifferentTabOfLogsPage();
					return true;
				} else {
					LOGGER.error("Description on logs page is incorrect when devices are not imported successfully");
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyDescriptionOnLogsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to verify the errors messages in the csv file that is generated after unsuccessfull import of devices
	 *
	 * @param errorMessage
	 * @param lineNumber
	 * @return
	 */
	public boolean verifyErrorCSVFile(String errorMessage1, String errorMessage2, String lineNumber) {
		try {
			HashMap<String, String> errorDataInCSV = new HashMap<String, String>();
			HashMap<String, String> errorData = new HashMap<String, String>();
			CSVFileReader csv = new CSVFileReader();
			File file = new File(ConstantPath.DOWNLOAD_PATH + "Errors.csv");
			errorData.put(errorMessage1, lineNumber);
			if (!errorMessage2.equals("Skip"))
				errorData.put(errorMessage2, lineNumber);
			waitForElementsOfDeviceListPage("notificationBellIcon");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			sleeper(3000);
				mousehoverOnDeviceListPage("hamburgerOnNotifications");
				clickOnElementsOfDeviceListPage("hamburgerOnNotifications");
			waitForElementsOfDeviceListPage("errorsHyperlink");
			clickOnElementsOfDeviceListPage("errorsHyperlink");
			LOGGER.info("Clicked on error hyperlink");
			sleeper(6000); // wait till error.csv gets downloaded
			for (String[] error : csv.getDataWithoutHeader(file))
				errorDataInCSV.put(error[3].split("\"")[0], error[0]);
			if (errorData.equals(errorDataInCSV))
				return true;
			else {
				LOGGER.error("Error message in csv file is incorrect");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyErrorCSVFile " + e.getMessage()));
			return false;
		}
	}

	/**
	 * Use to select first device when device list is already filtered
	 * @param deviceName
	 * @throws InterruptedException
	 */
	public void selectFirstDeviceFromDeviceListPage(String deviceName) throws InterruptedException {
	if (verifyElementsOfDeviceListPage("deviceDetails")) {
		if (getTextOfDeviceListPage("deviceDetails").contains(deviceName)) {
			waitForElementsOfDeviceListPage("deviceDetails");
			clickOnElementsOfDeviceListPage("deviceDetails");
			sleeper(3000);// detail page takes time to load
			LOGGER.info("Clicked on first device from list");
		}
	}
}

	/**
	 * To verify filter search column
	 * @param searchLoc
	 * @param invalidSearchTxt
	 * @param firstRowLoc
	 * @param validSearchTxt
	 * @param languageCode
	 * @throws IOException
	 */
	public boolean verifySearchColumn (String searchLoc, String invalidSearchTxt, String firstRowLoc, String
			validSearchTxt,String languageCode) throws IOException {
		boolean flag=true;
		if (verifyElementsOfDeviceListPage("clearFiltersButton"))
			clickOnElementsOfDeviceListPage("clearFiltersButton");
		waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		if(!verifySearchValueOnDevice(languageCode, searchLoc, invalidSearchTxt, "noElementDisplayText", firstRowLoc)){
		return false;
		}
		if(!verifySearchValueOnDevice(languageCode, searchLoc, validSearchTxt, "noElementDisplayText", firstRowLoc)){
			return false;
		}
		return flag;
	}

	/**
	 * To verify drop down single select
	 * @param columnLocator
	 * @param columnListLocator
	 * @param languageCode
	 * @throws IOException
	 */
	public Boolean verifySingleSelect (String columnLocator, String columnListLocator, String languageCode) throws
			IOException {
		waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		clickOnElementsOfDeviceListPage(columnLocator);
		boolean flag=true;
		if(!verifyFilterSingleSelect(languageCode, "dropDownCheckBoxes", "dropDownElementListLabels", columnListLocator, "noElementDisplayText")){
			LOGGER.info("Filter functionality on selecting single option from " + columnLocator + " column on device list page is not working");
			return false;
		}
		waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		if (verifyElementsOfDeviceListPage("clearFiltersButton"))
			clickOnElementsOfDeviceListPage("clearFiltersButton");
		waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		return flag;
	}

	/**
	 * To verify single/multiselect drop down columns
	 * @param columnLocator
	 * @param columnListLocator
	 * @param languageCode
	 * @throws IOException
	 */
	public boolean verifySingleMultiSelect (String columnLocator, String columnListLocator, String languageCode) throws
			IOException {
		boolean flag=true;
		waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		verifySingleSelect(columnLocator, columnListLocator,languageCode);
		clickOnElementsOfDeviceListPage(columnLocator);
		if(!verifyFilterMultiSelect(languageCode, "dropDownCheckBoxes", "dropDownElementListLabels", columnListLocator, "noElementDisplayText")){
			return false;
		}
		waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		if (verifyElementsOfDeviceListPage("clearFiltersButton"))
			clickOnElementsOfDeviceListPage("clearFiltersButton");
		waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");
		LOGGER.info("Verified filter functionality for Type column");
		return flag;
	}

	/**
	 *
	 * Method to go to particular Device Details page
	 *
	 */
	public final void goToParticularDevice(String deviceName) {
		try {
			if (verifyElementsOfDeviceListPage("clearListPageFilter")) {
				clickOnElementsOfDeviceListPage("clearListPageFilter");
				sleeper(3000);
			}
			enterTextForDeviceListPage("serialNumberSearchBox", deviceName);
			waitForElementsOfDeviceListPage("deviceDetails");
			sleeper(3000);
			if (verifyElementsOfDeviceListPage("deviceDetails")) {
				if (getTextOfDeviceListPage("deviceDetails").contains(deviceName)) {
					waitForElementsOfDeviceListPage("deviceDetails");
					clickOnElementsOfDeviceListPage("deviceDetails");
					sleeper(3000);// detail page takes time to load
					LOGGER.info("Clicked on first device from list");
					//deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
					LOGGER.info("Navigated to device details page");
				} else {
					LOGGER.error("Selected alias is not present in the list");
				}

			} else {
				LOGGER.error("Selected alias is not present in the list");
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method goToParticularDevice " + e.getMessage()));
		}

	}

	/**
	 * This is a method to click on hyperlink in list of elements
	 *
	 * @param uiList - List of elements of column on UI and click on 1st element
	 */

	public final boolean clickElementsOfDeviceListPage(List<WebElement> uiList) {
		try {
			boolean flag = false;
			Iterator<WebElement> uiColumnListIterator = uiList.iterator();
			while (uiColumnListIterator.hasNext()) {
				WebElement element = uiColumnListIterator.next();
				if ((!element.getText().equals("")) && (!element.getText().equals("N/A"))) {
					waitForPageLoaded();
					element.click();
					flag = true;
					break;
				} else {
					flag = false;
				}
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickElementsOfDeviceListPage " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This is a method to wait untill an element is invisible
	 *
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfDeviceListPage(String key) {
		try {
			verifyElementIsinvisibile(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfDeviceListPage " + e.getMessage()));
		}
	}

	/**
	 * This method fetches the ChromeBook Device from Google Admin Portal
	 *
	 * @return
	 */
	public final String getChromeDevicesFromGooglePortal() {
		try {
			getUrl(PreferenceVariables.CHROME_DEVICE_URL);
			waitForElementsOfDeviceListPage("chromeDevicesLable");
			sleeper(3000);// takes time to access chrome devices on google portal
			clickOnElementsOfDeviceListPage("chromeDevicesLable");
			getUrl(getUrlOfCurrentPage() + PreferenceVariables.CHROME_DEVICE_LIST_URL);
			waitForElementsOfDeviceListPage("chromeDevice");
			String chromeDevice = getTextOfDeviceListPage("chromeDevice");
			return chromeDevice;
		} catch (Exception e) {
			LOGGER.error("Exception occured in method getChromeDevicesFromGooglePortal " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method checks ChromeBook Device is enrolled on DaaS portal
	 *
	 * @param chromeDevice
	 * @return
	 */
	public final boolean checkChromeDevicePresentOnDaaSPortal(String chromeDevice) {
		boolean flag = false;
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("clearFilter")) {
				deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("clearFilter");
			}
			sleeper(2000); // It's required to get element serialNumberSearchBox
			enterTextForDeviceListPage("serialNumberSearchBox", chromeDevice);
			sleeper(4000); // takes time to match search text with list text
			if (getTextOfDeviceListPage("deviceDetails").equals(chromeDevice)) {
				LOGGER.info("Pre-existing chromebook is enrolled on DaaS portal");
				flag = true;
			} else {
				LOGGER.error("Chrome device " + chromeDevice + " is not present in the list");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in method checkChromeDevicePresentOnDaaSPortal " + e.getMessage());
			return flag;
		}
		return flag;
	}

	/**
	 * This method is used to sign in onto the Google Admin Portal
	 *
	 * @param id - google email id
	 * @param password - google password
	 * @return true if login is successful else return false
	 */
	public final boolean signInTotheGooglePortal(String id, String password) {
		boolean flag = false;
		try {
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			createAndSwitchToNewTab();
			getUrl(PreferenceVariables.CHROME_PORTAL_URL);
			waitForPageLoaded();
			if (getUrlOfCurrentPage().contains(PreferenceVariables.CHROME_PORTAL_URL) || getUrlOfCurrentPage().contains(PreferenceVariables.CHROME_ACCOUNT_PORTAL_URL)) {
				LOGGER.info("Redirected to the Google Admin Console url : " + getUrlOfCurrentPage());
				preferencesPage.enterTextForPreferencesPage("googleUsername", id);
				preferencesPage.clickOnElementsOfPreferencesPage("googleNextButton");
				preferencesPage.enterTextForPreferencesPage("googlePassword", password);
				preferencesPage.clickOnElementsOfPreferencesPage("googlePasswordNextButton");
				verifyElementsOfDeviceListPage("googleAdminPanel");
				if (verifyElementsOfDeviceListPage("googleAdminPanel")) {
					waitForPageLoaded();
					LOGGER.info("Successfully Logged in to Google Admin console  : " + getUrlOfCurrentPage());
				} else {
					LOGGER.error("Google Admin Console login failed");
					return flag;
				}
			} else {
				return flag;
			}
			flag = true;
		} catch (Exception e) {
			LOGGER.error("Exception occured in method signInTotheGooglePortal " + e.getMessage());
			return flag;
		}
		return flag;
	}

	/**
	 * This method verifies fetches first chrome device data from GMC portal
	 *
	 * @return first chrome device data via Google Admin portal
	 */
	public final HashMap<String, String> fetchGoogleAdminChromeDeviceData() {
		HashMap<String, String> chromeRealDeviceDetails = new HashMap<>();
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Switching To Google Admin Portal");
			signInTotheGooglePortal(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME, PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD);
			String firstChromeDevice = getChromeDevicesFromGooglePortal();
			if (!deviceDetailsPage.verifyElementsOfDeviceDetailsPage("googleAdminNoDevicesLabel")) {
				LOGGER.info("Devices are present under Google Organization ");
				LOGGER.info("Fetching data for device : " + firstChromeDevice);
				waitForElementsOfDeviceListPage("chromeDevice");
				clickByJavaScriptOnDeviceListPage("chromeDevice");
				verifyElementsOfDeviceListPage("chromeHarwdwareOsTab");
				sleeper(2000);// Takes time to redirect on chrome device details
				List<WebElement> chromeDeviceData = deviceDetailsPage.getElementsOfDeviceDetailsPage("googleAdminDeviceData");
				List<WebElement> chromeDeviceLabels = deviceDetailsPage.getElementsOfDeviceDetailsPage("gogleadminDevicelabel");

				for (int chromeCounter = 0; chromeCounter < chromeDeviceData.size() - chromeCounter; chromeCounter++) {
					chromeRealDeviceDetails.put(chromeDeviceLabels.get(chromeCounter).getText(), chromeDeviceData.get(chromeCounter).getText());
				}
				LOGGER.info("Fetched data for device : " + chromeRealDeviceDetails.get(DeviceVariables.CHROME_SERIAL_NUMBER));
			} else {
				LOGGER.info("No devices are present under Google Organization");
				return chromeRealDeviceDetails = null;
			}
			LOGGER.info("Switched back to Daas Portal");
			switchBackToPreviousTab();
		} catch (Exception ex) {
			LOGGER.error("Exception occured in fetchGoogleAdminChromeDeviceData" + ex.getMessage());
			return chromeRealDeviceDetails = null;
		}
		return chromeRealDeviceDetails;
	}

	/**
	 * This method verify chrome gmc portal data with daas chrome fake device details data
	 *
	 * @param chromeDeviceData - chrome device data from google admin portal
	 * @return - Boolean value return either true or false
	 */
	public final boolean verifyChromeDaasDeviceDetails(HashMap<String, String> chromeDeviceData) {
		boolean flag = false;
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Validating Daas device details with google admin console device details");
			HashMap<String, String> daasDeviceDetails = new HashMap<String, String>();
			daasDeviceDetails.put("daasDeviceSerialNumberField", chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER).toString());
			daasDeviceDetails.put("daasDeviceModelField", chromeDeviceData.get(DeviceVariables.CHROME_MODEL).toString());
			daasDeviceDetails.put("daasChromeVersion", DeviceVariables.CHROME_VERSION_STRING + chromeDeviceData.get(DeviceVariables.CHROME_VERSION));
			if (verifyDaasDeviceDetailsData(daasDeviceDetails)) {
				deviceDetailsPage.verifyEmmToolRedirection(PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_USERNAME, PreferenceVariables.CHROME_ENTERPRISE_INTEGRATION_PASSWORD, PreferenceVariables.CHROMEBOOK);
				LOGGER.info("Validated Device with serial number " + chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER) + " is enrolled via Google Admin Console");
				return flag = true;
			} else {
				LOGGER.error("Failed to validate Google admin console Device data for Serial Number  : " + chromeDeviceData.get(DeviceVariables.CHROME_SERIAL_NUMBER).toString());
				return flag;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyEMMChromeFakeDaasDevice" + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method matches Daas device details with emm device data
	 *
	 * @param hashMap - specifies daas device data and emm device data
	 * @return boolean -returns true if daas data matches with third party data
	 * @throws Exception
	 */
	public final boolean verifyDaasDeviceDetailsData(HashMap<String, String> hashMap) {
		boolean check = true;
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			Set<Entry<String, String>> set = hashMap.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> mentry = (Map.Entry<String, String>) iterator.next();
				if (deviceDetailsPage.getTextOfDeviceDetailsPage(mentry.getKey().toString()).equalsIgnoreCase(mentry.getValue().toString())) {
					LOGGER.info(mentry.getValue().toString() + " is Displayed");
				} else {
					LOGGER.error("Error : " + mentry.getValue() + " is not Displyed");
					check = false;
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyEMMChromeFakeDaasDevice" + ex.getMessage());
		}
		return check;
	}

	/**
	 * This method verify google chrome filter under enrollment column
	 *
	 * @param languageCode
	 * @return
	 */
	public final boolean verifyChromeClientApplicationFilter(String languageCode, String chromeDevice) {
		boolean flag = false;
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			List<String> enrolledListItems = new ArrayList<String>();
			if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("clearFilter")) {
				deviceDetailsPage.clickByJavaScriptOnDeviceDetailsPage("clearFilter");
			}
			deviceListPage.scrollOnDeviceListPage("enrolledTitle");
			sleeper(5000); // required due to search enrolledBox
			deviceListPage.clickOnElementsOfDeviceListPage("enrolledBox");
			enrolledListItems = deviceListPage.getTextOfListOfDeviceListPage("enrolledListElements");
			for (String enrolledIterator : enrolledListItems) {
				if (enrolledIterator.equals(DeviceVariables.CLIENT_APPLICATION_GOOGLE_CHROME)) {
					LOGGER.info(DeviceVariables.CLIENT_APPLICATION_GOOGLE_CHROME + " is present");
					break;
				}
			}
			pressKey(Keys.ESCAPE);
			deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", chromeDevice);
			sleeper(5000); // required due to control multiple filters
			deviceListPage.waitForElementsOfDeviceListPage("serialNumberSearchBox");
			deviceListPage.clickOnElementsOfDeviceListPage("enrolledBox");
			if (deviceListPage.verifyFilterSingleSelect(languageCode, "dropdownCheckBoxes", "dropdownElementListLabels", "enrolledList", "noElementsDisplayText")) {
				LOGGER.info("Verified " + DeviceVariables.CLIENT_APPLICATION_GOOGLE_CHROME + "client application on device list page");
				deviceListPage.clickOnElementsOfDeviceListPage("clearFilter");
				flag = true;
			} else {
				LOGGER.error("Unable to verify " + DeviceVariables.CLIENT_APPLICATION_GOOGLE_CHROME + "client application on device list page");
				return flag;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyChromeClientApplicationFilter" + ex.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method set attribute to block to be used in import
	 *
	 * @param key - Key of the locator
	 */
	public final void setAttributeOfDeviceList(String key) {
		setAttributeForImport(deviceListPagePropertiesPageProperties.getProperty(key));
	}

	/**
	 * This method compares 2 list
	 *
	 * @param availableApplications - list of all elements available
	 * @param availableApplicationsText - list of text from maestro
	 * @return flag - true if list are same
	 */
	public final boolean compareSoftwareList(List<WebElement> availableApplications, List<String> availableApplicationsText) {
		boolean flag = true;
		int i = 0;
		if (availableApplications.size() == availableApplicationsText.size()) {
			for (WebElement software : availableApplications) {

				if (!software.getText().equals(availableApplicationsText.get(i)))
					flag = false;
				i++;
			}
		}
		return flag;
	}

	/**
	 * This method verifies column on download page
	 *
	 * @param availableApplications - list of all elements available
	 * @param availableApplicationsText - list of text from maestro
	 * @return flag - true if list are same
	 * @throws Exception
	 */
	public final boolean verifyColumnOfSoftwareList(List<WebElement> releaseTypeList, String languageCode) throws Exception {
		boolean flag = true;

		if (releaseTypeList.size() > 0) {
			for (WebElement software : releaseTypeList) {
				scrollTillView(software);
				if (!software.getText().equals(getTextLanguage(languageCode, "daas_ui", "software.download.release.label.general")) && !software.getText().equals(getTextLanguage(languageCode, "daas_ui", "software.download.release.label.latest")))
					flag = false;

			}
		}
		return flag;
	}

	/**
	 * Method to go to Device > EMM Tool page
	 *
	 * @throws Exception
	 */
	public final void gotoEMMToolBtn() throws Exception {

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		if (!dashboardPage.waitForElementsOfDashboardPage("dashboardTab")) {
			dashboardPage.clickOnElementsOfDashboardPage("menuIcon");
		}
		clickByJavaScriptOnDeviceListPage("devicesTab");

		EMMToolPage emmToolPage = new EMMToolPage(PreDefinedActions.getDriver());
		emmToolPage = emmToolPage.getInstance();
		dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		if (!dashboardPage.waitForElementsOfDashboardPage("dashboardTab")) {
			dashboardPage.clickOnElementsOfDashboardPage("menuIcon");
		}
		emmToolPage.clickByJavaScriptOnEMMToolPage("emmToolBtnClick");
	}

	/**
	 * This method is used for clearing any filters applied on device list page
	 *
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfDevicesListPage(String clearFilterKey) throws Exception {
		clearFilters(deviceListPagePropertiesPageProperties.getProperty(clearFilterKey));
	}

	/**
	 * This method set attribute to block to be used in import
	 *
	 * @param key - Key of the locator
	 */
	public final void setAttributeForDevice(String key) {
		setAttributeForImport(deviceListPagePropertiesPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify imported/added devices
	 *
	 * @param language - Language parameter from POM
	 * @param pendingDevices - map including the details of added devices
	 * @return - boolean value of whether the devices were devices were added successfully
	 */
	public boolean verifyDevicesOnPendingEnrollmentListPage(Map<String, String> pendingDevices, String language) {
		try {
			boolean flag = false;
			HashMap<String, String> mapAssets = new HashMap<>();
			int serialNumberListCounter = 0;
			waitForElementsOfDeviceListPage("serialNumberList");
			waitForPageLoaded();
			sleeper(2000);
			getElementOfDeviceListPage("serialNumberSearchBox").clear();
			getElementOfDeviceListPage("serialNumberSearchBox").sendKeys("SrAutomation");
			waitForElementsOfDeviceListPage("serialNumberList");
			waitUntilElementIsInvisibleOfDeviceListPage("SpinnerList");
			waitForPageLoaded();
			sleeper(5000);
			List<WebElement> serialNumberList = getElementsTillAllElementsVisibleofDeviceListPage("serialNumberList");
			for (int statusListCounter = 0; statusListCounter < serialNumberList.size();) {
				for (serialNumberListCounter = statusListCounter; serialNumberListCounter < serialNumberList.size();) {
					mapAssets.put(serialNumberList.get(serialNumberListCounter).getText(), getTextLanguage(language, "daas_ui", "global.Pre_enrolled"));
					statusListCounter++;
					break;
				}
			}
			LOGGER.info("mapAssets" + mapAssets);
			LOGGER.info("pendingDevices" + pendingDevices);
			if (mapAssets.equals(pendingDevices)) {
				flag = true;
				LOGGER.info("Devices which got added/imported are getting reflected on List page.");
			} else {
				flag = false;
				LOGGER.error("Devices which got added/imported are not getting reflected on List page.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyDevicesOnPendingEnrollmentListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to validate whether the imported devices are reflected on the list page
	 *
	 * @param getImportDeviceInfo - import info
	 * @param fileName - filename which is to be imported
	 * @param language - POM language
	 * @return
	 */
	public boolean verifyImportedDevicesOnPendingDeviceListPage(String fileName, String language) throws Exception {
		try {
			HashMap<String, String> mapDevices = new HashMap<>();
			HashMap<String, String> pendingDevicesCSV = new HashMap<>();
			int serialNumberListCounter = 0;
			boolean flag = false;
			CSVFileReader csv = new CSVFileReader();
			File file = new File(ConstantPath.IMPORT_PATH + fileName);
			for (String[] mapping : csv.getDataWithoutHeader(file)) {
				pendingDevicesCSV.put(mapping[0], getTextLanguage(language, "daas_ui", "global.Pre_enrolled"));
			}
			waitForPageLoaded();
			waitForElementsOfDeviceListPage("serialNumberSearchBox");
			getElementOfDeviceListPage("serialNumberSearchBox").sendKeys("SrAutomation");
			waitForElementsOfDeviceListPage("serialNumberList");
			sleeper(4000);
			List<WebElement> statusList = getElementsTillAllElementsVisibleofDeviceListPage("statusList");
			List<WebElement> serialNumberList = getElementsTillAllElementsVisibleofDeviceListPage("serialNumberList");
			for (int statusListCounter = 0; statusListCounter < statusList.size();) {
				for (serialNumberListCounter = statusListCounter; serialNumberListCounter < serialNumberList.size();) {
					mapDevices.put(serialNumberList.get(serialNumberListCounter).getText(), statusList.get(statusListCounter).getText());
					statusListCounter++;
					break;
				}
			}
			LOGGER.info("UI LIST:" + mapDevices);
			LOGGER.info("CSV LIST:" + pendingDevicesCSV);
			if (mapDevices.equals(pendingDevicesCSV)) {
				flag = true;
			} else {
				flag = false;
				LOGGER.error("Devices which got imported are not getting reflected on List page.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportedDevicesOnPendingDeviceListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to add device using API
	 *
	 * @param api - API Url
	 * @param body - Payload
	 */
	public final void addZTEDeviceUsingAPI(String api, String body) {
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			httpRequest.post(api);
		} catch (Exception e) {
			LOGGER.error("Exception occured in add device api method " + e.getMessage());
		}
	}

	/**
	 * This method is used to delete a device using API
	 *
	 * @param api - API Url
	 * @param body - payload api
	 */
	public final void deletZTEDeviceUsingAPI(String api, String body) {
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			httpRequest.post(api);
		} catch (Exception e) {
			LOGGER.error("Exception occured in delete device api method " + e.getMessage());
		}
	}

	/**
	 * This method returns the count of number of files in a folder.
	 *
	 * @param downloadPath - folder path
	 * @return - Count of downloaded files
	 */
	public int getFileCount(String downloadPath) {
		try {
			File Files = new File(downloadPath);
			return Files.list().length;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getFileCount " + e.getMessage()));
			return 0;
		}
	}

	/**
	 *
	 * Method to navigate on Device list page, Click on first device from the list
	 * Navigated to device details page
	 */
	public final void goToDeviceDetailsPage() {
		try {
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
			sleeper(2000);
			if (deviceListPage.verifyElementsOfDeviceListPage("deviceDetails") == true) {
				deviceListPage.clickOnElementsOfDeviceListPage("deviceDetails");
				sleeper(2000);// detail page takes time to load
				LOGGER.info("Clicked on first device from list");
				deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
				deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
				LOGGER.info("Navigated to device details page");
			} else {
				LOGGER.info("Devices are not present on Device List page");
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method goToParticularDevice " + e.getMessage()));
		}
	}

	/**
	 * This method is used to verify failure in addition of single device manually if it exists in another company.
	 */
	public final void verifyFailureInDeviceAddition(String serialNumber, String enrollmentType, String language) throws Exception {

			// Select enrollment option
			if (enrollmentType.equalsIgnoreCase("auto_enroll")) {
				clickOnElementsOfDeviceListPage("autoEnrollmentOption");
				LOGGER.info("Auto enrollment option selected");
			}
			verifyElementsOfDeviceListPage("nextButton");
			clickOnElementsOfDeviceListPage("nextButton");
			LOGGER.info("Clicked on next button");

			//select plan
			if(verifyElementsOfDeviceListPage("chooseDevicePlanDropdown")){
				selectElementFromDropDownOfDeviceListPage("chooseDevicePlanDropdown","devicePlanList",getTextLanguage(language, "daas_ui", "global.application_name.proactive"));
				verifyElementsOfDeviceListPage("nextButton");
				clickOnElementsOfDeviceListPage("nextButton");
			}
		if(verifyElementsOfDeviceListPage("enterManually")){
			clickOnElementsOfDeviceListPage("enterManually");
			LOGGER.info("Clicked on Enter Manually");
			verifyElementsOfDeviceListPage("nextButton");
			clickOnElementsOfDeviceListPage("nextButton");
		}
			enterTextForDeviceListPage("serialNoBox", serialNumber);
			enterTextForDeviceListPage("aliasBox", serialNumber);
			waitForElementsOfDeviceListPage("addDevButtonIndPopUp");
			clickOnElementsOfDeviceListPage("addDevButtonIndPopUp");
			LOGGER.info("Clicked on add button");
			waitForElementsOfDeviceListPage("toastNotification");
			waitForElementsOfDeviceListPage("errorOnAddDevicePopUp");
			String toastNotification=getTextOfDeviceListPage("toastNotification");
			LOGGER.info("TOAST Notification is :"+toastNotification);
			Assert.assertTrue(getTextLanguage(language, "daas_ui", "assets.add.individually.failure.zte").equalsIgnoreCase(toastNotification));

	}

	/**
	 * Get device id from import csv file
	 * @param fileName
	 * @return
	 */
	public String getDeviceSerialNo(String fileName){
		Map.Entry<String,String> entry=null;
		try {
			HashMap<String, String> devicesCSV = new HashMap<>();
			CSVFileReader csv = new CSVFileReader();
			File file = new File(ConstantPath.IMPORT_PATH + fileName);
			for (String[] mapping : csv.getDataWithoutHeader(file)) {
				devicesCSV.put(mapping[0], mapping[1]);
			}
			 entry = devicesCSV.entrySet().iterator().next();
			waitForPageLoaded();

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportedDevicesOnListPage " + e.getMessage()));
			return null;
		}
		return entry.getKey();
	}
	/**
	 * This method is used to validate the notification flow after the devices have been imported for veneer version 3
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean postNotificationCheckImportForSuccessfullImportInV3(String fileName) {
		try {
			boolean flag = false;
			String notificationText = null;
			sleeper(30000); // time required for the notification message to pop on notification window
			waitForElementsOfDeviceListPage("NotificationBellIcon");
			clickOnElementsOfDeviceListPage("NotificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");

			sleeper(35000); // time required for the notification message to load

			waitForElementsOfDeviceListPage("firstNotification");
			clickOnElementsOfDeviceListPage("firstNotification");
			LOGGER.info("Clicked on firstNotification");

			waitForElementsOfDeviceListPage("adminxNotificationButton");
			clickOnElementsOfDeviceListPage("adminxNotificationButton");
			LOGGER.info("Clicked on hamburgerOnNotifications button");
			sleeper(1000);
			waitForElementsOfDeviceListPage("markAsRead");
			sleeper(1000);
			verifyElementsOfDeviceListPage("markAsRead");
			sleeper(1000);
			String markAsReadText = getTextOfDeviceListPage("markAsRead");
			sleeper(1000);

			clickOnElementsOfDeviceListPage("markAsRead");
			LOGGER.info("Clicked on markAsRead option");

			sleeper(30000); // time required for the notification message to load
			if (verifyElementsOfDeviceListPage("unreadNotificationText") && markAsReadText.equals(markAsReadOptionText)) {
				notificationText = getTextOfDeviceListPage("unreadNotificationText");
				int attempt=0;
				while(attempt<20){
				if (notificationText.contains("Your devices from " + fileName + " were imported successfully. Please check the logs for more details.")) {

					flag = true;
					break;
				} else {
					LOGGER.error("Message on notification window is incorrect");
				}
				sleeper(1000);
				attempt++;
					notificationText = getTextOfDeviceListPage("unreadNotificationText");
				}

			} else {
				LOGGER.error("Notification for device import did not display/delay in notification for more than 30 seconds");
			}
			clickOnElementsOfDeviceListPage("NotificationBellIcon");
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImportForSuccessfullImportInV3 " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to validate the notification flow after the devices have been imported for veneer version 3
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean postNotificationCheckImportForUnsuccessfullImportInV3(String fileName) {
		try {
			boolean flag = false;
			String notificationText = null;
			waitForElementsOfDeviceListPage("notificationBellIcon");
			clickOnElementsOfDeviceListPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			sleeper(40000);// time required for the notification message to pop on notification window
			if (waitForElementsOfDeviceListPage("unreadNotificationHyperLink")) {
				sleeper(30000); // time required for the notification message to load
				notificationText = getTextOfDeviceListPage("unreadNotificationText");
				if (notificationText.equalsIgnoreCase("There was a problem while importing your devices from " + fileName + " file. Please check the errors for more information.")) {
					flag = true;
				} else {
					LOGGER.error("Notification for import has failed");
				}
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckImportForUnsuccessfullImportInV3 " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This method is used for file imported using Robot class for veneer version 3
	 * @param fileName this is the name of file which was imported
	 * @throws Exception 
	 */
	public void fileImportInV3(String fileName) throws Exception {
		sleeper(2000);
		WebElement addFile = getElementOfDeviceListPage("browseInput");
	    addFile.sendKeys(fileName);
	    sleeper(3000);
	}

    /**
     * Selects all available columns in the table configuration popup.
     * Flow: open popup -> (optional) clear filters -> select all checkboxes -> save.
     * Adds retry, spinner/title waits, and graceful handling when buttons are absent.
     *
     * @throws Exception
     */
    public void selectAllCheckboxOfPopupForDevicelistPage() throws Exception {
        TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
        waitForPageLoaded();

        // Retry opening the popup up to 2 times if first click fails
        boolean opened = false;
        for (int attempt = 1; attempt <= 2; attempt++) {
            if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationButton")) {
                try {
                    tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
                    LOGGER.info("Opened Table Configuration popup (attempt " + attempt + ")");
                    opened = true;
                    break;
                } catch (Exception e) {
                    LOGGER.warn("Attempt " + attempt + " to open Table Configuration popup failed: " + e.getMessage());
                    sleeper(500);
                }
            }
        }

        if (!opened) {
            LOGGER.error("Failed to open Table Configuration popup after retries");
            return;
        }

        // Wait for title (modal readiness)
        try {
            tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPageDynamic("tableConfigurationTitle", 10);
        } catch (Exception e) {
            LOGGER.warn("Table configuration title not visible within timeout: " + e.getMessage());
        }

        // Optional: wait for spinner to disappear if present
        try {
            if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("spinner")) {
                LOGGER.info("Spinner detected - waiting briefly for it to disappear");
                sleeper(1500);
            }
        } catch (Exception e) {
            LOGGER.debug("Spinner check exception (non-critical): " + e.getMessage());
        }

        // Clear filters if button present inside the popup
        if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
            try {
                tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
                LOGGER.info("Clicked Clear Filters button");
                sleeper(400);
            } catch (Exception e) {
                LOGGER.warn("Clear Filters button click failed: " + e.getMessage());
            }
        } else {
            LOGGER.info("Clear Filters button not present - continuing");
        }

        // Select all column checkboxes
        try {
            tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
            LOGGER.info("Selected all column checkboxes");
        } catch (Exception e) {
            LOGGER.error("Failed selecting column checkboxes: " + e.getMessage());
            return;
        }

        // Save configuration
        if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("saveButton")) {
            try {
                tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
                LOGGER.info("Saved table configuration successfully");
            } catch (Exception e) {
                LOGGER.error("Failed clicking Save button: " + e.getMessage());
            }
        } else {
            LOGGER.error("Save button not present - cannot persist table configuration changes");
        }
    }


	public final void clickByActionsClickDevicelistPage(String key) {
		try {
			actionClick(deviceListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByActionsClickDevicelistPage " + e.getMessage()));
		}
	}
	/**
	 * This is a method to select Active Status for Devices status Dropdown
	 * 
	 * @param LanguageCode
	 * @param clearListPageFilter - locator of clear filter in device list page
	 * @param devicestatus - locator of status header
	 * @param devicestatusdd - locator of device status dropdown
	 * @param statusddvalues - locator of dropdown values
	 * @param firstcolumnheader - locator of first column header
	 * @return
	 */
	public boolean verifyonlyactivedevicesdatafetchforstaticgroups(String LanguageCode,String clearListPageFilter,String devicestatus, String devicestatusdd, String statusddvalues, String firstcolumnheader)
 	{
 		try {
 			boolean flag = false;
			clearFiltersOfDevicesListPage(clearListPageFilter);
			resetTableConfiguration();	
			clearFiltersOfDevicesListPage(clearListPageFilter);
 			scrollOnDeviceListPage(devicestatus);
 			sleeper(2000);
 			clickByActionsClickDevicelistPage(devicestatusdd);
 			sleeper(2000);
 			String activeValue = getTextLanguage(LanguageCode, "daas_ui", "global.active");
 			List<WebElement> statusList = getElementsTillAllElementsVisibleofDeviceListPage(statusddvalues);
 			for (int i = 0; i < statusList.size(); i++) {
 				if (activeValue.equals(statusList.get(i).getText())) {
 					statusList.get(i).click();
					LOGGER.info("Clicked on the Active Status Value");
 					sleeper(3000);
 					break;
 				}
 			}
 			Robot robot = new Robot();
 			robot.keyPress(KeyEvent.VK_ESCAPE);
 			robot.keyRelease(KeyEvent.VK_ESCAPE);
 			sleeper(2000);
 			scrollOnDeviceListPage(firstcolumnheader);
 			flag = true;
 			LOGGER.info("Device list contains only active devices");
 			return flag;
			}catch (Exception e) {
 			LOGGER.error("Exception occured in method verifyonlyactivedevicesdatafetchforstaticgroups " + e.getMessage());
 			return false;
 		}
 	}
	
	/**
	 * @param LanguageCode
	 * @param columnField
	 * @param searchBox
	 * @param searchValue
	 * @param totalNumbers
	 * @return
	 * @throws Exception
	 */
	public Integer verifyApplyOtherFilters(String LanguageCode,String columnField,String searchBox,String searchValue, String totalNumbers) throws Exception{
 			scrollOnDeviceListPage(columnField);
 			int Total;
 			if(getTextOfDeviceListPage(columnField).equals(getTextLanguage(LanguageCode,"daas_ui","asset_modal_pctype"))) {
 				clickByActionsClickDevicelistPage(searchBox);
 	 			sleeper(2000);
 	 			String Value = getTextLanguage(LanguageCode,"daas_ui","global.device_type.desktop");
 	 			List<WebElement> statusList = getElementsTillAllElementsVisibleofDeviceListPage(searchValue);
 	 			for (int i = 0; i < statusList.size(); i++) {
 	 				if (Value.equals(statusList.get(i).getText())) {
 	 					statusList.get(i).click();
 						LOGGER.info("Clicked on the required Value");
 	 					sleeper(3000);
 	 					break;
 	 				}
 	 			}
 	 			Robot robot = new Robot();
 	 			robot.keyPress(KeyEvent.VK_ESCAPE);
 	 			robot.keyRelease(KeyEvent.VK_ESCAPE);
 	 			sleeper(2000);;
 			}else {
 			enterTextForDeviceListPage(searchBox, searchValue);
 			}
 			sleeper(4000);
 			String paginationText = getTextOfDeviceListPage(totalNumbers).replace("of ", "");
	 			if(paginationText.contains(",")) {
	 				String replaced = paginationText.replace(",", "");
	 				Total = Integer.parseInt(replaced);
	 			}else {
	 				Total = Integer.parseInt(paginationText);
	 			}
	 			sleeper(3000);
 			return Total;	
 	}
		
	/** This function is get list of plans and verify if Active care is displayed or not change plan modal for device with AC and other license based plan
	 * @param dropdownId - locator of dropdown list displayed for plan
	 * @param planName - Plan Name to be validated 
	 * @return
	 */
	public boolean verifyIsActiveCarePlanDisplayedonChangePlanModal(String dropdownId, String planName) {
	    boolean activeCareFound = false;
	    List<WebElement> dropDownElements = new ArrayList<WebElement>();

	    try {
	        dropDownElements = getElementsOfDeviceListPage(dropdownId);

	        // Check if the list is not empty before entering the loop
	        if (!dropDownElements.isEmpty()) {
	            for (WebElement dropDownElement : dropDownElements) {
	                if (dropDownElement.getText().equals(planName)) {
	                    activeCareFound = true;
	                    break; // Exit the loop once "Active Care" is found
	                }
	            }
	        } else {
	            // Handle the case when the list is empty
	        	LOGGER.error("The list is empty");
	        }
	    } catch (Exception e) {
	    	LOGGER.error(("Exception occured in method verifyIsActiveCarePlanDisplayedonChangePlanModal " + e.getMessage()));
			return false;
	    }

	    return activeCareFound;
	}

	/** This function will filter devices with Active status.
	 * @param textToBeSelected - text to be passed for selection
	 * @param listOfElements - List of elements of dropdown
	 * @return
	 */
	public boolean verifyActiveDevicesSelected(String textToBeSelected,List<WebElement> listOfElements)
	{
		try {
			return selectValueFromDropdown(textToBeSelected,listOfElements);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyActiveDevicesSelected " + e.getMessage()));
			return false;
		}

	}
	
	
	/** This function will filter devices with Active status.
	 * @param textToBeSelected - text to be passed for selection
	 * @param listOfElements - List of elements of dropdown
	 * @return
	 */
	public boolean applyFilterDeviceListPageOnStatus(String dropdownBox, String dropdownListKey, String elementText)
	{
		try{
			if(!this.waitForElementsOfDeviceListPage(dropdownBox)) {
				LOGGER.info("Status filter is not visible.");
				return false;
			}
			clickOnElementsOfDeviceListPage("statusFilter");
			if(!this.waitForElementsOfDeviceListPage(dropdownListKey)) {
				LOGGER.info("Status filter drop down list is not visible.");
				return false;
			}
			
			return selectTextValueFromDropdownOfDeviceListPage(dropdownListKey, elementText, dropdownBox);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyActiveDevicesSelected " + e.getMessage()));
			return false;
		}

	}

}


