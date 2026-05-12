package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class UserDetailsPage extends CommonMethod {

	private ObjectReader userDetailsPagePropertiesReader = new ObjectReader();
	private Properties userDetailsPageProperties;
	private UserDetailsPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public UserDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (UserDetailsPage.class) {
				if (instance == null) {
					instance = new UserDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public UserDetailsPage(WebDriver driver) throws IOException {
		userDetailsPageProperties = userDetailsPagePropertiesReader.getObjectRepository("UserDetailsPageV3");
	}

	/**
	 * This is the method to verify if an element is present on user details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is present or not
	 * @throws Exception
	 */
	public final boolean verifyElementsOfUserDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to wait for an element list page until it is visible on user details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible or not
	 * @throws Exception
	 */
	public final boolean waitForElementsOfUserDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to match text present on an element with given text on user details page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be matched
	 * @return - boolean value of whether both the text match
	 * @throws Exception
	 */
	public final boolean matchTextOfUserDetailsPage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(userDetailsPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is the method to verify if any element is enabled on user details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsEnableOfUserDetailsPage(String key) throws Exception {
		return verifyElementIsEnable(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to verify if any element is selected on user details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is selected or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsSelectedOfUserDetailsPage(String key) throws Exception {
		return verifyElementIsSelected(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get text of an element on user details page
	 * 
	 * @param key - locator of the element
	 * @return - string value of text on the element
	 * @throws Exception
	 */
	public final String getTextOfUserDetailsPage(String key) throws Exception {
		return getTextBy(userDetailsPageProperties.getProperty(key));
	}

	public final String getTextOfUserDetailsPageDynamic(String key,int waitTime) throws Exception {
		return getTextByDynamic(userDetailsPageProperties.getProperty(key),waitTime);
	}

	/**
	 * This is a method to get attributes of an element on user details page
	 * 
	 * @param key - locator of the element
	 * @param desiredValue - desired attribute name
	 * @return - string value of the attribute
	 * @throws Exception
	 */
	public final String getAttributesOfUserDetailsPage(String key, String desiredValue) throws Exception {
		return getAttribute(userDetailsPageProperties.getProperty(key), desiredValue);
	}

	/**
	 * This is a method to click on element of user details page
	 * 
	 * @param key - locator of the element
	 * @throws Exception
	 */
	public final void clickOnElementsOfUserDetailsPage(String key) throws Exception {
		click(userDetailsPageProperties.getProperty(key));
	}

	public final void mouseHoverOnUserDetailsPage(String key) throws Exception {
		mouseHover(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text in a text box present on user details page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be entered
	 * @throws Exception
	 */
	public final void enterTextForUserDetailsPage(String key, String textToMatch) throws Exception {
		enterText(userDetailsPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is the method to verify if an element is clickable on user details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is clickable or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsClickableOfUserDetailsPage(String key) throws Exception {
		return verifyElementIsClickable(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to get a list of elements present on user details page
	 * 
	 * @param key - locator of the element
	 * @return - list of web elements
	 * @throws Exception
	 */
	public final List<WebElement> getElementsOfUserDetailsPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to clear text from an element present on user details page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clearTextFromUserDetailsPageTextField(String key) throws Exception {
		clearText(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to select role on role assignment dropdown on user details page
	 * 
	 * @param dropdownId - Dropdown on role assignment popup
	 * @param key - - Locator of elements
	 * @param text - Already assigned role
	 * @return - updated role as a string
	 * @throws Exception
	 */
	public final String selectRoleFromDropDownOfUserDetailsPage(String dropdownId, String key, String text) throws Exception {
		click(userDetailsPageProperties.getProperty(dropdownId));
		List<WebElement> roleList = getElementsOfUserDetailsPage(key);
		String firstRole, secondRole;
		if (roleList.get(0).getText().equals(text)) {
			firstRole = roleList.get(1).getText();
			roleList.get(1).click();
			return firstRole;
		} else {
			secondRole = roleList.get(0).getText();
			roleList.get(0).click();
			return secondRole;
		}

	}

	/**
	 * This is a method to get an element present on user details page
	 * 
	 * @param key - locator of the element
	 * @return - webelement
	 * @throws Exception
	 */
	public final WebElement getElementOfUserDetailsPage(String key) throws Exception {
		return getElement(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to scroll on user details page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void scrollOnUserPage(String key) throws Exception {
		scrollTillView(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to compare two arraylists
	 * 
	 * @param key - locator of element to fetch first list
	 * @param subTypes - second list
	 * @return - boolean value of whether both the lists match
	 * @throws Exception
	 */
	public final boolean compareTwoListOfUserDetailsPage(String key, ArrayList<String> subTypes) throws Exception {
		return compareTwoList(userDetailsPageProperties.getProperty(key), subTypes);
	}

	/**
	 * This is a method to get text present on a list of elements as a list of strings
	 * 
	 * @param key - locator of element to fetch list
	 * @return - arraylist of strings
	 * @throws Exception
	 */
	public final List<String> getTextOfListOnUserDetailsPage(String key) throws Exception {
		return getTextOfPresentElementList(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to click on an element by javascript
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnUserDetailsPage(String key) throws Exception {
		clickByJavaScript(userDetailsPageProperties.getProperty(key));
	}

	public final void actionClickOnUserDetailsPage(String key) throws Exception {
		actionClick(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify and wait Until element is present
	 * 
	 * @param key - locator of element
	 * @return - the boolean value of whether the element is clickable or not
	 * @throws Exception
	 */
	public final boolean waitUntillElementIsPresentOfUserDetailsPage(String key) throws Exception {
		return waitUntillElementIsPresent(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to select all checkboxes of a popup
	 * 
	 */
	public final void roleCheckboxSelectionOfUserDetailsPage() {
		try {
			selectAllCheckboxOfPopup(userDetailsPageProperties.getProperty("columnCheckboxesOnPopup"), userDetailsPageProperties.getProperty("listOfCheckboxRoleOnPopup"));
		} catch (Exception e) {
			LOGGER.error("Role checkbox selection failed: " + e.getMessage());
		}
	}

	/**
	 * This is the method to clear text refresh from an element present on user details page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clearTextRefreshFromUserDetailsPageTextField(String key) throws Exception {
		clearTextRefresh(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait till the element is not invisible
	 *
	 * @param key - Locator of element
	 */
	public final boolean waitForElementsOfUserPageForinvisibile(String key) throws Exception {
		return verifyElementIsinvisibile(userDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait untill an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfUserDetailsPage(String key) {
		try {
			verifyElementIsinvisibile(userDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfUserDetailsPage " + e.getMessage()));
		}
	}
	
	/**
	 * This method will select multiple roles from Edit role PopUp
	 * @throws Exception
	 */
	public final void selectMultipleRolesOnUserDetailsPage() throws Exception {
		List<WebElement> listOfRoles = getElementsOfUserDetailsPage("listOfRoles");
		sleeper(1000);
		if(listOfRoles.size()>0)
		{
			for(int i=0;i<3;i++)
			{
			listOfRoles.get(i).click();
			sleeper(500);
			}
		}
		else
			LOGGER.error("Roles list is empty");
	}

	//Method to get successful message after updating compliance status value from a dropdown
	public String getUpdateRPLComplianceStatusMsg(String txtComplianceValue) {
		String strUpdatedMsg = "";
		try {
			if (getTextOfUserDetailsPage("complianceStatusValue").equals("Blocked") || getTextOfUserDetailsPage("complianceStatusValue").contains("Pending for Approval")) {
				strUpdatedMsg = updateComplianceStatus("Approve");
			}
			strUpdatedMsg = updateComplianceStatus(txtComplianceValue);

		} catch (Exception e) {
			LOGGER.error(("Exception occurred in method getUpdateRPLComplianceStatusMsg " + e.getMessage()));
		}
		return strUpdatedMsg;
	}

	//Method to update compliance status value from a dropdown
	public String updateComplianceStatus(String txtComplianceValue) {
		String strUpdatedMsg = "";
		try {
			clickOnElementsOfUserDetailsPage("btnComplianceEdit");
			waitForPageLoaded();
			clickOnElementsOfUserDetailsPage("btnDropDownArrow");
			selectTextValueFromDropdown(userDetailsPageProperties.getProperty("lstComplianceValues"), txtComplianceValue, userDetailsPageProperties.getProperty("dropDownCompliance"));
			clickOnElementsOfUserDetailsPage("SaveButtonOnPopup");
			waitForPageLoaded();
			waitUntillElementIsPresentOfUserDetailsPage("txtMsgStatusUpdate");
			strUpdatedMsg = getTextOfUserDetailsPage("txtMsgStatusUpdate");
			waitUntilElementIsInvisibleOfUserDetailsPage("txtMsgStatusUpdate");
			LOGGER.info("Compliance status  is updated to "+ txtComplianceValue+ "  successfully");

		} catch (Exception e) {
			LOGGER.error(("Exception occurred in method updateComplianceStatus " + e.getMessage()));
		}
		return strUpdatedMsg;
	}
	
	public final boolean verifyElementIsEnableOnUserProfilePage(String key) throws Exception {
		return verifyElementIsEnaledOrDisabled(userDetailsPageProperties.getProperty(key));
	        
	}
	
	public final String getAttributesOfUserProfilePage(String key, String desiredValue) {
		try {
			return getAttribute(userDetailsPageProperties.getProperty(key), desiredValue);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAttributesOfUserProfilePage " + e.getMessage()));
			return null;
		}
	}
	public boolean verifyVisibilityofToggle(String key) {
	    int maxretrycnt = 5;
	    try {
			for (int attempt=1; attempt <=maxretrycnt; attempt++) {
				    if(verifyElementsOfUserDetailsPage(key)) { 
					 return true;
				}
				sleeper(2000);
		    }        
	    } catch (Exception e) {
	        LOGGER.error("Exception in verifyvisibilityofToggle: ", e);
	        return false;
	    }
	    return false;
	}
}
