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

public class WEXCustomerSubscriptionPage extends CommonMethod {

	private WEXCustomerSubscriptionPage instance;
	private ObjectReader WEXCustomerSubscriptionPagePropertiesReader = new ObjectReader();
	private Properties WEXCustomerSubscriptionPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEXCustomerSubscriptionPage.class);
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEXCustomerSubscriptionPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXCustomerSubscriptionPage.class) {
				if (instance == null) {
					instance = new WEXCustomerSubscriptionPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEXCustomerSubscriptionPage(WebDriver driver) throws IOException {
		WEXCustomerSubscriptionPageProperties = WEXCustomerSubscriptionPagePropertiesReader.getObjectRepository("WEXCustomerSubscriptionPage");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEXCustomerSubscriptionPage(String key) throws Exception {
		click(WEXCustomerSubscriptionPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEXCustomerSubscriptionPageProperties(String key) {
		try {
			return verifyElementIsVisible(WEXCustomerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEXCustomerSubscriptionPageProperties " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEXCustomerSubscriptionPage(String key) {
		try {
			return verifyElementIsPresent(WEXCustomerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEXCustomerSubscriptionPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEXCustomerSubscriptionPage(String key) throws Exception {
		return getTextBy(WEXCustomerSubscriptionPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEXCustomerSubscriptionPage(String key, String Text) {
		try {
			enterText(WEXCustomerSubscriptionPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextOfWEXCustomerSubscriptionPageProperties " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEXCustomerSubscriptionPage(String key) {
		try {
			scrollTillView(WEXCustomerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEXCustomerSubscriptionPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEXCustomerSubscriptionPage(String key) {
		try {
			mouseHover(WEXCustomerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEXCustomerSubscriptionPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEXCustomerSubscriptionPage(String key) throws Exception {
		clickByJavaScript(WEXCustomerSubscriptionPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOnWEXCustomerSubscriptionPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXCustomerSubscriptionPageProperties.getProperty(key), Text);
	}
	
	public final List<WebElement> getElementsTillAllElementsVisibleOnWEXCustomerSubscriptionPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEXCustomerSubscriptionPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWWEXCustomerSubscriptionPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEXCustomerSubscriptionPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEXCustomerSubscriptionPage(String key) throws Exception {
		actionClick(WEXCustomerSubscriptionPageProperties.getProperty(key));
	}
	
	public final void switchToPreviousTabOfWEXCustomerSubscriptionPage() {
		switchBackToPreviousTab();
	}
	
	public final void selectValueFromDropdownOnWEXCustomerSubscriptionPage(String dropDownInput, String inputField, String text, String value) throws Exception {
		actionClickOnWEXCustomerSubscriptionPage(dropDownInput);
		enterTextOfWEXCustomerSubscriptionPage(inputField,text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleOnWEXCustomerSubscriptionPage(value);
		for (WebElement country : countryList) {
			if (country.getText().equalsIgnoreCase(text)) {
				country.click();
				break;
			}
		}
	}
	
}
