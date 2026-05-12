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

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.DashboardVariables;

public class WEPSustainabilityPage extends CommonMethod {

	private WEPSustainabilityPage instance;
	private ObjectReader sustainabilityPagePropertiesReader = new ObjectReader();
	private Properties sustainabilityPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(WEPSustainabilityPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");


	public WEPSustainabilityPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPSustainabilityPage.class) {
				if (instance == null) {
					instance = new WEPSustainabilityPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public WEPSustainabilityPage(WebDriver driver) throws IOException {
		sustainabilityPageProperties = sustainabilityPagePropertiesReader.getObjectRepository("WEPSustainabilityPage");
	}
	public final boolean verifyElementsOfSustainabilityPage(String key) throws Exception {
		return verifyElementIsPresent(sustainabilityPageProperties.getProperty(key));
	}
	
	/**
	 * Verify WebElement is enabled or disabled.
	 * 
	 * @return
	 */
	public final boolean verifyElementIsEnableOnSustainabilityPage(String key) throws Exception {
		return verifyElementIsEnaledOrDisabled(sustainabilityPageProperties.getProperty(key));
	        
	}
	public final void clickOnElementsOfSustainabilityPage(String key) throws Exception {
		click(sustainabilityPageProperties.getProperty(key));
	}
	public final void mouseHoverOfSustainabilityPage(String key) throws Exception {
		mouseHover(sustainabilityPageProperties.getProperty(key));
	}
	public final void actionClickOfSustainabilityPage(String key) throws Exception {
		actionClick(sustainabilityPageProperties.getProperty(key));
	}
	public  void doubleClickOfSustainabilityPage(String key) throws Exception {
		doubleclick(sustainabilityPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsOfSustainabilityPage(String key) throws Exception {
		return getAllElements(sustainabilityPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnSustainabilityPage(String key) throws Exception {
		clickByJavaScript(sustainabilityPageProperties.getProperty(key));
	}
	public final void listMouseHoverOfSustainabilityPage(String key) throws Exception {
		listMouseHover(sustainabilityPageProperties.getProperty(key));
	}
	public final WebElement getElementOfSustainabilityPage(String key) throws Exception {
		return getElement(sustainabilityPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofSustainabilityPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(sustainabilityPageProperties.getProperty(key));
	}

	public final void switchToDifferentTabOfSustainabilityPage() {
		switchToDifferentTab();
	}

	public final void switchToPreviousTabOfSustainabilityPage() {
		switchBackToPreviousTab();
	}

	public final ArrayList<String> getChartLabelsSustainabilityPage(String key) throws Exception {
		return getLabelsOfChart(sustainabilityPageProperties.getProperty(key));
	}

	public void scrollToSustainabilityPage(String key) throws Exception {
		scrollTillView(sustainabilityPageProperties.getProperty(key));
	}

	/**
	 * This method designed to get the  Text of WebElement from web page
	 * @param WebElement 
	 */
	public final String getTextOfWEPSustainabilityPage(String key) throws Exception {
		return getTextBy(sustainabilityPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfSustainabilityPage(String key) throws Exception {
		return verifyElementIsVisible(sustainabilityPageProperties.getProperty(key));
	}

	public final boolean matchTextOfSustainabilityPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(sustainabilityPageProperties.getProperty(key), Text);
	}
	public final boolean waitForElementsOfSustainabilityPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(sustainabilityPageProperties.getProperty(key), waitTime);
	}

	public final boolean waitUntilElementIsInvisibleOfSustainabilityPage(String key){
		return verifyElementIsinvisibile(sustainabilityPageProperties.getProperty(key));
	}

	public final boolean waitUntilAllElementIsInvisibleOfSustainabilityPage(List<WebElement> key){
		return verifyAllElementIsinvisibile(key);
	}

	/** This method will verify export functionality **/
	public boolean verifyExportFunctionality(String languageCode) {
		{
			boolean flag = false;
			try {
				if (verifyElementsOfSustainabilityPage("exportButton")) {
					clickOnElementsOfSustainabilityPage("exportButton");
					//clickOnElementsOfSustainabilityPage("exportXLSXFileLink");
					sleeper(3000);
					if(getTextOfWEPSustainabilityPage("toastNotificationExport").equalsIgnoreCase(getTextLanguage(languageCode,"daas_ui","campaign.response.export.data.success"))){
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
			clickOnElementsOfSustainabilityPage(sortingArrowKey);
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
			clickOnElementsOfSustainabilityPage(sortingArrowKey);
			clickOnElementsOfSustainabilityPage(sortingArrowKey);
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
			unsortedList = getTextOfListOfDevicesFromSustainabilityPage(listKey);
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
			scrollTillView(sustainabilityPageProperties.getProperty(key));
		} catch (Exception e) {

			LOGGER.error(("Exception occured in method scrollOnDeviceListPage " + e.getMessage()));
		}
	}

	private ArrayList<String> getTextOfListOfDevicesFromSustainabilityPage(String listKey) {
		try {
			return getTextOfList(sustainabilityPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfDeviceListFromSustainabilityPage " + e.getMessage()));
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
			return verifySearchFunctionalityofColumn(languageCode, sustainabilityPageProperties.getProperty(textKey), text, sustainabilityPageProperties.getProperty(emptyTextKey), sustainabilityPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOfColumnOnDeviceList " + e.getMessage()));
			return false;
		}
	}

	public void enterTextForsustainabilityPage(String key, String globalfilterlocation) {
		try {
			enterText(sustainabilityPageProperties.getProperty(key), globalfilterlocation);
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
			actionClick(sustainabilityPageProperties.getProperty(dropdownField));    
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
		List<WebElement> listOfLabels = getElementsOfSustainabilityPage(labelsKey);
		//tooltipTextKeyValue = getElementsOfSustainabilityPage(tooltipTextKey);
		for (int i = 0; i < listOfLabels.size(); i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			//waitForElementsOfSustainabilityPage(tooltipTextKey);
			//	text = tooltipTextKeyValue.get(i).getText();
			//	System.out.println(text);
			sleeper(2000);
			listOfLabels.get(i).click();
			List<WebElement> monthtext = getElementsOfSustainabilityPage(textKey);

			if (monthtext.get(0).getText().contains("Total Devices")) {
				flag = true;
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
		}
		return flag;
	}
}