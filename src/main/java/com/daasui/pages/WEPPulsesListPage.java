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
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.WEPPulsesListPageVariables;

public class WEPPulsesListPage extends CommonMethod {

	private WEPPulsesListPage instance;
	private ObjectReader WEPPulsesListPagePropertiesReader = new ObjectReader();
	private Properties WEPPulsesListPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPPulsesListPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPPulsesListPage.class) {
				if (instance == null) {
					instance = new WEPPulsesListPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEPPulsesListPage(WebDriver driver) throws IOException {
		WEPPulsesListPageProperties = WEPPulsesListPagePropertiesReader.getObjectRepository("WEPPulsesListPage");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEPPulsesListPage(String key) throws Exception {
		click(WEPPulsesListPageProperties.getProperty(key));
	}
	/**
	 * This method is to click the elements via actions class
	 * @param key
	 */
	public final void clickByActionsClickWEPPulsesListPage(String key) {
		try {
			actionClick(WEPPulsesListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByActionsClickWEPPulsesListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEPPulsesListPage(String key) {
		try {
			return verifyElementIsVisible(WEPPulsesListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEPPulsesListPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPPulsesListPage(String key) {
		try {
			return verifyElementIsPresent(WEPPulsesListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPPulsesListPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEPPulsesListPage(String key) throws Exception {
		return getTextBy(WEPPulsesListPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForWEPPulsesListPage(String key, String Text) {
		try {
			enterText(WEPPulsesListPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEPPulsesListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEP EE Pulses List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEPPulsesListPage(String key) {
		try {
			scrollTillView(WEPPulsesListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEPPulsesListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEPPulsesListPage(String key) {
		try {
			mouseHover(WEPPulsesListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEPPulsesListPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEPPulsesListPage(String key) throws Exception {
		clickByJavaScript(WEPPulsesListPageProperties.getProperty(key));
	}
	
	/**
	 * This method will provide every text for an element in list
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final List<String> getAllTextOfPulseListPage(String key) throws Exception {
		return getallTextBy(WEPPulsesListPageProperties.getProperty(key));
	}
	
	public boolean verifyAllElementsinWEPPulsesListPage(String[] allElements) {
	    for (String element : allElements) {
	        if (verifyElementsOfWEPPulsesListPage(element)==false) {
	            return false; // Return false immediately if any element verification fails
	        }
	    }
	    return true; // All elements verified successfully
	}
	
	/**
	 * This is a method to get a list of elements present on pulse creation page
	 * @param key - Locator of element
	 * @return - list of web elements
	 */
	public final List<WebElement> getElementsTillAllElementsVisibleofPulseListPage(String key) {
		try {
			return getElementsTillAllElementsPresent(WEPPulsesListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofPulseListPage " + e.getMessage()));
			return null;
		}
	}

/**
 * This method is used to duplicate the Pulses(Custom+Sentiment) from List & Results Page.
 * @param pulseTitle
 * @param duplicateOption
 * @return boolean indicating success or failure
 * @throws Exception 
 */
public boolean verifyDuplicatePulseTypeFunctionality(String pulseTitle, String duplicateOption) throws Exception {
    boolean flag = false;
    WEPPulsesCreationPage WEPPulsesCreationPage = new WEPPulsesCreationPage(PreDefinedActions.getDriver()).getInstance();
    // Common actions for duplicating pulse
    try {
        // Perform duplication based on the selected option
        switch (duplicateOption) {
            case "Checkbox-Selection":
            	clickByActionsClickWEPPulsesListPage("firstPulseSelectionCheckbox");
            	clickByActionsClickWEPPulsesListPage("duplicateButtonListingPage");
            	clickByActionsClickWEPPulsesListPage("DuplicateButton");
            	sleeper(2000); 
            	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
            	break;           
            case "Elipces-Selection":
            	mousehoverOnWEPPulsesListPage("firstPulseSelectionCheckbox");
            	clickByActionsClickWEPPulsesListPage("firstRowElipsce");
            	clickByActionsClickWEPPulsesListPage("duplicateButtonElipsceSelection");
            	clickByActionsClickWEPPulsesListPage("DuplicateButton");
            	sleeper(2000);
            	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
            	break;              
            case "Results-Selection":
            	clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
            	clickByActionsClickWEPPulsesListPage("resultsActionButton");
            	clickByActionsClickWEPPulsesListPage("DuplicateButton");
            	clickByActionsClickWEPPulsesListPage("DuplicateButton");
            	sleeper(2000);
            	WEPPulsesCreationPage.clickByActionsClickWEPPulsesCreationPage("editTitleIcon");
            	break;        
            default:
                LOGGER.info("Provided option does not match any duplication types!");
                return false;
        }
        // Verify duplication by checking the pulse title
        String pulseTitleAfterDuplication = WEPPulsesCreationPage.getAttributesOfWEPPulsesCreationPage("PulseTitle", "value");
        if (pulseTitleAfterDuplication.equals(pulseTitle + "-Copy")) {
            LOGGER.info("Selected Pulse Got Duplicated successfully");
            flag = true;
        }else {
        LOGGER.error("Pulse didn't got duplicated - successfully");
        }
    } catch (Exception e) {
        LOGGER.error("Exception occurred in method verifyDuplicatePulseTypeFunctionality: " + e.getMessage());
        return false;
    }
    return flag;
}

/**
 * This method is used to delete the Pulses(Custom+Sentiment) from List & Results Page.
 * @param pulseTitle
 * @param duplicateOption
 * @return boolean indicating success or failure
 * @throws Exception 
 */
public boolean verifyDeletePulseTypeFunctionality(String pulseTitle, String deleteOption) throws Exception {
    boolean flag = false;
   // Common actions for deleting pulse
    try {
    	// Negative scenario - Selection of more than one Pulse from pulse List.
    	List<WebElement> checkBoxSelectionList = getElementsTillAllElementsVisibleofPulseListPage("checkBoxSelectionList");
    	for(WebElement morethanonecheckbox1:checkBoxSelectionList) {
    		morethanonecheckbox1.click();
    	}
    	 String[] allElements = {"deleteButtonListingPage","duplicateButtonListingPage","editButtonListingPage"};
    	 if( verifyAllElementsinWEPPulsesListPage(allElements)==false) {
    		 LOGGER.info("Buttons are not dsplayed into the listing page when more than one Pulses are selected");
    	 }
    	 clickByActionsClickWEPPulsesListPage("allSelectionCheckbox");
    	 //Twice a click to remove all selected values
    	 clickByActionsClickWEPPulsesListPage("allSelectionCheckbox");
        // Perform deletion from the provided option
        switch (deleteOption) {
            case "Checkbox-Selection":
            	clickByActionsClickWEPPulsesListPage("firstPulseSelectionCheckbox");
            	clickByActionsClickWEPPulsesListPage("deleteButtonListingPage");
            	clickByActionsClickWEPPulsesListPage("deleteButton");
            	waitForPageLoaded();
            	break;           
            case "Elipces-Selection":
            	sleeper(3000);
            	mousehoverOnWEPPulsesListPage("firstPulseSelectionCheckbox");
            	clickByActionsClickWEPPulsesListPage("firstRowElipsce");
            	clickByActionsClickWEPPulsesListPage("deleteButtonElipsceSelection");
            	clickByActionsClickWEPPulsesListPage("deleteButton");
            	break;        
            case "Results-Selection":
            	clickByActionsClickWEPPulsesListPage("firstLatestPulselist");
            	if(getTextOfWEPPulsesListPage("resultsStatusText").equals(WEPPulsesListPageVariables.SCHEDULED_STATUS)) {
            	clickByActionsClickWEPPulsesListPage("resultsActionButton");
            	clickByActionsClickWEPPulsesListPage("deleteButton");
               	clickByActionsClickWEPPulsesListPage("deleteButton");
            	}
            	else {
            		 LOGGER.error("Pulse Type other than Scheduled can't be deleted from pulse results Page");
            	}
            	break;        
            default:
                LOGGER.info("Provided option does not match any deletion types!");
                return false;
        }
        // Verify duplication by checking the pulse title
        sleeper(5000);
    	waitForPageLoaded();
    	waitForElementsOfWEPPulsesListPage("firstLatestPulselist");
    	String AfterDeletion = getTextOfWEPPulsesListPage("firstLatestPulselist");
    	if(!AfterDeletion.equals(pulseTitle)) {
    		flag=true;
    		LOGGER.info("Deleted Pulse record didn't displayed into the Pulse results page.");
        }else {
        LOGGER.error("Deleted Pulse gets displayed into the list page even after deletion.");
        }
    } catch (Exception e) {
        LOGGER.error("Exception occurred in method verifyDeletePulseTypeFunctionality: " + e.getMessage());
        return false;
    }
    return flag;
}
}