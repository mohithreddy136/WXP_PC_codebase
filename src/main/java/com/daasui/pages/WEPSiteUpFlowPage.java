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

public class WEPSiteUpFlowPage extends CommonMethod {
	
	private WEPSiteUpFlowPage instance;
	private ObjectReader WEPSiteUpFlowPagePropertiesReader = new ObjectReader();
	private Properties WEPSiteUpFlowPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	
	public WEPSiteUpFlowPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPSiteUpFlowPage.class) {
				if (instance == null) {
					instance = new WEPSiteUpFlowPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEPSiteUpFlowPage(WebDriver driver) throws IOException {
		WEPSiteUpFlowPageProperties = WEPSiteUpFlowPagePropertiesReader.getObjectRepository("WEPSiteUpFlowPage");
	}
	public final boolean verifyElementsOfWEPSiteUpFlowPage(String key) throws Exception {
		return verifyElementIsPresent(WEPSiteUpFlowPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfWEPSiteUpFlowPage(String key) throws Exception {
		click(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	public final void mouseHoverOfWEPSiteUpFlowPage(String key) throws Exception {
		mouseHover(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	public final void actionClickOfWEPSiteUpFlowPage(String key) throws Exception {
		actionClick(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	public final List<WebElement> getElementsOfWEPSiteUpFlowPage(String key) throws Exception {
		return getAllElements(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	
	public final void clickByJavaScriptOnWEPSiteUpFlowPage(String key) throws Exception {
		clickByJavaScript(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	public final void listMouseHoverOfWEPSiteUpFlowPage(String key) throws Exception {
		listMouseHover(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	public final WebElement getElementOfWEPSiteUpFlowPage(String key) throws Exception {
		return getElement(WEPSiteUpFlowPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofWEPSiteUpFlowPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEPSiteUpFlowPageProperties.getProperty(key));
	}

	public final void switchToDifferentTabOfWEPSiteUpFlowPage() {
		switchToDifferentTab();
	}

	public final void switchToPreviousTabOfWEPSiteUpFlowPage() {
		switchBackToPreviousTab();
	}

	public final ArrayList<String> getChartLabelsWEPSiteUpFlowPage(String key) throws Exception {
		return getLabelsOfChart(WEPSiteUpFlowPageProperties.getProperty(key));
	}

	public void scrollToWEPSiteUpFlowPage(String key) throws Exception {
		scrollTillView(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	
	/**
	 * This method designed to get the  Text of WebElement from web page
	 * @param key
	 */
	public final String getTextOfWexWEPSiteUpFlowPage(String key) throws Exception {
		return getTextBy(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfWEPSiteUpFlowPage(String key) throws Exception {
		return verifyElementIsVisible(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOfWEPSiteUpFlowPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEPSiteUpFlowPageProperties.getProperty(key), Text);
	}
	public final boolean waitForElementsOfWEPSiteUpFlowPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WEPSiteUpFlowPageProperties.getProperty(key), waitTime);
	}
	
	public final boolean waitUntilElementIsInvisibleOfWEPSiteUpFlowPage(String key){
		return verifyElementIsinvisibile(WEPSiteUpFlowPageProperties.getProperty(key));
	}
	
	public final boolean waitUntilAllElementIsInvisibleOfWEPSiteUpFlowPage(List<WebElement> key){
		return verifyAllElementIsinvisibile(key);
	}

	public final void enterTextForWEPSiteUpFlowPage(String key, String Text) {
		try {
			enterText(WEPSiteUpFlowPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForDashboardPage " + e.getMessage()));
		}
	}

}
