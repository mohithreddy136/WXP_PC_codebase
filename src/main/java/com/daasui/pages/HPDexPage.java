package com.daasui.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class HPDexPage extends CommonMethod {

	private Properties selectedLanguageProperties;
	private ObjectReader hPDexPagePropertiesReader = new ObjectReader();
	private Properties hPDexPageProperties;
	private static Logger LOGGER = LogManager.getLogger(HPDexPage.class);

	private HPDexPage instance;
	public static String uiVersion = System.getProperty("uiVersion");

	public HPDexPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (HPDexPage.class) {
				if (instance == null) {
					instance = new HPDexPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public HPDexPage(WebDriver driver) throws IOException {

		hPDexPageProperties = hPDexPagePropertiesReader.getObjectRepository("HPDexPageV3");

	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = hPDexPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final String getTextOfHPDexPage(String key) throws Exception {
		return getTextBy(hPDexPageProperties.getProperty(key));
	}

	public final boolean verifyElementsOfHPDexPage(String key) throws Exception {
		return verifyElementIsPresent(hPDexPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfHPDexPage(String key) throws Exception {
		return verifyElementIsVisible(hPDexPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfHPDexPage(String key) throws Exception {
		click(hPDexPageProperties.getProperty(key));
	}

	public final void enterTextForHPDexPage(String key, String Text) throws Exception {
		enterText(hPDexPageProperties.getProperty(key), Text);
	}

	public final void switchToDifferentTabOfHPDexPage() {
		switchToDifferentTab();
	}

	public final void verifySubtabsinHpdexPage(String key) throws Exception {
		String locator = hPDexPageProperties.getProperty(key);

		String summaryTab = "Summary";

		{
			List<WebElement> sutabslist = getAllElements(locator);
			int counter = 0;
			if (sutabslist.size() > 0) {
				for (WebElement subtab : sutabslist) {
					counter++;
					if (subtab.getText().equalsIgnoreCase(summaryTab)) {
						subtab.click();
						LOGGER.info("Summary Tab is avialable for PIXM cutsomers");
						break;
					}
					if (counter == sutabslist.size()) {
						LOGGER.info("Summary Tab is not  avialable for PIXM cutsomers");
					}
				}
			}
		}
	}

}
