package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

/**
 * This class implements methods related to subscription details page
 *
 */
public class LicensesDetailsPage extends CommonMethod {
	private ObjectReader LicensessDetailsPagePropertiesReader = new ObjectReader();
	private Properties LicensessDetailsPageProperties;
	private Properties selectedLanguageProperties;
	private LicensesDetailsPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public LicensesDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (UserPage.class) {
				if (instance == null) {
					instance = new LicensesDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public LicensesDetailsPage(WebDriver driver) throws IOException {
		LicensessDetailsPageProperties = LicensessDetailsPagePropertiesReader.getObjectRepository("LicensesDetailsPageV3");
	}
	
	/**
	 * @param language: Language code for localization testing
	 * @param localefolder: To specify the folder where the key is present
	 * @param key: Contains the string which is localized
	 * @return String which is localized
	 * @throws Exception
	 */
	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = LicensessDetailsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of element
	 * @param Text - Text to be matched
	 * @return - boolean value of whether the text present on element matches or not
	 */
	public final boolean matchTextOfLicensessDetailsPage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(LicensessDetailsPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error("Exception in matchTextOfLicensessDetailsPage" + e.getMessage());
			return false;
		}

	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfLicensessDetailsPage(String key) {
		try {
			return verifyElementIsPresent(LicensessDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("Exception in verifyElementsOfLicensessDetailsPage" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This is a method to get text of an element
	 * 
	 * @param key - Locator of element
	 * @return - String value of the text on the element
	 */
	public final String getTextOfLicensessDetailsPage(String key) {
		try {
			return getTextBy(LicensessDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfLicensessDetailsPage " + e.getMessage()));
			return null;
		}
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfLicensessDetailsPage(String key) throws Exception {
		click(LicensessDetailsPageProperties.getProperty(key));
	}

	/**This method is used to click by javascript.
	 * @param key
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnLicensessDetailsPage(String key) throws Exception {
		clickByJavaScript(LicensessDetailsPageProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfLicensessDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(LicensessDetailsPageProperties.getProperty(key));
	}
	
	public final boolean waitForPresenceOfElementsOfLicensessDetailsPage(String key) throws Exception {
		return waitUntillElementIsPresent(LicensessDetailsPageProperties.getProperty(key));
	}
	
	public final void enterTextForLicensessDetailsPage(String key, String Text) throws Exception {
		enterText(LicensessDetailsPageProperties.getProperty(key), Text);
	}
	
	public final void scrollOnLicensessDetailsPage(String key) throws Exception {
		scrollTillView(LicensessDetailsPageProperties.getProperty(key));
	}
	
	public final void clearTextOnLicenseDetailsPage(String key) throws Exception {
		clearText(LicensessDetailsPageProperties.getProperty(key));
	}
}
