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

public class WEPDeviceLevelAlertsPage  extends CommonMethod {

	private WEPDeviceLevelAlertsPage instance;
	private ObjectReader WEPDeviceLevelAlertsPagePropertiesReader = new ObjectReader();
	private Properties WEPDeviceLevelAlertsPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEPAlertManagementPage.class);
	public static String uiVersion = System.getProperty("uiVersion");

	public WEPDeviceLevelAlertsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPDeviceLevelAlertsPage.class) {
				if (instance == null) {
					instance = new WEPDeviceLevelAlertsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEPDeviceLevelAlertsPage(WebDriver driver) throws IOException {
		WEPDeviceLevelAlertsPageProperties = WEPDeviceLevelAlertsPagePropertiesReader
				.getObjectRepository("WEPDeviceLevelAlertsPage");
	}

	public final boolean verifyElementsOfDeviceAlertPage(String key) throws Exception {
		return verifyElementIsPresent(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public final void actionClickOfDeviceAlertPage(String key) throws Exception {
		actionClick(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}


	public final boolean verifyElementIsClickableOfDeviceAlertPage(String key) throws Exception {
		return verifyElementIsClickable(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public final void scrollOnDeviceAlertPage(String key) throws Exception {
		scrollTillView(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public final String getTextOfDeviceAlertPage(String key) throws Exception {
		return getTextBy(WEPDeviceLevelAlertsPageProperties.getProperty(key));

	}

	public final void clickOnElementsOfDeviceAlertPage(String key) throws Exception {
		click(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfDeviceAlertPage(String key) throws Exception {
		return verifyElementIsVisible(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public void switchToIframeofOfDeviceAlertPage(String key) throws Exception {
		switchToIframe(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public void switchToDefaultContentofDeviceAlertPage() throws Exception {
		switchToDefaultContent();
	}

	public final void clickByJavaScriptOnDeviceAlertPage(String key) throws Exception {
		clickByJavaScript(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public final List<WebElement> getllAllElementsVisibleofDeviceAlertPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public final void mouseHoverOnDeviceAlertPage(String key) throws Exception {
		mouseHover(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public final List<String> getllAllElementsTextVisibleofDeviceAlertPage(String key) throws Exception {
		return getUniqueElementsofStringsFromList(WEPDeviceLevelAlertsPageProperties.getProperty(key));
	}

	public final void enterTextOfDeviceAlertPage(String key, String Text) {
		try {
			enterText(WEPDeviceLevelAlertsPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXCustomerUsersDetailsPage " + e.getMessage()));
		}
	}

	public final boolean verifyFilteredDataOnDeviceAlertPage(String list, String filteredData) throws Exception {
		List<String> uiList = getTextOfList(WEPDeviceLevelAlertsPageProperties.getProperty(list));
		for (String uis : uiList) {
			if (!filteredData.equalsIgnoreCase(uis)) {
				LOGGER.info("Fails to compare filtered data Actual=" + uis + " Expected=" + filteredData);
				return false;
			}
		}
		return true;
	}


	public final boolean verifyDropdownOptionOrderOnDeviceAlertPage(String key) throws Exception {
		ArrayList<String> dropdownOptionList = new ArrayList<String>();
		List<WebElement> elements = getElementsTillAllElementsPresent(
				WEPDeviceLevelAlertsPageProperties.getProperty(key));
		for (WebElement webElement : elements) {
			dropdownOptionList.add(webElement.getText().trim().replaceAll("-|_", ""));
		}
		String previous = "";

		for (final String current : dropdownOptionList) {
			if (current.compareTo(previous) < 0)
				return false;
			previous = current;
		}

		return true;
	}
}



