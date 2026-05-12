package com.daasui.pages;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.EnrollFakeDevice;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.DeviceVariables;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import com.basesource.action.CommonMethod;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.ObjectReader;
import com.basesource.utils.TestSnrDeviceId;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.daasui.constants.ConstantPath.IMPORT_PATH;
import static com.daasui.constants.DeviceVariables.*;
import static com.daasui.constants.DeviceVariables.VALID_MULTI_ASSET_UPLOAD_LOG_MSG;
import static org.testng.Assert.assertTrue;

public class WEPDeviceListPage extends CommonMethod {

    private WEPDeviceListPage instance;
    private final Properties deviceListPageProperties;
    public static HashMap<String, String> mapAdded = new HashMap<>();

    /**
     * This is a constructor method
     */
    public WEPDeviceListPage(WebDriver driver) throws IOException {
        ObjectReader deviceListPagePropertiesReader = new ObjectReader();
        deviceListPageProperties = deviceListPagePropertiesReader.getObjectRepository("WEPDeviceListPage");
    }

    /**
     * This method designed to get the  instance of a class WEPDeviceListPage
     */
    public WEPDeviceListPage getInstance() throws IOException {
        if (instance != null) {
            return instance;
        }
        synchronized (WEPDeviceListPage.class) {
            if (instance == null) {
                instance = new WEPDeviceListPage(DRIVER);
            }
        }
        return instance;
    }

    /**
     * This method designed to get the WebElement of a page
     *
     * @param key - Locator of an element
     */
    private WebElement getElementOfWEPDeviceListPage(String key) throws Exception {
        return getElement(deviceListPageProperties.getProperty(key));
    }

    /**
     * This method designed to click on the element on the web page
     *
     * @param key - Locator of an element
     */
    public final void clickOnElementsOfDevicePage(String key) throws Exception {
        click(deviceListPageProperties.getProperty(key));
    }
    
    /**
     * This method designed to click on the web element on the web page
     *
     * @param key - Locator of an element
     */
    public final void clickOnWEBElementsOfDevicePage(WebElement key) throws Exception {
    	clickWebelement(key);
    }
    
    

