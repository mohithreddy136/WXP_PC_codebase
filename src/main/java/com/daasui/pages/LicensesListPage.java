package com.daasui.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class implements methods related to Subscriptions List Page
 *
 */
public class LicensesListPage extends CommonMethod {
	private ObjectReader SubscriptionsListPagePropertiesReader = new ObjectReader();
	private Properties SubscriptionsListPageProperties;
	private Properties selectedLanguageProperties;
	private LicensesListPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public LicensesListPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (UserPage.class) {
				if (instance == null) {
					instance = new LicensesListPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public LicensesListPage(WebDriver driver) throws IOException {
		SubscriptionsListPageProperties = SubscriptionsListPagePropertiesReader.getObjectRepository("LicensesListPageV3");
	}
	
	/**
	 * @param language: Language code for localization testing
	 * @param localefolder: To specify the folder where the key is present
	 * @param key: Contains the string which is localized
	 * @return String which is localized
	 * @throws Exception
	 */
	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = SubscriptionsListPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of element
	 * @param Text - Text to be matched
	 * @return - boolean value of whether the text present on element matches or not
	 */
	public final boolean matchTextOfSubscriptionsListPage(String key, String Text) {
		try {
			return verifyTextPresentOnElement(SubscriptionsListPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error("Exception in matchTextOfSubscriptionsListPage" + e.getMessage());
			return false;
		}

	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfSubscriptionsListPage(String key) {
		try {
			return verifyElementIsPresent(SubscriptionsListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error("Exception in verifyElementsOfSubscriptionsListPage" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This is a method to get text of an element
	 * 
	 * @param key - Locator of element
	 * @return - String value of the text on the element
	 * @throws Exception 
	 */
	public final String getTextOfSubscriptionsListPage(String key) throws Exception {
			return getTextBy(SubscriptionsListPageProperties.getProperty(key));
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfSubscriptionsListPage(String key) throws Exception {
		click(SubscriptionsListPageProperties.getProperty(key));
	}

	/**This method is used to click by javascript.
	 * @param key
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnSubscriptionsListPage(String key) throws Exception {
		clickByJavaScript(SubscriptionsListPageProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfSubscriptionsListPage(String key) throws Exception {
		return verifyElementIsVisible(SubscriptionsListPageProperties.getProperty(key));
	}
	public final boolean waitForElementsOfSubscriptionsListPageForInvisible(String key) throws Exception {
		return verifyElementIsinvisibile(SubscriptionsListPageProperties.getProperty(key));
	}
	
	public final boolean waitForPresenceOfElementsOfSubscriptionsListPage(String key) throws Exception {
		return waitUntillElementIsPresent(SubscriptionsListPageProperties.getProperty(key));
	}
	
	public final String getAttributesOfSubscriptionsListPage(String key, String value) throws Exception {
		return getAttribute(SubscriptionsListPageProperties.getProperty(key), value);
	}
	
	/**
	 * This is a method to enter text on an element
	 * 
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForSubscriptionsListPage(String key, String Text) {
		try {
			enterText(SubscriptionsListPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForSubscriptionsListPage " + e.getMessage()));
		}
	}
	
	
	public final void clearTextOnSubscriptionsListPage(String key) {
		try {
			clearTextRefresh(SubscriptionsListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clearTextOnSubscriptionsListPage " + e.getMessage()));
		}
	}
	
	
	public final boolean verifyElementIsEnabledOfSubscriptionsListPage(String key) throws Exception {
		return verifyElementIsEnable(SubscriptionsListPageProperties.getProperty(key));
	}
	
	/**
	 * @param api - Licence key API url
	 * @param licenceKey - key string
	 * @param sKUId - SKU name
	 * @return - boolean
	 * @throws Exception
	 */
	public final boolean createLicenceKey(String api, String licenceKey, String sKUId) throws Exception {
		Response response;
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		String body = "{\"buffer\":\"\",\"licenseKey\":\"" + licenceKey +"\",\"numberOfSeats\":100,\"orderId\":\"AutomationOrder\",\"partnerId\":\"\",\"skuId\":\"" + sKUId + "\",\"startDate\":\"\"}";
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
		response = httpRequest.post(api);
		if (response.statusCode() == 201)
			return true;
		else
			return false;
	}
	
	/**
	 * This method set attribute to block to be used in import
	 * 
	 * @param key - Key of the locator
	 */
	public final void setAttributeForSubscriptionPage(String key) {
		setAttributeForImport(SubscriptionsListPageProperties.getProperty(key));
	}
	
	/**
	 * This method used to redirect to SKU details modal
	 * 
	 * @param key - SKU id to be searched
	 * @throws Exception 
	 */
	public final void goToSKUDetailsPage(String key) throws Exception {
		enterTextForSubscriptionsListPage("skuListBox", key);
		sleeper(3000);// sleeper added to see filter result
		clickOnElementsOfSubscriptionsListPage("firstSKUListPage");
	}
	
	/**
	 * This method used to redirect to sub key details page
	 * 
	 * @param key - sub key to be searched
	 * @throws Exception 
	 */
	public final void goToSubscriptionKeyDetailsPage(String key) throws Exception {
		enterTextForSubscriptionsListPage("subKeyBox", key);
		sleeper(3000);
		clickOnElementsOfSubscriptionsListPage("firstSubKeyListPage");
	}
	
	
	/** Verify all plans visible in planList
	 * 
	 * @param key
	 * @param subTypes
	 * @return
	 * @throws Exception
	 */
	public final boolean comparePlansOfAddSKUPlansDropdown(String key, ArrayList<String> subTypes) throws Exception {
		return comparePlansList(SubscriptionsListPageProperties.getProperty(key), subTypes);
	}
	
	
	/**
	 *  Plans list compare with predefined plans array
	 * @param key
	 * @param array
	 * @return
	 * @throws Exception
	 */
	public final boolean comparePlansList(String key, ArrayList<String> array) throws Exception {
		ArrayList<String> menuTabs = new ArrayList<String>();
		List<WebElement> menuList = getElementsTillAllElementsPresent(key);
		for (WebElement listItem : menuList) {
			String value = listItem.getText();
			menuTabs.add(value);
		}
		if (menuTabs.containsAll(array)) {
			return true;
		}

		return false;
	}
	
	/**
	 * This method is used to import file in Veener3
	 * @param File which needs to be uploaded
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void ImportV3(String fileName) throws InterruptedException, AWTException {
		sleeper(2000);
		StringSelection path = new StringSelection(fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		sleeper(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * This is a method to get element
	 *
	 * @param key - Locator of element
	 * @return - web element that contains elements
	 * @throws Exception
	 */
	public final WebElement getElementOfLicenseListPage(String key) throws Exception {
		return getElement(SubscriptionsListPageProperties.getProperty(key));
	}
	/**
	 * This method is used for file imported using Robot class for veneer version 3
	 * @param fileName this is the name of file which was imported
	 * @throws Exception
	 */
	public void fileImportInV3(String fileName) throws Exception {
		sleeper(2000);
		WebElement addFile = getElementOfLicenseListPage("browseInput");
		addFile.sendKeys(fileName);
		sleeper(3000);
	}
}
