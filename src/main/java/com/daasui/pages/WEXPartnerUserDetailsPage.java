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

public class WEXPartnerUserDetailsPage extends CommonMethod {

	private WEXPartnerUserDetailsPage instance;
	private ObjectReader WEXPartnerUserDetailsPagePropertiesReader = new ObjectReader();
	private Properties 	WEXPartnerUserDetailsPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEXPartnerUserDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXPartnerUserDetailsPage.class) {
				if (instance == null) {
					instance = new WEXPartnerUserDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEXPartnerUserDetailsPage(WebDriver driver) throws IOException {
		WEXPartnerUserDetailsPageProperties = WEXPartnerUserDetailsPagePropertiesReader.getObjectRepository("WEXPartnerUserDetailsPage");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEXPartnerUserDetailsPage(String key) throws Exception {
		click(WEXPartnerUserDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEXPartnerUserDetailsPage(String key) {
		try {
			return verifyElementIsVisible(WEXPartnerUserDetailsPageProperties.getProperty(key));
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
	public final boolean verifyElementsOfWEXPartnerUserDetailsPage(String key) {
		try {
			return verifyElementIsPresent(WEXPartnerUserDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEXCustomerUserDetailsPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEXPartnerUserDetailsPage(String key) throws Exception {
		return getTextBy(WEXPartnerUserDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEXPartnerUserDetailsPage(String key, String Text) {
		try {
			enterText(WEXPartnerUserDetailsPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXCustomerUsersDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEXPartnerUserDetailsPage(String key) {
		try {
			scrollTillView(WEXPartnerUserDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEXCustomerUserDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEXPartnerUserDetailsPage(String key) {
		try {
			mouseHover(WEXPartnerUserDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEXCustomerUserDetailsPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEXPartnerUserDetailsPage(String key) throws Exception {
		clickByJavaScript(WEXPartnerUserDetailsPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOnWEXPartnerUserDetailsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXPartnerUserDetailsPageProperties.getProperty(key), Text);
	}
	
	public final List<WebElement> getElementsTillAllElementsVisibleOnWEXPartnerUserDetailsPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEXPartnerUserDetailsPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWEXPartnerUserDetailsPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEXPartnerUserDetailsPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEXPartnerUserDetailsPage(String key) throws Exception {
		actionClick(WEXPartnerUserDetailsPageProperties.getProperty(key));
	}
	
	public final void selectValueFromDropdownOnWEXPartnerUserDetailsPage(String dropDownInput, String inputField, String text, String value) throws Exception {
		actionClickOnWEXPartnerUserDetailsPage(dropDownInput);
		enterTextOfWEXPartnerUserDetailsPage(inputField,text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleOnWEXPartnerUserDetailsPage(value);
		for (WebElement country : countryList) {
			if (country.getText().equalsIgnoreCase(text)) {
				country.click();
				break;
			}
		}
	}
	
}
