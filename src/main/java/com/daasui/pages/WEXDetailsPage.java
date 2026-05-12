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

public class WEXDetailsPage extends CommonMethod{
	
	private WEXDetailsPage instance;
	private ObjectReader WEXDetailsPagePropertiesReader = new ObjectReader();
	private Properties WEXDetailsPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEXDetailsPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	
	public WEXDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXDashboardPage.class) {
				if (instance == null) {
					instance = new WEXDetailsPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEXDetailsPage(WebDriver driver) throws IOException {
		WEXDetailsPageProperties = WEXDetailsPagePropertiesReader.getObjectRepository("WEXDetailsPage");

}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEXDetailsPage(String key) throws Exception {
		click(WEXDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEXDetailsPage(String key) {
		try {
			return verifyElementIsVisible(WEXDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEXDetailsPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEXDetailsPage(String key) {
		try {
			return verifyElementIsPresent(WEXDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEXDetailsPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEXDetailsPage(String key) throws Exception {
		return getTextBy(WEXDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForWEXDetailsPage(String key, String Text) {
		try {
			enterText(WEXDetailsPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX Details Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEXDetailsPage(String key) {
		try {
			scrollTillView(WEXDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEXDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEXDetailsPage(String key) {
		try {
			mouseHover(WEXDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEXDetailsPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEXDetailsPage(String key) throws Exception {
		clickByJavaScript(WEXDetailsPageProperties.getProperty(key));
	}
	
	


	/**
	 * This is a method to verify if the elements are present and match the count as well
	 * 
	 * @param key - Locator of element
	 * @param Count - number of elements
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyListofElementsWithCountOfWEXDetailsPage(String key, int count) {
		boolean flag= true;
		try
		{
			List<WebElement> we = getElementsTillAllElementsPresent(WEXDetailsPageProperties.getProperty(key));
			if(we.size()==count)
			{	
				for(WebElement w: we)
				{
					if(w.isDisplayed() == false) {				
						flag= false;
						break;
					}
					else {
						jsDriver().executeScript("arguments[0].style.border='3px solid red'", w);
						LOGGER.info("All Elelments are present");
					}
				}	
			}
			else {
				flag=false;
				LOGGER.info("Elements count not matching");
				
			}
		}
			catch (Exception e) {
				LOGGER.error(("Exception occured in method verifyListofElementsWithCountOfWEXDetailsPage " + e.getMessage()));
				//flag =false;
			}
			return flag;
		}
	
	/**
	 * This is a method to verify if the elements are present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyListofElementsOfWEXDetailsPage(String key) {
		boolean flag= true;
		try
		{
			List<WebElement> we = getElementsTillAllElementsPresent(WEXDetailsPageProperties.getProperty(key));
			for(WebElement w: we)
			{
				if(w.isDisplayed() == false) {				
					flag= false;
					break;
				}
				else {
					jsDriver().executeScript("arguments[0].style.border='3px solid red'", w);
					LOGGER.info("All Elelments are present");
				}
			}	
		}
		catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyListofElementsOfWEXDetailsPage " + e.getMessage()));
			//flag =false;
		}
		return flag;
	}
	

}



