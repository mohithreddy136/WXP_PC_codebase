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

public class WEXPartnerSubscriptionPage extends CommonMethod {

	private WEXPartnerSubscriptionPage instance;
	private ObjectReader WEXPartnerSubscriptionPagePropertiesReader = new ObjectReader();
	private Properties WEXPartnerSubscriptionPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEXPartnerSubscriptionPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXPartnerSubscriptionPage.class) {
				if (instance == null) {
					instance = new WEXPartnerSubscriptionPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEXPartnerSubscriptionPage(WebDriver driver) throws IOException {
		WEXPartnerSubscriptionPageProperties = WEXPartnerSubscriptionPagePropertiesReader.getObjectRepository("WEXPartnerSubscriptionPage");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEXPartnerSubscriptionPage(String key) throws Exception {
		click(WEXPartnerSubscriptionPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEXPartnerSubscriptionPageProperties(String key) {
		try {
			return verifyElementIsVisible(WEXPartnerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEXPartnerSubscriptionPageProperties " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEXPartnerSubscriptionPage(String key) {
		try {
			return verifyElementIsPresent(WEXPartnerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEXPartnerSubscriptionPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEXPartnerSubscriptionPage(String key) throws Exception {
		return getTextBy(WEXPartnerSubscriptionPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEXPartnerSubscriptionPage(String key, String Text) {
		try {
			enterText(WEXPartnerSubscriptionPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextOfWEXPartnerSubscriptionPageProperties " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEXPartnerSubscriptionPage(String key) {
		try {
			scrollTillView(WEXPartnerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEXPartnerSubscriptionPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEXPartnerSubscriptionPage(String key) {
		try {
			mouseHover(WEXPartnerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEXPartnerSubscriptionPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEXPartnerSubscriptionPage(String key) throws Exception {
		clickByJavaScript(WEXPartnerSubscriptionPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOnWEXPartnerSubscriptionPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXPartnerSubscriptionPageProperties.getProperty(key), Text);
	}
	
	public final List<WebElement> getElementsTillAllElementsVisibleOnWEXPartnerSubscriptionPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEXPartnerSubscriptionPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWWEXPartnerSubscriptionPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEXPartnerSubscriptionPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEXPartnerSubscriptionPage(String key) throws Exception {
		actionClick(WEXPartnerSubscriptionPageProperties.getProperty(key));
	}
	
	public final void switchToPreviousTabOfWEXPartnerSubscriptionPage() {
		switchBackToPreviousTab();
	}
	
	public final void selectValueFromDropdownOnWEXPartnerSubscriptionPage(String dropDownInput, String inputField, String text, String value) throws Exception {
		actionClickOnWEXPartnerSubscriptionPage(dropDownInput);
		enterTextOfWEXPartnerSubscriptionPage(inputField,text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleOnWEXPartnerSubscriptionPage(value);
		for (WebElement country : countryList) {
			if (country.getText().equalsIgnoreCase(text)) {
				country.click();
				break;
			}
		}
	}
	
}
