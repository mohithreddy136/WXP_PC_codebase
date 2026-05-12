package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WEXHelpAndSupportPage extends CommonMethod  {
	
	private WEXHelpAndSupportPage instance;
	private ObjectReader WEXhelpAndSupportPropertiesReader = new ObjectReader();
	private Properties WEXhelpAndSupportProperties;
	
	
	public WEXHelpAndSupportPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SettingsPage.class) {
				if (instance == null) {
					instance = new WEXHelpAndSupportPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public final String getTextOfWEXHelpAndSupportPage(String key) {
		try {
			return getTextBy(WEXhelpAndSupportProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
			return null;
		}
	}
	
	public WEXHelpAndSupportPage(WebDriver driver) throws IOException {
		WEXhelpAndSupportProperties = WEXhelpAndSupportPropertiesReader.getObjectRepository("WEXHelpAndSupport");
	}
	
	public final boolean matchTextOfWEXHelpAndSupportPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXhelpAndSupportProperties.getProperty(key), Text);
	}
	
	public final boolean verifyElementsOfWEXHelpAndSupportPage(String key) throws Exception {
		return verifyElementIsPresent(WEXhelpAndSupportProperties.getProperty(key));
	}
    
	public final void clickOnElementsOfWEXHelpAndSupportPage(String key) throws Exception {
		click(WEXhelpAndSupportProperties.getProperty(key));
	}
	
	public final void clickOnWebElementsOfWEXHelpAndSupportPage(WebElement key) throws Exception {
		clickWebelement(key);
	}
	
	public final void actionClickOfWEXHelpAndSupportPage(String key) throws Exception {
		actionClick(WEXhelpAndSupportProperties.getProperty(key));
	}
	
	public final void mouseHoverclickOfWEXHelpAndSupportPage(WebElement key) throws Exception {
		mouseHoverclick(key);
	}
	
	public final WebElement getElementOfWEXHelpAndSupportPage(String key) throws Exception {
		return getElement(WEXhelpAndSupportProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfWEXHelpAndSupportPage(String key) throws Exception {
		return verifyElementIsVisible(WEXhelpAndSupportProperties.getProperty(key));
	}
	
	public final void scrollTillViewWEXHelpAndSupportPage(String locator) throws Exception {
		scrollTillView(WEXhelpAndSupportProperties.getProperty(locator));
	}
	
	public final boolean selectTextValueFromDropdownOfWEXHelpAndSupportPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(WEXhelpAndSupportProperties.getProperty(dropdownListKey), elementText, WEXhelpAndSupportProperties.getProperty(dropdownBox));
	}
	
	public final void SelectTextPresentInDropdownOfWEXHelpAndSupportPage(String locator, String text) throws Exception{
	   SelectTextPresentInDropdown(WEXhelpAndSupportProperties.getProperty(locator), text);
	}
	
	public final void enterTextForWEXHelpAndSupportPage(String key, String Text) throws Exception {
		enterText(WEXhelpAndSupportProperties.getProperty(key), Text);
	}

}
