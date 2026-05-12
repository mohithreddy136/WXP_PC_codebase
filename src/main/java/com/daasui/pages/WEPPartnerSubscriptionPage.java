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

public class WEPPartnerSubscriptionPage extends CommonMethod {

	private WEPPartnerSubscriptionPage instance;
	private ObjectReader WEPPartnerSubscriptionPagePropertiesReader = new ObjectReader();
	private Properties WEPPartnerSubscriptionPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPPartnerSubscriptionPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPPartnerSubscriptionPage.class) {
				if (instance == null) {
					instance = new WEPPartnerSubscriptionPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEPPartnerSubscriptionPage(WebDriver driver) throws IOException {
		WEPPartnerSubscriptionPageProperties = WEPPartnerSubscriptionPagePropertiesReader.getObjectRepository("WEPPartnerSubscriptionPage");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEPPartnerSubscriptionPage(String key) throws Exception {
		click(WEPPartnerSubscriptionPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEPPartnerSubscriptionPageProperties(String key) {
		try {
			return verifyElementIsVisible(WEPPartnerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEPPartnerSubscriptionPageProperties " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPPartnerSubscriptionPage(String key) {
		try {
			return verifyElementIsPresent(WEPPartnerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPPartnerSubscriptionPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEPPartnerSubscriptionPage(String key) throws Exception {
		return getTextBy(WEPPartnerSubscriptionPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEPPartnerSubscriptionPage(String key, String Text) {
		try {
			enterText(WEPPartnerSubscriptionPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextOfWEPPartnerSubscriptionPageProperties " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEP Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEPPartnerSubscriptionPage(String key) {
		try {
			scrollTillView(WEPPartnerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEPPartnerSubscriptionPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEPPartnerSubscriptionPage(String key) {
		try {
			mouseHover(WEPPartnerSubscriptionPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEPPartnerSubscriptionPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEPPartnerSubscriptionPage(String key) throws Exception {
		clickByJavaScript(WEPPartnerSubscriptionPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOnWEPPartnerSubscriptionPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEPPartnerSubscriptionPageProperties.getProperty(key), Text);
	}
	
	public final List<WebElement> getElementsTillAllElementsVisibleOnWEPPartnerSubscriptionPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEPPartnerSubscriptionPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWWEPPartnerSubscriptionPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEPPartnerSubscriptionPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEPPartnerSubscriptionPage(String key) throws Exception {
		actionClick(WEPPartnerSubscriptionPageProperties.getProperty(key));
	}
	
	public final void switchToPreviousTabOfWEPPartnerSubscriptionPage() {
		switchBackToPreviousTab();
	}
	
	public final void selectValueFromDropdownOnWEPPartnerSubscriptionPage(String dropDownInput, String inputField, String text, String value) throws Exception {
		actionClickOnWEPPartnerSubscriptionPage(dropDownInput);
		enterTextOfWEPPartnerSubscriptionPage(inputField,text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleOnWEPPartnerSubscriptionPage(value);
		for (WebElement country : countryList) {
			if (country.getText().equalsIgnoreCase(text)) {
				country.click();
				break;
			}
		}
	}
	
}
