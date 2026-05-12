package com.daasui.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WEXSettingPage extends CommonMethod  {
 
	private WEXSettingPage instance;
	private ObjectReader WEXSettingPropertiesReader = new ObjectReader();
	private Properties WEXSettingProperties;
	
	
	public WEXSettingPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SettingsPage.class) {
				if (instance == null) {
					instance = new WEXSettingPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public final String getTextOfWEXHelpAndSupportPage(String key) {
		try {
			return getTextBy(WEXSettingProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
			return null;
		}
	}
	
	public WEXSettingPage(WebDriver driver) throws IOException {
		WEXSettingProperties = WEXSettingPropertiesReader.getObjectRepository("WEXSettingPage");
	}
	
	public final boolean matchTextOfWEXSettingPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXSettingProperties.getProperty(key), Text);
	}
	
	public final boolean verifyElementsOfWEXSettingPage(String key) throws Exception {
		return verifyElementIsPresent(WEXSettingProperties.getProperty(key));
	}

	/**
	 * This is a method to click on element present on settings page
	 *
	 * @param key - locator of the element
	 */
	public final boolean clickOnElementsOfWEXSettingPage(String key) throws Exception {
		try {
			click(WEXSettingProperties.getProperty(key));
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementsOfWEXSettingPage " + e.getMessage()));
			return false;
		}
	}

	public final void clickOnWebElementsOfWEXSettingPage(WebElement key) throws Exception {
		clickWebelement(key);
	}
	
	public final void mouseHoverclickOfWEXSettingPage(WebElement key) throws Exception {
		mouseHoverclick(key);
	}
	
	public final WebElement getElementOfWEXSettingPage(String key) throws Exception {
		return getElement(WEXSettingProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfWEXSettingPage(String key) throws Exception {
		return verifyElementIsVisible(WEXSettingProperties.getProperty(key));
	}
	
	public final void scrollTillViewWEXSettingPage(String locator) throws Exception {
		scrollTillView(WEXSettingProperties.getProperty(locator));
	}
	
	public final boolean selectTextValueFromDropdownOfWEXSettingPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(WEXSettingProperties.getProperty(dropdownListKey), elementText, WEXSettingProperties.getProperty(dropdownBox));
	}
	
	public final void SelectTextPresentInDropdownOfWEXSettingPage(String locator, String text) throws Exception{
	   SelectTextPresentInDropdown(WEXSettingProperties.getProperty(locator), text);
	}
	
	public final void enterTextForWEXSettingPage(String key, String Text) throws Exception {
		enterText(WEXSettingProperties.getProperty(key), Text);
	}

	/**
	 * THis is a method to get the text on WEX Setting page
	 *
	 * @param key - locator of the element
	 */
	public final String getTextByWEXSettingPage(String key) {
		try {
			return getTextBy(WEXSettingProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextByWEXSettingPage " + e.getMessage()));
			return null;
		}

	}
	
	
	/**
	 * THis is a method to check if checkbox is selected or not.
	 *
	 * @param key - locator of the element
	 */
	public final boolean verifyElementIsSelectedWEXSettingPage(String key) {
		try {
			return verifyElementIsSelected(WEXSettingProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextByWEXSettingPage " + e.getMessage()));
			return false;
		}

	}
	
	

	/**
	 * THis is a method is to verify element is clickable or not on Setting page
	 * using action class
	 * 
	 * @param key - locator of the element
	 * @return
	 * @throws Exception
	 */
	public final void actionclickOnElementsOfWEXSettingPage(String key) throws Exception {
		actionClick(WEXSettingProperties.getProperty(key));
	}
	
	/**
	 * Enable Status of HardwareAlert-Battery Attention
	 * 
	 * @throws Exception
	 */
	public boolean modifyHardwareBatteryAttentionSubtypeStatus(String key, String enableToggleButton, String saveBtn)
			throws Exception {
		try {
			LOGGER.info("Scrolling to view the element: " + key);
			scrollTillViewWEXSettingPage(key);
			LOGGER.info("waiting for the element: " + key);
			waitForElementsOfWEXSettingPage(key);
			sleeper(2000);
			LOGGER.info("Clicking on the element: " + key);
			actionclickOnElementsOfWEXSettingPage(key);
			LOGGER.info("Waiting for the element to be visible: " + enableToggleButton);
			waitForElementsOfWEXSettingPage(enableToggleButton);
			LOGGER.info("Click on the element: " + enableToggleButton);
			actionclickOnElementsOfWEXSettingPage(enableToggleButton);
			LOGGER.info("Click on the save button: " + enableToggleButton);
			actionclickOnElementsOfWEXSettingPage(saveBtn);
			waitForPageLoaded();
			// If all actions are completed without exception, return true
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception occurred: " + e.getMessage());
			e.printStackTrace();
			return false; // Return false if any exception occurs
		}
	}

	/**
	 * THis is a method is to getallText in a list or not on Setting page
	 * 
	 * @param key - locator of the element
	 * @return
	 * @throws Exception
	 */
	public final List<String> verifygetallTextByForWEXSettingPage(String locator) throws Exception {
		return getallTextBy(WEXSettingProperties.getProperty(locator));
	}
	
	/**
	 * THis is a method to get the Alerts and status of Self Service Alerts for
	 * Hardware Health and Active care
	 *
	 * @param key    - locator of the element
	 * @param string
	 * @return
	 * @return
	 * @throws Exception
	 */

	public boolean checkStatusOfAlerts(String enabledText, String disabledText, List<String> list, String locator2,
			boolean checkEnabled) throws Exception {
		List<WebElement> menuList = getElementsTillAllElementsPresent(WEXSettingProperties.getProperty(locator2));
		boolean flag = true; // Initialize to true

		if (list.size() == menuList.size()) {
			for (int i = 0; i < menuList.size(); i++) {
				// Get the actual status text, trim whitespace, and normalize to lowercase
				String actualText = menuList.get(i)
						.findElement(
								getObject(WEXSettingProperties.getProperty("selfServiceAlertSubtypeSpecificStatus")))
						.getText().trim().toLowerCase();

				// Log the expected and actual status for debugging
				LOGGER.info(
						"Expected Status: " + (checkEnabled ? enabledText.toLowerCase() : disabledText.toLowerCase()));
				LOGGER.info("Actual Status: " + actualText);

				// Check the status based on the checkEnabled flag
				if (checkEnabled) {
					// Check for enabled status
					if (!actualText.equals(enabledText.toLowerCase())) {
						LOGGER.info("Status mismatch at index " + i + ": Expected 'Enabled' but found " + actualText);
						flag = false; // Set flag to false if mismatch
						break; // Exit the loop early since the condition is not met
					}
				} else {
					// Check for disabled status
					if (actualText.equals(disabledText.toLowerCase())) {
						LOGGER.info("Status mismatch at index {}: Expected not 'Disabled' but found {}", i, actualText);
						flag = false; // Set flag to false if mismatch
						break; // Exit the loop early since the condition is not met
					}
				}
			}
		} else {
			flag = false; // If the list sizes are different, set flag to false
		}

		return flag; // Return flag indicating whether all statuses matched the expected values
	}
	
	/* Method is checking list of the items listed under Notification tab -- hardware health 
	 * and Active care if enabled or disabled
	 * 
	 * @param  list --> list of all elements under hardware health and Active care
	 */
	public boolean verifyListOfSubCategoriesUnderSelfServiceAlerts(List<String> list, String locator)
	        throws Exception {
		List<String> menuList = getallTextBy(WEXSettingProperties.getProperty(locator));
		boolean flag = true; // Initialize to true
		
		// Check if both lists have the same size
		if (list.size() == menuList.size()) {
		    for (int i = 0; i < list.size(); i++) {
		        // Trim whitespace and convert both to uppercase to ensure case-insensitive matching
		        String listItem = list.get(i).toUpperCase().trim();

		        // Log the values being compared for debugging
		        LOGGER.info("Comparing: " + listItem + " with menu list content: " + menuList);

		        // Check if listItem exists anywhere in menuList
		        if (!menuList.contains(listItem)) {
		            LOGGER.info(listItem + " is not present in Self Service Alert list under Hardware Health sub-categories.");
		            flag = false;
		            break;
		        }
		    }
		} else {
		    LOGGER.warn("Lists size mismatch: list size = " + list.size() + ", menuList size = " + menuList.size());
		}

		return flag;
	}
}


 