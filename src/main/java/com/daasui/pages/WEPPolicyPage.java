package com.daasui.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import java.time.Duration;
import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.basesource.utils.CSVFileReader;
import com.opencsv.CSVReader;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class WEPPolicyPage extends CommonMethod {

    private static WEPPolicyPage instance;
    private static final Logger LOGGER = LogManager.getLogger(WEPPolicyPage.class);
    
    private final ObjectReader policyPagePropertiesReader = new ObjectReader();
    private final Properties policyPageProperties;
    
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");

    // Singleton Pattern for WEPPolicyPage
    public static synchronized WEPPolicyPage getInstance(WebDriver driver) throws IOException {
        if (instance == null) {
            instance = new WEPPolicyPage(driver);
        }
        return instance;
    }

    // Private constructor for Singleton
    private WEPPolicyPage(WebDriver driver) throws IOException {
        policyPageProperties = policyPagePropertiesReader.getObjectRepository("WEPPolicyPage");
    }

    /**
     * Verifies if an element is present on the Policy Page.
     * 
     * @param key The property key of the element
     * @return true if element is present, false otherwise
     */
    public final boolean verifyElementIsPresentOnPolicyPage(String key) throws Exception {
		return verifyElementIsPresent(policyPageProperties.getProperty(key));
	}
    
    /**
	 * This method designed to get the  Text of WebElement from web page
	 * @param WebElement 
	 */
	public final String getTextOfWEPPolicyPage(String key) throws Exception {
		return getTextBy(policyPageProperties.getProperty(key));
	}

    /**
     * Verifies if two lists of elements match.
     * 
     * @param originalList Expected list of elements
     * @param actualList Actual list of elements
     */
	public void verifyListOfElemetnsOnPolicyPage(ArrayList<String> originalpropertyListOptions,ArrayList<String> propertyListOptions) throws Exception {
		// Compare the two ArrayLists
		if (originalpropertyListOptions.equals(propertyListOptions)) {
			System.out.println("The String array and ArrayList are equal.");
		} else {
			System.out.println("The String array and ArrayList are NOT equal.");
		}
	}
	
	public final boolean waitElementsOfPolicyPage(String key) throws Exception {
		return verifyElementIsVisible(policyPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfPolicyPage(String key) throws Exception {
		click(policyPageProperties.getProperty(key));
	}
    /**
     * Enters text into a field on the Policy Page.
     * 
     * @param key The property key of the element
     * @param text Text to enter
     */
    public void enterTextForPolicyPage(String key, String text) {
        try {
            enterText(policyPageProperties.getProperty(key), text);
            LOGGER.info("Entered text '{}' into field: {}", text, key);
        } catch (Exception e) {
            LOGGER.error("Error entering text in field {}: {}", key, e.getMessage());
        }
    }

    /**
     * Waits until an element is visible on the Policy Page.
     * 
     * @param key The property key of the element
     * @return true if element is visible, false otherwise
     */
    public boolean waitForElementsOfPolicyPage(String key) {
        try {
            return verifyElementIsVisible(policyPageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error("Error waiting for element visibility {}: {}", key, e.getMessage());
            return false;
        }
    }
    
    /**
	 * This method is to get the attribute value for the provided tag
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public final String getAttributesOfWEPPolicyPage(String key, String value) throws Exception {
		return getAttribute(policyPageProperties.getProperty(key), value);
	}

    
	public void gotoPolicyTab(String languageCode) throws Exception {
	    clickOnElementsOfPolicyPage("groupsMenu");
	    LOGGER.info("Groups are Showing in sidemenu bar");
	    waitForPageLoaded();
	    String actualHeader = getTextOfWEPPolicyPage("groupsHeader");
	    String expectedHeader = getTextLanguage(languageCode, "daas_ui", "device.groups.sidebar.title");
	    if (actualHeader.equals(expectedHeader)) {
	        LOGGER.info("Groups Header is Matched");
	    } else {
	        LOGGER.warn("Groups Header doesn't match. Expected: " + expectedHeader + ", but found: " + actualHeader);
	    }
	}
    
    public final void mouseHoverOfPolicyPage(String key) throws Exception {
		mouseHover(policyPageProperties.getProperty(key));
	}
    public final boolean waitUntilElementIsInvisibleOfPolicyPage(String key){
		return verifyElementIsinvisibile(policyPageProperties.getProperty(key));
	}
    
    public void clickOnEntraIDNextBtn() throws Exception {
		sleeper(3000);
		waitForElementsOfPolicyPage("addGrpNextBtnEntraID");
		actionClickOfPolicyPage("addGrpNextBtnEntraID");
	}

    /**
     * Performs an action click on an element on the Policy Page.
     * 
     * @param key The property key of the element
     */
    public final void actionClickOfPolicyPage(String key) throws Exception {
		actionClick(policyPageProperties.getProperty(key));
	}
    /**
	 * This is a method to scroll on roomListPage page

	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnPolicyListPage(String key) {
		try {
			scrollTillView(policyPageProperties.getProperty(key));
		} catch (Exception e) {

			LOGGER.error(("Exception occured in method scrollOnDeviceListPage " + e.getMessage()));
	}
	}
	
	/**
	 * This is a method to verify if the element is enabled
	 *
	 * @param key - Locator of element
	 * @return - - boolean value of whether the element is enabled or not
	 */
	public final boolean verifywaituntilelementisenabled(String key) {
		try {
			return waitUntilElementIsEnabled(policyPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyelement is enabled " + e.getMessage()));
			return false;
		}

	}
		
	public final List<WebElement> getElementsOfPolicyPage(String key) throws Exception {
        return getAllElements(policyPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnPolicyPage(String key) throws Exception {
        clickByJavaScript(policyPageProperties.getProperty(key));
    }

    public final WebElement getElementOfPolicyPage(String key) throws Exception {
        return getElement(policyPageProperties.getProperty(key));
    }

    public void scrollOnPolicyPage(String key) throws Exception {
        scrollTillView(policyPageProperties.getProperty(key));
    }
	
	/**
	 * This is a method to verify if the element is selected
	 * @param key - Locator of element
	 * @return - - boolean value of whether the element is selected or not
	 */
	public final boolean verifyElementsIsSelectedOfPolicyPage(String key) {
	try {
		return verifyElementIsSelected(policyPageProperties.getProperty(key));
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method verifyElementsOfGroupsPageProperties " + e.getMessage()));
		return false;
	}
  }
  
    /**
	 * This is a method to verify if the element is enabled
	 * @param key - Locator of element
	 * @return - - boolean value of whether the element is enabled or not
	 */
	public final boolean verifyElementsIsEnabledOfPolicyPage(String key) {
	try {
		return verifyElementIsEnaledOrDisabled(policyPageProperties.getProperty(key));
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method verifyelement is enabled " + e.getMessage()));
		return false;
	}
	
	}
	public final boolean waitForElementsOfPolicypageDynamic(String key, int waitTime) throws Exception {
		return waitUntillElementIsPresentDynamic(policyPageProperties.getProperty(key), waitTime);
	}
	
	
	public void uploadFile(String filePath) throws AWTException {
	    StringSelection selection = new StringSelection(filePath);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
	    Robot robot = new Robot();
	    robot.setAutoDelay(500);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 * This method is used to verify the table columns
	 * 
	 * @param expectedColumnList
	 * @return
	 * @throws Exception
	 */
	public boolean verifyTableColumns(List<String> expectedColumnList, String key) throws Exception {
		boolean flag = false;
		int counter = 0;
		try {
			List<WebElement> actualColumnList = getElementsOfPolicyPage(key);
			for (WebElement we : actualColumnList)

				if (we.getText().contains("sorted")) {
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
				} else if (we.getText().equalsIgnoreCase(expectedColumnList.get(counter))
						|| we.getText().toUpperCase().contains(expectedColumnList.get(counter).toUpperCase())) {
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
	
		public static boolean isFileExists(String filePath) {
	        File file = new File(filePath);
	        return file.exists();
	    }
	
	public boolean waitUntilToastContainsText(String toastLocator, String expectedText, int timeoutInSeconds) {
	    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
	    try {
	        return wait.until(driver -> {
	            try {
	                WebElement toast = driver.findElement(this.getObject(toastLocator));
	                return toast.getText().contains(expectedText);
	            } catch (NoSuchElementException | StaleElementReferenceException e) {
	                return false;
	            }
	        });
	    } catch (TimeoutException e) {
	        return false;
	    }
	}

    
	/*
	 * This method fetches all the records from the csv file and converts it to 2D array
	 */
	public final String[][] getDataWithHeader(File csvFile) throws IOException {
        CSVReader csvReader = null;

        try {
            if (!csvFile.exists()) {
                System.err.println("File does not exist: " + csvFile.getAbsolutePath());
                return null;
            }

            csvReader = new CSVReader(new FileReader(csvFile));
            List<String[]> list = csvReader.readAll();

            String[][] dataArr = new String[list.size()][];
            dataArr = list.toArray(dataArr);
            return dataArr;

        } catch (Exception e) {
            System.err.println("Exception occurred in getDataWithHeader(): " + e.getMessage());
            return null;
        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
        }
    }
	
	/*
	 * This method fetches all the records from the csv file and converts it to 2D array
	 */
	public final WebElement getElementOLDKfPolicyPage(String key) throws Exception {
		  return getElement(policyPageProperties.getProperty(key));
	}
	
	
	public final boolean verifyPolicyDetailNavigation(String languageCode) throws Exception {
    // Verify the serial number search box is present
    if (!verifyElementIsPresentOnPolicyPage("policyNameColumn")) {
        LOGGER.error("Policy number search box not found.");
        return false;
    }

    // Wait for serial number column values to be visible
       if (waitForElementsOfPolicyPage("policyNumberColumnValues")) {
        List<WebElement> serialNumberElements = getElementsOfPolicyPage("policyNumberColumnValues");
        if (serialNumberElements != null && !serialNumberElements.isEmpty()) {
            serialNumberElements.get(0).click();
            LOGGER.info("Clicked on the first policy number element.");
            return true;
            } else {
            LOGGER.error("No policy number elements found.");
         }
      } else {
        LOGGER.error("policy number column values not visible.");
      }
      return false;
  }

	public final boolean verifySecretDetailNavigation(String languageCode) throws Exception {
    // Verify the serial number search box is present
    if (!verifyElementIsPresentOnPolicyPage("SecretNameColumn")) {
        LOGGER.error("Secret number search box not found.");
        return false;
    }

    // Wait for serial number column values to be visible
    if (waitForElementsOfPolicyPage("SecretNumberColumnValues")) {
        List<WebElement> serialNumberElements = getElementsOfPolicyPage("SecretNumberColumnValues");
        if (serialNumberElements != null && !serialNumberElements.isEmpty()) {
            serialNumberElements.get(0).click();
            LOGGER.info("Clicked on the first Secret number element.");
            return true;
        } else {
            LOGGER.error("No Secret number elements found.");
        }
    } else {
        LOGGER.error("Secret number column values not visible.");
    }
    return false;
}

	

}
	
	 
	
	

	


		

	