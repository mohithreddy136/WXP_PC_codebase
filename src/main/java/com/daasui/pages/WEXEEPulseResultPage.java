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

public class WEXEEPulseResultPage extends CommonMethod {

	private WEXEEPulseResultPage instance;
	private ObjectReader pulseResultPagePropertiesReader = new ObjectReader();
	private Properties PulseResultPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEXEEPulseResultPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");


	public WEXEEPulseResultPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXDashboardPage.class) {
				if (instance == null) {
					instance = new WEXEEPulseResultPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public WEXEEPulseResultPage(WebDriver driver) throws IOException {
		PulseResultPageProperties = pulseResultPagePropertiesReader.getObjectRepository("WEXEEPulseResultPage");

	}
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEXEEPulseResultPage(String key) throws Exception {
		click(PulseResultPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEXEEPulseResultPage(String key) {
		try {
			return verifyElementIsVisible(PulseResultPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEXEEPulseResultPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEXEEPulseResultPage(String key) {
		try {
			return verifyElementIsPresent(PulseResultPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEXEEPulseResultPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEXEEPulseResultPage(String key) throws Exception {
		return getTextBy(PulseResultPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForWEXEEPulseResultPage(String key, String Text) {
		try {
			enterText(PulseResultPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXEEPulseResultPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX EE Pulses Result Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEXEEPulseResultPage(String key) {
		try {
			scrollTillView(PulseResultPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEXEEPulseResultPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEXEEPulseResultPage(String key) {
		try {
			mouseHover(PulseResultPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEXEEPulseResultPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get count of elements
	 *
	 * @param key - Locator of element
	 * @return 
	 * @throws Exception 
	 */
	public final int getCountOfRowsonWEXEEPulseResultPage(String key) throws Exception {
		return		
		getCountOfRows(PulseResultPageProperties.getProperty(key));
		
	}
	
	public final void clickByJavaScriptOnWEXEEPulseResultPage(String key) throws Exception {
		clickByJavaScript(PulseResultPageProperties.getProperty(key));
	}


	/**
	 * This is a method is used to compare the page title with pulse name on pulse result screen
	 * 
	 * @param title - Locator of element
	 * @param name - Locator of element
	 * @return - boolean value of whether the page tile matches with pulse name
	 */
	public boolean VerifyPageTitleWithPulseName(String title,String name) {
		try {
			String title1 = getTextOfWEXEEPulseResultPage(title);
			String name1 = getTextOfWEXEEPulseResultPage(name);
			if(title1.equals(name1))
			{
				return true;
			}
			else {
				return false;
			}

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method VerifyPageTitleWithPulseName " + e.getMessage()));
			return false;
		}
	}


	/**
	 * This is a method to verify if the elements are present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyListofElementsOfWEXEEPulseResultPage(String key) {
		boolean flag= true;
		try
		{
			List<WebElement> we = getElementsTillAllElementsPresent(PulseResultPageProperties.getProperty(key));
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
			LOGGER.error(("Exception occured in method verifyListofElementsOfWEXEEPulseResultPage " + e.getMessage()));
			//flag =false;
		}
		return flag;
	}
	
	/**
	 * This is a method to verify if the elements are present and match the count as well
	 * 
	 * @param key - Locator of element
	 * @param Count - number of elements
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyListofElementsWithCountOfWEXEEPulseResultPage(String key, int count) {
		boolean flag= true;
		try
		{
			List<WebElement> we = getElementsTillAllElementsPresent(PulseResultPageProperties.getProperty(key));
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
				LOGGER.error(("Exception occured in method verifyListofElementsWithCountOfWEXEEPulseResultPage " + e.getMessage()));
				//flag =false;
			}
			return flag;
		}


	/**
	 * This is a method to match text present on element
	 * 
	 * @param key - locator of element
	 * @param Text - text to be matched
	 * @return - boolean value of whether the text is matched or not
	 */
	public final boolean matchTextOfPulseResultPage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(PulseResultPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextOfPulseResultPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is used to Match/verify the list of texts 
	 * @param expectedText
	 * @return
	 * @throws Exception
	 */

	public final boolean matchTextsOfPulseResultPage(List<String> Text, String key) {
		boolean flag= true;
		try
		{

			List<String> we = getallTextBy(PulseResultPageProperties.getProperty(key));
			int counter=0;
			for(String w : we)
			{
				if(w.equalsIgnoreCase(Text.get(counter).trim())) {	
					flag = true;
					counter++;
				}
				else {
					flag = false;
					LOGGER.error(we + " Text did not match");  
					break;
				}

			}
		}
		catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextsOfPulseResultPage " + e.getMessage()));
			flag =false;
		}
		return flag;



	}



}

