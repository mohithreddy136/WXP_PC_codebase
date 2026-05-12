package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

/**
 * This class implements all the methods related to sales team details page related test cases
 */
public class SalesTeamDetailsPage extends CommonMethod {

	private ObjectReader salesTeamDetailsPagePropertiesReader = new ObjectReader();
	private Properties salesTeamDetailsPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private SalesTeamDetailsPage instance;

	public SalesTeamDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SalesTeamDetailsPage.class) {
				if (instance == null) {
					instance = new SalesTeamDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public SalesTeamDetailsPage(WebDriver driver) throws IOException {
		salesTeamDetailsPageProperties = salesTeamDetailsPagePropertiesReader.getObjectRepository("SalesTeamDetailsPageV3");
	}

	/**
	 * This method is the method to verify if an element is present on sales team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is present or not
	 * @throws Exception
	 */
	public final boolean verifyElementsOfSalesTeamDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to verify if an element is visible on sales team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible or not
	 * @throws Exception
	 */
	public final boolean waitForElementsOfSalesTeamDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to match text present on an element with the given text
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be matched
	 * @return - boolean value of whether both the text match
	 * @throws Exception
	 */
	public final boolean matchTextOfSalesTeamDetailsPage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(salesTeamDetailsPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is the method to verify if an element is enabled on sales team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsEnableOfSalesTeamDetailsPage(String key) throws Exception {
		return verifyElementIsEnable(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to verify if an element is selected on sales team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is selected or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsSelectedOfSalesTeamDetailsPage(String key) throws Exception {
		return verifyElementIsSelected(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to get text present on an element on sales team details page
	 * 
	 * @param key - locator of the element
	 * @return - string value of text on the element
	 * @throws Exception
	 */
	public final String getTextOfSalesTeamDetailsPage(String key) throws Exception {
		return getTextBy(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to get attributes of an element on sales team details page
	 * 
	 * @param key - locator of the element
	 * @param desiredValue - attribute of the element to be fetched
	 * @return - string value of the attribute
	 * @throws Exception
	 */
	public final String getAttributesOfSalesTeamDetailsPage(String key, String desiredValue) throws Exception {
		return getAttribute(salesTeamDetailsPageProperties.getProperty(key), desiredValue);
	}

	/**
	 * This is the method to click on an element present on sales team details page
	 * 
	 * @param key - locator of the element
	 * @throws Exception
	 */
	public final void clickOnElementsOfSalesTeamDetailsPage(String key) throws Exception {
		click(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to enter text on an element present on sales team details page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be entered
	 * @throws Exception
	 */
	public final void enterTextForSalesTeamDetailsPage(String key, String textToMatch) throws Exception {
		enterText(salesTeamDetailsPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is the method to verify if an element is clickable on sales team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is clickable or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsClickableOfSalesTeamDetailsPage(String key) throws Exception {
		return verifyElementIsClickable(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to get a list of elements present on sales team details page
	 * 
	 * @param key - locator of the element
	 * @return - list of web elements
	 * @throws Exception
	 */
	public final List<WebElement> getElementsOfSalesTeamDetailsPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to clear text from an element
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clearTextFromSalesTeamDetailsPageTextField(String key) throws Exception {
		clearText(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to select role on role assignment dropdown on sales team details page
	 * 
	 * @param dropdownId - Dropdown on role assignment popup
	 * @param key - - Locator of elements
	 * @param text - Already assigned role
	 * @return - updated role as a string
	 * @throws Exception
	 */
	public final String selectRoleFromDropDownOfSalesTeamDetailsPage(String dropdownId, String key, String text) throws Exception {
		click(salesTeamDetailsPageProperties.getProperty(dropdownId));
		List<WebElement> roleList = getElementsOfSalesTeamDetailsPage(key);
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
	 * This is a method to get an element present on sales team details page
	 * 
	 * @param key - locator of the element
	 * @return - webelement
	 * @throws Exception
	 */
	public final WebElement getElementOfSalesTeamPage(String key) throws Exception {
		return getElement(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to select all incidents subtypes
	 * 
	 * @param allLocator - locator of select all checkbox
	 * @throws Exception
	 */
	public final void selectAllIncidentTypeOfSupportTeamDetailsPage(String allLocator) throws Exception {
		if (getAttributesOfSalesTeamDetailsPage(allLocator, "class").contains("checked")) {
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
		} else {
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
		}
	}

	/**
	 * This is a method to select single incident subtypes
	 * 
	 * @param allLocator - locator of select all checkbox
	 * @param locator - locator of single subtype
	 * @throws Exception
	 */
	public final void selectSingleIncidentTypeOnSupportTeamDetailsPage(String allLocator, String locator) throws Exception {
		if (getAttributesOfSalesTeamDetailsPage(allLocator, "class").contains("checked")) {
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
			clickOnElementsOfSalesTeamDetailsPage(locator);
		} else {
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
			clickOnElementsOfSalesTeamDetailsPage(locator);
		}
	}

	/**
	 * This is a method to deselect all incidents subtypes
	 * 
	 * @param allLocator - locator of select all checkbox
	 * @throws Exception
	 */
	public final void deselectAllIncidentTypeOnSupportTeamDetailsPage(String allLocator) throws Exception {
		if (getAttributesOfSalesTeamDetailsPage(allLocator, "class").contains("checked")) {
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
		} else {
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
			clickOnElementsOfSalesTeamDetailsPage(allLocator);
		}
	}

	/**
	 * This is a method to scroll on sales team details page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void scrollOnSupportTeamPage(String key) throws Exception {
		scrollTillView(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to compare two arraylists
	 * 
	 * @param key - locator of element to fetch first list
	 * @param subTypes - second list
	 * @return - boolean value of whether both the lists match
	 * @throws Exception
	 */
	public final boolean compareTwoListOfSupportTeamDetailsPage(String key, ArrayList<String> subTypes) throws Exception {
		return compareTwoList(salesTeamDetailsPageProperties.getProperty(key), subTypes);
	}

	/**
	 * This is a method to get text present on a list of elements as a list of strings
	 * 
	 * @param key - locator of element to fetch list
	 * @return - arraylist of strings
	 * @throws Exception
	 */
	public final ArrayList<String> getTextOfColumns(String key) throws Exception {
		return getTextOfList(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to click on an element by javascript
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnSupportTeamDetailsPage(String key) throws Exception {
		clickByJavaScript(salesTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify the options that are present on a dropdown by default
	 * 
	 * @param key - Locator of available options
	 * @param optionsOnDropdown - The expected options to be present
	 * @return - boolean value of whether the options are correctly displayed
	 */
	public final boolean verifyOptionsOnDropdownForSalesTeamDetailsPage(String key, ArrayList<String> optionsOnDropdown) {
		try {
			return compareTwoList(salesTeamDetailsPageProperties.getProperty(key), optionsOnDropdown);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyOptionsOnDropdownForSalesTeamDetailsPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to wait untill an element is invisible
	 * 
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfSalesTeamDetailsPage(String key) {
		try {
			verifyElementIsinvisibile(salesTeamDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfSalesTeamDetailsPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to select first option from dropdown
	 * 
	 * @param dropdownListKey - locator of list of options available in dropdown
	 * @return - String value of the selected option from dropdown
	 */
	public final String selectFirstOptionFromDropdownOnSalesTeamDetailsPage(String dropdownListKey) {
		try {
			return selectFirstValueFromDropdown(salesTeamDetailsPageProperties.getProperty(dropdownListKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectFirstOptionFromDropdownOnSalesTeamDetailsPage " + e.getMessage()));
			return null;
		}
	}
	
	/**
	 * This is a method to select multiple roles from Edit role Popup
	 * 
	 * @throws Exception
	 */
	public final void selectMultipleRolesOnSalesTeamDetailsPage() throws Exception {
		List<WebElement> listOfRoles = getElementsOfSalesTeamDetailsPage("listOfRoles");
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

}
