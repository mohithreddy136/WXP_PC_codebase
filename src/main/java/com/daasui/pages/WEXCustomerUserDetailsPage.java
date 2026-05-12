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
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;

public class WEXCustomerUserDetailsPage extends CommonMethod {

	private WEXCustomerUserDetailsPage instance;
	private ObjectReader WEXCustomerUserDetailsPagePropertiesReader = new ObjectReader();
	private Properties WEXCustomerUserDetailsPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");

	public WEXCustomerUserDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXCustomerUserDetailsPage.class) {
				if (instance == null) {
					instance = new WEXCustomerUserDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEXCustomerUserDetailsPage(WebDriver driver) throws IOException {
		WEXCustomerUserDetailsPageProperties = WEXCustomerUserDetailsPagePropertiesReader
				.getObjectRepository("WEXCustomerUserDetailsPage");
	}

	/**
	 * This method is used to click.
	 * 
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEXCustomerUserDetailsPage(String key) throws Exception {
		click(WEXCustomerUserDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEXCustomerUserDetailsPage(String key) {
		try {
			return verifyElementIsVisible(WEXCustomerUserDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEXCustomerUserDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEXCustomerUserDetailsPage(String key) {
		try {
			return verifyElementIsPresent(WEXCustomerUserDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEXCustomerUserDetailsPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEXCustomerUserDetailsPage(String key) throws Exception {
		return getTextBy(WEXCustomerUserDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key  - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEXCustomerUserDetailsPage(String key, String Text) {
		try {
			enterText(WEXCustomerUserDetailsPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXCustomerUsersDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEXCustomerUserDetailsPage(String key) {
		try {
			scrollTillView(WEXCustomerUserDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEXCustomerUserDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEXCustomerUserDetailsPage(String key) {
		try {
			mouseHover(WEXCustomerUserDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEXCustomerUserDetailsPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEXCustomerUserDetailsPage(String key) throws Exception {
		clickByJavaScript(WEXCustomerUserDetailsPageProperties.getProperty(key));
	}

	public final boolean matchTextOnWEXCustomerUserDetailsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXCustomerUserDetailsPageProperties.getProperty(key), Text);
	}

	public final List<WebElement> getElementsTillAllElementsVisibleOnWEXCustomerUserDetailsPage(String key)
			throws Exception {
		return getElementsTillAllElementsVisible(WEXCustomerUserDetailsPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWEXCustomerUserDetailsPage(String dropdownListKey)
			throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEXCustomerUserDetailsPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEXCustomerUserDetailsPage(String key) throws Exception {
		actionClick(WEXCustomerUserDetailsPageProperties.getProperty(key));
	}

	public final void selectValueFromDropdownOnWEXCustomerUserDetailsPage(String dropDownInput, String inputField,
			String text, String value) throws Exception {
		actionClickOnWEXCustomerUserDetailsPage(dropDownInput);
		enterTextOfWEXCustomerUserDetailsPage(inputField, text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleOnWEXCustomerUserDetailsPage(value);
		for (WebElement country : countryList) {
			if (country.getText().equalsIgnoreCase(text)) {
				country.click();
				break;
			}
		}
	}

	/**
	 * This is a method to verify value of the element
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 * @throws Exception
	 */
	public final boolean checkIDPTypeIsPresent(String key) throws Exception {
		String value = getTextOfWEXCustomerUserDetailsPage(key);
		System.out.println(value);
		if (value.equals("HPID") || value.equals("AAD"))
			return true;
		else
			return false;
	}

}
