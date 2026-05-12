package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.LaunchDarkly;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class WEPTelemetryDataPage extends CommonMethod {
    public static String uiVersion = System.getProperty("uiVersion");
    private ObjectReader WEPTelemetryDataPagePageReader = new ObjectReader();
    private Properties WEXTelemetryDataPage;
    private WEPTelemetryDataPage instance;

    public WEPTelemetryDataPage(WebDriver driver) throws IOException {
    	WEXTelemetryDataPage = WEPTelemetryDataPagePageReader.getObjectRepository("WEXTelemetryData");
    }

    public WEPTelemetryDataPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WEPTelemetryDataPage.class) {
                if (instance == null) {
                    instance = new WEPTelemetryDataPage(DRIVER);
                }
            }
        }
        return instance;
    }

    /**
     * This method is the method to verify if an element is present on Telemetry Data Page
     * @param key - locator of the element
     * @return - boolean value of whether the element is present
     */
    public final boolean verifyElementsOfWEPTelemetryDataPage(String key) {
        try {
            return verifyElementIsPresent(WEXTelemetryDataPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementsOfWEPTelemetryDataPage " + e.getMessage()));
            return false;     
        }  
    }
    
    /**
     * This is the method to wait for any element on the Telemetry page until it is visible
     * @param key - locator of the element
     * @return - boolean value of whether the element is visible
     */
    public final boolean waitForElementsOfWEPTelemetryDataPage(String key) {
        try {
            return verifyElementIsVisible(WEXTelemetryDataPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitForElementsOfWEPTelemetryDataPage " + e.getMessage()));
            return false;
        }
    }

    public final void waitUntilElementIsPresentOnWEPTelemetryDataPage(String key) {
        try {
            waitUntillElementIsPresent(WEXTelemetryDataPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method waitUntillElementIsPresentOnWEPTelemetryDataPage " + e.getMessage()));
        }
    }
    
    /**
     * This is a method to click on element present on Telemetry Data page using java script
     * @param key - locator of the element
     */
    public final boolean clickOnElementsOfWEPTelemetryDataPage(String key) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(WEXTelemetryDataPage.getProperty(key))));
            element.click();
            return true;
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method clickOnElementsOfWEXTelemetryDataPage " + e.getMessage()));
            return false;
        }         
    }
    
    /**
     * This is a method to match text on an element which is present on Telemetry Data Page
     * @param key - locator of the element
     * @param textToMatch - text to be compared
     * @return - boolean value of whether both the texts match
     */
    public final boolean matchTextOfWEPTelemetryDataPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WEXTelemetryDataPage.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method matchTextOfWEPTelemetryDataPage " + e.getMessage()));
            return false;
        }    
    }
    
    /**
     * This is a method to get text present on an element on Telemetry Data page
     * @param key - locator of the element
     * @return - string value of the text present on the element
     */
    public final String getTextOfWEPTelemetryDataPage(String key) {
        try {
            return getTextBy(WEXTelemetryDataPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEPTelemetryDataPage " + e.getMessage()));
            return null;
        }
    } 
	
    /*
     * This is a method to get list of web elements
     * @param key - locator of the element
     * @return - list of web elements
     * */
    public List<WebElement> getWebelementsOfWEPTelemetryDataPage(String key) {
        try {
            return getAllElements(WEXTelemetryDataPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getWebelementsOfWEPTelemetryDataPage " + e.getMessage()));
            return null;
        }
    }
    
    /**
     * This method is used to get the current date in the format "MM/dd/yyyy"
     * @return formattedDate
     * @throws Exception
     */
    public String getTodaysDateInMMDDYYYY() {
        // Get today's date
        LocalDate today = LocalDate.now();

        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd MMM yyyy"));

        // Format today's date
        String formattedDate = today.format(formatter);
        return formattedDate;
    }
    
    /**
	 * This is a method to enter text on an element
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfTelemetryDataPage(String key, String Text) {
		try {
			enterText(WEXTelemetryDataPage.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextOfTelemetryDataPage " + e.getMessage()));
		}
	}
	
	public final void actionClickOfTelemetryDataPage(String key) throws Exception {
		actionClick(WEXTelemetryDataPage.getProperty(key));
	}
	
	/**
	 * This method is used to scroll to the pagination element on the Telemetry Data
	 * page
	 * 
	 * @param key - locator of the pagination element
	 */
	
	public void scrollToTelemetryDataPage(String key) throws Exception {
		scrollTillView(WEXTelemetryDataPage.getProperty(key));
	}
	
    /**
     * This is a method to remove the empty values from a list
     *
     * @param requestIdList - request Id number list (unfiltered)
     * @return - list of elements with empty values removed
     */
    public static List<String> removeEmptyDuplicate(List<String> requestIdList) {
        Set<String> uniquerequestIdSet = new LinkedHashSet<>(requestIdList);
        return uniquerequestIdSet.stream().filter((s -> !s.isEmpty())).collect(Collectors.toList());
    }

    /**
     * This is a method to all available request Id in the page
     *
     * @return List of string that is available in UI
     */
    public List<String> getAvailableRequestId() throws Exception {
        List<String> topAvailableRequestId = new ArrayList<>();
        sleeper(3000);
        List<WebElement> columnElements;
        columnElements = getElementsTillAllElementsPresent(WEXTelemetryDataPage.getProperty("requestIdRows"));

        if (!columnElements.isEmpty()) {
            for (WebElement element : columnElements) {
                topAvailableRequestId.add(element.findElement(By.xpath("//*[contains(@id,'sequence_id')]")).getText());
            }
        }
        System.out.println(("Request Id Number: " + topAvailableRequestId));
        LOGGER.info("Extracted available Request Id to test the sorting");
        return topAvailableRequestId;
    }

    /**
     * This is a method to get sorting order
     *
     * @return - true if the sort is descending, false otherwise
     */
    public boolean getSortingOrderType() throws Exception {
        String result = getAttribute(WEXTelemetryDataPage.getProperty("requestIdSortBtn"), "aria-label");
        return result.equals("sorted by Request Id descending, sort by ascending");
    }

    /**
     * This is a method to get sorting order
     *
     * @param requestIdNumber - List of device serial number to check sorting
     * @param isDesc             - To carry the sorting type
     * @return - true if the sort is descending, false otherwise
     */
    public boolean isRequestIdSorted(List<String> requestIdNumber, boolean isDesc) {
        List<String> uniquerequestIdNumberList = removeEmptyDuplicate(requestIdNumber);
        boolean isInOrder = true;
        if (isDesc) {
            for (int i = 0; i < uniquerequestIdNumberList.size() - 1; i++) {
                if (uniquerequestIdNumberList.get(i).compareTo(uniquerequestIdNumberList.get(i + 1)) <= 0) {
                    isInOrder = false;
                    break;
                }
            }
        } else {
            for (int i = 0; i < uniquerequestIdNumberList.size() - 1; i++) {
                if (uniquerequestIdNumberList.get(i).compareTo(uniquerequestIdNumberList.get(i + 1)) >= 0) {
                    isInOrder = false;
                    break;
                }
            }
        }
        requestIdNumber.clear();
        return isInOrder;
    }

	/**
	 * This method is used to verify the filtered data on the Telemetry Data page
	 * 
	 * @param list         - The locator of the list to be verified
	 * @param filteredData - The data to be compared with the list items
	 * @return true if all items in the list match the filtered data, false
	 *         otherwise
	 * @throws Exception if an error occurs during verification
	 */
    
    public final boolean verifyFilteredDataOnTelemetryDataPage(String list, String filteredData) throws Exception {
        List<String> uiList = getTextOfList(WEXTelemetryDataPage.getProperty(list));
        for (String uis : uiList) {
            if (!filteredData.equalsIgnoreCase(uis)) {
                LOGGER.info("Fails to compare filtered data Actual=" + uis + " Expected=" + filteredData);
                return false;
            }
        }
        return true;
    }

	/**
	 * This method checks if the telemetry data table is empty. It first checks for
	 * the "No results" message, and if not found, it checks if there are any rows
	 * in the table.
	 *
	 * @return true if the table is empty, false otherwise
	 * @throws Exception if an error occurs during the check
	 */
    
    public boolean isDataTableEmpty() throws Exception {
		// Check if the "No results" message is visible
		if (verifyElementsOfWEPTelemetryDataPage("noRecordText")) {
			return true;
		}
		// Alternatively, check if table rows are present
		List<WebElement> rows = getAllElements("statuscolumndropdown");
		return rows == null || rows.isEmpty();
	}
    
    /**
     * This is a method to hover mouse on an element
     *
     * @param key - Locator of element
     */
    public final void mousehoverOnTelemetryDataPage(String key) {
        try {
            mouseHover(WEXTelemetryDataPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method mousehoverOnTelemetryDataPage " + e.getMessage()));
        }
    }
    
    /**
     * This is a method to verify if an element on Telemetry Data page is enabled
     *
     * @param key - locator of the element
     * @return - boolean value of whether the element is enabled
     */
    public final boolean verifyElementIsEnableOnTelemetryDataPage(String key) {
        try {
            return verifyElementIsEnable(WEXTelemetryDataPage.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyElementIsEnableOnTelemetryDataPage " + e.getMessage()));
            return false;
        }
    } 
 
}
