package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class PaginationPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader paginationPagePropertiesReader = new ObjectReader();
	private Properties paginationPagePropertiesPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private PaginationPage instance;

	public PaginationPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (PaginationPage.class) {
				if (instance == null) {
					instance = new PaginationPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public PaginationPage(WebDriver driver) throws IOException {
		paginationPagePropertiesPageProperties = paginationPagePropertiesReader.getObjectRepository("PaginationV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = paginationPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final String getTextOfPaginationPage(String key) throws Exception {
		return getTextBy(paginationPagePropertiesPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait until an element is invisible
	 * 
	 * @param key
	 *            - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfPaginationPage(String key) {
		try {
			verifyElementIsinvisibile(paginationPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfPaginationPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to verify if the elemeent is present
	 * 
	 * @param key - locator of element
	 * @return - - the boolean value of whether the element is present or not
	 * @throws Exception
	 */
	public final boolean verifyElementsOfPaginationPage(String key) throws Exception {
		return verifyElementIsPresent(paginationPagePropertiesPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify if element is enable or not
	 * 
	 * @param key - locator of element
	 * @return - the boolean value of whether the element is enable or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsEnableOfPaginationPage(String key) throws Exception {
		return verifyElementIsEnable(paginationPagePropertiesPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify if element is clickable or not
	 * 
	 * @param key - locator of element
	 * @return - the boolean value of whether the element is clickable or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsClickableOfPaginationPage(String key) throws Exception {
		return verifyElementIsClickable(paginationPagePropertiesPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element to be visible
	 * 
	 * @param key - locator of element
	 * @return - the boolean value of whether the element is visible or not
	 * @throws Exception
	 */
	public final boolean waitForElementsOfPaginationPage(String key) throws Exception {
		return verifyElementIsVisible(paginationPagePropertiesPageProperties.getProperty(key));
	}

	/**
	 * This is the method to get the enability status for link/button
	 * 
	 * @param navigationItemPreviouskey
	 *            - locator for the button/link to be tested
	 * @return - boolean value of the enability status
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemPreviouskey) {
		try {
			WebElement element = null;
			element = getDriver().findElement(this.getObject(paginationPagePropertiesPageProperties.getProperty(navigationItemPreviouskey)));
			jsDriver().executeScript("arguments[0].hasAttribute(\"disabled\");", element);
			return element.isEnabled();
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getButtonEnabilityStatus " + e.getMessage()));
			return false;
		}
	}

	public final int getTotalRecordCount(String key) {
		try {
			int totalRecord = 0;
			String paginationText = getTextOfPaginationPage(key);
			String [] totalRecordArray = paginationText.split(" ");
			totalRecord = Integer.parseInt(totalRecordArray[1].trim());
			return totalRecord;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTotalRecordCount " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * This is the method to get which pagination option is selected
	 * 
	 * @param dropdownIdKey
	 *            - Locator of element to open the pagination dropdown
	 * @param dropdownOptionlistKey
	 *            - Locator of list of pagination options
	 * @return - Pagination option which is already selected
	 */
	public final int getSelectedOptionTextofPaginationCompanyPage(String dropdownIdKey, String dropdownOptionlistKey) {
		try {
			clickOnElementsOfPaginationPage(dropdownIdKey);
			return getSelectedDropdownOptionOnPaginationPage(dropdownOptionlistKey);
		} catch (Exception e) {
			LOGGER.error(
					("Exception occured in method getSelectedOptionTextofPaginationCompanyPage " + e.getMessage()));
			return 0;
		}

	}

	public final int getSelectedDropdownOptionOnPaginationPage(String dropdownOptionlistKey) throws Exception
	{
		return getSelectedDropdownOptionOnPagination(paginationPagePropertiesPageProperties.getProperty(dropdownOptionlistKey));
	}

	/**
	 * This is a method to select element from a dropdown by passing dynamic xpath
	 * 
	 * @param dropdownId
	 *            - Locator of the arrow present on dropdown
	 * @param key
	 *            - Locator of the list of dropdown options
	 * @param text
	 *            - Text of the option which is to be selected
	 * @return - Option selected
	 */
	public final boolean selectElementFromDropDownOfPaginationPage(String dropdownId, String key, String text) {
		try {
			click(paginationPagePropertiesPageProperties.getProperty(dropdownId));
			return selectFromDropdown(paginationPagePropertiesPageProperties.getProperty(dropdownId),
					paginationPagePropertiesPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectElementFromDropDownOfPaginationPage " + e.getMessage()));
			return false;
		}
	}

	public final void clickOnElementsOfPaginationPage(String key) throws Exception {
		click(paginationPagePropertiesPageProperties.getProperty(key));
	}

	/**
	* This is a method to get attribute of an element
	*
	* @param key - Locator of element
	* @param value - the name of attribute
	* @return - String value of the attribute
	*/
	public final String getAttributesOfPaginationPage(String key, String value) {
	try {
	return getAttribute(paginationPagePropertiesPageProperties.getProperty(key), value);
	} catch (Exception e) {
	LOGGER.error(("Exception occured in method getAttributesOfPaginationPage " + e.getMessage()));
	return null;
	}
	}
}

