package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.DashboardVariables;

public class WEPRemediationPage extends CommonMethod {

	private WEPRemediationPage instance;
	private ObjectReader RemediationPagePropertiesReader = new ObjectReader();
	private Properties RemediationPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEPRemediationPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");


	public WEPRemediationPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPRemediationPage.class) {
				if (instance == null) {
					instance = new WEPRemediationPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public WEPRemediationPage(WebDriver driver) throws IOException {
		RemediationPageProperties = RemediationPagePropertiesReader.getObjectRepository("WEPRemediationPage");
	}
	public final boolean verifyElementsOfRemediationPage(String key) throws Exception {
		return verifyElementIsPresent(RemediationPageProperties.getProperty(key));
	}
	
	/**
	 * Verify WebElement is enabled or disabled.
	 * 
	 * @return
	 */
	public final boolean verifyElementIsEnableOnRemediationPage(String key) throws Exception {
		return verifyElementIsEnaledOrDisabled(RemediationPageProperties.getProperty(key));
	        
	}
	public final boolean verifyElementIsSelectedOnRemediationPage(String key) throws Exception {
		return verifyElementIsSelected(RemediationPageProperties.getProperty(key));      
	}
	public final void clickOnElementsOfRemediationPage(String key) throws Exception {
		click(RemediationPageProperties.getProperty(key));
	}
	public final boolean verifyElementAttributeValueOnRemediationPage(String key) throws Exception {
		return getAttribute(RemediationPageProperties.getProperty(key),"class").contains("highlight"); 
	}
	public final void mouseHoverOfRemediationPage(String key) throws Exception {
		mouseHover(RemediationPageProperties.getProperty(key));
	}
	public final void actionClickOfRemediationPage(String key) throws Exception {
		actionClick(RemediationPageProperties.getProperty(key));
	}
	public  void doubleClickOfRemediationPage(String key) throws Exception {
		doubleclick(RemediationPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsOfRemediationPage(String key) throws Exception {
		return getAllElements(RemediationPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnRemediationPage(String key) throws Exception {
		clickByJavaScript(RemediationPageProperties.getProperty(key));
	}
	public final void listMouseHoverOfRemediationPage(String key) throws Exception {
		listMouseHover(RemediationPageProperties.getProperty(key));
	}
	public final WebElement getElementOfRemediationPage(String key) throws Exception {
		return getElement(RemediationPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofRemediationPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(RemediationPageProperties.getProperty(key));
	}

	public final void switchToDifferentTabOfRemediationPage() {
		switchToDifferentTab();
	}

	public final void switchToPreviousTabOfRemediationPage() {
		switchBackToPreviousTab();
	}

	public final ArrayList<String> getChartLabelsRemediationPage(String key) throws Exception {
		return getLabelsOfChart(RemediationPageProperties.getProperty(key));
	}

	public void scrollToRemediationPage(String key) throws Exception {
		scrollTillView(RemediationPageProperties.getProperty(key));
	}

	/**
	 * This method designed to get the  Text of WebElement from web page
	 * @param WebElement 
	 */
	public final String getTextOfWEPRemediationPage(String key) throws Exception {
		return getTextBy(RemediationPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfRemediationPage(String key) throws Exception {
		return verifyElementIsVisible(RemediationPageProperties.getProperty(key));
	}

	public final boolean matchTextOfRemediationPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(RemediationPageProperties.getProperty(key), Text);
	}
	public final boolean waitForElementsOfRemediationPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(RemediationPageProperties.getProperty(key), waitTime);
	}

	public final boolean waitUntilElementIsInvisibleOfRemediationPage(String key){
		return verifyElementIsinvisibile(RemediationPageProperties.getProperty(key));
	}

	public final boolean waitUntilAllElementIsInvisibleOfRemediationPage(List<WebElement> key){
		return verifyAllElementIsinvisibile(key);
	}

	/** This method will verify export functionality **/
	public boolean verifyExportFunctionality(String languageCode) {
		{
			boolean flag = false;
			try {
				if (verifyElementsOfRemediationPage("exportButton")) {
					clickOnElementsOfRemediationPage("exportButton");
					//clickOnElementsOfRemediationPage("exportXLSXFileLink");
					sleeper(3000);
					if(getTextOfWEPRemediationPage("toastNotificationExport").equalsIgnoreCase(getTextLanguage(languageCode,"daas_ui","campaign.response.export.data.success"))){
						flag=true;
						LOGGER.info("Toast Notification generated successfully for Export of Sustainability");
					}else{
						flag=false;
						LOGGER.error("Export API is not working correctly.");
					}
				}else{
					LOGGER.info("Export button is not showing on UI due to no data.");
				}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return flag;

		}
	}



	/**
	 * This is the method for sorting the RoomName column in ascending order
	 * 
	 * @param sortingArrowKey - locator of sorting arrow key
	 * @param listKey - locator of list of items
	 * @param nextPaginationLinkKey - locator of next page navigation link present on pagination area
	 * @param firstPageNavigationKey - locator of first page navigation link present on pagination area
	 * @param idKey - locator of element to scroll up on page
	 * @return - boolean value of whether the sorting functionality is working correctly
	 */

	public final boolean sortingOfRoomNameoColumnInAscendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			//Before sorting
			ArrayList<String> unsortedList = getAllDevices(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			//After sorting
			clickOnElementsOfRemediationPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllDevices(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			return sortingInAscendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfDeviceNameColumnInAscendingOrder " + e.getMessage()));
			return false;
		}
	}

	public final boolean sortingOfRoomNameColumnInDescendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllDevices(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			clickOnElementsOfRemediationPage(sortingArrowKey);
			clickOnElementsOfRemediationPage(sortingArrowKey);
			sleeper(2000);
			ArrayList<String> sortedList = getAllDevices(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			return sortingInDescendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfDeviceNameColumnInDescendingOrder " + e.getMessage()));
			return false;
		}
	}


	/**
	 * This is a method to get a list of all the devices present on all the pages
	 * 
	 * @param listKey - This is the key for the list of values on column
	 * @param nextPaginationLinkKey - This is the key for the next page link on pagination
	 * @param firstPageNavigationKey - This is the key for the next page link on pagination
	 * @param idKey - This is the key for the element locator to scroll up on page
	 * @return - This method returns a arraylist of all the devices present on all the sustaianbility pages
	 */
	public final ArrayList<String> getAllDevices(String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		ArrayList<String> unsortedList=null;
		try {
			sleeper(2000);
			unsortedList = getTextOfListOfDevicesFromRemediationPage(listKey);
			scrollOnDeviceListPage(idKey);
			sleeper(2000);
			return unsortedList;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAllRooms " + e.getMessage()));
			return unsortedList;
		}
	}

	/**
	 * This is a method to scroll on roomListPage page

	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnDeviceListPage(String key) {
		try {
			scrollTillView(RemediationPageProperties.getProperty(key));
		} catch (Exception e) {

			LOGGER.error(("Exception occured in method scrollOnDeviceListPage " + e.getMessage()));
		}
	}

	private ArrayList<String> getTextOfListOfDevicesFromRemediationPage(String listKey) {
		try {
			return getTextOfList(RemediationPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfDeviceListFromRemediationPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify search functionality of search filters on column present on deviceListPage page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOfColumnOnDeviceList(String languageCode, String textKey, String text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionalityofColumn(languageCode, RemediationPageProperties.getProperty(textKey), text, RemediationPageProperties.getProperty(emptyTextKey), RemediationPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOfColumnOnDeviceList " + e.getMessage()));
			return false;
		}
	}

	public void enterTextForRemediationPage(String key, String globalfilterlocation) {
		try {
			enterText(RemediationPageProperties.getProperty(key), globalfilterlocation);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfRoomsListPage " + e.getMessage()));
		}
	}


	/**
	 * This method designed to click on DropDown field on Global Filter page
	 * @param dropdownField 
	 */
	public final void selectdropdownField(String dropdownField) {
		try {
			actionClick(RemediationPageProperties.getProperty(dropdownField));    
		}catch(Exception e) {
			LOGGER.error("Exception occured on selecting the company fromlist box "+dropdownField+" view"+e.getMessage());
		}

	}

	/**
	 * This method designed to verify the tooltip functionality on Sustainability page
	 * @param labelsKey , tooltipTextKey and textKey
	 */
	public final boolean verifyTooltipTextOnPowerConsumptionGraph(String labelsKey, String tooltipTextKey, String textKey)
			throws Exception {
		boolean flag = false;
		String text = null;
		List<WebElement> tooltipTextKeyValue = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfRemediationPage(labelsKey);
		//tooltipTextKeyValue = getElementsOfRemediationPage(tooltipTextKey);
		for (int i = 0; i < listOfLabels.size(); i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			//waitForElementsOfRemediationPage(tooltipTextKey);
			//	text = tooltipTextKeyValue.get(i).getText();
			//	System.out.println(text);
			sleeper(2000);
			listOfLabels.get(i).click();
			List<WebElement> monthtext = getElementsOfRemediationPage(textKey);

			if (monthtext.get(0).getText().contains("Total Devices")) {
				flag = true;
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
		}
		return flag;
	}
}