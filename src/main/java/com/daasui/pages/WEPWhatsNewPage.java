package com.daasui.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WEPWhatsNewPage extends CommonMethod {
	
	private WEPWhatsNewPage instance;
	private Properties selectedLanguageProperties;
	private ObjectReader WEPWhatsNewPagePropertiesReader = new ObjectReader();
	private Properties WEPWhatsNewPage;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPWhatsNewPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPWhatsNewPage.class) {
				if (instance == null) {
					instance = new WEPWhatsNewPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEPWhatsNewPage(WebDriver driver) throws IOException {
		WEPWhatsNewPage = WEPWhatsNewPagePropertiesReader.getObjectRepository("WEPWhatsNewPage");	
	}
	
	public final String getTextLanguage(String language, String localeFolder, String key) throws Exception {
		selectedLanguageProperties = WEPWhatsNewPagePropertiesReader.getLanguageObjectRepository(localeFolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfWEPWhatsNewPage(String key) throws Exception {
		return verifyElementIsPresent(WEPWhatsNewPage.getProperty(key));
	}

	public final boolean waitForElementsOfWEPWhatsNewPage(String key) throws Exception {
		return verifyElementIsVisible(WEPWhatsNewPage.getProperty(key));
	}

	public final boolean matchTextOfWEPWhatsNewPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEPWhatsNewPage.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfWEPWhatsNewPage(String key) throws Exception {
		return verifyElementIsEnable(WEPWhatsNewPage.getProperty(key));
	}

	public final String getTextOfWEPWhatsNewPage(String key) throws Exception {
		return getTextBy(WEPWhatsNewPage.getProperty(key));
	}

	public final String getAttributesOfWEPWhatsNewPage(String key, String value) throws Exception {
		return getAttribute(WEPWhatsNewPage.getProperty(key), value);
	}
	
	public final void clickOnElementsOfWEPWhatsNewPage(String key) throws Exception {
		click(WEPWhatsNewPage.getProperty(key));
	}

	public final void clickOnWebElementsOfWEPWhatsNewPage(WebElement key) throws Exception {
		clickWebelement(key);
	}
	
	
	public final void enterTextForWEPWhatsNewPage(String key, String Text) throws Exception {
		enterText(WEPWhatsNewPage.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfWEPWhatsNewPage(String key) throws Exception {
		return verifyElementIsClickable(WEPWhatsNewPage.getProperty(key));
	}

	public final void MoveoverElementForWEPWhatsNewPage(String key) throws Exception {
		moveToElements(WEPWhatsNewPage.getProperty(key));
	}

	public final List<WebElement> getElementsOfWEPWhatsNewPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(WEPWhatsNewPage.getProperty(key));
	}

	public final WebElement getElementOfWhatsNewPage(String key) throws Exception {
		return getElement(WEPWhatsNewPage.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofWEPWhatsNewPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEPWhatsNewPage.getProperty(key));
	}

	public final void scrollDownWhatsNew() {
		jsDriver().executeScript("scroll(0, 750);");
	}

	public final void waitUntilElementIsInvisibleOfWEPWhatsNewPage(String key) throws Exception {
		verifyElementIsinvisibile(WEPWhatsNewPage.getProperty(key));
	}

	public final void mouseHoverclickOfWhatsNewPage(WebElement key) throws Exception {
		mouseHoverclick(key);
	}

	public final boolean selectTextValueFromDropdownOfWhatsNewPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(selectedLanguageProperties.getProperty(dropdownListKey), elementText, selectedLanguageProperties.getProperty(dropdownBox));
	}

	public final void enterTextOfWhatsNewPage(String key, String Text) throws Exception {
		enterText(selectedLanguageProperties.getProperty(key), Text);
	}

	public final void enterTextForOfWhatsNewPage(String key, String Text) throws Exception {
		enterText(selectedLanguageProperties.getProperty(key), Text);
	}

	public final String[] generateAndEnterRandomVersion() {
		String[] randomString = new String[3];

		try {
			Random random = new Random();

			for (int i = 0; i < 3; i++) {
				randomString[i] = String.valueOf(random.nextInt(100));
			}

			String version = randomString[0] + "." + randomString[1] + "." + randomString[2];
			enterTextForOfWhatsNewPage("releaseversiontextbox", version);
			LOGGER.info("Random version '" + version + "' is generated and entered in the release version textbox.");
		} catch (Exception e) {
			LOGGER.error("Error while generating or entering the random version string: ", e);
		}

		return randomString;
	}

	 /**
     * This is a method to click on an element using javascript
     *
     * @param key - locator of element
     * @throws Exception
     */
    public final void clickByJavaScriptOnfWEPWhatsNewPage(String key) throws Exception {
        clickByJavaScript(WEPWhatsNewPage.getProperty(key));
    }
	
	/**
     * This is a method to click on webelement present on whats new page
     *
     * @param locator - locator of the element
     */
    public final boolean clickOnWebelementOfWEPWhatsNewPage(String key) {
        try {
//            clickWebelement(locator);
            clickByJavaScript(WEPWhatsNewPage.getProperty(key));
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnWebelementOfWEPWhatsNewPage " + e.getMessage()));
            return false;
        }
    }
    
	/**
	 * This method verifies button presence on What's New page.
	 *
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyReleaseButton() {
		try {
			boolean flag = false;
			if (verifyElementsOfWEPWhatsNewPage("draftNewReleaseButtonFirst") || verifyElementsOfWEPWhatsNewPage("draftNewReleaseButton")) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyReleaseButton: " + e.getMessage());
			return false;
		}
	}
	
	 /**
     * This is a method to verify if an element on partner page is selected
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is selected
     */
    public final boolean verifyElementIsSelectedOfWEPWhatsNewPage(String key) {
        try {
            return verifyElementIsSelected(WEPWhatsNewPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsSelectedOfWEPWhatsNewPage " + e.getMessage()));
            return false;
        }
    }
    
    
    /**
     * This is a method to hover mouse on an element
     *
     * @param key - Locator of element
     */
    public final void mousehoverOnWEPWhatsNewPage(String key) {
        try {
            mouseHover(WEPWhatsNewPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnPartnerPage " + e.getMessage()));
        }
    }

    
  /** This is a method to open the dropdown of the whats new page
    *
    * @param key - locator of the element
    */
   public final void openDropDownOfWEPWhatsNewPage(String dropDownKey) {
       try {
           verifyElementsOfWEPWhatsNewPage(dropDownKey);
           mousehoverOnWEPWhatsNewPage(dropDownKey);
           sleeper(1000);
           clickByJavaScriptOnfWEPWhatsNewPage(dropDownKey);
           sleeper(1000);
           clickOnElementsOfWEPWhatsNewPage(dropDownKey);
           sleeper(1000);
           LOGGER.info("Clicked on dropdown");
       } catch (Exception e) {
           LOGGER.error(("Exception occured in method openDropDownOfWEPPartnerCustomersPage " + e.getMessage()));
       }
   }
	
}
