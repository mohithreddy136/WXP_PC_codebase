package com.daasui.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.interactions.Actions;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.mchange.v2.resourcepool.TimeoutException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONObject;

public class WXPWorkflowsPage extends CommonMethod {
    private WXPWorkflowsPage instance;
    private ObjectReader WXPWorkflowsPagePropertiesReader = new ObjectReader();
    private Properties WXPWorkflowsPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(WXPWorkflowsPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");

    public WXPWorkflowsPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WXPWorkflowsPage.class) {
                if (instance == null) {
                    instance = new WXPWorkflowsPage(DRIVER);
                }
            }
        }
        return instance;
    }

    public WXPWorkflowsPage(WebDriver driver) throws IOException {
        WXPWorkflowsPageProperties = WXPWorkflowsPagePropertiesReader.getObjectRepository("WXPWorkflowsPage");
    }

    public final boolean verifyElementsOfWorkflowsPage(String key) throws Exception {
        return verifyElementIsPresent(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final void clickOnElementsOfWorkflowsPage(String key) throws Exception {
        click(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final void mouseHoverAndClickOfWorkflowsPage(String key) throws Exception {
        actionClick(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final void actionClickOfWorkflowsPage(String key) throws Exception {
        actionClick(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final List<WebElement> getElementsOfWorkflowsPage(String key) throws Exception {
        return getAllElements(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnWorkflowsPage(String key) throws Exception {
        clickByJavaScript(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final void doubleClickOnWorkflowsPage(String key) throws Exception {
    	doubleclick(WXPWorkflowsPageProperties.getProperty(key));
    }
    
    public final WebElement getElementOfWorkflowsPage(String key) throws Exception {
        return getElement(WXPWorkflowsPageProperties.getProperty(key));
    }

    public void scrollOnWorkflowsPage(String key) throws Exception {
        scrollTillView(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final void listMouseHoverOnWorkflowsPage(String key) throws Exception {
        listMouseHover(WXPWorkflowsPageProperties.getProperty(key));
    }

    public void enterTextOnWorkflowsPage(String key, String text) {
        try {
            enterText(WXPWorkflowsPageProperties.getProperty(key), text);
            LOGGER.info("Entered text '{}' into field: {}", text, key);
        } catch (Exception e) {
            LOGGER.error("Error entering text in field {}: {}", key, e.getMessage());
        }
    }

    public final void enterTextWithoutClearOnWorkflowsPage(String key, String Text) throws Exception {
        enterTextwithoutclear(WXPWorkflowsPageProperties.getProperty(key), Text);
    }

    public final void scrollTillViewWorkflowsPage(String key) throws Exception {
        scrollTillView(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final void enterTextByJavaScriptOnWorkflowsPage(String key, String Text) throws Exception {
        enterTextUsingJavaScript(WXPWorkflowsPageProperties.getProperty(key), Text);
    }

    public final boolean verifyTextPresentOnElementWorkflowsPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WXPWorkflowsPageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementIsDisplayedOfWorkflowsPage(String key) throws Exception {
        return verifyElementIsVisible(WXPWorkflowsPageProperties.getProperty(key));
    }

    public final void onChangeCalendarEventOfWorkflowsPage(String key) throws Exception {
        onChangeCalendarEvent(WXPWorkflowsPageProperties.getProperty(key));
    }
	
	/**
	 * This method designed to get the  Text of WebElement from web page
	 * @param WebElement 
	 */
	public final String getTextOfWorkflowsPage(String key) throws Exception {
		return getTextBy(WXPWorkflowsPageProperties.getProperty(key));
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
            List<WebElement> actualColumnList = getElementsOfWorkflowsPage(key);
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

    /* This is a method to match text on an element
     *
     * @param key         - Locator of element
     * @param textToMatch - Text to be matched
     * @return - boolean value of whether the text present on element matches or not
     */
    public final boolean matchTextOfWorkflowsPage(String key, String textToMatch) {
        try {
            return verifyTextPresentOnElement(WXPWorkflowsPageProperties.getProperty(key), textToMatch);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method matchTextOfWorkflowsPage {}", e.getMessage());
            return false;
        }
    }

    /**
     * This method used to check is any value exist on the extracted web element
     * @param locator - Web element locator key
     * @return - either true/false if value exist
     * @throws Exception - On exception
     */
    public boolean anyValueExists(String locator) throws Exception {
        return !getElementOfWorkflowsPage(locator).getText().isEmpty();
    }

    /**
     * This method used to extract the text of a web element
     * @param locator - Web element locator key
     * @return - String webelement
     */
    public String getTextOfWorkflowsPageElement(String locator) throws Exception {
        return getTextBy(WXPWorkflowsPageProperties.getProperty(locator));
    }

    /**
     * This method used to get the attribute of the workflows page
     *
     * @param locator - Web element locator key
     * @param attributeName - attribute of web element
     * @return - String attribute of web element
     */
    public String getAttributeOfWorkflowsPageElement(String locator, String attributeName) throws Exception {
        return getAttribute(WXPWorkflowsPageProperties.getProperty(locator), attributeName);
    }

    /**
     * This method used to send keys
     *
     * @param key - Web element locator key
     * @param value - value to send to webelement
     */
    public final void sendKeysOnWXPWorkflowsPage(String key, String value) throws Exception {
        enterTextWithoutClearOnWorkflowsPage(key, value);
        onChangeCalendarEventOfWorkflowsPage(key);
    }
	
	/**
     * This method is used to verify the table columns in Activity tab
     * @param expectedColumnList
     * @return
     * @throws Exception
     */
    public boolean verifyColumnsinActivityPage(List<String> expectedColumnList, String key) {
        boolean flag = true;
        int counter = 0;

        try {
            List<WebElement> actualColumnList = getElementsOfWorkflowsPage(key);

            for (WebElement we : actualColumnList) {

                if (counter >= expectedColumnList.size()) {
                    break;
                }

                String actualText = we.getText();
                if (actualText == null || actualText.trim().isEmpty()) {
                    continue;
                }
                actualText = actualText.split("\n")[0];
                actualText = actualText.replaceAll("[^a-zA-Z ]", "").trim();
                String expectedRaw = expectedColumnList.get(counter);
                if (expectedRaw == null) {
                    LOGGER.error("Expected column at index " + counter + " is NULL");
                    return false;
                }
                String expectedText = expectedRaw
                        .replaceAll("[^a-zA-Z ]", "")
                        .trim();
                if (actualText.equalsIgnoreCase(expectedText)) {
                    counter++;
                } else {
                    LOGGER.error(
                            "Column mismatch at index " + counter +
                            " | Expected: [" + expectedText +
                            "] | Found: [" + actualText + "]"
                    );
                    flag = false;
                    break;
                }
            }
            if (counter != expectedColumnList.size()) {
                LOGGER.error(
                        "Column count mismatch | Expected: " +
                        expectedColumnList.size() +
                        " | Matched: " + counter
                );
                flag = false;
            }
        } catch (Exception e) {
            LOGGER.error("Error while verifying table columns: " + e.getMessage(), e);
            flag = false;
        }

        return flag;
    }
	
	public final void mouseHoverOfWorkflowsPage(String key) throws Exception {
		mouseHover(WXPWorkflowsPageProperties.getProperty(key));
	}
	
	public final void clearTextOfWorkflowsPage(String key) throws Exception {
		clearTextofWorkflow(WXPWorkflowsPageProperties.getProperty(key));
	}
    
    public void switchToNewTab() {
	    String current = getDriver().getWindowHandle();
	    new WebDriverWait(getDriver(), Duration.ofSeconds(10))
	            .until(driver -> driver.getWindowHandles().size() > 1);

	    for (String tab : getDriver().getWindowHandles()) {
	        if (!tab.equals(current)) {
	            getDriver().switchTo().window(tab);
	            break;
	        }
	    }
	}
         
    public static String getRoundedTimeTo15Min() {

	    LocalTime now = LocalTime.now();

	    int minute = now.getMinute();
	    int modulo = minute % 15;
	    if (modulo == 0) {
	        return now.withSecond(0).withNano(0)
	                  .format(DateTimeFormatter.ofPattern("h:mm a").withLocale(Locale.US));
	    }
	    int minutesToAdd = 15 - modulo;

	    LocalTime rounded = now.plusMinutes(minutesToAdd)
	                           .withSecond(0)
	                           .withNano(0);

	    return rounded.format(DateTimeFormatter.ofPattern("h:mm a").withLocale(Locale.US));
	}

    
 
	 public static void clickonblankpage() {
	        WebDriver driver = PreDefinedActions.getDriver();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement canvas = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='react-flow__pane draggable']")));
	        Actions actions = new Actions(driver);
	        actions.moveToElement(canvas, 200, 200).click().perform();
	    }
	 
	 /**
	     * Validate JSON payload from filepath using JavaScript
	     * This method handles special characters and large payloads correctly
	     * @param jsonPayload is validating
	     */
	private static void validateJson(String jsonString) {
		    try {
		        // Check if JSON is Object or Array
		        if (jsonString.startsWith("{")) {
		            new JSONObject(jsonString);
		        } else if (jsonString.startsWith("[")) {
		            new JSONArray(jsonString);
		        } else {
		            throw new JSONException("Invalid JSON format");
		        }
		    } catch (JSONException e) {
		        throw new RuntimeException("JSON validation failed: Invalid format", e);
		    }
		}
	 
	 /**
	     * get JSON data from the file using JavaScript
	     * This method handles special characters and large payloads correctly
	     * @param jsonPayload
	     */
	 
	public static String getJsonData(String filePath) {
		    if (filePath == null || filePath.trim().isEmpty()) {
		        throw new IllegalArgumentException("File path cannot be null or empty");
		    }

		    Path path = Paths.get(filePath);

		    if (!Files.exists(path)) {
		        throw new RuntimeException("File not found: " + filePath);
		    }

		    if (!Files.isReadable(path)) {
		        throw new RuntimeException("File is not readable: " + filePath);
		    }

		    try {
		        String content = Files.readString(path, StandardCharsets.UTF_8);

		        if (content.isEmpty()) {
		            throw new RuntimeException("JSON file is empty: " + filePath);
		        }

		        validateJson(content);

		        LOGGER.info("Successfully loaded JSON from: " + filePath);
		        return content;

		    } catch (IOException e) {
		        throw new RuntimeException("Failed to read JSON file: " + filePath, e);
		    } catch (com.google.gson.JsonSyntaxException e) {
		        throw new RuntimeException("Invalid JSON syntax in file: " + filePath, e);
		    }
		}



	    /**
	     * Enter JSON payload into textarea using JavaScript
	     * This method handles special characters and large payloads correctly
	     * @param jsonPayload the JSON string to enter
	     */

	public void enterJsonPayloadToTextarea(String jsonPayload) {
		    try {
		        if (!isValidJson(jsonPayload)) {
		            throw new IllegalArgumentException("Invalid JSON payload format");
		        }
		        WebElement payloadTextarea = getElementsOfWorkflowsPage("PayloadText").get(0);
		        JavascriptExecutor jsExecutor = (JavascriptExecutor) PreDefinedActions.getDriver();
		        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", payloadTextarea);
		        jsExecutor.executeScript(
		            "var element = arguments[0];" +
		            "var value = arguments[1];" +
		            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(" +
		            "window.HTMLTextAreaElement.prototype, 'value').set;" +
		            "nativeInputValueSetter.call(element, value);" +
		            "element.dispatchEvent(new Event('input', { bubbles: true }));",
		            payloadTextarea,
		            jsonPayload
		        );
		        jsExecutor.executeScript("arguments[0].blur();", payloadTextarea);
		        LOGGER.info("JSON payload entered successfully");
		    } catch (Exception e) { 
		        LOGGER.error("Failed to enter JSON payload: " + e.getMessage(), e);
		        throw new RuntimeException("Failed to enter JSON payload into textarea", e);
		    }
		}

	 
	private boolean isValidJson(String jsonPayload) {
		if (jsonPayload == null || jsonPayload.trim().isEmpty()) {
			LOGGER.warn("JSON payload is null or empty");
			return false;
		}

		try {
			new com.google.gson.JsonParser().parse(jsonPayload);
			LOGGER.info("JSON payload format is valid");
			return true;
		} catch (com.google.gson.JsonSyntaxException e) {
			LOGGER.error("Invalid JSON syntax: " + e.getMessage());
			return false;
		}
	}

	
	 public final boolean waitUntilElementIsInvisibleOfWorkflowsPage(String key){
			return verifyElementIsinvisibile(WXPWorkflowsPageProperties.getProperty(key));
		}
	 	 
	 public By getDynamicXpath(String key, String value) {
		    String xpath = String.format(WXPWorkflowsPageProperties.getProperty(key), value);
		    return By.xpath(xpath);
		}

	 /**
		 * Helper method to select an item from a drop-down
		 * @param dropdownOptions
		 * @param inputValue
		 * @throws Exception
		 */ 
	 public void selectFromDropdown(String dropdownOptions, String inputValue) throws Exception {

		    List<WebElement> optionsList = getElementsTillAllElementsVisibleofWorkflowpages(dropdownOptions);

		    if (optionsList == null || optionsList.isEmpty()) {
		        throw new RuntimeException("Dropdown options not found for key : " + dropdownOptions);
		    }
		    String expected = normalizeText(inputValue);
		    LOGGER.info("Looking for dropdown value : [{}]", expected);

		    for (WebElement option : optionsList) {

		        String actual = normalizeText(option.getText());
		        LOGGER.info("Available option : [{}]", actual);

		        if (actual.equalsIgnoreCase(expected)) {
		            clickWebelement(option);
		            LOGGER.info("Selected required option : {}", actual);
		            return;
		        }
		    }

		    throw new RuntimeException("Value not found in dropdown : " + expected);
		}
	 
	 private String normalizeText(String text) {

		    if (text == null) return "";

		    return text
		            .replace('\u00A0',' ')   
		            .replace('\u202F',' ')   
		            .replaceAll("\\s+"," ")
		            .trim();
		}
		
		/**
		 * This is a method to get a list of elements present on pulse creation page
		 * @param key - Locator of element
		 * @return - list of web elements
		 */
		public final List<WebElement> getElementsTillAllElementsVisibleofWorkflowpages(String key) {
			try {
				return getElementsTillAllElementsPresent(WXPWorkflowsPageProperties.getProperty(key));
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofPulseCreationpage " + e.getMessage()));
				return null;
			}
		}

		
	/*
	 * This method will fetch the current time and select the time in dropdown
	 */ 
	public void selectRoundedUpCurrentTimeFromDropdown(String dropdownClickKey,String dropdownOptionsKey) throws Exception {

		String expectedRoundedTime = getRoundedTimeTo15Min();
		LOGGER.info("Rounded time to select: {}", expectedRoundedTime);
		actionClickOfWorkflowsPage(dropdownClickKey);
		selectFromDropdown(dropdownOptionsKey, expectedRoundedTime);
		
		LOGGER.info("Successfully selected time from dropdown: {}", expectedRoundedTime);
		}
	 
	 /* This is a method to match text on an element
     *
     * @param key         - Locator of element
     * @param textToMatch - Text to be matched
     * @return - boolean value of whether the text present on element matches or not
     */
    public final boolean matchTextOfWorkflowPage(String key, List<String> texts) {
        try {
            return verifyTextsPresentOnElement(WXPWorkflowsPageProperties.getProperty(key), texts);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in method matchTextOfDeviceListPage {}", e.getMessage());
            return false;
        }

    }
    
	/**
     * Drag the source element to destination element using JavaScript and Robot class
     * This method  Drag the source element to destination element using JavaScript and Robot class
     * @param Drag and Drop source element locator key and destination element locator key
     */	
	 public void dragAndDropOfWorkflowpage(String sourceKey, String destKey) throws Exception {
    	dragAndDrop(WXPWorkflowsPageProperties.getProperty(sourceKey),WXPWorkflowsPageProperties.getProperty(destKey));
	}
      
    /**
     * Fetches all device names from the table with the given id and returns a sorted list.
     *
     * @param driver WebDriver instance
     * @param tableId The id of the table containing devices
     * @return Alphabetically sorted list of device names
     */
    public List<String> getSortedDeviceNames() throws Exception {
        List<WebElement> deviceElements = getElementsOfWorkflowsPage("WorkflowDeviceList");
        List<String> deviceNames = new ArrayList<>();
        if (deviceElements != null) {
            for (WebElement device : deviceElements) {
                String name = device.getText().trim();
                if (!name.isEmpty()) {
                    deviceNames.add(name);
                }
            }
        }
        Collections.sort(deviceNames);
        LOGGER.info("Sorted device names: {}", deviceNames);
        return deviceNames;
    }

	}
    

    

