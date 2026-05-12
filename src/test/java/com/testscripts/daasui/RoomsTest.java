package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.DashboardVariables;
import com.daasui.pages.RoomsDetailsPage;
import com.daasui.pages.RoomsListPage;

public class RoomsTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(RoomsTest.class);
	String numberOfRooms = null;

	public static String searchText_RoomName = "test";
	public static String searchText_Floor = "1";
	public static String searchText_Location = "Blore";
	public static String roomName = "Sample_Test";
	public static String floor = "2";
	public static String building = "Serenity";
	public static String location = "Bangalore";
	public static int waitTime = 10000;
	public static String searchText_RoomNameOnline = "Zoom";
	public static String confPCDeviceValue = "HP Presence Mini Conferencing PC";
	public static String confPCTypeValue = "OptionCard";
	public static String presenceControlDeviceValue = "HP Presence Control";
	public static String presenceControlTypeValue = "CenterOfRoomControlStandalone";
	public static String toasterMsgConfPCLine2 = "HP Presence Mini Conferencing PC in Priyesh";
	public static String rebootScreenConfPCHeader = "Reboot HP Presence Mini Conferencing PC ?";
	public static String rebootScreenConfPCText = "You are about to reboot HP Presence Mini Conferencing PC";
	public static String toasterMsgCORCLine2 = "HP Presence Control in Priyesh";
	public static String rebootScreenCORCHeader = "Reboot HP Presence Control ?";
	public static String rebootScreenCORCText = "You are about to reboot HP Presence Control";
	public static String rebootDeviceName = "DESKTOP-670C6GQ";
	public static String confPCTask= "Restart Device";
	public static String corcTask= "Reboot CoRC Device";
	public String corcTaskID;
	public String confPCTaskID;
	public String addRoomName = "Auto_Room2_Test";
	public String updatedRoomName = "Updated_Auto_Room";

	@DataProvider
	public Object[][] loginDataListPage() {
		Object[][] data = new Object[2][2];
		data[0][0] = "COMPANY_CAAS_EMAIL";
		data[0][1] = "COMPANY_CAAS_PASSWORD";
		return data;
	}


	/**
	 * This method will verify the title of Rooms page
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"CAAS_REGRESSION"}, description = "TC939603")
	public final void verifyRoomsTitle() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");
		} 

	}

	/**
	 * This Test will verify the Buttons deleteButton,deviceSettings,configureRoomAssistant is present When No Rooms selected
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * @throws Exception
	 */

	@Test(priority = 2, groups = {"CAAS_REGRESSION"}, description = "TC974091")
	public final void verifyButtonsWhenNoRoomSelected() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		sleeper(3000);

		//Test case - This test case validates if the delete,configureRoomAssistant and deviceSettings button is present when no rooms are selected
		softAssert.assertFalse(RoomsListPage.verifyElementsOfRoomsListPage("deleteButton"), "Delete button is present even when no room is selected");
		softAssert.assertFalse(RoomsListPage.verifyElementsOfRoomsListPage("deviceSettings"), "Device Settings is present even when no room is selected");
		softAssert.assertFalse(RoomsListPage.verifyElementsOfRoomsListPage("configureRoomAssistant"), "Configure Room Assistant is present even when no room is selected");

		LOGGER.info("deleteButton,deviceSettings and configureRoomAssistant buttons are not present when no room selected ");

		softAssert.assertAll();

	}

	/**
	 * This method will verify the Search functionality of Columns - Room Name, Floor and Location in the Rooms page
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {"CAAS_REGRESSION"}, description = "TC977280")
	public final void verifySearch() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", searchText_RoomName);
			RoomsListPage.clickOnElementsOfRoomsListPage("roomSizeSmall");	
			LOGGER.info("Inputs entered in Add Room General Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location")), "Title on Add room location screen is incorrect");
			LOGGER.info("Add Room Location Screen matched");

			RoomsListPage.enterTextForRoomsListPage("floorTextBox", searchText_Floor);
			RoomsListPage.enterTextForRoomsListPage("buildingTextBox", building);
			RoomsListPage.enterTextForRoomsListPage("locationTextBox", searchText_Location);
			LOGGER.info("Inputs entered in Add Room Location Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.link_conf")), "Title on Add room Link Conferencing Device screen is incorrect");
			LOGGER.info("Add Room Link Conferencing Device Screen matched");

			RoomsListPage.clickOnElementsOfRoomsListPage("deviceCheckBox");
			LOGGER.info("Inputs entered in Add Room Link Conferencing Device Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.summary")), "Title on Add room Summary screen is incorrect");
			LOGGER.info("Add Room Summary Screen matched");

			RoomsListPage.clickOnElementsOfRoomsListPage("summaryAddButton");
			LOGGER.info("Add button in summary page is clicked");

			RoomsListPage.verifyElementIsVisibleDynamic("addRoomConfirmation", waitTime);
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("addRoomConfirmation").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.addroom_title")), "Add Room Confirmation text is incorrect");
			LOGGER.info("Room added successfuly");	
         
			sleeper(3000);
			RoomsListPage.waitForElementsOfRoomsListPage("floorSearchBox");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"floorSearchBox",searchText_Floor,"noItemsAvailable","floorSearchList"),true);
			LOGGER.info("Floor Search is working");
			RoomsListPage.waitForElementsOfRoomsListPage("locationSearchBox");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"locationSearchBox",searchText_Location,"noItemsAvailable","locationSearchList"),true);
			LOGGER.info("Location Search is working");
			RoomsListPage.waitForElementsOfRoomsListPage("roomNameSearchBox");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",searchText_RoomName,"noItemsAvailable","listkey"),true);
			LOGGER.info("Room Name Search is working");
			RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
			sleeper(2000);
			waitForPageLoaded();
			LOGGER.info("Redirected to Rooms details page");
			waitForPageLoaded();
			sleeper(3000);
			String roomId = RoomsListPage.getRoomID();
			String body="{\"roomIdList\": [\""+roomId+"\"]}";
			Assert.assertTrue(RoomsListPage.deleteRoom(environment,body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL")), "Delete Room failed");
		} 			
	}	


	/**
	 * This Test will validates the Room count(rows) matches with pagination count number of rooms
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"CAAS_REGRESSION"}, description = "TC977283")
	public final void verifyRoomsCountsWithPaginationCount() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		// Test case  - This test case validates the Total count of Rooms with Menu display number of Rooms

		Assert.assertEquals(RoomsListPage.verifyTotalNumberOfRooms("roomPageCount"),true);

	}

	/**
	 * This Test will verify the Buttons deleteButton,deviceSettings,configureRoomAssistant When All Rooms selected
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * @throws Exception
	 */
	@Test(priority = 5, groups = {"CAAS_REGRESSION"}, description = "TC974091")
	public final void verifyButtonsWhenAllRoomsSelected() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();			
		sleeper(3000);

		RoomsListPage.waitForElementsOfRoomsListPage("selectAllCheckBoxRoomList");
		RoomsListPage.clickByJavaScriptOnRoomsListPage("selectAllCheckBoxRoomList");
		LOGGER.info("Selected all rooms");

		RoomsListPage.clickByJavaScriptOnRoomsListPage("moreButton");

		RoomsListPage.waitForElementsOfRoomsListPage("configureRoomAssistantBtn");
		softAssert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("deviceSettings"), "Delete button is not present when all rooms selected");
		softAssert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("configureRoomAssistantBtn"), "Configure Room Assistant button is not present when all rooms selected");
		softAssert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("configureRoomAssistant"), "Device Settings button is not present when all rooms selected");
		System.out.println(RoomsListPage.getTextOfRoomsListPage("configureRoomAssistant")+" "+","+" "+RoomsListPage.getTextOfRoomsListPage("deviceSettings")+" and "+RoomsListPage.getTextOfRoomsListPage("configureRoomAssistantBtn") +" "+"Buttons are present,when selectAll checkBox is selected");		

		softAssert.assertAll();
	}

	/**
	 * This Test will Validate the room count(rows) matches with SelectAllCheckBox
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * @throws Exception
	 */
	@Test(priority = 6, groups = {"CAAS_REGRESSION"}, description = "TC977283")
	public final void verifyTotalRoomsWithSelectAllAndDeselectAllRooms() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		RoomsListPage.waitForElementsOfRoomsListPage("selectAllCheckBoxRoomList");
		RoomsListPage.clickByJavaScriptOnRoomsListPage("selectAllCheckBoxRoomList");
		LOGGER.info("Selected all rooms");

		//Test case - This test case validates if the rooms count matches with selectAllCheckBox items count
		Assert.assertEquals(RoomsListPage.verifyTotalNumberOfRooms("roomsSelectAllCount"),true);

		RoomsListPage.clickByJavaScriptOnRoomsListPage("selectAllCheckBoxRoomList");
		LOGGER.info("Deselected all rooms");

	}

	/**
	 * This Test will verify the Buttons deleteButton,deviceSettings,configureRoomAssistant is present When one Room is selected
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * @throws Exception
	 */
	@Test(priority = 7, groups = {"CAAS_REGRESSION"}, description = "TC974091")
	public final void verifyButtonsWhenOneRoomSelected() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		sleeper(3000);
		RoomsListPage.waitForElementsOfRoomsListPage("roomListCheckBox");		
		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomListCheckBox");		
		LOGGER.info("Selected one room");

		RoomsListPage.clickByJavaScriptOnRoomsListPage("closeButton");

		RoomsListPage.clickByJavaScriptOnRoomsListPage("moreButton");

		softAssert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("deviceSettings"), "Delete button is not present when one room is selected");
		softAssert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("configureRoomAssistantBtn"), "Configure Room Assistant button is not present when one room is selected");
		softAssert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("configureRoomAssistant"), "Device Settings button is not present when one room is selected");
		System.out.println(RoomsListPage.getTextOfRoomsListPage("deviceSettings")+" "+","+" "+RoomsListPage.getTextOfRoomsListPage("configureRoomAssistantBtn")+" and "+RoomsListPage.getTextOfRoomsListPage("configureRoomAssistant") +" "+"Buttons are present,when one check box is selected");		

		softAssert.assertAll();
	}

	/**
	 * This Test will Validate the room one selected count matches with display selected item count
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * @throws Exception
	 */
	@Test(priority = 8, groups = {"CAAS_REGRESSION"}, description = "TC977283")
	public final void verifyOneRoomSelectedCount() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		RoomsListPage.waitForElementsOfRoomsListPage("roomListCheckBox");
		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomListCheckBox");
		LOGGER.info("Selected one room");

		//Test case - This will Validate the room one selected count matches with display selected item count
		//Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsSelectAllCount").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "list.item_selected")), "item selected is incorrect");
		String roomOneCount = RoomsListPage.getTextOfRoomsListPage("roomsSelectAllCount");
		Assert.assertEquals(roomOneCount,"1 item is selected.");

	}

	/**
	 * This method will verify the Roomlist page is in sorting or not
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * Test Case 977249: [Caas][UI] Verify user is able to sort the Room List Table
	 * @throws Exception
	 */

	@Test(priority = 9, groups = { "CAAS_REGRESSION"}, description = "TC977249")
	public final void verifySortingOfRoomNameoColumnInAscendingOrder() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		sleeper(3000);
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();
		RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
		Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
		LOGGER.info("Rooms title matched");
		RoomsListPage.sortingOfRoomNameoColumnInAscendingOrder("sortingArrowKey","listkey","navigationItemNext","navigationItemFrist","idKey","selectAllCheckBox");
		LOGGER.info("RoomNames are in Ascecnding Order");
	} 



	/**
	 * This method will verify the Roomlist page is in sorting or not
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * Test Case 977249: [Caas][UI] Verify user is able to sort the Room List Table
	 * @throws Exception
	 */

	@Test(priority = 10, groups = { "CAAS_REGRESSION"}, description = "TC977249")
	public final void verifySortingOfRoomNameColumnInDescendingOrder() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		sleeper(3000);
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();
		RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
		Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
		LOGGER.info("Rooms title matched");
		RoomsListPage.sortingOfRoomNameColumnInDescendingOrder("sortingArrowKey","listkey","navigationItemNext","navigationItemFrist","idKey","selectAllCheckBox");
		LOGGER.info("RoomNames are in Descending Order");


	} 


	/**
	 * This method will verify the Hidden Columns in the Roomlist Page on Settings Icon
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * Test Case 972119: [Caas][UI] Verify user is able to Update Room List Table Configuration
	 * @throws Exception
	 */
	@Test(priority = 11,groups = { "CAAS_REGRESSION"}, description = "TC972119")
	public final void verifyRoomListTableConfigurationFunctionality() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		sleeper(3000);
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(2000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();
		//		RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
		//		Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
		//		LOGGER.info("Rooms title matched");
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			ArrayList<String> roomName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.name")));

			ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.name"),
					getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.size"),
					getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.floor"),
					getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location"),
					getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.company"),
					getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.health_status"),
					getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.update_status"),
					getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.current_state")));
			ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true", "false", "false"));
			verifyTableConfigurationTests(columnName, checkboxValue, roomName);

		} 

	}

	/**
	 * This Test will verify the title of room page
	 * User Story 797793: [CaaS][UI] Create the Room Details page
	 * @throws Exception
	 */
	@Test(priority = 12, groups = {"CAAS_REGRESSION"}, description = "TC988566")
	public final void verifyRoomDetailsTitle() throws Exception {

		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
		waitForPageLoaded();
		LOGGER.info("Redirected to Rooms details page");
		sleeper(5000);
		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

		//Test case - This Test case will Verify the title of Room details page
		//System.out.println(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsTitle") + " equals" + roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","breadcrumbs.rooms_details"));
		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsTitle").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","breadcrumbs.rooms_details")), "Room details title is incorrect");

		LOGGER.info("Rooms details page title matched");
	}

	/**
	 * This Test will verify the name of room in Rooms details page
	 * User Story 797793: [CaaS][UI] Create the Room Details page
	 * @throws Exception
	 */
	@Test(priority = 13, groups = {"CAAS_REGRESSION"}, description = "TC988568")
	public final void verifyRoomName() throws Exception {

		String roomNameLink = null;
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		roomNameLink = RoomsListPage.getTextOfRoomsListPage("roomList");
		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
		waitForPageLoaded();
		LOGGER.info("Redirected to Rooms details page");
		sleeper(5000);
		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsTitle").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","breadcrumbs.rooms_details")), "Room details title is incorrect");
		LOGGER.info("Rooms details page title matched");

		//Test case - This Test case will Verify the Room Name
		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomName").equals(roomNameLink), "Room name is incorrect");
		System.out.println("Clicked on Room :"+" "+roomNameLink+"----->"+"matches with Room details page Room name :"+" "+roomsDetailsPage.getTextOfRoomsDetailsPage("RoomName"));
	}

	/**
	 * This Test will verify the All Tabs in Rooms details page
	 * User Story 797793: [CaaS][UI] Create the Room Details page 
	 * @throws Exception
	 */
	@Test(priority = 14, groups = {"CAAS_REGRESSION"}, description = "TC988570")
	public final void verifyAllTabs() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
		waitForPageLoaded();
		LOGGER.info("Redirected to Rooms details page");
		waitForPageLoaded();

		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(roomsDetailsPage.verifyElementsOfRoomsDetailsPage("RoomAssistantTab"), "RoomAssistant Tab on Rooms details page is not loaded successfully");
		softAssert.assertTrue(roomsDetailsPage.verifyElementsOfRoomsDetailsPage("SettingsTab"), "Settings Tab on Rooms details page is not loaded successfully");
		softAssert.assertTrue(roomsDetailsPage.verifyElementsOfRoomsDetailsPage("IncidentsTab"), "Incidents Tab on Rooms details page is not loaded successfully");
		softAssert.assertTrue(roomsDetailsPage.verifyElementsOfRoomsDetailsPage("MeetingsTab"), "Meetings Tab on Rooms details page is not loaded successfully");
		softAssert.assertTrue(roomsDetailsPage.verifyElementsOfRoomsDetailsPage("HardwareTab"), "Hardware Tab on Rooms details page is not loaded successfully");
		softAssert.assertTrue(roomsDetailsPage.verifyElementsOfRoomsDetailsPage("OverviewTab"), "Overview Tab on Rooms details page is not loaded successfully");

		LOGGER.info("All 6 tabs have been Verified in Room details Page");
		softAssert.assertAll();
	}

	/**
	 * This method will verify the Add Room functionality - Add Room => Success Flow
	 * User Story 829108: [CaaS][UI] Add Room
	 * @throws Exception
	 */
	@Test(priority = 15, groups = {"CAAS_REGRESSION"}, description = "TC857085")
	public final void verifyAddRoomSuccessFlow() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", roomName);
			RoomsListPage.clickOnElementsOfRoomsListPage("roomSizeSmall");	
			LOGGER.info("Inputs entered in Add Room General Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location")), "Title on Add room location screen is incorrect");
			LOGGER.info("Add Room Location Screen matched");

			RoomsListPage.enterTextForRoomsListPage("floorTextBox", floor);
			RoomsListPage.enterTextForRoomsListPage("buildingTextBox", building);
			RoomsListPage.enterTextForRoomsListPage("locationTextBox", location);
			LOGGER.info("Inputs entered in Add Room Location Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.link_conf")), "Title on Add room Link Conferencing Device screen is incorrect");
			LOGGER.info("Add Room Link Conferencing Device Screen matched");

