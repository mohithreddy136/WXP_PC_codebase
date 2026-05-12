package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

/**
 * This class implements methods related to system requirements page
 *
 */
public class SystemRequirementsPage extends CommonMethod {
	private ObjectReader SystemRequirementsPagePropertiesReader = new ObjectReader();
	private Properties SystemRequirementsPageProperties;
	private Properties selectedLanguageProperties;
	private SystemRequirementsPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public SystemRequirementsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (UserPage.class) {
				if (instance == null) {
					instance = new SystemRequirementsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public SystemRequirementsPage(WebDriver driver) throws IOException {
		SystemRequirementsPageProperties = SystemRequirementsPagePropertiesReader.getObjectRepository("SystemRequirementsPageV3");
	}
	
	/**
	 * @param language: Language code for localization testing
	 * @param localefolder: To specify the folder where the key is present
	 * @param key: Contains the string which is localized
	 * @return String which is localized
	 * @throws Exception
	 */
	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = SystemRequirementsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of element
	 * @param Text - Text to be matched
	 * @return - boolean value of whether the text present on element matches or not
	 */
	public final boolean matchTextOfSystemRequirementsPage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(SystemRequirementsPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error("Exception in matchTextOfSystemRequirementsPage" + e.getMessage());
			return false;
		}

	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfSystemRequirementsPage(String key) {
		try {
			return verifyElementIsPresent(SystemRequirementsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("Exception in verifyElementsOfSystemRequirementsPage" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This is a method to get text of an element
	 * 
	 * @param key - Locator of element
	 * @return - String value of the text on the element
	 */
	public final String getTextOfSystemRequirementsPage(String key) {
		try {
			return getTextBy(SystemRequirementsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
			return null;
		}
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfSystemRequirementsPage(String key) throws Exception {
		click(SystemRequirementsPageProperties.getProperty(key));
	}

	/**This method is used to click by javascript.
	 * @param key
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnSystemRequirementsPage(String key) throws Exception {
		clickByJavaScript(SystemRequirementsPageProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfSystemRequirementsPage(String key) throws Exception {
		return verifyElementIsVisible(SystemRequirementsPageProperties.getProperty(key));
	}
	
	public final boolean waitForPresenceOfElementsOfSystemRequirementsPage(String key) throws Exception {
		return waitUntillElementIsPresent(SystemRequirementsPageProperties.getProperty(key));
	}
}
