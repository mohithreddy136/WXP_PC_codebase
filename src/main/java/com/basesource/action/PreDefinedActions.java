package com.basesource.action;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Map;
import org.openqa.selenium.Point;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import com.basesource.utils.ExcelReader;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.WEPPolicyPage;
import com.google.common.base.Strings;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.AWTException;


public class PreDefinedActions {
	public WebDriver DRIVER;
	public static WebDriverWait wait;
	
	public String parentWindowHandler = null;
	protected ExcelReader excelReader;
	protected final static Logger LOGGER = LogManager.getLogger(PreDefinedActions.class);
	public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();
	public static String osName = System.getProperty("osName");
	protected JSONParser parser = new JSONParser();
	
	

	public static WebDriver getDriver() {
		return dr.get();
	}

	public static void setWebDriver(WebDriver DRIVER) {
		dr.set(DRIVER);
	}

	public JavascriptExecutor jsDriver(){
		 return (JavascriptExecutor) getDriver();
	}
	
	
	/**
	 * Wait until page get load. It will wait till all the elements of page get loaded
	 * 
	 */
	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}
	
	
	/**
	 * Find if the element is present or not
	 * 
	 */
	public boolean verifyElementIsPresent(String locator) throws Exception {
		WebElement element = null;
		try {
			element = getDriver().findElement(this.getObject(locator));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsPresent");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}
		
	/**
	 * Find if the element is enabled or disabled
	 * 
	 */
	public boolean verifyElementIsEnaledOrDisabled(String locator) throws Exception {
		WebElement element = null;
		try {
			element = getDriver().findElement(this.getObject(locator));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			 String ariaDisabled = element.getAttribute("aria-disabled");

		        // Check if the element is enabled or disabled based on the aria-disabled attribute
		        if (ariaDisabled != null && ariaDisabled.equals("true")) {
		          //  System.out.println("The element is disabled.");
		        	return false;
		        } else {
		           // System.out.println("The element is enabled.");
		            return element.isEnabled();
		        }
		} catch (NoSuchElementException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsPresent");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}        
        
	/**
	 * get WebElement when it is visible.
	 * 
	 * @return
	 */
	public final WebElement getElement(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element;
	}

	/**
	 * Find if the element is visible or not
	 * 
	 */
	public boolean verifyElementIsVisible(String locator) throws Exception {
		WebElement element = null;
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return true;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsVisible");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * Find if the element is visible or not
	 * 
	 */
	public boolean verifyElementIsVisibleDynamic(String locator,int waitTime) throws Exception {
		WebElement element = null;
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTime));
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return true;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsVisible");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Find if the element is present or not
	 * 
	 */
	public boolean waitUntillElementIsPresent(String locator) {
		WebElement element = null;
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return true;
		} catch (TimeoutException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception waitUntillElementIsPresent");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Find if the element is present or not (Dynamic)
	 * @param locator
	 * @param explicitWait
	 * @return
	 */
	public boolean waitUntillElementIsPresentDynamic(String locator,int explicitWait) {
		WebElement element = null;
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(explicitWait));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return true;
		} catch (TimeoutException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception waitUntillElementIsPresent");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Find if text is present on element or not
	 *
	 */
	public boolean verifyTextPresentOnElement(String locator, String text){
		WebElement element = null;
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return wait.until(ExpectedConditions.textToBePresentInElementLocated(this.getObject(locator), text));
		} catch (TimeoutException e) {
			LOGGER.error(e.getMessage());
			return false;
			
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyTextPresentOnElement");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Find if the element is enable or not
	 * 
	 */
	public boolean verifyElementIsEnable(String locator) throws Exception {
		WebElement element = null;
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return element.isEnabled();
		} catch (TimeoutException e) {
			e.printStackTrace();
			return false;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsEnable");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Find if the element is clickable or not
	 * 
	 */
	public boolean verifyElementIsClickable(String locator) throws Exception {
		WebElement element = null;
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			element = wait.until(ExpectedConditions.elementToBeClickable(this.getObject(locator)));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return true;
		} catch (TimeoutException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsClickable");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Find if the elements are clickable or not
	 * 
	 * @param uiList accepts list of web elements
	 * @throws Exception
	 * @return flag if all elements are clickable returns true
	 */
	public boolean verifyElementsAreClickable(WebElement uiList) throws Exception {

		boolean flag = true;
		if (wait.until(ExpectedConditions.elementToBeClickable(uiList)) == null) {
			flag = false;
		}
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", uiList);
		return flag;
	}

	/**
	 * Find if the element is checked or not
	 * 
	 */
	public boolean verifyElementIsSelected(String locator) throws Exception {
		WebElement element = null;
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return element.isSelected();
		} catch (TimeoutException e) {
			return false;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsSelected");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get text
	 */
	public String getTextBy(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element.getText().trim();
	}

	/**
	 * This is a method to get text present on an element on partner page
	 *
	 * @param element - locator of the element
	 * @return - string value of the text present on the element
	 */
	public String getTextBy(WebElement element) throws Exception {
		try{
			jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
			return element.getText().trim();
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextBy " + e.getMessage()));
			return null;
		}
	}

	public String getTextByDynamic(String locator,int waitTime) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTime));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element.getText().trim();
	}
	
	/**
	 * Get all text in list
	 */
	public List<String> getallTextBy(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		List<WebElement> element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.getObject(locator)));
		//jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		for(WebElement Webelement: element) {
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", Webelement);
		String text = Webelement.getText();
		columnNamesOnPage.add(text);
		}
		return columnNamesOnPage;
	}
	/** This is used to fetch any parameter from Auth token.
	 * @param authToken: Parameter name.
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String authTokenParse(String authToken) throws UnsupportedEncodingException {
		// Need to split the auth token based on the "." so that the body received in
		// the auth token can be decoded
		String b64payload = null;
		String[] splitAuth = authToken.split("\\.");
		if (splitAuth.length >= 1) {
			b64payload = splitAuth[1];
		} else {
			LOGGER.error("Authorization token has some problem, Please check token again");
		}
		// Decoding the body received in the auth token
		return new String(Base64.decodeBase64(b64payload), "UTF-8");
	}

	/**
	 * Click element
	 */
	public final void click(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		jsDriver().executeScript("arguments[0].click();", element);
	}
	
	/**
	 * Click a webElement
	 * 
	 * @param key - webElement
	 * @throws Exception
	 */
	public final void clickWebelement(WebElement key) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOf(key));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		jsDriver().executeScript("arguments[0].click();", element);	
	}

	/**
	 * Click element using java script
	 */
	public final void clickByJavaScript(String locator) throws Exception {
		WebElement element = getDriver().findElement(this.getObject(locator));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		jsDriver().executeScript("arguments[0].click();", element);
	}
	
	/**
	 * Click multiple element
	 */
	public final void multipleclick(String locator, Integer count) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		for(int i=1;i<=count-1;i++) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		element.click();	
		sleeper(2000);
		}
	}
	
	/**
	 * Enter text
	 */
	public final void enterText(String locator, String text) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		if (isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)) {
			if (getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v3\""))
				clearText(locator);
			else
				clearText(locator);
		}
		else{
			clearText(locator);
		}
		if (isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)) {
			if (getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v3\"")){

				StringSelection stringSelection= new StringSelection(text);
				 Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				 clipboard.setContents(stringSelection, null); // copy text to the keyboard
				 element.sendKeys(Keys.CONTROL+"v");
			}else{
				element.sendKeys(text);
			}
		}else{
			//element.sendKeys(text);
			StringSelection stringSelection= new StringSelection(text);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null); // copy text to the keyboard
			element.sendKeys(Keys.CONTROL+"v");
		}
	}
	
	 /** Enter text without clear
	 */
	public final void enterTextwithoutclear(String locator, String text) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		if (isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)) {
			if (getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v3\"")){
				StringSelection stringSelection= new StringSelection(text);
				 Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				 clipboard.setContents(stringSelection, null); // copy text to the keyboard
				 element.sendKeys(Keys.CONTROL+"v");
			}else{
				element.sendKeys(text);
			}
		}else{
			element.sendKeys(text);
		}
	}
	
	/**
	 * Method for entering text using javascript
	 * 
	 * @param locator of the webelement
	 * @param text to be entered
	 * @throws Exception
	 */
	public final void enterTextUsingJavaScript(String locator, String text) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		element.clear();
		jsDriver().executeScript("arguments[0].value='" + text + "'", element);
	}

	public final void clearAndReplaceText(String locator, String newValue) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));

		// Highlight the element for visibility
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);

		// Clear the text field using multiple approaches
		element.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all text
		element.sendKeys(Keys.DELETE); // Delete selected text
		element.clear(); // Use clear method as a fallback

		// Check if the field still contains the default value (e.g., "0")
		if ("0".equals(element.getAttribute("value").trim())) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE); // Retry clearing
		}

		// Enter the new value
		element.sendKeys(newValue);
	}

	/**
	 * Enter text
	 */
    public final void enterTextLogin(String locator, String text) throws Exception {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
        jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
        if (isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)) {
            if (getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v3\""))
                clearTextRefresh(locator);
            else
                clearText(locator);
        }
        else{
            clearText(locator);
        }
        // element.sendKeys(text); // Commenting this as we are using clipboard to paste the value in case of UI V3 and for rest of the UI we can use sendkeys
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        element.sendKeys(Keys.CONTROL + "v");
    }
	
	/**
	 * get attribute value
	 */
	public final String getAttribute(String locator, String attribute) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element.getAttribute(attribute);
	}

	/**
	 * get attributes for all value
	 */
	public final List<String> getAllAttributes(String locator, String attribute) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		ArrayList<String> columnNamesOnPage = new ArrayList<>();	
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.getObject(locator)));
		for(WebElement element:elements) {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		columnNamesOnPage.add(element.getAttribute(attribute));
		}
		return columnNamesOnPage;
	}
	
	/**
	 * get attribute value
	 */
	public final String getAttributeDynamic(String locator, String attribute,int waitTime) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTime));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element.getAttribute(attribute);
	}
	
	
	/**
	 * gettext of Mouse Hover elements
	 */
	public final List<String> gettextmouseHoverelements(String locator1,String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.getObject(locator1)));
		for(WebElement element:elements) {
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Actions action = new Actions(getDriver());
		action.click(element);
		action.moveToElement(element).perform();
		sleeper(5000);
	    String text2 = getTextBy(locator);
	    columnNamesOnPage.add(text2);
		}
		return columnNamesOnPage;
	}
	/**
	 * Mouse Hover
	 */
	public final void mouseHover(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Actions action = new Actions(getDriver());
		action.moveToElement(element);
		action.perform();
	}
	
	public final void actionClick(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Actions action = new Actions(getDriver());
		action.moveToElement(element).click().perform();
	}

	/**
	 * Right Click element
	 */
	public final void rightclick(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Actions action = new Actions(getDriver());
		action.contextClick(element).perform();
	}

	/**
	 * Perform hard refresh
	 */
	public final void hardRefresh() {
		jsDriver().executeScript("location.reload(true);");
	}

	/**
	 * Double Click element
	 */
	public final void doubleclick(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Actions action = new Actions(getDriver());
		action.doubleClick(element).perform();
	}
	
