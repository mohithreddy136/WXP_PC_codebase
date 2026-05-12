package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class TableConfigurationPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader tableConfigurationPagePropertiesReader = new ObjectReader();
	private Properties tableConfigurationPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private TableConfigurationPage instance;

	public TableConfigurationPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (TableConfigurationPage.class) {
				if (instance == null) {
					instance = new TableConfigurationPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public TableConfigurationPage(WebDriver driver) throws IOException {
		tableConfigurationPageProperties = tableConfigurationPagePropertiesReader.getObjectRepository("TableConfigurationPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = tableConfigurationPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This is a method to verify if element is enable or not
	 * 
	 * @param key - locator of element
	 * @return - the boolean value of whether the element is clickable or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsEnableOftableConfigurationPage(String key) throws Exception {
		return verifyElementIsClickable(tableConfigurationPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify and wait Until element is present
	 * 
	 * @param key - locator of element
	 * @return - the boolean value of whether the element is clickable or not
	 * @throws Exception
	 */
	public final boolean waitUntillElementIsPresentOftableConfigurationPage(String key) throws Exception {
		return waitUntillElementIsPresent(tableConfigurationPageProperties.getProperty(key));
	}

	public final boolean waitUntillElementIsPresentOftableConfigurationPageDynamic(String key,int waitTime) throws Exception {
		return waitUntillElementIsPresentDynamic(tableConfigurationPageProperties.getProperty(key),waitTime);
	}
	
	/**
	 * This is a method to click on an element
	 * 
	 * @param key - locator of element
	 * @throws Exception
	 */
	public final void clickOnElementsOfTableConfigurationPage(String key) throws Exception {
		click(tableConfigurationPageProperties.getProperty(key));
	}
	
	/**
	 * This is a method to click on an element
	 * 
	 * @param key - locator of element
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnElementsOfTableConfigurationPage(String key) {
		try {
			clickByJavaScript(tableConfigurationPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnElementsOfTableConfigurationPage " + e.getMessage()));
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
		List<WebElement> elements = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key));

		for (WebElement webElement : elements) {
			String columnNameFromPopup = webElement.getText().toLowerCase();
			columnNameFromPopup = columnNameFromPopup.replaceAll("\n[ \t]+", "");
			columnNamesInPopUp.add(columnNameFromPopup);
		}
		if (columnName.equals(columnNamesInPopUp)) {
			return true;
		}
		return false;
	}

	/**
	 * This is a method to verify the default value of all the checboxes present on an popup
	 * 
	 * @param key - locator of element
	 * @param checkboxValue - arraylist of the values to be compared
	 * @return - the boolean value of whether the default value is correct
	 * @throws Exception
	 */
	public final boolean verifyDefaultValueOfCheckboxOfPopup(String key, ArrayList<String> checkboxValue) throws Exception {
		ArrayList<String> checkboxOfPopup = new ArrayList<>();
		List<WebElement> elements = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key));

		for (WebElement webElement : elements) {
			if (webElement.isSelected()) {
				checkboxOfPopup.add("true");
			} else {
				checkboxOfPopup.add("false");
			}
		}

		if (checkboxValue.equals(checkboxOfPopup)) {
			return true;
		}
		return false;
	}

	/**
	 * This is a method to get a list of selected columns
	 * 
	 * @param key1 - locator of the list of checkboxes
	 * @param key2 - locator of the list of values against the checkboxes
	 * @return - arraylist of the values against the checkboxes
	 * @throws Exception
	 */
	public final ArrayList<String> getSequenceOfSelectedColumns(String key1, String key2) throws Exception {
		ArrayList<String> columnNameOnPopup = new ArrayList<>();
		List<WebElement> element1 = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key1));
		List<WebElement> element2 = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key2));

		for (int i = 0; i < element1.size(); i++) {
			String columnName = element2.get(i).getText().toLowerCase();
			columnName = columnName.replaceAll("\n[ \t]+", "");
			columnNameOnPopup.add(columnName);
		}
		return columnNameOnPopup;
	}

	/**
	 * This is a method to select all checkboxes of a popup
	 * 
	 * @param key1 - locator of the list of checkboxes
	 * @param key2 - locator of the list
	 * @throws Exception
	 */
	public final void selectAllCheckboxOfPopup(String key1, String key2) throws Exception {
		List<WebElement> element1 = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key1));
		List<WebElement> element2 = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key2));
		for (int i = 0; i < element1.size(); i++) {
			if (!element1.get(i).isSelected()) {
				sleeper(500);
				element2.get(i).click();
			}
		}
	}

	/**
	 * This is a method to deselect all checboxes of a popup
	 * 
	 * @param key1 - locator of the list of checkboxes
	 * @param key2 - locator of the list
	 * @throws Exception
	 */
	public final void deselectAllCheckboxOfPopup(String key1, String key2) throws Exception {
		List<WebElement> element1 = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key1));
		List<WebElement> element2 = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key2));
		for (int i = 0; i < element1.size(); i++) {
			if (element1.get(i).isSelected()) {
				sleeper(500);
				element2.get(i).click();
			}
		}
	}

	/**
	 * This is a method to verify Advance Filter Functionality When On
	 * 
	 * @param list1Key - locator of list of elements
	 * @param buttonKey - locator of the toggle
	 * @param list2Key - locator of list of elements
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifyAdvanceFilterFunctionalityWhenOn(String list1Key, String buttonKey, String list2Key) throws Exception {
		List<WebElement> element1 = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(list1Key));
		clickOnElementsOfTableConfigurationPage(buttonKey);
		sleeper(1000);
		List<WebElement> element2 = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(list2Key));

		if (element1.size() == element2.size()) {
			return true;
		}
		return false;
	}

	/**
	 * This is a method to get count of all the selected checkboxes on popup
	 * 
	 * @param key - locator of checkboxes
	 * @return - integer value of te count of selected checkboxes
	 * @throws Exception
	 */
	public final int getCountOfSelectedCheckBoxOnPopup(String key) throws Exception {
		List<WebElement> element = getElementsTillAllElementsPresent(tableConfigurationPageProperties.getProperty(key));
		return element.size();
	}

	/**
	 * This is a method to verify if the elemeent is present
	 * 
	 * @param key - locator of element
	 * @return - - the boolean value of whether the element is present or not
	 * @throws Exception
	 */
	public final boolean verifyElementsOfTableConfigurationPage(String key) throws Exception {
		return verifyElementIsPresent(tableConfigurationPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get attribute of an element
	 * 
	 * @param key - locator of element
	 * @param value - the value of attribute
	 * @return - String value of the attribute of the element
	 * @throws Exception
	 */
	public final String getAttributesOfTableConfiguration(String key, String value) throws Exception {
		return getAttribute(tableConfigurationPageProperties.getProperty(key), value);
	}

	/**
	 * This is a method to wait for an element to be visible
	 * 
	 * @param key - locator of element
	 * @return - the boolean value of whether the element is visible or not
	 * @throws Exception
	 */
	public final boolean waitForElementsOfTableConfigurationPage(String key) throws Exception {
		return verifyElementIsVisible(tableConfigurationPageProperties.getProperty(key));
	}

	/**
	 * This is a method to scroll on an element untill visible
	 * 
	 * @param key - locator of element
	 * @throws Exception
	 */
	public final void scrollOnTableConfigurationPage(String key) throws Exception {
		scrollTillView(tableConfigurationPageProperties.getProperty(key));
	}
	
	public final void mouseHoverOnTableConfigurationPage(String key) throws Exception {
		scrollTillView(tableConfigurationPageProperties.getProperty(key));
	}
	
	/**
	 * This is a method to wait until an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfTableConfigurationPage(String key) {
		try {
			verifyElementIsinvisibile(tableConfigurationPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfTableConfigurationPage " + e.getMessage()));
		}
	}
	
	
	/**
	 * This is a method to wait for an element to be invisible
	 * 
	 * @param key - locator of element
	 * @throws Exception
	 */
	public final void waitForInvisibilityOfElementOfTableConfigurationPage(String key) throws Exception {
		waitUntilElementIsVisible(tableConfigurationPageProperties.getProperty(key));
	}

    /**
     * This method marks the selected checkboxes in the respective column displayed on the device list page
     *
     * @throws Exception
     */
    public void markSelectedCheckboxOfPopupForDeviceListPage(List<String> columnNames) throws Exception {
        refreshPage();
        boolean opened = false;
        for (int attempt = 1; attempt <= 2; attempt++) {
            if (verifyElementsOfTableConfigurationPage("tableConfigurationButton")) {
                try {
                    clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
                    LOGGER.info("Opened Table Configuration popup (attempt " + attempt + ")");
                    opened = true;
                    break;
                } catch (Exception e) {
                    LOGGER.warn("Attempt " + attempt + " to open Table Configuration popup failed: " + e.getMessage());
                    sleeper(500);
                }
            }
        }
        if (!opened) {
            LOGGER.error("Failed to open Table Configuration popup after retries");
            return;
        }

        try {
            waitUntillElementIsPresentOftableConfigurationPageDynamic("tableConfigurationTitle", 10);
        } catch (Exception e) {
            LOGGER.warn("Table configuration title not visible within timeout: " + e.getMessage());
        }


        if (verifyElementsOfTableConfigurationPage("resettodefault")) {
            try {
                clickOnElementsOfTableConfigurationPage("resettodefault");
                LOGGER.info("Clicked reset to default");
                sleeper(400);
            } catch (Exception e) {
                LOGGER.warn("Reset to default click failed: " + e.getMessage());
            }
        } else {
            LOGGER.info("Reset to default click button not present - skipping");
        }

        try {
            for (String columnName : columnNames) {
                try {
                    String xpath = String.format(tableConfigurationPageProperties.getProperty("selectColumnToDisplay"), columnName);
                    if(verifyElementIsPresent(xpath)) {
                        clickByJavaScript(xpath);
                    }
                    LOGGER.info("Clicked column option: {}", columnName);
                } catch (Exception e) {
                    LOGGER.error("Failed to click column option '{}': {}", columnName, e.getMessage());
                }
            }

        } catch (Exception e) {
            LOGGER.error("Failed selecting column checkboxes: " + e.getMessage());
            return;
        }

        if (verifyElementsOfTableConfigurationPage("saveButton")) {
            try {
                clickOnElementsOfTableConfigurationPage("saveButton");
                LOGGER.info("Saved table configuration");
            } catch (Exception e) {
                LOGGER.error("Failed clicking Save button: " + e.getMessage());
            }
        } else {
            LOGGER.error("Save button not present - configuration not saved");
        }
    }

    /**
     * Resets the table configuration to default settings for device list page.
     * Opens the table configuration, clicks reset, and saves changes.
     *
     * @throws Exception if any UI interaction fails
     */
    public void resetTableConfig() throws Exception {
        sleeper(4000);
        if (verifyElementsOfTableConfigurationPage("tableConfigurationButton")) {
            clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
            if (verifyElementIsEnableOftableConfigurationPage("resettodefault")) {
                clickOnElementsOfTableConfigurationPage("resettodefault");
                clickOnElementsOfTableConfigurationPage("saveButton");
                sleeper(4000);
            }
        }
    }
}
