package com.daasui.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.sis.internal.jaxb.gmd.LanguageCode;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.DeviceVariables;
import com.daasui.constants.WEPPulsesCreationPageVariables;

public class WEPPulsesCreationPage extends CommonMethod {

	private WEPPulsesCreationPage instance;
	private ObjectReader WEPPulsesCreationPagePropertiesReader = new ObjectReader();
	private Properties WEPPulsesCreationPagePageProperties;
	private static Logger LOGGER = LogManager.getLogger(WEPPulsesCreationPage.class);
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPPulsesCreationPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPPulsesCreationPage.class) {
				if (instance == null) {
					instance = new WEPPulsesCreationPage(DRIVER);
				}
			}
		}
		return instance;
	}
	
	public WEPPulsesCreationPage(WebDriver driver) throws IOException {
		WEPPulsesCreationPagePageProperties = WEPPulsesCreationPagePropertiesReader.getObjectRepository("WEPPulsesCreationPage");
	}

	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEPPulsesCreationPage(String key) throws Exception {
        try {
        	click(WEPPulsesCreationPagePageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByActionsClickWEPPulsesCreationPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEPPulsesCreationPage(String key) {
		try {
			return verifyElementIsVisible(WEPPulsesCreationPagePageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEPPulsesCreationPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPPulsesCreationPage(String key) {
		try {
			return verifyElementIsPresent(WEPPulsesCreationPagePageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPPulsesCreationPage " + e.getMessage()));
			return false;
		}
	}
	
	public final void switchToIframeofWEPPulsesCreationPage(String key) throws Exception {
		switchToIframe(WEPPulsesCreationPagePageProperties.getProperty(key));
	}

	public void switchToDefaultContentofOfIntegrationPage() throws Exception {
		switchToDefaultContent();
	}
	
	/**
	 * This is a method to verify if the element is selected
	 * @param key - Locator of element
	 * @return - - boolean value of whether the element is selected or not
	 */
	public final boolean verifyElementsIsSelectedOfWEPPulsesCreationPage(String key) {
	try {
		return verifyElementIsSelected(WEPPulsesCreationPagePageProperties.getProperty(key));
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method verifyElementsOfWEPPulsesCreationPage " + e.getMessage()));
		return false;
	}
	}

	/**
	 * This method is to get the element text for the provided key
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final String getTextOfWEPPulsesCreationPage(String key) throws Exception {
		return getTextBy(WEPPulsesCreationPagePageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForWEPPulsesCreationPage(String key, String Text) {
		try {
			enterText(WEPPulsesCreationPagePageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEPPulsesCreationPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextJavaScriptForWEPPulsesCreationPage(String key, String Text) {
		try {
			enterTextUsingJavaScript(WEPPulsesCreationPagePageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEPPulsesCreationPage " + e.getMessage()));
		}
	}
	

	/**
	 * This is a method to scroll on WEP EE Pulses Creation Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEPPulsesCreationPage(String key) {
		try {
			scrollTillView(WEPPulsesCreationPagePageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEPPulsesCreationPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEPPulsesCreationPage(String key) {
		try {
			mouseHover(WEPPulsesCreationPagePageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEPPulsesCreationPage " + e.getMessage()));
		}
	}
	
	/**
	 * This method is to get the elements from the webpage of WEPEE Pulse Creation Page
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final WebElement getElementOnWEPPulsesCreationPage(String key) throws Exception {
		return getElement(WEPPulsesCreationPagePageProperties.getProperty(key));
	}
	
	
	/**
	 * This method is used to click the elements via java script executor
	 * @param key
	 */
	public final void clickByJavaScriptOnWEPPulsesCreationPage(String key) {
		try {
		clickByJavaScript(WEPPulsesCreationPagePageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnWEPPulsesCreationPage " + e.getMessage()));
		}
	}
	
	/**
	 * Upload user via CSV
	 * @param inviteUserFirstName
	 * @param inviteUser_email
	 * @throws Exception
	 */
	public void uploadCSVFunctionalityOnWEPPulsesCreationPage(List<String> devicelist) throws Exception {
		CSVFileReader csvFileReader = new CSVFileReader();
		File csvFile = new File(ConstantPath.IMPORT_PATH + WEPPulsesCreationPageVariables.Import_CSV_UPLOAD);
		csvFileReader.writeDataCSVmultiple(csvFile, devicelist);
		String Filename = WEPPulsesCreationPageVariables.Import_CSV_UPLOAD;
		fileImportInV3(ConstantPath.IMPORT_PATH + WEPPulsesCreationPageVariables.Import_CSV_UPLOAD);
		VerifyUploadCSVInProgressElements("uploadinprogressFileName", "uploadInprogressText", "uploadInprogessBar", "fileDeleteIcon", Filename);
	}
	
	
	/**
	 * Verify UploadCSVInProgress Elements
	 * @param uploadIconInprogress
	 * @param uploadInprogressText
	 * @param uploadInprogressContext
	 * @param uploadInprogessBar
	 * @throws Exception
	 */
	public void VerifyUploadCSVInProgressElements(String fileNameInProgress, String uploadInprogressText, String uploadInprogessBar,String fileDeleteIcon, String filenameprovided) throws Exception {
		verifyElementsOfWEPPulsesCreationPage(fileNameInProgress);
		getTextOfWEPPulsesCreationPage(fileNameInProgress).equals(filenameprovided);
		verifyElementsOfWEPPulsesCreationPage(uploadInprogressText);
		verifyElementsOfWEPPulsesCreationPage(fileDeleteIcon);
		verifyElementsOfWEPPulsesCreationPage(uploadInprogessBar);
	}
	
	/**
	 * File Import in V3
	 * @param filename
	 * @throws Exception
	 */
	public void fileImportInV3(String fileName) throws Exception {
		StringSelection path = new StringSelection(fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);   
		Robot robot = new Robot();
		robot.setAutoDelay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		sleeper(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 * This method is to click the elements via actions class
	 * @param key
	 */
	public final void clickByActionsClickWEPPulsesCreationPage(String key) {
		try {
			actionClick(WEPPulsesCreationPagePageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByActionsClickWEPPulsesCreationPage " + e.getMessage()));
		}
	}
	
	
	/**
	 * This method is to get the attribute value for the provided tag
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public final String getAttributesOfWEPPulsesCreationPage(String key, String value) throws Exception {
		return getAttribute(WEPPulsesCreationPagePageProperties.getProperty(key), value);
	}
	
	/**
	 * This method is to switch back to the previous browser
	 */
	public final void switchToPreviousTabOfWEPPulsesCreationPage() {
		switchBackToPreviousTab();
	}
	
	/**
	 * This method is to switch to different browser
	 */
	public final void switchToDifferentTabOfWEPPulsesCreationPage(){
		switchToDifferentTab();
	}
	
	/** 
	 * This is a method to select date from calendar filter
	 * @param date - current date
	 * @param monthKeyCurrent - locator of current month
	 * @param rightArrowKey - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month 
	 */
	public final void selectDateFromCalenderOnWEPPulsesCreationPage(String date, String monthKeyCurrent, String rightArrowKey, String daysOnCurrentMonthKey) {
		try {
			selectDateFromCalenderDatePickerContainer(date, WEPPulsesCreationPagePageProperties.getProperty(monthKeyCurrent), WEPPulsesCreationPagePageProperties.getProperty(rightArrowKey), WEPPulsesCreationPagePageProperties.getProperty(daysOnCurrentMonthKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderOnWEPPulsesCreationPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to verify element is clickable
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifyElementIsClickableOnWEPPulsesCreationPage (String key) throws Exception {
		return verifyElementIsClickable(WEPPulsesCreationPagePageProperties.getProperty(key));
	}
	
	/**
	 * This is a method to verify element is Enabled
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyElementIsEnableonWEPPulsesCreationPage (String key) throws Exception{
		return verifyElementIsEnable (WEPPulsesCreationPagePageProperties.getProperty(key));
	}
	
	/**
	 * This method will enter the text in input field without even clearing the existing text
	 * @param key
	 * @param Text
	 * @throws Exception
	 */
	public final void enterTextwithoutclearForWEPPulsesCreationPage(String key, String Text) throws Exception {
		enterTextwithoutclear(WEPPulsesCreationPagePageProperties.getProperty(key), Text);
	}
	
	/**
	 * This method is used to upload company logo from company setting page from Pulses creation page redirection
	 * @param LanguageCode
	 * @param selectLogoImageIcon
	 * @param selectImageRadioButtonOnPopup
	 * @param browseButton
	 * @param fileName
	 * @param saveButtonOnPopUp
	 * @return
	 */
	public boolean verifyUploadCompanyLogo(String LanguageCode,String selectLogoImageIcon, String selectImageRadioButtonOnPopup, String browseButton,String fileName, String saveButtonOnPopUp) {
		boolean flag = false;
		try {
			switchToDifferentTabOfWEPPulsesCreationPage();
			LOGGER.info("Switched to other browser tab successfully");
			waitForPageLoaded();
			waitForElementsOfWEPPulsesCreationPage(selectLogoImageIcon);
			sleeper(2000);
			clickByActionsClickWEPPulsesCreationPage(selectLogoImageIcon);
			sleeper(2000);
			//waitForElementsOfWEPPulsesCreationPage(selectImageRadioButtonOnPopup);
			clickByJavaScriptOnWEPPulsesCreationPage(selectImageRadioButtonOnPopup);
			waitForElementsOfWEPPulsesCreationPage(browseButton);
			clickByActionsClickWEPPulsesCreationPage(browseButton);
			sleeper(5000);
			LOGGER.info("Clicked on browse image");
			StringSelection path = new StringSelection(ConstantPath.PULSE_COMPANY_LOGO + fileName);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);   
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			sleeper(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			sleeper(1000);
			clickByActionsClickWEPPulsesCreationPage(saveButtonOnPopUp);
			sleeper(3000);
			waitForPageLoaded();
			LOGGER.info("Company logo has been uploaded successfully");
			switchToPreviousTabOfWEPPulsesCreationPage();
			LOGGER.info("Switched back to previous browser tab successfully");
			refreshPage();
			LOGGER.info("Refreshed the browser tab to view the updated Company logo");
			flag= true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyUploadCompanyLogo " + e.getMessage()));
			return false;
		}
		return flag;
	}

	/**
	 * This method is to verify the Company Logo sections of the Sentiment Pulse Creation page
	 * @param LanguageCode
	 * @param LogoSectionTobeEnabled
	 * @param fromSettingsPage
	 * @return
	 */
	public boolean verifyCompanylogoSectionValidationsPulseCreation(String LanguageCode, Boolean LogoSectionTobeEnabled,  Boolean fromSettingsPage){
		boolean flag = false;
		try {
			sleeper(3000);
		    verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel");   
		    boolean isCompanyLogoNotPresent = verifyElementsOfWEPPulsesCreationPage("companyLogoNotPresent");
		    boolean isCompanyLogoPresent = verifyElementsOfWEPPulsesCreationPage("companyLogoPresent");
		    // Case 1: Logo not present and company logo to be uploaded & enabled
		    if (isCompanyLogoNotPresent==true && isCompanyLogoPresent==false && LogoSectionTobeEnabled==true) {
		    	sleeper(3000);
		        clickByActionsClickWEPPulsesCreationPage("uploadCompanyLogoLabel");
		        if (verifyUploadCompanyLogo(LanguageCode, "selectLogoImageIcon", "selectImageRadioButtonOnPopup", "browseButton", "cat.jpg", "saveButtonOnPopUp")) {
		            if (verifyElementsOfWEPPulsesCreationPage("companyLogoPresent")) {
		                verifyElementIsEnableonWEPPulsesCreationPage("companyLogoToggle");
		                verifyElementsOfWEPPulsesCreationPage("updateCompanyLogoLabel");
		                LOGGER.info("Show Company logo label & settings link - present and verified successfully");
		                flag = true;
		            }
		        }
		    } 
		    // Case 2: Logo not present and company logo section to be disabled
		    else if (isCompanyLogoNotPresent==true && isCompanyLogoPresent == false && LogoSectionTobeEnabled==false) {
		       waitForElementsOfWEPPulsesCreationPage("companyLogoToggle");
		    	verifyElementIsEnableonWEPPulsesCreationPage("companyLogoToggle");
		        if (verifyElementsOfWEPPulsesCreationPage("uploadCompanyLogoLabel")) {
		            clickByActionsClickWEPPulsesCreationPage("companyLogoToggle");
		            LOGGER.info("Company Logo Not present by default - Pulse Creation without CompanyLogo - Scenario");
		            flag = true;
		            }
		    } 
		    // Case 3: Logo present and company logo section to be enabled
		    else if (isCompanyLogoNotPresent==false && isCompanyLogoPresent==true && LogoSectionTobeEnabled==true) {
		        LOGGER.info("Company Logo is Present already and verified successfully");
		        waitForElementsOfWEPPulsesCreationPage("companyLogoToggle");
		        verifyElementIsEnableonWEPPulsesCreationPage("companyLogoToggle");
		        if (verifyElementsOfWEPPulsesCreationPage("updateCompanyLogoLabel")) {
		            LOGGER.info("Show Company logo label & settings link - present and verified successfully");
		            flag = true;
		        }
		    } 
		    // Case 4: Logo present and company logo to be removed via settings page
		    else if (isCompanyLogoNotPresent==false && isCompanyLogoPresent==true && LogoSectionTobeEnabled==false && fromSettingsPage==true) {
		        waitForElementsOfWEPPulsesCreationPage("updateCompanyLogoLabel");
		    	clickByActionsClickWEPPulsesCreationPage("updateCompanyLogoLabel");
		        switchToDifferentTabOfWEPPulsesCreationPage();
		        sleeper(4000);
		        clickByActionsClickWEPPulsesCreationPage("selectLogoImageIcon");
		        waitForElementsOfWEPPulsesCreationPage("saveButtonOnPopUp");
		        clickByActionsClickWEPPulsesCreationPage("saveButtonOnPopUp");
		        sleeper(4000);
		        if (verifyElementsOfWEPPulsesCreationPage("LogoPresent")==false) {
		            switchToPreviousTabOfWEPPulsesCreationPage();
		            refreshPage();
		            verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel");
		            verifyElementsOfWEPPulsesCreationPage("uploadCompanyLogoLabel");
		            if (verifyElementsOfWEPPulsesCreationPage("companyLogoNotPresent")) {
		                verifyElementIsEnableonWEPPulsesCreationPage("companyLogoToggle");
		                clickByActionsClickWEPPulsesCreationPage("companyLogoToggle");
		                LOGGER.info("Company logo got removed successfully from -> company settings page");
		                flag = true;
		            }
		        }
		    } 
		    // Case 5: Logo present and company logo toggle to be disabled
		    else if (isCompanyLogoNotPresent==false && isCompanyLogoPresent==true && LogoSectionTobeEnabled==false && fromSettingsPage==false) {
		       waitForElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel");
		    	verifyElementsOfWEPPulsesCreationPage("CompanyLogoTitleLabel");
		        verifyElementIsEnableonWEPPulsesCreationPage("companyLogoToggle");
		        if (verifyElementsOfWEPPulsesCreationPage("updateCompanyLogoLabel")) {
		        	clickByActionsClickWEPPulsesCreationPage("companyLogoToggle");
		            LOGGER.info("Company logo present & set to off -> manually through turning off the Toggle - successfully");
		            flag = true;
		        }
		    }
		} catch (Exception e) {
		    LOGGER.error("Exception occurred in method verifyCompanyLogoSectionValidationsPulseCreation: " + e.getMessage());
		    return false;
		}
		return flag;		
		}


/**
 * This method is to verify the Scheduling section for the sentiment Pulse creation page
	 * @param PulseType
	 * @param ScheduleMethod
	 * @param EndDateToggle
	 * @return
	 */
	public boolean verifyScheduleSectionTabValidationsPulseCreation(String LanguageCode, String PulseType, String ScheduleMethod, Boolean WithEndDate, String Priority) {
		boolean flag = false;
		try {
		    if ((PulseType.equals("Custom Pulse")&&ScheduleMethod.equals("Date-based"))) {
		    	if(Priority.equals("Urgent")) {
		    		verifyElementsIsSelectedOfWEPPulsesCreationPage("standardPriorityDefaultSelection");
		    		 verifyElementsOfWEPPulsesCreationPage("urgentPriorityRadioButton");
			          clickByActionsClickWEPPulsesCreationPage("urgentPriorityRadioButton");
			          getTextOfWEPPulsesCreationPage("sendImmediatelyHeaderText").equals(getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.notification.title"));
			          getTextOfWEPPulsesCreationPage("sendImmediatelyFirstContent").equals(getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.notification.text_subcopy"));
			          getTextOfWEPPulsesCreationPage("sendImmediatelySecondContent").equals(getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.notification.text_subcopy.list.element.one"));
			          getTextOfWEPPulsesCreationPage("sendImmediatelythirdContent").equals(getTextLanguage(LanguageCode, "daas_ui", "ee.pulses.urgent.pulses.notification.text_subcopy.list.element.two"));
			          return flag = true;
		    	}
		    	//For Only Standard Pulses to check if the standard is selected by default
		    	verifyElementsIsSelectedOfWEPPulsesCreationPage("standardPriorityDefaultSelection");
		    }
		    //Normal flow will be executed if it's a Sentiment Pulse
		    verifyElementsOfWEPPulsesCreationPage("startDateLabel");
		            clickByActionsClickWEPPulsesCreationPage("startDatePickField");
		            clickByActionsClickWEPPulsesCreationPage("currentDateOnCalender");
		            LOGGER.info("Current Date is selected as the Start Date value");
		            if (verifyElementsOfWEPPulsesCreationPage("StartTimeTitle")) {
		                clickByActionsClickWEPPulsesCreationPage("StartTimeDD");
		                sleeper(1000);
		                clickByActionsClickWEPPulsesCreationPage("StartTimefirstValue");
		                LOGGER.info("First Value from the drop-down is selected as Start Time");
		                if (verifyElementsOfWEPPulsesCreationPage("endDateSection")) {
		                    if (WithEndDate==true) {
		                        verifyElementsOfWEPPulsesCreationPage("endDateLabel");
		                        scrollOnWEPPulsesCreationPage("endDateLabel");
		                        // Handled negative scenario: Same start-end date
		                        sleeper(3000);
		                        clickByActionsClickWEPPulsesCreationPage("endDatePicker");
		                        clickByActionsClickWEPPulsesCreationPage("currentDateOnCalender");
		                        waitForElementsOfWEPPulsesCreationPage("endDateHelperText");
		                        LOGGER.info("Negative Scenario: By selecting the same start-end date scenario");
		                        if (verifyElementsOfWEPPulsesCreationPage("endDateHelperText")) {
		                            clickByActionsClickWEPPulsesCreationPage("endDateClear");
		                            LOGGER.info("Error helper text appeared and verified successfully; cleared the invalid End date value");
		                            // Positive scenario: End date greater than the Start date
		                            clickByActionsClickWEPPulsesCreationPage("endDatePicker");
		                            selectDateFromCalenderOnWEPPulsesCreationPage(addDaysToCurrentDate(31), "monthKeyCurrent", "endDateDialogRightArrow", "daysOnCurrentMonthKey");
		                            LOGGER.info("Valid End date value selected successfully");
		                        } else {
		                            LOGGER.info("End date helper text is not visible even though the start and end dates are the same.");
		                            flag = false; // set the value to false as it's not displaying the error message marking this method to be failed
		                        }
		                    } else {
		                        // Toggle end date off if helper text is not present
		                        verifyElementsOfWEPPulsesCreationPage("endDateToggle");
		                        clickByActionsClickWEPPulsesCreationPage("endDateToggle");
		                        LOGGER.info("End-Date toggle was set to ON by default -> toggled OFF successfully");
		                        sleeper(2000);
		                    }
		                    flag = true; // Set flag to true as every conditions are met
		                }
		            }  
		} catch (Exception e) {
		    LOGGER.error("Exception occurred in method verifySchedulingSectionValidationsOnSentimentPulseType: " + e.getMessage());
		    return false;
		}
		return flag;
	}

 
/**
 * This method is to verify the audience section via CSV upload method on Pulse Creation Page
 * @param LanguageCode
 * @param methodValues
 * @param csvMethod
 * @param devicelist
 * @return
 */
	public boolean verifyAudienceSectionValidationsTargetCSVmethod(String LanguageCode, String methodValues, String csvMethod, List<String> devicelist, Integer TotalActiveDevices) {
	    boolean flag = false;
	    try {
	        List<WebElement> targetingMethodList = getElementsTillAllElementsVisibleofPulseCreationpage(methodValues);
	        // Click the matching method from the list (Using Stream API)
	        targetingMethodList.stream()
	                .filter(method -> csvMethod.equals(method.getText()))
	                .findFirst()
	                .ifPresent(WebElement::click);
	        LOGGER.info("Selected targeting method: " + csvMethod);
	        clickByActionsClickWEPPulsesCreationPage("uploadCSVButton");
	        LOGGER.info("CSV Upload button clicked successfully");
	        uploadCSVFunctionalityOnWEPPulsesCreationPage(devicelist);
	        waitForElementsOfWEPPulsesCreationPage("deviceCount");
	        LOGGER.info("CSV uploaded successfully with valid active devices");
	        // Extract device count and validate
	        String expectedCountText = devicelist.size() + " out of " + TotalActiveDevices + " enrolled devices";
	        String actualDeviceCountText = getTextOfWEPPulsesCreationPage("deviceCount");
	        if (expectedCountText.equals(actualDeviceCountText)) {
	            LOGGER.info("Audience count matched: " + actualDeviceCountText);
	            flag = true;
	        } else {
	            LOGGER.error("Audience count mismatch. Expected: " + expectedCountText + ", but got: " + actualDeviceCountText);
	        }
	        verifyElementsOfWEPPulsesCreationPage("reachHeaderText");
	        LOGGER.info("Reach section verified with actual reach count & description");
	    } catch (Exception e) {
	        LOGGER.error("Exception in verifyAudienceSectionValidationsTargetCSVmethod: ", e);
	        return false;
	    }
	    return flag;
	}

	
/**
 * @param LanguageCode
 * @param StartDate
 * @return
 */
public boolean verifyNotificationSectionValidations(String LanguageCode, String StartDate) {
	boolean flag = false;
	try {
	    verifyElementsOfWEPPulsesCreationPage("notificationHeaderRequired");  
	    // Verify URL note text
	    String urlNoteText = getTextOfWEPPulsesCreationPage("urlNoteText");
	    String expectedUrlNoteText = getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_message_text");
	    if (!urlNoteText.equals(expectedUrlNoteText)) {
	        LOGGER.error("URL note text does not match expected value.");
	        return flag; // Early return if mismatch
	    }
	    // Enter notification details
	    enterTextwithoutclearForWEPPulsesCreationPage("questionInputField", StartDate + " Automation Notification Question");
	    enterTextwithoutclearForWEPPulsesCreationPage("questionInputField", WEPPulsesCreationPageVariables.CUSTOM_PULSE_NOTIFICATION_URL_TEXT);
	    enterTextwithoutclearForWEPPulsesCreationPage("questionInputField", WEPPulsesCreationPageVariables.CUSTOM_PULSE_NOTIFICATION_URL);
	    // Wait for and verify acknowledgment checkbox
	    waitForElementsOfWEPPulsesCreationPage("checkBoxAck");
	    if (verifyElementsOfWEPPulsesCreationPage("checkBoxAck")) {
	        verifyElementsOfWEPPulsesCreationPage("ackTextContent");
	        String ackTextContent = getTextOfWEPPulsesCreationPage("ackTextContent");
	        String expectedAckText = getTextLanguage(LanguageCode, "daas_ui", "ee_msg_url_disclaimer"); 
	        if (!ackTextContent.equals(expectedAckText)) {
	            LOGGER.error("Acknowledgment text content does not match expected value.");
	            return flag; // Early return if mismatch
	        }
	        // Proceed with verification of required message for checkbox selection
	        clickByActionsClickWEPPulsesCreationPage("audienceSectionTab");
	        clickByActionsClickWEPPulsesCreationPage("publishButton");
	        clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
	        // Verify checkbox selection
	        if (verifyElementsOfWEPPulsesCreationPage("reqCheckboxSelection")) {
	            clickByActionsClickWEPPulsesCreationPage("checkBoxAck");
	            flag = true; // Successful completion
	        }
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception occurred in method verifyNotificationSectionValidations: " + e.getMessage());
	    return false;
	}
	return flag;
}

public boolean verifyRecurringFlowDateBased(String LanguageCode, String Interval) {
	boolean flag = false;
	try {
	    // Verify frequency header and repeats toggle
	    verifyElementsOfWEPPulsesCreationPage("frequencyHeader");	
	    verifyElementsOfWEPPulsesCreationPage("repeatsToggle");
	    // Check absence of every title header and absence of interval dropdown header
	    if (verifyElementsOfWEPPulsesCreationPage("everyTitleHeader")==false && verifyElementsOfWEPPulsesCreationPage("intervalDDHeader")==false) {
	        // Click the repeats toggle
	        clickByActionsClickWEPPulsesCreationPage("repeatsToggle");
	        // Verify headers after toggling ON
	        verifyElementsOfWEPPulsesCreationPage("everyTitleHeader");
	        verifyElementsOfWEPPulsesCreationPage("intervalDDHeader");
	        // Default months value selection check
	        String selectedInterval = getTextOfWEPPulsesCreationPage("intervalDDValueSelected");
	        String expectedMonthsText = getTextLanguage(LanguageCode, "daas_ui", "ee_select_text_months");
	        String expectedSingleMonthText = getTextLanguage(LanguageCode, "daas_ui", "ee_select_text_month");
	        if (selectedInterval.equals(expectedMonthsText)) {
	            enterTextForWEPPulsesCreationPage("everyInputField", "1");
	            selectedInterval = getTextOfWEPPulsesCreationPage("intervalDDValueSelected"); // Update after input
	            if (selectedInterval.equals(expectedSingleMonthText)) {
		            enterTextForWEPPulsesCreationPage("everyInputField", "2");
		            selectedInterval = getTextOfWEPPulsesCreationPage("intervalDDValueSelected"); // Update after input
		            // Final check for months
		            if (selectedInterval.equals(expectedMonthsText)) {
			            flag = true; // Successful completion
			        }
		        }
	        }    
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception occurred in method verifyNotificationSectionValidations: " + e.getMessage());
	    return false;
	}
	return flag;
}
/**
 * Verify Audience Section - CSV Upload Section with Negative Scenario's
 * @param LanguageCode
 * @param methodValues
 * @param csvMethod
 * @return
 */
public boolean verifyAudienceSectionErrorMsgsValidationsCSVUpload(String LanguageCode, String methodValues, String csvMethod) {
	boolean flag = false;
	try {
	    List<WebElement> targetingMethodList = getElementsTillAllElementsVisibleofPulseCreationpage(methodValues);
	    // Click the targeting method if found
	    for (WebElement method : targetingMethodList) {
	        if (csvMethod.equals(method.getText())) {
	            method.click();
	            break; // Exit the loop once clicked
	        }
	    }
	    // Defined an array of test cases for different file uploads
	    String[] testFiles = {
	        WEPPulsesCreationPageVariables.Import_EMPTY_CSV_UPLOAD,
	        WEPPulsesCreationPageVariables.Import_MORE_THAN_10K_CSV_UPLOAD,
	        WEPPulsesCreationPageVariables.Import_INVALID_FORMAT_UPLOAD,
	        WEPPulsesCreationPageVariables.Import_INVALID_CSV_UPLOAD
	    };
	    String[] errorMessages = {
	        getTextLanguage(LanguageCode, "daas_ui", "ee_csv_upload_error_empty_file"),
	        getTextLanguage(LanguageCode, "daas_ui", "ee_csv_upload_error_record_limit"),
	        getTextLanguage(LanguageCode, "daas_ui", "ee_csv_upload_error_file_type")
	    };
	    for (int i = 0; i < testFiles.length; i++) {
	        clickByActionsClickWEPPulsesCreationPage("uploadCSVButton");
	        fileImportInV3(ConstantPath.IMPORT_PATH + testFiles[i]);
	        if(i!=2) {
	        VerifyUploadCSVInProgressElements("uploadinprogressFileName", "uploadInprogressText", "uploadInprogessBar", "fileDeleteIcon", testFiles[i]);
	        }
	        // Handle specific errors based on the test file
	        if (i < 2) { // For empty CSV and record limit
	            verifyElementsOfWEPPulsesCreationPage("errorMessage");
	            String errorMsg = getTextOfWEPPulsesCreationPage("errorMessage");
	            if (errorMsg.equals(errorMessages[i])) {
	                clickByActionsClickWEPPulsesCreationPage("deleteFileStateIcon");
	                if (verifyElementsOfWEPPulsesCreationPage("errorMessage")==false) {
	                    LOGGER.info((i == 0 ? "Empty" : "More than 10k") + " CSV file upload scenario verified successfully & error message validated");
	                    flag = true;
	                }
	            }
	        } else if (i == 2) { // For invalid format
	            verifyElementsOfWEPPulsesCreationPage("errorMessageformatInvalid");
	            if (getTextOfWEPPulsesCreationPage("errorMessageformatInvalid").equals(errorMessages[2])) {
	                flag = true;
	                LOGGER.info("Incorrect file format upload scenario verified successfully & error message validated");
	            }
	        } else if (i == 3) { // For invalid CSV
	            if (verifyElementsOfWEPPulsesCreationPage("errorMessage")) {
	                clickByActionsClickWEPPulsesCreationPage("errorFile");
	                sleeper(3000);
	                clickByActionsClickWEPPulsesCreationPage("deleteFileStateIcon");
	                if (verifyElementsOfWEPPulsesCreationPage("errorMessage")==false) {
	                    LOGGER.info("Errors CSV file downloaded successfully");
	                    LOGGER.info("Invalid CSV file upload scenario verified successfully & error message validated");
	                    flag = true;
	                }
	            }
	        }
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception occurred in method verifyAudienceSectionErrorMsgsValidationsCSVUpload: " + e.getMessage());
	    return false;
	}
	return flag;
}

/**
 * This method is to verify the content section for sentiment pulse type
 * @param contentLogoPresent
 * @param contentLogo
 * @return
 */
public boolean verifyContentSectionOnSentimentPulsesType(boolean LogoPresent, String contentLogo) {
	boolean flag = false;
	try {
	    int maxContentQuesCount = 3;    
	    // Verify preview count and content sections
	    verifyElementsOfWEPPulsesCreationPage("contentpreviewNumbers");
	    LOGGER.info("Total preview count section verified successfully");   
	    verifyElementsOfWEPPulsesCreationPage("contentDescriptionSentiment");
	    verifyElementsOfWEPPulsesCreationPage("contentQuestionSections"); 
	    // Check if the back button is disabled
	    if ((getAttributesOfWEPPulsesCreationPage("contentpreviewbackEnabled", "disabled").equals("true"))&&(getAttributesOfWEPPulsesCreationPage("contentpreviewfarwardenabled", "disabled")==null)) {  
	    if (LogoPresent==true) {
	            if (verifyElementsOfWEPPulsesCreationPage(contentLogo)==true) {
	                LOGGER.info("Company logo present on the content preview section when logo is present - verified successfully");
	                flag = true;
	            }else if(verifyElementsOfWEPPulsesCreationPage(contentLogo)==false){
	            	LOGGER.info("Company logo is not present on the content preview section when logo is present - verified successfully");
	            	return flag;
	            }
	        } else if(LogoPresent==false) {
	        	if (verifyElementsOfWEPPulsesCreationPage(contentLogo)==false) {
	                LOGGER.info("Company logo is not present on the content preview section when logo is not present - verified successfully");
	                flag = true;
	            }else if(verifyElementsOfWEPPulsesCreationPage(contentLogo)==false){
	            	LOGGER.info("Company logo is present on the content preview section when logo is not present - verified successfully");
	            	return flag;
	            }
	        }
	        
	        // Forward content preview
	        for (int i = 1; i < maxContentQuesCount; i++) {
	            clickByActionsClickWEPPulsesCreationPage("contentpreviewfarwardenabled");
	            verifyElementsOfWEPPulsesCreationPage("contentQuestionSections");
	        }
	        LOGGER.info("Clicked on max forward preview button and verified the content sections");
	        sleeper(4000);
	        // Check button states at max content count
	        if ((getAttributesOfWEPPulsesCreationPage("contentpreviewbackEnabled", "disabled")==null)&&(getAttributesOfWEPPulsesCreationPage("contentpreviewfarwardenabled", "disabled").equals("true"))) {
	            LOGGER.info("Content forward button got disabled and verified at the max content count reach");            
	            // Backward content preview
	            for (int i = maxContentQuesCount; i > 1; i--) {
	                clickByActionsClickWEPPulsesCreationPage("contentpreviewbackEnabled");
	                verifyElementsOfWEPPulsesCreationPage("contentQuestionSections");
	            }
	            LOGGER.info("Clicked on max backward preview button and verified the content sections");   
	            if (verifyElementsOfWEPPulsesCreationPage("contentpreviewfarwardenabled")) {
	                LOGGER.info("Content backward button got disabled and verified at the min content count reach");
	                flag = true;
	            } else {
	                LOGGER.info("Content backward button not disabled even after max backward clicks");
	                //flag = false;
	            }
	            }
	        else {
		        LOGGER.info("Content backward button is not verified");
		        //flag = false;
	        }
	    } else {
	        LOGGER.info("Content backward button is disabled / forward button is enabled still");
	        flag = false;
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception occurred in method verifyContentSectionOnSentimentPulsesType: " + e.getMessage());
	    return false;
	}
	return flag;
}

/**
 * @param TotalActiveDevices
 * @param TotalTargetedDevices
 * @return
 */
public boolean verifyAudienceSectionValidationsEveryoneMethodApplied(Integer TotalActiveDevices, Integer TotalTargetedDevices) {
	boolean flag = false;
	try {
	    // Verify necessary elements are present
	    if (verifyElementsOfWEPPulsesCreationPage("reachHeaderText") &&
	    		verifyElementsOfWEPPulsesCreationPage("deviceCount") &&
	    			verifyElementsOfWEPPulsesCreationPage("ReachNoteText")) {      
	        String potentialAudienceCount = getTextOfWEPPulsesCreationPage("deviceCount");
	        LOGGER.info("Potential Audience Count for the selected filter is " + potentialAudienceCount);  
	        // Check if the audience count matches the expected values
	        if (potentialAudienceCount.equals(TotalTargetedDevices + " out of " + TotalActiveDevices+" enrolled devices")) {
	            LOGGER.info("Verified the audience count for everyone and it gets matched");
	            flag = true; // Set flag to true if counts match
	        } else {
	            LOGGER.error("Audience count not matched, hence marking this method as failed");
	        }
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception occurred in method verifyAudienceSectionValidationsEveryoneMethodApplied: " + e.getMessage());
	    return false; // Return false if an exception occurs
	}
	return flag; // Return the final status
}

/**
 * @param fieldInputValue - Field input value for TC
 * @param operatorInputValue - operator input value for TC
 * @param valueInputValue - value input value for TC
 * @param TotalActiveDevices - Expected Active devices count under the tenant
 * @param TotalAudience - Audience count after applying the respective filters from the devices list page
 * @return
 */
public boolean verifyAudienceSectionValidationsApplyFiltersDynamicMethod(String fieldInputValue, String operatorInputValue, String valueInputValue,Integer TotalActiveDevices, Integer TotalAudience) {
	boolean flag = false;
	try {
		if(fieldInputValue!="Entra ID groups") {
	    // Verify necessary elements
	    verifyElementsOfWEPPulsesCreationPage("filterHeaderText");
	    LOGGER.info("filterHeaderText Verified Successfully");
	    verifyElementsOfWEPPulsesCreationPage("FieldText");
	    LOGGER.info("field text Verified Successfully");
	    verifyElementsOfWEPPulsesCreationPage("OperatorText");
	    LOGGER.info("operator text Verified Successfully");
	    verifyElementsOfWEPPulsesCreationPage("ValueText");
	    LOGGER.info("value text Verified Successfully");	    
	    // Handle field dropdown
	    clickByActionsClickWEPPulsesCreationPage("fieldDropDown");
	    LOGGER.info("fieldDropDown clicked Successfully");
	    sleeper(5000);
	    selectFromDropdown("FieldDropDownOptions", fieldInputValue);
	    // Handle operator dropdown
	    clickByActionsClickWEPPulsesCreationPage("OperatorDropDown");
	    LOGGER.info("operatorDropDown clicked Successfully");
	    sleeper(5000);
	    selectFromDropdown("OperatorDropDownOptions", operatorInputValue);
	    // Handle value dropdown
	    verifyElementsOfWEPPulsesCreationPage("ValueDropDown");
	    LOGGER.info("valueDropDown Verified Successfully");
	    clickByActionsClickWEPPulsesCreationPage("ValueDropDown");
	    LOGGER.info("valueDropDown clicked Successfully");
	    if (fieldInputValue.equals(WEPPulsesCreationPageVariables.PULSE_FILTERS_FIELD_DEVICE_TYPE)) {
	        selectFromDropdown("deviceTypeValues", valueInputValue);
	    } else {
	        verifyElementsOfWEPPulsesCreationPage("ValueDropDownSearchBox");
	        LOGGER.info("ValueDropDownSearchBox Verified Successfully");
	        clickByActionsClickWEPPulsesCreationPage("ValueDropDownSearchBox");
	        LOGGER.info("ValueDropDownSearchBox clicked Successfully");
	        enterTextForWEPPulsesCreationPage("ValueDropDownSearchBox", valueInputValue);
	        LOGGER.info("searched required value from valueDropDown");
	        sleeper(2000);
	        verifyElementsOfWEPPulsesCreationPage("ValueDropDownOptions");
	        LOGGER.info("ValueDropDownOptions Verified Successfully");
	        clickByActionsClickWEPPulsesCreationPage("ValueDropDownOptions");
	        LOGGER.info("ValueDropDownOptions clicked Successfully");
	    }
	    sleeper(2000);
	    // Robot to simulate ESC key press
	    Robot robot = new Robot();
	    robot.keyPress(KeyEvent.VK_ESCAPE);
	    robot.keyRelease(KeyEvent.VK_ESCAPE);
		}else {
			verifyElementsOfWEPPulsesCreationPage("entraIDdropDownHeader");
			clickByActionsClickWEPPulsesCreationPage("entraIDgroupsdropDown");
			enterTextForWEPPulsesCreationPage("groupsSearchEntraID",valueInputValue);
			clickByActionsClickWEPPulsesCreationPage("firstOptionSelection");
		}
	    // Verify and click recalculate button
	    verifyElementsOfWEPPulsesCreationPage("RecalculateButtonEnabled");
	    LOGGER.info("recalculateButtonEnabled Verified Successfully");
	    clickByActionsClickWEPPulsesCreationPage("RecalculateButtonEnabled");
	    LOGGER.info("recalculateButton clicked");
	    waitForElementsOfWEPPulsesCreationPage("countText");
	    verifyElementsOfWEPPulsesCreationPage("countText");
	    LOGGER.info("Verified countText");
	    verifyElementsOfWEPPulsesCreationPage("reachHeaderText");
	    LOGGER.info("Verified reachHeaderText");

	    // Check audience count based on operator input value
	    String actualAudienceCount = getTextOfWEPPulsesCreationPage("countText");
	    LOGGER.info("Potential Audience Count for the selected filter is " + actualAudienceCount);
	    
	    if ("Not equal to".equals(operatorInputValue)) {
	        int expectedAudienceCount = TotalActiveDevices - TotalAudience;
	        flag = actualAudienceCount.equals(expectedAudienceCount + " out of " + TotalActiveDevices + " enrolled devices");
	    } else {
	        flag = actualAudienceCount.equals(TotalAudience + " out of " + TotalActiveDevices + " enrolled devices");
	    }

	    if (flag) {
	        LOGGER.info("Verified the Audience count and it gets matched");
	    } else {
	        LOGGER.error("Audience count not matched");
	    }
	    verifyElementsOfWEPPulsesCreationPage("ReachNoteText");
	    LOGGER.info("Reach section got verified with actual reach count & description after applying filters");
	} catch (Exception e) {
	    LOGGER.error("Exception occurred in method verifyAudienceSectionValidationsApplyFiltersOnSentimentPulseType: " + e.getMessage());
	    return false;
	}
	return flag;
}



public boolean verifyAudienceSectionValidationsEntraIDMethod(String GroupsValue,Integer TotalActiveDevices, Integer TotalAudience) {
	boolean flag = false;
	try {
		verifyElementsOfWEPPulsesCreationPage("entraIDdropDownHeader");
		clickByActionsClickWEPPulsesCreationPage("entraIDgroupsdropDown");
		enterTextForWEPPulsesCreationPage("groupsSearchEntraID",GroupsValue);
		clickByActionsClickWEPPulsesCreationPage("firstOptionSelection");
		sleeper(2000);
		// Robot to simulate ESC key press
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
	    // Verify and click recalculate button
	    verifyElementsOfWEPPulsesCreationPage("RecalculateButtonEnabled");
	    LOGGER.info("recalculateButtonEnabled Verified Successfully");
	    clickByActionsClickWEPPulsesCreationPage("RecalculateButtonEnabled");
	    LOGGER.info("recalculateButton clicked");
	    verifyElementsOfWEPPulsesCreationPage("reachHeaderText");
	    LOGGER.info("Verified reachHeaderText");
	    verifyElementsOfWEPPulsesCreationPage("countText");

	    // Check audience count based on groups input value
	    String actualAudienceCount = getTextOfWEPPulsesCreationPage("countText").replace(",","");
	    LOGGER.info("Potential Audience Count for the selected filter is " + actualAudienceCount);
	    flag = actualAudienceCount.equals(TotalActiveDevices  + " out of " + TotalAudience);
	    if (flag) {
	        LOGGER.info("Verified the Audience count and it gets matched");
	    } else {
	        LOGGER.error("Audience count not matched");
	    }
	    verifyElementsOfWEPPulsesCreationPage("ReachNoteText");
	    LOGGER.info("Reach section got verified with actual reach count & description after applying filters");
	} catch (Exception e) {
	    LOGGER.error("Exception occurred in method verifyAudienceSectionValidationsEntraIDMethod: " + e.getMessage());
	    return false;
	}
	return flag;
}

	/**
	 * Helper method to select an item from a drop-down
	 * @param dropdownOptions
	 * @param inputValue
	 * @throws Exception
	 */
	public void selectFromDropdown(String dropdownOptions, String inputValue) throws Exception {
	    List<WebElement> optionsList = getElementsTillAllElementsVisibleofPulseCreationpage(dropdownOptions);
	    for (WebElement option : optionsList) {
	    	String acttext = option.getText();
	        if (inputValue.equals(acttext)) {
	        	clickWebelement(option);
	            //sleeper(2000);
	            LOGGER.info("Selected required option from " + dropdownOptions);
	            break;
	        }
	    }}

/**
 * This is a method to get a list of elements present on pulse creation page
 * @param key - Locator of element
 * @return - list of web elements
 */
public final List<WebElement> getElementsTillAllElementsVisibleofPulseCreationpage(String key) {
	try {
		return getElementsTillAllElementsPresent(WEPPulsesCreationPagePageProperties.getProperty(key));
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofPulseCreationpage " + e.getMessage()));
		return null;
	}
}

public boolean verifyAllElementsinWEPPulsesCreationPage(String[] allElements) {
    for (String element : allElements) {
        if (verifyElementsOfWEPPulsesCreationPage(element)==false) {
            return false; // Return false immediately if any element verification fails
        }
    }
    return true; // All elements verified successfully
}

/**
 * Default filter check
 */
public final void defaultFilterCheck (String PulseType) {
	String[] allElements = {"reachHeaderText","addFilterButton","undeterminedStateText","ReachNoteText","progressBarDynamic","RecalculateButtonEnabled"};
	verifyAllElementsinWEPPulsesCreationPage(allElements);
	LOGGER.info("Reach section got verified with actual reach count & description before applying filters");
	
}


/**
 * 
 * @param questionType
 * @param questionInputDate
 * @return
 * @throws Exception
 */
public List<String> verifyContentSectionQuestionsSelectionCustomPulseType(String questionType, String questionInputDate) throws Exception {
    List<String> providedInput = new ArrayList<String>();
     // Generate random strings
//    String Qexcesslimit = generateRandomString(256);
//    String Optionexcesslimit = generateRandomString(129);

    // Verify initial UI elements
    String[] allElements = {"newQuestionsButton", "newNotificationButton", "questionnotificationTypeHeader", "newQuestionnotificationSection"};
    verifyAllElementsinWEPPulsesCreationPage(allElements);
    LOGGER.info("Initial UI elements verified successfully");

    // Click on new questions button
    clickByActionsClickWEPPulsesCreationPage("newQuestionsButton");
    LOGGER.info("New Question section clicked Successfully");

    // Select question type from dropdown
    selectQuestionType(questionType);
    
    // Handle different question types using a map-based approach for better scalability
    switch (questionType) {
        case "Multi-select":
        case "Single-select":
            providedInput = handleMultiSingleSelect(questionInputDate, questionType);
            break;
        case "5-star rating":
        case "Thumbs up":
        case "Comment box":
            providedInput = handleTextQuestionTypes(questionInputDate, questionType);
            break;
        case "Net promoter score":
            providedInput = handleNPSQuestionType();
            break;
        case "Matrix rating":
            providedInput = handleMatrixQuestionTypes(questionInputDate, questionType);
            break;
        default:
            LOGGER.info("Provided option does not match any question type!");
            break;
    }
    return providedInput;
}

/**
 * This method is to handle the Question types by providing the Input values and holds all the input data.
 * @param questionInputDate
 * @param questionType
 * @return
 * @throws Exception
 */
private List<String> handleTextQuestionTypes(String questionInputDate, String questionType) throws Exception {
    List<String> providedInput = new ArrayList<String>();
    String questionText = questionInputDate + " Automation " + questionType;  
    // Custom question input texts for each question type
    if (questionType.equals("5 star rating")) {
        questionText += WEPPulsesCreationPageVariables.FIVE_STAR_RATING_QUESTION_TEXT;
    } else if (questionType.equals("Thumbs up")) {
        questionText += WEPPulsesCreationPageVariables.THUMBS_UP_QUESTION_TEXT;
    } else if (questionType.equals("Comment box")) {
        questionText += WEPPulsesCreationPageVariables.COMMENT_BOX_QUESTION_TEXT;
    }
    // Enter and store the input
    clickByActionsClickWEPPulsesCreationPage("contentSectionTab");
    sleeper(2000);
    enterTextForWEPPulsesCreationPage("questionInputField", questionText);
    providedInput.add(getTextOfWEPPulsesCreationPage("questionInputField"));
    return providedInput;
}

/**
 * @return
 * @throws Exception
 */
private List<String> handleNPSQuestionType() throws Exception {
    List<String> providedInput = new ArrayList<String>();
    // Verify NPS specific elements
    String[] npsAllElements = {"npsQuestionHeader", "npsQuestionText", "npsAnswerInput"};
    verifyAllElementsinWEPPulsesCreationPage(npsAllElements);
    // Input NPS answer and handle possible char limit error
    enterTextForWEPPulsesCreationPage("npsAnswerInput", generateRandomString(101));
    if (verifyElementsOfWEPPulsesCreationPage("maxAOCharLimitError")) {
        enterTextForWEPPulsesCreationPage("npsAnswerInput", "Automation - Workforce Experience");
        providedInput.add(getTextOfWEPPulsesCreationPage("npsContent"));
    }
    
    return providedInput;
}





    private List<String> handleMatrixQuestionTypes(String questionInputDate, String questionType) throws Exception {
        List<String> providedInput = new ArrayList<String>();
        String questionText = questionInputDate + " Automation " + questionType;
        // Custom question input texts for each question type
        if (questionType.equals("Matrix rating")) {
            questionText += WEPPulsesCreationPageVariables.Matrix_rating_QUESTION_TEXT;
        }
        // Enter and store the input
        enterTextForWEPPulsesCreationPage("questionInputField", questionText);
        enterTextForWEPPulsesCreationPage("chooseOptionone", "A");
        enterTextForWEPPulsesCreationPage("chooseOptiontwo", "B");
        enterTextForWEPPulsesCreationPage("chooseOptionthree", "C");


        providedInput.add(getTextOfWEPPulsesCreationPage("questionInputField"));
        return providedInput;
    }


/**
 * @param questionType
 * @throws Exception
 */
private void selectQuestionType(String questionType) throws Exception {
    clickByActionsClickWEPPulsesCreationPage("questionTypeSelectionDD");
    LOGGER.info("Choose option for question got clicked Successfully");
    sleeper(2000);
    List<WebElement> fieldlist = getElementsTillAllElementsVisibleofPulseCreationpage("questionList");
    for (WebElement field : fieldlist) {
        if (questionType.equals(field.getText())) {
            field.click();
            break;
        }
    }
}





/**
 * This method is handle MultiSingle Select Question
 * @param questionInputDate
 * @param Optionexcesslimit
 * @param QuestionType
 * @return
 * @throws Exception
 */
private List<String> handleMultiSingleSelect(String questionInputDate, String QuestionType) throws Exception {
    int maxAllowedSingleMultiOptionLimit = 3;
    List<String> providedInput = new ArrayList<>();
    enterTextForWEPPulsesCreationPage("questionInputField", questionInputDate + " Automation " + QuestionType + " test Please select the respective Option");
    providedInput.add(getTextOfWEPPulsesCreationPage("questionInputField"));
    // Handle first two mandatory options with excess limit check
    providedInput = handleOptionWithExcessLimitCheck("option1MultiSingleSelect", QuestionType, "001", providedInput);
    providedInput = handleOptionWithExcessLimitCheck("option2MultiSingleSelect", QuestionType, "002", providedInput);

    // Handle options 3 to maxAllowedSingleMultiOptionLimit
    for (int i = 3; i <= maxAllowedSingleMultiOptionLimit; i++) {
        String optionInput = QuestionType + "-option test option 00" + i;
       
        enterTextForWEPPulsesCreationPage("optionlastMultiSingleSelect", optionInput);
        LOGGER.info("Option text entered for option " + i);
        providedInput.add(getAttributesOfWEPPulsesCreationPage("optionlastMultiSingleSelect", "value"));
        // Handle adding options dynamically if within allowed limit
        if (i <= maxAllowedSingleMultiOptionLimit) {
     //       clickByActionsClickWEPPulsesCreationPage("addMultiSingleOption");
            LOGGER.info("Clicked on add option for option " + i + " added successfully");
        } else {
            handleAddOptionButtonVisibility();
        }
    }
    return providedInput;
}

/**
 * Method to handle options with excess limit check
 * @param optionField
 * @param QuestionType
 * @param optionNumber
 * @param providedInput
 * @return
 * @throws Exception
 */
private List<String> handleOptionWithExcessLimitCheck(String optionField, String QuestionType, String optionNumber, List<String> providedInput) throws Exception {
    enterTextForWEPPulsesCreationPage(optionField, QuestionType + "-option test option " + optionNumber);
    providedInput.add(getAttributesOfWEPPulsesCreationPage(optionField, "value"));
    return providedInput;
}

/**
 * Method to handle visibility of the add option button
 */
private void handleAddOptionButtonVisibility() {
    if (!verifyElementsOfWEPPulsesCreationPage("addMultiSingleOption")) {
        LOGGER.info("Add option button not displayed after max allowed option limit reached");
    } else {
        LOGGER.info("Add option button still displayed even after max allowed option limit reached");
    }
}


private boolean checkMaxCharLimitError() {
    return verifyElementsOfWEPPulsesCreationPage("maxAOCharLimitError");
}

/**
 * This method will provide every text for an element in list
 * @param key
 * @return
 * @throws Exception
 */
public final List<String> getAllTextOfPulseCreationPage(String key) throws Exception {
	return getallTextBy(WEPPulsesCreationPagePageProperties.getProperty(key));
}

/**
 * This method will provide every attribute value as a text for an element in list
 * @param key
 * @return
 * @throws Exception
 */
public final List<String> getAllAttributeTextOfPulseCreationPage(String key, String Value) throws Exception {
	return getAllAttributes(WEPPulsesCreationPagePageProperties.getProperty(key),Value);
}

public final String getspecificIndexInputTextOfPulseCreationPage(String key, int index) throws Exception {
	return getTextBy(WEPPulsesCreationPagePageProperties.getProperty(key) + "[position()=" + index + "]");
}


/**
 * This method is used to verify the Content after duplication is done successfully
 * @param pulseTitle
 * @param duplicateOption
 * @return boolean indicating success or failure
 * @throws Exception 
 */
public boolean verifyContentSectionAfterDuplicateOrSavedDraftCustomPulseFunctionality(List<String> providedData,Integer index) throws Exception {
    boolean flag = false;  
    try {
    	List<String> afterDupList = new ArrayList<String>();
    	String questionTypeDuplication = getspecificIndexInputTextOfPulseCreationPage("totalNoQuestionsDuplication",index);
    		// Handle different question types
          switch (questionTypeDuplication) {
              case "Multi-select":
            	  afterDupList.add(getspecificIndexInputTextOfPulseCreationPage("totalQuestionsInputSection",index));
            	  afterDupList.addAll(getAllAttributeTextOfPulseCreationPage("MultitotalOptionsInputSection","value"));  
            	  break;
              case "Single-select":
            	  afterDupList.add(getspecificIndexInputTextOfPulseCreationPage("totalQuestionsInputSection",index));
            	  afterDupList.addAll(getAllAttributeTextOfPulseCreationPage("SingletotalOptionsInputSection","value"));
            	  break;
              case "5 star rating":
            	  afterDupList.add(getspecificIndexInputTextOfPulseCreationPage("totalQuestionsInputSection",index));
                  break;
              case "Thumbs up":
            	  afterDupList.add(getspecificIndexInputTextOfPulseCreationPage("totalQuestionsInputSection",index));          	  
            	  break;
              case "Comment box":
            	  afterDupList.add(getspecificIndexInputTextOfPulseCreationPage("totalQuestionsInputSection",index));           	  
                  break;
              case "Net promoter score":
            	  afterDupList.add(getTextOfWEPPulsesCreationPage("npsContent"));           	  
                  break;

    	}     
          if(providedData.equals(afterDupList)) {
    		  flag = true;
    	  }
    } catch (Exception e) {
        LOGGER.error("Exception occurred in method verifyContentSectionAfterDuplicatePulseTypeFunctionality: " + e.getMessage());
        return false;
    }
    return flag;
}

/**
 * This method is used to verify the Content before/after redirection is done successfully
 * @param AudienceMethod
 * @return <List>String provided value / redirected value present
 * @throws Exception 
 */
public List<String> getInputsforAudienceMethodBeforeAndAfterRedirection(String AudienceMethod) throws Exception {
    	List<String> afterDupList = new ArrayList<String>();  	
    	 switch (AudienceMethod) {
    	 case "Entra-ID":
    		 afterDupList.add(getTextOfWEPPulsesCreationPage("entraIDselectedGroup"));
    		 afterDupList.add(getTextOfWEPPulsesCreationPage("countText"));
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("ReachNoteText"));
        	 if(verifyElementsOfWEPPulsesCreationPage("RecalculateButtonDisabled")==true) {
        		 afterDupList.add("Button Disabled");
        	 }
        	 break;
    	 case "Dynamic":
    		 sleeper(2000);
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("countText"));
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("ReachNoteText"));
        	 if(verifyElementsOfWEPPulsesCreationPage("RecalculateButtonDisabled")==true) {
        		 afterDupList.add("Button Disabled");
        	 }
        	 break;
         case "Static":
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("sucessFileName"));
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("uploadStatusText"));
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("deviceCount"));
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("audiencetextbasedOnScheduleMethodselection"));
        	 break;
         case "Everyone":
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("deviceCount"));
        	 afterDupList.add(getTextOfWEPPulsesCreationPage("ReachNoteText"));
        	 break;
    	 }
		return afterDupList;
}

	/**
	 * This method is used to redirect to tab and verify the redirection is done successfully
	 * @param tabRedirection
	 * @return boolean indicating success or failure
	 * @throws Exception
	 */
public boolean leaveRedirectionDraftPulsesCreationPage(String tabRedirection) {
	boolean flag = false;
	try {
		clickByActionsClickWEPPulsesCreationPage("pulseBreadCrumbs");
		clickByActionsClickWEPPulsesCreationPage("leavePage");
		waitForPageLoaded();
		sleeper(2000);
		clickByActionsClickWEPPulsesCreationPage("firstLatestPulselist");
		waitForPageLoaded();
		waitForElementsOfWEPPulsesCreationPage(tabRedirection);
		if (verifyElementsOfWEPPulsesCreationPage(tabRedirection)) {
			clickByActionsClickWEPPulsesCreationPage(tabRedirection);
		LOGGER.info("Redirection is successfull on " + tabRedirection + " tab");
			flag = true;
		}
	} catch (Exception e) {
		LOGGER.error("Exception occurred in method redirectionDraftPulsesCreationPage: " + e.getMessage());
		return false;
	}
	return flag;
}

	public void scrollinDropdown(String dropdownLocator, String direction) throws Exception {
		WebElement dropdown = getElement(dropdownLocator);
	    if (direction.equalsIgnoreCase("down")) {
	        dropdown.sendKeys(Keys.ARROW_DOWN);
	    } else if (direction.equalsIgnoreCase("up")) {
	        dropdown.sendKeys(Keys.ARROW_UP);
	    }

	}
}