package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.LogsVariables;

public class WEXLogPage extends CommonMethod {
	private ObjectReader WEXlogPagePropertiesReader = new ObjectReader();
	private Properties WEXlogPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private WEXLogPage instance;

	public WEXLogPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXLogPage.class) {
				if (instance == null) {
					instance = new WEXLogPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEXLogPage(WebDriver driver) throws IOException {
		WEXlogPageProperties = WEXlogPagePropertiesReader.getObjectRepository("WEXLogPage");
	}

	/**
	 * This is a method to verify if the element is present
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean verifyElementsOfWEXLogPage(String key) throws Exception {
		return verifyElementIsPresent(WEXlogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean waitForElementsOfWEXLogPage(String key) throws Exception {
		return verifyElementIsVisible(WEXlogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify if the element is enable
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifyElementIsEnableOfWEXLogPage(String key) throws Exception {
		return verifyElementIsEnable(WEXlogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get text of an element
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final String getTextOfWEXLogPage(String key) throws Exception {
		return getTextBy(WEXlogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to to click on an element
	 * @param key
	 * @throws Exception
	 */

	public final void clickOnElementsOfWEXLogPage(String key) throws Exception {
		click(WEXlogPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnWEXLogPage(String key) throws Exception {
		clickByJavaScript(WEXlogPageProperties.getProperty(key));
	}
	
	public final void clickByActionsClickWEXLogPage(String key) {
		try {
			actionClick(WEXlogPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByActionsClickWEXLogPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to enter text on an element
	 * @param key
	 * @throws Exception
	 */

	public final void enterTextForWEXLogPage(String key, String Text) throws Exception {
		enterTextwithoutclear(WEXlogPageProperties.getProperty(key), Text);
	}

	/**
	 * This is a method to verify element is clickable
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifyElementIsClickableOfWEXLogPage(String key) throws Exception {
		return verifyElementIsClickable(WEXlogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify search functionality of search filters present on Logs page
	 * @param textKey
	 * @param emptyTextKey
	 * @param text
	 * @param listKey
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifySearchValueOnLogs(String LanguageCode, String textKey, String text, String emptyTextKey, String listKey) throws Exception {
		return verifySearchFunctionalityofColumn(LanguageCode, WEXlogPageProperties.getProperty(textKey), text, WEXlogPageProperties.getProperty(emptyTextKey), WEXlogPageProperties.getProperty(listKey));
	}

	/**
	 * This is a method to verify the filter functionality when a single options are selected from a static list of options
	 * @param checkboxKey
	 * @return true
	 * @throws Exception
	 */
	public final boolean verifyFilterSingleSelectOnLogs(String LanguageCode, String checkboxKey, String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		return verifyFilterFunctionalityForSingleSelect(LanguageCode, WEXlogPageProperties.getProperty(checkboxKey), WEXlogPageProperties.getProperty(listOfElementKey), WEXlogPageProperties.getProperty(columnListKey), WEXlogPageProperties.getProperty(emptyTextKey));
	}

	/**
	 * This is a method to get a sequence of selected columns
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final ArrayList<String> getSequenceOfSelectedColumnsOnWEXLogPage(String key) throws Exception {
		return getTextOfList(WEXlogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to clear all filters on logs
	 * @param key
	 * @throws Exception
	 */
	public final void clearAllFiltersOfLogsPage(String key) throws Exception {
		clearAllFilters(WEXlogPageProperties.getProperty(key));

	}

	/**
	 * @param locatorkey - column locator
	 * @param oFlag -order flag either true or flase
	 * @throws Exception
	 */
	public final void clickSortColumnOfWEXLogPage(String locatorkey, boolean oFlag) throws Exception {
		clickSortIconColumn(WEXlogPageProperties.getProperty(locatorkey), oFlag);
	}

	/**
	 * This is a method to get all the elements
	 * 
	 * @param key locator of element
	 * @return List<WebElement>
	 * @throws Exception
	 */

	public final List<WebElement> getElementsOfWEXLogPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(WEXlogPageProperties.getProperty(key));
	}

	public final WebElement getElementOfWEXLogPage(String key) throws Exception {
		return getElement(WEXlogPageProperties.getProperty(key));
	}
	
	public final boolean ElementIsSelectedOfWEXLogPage(String key) throws Exception {
		return verifyElementIsSelected(WEXlogPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait until an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfWEXLogPage(String key) {
		try {
			verifyElementIsinvisibile(WEXlogPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfWEXLogPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnLogsPage(String key) {
		try {
			mouseHover(WEXlogPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnLogsPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to verify the sequence of column present inside a popup
	 * 
	 * @param key - locator of element
	 * @param columnName - the arraylist to be compared
	 * @return - the boolean value of whether the sequence matches or not
	 * @throws Exception
	 */
	public final boolean verifySequenceOfColumnInPopup(String key, ArrayList<String> columnName) throws Exception {
		ArrayList<String> columnNamesInPopUp = new ArrayList<>();
		List<WebElement> elements = getElementsTillAllElementsPresent(WEXlogPageProperties.getProperty(key));
		for (WebElement webElement : elements) {
			String columnNameFromPopup = webElement.getText();
			columnNameFromPopup = columnNameFromPopup.replaceAll("\n[ \t]+", "");
			columnNamesInPopUp.add(columnNameFromPopup);
		}
		if (columnName.equals(columnNamesInPopUp)) {
			return true;
		}
		return false;
	}

	public final void scrollOnWEXLogPage(String key) throws Exception {
		scrollTillView(WEXlogPageProperties.getProperty(key));
		
	}
	
	 public final boolean verifyDropdownOptionOrderOnLogPage(String key) throws Exception {
	        ArrayList<String> dropdownOptionList = new ArrayList<String>();
	        List<WebElement> elements = getElementsTillAllElementsPresent(WEXlogPageProperties.getProperty(key));
	        for (WebElement webElement : elements) {
	            dropdownOptionList.add(webElement.getText().trim().replaceAll("-|_", ""));
	        }
	        String previous = "";

	        for (final String current: dropdownOptionList) {
	            if (current.compareTo(previous) < 0)
	                return false;
	            previous = current;
	        }

	        return true;
	    }
	 
	 public final boolean verifyFilteredDataOnLogPage(String list,String filteredData) throws Exception {
	        List<String> uiList=getTextOfList(WEXlogPageProperties.getProperty(list));
	        for(String uis:uiList){
	           if(!filteredData.equalsIgnoreCase(uis)){
	               LOGGER.info("Fails to compare filtered data Actual="+uis+" Expected="+filteredData);
	              return false;
	           }
	        }	            
	        return true;
	  }

	  public final void clickDropdownOptionOnLogPage(String list,String option) throws Exception {
	        List<WebElement> elements = getElementsTillAllElementsPresent(WEXlogPageProperties.getProperty(list));
	        for (WebElement webElement : elements) {
	            if (option.equalsIgnoreCase(webElement.getText().toLowerCase().trim().replaceAll("-|_", ""))) {
	                    clickWebelement(webElement);
	                    break;        
	            }
	        }

	    }
	 	
}
