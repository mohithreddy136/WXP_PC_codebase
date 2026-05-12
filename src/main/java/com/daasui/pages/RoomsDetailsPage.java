package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class RoomsDetailsPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader roomsDetailsPagePropertiesReader = new ObjectReader();
	private ObjectReader environmentPropertiesReader = new ObjectReader();
	private Properties roomsDetailsPageProperties;
	private Properties environmentProperties;
	private static Logger LOGGER = LogManager.getLogger(RoomsDetailsPage.class);
	public List<String> listOfValuesSaved = new ArrayList<String>();
	public String serialNumberCustom = null;
	public static String uiVersion = System.getProperty("uiVersion");
	private RoomsDetailsPage instance;

	public RoomsDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (RoomsDetailsPage.class) {
				if (instance == null) {
					instance = new RoomsDetailsPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public RoomsDetailsPage(WebDriver driver) throws IOException {

		environmentProperties = environmentPropertiesReader.getObjectRepository("Environment");
		roomsDetailsPageProperties = roomsDetailsPagePropertiesReader.getObjectRepository("RoomsDetailsPage");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = roomsDetailsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfRoomsDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(roomsDetailsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfRoomsDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(roomsDetailsPageProperties.getProperty(key));
	}

	public final boolean matchTextOfRoomsDetailsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(roomsDetailsPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfRoomsDetailsPage(String key) throws Exception {
		return verifyElementIsEnable(roomsDetailsPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfRoomsDetailsPage(String key) throws Exception {
		return verifyElementIsSelected(roomsDetailsPageProperties.getProperty(key));
	}

	public final String getTextOfRoomsDetailsPage(String key) throws Exception {
		return getTextBy(roomsDetailsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfRoomsDetailsPageForinvisibile(String key) throws Exception {
		return verifyElementIsinvisibile(roomsDetailsPageProperties.getProperty(key));
	}

	public final String getAttributesOfRoomDetailsPage(String key, String value) throws Exception {
		return getAttribute(roomsDetailsPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfRoomsDetailsPage(String key) throws Exception {
		click(roomsDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to click on an element by javascript
	 * 
	 * @param key - Locator of element
	 */
	public final void clickByJavaScriptOnRoomsDetailsPage(String key) {
		try {
			clickByJavaScript(roomsDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnRoomDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on incident page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void scrollOnRoomsDetailsPage(String key) throws Exception {
		scrollTillView(roomsDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get a list of elements of room details page
	 * 
	 * @param key - Locator of list
	 * @return - list of webElements
	 */
	public final List<WebElement> getElementsTillAllElementsVisibleofRoomsDetailsPage(String key) {
		try {
			return getElementsTillAllElementsVisible(roomsDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofRoomsDetailspage " + e.getMessage()));
			return null;
		}
	}

	public void verifyMandatoryFieldValue(String value,String mandatoryField) throws Exception
	{ 
		switch(mandatoryField)
		{
		case "Size":

			Assert.assertTrue(value.equals("Huddle") ||value.equals("Small") || value.equals("Medium")||value.equals("Large"),"Room Details Summary Panel does not have mandatory Field Size Value ");
			System.out.println("Room has Size : "+ value);
			break;

		case "Capacity":

			Assert.assertTrue(value.equals("2 - 4 People") ||value.equals("4 - 6 people") || value.equals("6 - 12 people")||value.equals("12+ People"),"Room Details Summary Panel does not have mandatory Field Capacity Value");
			System.out.println("Room has capacity of : "+ value);
			break;

		case "Company":

			Assert.assertTrue(!(value.equals("-")),"Room Details Summary Panel does not have mandatory Field Company Value");
			System.out.println("Room has company name : "+ value);
			break;

		case "Location":

			Assert.assertTrue(!(value.equals("-")),"Room Details Summary Panel does not have mandatory Field Location Value");
			System.out.println("Room has Location name : "+ value);
			break;	

		}
	}

	/**
	 * This is a method to verify if the health status in room details page
	 * 
	 * @param - NA
	 * @return - NA
	 */
	public void verifyHealthStatus() throws Exception
	{
		if((getTextOfRoomsDetailsPage("RoomDetailsComputeDeviceValue")).equals("-"))
		{
			LOGGER.info("Device is not Linked : Compute device value is "+ getTextOfRoomsDetailsPage("RoomDetailsComputeDeviceValue")+" . Hence the Health field is not present");
		}
		else
		{	
			if(getElementsTillAllElementsVisibleofRoomsDetailsPage("RoomDetailsSummaryPanelColumns").size()>3)
			{
				Assert.assertTrue(getTextOfRoomsDetailsPage("RoomDetailsHealthStatus").equals("Health Status"), "health Status title is incorrect");
				LOGGER.info("Health Status title is present");
				System.out.println("Health Status is : "+ getTextOfRoomsDetailsPage("RoomDetailsHealthStatusValue"));
			}
			else
			{
				clickOnElementsOfRoomsDetailsPage("RoomAssistantTab");
				System.out.println(getTextOfRoomsDetailsPage("RoomSubTypesNotAvailable") + "in Room Assistant Tab. Hence the Health Status field is not present");
			}
		}
	}	

	/**
	 * This is a method to verify if the health status is Online or not in room details page
	 * 
	 * @param - NA
	 * @return - boolean value true or false
	 */
	public boolean verifyHealthStatusOnline() throws Exception
	{
		if((getTextOfRoomsDetailsPage("RoomDetailsHealthStatusValue")).equals("Online"))
		{
			LOGGER.info("Health Status is : "+ getTextOfRoomsDetailsPage("RoomDetailsHealthStatusValue"));
			return true;
		}
		else 
			return false;
	}


	/**
	 * This is a method used to link the device to existing room
	 * 
	 * @param param1 - Room Name to search
	 * @param param2 - The text to be entered
	 * @param param3 - Locator of the Room Name element
	 * @param param4 - The device name text to be entered
	 * @param param5 - Locator of the Room Name element
	 * @param param6 - Locator of the Room Name element
	 * @param param7 - Locator of the Room Name element
	 * @param param8 - Locator of the Room Name element
	 * @param param9 - Locator of the Room Name element
	 */
	public void linkDeviceToExistingRoom(String roomNameSearch, String txtRoomName, String roomNameLink, String txtDeviceName, String linkDeviceBtn, String deviceSearch, String selectDevice, String navigationItemDiv, String linkButton) throws InterruptedException {

		try {
			enterText(roomsDetailsPageProperties.getProperty(roomNameSearch),txtRoomName);
			sleeper(2000);
			clickOnElementsOfRoomsDetailsPage(roomNameLink);
			sleeper(2000);
			clickOnElementsOfRoomsDetailsPage(linkDeviceBtn);
			enterText(roomsDetailsPageProperties.getProperty(deviceSearch),txtDeviceName);
			sleeper(2000);
			//scrollOnRoomDetailsPage(navigationItemDiv);
			waitForElementsOfRoomsDetailsPage(selectDevice);
			clickByJavaScriptOnRoomsDetailsPage(selectDevice);
			sleeper(2000);
			clickByJavaScriptOnRoomsDetailsPage(linkButton);
			LOGGER.info("Device added to the Room");
			sleeper(5000);
			refreshPage();
			waitForElementsOfRoomsDetailsPage("deviceNameOnSummary");
			LOGGER.info("Device Name: "+ getTextOfRoomsDetailsPage("deviceNameOnSummary"));
			Assert.assertTrue(getTextOfRoomsDetailsPage("deviceNameOnSummary").equals(txtDeviceName), "Device Name text is Incorrect");
			LOGGER.info("Device Name is showing as expected");	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This is a method to enter text on an element
	 * 
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextForRoomsDetailsPage(String key, String Text) {
		try {
			enterText(roomsDetailsPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementIsEnableOfRoomsDetailPage " + e.getMessage()));
		}
	}

}