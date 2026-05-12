package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;

public class WEPPartnerAlertsPage extends CommonMethod {

	private WEPPartnerAlertsPage instance;
	private ObjectReader WEPPartnerAlertsPagePropertiesReader = new ObjectReader();
	private Properties WEPPartnerAlertsPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEPPartnerAlertsPage.class);
	public static String uiVersion = System.getProperty("uiVersion");

	public WEPPartnerAlertsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPPartnerAlertsPage.class) {
				if (instance == null) {
					instance = new WEPPartnerAlertsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEPPartnerAlertsPage(WebDriver driver) throws IOException {
		WEPPartnerAlertsPageProperties = WEPPartnerAlertsPagePropertiesReader
				.getObjectRepository("WEPPartnerAlertsPage");
	}

	/**
	 * This method is used to click.
	 * 
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEPPartnerAlertsPage(String key) throws Exception {
		click(WEPPartnerAlertsPageProperties.getProperty(key));
	}

	public final void scrollDownAlertManagementPage() {
		jsDriver().executeScript("scroll(0, 750);");
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEPPartnerAlertsPage(String key) {
		try {
			return verifyElementIsVisible(WEPPartnerAlertsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEPPartnerAlertsPageProperties "
					+ e.getMessage()));
			return false;
		}
	}

	public final boolean waitForElementsOfWEPPartnerAlertsPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WEPPartnerAlertsPageProperties.getProperty(key), waitTime);
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPPartnerAlertsPage(String key) {
		try {
			return verifyElementIsPresent(WEPPartnerAlertsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerAlertsPage " + e.getMessage()));
			return false;
		}
	}

	public final boolean verifyElementIsClickableOfAlertManagementPage(String key) throws Exception {
				return verifyElementIsClickable(WEPPartnerAlertsPageProperties.getProperty(key));
	}
	
	
	public final String getTextOfWEPPartnerAlertsPage(String key) throws Exception {
		return getTextBy(WEPPartnerAlertsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key  - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEPPartnerAlertsPage(String key, String Text) {
		try {
			enterText(WEPPartnerAlertsPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextOfWEPPartnerAlertsPageProperties " + e.getMessage()));
		}
	}
	/**
	 * This method will enter the text in input field without even clearing the existing text
	 * @param key
	 * @param Text
	 * @throws Exception
	 */
	public final void enterTextwithoutclearForWEPPartnerAlertsPage(String key, String Text) throws Exception {
		
		enterTextwithoutclear(WEPPartnerAlertsPageProperties.getProperty(key), Text);
	}
	
	
public final void clearAndReplaceTextForWEPPartnerAlertsPage(String key, String Value) throws Exception {
		
	 clearAndReplaceText(WEPPartnerAlertsPageProperties.getProperty(key), Value);
	}
	
	
	
/**
	 * This is a method to scroll on WEP Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEPPartnerAlertsPage(String key) {
		try {
			scrollTillView(WEPPartnerAlertsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEPPartnerAlertsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEPPartnerAlertsPage(String key) {
		try {
			mouseHover(WEPPartnerAlertsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEPPartnerAlertsPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEPPartnerAlertsPage(String key) throws Exception {
		clickByJavaScript(WEPPartnerAlertsPageProperties.getProperty(key));
	}

	public final boolean matchTextOnWEPPartnerAlertsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEPPartnerAlertsPageProperties.getProperty(key), Text);
	}

	public final List<WebElement> getElementsTillAllElementsVisibleOnWEPPartnerAlertsPage(String key)
			throws Exception {
		return getElementsTillAllElementsVisible(WEPPartnerAlertsPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWWEPPartnerAlertsPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEPPartnerAlertsPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEPPartnerAlertsPage(String key) throws Exception {
		actionClick(WEPPartnerAlertsPageProperties.getProperty(key));
	}

	public final void switchToPreviousTabOfWEPPartnerAlertsPage() {
		switchBackToPreviousTab();
	}

	public final void selectValueFromDropdownOnWEPPartnerAlertsPage(String dropDownInput, String inputField,
			String text, String value) throws Exception {
		actionClickOnWEPPartnerAlertsPage(dropDownInput);
		enterTextOfWEPPartnerAlertsPage(inputField, text);
		List<WebElement> userRoleList = getElementsTillAllElementsVisibleOnWEPPartnerAlertsPage(value);
		for (WebElement userRole : userRoleList) {
			if (userRole.getText().equalsIgnoreCase(text)) {
				userRole.click();
				break;
			}
		}
	}

	/**
	 * This is a method to return elements of alert management list page
	 *
	 * @param key - Locator of element
	 * @return WebElement list that are present in that locator
	 */
	public final List<WebElement> getElementsOfAlertListPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(WEPPartnerAlertsPageProperties.getProperty(key));
	}

	/**
	 * This method is used to verify the table columns
	 * 
	 * @param expectedColumnList
	 * @return
	 * @throws Exception
	 */
	public boolean verifyTableColumns(List<String> expectedColumnList, String key) throws Exception {
		boolean flag = false;
		int counter = 0;
		try {
			List<WebElement> actualColumnList = getElementsOfAlertListPage(key);
			for (WebElement we : actualColumnList)

				if (we.getText().contains("sorted")) {
					String[] split = we.getText().split("\n");
					String columnName = split[0];

					if (columnName.equalsIgnoreCase(expectedColumnList.get(counter))) {
						flag = true;
						counter++;
					} else {
						flag = false;
						LOGGER.error(we.getText() + " Header does not match at list table page.");
						break;
					}
				} else if (we.getText().equalsIgnoreCase(expectedColumnList.get(counter))
						|| we.getText().toUpperCase().contains(expectedColumnList.get(counter).toUpperCase())) {
					flag = true;
					counter++;
				} else {
					flag = false;
					LOGGER.error(we.getText() + " Header does not match at list table page.");
					break;
				}
		} catch (Exception e) {
			LOGGER.error("Error while verifying table columns" + e.getMessage());
		}
		return flag;
	}

	/**
	 * This method designed to get the WebElement of a page
	 *
	 * @param key - Locator of an element
	 */
	private WebElement getElementOfWEPAlertManagementListPage(String key) throws Exception {
		return getElement(WEPPartnerAlertsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to clear the filter if present in UI
	 */
	public void clearFiltersOfWEPAlertManagementListPage(String clearFilterKey) throws Exception {
		clearFilters(WEPPartnerAlertsPageProperties.getProperty(clearFilterKey));
	}

	/**
	 * This is a method to clear and reset the device list table configuration
	 */
	public void ClearAndResetAlertManagementListTable() throws Exception {
		verifyElementsOfWEPPartnerAlertsPage("columnSettingIcon");
		clickByJavaScriptOnWEPPartnerAlertsPage("columnSettingIcon");
		sleeper(3000);
		WebElement resetBtn = getElementOfWEPAlertManagementListPage("resetToDefaultButton");
		if (Boolean.parseBoolean(resetBtn.getAttribute("aria-disabled"))) {
			TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
			tableConfigurationPage = tableConfigurationPage.getInstance();
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			sleeper(3000);
			clearFiltersOfWEPAlertManagementListPage("clearfilter");
			return;
		}
		resetTableConfiguration();
		clearFiltersOfWEPAlertManagementListPage("clearfilter");
	}

	/**
	 * This is a metod to get attribute of an element present on partner page
	 *
	 * @param key          - locator of the element
	 * @param desiredValue - desired attribute name
	 * @return - value of the attribute as a string
	 */

	/**
	 * This method is to get the attribute value for the provided tag
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public final String getAttributesOfWEPPartnerAlertsPage(String key, String value) throws Exception {
		String result = "false";
		return result = getAttribute(WEPPartnerAlertsPageProperties.getProperty(key), value) != null ? getAttribute(WEPPartnerAlertsPageProperties.getProperty(key), value) : result;
	}
 

	public final boolean verifyFilteredDataOnAlertManagementPage(String list, String filteredData) throws Exception {
		List<String> uiList = getTextOfList(WEPPartnerAlertsPageProperties.getProperty(list));
		for (String uis : uiList) {
			if (!filteredData.equalsIgnoreCase(uis)) {
				LOGGER.info("Fails to compare filtered data Actual=" + uis + " Expected=" + filteredData);
				return false;
			}
		}
		return true;
	}

	public final boolean waitForElementsOfAlertManagementPage(String key) throws Exception {
		return verifyElementIsVisible(WEPPartnerAlertsPageProperties.getProperty(key));
	}

	public final boolean verifyDropdownOptionOrderOnAlertManagementPage(String key) throws Exception {
		ArrayList<String> dropdownOptionList = new ArrayList<String>();
		List<WebElement> elements = getElementsTillAllElementsPresent(
				WEPPartnerAlertsPageProperties.getProperty(key));
		for (WebElement webElement : elements) {
			dropdownOptionList.add(webElement.getText().trim().replaceAll("-|_", ""));
		}
		String previous = "";

		for (final String current : dropdownOptionList) {
			if (current.compareTo(previous) < 0)
				return false;
			previous = current;
		}

		return true;
	}
	
	/*
	 * This is a method to get size of an all elements present on page
	 * 
	 * @param 	Key - locator of the element
	 * 
	 */
	public final int getSizeOfAllElementsVisibleOnWEPPartnerAlertsPage(String key)
			throws Exception {
		return getElementsTillAllElementsVisible(WEPPartnerAlertsPageProperties.getProperty(key)).size();
	}
	public final boolean verifyElementIsEnableOnWEPPartnerAlertsPage(String key) throws Exception {
		return verifyElementIsEnaledOrDisabled(WEPPartnerAlertsPageProperties.getProperty(key));
	        
	}
	public final boolean verifyElementIsEnabledOnWEPPartnerAlertsPage(String key) throws Exception {
		return verifyElementIsEnable(WEPPartnerAlertsPageProperties.getProperty(key));
	        
	}

}
