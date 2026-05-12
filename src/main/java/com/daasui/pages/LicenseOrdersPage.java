package com.daasui.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class LicenseOrdersPage extends CommonMethod {
	private ObjectReader subscriptionOrdersPagePropertiesReader = new ObjectReader();
	private Properties subscriptionOrdersPageProperties;

	private LicenseOrdersPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public LicenseOrdersPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (IncidentPage.class) {
				if (instance == null) {
					instance = new LicenseOrdersPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public LicenseOrdersPage(WebDriver driver) throws IOException {
		subscriptionOrdersPageProperties = subscriptionOrdersPagePropertiesReader.getObjectRepository("LicensesOrdersPageV3");
	}

	/**
	 * This is a method to click on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void clickOnElementsOfSubscriptionOrdersPage(String key) {
		try {
			click(subscriptionOrdersPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementsOfSubscriptionOrdersPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get text of an element
	 * 
	 * @param key - Locator of element
	 * @return - String value of the text on the element
	 */
	public final String getTextOfSubscriptionOrdersPage(String key) {
		try {
			return getTextBy(subscriptionOrdersPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfSubscriptionOrdersPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfSubscriptionOrdersPage(String key) {
		try {
			return verifyElementIsPresent(subscriptionOrdersPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfSubscriptionOrdersPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfSubscriptionOrdersPage(String key) {
		try {
			return verifyElementIsVisible(subscriptionOrdersPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfSubscriptionOrdersPage " + e.getMessage()));
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
	public final boolean verifySearchValueOnSubscriptionOrdersPage(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionality(LanguageCode, subscriptionOrdersPageProperties.getProperty(textKey), Text, subscriptionOrdersPageProperties.getProperty(emptyTextKey), subscriptionOrdersPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOnIncident " + e.getMessage()));
			return false;
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
			return verifyFilterFunctionalityForSingleSelect(LanguageCode, subscriptionOrdersPageProperties.getProperty(checkboxKey), subscriptionOrdersPageProperties.getProperty(listOfElementKey), subscriptionOrdersPageProperties.getProperty(columnListKey), subscriptionOrdersPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterSingleSelect " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait untill an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfSubscriptionOrdersPage(String key) {
		try {
			verifyElementIsinvisibile(subscriptionOrdersPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfSubscriptionOrdersPage " + e.getMessage()));
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
			return verifyFilterFunctionalityForMultiSelectForDyanmicList(LanguageCode, subscriptionOrdersPageProperties.getProperty(checkboxKey), subscriptionOrdersPageProperties.getProperty(listOfElementKey), subscriptionOrdersPageProperties.getProperty(columnListKey), subscriptionOrdersPageProperties.getProperty(emptyTextKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyFilterMultiSelect " + e.getMessage()));
			return false;
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
			enterText(subscriptionOrdersPageProperties.getProperty(textKey), Text);
			int flag = 0;
			sleeper(3000);
			String empty_text = null, text1 = null, text2 = null;
			if (verifyElementIsVisible(subscriptionOrdersPageProperties.getProperty(emptyTextKey))) {
				empty_text = getTextBy(subscriptionOrdersPageProperties.getProperty(emptyTextKey));
				String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
				if (empty_text.contains(emptytext[0].concat(Text))) {
					flag = 1;
				}
				pressKey(Keys.ESCAPE);
			} else {
				List<WebElement> listOfitems = getElementsTillAllElementsVisible(subscriptionOrdersPageProperties.getProperty(checkboxKey));
				if (listOfitems.size() > 1) {
					listOfitems.get(0).click();
					sleeper(5000);
					List<WebElement> listOfitems1 = getElementsTillAllElementsVisible(subscriptionOrdersPageProperties.getProperty(checkboxKey));
					listOfitems1.get(listOfitems1.size() - 1).click();
					sleeper(5000); // Will be adding page refresh code
					List<WebElement> listOfitems2 = getElementsTillAllElementsVisible(subscriptionOrdersPageProperties.getProperty(listKey));
					text1 = listOfitems2.get(0).getText();
					List<WebElement> listOfitems3 = getElementsTillAllElementsVisible(subscriptionOrdersPageProperties.getProperty(listKey));
					text2 = listOfitems3.get(listOfitems3.size() - 1).getText();
					pressKey(Keys.ESCAPE);

					if (verifyElementIsVisible(subscriptionOrdersPageProperties.getProperty(emptyTextColumnKey))) {
						empty_text = getTextBy(subscriptionOrdersPageProperties.getProperty(emptyTextColumnKey));
						if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
							flag = 1;
						}
					} else {
						List<WebElement> columnlist = getElementsTillAllElementsVisible(subscriptionOrdersPageProperties.getProperty(columnListKey));
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
			enterText(subscriptionOrdersPageProperties.getProperty(textKey), Text);
			int flag = 0;
			sleeper(3000);
			String empty_text = null, text = null;
			if (verifyElementIsVisible(subscriptionOrdersPageProperties.getProperty(emptyTextKey))) {
				empty_text = getTextBy(subscriptionOrdersPageProperties.getProperty(emptyTextKey));
				String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
				if (empty_text.contains(emptytext[0].concat(Text))) {
					flag = 1;
				}
				pressKey(Keys.ESCAPE);
			} else {
				List<WebElement> elements = getElementsTillAllElementsVisible(subscriptionOrdersPageProperties.getProperty(listKey));
				List<WebElement> listOfCheckbox = getElementsTillAllElementsVisible(subscriptionOrdersPageProperties.getProperty(checkboxKey));
				if (!listOfCheckbox.get(0).isSelected()) {
					text = elements.get(0).getText();
					listOfCheckbox.get(0).click();
					pressKey(Keys.ESCAPE);
				}
				sleeper(2000);

				if (verifyElementIsVisible(subscriptionOrdersPageProperties.getProperty(emptyTextColumnKey))) {
					empty_text = getTextBy(subscriptionOrdersPageProperties.getProperty(emptyTextColumnKey));
					if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
						flag = 1;
					}
				} else {
					List<WebElement> columnList = getElementsTillAllElementsVisible(subscriptionOrdersPageProperties.getProperty(columnListKey));
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
	public final boolean selectLastOneWeekRangeOnSubscriptionOrdersListPage(String LanguageCode, String calenderKey, String monthKeyLeft, String monthKeyRight, String leftArrowKey, String daysKeyLeft, String daysKeyRight, String emptyTextKey, String columnListKey) {
		try {
			return verifyCalendarWithDateFormat(LanguageCode, subscriptionOrdersPageProperties.getProperty(calenderKey), subscriptionOrdersPageProperties.getProperty(monthKeyLeft), subscriptionOrdersPageProperties.getProperty(monthKeyRight), subscriptionOrdersPageProperties.getProperty(leftArrowKey), subscriptionOrdersPageProperties.getProperty(daysKeyLeft),
					subscriptionOrdersPageProperties.getProperty(daysKeyRight), subscriptionOrdersPageProperties.getProperty(emptyTextKey), subscriptionOrdersPageProperties.getProperty(columnListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectLastOneWeekRange " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to scroll on incident page
	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnSubscriptionOrdersListPage(String key) {
		try {
			scrollTillView(subscriptionOrdersPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnSubscriptionOrdersListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to verify if the element is enable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is enabled or not
	 */
	public final boolean verifyElementIsEnableOfSubscriptionOrdersListPage(String key) {
		try {
			return verifyElementIsEnable(subscriptionOrdersPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfSubscriptionOrdersListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is the method to verify if element is clickable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is clickable or not
	 */
	public final boolean verifyElementIsClickableOfSubscriptionOrdersListPage(String key) {
		try {
			return verifyElementIsClickable(subscriptionOrdersPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsClickableOfSubscriptionOrdersListPage " + e.getMessage()));
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
	public final boolean selectElementFromDropDownofSubscriptionOrdersListPage(String dropdownId, String key, String text) {
		try {
			click(subscriptionOrdersPageProperties.getProperty(dropdownId));
			return selectFromDropdown(subscriptionOrdersPageProperties.getProperty(dropdownId), subscriptionOrdersPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectElementFromDropDownofSubscriptionOrdersListPage " + e.getMessage()));
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
			return !getElement(subscriptionOrdersPageProperties.getProperty(navigationItemPreviouskey)).getAttribute("class").contains("disabled");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getButtonEnabilityStatus " + e.getMessage()));
			return false;
		}

	}
	
	/**
	 * This method is used to get unique strings of elements from a subscription order page
	 * 
	 * @param listLocator - list locator
	 * @return - List<String>
	 * @throws Exception
	 */
	public final List<String> getUniqueElementsFromSubscriptionOrdersPageList(String listLocator) throws Exception {
		return getUniqueElementsofStringsFromList(subscriptionOrdersPageProperties.getProperty(listLocator));
	}

	/**
	 * This method is used to enter text on subscription order page
	 *
	 * @param key
	 * @param Text
	 * @throws Exception
	 */

	public final void enterTextForSubscriptionOrderPage(String key, String Text) throws Exception {
		enterText(subscriptionOrdersPageProperties.getProperty(key), Text);
	}
}
