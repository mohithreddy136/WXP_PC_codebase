package com.daasui.pages;
import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileInputStream;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.io.IOException;
import java.util.*;

import com.basesource.utils.ActiveCareDBValidation.UIDeviceData;
import com.basesource.utils.ActiveCareDBValidation.UIPlanCard;

public class WEPDeviceDetailsPage extends CommonMethod {

    private WEPDeviceDetailsPage instance;
    private ObjectReader deviceDetailsPagePropertiesReader = new ObjectReader();
    private Properties deviceDetailsPageProperties;
    public static HashMap<String, String> mapAdded = new HashMap<>();

    public WEPDeviceDetailsPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPDeviceDetailsPage.class) {
                if (instance == null) {
                    instance = new WEPDeviceDetailsPage(DRIVER);
                }
            }
        }
        return instance;
    }

    public static String fetchingDeviceIdFromURL() {
        String currentUrl = PreDefinedActions.getDriver().getCurrentUrl();
        String[] parts = currentUrl.split("/");
        String deviceId = parts[parts.length - 1];
        return deviceId;
    }

    /**
     * Captures complete device information including:
     * - Device ID from URL
     * - Device Status
     * - All plan cards (Warranty & Service Coverage section)
     * 
     * @return UIDeviceData object containing all captured information
     * @throws Exception if unable to capture device data
     */
    public UIDeviceData captureDeviceDetailsFromUI() throws Exception {
        LOGGER.info("=== Starting UI Device Data Capture ===");
        waitForPageLoaded();
        String deviceId = fetchingDeviceIdFromURL();
        LOGGER.info("Captured Device ID from URL: {}", deviceId);

        String deviceStatus = "UNKNOWN";
        String deviceSrNo = "UNKNOWN";
        try {
            deviceStatus = getTextOfWEPDeviceDetailsPage("deviceStatusValue").trim();
            deviceSrNo = getTextOfWEPDeviceDetailsPage("deviceSerialNumberValue").trim();
        } catch (Exception e) {
            LOGGER.warn("Unable to capture device status/serial from UI: {}", e.getMessage());
        }

        LOGGER.info("Captured Device Status: {}", deviceStatus);
        LOGGER.info("Captured Device Serial Number: {}", deviceSrNo);

        LOGGER.info("Searching for plan cards...");
        List<UIPlanCard> uiPlanCards = new ArrayList<>();

        boolean success = false;
        for (int attempt = 1; attempt <= 2 && !success; attempt++) {
            try{
                List<WebElement> nameElements = getElementsOfDeviceDetailsPage("planCardName");
                List<WebElement> statusElements = getElementsOfDeviceDetailsPage("planCardStatus");
                List<WebElement> startDateElements = getElementsOfDeviceDetailsPage("planCardStartDate");
                List<WebElement> endDateElements = getElementsOfDeviceDetailsPage("planCardEndDate");
                List<WebElement> descriptionElements = getElementsOfDeviceDetailsPage("planCardDescription");

                int cardCount = Collections.min(List.of(
                        nameElements.size(),
                        statusElements.size(),
                        startDateElements.size(),
                        endDateElements.size(),
                        descriptionElements.size()
                ));

                LOGGER.info("Found {} plan cards.", cardCount);

                for (int i = 0; i < cardCount; i++) {
                    try {
                        String startDateText = startDateElements.get(i).getText().trim();
                        String endDateText = endDateElements.get(i).getText().trim();

                        // Debug logging to trace timezone issues
                        LOGGER.info("[DEBUG] Plan #{} - Start Date Text: {}", i + 1, startDateText);
                        LOGGER.info("[DEBUG] Plan #{} - Start Date title attribute: {}", i + 1, startDateElements.get(i).getAttribute("title"));
                        LOGGER.info("[DEBUG] Plan #{} - Start Date data-date attribute: {}", i + 1, startDateElements.get(i).getAttribute("data-date"));
                        LOGGER.info("[DEBUG] Plan #{} - End Date Text: {}", i + 1, endDateText);
                        LOGGER.info("[DEBUG] Plan #{} - End Date title attribute: {}", i + 1, endDateElements.get(i).getAttribute("title"));
                        LOGGER.info("[DEBUG] Plan #{} - End Date data-date attribute: {}", i + 1, endDateElements.get(i).getAttribute("data-date"));

                        UIPlanCard planCard = new UIPlanCard(
                                nameElements.get(i).getText().trim(),
                                statusElements.get(i).getText().trim(),
                                startDateText,
                                endDateText,
                                descriptionElements.get(i).getText().trim()
                        );
                        uiPlanCards.add(planCard);
                        LOGGER.info("Extracted Plan #{}: {} — description={}", i + 1, planCard, descriptionElements.get(i).getText().trim());
                    } catch (Exception e) {
                        LOGGER.error("Failed to extract plan card #{}: {}", i + 1, e.getMessage());
                    }
                }
                success = true;
            } catch (Exception e) {
                LOGGER.warn("Attempt {} to capture plan cards failed: {}", attempt, e.getMessage());
                if (attempt == 2) {
                    LOGGER.error("Unable to capture plan cards after retry. Or Don't have plan assigned");
                }
                LOGGER.info("Refreshing page before retrying plan-card capture");
                PreDefinedActions.getDriver().navigate().refresh();
                waitForPageLoaded();
                sleeper(2000);
            }
        }

        UIDeviceData deviceData = new UIDeviceData(deviceId, deviceStatus, deviceSrNo, uiPlanCards);
        LOGGER.info("Captured Device Data: {}", deviceData);
        LOGGER.info("=== Device Data Capture Complete ===");

        return deviceData;
    }

    /**
     * This is the constructor
     */
    public WEPDeviceDetailsPage(WebDriver driver) throws IOException {
        deviceDetailsPageProperties = deviceDetailsPagePropertiesReader.getObjectRepository("WEPDeviceDetailsPage");
    }

    /**
     * This is a method to get the elements of the details page
     *
     * @param key - Locator of element
     */
    public final WebElement getElementOfWEPDeviceDetailsPage(String key) throws Exception {
        return getElement(deviceDetailsPageProperties.getProperty(key));
    }

    /**
     * This is a method to click on the element if it is present
     *
     * @param key - Locator of element
     */
    public final void clickOnElementsOfDevicePage(String key) throws Exception {
        click(deviceDetailsPageProperties.getProperty(key));
    }

    /**
     * This is a method to click on the element if it is present by javascript
     *
     * @param key - Locator of element
     */
    public final void clickByJavaScriptOnDevicePage(String key) throws Exception {
        clickByJavaScript(deviceDetailsPageProperties.getProperty(key));
    }

    /**
     * This is a method to verify if the element is present
     *
     * @param key - Locator of element
     */
    public final boolean verifyElementsOfWEPDeviceDetailsPage(String key) {
        try {
            scrollOnDeviceDetailsListPage(key);
            return  verifyElementIsPresent(deviceDetailsPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method verifyElementsOfWEPDeviceListPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * This is a method to enter any text for the device
     *
     * @param key - Locator of element
     */
    public final void enterTextForDeviceDetailPage(String key, String Text) {
        try {
            enterText(deviceDetailsPageProperties.getProperty(key), Text);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method enterTextForDeviceListPage {}", e.getMessage());
        }
    }

    /**
     * This method designed to get the  Text of WebElement from web page
     * @param key - Locator of a web element
     */
    public final String getTextOfWEPDeviceDetailsPage(String key) throws Exception {
        return getTextBy(deviceDetailsPageProperties.getProperty(key));
    }


    /**
     * This is a method to wait for an element till it is visible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is visible or not
     */
    public final boolean waitForElementOfWEPDeviceDetailsPage(String key) {
        try {
            return verifyElementIsVisible(deviceDetailsPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method waitForElementsOfWEPDeviceListPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * This is a method to match text on an element
     *
     * @param key         - Locator of element
     * @param textToMatch - Text to be matched
     * @return - boolean value of whether the text present on element matches or not
     */
    public final boolean matchTextOfDeviceDetailsPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(deviceDetailsPageProperties.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method matchTextOfDeviceListPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * Used to get the elements in the device list page
     * @param key - locator of element
     * @return list of webelements
     */
    public final List<WebElement> getElementsOfDeviceDetailsPage(String key) throws Exception {
        return getElementsTillAllElementsPresent(deviceDetailsPageProperties.getProperty(key));
    }

    /**
     * Used to get the elements in the device list page
     * @param key - locator of element
     * @return WebElement
     */
    public final WebElement getElementOfDeviceDetailsPage(String key) throws Exception {
        return getElement(deviceDetailsPageProperties.getProperty(key));
    }

    /**
     * This is a method to scroll on deviceListPage page
     *
     * @param key - Locator of element
     */
    public final void scrollOnDeviceDetailsListPage(String key) {
        try {
            scrollTillView(deviceDetailsPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method scrollOnDeviceDetailsListPage " + e.getMessage()));
        }
    }

    /**
     * Used to verify the description message on logs page
     * @param logErrorMessage - expected log error message to check against
     * @throws Exception
     */
    public boolean verifyDescriptionOnLogsPage(String logErrorMessage) {
        try {
            WEXLogPage logPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
            waitForElementsOfDeviceDetailsPage("notificationBellIcon");
            clickByJavaScriptOnDevicePage("notificationBellIcon");
            LOGGER.info("Clicked on notification bell icon");
            clickByJavaScriptOnDevicePage("firstNotification");
            clickByJavaScriptOnDevicePage("openLogsBellIcon");
            LOGGER.info("Clicked on open logs hyperlink");
            clickByJavaScriptOnDevicePage("logNotificationBellIcon");
            LOGGER.info("Redirected to logs list page");
            logPage.waitForPageLoaded();
            logPage.resetTableConfiguration();
            logPage.verifyElementsOfWEXLogPage("listTable");
            String descriptionOfFirstRow = logPage.getTextOfWEXLogPage("firstRowDescription");
            if (descriptionOfFirstRow.equalsIgnoreCase(logErrorMessage)) {
                return true;
            } else {
                LOGGER.error("Description on logs page is incorrect when devices are not imported successfully");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyDescriptionOnLogsPage " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to wait for an element till it is visible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is visible or not
     */
    public final boolean waitForElementsOfDeviceDetailsPage(String key) {
        try {
            return verifyElementIsVisible(deviceDetailsPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method waitForElementsOfDeviceListPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * Used to click on element of device details page
     * @param clearFilterKey - Locator of element to clear
     * @throws Exception
     */
    public void clearFiltersOfDevicesListPage(String clearFilterKey) throws Exception {
        clearFilters(deviceDetailsPageProperties.getProperty(clearFilterKey));
    }


    /**
     * Used to click on element of device details page
     * @param key - Locator of element to click
     * @throws Exception
     */
    public final void actionClickOfDeviceDetailsPage(String key) throws Exception {
        actionClick(deviceDetailsPageProperties.getProperty(key));
    }

    /**
     * Used to Verify Hardware Tab contents
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifyHardwareTabContents() throws Exception {
        try {
            waitForElementOfWEPDeviceDetailsPage("hardwareTab");
            actionClickOfDeviceDetailsPage("hardwareTab");
            sleeper(10000); // Wait for the hardware tab to load completely
            waitUntilElementIsPresentOnDeviceDetailsPage("hardwareTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("hardwareTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("Hardware tab is selected");
                LOGGER.info("Verifying Hardware Headers");         
                waitForElementOfWEPDeviceDetailsPage("hardwareTabBatteryHeaderSection");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("hardwareTabBatteryHeaderSection"), "Header of Battery section is not matching");
                LOGGER.info("Battery section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("hardwareTabGraphicsHeaderSection"), "Header of Graphics section is not matching");
                LOGGER.info("Graphics section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("hardwareTabHardDriveHeaderSection"), "Header of Hard Drive section is not matching");
                LOGGER.info("Hard Drive section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("hardwareTabMemoryHeaderSection"), "Header of Memory section is not matching");
                LOGGER.info("Memory section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("hardwareTabProcessorHeaderSection"), "Header of Processor section is not matching");
                LOGGER.info("Processor section is present");
                LOGGER.info("Verified Hardware Tab successfully");
            }else{
                LOGGER.error(("Hardware tab is not selected to verify contents"));
                return  false;
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(("Exception occurred in method VerifyHardwareTabContents " + e.getMessage()));
            return false;
        }
    }
    
    /**
     * Used to Verify Hardware Tab contents for Virtual Machine
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifyHardwareTabContentsforVM() throws Exception {
        try {
        	waitForElementOfWEPDeviceDetailsPage("hardwareTab");
            actionClickOfDeviceDetailsPage("hardwareTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("hardwareTabElements");
            LOGGER.info("Hardware tab elements are loaded");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("hardwareTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("Hardware tab is selected");
                LOGGER.info("Verifying Hardware Headers");
                Assert.assertTrue(matchTextOfDeviceDetailsPage("hardwareTabHardDriveHeaderSection","Hard Drive"), "Header of Hard Drive section is not matching");
                scrollOnDeviceDetailsListPage("hardwareTabMemoryHeaderSection");
                Assert.assertTrue(matchTextOfDeviceDetailsPage("hardwareTabMemoryHeaderSection","Memory"), "Header of Memory section is not matching");
                Assert.assertTrue(matchTextOfDeviceDetailsPage("hardwareTabProcessorHeaderSection","Processor"), "Header of Processor section is not matching");
            }else{
                LOGGER.error(("Hardware tab is not selected to verify contents"));
                return  false;
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(("Exception occurred in method VerifyHardwareTabContents " + e.getMessage()));
            return false;
        }
    }

    /**
     * This is a method to wait for an element till it is invisible
     *
     * @param key - Locator of element
     * @return - boolean value of whether the element is invisible or not
     */
    public final void waitUntilElementIsPresentOnDeviceDetailsPage(String key) throws Exception {
        waitUntillElementIsPresent(key);
    }

    /**
     * Used to Verify TimelineTab Tab contents
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifyTimelineTabContents() throws Exception {
      try {
    	  	sleeper(3000);
            waitForElementOfWEPDeviceDetailsPage("timelineTab");
            clickByJavaScriptOnDevicePage("timelineTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("timelineTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("timelineTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("TimelineTab tab is selected");
                LOGGER.info("Verifying TimelineTab Tab Headers");
                waitForElementOfWEPDeviceDetailsPage("timelineTabHeader");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("timelineTabHeader"), "Header of timeline section is not matching");
                waitForElementOfWEPDeviceDetailsPage("timelineLearnMore");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("timelineLearnMore"), "Timeline Learn More section is not matching");
                if(verifyElementsOfWEPDeviceDetailsPage("stateTimelineTabHeader"))
                {
                    if(getTextOfWEPDeviceDetailsPage("stateTimelineTabHeader").equalsIgnoreCase("Nothing going on here"))
                    LOGGER.info("No data is present in timeline tile.");
                }
                else if(verifyElementsOfWEPDeviceDetailsPage("deviceDataTimeline"))
                {
                	
                	 LOGGER.info("Data is present in timeline tile.");
                }
            }
            else{
                LOGGER.error(("TimelineTab tab is not selected to verify contents"));
                return  false;
            }
           return true;
           
       }catch (Exception e) {
           LOGGER.error(("Exception occurred in method VerifyTimelineTabContents " + e.getMessage()));
            return false;
            
     }

    }


    /**
     * Used to Verify Newly implemented TimelineTab Tab contents
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifyNewTimelineTabContents() throws Exception {
      try {
    	  	sleeper(3000);
            waitForElementOfWEPDeviceDetailsPage("timelineTab");
            clickByJavaScriptOnDevicePage("timelineTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("timelineTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("timelineTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("TimelineTab tab is selected");
                waitForElementOfWEPDeviceDetailsPage("timelineTabElements");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("timelineTabElements"), "timeline section containts are missing");
                LOGGER.info("Data is present in timeline tab.");
            }
            else{
                LOGGER.error(("TimelineTab tab is not selected to verify contents"));
                return  false;
            }
           return true;

       }catch (Exception e) {
           LOGGER.error(("Exception occurred in method VerifyTimelineTabContents " + e.getMessage()));
            return false;
     }
    }

    /**
     * Used to Verify VerifyHealthAndProtectionTabContents Tab contents
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifyHealthAndProtectionTabContents() throws Exception {
        try {
            waitForElementOfWEPDeviceDetailsPage("healthAndProtectionTab");
            actionClickOfDeviceDetailsPage("healthAndProtectionTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("healthProtectionTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("healthAndProtectionTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("Health Protection tab is selected");
                LOGGER.info("Verifying Health Protection Tab Headers");
                waitForElementOfWEPDeviceDetailsPage("issueIndicator");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("issueIndicator"), "Issue Indicators section is not available");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("bsodCrashes"), "Header of BSOD Crashes (last 90 days) section is not matching");
                scrollOnDeviceDetailsListPage("firewallAndNetworkProtection");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("firewallAndNetworkProtection"), "Firewall and Network Protection section is not available");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("resolvedIssue"), "Resolved Issues section is not available");                
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("securityTab"), "Security section is not available");
                //Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("logsTab"), "Logs section is not available");

            }else{
                LOGGER.error(("HealthAndProtection is not selected to verify contents"));
                return  false;
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(("Exception occurred in  method VerifyHealthAndProtectionTabContents" + e.getMessage()));
            return false;
        }
    }

    /**
     * Used to Verify sustainabilityTab Tab contents
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifySustainabilityTabContents() throws Exception {
        try {
            waitForElementOfWEPDeviceDetailsPage("sustainabilityTab");
            actionClickOfDeviceDetailsPage("sustainabilityTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("sustainabilityTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("sustainabilityTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("Sustainability tab is selected");
                LOGGER.info("Verifying Sustainability Tab Headers");
                if(verifyElementsOfWEPDeviceDetailsPage("sustainabilityTabValues")) {
                	LOGGER.info("Power Consumption data is present in sustainabilityTab tile.");
                }else {
                	LOGGER.info("No data is present in sustainabilityTab tile.");
                }
            }else{
                LOGGER.error(("Sustainability is not selected to verify contents"));
                return  false;
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(("Exception occurred in  method VerifySustainabilityTabContents" + e.getMessage()));
            return false;
        }
    }

    /**
     * Used to Verify BIOS Tab contents
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifyBIOSTabContents() throws Exception {
        try {
            waitForElementOfWEPDeviceDetailsPage("BIOSTab");
            actionClickOfDeviceDetailsPage("BIOSTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("biosTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("BIOSTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("BIOSTab tab is selected");
                waitForElementOfWEPDeviceDetailsPage("biosTabHeader");
                LOGGER.info("Verifying BIOSTab Tab");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("biosTabHeader"), "Header of BIOS section is not matching");
                LOGGER.info("biosTab Header is present");
                if (verifyElementsOfWEPDeviceDetailsPage("BIOSHistoryHide")) {
                    WebElement BIOSHistoryHide = getElementOfWEPDeviceDetailsPage("BIOSHistoryHide");
                    if (BIOSHistoryHide.getAttribute("aria-label").equalsIgnoreCase("Show BIOS History")) {
                        LOGGER.info("BIOS History section was hidden, clicking on it to expand the section");
                        actionClickOfDeviceDetailsPage("BIOSHistoryHide");
                    } else {
                        LOGGER.error("BIOS History section is not in collapsed state by default");
                    }
                } else {
                    LOGGER.info("BIOSHistoryHide element is not present, skipping BIOS history expand/collapse check");
                }
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("biosTabHistoryHeader"), "Header of BIOS History section is not matching");
                LOGGER.info("biosTabHeader section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("installedHPdriverpackages"), "Installed HP driver packages section is not matching");
                LOGGER.info("biosTabHistoryHeader section is present");
                if (verifyElementsOfWEPDeviceDetailsPage("biosHistoryElements")) {
                    Assert.assertTrue(true, "BIOS history option is present");
                    LOGGER.info("biosHistoryElements section is present");
                    actionClickOfDeviceDetailsPage("biosHistoryElements");
                    LOGGER.info("clicked on biosHistoryElements");
                } else {
                    LOGGER.info("biosHistoryElements option is not available, skipping this step.");
                }
                waitUntilElementIsPresentOnDeviceDetailsPage("biosTabElements");
                LOGGER.info("biosTabElements is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("biosTabHistoryHeader"), "Header of BIOS History section is not matching");
                LOGGER.info("biosTabHistoryHeader section is present");
                LOGGER.info("BIOSTab tab is verified successfully");
            }else{
                LOGGER.error(("BIOS and Drivers Tab tab is not selected to verify contents"));
                return  false;
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(("Exception occurred in method VerifyBIOSTabContents " + e.getMessage()));
            return false;
        }
    }

    /**
     * Used to Verify Software Tab contents
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifySoftwareTabContents() throws Exception {
        try {
            waitForElementOfWEPDeviceDetailsPage("softwareTab");
            actionClickOfDeviceDetailsPage("softwareTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("softwareTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("softwareTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("softwareTab tab is selected");
                LOGGER.info("Verifying softwareTab Tab Headers");
                waitForElementOfWEPDeviceDetailsPage("softwareTabUtilizationHeader");
//                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("softwareTabUtilizationHeader"), "Header of Software Utilization section is not matching");
//                LOGGER.info("softwareTabUtilizationHeader section is present");

                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("softwareTabMissWinUpdateHeader"), "Header of Missing Windows Updates section is not matching");
                LOGGER.info("softwareTabMissWinUpdateHeader section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("appInventoryTabElements"), "Header of Application Inventory section is not matching");
                LOGGER.info("appInventoryTabElements section is present");
//                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("memUtilizationGraph"), "memUtilizationGraph section is not matching");
//                LOGGER.info("memUtilizationGraph section is present");
//                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("cpuUtilizationGraph"), "cpuUtilizationGraph section is not matching");
//                LOGGER.info("cpuUtilizationGraph section is present");
             
                LOGGER.info("softwareTab tab is verified successfully");
            }else{
                LOGGER.error(("Software tab is not selected to verify contents"));
                return  false;
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(("Exception occurred in method VerifySoftwareTabContents " + e.getMessage()));
            return false;
        }
    }

    /**
     * Used to VerifyLocation Tab contents
     * @return true - If the required elements are present
     * @throws Exception
     */
    public boolean VerifyLocationTabContents() throws Exception {
        try {
            waitForElementOfWEPDeviceDetailsPage("locationTab");
            actionClickOfDeviceDetailsPage("locationTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("locationTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("locationTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("locationTab tab is selected");
                LOGGER.info("Verifying locationTab Tab Headers");
                waitForElementOfWEPDeviceDetailsPage("locationTabMapSection");
                Assert.assertTrue(waitForElementOfWEPDeviceDetailsPage("locationTabMapSection"), "Google map doesn't exist");
                LOGGER.info("locationTabMap Section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("locationHeaderSection"), "Header of Location section is not matching");
                LOGGER.info("locationHeader Section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("reloadLocationElements"), "Reload location option missing");
                LOGGER.info("reloadLocationElements Section is present");
                actionClickOfDeviceDetailsPage("reloadLocationElements");
                LOGGER.info("clicked on reloadLocation Element");
                waitUntilElementIsPresentOnDeviceDetailsPage("locationTabElements");
                LOGGER.info("locationTabElements section is present after clicking on reload");
                LOGGER.info("location Tab is verified successfully");
            }else{
                LOGGER.error(("Location tab is not selected to verify contents"));
                return  false;
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(("Exception occurred in method VerifyNetworkTabContents " + e.getMessage()));
            return false;
        }
    }

    /**
     * Used to VerifyNetworkTabContents
     * @return true - If the required elements are present
     * @throws Exception- ON Exception
     */
    public boolean VerifyNetworkTabContents() throws Exception {
        try {
            waitForElementOfWEPDeviceDetailsPage("networkTab");
            actionClickOfDeviceDetailsPage("networkTab");
            waitUntilElementIsPresentOnDeviceDetailsPage("networkTabElements");
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("networkTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("Network tab is selected");
                LOGGER.info("Verifying Network Tab Headers");
                waitForElementOfWEPDeviceDetailsPage("networkTabInterfaceHeaderSection");
                verifyElementsOfWEPDeviceDetailsPage("networkTabInterfaceHeaderSection");
                LOGGER.info("Network Interface Section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("networkTabHealthHeaderSection"), "Header of Network Health Header section is not matching");
                LOGGER.info("Network Health Section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("networkTabDisconnectionsHeaderSection"), "Header of Network Disconnections Header section is not matching");
                LOGGER.info("Network Disconnections Section is present");
                LOGGER.info("Network Tab is verified successfully");
            }else{
                LOGGER.error(("Network tab is not selected to verify contents"));
                return  false;
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(("Exception occurred in method VerifyNetworkTabContents " + e.getMessage()));
            return false;
        }
    }

    /**
     * Used to VerifyOverviewTabContents
     * @param deviceSerialNumberToTest - Serial number to test on VerifyOverviewTabContents
     * @return true - If the required elements are present
     * @throws Exception - ON Exception
     */
    public boolean VerifyOverviewTabsContents(String deviceSerialNumberToTest) throws Exception {
        try {
        	sleeper(3000);
            waitForElementOfWEPDeviceDetailsPage("overViewTab");
            waitForElementOfWEPDeviceDetailsPage("overViewTabLastRestartLabel");
            sleeper(10000);
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("overViewTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("Overview tab is selected.");
                LOGGER.info("Verifying Profile of device information.");
                waitForElementOfWEPDeviceDetailsPage("overallExp");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("overallExp"), "Label of Overall Experience is not matching");
                LOGGER.info("overallExp section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("recommendedAction"), "Label of Recommended Actions is not matching");
                LOGGER.info("recommendedAction section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("profileTab"), "Label of Profile is not matching");
                LOGGER.info("profileTab section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("customFieldsTab"), "Label of Custom Fields is not matching");
                LOGGER.info("customFieldsTab section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("systemHeaderTab"), "Label of System is not matching");
                LOGGER.info("systemHeaderTab section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("enrollApp"), "Label of Workforce Experience Enrolled Applications is not matching");
                LOGGER.info("enrollApp section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("warrantyHeaderTab"), "Label of Warranty tab is not matching");
                LOGGER.info("warrantyHeaderTab section is present");
                Assert.assertTrue(verifyElementsOfWEPDeviceDetailsPage("viewHistory"), "view History check matching");
                LOGGER.info("viewHistory section is present");
                LOGGER.info("Overview tab is verified.");
                return true;
            }
            else{
                LOGGER.error(("Overview tab is not selected to verify Overview tab contents"));
                return  false;
            }
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method VerifyOverviewTabContents " + e.getMessage()));
            return false;
        }
    }


    /**
     * Used to VerifyOverviewTabContents
     * @param deviceSerialNumberToTest - Serial number to test on VerifyOverviewTabContents
     * @return true - If the required elements are present
     * @throws Exception - ON Exception
     */
    public boolean VerifyOverviewTabContents(String deviceSerialNumberToTest) throws Exception {
        try {
            waitForElementOfWEPDeviceDetailsPage("overViewTab");
            waitForElementOfWEPDeviceDetailsPage("overViewTabLastRestartLabel");
            sleeper(10000);
            WebElement selectedElement = getElementOfWEPDeviceDetailsPage("overViewTab");
            if(selectedElement !=null && selectedElement.getAttribute("aria-selected").equals("true")){
                LOGGER.info("Overview tab is selected");
                LOGGER.info("Verifying Profile of device information");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabAliasLabel","Alias"), "Label of Alias is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabAliasName"), "Value doesn't exist for Alias column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabSerialNumberLabel","Serial Number"), "Label of serial number is not matching");
                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabSerialNumberValue",deviceSerialNumberToTest), "Device serial number used to search is not matching");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabGroupLabel","Group"), "Label of group is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabGroupName"), "Value doesn't exist for Group column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabEnrolledUserLabel","Enrollment User"), "Label of Enrollment User is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabEnrolledUserName"), "Value doesn't exist for Enrolled User column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabLastSignInUserLabel","Last Signed-In User"), "Label of last signed in user is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabLastSignInUserName"), "Value doesn't exist for last logged in user column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabLifeCycleLabel","Lifecycle Status"), "Label of Lifecycle Status is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabLifeCycleValue"), "Value doesn't exist for Lifecycle Status column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDepartmentLabel","Department"), "Label of Department is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabDepartmentValue"), "Value doesn't exist for Department column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceNameLabel","Name"), "Label of Device Name is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabDeviceNameValue"), "Value doesn't exist for Device Name column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceTagLabel","Asset Tag"), "Label of Asset Tag is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabDeviceTagValue"), "Value doesn't exist for Asset Tag column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabCostCenterLabel","Cost Center"), "Label of Cost Center is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabCostCenterValue"), "Value doesn't exist for Cost Center column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabLastRestartLabel","Last restart date"), "Label of Last restart date is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabLastRestartValue"), "Value doesn't exist for Last restart date column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceRoleLabel","Device Role"), "Label of Device Role is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabDeviceRoleValue"), "Value doesn't exist for Device Role column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabBYODLabel","Bring Your Own Device(BYOD)"), "Label of Bring Your Own Device(BYOD) is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabBYODValue"), "Value doesn't exist for BYOD column");

                LOGGER.info("Verifying System information");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabManufacturerLabel","Manufacture"), "Label of Manufacturer is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabManufacturerValue"), "Value doesn't exist for Manufacturer column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabManufactureDateLabel","Manufacture Date"), "Label of Manufacture Date is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabManufactureDateValue"), "Value doesn't exist for Manufacturer column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceBornDateLabel","Born on Date"), "Label of Born on Date is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabDeviceBornDateValue"), "Value doesn't exist for Born on Date column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabProductLabel","Product"), "Label of Product is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabProductValue"), "Value doesn't exist for Product column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceModelLabel","Model"), "Label of Model is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabDeviceModelValue"), "Value doesn't exist for Model column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceTypeLabel","Type"), "Label of Type is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabDeviceTypeValue"), "Value doesn't exist for Type column");

                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabOSLabel","Operating System"), "Label of Operating System is not matching");
                Assert.assertTrue(isAnyValueExist("overViewTabOSValue"), "Value doesn't exist for Operating System column");

                LOGGER.info("Verifying Warranty Section");
                Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabWarrantyHeader","Warranty"), "Warranty Section Not available");

                return true;
            }
            else{
                LOGGER.error(("Overview tab is not selected to verify Overview tab contents"));
                return  false;
            }
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method VerifyOverviewTabContents " + e.getMessage()));
            return false;
        }
    }
    
    
	/**
	 * Used to VerifyOverviewTabContents
	 * 
	 * @param deviceSerialNumberToTest - Serial number to test on
	 *                                 VerifyOverviewTabContents
	 * @return true - If the required elements are present
	 * @throws Exception - ON Exception
	 */
	public boolean verifyPhysicalAssetsOverviewTabContents(String TEST_PhysicalAsset_SERIAL_NUM) throws Exception {
		try {
			LOGGER.info("Overview tab is selected");
			LOGGER.info("Verifying Profile information");
			scrollOnDeviceDetailsListPage("overViewTabAliasLabel");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabAliasLabel", "Alias"),"Label of Alias is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabAliasName"), "Value doesn't exist for Alias column");
			scrollOnDeviceDetailsListPage("overViewTabDeviceNameLabel");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceNameLabel", "Name"),"Label of Device Name is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabDeviceNameValue"),"Value doesn't exist for Device Name column");
			scrollOnDeviceDetailsListPage("overViewTabSerialNumberLabel");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabSerialNumberLabel", "Serial Number"),"Label of serial number is not matching");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabSerialNumberValue", TEST_PhysicalAsset_SERIAL_NUM),"Device serial number used to search is not matching");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceTagLabel", "Asset Tag"),"Label of Asset Tag is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabDeviceTagValue"), "Value doesn't exist for Asset Tag column");
			scrollOnDeviceDetailsListPage("overViewTabCostCenterLabel");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabCostCenterLabel", "Cost Center"),"Label of Cost Center is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabCostCenterValue"),"Value doesn't exist for Cost Center column");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabLifeCycleLabel", "Lifecycle Status"),"Label of Lifecycle Status is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabLifeCycleValue"),"Value doesn't exist for Lifecycle Status column");
			scrollOnDeviceDetailsListPage("overViewTabDepartmentLabel");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDepartmentLabel", "Department"),"Label of Department is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabDepartmentValue"),"Value doesn't exist for Department column");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabAssetLocationLabel", "Asset Location"),"Label of Asset Location is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabAssetLocationValue"),"Value doesn't exist for Asset Location column");
			LOGGER.info("Verifying System information");
			scrollOnDeviceDetailsListPage("overViewTabAssetTypeLabel");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabAssetTypeLabel", "Asset Type"),"Label of Asset Type is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabAssetTypeValue"),"Value doesn't exist for Asset Type column");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabAssignedAreaLabel", "Assigned Area"),"Label of Assigned Area is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabAssignedAreaValue"),"Value doesn't exist for Assigned Area column");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabManufacturerLabel", "Manufacturer"),	"Label of Manufacturer is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabManufacturerValue"),"Value doesn't exist for Manufacturer column");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabManufactureDateLabel", "Manufacture Date"),"Label of Manufacture Date is not matching");
			Assert.assertTrue(isAnyValueExist("overViewTabManufactureDateValue"),"Value doesn't exist for Manufacturer column");
			scrollOnDeviceDetailsListPage("overViewTabDeviceModelLabel");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabDeviceModelLabel", "Model"),"Label of Model is not matching");	
			Assert.assertTrue(isAnyValueExist("overViewTabDeviceModelValue"), "Value doesn't exist for Model column");
			LOGGER.info("Verifying Warranty Section");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("overViewTabWarrantyHeader", "Warranty"),"Warranty Section Not available");
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method VerifyOverviewTabContents " + e.getMessage()));
			return false;
		}
	}

    /**
     * This method used to check is any value exist on the extracted web element
     * @param locator - Web element locator key
     * @return - either true/false if value exist
     * @throws Exception - On exception
     */
    private boolean isAnyValueExist(String locator) throws Exception {
        return !getElementOfWEPDeviceDetailsPage(locator).getText().isEmpty();
    }
    
    /**
     * This is a method to check text on given matching elements
     *
     * @param key         - Locator of element
     * @param textToMatch - Text to be matched
     * @return - boolean value of whether the text present on element matches or not
     */
    public final boolean checkTextOnDeviceDetailsPage(String key, String textToMatch) {
        try {
            List<WebElement> listOfEnrolledApp = getElementsOfDeviceDetailsPage(key);
            for (WebElement element : listOfEnrolledApp) {
            	mouseHover(element);
                if (element.getText().equalsIgnoreCase(textToMatch)) {
                    LOGGER.info("Text matched on device details page: {}", textToMatch);
                    return true;
                }
            }
            LOGGER.error("Text not matched on device details page: {}", textToMatch);
            return false;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method checkTextOnDeviceDetailsPage: {}", e.getMessage());
            return false;
        }
    }


	/**
	 * Verify if any type of data is present or not in the element
	 */
	public boolean verifyDataIsPresetOrNot(String locator) throws Exception {
		String locatorValue = getTextBy(deviceDetailsPageProperties.getProperty(locator));
		if (locatorValue.isEmpty()) {
			return false;
		}
		return true;
	}

    /**
     * This method verifies if export is successful
     */
    public boolean isExportSuccess(String deviceName) throws Exception {
    	try {
    		boolean flag = false;
            sleeper(60000);
            deviceName = getTextOfWEPDeviceDetailsPage("Asset_deviceNameInDetailsPage");
            LOGGER.info("Device name in details page captured: " + deviceName);
            verifyElementsOfWEPDeviceDetailsPage("notificationBellIcon");
            actionClickOfDeviceDetailsPage("notificationBellIcon");
            if (verifyElementsOfWEPDeviceDetailsPage("notificationTabInBell")){
                actionClickOfDeviceDetailsPage("notificationTabInBell");
            }
            List<WebElement> listOfUnreadNotificationText = getElementsOfDeviceDetailsPage("listOfUnreadNotificationText");
            List<WebElement> listOfNotificationActionButton = getElementsOfDeviceDetailsPage("listOfNotificationActionButton");
            for (int i = 0; i < 2; i++) {
    			mouseHover(listOfUnreadNotificationText.get(i));
    			if(listOfUnreadNotificationText.get(i).getText().contains("Your file containing device details for " + deviceName + " is ready to download.")) {
    				LOGGER.info("Notification for export devices is present");
    				listOfNotificationActionButton.get(i).click();
                    LOGGER.info("Clicked on notification action button for export devices");
                    waitForElementOfWEPDeviceDetailsPage("downloadLinkBellIcon");
    				clickByJavaScriptOnDevicePage("downloadLinkBellIcon");
                    LOGGER.info("Clicked on download file option from device notification");
                    flag = true;
                    break;
    			}
    		}
            if(!flag) {
            	LOGGER.error("Notification for import has failed");
            }
            return flag;
    	}catch (Exception e) {
            LOGGER.error((String.format("Exception occurred in method postImportCHeckForNotificationMessage %s", e.getMessage())));
            return false;
        }
    	
    }

    /**
     * This is a method to verify if the export is present in the download path
     *
     * @return - true if file is present, false otherwise
     */
    public boolean verifyExportIsPresent(String filename) throws InterruptedException, IOException {
        return isFileDownloaded(ConstantPath.DOWNLOAD_PATH,filename);
    }

    /**
	 * Used to Verify Custom Fields in Asset Details Page
	 */
	public boolean verifyCustomFieldsInAssetDetailsPage() throws Exception {
		try {
			LOGGER.info("Asset Details Page is displayed successfully");
			LOGGER.info("Verifying Custom Fields information");
			Assert.assertTrue(matchTextOfDeviceDetailsPage("customFieldHeaderInDetailsPage", "Custom Fields"),"Header of Custom Fields is not matching");
			List<WebElement> customFieldLabels = getElementsOfDeviceDetailsPage("customFieldLabelsInDetailsPage");
			for (int i = 0; i < customFieldLabels.size(); i++) {
				mouseHover(customFieldLabels.get(i));
				Assert.assertTrue(customFieldLabels.get(i).getText().contains("Field" + i),"Label of Custom Field is not matching for Field" + i);
			}
			List<WebElement> customFieldValues = getElementsOfDeviceDetailsPage("customFieldValuesInDetailsPage");
			for (int i = 0; i < customFieldValues.size(); i++) {
				mouseHover(customFieldValues.get(i));
				Assert.assertTrue(customFieldValues.get(i).getText().contains("AutoCF" + i),"Label of Custom Field is not matching for AutoCF" + i);
			}
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method VerifyOverviewTabContents " + e.getMessage()));
			return false;
		}
	}
	
    /**
	 * Used to Verify exported csv of asset details page
	 */

	public boolean verifyExportedCSVOfAssetDetailsPage(String filename) throws Exception {
		try {
			sleeper(5000);
			WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("starting to verify exported CSV of asset details page");
			FileInputStream file = new FileInputStream(ConstantPath.DOWNLOAD_PATH + filename);
			LOGGER.info("initiated file input stream");
			Workbook workbook = WorkbookFactory.create(file);
			LOGGER.info("initiated workbook");
			Sheet sheet = workbook.getSheet("Overview");
			LOGGER.info("initiated sheet");
			int rowCount = sheet.getPhysicalNumberOfRows();
			HashMap<String, String> fieldsPresent = new HashMap<>();
			ArrayList<String> profileValuesPresent = new ArrayList<>();
			ArrayList<String> warrantyValuesPresent = new ArrayList<>();
			ArrayList<String> customValuesPresent = new ArrayList<>();
			ArrayList<String> systemValuesPresent = new ArrayList<>();
			Set<String> requiredKeys = new HashSet<>(Arrays.asList("Profile", "Warranty", "Custom Fields", "System"));
			for (int i = 1; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					String key = row.getCell(0).getStringCellValue();
					String value = row.getCell(1).getStringCellValue();
					if (key.equalsIgnoreCase("Profile")) {
						profileValuesPresent.add(value);
					} else if (key.equalsIgnoreCase("Warranty")) {
						warrantyValuesPresent.add(value);
					} else if (key.equalsIgnoreCase("Custom Fields")) {
						customValuesPresent.add(value);
					} else if (key.equalsIgnoreCase("System")) {
						systemValuesPresent.add(value);
					} else {
						LOGGER.warn("Unexpected key: " + key);
					}
				} else {
					LOGGER.error("Row " + i + " is null");
				}
			}
			fieldsPresent.put("Profile", profileValuesPresent.toString());
			fieldsPresent.put("Warranty", warrantyValuesPresent.toString());
			fieldsPresent.put("Custom Fields", customValuesPresent.toString());
			fieldsPresent.put("System", systemValuesPresent.toString());
			for (String key : fieldsPresent.keySet()) {
				String value = fieldsPresent.get(key);
				if (key.equalsIgnoreCase("Profile")) {
					Assert.assertTrue(
							value.contains("Alias") && value.contains("Name") && value.contains("Serial Number")
									&& value.contains("Asset Tag") && value.contains("Cost Center")
									&& value.contains("Warranty") && value.contains("Lifecycle Status")
									&& value.contains("Department") && value.contains("Asset Location"),
							"Profile fields validation failed");
					LOGGER.info("Profile fields validation is passed");
				} else if (key.equalsIgnoreCase("Warranty")) {
					Assert.assertTrue(
							value.contains("Warranty") && value.contains("Start Date") && value.contains("End Date")
									&& value.contains("Details") && value.contains("Status"),
							"Warranty fields validation failed");
					LOGGER.info("Warranty fields validation is passed");
				} else if (key.equalsIgnoreCase("System")) {
					Assert.assertTrue(value.contains("Asset Type") && value.contains("Manufacturer")
							&& value.contains("Assigned Area") && value.contains("Model")
							&& value.contains("Deployment Date") && value.contains("Manufacture Date")
							&& value.contains("Retirement Date"), "System fields validation failed");
					LOGGER.info("System fields validation is passed");
				}
			}
			Assert.assertTrue(fieldsPresent.containsKey("Custom Fields"), "Custom Fields validation failed");
			LOGGER.info("Custom Fields validation is passed");
			Assert.assertTrue(fieldsPresent.keySet().containsAll(requiredKeys), "Required sections validation failed");
			Assert.assertTrue(fieldsPresent.keySet().size() == 4, "Number of sections validation failed");
			LOGGER.info("Physical assets CSV validation passed");
			wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception occurred in method verifyExportedCSVOfAssetDetailsPage: " + e.getMessage());
			return false;
		}
	}

    /** Deletes a all filters present in list page
     * @throws Exception */
    public void verifyDeleteDeviceFromActionsDD() throws Exception {
    	WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		waitForElementOfWEPDeviceDetailsPage("actionsButton");
        actionClickOfDeviceDetailsPage("actionsButton");
        waitForElementOfWEPDeviceDetailsPage("deleteDeviceinActionDD");
        actionClickOfDeviceDetailsPage("deleteDeviceinActionDD");
        LOGGER.info("Clicked on delete device from Actions dropdown");
        waitForElementOfWEPDeviceDetailsPage("deleteDevicePopup");
        String extractedSecurityKey = getTextOfWEPDeviceDetailsPage("securityNumberLabel");
        enterTextForDeviceDetailPage("securityNumberTxtBox", extractedSecurityKey);
        clickOnElementsOfDevicePage("deleteSelectedDeviceFromActionsDD");
        sleeper(3000);
        Assert.assertEquals(wepDeviceListPage.getTextOfWEPDeviceListPage("importNotification"),"Device Deletion in progress. Please check notification center for update.", "Deleting selected device failed");
        LOGGER.info("Deleting selected device from details page is successful");
        sleeper(5000);
        Assert.assertTrue(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("addDeviceBtn"),"system did't redirected to list page after device is deleted from details page");
        Assert.assertFalse(wepDeviceListPage.verifyElementsOfWEPDeviceLstPage("oldDeviceNav"),"Old navigation menu is appeared in list page in new navigation");
    }
    
    /** Verify Lifecycle status in details page
     * @return - Lifecycle status value from overview tab
     * @throws Exception */
    public String verifyLifecycleStatusInDetailsPage() throws Exception {
    	sleeper(5000);
    	waitForElementOfWEPDeviceDetailsPage("overViewTab");
        waitForElementOfWEPDeviceDetailsPage("lifecycleStatusInOverview");
    	mouseHover(deviceDetailsPageProperties.getProperty("lifecycleStatusInOverview"));
    	verifyElementsOfWEPDeviceDetailsPage("lifecycleStatusInOverview");
		actionClickOfDeviceDetailsPage("lifecycleStatusEditIcon");
		waitForElementOfWEPDeviceDetailsPage("openLifecycleStatusList");
		actionClickOfDeviceDetailsPage("cancelLifecycleStatus");
		waitForElementOfWEPDeviceDetailsPage("lifecycleStatusEditIcon");
		sleeper(2000);
		clickByJavaScriptOnDevicePage("lifecycleStatusEditIcon");
		waitForElementOfWEPDeviceDetailsPage("openLifecycleStatusList");
		actionClickOfDeviceDetailsPage("openLifecycleStatusList");
		String lifycleStatus = selectLifecycleStatusFromList("Delivered");
		waitForElementOfWEPDeviceDetailsPage("saveLifecycleStatus");
		actionClickOfDeviceDetailsPage("saveLifecycleStatus");
		sleeper(2000);
		String lifecycleStatusPresentInOverview = getTextOfWEPDeviceDetailsPage("lifecycleStatusPresentInOverview");
		LOGGER.info("Lifecycle status in overview is: "+ lifecycleStatusPresentInOverview);
		Assert.assertEquals(lifecycleStatusPresentInOverview, lifycleStatus, "Lifecycle status in overview is not matching with the selected lifecycle status from list");
		LOGGER.info("Lifecycle status in overview tab is matching with the selected lifecycle status from list");
		return lifecycleStatusPresentInOverview;
    }
    
    /** Select Lifecycle status from list in details page
     * @return - Lifecycle status value from list 
     * @throws Exception */
    public String selectLifecycleStatusFromList(String lifecycleStatus) throws Exception {
    	String status = "Fail to Select";
    	try {
    		waitForElementOfWEPDeviceDetailsPage("lifecycleOptionsList");
        	List<WebElement> lifecycleStatusList = getElementsOfDeviceDetailsPage("lifecycleOptionsListOptions");
    		for (WebElement element : lifecycleStatusList) {
    			mouseHover(element);
    			sleeper(1000);
    			if (element.getText().equalsIgnoreCase(lifecycleStatus)) {
    				status = element.getText();
    				LOGGER.info("Lifecycle status to be selected from list is: " + lifecycleStatus);
    				clickWebelement(element);
    				break;
    			}
    		}
    		return status;
    	}catch (Exception e) {
    		LOGGER.info(("Exception occurred in method selectLifecycleStatusFromList " + e.getMessage()));
    		return status;
    	}
    }
    

	/**
	 * Used to Verify exported csv of device details page
	 */

	public boolean verifyExportedCSVOfDeviceDetailsPage(String description, String value, String filename) throws Exception {
		try {
			sleeper(5000);
			WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("starting to verify exported CSV of device details page");
			FileInputStream file = new FileInputStream(ConstantPath.DOWNLOAD_PATH + filename);
			LOGGER.info("initiated file input stream");
			Workbook workbook = WorkbookFactory.create(file);
			LOGGER.info("initiated workbook");
			Sheet sheet = workbook.getSheet("Overview");
			LOGGER.info("initiated sheet");
			int rowCount = sheet.getPhysicalNumberOfRows();
			HashMap<String, String> fieldsPresent = new HashMap<>();
			DataFormatter formatter = new DataFormatter();
			for (int i = 1; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					String val = formatter.formatCellValue(row.getCell(1));
					String desc = formatter.formatCellValue(row.getCell(2));
					fieldsPresent.put(desc, val);
				} else {
					LOGGER.error("Row " + i + " is null");
				}
			}
			for (String key : fieldsPresent.keySet()) {
				String val = fieldsPresent.get(key);
				if (key.equalsIgnoreCase(description)) {
					Assert.assertTrue(val.contains(value), description + " field validation is failed");
					LOGGER.info(description + " field validation is passed successfully with value = " + value);
				}
			}
			wepDeviceListPage.deleteDeviceDir(ConstantPath.DOWNLOAD_PATH);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception occurred in method verifyExportedCSVOfAssetDetailsPage: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public boolean getWarrantyCoverageTiles(String plan, String start_date, String end_date) throws Exception{
		if(this.waitForElementOfWEPDeviceDetailsPage("allWarrantyTiles")){
			String planName = this.getTextOfWEPDeviceDetailsPage("warrantyTilePlanName");
			String startDate = this.getTextOfWEPDeviceDetailsPage("warrantyTileStartDate");
			String endDate = this.getTextOfWEPDeviceDetailsPage("warrantryTileEndDate");
			LOGGER.info(planName);
			LOGGER.info(startDate);
			LOGGER.info(endDate);
			
		}
		
		return true;
		
	}
}