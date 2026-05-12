package com.daasui.pages;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WEPVyoptaTelephonePage extends CommonMethod {
    private WEPVyoptaTelephonePage instance;
    private ObjectReader WEPVyoptaTelephonePagePropertiesReader = new ObjectReader();
    private Properties WEPVyoptaTelephonePageProperties;
    private final static Logger LOGGER = LogManager.getLogger(WEPVyoptaTelephonePage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");


    public WEPVyoptaTelephonePage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPVyoptaTelephonePage.class) {
                if (instance == null) {
                    instance = new WEPVyoptaTelephonePage(DRIVER);

                }
            }
        }
        return instance;
    }

    public WEPVyoptaTelephonePage(WebDriver driver) throws IOException {
        WEPVyoptaTelephonePageProperties = WEPVyoptaTelephonePagePropertiesReader.getObjectRepository("WEPVyoptaTelephonePage");
    }

    public final boolean verifyElementsOfTelephonePage(String key) throws Exception {
        return verifyElementIsPresent(WEPVyoptaTelephonePageProperties.getProperty(key));
    }

    public final void clickOnElementsOfTelephonePage(String key) throws Exception {
        click(WEPVyoptaTelephonePageProperties.getProperty(key));
    }

    public final void mouseHoverAndClickOfTelephonePage(String key) throws Exception {
        actionClick(WEPVyoptaTelephonePageProperties.getProperty(key));
    }
    /**
     * This method is to mouse hover and click through javascript for a webelement
     * @param key - webelement
     * @throws Exception
     */
    public final void mouseHoverAndClickOfTelephonePage(WebElement key) throws Exception {
        mouseHoverclick(key);
    }

    public final void actionClickOfTelephonePage(String key) throws Exception {
        actionClick(WEPVyoptaTelephonePageProperties.getProperty(key));
    }

    public final List<WebElement> getElementsOfTelephonePage(String key) throws Exception {
        return getAllElements(WEPVyoptaTelephonePageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnTelephonePage(String key) throws Exception {
        clickByJavaScript(WEPVyoptaTelephonePageProperties.getProperty(key));
    }

    public final WebElement getElementOfTelephonePage(String key) throws Exception {
        return getElement(WEPVyoptaTelephonePageProperties.getProperty(key));
    }

    public void scrollTillViewOnTelephonePage(String key) throws Exception {
        scrollTillView(WEPVyoptaTelephonePageProperties.getProperty(key));
    }

    public final void listMouseHoverOnTelephonePage(String key) throws Exception {
        listMouseHover(WEPVyoptaTelephonePageProperties.getProperty(key));
    }

    public final void enterTextOnTelephonePage(String key, String Text) throws Exception {
        enterText(WEPVyoptaTelephonePageProperties.getProperty(key), Text);
    }


    public final void enterTextByJavaScriptOnTelephonePage(String key, String Text) throws Exception {
        enterTextUsingJavaScript(WEPVyoptaTelephonePageProperties.getProperty(key), Text);
    }

    public final boolean verifyTextPresentOnElementScriptsPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WEPVyoptaTelephonePageProperties.getProperty(key), Text);
    }

    /**
     * This method designed to get the  Text of WebElement from web page
     * @param key
     */
    public final String getTextOfWEPTelephonesPage(String key) throws Exception {
        return getTextBy(WEPVyoptaTelephonePageProperties.getProperty(key));
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
            List<WebElement> actualColumnList = getElementsOfTelephonePage(key);
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


    public final boolean waitForElementsOfTelephonePage(String key, int waitTime) throws Exception {
        return verifyElementIsVisibleDynamic(WEPVyoptaTelephonePageProperties.getProperty(key), waitTime);
    }
    /**
     * This is a method to clear the filter if present in UI
     */
    public void clearFiltersOfTelephonePage(String clearFilterKey) throws Exception {
        if(waitForElementsOfTelephonePage(clearFilterKey,5)) {
            clearFilters(WEPVyoptaTelephonePageProperties.getProperty(clearFilterKey));
        }
    }

    /**
     * This method gets the total pagination number
     * @param locator - locator
     * @return total count displayed
     * @throws Exception
     */
    public int getTotalNumbersFromPagination(WEPVyoptaTelephonePage wepVyoptaTelephonePage, String locator) throws Exception {
        String totalDeviceText = wepVyoptaTelephonePage.getTextOfWEPTelephonesPage(locator);
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
        mouseHoverbyoffsetClick(WEPVyoptaTelephonePageProperties.getProperty(key),left,right);
    }

    /**
     * This is a method to get all clickable elements of the page
     *
     * @return List of string that is available in UI
     */
    public List<String> getAvailableEndpoints() throws Exception {
        List<String> allData = getUniqueElementsofStringsFromList(WEPVyoptaTelephonePageProperties.getProperty("telephonesPageFirstRowData"));
        LOGGER.info("Extracted available endpoints to test the sorting");
        return allData;
    }
    /**
     * This is a method to get sorting order
     *
     * @return - true if the sort is descending, false otherwise
     */
    public boolean getSortingOrderType(String columnLocator) throws Exception {
        String result = getAttribute(WEPVyoptaTelephonePageProperties.getProperty(columnLocator), "aria-label");
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
                .map(s -> s.replaceAll("\\s+", "")
                        .replaceAll("[^a-zA-Z0-9]", "")
                        .toLowerCase())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * This method verifies if count is matching
     * @param locator - locator to check
     * @param finalCountToVerify -number to verify against
     * @return true if count is matching false otherwise
     * @throws Exception
     */
    public boolean isCountMatching(String locator, int finalCountToVerify) throws Exception {
        int finalCount = Integer.parseInt(getTextOfWEPTelephonesPage(locator));
        return finalCountToVerify == finalCount;
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
                mouseHoverAndClickOfTelephonePage(element);

                String latestCountOfRemainPcs = getTextOfWEPTelephonesPage("centerWidgetValue");
                if(!latestCountOfRemainPcs.isEmpty()){
                    String manName = element.getText();
                    int resultToTest=   getManufacturerCount(latestCountOfRemainPcs);
                    differentManufacturerPcs.put(manName,resultToTest);

                }
                mouseHoverAndClickOfTelephonePage(element);
            }
        }
        return differentManufacturerPcs;
    }

    /**
     * This gets the total manufacturer count and accounts for over 1k in number.
     * @param valueToExtractor center value to extract
     * @return
     */
    public Integer getManufacturerCount(String valueToExtractor) {
        if(valueToExtractor.contains("K")){
            double value = Double.parseDouble(valueToExtractor.replace('K','\0'));
            return (int) (value *1000);
        }
        return Integer.parseInt(valueToExtractor);
    }
    public boolean verifyTheCountWithinRange(Integer roundedCount, int actualCount) {
        int difference = roundedCount-actualCount;
        return difference <= 100;
    }

    /**
     * This method verifies if the count is matching once redirected to device list page
     * @param fleetInventoriesListElements - elements of fleet widget
     * @param differentManuDeviceCount - count for the different manufacturers
     * @return true if count is matching
     * @throws Exception
     */
    public boolean verifyManufacturerCountFromTelephoneList(List<WebElement> fleetInventoriesListElements, HashMap<String, Integer> differentManuDeviceCount, WEPVyoptaTelephonePage wepVyoptaTelephonePage) throws Exception {
        mouseHoverAndClickOfTelephonePage(fleetInventoriesListElements.get(1));
        String pcInventoryName = fleetInventoriesListElements.get(1).getText();
        int latestCountOfRemainPcs = getManufacturerCount(wepVyoptaTelephonePage.getTextOfWEPTelephonesPage("centerWidgetValue"));
        boolean flag = false;
        sleeper(2000);

        if(differentManuDeviceCount.containsKey(pcInventoryName) && differentManuDeviceCount.get(pcInventoryName) == latestCountOfRemainPcs){
            wepVyoptaTelephonePage.mouseHoverbyoffsettClick("chartCircle", 00, 75);
            waitForPageLoaded();
            int numberOfTotalDevices = wepVyoptaTelephonePage.getTotalNumbersFromPagination(wepVyoptaTelephonePage,"showingPaginationTotalCount");
            flag = verifyTheCountWithinRange(differentManuDeviceCount.get(pcInventoryName), numberOfTotalDevices);
            LOGGER.info("Verified redirection count of first manufacturer is matching device list page count");
        }
        return flag;
    }
}