/**listMouseHover Dashboard**/
	
	public final void listMouseHover(String locator)throws Exception{
	
	List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.getObject(locator)));
	for(WebElement element:elements) {
	jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
	Actions action = new Actions(getDriver());
	action.moveToElement(element).perform();
		
	}
	}
	
/** MouseHover Dashboard**/
	public final void mouseHoverclick(WebElement key) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOf(key));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Actions action = new Actions(getDriver());
		action.click(element);
		action.moveToElement(element).perform();
	}
	
	/**
	 * Mouse hover on a webElement
	 *
	 * @param key - webElement
	 * @throws Exception
	 */
	public final void mouseHover(WebElement key) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOf(key));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Actions action = new Actions(getDriver());
		action.moveToElement(element).perform();
	}
	
	/**
	 * Keyboard keys
	 */
	public final void pressKey(Keys key) {
		Actions action = new Actions(getDriver());
		action.sendKeys(key).build().perform();
	}

	/**
	 * get inner Text from web element
	 * 
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public final String getInnerTextFromElement(WebElement locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		String innerhtml = (String) jsDriver().executeScript("return arguments[0].innerHTML;", locator);
		return innerhtml;
	}

	/**
	 * Wait until element is visible.This method is designed specifically for reports loading.
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public final void waitUntilElementIsVisible(String locator) throws Exception {
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(this.getObject(locator)));
		} catch (TimeoutException e) {
			LOGGER.error("Exception in Waiting for the specified locator." + e.getMessage());
		}
	}
	
	/**
	 * Wait until element is visible.This method is designed specifically for pdf and xls report download.
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public final boolean waitUntilElementIsVisibleDynamic(String locator, int waitime) throws Exception {
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitime));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(this.getObject(locator)));
		} catch (TimeoutException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * get WebElements
	 * 
	 * @return
	 */
	public final List<WebElement> getElementsTillAllElementsPresent(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		List<WebElement> element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(this.getObject(locator)));
		return element;
	}

	public final List<WebElement> getElementsTillAllElementsVisible(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		List<WebElement> element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.getObject(locator)));
		return element;
	}
	
	public final List<WebElement> getAllElements(String locator) throws Exception {
		List<WebElement> element = getDriver().findElements(this.getObject(locator));
		return element;
	}
	
	/**
	 * This method is used read unique elements from a list of strings
	 * 
	 * @param locator - list locators
	 * @return - List<String>
	 * @throws Exception
	 */
	public List<String> getUniqueElementsofStringsFromList(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(this.getObject(locator)));
		List<String> stringElements = new ArrayList<String>();

		for (int i = 0; i < elements.size(); i++) {
			stringElements.add(elements.get(i).getText());
		}

		return stringElements;
	}

	/**
	 * get count
	 * 
	 * @return
	 */
	public int getCountOfElement(String key) {
		WebElement element = getDriver().findElement(this.getObject(key));
		Dimension dimesions = element.getSize();
		return dimesions.width;
	}

	/**
	 * Clear text
	 */
	public final void clearText(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
		element.sendKeys(Keys.BACK_SPACE);
		element.click();
	}

	/**
	 * Clear text using javascript
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public final void clearTextRefresh(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
		element.sendKeys(Keys.BACK_SPACE);
		element.click();
	}

	/**
	 * Clear text using keys
	 *
	 * @param locator
	 * @throws Exception
	 */
	public final void clearTextRefreshusingKeys(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
			element.click();
		} else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
			element.sendKeys(Keys.chord(Keys.COMMAND, "a") + Keys.BACK_SPACE);
			element.click();
		}

	}

	/**
	 * select value from drop-down using value
	 */
	public final void selecValueFromDropdown(String locator, String text) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		WebElement element = getDriver().findElement(this.getObject(locator));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Select oSelect = new Select(element);
		oSelect.selectByValue(text);
	}

	/**
	 * Scroll the page
	 */
	public final void scrollTillView(String locator) {
		WebElement element = getDriver().findElement(this.getObject(locator));
		jsDriver().executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	/**
	 * Scroll the page
	 */
	public final void scrollTillView(WebElement element) {
		jsDriver().executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * Get all count of rows
	 */
	public final int getCountOfRows(String locator) throws Exception {
		return getDriver().findElements(this.getObject(locator)).size();
	}

	/**
	 * Move to element
	 */
	public final void moveToElements(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		Actions action = new Actions(getDriver());
		WebElement element = getDriver().findElement(this.getObject(locator));
		jsDriver().executeScript("arguments[0].style.border='3px solid black'", element);
		jsDriver().executeScript("arguments[0].scrollIntoView(true)", element);
		action.moveToElement(element);
	}

	/**
	 * check if the element is invisible or not
	 * 
	 * @param locator
	 * @return
	 */
	public boolean verifyElementIsinvisibile(String locator) {
		try {
			boolean status;
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			status = wait.until(ExpectedConditions.invisibilityOfElementLocated(this.getObject(locator)));
			return status;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsinvisibile");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(getDriver().findElement(this.getObject(locator)))));
		} catch (Exception e) {
			return false;
		}
	}

	public boolean verifyElementIsinvisibileDynamic(String locator,int waittime) {
		try {
			boolean status;
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waittime));
			status = wait.until(ExpectedConditions.invisibilityOfElementLocated(this.getObject(locator)));
			return status;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsinvisibile");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(getDriver().findElement(this.getObject(locator)))));
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * check if the all WebElements are invisible or not
	 * 
	 * @param locator
	 * @return
	 */
	public boolean verifyAllElementIsinvisibile(List<WebElement> elements) {
		try {
			boolean status;
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			status = wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
			return status;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsinvisibile");
//			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(elements)));
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	
	/**
	 * This method will retry 3 attempts to check if element is visible.
	 * @param locator Locator of the element to be visible.
	 * @param waittime  Hardwait in miliseconds.
	 * @return
	 */
	public final boolean retryWaitUntilVisible(String locator,int waittime) {
		try {
			 boolean flag = false;
			 int counter = 3;
			 while(counter != 0) {
				 if(verifyElementIsVisibleDynamic(locator, waittime))
						 {
					 flag = true;
					 break;
				 }
				 counter-= 1;
			 }
			return flag;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsinvisibile");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(getDriver().findElement(this.getObject(locator)))));
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * This method will retry 3 attempts to check if element is invisible.
	 * @param locator Locator of the element to be visible.
	 * @param waittime  Hardwait in miliseconds.
	 * @return
	 */
	public final boolean retryWaitUntilInVisible(String locator,int waittime) {
		try {
			 boolean flag = false;
			 int counter = 3;
			 while(counter != 0) {
				 if(verifyElementIsinvisibileDynamic(locator, waittime))
						 {
					 flag = true;
					 break;
				 }
				 counter-= 1;
			 }
			return flag;
		} catch (StaleElementReferenceException e) {
			LOGGER.error("inside stale element exception verifyElementIsinvisibile");
			return wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(getDriver().findElement(this.getObject(locator)))));
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	/**
	 * Get object by locator from properties file
	 * 
	 * @param locator Locator from properties file
	 * @return By
	 */
	public By getObject(String locator) {
		try {
			String locatorType = getLocatorType(locator);
			String locatorVal = getLocatorValue(locator);

			switch (locatorType.toUpperCase()) {
			// Find by id
			case "ID":
				return By.id(locatorVal);
			// Find by xpath
			case "XPATH":
				return By.xpath(locatorVal);
			// Find by Classname
			case "CLASSNAME":
				return By.className(locatorVal);

			// Find by Name
			case "NAME":
				return By.name(locatorVal);

			// Find by CSS
			case "CSS":
				return By.cssSelector(locatorVal);

			// Find by Link
			case "LINK":
				return By.linkText(locatorVal);

			// Find by PartialLink
			case "PARTIALLINK":
				return By.partialLinkText(locatorVal);

			default:
				LOGGER.error("Provided : " + locatorType + " type is wrong, You can use : ID, XPATH, CLASSNAME, NAME, CSS, LINK, PARTIALLINK");
				throw new InputMismatchException("You can use : ID, XPATH, CLASSNAME, NAME, CSS, LINK, PARTIALLINK only ");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getting locator as type is wrong "+ locator +" " + e.getMessage());
			return null;
		}
	}

	/**
	 * Get locator value from properties file.
	 * 
	 * @param locator Locator value from the file
	 * @return Locator value only (not locator type)
	 */
	private String getLocatorValue(String locator) {
		String value = null;
		try {
			int pos = locator.indexOf(":");
			if (pos == -1) {
				LOGGER.error("Error in locator format "+ locator +" Please check properties file for object type");
			} else {
				value = locator.substring(pos + 1);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception in getting locator value is wrong." + ex.getMessage());
			return null;
		}
		return value;
	}

	/**
	 * Function to get locator type from locator from properties file
	 * 
	 * @param locator Locator value
	 * @return locator type
	 */
	private String getLocatorType(String locator) {
		String locatorType = null;
		try {
			if (!Strings.isNullOrEmpty(locator)) {
				String locatorValue = locator.split(":")[0];
				if (!Strings.isNullOrEmpty(locatorValue)) {
					locatorType = locatorValue.substring(locatorValue.indexOf("[") + 1, locatorValue.indexOf("]"));
				} else {
					LOGGER.error("Locator is not in correct format in properties file. Please check the file.");
				}
			} else {
				LOGGER.error("Locator is missing from properties file. Please check the property file for the locator");
			}
		} catch (Exception ex) {
			LOGGER.error("Exception in getting locator type "+ locator +" from properties file." + ex.getMessage());
		}
		return locatorType;
	}

	/**
	 * 
	 * Common methods: sleep, navigate to back, refresh, get url, move to
	 */
	public final static void sleeper(final int sleepTime) throws InterruptedException {
		Thread.sleep(sleepTime);
	}
	
	public final static void sleeper(final long sleepTime) throws InterruptedException {
		Thread.sleep(sleepTime);
	}
	
	

	public final void navigateToBack() {
		getDriver().navigate().back();
	}

	public final void refreshPage() {
		getDriver().navigate().refresh();
	}

	public String getUrlOfCurrentPage() {
		return getDriver().getCurrentUrl();
	}

	// This method will allow user to switch to different tab
	public void switchToDifferentTab() {
		parentWindowHandler = getDriver().getWindowHandle();
		try {
			for (String handle : getDriver().getWindowHandles()) {
				if (!parentWindowHandler.equals(handle)) {	
					if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
					try {
						getDriver().switchTo().window(handle);
						sleeper(2000);
					} catch (InterruptedException e) {
						LOGGER.error("Exception in Switching Window for WINDOWS" + e.getMessage());
					}
				} else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
					try {
						getDriver().switchTo().window(handle);
						sleeper(2000);
					} catch (InterruptedException e) {
						LOGGER.error("Exception in Switching Window for MAC" + e.getMessage());
					}
				}
				// checking for OS name as LINUX and switching to different tab 
				  else if (!Strings.isNullOrEmpty(osName) && osName.toUpperCase().contains("LINUX")) {
					try {
						getDriver().switchTo().window(handle);
						sleeper(2000);
					} catch (InterruptedException e) {
						LOGGER.error("Exception in Switching Window for LINUX" + e.getMessage());
					}
				}
				}
			}
		} catch (Exception e) {
			LOGGER.error(("Exception in getting OS name in switchToDifferentTab() method. " + e.getMessage()));
		}
	}

	public void switchBackToPreviousTab() {
		getDriver().close();
		getDriver().switchTo().window(parentWindowHandler);
	}

	public final void getUrl(String url) {
		getDriver().get(url);
	}

	public final void switchToIframe(String locator) throws Exception {
		getDriver().switchTo().frame(this.getElement(locator));
	}

	/**
	 * this method Switch back to the default content (main page)
	 */
	public final void switchToDefaultContent() {
		getDriver().switchTo().defaultContent();
	}

	/**
	 * this method helpfull to scroll top side
	 */
	public final void scrollToTop() {
		jsDriver().executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	/**
	 * this method helpful to scroll Bottom side
	 */
	public final void scrollToBottom() {
		jsDriver().executeScript("window.scrollTo(document.body.scrollHeight,document.body.scrollHeight)");
	}

	// Method for switch to a Iframe
	public final void switchToIframeById(String locator) throws Exception {
		getDriver().switchTo().frame(getLocatorValue(locator));
	}

	/**
	 * This method is used to switch back to parent window without closing current tab
	 */
	public void switchBackToParentWithoutCloseTab() {
		getDriver().switchTo().window(parentWindowHandler);
	}

	/**
	 * This method is used to open new tab in same browser
	 * 
	 * @return
	 * @throws Exception
	 */
	public String createAndSwitchToNewTab() throws Exception {
		((JavascriptExecutor) getDriver()).executeScript("window.open('about:blank','_blank');");
		switchToDifferentTab();
		return getDriver().getWindowHandle();
	}

	/** This method is used to fetch desired cookies name.
	 * @param key
	 * @return
	 */
	public Cookie getCookiesName(String key){
		return getDriver().manage().getCookieNamed(key);
	}
	
	public boolean isItemPresentInLocalStorage(String item) {
		try{
	    return !(jsDriver().executeScript(String.format(
	        "return window.localStorage.getItem('%s');", item)) == null);
		}catch(Exception e){
			return false;
		}
	  }
	
	/** This method is used to delete all cookies.
	 * 
	 */
	public void deleteAllcookies(){
		getDriver().manage().deleteAllCookies();
	}
	
	
	
	/** This method returns items from local storage.
	 * @param key: name of parameter which needs to be passed.
	 * @return
	 */
	public String getItemFromLocalStorage(String key) {
	    return (String) jsDriver().executeScript(String.format("return window.localStorage.getItem('%s');", key));
	  }

	/**
	 * This method is for getting the count of WindowHandles.
	 */
	public final int getCountofWindowHandles() {
		int handlesCount = 0;
		try {
			Set<String> handles = getDriver().getWindowHandles();
			handlesCount = handles.size();
		} catch (TimeoutException e) {
			LOGGER.error("Exception in Waiting for the Window handles." + e.getMessage());
		}
		return handlesCount;
	}
	
	/**
	 * Find if the element is visible
	 * 
	 * @param stepperElement web element of stepper elements
	 * @return flag element is visible
	 */
	public boolean verifyElementIsVisible(WebElement stepperElement) {
	
		try {
			boolean flag = true;
			if (wait.until(ExpectedConditions.visibilityOf(stepperElement)) == null) {
				flag = false;
			}
			//js.executeScript("arguments[0].style.border='3px solid red'", stepperElement);
			return flag;
		} catch (Exception e) {
			return false;
		}	
	}
	/**
     * Mouse Hover by offsetClick
     * @param locator:location from start mouse hover
     * @param X:Left side dimension from locator
     * @param Y:Right side dimension from locator
     * @throws Exception
     */
    public final void mouseHoverbyoffsetClick(String locator,int x,int y) throws Exception {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
        jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
        Actions action = new Actions(getDriver());
        action.moveToElement(element, x, y).click().perform();
    }
    /** 
     * Mouse Hover by offset from one point to the left side
     * @param locator:location from start mouse hover
     * @param X:Left side dimension from locator
     * @param Y:Right side dimension from locator
     * @throws Exception
     */
    public final void mouseHoverbyoffset(String locator,int x,int y) throws Exception {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
        jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
        Actions action = new Actions(getDriver());
        action.moveToElement(element, x, y).build().perform();
    }
    
	/**
	 * Scroll to Coordinates of Page
	 * @param xPixels : x- Coordinates of page
	 * @param yPixels : y- Coordinates of page
	 */
    
    public void scrollByCoordinatesofAPage(int xPixels,int yPixels) 
    {
    	jsDriver().executeScript("window.scrollBy("+xPixels+","+yPixels+")");
	}
    
	/**
	 * Scroll up in a vertical direction of the page
	 * 
	 */
	public void scrollUpInVertical() {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	/**
	 * This method is wait till report download
	 */
	public void waitForAnalyticsPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(7000);  // Some reports take more then 5 sec to get download in pdf and xls.
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}
	
	// This method will allow user to switch to different parent closing all other tabs
		public void switchToParentTab() {
			parentWindowHandler = getDriver().getWindowHandle();
			try {
				for (String handle : getDriver().getWindowHandles()) {
					if (!parentWindowHandler.equals(handle)) {	
						if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
							getDriver().close();
							getDriver().switchTo().window(handle);
						} 
					}
				}
			} catch (Exception e) {
				LOGGER.error(("Exception in getting OS name in switchToParentTab() method. " + e.getMessage()));
			}
		}
		
		/**
		 * This method will get the current page URL and extract room id from there
		 * 
		 * @param NA
		 * @return String
		 * @throws Exception
		 */
		public final String getRoomID() {
			String roomId= getDriver().getCurrentUrl().split("rooms/")[1];
			LOGGER.info("RoomId : "+roomId.split("/")[0]);
			return roomId.split("/")[0];
		}
		
		public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password,
				MessageDigest md) {

			int digestLength = md.getDigestLength();
			int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
			byte[] generatedData = new byte[requiredLength];
			int generatedLength = 0;

			try {
				md.reset();

				// Repeat process until sufficient data has been generated
				while (generatedLength < keyLength + ivLength) {

					// Digest data (last digest if available, password data, salt if available)
					if (generatedLength > 0)
						md.update(generatedData, generatedLength - digestLength, digestLength);
					md.update(password);
					if (salt != null)
						md.update(salt, 0, 8);
					md.digest(generatedData, generatedLength, digestLength);

					// additional rounds
					for (int i = 1; i < iterations; i++) {
						md.update(generatedData, generatedLength, digestLength);
						md.digest(generatedData, generatedLength, digestLength);
					}

					generatedLength += digestLength;
				}

				// Copy key and IV into separate byte arrays
				byte[][] result = new byte[2][];
				result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
				if (ivLength > 0)
					result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

				return result;

			} catch (Exception e) {
				LOGGER.error("Exception while genarating key" + e.getMessage());
				throw new RuntimeException(e);

			} finally {
				// Clean out temporary data
				Arrays.fill(generatedData, (byte) 0);
			}
		}
		
		/**
		 * This method returns dui_token from local storage.
		 * 
		 * @param key: name of parameter which needs to be passed.
		 * @return
		 */
		public String getTokenFromLocalStorage(String keys) {
			try {
				String secret = "5XBoZ7li2W5wzhOULEqtQzdkufjsVFs4";
				String cipherText = (String) jsDriver()
						.executeScript(String.format("return window.localStorage.getItem('%s');", keys));

				byte[] cipherData = Base64.decodeBase64(cipherText);
				byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

				MessageDigest md5 = MessageDigest.getInstance("MD5");
				final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8),
						md5);
				SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
				IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

				byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
				Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
				aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
				byte[] decryptedData = aesCBC.doFinal(encrypted);
				String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

				return decryptedText;
			} catch (Exception e) {
				LOGGER.error("Exception in getting token value" + e.getMessage());
				return null;
			}

		}


	public boolean isAttributePresent(String locator, String attribute) {
		try {
			String value = getAttribute(locator,attribute);
			if (value == null){
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method is used to calculate the offset needed from the x and y
	 * coordinate it is at for the webelement for the widget
	 * then click on an interactable widget to navigate to the respective page.
	 *
	 * @param svgElement - webelement of the widget
	 * @param xOffset - offset from center of element to click in x coord
	 * @param yOffset - offset from center of element to click in y coord
	 */
	public void calculateCoordinatesAndClickOnWidget(WebElement svgElement, int xOffset,int yOffset) {
		var driver = PreDefinedActions.getDriver();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Map<String, Number> boundingBox = (Map<String, Number>) jse.executeScript(
				"const ele= arguments[0];" +
						"const bbox= ele.getBoundingClientRect();" +
						"return {x: bbox.x, y:bbox.y, width:bbox.width, height:bbox.height};", svgElement);
		double targetX = boundingBox.get("width").doubleValue()/2;
		double targetY = boundingBox.get("height").doubleValue()/2;
		Actions actions = new Actions(driver);
		actions.moveToElement(svgElement,(int) targetX + xOffset, (int) targetY + yOffset).click().perform();
	}

	/**
	 * Method to trigger On change event in calendar using javascript
	 *
	 * @param locator of the webelement
	 * @throws Exception
	 */
	public final void onChangeCalendarEvent(String locator) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		jsDriver().executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
		jsDriver().executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element);
	}
	
	/**
	 * Method is wait until element is enabled
	 *
	 * @param locator of the web element
	 * @throws Exception
	 */
	public boolean waitUntilElementIsEnabled(String locator) throws Exception {
		try {
			By by = this.getObject(locator);
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
			long start = System.currentTimeMillis();
			WebElement element = wait.until(driver -> {
				WebElement el = driver.findElement(by);
				return (el != null && el.isEnabled()) ? el : null;
			});
			long end = System.currentTimeMillis();
			LOGGER.info("Waited for " + (end - start) + " ms for element to be enabled.");
			if (element != null) {
				jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
				return true;
			}
		} catch (TimeoutException e) {
			LOGGER.error("Timeout: Element was not enabled within the specified time.");
		} catch (Exception e) {
			LOGGER.error("Exception in waitUntilElementIsEnabled: " + e.getMessage());
		}
		return false;
	}
	
	public static String getRoundedTime() {
        Calendar cal = Calendar.getInstance(); 
        int minutes = cal.get(Calendar.MINUTE); 
        int remainder = minutes % 15; 
        if (remainder != 0) {
            cal.add(Calendar.MINUTE, 15 - remainder); 
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(cal.getTime());
    }
	
	/*
	 * This method will fetch the current time and select the time in dropdown
	 */ 	
	public void selectRoundedUpCurrentTimeFromDropdown() throws Exception {
	    WEPPolicyPage policyPage = WEPPolicyPage.getInstance(getDriver());
	    ZoneId systemZone = ZoneId.systemDefault();
	    LocalTime currentTime = LocalTime.now(systemZone);
	    LOGGER.info("System time zone: {}", systemZone);
	    LOGGER.info("Current local time: {}", currentTime);
	    LocalTime adjustedTime;
	    if (systemZone.getId().equalsIgnoreCase("Asia/Kolkata")) {
	        adjustedTime = currentTime.minusHours(6);
	        LOGGER.info("Detected IST. Subtracting 5 hours.");
	    } else if (systemZone.getId().equalsIgnoreCase("America/Chicago")) {
	        adjustedTime = currentTime.plusHours(6);
	        LOGGER.info("Detected Houston (CST/CDT). Adding 5 hours.");
	    } else {
	        adjustedTime = currentTime;
	        LOGGER.warn("Unknown timezone ({}). No time adjustment applied.", systemZone);
	    }
	    int minute = adjustedTime.getMinute();
	    int roundedMinutes = ((minute + 14) / 15) * 15; 
	    if (roundedMinutes == 60) {
	        adjustedTime = adjustedTime.plusHours(1).withMinute(0);
	    } else {
	        adjustedTime = adjustedTime.withMinute(roundedMinutes);
	    }
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
	    String expectedRoundedTime = adjustedTime.format(formatter).toUpperCase().trim();
	    LOGGER.info("Adjusted and rounded time to select: {}", expectedRoundedTime);
	    policyPage.actionClickOfPolicyPage("starttimedropdown");
	    WebDriverWait dropdownWait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
	    List<WebElement> timeOptions = dropdownWait.until(
	        ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[@role='option']"))
	    );
	    for (WebElement optionElement : timeOptions) {
	        String optionValue = optionElement.getAttribute("data-value");
	        if (optionValue == null || optionValue.isBlank()) {
	            optionValue = optionElement.getText();
	        }
	        String normalizedOptionValue = optionValue.toUpperCase().trim();
	        if (normalizedOptionValue.equals(expectedRoundedTime)) {
	            ((JavascriptExecutor) getDriver())
	                    .executeScript("arguments[0].scrollIntoView(true);", optionElement);
	            dropdownWait.until(ExpectedConditions.elementToBeClickable(optionElement));
	            optionElement.click();
	            LOGGER.info("Selected time option: {}", normalizedOptionValue);
	            return;
	        }
	    }

	    throw new NoSuchElementException("Could not find time option: " + expectedRoundedTime);
	}
	
	public static String getNextDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDate = currentDate.plusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        String formattedNextDate = nextDate.format(formatter);

        System.out.println("Current Date: " + currentDate.format(formatter));
        System.out.println("Next Date (+1 day): " + formattedNextDate);

        return formattedNextDate;
    }
	
	/*
	 * This method will get the current date and add plus one to the existing and select
	 */
	public void selectNextDateFromCalendar() throws Exception {
	    WEPPolicyPage policyPage = WEPPolicyPage.getInstance(getDriver());
	    String nextDate = getNextDate();
	    LOGGER.info("Next Date to select: " + nextDate);
	    policyPage.actionClickOfPolicyPage("StartDateIcon");
	    String day = nextDate.split("-")[0];
	    LOGGER.info("Selecting day: " + day);
	    String[] possibleXPaths = {
	        String.format("(//td[contains(@class,'CalendarDay__today')]/following-sibling::td[normalize-space()])[1]", day),
	    };
	    WebElement dateElement = null;
	    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
	    for (String xpath : possibleXPaths) {
	        try {
	            dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	            LOGGER.info("Date found with XPath: " + xpath);
	            break;
	        } catch (Exception ignored) {
	        }
	    }
	    if (dateElement == null) {
	        throw new RuntimeException("Date element not found for: " + day);
	    }

	    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", dateElement);
	    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dateElement);

	    LOGGER.info("Successfully selected next date: " + nextDate);
	}
	
	/**
	 * Verifies that multiple texts are present in the specified element on the page.
	 * @param locator The locator for the WebElement to search for.
	 * @param texts The list of texts to verify are present in the WebElement.
	 * @return boolean True if all texts are present in the element, false otherwise.
	 */
	public boolean verifyTextsPresentOnElement(String locator, List<String> texts) {
	    WebElement element = null;
	    try {
	        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
	        element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
	        jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
	        for (String text : texts) {
	            if (!wait.until(ExpectedConditions.textToBePresentInElementLocated(this.getObject(locator), text))) {
	                LOGGER.error("Text not found: " + text);
	                return false;  
	            }
	        }
	        return true;  
	    } catch (WebDriverException e) {
	        LOGGER.error("WebDriverException caught: " + e.getMessage());
	        return false;
	    } catch (Exception e) {
	        LOGGER.error("General Exception: " + e.getMessage());
	        return false;
	    }
	}
	
	/**
	 * This method will clear the text using keys in workflow
	 *
	 * @param locator
	 * @throws Exception
	 */
	public final void clearTextofWorkflow(String locator) throws Exception {

	    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
	    WebElement element = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
	    jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
	    Actions actions = new Actions(getDriver());
	    actions.click(element)
	           .keyDown(Keys.CONTROL)
	           .sendKeys("a")
	           .keyUp(Keys.CONTROL)
	           .sendKeys(Keys.DELETE)
	           .perform();
	}
	
	/**
     * Drag the source element to destination element using JavaScript and Robot class
     * This method  Drag the source element to destination element using JavaScript and Robot class
     * @param Drag and Drop source element locator key and destination element locator key
     */	
	/**
     * Drag the source element to destination element using JavaScript and Robot class
     * This method  Drag the source element to destination element using JavaScript and Robot class
     * @param Drag and Drop source element locator key and destination element locator key
     */	
	public void dragAndDrop(String sourceKey, String destKey) throws Exception {

	    WebDriver driver = PreDefinedActions.getDriver();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    try {
	        List<WebElement> sourceList = getAllElements(sourceKey);
	        if (sourceList == null || sourceList.isEmpty()) {
	            throw new RuntimeException("Source element not found for key: " + sourceKey);
	        }
	        boolean isDestVisible = false;
	        List<WebElement> destList = getAllElements(destKey);
	        if (destList != null && !destList.isEmpty()) {
	            isDestVisible = destList.get(0).isDisplayed();
	        }
	        if (isDestVisible) {
	            LOGGER.info("[DnD] Destination element is visible. Using standard Actions drag and drop.");
	            WebElement source = wait.until(ExpectedConditions.elementToBeClickable(sourceList.get(0)));
	            WebElement destination = wait.until(ExpectedConditions.visibilityOf(destList.get(0)));
	            Actions actions = new Actions(driver);
	            actions.moveToElement(source)
	                   .clickAndHold()
	                   .pause(Duration.ofMillis(500))
	                   .moveToElement(destination)
	                   .pause(Duration.ofMillis(500))
	                   .release()
	                   .build()
	                   .perform();
	            LOGGER.info("[DnD] Standard drag and drop completed from '{}' to '{}'", sourceKey, destKey);
	        } else {
	            LOGGER.info("[DnD] Destination element is not visible. Using Robot-based drag and drop with polling.");
	            WebElement source = wait.until(ExpectedConditions.elementToBeClickable(sourceList.get(0)));
	            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", source);
	            Thread.sleep(500);
	            WebElement canvas = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[@class='react-flow__pane draggable']")));
	            long pageXOffset = ((Number) js.executeScript(
	                "return window.pageXOffset || document.documentElement.scrollLeft;")).longValue();
	            long pageYOffset = ((Number) js.executeScript(
	                "return window.pageYOffset || document.documentElement.scrollTop;")).longValue();
	            long browserHeaderHeight = ((Number) js.executeScript(
	                "return window.outerHeight - window.innerHeight;")).longValue();
	            Point srcLoc = source.getLocation();
	            Dimension srcSize = source.getSize();
	            final int absoluteSrcX = (int)(srcLoc.getX() + srcSize.getWidth() / 2 - pageXOffset);
	            final int absoluteSrcY = (int)(srcLoc.getY() + srcSize.getHeight() / 2 - pageYOffset + browserHeaderHeight);
	            Point canvasLoc = canvas.getLocation();
	            Dimension canvasSize = canvas.getSize();
	            final int absoluteCanvasX = (int)(canvasLoc.getX() + canvasSize.getWidth() / 2 - pageXOffset);
	            final int absoluteCanvasY = (int)(canvasLoc.getY() + canvasSize.getHeight() / 2 - pageYOffset + browserHeaderHeight);
	            LOGGER.info("[DnD] Source -> X:{} Y:{}", absoluteSrcX, absoluteSrcY);
	            LOGGER.info("[DnD] Canvas -> X:{} Y:{}", absoluteCanvasX, absoluteCanvasY);
	            Robot robot = new Robot();
	            robot.setAutoDelay(0);
	            robot.mouseMove(absoluteSrcX, absoluteSrcY);
	            Thread.sleep(400);
	            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	            Thread.sleep(600);
	            for (int i = 0; i < 5; i++) { robot.mouseMove(absoluteSrcX + i, absoluteSrcY); Thread.sleep(80); }
	            for (int i = 5; i >= 0; i--) { robot.mouseMove(absoluteSrcX + i, absoluteSrcY); Thread.sleep(80); }
	            Thread.sleep(200);
	            int steps = 60;
	            for (int i = 1; i <= steps; i++) {
	                robot.mouseMove(
	                    absoluteSrcX + (absoluteCanvasX - absoluteSrcX) * i / steps,
	                    absoluteSrcY + (absoluteCanvasY - absoluteSrcY) * i / steps
	                );
	                Thread.sleep(25);
	            }
	            LOGGER.info("[DnD] Hovering on canvas, waiting for destination to appear...");
	            final int[] absoluteDestCoords = new int[2];
	            boolean destFound = false;
	            long timeoutMs = 15000;
	            long pollIntervalMs = 500;
	            long elapsed = 0;
	            while (elapsed < timeoutMs) {
	                robot.mouseMove(
	                    absoluteCanvasX + (int)(Math.random() * 4 - 2),
	                    absoluteCanvasY + (int)(Math.random() * 4 - 2)
	                );
	                Thread.sleep(pollIntervalMs);
	                elapsed += pollIntervalMs;
	                List<WebElement> polledDestList = getAllElements(destKey);
	                if (polledDestList != null && !polledDestList.isEmpty()) {
	                    WebElement dest = polledDestList.get(0);
	                    if (dest.isDisplayed()) {
	                        long newPageX = ((Number) js.executeScript(
	                            "return window.pageXOffset || document.documentElement.scrollLeft;")).longValue();
	                        long newPageY = ((Number) js.executeScript(
	                            "return window.pageYOffset || document.documentElement.scrollTop;")).longValue();
	                        Point dLoc = dest.getLocation();
	                        Dimension dSize = dest.getSize();
	                        absoluteDestCoords[0] = (int)(dLoc.getX() + dSize.getWidth() / 2 - newPageX);
	                        absoluteDestCoords[1] = (int)(dLoc.getY() + dSize.getHeight() / 2 - newPageY + browserHeaderHeight);

	                        LOGGER.info("[DnD] Dest found -> X:{} Y:{}", absoluteDestCoords[0], absoluteDestCoords[1]);
	                        destFound = true;
	                        break;
	                    }
	                }
	            }
	            if (!destFound) {
	                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	                throw new RuntimeException("Destination element did not appear after hovering on canvas for key: " + destKey);
	            }
	            int finalSteps = 30;
	            for (int i = 1; i <= finalSteps; i++) {
	                robot.mouseMove(
	                    absoluteCanvasX + (absoluteDestCoords[0] - absoluteCanvasX) * i / finalSteps,
	                    absoluteCanvasY + (absoluteDestCoords[1] - absoluteCanvasY) * i / finalSteps
	                );
	                Thread.sleep(40);
	            }
	            Thread.sleep(400);
	            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	            Thread.sleep(700);
	        }
	    } catch (AWTException e) {
	        LOGGER.error("[DnD] Robot initialization failed: {}", e.getMessage());
	        throw new RuntimeException("Robot could not be initialized for drag and drop: " + e.getMessage(), e);
	    } catch (TimeoutException e) {
	        LOGGER.error("[DnD] Element not found within timeout from '{}' to '{}': {}", sourceKey, destKey, e.getMessage());
	        throw new RuntimeException("Timeout during drag and drop from '" + sourceKey + "' to '" + destKey + "': " + e.getMessage(), e);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        LOGGER.error("[DnD] Thread interrupted during drag and drop: {}", e.getMessage());
	        throw new RuntimeException("Drag and drop interrupted: " + e.getMessage(), e);
	    } catch (RuntimeException e) {
	        LOGGER.error("[DnD] Drag and drop failed from '{}' to '{}': {}", sourceKey, destKey, e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        LOGGER.error("[DnD] Unexpected error during drag and drop from '{}' to '{}': {}", sourceKey, destKey, e.getMessage());
	        throw new RuntimeException("Drag and drop failed from '" + sourceKey + "' to '" + destKey + "': " + e.getMessage(), e);
	    }
	}
	
}