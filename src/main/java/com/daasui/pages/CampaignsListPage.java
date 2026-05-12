package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class CampaignsListPage extends CommonMethod {

	private ObjectReader CampaignsListPagePropertiesReader = new ObjectReader();
	private Properties CampaignsListPageProperties;
	private CampaignsListPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public CampaignsListPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (CampaignsListPage.class) {
				if (instance == null) {
					instance = new CampaignsListPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public CampaignsListPage(WebDriver driver) throws IOException {
		CampaignsListPageProperties = CampaignsListPagePropertiesReader.getObjectRepository("CampaignsListPageV3");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfCampaignsListPage(String key) throws Exception {
		click(CampaignsListPageProperties.getProperty(key));
	}

	/**
	 * This method is used for clearing any filters applied on Campaigns list page
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFiltersOfCampaigsListPage(String clearFilterKey) throws Exception {
		clearFilters(CampaignsListPageProperties.getProperty(clearFilterKey));
	}
	

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfCampaignsListPage(String key) {
		try {
			return verifyElementIsVisible(CampaignsListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfCampaignsListPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfCampaignsListPage(String key) {
		try {
			return verifyElementIsPresent(CampaignsListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfCampaignsListPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfCampaignsListPage(String key) throws Exception {
		return getTextBy(CampaignsListPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForCampaignsListPage(String key, String Text) {
		try {
			enterText(CampaignsListPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForCampaignsListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on campaign list page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnCampaignListPage(String key) {
		try {
			scrollTillView(CampaignsListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnCampaignListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnCampaignListPage(String key) {
		try {
			mouseHover(CampaignsListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnCampaignListPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnCampaignListPage(String key) throws Exception {
		clickByJavaScript(CampaignsListPageProperties.getProperty(key));
	}

}