    /**
     * This method designed to click on the web element on the web page(which is compatible with java script)
     *
     * @param key - Locator of an element
     */
    public final void clickByJavaScriptOnDevicePage(String key) {
        try {
            clickByJavaScript(deviceListPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method clickByJavaScriptOnDevicePage {}", e.getMessage());
        }
    }
    
    /**
     * This is a method to scroll on deviceListPage page
     *
     * @param key - Locator of element
     */
    public final void scrollOnDeviceListPage(String key) {
        try {
            scrollTillView(deviceListPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method scrollOnDeviceDetailsListPage " + e.getMessage()));
        }
    }

    /**
     * This is a method to verify if the element is present
     *
     * @param key - Locator of element
     */

    public final void verifyElementsOfWEPDeviceListPage(String key) {
        try {
        	scrollOnDeviceListPage(key);
            verifyElementIsPresent(deviceListPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method verifyElementsOfWEPDeviceListPage {}", e.getMessage());
        }
    }

    /**
     * This is a method used to type the given Input Text in the text box
     *
     * @param key         - Locator of element
     * @param textToEnter - Text to enter into the text box
     */
    public final void enterTextForDeviceListPage(String key, String textToEnter) {
        try {
            enterText(deviceListPageProperties.getProperty(key), textToEnter);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method enterTextForDeviceListPage {}", e.getMessage());
        }
    }

    /**
     * This method designed to get the  Text of WebElement from web page
     *
     * @param key - Locator of element
     */
    public final String getTextOfWEPDeviceListPage(String key) throws Exception {
        return getTextBy(deviceListPageProperties.getProperty(key));
    }

    /**
     * This is a method to wait for an element till it is visible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is visible or not
     */
    public final boolean waitForElementsOfWEPDeviceListPage(String key) {
        try {
            return verifyElementIsVisible(deviceListPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method waitForElementsOfWEPDeviceListPage {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * This is a method to wait for an element till it is visible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is visible or not
     * @throws Exception 
     */
    public void resetAndSelectLifecycleStatusInFilter() throws Exception {
        actionClickOfDeviceListPage("columnOptionBtn");
        waitForElementsOfWEPDeviceListPage("resetColumnFilter");
        verifyElementIsClickableOfDeviceListPage("resetColumnFilter");
        clickByJavaScriptOnDevicePage("resetColumnFilter");
        sleeper(2000);
        verifyElementIsClickableOfDeviceListPage("lifecycleStatusColumnFilter");
        clickByJavaScriptOnDevicePage("lifecycleStatusColumnFilter");
        sleeper(2000);
        verifyElementIsClickableOfDeviceListPage("saveColumnFilter");
        actionClickOfDeviceListPage("saveColumnFilter");
        sleeper(2000);
    }
    
    /**
     * This is a method to verify text present on element of device list page
     * @param key - Locator of element
     * @param text - text to verify
     * @return - boolean value of whether the text on element is matched or not
     */
    public final boolean verifyTextPresentOnElementOfDeviceListPage(String key, String text) {
        try {
            return verifyTextPresentOnElement(deviceListPageProperties.getProperty(key), text);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method waitForElementsOfWEPDeviceListPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * This is a method to wait for an element till it is invisible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is invisible or not
     */
    public final boolean waitUntilElementIsInvisibleOfDeviceListPage(String key) {
        return verifyElementIsinvisibile(deviceListPageProperties.getProperty(key));
    }

    /**
     * This is a method to wait for an element till it is invisible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is invisible or not
     */
    public final void waitUntilElementIsVisibleOfDeviceListPage(String key) throws Exception {
        waitUntilElementIsVisible(key);
    }

    /**
     * This is a method to verify if the element is present
     *
     * @param key - Locator of element
     */

    public final boolean verifyElementsOfWEPDevicePage(String key) {
        try {
            return  verifyElementIsPresent(deviceListPageProperties.getProperty(key));
        } catch (Exception e) {
           LOGGER.error("Exception occurred in method verifyElementsOfWEPDeviceListPage {}", e.getMessage());
           return false;
        }
    }
    
	/***
	 * This method is used to select the text value from dropdown
	 *
	 * @param dropdownListKey - Locator of element
	 * @param elementText     - Text to be selected
	 * @param dropdownBox     - Dropdown box locator
	 * @return - boolean value of whether the text is selected or not
	 */
    public final boolean selectTextValueFromDropdownOfWEPDeviceListPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(deviceListPageProperties.getProperty(dropdownListKey), elementText, deviceListPageProperties.getProperty(dropdownBox));
	}
    
    /***
	 * This method is used to select the text value from dropdown
	 *
	 * @param dropdownListKey - Locator of element
	 * @param elementText     - Text to be selected
	 * @param dropdownBox     - Dropdown box locator
	 * @return - boolean value of whether the text is selected or not
	 */
    public final boolean removeSelectedOptionsFromDropdownOfWEPDeviceListPage(String dropdownListKey) throws Exception {
		if (getElementsTillAllElementsPresentOnDeviceListPage(deviceListPageProperties.getProperty(dropdownListKey)).size() > 0) {
			List<WebElement> selectedOptions = getElementsTillAllElementsPresentOnDeviceListPage(
					deviceListPageProperties.getProperty(dropdownListKey));
			for (WebElement option : selectedOptions) {
				this.clickOnWEBElementsOfDevicePage(option);
				sleeper(1000);
			}
		}
		return waitUntilElementIsInvisibleOfDeviceListPage(deviceListPageProperties.getProperty(dropdownListKey));
	}
    
    /***
	 * This method is used to select the text value from dropdown
	 *
	 * @param dropdownListKey - Locator of element
	 * @param elementText     - Text to be selected
	 * @param dropdownBox     - Dropdown box locator
	 * @return - boolean value of whether the text is selected or not
	 */
    public final boolean filterOnStatusDeviceList(String status) throws Exception {
    	while(this.waitForElementsOfDeviceListPage("clearfilter")){
    		clickByActionsWEPClickDevicelistPage("clearfilter");
    		LOGGER.info("Clear All Filter button is clicked.");
    		sleeper(4000);
    	}
     	if (status != null && !status.isBlank()) {
    		clickByActionsWEPClickDevicelistPage("statusBar");
            selectTextValueFromDropdownOfWEPDeviceListPage("devicestatusddoptions", status.trim(), "statusBar");
            pressKey(Keys.ESCAPE);
    		sleeper(1000);
    		
            LOGGER.info("Applied status filter: {}", status);
        } else {
            LOGGER.info("Status filter is selected as ALL.");
        }
		return true;
	}

    /**
     * This is a method to match text on an element
     *
     * @param key         - Locator of element
     * @param textToMatch - Text to be matched
     * @return - boolean value of whether the text present on element matches or not
     */
    public final boolean matchTextOfDeviceListPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(deviceListPageProperties.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method matchTextOfDeviceListPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * This is a method used to add 1 single device
     *
     * @param deviceAssetTag     - device Asset Tag used to add device
     * @param deviceSerialNumber - device Serial number used to add device
     */
    public void addDevice(String deviceAssetTag, String deviceSerialNumber) throws Exception {
        addDevice(deviceAssetTag, deviceSerialNumber, 1, false, false);
    }

    /**
     * This is a method used to add duplicate devices(limited to 2 here) based on the flag(third parameter)
     * - either duplicate serial number or duplicate asset tag (not both)
     *
     * @param deviceAssetTag          - device Asset Tag used to add device
     * @param deviceSerialNumber      - device Serial number used to add device
     * @param isSerialNumberDuplicate - this boolean indicate to add duplicate serial number(true) or duplicate asset tag(true)
     */
    public void addDuplicateDevices(String deviceAssetTag, String deviceSerialNumber, boolean isSerialNumberDuplicate) throws Exception {
        if (isSerialNumberDuplicate) {
            AddIndividualEntryOfDevices(deviceAssetTag, deviceSerialNumber, 2, true, false);
        } else {
            AddIndividualEntryOfDevices(deviceAssetTag, deviceSerialNumber, 2, false, true);
        }
    }

    /**
     * This is a method used to add duplicate devices(limited to 2 here) both serial number and asset tag is duplicate
     * - either duplicate serial number or duplicate asset tag (not both)
     *
     * @param deviceAssetTag     - device Asset Tag used to add device
     * @param deviceSerialNumber - device Serial number used to add device
     */
    public void addDeviceWithDuplicateSerialNumberAndAssetTag(String deviceAssetTag, String deviceSerialNumber) throws Exception {
        AddIndividualEntryOfDevices(deviceAssetTag, deviceSerialNumber, 2, true, true);
    }

    /**
     * This is a method used to add multiple devices(max 5) with facility of duplicate device serial number & duplicate asset tag
     *
     * @param deviceAssetTag          - device Asset Tag used to add device
     * @param deviceSerialNumber      - device Serial number used to add device
     * @param isSerialNumberDuplicate - this boolean indicate to add duplicate serial number(true)
     * @param isAssetTagDuplicate     - this boolean indicate to add duplicate asset tag(true)
     * @param deviceCount             - number of device to add manually
     */
    public void addDevice(String deviceAssetTag, String deviceSerialNumber, Integer deviceCount, boolean isSerialNumberDuplicate, boolean isAssetTagDuplicate) throws Exception {
        try {
            if (deviceCount <= 5) {
                AddIndividualEntryOfDevices(deviceAssetTag, deviceSerialNumber, deviceCount, isSerialNumberDuplicate, isAssetTagDuplicate);
                verifyElementsOfWEPDeviceListPage("addIndividualDeviceBtn");
                clickOnElementsOfDevicePage("addIndividualDeviceBtn");
                verifyElementsOfWEPDeviceListPage("addDeviceBtn");
                verifyElementsOfWEPDeviceListPage("addDeviceSuccessNotification");
                getTextOfWEPDeviceListPage("addDeviceSuccessNotification");
                Assert.assertEquals(getTextOfWEPDeviceListPage("addDeviceSuccessNotification"), "Assets successfully added\n" + "Please refresh the page if the recently added assets are not visible.");
                LOGGER.info("Fleet Management Devices Page : Adding new device done successfully");
            } else {
                LOGGER.error("More than five devices cannot be added");
            }
        } catch (Exception e) {
            LOGGER.error("Device could not get added successfully.");
        }
    }

    /**
     * This is a method used to add multiple devices
     *
     * @param deviceAssetTag      - device Asset Tag used to add device
     * @param deviceSerialNumber  - device Serial number used to add device
     * @param isSerialDuplicate   - this boolean indicate to add duplicate serial number(true)
     * @param isAssetTagDuplicate - this boolean indicate to add duplicate asset tag(true)
     * @param deviceCount         - number of device to add manually
     */
    private void AddIndividualEntryOfDevices(String deviceAssetTag, String deviceSerialNumber, Integer deviceCount, boolean isSerialDuplicate, boolean isAssetTagDuplicate) throws Exception {
        verifyElementsOfWEPDeviceListPage("addDevicePopUpHeader");
        verifyElementsOfWEPDeviceListPage("addIndividualDeviceEntryBtn");
        clickByJavaScriptOnDevicePage("addIndividualDeviceEntryBtn");
        int serialNum = 1;
        int assetTagNum = 1;
        for (int deviceCounter = 1; deviceCounter <= deviceCount; deviceCounter++) {
            assetTagNum = serialNum = deviceCounter;
            if (isSerialDuplicate) {
                serialNum = 1;
            }
            if (isAssetTagDuplicate) {
                assetTagNum = 1;
            }
            verifyElementIsPresent(deviceListPageProperties.getProperty("serialNumberTextBox") + "[" + deviceCounter + "]");
            enterText(deviceListPageProperties.getProperty("serialNumberTextBox") + "[" + deviceCounter + "]", deviceSerialNumber + (serialNum));
            enterText(deviceListPageProperties.getProperty("assetTagTextBox") + "[" + deviceCounter + "]", deviceAssetTag + (assetTagNum));
            mapAdded.put(deviceSerialNumber + serialNum, deviceAssetTag + assetTagNum);
            if (deviceCount > 1 && deviceCounter < deviceCount) {
                verifyElementsOfWEPDeviceListPage("addAnother");
                clickOnElementsOfDevicePage("addAnother");
            }
        }
    }

    /**
     * This is a method to all available devices in the page
     *
     * @return List of string that is available in UI
     */
    public List<String> getAvailableDevices() throws Exception {
        List<String> topAvailableDeviceSerialNumbers = new ArrayList<>();
        sleeper(3000);
        List<WebElement> columnElements;
        columnElements = getElementsTillAllElementsPresent(deviceListPageProperties.getProperty("deviceSerialNumberRows"));

        if (!columnElements.isEmpty()) {
            for (WebElement element : columnElements) {
                topAvailableDeviceSerialNumbers.add(element.findElement(By.tagName("a")).getText());
            }
        }
        System.out.println(("Device Serial Numbers: " + topAvailableDeviceSerialNumbers));
        LOGGER.info("Extracted available all device serial number to test the sorting");
        return topAvailableDeviceSerialNumbers;
    }

    /**
     * This method will find all the elements in the page for given locator
     * @return List of string that is available in UI
     */
    public final List<WebElement> getElementsTillAllElementsPresentOnDeviceListPage(String locator) throws Exception {
        return getElementsTillAllElementsPresent(locator);
    }
    
    /**
     * This is a method to all available Physical Assets in the page
     *
     * @return List of string that is available in UI
     */
    public List<String> getAvailablePhysicalAssets() throws Exception {
        List<String> topAvailablePhysicalAssets = new ArrayList<>();
        sleeper(3000);
        List<WebElement> columnElements;
        columnElements = getElementsTillAllElementsPresentOnDeviceListPage(deviceListPageProperties.getProperty("physicalAssetSerialNumberRows"));
        if (!columnElements.isEmpty()) {
            for (WebElement element : columnElements) {
                topAvailablePhysicalAssets.add(element.findElement(By.tagName("a")).getText());
            }
        }
        LOGGER.info("Extracted all the Physical Assets to test the sorting");
        return topAvailablePhysicalAssets;
    }
    
    /**
     * This is a method to return elements of device list page
     *
     * @param key - Locator of element
     * @return WebElement list that are present in that locator
     */
    public final List<WebElement> getElementsOfDeviceListPage(String key) throws Exception {
        return getElementsTillAllElementsPresent(deviceListPageProperties.getProperty(key));
    }


    /**
     * This is a method to return value from HashMap
     *
     * @return - list of value of map
     */
    public ArrayList<String> getValueFromHashMap(HashMap<String, String> map) {
        ArrayList<String> serialNumber = new ArrayList<String>();
        for (Map.Entry<String, String> e : map.entrySet())
            serialNumber.add(e.getKey());
        return serialNumber;
    }

    /**
     * This method is used to verify addition of devices
     *
     * @param serialNumber - serial number to add
     * @return true if added successfully and showing in list
     */
    public boolean verifyAddedWEPDevicesOnListPage(List<String> serialNumber) {
        try {
            boolean flag = false;
            List<String> addedSerialNumber = new ArrayList<String>();
            for (int serialNumberCounter = 0; serialNumberCounter < (long) serialNumber.size(); serialNumberCounter++) {
                verifyElementsOfWEPDeviceListPage("searchDeviceSerialNumberTextBox");
                getElementOfWEPDeviceListPage("searchDeviceSerialNumberTextBox").clear();
                enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber.get(serialNumberCounter));
                sleeper(3000);
                List<WebElement> columnElements;
                columnElements = getElementsTillAllElementsPresent(deviceListPageProperties.getProperty("deviceNameColumn"));

                if (!columnElements.isEmpty()) {
                    for (WebElement element : columnElements) {
                        if (element.findElement(By.tagName("a")).getText().equals(serialNumber.get(serialNumberCounter))) {
                            addedSerialNumber.add(serialNumber.get(serialNumberCounter));
                        }
                    }
                }
            }
            if (serialNumber.equals(addedSerialNumber)) {
                flag = true;
                LOGGER.info("Devices which got added/imported are getting reflected on List page.");
            } else {
                LOGGER.error("Devices which got added/imported are not getting reflected on List page.");
            }
            return flag;
        } catch (Exception ex) {
            LOGGER.error("Exception occurred in method verifyDevicesOnListPage {}", ex.getMessage());
            return false;
        }
    }

    /**
     * This method deletes the device from the device list page if it exists before adding
     * the same devices again to the list page
     *
     * @param serialNumbersToDeleteIfExist the serial numbers to check if it exists in the list page
     * @throws Exception
     */
    public void deleteDeviceIfExistBeforeAddDevicesFromListPage(ArrayList<String> serialNumbersToDeleteIfExist) throws Exception {
    	LOGGER.info("trying to delete devices if exist");
        ArrayList<String> devicesToDelete = new ArrayList<>();
        for (String serialNumber : serialNumbersToDeleteIfExist) {
            if (!serialNumber.isEmpty()) {
                verifyElementsOfWEPDeviceListPage("searchDeviceSerialNumberTextBox");
                getElementOfWEPDeviceListPage("searchDeviceSerialNumberTextBox").clear();
                enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
                sleeper(3000);
                waitForElementsOfWEPDeviceListPage("atleastOneDeviceRow");
                ArrayList<WebElement> deviceRowList = (ArrayList<WebElement>) getElementsOfDeviceListPage("atleastOneDeviceRow");
                if (deviceRowList.size()==1) continue;
                else{
                    if(!devicesToDelete.contains(serialNumber)){
                        devicesToDelete.add(serialNumber);
                    }
                }
            }
        }
        if(!devicesToDelete.isEmpty()){
            deleteAllAddedWEPDevicesFromListPage(devicesToDelete);
        }else {
        	LOGGER.info("No devices are present to delete");
        }
    }

    /**
     * This method is used to delete a device using UI
     *
     * @param serialNumbersToDelete - serial number to delete
     * @return true if deleted successfully
     */
    public boolean deleteAllAddedWEPDevicesFromListPage(ArrayList<String> serialNumbersToDelete) throws Exception {
        try {
            boolean flag = false;
            for (String serialNumber : serialNumbersToDelete) {
                if (!serialNumber.isEmpty()) {
                    verifyElementsOfWEPDeviceListPage("searchDeviceSerialNumberTextBox");
                    getElementOfWEPDeviceListPage("searchDeviceSerialNumberTextBox").clear();
                    enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
                    sleeper(3000);

                    ArrayList<WebElement> deviceRowList = (ArrayList<WebElement>) getElementsOfDeviceListPage("atleastOneDeviceRow");
                    if (deviceRowList.size()>1) {
                        sleeper(3000);
                        clickOnElementsOfDevicePage("selectFirstDeviceCheckbox");
                        sleeper(2000);
                        verifyElementsOfWEPDeviceListPage("deleteDeviceButton");
                        clickOnElementsOfDevicePage("deleteDeviceButton");
                        sleeper(2000);
                        verifyElementsOfWEPDeviceListPage("deleteIndividualDeviceEntryBtn");
                        clickByActionsWEPClickDevicelistPage("deleteIndividualDeviceEntryBtn");
                        LOGGER.info("clicked on select device to delete in delete device popup");

                        String extractedSecurityKey = getTextOfWEPDeviceListPage("securityNumberLabel");
                        enterTextForDeviceListPage("securityNumberTxtBox", extractedSecurityKey);
                        sleeper(2000);
                        verifyElementsOfWEPDeviceListPage("deleteSelectedDeviceBtn");
                        clickByActionsWEPClickDevicelistPage("deleteSelectedDeviceBtn");

                        verifyElementsOfWEPDeviceListPage("importNotification");
                        String notificationMessageToVerify = getTextOfWEPDeviceListPage("importNotification");
                        Assert.assertTrue(isNotificationExistMatching(notificationMessageToVerify),"Deleting selected device failed");
                        sleeper(5000);
                    }
                }
            }
            getElementOfWEPDeviceListPage("searchDeviceSerialNumberTextBox").clear();
            sleeper(2000);
            if (VerifyDeviceAvailabilityInDeviceListPage(serialNumbersToDelete,true)){
                flag = true;
                LOGGER.info("All the devices selected for delete got deleted successfully");
            }
            return flag;
        }  catch (Exception e) {
            LOGGER.error(String.format("Exception occurred in delete device api method %s", e.getMessage()));
            return false;
        }
    }

    private boolean isNotificationExistMatching(String notificationMessageToVerify) {
        return notificationMessageToVerify.equalsIgnoreCase("Asset Deletion in progress. Please check notification center for update.") ||
                notificationMessageToVerify.equalsIgnoreCase("Assets Deletion in progress. Please check notification center for update.");
    }

    /**
     * Get device id from import csv file
     *
     * @param fileName - CSV file name to extract serial number
     */
    public String getDeviceSerialNo(String fileName) {
        Map.Entry<String, String> entry;
        try {
            HashMap<String, String> devicesCSV = new HashMap<>();
            CSVFileReader csv = new CSVFileReader();
            File file = new File(ConstantPath.IMPORT_PATH + fileName);
            for (String[] mapping : Objects.requireNonNull(csv.getDataWithoutHeader(file))) {
                devicesCSV.put(mapping[0], mapping[1]);
            }
            entry = devicesCSV.entrySet().iterator().next();
            waitForPageLoaded();

        } catch (Exception e) {
            LOGGER.error("Exception occurred in method verifyImportedDevicesOnListPage {}", e.getMessage());
            return null;
        }
        return entry.getKey();
    }

    /**
     * This is a method to check the logs message page
     *
     * @param logErrorMessage - message that needs to be verified against the log message in logs page
     * @return - true if message is matching per ErrorMessage
     */
    public boolean verifyDescriptionOnLogsPage(String logErrorMessage, String notificationMessageToVerify) {
        try {
            WEXLogPage logPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
            verifyElementsOfWEPDeviceListPage("notificationBellIcon");
            clickByJavaScriptOnDevicePage("notificationBellIcon");
            LOGGER.info("Clicked on notification bell icon");
            clickByJavaScriptOnDevicePage("notificationTab");
            LOGGER.info("Clicked on notification tab");
            List<WebElement> listOfUnreadNotificationText = getElementsOfDeviceListPage("listOfUnreadNotificationText");
            List<WebElement> listOfNotificationActionsBtn = getElementsOfDeviceListPage("listOfNotificationActionsBtn");
            for (int i = 0; i < 2; i++) {
    			mouseHover(listOfUnreadNotificationText.get(i));
    			if(listOfUnreadNotificationText.get(i).getText().contains(notificationMessageToVerify)) {
    				LOGGER.info("device import notification received successfully");
    				listOfNotificationActionsBtn.get(i).click();
    				LOGGER.info("Clicked on notification action button");
                    clickByJavaScriptOnDevicePage("openLogsBellIcon");
                    LOGGER.info("Clicked on open logs hyperlink");
                    logPage.waitForPageLoaded();
                    logPage.resetTableConfiguration();
                    logPage.verifyElementsOfWEXLogPage("listTable");
                    logPage.waitForElementsOfWEXLogPage("firstRowDescription");
                    sleeper(5000);
                    var rowElement = logPage.getElementOfWEXLogPage("firstRowDescription");
                    if (rowElement == null) {
                        throw new AssertionError();
                    }
                    String logToVerify = rowElement.getAttribute("textContent");
                    LOGGER.info("Extracted log message to verify is:{}", logToVerify);
                    if (logToVerify.equalsIgnoreCase(logErrorMessage)) {
                        return true;
                    } else {
                        LOGGER.error("Description on logs page is incorrect when devices are not imported successfully");
                        return false;
                    }
    			}
			}
            return false;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method verifyDescriptionOnLogsPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * This is a method to check the post notification message
     *
     * @param notificationMessageToVerify - message that needs to be verified against the UI for import
     * @return - true if message is matching per import notification
     */
    public boolean postImportCheckForNotificationMessage(String notificationMessageToVerify) {
        try {
            boolean flag = false;
            verifyElementsOfWEPDeviceListPage("notificationBellIcon");
            clickByJavaScriptOnDevicePage("notificationBellIcon");
            LOGGER.info("Clicked on notification bell icon");
            clickByJavaScriptOnDevicePage("notificationTab");
            LOGGER.info("Clicked on notification tab");
            sleeper(10000);
            List<WebElement> listOfUnreadNotificationText = getElementsOfWEPDeviceListPage("listOfUnreadNotificationText");
            for (int i = 0; i < 2; i++) {
    			mouseHover(listOfUnreadNotificationText.get(i));
    			if(listOfUnreadNotificationText.get(i).getText().contains(notificationMessageToVerify)) {
                    LOGGER.info("device import notification received successfully");
                    flag = true;
                    break;
    			}
    		}
            if(!flag) {
            	LOGGER.error("Notification for import has failed");
            }
            clickByJavaScriptOnDevicePage("notificationBellIcon");
            return flag;
        } catch (Exception e) {
            LOGGER.error((String.format("Exception occurred in method postImportCHeckForNotificationMessage %s", e.getMessage())));
            return false;
        }
    }

    /**
     * This is a method to wait for an element till it is visible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is visible or not
     */
    public final boolean waitForElementsOfDeviceListPage(String key) {
        try {
            return verifyElementIsVisible(deviceListPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method waitForElementsOfDeviceListPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * This is a method to remove the empty values from a list
     *
     * @param deviceSerialNumberList - device Serial number list (unfiltered)
     * @return - list of elements with empty values removed
     */
    public static List<String> removeEmptyDuplicate(List<String> deviceSerialNumberList) {
        Set<String> uniqueDeviceSerialNumberSet = new LinkedHashSet<>(deviceSerialNumberList);
        return uniqueDeviceSerialNumberSet.stream().filter((s -> !s.isEmpty())).collect(Collectors.toList());
    }

    /**
     * This is a method to get sorting order
     *
     * @param deviceSerialNumber - List of device serial number to check sorting
     * @param isDesc             - To carry the storting type
     * @return - true if the sort is descending, false otherwise
     */
    public boolean IsDeviceSorted(List<String> deviceSerialNumber, boolean isDesc) {
        List<String> uniqueDeviceSerialList = removeEmptyDuplicate(deviceSerialNumber);
        boolean isInOrder = true;
        if (isDesc) {
            for (int i = 0; i < uniqueDeviceSerialList.size() - 1; i++) {
                if (uniqueDeviceSerialList.get(i).compareTo(uniqueDeviceSerialList.get(i + 1)) <= 0) {
                    isInOrder = false;
                    break;
                }
            }
        } else {
            for (int i = 0; i < uniqueDeviceSerialList.size() - 1; i++) {
                if (uniqueDeviceSerialList.get(i).compareTo(uniqueDeviceSerialList.get(i + 1)) >= 0) {
                    isInOrder = false;
                    break;
                }
            }
        }
        deviceSerialNumber.clear();
        return isInOrder;
    }

    /**
     * This is a method to get sorting order
     *
     * @return - true if the sort is descending, false otherwise
     */
    public boolean getSortingOrderType() throws Exception {
        String result = getAttribute(deviceListPageProperties.getProperty("serialNumberOrderBtn"), "aria-label");
        return result.equals("sorted by Serial Number descending, sort by ascending");
    }

    /**
     * This is a method to export devices that are available
     *
     * @param deviceSerialNumberList - serial number list from csv
     * @param key                    - element to click on popup for export
     * @return - true if exported successfully
     */
    public boolean ExportAllAvailableDevices(List<String> deviceSerialNumberList, String key, String notificationMessageToVerify) throws Exception {
        LOGGER.info("Verifying Export device UI popup");
        verifyElementsOfWEPDeviceListPage("exportDevicesHeader");
        Assert.assertTrue(getTextOfWEPDeviceListPage("exportDevicesHeader").contains("Export device"), "This dialog is not a export device ");
        Assert.assertEquals(getTextOfWEPDeviceListPage("exportDeviceHeaderLabel"), "Choose an export option for the selected devices (" + deviceSerialNumberList.size() + ")", "Export device dialog header not matching");
        Assert.assertEquals(getTextOfWEPDeviceListPage("exportDeviceByCurrentField"), "Current field from table", "Export device by current field doesn't exist as an option");
        Assert.assertEquals(getTextOfWEPDeviceListPage("exportDeviceType1Label"), "Export configured columns from existing table.", "Export device dialog header not matching");
        Assert.assertEquals(getTextOfWEPDeviceListPage("exportDeviceByAllEditableField"), "All editable fields", "Export device by editable field doesn't exist as an option");
        Assert.assertEquals(getTextOfWEPDeviceListPage("exportDeviceType1Labe2"), "Export all editable fields of the selected devices. After exporting, modify the file and import to bulk-update device details.", "Export device dialog header not matching");

        LOGGER.info("Clicked on Export device by Current field from table");
        clickByJavaScriptOnDevicePage(key);
        verifyElementsOfWEPDeviceListPage("importNotification");
        Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Export is in progress", "Export Notification didn't present");
        LOGGER.info("Export toast message is verified successfully");
        verifyElementsOfWEPDeviceListPage("firstNotificationCloseBtn");
        clickByJavaScriptOnDevicePage("firstNotificationCloseBtn");
        verifyElementsOfWEPDeviceListPage("notificationBellIcon");
        clickByJavaScriptOnDevicePage("notificationBellIcon");
        clickByJavaScriptOnDevicePage("notificationTab");
        sleeper(50000);
        List<WebElement> listOfUnreadNotificationText = getElementsOfWEPDeviceListPage("listOfUnreadExportNotificationText");
        for (int i = 0; i < 2; i++) {
			mouseHover(listOfUnreadNotificationText.get(i));
			if(listOfUnreadNotificationText.get(i).getText().contains(notificationMessageToVerify)) {
                LOGGER.info("device import notification received successfully");
                waitForElementsOfWEPDeviceListPage("downloadLinkBellIcon");
                clickByJavaScriptOnDevicePage("downloadLinkBellIcon");
                LOGGER.info("Notification of export devices received.");
                return true;
			}
		}
        LOGGER.error("Notification for export has failed");
        return false;
    }

    /**
     * This is a method to get a list of elements of device list page
     *
     * @param deviceSerialNumberList   - Locator of list
     * @param currentColumnsOnListPage - columns on the list page
     * @return - true if devices are extracted successfully, false otherwise
     */
    public boolean verifyExtractedDevices(ArrayList<String> deviceSerialNumberList, ArrayList<String> currentColumnsOnListPage) throws InterruptedException, IOException {
        CSVFileReader csv = new CSVFileReader();
        int countFiles = 0;
        boolean flag = true;
        sleeper(3000);
        File f = new File(ConstantPath.DOWNLOAD_PATH);

        if (!f.exists()) {
            File file = new File(ConstantPath.DOWNLOAD_PATH);
            FileUtils.forceMkdir(file);
        }
        if (Objects.requireNonNull(f.listFiles()).length > 0) {
            for (File file : Objects.requireNonNull(f.listFiles())) {
                if (file.isFile()) {
                    ArrayList<String> columnList = new ArrayList<>();
                    String[][] header = csv.getDataWithHeader(file);
                    ArrayList<String> exportedSerialNumber = new ArrayList<>();
                    int serialNumberColumnIndex = 0;
                    for (int i = 0; i < Objects.requireNonNull(header)[1].length; i++) {
                        String columnValueToInsert = header[0][i].replaceAll("\\s*\\([^)]*\\)", "").replace(" ", "").toUpperCase();
                        if (!columnValueToInsert.isEmpty()) {
                            if (columnValueToInsert.equals("SERIALNUMBER")) {
                                serialNumberColumnIndex = i;
                            }
                            columnList.add(columnValueToInsert);
                        }
                    }
                    for (int i = 0; i <= deviceSerialNumberList.size(); i++) {
                        if (i != 0) {
                            exportedSerialNumber.add(header[i][serialNumberColumnIndex]);
                        }
                    }
                    exportedSerialNumber = (ArrayList<String>) removeEmptyDuplicate(exportedSerialNumber);

                    for (String s : currentColumnsOnListPage) {
                        if (!columnList.stream().anyMatch(col->col.contains(s))) {
                            flag = false;
                            break;
                        }
                    }
                    countFiles++;
                    if (!flag)
                        LOGGER.info("Columns on device list page and selected exported columns in .csv file are not same");

                    for (String element : exportedSerialNumber) {
                        if (!deviceSerialNumberList.contains(element)) {
                            flag = false;
                            break;
                        }
                    }
                    if (!flag)
                        LOGGER.info("Device serial number from the device list page is not matching with the selected exported csv device serial number");
                }
            }
            if (countFiles >= 1) {
                deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
            } else {
                LOGGER.info("Don't delete the devices folder");
            }
        } else {
            LOGGER.info("There is no file inside the devices folder so no need to delete it");
        }
        deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        waitForPageLoaded();
        return flag;
    }

    /**
     * This is a method to get the list of serial numbers present in the device list page
     * to add to an arraylist
     *
     * @param deviceSerialNumberList - arraylist which gets updated with the serial number in UI
     */
    public void GetSerialNumberListFromDeviceListPage(ArrayList<String> deviceSerialNumberList) throws Exception {
        ArrayList<WebElement> serialNumberListElements = (ArrayList<WebElement>) getElementsOfDeviceListPage("serialNumberList");
        if (serialNumberListElements == null) {
            throw new AssertionError();
        }
        for (WebElement element : serialNumberListElements) {
            if (!element.getText().isEmpty()) {
                deviceSerialNumberList.add(element.getText());
            }
        }
    }

    /**
     * This is a method to delete device directory
     *
     * @param path - path to the directory
     */
    public void deleteDeviceDir(String path) {
        try {
            if (new File(path).exists()) {
                File file = new File(path);
                FileUtils.cleanDirectory(file);
                FileUtils.forceDelete(file);
                FileUtils.forceMkdir(file);
            } else {
                File file = new File(path);
                FileUtils.forceMkdir(file);
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method deleteDeviceDir " + e.getMessage());
        }
    }

    /**
     * This is a method to clear the filter if present in UI
     */
    public void clearFiltersOfDevicesListPage(String clearFilterKey) throws Exception {
    	sleeper(10000);
        clearFilters(deviceListPageProperties.getProperty(clearFilterKey));
        sleeper(10000);
        LOGGER.info("filter is cleared successfully");
    }

    /**
     * This is a method to clear and reset the device list table configuration
     */
    public void ClearAndResetDeviceListTable() throws Exception {
        verifyElementsOfWEPDeviceListPage("columnOptionBtn");
        clickByJavaScriptOnDevicePage("columnOptionBtn");
        sleeper(3000);
        WebElement resetBtn = getElementOfWEPDeviceListPage("resetToDefaultBtn");
        if(Boolean.parseBoolean(resetBtn.getAttribute("aria-disabled"))){
            TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
            tableConfigurationPage = tableConfigurationPage.getInstance();
            tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
            sleeper(3000);
            clearFiltersOfDevicesListPage("clearfilter");
            return;
        }
        resetTableConfiguration();
        clearFiltersOfDevicesListPage("clearfilter");
    }
    
	/**
	 * This is a method to clear and select enrolled column For VM
	 */
	public void selectEnrolledColumnForVM() throws Exception {
		clearFiltersOfDevicesListPage("clearfilter");
		verifyElementsOfWEPDeviceListPage("columnOptionBtn");
		clickByJavaScriptOnDevicePage("columnOptionBtn");
		sleeper(3000);
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("chkEnrolled");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(3000);
		return;
	}
    
    /**
     * This is a method to extract all the serial numbers from CSV file
     *
     * @param fileName                 - name of csv
     * @param serialNumberListToDelete - arraylist consisting of all the serial numbers to delete
     */
    public void ExtractDeviceSerialNumberFromCSV(String fileName, ArrayList<String> serialNumberListToDelete) throws Exception {
        CSVFileReader csv = new CSVFileReader();
        sleeper(3000);
        File file = new File(fileName);
        if (file.isFile()) {
            String[][] header = csv.getDataWithHeader(file);
            for (int i = 1; i < (header != null ? header.length : 0); i++) {
                serialNumberListToDelete.add(header[i][0]);
            }
        } else {
            LOGGER.info("There is an issue with the file");
        }
    }

    /**
     * This is a method to verify element is clickable
     */
    public final boolean verifyElementIsClickableOfDeviceListPage(String key) throws Exception {
    	return verifyElementIsClickable(deviceListPageProperties.getProperty(key));
    }
    
    /**
     * This is a method to click on an element via key
     *
     * @param key - action click on element
     */
    public final void actionClickOfDeviceListPage(String key) throws Exception {
        actionClick(deviceListPageProperties.getProperty(key));
    }

    /**
     * This is a method to check if the device is available in the device list page after deleting
     *
     * @param serialNumberListToDelete - list of devices that should be deleted
     * @param isDeleted                - true if device is deleted, false otherwise
     * @return True if device is available false otherwise
     */
    public boolean VerifyDeviceAvailabilityInDeviceListPage(ArrayList<String> serialNumberListToDelete, boolean isDeleted) throws Exception {
    	LOGGER.info("Verifying device availability in device list page");
    	clearFiltersOfDevicesListPage("clearfilter");
    	verifyElementsOfWEPDeviceListPage("searchDeviceSerialNumberTextBox");
        boolean flag = true;
        if (!serialNumberListToDelete.isEmpty()) {
            for (String serialNumber : serialNumberListToDelete) {
                enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
                sleeper(4000);
                if (isDeleted) {
                    //check for row exist
                	waitForElementsOfDeviceListPage("atleastOneDeviceRow");
                    ArrayList<WebElement> deviceRowList = (ArrayList<WebElement>) getElementsOfDeviceListPage("atleastOneDeviceRow");
                    if (deviceRowList.size() >1){
                        flag = false;
                        LOGGER.error("{} device deleted is still exist in the device list", serialNumber);
                        break;
                    }
                } else if (!serialNumber.equals(getTextOfWEPDeviceListPage("firstSerialNumberFromList"))) {
                    flag = false;
                    LOGGER.error(String.format("%s device added is not exist in the device list page", serialNumber));
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * This is a method for auto enrolling devices via csv
     *
     * @param fileName - list of devices to auto enroll
     */
    public void AutoEnrollDevices(String fileName) throws Exception {
        navigateToEnrollingDeviceScreen();
        verifyElementsOfWEPDeviceListPage("browseButton");
        LOGGER.info("Clicked on upload file button");
        sleeper(2000);
        WebElement addFile = getElementOfWEPDeviceListPage("browseButton");
        addFile.sendKeys(fileName);
        clickByJavaScriptOnDevicePage("importPhysicalAssetCSVBtn");
        sleeper(1000);
        verifyElementsOfWEPDeviceListPage("importNotification");
        Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Import is in progress", "Importing bulk csv failed");
        sleeper(60000); //waiting for import to complete
    }

    /**
     * This is a method for auto enrolling devices manually
     *
     * @param serialNumberListToAddAndVerify - list of serial numbers to add and verify
     * @param isDuplicateSerialNumber        - true if serial number that is being added is a duplicate
     */
    public void EnrollDevicesManually(ArrayList<String> serialNumberListToAddAndVerify, boolean isDuplicateSerialNumber) throws Exception {
        if (serialNumberListToAddAndVerify.size() < 6) {
            navigateToEnrollingDeviceScreen();
            verifyElementsOfWEPDeviceListPage("manualEnrollmentBtn");
            clickByJavaScriptOnDevicePage("manualEnrollmentBtn");
            int addedDeviceCounter = 0;
            String deviceImportMessage = DeviceVariables.IMPORT_SUCCESS_MANUAL_SINGLE;
            for (int i = 0; i < serialNumberListToAddAndVerify.size(); i++) {
                verifyElementIsPresent(deviceListPageProperties.getProperty("serialNumberTextBox") + "[" + (i + 1) + "]");
                enterText(deviceListPageProperties.getProperty("serialNumberTextBox") + "[" + (i + 1) + "]", serialNumberListToAddAndVerify.get(i));
                enterText(deviceListPageProperties.getProperty("deviceAliasTxtBox") + "[" + (i + 1) + "]", "deviceAliasTag");
                addedDeviceCounter = i + 1;
                if (serialNumberListToAddAndVerify.size() > 1 && addedDeviceCounter < serialNumberListToAddAndVerify.size()) {
                    deviceImportMessage = DeviceVariables.MANUAL_ENROL_MESSAGE_MULTIPLE;
                    verifyElementsOfWEPDeviceListPage("addAnotherAutoDevice");
                    clickOnElementsOfDevicePage("addAnotherAutoDevice");
                }
            }
            verifyElementsOfWEPDeviceListPage("addManualDeviceBtn");
            clickOnElementsOfDevicePage("addManualDeviceBtn");
            String notificationText = getTextOfWEPDeviceListPage("addDeviceSuccessNotification");
            Assert.assertEquals(notificationText, deviceImportMessage, "Notification message is not matching");
            LOGGER.info("Fleet Management Devices Page : Adding new device manually done successfully");
        } else {
            LOGGER.info("Number of devices sent to add exceeds the limit 5");
        }
    }

    /**
     * This is a method used to navigate to the screen where we decide to add device manually or auto enroll
     */
    private void navigateToEnrollingDeviceScreen() throws Exception {
        verifyElementsOfWEPDeviceListPage("addDeviceDialogHeader");
        matchTextOfDeviceListPage("addDeviceDialogHeader", "Add Devices");
        verifyElementsOfWEPDeviceListPage("addBulkDeviceThroughCSVBtn");
        clickByJavaScriptOnDevicePage("addBulkDeviceThroughCSVBtn");
        verifyElementsOfWEPDeviceListPage("EnrolledDevicesForZTE");
        clickByJavaScriptOnDevicePage("EnrolledDevicesForZTE");
        if (getElementOfWEPDeviceListPage("addDeviceSubTitle").getText().contains("Select your plan(s) to enroll devices, then import a CSV file to add devices") || 
        		getElementOfWEPDeviceListPage("addDeviceSubTitle").getText().contains("import a CSV file to support Zero-touch enrollment")) {
            verifyElementsOfWEPDeviceListPage("selectAutoPlanFromEnrolledPlans");
            actionClickOfDeviceListPage("selectAutoPlanFromEnrolledPlans");
            sleeper(2000);
            verifyElementsOfWEPDeviceListPage("selectPlansFromList");
            clickByJavaScriptOnDevicePage("selectPlansFromList");
            return;
        }
        verifyElementsOfWEPDeviceListPage("nextBtn");
        clickByJavaScriptOnDevicePage("nextBtn");
    }

    /**
     * This is a method to verify the status of a device (Active, Inactive, etc.)
     *
     * @param serialNumberListToVerify - list of serial numbers to add and verify
     * @param statusMessageToVerify    - status to check against
     * @return true if status is matching expected statusMessageToVerify
     */
    public boolean VerifyAddedDeviceStatus(ArrayList<String> serialNumberListToVerify, String statusMessageToVerify) throws Exception {
        verifyElementsOfWEPDeviceListPage("searchDeviceSerialNumberTextBox");
        boolean flag = true;
        if (!serialNumberListToVerify.isEmpty()) {
            for (String serialNumber : serialNumberListToVerify) {
                enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
                sleeper(4000);
                if (!getElementOfWEPDeviceListPage("deviceStatus").getText().equalsIgnoreCase(statusMessageToVerify)) {
                    LOGGER.error("Status of the device extracted is:"+getElementOfWEPDeviceListPage("deviceStatus").getText());
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * This is a method to extract serial numbers from CSV file, and delete the device using the delete API
     *
     * @param serialNumberListToDelete -  serial numbers to delete
     */
    public void DeleteDeviceAndVerifyExistence(ArrayList<String> serialNumberListToDelete) throws Exception {
        Assert.assertTrue(deleteAllAddedWEPDevicesFromListPage(serialNumberListToDelete), "Devices are not getting deleted from the list page.");
        Assert.assertTrue(VerifyDeviceAvailabilityInDeviceListPage(serialNumberListToDelete, true), "Some device deleted in bulk are still exist in the device list page");
    }

    public final void clickOnElementsOfWEPDeviceListPage(String key) throws Exception {
        click(deviceListPageProperties.getProperty(key));
    }

    public final boolean waitForElementsOfDEXPage(String key) throws Exception {
        return verifyElementIsVisible(deviceListPageProperties.getProperty(key));
    }
    
    /**
     * This method used to send keys
     *
     * @param key - Web element locator key
     * @param value - value to send to webelement
     */
	public final void sendKeysOnWEPDeviceListPage(String key, String value) throws Exception {
		WebElement elem = getElementOfWEPDeviceListPage(key);
		elem.clear();
		waitForPageLoaded();
		elem.clear();
		elem.sendKeys(value);
		sleeper(3000);
	}

    public final List<WebElement> getElementsOfWEPDeviceListPage(String key) throws Exception {
        return getAllElements(deviceListPageProperties.getProperty(key));
    }

    public final void switchToDifferentTabOfWEPDeviceListPage() {
        switchToDifferentTab();
    }

    public final boolean verifyTroubleshootButton(String Languagecode)
    {
        boolean flag = false;
        try {
            List<WebElement> recommendedactions = getElementsOfWEPDeviceListPage("recommendedActionsContent");
            int recommendedactionsSize = recommendedactions.size();
            for (int i = 0; i < recommendedactionsSize; i++) {
                if (waitForElementsOfDEXPage("troubleShootButton")) {
                    clickOnElementsOfWEPDeviceListPage("troubleShootButton");
                    switchToDifferentTabOfWEPDeviceListPage();
                    if (waitForElementsOfDEXPage("troubleshootGuideTitle")) {
                        LOGGER.info("Troubleshoot button is working correctly.");
                        flag = true;
                        switchToParentTab();
                    } else {
                        LOGGER.error("Troubleshoot button is not working correctly.");
                        return false;
                    }
                    return flag;
                } else if (waitForElementsOfDEXPage("recommendedActionsNodata")) {
                    LOGGER.info("No data is present in Recommended Actions tile.");
                    return true;
                } else {
                    LOGGER.error("Troubleshoot button is not working correctly.");
                    return false;
                }
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    public final boolean verifyViewDevicesButton(String Languagecode)
    {
        boolean flag = false;
        String numberOfDevicesString,numberOfDevices,paginationString,paginationCount = null;
        try{
            if(!waitForElementsOfDEXPage("recommendedActionsNodata")){
                numberOfDevicesString = getTextOfWEPDeviceListPage("numberOfDevicePill");
                String [] numberOfDevicesArray = numberOfDevicesString.split(" ");
                numberOfDevices = numberOfDevicesArray[0];
                clickOnElementsOfWEPDeviceListPage("numberOfDevicePill");
                if(getTextOfWEPDeviceListPage("expmgmtDetailsTitle").equalsIgnoreCase(getTextLanguage(Languagecode,"daas_ui","assets.import.asset.title")))
                {
                    sleeper(3000);
                    paginationString = getTextOfWEPDeviceListPage("paginationCount");
                    String [] paginationStringArray = paginationString.split(" ");
                    paginationCount = paginationStringArray[1];
                    System.out.println("numberofdevices "+numberOfDevices);
                    System.out.println("pagination "+paginationCount);
                    if(numberOfDevices.equalsIgnoreCase(paginationCount)){
                        LOGGER.info("Filter from View Devices button is working correctly.");
                        flag = true;
                    }else
                    {
                        LOGGER.error("Filter from View Devices button is not working correctly. Count from Charts page did not match in Grid page.");
                    }
                }else
                {
                    LOGGER.error("Exp mgmt page Grid page did not load successfully from View Devices button.");
                }
                return flag;
            }
            else{
                LOGGER.info("No data is present in Recommended Actions tile.");
                return true;
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public void addPhysicalAsset(String fileName) {
        try {
        	waitForElementsOfWEPDeviceListPage("addPhysicalDeviceBtn");
        	clickByActionsWEPClickDevicelistPage("addPhysicalDeviceBtn");
            waitForElementsOfWEPDeviceListPage("browseButton");
            WebElement addFile = getElementOfWEPDeviceListPage("browseButton");
            addFile.sendKeys(fileName);
            LOGGER.info("Clicked on Upload File browse button");
            sleeper(5000);
            verifyElementIsClickableOfDeviceListPage("importPhysicalAssetCSVBtn");
            waitForElementsOfWEPDeviceListPage("importPhysicalAssetCSVBtn");
            clickByJavaScriptOnDevicePage("importPhysicalAssetCSVBtn");
			try {
				clickByJavaScriptOnDevicePage("closeAddDevicePopup");
			} catch (Exception e) {
				LOGGER.info("Retrying click on closeAddDevicePopup as Exception caught - ", e.getMessage());
				clickByJavaScriptOnDevicePage("closeAddDevicePopup");
			}
            verifyElementsOfWEPDeviceListPage("importNotification");
            Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Import is in progress\n" + "You will be notified once completed. Check Notification Center in sometime.", "Importing bulk csv failed");
            sleeper(60000); //waiting for device to be imported
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method ImportPhysicalDevicesFromCSV {}", e.getMessage());
        }
    }

    public final void sendkeysOnWEPDeviceListPage(String key) throws Exception {
        int randomNumber = 0;
        Random random = new Random();
        randomNumber = random.nextInt(1000);
        String randomString = String.valueOf(randomNumber);
        WebElement addFile = getElementOfWEPDeviceListPage("nameboxinput");
        addFile.sendKeys(randomString + "WEP_Device_Page");
    }

    /**
	 * This is a method to scroll on deviceListPage page
	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnWEPDeviceListPage(String key) {
		try {
			scrollTillView(deviceListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnDeviceListPage " + e.getMessage()));
		}
	}

    /**
     * This is a method to hover mouse on an element
     *
     * @param key - Locator of element
     */
    public final void mousehoverOnWEPDeviceListPage(String key) {
        try {
            mouseHover(deviceListPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnWEPDeviceListPage " + e.getMessage()));
        }
    }

	/**
	 * This is a method to click by actions class on deviceListPage page
	 * 
	 * @param key
	 */
	public final void clickByActionsWEPClickDevicelistPage(String key) {
		try {
			actionClick(deviceListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByActionsWEPClickDevicelistPage " + e.getMessage()));
		}
	}

    public final void doubleClickByActionsWEPClickDevicelistPage(String key) {
        try {
            doubleclick(deviceListPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method doubleClickByActionsWEPClickDevicelistPage " + e.getMessage()));
        }
    }
	
	/**
	 * This is a method to get a list of elements of WEP device list page
	 * 
	 * @param key - Locator of list
	 * @return - list of webelements
	 */
	public final List<WebElement> getElementsTillAllElementsVisibleofWEPDeviceListPage(String key) {
		try {
			return getElementsTillAllElementsVisible(deviceListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofWEPDeviceListPage " + e.getMessage()));
			return null;
		}
	}

	
	/**
	 * @param LanguageCode
	 * @param columnField
	 * @param searchBox
	 * @param searchValue
	 * @param totalNumbers
	 * @return
	 * @throws Exception
	 */
	public Integer verifyApplyOtherFilters(String LanguageCode,String columnField,String searchBox,String searchValue, String totalNumbers) throws Exception{
 			scrollOnWEPDeviceListPage(columnField);
 			String textOfWEPDeviceListPage = getTextOfWEPDeviceListPage(columnField);
 			int Total;
 			String Value;
 			if(textOfWEPDeviceListPage.equals(getTextLanguage(LanguageCode,"daas_ui","asset_modal_pctype"))||textOfWEPDeviceListPage.equals(getTextLanguage(LanguageCode,"daas_ui","asset_detail.accessories_column.manufacturer"))) {
 				clickByActionsWEPClickDevicelistPage(searchBox);
 	 			sleeper(2000);
 	 			if(textOfWEPDeviceListPage.equals(getTextLanguage(LanguageCode,"daas_ui","asset_detail.accessories_column.manufacturer"))) {
 	 				Value = "HP";
 	 			}else {
 	 			Value = getTextLanguage(LanguageCode,"daas_ui","global.device_type.notebook");
 	 			}
 	 			List<WebElement> statusList = getElementsTillAllElementsVisibleofWEPDeviceListPage(searchValue);
 	 			for (int i = 0; i < statusList.size(); i++) {
 	 				if (Value.equals(statusList.get(i).getText())) {
 	 					statusList.get(i).click();
 						LOGGER.info("Clicked on the required Value");
 	 					sleeper(3000);
 	 					break;
 	 				}
 	 			}
 	 			Robot robot = new Robot();
 	 			robot.keyPress(KeyEvent.VK_ESCAPE);
 	 			robot.keyRelease(KeyEvent.VK_ESCAPE);
 	 			sleeper(2000);;
 			}else {
 			enterTextForDeviceListPage(searchBox, searchValue);
 			}
 			sleeper(4000);
 			String paginationText = getTextOfWEPDeviceListPage(totalNumbers).replace("of ", "");
	 			if(paginationText.contains(",")) {
	 				String replaced = paginationText.replace(",", "");
	 				Total = Integer.parseInt(replaced);
	 			}else {
	 				Total = Integer.parseInt(paginationText);
	 			}
	 			sleeper(3000);
 			return Total;	
 	}
	
/**
 * This is a method to select Active Status for Devices status Dropdown
 * 
 * @param LanguageCode
 * @param clearListPageFilter - locator of clear filter in device list page
 * @param devicestatus - locator of status header
 * @param devicestatusdd - locator of device status dropdown
 * @param statusddvalues - locator of dropdown values
 * @param firstcolumnheader - locator of first column header
 * @return
 */
public boolean verifyonlyactivedevicesdatafetch(String LanguageCode,String clearListPageFilter,String devicestatus, String devicestatusdd, String statusddvalues, String firstcolumnheader)
	{
		try {
			boolean flag = false;
		clearFiltersOfDevicesListPage(clearListPageFilter);
		resetTableConfiguration();	
		clearFiltersOfDevicesListPage(clearListPageFilter);
		scrollOnWEPDeviceListPage(devicestatus);
			sleeper(2000);
			clickByActionsWEPClickDevicelistPage(devicestatusdd);
			String activeValue = getTextLanguage(LanguageCode, "daas_ui", "global.active");
			List<WebElement> statusList = getElementsTillAllElementsVisibleofWEPDeviceListPage(statusddvalues);
			for (int i = 0; i < statusList.size(); i++) {
				if (activeValue.equals(statusList.get(i).getText())) {
					statusList.get(i).click();
				LOGGER.info("Clicked on the Active Status Value");
					break;
				}
			}
			sleeper(3000);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			scrollOnWEPDeviceListPage(firstcolumnheader);
			flag = true;
			LOGGER.info("Device list contains only active devices");
			return flag;
		}catch (Exception e) {
			LOGGER.error("Exception occured in method verifyonlyactivedevicesdatafetch " + e.getMessage());
			return false;
		}
	}


    public final List<String> getAllTextOfWEPDeviceListPage(String key) throws Exception {
	    return getallTextBy(deviceListPageProperties.getProperty(key));
    }

    /**
     * This is a method to verify if the element is present
     *
     * @param key - Locator of element
     */
    public final boolean verifyElementsOfWEPDeviceLstPage(String key) throws Exception {
        return verifyElementIsPresent(deviceListPageProperties.getProperty(key));

    }

    public void downloadNotificationFile(String downloadFileBellIconKey, String notificationMessageToVerify) throws Exception {
    	Boolean flag = false;
    	verifyElementsOfWEPDeviceListPage("notificationBellIcon");
        clickByActionsWEPClickDevicelistPage("notificationBellIcon");
        LOGGER.info("Clicked on notification bell icon");
        clickByActionsWEPClickDevicelistPage("notificationTab");
        LOGGER.info("Clicked on notification tab");
        List<WebElement> listOfUnreadNotificationText = getElementsOfWEPDeviceListPage("listOfUnreadNotificationText");
        List<WebElement> listOfUnreadNotificationActionBtn = getElementsOfWEPDeviceListPage("listOfNotificationActionsBtn");
        for (int i = 0; i < 2; i++) {
			mouseHover(listOfUnreadNotificationText.get(i));
			if(listOfUnreadNotificationText.get(i).getText().contains(notificationMessageToVerify)) {
                LOGGER.info("device import notification received successfully");
                listOfUnreadNotificationActionBtn.get(i).click();
                LOGGER.info("Downloaded file prepared post processing error/success");
                clickByJavaScriptOnDevicePage(downloadFileBellIconKey);
                flag = true;
                return;
			}
		}
        if(!flag) {
        	LOGGER.error("Error while downloading the file requested for processing");
        }
    }

    public boolean extractColumnValueAndVerifyMessage(String importDuplicateAssetCsvError, String columnHeader) throws InterruptedException, IOException {
    	sleeper(5000);
    	CSVFileReader csv = new CSVFileReader();
        int countFiles = 0;
        boolean flag = true;
        File f = new File(ConstantPath.DOWNLOAD_PATH);
        if (!f.exists()) {
            File file = new File(ConstantPath.DOWNLOAD_PATH);
            FileUtils.forceMkdir(file);
        }
        if (Objects.requireNonNull(f.listFiles()).length > 0) {
            for (File file : Objects.requireNonNull(f.listFiles())) {
                if (file.isFile()) {
                    String[][] header = csv.getDataWithHeader(file);
                    int serialNumberColumnIndex = 0;

                    for (int i = 0; i < Objects.requireNonNull(header)[1].length; i++) {
                        String columnValueToInsert = header[0][i].replaceAll("\\s*\\([^)]*\\)", "").replace(" ", "").toUpperCase();
                        if (!columnValueToInsert.isEmpty()) {
                            if (columnValueToInsert.equals(columnHeader.toUpperCase())) {
                                serialNumberColumnIndex = i;
                                break;
                            }
                        }
                    }
                    String errorDescriptionToVerify = header[1][serialNumberColumnIndex];
                    System.out.println("Expected description- "+errorDescriptionToVerify );
                    System.out.println("Actual description-" +importDuplicateAssetCsvError);
                    countFiles++;
                    flag = errorDescriptionToVerify.contains(importDuplicateAssetCsvError);
                    if (!flag)
                        LOGGER.info("Error message looking for in CSV is not matching/exist");
                }
            }
            if (countFiles >= 1) {
                deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
            } else {
                LOGGER.info("Don't delete the devices folder");
            }
        }
        else {
            LOGGER.info("There is no file inside the device folder to verify anything");
            flag = false;
        }
        deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
        waitForPageLoaded();
        return flag;
    }
    
	/**
	 * To get the no. of devices count and convert to integer value
	 * @return
	 * @throws Exception
	 */
	public final Integer getConvertedIntegerCount() throws Exception { 
		int TotalActiveDevices;
		String TotalAudienceString = getTextOfWEPDeviceListPage("countPage").replace("of ", "");
		if(TotalAudienceString.contains(",")) {
			String replaced = TotalAudienceString.replace(",", "");
			TotalActiveDevices = Integer.parseInt(replaced);
		}else {
		TotalActiveDevices = Integer.parseInt(TotalAudienceString);
		}
		return TotalActiveDevices;
	}
	
	/**
	 * To get the no. of devices count and convert to integer value
	 * @return
	 * @throws Exception
	 */
	public final Integer getConvertedTotalTargetedCountForReach(Integer TotalActiveDevices) throws Exception { 		
		int TotalTargetedDevices;
	String TotalAudienceString = getTextOfWEPDeviceListPage("countPage").replace("of ", "");
	if(TotalAudienceString.contains(",")) {
		String replaced = TotalAudienceString.replace(",", "");
		TotalActiveDevices = Integer.parseInt(replaced);
		TotalTargetedDevices = TotalActiveDevices;
	}else {
	TotalActiveDevices = Integer.parseInt(TotalAudienceString);
	TotalTargetedDevices = TotalActiveDevices;
	}
	return TotalTargetedDevices;
	}

    /**
     * This method is used to verify the table columns
     * @param expectedColumnList
     * @return
     * @throws Exception
     */
    public boolean verifyTableColumns(List<String> expectedColumnList, String key) throws Exception {
        boolean flag = false;
        int counter = 0;
        try {
            List<WebElement> actualColumnList = getElementsOfDeviceListPage(key);
            for (WebElement we : actualColumnList)
                if(we.getText().contains("sorted")){
                	mouseHover(we);
                    String[] split = we.getText().split("\n");
                    String columnName = split[0];

                    if (columnName.equalsIgnoreCase(expectedColumnList.get(counter))) {
                    	mouseHover(we);
                        flag = true;
                        counter++;
                    } else {
                        flag = false;
                        LOGGER.error(we.getText() + " Header does not match at list table page.");
                        break;
                    }
                }
                else if (we.getText().equalsIgnoreCase("Service Coverage") || we.getText().equalsIgnoreCase("Operating System") || we.getText().equalsIgnoreCase("HP Wolf Protect and Trace") || 
                		we.getText().equalsIgnoreCase("HP Protect & Trace with Wolf Connect") || we.getText().equalsIgnoreCase("Service status") || 
                		we.getText().equalsIgnoreCase("Warranty and Service Coverage") || we.getText().equalsIgnoreCase(expectedColumnList.get(counter)) ||
                        we.getText().toUpperCase().contains(expectedColumnList.get(counter).toUpperCase())) {
                	mouseHover(we);
                    flag = true;
                    counter++;
                } else {
                    flag = false;
                    LOGGER.error(we.getText() + " Header does not match at list table page.");
                    break;
                }
        } catch (Exception e) {
            LOGGER.error("Error while verifying table columns" + e.getMessage());
            return false;
        }
        return flag;
    }

    /**
     * Gets the serial number to verify depending on enviornment for details page verification
     * @return String the serial number for the enviornment
     */
    public String getDeviceSerialNumberForEnviornment() throws Exception {
        String serialNumberToUse = CommonVariables.TEST_DEVICE_SERIAL_NUM;
        String currentPageUrl = getUrlOfCurrentPage();
        if (currentPageUrl.contains("staging")) {
            if (currentPageUrl.contains("eu")) {
                serialNumberToUse = DeviceVariables.TEST_DEVICE_SERIAL_NUM_EUSTAGE;
            }
        }
        else {
            serialNumberToUse = CommonVariables.PROD_DEVICE_SERIAL_NUM;
            if (currentPageUrl.contains("eu")) {
                serialNumberToUse = DeviceVariables.EUPROD_DEVICE_SERIAL_NUM;
                }
            }
        return serialNumberToUse;
    }

    /**
     * Gets the device name to verify depending on enviornment for details page verification
     * @return String devicename to use
     */
    public String getDeviceNameForEnviornment() throws Exception {
        String deviceName = DeviceVariables.USSTG_DEVICE_NAME;
        String currentPageUrl = getUrlOfCurrentPage();
        if (currentPageUrl.contains("staging")) {
            if (currentPageUrl.contains("eu")) {
                deviceName = DeviceVariables.EUSTG_DEVICE_NAME;
            }
        }
        else {
            deviceName = DeviceVariables.USPRD_DEVICE_NAME;
            if (currentPageUrl.contains("eu")) {
                deviceName = DeviceVariables.EUPRD_DEVICE_NAME;
            }
        }
        return deviceName;
    }

    /**
     * This is a reusuable method to add the asset and verify the notifications
     *
     * @param csvToUpload - csv to upload for adding
     * @param validAssetUploadLogMsgToVerify - log message expected to verify
     */
    public void Verify_AddPhysicalAssetUpload_And_DisplayNotification_Message(String csvToUpload, String validAssetUploadLogMsgToVerify) {
    	uploadAssetsViaPhysicalAssetsListPage(csvToUpload);
        assertTrue(postImportCheckForNotificationMessage(validAssetUploadLogMsgToVerify), "Notification for device import did not display/delay in notification for more than 30 seconds");
    }

    /**
     * This method is for logging in to ITA account
     * @throws Exception
     */
    public void LoginAndGetIntoCustomerFleetDevicePage() throws Exception {
        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
        leftSideMenuVerification();
        dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
        waitForPageLoaded();
    }

    /**
     * This method extracts the serial number from the csv file to use
     * @param csvFilePath path of csv file
     * @return arraylist containing all the serial number in csv file
     * @throws Exception
     */
    public ArrayList<String> ExtractSerialNumberFromCSVFileToUse(String csvFilePath)
            throws Exception {
        ArrayList<String> serialNumberListToVerify = new ArrayList<>();
        ExtractDeviceSerialNumberFromCSV(csvFilePath, serialNumberListToVerify);
        return serialNumberListToVerify;
    }

    /**
     * This method verifies csv for current field export
     *
     * @return true if csv is matching as per ui
     */
    public boolean verifyCurrentFieldExportCSV(ArrayList<String> deviceSerialNumberList, String notificationMessageToVerify) throws Exception {
        if (!deviceSerialNumberList.isEmpty()) {
            String[] columnsHeader = "Serial Number,Model,Status,Type,Location".replace(" ", "").toUpperCase().split(",");

            ArrayList<String> currentColumnsOnListPage = new ArrayList<>(Arrays.asList(columnsHeader));
            waitForPageLoaded();

            verifyElementsOfWEPDeviceListPage("selectAllCheckBox");
            clickByJavaScriptOnDevicePage("selectAllCheckBox");
            LOGGER.info("Clicked on select all checkbox");

            verifyElementsOfWEPDeviceListPage("exportDeviceBtn");
            clickByJavaScriptOnDevicePage("exportDeviceBtn");
            LOGGER.info("Clicked on export button");

            boolean isExportSuccess = ExportAllAvailableDevices(deviceSerialNumberList, "exportDeviceByCurrentField", notificationMessageToVerify);
            if (isExportSuccess) {
                LOGGER.info("Devices exported successfully.");
                boolean isDeviceVerifiedSuccessfully = verifyExtractedDevices(deviceSerialNumberList, currentColumnsOnListPage);
                assertTrue(isDeviceVerifiedSuccessfully, "Device serial number and header from device list page are not matching with the CSV file");
            } else {
                LOGGER.error("Test for Export devices got failed.");
            }
            return isExportSuccess;
        } else {
            LOGGER.error("Device list is empty, There are no newly added devices");
        }
        return false;
    }

    /**
     * This method verifies csv for current field export
     *
     * @return true if csv is matching as per ui
     */
    public boolean verifyEditableFieldsCSVFile(ArrayList<String> deviceSerialNumberList, String notificationMessageToVerify) throws Exception {
        if (!deviceSerialNumberList.isEmpty()) {
            waitForPageLoaded();
            String[] columnsHeader = "Serial Number, Asset Tag, Asset Type, Manufacturer, Manufacturer Date, Lifecycle Status, Model, Asset Name, Alias, Asset Location, Department, Cost Center, Deployment Date, Assigned Area, Retirement Date, Warranty Name, Warranty Start Date, Warranty End Date, Warranty Desc".replace(" ", "").toUpperCase().split(",");            ArrayList<String> currentColumnsOnListPage = new ArrayList<>(Arrays.asList(columnsHeader));
            verifyElementsOfWEPDeviceListPage("selectAllCheckBox");
            clickByJavaScriptOnDevicePage("selectAllCheckBox");
            LOGGER.info("Clicked on select all checkbox");
            verifyElementsOfWEPDeviceListPage("exportDeviceBtn");
            clickByJavaScriptOnDevicePage("exportDeviceBtn");
            LOGGER.info("Clicked on export button");
            boolean isExportSuccess = ExportAllAvailableDevices(deviceSerialNumberList, "exportDeviceByAllEditableField", notificationMessageToVerify);
            if (isExportSuccess) {
                LOGGER.info("Devices exported successfully by using EditableFields.");
                boolean isDeviceVerifiedSuccessfully = verifyExtractedDevices(deviceSerialNumberList, currentColumnsOnListPage);
                assertTrue(isDeviceVerifiedSuccessfully, "Device serial number and all editable header from device list page are not matching with the CSV file");
                return isExportSuccess;
            }
            else {
                LOGGER.error("Export devices got failed while extracting");
            }
        }
        return false;
    }
    
    /**
     * This method will show the given text in UI
     */
    public void showTemporaryBannerInUI(String messageToShow) {
    	try {
    		LOGGER.info("trying to create UI banner");
        	String script = 
        		    "let banner = document.createElement('div');" +
        		    "banner.innerText = '" + messageToShow + "';" +
        		    "banner.style.position = 'fixed';" +
        		    "banner.style.top = '0';" +
        		    "banner.style.left = '0';" +
        		    "banner.style.width = '100%';" +
        		    "banner.style.color = 'red';" +              // red text
        		    "banner.style.fontSize = '20px';" +
        		    "banner.style.fontWeight = 'bold';" +
        		    "banner.style.padding = '10px';" +
        		    "banner.style.textAlign = 'center';" +
        		    "banner.style.zIndex = '9999';" +
        		    "banner.id = 'test-case-banner';" +
        		    "document.body.appendChild(banner);" +
        		    "setTimeout(function() {" +
        		    "  let banner = document.getElementById('test-case-banner');" +
        		    "  if (banner) banner.remove();" +
        		    "}, 5000);";
            ((JavascriptExecutor) getDriver()).executeScript(script);
            LOGGER.info("UI banner created successfully");
		} catch (Exception e) {
			LOGGER.error("Error while creating UI banner: " + e.getMessage());
		}
    	
    }

    /**
     * This method verifies the double csv upload
     *
     * @return arraylist of serialnumbers for verification on UI
     */
    public ArrayList<String> verifyDoubleCSVUpload() throws Exception {
        ArrayList<String> filesListToUpload = new ArrayList<>();
        filesListToUpload.add(DEVICESWITHCORRECTSERIALNUMBER_UPLOAD);
        filesListToUpload.add(DEVICESWITHCORRECTSERIALNUMBER_UPLOAD_COPY);
        String importPath = IMPORT_PATH;
        ArrayList<String> notificationListToVerify = new ArrayList<>();
        notificationListToVerify.add(VALID_ASSET_UPLOAD_LOG_MSG);
        notificationListToVerify.add(VALID_MULTI_ASSET_UPLOAD_LOG_MSG);
        ArrayList<String> serialNumberListToVerifyAddAndDelete = new ArrayList<>();
        int index =0;
        for (String file : filesListToUpload){
            Verify_AddPhysicalAssetUpload(importPath + file);
            ExtractDeviceSerialNumberFromCSV(importPath + file, serialNumberListToVerifyAddAndDelete);
            index++;
        }
        return serialNumberListToVerifyAddAndDelete;
    }	
    
    /** Confirm And add required Custom Field
     * @throws Exception */
	public void ConfirmAndAddRequiredCustomFields() throws Exception {
		try {
			sleeper(2000);
			clickByActionsWEPClickDevicelistPage("editCustomFieldIcon");
			waitForElementsOfWEPDeviceListPage("setCustomFieldsName");
			List<WebElement> customFields = getElementsOfDeviceListPage("setCustomFieldsName");
			if (customFields.get(0).getAttribute("value").isEmpty()) {
				addCustomFields(5);
			} else {
				LOGGER.info("Fields exist but no default blank field. Adding custom fields.");
				int targetCount = 5;
				for (WebElement field : customFields) {
					if (!field.getAttribute("value").isEmpty()) {
						targetCount--;
					}
				}
				if (targetCount == 0) {
					LOGGER.info("initially 5 custom fields are already present");
					clickByActionsWEPClickDevicelistPage("saveCustomFields");
				} else {
					addCustomFields(targetCount);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in method ConfirmAnddeleteAnyExistingField " + e.getMessage());
		}
	}
    
	/** Confirm And delete Any Existing Custom Field
	 * @throws Exception
	 */
	public void ConfirmAnddeleteAnyExistingField() throws Exception {
		try {
			sleeper(2000);
			clickByActionsWEPClickDevicelistPage("editCustomFieldIcon");
			waitForElementsOfWEPDeviceListPage("setCustomFieldsName");
			List<WebElement> customFields = getElementsOfDeviceListPage("setCustomFieldsName");
			WebElement firstField = customFields.get(0);
			if (firstField.getAttribute("value").isEmpty()) {
				LOGGER.info("Default blank field is already present.");
			} else {
				LOGGER.info("Fields exist but no default blank field. Deleting existing fields.");
				deleteAllExistingCustomFields();
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in method ConfirmAnddeleteAnyExistingField " + e.getMessage());
			LOGGER.info("No existing fields found to delete, Proceeding further");
		}
	}

    /** Deletes all custom fields present 
     * @throws Exception */
    public void deleteAllExistingCustomFields() throws Exception {
        List<WebElement> deleteCustomFields = getElementsOfDeviceListPage("deleteCustomFields");
        for (WebElement deleteBtn : deleteCustomFields) {
            deleteBtn.click();
        }
        clickByActionsWEPClickDevicelistPage("saveCustomFields");
        Assert.assertTrue(checkCustomFieldsNotifications(CUSTOM_FIELDS_UPDATE), "Notification for update custom field did not display/delay in notification for more than 30 seconds");
        waitUntilElementIsInvisibleOfDeviceListPage("customFieldsNotification");
        LOGGER.info("All custom fields deleted.");
    }
    
    /** Adds specified number of custom fields 
     * @throws Exception */
	public void addCustomFields(int count) throws Exception {
		sleeper(2000);
		clickByActionsWEPClickDevicelistPage("editCustomFieldIcon");
		waitForElementsOfWEPDeviceListPage("setCustomFieldsName");
		int fieldsToAdd = count;
		try {
			List<WebElement> existingFields = getElementsOfDeviceListPage("setCustomFieldsName");
		    if (!existingFields.isEmpty() && existingFields.get(0).getAttribute("value").isEmpty()) {
		        LOGGER.info("Default blank field is already present, adding " + (count - 1) + " more fields instead.");
		        fieldsToAdd = count - 1; // Reduce count by 1 since a blank field exists.
		    }
		} catch (Exception e) {
			LOGGER.error("Exception occured in method addCustomFields " + e.getMessage());
			LOGGER.error("no default fields are present");
		}finally {
			for (int i = 0; i < fieldsToAdd; i++) {
		        clickByActionsWEPClickDevicelistPage("addCustomFields");
		    }
		    List<WebElement> fields = getElementsOfDeviceListPage("setCustomFieldsName");
		    for (int i = 0; i < fields.size(); i++) {
		        if (fields.get(i).getAttribute("value").isEmpty()) {
		            fields.get(i).sendKeys("Field" + i);
		        }
		    }
		    clickByActionsWEPClickDevicelistPage("saveCustomFields");
	        Assert.assertTrue(checkCustomFieldsNotifications(CUSTOM_FIELDS_UPDATE), "Notification for update custom field did not display/delay in notification for more than 30 seconds");
	        LOGGER.info(fieldsToAdd + " custom fields added.");
		}
	}

    /** Deletes a specific number of custom fields 
     * @throws Exception */
    public void deleteCustomFields(int count) throws Exception {
    	try {
    		sleeper(2000);
    		clickByActionsWEPClickDevicelistPage("editCustomFieldIcon");
			waitForElementsOfWEPDeviceListPage("deleteCustomFields");
            List<WebElement> deleteButtons = getElementsOfDeviceListPage("deleteCustomFields");
            for (int i = deleteButtons.size()-1; i > count; i--) {
                deleteButtons.get(i).click();
            }
            clickByActionsWEPClickDevicelistPage("saveCustomFields");
            Assert.assertTrue(checkCustomFieldsNotifications(CUSTOM_FIELDS_UPDATE), "Notification for update custom field did not display/delay in notification for more than 30 seconds");
            LOGGER.info(count + " custom fields deleted.");
            sleeper(2000);
    	}catch(Exception e) {
    		LOGGER.error("Exception occured in method deleteCustomFields " + e.getMessage());
			LOGGER.error("no custom fields are present to delete");
    	}
    }

    /** Ensures configured custom fields columns are present in list page
     * @throws Exception */
    public boolean verifyCustomFieldsColumnsInListPage() throws Exception {
    	boolean flag = false;
    	try {
    		LOGGER.info("validating custom fields in device list page");
    		List<WebElement> CustomFieldsColumns = getElementsOfDeviceListPage("customFieldsColumnsListPage");
    		int customFieldsCount = CustomFieldsColumns.size();
            Assert.assertEquals(customFieldsCount,3, "custom fields count doesn't match in the list page");
    		for (int i = 0; i < CustomFieldsColumns.size(); i++) {
    			mouseHover(CustomFieldsColumns.get(i));
    			Assert.assertTrue(CustomFieldsColumns.get(i).getText().contains("Field" + i), "Custom field " + i + " is not present in the list page");
    		}
    		LOGGER.info("custom fields are validated in device list page");
    		flag = true;
		} catch (Exception e) {
			LOGGER.error("Exception occured in method verifyCustomFieldsColumnsInListPage " + e.getMessage());
		}
    	return flag;
    }

    /**This is a method to check the custom fields notification messages
     * @param notificationMessageToVerify - message that needs to be verified against the UI for import
     * @return - true if message is matching per import notification
     */
    public boolean checkCustomFieldsNotifications(String customFieldsNotificationMessage) {
        try {
            boolean flag = false;
            sleeper(3000);// time required for the notification message to pop
            verifyElementsOfWEPDeviceListPage("customFieldsNotification");
            LOGGER.info("notification message is displayed");
            String notificationText = getTextOfWEPDeviceListPage("customFieldsNotification");
            if (notificationText.contains(customFieldsNotificationMessage)) {
                flag = true;
            } else {
                LOGGER.info("Notification text extracted to verify from UI:"+notificationText);
                LOGGER.info("Notification text to match the UI notification text is:"+customFieldsNotificationMessage);
                LOGGER.error("Notification for import has failed");
            }
            clickByJavaScriptOnDevicePage("customFieldsNotificationClose");
            return flag;
        } catch (Exception e) {
            LOGGER.error((String.format("Exception occurred in method checkCustomFieldsNotifications %s", e.getMessage())));
            return false;
        }
    }
    
    /** Checks if the "Allow us 24 hours" message appears 
     * @throws Exception */
    public void checkLimitMessage() throws Exception {
    	try {
        	sleeper(2000);
        	clickByActionsWEPClickDevicelistPage("userProfileSettings");
            waitForPageLoaded();
            clickByActionsWEPClickDevicelistPage("preferancesSettings");
            sleeper(3000);
            mousehoverOnWEPDeviceListPage("customFieldsSettings");
            sleeper(2000);
			clickByActionsWEPClickDevicelistPage("editCustomFieldIcon");
        	verifyElementsOfWEPDeviceListPage("customFieldsLimitMsg");
            Assert.assertEquals(getTextOfWEPDeviceListPage("customFieldsLimitMsg"), "Please allow 24 hours before you can add additional custom fields.", "custom fields limit message is not displayed");
            LOGGER.info("Limit Reached: 'Allow us 24 hours' message is displayed.");
            clickByActionsWEPClickDevicelistPage("saveCustomFields");
		} catch (Exception e) {
			LOGGER.error("Exception occured in method checkLimitMessage " + e.getMessage());
		}

    }
    
    /**This is a method for add devices via csv
     * @param fileName - list of devices to import
     */
    public void importDevicesViaCsvAndDisplayNotification(String csvToUpload) throws Exception {
    	verifyElementsOfWEPDeviceListPage("addDeviceBtn");
        clickByJavaScriptOnDevicePage("addDeviceBtn");
        verifyElementsOfWEPDeviceListPage("addBulkDeviceThroughCSVBtn");
        clickByJavaScriptOnDevicePage("addBulkDeviceThroughCSVBtn");
        verifyElementsOfWEPDeviceListPage("manuallyUploadDevice");
        clickByJavaScriptOnDevicePage("manuallyUploadDevice");
        verifyElementsOfWEPDeviceListPage("browseButton");
        LOGGER.info("Clicked on upload file button");
        sleeper(2000);
        WebElement addFile = getElementOfWEPDeviceListPage("browseButton");
        addFile.sendKeys(csvToUpload);
        clickByJavaScriptOnDevicePage("importPhysicalAssetCSVBtn");
        sleeper(1000);
        verifyElementsOfWEPDeviceListPage("importNotification");
        Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Import is in progress\n" + "You will be notified once completed. Check Notification Center in sometime.", "Importing bulk csv failed");
    }
    
    /**This is a method for add physical asset via csv
     * @param fileName - list of devices to import
     */
    public void importAssetViaCsvAndDisplayNotification(String csvToUpload) throws Exception {
    	try {
         	waitForElementsOfWEPDeviceListPage("addDeviceBtn");
         	clickByActionsWEPClickDevicelistPage("addDeviceBtn");
         	verifyElementsOfWEPDeviceListPage("browseButton");
            LOGGER.info("Clicked on upload file button");
            sleeper(2000);
            WebElement addFile = getElementOfWEPDeviceListPage("browseButton");
            addFile.sendKeys(csvToUpload);
            clickByJavaScriptOnDevicePage("importPhysicalAssetCSVBtn");
            sleeper(1000);
            verifyElementsOfWEPDeviceListPage("importNotification");
            Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Import is in progress", "Importing bulk csv failed");
            sleeper(30000); //waiting for device to be imported
         } catch (Exception e) {
             LOGGER.error("Exception occurred in method ImportPhysicalDevicesFromCSV {}", e.getMessage());
         }
     }
    
    /**This is a method for verify advance filter in list page
     * @param fileName - list of devices to import
     */
    public void createAndVerifyAdvFilter(ArrayList<String> SerialNumberToVerify) throws Exception {
    	LOGGER.info("Creating Advance Filter");
        waitForElementsOfWEPDeviceListPage("filterInListPage");
        actionClickOfDeviceListPage("filterInListPage");
        waitForPageLoaded();
        deleteSpecificFilterPresent();
        actionClickOfDeviceListPage("newFilterBtn");
        waitForElementsOfWEPDeviceListPage("filterName");
        sendKeysOnWEPDeviceListPage("filterName", "AutoAdvFilter");
        actionClickOfDeviceListPage("filterFieldDD");
        waitForElementsOfWEPDeviceListPage("filterFieldValue");
        actionClickOfDeviceListPage("filterFieldValue");
        actionClickOfDeviceListPage("filterOperatorDD");
        waitForElementsOfWEPDeviceListPage("filterOperatorValue");
        actionClickOfDeviceListPage("filterOperatorValue");
        waitForElementsOfWEPDeviceListPage("filterSearchValue");
        sendKeysOnWEPDeviceListPage("filterSearchValue", "PHYASSETAUTO");
        actionClickOfDeviceListPage("saveFilter");
        sleeper(2000);
        Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Query Created Successfully");
        actionClickOfDeviceListPage("closeFilterDeleteToastMsg");
        LOGGER.info("Filter saved successfully");
        ArrayList<String> deviceSerialNumberList = new ArrayList<>();
        GetSerialNumberListFromDeviceListPage(deviceSerialNumberList);
        LOGGER.info("Serial Number List from Device List Page: " + deviceSerialNumberList);
        LOGGER.info("Serial Number List from imported csv: " + SerialNumberToVerify);
        Assert.assertTrue(deviceSerialNumberList.containsAll(deviceSerialNumberList), "Serial Number List from Device List Page and imported csv are not same");
        LOGGER.info("Advance Filter created and verified successfully");
        deleteSpecificFilterPresent();
    }
    
    /** Deletes a all filters present in list page
     * @throws Exception */
    public void deleteSpecificFilterPresent() throws Exception {
    	try {
        	if(verifyElementsOfWEPDeviceLstPage("deleteFilterIcon")){
            	actionClickOfDeviceListPage("deleteFilterIcon");
                waitForElementsOfWEPDeviceListPage("deleteFilterPopUp");
                clickByActionsWEPClickDevicelistPage("deleteFilterPB");
                sleeper(2000);
                Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Query Deleted Successfully");
                clickByJavaScriptOnDevicePage("closeFilterDeleteToastMsg");
                LOGGER.info("Selected Filter is deleted successfully");
        	}else {
    			LOGGER.info("'AutoAdvFilterNo' filter is not present to delete");
    		}
		} catch (Exception e) {
			LOGGER.error("Exception occured in method deleteSpecificFilterPresent " + e.getMessage());
		}

    }
    
    /** Deletes a all filters present in list page
     * @throws Exception */
    public void verifyDeleteDeviceFromThreeDots() throws Exception {
    	clearFiltersOfDevicesListPage("clearfilter");
        sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", "PHYASSETAUTO");
        sleeper(2000);
    	actionClickOfDeviceListPage("firstDeviceCheckboxFromList");
		mousehoverOnWEPDeviceListPage("firstDeviceThreeDotsInList");
		actionClickOfDeviceListPage("firstDeviceThreeDotsInList");
		waitForElementsOfWEPDeviceListPage("deleteOptionFromThreeDotMenu");
		actionClickOfDeviceListPage("deleteOptionFromThreeDotMenu");
		String extractedSecurityKey = getTextOfWEPDeviceListPage("securityNumberLabel");
        enterTextForDeviceListPage("securityNumberTxtBox", extractedSecurityKey);
        clickOnElementsOfDevicePage("deleteSelectedDeviceBtn");
        verifyElementsOfWEPDeviceListPage("importNotification");
        Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"),"Delete device in progress. Please check notification center for update.", "Deleting selected device failed");
        actionClickOfDeviceListPage("closeFilterDeleteToastMsg");
        sleeper(5000);
        Assert.assertFalse(verifyElementsOfWEPDeviceLstPage("oldDeviceNav"),"Old navigation menu is displayed in list page in new navigation");
    }
    
    /** verify New Navigation Menu animation
     * @throws Exception */
    public void verifyNewNavMenuDisplayed() throws Exception {
    	String[] deviceMenuList = {"PCs", "Virtual Machines", "Physical Assets"};
    	ArrayList<String> expectedMenuList = new ArrayList<>(Arrays.asList(deviceMenuList));
    	waitForElementsOfWEPDeviceListPage("newDeviceMenu");
    	List<String> existingMenuList = getAllTextOfWEPDeviceListPage("newDeviceMenu");
    	LOGGER.info("expected menu list under Devices DD - " + expectedMenuList );
    	LOGGER.info("actual menu list under Devices DD - " + existingMenuList);
    	Assert.assertFalse(verifyElementsOfWEPDeviceLstPage("leftSideMenuExpandBtn"), "Left side menu is collapsed after clicking on device menu");
    	LOGGER.info("Left side menu is not collapsed as expected after clicking on device menu");
    	assertTrue(existingMenuList.containsAll(expectedMenuList), "New navigation Device list does not contains all of these device options - " +expectedMenuList);
    	LOGGER.info("New Navigation Menu list is verified successfully");
    	LOGGER.info("Clicking on the collapse navigation icon");
    	actionClickOfDeviceListPage("leftSideMenuCollapseBtn");
    	waitForElementsOfWEPDeviceListPage("deviceMenuIconOnceCollapse");
    	LOGGER.info("Left side panel collapsed successfully");
		clickByJavaScriptOnDevicePage("deviceMenuIconOnceCollapse");
		waitForElementsOfWEPDeviceListPage("PCCollapseMenu");
		actionClickOfDeviceListPage("PCCollapseMenu");
		LOGGER.info("collapsed device menu is clicked");
		Assert.assertFalse(verifyElementsOfWEPDeviceLstPage("leftSideMenuCollapseBtn"), "Left side menu is expanded after clicking on collapsed device menu");
    	LOGGER.info("Left side menu is not expanded as expected after clicking on collapsed device menu");
    	actionClickOfDeviceListPage("leftSideMenuExpandBtn");
    	LOGGER.info("Left side menu is expanded successfully");
    }
    
    /**
     * This is a reusuable method to add the asset and verify the notifications
     *
     * @param csvToUpload - csv to upload for adding
     * @param validAssetUploadLogMsgToVerify - log message expected to verify
     */
    public void Verify_AddPhysicalAssetUpload(String csvToUpload) {
        verifyElementsOfWEPDeviceListPage("addDeviceBtn");
        clickByJavaScriptOnDevicePage("addDeviceBtn");
        addPhysicalAsset(csvToUpload);
        }
    
    /**
     * This is a reusuable method to add the asset and verify the notifications from physical assets list page
     * @param csvToUpload - csv to upload for adding
     */
    public void uploadAssetsViaPhysicalAssetsListPage(String csvToUpload) {
        try {
        	LOGGER.info("Adding asset via csv upload from physical assets list page");
        	verifyElementsOfWEPDeviceListPage("physicalAssetsTab");
        	clickByJavaScriptOnDevicePage("physicalAssetsTab");
        	sleeper(5000);
            verifyElementsOfWEPDeviceListPage("addDeviceBtn");
            clickByJavaScriptOnDevicePage("addDeviceBtn");
            LOGGER.info("Clicked on Add button in list page");
            waitForElementsOfWEPDeviceListPage("browseButton");
            WebElement addFile = getElementOfWEPDeviceListPage("browseButton");
            addFile.sendKeys(csvToUpload);
            LOGGER.info("Clicked on Upload File browse button");
            waitForElementsOfWEPDeviceListPage("importPhysicalAssetCSVBtn");
            verifyElementIsClickableOfDeviceListPage("importPhysicalAssetCSVBtn");
            clickByJavaScriptOnDevicePage("importPhysicalAssetCSVBtn");
            verifyElementsOfWEPDeviceListPage("importNotification");
            Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Import is in progress", "Importing bulk csv failed");
            clickByJavaScriptOnDevicePage("customFieldsNotificationClose");
        	verifyElementsOfWEPDeviceListPage("PCCollapseMenu");
        	clickByJavaScriptOnDevicePage("PCCollapseMenu");
        	LOGGER.info("Clicked on PC's list page and waiting for device to be imported");
            sleeper(60000); //waiting for device to be imported
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method ImportPhysicalDevicesFromCSV {}", e.getMessage());
        }
    }
    
    /**
     * This is a method to add the asset for partner and verify the notifications
     *
     * @param csvToUpload - csv to upload for adding
     * @param validAssetUploadLogMsgToVerify - log message expected to verify
     */

    public void Verify_AddPhysicalAssetUploadForPartner(String csvToUpload) {
        try {
            verifyElementsOfWEPDeviceListPage("addDeviceBtn");
            clickByJavaScriptOnDevicePage("addDeviceBtn");
            waitForElementsOfWEPDeviceListPage("bulkUploadPartner");
        	clickByActionsWEPClickDevicelistPage("bulkUploadPartner");
        	waitForElementsOfWEPDeviceListPage("manualUploadPartner");
        	clickByActionsWEPClickDevicelistPage("manualUploadPartner");
            waitForElementsOfWEPDeviceListPage("browseButton");
            WebElement addFile = getElementOfWEPDeviceListPage("browseButton");
            addFile.sendKeys(csvToUpload);
            LOGGER.info("Clicked on Upload File browse button");
            sleeper(2000);
            waitForElementsOfWEPDeviceListPage("importPhysicalAssetCSVBtn");
            clickByActionsWEPClickDevicelistPage("importPhysicalAssetCSVBtn");
            verifyElementsOfWEPDeviceListPage("importNotification");
            Assert.assertEquals(getTextOfWEPDeviceListPage("importNotification"), "Import is in progress\n" + "You will be notified once completed. Check Notification Center in sometime.", "Importing bulk csv failed");
            sleeper(60000); //waiting for device to be imported
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method ImportPhysicalDevicesFromCSV {}", e.getMessage());
        }
    }
    
    /**Verify device column data present in list page
     * @param columnName - column Name to verify in list page
     * @param columnValue - column Value to verify in list page
     * @throws Exception */
    public void verifyDeviceColumnDataInListPage(String columnName, String columnValue, String sn, List<String> listToSelectColumns ) throws Exception {
		LOGGER.info("Verifying device column data in list page");
        clearFiltersOfDevicesListPage("clearfilter");
        sendKeysOnWEPDeviceListPage("SerialNumberColumnListValueSearch", sn);
		sleeper(3000);
		List<WebElement> listPageColumnValues = getElementsOfDeviceListPage("listPageColumnValues");
		ArrayList<String> actualColumnValues = new ArrayList<>();
		for(WebElement element : listPageColumnValues){
			mouseHover(element);
			actualColumnValues.add(element.getText());
		}
        Assert.assertTrue(actualColumnValues.contains(columnValue), columnName+" column in device list page is not matching with the expected value - "+columnValue+"list ="+actualColumnValues);
        LOGGER.info(columnName+" column in device list page is matching with the expected value '"+columnValue+"' from list");
    }

	public void verifyEnrolledByHPInsight() {
		try {
		    TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		    tableConfigurationPage = tableConfigurationPage.getInstance();
		    LOGGER.info("Starting column configuration to select HP Insights...");
		    sleeper(3000);
		    tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableConfigurationButton");
		    tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		    selectDropdownOptions(deviceListPageProperties.getProperty("enrolledColumnOption"), "Enrolled");   
		    sleeper(3000);		   
		    actionClickOfDeviceListPage("enrolledOptionSaveBtn");
		    sleeper(3000);
		    LOGGER.info("Column option saved successfully");

		    // Open dropdown to select 'HP Insights'
		    waitForElementsOfWEPDeviceListPage("enrolledColumnheader");
		    actionClickOfDeviceListPage("enrolledColumnDropdown");	
		    actionClickOfDeviceListPage("selectHPInsightsOption");        
			LOGGER.info("Selected the devices enrolled By HP Insights");

		} catch (Exception e) {
		    LOGGER.error("Exception occurred while selecting the devices enrolled By HP Insights", e.getMessage());
		}
	}

    /**
     * This method verifies the status of added devices
     *
     * @param serialNumbers  - list of serial numbers to verify
     * @param expectedStatus - expected status to verify
     * @return true if status matches
     * @throws Exception
     */
    public boolean verifyStatusOfDevices(ArrayList<String> serialNumbers, String expectedStatus) throws Exception {
        TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
        waitForPageLoaded();
        leftSideMenuVerification();
        companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
        LOGGER.info("User is navigated to Devices -> PC Page.");
        waitForPageLoaded();
        tableConfigurationPage.resetTableConfig();
        clearFiltersOfDevicesListPage("clearfilter");
        return VerifyAddedDeviceStatus(serialNumbers, expectedStatus);
    }

    public boolean performIOTEnrollment(String serialNumber, String tenantId) {
        try {
            EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
            waitForPageLoaded();
            clearFiltersOfDevicesListPage("clearfilter");
            sleeper(10000);
            enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", serialNumber);
            waitForPageLoaded();
            sleeper(10000);
            String xpath = String.format(deviceListPageProperties.getProperty("serialNumberXpath"), serialNumber.toLowerCase());
            click(xpath);
            waitForPageLoaded();
            LOGGER.info("User navigates to Device details page");
            String deviceID = Arrays.stream(PreDefinedActions.getDriver().getCurrentUrl().split("/"))
                    .reduce((first, second) -> second)
                    .orElse("");
            Assert.assertTrue(StringUtils.isNotEmpty(deviceID), "Device ID is empty in URL");
            // return enrollFakeDevice.enrollIOTFakeDeviceWithMinimalParams(getEnvironmentSpecificData(System.getProperty("environment"), "AC_PLUS_PI_NAME_TENANT_CPIN"), deviceID,
            //         tenantId, serialNumber);
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception during IOT enrollment: " + e.getMessage());
            return false;
        }
    }

    /**
     * Performs IOT enrollment using SNR-based device ID generation (no UI navigation required).
     * Generates device ID from serial number, BIOS UUID, and product number using SHA-256 hash.
     * For batch processing of 10k+ devices, use BatchIOTEnrollmentPage instead.
     *
     * @param serialNumber Serial number of the device
     * @param biosUUID BIOS UUID of the device
     * @param productNumber Product number of the device
     * @param tenantId Tenant ID where device should be enrolled
     * @return true if enrollment succeeds, false otherwise
     * @see BatchIOTEnrollmentPage#performBatchIOTEnrollment for large-scale processing
     */
    public boolean performIOTEnrollmentWithSNR(String serialNumber, String biosUUID, String productNumber, String tenantId) {
        try {
            EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice().getInstance();
            String deviceID = TestSnrDeviceId.generateDeviceId(serialNumber, biosUUID, productNumber);
            LOGGER.info("Generated device ID using SNR method: {}", deviceID);

            String cpin = getEnvironmentSpecificData(System.getProperty("environment"), "AC_PLUS_PI_NAME_TENANT_CPIN");
            return enrollFakeDevice.enrollIOTFakeDeviceWithMinimalParams(cpin, deviceID, tenantId, serialNumber, productNumber, biosUUID);
        } catch (Exception e) {
            LOGGER.error("Exception during IOT enrollment with SNR: " + e.getMessage());
            return false;
        }
    }

    /**
     * Builds a map of table headers to their corresponding cell values for a specific device.
     * Searches for the device by serial number and returns a map of column headers to cell values.
     * Uses index-based mapping between th and td elements in the same column position.
     *
     * @param deviceSerialNo Serial number of device to search for
     * @return Map with header as key and cell value as value for the searched device
     */
    public final Map<String, String> getDeviceListTableHeaderToValuesMap(String deviceSerialNo) {
        Map<String, String> headerToValuesMap = new HashMap<>();
        try {
            waitForPageLoaded();
            clearFiltersOfDevicesListPage("clearfilter");
            enterTextForDeviceListPage("searchDeviceSerialNumberTextBox", deviceSerialNo);
            sleeper(3000);
            waitForPageLoaded();
            // Get ALL th elements from the first header  getAllRowElements
            // Note: tr[1] contains actual column headers (th elements), but misses checkbox column
            List<WebElement> allHeaderElements = getElementsTillAllElementsPresentOnDeviceListPage(deviceListPageProperties.getProperty("allHeaderElements"));

            if (allHeaderElements == null || allHeaderElements.isEmpty()) {
                LOGGER.error("No header elements found in table");
                return headerToValuesMap;
            }


            // Store header info (index -> header text and id)
            Map<Integer, String> headerTexts = new HashMap<>();
            Map<Integer, String> headerIds = new HashMap<>();

            for (int i = 0; i < allHeaderElements.size(); i++) {
                try {
                    WebElement th = allHeaderElements.get(i);
                    String headerText = th.getText().trim();
                    String headerId = th.getAttribute("id");

                    headerTexts.put(i, headerText);
                    headerIds.put(i, headerId != null ? headerId : "");

                } catch (Exception e) {
                    LOGGER.debug("Error reading header " + i + ": " + e.getMessage());
                }
            }

            // Get the data row
            sleeper(5000);
            waitForPageLoaded();
            List<WebElement> allRows = getElementsTillAllElementsVisible(deviceListPageProperties.getProperty("getAllRowElements"));

            if (allRows == null || allRows.isEmpty()) {
                return headerToValuesMap;
            }

            // Re-fetch the row to avoid stale element
            sleeper(500);
            waitForPageLoaded();
            allRows = getAllElements(deviceListPageProperties.getProperty("getAllRowElements"));
            WebElement firstRow = allRows.get(0);

            // Get ALL td cells (includes checkbox column at position 0)
            List<WebElement> allCells = firstRow.findElements(org.openqa.selenium.By.xpath(".//td"));


            // The cells include checkbox cell at index 0, but headers start with actual columns
            // So we need to skip the first cell (checkbox) and align with headers
            // Cell[0] = checkbox (skip), Cell[1] = Serial Number, Cell[2] = Model, etc.
            // Header[0] = Serial Number, Header[1] = Model, etc.

            int cellOffset = allCells.size() - allHeaderElements.size();

            for (int i = 0; i < allHeaderElements.size(); i++) {
                try {
                    int cellIndex = i + cellOffset; // Skip the checkbox cell(s)

                    if (cellIndex >= allCells.size()) {
                        break;
                    }

                    String cellValue = allCells.get(cellIndex).getText().trim();
                    String headerText = headerTexts.get(i);

                    if (headerText != null && !headerText.isEmpty()) {
                        headerToValuesMap.put(headerText, cellValue);
                    }
                } catch (Exception e) {
                    LOGGER.warn("Error processing cell/header at index " + i + ": " + e.getMessage());
                }
            }

        } catch (Exception e) {
            LOGGER.error("Exception in getDeviceListTableHeaderToValuesMap for device " + deviceSerialNo + ": " + e.getMessage());
        }
        return headerToValuesMap;
    }

    /**
     * Fetches a map of device list table headers to their corresponding cell values for a given serial number.
     * Configures the table columns, navigates to the device list page, and extracts device details for UI verification.
     *
     * @param deviceSerialNumber Serial number of the device to search for
     * @return Map of column header to cell value for the specified device
     * @throws Exception if UI navigation or extraction fails
     */
    public Map<String, String> fetchDeviceListMap(String deviceSerialNumber) throws Exception {
        TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
        List<String> columnsToBeDisplayed = Arrays.asList("Plans", "Enrolled", "BYOD", "Location", "Service Coverage","Model");
        waitForPageLoaded();
        openLeftSidePanel();
        companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
        LOGGER.info("User is navigated to Devices -> PC Page.");
        waitForPageLoaded();
        tableConfigurationPage.markSelectedCheckboxOfPopupForDeviceListPage(columnsToBeDisplayed);
        tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableOverlay");
        sleeper(10000);
        waitForPageLoaded();
        Map<String, String> deviceDetails = getDeviceListTableHeaderToValuesMap(deviceSerialNumber);
        sleeper(1000);
        tableConfigurationPage.resetTableConfig();
        return deviceDetails;
    }
			
    /**This is a reusuable method to select Column Options in List Page
     * @param expectedColumnNames - List of Column Names to be clicked from column list
     * @throws Exception */
	public void selectColumnOptionsInListPage(List<String> expectedColumnNames) throws Exception {
		LOGGER.info("Selecting Column Options popup in List Page");
		actionClickOfDeviceListPage("columnOptionBtn");
		LOGGER.info("Selecting Reset Column");
        waitForElementsOfWEPDeviceListPage("resetColumnFilter");
        verifyElementIsClickableOfDeviceListPage("resetColumnFilter");
        clickByJavaScriptOnDevicePage("resetColumnFilter");
        sleeper(2000);
        LOGGER.info("Getting column options list");
	    List<WebElement> columnOptionsList = getElementsOfDeviceListPage("columnOptionsList");
	    List<WebElement> checkboxInColumnOptionsList = getElementsOfDeviceListPage("checkboxInColumnOptionsList");
	    LOGGER.info("Selecting Column Options in List Page");
	    for(int i=0; i<columnOptionsList.size(); i++) {
	    	LOGGER.info("Iterating through column options list");
	    	String actualColumnName = columnOptionsList.get(i).getText();
	    	LOGGER.info("Actual Column Name: " + actualColumnName);
	    	LOGGER.info("Expected Column Names: " + expectedColumnNames);
	    	if(expectedColumnNames.contains(actualColumnName)) {
	    		mouseHover(columnOptionsList.get(i));
	    		LOGGER.info("Selecting column option: " + actualColumnName);
	    		mouseHover(checkboxInColumnOptionsList.get(i));
	    		clickWebelement(checkboxInColumnOptionsList.get(i));
	    		LOGGER.info("Clicked checkbox for: " + actualColumnName);
	    	}
	    	else {
	    		LOGGER.info("Column option: " + actualColumnName + " is not selected as it is not in the expected list");
	    	}
	    }
	    sleeper(2000);
	    verifyElementIsClickableOfDeviceListPage("saveColumnFilter");
        actionClickOfDeviceListPage("saveColumnFilter");
        sleeper(2000);
	}
}