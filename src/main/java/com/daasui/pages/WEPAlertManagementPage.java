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

public class WEPAlertManagementPage extends CommonMethod {

	private WEPAlertManagementPage instance;
	private ObjectReader WEPAlertManagementPagePropertiesReader = new ObjectReader();
	private Properties WEPAlertManagementPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEPAlertManagementPage.class);
	public static String uiVersion = System.getProperty("uiVersion");

	public WEPAlertManagementPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPAlertManagementPage.class) {
				if (instance == null) {
					instance = new WEPAlertManagementPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEPAlertManagementPage(WebDriver driver) throws IOException {
		WEPAlertManagementPageProperties = WEPAlertManagementPagePropertiesReader
				.getObjectRepository("WEPAlertManagementPage");
	}

	/**
	 * This method is used to click.
	 * 
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEPAlertManagementPage(String key) throws Exception {
		click(WEPAlertManagementPageProperties.getProperty(key));
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
	public final boolean waitForElementsOfWEPAlertManagementPage(String key) {
		try {
			return verifyElementIsVisible(WEPAlertManagementPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEPAlertManagementPageProperties "
					+ e.getMessage()));
			return false;
		}
	}

	public final boolean waitForElementsOfWEPAlertManagementPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WEPAlertManagementPageProperties.getProperty(key), waitTime);
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPAlertManagementPage(String key) {
		try {
			return verifyElementIsPresent(WEPAlertManagementPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPAlertManagementPage " + e.getMessage()));
			return false;
		}
	}

	public final boolean verifyElementIsClickableOfAlertManagementPage(String key) throws Exception {
				return verifyElementIsClickable(WEPAlertManagementPageProperties.getProperty(key));
	}
	
	
	public final String getTextOfWEPAlertManagementPage(String key) throws Exception {
		return getTextBy(WEPAlertManagementPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key  - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEPAlertManagementPage(String key, String Text) {
		try {
			enterText(WEPAlertManagementPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextOfWEPAlertManagementPageProperties " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEP Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEPAlertManagementPage(String key) {
		try {
			scrollTillView(WEPAlertManagementPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEPAlertManagementPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEPAlertManagementPage(String key) {
		try {
			mouseHover(WEPAlertManagementPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEPAlertManagementPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEPAlertManagementPage(String key) throws Exception {
		clickByJavaScript(WEPAlertManagementPageProperties.getProperty(key));
	}

	public final boolean matchTextOnWEPAlertManagementPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEPAlertManagementPageProperties.getProperty(key), Text);
	}

	public final List<WebElement> getElementsTillAllElementsVisibleOnWEPAlertManagementPage(String key)
			throws Exception {
		return getElementsTillAllElementsVisible(WEPAlertManagementPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWWEPAlertManagementPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEPAlertManagementPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEPAlertManagementPage(String key) throws Exception {
		actionClick(WEPAlertManagementPageProperties.getProperty(key));
	}

	public final void switchToPreviousTabOfWEPAlertManagementPage() {
		switchBackToPreviousTab();
	}

	public final void selectValueFromDropdownOnWEPAlertManagementPage(String dropDownInput, String inputField,
			String text, String value) throws Exception {
		actionClickOnWEPAlertManagementPage(dropDownInput);
		enterTextOfWEPAlertManagementPage(inputField, text);
		List<WebElement> userRoleList = getElementsTillAllElementsVisibleOnWEPAlertManagementPage(value);
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
		return getElementsTillAllElementsPresent(WEPAlertManagementPageProperties.getProperty(key));
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
		return getElement(WEPAlertManagementPageProperties.getProperty(key));
	}

	/**
	 * This is a method to clear the filter if present in UI
	 */
	public void clearFiltersOfWEPAlertManagementListPage(String clearFilterKey) throws Exception {
		clearFilters(WEPAlertManagementPageProperties.getProperty(clearFilterKey));
	}

	/**
	 * This is a method to clear and reset the device list table configuration
	 */
	public void ClearAndResetAlertManagementListTable() throws Exception {
		verifyElementsOfWEPAlertManagementPage("columnSettingIcon");
		clickByJavaScriptOnWEPAlertManagementPage("columnSettingIcon");
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
	public final String getAttributesOfWEPAlertManagementPage(String key, String value) throws Exception {
		String result = "false";
		return result = getAttribute(WEPAlertManagementPageProperties.getProperty(key), value) != null ? getAttribute(WEPAlertManagementPageProperties.getProperty(key), value) : result;
	}
 

	public final boolean verifyFilteredDataOnAlertManagementPage(String list, String filteredData) throws Exception {
		List<String> uiList = getTextOfList(WEPAlertManagementPageProperties.getProperty(list));
		for (String uis : uiList) {
			if (!filteredData.equalsIgnoreCase(uis)) {
				LOGGER.info("Fails to compare filtered data Actual=" + uis + " Expected=" + filteredData);
				return false;
			}
		}
		return true;
	}

	public final boolean waitForElementsOfAlertManagementPage(String key) throws Exception {
		return verifyElementIsVisible(WEPAlertManagementPageProperties.getProperty(key));
	}

	public final boolean verifyDropdownOptionOrderOnAlertManagementPage(String key) throws Exception {
		ArrayList<String> dropdownOptionList = new ArrayList<String>();
		List<WebElement> elements = getElementsTillAllElementsPresent(
				WEPAlertManagementPageProperties.getProperty(key));
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
	public final int getSizeOfAllElementsVisibleOnWEPAlertManagementPage(String key)
			throws Exception {
		return getElementsTillAllElementsVisible(WEPAlertManagementPageProperties.getProperty(key)).size();
	}
	
	public final boolean verifyElementIsEnableOnWEPAlertManagementPage(String key) throws Exception {
		return verifyElementIsEnaledOrDisabled(WEPAlertManagementPageProperties.getProperty(key));
	        
	}
	
	public final boolean verifyElementIsEnabledOnWEPAlertManagementPage(String key) throws Exception {
		return verifyElementIsEnable(WEPAlertManagementPageProperties.getProperty(key));
	        
	}
	
	public final void findEligibleAlertForDisabling() throws Exception {
		List<WebElement> elements = getElementsTillAllElementsPresent(
				WEPAlertManagementPageProperties.getProperty("predefinedSelectAlertCheckbox"));
		int i =1;
		for (WebElement checkbox : elements) {
			checkbox.click();
			clickOnElementsOfWEPAlertManagementPage("disableAlert");
			LOGGER.info("Clicked on disable Alert - "+i);
			i++;
			sleeper(2000);
			if(!verifyElementsOfWEPAlertManagementPage("disableAlertErrorPopup"))
			{
				break;
			}
			clickOnElementsOfWEPAlertManagementPage("disableAlertErrorPopupCloseBtn");
			checkbox.click();
		}
	}

}