//			RoomsListPage.clickOnElementsOfRoomsListPage("deviceCheckBox");
//			LOGGER.info("Inputs entered in Add Room Link Conferencing Device Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.summary")), "Title on Add room Summary screen is incorrect");
			LOGGER.info("Add Room Summary Screen matched");

			RoomsListPage.clickOnElementsOfRoomsListPage("summaryAddButton");
			LOGGER.info("Add button in summary page is clicked");

			RoomsListPage.verifyElementIsVisibleDynamic("addRoomConfirmation", waitTime);
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("addRoomConfirmation").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.addroom_title")), "Add Room Confirmation text is incorrect");
			LOGGER.info("Room added successfuly");	

			RoomsListPage.waitForElementsOfRoomsListPage("roomNameSearchList");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",roomName,"noItemsAvailable","roomNameSearchList"),true);
			LOGGER.info("Added room is found in the rooms list page");
			
			sleeper(2000);
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",roomName,"noItemsAvailable","listkey"),true);
			LOGGER.info("Room Name Search is working");
			RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
			sleeper(2000);
			waitForPageLoaded();
			LOGGER.info("Redirected to Rooms details page");
			waitForPageLoaded();
			sleeper(3000);
			String roomId = RoomsListPage.getRoomID();
			String body="{\"roomIdList\": [\""+roomId+"\"]}";
			Assert.assertTrue(RoomsListPage.deleteRoom(environment,body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL")), "Delete Room failed");
		}
	}

	/**
	 * This method will verify the Add Room functionality - Add Room => General screen validation and click on cancel button
	 * User Story 829108: [CaaS][UI] Add Room
	 * @throws Exception
	 */
	@Test(priority = 16, groups = {"CAAS_REGRESSION"}, description = "TC857085")
	public final void verifyAddRoomGeneralScreen() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.waitForElementsOfRoomsListPage("roomName");
			RoomsListPage.waitForElementsOfRoomsListPage("roomSize");
			RoomsListPage.waitForElementsOfRoomsListPage("roomSizeHuddle");
			RoomsListPage.waitForElementsOfRoomsListPage("roomSizeSmall");
			RoomsListPage.waitForElementsOfRoomsListPage("roomSizeMedium");
			RoomsListPage.waitForElementsOfRoomsListPage("roomSizeLarge");
			LOGGER.info("Add Room General Screen validated");

			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomCancelButton");
			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			LOGGER.info("Add Room Cancel button verified");			
		}
	}

	/**
	 * This method will verify the Add Room functionality - Add Room => Location screen validation and click on back button
	 * User Story 829108: [CaaS][UI] Add Room
	 * @throws Exception
	 */
	@Test(priority = 17, groups = {"CAAS_REGRESSION"}, description = "TC857085")
	public final void verifyAddRoomLocationScreen() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", searchText_RoomName);
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location")), "Title on Add room location screen is incorrect");
			LOGGER.info("Add Room Location Screen matched");

			RoomsListPage.waitForElementsOfRoomsListPage("floor");
			RoomsListPage.waitForElementsOfRoomsListPage("building");
			RoomsListPage.waitForElementsOfRoomsListPage("location");
			LOGGER.info("Add Room Location Screen validated");

			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomBackButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room Back button verified");						
		}
	}

	/**
	 * This method will verify the Add Room functionality - Add Room => Link Conferencing Device screen validation and click on back button
	 * User Story 829108: [CaaS][UI] Add Room
	 * @throws Exception
	 */
	@Test(priority = 18, groups = {"CAAS_REGRESSION"}, description = "TC857085")
	public final void verifyAddRoomLinkConferencingDeviceScreen() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", searchText_RoomName);
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location")), "Title on Add room location screen is incorrect");
			LOGGER.info("Add Room Location Screen matched");

			RoomsListPage.enterTextForRoomsListPage("locationTextBox", location);
			LOGGER.info("Input entered in Mandatory field Location in Add Room Location Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.link_conf")), "Title on Add room Link Conferencing Device screen is incorrect");
			LOGGER.info("Add Room Link Conferencing Device Screen matched");

			RoomsListPage.waitForElementsOfRoomsListPage("buttonDeviceName");
			RoomsListPage.waitForElementsOfRoomsListPage("buttonSerialNumber");
			RoomsListPage.waitForElementsOfRoomsListPage("buttonRoomName");
			RoomsListPage.waitForElementsOfRoomsListPage("buttonAssignmentStatus");
			RoomsListPage.waitForElementsOfRoomsListPage("buttonDeviceHealth");
			RoomsListPage.waitForElementsOfRoomsListPage("buttonUCPlatform");
			RoomsListPage.waitForElementsOfRoomsListPage("buttonPeripherals");
			LOGGER.info("Add Room Link Conferencing Device Screen validated");

			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomBackButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location")), "Title on Add room location screen is incorrect");
			LOGGER.info("Add Room Back button verified");						
		}
	}

	/**
	 * This method will verify the Add Room functionality - Add Room => summary screen validation and click on back button
	 * User Story 829108: [CaaS][UI] Add Room
	 * @throws Exception
	 */
	@Test(priority = 19, groups = {"CAAS_REGRESSION"}, description = "TC857085")
	public final void verifyAddRoomSummaryScreen() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", searchText_RoomName);
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location")), "Title on Add room location screen is incorrect");
			LOGGER.info("Add Room Location Screen matched");

			RoomsListPage.enterTextForRoomsListPage("locationTextBox", location);
			LOGGER.info("Input entered in Mandatory field Location in Add Room Location Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.link_conf")), "Title on Add room Link Conferencing Device screen is incorrect");
			LOGGER.info("Add Room Link Conferencing Device Screen matched");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.summary")), "Title on Add room Summary screen is incorrect");
			LOGGER.info("Add Room Summary Screen matched");

			RoomsListPage.waitForElementsOfRoomsListPage("summaryGENERAL");
			RoomsListPage.waitForElementsOfRoomsListPage("roomName");
			RoomsListPage.waitForElementsOfRoomsListPage("roomSize");
			RoomsListPage.waitForElementsOfRoomsListPage("summaryLOCATION");
			RoomsListPage.waitForElementsOfRoomsListPage("summaryCompany");
			RoomsListPage.waitForElementsOfRoomsListPage("building");
			RoomsListPage.waitForElementsOfRoomsListPage("location");
			RoomsListPage.waitForElementsOfRoomsListPage("floor");
			RoomsListPage.waitForElementsOfRoomsListPage("summaryDEVICEDETAILS");
			RoomsListPage.waitForElementsOfRoomsListPage("summaryDevice");
			RoomsListPage.waitForElementsOfRoomsListPage("summarySerialNumber");
			RoomsListPage.waitForElementsOfRoomsListPage("summaryUCPlatform");
			LOGGER.info("Add Room Summary Screen validated");

			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomBackButtonSummaryScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.link_conf")), "Title on Add room Link Conferencing Device screen is incorrect");
			LOGGER.info("Add Room Back button verified");						
		}
	}

	/**
	 * This method will verify the Add Room functionality - Add Room => add already existing room and verify room name existing error
	 * User Story 829108: [CaaS][UI] Add Room
	 * @throws Exception
	 */
	@Test(priority = 20, groups = {"CAAS_REGRESSION"}, description = "TC857085")
	public final void verifyAddRoomRoomNameExistingError() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", roomName);
			RoomsListPage.clickOnElementsOfRoomsListPage("roomSizeSmall");	
			LOGGER.info("Inputs entered in Add Room General Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location")), "Title on Add room location screen is incorrect");
			LOGGER.info("Add Room Location Screen matched");

			RoomsListPage.enterTextForRoomsListPage("floorTextBox", floor);
			RoomsListPage.enterTextForRoomsListPage("buildingTextBox", building);
			RoomsListPage.enterTextForRoomsListPage("locationTextBox", location);
			LOGGER.info("Inputs entered in Add Room Location Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.link_conf")), "Title on Add room Link Conferencing Device screen is incorrect");
			LOGGER.info("Add Room Link Conferencing Device Screen matched");

			RoomsListPage.clickOnElementsOfRoomsListPage("deviceCheckBox");
			LOGGER.info("Inputs entered in Add Room Link Conferencing Device Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.summary")), "Title on Add room Summary screen is incorrect");
			LOGGER.info("Add Room Summary Screen matched");

			RoomsListPage.clickOnElementsOfRoomsListPage("summaryAddButton");
			LOGGER.info("Add button in summary page is clicked");

			RoomsListPage.verifyElementIsVisibleDynamic("addRoomConfirmation", waitTime);
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("addRoomConfirmation").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.addroom_title")), "Add Room Confirmation text is incorrect");
			LOGGER.info("Room added successfuly");	

			RoomsListPage.waitForElementsOfRoomsListPage("roomNameSearchList");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",roomName,"noItemsAvailable","roomNameSearchList"),true);
			LOGGER.info("Added room is found in the rooms list page");
			
			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", roomName);	
			LOGGER.info("Inputs entered in Add Room General Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			RoomsListPage.verifyElementIsVisibleDynamic("roomNameExistingError", waitTime);
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomNameExistingError").equals(getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.text.resource_exist")), "Room Name existing error is incorrect");
			LOGGER.info("Resource already exists for requested Tenant id and roomName. Message is verified");
			
			refreshPage();
			sleeper(2000);
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",roomName,"noItemsAvailable","listkey"),true);
			LOGGER.info("Room Name Search is working");
			RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
			sleeper(2000);
			waitForPageLoaded();
			LOGGER.info("Redirected to Rooms details page");
			waitForPageLoaded();
			sleeper(3000);
			String roomId = RoomsListPage.getRoomID();
			String body="{\"roomIdList\": [\""+roomId+"\"]}";
			Assert.assertTrue(RoomsListPage.deleteRoom(environment,body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL")), "Delete Room failed");
			
		}
	}

	/**
	 * This method will verify the Add Room functionality - Add Room => verify room name mandatory field error
	 * User Story 829108: [CaaS][UI] Add Room
	 * @throws Exception
	 */
	@Test(priority = 21, groups = {"CAAS_REGRESSION"}, description = "TC857085")
	public final void verifyAddRoomRoomNameMandatoryField() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			RoomsListPage.waitForElementsOfRoomsListPage("fieldRequiredError");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("fieldRequiredError").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Madatory field validation text is incorrect");			
		}
	}
	/**
	 * This method used to verify User can link the device to Existing room or not
	 * User Story 797791: [CaaS][UI] Create the Room List page
	 * Test Case 991662: [Caas][Rooms][SanityCheck] Verify user is able to link the device to Existing Room
	 * @throws Exception
	 */
	@Test(priority = 22,groups = { "CAAS_REGRESSION"}, description = "TC991662")
	public final void linkDeviceToExistingRoom() throws Exception {
		String exisitingRoom= "AUTO_ROOM_DO_NOT_DELETE";
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("roomsTitleOnBreadcrumbs").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");
			roomsDetailsPage.linkDeviceToExistingRoom("roomNameSearch",exisitingRoom,"AutoRoomNameLink",rebootDeviceName,"linkDeviceBtn","deviceSearch","selectDevice","navigationItemDiv","linkSubmitButton");

		}
	}

	/**
	 * This Test will verify the Summary Panel in Rooms details page
	 * User Story 797793: [CaaS][UI] Create the Room Details page 
	 * @throws Exception
	 */
	@Test(priority = 23, groups = {"CAAS_REGRESSION"}, description = "TC1035023")
	public final void verifySummaryPanel() throws Exception {

		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
		waitForPageLoaded();
		LOGGER.info("Redirected to Rooms details page");
		waitForPageLoaded();

		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsComputeDevice").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","rooms.details.section.compute_device")), "Compute Device title is incorrect");
		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsSize").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","rooms.details.section.size")), "Room details Size title is incorrect");
		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsCapacity").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","rooms.details.section.capacity")), "Room details Capacity title is incorrect");
		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsCompany").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","rooms.list.label.company")), "Room details Company title is incorrect");
		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsLocation").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","rooms.list.label.location")), "Room details Location title is incorrect");
		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsBuilding").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","rooms.details.section.building")), "Room details Building title is incorrect");
		Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsFloor").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui","rooms.details.section.floor")), "Room details Floor title is incorrect");
		LOGGER.info("ComputDevice,Size,Capacity,Company,Location,Building,Floor titles are Verified in Summary Panel");
	}

	/**
	 * This Test will verify mandatory fields of Summary Panel as data in Rooms details page
	 * User Story 797793: [CaaS][UI] Create the Room Details page 
	 * @throws Exception
	 */
	@Test(priority = 24, groups = {"CAAS_REGRESSION"}, description = "TC1035026")
	public final void verifySummaryPanelMandatoryFieldvalues() throws Exception {

		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
		waitForPageLoaded();
		LOGGER.info("Redirected to Rooms details page");
		waitForPageLoaded();

		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

		roomsDetailsPage.verifyMandatoryFieldValue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsSizeValue"),"Size");
		roomsDetailsPage.verifyMandatoryFieldValue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsCapacityValue"),"Capacity");
		roomsDetailsPage.verifyMandatoryFieldValue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsCompanyValue"),"Company");
		roomsDetailsPage.verifyMandatoryFieldValue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsLocationValue"),"Location");	
	}

	/**
	 * This Test will verify Health Status is present in Summary Panel in Rooms details page
	 * User Story 797793: [CaaS][UI] Create the Room Details page 
	 * @throws Exception
	 */
	@Test(priority = 25, groups = {"CAAS_REGRESSION"}, description = "TC1035023")
	public final void verifyHealthStatusInSummaryPanel() throws Exception {

		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
		waitForPageLoaded();
		LOGGER.info("Redirected to Rooms details page");
		waitForPageLoaded();

		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

		roomsDetailsPage.verifyHealthStatus();

	}

	/**
	 * This Test will Verify UI in reboot functionality for HP Presence Conf PC
	 * User Story 1056720: [CaaS][UI] After clicking Reboot, call template Service newly exposed API for HP Presence Conf PC device.
	 * @throws Exception
	 */
	@Test(priority = 26, groups = {"CAAS_REGRESSION"}, description = "TC1091908")
	public final void verifyConfPCReboot() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");
			RoomsListPage.waitForElementsOfRoomsListPage("roomNameSearchBox");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",searchText_RoomNameOnline,"noItemsAvailable","roomNameSearchList"),true);
			LOGGER.info("Room Name Search is working");

			RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
			waitForPageLoaded();
			LOGGER.info("Redirected to Rooms details page");
			waitForPageLoaded();

			RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertEquals(roomsDetailsPage.verifyHealthStatusOnline(),true);
			sleeper(3000);
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsSummaryPanelHeader");
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsHardwareTab");
			sleeper(10000);

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabHeader").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.hardware.title")), "Hardware header is not present");
			LOGGER.info("Hardware header is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabDeviceColumn").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.global.device")), "Device Column is not present");
			LOGGER.info("Device Column is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabTypeColumn").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.type")), "Type Column is not present");
			LOGGER.info("Type Column is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabActionColumn").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.hardware.label.action")), "Action Column is not present");
			LOGGER.info("Action Column is present");

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabConfPCDeviceValue").equals(confPCDeviceValue), "RoomDetailsHardwareTabConfPCDeviceValue is not present");
			LOGGER.info("RoomDetailsHardwareTabConfPCDeviceValue is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabConfPCTypeValue").equals(confPCTypeValue), "RoomDetailsHardwareTabConfPCTypeValue is not present");
			LOGGER.info("RoomDetailsHardwareTabConfPCTypeValue is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabConfPCActionValue").contains(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.reboot")), "RoomDetailsHardwareTabConfPCActionValue is not present");
			LOGGER.info("RoomDetailsHardwareTabConfPCActionValue is present");

			Assert.assertTrue(roomsDetailsPage.verifyElementsOfRoomsDetailsPage("RoomDetailsHardwareTabActionValueIcon"),"Reboot icon is not present");

			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsHardwareTabConfPCActionValue");

			//Validation of toaster message
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootToasterMessage").contains(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.corcdevice_reqsuc_heading")), "Toaster message line 1 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootToasterMessage").contains(toasterMsgConfPCLine2), "Toaster message line 2 is not present");
			//Validation of reboot screen

			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RebootScreenHeader");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootScreenHeader").equals(rebootScreenConfPCHeader), "Reboot screen header is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootScreenText1").equals(rebootScreenConfPCText), "RebootScreenText1 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootScreenText2").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.hardware_meeting")), "RebootScreenText2 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootScreenText3").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.hardware_action")), "RebootScreenText3 is not present");

			//click on cancel button
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RebootScreenCancelButton");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabHeader").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.hardware.title")), "Hardware header is not present");
			LOGGER.info("Hardware header is present");

			//Again click on reboot link
			sleeper(7000);
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsHardwareTabConfPCActionValue");

			sleeper(10000);
			//click on reboot button
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RebootScreenRebootButton");
			//Validation of Toaster message 
			sleeper(2000);
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RebootToasterMessage");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootToasterMessage").contains(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.corcdevice_toast_successful")), "Second Toaster message line 1 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootToasterMessage").contains(toasterMsgConfPCLine2), "Second Toaster message line 2 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabHeader").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.hardware.title")), "Hardware header is not present");
			LOGGER.info("Hardware header is present");
			//Verify Reboot in Progress
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabConfPCActionValue").contains(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.rebootInProgress")), "RoomDetailsHardwareTabConfPCActionValue Reboot in Progress is not present");
			LOGGER.info("RoomDetailsHardwareTabConfPCActionValue Reboot in Progress is present");		

			//verify if Reboot in Progress is not clickable 
			sleeper(5000);
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsHardwareTabConfPCActionValue");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabHeader").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.hardware.title")), "Hardware header is not present");
			LOGGER.info("Hardware header is present");
		}
	}

	/**
	 * This Test will Verify the task after reboot of HP Presence Conf PC
	 * User Story 1056720: [CaaS][UI] After clicking Reboot, call template Service newly exposed API for HP Presence Conf PC device.
	 * @throws Exception
	 */
	@Test(priority = 27, groups = {"CAAS_REGRESSION"}, description = "TC1096840")
	public final void verifyConfPCRebootTask() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoTasksTab();
		LOGGER.info("Redirected to Tasks list page");
		sleeper(10000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {

			RoomsListPage.waitForElementsOfRoomsListPage("tasksTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("tasksTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.tasks")), "Tasks title text is incorrect");
			LOGGER.info("Tasks title matched");
			RoomsListPage.clickOnElementsOfRoomsListPage("firstTask");

			RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Status").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "incidents.status")), "Status is not present");
			LOGGER.info("Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("StatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.task_status_successful")), "Status value Successful is not present");
			LOGGER.info("Status value Successful is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Identifier").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.identifier")), "Identifier is not present");
			LOGGER.info("Identifier is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IdentifierValueConfPC").equals(confPCTask), "Identifier value Restart Device is not present");
			LOGGER.info("Identifier value Restart Device is present");	

			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("TaskDetailsSummaryPanelHeader");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("TaskStatus").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.task_execution")), "Task Execution Status is not present");
			LOGGER.info("Task Execution Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("ConnectionStatus").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.device_connection_status")), "Device Connection Status is not present");
			LOGGER.info("Device Connection Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("DeviceName").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.device_name")), "Device Name is not present");
			LOGGER.info("Device Name is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("TaskStatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.task_status_successful")), "Task Execution Status Value is not present");
			LOGGER.info("Task Execution Status Value is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("ConnectionStatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.connection_status_connected")), "Device Connection Status Value is not present");
			LOGGER.info("Device Connection Status Value is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("DeviceNameValue").equals(rebootDeviceName), "Device Name Value is not present");
			LOGGER.info("Device Name Value is present");
		}
	}

	/**
	 * This Test will Verify the notification after reboot of HP Presence Conf PC
	 * User Story 1056720: [CaaS][UI] After clicking Reboot, call template Service newly exposed API for HP Presence Conf PC device.
	 * @throws Exception
	 */
	@Test(priority = 28, groups = {"CAAS_REGRESSION"}, description = "TC1099332")
	public final void verifyConfPCRebootNotification() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoTasksTab();
		LOGGER.info("Redirected to Tasks list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("tasksTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("tasksTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.tasks")), "Tasks title text is incorrect");
			LOGGER.info("Tasks title matched");
			RoomsListPage.clickOnElementsOfRoomsListPage("firstTask");

			RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();
			confPCTaskID = roomsDetailsPage.getTextOfRoomsDetailsPage("TaskDetailsSummaryPanelHeader");
			LOGGER.info("Task ID "+confPCTaskID);

			gotoTasksTab();
			LOGGER.info("Redirected to Tasks list page");
			sleeper(3000);

			RoomsListPage.clickOnElementsOfRoomsListPage("notification");
			RoomsListPage.waitForElementsOfRoomsListPage("notification_header");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_header").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.notifications")), "Notifications is not present");
			LOGGER.info("Notifications is present");

			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_firstItem").equals("TODAY"), "Today is not present");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_secondItem").contains("Task Completed"), "Task Completed is not present");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_secondItem").contains("m ago"), "m ago is not present");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_secondItem").contains(confPCTaskID), "Task id is not present");
			LOGGER.info("Task ID "+confPCTaskID);
			Assert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("notification_tickmark"),"Notification tick mark is not present");

			RoomsListPage.clickByJavaScriptOnRoomsListPage("notification_secondItem");
			RoomsListPage.clickByJavaScriptOnRoomsListPage("notification_secondItem");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("TaskDetailsSummaryPanelHeader").contains(confPCTaskID), "Task id is not present");

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Status").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "incidents.status")), "Status is not present");
			LOGGER.info("Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("StatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.task_status_successful")), "Status value Successful is not present");
			LOGGER.info("Status value Successful is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Identifier").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.identifier")), "Identifier is not present");
			LOGGER.info("Identifier is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IdentifierValueConfPC").equals(confPCTask), "Identifier value Restart Device is not present");
			LOGGER.info("Identifier value Restart Device is present");		

		}
	}

	/**
	 * This Test will Verify UI in reboot functionality for HP Presence Control
	 * User Story 1055156: [CaaS][UI] After clicking Reboot, call template Service newly exposed API for HP Presence CoRC device.
	 * @throws Exception
	 */
	@Test(priority = 29, groups = {"CAAS_REGRESSION"}, description = "TC1091918")
	public final void verifyPresenceControlReboot() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");
			RoomsListPage.waitForElementsOfRoomsListPage("roomNameSearchBox");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",searchText_RoomNameOnline,"noItemsAvailable","roomNameSearchList"),true);
			LOGGER.info("Room Name Search is working");

			RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
			waitForPageLoaded();
			LOGGER.info("Redirected to Rooms details page");
			waitForPageLoaded();

			RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertEquals(roomsDetailsPage.verifyHealthStatusOnline(),true);
			sleeper(3000);
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsSummaryPanelHeader");
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsHardwareTab");
			sleeper(10000);

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabHeader").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.hardware.title")), "Hardware header is not present");
			LOGGER.info("Hardware header is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabDeviceColumn").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.global.device")), "Device Column is not present");
			LOGGER.info("Device Column is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabTypeColumn").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.type")), "Type Column is not present");
			LOGGER.info("Type Column is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabActionColumn").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.hardware.label.action")), "Action Column is not present");
			LOGGER.info("Action Column is present");

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabPresenceControlDeviceValue").equals(presenceControlDeviceValue), "RoomDetailsHardwareTabPresenceControlDeviceValue is not present");
			LOGGER.info("RoomDetailsHardwareTabPresenceControlDeviceValue is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabPresenceControlTypeValue").equals(presenceControlTypeValue), "RoomDetailsHardwareTabPresenceControlTypeValue is not present");
			LOGGER.info("RoomDetailsHardwareTabPresenceControlTypeValue is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabPresenceControlActionValue").contains(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.reboot")), "RoomDetailsHardwareTabPresenceControlActionValue is not present");
			LOGGER.info("RoomDetailsHardwareTabPresenceControlActionValue is present");
			
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsHardwareTabPresenceControlActionValue");

			//Validation of toaster message 
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootToasterMessage").contains(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.corcdevice_reqsuc_heading")), "Toaster message line 1 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootToasterMessage").contains(toasterMsgCORCLine2), "Toaster message line 2 is not present");
			//Validation of reboot screen
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootScreenHeader").equals(rebootScreenCORCHeader), "Reboot screen header is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootScreenText1").equals(rebootScreenCORCText), "RebootScreenText1 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootScreenText2").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.hardware_meeting")), "RebootScreenText2 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootScreenText3").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.hardware_action")), "RebootScreenText3 is not present");

			//click on cancel button
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RebootScreenCancelButton");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabHeader").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.hardware.title")), "Hardware header is not present");
			LOGGER.info("Hardware header is present");

			//Again click on reboot link
			sleeper(7000);
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsHardwareTabPresenceControlActionValue");

			//click on reboot button
			sleeper(13000);
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RebootScreenRebootButton");
			//Validation of Toaster message 
			sleeper(2000);
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RebootToasterMessage");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootToasterMessage").contains(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.corcdevice_toast_successful")), "Second Toaster message line 1 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RebootToasterMessage").contains(toasterMsgCORCLine2), "Toaster message line 2 is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabHeader").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.hardware.title")), "Hardware header is not present");
			LOGGER.info("Hardware header is present");
			//Verify Reboot in Progress
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabPresenceControlActionValue").contains(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.rebootInProgress")), "RoomDetailsHardwareTabPresenceControlActionValue Reboot in Progress is not present");
			LOGGER.info("RoomDetailsHardwareTabPresenceControlActionValue Reboot in Progress is present");	
			//verify if Reboot in Progress is not clickable 
			sleeper(5000);
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("RoomDetailsHardwareTabPresenceControlActionValue");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("RoomDetailsHardwareTabHeader").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "assets.details.hardware.title")), "Hardware header is not present");
			LOGGER.info("Hardware header is present");
		}
	}

	/**
	 * This Test will Verify the task after reboot of HP Presence Control
	 * User Story 1055156: [CaaS][UI] After clicking Reboot, call template Service newly exposed API for HP Presence CoRC device.
	 * @throws Exception
	 */
	@Test(priority = 30, groups = {"CAAS_REGRESSION"}, description = "TC1096842")
	public final void verifyPresenceControlRebootTask() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoTasksTab();
		LOGGER.info("Redirected to Tasks list page");
		sleeper(10000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("tasksTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("tasksTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.tasks")), "Tasks title text is incorrect");
			LOGGER.info("Tasks title matched");
			RoomsListPage.clickOnElementsOfRoomsListPage("firstTask");

			RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Status").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "incidents.status")), "Status is not present");
			LOGGER.info("Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("StatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.task_status_successful")), "Status value Successful is not present");
			LOGGER.info("Status value Successful is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Identifier").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.identifier")), "Identifier is not present");
			LOGGER.info("Identifier is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IdentifierValueCORC").equals(corcTask), "Identifier value Reboot CoRC Device is not present");
			LOGGER.info("Identifier value Reboot CoRC Device is present");		

			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("TaskDetailsSummaryPanelHeader");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("TaskStatus").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.task_execution")), "Task Execution Status is not present");
			LOGGER.info("Task Execution Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("ConnectionStatus").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.device_connection_status")), "Device Connection Status is not present");
			LOGGER.info("Device Connection Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("DeviceName").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.device_name")), "Device Name is not present");
			LOGGER.info("Device Name is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("TaskStatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.task_status_successful")), "Task Execution Status Value is not present");
			LOGGER.info("Task Execution Status Value is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("ConnectionStatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.connection_status_connected")), "Device Name Value is not present");
			LOGGER.info("Device Connection Status Value is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("DeviceNameValue").equals(rebootDeviceName), "Device Name Value is not present");
			LOGGER.info("Device Name Value is present");

		}
	}

	/**
	 * This Test will Verify the notification after reboot of HP Presence Control
	 * User Story 1055156: [CaaS][UI] After clicking Reboot, call template Service newly exposed API for HP Presence CoRC device.
	 * @throws Exception
	 */
	@Test(priority = 31, groups = {"CAAS_REGRESSION"}, description = "TC1099333")
	public final void verifyPresenceControlRebootNotification() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoTasksTab();
		LOGGER.info("Redirected to Tasks list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("tasksTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("tasksTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.tasks")), "Tasks title text is incorrect");
			LOGGER.info("Tasks title matched");
			RoomsListPage.clickOnElementsOfRoomsListPage("firstTask");

			RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();
			corcTaskID = roomsDetailsPage.getTextOfRoomsDetailsPage("TaskDetailsSummaryPanelHeader");
			LOGGER.info("Task ID "+corcTaskID);

			gotoTasksTab();
			LOGGER.info("Redirected to Tasks list page");
			sleeper(3000);

			RoomsListPage.clickOnElementsOfRoomsListPage("notification");
			RoomsListPage.waitForElementsOfRoomsListPage("notification_header");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_header").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.notifications")), "Notifications is not present");
			LOGGER.info("Notifications is present");

			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_firstItem").equals("TODAY"), "Today is not present");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_secondItem").contains("Task Completed"), "Task Completed is not present");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_secondItem").contains("m ago"), "m ago is not present");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_secondItem").contains(corcTaskID), "Task id is not present");
			LOGGER.info("Task ID "+corcTaskID);
			Assert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("notification_tickmark"),"Notification tick mark is not present");

			RoomsListPage.clickByJavaScriptOnRoomsListPage("notification_secondItem");
			RoomsListPage.clickByJavaScriptOnRoomsListPage("notification_secondItem");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("TaskDetailsSummaryPanelHeader").contains(corcTaskID), "Task id is not present");

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Status").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "incidents.status")), "Status is not present");
			LOGGER.info("Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("StatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.task_status_successful")), "Status value Successful is not present");
			LOGGER.info("Status value Successful is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Identifier").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.identifier")), "Identifier is not present");
			LOGGER.info("Identifier is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IdentifierValueCORC").equals(corcTask), "Identifier value Reboot CoRC Device is not present");
			LOGGER.info("Identifier value Reboot CoRC Device is present");		

		}
	}


	/**
	 * This method used to verify Room Name MisMatch Notification and Incident creation after adding a device to new room or link the device to existing room
	 * Feature 1040093: Streamline Room Name across TP Portal, SpaceX device & UC Room name
	 * User Story 1056876: [CaaS][Backend] Generate Bell icon notification for the incident raised.
	 * User Story 1056871: [CaaS][Backend] Generate Incident for the discrepancy found
	 * Test Case 1093056: [CaaS][Incidents]>>Verify UC Room Name MisMatch with Either of Techpulse Room Name or Device Room Name and Device is Online State
	 * Test Case 1092952: [CaaS][Incidents][Notifications]>>Verify UC Room Name MisMatch with Techpulse Room Name and Device Room Name and Device is in Online State
	 * @throws Exception
	 */
	@Test(priority = 33,groups = { "CAAS_REGRESSION"}, description = "TC1093056,TC1092952")
	public final void verifyRoomNameMisMatchNotificationByAddingDeviceToNewRoom() throws Exception {
		String newRoomName = "Auto_New_Room";
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);		

		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();
		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();


		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			//***** Add New Room and link the Device ******// 
			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.general")), "Title on Add room general screen is incorrect");
			LOGGER.info("Add Room General Screen matched");

			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", newRoomName);
			RoomsListPage.waitForElementsOfRoomsListPage("roomSizeSmall");
			RoomsListPage.clickByJavaScriptOnRoomsListPage("roomSizeSmall");	
			LOGGER.info("Inputs entered in Add Room General Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.location")), "Title on Add room location screen is incorrect");
			LOGGER.info("Add Room Location Screen matched");

			RoomsListPage.enterTextForRoomsListPage("floorTextBox", floor);
			RoomsListPage.enterTextForRoomsListPage("buildingTextBox", building);
			RoomsListPage.enterTextForRoomsListPage("locationTextBox", location);
			LOGGER.info("Inputs entered in Add Room Location Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.link_conf")), "Title on Add room Link Conferencing Device screen is incorrect");
			LOGGER.info("Add Room Link Conferencing Device Screen matched");

			roomsDetailsPage.enterTextForRoomsDetailsPage("deviceSearch", rebootDeviceName);
			sleeper(2000);
			RoomsListPage.waitForElementsOfRoomsListPage("deviceCheckBox");
			RoomsListPage.clickOnElementsOfRoomsListPage("deviceCheckBox");
			LOGGER.info("Inputs entered in Add Room Link Conferencing Device Screen");

			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			Assert.assertTrue(RoomsListPage.matchTextOfRoomsListPage("addRoomScreenTitle", getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.summary")), "Title on Add room Summary screen is incorrect");
			LOGGER.info("Add Room Summary Screen matched");

			RoomsListPage.clickOnElementsOfRoomsListPage("summaryAddButton");
			LOGGER.info("Add button in summary page is clicked");
			sleeper(5000);
			RoomsListPage.waitForElementsOfRoomsListPage("addRoomConfirmation");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("addRoomConfirmation").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.addroom_title")), "Add Room Confirmation text is incorrect");
			LOGGER.info("Room added successfuly");	
			sleeper(3000);
			refreshPage();
			RoomsListPage.waitForElementsOfRoomsListPage("listkey");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",newRoomName,"noItemsAvailable","listkey"),true);
			LOGGER.info("Added room is found in the rooms list page");
			sleeper(3000);
			roomsDetailsPage.enterTextForRoomsDetailsPage("roomNameSearch", newRoomName);
			sleeper(2000);
			RoomsListPage.waitForElementsOfRoomsListPage("AutoRoomNameLink");
			RoomsListPage.clickOnElementsOfRoomsListPage("AutoRoomNameLink");
			sleeper(2000);

			////****** get the Room name from Title card****///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNameTitle");
			String roomNameOnTitleCard=roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameTitle");
			LOGGER.info("Room Name on TitleCard : "+roomNameOnTitleCard);

			////****** get the RoomId from current URL****///
			String roomId = RoomsListPage.getRoomID();
			String body="{\"roomIdList\": [\""+roomId+"\"]}";

			////****** get the Room Name from Settings Tab****///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNamesettingsPageCollapseIcon");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("RoomNamesettingsPageCollapseIcon");
			sleeper(3000);
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("SettingsTab");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("SettingsTab");
			sleeper(3000);
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNameOnSettingsTab");
			String roomNameOnSettingsTab=roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameOnSettingsTab");
			LOGGER.info("Room Name on Settings tab : "+roomNameOnSettingsTab);


			///*** Verifying the Notification Generation ***///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("BellIcon");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("BellIcon");
			LOGGER.info("Clicked on Bell Icon");
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("Notification_header");
			roomsDetailsPage.matchTextOfRoomsDetailsPage("Notification_header","Notifications");
			LOGGER.info("Notifications is present");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_firstItem").equals("TODAY"), "Today is not present");
			Assert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("notification_warningIcon"),"Notification Warning Icon is not present");

			if(roomNameOnTitleCard.equals(roomNameOnSettingsTab)) {
				String expectedMsg="Room Name "+'"'+roomNameOnSettingsTab+'"'+" on HP Presense Manager does match with " +'"'+roomNameOnSettingsTab+'"'+" on HP Presense Conf PC.";
				LOGGER.info("Expected Fixed Notification- Message : "+expectedMsg);
				LOGGER.info("Actual Fixed Notification Message: "+roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameMismatchFixedNotification"));
				roomsDetailsPage.matchTextOfRoomsDetailsPage("RoomNameMismatchFixedNotification",expectedMsg);
			}else {
				String expectedMsg="Room Name "+'"'+roomNameOnTitleCard+'"'+" on HP Presense Manager does not match with " +'"'+roomNameOnSettingsTab+'"'+" on HP Presense Conf PC. Please rename them manually. Read More";
				LOGGER.info("Expected MisMatch Notification- Message : "+expectedMsg);
				LOGGER.info("Actual MisMatch Notification Message: "+roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameMismatchNotification"));
				roomsDetailsPage.matchTextOfRoomsDetailsPage("RoomNameMismatchNotification",expectedMsg);
			}
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("BellIcon");


			////****** Verifying the Incidents on Room Details page****///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("IncidentsTab");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("IncidentsTab");
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("GotoIncidentList");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("GotoIncidentList");

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IncidentType").equals("Room Assistant"), "Room Assistant type is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IncidentSubTypeName").equals("Room Name Mismatch"), "Room Assistant type is not present");

			////******Deleting the above created Room****///
			Assert.assertTrue(RoomsListPage.deleteRoom(environment,body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL")), "Delete Room failed");

		}
	}

	/**
	 * This method used to verify Room Name MisMatch Notification and Incident creation after adding a device to new room or link the device to existing room
	 * Feature 1040093: Streamline Room Name across TP Portal, SpaceX device & UC Room name
	 * User Story 1056876: [CaaS][Backend] Generate Bell icon notification for the incident raised.
	 * User Story 1056871: [CaaS][Backend] Generate Incident for the discrepancy found
	 * Test Case 1093026: [CaaS][Incidents][Notifications]>>Verify When Room Name is corrected in all the Places Room name MisMatch fixed notification should shows up on Bell Icon
	 * Test Case 1092952: [CaaS][Incidents][Notifications]>>Verify UC Room Name MisMatch with Techpulse Room Name and Device Room Name and Device is in Online State
	 * @throws Exception
	 */
	@Test(priority = 34,groups = { "CAAS_REGRESSION"}, description = "TC1093026,TC1092952")
	public final void verifyRoomNameMisMatchNotificationBylinkingDeviceToExistingRoom() throws Exception {
		String existingRoomName = "Auto_Room2_Test";
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);		
		RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("roomsTitleOnBreadcrumbs").equals(roomsDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");

			//***** Add New Room and link the Device ******// 
			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", existingRoomName);
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			RoomsListPage.enterTextForRoomsListPage("floorTextBox", floor);
			RoomsListPage.enterTextForRoomsListPage("buildingTextBox", building);
			RoomsListPage.enterTextForRoomsListPage("locationTextBox", location);
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			RoomsListPage.clickOnElementsOfRoomsListPage("summaryAddButton");
			sleeper(5000);
			RoomsListPage.waitForElementsOfRoomsListPage("addRoomConfirmation");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("addRoomConfirmation").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.addroom_title")), "Add Room Confirmation text is incorrect");
			LOGGER.info("Room added successfuly");	
			sleeper(2000);
			//***** Add New Room and link the Device ******// 
			roomsDetailsPage.linkDeviceToExistingRoom("roomNameSearch",existingRoomName,"AutoRoomNameLink",rebootDeviceName,"linkDeviceBtn","deviceSearch","selectDevice","navigationItemDiv","linkSubmitButton");

			refreshPage();
			sleeper(3000);

			///****** get the Room name from Title card****///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNameTitle");
			String roomNameOnTitleCard=roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameTitle");
			LOGGER.info("Room Name on TitleCard : "+roomNameOnTitleCard);

			////****** get the RoomId from current URL****///
			String roomId = RoomsListPage.getRoomID();
			String body="{\"roomIdList\": [\""+roomId+"\"]}";


			////****** get the Room Name from Settings Tab****///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNamesettingsPageCollapseIcon");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("RoomNamesettingsPageCollapseIcon");
			sleeper(3000);

			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("SettingsTab");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("SettingsTab");
			sleeper(3000);
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNameOnSettingsTab");
			String roomNameOnSettingsTab=roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameOnSettingsTab");
			LOGGER.info("Room Name on Settings tab : "+roomNameOnSettingsTab);

			///*** Verifying the Notification Generation ***///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("BellIcon");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("BellIcon");
			LOGGER.info("Clicked on Bell Icon");
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("Notification_header");
			roomsDetailsPage.matchTextOfRoomsDetailsPage("Notification_header","Notifications");
			LOGGER.info("Notifications is present");
			sleeper(3000);
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("notification_firstItem").equals("TODAY"), "Today is not present");
			Assert.assertTrue(RoomsListPage.verifyElementsOfRoomsListPage("notification_warningIcon"),"Notification Warning Icon is not present");

			if(roomNameOnTitleCard.equals(roomNameOnSettingsTab)) {
				String expectedMsg="Room Name "+'"'+roomNameOnSettingsTab+'"'+" on HP Presense Manager does match with " +'"'+roomNameOnSettingsTab+'"'+" on HP Presense Conf PC.";
				LOGGER.info("Expected Fixed Notification- Message : "+expectedMsg);
				LOGGER.info("Actual Fixed Notification Message: "+roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameMismatchFixedNotification"));
				roomsDetailsPage.matchTextOfRoomsDetailsPage("RoomNameMismatchFixedNotification",expectedMsg);
			}else {
				String expectedMsg="Room Name "+'"'+roomNameOnTitleCard+'"'+" on HP Presense Manager does not match with " +'"'+roomNameOnSettingsTab+'"'+" on HP Presense Conf PC. Please rename them manually. Read More";
				LOGGER.info("Expected MisMatch Notification- Message : "+expectedMsg);
				LOGGER.info("Actual MisMatch Notification Message: "+roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameMismatchNotification"));
				roomsDetailsPage.matchTextOfRoomsDetailsPage("RoomNameMismatchNotification",expectedMsg);
			}

			///*** Verifying the Notification Generation ***///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("BellIcon");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("BellIcon");
			LOGGER.info("Closed Bell Icon");

			/////*****Update Room Name *****/////
			sleeper(4000);
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomName_header");
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("UpdateRoomNameBtn");
			sleeper(2000);
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNameTextBox");
			roomsDetailsPage.enterTextForRoomsDetailsPage("RoomNameTextBox",updatedRoomName);
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("SaveBtn_RoomName");
			sleeper(5000);
			LOGGER.info("Inputs entered in Update Room Name window");
			refreshPage();

			////****** get the Room name from Title card****///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNameTitle");
			sleeper(2000);
			String roomNameOnTitleCard2=roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameTitle");
			LOGGER.info("Update Case >> Room Name on TitleCard  : "+roomNameOnTitleCard2);


			////****** get the Room Name from Settings Tab****///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNamesettingsPageCollapseIcon");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("RoomNamesettingsPageCollapseIcon");
			sleeper(3000);

			////****** get the Room Name from Settings Tab****///

			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("RoomNameOnSettingsTab");
			String roomNameOnSettingsTab2=roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameOnSettingsTab");
			LOGGER.info("Update Case >> Room Name on Settings tab : "+roomNameOnSettingsTab2);


			///*** Verifying the Notification Generation zAfter Room Name Update***///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("BellIcon");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("BellIcon");
			LOGGER.info("Clicked on Bell Icon");

			if(roomNameOnTitleCard2.equals(roomNameOnSettingsTab2)) {
				String expectedMsg="Room Name "+'"'+roomNameOnSettingsTab2+'"'+" on HP Presense Manager does match with " +'"'+roomNameOnSettingsTab2+'"'+" on HP Presense Conf PC.";
				LOGGER.info("Expected Fixed Notification- Message : "+expectedMsg);
				LOGGER.info("Actual Fixed Notification Message: "+roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameMismatchFixedNotification"));
				roomsDetailsPage.matchTextOfRoomsDetailsPage("RoomNameMismatchFixedNotification",expectedMsg);
			}else {
				String expectedMsg="Room Name "+'"'+roomNameOnTitleCard2+'"'+" on HP Presense Manager does not match with " +'"'+roomNameOnSettingsTab2+'"'+" on HP Presense Conf PC. Please rename them manually. Read More";
				LOGGER.info("Expected MisMatch Notification- Message : "+expectedMsg);
				LOGGER.info("Actual MisMatch Notification Message: "+roomsDetailsPage.getTextOfRoomsDetailsPage("RoomNameMismatchNotification"));
				roomsDetailsPage.matchTextOfRoomsDetailsPage("RoomNameMismatchNotification",expectedMsg);
			}

			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("BellIcon");

			////****** Verifying the Incidents on Room Details page****///
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("IncidentsTab");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("IncidentsTab");
			roomsDetailsPage.waitForElementsOfRoomsDetailsPage("GotoIncidentList");
			roomsDetailsPage.clickByJavaScriptOnRoomsDetailsPage("GotoIncidentList");

			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IncidentType").equals("Room Assistant"), "Room Assistant type is not present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IncidentSubTypeName").equals("Room Name Mismatch"), "Room Assistant type is not present");

			////******Deleting the above created Room****///
			Assert.assertTrue(RoomsListPage.deleteRoom(environment,body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL")), "Delete Room failed");

		}

	}


	/**
	 * This method will delete room from rooms list page
	 * User Story 858258: [CaaS][UI] Single delete room in add room
	 * @throws Exception
	 **/
	@Test(priority = 32, groups = {"CAAS_REGRESSION"}, description = "TC810628")
	public final void verifyDeleteRoom() throws Exception {
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		String deleteRoomName = "Auto_Delete_Room";
		environment = System.getProperty("environment");
		gotoRoomsTab();
		LOGGER.info("Redirected to Rooms list page");
		sleeper(3000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
			RoomsListPage.waitForElementsOfRoomsListPage("roomsTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("roomsTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.rooms")), "Rooms title text is incorrect");
			LOGGER.info("Rooms title matched");
			sleeper(3000);

			//***** Add New Room and link the Device ******// 
			RoomsListPage.waitForElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.clickOnElementsOfRoomsListPage("addRoomButton");
			RoomsListPage.enterTextForRoomsListPage("roomNameTextBox", deleteRoomName);
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonGeneralScreen");
			RoomsListPage.enterTextForRoomsListPage("floorTextBox", floor);
			RoomsListPage.enterTextForRoomsListPage("buildingTextBox", building);
			RoomsListPage.enterTextForRoomsListPage("locationTextBox", location);
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			RoomsListPage.clickOnElementsOfRoomsListPage("nextButtonLocationScreen");
			RoomsListPage.clickOnElementsOfRoomsListPage("summaryAddButton");
			sleeper(5000);
			RoomsListPage.waitForElementsOfRoomsListPage("addRoomConfirmation");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("addRoomConfirmation").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.addroom_title")), "Add Room Confirmation text is incorrect");
			LOGGER.info("Room added successfuly");	
			sleeper(3000);
			RoomsListPage.waitForElementsOfRoomsListPage("roomNameSearchBox");
			Assert.assertEquals(RoomsListPage.verifySearchValueOfColumnOnRoomList(LanguageCode,"roomNameSearchBox",deleteRoomName,"noItemsAvailable","listkey"),true);
			LOGGER.info("Room Name Search is working");
			RoomsListPage.clickByJavaScriptOnRoomsListPage("roomList");
			sleeper(2000);
			waitForPageLoaded();
			LOGGER.info("Redirected to Rooms details page");
			waitForPageLoaded();
			sleeper(3000);
			String roomId = RoomsListPage.getRoomID();
			String body="{\"roomIdList\": [\""+roomId+"\"]}";
			Assert.assertTrue(RoomsListPage.deleteRoom(environment,body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL")), "Delete Room failed");
		} 
	}
}	