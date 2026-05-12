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

public class WEXEEDashboardPage extends CommonMethod{
	
	private WEXEEDashboardPage instance;
	private ObjectReader WEXEEDashboardPagePropertiesReader = new ObjectReader();
	private Properties WEXEEDashboardPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEXEEDashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	
	public WEXEEDashboardPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXDashboardPage.class) {
				if (instance == null) {
					instance = new WEXEEDashboardPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEXEEDashboardPage(WebDriver driver) throws IOException {
		WEXEEDashboardPageProperties = WEXEEDashboardPagePropertiesReader.getObjectRepository("WEXEEDashboardPage");

}
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEXEEDashboardPage(String key) throws Exception {
		click(WEXEEDashboardPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEXEEDashboardPage(String key) {
		try {
			return verifyElementIsVisible(WEXEEDashboardPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEXEEDashboardPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEXEEDashboardPage(String key) {
		try {
			return verifyElementIsPresent(WEXEEDashboardPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEXEEDashboardPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEXEEDashboardPage(String key) throws Exception {
		return getTextBy(WEXEEDashboardPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForWEXEEDashboardPage(String key, String Text) {
		try {
			enterText(WEXEEDashboardPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXEEDashboardPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX EE Dashboard Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEXEEDashboardPage(String key) {
		try {
			scrollTillView(WEXEEDashboardPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEXEEDashboardPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of element
	 * @param Text - Text to be matched
	 * @return - boolean value of whether the text present on element matches or not
	 */
	public final boolean matchTextOfWEXEEDashboardpage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(WEXEEDashboardPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextOfWEXEEDashboardPage" + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEXEEDashboardPage(String key) {
		try {
			mouseHover(WEXEEDashboardPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEXEEDashboardPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEXEEDashboardPage(String key) throws Exception {
		clickByJavaScript(WEXEEDashboardPageProperties.getProperty(key));
	}
	
	/**
	 * This is a method to verify if the elements are present and match the count as well
	 * 
	 * @param key - Locator of element
	 * @param Count - number of elements
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyListofElementsWithCountOfWEXEEDashboardPage(String key, int expectedCount) {
	    try {
	        // Retrieve the list of web elements using the provided key
	        List<WebElement> elements = getElementsTillAllElementsPresent(WEXEEDashboardPageProperties.getProperty(key));
	        
	        // If the element count doesn't match, log the mismatch and return false immediately
	        if (elements.size() != expectedCount) {
	            LOGGER.info("Element count mismatch. Expected: " + expectedCount + ", Found: " + elements.size());
	            return false;
	        }

	        // Iterate over the elements and check if each is displayed
	        for (WebElement element : elements) {
	            if (!element.isDisplayed()) {
	                LOGGER.warn("Element not displayed: " + element.toString());
	                return false; // Return false immediately on the first non-displayed element
	            }
	            // Highlight the displayed element with a red border for debugging
	            jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
	            LOGGER.info("Element is displayed: " + element.toString());
	        }

	        // If all elements are displayed, return true
	        return true;
	    } catch (Exception e) {
	        // Log any exceptions and return false in case of an error
	        LOGGER.error("Exception occurred in method verifyListofElementsWithCountOfWEXEEDashboardPage: " + e.getMessage(), e);
	        return false;
	    }
	}


	
	/**
	 * This is a method to verify if the elements are present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyListofElementsOfWEXEEDashboardPage(String key) {
		boolean flag= true;
		try
		{
			List<WebElement> we = getElementsTillAllElementsPresent(WEXEEDashboardPageProperties.getProperty(key));
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
			LOGGER.error(("Exception occured in method verifyListofElementsOfWEXEEDashboardPage " + e.getMessage()));
			//flag =false;
		}
		return flag;
	}

	

	/**
	 * This is a method to action click on an element
	 *
	 * @param key - Locator of element
	 */
	public final void actionClickOnWEXEEDashboardPage(String key) {
		try {
			actionClick(WEXEEDashboardPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method actionClickOnWEXEEDashboardPage " + e.getMessage()));
		}
	}
	

	/**
	 * This is a method to match the filter count on apply btn and filter icon on EE dashboard
	 *
	 * @param key - Locator of element
	 */
	public final boolean MatchFilterCountOnWEXEEDashboardPage() {
	    try {
	        // Click on the first date range option in the dashboard filter
	        actionClickOnWEXEEDashboardPage("DateRangeFirstOption");
	        String str = getTextOfWEXEEDashboardPage("ApplyBtnText").trim();
	        int i = extractNumberFromText(str);       
	        actionClickOnWEXEEDashboardPage("ApplyBtn");	        
	        String filterCountText = getTextOfWEXEEDashboardPage("filterCount").trim();
	        int j = extractNumberFromText(filterCountText);

	        // Return whether the numbers match
	        return i == j;

	    } catch (Exception e) {
	        LOGGER.error("Exception occurred in method MatchFilterCountOnWEXEEDashboardPage: " + e.getMessage(), e);
	        return false; // Return false in case of any exception
	    }
	}

	/**
	 * Extracts the first number from a string. If no valid number is found, returns 0.
	 */
	private int extractNumberFromText(String text) {
	    try {
	        
	        String numberStr = text.replaceAll("\\D", ""); 
	        return numberStr.isEmpty() ? 0 : Integer.parseInt(numberStr); 
	    } catch (NumberFormatException e) {
	        LOGGER.error("Error extracting number from text: " + text, e);
	        return 0; 
	    }
	}


}


