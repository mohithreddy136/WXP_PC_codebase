package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WEPVyoptaVideoEndpointsPage extends CommonMethod {
    private WEPVyoptaVideoEndpointsPage instance;
    private ObjectReader WEPVyoptaVideoEndpointsPagePropertiesReader = new ObjectReader();
    private Properties WEPVyoptaVideoEndpointsPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(WEPScriptsPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");


    public WEPVyoptaVideoEndpointsPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPVyoptaVideoEndpointsPage.class) {
                if (instance == null) {
                    instance = new WEPVyoptaVideoEndpointsPage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPVyoptaVideoEndpointsPage(WebDriver driver) throws IOException {
        WEPVyoptaVideoEndpointsPageProperties  = WEPVyoptaVideoEndpointsPagePropertiesReader.getObjectRepository("WEPVyoptaVideoEndpointsPage");
    }

    public final boolean verifyElementsOfVideoEndpointsPage(String key) throws Exception {
        return verifyElementIsPresent(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public final void clickOnElementsOfVideoEndpointsPage(String key) throws Exception {
        click(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public final void mouseHoverAndClickOfVideoEndpointsPage(String key) throws Exception {
        actionClick(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public final void actionClickOfVideoEndpointsPage(String key) throws Exception {
        actionClick(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public final List<WebElement> getElementsOfVideoEndpointsPage(String key) throws Exception {
        return getAllElements(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnVideoEndpointsPage(String key) throws Exception {
        clickByJavaScript(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public final WebElement getElementOfVideoEndpointsPage(String key) throws Exception {
        return getElement(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public void scrollTillViewOnVideoEndpointsPage(String key) throws Exception {
        scrollTillView(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public final void listMouseHoverOnVideoEndpointsPage(String key) throws Exception {
        listMouseHover(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
    }

    public final void enterTextOnVideoEndpointsPage(String key, String Text) throws Exception {
        enterText(WEPVyoptaVideoEndpointsPageProperties.getProperty(key), Text);
    }


    public final void enterTextByJavaScriptOnVideoEndpointsPage(String key, String Text) throws Exception {
        enterTextUsingJavaScript(WEPVyoptaVideoEndpointsPageProperties.getProperty(key), Text);
    }

    public final boolean verifyTextPresentOnElementOfVideoEndpointsPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPVyoptaVideoEndpointsPageProperties.getProperty(key), Text);
    }

    public final boolean waitForElementsOfVideoEndpointsPage(String key, int waitTime) throws Exception {
        return verifyElementIsVisibleDynamic(WEPVyoptaVideoEndpointsPageProperties.getProperty(key), waitTime);
    }
    /**
     * This method designed to get the  Text of WebElement from web page
     * @param key
     */
    public final String getTextOfWEPVideoEndpointsPage(String key) throws Exception {
        return getTextBy(WEPVyoptaVideoEndpointsPageProperties.getProperty(key));
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
            List<WebElement> actualColumnList = getElementsOfVideoEndpointsPage(key);
            for (WebElement we : actualColumnList)

                if(we.getText().contains("sorted")){
                    String[] split = we.getText().split("\n");
                    String columnName = split[0];

                    if (columnName.equalsIgnoreCase(expectedColumnList.get(counter))) {
                        flag = true;
                        counter++;
                    } else {
                        flag = false;
                        LOGGER.error(we.getText() + " Header does not match at list table page.");
                        break;
                    }
                }
                else if (we.getText().equalsIgnoreCase(expectedColumnList.get(counter))) {
                    flag = true;
                    counter++;
                } else {
                    flag = false;
                    LOGGER.error(we.getText() + " Header does not match at list table page.");
                    break;
                }
        } catch (Exception e) {
            LOGGER.error("Error while verifying table columns" + e.getMessage());
        }
        return flag;
    }

    /**
     * This is a method to get all clickable elements of the page
     *
     * @return List of string that is available in UI
     */
    public List<String> getAvailableEndpoints() throws Exception {
        List<String> allData = getUniqueElementsofStringsFromList(WEPVyoptaVideoEndpointsPageProperties.getProperty("videoEndpointPageFirstRowData"));
        LOGGER.info("Extracted available endpoints to test the sorting");
        return allData;
    }

    /**
     * This is a method to get sorting order
     *
     * @return - true if the sort is descending, false otherwise
     */
    public boolean getSortingOrderType(String columnLocator) throws Exception {
        String result = getAttribute(WEPVyoptaVideoEndpointsPageProperties.getProperty(columnLocator), "aria-label");
        return result.equals("sorted by Endpoint Name descending, sort by ascending");
    }

    /**
     * This is a method to get sorting order
     *
     * @param listOfEndpoints - List of device serial number to check sorting
     * @param isDesc             - To carry the storting type
     * @return - true if the sort is descending, false otherwise
     */
    public boolean IsColumnSorted(List<String> listOfEndpoints, boolean isDesc) {
        List<String> uniqueEndpointNameList = normalizeVyoptaData(listOfEndpoints);
        boolean isInOrder = true;
        if (isDesc) {
            for (int i = 0; i < uniqueEndpointNameList.size() - 1; i++) {
                if (uniqueEndpointNameList.get(i).compareToIgnoreCase(uniqueEndpointNameList.get(i + 1)) <= 0) {
                    isInOrder = false;
                    break;
                }
            }
        } else {
            for (int i = 0; i < uniqueEndpointNameList.size() - 1; i++) {
                if (uniqueEndpointNameList.get(i).compareToIgnoreCase(uniqueEndpointNameList.get(i + 1)) >= 0) {
                    isInOrder = false;
                    break;
                }
            }
        }
        listOfEndpoints.clear();
        return isInOrder;
    }

    /**
     * This is a method to remove the empty values
     * remove the space and keep them all in same case from a list
     *
     * @param listToCheck -  list (unfiltered)
     * @return - list of elements with empty values removed
     */
    public static List<String> normalizeVyoptaData(List<String> listToCheck) {
        Set<String> uniqueSet = new LinkedHashSet<>(listToCheck);
        return uniqueSet.stream()
                .map(s -> s.replaceAll("\\s+", "")  // Remove spaces
                        .replaceAll("[^a-zA-Z0-9]", "")  // Remove special characters
                        .toLowerCase())  // Convert to lowercase
                .filter(s -> !s.isEmpty())  // Filter out empty strings
                .collect(Collectors.toList());
    }

    /**
     * This is a method to clear the filter if present in UI
     */
    public void clearFiltersOfVideoEndpointsPage(String clearFilterKey) throws Exception {
        if(waitForElementsOfVideoEndpointsPage(clearFilterKey,5)) {
            clearFilters(WEPVyoptaVideoEndpointsPageProperties.getProperty(clearFilterKey));
        }
    }

    /**
     * This method is to mouse hover and click through javascript for a webelement
     * @param key - webelement
     * @throws Exception
     */
    public final void mouseHoverclickOfVyoptaPage(WebElement key) throws Exception {
        mouseHoverclick(key);
    }

    /**
     * This method returns the manufacturer from hashmap based on the device count
     * @param fleetInventoriesListElements list of widget elements
     * @return hashmap containing manufacturer with the count
     * @throws Exception
     */
    public HashMap<String, Integer> getManufacturerBasedDeviceCount(List<WebElement> fleetInventoriesListElements,String widgetTitle) throws Exception {
        int newCount = 0;

        HashMap<String, Integer> differentManufacturerPcs = new HashMap<>();
        for (WebElement element : fleetInventoriesListElements) {
            if (!element.getText().isEmpty() && !element.getText().equals(widgetTitle)) {
                mouseHoverclickOfVyoptaPage(element);

                String latestCountOfRemainPcs = getTextOfWEPVideoEndpointsPage("centerWidgetValue");
                if(!latestCountOfRemainPcs.isEmpty()){
                    String manName = element.getText();
                    int resultToTest=   getManufacturerCount(latestCountOfRemainPcs);
                    differentManufacturerPcs.put(manName,resultToTest);

                }
                mouseHoverclickOfVyoptaPage(element);
            }
        }
        return differentManufacturerPcs;
    }

    /**
     * This method returns the manufacturer from hashmap based on the device count
     * @param meetingQualityListElements list of widget elements
     * @return hashmap containing manufacturer with the count
     * @throws Exception
     */
    public HashMap<String, Integer> getApplicationWithMeetingCount(List<WebElement> meetingQualityListElements) throws Exception {
        HashMap<String, Integer> differentApplications = new HashMap<>();
        for (int i = 0; i <= meetingQualityListElements.size()-2;i+=2) {
            if (!meetingQualityListElements.get(i).getText().isEmpty() ) {
                try {
                    int nextValue = Integer.parseInt(meetingQualityListElements.get(i + 1).getText());
                    differentApplications.put(meetingQualityListElements.get(i).getText(),nextValue);

                } catch (NumberFormatException e) {
                    System.out.println("Next element text is not a valid integer.");
                }
            }
        }
        int sum = 0;
        for (int value : differentApplications.values()) {
            sum += value;
        }
        differentApplications.put("Total",sum);

        return differentApplications;
    }

    /**
     * This method verifies if count is matching
     * @param locator - locator to check
     * @param finalCountToVerify -number to verify against
     * @return true if count is matching false otherwise
     * @throws Exception
     */
    public boolean isCountMatching(String locator, int finalCountToVerify) throws Exception {
        int finalCount = Integer.parseInt(getTextOfWEPVideoEndpointsPage(locator));
        return finalCountToVerify == finalCount;
    }

    /**
     * This method verifies if the count is matching once redirected to device list page
     * @param fleetInventoriesListElements - elements of fleet widget
     * @param differentManuDeviceCount - count for the different manufacturers
     * @param wepVyoptaVideoEndpointPage -obj of supportive class
     * @return true if count is matching
     * @throws Exception
     */
    public boolean verifyManufacturerCountFromVideoEndpointList(List<WebElement> fleetInventoriesListElements, HashMap<String, Integer> differentManuDeviceCount, WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage) throws Exception {
        mouseHoverclickOfVyoptaPage(fleetInventoriesListElements.get(1));
        String pcInventoryName = fleetInventoriesListElements.get(1).getText();
        int latestCountOfRemainPcs = getManufacturerCount(wepVyoptaVideoEndpointPage.getTextOfWEPVideoEndpointsPage("centerWidgetValue"));
        boolean flag = false;
        sleeper(2000);

        if(differentManuDeviceCount.containsKey(pcInventoryName) && differentManuDeviceCount.get(pcInventoryName) == latestCountOfRemainPcs){
            wepVyoptaVideoEndpointPage.mouseHoverbyoffsettClick("chartCircle", 00, 75);
            waitForPageLoaded();
            int numberOfTotalDevices = wepVyoptaVideoEndpointPage.getTotalNumbersFromPagination(wepVyoptaVideoEndpointPage,"showingPaginationTotalCount");
            flag = verifyTheCountWithinRange(differentManuDeviceCount.get(pcInventoryName), numberOfTotalDevices);
            LOGGER.info("Verified redirection count of first manufacturer is matching device list page count");
        }
        return flag;
    }

    /**
     * Verifies if the actual count is within 100 difference of the rounded count
     * @param roundedCount
     * @param actualCount
     * @return
     */
    public boolean verifyTheCountWithinRange(Integer roundedCount, int actualCount) {
        int difference = roundedCount-actualCount;
        return difference <= 100;
    }

    /**
     * This method gets the total pagination number
     * @param wepVyoptaVideoEndpointPage device list page object
     * @param locator - locator
     * @return total count displayed
     * @throws Exception
     */
    public int getTotalNumbersFromPagination(WEPVyoptaVideoEndpointsPage wepVyoptaVideoEndpointPage, String locator) throws Exception {
        String totalDeviceText = wepVyoptaVideoEndpointPage.getTextOfWEPVideoEndpointsPage(locator);
        return Integer.parseInt(totalDeviceText.replace(",","").split("of ")[1]);
    }
    /**
     * This method used for click on left side of total count.
     * @param key:it is center point from that we start moving.
     * @param left:It is value for moving left from center point
     * @param right:It is value for moving Right from center point
     * @throws Exception
     */
    public final void mouseHoverbyoffsettClick(String key,int left,int right) throws Exception {
        mouseHoverbyoffsetClick(WEPVyoptaVideoEndpointsPageProperties.getProperty(key),left,right);
    }

    /**
     * This method used for move the mouse left side of total count.
     *
     * @param key:it   is center point from that we start moving.
     * @param left:It  is value for moving left from center point
     * @param right:It is value for moving Right from center point
     * @throws Exception
     */
    public final void mouseHoverbyoffsett(String key, int left, int right) throws Exception {
        mouseHoverbyoffset(WEPVyoptaVideoEndpointsPageProperties.getProperty(key), left, right);
    }

    /**
     * This method gets the manufacturers count and checks if its over 1K
     * @param valueToExtractor
     * @return
     */
    public Integer getManufacturerCount(String valueToExtractor) {
        if(valueToExtractor.contains("K")){
            double value = Double.parseDouble(valueToExtractor.replace('K','\0'));
            return (int) (value *1000);
        }
        return Integer.parseInt(valueToExtractor);
    }
}