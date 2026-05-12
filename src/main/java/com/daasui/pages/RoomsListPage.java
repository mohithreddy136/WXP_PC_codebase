package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;

/**
 * This class includes all the methods related to Rooms list page test cases
 */

public class RoomsListPage extends CommonMethod {
	private ObjectReader RoomsListPagePropertiesReader = new ObjectReader();
	private Properties RoomsListPagePropertiesPageProperties;

	private RoomsListPage instance;
	public static String uiVersion = System.getProperty("uiVersion");

	public RoomsListPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (RoomsListPage.class) {
				if (instance == null) {
					instance = new RoomsListPage(DRIVER);
				}
			}
		}
		return instance;
	}
	public RoomsListPage(WebDriver driver) throws IOException {
		RoomsListPagePropertiesPageProperties = RoomsListPagePropertiesReader.getObjectRepository("RoomsListPage");
	}

	public final void scrollTillViewRoomsListPage(String locator) throws Exception {
		scrollTillView(RoomsListPagePropertiesPageProperties.getProperty(locator));
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfRoomsListPage(String key) {
		try {
			return verifyElementIsPresent(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfRoomsListPage " + e.getMessage()));
			return false;
		}
	}


	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfRoomsListPage(String key) {
		try {
			return verifyElementIsVisible(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfRoomsListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfRoomsListPageDynamic(String key, int waitTime) {
		try {
			return verifyElementIsVisibleDynamic(RoomsListPagePropertiesPageProperties.getProperty(key), waitTime);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfRoomsListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to match text on an element
	 * 
	 * @param key - Locator of element
	 * @param textToMatch - Text to be matched
	 * @return - boolean value of whether the text present on element matches or not
	 */
	public final boolean matchTextOfRoomsListPage(String key, String textToMatch) {
		try {
			return verifyTextPresentOnElement(RoomsListPagePropertiesPageProperties.getProperty(key), textToMatch);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method matchTextOfRoomsListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to click on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void clickOnElementsOfRoomsListPage(String key) {
		try {
			click(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementsOfRoomsListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get text of an element
	 * 
	 * @param key - Locator of element
	 * @return - String value of the text on the element
	 */
	public final String getTextOfRoomsListPage(String key) {
		try {
			return getTextBy(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfRoomsListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify if the element is enable
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is enabled or not
	 */
	public final boolean verifyElementIsEnableOfRoomsListPage(String key) {
		try {
			return verifyElementIsEnable(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfRoomsListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if the element is selected
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is selected or not
	 */
	public final boolean verifyElementIsSelectedOfRoomsListPage(String key) {
		try {
			return verifyElementIsSelected(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsSelectedOfRoomsListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to clear text present on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void clearTextOnElementsOfRoomsListPage(String key) {
		try {
			clearTextRefresh(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clearTextOnElementsOfRoomsListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to enter text on an element
	 * 
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForRoomsListPage(String key, String Text) {
		try {
			enterText(RoomsListPagePropertiesPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfRoomsListPage " + e.getMessage()));
		}
	}	

	/**
	 * This is the method to verify if all the checkboxes in a column of a table are selected
	 * 
	 * @return - boolean value of whether the checkboxes are selected or not
	 */
	public final boolean verifyAllCheckBoxesSelectedOnRoomsListPage() {
		try {
			return verifyAllCheckBoxesSelectedOfTable(RoomsListPagePropertiesPageProperties.getProperty("allCheckBoxOfPage"));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfRoomsListPage " + e.getMessage()));
			return false;
		}
	}


	/**
	 * This is a method to verify search functionality of search filters on column present on RoomsListPage page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOfColumnOnRoomsList(String languageCode, String textKey, String text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionalityofColumn(languageCode, RoomsListPagePropertiesPageProperties.getProperty(textKey), text, RoomsListPagePropertiesPageProperties.getProperty(emptyTextKey), RoomsListPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOfColumnOnRoomsList " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to get a list of elements of Rooms list page
	 * 
	 * @param key - Locator of list
	 * @return - list of web elements
	 */
	public final List<WebElement> getElementsTillAllElementsVisibleofRoomsListPage(String key) {
		try {
			return getElementsTillAllElementsVisible(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofRoomsListPage " + e.getMessage()));
			return null;
		}
	}

	/**
	 * This is a method to verify the options that are present on a dropdown by default
	 * 
	 * @param key - Locator of available options
	 * @param optionsOnDropdown - The expected options to be present
	 * @return - boolean value of whether the options are correctly displayed
	 */
	public final boolean verifyOptionsOnDropdownForRoomsListPage(String key, ArrayList<String> optionsOnDropdown) {
		try {
			return compareTwoList(RoomsListPagePropertiesPageProperties.getProperty(key), optionsOnDropdown);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyOptionsOnDropdownForRoomsListPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to click on an element by javascript
	 * 
	 * @param key - Locator of element
	 */
	public final void clickByJavaScriptOnRoomsListPage(String key) {
		try {
			clickByJavaScript(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnRoomsListPage " + e.getMessage()));
		}
	}

	/**
	 * This is the method for sorting the RoomName column in descending order
	 * 
	 * @param sortingArrowKey - locator of sorting arrow key
	 * @param listKey - locator of list of items
	 * @param nextPaginationLinkKey - locator of next page navigation link present on pagination area
	 * @param firstPageNavigationKey - locator of first page navigation link present on pagination area
	 * @param idKey - locator of element to scroll up on page
	 * @return - boolean value of whether the sorting functionality is working correctly
	 */


	public final boolean sortingOfRoomNameColumnInDescendingOrder(String sortingArrowKey, String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		try {
			ArrayList<String> unsortedList = getAllRooms(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			clickOnElementsOfRoomListPage(sortingArrowKey);
			clickOnElementsOfRoomListPage(sortingArrowKey);
			sleeper(2000);
			ArrayList<String> sortedList = getAllRooms(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			return sortingInDescendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfRoomNameColumnInDescendingOrder " + e.getMessage()));
			return false;
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
			ArrayList<String> unsortedList = getAllRooms(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			//After sorting
			clickOnElementsOfRoomListPage(sortingArrowKey);
			sleeper(4000);
			ArrayList<String> sortedList = getAllRooms(listKey, nextPaginationLinkKey, firstPageNavigationKey, idKey, selectAllCheckbox);
			return sortingInAscendingOrder(unsortedList, sortedList);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method sortingOfRoomNameColumnInAscendingOrder " + e.getMessage()));
			return false;
		}
	}

	private void clickOnElementsOfRoomListPage(String key) {
		try {
			click(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickOnElementsOfRoomListPage " + e.getMessage()));
		}

	}
	/**
	 * This is a method to get a list of all the incidents present on all the pages
	 * 
	 * @param listKey - This is the key for the list of values on column
	 * @param nextPaginationLinkKey - This is the key for the next page link on pagination
	 * @param firstPageNavigationKey - This is the key for the next page link on pagination
	 * @param idKey - This is the key for the element locator to scroll up on page
	 * @return - This method returns a arraylist of all the incidents present on all the pages
	 */
	public final ArrayList<String> getAllRooms(String listKey, String nextPaginationLinkKey, String firstPageNavigationKey, String idKey, String selectAllCheckbox) {
		ArrayList<String> unsortedList=null;
		try {
			sleeper(2000);
			unsortedList = getTextOfListOfRoomsPage(listKey);
			//			while (getButtonEnabilityStatus(nextPaginationLinkKey)) {
			//				System.out.println("Paginaton started");
			//				scrollOnRoomListPage(firstPageNavigationKey);
			//				sleeper(3000);
			//				clickOnElementsOfRoomListPage(nextPaginationLinkKey);
			//				sleeper(5000);
			//				unsortedList.addAll(getTextOfListOfRoomsPage(listKey));
			//			}
			scrollOnRoomListPage(idKey);
			sleeper(2000);
			return unsortedList;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getAllRooms " + e.getMessage()));
			return unsortedList;
		}
	}

	private ArrayList<String> getTextOfListOfRoomsPage(String listKey) {
		try {
			return getTextOfList(RoomsListPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfRoomsPage " + e.getMessage()));
			return null;
		}
	}
	/**
	 * This is a method to scroll on incident page

	/** 
	 * This is a method to verify search functionality of search filters on column present on roomListPage page
	 * 
	 * @param languageCode - Language code
	 * @param textKey - Locator of searchbox
	 * @param text - Text to be entered
	 * @param emptyTextKey - Locator for "No items available" message
	 * @param listKey - Locator for list of items filtered
	 * @return - boolean value of whether the search functionality is working correctly
	 */
	public final boolean verifySearchValueOfColumnOnRoomList(String languageCode, String textKey, String text, String emptyTextKey, String listKey) {
		try {
			return verifySearchFunctionalityofColumn(languageCode, RoomsListPagePropertiesPageProperties.getProperty(textKey), text, RoomsListPagePropertiesPageProperties.getProperty(emptyTextKey), RoomsListPagePropertiesPageProperties.getProperty(listKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifySearchValueOfColumnOnRoomList " + e.getMessage()));
			return false;
		}
	}


	/** This method getAddRoomsManuallyInfo
	 * @return Room Count Considering the no of rows
	 */
	public String getCountOfRooms() {
		try {
			int numOfRooms = 0;
			scrollOnRoomListPage("roomList");
			numOfRooms = getElementsTillAllElementsVisibleofRoomsListPage("roomList").size();
			String number = String.valueOf(numOfRooms);
			LOGGER.info("Rooms count is fetched.");
			return number;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getting count of rooms : " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method verifyTotalNumberOfRoomsInfo
	 * @param roomCount Locator of element 
	 */
	public boolean verifyTotalNumberOfRooms(String roomCount)
	{
		String totalNumberOfRooms = getCountOfRooms();
		String roomsCount = getTextOfRoomsListPage(roomCount);
		String numberOnly="";
		if(roomsCount.contains("-"))
		{
		String[] arrOfStr = roomsCount.split("- ");
		for(int i=arrOfStr.length-1;i==arrOfStr.length-1;i-- )
		numberOnly = arrOfStr[i];
		}
		else
		{
		numberOnly = roomsCount.replaceAll("[^0-9]", "");
		}

		int result = Integer.parseInt(numberOnly);
		int numberOfRoomsConvertToNo = Integer.parseInt(totalNumberOfRooms);
		if (result == numberOfRoomsConvertToNo) {
			LOGGER.info("Room count matches");
			System.out.println("RoomsCount = "+result+" and "+"Number of Rooms(rows) = "+numberOfRoomsConvertToNo);
			return true;
		}
		else {
			System.out.println("RoomsCount = "+result+" and "+"Number of Rooms(rows) = "+numberOfRoomsConvertToNo);
			LOGGER.info("Room count does not match");
			return false;
		}
	}

	/**
	 * This is a method to scroll on roomListPage page

	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnRoomListPage(String key) {
		try {
			scrollTillView(RoomsListPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {

			LOGGER.error(("Exception occured in method scrollOnRoomListPage " + e.getMessage()));
		}
	}


	/**
	 * This is the method to get the enability status for link/button
	 * 
	 * @param navigationItemPreviouskey - locator for the button/link to be tested
	 * @return - boolean value of the enability status
	 */
	public final boolean getButtonEnabilityStatus(String navigationItemkey) {
		try {
			System.out.println("Button Enable: "+getElement(RoomsListPagePropertiesPageProperties.getProperty(navigationItemkey)).getAttribute("class").contains("disabled"));
			return !getElement(RoomsListPagePropertiesPageProperties.getProperty(navigationItemkey)).getAttribute("class").contains("disabled");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getButtonEnabilityStatus " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This method will delete room
	 * 
	 * @param environment
	 * @return
	 * @throws Exception
	 */
	public final boolean deleteRoom(String environment, String body_deleteRoomAPI, String environmentURL) {
		try {
			boolean flag = false;
					int code = getStatusCode(environmentURL + ConstantURL.DELETE_API_ROOM, body_deleteRoomAPI, "DELETE", environment);
					if (code != CommonVariables.CODEOK) {
						flag = false;
						LOGGER.error("Delete API got failed while deleting room.");
					}
					else
					{
						LOGGER.info("Delete room is successful");
					   flag = true;
					}
			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in deleteRoom: " + e.getMessage());
			return false;
		}
	}
}
