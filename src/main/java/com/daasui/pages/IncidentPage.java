package com.daasui.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ExcelReader;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.IncidentsVariables;
import com.google.common.base.Strings;

/**
 * This class includes all the methods related to incident list page test cases
 **/
public class IncidentPage extends CommonMethod {
	private ObjectReader incidentPagePropertiesReader = new ObjectReader();
	private Properties incidentPagePropertiesPageProperties;

	private IncidentPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public IncidentPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (IncidentPage.class) {
				if (instance == null) {
					instance = new IncidentPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public IncidentPage(WebDriver driver) throws IOException {
		incidentPagePropertiesPageProperties = incidentPagePropertiesReader.getObjectRepository("IncidentPageV3");
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfIncidentPage(String key) {
		try {
			return verifyElementIsPresent(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfIncidentPage(String key) {
		try {
			return verifyElementIsVisible(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of element
	 * @param Text - Text to be matched
	 * @return - boolean value of whether the text present on element matches or not
	 */
	public final boolean matchTextOfIncidentPage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(incidentPagePropertiesPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextOfIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if the element is enable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is enabled or not
	 */
	public final boolean verifyElementIsEnableOfIncidentPage(String key) {
		try {
			return verifyElementIsEnable(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if the element is selected
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is selected or not
	 */
	public final boolean verifyElementIsSelectedOfIncidentPage(String key) {
		try {
			return verifyElementIsSelected(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsSelectedOfIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get text of an element
	 * 
	 * @param key - Locator of element
	 * @return - String value of the text on the element
	 */
	public final String getTextOfIncidentPage(String key) {
		try {
			return getTextBy(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfIncidentPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get attribute of an element
	 * 
	 * @param key - Locator of element
	 * @param value - the name of attribute
	 * @return - String value of the attribute
	 */
	public final String getAttributesOfIncidentPage(String key, String value) {
		try {
			return getAttribute(incidentPagePropertiesPageProperties.getProperty(key), value);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAttributesOfIncidentPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to click on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void clickOnElementsOfIncidentPage(String key) {
		try {
			click(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementsOfIncidentPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to wait untill an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfIncidentPage(String key) {
		try {
			verifyElementIsinvisibile(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfIncidentPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to click on an element by javascript
	 * 
	 * @param key - Locator of element
	 */
	public final void clickByJavaScriptOnIncidentPage(String key) {
		try {
			clickByJavaScript(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnIncidentPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to clear text from an element
	 * 
	 * @param key - Locator of element
	 */
	public final void clearTextFromIncidentPageTextField(String key) {
		try {
			clearText(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clearTextFromIncidentPageTextField " + e.getMessage()));
		}
	}

	/**
	 * This is a method to enter text on an element
	 * 
	 * @param key - Locator of element
	 * @param Text - The text to be entered
	 */
	public final void enterTextForIncidentPage(String key, String Text) {
		try {
			enterText(incidentPagePropertiesPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForIncidentPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void mousehoverOnIncientPage(String key) {
		try {
			mouseHover(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnIncientPage " + e.getMessage()));
		}
	}

	/**
	 * This is the method to verify if all the checkboxes in a column of a table are selected
	 * 
	 * @return - boolean value of whether the checkboxes are selected or not
	 */
	public final boolean verifyAllCheckBoxesSelectedOnIncidentPage() {
		try {
			return verifyAllCheckBoxesSelectedOfTable(incidentPagePropertiesPageProperties.getProperty("allCheckBoxOfPage"));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyAllCheckBoxesSelectedOnIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify search functionality of search filters present on incident page
	 * 
	 * @param LanguageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param Text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOnIncident(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionality(LanguageCode, incidentPagePropertiesPageProperties.getProperty(textKey), Text, incidentPagePropertiesPageProperties.getProperty(emptyTextKey), incidentPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOnIncident " + e.getMessage()));
			return false;
		}
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
	 */
	public final boolean verifyFilterSingleSelectDynamic(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) {
		try {
			return verifyFilterFunctionalityForSingleSelectFromDynamicDropdown(LanguageCode, incidentPagePropertiesPageProperties.getProperty(textKey), Text, incidentPagePropertiesPageProperties.getProperty(emptyTextKey), incidentPagePropertiesPageProperties.getProperty(listKey), incidentPagePropertiesPageProperties.getProperty(checkboxKey), incidentPagePropertiesPageProperties.getProperty(columnListKey), incidentPagePropertiesPageProperties.getProperty(emptyTextColumnKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterSingleSelectDynamic " + e.getMessage()));
			return false;
		}
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
	 */
	public final boolean verifyFilterMultiSelectDynamic(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) {
		try {
			return verifyFilterFunctionalityForMultiSelectFromDynamicDropdown(LanguageCode, incidentPagePropertiesPageProperties.getProperty(textKey), Text, incidentPagePropertiesPageProperties.getProperty(emptyTextKey), incidentPagePropertiesPageProperties.getProperty(listKey), incidentPagePropertiesPageProperties.getProperty(checkboxKey), incidentPagePropertiesPageProperties.getProperty(columnListKey), incidentPagePropertiesPageProperties.getProperty(emptyTextColumnKey));
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
	public final boolean verifyOptionsOnDropdownForIncidentPage(String key, ArrayList<String> optionsOnDropdown) {
		try {
			return compareTwoList(incidentPagePropertiesPageProperties.getProperty(key), optionsOnDropdown);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyOptionsOnDropdownForIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to select first option from any dropdown
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectFirstOptionFromDropdownOnIncidentPage(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(incidentPagePropertiesPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectFirstOptionFromDropdownOnIncidentPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is the method to select second option from the dropdown if the first one is already selected
	 * 
	 * @param dropdownListKey - Locator of dropdown elements
	 * @param valueOnDropdown - Already present value on the dropdown
	 * @return - String value of the option selecetd from the dropdown
	 */
	public final String selectOptionFromDropdownOnIncidentPage(String dropdownListKey, String valueOnDropdown) {
		try {
			return selectValueOnPopup(incidentPagePropertiesPageProperties.getProperty(dropdownListKey), valueOnDropdown);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectOptionFromDropdownOnIncidentPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the filter functionality when a single option is selected from a static list of options
	 * 
	 * @param LanguageCode - Language code
	 * @param checkboxKey - Locator for the checkboxes in dropdown
	 * @param listOfElementKey - Locator for list of items in dropdown
	 * @param columnListKey - Locator for list of all items in the column
	 * @param emptyTextKey - Locator for "No items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 */
	public final boolean verifyFilterSingleSelect(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) {
		try {
			return verifyFilterFunctionalityForSingleSelect(LanguageCode, incidentPagePropertiesPageProperties.getProperty(checkboxKey), incidentPagePropertiesPageProperties.getProperty(listOfElementKey), incidentPagePropertiesPageProperties.getProperty(columnListKey), incidentPagePropertiesPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterSingleSelect " + e.getMessage()));
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
			return verifyFilterFunctionalityForMultiSelectForDyanmicList(LanguageCode, incidentPagePropertiesPageProperties.getProperty(checkboxKey), incidentPagePropertiesPageProperties.getProperty(listOfElementKey), incidentPagePropertiesPageProperties.getProperty(columnListKey), incidentPagePropertiesPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterMultiSelect " + e.getMessage()));
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
	public final String selectValueFromDropdownInPopup(String editButtonKey, String dropdownButtonKey, String dropdownListKey, String valueOnDropdown, String submitButtonKey) {
		try {
			clickOnElementsOfIncidentPage(editButtonKey);
			clickOnElementsOfIncidentPage(dropdownButtonKey);
			String text = selectValueOnPopup(incidentPagePropertiesPageProperties.getProperty(dropdownListKey), valueOnDropdown);
			clickOnElementsOfIncidentPage(submitButtonKey);
			return text;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectValueFromDropdownInPopup " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to select value from dropdown in popup when all incidents are selected
	 * 
	 * @param editButtonKey
	 * @param dropdownButtonKey
	 * @param dropdownListKey
	 * @param valueOnDropdown
	 * @param submitButtonKey
	 * @return
	 */
	public final String selectValueFromDropdownInPopupForAllIncidentsSelected(String editButtonKey, String dropdownButtonKey, String dropdownListKey, String submitButtonKey) {
		try {
			clickOnElementsOfIncidentPage(editButtonKey);
			clickOnElementsOfIncidentPage(dropdownButtonKey);
			String text = selectFirstValueFromDropdown(incidentPagePropertiesPageProperties.getProperty(dropdownListKey));
			clickOnElementsOfIncidentPage(submitButtonKey);
			return text;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectValueFromDropdownInPopupForAllIncidentsSelected " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get a sequence of selected columns
	 * 
	 * @param key - Locator of the list of selected columns
	 * @return - arraylist of the text present on the list of elements
	 */
	public final ArrayList<String> getSequenceOfSelectedColumnsOnIncidentPage(String key) {
		try {
			return getTextOfList(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getSequenceOfSelectedColumnsOnIncidentPage " + e.getMessage()));
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
			return verifySelectedValueFromPopupInTable(incidentPagePropertiesPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySelectedValuesOfPopupInTable " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify searched value for a search box present on a popup
	 * 
	 * @param LanguageCode - Language code
	 * @param textKey - Locator for searchbox
	 * @param Text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for the list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOnIncidentInsidePopup(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionalityInsidePopup(LanguageCode, incidentPagePropertiesPageProperties.getProperty(textKey), Text, incidentPagePropertiesPageProperties.getProperty(emptyTextKey), incidentPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOnIncidentInsidePopup " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to verify if element is clickable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is clickable or not
	 */
	public final boolean verifyElementIsClickableOfIncidentPage(String key) {
		try {
			return verifyElementIsClickable(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsClickableOfIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to get a list of all elements
	 * 
	 * @param key - Locator of the list
	 * @return - list of web elements
	 */
	public List<WebElement> getWebElementsOfIncidentPage(String key) {
		try {
			return getElementsTillAllElementsPresent(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getWebElementsOfIncidentPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to scroll on incident page
	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnIncidentPage(String key) {
		try {
			scrollTillView(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getWebElementsOfIncidentPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get details of an incident present on incident list page
	 * 
	 * @return - arraylits of the details of incident
	 */
	public final ArrayList<String> getDetailsOfIncident() {
		try {
			ArrayList<String> incidentPageDetails = new ArrayList<>();
			incidentPageDetails.add(getTextBy(incidentPagePropertiesPageProperties.getProperty("incidentId")));
			incidentPageDetails.add(getTextBy(incidentPagePropertiesPageProperties.getProperty("incidentType")));
			incidentPageDetails.add(getTextBy(incidentPagePropertiesPageProperties.getProperty("incidentSubType")));
			scrollOnIncidentPage("incidentPriority");
			incidentPageDetails.add(getTextBy(incidentPagePropertiesPageProperties.getProperty("incidentPriority")));
			scrollOnIncidentPage("statusBoxTitle");
			incidentPageDetails.add(getTextBy(incidentPagePropertiesPageProperties.getProperty("incidentStatus")));
//			incidentPageDetails.add(getTextBy(incidentPagePropertiesPageProperties.getProperty("incidentTitleColumn")));
			incidentPageDetails.add(getTextBy(incidentPagePropertiesPageProperties.getProperty("incidentCreatedAt")).split("at")[0].trim());
			return incidentPageDetails;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getDetailsOfIncident " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to wait for an element to get clicable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible after the wait
	 */
	public final boolean waitForElementtobeClickableOfIncidentPage(String key) {
		try {
			return verifyElementIsClickable(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementtobeClickableOfIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if the selected value from searchbox
	 * 
	 * @param LanguageCode - language code
	 * @param textKey - locator of search box
	 * @param text - text to be entered
	 * @param emptyTextKey - locator for "no items available" message on popup
	 * @param listKey - locator of list of items filtered on popup
	 * @param assignToPopupKey - locator to get assign to value on popup
	 * @param dropdownkey - locator for dropdown arrow on popup
	 * @param cancelButtonKey - locator for cancel button on popup
	 * @return - String value of the selected option
	 */
	public final String selectedValueFromSearchBoxInsidePopup(String LanguageCode, String textKey, String text, String emptyTextKey, String listKey, String assignToPopupKey, String dropdownkey, String cancelButtonKey) {
		try {
			enterText(incidentPagePropertiesPageProperties.getProperty(textKey), text);
			sleeper(5000);
			String empty_text = null;
			String entered_text = null;
			String str = null;

			if (verifyElementIsPresent(incidentPagePropertiesPageProperties.getProperty(emptyTextKey))) {
				empty_text = getTextBy(incidentPagePropertiesPageProperties.getProperty(emptyTextKey));
				String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
				if (empty_text.contains(emptytext[0].concat(text))) {
					System.out.println("Entered text doesn't match");
					clickOnElementsOfIncidentPage(dropdownkey);
					sleeper(2000);
					clickOnElementsOfIncidentPage(cancelButtonKey);

				}
			} else {
				List<WebElement> elements = getElementsTillAllElementsPresent(incidentPagePropertiesPageProperties.getProperty(listKey));
				for (WebElement webElement : elements) {
					if (webElement.getText().toLowerCase().contains(text.toLowerCase())) {
						elements.get(0).click();
						entered_text = getTextBy(incidentPagePropertiesPageProperties.getProperty(assignToPopupKey));
						str = entered_text.substring(0, entered_text.indexOf("<")).trim();
						break;
					}
				}
			}
			return str;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectedValueFromSearchBoxInsidePopup " + e.getMessage()));
			return null;
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
	public final void selectDateFromCalenderOnIncidentListPage(String date, String calenderKey, String monthKeyLeft, String monthKeyRight, String leftArrowKey, String daysOnLeftSideKey, String daysOnRightSideKey) {
		try {
			selectDateFromCalender(date, incidentPagePropertiesPageProperties.getProperty(calenderKey), incidentPagePropertiesPageProperties.getProperty(monthKeyLeft), incidentPagePropertiesPageProperties.getProperty(monthKeyRight), incidentPagePropertiesPageProperties.getProperty(leftArrowKey), incidentPagePropertiesPageProperties.getProperty(daysOnLeftSideKey), incidentPagePropertiesPageProperties.getProperty(daysOnRightSideKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalender " + e.getMessage()));
		}
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
	 */
	public final boolean selectLastOneWeekRangeOnIncidentListPage(String LanguageCode, String calenderKey, String monthKeyLeft, String monthKeyRight, String leftArrowKey, String daysKeyLeft, String daysKeyRight, String emptyTextKey, String columnListKey) {
		try {
			return verifyCalendarWithDateFormat(LanguageCode, incidentPagePropertiesPageProperties.getProperty(calenderKey), incidentPagePropertiesPageProperties.getProperty(monthKeyLeft), incidentPagePropertiesPageProperties.getProperty(monthKeyRight), incidentPagePropertiesPageProperties.getProperty(leftArrowKey), incidentPagePropertiesPageProperties.getProperty(daysKeyLeft), incidentPagePropertiesPageProperties.getProperty(daysKeyRight),
					incidentPagePropertiesPageProperties.getProperty(emptyTextKey), incidentPagePropertiesPageProperties.getProperty(columnListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectLastOneWeekRange " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to select element from a dropdown by passing dynamic xpath
	 * 
	 * @param dropdownId - Locator of the arrow present on dropdown
	 * @param key - Locator of the list of dropdown options
	 * @param text - Text of the option which is to be selected
	 * @return - Option selected
	 */
	public final boolean selectElementFromDropDownofIncidentPage(String dropdownId, String key, String text) {
		try {
			click(incidentPagePropertiesPageProperties.getProperty(dropdownId));
			return selectFromDropdown(incidentPagePropertiesPageProperties.getProperty(dropdownId), incidentPagePropertiesPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectElementFromDropDownofIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to get the enability status for link/button
	 * 
	 * @param navigationItemPreviouskey - locator for the button/link to be tested
	 * @return - boolean value of the enability status
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviouskey) {
		try {
			return !getElement(incidentPagePropertiesPageProperties.getProperty(navigationItemPreviouskey)).getAttribute("class").contains("disabled");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getButtonEnabilityStatus " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This is the method to get which pagination option is selected
	 * 
	 * @param dropdownIdKey - Locator of element to open the pagination dropdown
	 * @param dropdownOptionlistKey - Locator of list of pagination options
	 * @return - Pagination option which is already selected
	 */
	public final int getSelectedOptionTextofPaginationIncidentPage(String dropdownIdKey, String dropdownOptionlistKey) {
		try {
			click(incidentPagePropertiesPageProperties.getProperty(dropdownIdKey));
			return getSelectedDropdownOptionOnPagination(incidentPagePropertiesPageProperties.getProperty(dropdownOptionlistKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getSelectedOptionTextofPaginationIncidentPage " + e.getMessage()));
			return 0;
		}
	}

	public final int getTotalRecordCount(String key) {
		try {
			int totalRecord = 0;
			String[] allText = getTextBy(incidentPagePropertiesPageProperties.getProperty(key)).split(" |/");
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
	 * This is a method to verify filter functionality for assigned to column when multiple options are selected
	 * 
	 * @param LanguageCode - language code
	 * @param textKey - locator of searchbox
	 * @param Text - text to be entered
	 * @param emptyTextKey -locator for "no items available" message
	 * @param listKey - locator of optiosn filetered on popup
	 * @param checkboxKey - locator of checboxes of available options
	 * @param columnListKey - locator of all items filtered in column
	 * @param emptyTextColumnKey - locator of "no items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 */
	public final boolean verifyFilterFunctionalityForAssignedToMultiSelectFromDynamicDropdown(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) {
		try {
			enterText(incidentPagePropertiesPageProperties.getProperty(textKey), Text);
			int flag = 0;
			sleeper(3000);
			String empty_text = null, text1 = null, text2 = null;
			if (verifyElementIsVisible(incidentPagePropertiesPageProperties.getProperty(emptyTextKey))) {
				empty_text = getTextBy(incidentPagePropertiesPageProperties.getProperty(emptyTextKey));
				String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
				if (empty_text.contains(emptytext[0].concat(Text))) {
					flag = 1;
				}
				pressKey(Keys.ESCAPE);
			} else {
				List<WebElement> listOfitems = getElementsTillAllElementsVisible(incidentPagePropertiesPageProperties.getProperty(checkboxKey));
				if (listOfitems.size() > 1) {
					listOfitems.get(0).click();
					sleeper(5000);
					List<WebElement> listOfitems1 = getElementsTillAllElementsVisible(incidentPagePropertiesPageProperties.getProperty(checkboxKey));
					listOfitems1.get(listOfitems1.size() - 1).click();
					sleeper(5000); // Will be adding page refresh code
					List<WebElement> listOfitems2 = getElementsTillAllElementsVisible(incidentPagePropertiesPageProperties.getProperty(listKey));
					text1 = listOfitems2.get(0).getText();
					List<WebElement> listOfitems3 = getElementsTillAllElementsVisible(incidentPagePropertiesPageProperties.getProperty(listKey));
					text2 = listOfitems3.get(listOfitems3.size() - 1).getText();
					pressKey(Keys.ESCAPE);

					if (verifyElementIsVisible(incidentPagePropertiesPageProperties.getProperty(emptyTextColumnKey))) {
						empty_text = getTextBy(incidentPagePropertiesPageProperties.getProperty(emptyTextColumnKey));
						if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
							flag = 1;
						}
					} else {
						List<WebElement> columnlist = getElementsTillAllElementsVisible(incidentPagePropertiesPageProperties.getProperty(columnListKey));
						for (WebElement webElement : columnlist) {
							flag = 0;
							if (webElement.getText().equals(text1) || webElement.getText().equals(text2)) {
								flag = 1;
							}
						}
					}
				} else {
					System.out.println("Unable to proceed with this dropdown, since this dropdown contains only single elements");
					return true;
				}
			}
			if (flag == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterFunctionalityForAssignedToMultiSelectFromDynamicDropdown " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify filter functionality for single select assigned to column
	 * 
	 * @param LanguageCode - language code
	 * @param textKey - locator of searchbox
	 * @param Text - text to be entered
	 * @param emptyTextKey -locator for "no items available" message
	 * @param listKey - locator of optiosn filetered on popup
	 * @param checkboxKey - locator of checboxes of available options
	 * @param columnListKey - locator of all items filtered in column
	 * @param emptyTextColumnKey - locator of "no items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 */
	public final boolean verifyFilterFunctionalityForAssignedToSingleSelectFromDynamicDropdown(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) {
		try {
			enterText(incidentPagePropertiesPageProperties.getProperty(textKey), Text);
			int flag = 0;
			sleeper(3000);
			String empty_text = null, text = null;
			if (verifyElementIsVisible(incidentPagePropertiesPageProperties.getProperty(emptyTextKey))) {
				empty_text = getTextBy(incidentPagePropertiesPageProperties.getProperty(emptyTextKey));
				String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
				if (empty_text.contains(emptytext[0].concat(Text))) {
					flag = 1;
				}
				pressKey(Keys.ESCAPE);
			} else {
				List<WebElement> elements = getElementsTillAllElementsVisible(incidentPagePropertiesPageProperties.getProperty(listKey));
				List<WebElement> listOfCheckbox = getElementsTillAllElementsVisible(incidentPagePropertiesPageProperties.getProperty(checkboxKey));
				if (!listOfCheckbox.get(0).isSelected()) {
					text = elements.get(0).getText();
					listOfCheckbox.get(0).click();
					pressKey(Keys.ESCAPE);
				}
				sleeper(2000);

				if (verifyElementIsVisible(incidentPagePropertiesPageProperties.getProperty(emptyTextColumnKey))) {
					empty_text = getTextBy(incidentPagePropertiesPageProperties.getProperty(emptyTextColumnKey));
					if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
						flag = 1;
					}
				} else {
					List<WebElement> columnList = getElementsTillAllElementsVisible(incidentPagePropertiesPageProperties.getProperty(columnListKey));
					for (WebElement webElement : columnList) {
						if (webElement.getText().equals(text)) {
							flag = 1;
						} else {
							return false;
						}
					}
				}
			}

			if (flag == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterFunctionalityForAssignedToSingleSelectFromDynamicDropdown " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method for sorting the CreatedBy column in descending
	 * 
	 * @param sortingArrowKey - locator of sorting arrow key
	 * @param listKey - locator of list of items
	 * @param nextPaginationLinkKey - locator of next page navigation link present on pagination area
	 * @param firstPageNavigationKey - locator of first page navigation link present on pagination area
	 * @param idKey - locator of element to scroll up on page
	 * @return - boolean value of whether the sorting functionality is working correctly
	 */

	public final boolean sortingOfCreatedByColumnInDescendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			for (int i = 0; i < unsortedList.size(); i++) {
				if (unsortedList.get(i).contains("System generated")) {
					unsortedList.remove(i);
					i--;
				}
			}
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			for (int i = 0; i < sortedList.size(); i++) {
				if (sortedList.get(i).contains("System generated")) {
					sortedList.remove(i);
					i--;
				}
			}
			return sortingInDescendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfCreatedByColumnInDescendingOrder " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method for sorting the CreatedBy column in ascending order
	 * 
	 * @param sortingArrowKey - locator of sorting arrow key
	 * @param listKey - locator of list of items
	 * @param nextPaginationLinkKey - locator of next page navigation link present on pagination area
	 * @param firstPageNavigationKey - locator of first page navigation link present on pagination area
	 * @param idKey - locator of element to scroll up on page
	 * @return - boolean value of whether the sorting functionality is working correctly
	 */

	public final boolean sortingOfCreatedByColumnInAscendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			for (int i = 0; i < unsortedList.size(); i++) {
				if (unsortedList.get(i).contains("System generated")) {
					unsortedList.remove(i);
					i--;
				}
			}
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			for (int i = 0; i < sortedList.size(); i++) {
				if (sortedList.get(i).contains("System generated")) {
					sortedList.remove(i);
					i--;
				}
			}
			return sortingInAscendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfCreatedByColumnInAscendingOrder " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method for sorting the AssignedTo column in descending order
	 * 
	 * @param sortingArrowKey - locator of sorting arrow key
	 * @param listKey - locator of list of items
	 * @param nextPaginationLinkKey - locator of next page navigation link present on pagination area
	 * @param firstPageNavigationKey - locator of first page navigation link present on pagination area
	 * @param idKey - locator of element to scroll up on page
	 * @return - boolean value of whether the sorting functionality is working correctly
	 */

	public final boolean sortingOfAssignedToColumnInDescendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			for (int i = 0; i < unsortedList.size(); i++) {
				if (unsortedList.get(i).contains("Unassigned")) {
					unsortedList.remove(i);
					i--;
				}
			}
			for (int i = 0; i < sortedList.size(); i++) {
				if (sortedList.get(i).contains("Unassigned")) {
					sortedList.remove(i);
					i--;
				}
			}
			return sortingInDescendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfAssignedToColumnInDescendingOrder " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method for sorting the AssignedTo column in ascending order
	 * 
	 * @param sortingArrowKey - locator of sorting arrow key
	 * @param listKey - locator of list of items
	 * @param nextPaginationLinkKey - locator of next page navigation link present on pagination area
	 * @param firstPageNavigationKey - locator of first page navigation link present on pagination area
	 * @param idKey - locator of element to scroll up on page
	 * @return - boolean value of whether the sorting functionality is working correctly
	 */

	public final boolean sortingOfAssignedToColumnInAscendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			for (int i = 0; i < unsortedList.size(); i++) {
				if (unsortedList.get(i).contains("Unassigned")) {
					unsortedList.remove(i);
					i--;
				}
			}
			for (int i = 0; i < sortedList.size(); i++) {
				if (sortedList.get(i).contains("Unassigned")) {
					sortedList.remove(i);
					i--;
				}
			}
			return sortingInAscendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfAssignedToColumnInAscendingOrder " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get a list of all the incidents present on all the pages
	 * 
	 * @param listKey - This is the key for the list of values on column
	 * @param nextPaginationLinkKey - This is the key for the next page link on pagination
	 * @param firstPageNavigationKey - This is the key for the next page link on pagination
	 * @param idKey - This is the key for the element locator to scroll up on page
	 * @return - This method returns a arraylist of all the incidents present on all the pages
	 */
	public final ArrayList<String> getAllIncidents(String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			sleeper(2000);
			ArrayList<String> unsortedList = getTextOfListOfIncidentPage(listKey);
			sleeper(2000);
			while (getButtonEnabilityStatus(nextPaginationLinkKey)) {
				scrollOnIncidentPage(firstPageNavigationKey);
				sleeper(3000);
				clickOnElementsOfIncidentPage(nextPaginationLinkKey);
				sleeper(5000);
				unsortedList.addAll(getTextOfListOfIncidentPage(listKey));
			}
			scrollOnIncidentPage(idKey);
			sleeper(2000);
			return unsortedList;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAllIncidents " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to validate sorting for filters that have calendar
	 * 
	 * @param sortingArrowKey - This is the key for sorting arrow
	 * @param listKey - This is the key for the list of values on column
	 * @param nextPaginationLinkKey - This is the key for the next page link on pagination
	 * @param firstPageNavigationKey - This is the key for the next page link on pagination
	 * @param idKey - This is the key for the element locator to scroll up on page
	 * @return - This method returns the boolean value if the sorting is applied correctly
	 */

	public final boolean sortingForCalendarOnIncidentPage(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			return sortingForCalendar(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingForCalendarOnIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to validate sorting for descending order
	 * 
	 * @param sortingArrowKey - This is the key for sorting arrow
	 * @param listKey - This is the key for the list of values on column
	 * @param nextPaginationLinkKey - This is the key for the next page link on pagination
	 * @param firstPageNavigationKey - This is the key for the next page link on pagination
	 * @param idKey - This is the key for the element locator to scroll up on page
	 * @return - This method returns the boolean value if the sorting is applied correctly
	 */

	public final boolean sortingInDescendingOnIncidentPage(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			unsortedList.removeAll(Collections.singletonList(""));
			sortedList.removeAll(Collections.singletonList(""));
			return sortingInDescendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingInDescendingOnIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to validate sorting for ascending order
	 * 
	 * @param sortingArrowKey - This is the key for sorting arrow
	 * @param listKey - This is the key for the list of values on column
	 * @param nextPaginationLinkKey - This is the key for the next page link on pagination
	 * @param firstPageNavigationKey - This is the key for the next page link on pagination
	 * @param idKey - This is the key for the element locator to scroll up on page
	 * @return - This method returns the boolean value if the sorting is applied correctly
	 */

	public final boolean sortingInAscendingOnIncidentPage(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			waitForElementsOfIncidentPage(sortingArrowKey);
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			unsortedList.removeAll(Collections.singletonList(""));
			sortedList.removeAll(Collections.singletonList(""));
			return sortingInAscendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingInAscendingOnIncidentPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key - locator of element
	 * @return - arraylist of the text of all elements present in the list
	 */

	public final ArrayList<String> getTextOfListOfIncidentPage(String key) {
		try {
			return getTextOfList(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfIncidentPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key - locator of element
	 * @return - list of the text of all elements present in the list
	 */
	public final List<String> getTextOfPresentListOfIncidentPage(String key) {
		try {
			return getTextOfPresentElementList(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfPresentListOfIncidentPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is the method to save filter selection of users for incident list filters
	 * 
	 * @param storedFilterKey This is the key for filter searchbox/dropdown
	 * @param filterTitleKey This is the key for the name of filter searchbox/dropdown
	 * @return This method returns a hashmap of the filter names against the stored filters
	 */

	public final HashMap<String, String> storeAllFilters(String storedFilterKey, String filterTitleKey) {
		HashMap<String, String> map = new HashMap<String, String>();
		ArrayList<String> filter = getTextOfListOfIncidentPage(storedFilterKey);
		ArrayList<String> title = getTextOfListOfIncidentPage(filterTitleKey);
		for (int i = 0; i < title.size(); i++) {
			String newKey = title.get(i);
			String newValue = filter.get(i);
			map.put(newKey, newValue);
		}

		return map;

	}

	/**
	 * This is the method to check if the sorting on the priority column is working correctly
	 * 
	 * @param LanguageCode - This is the language code
	 * @param sortingArrowKey - This is the key for the sorting arrow on priority column
	 * @param listKey - This is the key for the list of all values in priority column
	 * @param nextPaginationLinkKey - This is the key for the next page link in pagination
	 * @param firstPageNavigationKey - This is the key for the first page link in pagination
	 * @param idKey - This is the key for the generate xls button so as to scroll up
	 * @return - This method returns a boolean value of whether the sorting is correct
	 */

	public final boolean sortingForPriorityColumn(String LanguageCode, String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			scrollOnIncidentPage(idKey);
			sleeper(2000);
			ArrayList<String> unsorted = new ArrayList<String>();
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			ArrayList<String> sorted = new ArrayList<String>();
			String Unassigned = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned");
			String Critical = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.critical");
			String High = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.high");
			String Medium = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.medium");
			String Low = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.low");
			for (int i = 0; i < unsortedList.size(); i++)

			{
				if (unsortedList.get(i).contains(Unassigned)) {
					unsorted.add("1");
				} else if (unsortedList.get(i).contains(Critical)) {
					unsorted.add("2");
				} else if (unsortedList.get(i).contains(High)) {
					unsorted.add("3");
				} else if (unsortedList.get(i).contains(Medium)) {
					unsorted.add("4");
				} else if (unsortedList.get(i).contains(Low)) {
					unsorted.add("5");
				}

			}
			for (int i = 0; i < sortedList.size(); i++)

			{
				if (sortedList.get(i).contains(Unassigned)) {
					sorted.add("1");
				} else if (sortedList.get(i).contains(Critical)) {
					sorted.add("2");
				} else if (sortedList.get(i).contains(High)) {
					sorted.add("3");
				} else if (sortedList.get(i).contains(Medium)) {
					sorted.add("4");
				} else if (sortedList.get(i).contains(Low)) {
					sorted.add("5");
				}
			}
			Collections.sort(unsorted);
			if (sorted.equals(unsorted))
				return true;
			else
				return false;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingForPriorityColumn " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to check if the sorting on the status column is working correctly
	 * 
	 * @param LanguageCode - This is the language code
	 * @param sortingArrowKey - This is the key for the sorting arrow on status column
	 * @param listKey - This is the key for the list of all values in status column
	 * @param nextPaginationLinkKey - This is the key for the next page link in pagination
	 * @param firstPageNavigationKey - This is the key for the first page link in pagination
	 * @param idKey - This is the key for the generate xls button so as to scroll up
	 * @return - This method returns a boolean value of whether the sorting is correct
	 */

	public final boolean sortingForStatusColumn(String LanguageCode, String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			scrollOnIncidentPage(idKey);
			sleeper(2000);
			ArrayList<String> unsorted = new ArrayList<String>();
			clickOnElementsOfIncidentPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllIncidents(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			ArrayList<String> sorted = new ArrayList<String>();
			String New = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.new");
			String Investigating = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.investigating");
			String FixInProgress = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.progress");
			String AwaitingCustomer = getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.status.awaitingcustomer");
			String Fixed = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.fixed");
			String Dismissed = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.dismissed");
			for (int i = 0; i < unsortedList.size(); i++)

			{
				if (unsortedList.get(i).contains(New)) {
					unsorted.add("1");
				} else if (unsortedList.get(i).contains(Investigating)) {
					unsorted.add("2");
				} else if (unsortedList.get(i).contains(FixInProgress)) {
					unsorted.add("3");
				} else if (unsortedList.get(i).contains(AwaitingCustomer)) {
					unsorted.add("4");
				} else if (unsortedList.get(i).contains(Fixed)) {
					unsorted.add("5");
				} else if (unsortedList.get(i).contains(Dismissed)) {
					unsorted.add("6");
				}

			}
			for (int i = 0; i < sortedList.size(); i++)

			{
				if (sortedList.get(i).contains(New)) {
					sorted.add("1");
				} else if (sortedList.get(i).contains(Investigating)) {
					sorted.add("2");
				} else if (sortedList.get(i).contains(FixInProgress)) {
					sorted.add("3");
				} else if (sortedList.get(i).contains(AwaitingCustomer)) {
					sorted.add("4");
				} else if (sortedList.get(i).contains(Fixed)) {
					sorted.add("5");
				} else if (sortedList.get(i).contains(Dismissed)) {
					sorted.add("6");
				}
			}
			Collections.sort(unsorted);
			if (sorted.equals(unsorted))
				return true;
			else
				return false;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingForStatusColumn " + e.getMessage()));
			return false;
		}
	}

	/*
	 * This method validates the data in excel report exported from incident page, user searched for an incident and then export the details of the that incident in an excel file, this
	 * method validates whether information, column count and created by in the exported excel file are same as given in the incident or not, in case of any mismatch it returns false
	 * 
	 * @param incidentData HashMap having information about the submitted incident
	 * 
	 * @return true or false
	 */
	public final boolean validateIncidentDataInXLS(Map<String, String> incidentData, String languageCode) {
		try {
			// Below are the localization strings MUST be there in the generated excel file
			// as headers for fetching the data
			String strIncidents = getTextLanguage(languageCode, "daas_ui", "companies.details.section.incidents");
			String strListOfIncidents = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.xlsx.list_of_incidents");
			//String strCompanyId = getTextLanguage(languageCode, "daas_ui", "workflowOnboarding.company.id");
			String strFilterCriteria = getTextLanguage(languageCode, "daas_ui", "reports.add_report.report_criteria");
			String strDeviceSerialNumber = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.dsn");
			String strUserName = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.username");
			String strIncidentID = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.xlsx.id");
			String strDeviceName = getTextLanguage(languageCode, "daas_ui", "incidents.deviceName");
			String strPriority = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.priority");
			String strStatus = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.status");
			String strDateCreated = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.created_on");
			String strType = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.type");
			String strSubtype = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.subtype");
			String strTitle = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.title");
			String strDeviceOS = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.xlsx.os");
			String strDescription = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.discription");
			String strDeviceManufacturer = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.xlsx.device_mfg");
			String strDeviceModelName = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.xlsx.device_model_name");
			String strAssignedTo = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.xlsx.assigned_to");
			String strCompany = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.company");
			String strCreatedBy = getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.created_by");
			String strUpdatedOn = getTextLanguage(languageCode, "daas_ui", "incidents.updatedOn");
			String strFilterCriteriaText = getTextLanguage(languageCode, "daas_ui", "reports.add_report.report_criteria");

			// Below are the lists for holding applied filters values from UI and Excel
			// sheet
			ArrayList<String> excelFilterCriteriaText = new ArrayList<String>();
			ArrayList<String> expectedFilterCriteriaText = new ArrayList<String>();

			// Below are the 2 maps for holding expected and actual values
			Map<String, String> ExcelData = new HashMap<String, String>();
			Map<String, String> ActualData = new HashMap<String, String>();

			String IncidentIdOnUi = incidentData.get("incidentId");// Fetch it from the supplied incident data
			LOGGER.info("Search for the incident ID which data need to validated in excel report: " + IncidentIdOnUi);
			sleeper(2000);// Tried removing this sleeper but it is not working, so this is needed
			LOGGER.info("Enter the incident ID");
			enterTextForIncidentPage("idSearchBox", IncidentIdOnUi);
			sleeper(3000);// Tried removing this sleeper but it is not working, so this is needed

			// Below arraylists have headers and values available on UI after searching the
			// incident
			List<WebElement> objHeaders = getWebElementsOfIncidentPage("incidentTableHeaders");
			List<WebElement> objValues = getWebElementsOfIncidentPage("incidentTableValues");

			if ((objHeaders.size() != objValues.size()) || objHeaders.isEmpty() || objValues.isEmpty()) {// Both are not
				// same or
				// anyone is
				// empty
				LOGGER.error("Header and Values of the table shown on UI are not of same size. Header Size: " + objHeaders.size() + ", Values Size: " + objValues.size());
				return false;
			}

			// Fetch the headers and footers from the UI after search for validation use
			for (int headerRow = 0; headerRow < objValues.size(); headerRow++) {
				ActualData.put(objHeaders.get(headerRow).getText().toString(), objValues.get(headerRow).getText().toString());
				expectedFilterCriteriaText.add(objHeaders.get(headerRow).getText().toString() + ": " + objValues.get(headerRow).getText().toString());
			}
			// Apply filters fetched from UI
			// ********************Below sleepers are very much needed as UI gets refreshed
			// every time after applying every filter and there are no method as of now to
			// keep execution paused to
			// take care of that, already spent enough time in avoiding this**********
			int sleepTime = 4000;
			// Select Type from the dropdown menu
			clickOnElementsOfIncidentPage("typeBoxBefore");
			sleeper(sleepTime);
			selectDropdownOptions(incidentPagePropertiesPageProperties.getProperty("typeListOptions"), ActualData.get("TYPE"));
			sleeper(sleepTime);

			// Select subType from the dropdown menu
			clickOnElementsOfIncidentPage("subTypeBoxBefore");
			sleeper(sleepTime);
			selectDropdownOptions(incidentPagePropertiesPageProperties.getProperty("typeListOptions"), ActualData.get("SUBTYPE"));
			sleeper(sleepTime);

			// Select Created On from calendar control
			clickOnElementsOfIncidentPage("createdOnBox");
			sleeper(sleepTime);
			clickOnElementsOfIncidentPage("createdOnBox");
			sleeper(sleepTime);
			clickByJavaScriptOnIncidentPage("todaysDateButton");
			sleeper(sleepTime);
			clickByJavaScriptOnIncidentPage("createdOnBox");
			sleeper(sleepTime);

			// Select Priority from the dropdown menu
			clickOnElementsOfIncidentPage("priorityBoxBefore");
			sleeper(sleepTime);
			selectDropdownOptions(incidentPagePropertiesPageProperties.getProperty("typeListOptions"), ActualData.get("PRIORITY"));
			sleeper(sleepTime);

			// Select Status from the dropdown menu
			clickOnElementsOfIncidentPage("statusBoxBefore");
			sleeper(sleepTime);
			selectDropdownOptions(incidentPagePropertiesPageProperties.getProperty("typeListOptions"), ActualData.get("STATUS"));
			sleeper(sleepTime);

			// Select AssignedTo from the dropdown menu
			clickOnElementsOfIncidentPage("assignedToBoxBefore");
			sleeper(sleepTime);
			selectDropdownOptions(incidentPagePropertiesPageProperties.getProperty("typeListOptions"), ActualData.get("ASSIGNED TO"));
			sleeper(sleepTime);

			// Enter device name in the text box
			sleeper(sleepTime);
			enterTextForIncidentPage("deviceNameSearchBox", ActualData.get("DEVICE NAME"));

			// Enter device serial number in the text box
			sleeper(sleepTime);
			enterTextForIncidentPage("deviceSerialNumberSearchBox", ActualData.get("DEVICE SERIAL NUMBER"));
			sleeper(sleepTime);

			// Select Device Manufacturer from the dropdown menu
			clickOnElementsOfIncidentPage("deviceManufactureBefore");
			sleeper(sleepTime);
			selectDropdownOptions(incidentPagePropertiesPageProperties.getProperty("typeListOptions"), ActualData.get("DEVICE MANUFACTURER"));
			sleeper(sleepTime);

			// Enter Title in the text box
			enterTextForIncidentPage("titleSearchBox", ActualData.get("TITLE"));
			sleeper(sleepTime);

			// Select Created By from the dropdown menu
			clickOnElementsOfIncidentPage("createdByBefore");
			sleeper(sleepTime);
			selectDropdownOptions(incidentPagePropertiesPageProperties.getProperty("typeListOptions"), ActualData.get("CREATED BY"));
			sleeper(sleepTime);

			// Select Occurred at from calendar control
			clickOnElementsOfIncidentPage("occurredAtBox");
			sleeper(sleepTime);
			clickOnElementsOfIncidentPage("occurredAtBox");
			sleeper(sleepTime);
			clickByJavaScriptOnIncidentPage("todaysDateButton");
			sleeper(sleepTime);
			clickByJavaScriptOnIncidentPage("occurredAtBox");
			sleeper(sleepTime);

			// Select Updated On from calendar control
			clickByJavaScriptOnIncidentPage("updatedOnBox");
			sleeper(sleepTime);
			clickOnElementsOfIncidentPage("updatedOnBox");
			sleeper(sleepTime);
			clickByJavaScriptOnIncidentPage("todaysDateButton");
			sleeper(sleepTime);
			clickByJavaScriptOnIncidentPage("updatedOnBox");

			// Enter Device Model Name in the text box
			sleeper(sleepTime);
			enterTextForIncidentPage("deviceModelNameSearchBox", ActualData.get("DEVICE MODEL NAME"));
			sleeper(sleepTime);
			LOGGER.info("Now click on generate excel button to generate the excel report for the searched id");
			clickOnElementsOfIncidentPage("generateXlsButton");
			sleeper(8000);// Need sleeper to download and to verify file is downloded or not, as this is
			// non-ui so will have to rely on explicit wait
			LOGGER.info("Save and open the downloaded excel report for data validation");
			// checking for the xls is downloaded or not
			boolean isFileDownlodedorNot = isFileDownloaded(ConstantPath.DOWNLOAD_PATH, "IncidentReport.xlsx");
			if (!isFileDownlodedorNot) {
				LOGGER.error("Excel File was not downloaded successfully.");
				return false;
			}

			// Reading XLS data
			ExcelReader excelReader = new ExcelReader(ConstantPath.DOWNLOAD_PATH + "/IncidentReport.xlsx");
			int incidentdataRowNumber = excelReader.getRowNumber(strDeviceSerialNumber);
			// Actual validation starts here, first match the column count in excel and
			// count shown on the ui
			if (excelReader.getSheetColumns(strIncidents, incidentdataRowNumber) == IncidentsVariables.NUMBER_OF_COLUMN_IN_INCIDENTXLS) {
				// Fetch Device serial number
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber).equalsIgnoreCase(strDeviceSerialNumber)) {
					ExcelData.put(strDeviceSerialNumber, excelReader.getCellData(strIncidents, 0, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Device Serial Number header is not there in Excel, Expected: " + strDeviceSerialNumber + ", Actual: " + excelReader.getCellData(strIncidents, 0, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 1, incidentdataRowNumber).equalsIgnoreCase(strDeviceName)) {
					ExcelData.put(strDeviceName, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 1, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Device Name header is not there in Excel, Expected: " + strDeviceName + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 1, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 2, incidentdataRowNumber).equalsIgnoreCase(strUserName)) {
					ExcelData.put(strUserName, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 2, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("User Name header is not there in Excel, Expected: " + strUserName + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 2, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 3, incidentdataRowNumber).equalsIgnoreCase(strIncidentID)) {
					ExcelData.put(strIncidentID.replace("#", ""), excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 3, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Incident ID header is not there in Excel, Expected: " + strIncidentID + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 3, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 4, incidentdataRowNumber).equalsIgnoreCase(strPriority)) {
					ExcelData.put(strPriority, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 4, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Priority header is not there in Excel, Expected: " + strPriority + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 4, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 5, incidentdataRowNumber).equalsIgnoreCase(strStatus)) {
					ExcelData.put(strStatus, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 5, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Status header is not there in Excel, Expected: " + strStatus + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 5, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 6, incidentdataRowNumber).equalsIgnoreCase(strDateCreated)) {
					ExcelData.put(strDateCreated, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 6, incidentdataRowNumber + 1).split(" ")[0]);
				} else {
					LOGGER.error("Date Created header is not there in Excel, Expected: " + strDateCreated + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 6, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 7, incidentdataRowNumber).equalsIgnoreCase(strType)) {
					ExcelData.put(strType, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 7, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Type header is not there in Excel, Expected: " + strType + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 7, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 8, incidentdataRowNumber).equalsIgnoreCase(strSubtype)) {
					ExcelData.put(strSubtype, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 8, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Subtype header is not there in Excel, Expected: " + strSubtype + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 8, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 9, incidentdataRowNumber).equalsIgnoreCase(strTitle)) {
					ExcelData.put(strTitle, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 9, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Title header is not there in Excel, Expected: " + strTitle + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 9, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 10, incidentdataRowNumber).equalsIgnoreCase(strDescription)) {
					ExcelData.put(strDescription, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 10, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Description is not there in Excel, Expected: " + strDescription + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 10, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 11, incidentdataRowNumber).equalsIgnoreCase(strDeviceOS)) {
					ExcelData.put(strDeviceOS, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 11, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Device Manufacturer header is not there in Excel, Expected: " + strDeviceOS + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 11, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 12, incidentdataRowNumber).equalsIgnoreCase(strDeviceManufacturer)) {
					ExcelData.put(strDeviceManufacturer, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 12, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Device Manufacturer header is not there in Excel, Expected: " + strDeviceManufacturer + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 12, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 13, incidentdataRowNumber).equalsIgnoreCase(strDeviceModelName)) {
					ExcelData.put(strDeviceModelName, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 13, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Device Model Name header is not there in Excel, Expected: " + strDeviceModelName + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 13, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 14, incidentdataRowNumber).equalsIgnoreCase(strAssignedTo)) {
					ExcelData.put(strAssignedTo, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 14, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Assigned To header is not there in Excel, Expected: " + strAssignedTo + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 14, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 15, incidentdataRowNumber).equalsIgnoreCase(strCompany)) {
					ExcelData.put(strCompany, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 15, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Company header is not there in Excel, Expected: " + strCompany + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 15, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 16, incidentdataRowNumber).equalsIgnoreCase(strCreatedBy)) {
					ExcelData.put(strCreatedBy, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 16, incidentdataRowNumber + 1));
				} else {
					LOGGER.error("Created By header is not there in Excel, Expected: " + strCreatedBy + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 16, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 17, incidentdataRowNumber).equalsIgnoreCase(strUpdatedOn)) {
					ExcelData.put(strUpdatedOn, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 17, incidentdataRowNumber + 1).split(" ")[0]);
				} else {
					LOGGER.error("Updated On header is not there in Excel, Expected: " + strUpdatedOn + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS + 18, incidentdataRowNumber));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 6).toLowerCase().contains(strCreatedBy.toLowerCase())) {
					ExcelData.put(strCreatedBy.toLowerCase(), excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 6).toLowerCase().replace("：", " "));
				} else {
					LOGGER.error("Created By {User Name} is not there in Excel, Expected: " + strCreatedBy + ": " + incidentData.get("strLoggedInUserName") + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 6));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 12).equalsIgnoreCase(strIncidents)) {
					ExcelData.put(strIncidents, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 12));
				} else {
					LOGGER.error("Incidents title header is not there in Excel, Expected: " + strIncidents + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 12));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 9).equalsIgnoreCase(strDescription + ":" + strListOfIncidents)) {
					ExcelData.put(strDescription + ":" + strListOfIncidents, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 9));
				} else {
					LOGGER.error("Description header is not there in Excel, Expected: " + strDescription + ":" + strListOfIncidents + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 9));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 8).equalsIgnoreCase(strCompany + ":" + incidentData.get("deviceCompanyName"))) {
					ExcelData.put(strCompany + ":" + incidentData.get("deviceCompanyName"), excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 8));
				} else {
					LOGGER.error("Company ID header is not there in Excel, Expected: " + strCompany + ":" + incidentData.get("deviceCompanyName") + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 8));
					return false;
				}
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 3).equalsIgnoreCase(strFilterCriteria)) {
					ExcelData.put(strFilterCriteria, excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 3));
				} else {
					LOGGER.error("Filter Criteria header is not there in Excel, Expected: " + strFilterCriteria + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 3));
					return false;
				}
				String strFilterValueInExcel;
				if (excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 3).toLowerCase().contains(strFilterCriteriaText.toLowerCase())) {
					// Fetch filter values from excel file
					for (int filterRow = 0; filterRow <= IncidentsVariables.NUMBER_OF_COLUMN_IN_INCIDENTXLS; filterRow++) {
						strFilterValueInExcel = excelReader.getCellData(strIncidents, filterRow, incidentdataRowNumber - 2);
						if (!Strings.isNullOrEmpty(strFilterValueInExcel)) {
							excelFilterCriteriaText.add(strFilterValueInExcel);
						} else {
							break;
						}
					}
				} else {
					LOGGER.error("Filter Criteria header is not there in Excel, Expected: " + strFilterCriteriaText + ", Actual: " + excelReader.getCellData(strIncidents, IncidentsVariables.START_COLUMN_IN_INCIDENTXLS, incidentdataRowNumber - 3));
					return false;
				}

				// Now add some extra data that was fetched during the flow and need to be
				// validated in excel report, this data is not available on search result
				ActualData.put(strUserName, incidentData.get("deviceUserName")); // User Name fetched from device page
				ActualData.put(strCreatedBy.toLowerCase(), strCreatedBy.toLowerCase() + " " + incidentData.get("strLoggedInUserName").toLowerCase());// 'Created
				// By'string
				// is
				// appended
				// to
				// match
				// value
				// in
				// excel
				// report
				ActualData.put(strDescription, incidentData.get("incidentdescription")); // Description fetched from
				// incident page
				ActualData.put(strIncidents, strIncidents); // Incident header inserted for validation
				ActualData.put(strDescription + ":" + strListOfIncidents, strDescription + ":" + strListOfIncidents); // Description header inserted for validation
				ActualData.put(strCompany + ":" + incidentData.get("deviceCompanyName"), strCompany + ":" + incidentData.get("deviceCompanyName")); // Company header inserted for
				// validation
				ActualData.put(strCompany, incidentData.get("deviceCompanyName"));
				ActualData.put(strFilterCriteria, strFilterCriteria); // Filter criteria header inserted for validation

				if (!incidentData.get("currentCreatedOn").contains("/")) {
					String[] splittedDate = incidentData.get("currentCreatedOn").split(" ");
					ActualData.put(strDateCreated, convertCreatedOnDateFormat(splittedDate[0] + " " + splittedDate[1] + " " + splittedDate[2])); // Date Created fetched
					// from incident page
				} else {
					ActualData.put(strDateCreated, formateDateAsExpected(incidentData.get("currentCreatedOn").split(" ")[0])); // Date Created
					// fetched from
					// incident page
				}
				ActualData.put(strUpdatedOn, formateDateAsExpected(getSubstringUsingRegExp(incidentData.get("incidentUpdatedOnText"), "(0?[1-9]|1[012])[\\/\\-](0?[1-9]|[12][0-9]|3[01])[\\/\\-]\\d{4}"))); // Updated On
				// fetched from
				// incident page
				ActualData.put(strFilterCriteriaText.toLowerCase(), "#id: " + IncidentIdOnUi);

				boolean dataFlag = true;// Default is true
				for (String key : ExcelData.keySet()) {
					if (!ActualData.containsValue(ExcelData.get(key))) {
						LOGGER.error("Value mismatch, Expected Value in excel report for: " + key + ", " + ExcelData.get(key) + ", Actual Value on UI: " + ActualData.get(key));
						return false;// Means data is there but values are not matching, test failed
					} else {
						LOGGER.info("Value matched, Expected Value in excel report, " + ExcelData.get(key) + ", Actual Value on UI: " + ActualData.get(key));
					}
				}
				// Validate applied filter values
				for (String filterExcelValue : excelFilterCriteriaText) {
					boolean dataFilterFlag = false;// Default is true
					for (String ExpectedUIValue : expectedFilterCriteriaText) {
						if (filterExcelValue.contains("UTC")) {
							dataFilterFlag = true;
							LOGGER.error("Expected applied filter: " + filterExcelValue + " is found in the Excel");
							break;
						} else if (ExpectedUIValue.equalsIgnoreCase(filterExcelValue.replace("#", ""))) {
							dataFilterFlag = true;
							LOGGER.error("Expected applied filter: " + filterExcelValue + " is found in the Excel");
							break;
						}
					}
					if (!dataFilterFlag) {
						dataFlag = false;// Means filters are not matching
						LOGGER.error("Expected applied filter: " + filterExcelValue + " is not found in the excel");
						break;
					}
				}
				return dataFlag; // Means we are good
			} else {
				LOGGER.error("Number of columns in excel file are not same as Expected: " + IncidentsVariables.NUMBER_OF_COLUMN_IN_INCIDENTXLS + "Actual: " + excelReader.getSheetColumns(strIncidents, incidentdataRowNumber));
				return false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in validateIncidentDataInXLS method:" + ex.toString());
			return false;
		}
	}

	/**
	 * Below method is for converting date as per excel report format
	 * 
	 * @param dateToBeFormated date that need to be formatted
	 * @return formated string date value
	 */
	private String formateDateAsExpected(String dateToBeFormated) {
		String dateAfterFormating = "";
		String[] dateArray = dateToBeFormated.split("/");
		dateAfterFormating = dateArray[2] + "/" + dateArray[0] + "/" + dateArray[1];
		return dateAfterFormating;
	}

	/**
	 * Below method is for fetching date string from a given string using regexp
	 * 
	 * @param mainString String that has date string
	 * @param patternString String RegExp Pattern
	 * @return string date value
	 */
	public String getSubstringUsingRegExp(String mainString, String patternString) {
		String foundString = "";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(mainString);
		if (matcher.find()) {
			foundString = matcher.group(0);
		}
		return foundString;
	}

	/**
	 * For getting incident created on date format in dd/mm/yyyy from dd-MMM-yyyy
	 * 
	 * @param incidentCreatedOn -incident Created On date in dd-MMM-yyyy format
	 * @return @
	 */
	public static String convertCreatedOnDateFormat(String incidentCreatedOn) throws Exception {
		String incidentCreatedOnConverted = "";
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("dd MMM yyyy").parse(incidentCreatedOn));
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			incidentCreatedOnConverted = sdf.format(cal.getTime());
			LOGGER.info(incidentCreatedOn + " converted by convertCreatedOnDateFormat to " + sdf.format(cal.getTime()));
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method convertCreatedOnDateFormat:" + ex.toString());
			throw ex;
		}
		return incidentCreatedOnConverted;
	}

	/**
	 * This method returns true if incident tab is visible for the user else return false
	 * 
	 * @param languageCode:Language code for localization testing
	 * @return true if incident tab is present for the logged in user else return false @
	 */
	public boolean isIncidentTabVisible(String languageCode) throws Exception {
		try {
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (!dashboardPage.waitForElementsOfDashboardPage("dashboardTab")) {
				dashboardPage.clickOnElementsOfDashboardPage("menuIcon");
			}
			clickOnElementsOfIncidentPage("incidentTab");
			waitForPageLoaded();
			waitForElementsOfIncidentPage("incidentTitle");
			sleeper(5000); //Without this sleeper, unable to validate title.
			if(getTextOfIncidentPage("incidentTitle").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "incidents.label")))
				return true;
			else
				return false;
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method isIncidentTabVisible" + ex.getMessage());
			throw ex;
		}
	}

	/**
	 * This method is validating weather incidents are editable to the logged in user
	 * 
	 * @return true if incidents are editable to the logged in user else return false @
	 */
	public boolean isIncidentsEditable() throws Exception {
		try {
			waitForPageLoaded();
			TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
			clickOnElementsOfIncidentPage("tableConfigurationButton");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			waitForElementsOfIncidentPage("selectAllCheckBox");
			clickOnElementsOfIncidentPage("selectAllIncident");
			if (verifyElementsOfIncidentPage("assignPriorityButton") && verifyElementsOfIncidentPage("changeStatusButton"))
				return true;
			else {
				return false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method isIncidenteditable" + ex.getMessage());
			throw ex;
		}
	}

	/**
	 * This is a method to click on hyperlink in list of elements
	 * 
	 * @param uiList - List of elements of column on UI and click on 1st element @
	 */

	public final void clickElementsOfIncidentPage(List<WebElement> uiList) {

		try {
			Iterator<WebElement> uiColumnListIterator = uiList.iterator();
			while (uiColumnListIterator.hasNext()) {
				WebElement element = uiColumnListIterator.next();
				if ((!element.getText().equals("")) && verifyElementsAreClickable(element)) {
					waitForPageLoaded();
					element.click();
					break;
				}

			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickElementsOfIncidentPage " + e.getMessage()));
		}

	}

	/**
	 * This method is used for clearing any filters applied on incident list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfIncidentListPage(String clearFilterKey) throws Exception {
		clearFilters(incidentPagePropertiesPageProperties.getProperty(clearFilterKey));
	}
	
	/**
	 * This is a method to store all incident into an array list.
	 * @param incidentNumber - Incident Name
	 * @throws Exception
	 */
	public final ArrayList<String> getAllIncidentInfo(String incidentName) throws Exception {
		try {
			ArrayList<String> incidentInfo = new ArrayList<String>();
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			incidentPage.enterTextForIncidentPage("idSearchBox", incidentName);
			sleeper(2000);
			if (incidentPage.verifyElementsOfIncidentPage("idList") == true) {
				if (incidentPage.getTextOfIncidentPage("idList").equals(incidentName)) {
					sleeper(3000);
					List<WebElement> detailsList = getElementsTillAllElementsPresent(incidentPagePropertiesPageProperties.getProperty("allDetailsOfSelectedIncidents"));
					for (int i = 1; i < detailsList.size(); i++) {
						String value = detailsList.get(i).getText();
						incidentInfo.add(value);
					}
					for (int i = 0; i < incidentInfo.size(); i++) {
						if (incidentInfo.get(i).equals("N/A")) {
							incidentInfo.set(i, "-");
						}
					}
				} else {
					LOGGER.error("Selected Incident is not present in the list");
				}
			}
			return incidentInfo;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAllIncidentInfo " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to get a list of elements present on Incident list page
	 * @param key - Locator of element
	 * @return - list of web elements
	 */
	public final List<WebElement> getElementsOfIncidentListPage(String key) {
		try {
			return getElementsTillAllElementsPresent(incidentPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsOfIncidentListPage " + e.getMessage()));
			return null;
		}
	}
	
	/**
	 * This method verifies the column headers for insights incident list page
	 * 
	 * @param languageCode
	 * @param string
	 * @param incidentListColumns
	 * @return
	 */
	public boolean verifyColumnsofInsightsIncidentList(String languageCode, String locator,
			String[] incidentListColumns) {
		boolean flag = false;
		int counter = 0;
		List<WebElement> insightsColumnHeader = getElementsOfIncidentListPage(locator);
		insightsColumnHeader.remove(0);
		for (WebElement we : insightsColumnHeader) {
			try {
				if (we.getText().equalsIgnoreCase(
						getTextLanguage(languageCode, "daas_ui", incidentListColumns[counter]))) {
					flag = true;
					counter++;
				} else {
					flag = false;
					LOGGER.error(we.getText() + " Header does not match at list table page.");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * This Method matches the count shown on insights widget and table
	 * 
	 * @return
	 */
	public boolean verifyCountOnInsightsIncidentPage(String widgetCount, String pageName) {
		boolean flag = false;
		try {
			String tableCount = getTextOfIncidentPage("insightsIncidentListTableCount");
			String[] splitTableCount = tableCount.split(" ");
			if (splitTableCount[1].equals(widgetCount)) {
				LOGGER.info(pageName + " widget and list table count matched and is validated successfully");
				flag = true;
			} else {
				LOGGER.error(pageName + " widget and list table count is mismatching");
				mousehoverOnIncientPage("hddtypeInsightsLegend");
				clickOnElementsOfIncidentPage("hddtypeInsightsLegend");
				return flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * This method checks for specific value not present in dropdown
	 * 
	 * @return
	 */
	public boolean verifyValueNotInDropDown(String dropDownListLocator, String value) {
		boolean flag = true;
		try {
			sleeper(2000);
			List<WebElement> subtypes = getElementsOfIncidentListPage(dropDownListLocator);
			for (WebElement we : subtypes) {
				if (we.getText().equalsIgnoreCase(value)) {
					flag = false;
					LOGGER.error(we.getText() + " is present in drop down which is not expected");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	/**
	 * This method verifies the column headers for insights bulk case creation pop
	 * up table
	 * 
	 * @param languageCode
	 * @param string
	 * @param incidentListColumns
	 * @return
	 */
	public boolean verifyColumnsofInsightsBulkCreationTable(String languageCode, String locator,
			String[] incidentListColumns) {
		boolean flag = false;
		int counter = 0;
		List<WebElement> insightsColumnHeader = getElementsOfIncidentListPage(locator);
		for (WebElement we : insightsColumnHeader) {
			try {
				if (we.getText()
						.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", incidentListColumns[counter]))) {
					flag = true;
					counter++;
				} else {
					flag = false;
					LOGGER.error(we.getText() + " Header does not match at list table page.");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * This method verifies the column names on the column settings pop up
	 * 
	 * @param languageCode
	 * @param locator
	 * @param columns
	 * @return
	 */
	public boolean verifyColumnsofInsightsPageSettingsPopUp(String languageCode, String locator, String[] columns) {
		boolean flag = false;
		List<WebElement> insightsColumnList = getElementsOfIncidentListPage(locator);
		for (int i = 0; i < columns.length; i++) {
			for (WebElement we : insightsColumnList) {
				try {
					if (we.getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", columns[i]))) {
						we.click();
						flag = true;
						LOGGER.info(we.getText() + " Column found in list");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	
	

	/**
		 * @param key
		 * @return
		 */
		public final boolean verifyElementPresentOnIncidentListPage(String key) {
			try {
				return waitUntillElementIsPresent(incidentPagePropertiesPageProperties.getProperty(key));
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method getTextOfIncidentPage " + e.getMessage()));
				return false;
			}
		}


		/**
		 * @return 
		 */
		public boolean verifyCreateCaseButtonNotDisplayedOnIncidentListPage() {

			try {
				sleeper(3000);
				clickByJavaScriptOnIncidentPage("clickIncidentCheckbox");
				clickByJavaScriptOnIncidentPage("sideBarCloseButton");
				sleeper(1000);
				return verifyElementPresentOnIncidentListPage("createCaseButton");

			} catch (Exception e) {
				return false;
			}

		}
		
}	