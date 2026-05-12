package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WXPVideoEndpointsPage extends CommonMethod {
	private WXPVideoEndpointsPage instance;
    private ObjectReader WXPVideoEndpointsPagePropertiesReader = new ObjectReader();
    private Properties WXPVideoEndpointsPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(WXPVideoEndpointsPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");


	public WXPVideoEndpointsPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WXPVideoEndpointsPage.class) {
                if (instance == null) {
                    instance = new WXPVideoEndpointsPage(DRIVER);

                }
            }
        }
        return instance;

   }
	public WXPVideoEndpointsPage(WebDriver driver) throws IOException {
		WXPVideoEndpointsPageProperties  = WXPVideoEndpointsPagePropertiesReader.getObjectRepository("WXPVideoEndpointsPage");
    }
	
	public final boolean verifyElementsOfVideoEndpointsPage(String key) throws Exception {
        return verifyElementIsPresent(WXPVideoEndpointsPageProperties.getProperty(key));
    }
	
	public final boolean verifyByJavaScriptOfVideoEndpointsPage(String key) throws Exception {
        return verifyElementIsPresent(WXPVideoEndpointsPageProperties.getProperty(key));
    }
	

    public final void clickOnElementsOfVideoEndpointsPage(String key) throws Exception {
        click(WXPVideoEndpointsPageProperties.getProperty(key));
    }

    public final void mouseHoverAndClickOfVideoEndpointsPage(String key) throws Exception {
        actionClick(WXPVideoEndpointsPageProperties.getProperty(key));
    }

    public final void actionClickOfVideoEndpointsPage(String key) throws Exception {
        actionClick(WXPVideoEndpointsPageProperties.getProperty(key));
    }

    public final List<WebElement> getElementsOfVideoEndpointsPage(String key) throws Exception {
        return getAllElements(WXPVideoEndpointsPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnVideoEndpointsPage(String key) throws Exception {
        clickByJavaScript(WXPVideoEndpointsPageProperties.getProperty(key));
    }

    public final WebElement getElementOfVideoEndpointsPage(String key) throws Exception {
        return getElement(WXPVideoEndpointsPageProperties.getProperty(key));
    }

    public void scrollTillViewOnVideoEndpointsPage(String key) throws Exception {
        scrollTillView(WXPVideoEndpointsPageProperties.getProperty(key));
    }

    public final void listMouseHoverOnVideoEndpointsPage(String key) throws Exception {
        listMouseHover(WXPVideoEndpointsPageProperties.getProperty(key));
    }

    public final void enterTextOnVideoEndpointsPage(String key, String Text) throws Exception {
        enterText(WXPVideoEndpointsPageProperties.getProperty(key), Text);
    }

    public final void enterTextByJavaScriptOnVideoEndpointsPage(String key, String Text) throws Exception {
        enterTextUsingJavaScript(WXPVideoEndpointsPageProperties.getProperty(key), Text);
    }

    public final boolean verifyTextPresentOnElementOfVideoEndpointsPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WXPVideoEndpointsPageProperties.getProperty(key), Text);
    }

    public void waitForElementsOfVideoEndpointsPage(String string) {
	}
    
    public final String getTextOfWEPVideoEndpointsPage(String key) throws Exception {
        return getTextBy(WXPVideoEndpointsPageProperties.getProperty(key));
    }
    /**
     * This method is used to verify the table columns
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
     */
    public List<String> getAvailableEndpoints() throws Exception {
        List<String> allData = getUniqueElementsofStringsFromList(WXPVideoEndpointsPageProperties.getProperty("videoEndpointPageFirstRowData"));
        LOGGER.info("Extracted available endpoints to test the sorting");
        return allData;
    }

    public boolean getSortingOrderType(String columnLocator) throws Exception {
        String result = getAttribute(WXPVideoEndpointsPageProperties.getProperty(columnLocator), "aria-label");
        return result.equals("sorted by Endpoint Name descending, sort by ascending");
    }

    /**
     * This is a method to get sorting order
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

    public String isFileExists(String filePath) {
		return null;
	}

    public void switchToIframeVideoEndpointsPage() {
        try {
            switchToIframe(WXPVideoEndpointsPageProperties.getProperty("VyoptaPageIframe"));
        } catch (Exception e) {
            Assert.fail("Failed to switch to integrations iframe: " + e.getMessage());

        }
    }
	

}
