package com.basesource.action;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.basesource.utils.LaunchDarkly;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.PartnerPage;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.WEXCustomerUserListPage;
import com.daasui.pages.WEXDashboardPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonMethod extends PreDefinedActions {

	private ObjectReader commonPagePropertiesReader = new ObjectReader();
	private ObjectReader environmentPropertiesReader = new ObjectReader();
	protected JSONParser parser = new JSONParser();
	private Properties environmentProperties;
	private Properties selectedLanguageProperties;
	private Properties dashboardPageProperties;
	private CommonMethod instance;
	private static ObjectReader envDataPropertiesReader = new ObjectReader();
	private static Properties envDataProperties;
	private static String fileName;
	public static String uiVersion = System.getProperty("uiVersion");
	public static String environment = System.getProperty("environment");
	public static String entityId = null;

	public CommonMethod getInstance() throws IOException {
		if (instance == null) {
			synchronized (CommonMethod.class) {
				if (instance == null) {
					instance = new CommonMethod();
				}
			}
		}
		return instance;

	}

	public String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = commonPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This is the method to generate phone number
	 * 
	 */
	public final long generatePhoneNumber() {
		long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		return number;
	}
	
	/**
	 * This is the method to fetch PAT token from Credential folder.
	 * 
	 */
	public final static String getPAT(String key) throws Exception {
		envDataProperties = envDataPropertiesReader.getCredentials(environment);
		return envDataProperties.getProperty(key);
	}
	
	

	/**
	 * This is the method to generate date
	 * 
	 */
	public final String generateDate() {
		Date dNow = new Date();
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String datetime = formatTime.format(dNow);
		return datetime;
	}

	/**
	 * This is the method to generate date in mm/dd/yyyy format
	 * 
	 */
	public final String generateCustomDate() {
		Date dNow = new Date();
		SimpleDateFormat formatTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String datetime = formatTime.format(dNow);
		return datetime;
	}

	/**
	 * This method is used to get the entity ID based on the environment. The
	 * environment is a system property and can be one of the following:
	 * "US-PRODUCTION", "EU-PRODUCTION", "US-PRODUCTION-ADMINX",
	 * "EU-PRODUCTION-ADMINX".
	 *
	 * @return entityId - The entity ID corresponding to the environment. If the
	 *         environment does not match any of the predefined ones, the method
	 *         returns null.
	 */
	public static String getEntityId() {
		if (environment.equalsIgnoreCase("US-PRODUCTION")) {
			entityId = "US-PRODUCTION";
		} else if (environment.equalsIgnoreCase("EU-PRODUCTION")) {
			entityId = "EU-PRODUCTION";
		} else if (environment.equalsIgnoreCase("US-PROD-WEP")) {
			entityId = "US-PROD-WEP";
		} else if (environment.equalsIgnoreCase("EU-PROD-WEP")) {
			entityId = "EU-PROD-WEP";
		}

		return entityId;

	}

	/**
	 * This is the method to get a list of all the elements and compare it with an
	 * already existing list
	 * 
	 */
	public final boolean compareTwoList(String key, ArrayList<String> array) throws Exception {
		ArrayList<String> menuTabs = new ArrayList<String>();
		List<WebElement> menuList = getElementsTillAllElementsPresent(key);
		for (WebElement listItem : menuList) {
			String value = listItem.getText();
			menuTabs.add(value);
		}

		if (menuTabs.equals(array)) {
			return true;
		}

		return false;
	}

	/**
	 * This is the method to verify the search functionality of a search box. It
	 * will get a list of all the options available after the text is entered and
	 * check if the option matching with the entered text is present. If the option
	 * is not present, the message "No items available" will be displayed This is
	 * the method to verify the search functionality of a search box. It will get a
	 * list of all the options available after the text is entered and check if the
	 * option matching with the entered text is present. If the option is not
	 * present, the message "No items available" will be displayed
	 * 
	 * @param LanguageCode: This is used as code for multiple languages.
	 * @param textKey:      Locator of text box in which text to be entered.
	 * @param Text:         Text which is to be searched.
	 * @param emptyTextKey: Locator for empty text message.
	 * @param listKey:      Locator for list of elements.
	 * @return : Return boolean value either it may be true or false.
	 * @throws Exception
	 */
	public final boolean verifySearchFunctionality(String LanguageCode, String textKey, String Text,
			String emptyTextKey, String listKey) throws Exception {
		enterText(textKey, Text);
		boolean flag = false;
		sleeper(3000);
		String empty_text = null;
		if (verifyElementIsVisibleDynamic(emptyTextKey, 5)) {
			empty_text = getTextBy(emptyTextKey);
			if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
				flag = true;
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(listKey);
			for (WebElement webElement : elements) {
				if (webElement.getText().toLowerCase().contains(Text.toLowerCase())) {
					flag = true;
				} else {
					return flag;
				}
			}
		}
		sleeper(4000);
		clearTextRefresh(textKey);
		waitForPageLoaded();
		sleeper(4000);
		return flag;
	}

	/**
	 * This is the method to verify the search functionality of a search box. It
	 * will get a list of all the options available after the text is entered and
	 * check if the option matching with the entered text is present. If the option
	 * is not present it return false This is the method to verify the search
	 * functionality of a search box. It will get a list of all the options
	 * available after the text is entered and check if the option matching with the
	 * entered text is present. If the option is not present it return false
	 */
	public final boolean verifySearchFunctionalityofColumn(String LanguageCode, String textKey, String Text,
			String emptyTextKey, String listKey) throws Exception {
		enterText(textKey, Text);
		sleeper(3000);
		if (verifyElementIsPresent(emptyTextKey)) {
			if (getTextBy(emptyTextKey).equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
				return false;
			} else if (getTextBy(emptyTextKey).equals(getTextLanguage(LanguageCode, "daas_ui", "global.no_results"))) {
				return true;
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(listKey);
			for (WebElement webElement : elements) {
				if (webElement.getText().toLowerCase().contains(Text.toLowerCase())) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * This is the method to verify search functionality of a search box present
	 * inside a popup. It will get a list of all the options after the text is
	 * entered and check if the option matching with the entered text is present. If
	 * the option is not present then the message "No results matched" will be
	 * displayed This is the method to verify search functionality of a search box
	 * present inside a popup. It will get a list of all the options after the text
	 * is entered and check if the option matching with the entered text is present.
	 * If the option is not present then the message "No results matched" will be
	 * displayed
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param textKey:      Key of text.
	 * @param Text:         Text which will be searched.
	 * @param emptyTextKey: Key for No data message.
	 * @param listKey:      Key for list of elements.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifySearchFunctionalityInsidePopup(String LanguageCode, String textKey, String Text,
			String emptyTextKey, String listKey) throws Exception {
		enterText(textKey, Text);
		int flag = 0;
		sleeper(3000);
		String empty_text = null;
		if (verifyElementIsVisible(emptyTextKey)) {
			empty_text = getTextBy(emptyTextKey);
			if (empty_text.equals(
					getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").replaceAll("\\{([^}]+)\\}", Text))) {
				flag = 1;
			}
			/*
			 * String[] text = getTextLanguage(LanguageCode, "daas_ui",
			 * "dropdown.noResults").split("%"); if
			 * (empty_text.contains(text[0].concat(Text))) { flag = 1; }
			 */

		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(listKey);

			for (WebElement webElement : elements) {
				flag = 0;
				if (webElement.getText().toLowerCase().contains(Text.toLowerCase())) {
					flag = 1;
				} else {
					return false;

				}
			}
		}

		clearText(textKey);

		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This is a method to select last week range dates from calendar filter
	 * 
	 * @param date               - current date
	 * @param calenderKey        - locator for calendar filter
	 * @param monthKeyLeft       - locator to go to months on left side
	 * @param monthKeyRight      - locator to go to months on right side
	 * @param leftArrowKey       - locator for left arrow key on calendar
	 * @param daysOnLeftSideKey  - locator for days on left side
	 * @param daysOnRightSideKey - locator for days on right side
	 */
	public final void selectDateFromCalender(String date, String calenderKey, String monthKeyLeft, String monthKeyRight,
			String leftArrowKey, String daysOnLeftSideKey, String daysOnRightSideKey) {
		try {
			String[] strArray = date.split(" ");
			String day = strArray[1].replaceAll(",", "");
			sleeper(1000);
			while (!(getTextBy(monthKeyLeft).contains(strArray[0] + " " + strArray[2])
					|| getTextBy(monthKeyRight).contains(strArray[0] + " " + strArray[2]))) {
				click(leftArrowKey);
				sleeper(6000); // this sleeper is needed to allow the calendar to go to the previous set of
								// months
			}
			List<WebElement> datesOnLeftSide = getElementsTillAllElementsVisible(daysOnLeftSideKey);
			int countOfDaysOnLeftSide = datesOnLeftSide.size();

			List<WebElement> datesOnRightSide = getElementsTillAllElementsVisible(daysOnRightSideKey);
			int countOfDaysOnRightSide = datesOnRightSide.size();

			if (getTextBy(monthKeyRight).contains(strArray[0] + " " + strArray[2])) {
				for (int daysCounter = 0; daysCounter < countOfDaysOnRightSide; daysCounter++) {
					String text = datesOnRightSide.get(daysCounter).getText();
					if (text.equalsIgnoreCase(day)) {
						datesOnRightSide.get(daysCounter).click();
						break;
					}
				}
			} else {
				for (int daysCounter = 0; daysCounter < countOfDaysOnLeftSide; daysCounter++) {
					String text = datesOnLeftSide.get(daysCounter).getText();
					if (text.equalsIgnoreCase(day)) {
						datesOnLeftSide.get(daysCounter).click();
						break;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalender " + e.getMessage()));
		}
	}

	/**
	 * This is a method to verify calendar filter with dates in days hours and
	 * minutes format
	 * 
	 * @param languageCode  - language code
	 * @param calenderKey-  locator for calendar column
	 * @param monthKeyLeft  locator to go to months on left side
	 * @param monthKeyRight - locator to go to months on right side
	 * @param leftArrowKey  - locator for left arrow key on calendar
	 * @param daysKeyLeft   - locator for days on left side
	 * @param daysKeyRight  - locator for days on right side
	 * @param emptyTextKey  - locator for "no items available" message on column
	 * @param columnListKey - locator for all items filtered on column
	 * @return - boolean value of whether the dates are selected properly
	 */
	public final boolean verifyCalendarWithoutDateFormat(String languageCode, String calenderKey, String monthKeyLeft,
			String monthKeyRight, String leftArrowKey, String daysKeyLeft, String daysKeyRight, String emptyTextKey,
			String columnListKey) {
		try {
			int flag = 0;
			String empty_text = null;
			Date currentDate = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("MMMMM d, YYYY");
			String d1 = ft.format(currentDate);
			Date daysAgo = DateUtils.addDays(new Date(), -7);
			String d2 = ft.format(daysAgo);
			sleeper(3000);
			selectDateFromCalender(d2, calenderKey, monthKeyLeft, monthKeyRight, leftArrowKey, daysKeyLeft,
					daysKeyRight);
			sleeper(3000);
			selectDateFromCalender(d1, calenderKey, monthKeyLeft, monthKeyRight, leftArrowKey, daysKeyLeft,
					daysKeyRight);
			if (verifyElementIsVisible(emptyTextKey)) {
				empty_text = getTextBy(emptyTextKey);
				if (empty_text.equals(getTextLanguage(languageCode, "daas_ui", "incidents.list.no_items"))) {
					flag = 1;
				}
			} else {
				List<WebElement> columnListDates = getElementsTillAllElementsPresent(columnListKey);
				for (WebElement webElement : columnListDates) {
					if (webElement.getText().contains("day") || webElement.getText().contains("days")) {
						if (Integer.parseInt(webElement.getText().substring(0, webElement.getText().indexOf(" "))) <= 7)
							flag = 1;
						else
							flag = 0;
					}

					else if (webElement.getText().contains("hours") || webElement.getText().contains("hour")
							|| webElement.getText().contains("minutes"))
						flag = 1;

					else
						flag = 0;
				}
			}
			if (flag == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectLastOneWeekRange " + e.getMessage()));
			return false;
		}

	}

	/**
	 * This is a method to verify calendare filter with dd/mm/yyyy format
	 * 
	 * @param LanguageCode  - language code
	 * @param calenderKey-  locator for calendar column
	 * @param monthKeyLeft  locator to go to months on left side
	 * @param monthKeyRight - locator to go to months on right side
	 * @param leftArrowKey  - locator for left arrow key on calendar
	 * @param daysKeyLeft   - locator for days on left side
	 * @param daysKeyRight  - locator for days on right side
	 * @param emptyTextKey  - locator for "no items available" message on column
	 * @param columnListKey - locator for all items filtered on column
	 * @return - boolean value of whether the dates are selected properly
	 * @throws Exception
	 */
	public final boolean verifyCalendarWithDateFormat(String LanguageCode, String calenderKey, String monthKeyLeft,
			String monthKeyRight, String leftArrowKey, String daysKeyLeft, String daysKeyRight, String emptyTextKey,
			String columnListKey) throws Exception {
		boolean flag = false;
		String empty_text = null;
		Date currentDate = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("MMMMM d, YYYY");
		String d1 = ft.format(currentDate);
		Date daysAgo = DateUtils.addDays(new Date(), -6);
		String d2 = ft.format(daysAgo);
		sleeper(4000);
		selectDateFromCalender(d2, calenderKey, monthKeyLeft, monthKeyRight, leftArrowKey, daysKeyLeft, daysKeyRight);
		sleeper(3000);
		selectDateFromCalender(d1, calenderKey, monthKeyLeft, monthKeyRight, leftArrowKey, daysKeyLeft, daysKeyRight);
		if (verifyElementIsVisibleDynamic(emptyTextKey, 5)) {
			empty_text = getTextBy(emptyTextKey);
			if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
				flag = true;
			}
		} else {
			List<WebElement> columnListDates = getElementsTillAllElementsPresent(columnListKey);
			List<String> listofDates = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -8);
			for (int i = 0; i <= 7; i++) {
				cal.add(Calendar.DAY_OF_YEAR, 1);
				listofDates.add(sdf.format(cal.getTime()));
			}
			for (WebElement wb : columnListDates) {
				for (int j = 0; j < listofDates.size(); j++) {
					if (wb.getText().contains(listofDates.get(j))) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * This is the method to verify filter functionality when a single filter is
	 * selected. A list of all the options available from the filter dropdown is
	 * fetched and the first available option is selected. It also checks if there
	 * are no available items available for the filter applied,if so, displays the
	 * message "No items available"
	 * 
	 * @param LanguageCode      : This is used as code for multiple languages.
	 * @param checkboxKey       : Locator for check box
	 * @param listOfElementKey: Locator for list of elements
	 * @param columnListKey:    Locator of elements of column.
	 * @param emptyTextKey:     Locator of empty text message.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyFilterFunctionalityForSingleSelect(String LanguageCode, String checkboxKey,
			String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		List<WebElement> listOfCheckbox = getElementsTillAllElementsVisible(checkboxKey);
		List<WebElement> listOfItems = getElementsTillAllElementsVisible(listOfElementKey);
		Actions action = new Actions(getDriver());
		String text = null;
		String empty_text = null;
		int flag = 0;
		if (!listOfCheckbox.get(0).isSelected()) {
			text = listOfItems.get(0).getText();
			action.moveToElement(listOfItems.get(0)).click().build().perform();
			sleeper(3000);
			pressKey(Keys.ESCAPE);
		}
		if (waitUntillElementIsPresentDynamic(emptyTextKey, 5)) {
			empty_text = getTextBy(emptyTextKey);
			if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
				flag = 1;
			}
		} else {
			List<WebElement> columnList = getElementsTillAllElementsVisible(columnListKey);
			for (WebElement webElement : columnList) {
				if (webElement.getText().contains(text)) {
					flag = 1;
				} else {
					return false;
				}
			}
		}
		if (flag == 1) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * This is the method to select the first value from a drop down
	 * 
	 * @param dropdownListKey: List of values in drop down.
	 * @return
	 * @throws Exception
	 */
	public final String selectFirstValueFromDropdown(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsPresent(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	/**
	 * This is the method to select the any value from a drop down
	 * 
	 * @param dropdownListKey: List of values in drop down.
	 * @return
	 * @throws Exception
	 */
	public final String selectAnyValueFromDropdown(String dropdownListKey, int index) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsPresent(dropdownListKey);
		String text = listOfOptions.get(index).getText();
		listOfOptions.get(index).click();
		return text;
	}

	/**
	 * This is the method to select value value from dropdown, if the already
	 * assigned value is same as the first option of the dropdown, the second option
	 * from dropdown is selected
	 * 
	 * @param dropdownListKey: List of values in drop down.
	 * @param text:            already assigned value
	 * @return
	 * @throws Exception
	 */
	public final String selectValueOnPopup(String dropdownListKey, String text) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsPresent(dropdownListKey);
		String firstOptionOfDropdown = listOfOptions.get(0).getText();
		String secondOptionOfDropdown = listOfOptions.get(1).getText();
		if (firstOptionOfDropdown.equals(text)) {
			listOfOptions.get(1).click();
			return secondOptionOfDropdown;
		} else {
			listOfOptions.get(0).click();
			return firstOptionOfDropdown;
		}
	}

	/**
	 * This is the method to select the element based on text from a drop down
	 * 
	 * @param dropdownListKey: List of values in drop down.
	 * @param elementText:     Element text which is to be clicked.
	 * @param dropdownBox:     Drop down box locator.
	 * @return
	 * @throws Exception
	 */
	public final boolean selectTextValueFromDropdown(String dropdownListKey, String elementText, String dropdownBox)
			throws Exception {
		boolean flag = false;
		List<WebElement> listOfOptions = getElementsTillAllElementsPresent(dropdownListKey);
		WebElement dropdown = getElement(dropdownBox);
		for (int i = 0; i < listOfOptions.size(); i++) {
			if (listOfOptions.get(i).getText().equalsIgnoreCase(elementText)) {
				listOfOptions.get(i).click();
				sleeper(2000);
				break;
			}
		}
		if (dropdown.getText().equalsIgnoreCase(elementText)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

	}

	/**
	 * This is the method to select the element based on text from a drop down for
	 * workflow
	 * 
	 * @param dropdownListKey: List of values in drop down.
	 * @param elementText:     Element text which is to be clicked.
	 * @param dropdownBox:     Drop down box locator.
	 * @return
	 * @throws Exception
	 */
	public final boolean selectTextValueFromDropdownWorkflow(String dropdownListKey, String elementText,
			String dropdownBox) throws Exception {
		boolean flag = false;
		List<WebElement> listOfOptions = getElementsTillAllElementsPresent(dropdownListKey);
		for (int i = 0; i < listOfOptions.size(); i++) {
			if (listOfOptions.get(i).getText().equalsIgnoreCase(elementText)) {
				listOfOptions.get(i).click();
				sleeper(2000);
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * This is the method to verify if a particular value that is selected from drop
	 * down/search box is applied to all the elements of a column inside a table
	 * This is the method to verify if a particular value that is selected from drop
	 * down/search box is applied to all the elements of a column inside a table
	 * 
	 * @param incidentListKey
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public final boolean verifySelectedValueFromPopupInTable(String incidentListKey, String text) throws Exception {
		int flag = 0;
		List<WebElement> listOfOptions = getElementsTillAllElementsVisible(incidentListKey);
		for (WebElement webElement : listOfOptions) {
			if (webElement.getText().contains(text)) {
				flag = 1;
			} else {
				return false;
			}
		}
		if (flag == 1) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * This method returns the text of a list of elements
	 */
	public final ArrayList<String> getTextOfList(String key) throws Exception {
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		List<WebElement> element = getElementsTillAllElementsPresent(key);

		for (WebElement webElement : element) {
			String columnName = webElement.getText().toLowerCase();
			columnName = columnName.replaceAll("\n[ \t]+", "");
			columnNamesOnPage.add(columnName);
		}
		return columnNamesOnPage;
	}

	/**
	 * This method returns the text of a list of elements
	 */
	public final ArrayList<String> getList(String key) throws Exception {
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		List<WebElement> element = getElementsTillAllElementsPresent(key);

		for (WebElement webElement : element) {
			String columnName = webElement.getText();
			columnName = columnName.replaceAll("\n[ \t]+", "");
			columnNamesOnPage.add(columnName);
		}
		return columnNamesOnPage;
	}

	/**
	 * This method is used to verify if all the check boxes are selected of a table
	 */
	public final boolean verifyAllCheckBoxesSelectedOfTable(String key) throws Exception {
		ArrayList<String> checkboxes = new ArrayList<>();
		List<WebElement> element = getElementsTillAllElementsPresent(key);
		for (int i = 1; i < element.size(); i++) {
			if (element.get(i).isSelected()) {
				checkboxes.add("true");
			} else {
				checkboxes.add("false");
			}
		}
		if (checkboxes.contains("false")) {
			return false;
		}
		return true;
	}

	/*
	 * Common method to verify search functionality inside single select drop down
	 */

	public final boolean verifySearchFunctionalityUsingSingleSelectDropdown(String LanguageCode, String textKey,
			String Text, String emptyTextKey, String listKey, String dropdownBoxKey) throws Exception {
		enterText(textKey, Text);
		boolean flag = false;
		sleeper(10000);
		String empty_text = null;

		if (verifyElementIsPresent(emptyTextKey)) {
			empty_text = getTextBy(emptyTextKey);
			if (empty_text.equals(CompaniesVariables.COMPANY_SEARCH_EMPTY_DASHBOARD + " " + Text)) {
				flag = true;
				LOGGER.info("Company not found in drop down list.");
				clearText(textKey);
				pressKey(Keys.ESCAPE);
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(listKey);
			for (WebElement webElement : elements) {
				if (webElement.getText().contains(Text)) {
					String dropdownListText = webElement.getText();
					webElement.click();
					sleeper(3000);
					String text = getTextBy(dropdownBoxKey);
					if (text.equalsIgnoreCase(dropdownListText)
							|| text.contains(Integer.toString(CompaniesVariables.SELECTED_COMPANY_COUNT1) + " "
									+ getTextLanguage(LanguageCode, "daas_ui", "dropdown.selected"))) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	/*
	 * Common method to verify search functionality inside single select drop down
	 */

	public final boolean verifySearchFunctionalityUsingSingleSelectDropdownRadioButton(String LanguageCode,
			String textKey, String Text, String emptyTextKey, String listKey, String dropdownBoxKey, String saveButton)
			throws Exception {
		clickByJavaScript(textKey);
		sleeper(2000);
		enterText(textKey, Text);
		boolean flag = false;
		sleeper(10000);
		String empty_text = null;

		if (verifyElementIsPresent(emptyTextKey)) {
			empty_text = getTextBy(emptyTextKey);
			if (empty_text.equals(CompaniesVariables.COMPANY_NO_DATA + " " + Text)) {
				flag = true;
				LOGGER.info("Company not found in drop down list.");
				clearText(textKey);
				pressKey(Keys.ESCAPE);
			}
		} else {
			List<WebElement> elements = getAllElements(listKey);
			for (WebElement webElement : elements) {
				if (webElement.getText().contains(Text)) {
					String dropdownListText = webElement.getText();
					webElement.click();
					sleeper(3000);
					clickByJavaScript(saveButton);
					sleeper(1000);
					String text = getTextBy(dropdownBoxKey);
					if (text.equalsIgnoreCase(dropdownListText) || text
							.contains(getTextLanguage(LanguageCode, "daas_ui", "location.filter.oneCompanySelected"))) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * Select text in drop down of select tag
	 * 
	 * @param locator
	 * @param text
	 * @throws Exception
	 */
	public final void SelectTextPresentInDropdown(String locator, String text) throws Exception {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid blue'", element);
		Select oSelect = new Select(getDriver().findElement(this.getObject(locator)));
		oSelect.selectByVisibleText(text);
	}

	/**
	 * 
	 * Verify text in drop down is Present for drop down options of select tag
	 * 
	 * @param locator
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyTextPresentInDropdown(String locator, String text) throws Exception {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid blue'", element);

		Select oSelect = new Select(getDriver().findElement(this.getObject(locator)));
		List<WebElement> elementOptionsCount = oSelect.getOptions();

		for (int i = 0; i < elementOptionsCount.size(); i++) {
			String elementtext = elementOptionsCount.get(i).getText();

			if (elementtext.contentEquals(text)) {
				return true;
			}
		} // efo

		return false;
	}

	/**
	 * Common method to get Labels of charts down
	 */

	public final ArrayList<String> getLabelsOfChart(String key) throws Exception {
		ArrayList<String> labelsOFChartList = new ArrayList<String>();
		List<WebElement> labelsList = getElementsTillAllElementsVisible(key);
		for (WebElement label : labelsList) {
			String value = label.getText();
			labelsOFChartList.add(value);
		}
		return labelsOFChartList;
	}

	/**
	 * @param languageCode - language code from pom.xml
	 * @throws Exception
	 */
	public final void changeLanguage(String languageCode) throws Exception {
		if (!getItemFromLocalStorage("lang").equalsIgnoreCase(languageCode)) {
			jsDriver().executeScript(
					String.format("window.localStorage.setItem('%s','%s');", "forceLocalStorageLocale", languageCode));
			getDriver().navigate().refresh();
		} else {
			LOGGER.info("Portal language is same as in POM i.e. " + languageCode + ", language change not needed");
		}
	}

	// Commenting it as we will not use this method for now
	/*
	 * public final void changeLanguage(String languageCode) throws
	 * InterruptedException { if
	 * (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) { if
	 * (System.getProperty("browserName").equalsIgnoreCase("Chrome")) { Cookie name
	 * = new Cookie("language", languageCode); WebStorage webStorage = (WebStorage)
	 * new Augmenter().augment(getDriver()); LocalStorage localStorage =
	 * webStorage.getLocalStorage(); localStorage.setItem("forceCookieLang",
	 * "true"); getDriver().manage().addCookie(name);
	 * getDriver().navigate().refresh(); } else if
	 * (System.getProperty("browserName").equalsIgnoreCase("Firefox")) { Cookie name
	 * = new Cookie("language", languageCode); js = jsDriver();
	 * js.executeScript(String.format("window.localStorage.setItem('%s','%s');",
	 * "forceCookieLang", "true")); getDriver().manage().addCookie(name);
	 * getDriver().navigate().refresh(); } } else if
	 * (System.getProperty("os.name").toUpperCase().contains("MAC")) {
	 * DRIVER.navigate().refresh(); } }
	 */

	/**
	 * This is the method to verify filter functionality when dynamic multiple
	 * filters are selected. It selects the first and last filter check box from the
	 * drop down. It has functionality same as the method
	 * verifyFilterFunctionalityForSingleSelect only difference being for
	 * dynamically changing multiple filters
	 */
	public final boolean verifyFilterFunctionalityForMultiSelectForDyanmicList(String LanguageCode, String checkboxKey,
			String listOfElementKey, String columnListKey, String emptyTextKey) throws Exception {
		List<WebElement> listOfitems = getElementsTillAllElementsVisible(checkboxKey);
		String text1 = null;
		String text2 = null;
		String empty_text = null;
		boolean flag = false;
		if (listOfitems.size() > 1) {
			text1 = listOfitems.get(0).getText();
			listOfitems.get(0).click();
			sleeper(3000);
			List<WebElement> listOfitems1 = getElementsTillAllElementsVisible(checkboxKey);
			text2 = listOfitems1.get(listOfitems1.size() - 1).getText();
			listOfitems1.get(listOfitems1.size() - 1).click();
			pressKey(Keys.ESCAPE);

			if (waitUntillElementIsPresentDynamic(emptyTextKey, 5)) {
				empty_text = getTextBy(emptyTextKey);
				if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
					flag = true;
				}
			} else {
				List<WebElement> columnlist = getElementsTillAllElementsVisible(columnListKey);
				for (WebElement webElement : columnlist) {
					if (webElement.getText().contains(text1) || webElement.getText().contains(text2)) {
						flag = true;
					}
				}
			}
			return flag;
		} else {
			LOGGER.info("Unable to proceed with this dropdown, since this dropdown contains only single elements");
			return true;
		}

	}

	public final boolean verifyFilterFunctionalityForSingleSelectFromDynamicDropdown(String LanguageCode,
			String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey,
			String emptyTextColumnKey) throws Exception {
		enterText(textKey, Text);
		int flag = 0;
		sleeper(3000);
		String empty_text = null, text = null;
		if (verifyElementIsVisible(emptyTextKey)) {
			empty_text = getTextBy(emptyTextKey);
			String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
			if (empty_text.contains(emptytext[0].concat(Text))) {
				flag = 1;
			}
			pressKey(Keys.ESCAPE);
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(listKey);
			List<WebElement> listOfCheckbox = getElementsTillAllElementsVisible(checkboxKey);
			for (WebElement webElement : elements) {
				flag = 0;
				if (webElement.getText().toLowerCase().contains(Text.toLowerCase())) {
					flag = 1;
				} else {
					return false;

				}
			}
			if (!listOfCheckbox.get(0).isSelected()) {
				text = elements.get(0).getText();
				listOfCheckbox.get(0).click();
				pressKey(Keys.ESCAPE);
			}
			sleeper(2000);

			if (verifyElementIsVisible(emptyTextColumnKey)) {
				empty_text = getTextBy(emptyTextColumnKey);
				if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
					flag = 1;
				}
			} else {
				List<WebElement> columnList = getElementsTillAllElementsVisible(columnListKey);
				for (WebElement webElement : columnList) {
					if (webElement.getText().equals(text)) {
						flag = 1;
					} else {
						return false;
					}
				}
			}
		}

		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	public final boolean verifyFilterFunctionalityForMultiSelectFromDynamicDropdown(String LanguageCode, String textKey,
			String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey,
			String emptyTextColumnKey) throws Exception {
		enterText(textKey, Text);
		boolean flag = false;
		sleeper(3000);
		String empty_text = null, text1 = null, text2 = null;
		if (verifyElementIsVisibleDynamic(emptyTextKey, 5)) {
			empty_text = getTextBy(emptyTextKey);
			String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
			if (empty_text.contains(emptytext[0].concat(Text))) {
				flag = true;
			}
			pressKey(Keys.ESCAPE);
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(listKey);
			for (WebElement webElement : elements) {
				flag = false;
				if ((webElement.getText().toLowerCase().contains(Text.toLowerCase()))) {
					flag = true;
				} else {
					return false;

				}
			}

			List<WebElement> listOfitems = getElementsTillAllElementsVisible(checkboxKey);
			if (listOfitems.size() > 1) {
				listOfitems.get(0).click();
				sleeper(5000);
				List<WebElement> listOfitems1 = getElementsTillAllElementsVisible(checkboxKey);
				listOfitems1.get(listOfitems1.size() - 1).click();
				sleeper(5000); // Will be adding page refresh code
				List<WebElement> listOfitems2 = getElementsTillAllElementsVisible(listKey);
				text1 = listOfitems2.get(0).getText();
				List<WebElement> listOfitems3 = getElementsTillAllElementsVisible(listKey);
				text2 = listOfitems3.get(listOfitems3.size() - 1).getText();
				pressKey(Keys.ESCAPE);

				if (verifyElementIsVisible(emptyTextColumnKey)) {
					empty_text = getTextBy(emptyTextColumnKey);
					if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
						flag = true;
					}
				} else {
					List<WebElement> columnlist = getElementsTillAllElementsVisible(columnListKey);
					for (WebElement webElement : columnlist) {
						flag = false;
						if (webElement.getText().equals(text1) || webElement.getText().equals(text2)) {
							flag = true;
						}
					}
				}
			} else {
				LOGGER.info("Unable to proceed with this dropdown, since this dropdown contains only single elements");
				return true;
			}
		}
		return flag;
	}

	/**
	 * This is a method to verify filter functionality for single select assigned to
	 * column
	 * 
	 * @param languageCode       - language code
	 * @param searchTextKey      - Text to be entered
	 * @param emptyTextKey       -locator for "no items available" message
	 * @param listKey            - locator of options filtered on popup
	 * @param columnListKey      - locator of all items filtered in column
	 * @param emptyTextColumnKey - locator of "no items available" message in column
	 * @return - boolean value of whether the filter functionality is working
	 *         correctly
	 * @throws Exception
	 */
	public final boolean verifyFilterFunctionalityForSingleSelectionFromDynamicDropdown(String languageCode,
			String searchTextKey, String searchText, String emptyTextKey, String listKey, String columnListKey,
			String emptyTextColumnKey) throws Exception {
		enterText(searchTextKey, searchText);
		boolean flag = false;
		sleeper(3000);
		String empty_TextOfNoAvailableItem = null, selectionText = null;
		if (verifyElementIsVisibleDynamic(emptyTextKey, 5)) {
			empty_TextOfNoAvailableItem = getTextBy(emptyTextKey);
			String[] emptyTextOfNoAvailableItem = getTextLanguage(languageCode, "daas_ui", "dropdown.noResults")
					.split("%");
			if (empty_TextOfNoAvailableItem.contains(emptyTextOfNoAvailableItem[0].concat(searchText))) {
				flag = true;
			}
			pressKey(Keys.ESCAPE);
		} else {
			List<WebElement> listOfCheckbox = getElementsTillAllElementsVisible(listKey);
			if (!listOfCheckbox.get(0).isSelected()) {
				selectionText = listOfCheckbox.get(0).getText();
				listOfCheckbox.get(0).click();
				pressKey(Keys.ESCAPE);
			}
			sleeper(4000);
			if (verifyElementIsVisibleDynamic(emptyTextColumnKey, 5)) {
				empty_TextOfNoAvailableItem = getTextBy(emptyTextColumnKey);
				if (empty_TextOfNoAvailableItem
						.equals(getTextLanguage(languageCode, "daas_ui", "incidents.list.no_items"))) {
					flag = true;
				}
			} else {
				List<WebElement> columnList = getElementsTillAllElementsVisible(columnListKey);
				for (WebElement webElement : columnList) {
					if (webElement.getText().equals(selectionText)) {
						flag = true;
					} else {
						flag = false;
						return flag;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * This is a method to verify filter functionality for assigned to column when
	 * multiple options are selected
	 * 
	 * @param LanguageCode       - language code
	 * @param searchTextKey      - locator of searchbox
	 * @param searchText         - search text to be entered
	 * @param emptyTextKey       -locator for "no items available" message
	 * @param listKey            - locator of options filtered on popup
	 * @param columnListKey      - locator of all items filtered in column
	 * @param emptyTextColumnKey - locator of "no items available" message in column
	 * @return - boolean value of whether the filter functionality is working
	 *         correctly
	 * @throws Exception
	 */
	public final boolean verifyMultiSelectionFilterFunctionalityFromDynamicDropdown(String LanguageCode,
			String searchTextKey, String searchText, String emptyTextKey, String listKey, String columnListKey,
			String emptyTextColumnKey) throws Exception {
		enterText(searchTextKey, searchText);
		boolean flag = false;
		sleeper(3000);
		String empty_text = null, firstSelection = null, lastSelection = null;
		if (verifyElementIsVisibleDynamic(emptyTextKey, 5)) {
			empty_text = getTextBy(emptyTextKey);
			String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
			if (empty_text.contains(emptytext[0].concat(searchText))) {
				flag = true;
			}
			pressKey(Keys.ESCAPE);
		} else {
			List<WebElement> listOfitems = getElementsTillAllElementsVisible(listKey);
			if (listOfitems.size() > 1) {
				firstSelection = listOfitems.get(0).getText();
				listOfitems.get(0).click();
				sleeper(5000);
				lastSelection = listOfitems.get(listOfitems.size() - 1).getText();
				listOfitems.get(listOfitems.size() - 1).click();
				sleeper(5000); // Will be adding page refresh code
				pressKey(Keys.ESCAPE);

				if (verifyElementIsVisibleDynamic(emptyTextColumnKey, 5)) {
					empty_text = getTextBy(emptyTextColumnKey);
					if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
						flag = true;
					}
				} else {
					List<WebElement> columnlist = getElementsTillAllElementsVisible(columnListKey);
					for (WebElement webElement : columnlist) {
						flag = false;
						if (webElement.getText().equals(firstSelection) || webElement.getText().equals(lastSelection)) {
							flag = true;
						}
					}
				}
			} else {
				System.out.println(
						"Unable to proceed with this dropdown, since this dropdown contains only single elements");
				return true;
			}
		}
		return flag;
	}

	/*
	 * Method to validate file is downloaded or not
	 */
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();
		for (int filecount = 0; filecount < dir_contents.length; filecount++) {
			if (dir_contents[filecount].getName().equals(fileName))
				return flag = true;
		}
		return flag;
	}

	public final long generateRandomNumber(int min, int max) {
		return (long) Math.floor(Math.random() * (max - min + 1)) + min;
	}

	/*
	 * Click on dropdown before calling this method
	 */
	public final boolean selectFromDropdown(String locator, String key, String text) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid blue'", element);
		List<WebElement> xpath = getDriver().findElements(this.getObject(key));
		int xpathCount = xpath.size();
		for (int i = 1; i <= xpathCount; i++) {
			String dropdownText;
			dropdownText = this.getTextBy(key + "[" + i + "]//*");
			if (dropdownText.contains(text)) {
				moveToElements(key + "[" + i + "]//*");
				this.click(key + "[" + i + "]//*");
				return true;
			}
		}
		return false;
	}

	/*
	 * Common method to verify search functionality inside multiple select drop down
	 */
	public final boolean verifyMultipleCompaniesSelection(String LanguageCode, String textKey, String Text,
			String emptyTextKey, String listKey, String dropdownBoxKey, int counter) throws Exception {
		enterText(textKey, Text);
		boolean flag = false;

		sleeper(10000); // wait for search company to be listed in the drop-down

		String empty_text = null;
		if (verifyElementIsPresent(emptyTextKey)) {
			empty_text = getTextBy(emptyTextKey);
			if (empty_text.equals(CompaniesVariables.COMPANY_SEARCH_EMPTY_DASHBOARD + "\"" + Text + "\"")) {
				flag = false;
				clearText(textKey);
				pressKey(Keys.ESCAPE);
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(listKey);
			for (WebElement webElement : elements) {
				if (webElement.getText().contains(Text)) {
					String dropdownListText = webElement.getText();
					webElement.click();
					sleeper(2000); // wait for selected company to be displayed in the search box
					String text = getTextBy(dropdownBoxKey);
					if (text.equalsIgnoreCase(dropdownListText) || text.contains(Integer.toString(counter) + " "
							+ getTextLanguage(LanguageCode, "daas_ui", "dropdown.selected"))) {
						flag = true;
						break;
					}
				}
			}
		}
		if (flag) {
			return true;
		} else {
			return false;
		}
	}

	public final boolean selectDropdownOptions(String key, String text) throws Exception {
		List<WebElement> ops = getElementsTillAllElementsPresent(key);
		for (int i = 0; i < ops.size(); i++) {
			if (ops.get(i).getText().equalsIgnoreCase(text)) {
				ops.get(i).click();
				return true;
			}
		}
		return false;
	}

	public final int getTableRowsCount(String tableRowsKey) throws Exception {
		List<WebElement> elementList = getElementsTillAllElementsVisible(tableRowsKey);
		return elementList.size();
	}

	public final boolean verifyButtonStatusofPagination(int activepageNumber, int firstPageNumber, int lastPageNumber,
			int totalRecordCount, boolean previousButtonStatus, boolean nextButtonStatus) {
		boolean flag = false;
		if (firstPageNumber == lastPageNumber) {
			if ((!previousButtonStatus) && (!nextButtonStatus)) {
				flag = true;
			}
		} else if (activepageNumber == 1) {
			if ((!previousButtonStatus) && (nextButtonStatus)) {
				flag = true;
			}
		} else if (lastPageNumber == activepageNumber) {
			if ((previousButtonStatus) && (!nextButtonStatus)) {
				flag = true;
			}
		} else {
			if ((previousButtonStatus) && (nextButtonStatus)) {
				flag = true;
			}
		}
		return flag;
	}

	public final int getSelectedDropdownOptionOnPagination(String key) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		String selectedText = null;
		WebElement dropDownListElement = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(key)));
		jsDriver().executeScript("arguments[0].style.border='3px solid blue'", dropDownListElement);
		List<WebElement> optionList = getDriver().findElements(this.getObject(key));
		for (int optionListCounter = 0; optionListCounter < optionList.size(); optionListCounter++) {
			if (optionList.get(optionListCounter).getAttribute("aria-current").equals("true")) {
				selectedText = optionList.get(optionListCounter).getText();
			}
		}
		if (selectedText.isEmpty() || selectedText == null) {
			throw new InputMismatchException("There is no text for selected dropdown option");
		}
		Actions action = new Actions(getDriver());
		action.sendKeys(Keys.ESCAPE).perform();
		return Integer.parseInt(selectedText);
	}

	public final boolean verifyTotalPageCountChangeAsSelectedOption(int selectedOption, int totalCount,
			int lastPageNumber) {
		boolean flag = false;
		double pageCount = Double.valueOf(totalCount) / Double.valueOf(selectedOption);
		int pageCountround = (int) Math.ceil(pageCount);
		if (pageCountround == lastPageNumber) {
			flag = true;
		}
		return flag;
	}

	public final boolean changeSelectedOption(int totalCount, int goingToSelectOption) {
		boolean Flag = false;
		if (totalCount >= goingToSelectOption) {
			Flag = true;
		}
		return Flag;
	}

	public final boolean verifySelectedOptionForNewSelection(int selectedOption, int goingToSelectOption) {
		boolean Flag = false;
		if (goingToSelectOption != selectedOption) {
			Flag = true;
		}
		return Flag;
	}

	public final boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException er) {
			return false;
		}
	}

	public final boolean canClickNext(int activepageNumber, int lastPageNumber, boolean nextButtonStatus) {
		boolean Flag = false;
		if ((activepageNumber != lastPageNumber) && (nextButtonStatus)) {
			Flag = true;
		}
		return Flag;
	}

	public final boolean canClickPrevious(int activepageNumber, int firstPageNumber, boolean previousButtonStatus) {
		boolean Flag = false;
		if ((activepageNumber != firstPageNumber) && (previousButtonStatus)) {
			Flag = true;
		}
		return Flag;
	}

	/**
	 * This is the method to validate the sorting in ascending order
	 * 
	 * @param unsortedList - This is the list of values on the column that is
	 *                     unsorted
	 * @param sortedList   - This is the list that appears after the sorting is
	 *                     applied
	 * @return - This method returns a boolean value after comparing both the lists
	 * @throws Exception
	 */

	public final boolean sortingInAscendingOrder(ArrayList<String> unsortedList, ArrayList<String> sortedList)
			throws Exception {
		boolean result;
		Collections.sort(unsortedList, String.CASE_INSENSITIVE_ORDER);
		LOGGER.info("Unsorted list of RoomNames :" + unsortedList);
		LOGGER.info("Sorted list of RoomNames :" + sortedList);
		result = unsortedList.equals(sortedList);
		return result;
	}

	/**
	 * This is the method to validate the sorting in descending order
	 * 
	 * @param unsortedList - This is the list of values on the column that is
	 *                     unsorted
	 * @param sortedList   - This is the list that appears after the sorting is
	 *                     applied
	 * @return - This method returns a boolean value after comparing both the lists
	 * @throws Exception
	 */

	public final boolean sortingInDescendingOrder(ArrayList<String> unsortedList, ArrayList<String> sortedList)
			throws Exception {
		boolean result;
		Collections.sort(unsortedList, String.CASE_INSENSITIVE_ORDER);
		Collections.reverse(unsortedList);
		LOGGER.info("Unsorted list of RoomNames :" + unsortedList);
		LOGGER.info("Sorted list of RoomNames :" + sortedList);
		result = unsortedList.equals(sortedList);
		return result;
	}

	/**
	 * This is the method to validate the sorting for a calendar filter
	 * 
	 * @param unsortedList - This is the list of values on the column that is
	 *                     unsorted
	 * @param sortedList   - This is the list that appears after the sorting is
	 *                     applied
	 * @return - This method returns a boolean value after parsing and comparing
	 *         both the lists
	 * @throws Exception
	 */

	public final boolean sortingForCalendar(ArrayList<String> unsortedList, ArrayList<String> sortedList)
			throws Exception {
		int flag = 0;
		Collections.sort(unsortedList);
		DateFormat f = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a");
		for (int i = 0; i < unsortedList.size(); i++) {
			flag = f.parse(unsortedList.get(i)).compareTo(f.parse(sortedList.get(i)));
		}
		if (flag == 1 || flag == -1)
			return false;
		else
			return true;
	}

	public final void clearAllFilters(String clearFilterKey) throws Exception {
		if (verifyElementIsPresent(clearFilterKey)) {
			List<WebElement> elements = getElementsTillAllElementsPresent(clearFilterKey);
			for (WebElement webElement : elements) {
				String searchValue = webElement.getAttribute("value");
				if (searchValue == null) {
					webElement.click();
				} else if (!searchValue.isEmpty()) {
					if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
						webElement.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
						webElement.click();
					} else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
						webElement.sendKeys(Keys.chord(Keys.COMMAND, "a") + Keys.BACK_SPACE);
						webElement.click();
					}
				} else {
					LOGGER.info("Filters are cleared already");
				}
				sleeper(4000);
			}
		}
	}

	/**
	 * This is the method to verify if a particular value that is selected from drop
	 * down/search box is applied to all the elements of a column inside a table
	 * 
	 * @param valuesListKey
	 * @param text
	 * @param searchBoxKey
	 * @param dropDownBoxKey
	 * @param emptyText
	 * @return
	 * @throws Exception
	 */
	public final boolean verifySingleSelectDropdownSearchBox(String valuesListKey, String text, String searchBoxKey,
			String dropDownBoxKey, String emptyText) throws Exception {
		try {
			boolean flag = false;
			sleeper(2000);
			enterText(searchBoxKey, text);
			sleeper(2000);
			// if (waitUntillElementIsPresentDynamic(emptyText,3)) {
			// LOGGER.error("Dropdown is empty! Unable to select any element.");
			// } else {
			int tryAgain = 0;
			boolean found = false;
			while (tryAgain < 20 && !found) {
				sleeper(1000);
				List<WebElement> listOfOptions = getElementsTillAllElementsVisible(valuesListKey);
				if (listOfOptions.size() > 0) {
					for (WebElement webElement : listOfOptions) {
						if (webElement.getText().equals(text)) {
							webElement.click();
							found = true;
							break;
						}
					}
				} else {
					LOGGER.error("Dropdown is empty! Unable to select any element.");
				}
				tryAgain++;
			}
			sleeper(2000);
			waitUntillElementIsPresent(dropDownBoxKey);
			WebElement dropdownBox = getElement(dropDownBoxKey);
			if (dropdownBox.getText().equalsIgnoreCase(text)) {
				flag = true;
			} else {
				LOGGER.error("Value is not selected in dropdown.");
				flag = false;
			}
			// }
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured while selecting value from dropdown " + e.getMessage());
			return false;
		}
	}

	/*
	 * @param homeEnvironment - environment in string type
	 * 
	 * @return - This method return Search service api host address
	 * 
	 * @throws IOException
	 */
	public final String getSearchServiceApi(String homeEnvironment) throws IOException {
		environmentProperties = environmentPropertiesReader.getObjectRepository("Environment");
		String serverAPI = homeEnvironment;
		switch (homeEnvironment.toUpperCase()) {
		case "US-STABLE":
			serverAPI = environmentProperties.getProperty("StableUSADDROLEURL");
			break;
		case "EU-STABLE":
			serverAPI = environmentProperties.getProperty("StableEUADDROLEURL");
			break;
		case "US-STAGING":
			serverAPI = environmentProperties.getProperty("StagingUSADDROLEURL");
			break;
		case "EU-STAGING":
			serverAPI = environmentProperties.getProperty("StagingEUADDROLEURL");
			break;
		case "US-PRODUCTION":
			serverAPI = environmentProperties.getProperty("ProdUSSS");
			break;
		case "EU-PRODUCTION":
			serverAPI = environmentProperties.getProperty("ProdEUSS");
			break;
		case "PENTEST":
			serverAPI = environmentProperties.getProperty("Pentest_Environment");
			break;

		default:
			LOGGER.error("Incorrect homeEnvironment name given, please check the homeEnvironment name!!. "
					+ homeEnvironment + " is not a valid entry.");
		}
		return serverAPI;
	}

	/**
	 * @param authToken- this is AID from Cookies
	 * @return String
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
	 * @param key            -Column order expected in Json object
	 * @param responseString - response body in String format
	 * @return List
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public final List<String> getListFromAPIKEY(String key, String responseString)
			throws JsonProcessingException, IOException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(responseString);
		String[] data = key.split("\\.");
		if (data.length < 1) {
			LOGGER.error("Please use proper Key : " + key + " It is not valid key to search");
			throw new InputMismatchException("Please provide proper Key");
		}
		List<JsonNode> jsonNodes = rootNode.findValues(data[0]);
		for (int dataCounter = 1; dataCounter < data.length; dataCounter++) {
			if (dataCounter == data.length - 1 && !jsonNodes.get(0).path(data[dataCounter]).isValueNode()) {
				LOGGER.error("Please use proper Key : " + data[dataCounter] + " It is not valid key to search");
			} else {
				List<JsonNode> tempJsonNodes = new ArrayList<>();
				for (int jsonNodesCounter = 0; jsonNodesCounter < jsonNodes.size(); jsonNodesCounter++) {
					tempJsonNodes.addAll(jsonNodes.get(jsonNodesCounter).findValues(data[dataCounter]));
				}
				if (!tempJsonNodes.isEmpty()) {
					jsonNodes = (tempJsonNodes);
				}
				LOGGER.info("We have search value for " + data[dataCounter]);
			}
		}
		List<String> jsonValuesString = new ArrayList<>();
		for (JsonNode jsonNode : jsonNodes) {
			jsonValuesString.add(jsonNode.toString());
		}
		sleeper(500); // this sleeper required for conversion
		return jsonValuesString;
	}

	/**
	 * @param jsonValuesString
	 * @param order            -true = ASC and false = DESC
	 * @return boolean- true if match otherwise false
	 */
	public final boolean verifySorting(List<String> jsonValuesString, boolean order) {
		List<String> sortResult = jsonValuesString.stream().map(String::toLowerCase).collect(Collectors.toList());
		List<String> jsonValuesStringBefore = new ArrayList<>(sortResult);
		String[] SortArr = new String[sortResult.size()];

		// ArrayList to Array Conversion
		for (int i = 0; i < sortResult.size(); i++)
			SortArr[i] = sortResult.get(i);

		if (order) {
			Arrays.sort(SortArr);
		} else {
			Arrays.sort(SortArr, Collections.reverseOrder());
		}
		return jsonValuesStringBefore.equals(sortResult);
	}

	/**
	 * @param replacseString   - replacseString
	 * @param replacingElement - key Element in API query
	 * @param replaceValue     - value
	 * @return String of new query with replaced value
	 */
	public static final String replceAPIStringValue(String replacseString, String replacingElement,
			String replaceValue) {
		String temp1 = replacseString.substring(0,
				replacseString.lastIndexOf(replacingElement.toLowerCase()) + replacingElement.length() + 3);
		String temp2 = replacseString.substring(temp1.length());
		temp2 = temp2.substring(temp2.indexOf(','));
		return temp1 + replaceValue + temp2;
	}

	/**
	 * @param replacseString
	 * @param replaceKey
	 * @return String of new query with replaced value for API KEY
	 */
	public static final String replceAPISortKey(String replacseString, String replaceKey) {
		String temp1 = replacseString.substring(0,
				replacseString.substring(0, replacseString.lastIndexOf("sort_en") + 1).lastIndexOf('\"') + 1);
		String temp2 = replacseString.substring(replacseString.lastIndexOf("sort_en") - 1);
		return temp1 + replaceKey + temp2;
	}

	/**
	 * @param key
	 * @return the text of all present list of elements
	 * @throws Exception
	 */
	public final List<String> getTextOfPresentElementList(String key) throws Exception {
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		List<WebElement> element = getElementsTillAllElementsPresent(key);
		for (WebElement webElement : element) {
			columnNamesOnPage.add(webElement.getText());
		}
		return columnNamesOnPage;
	}

	/**
	 * @param eleLocator -locators of coloum
	 * @param oFlag      - order flag -true for ASC and false for DESC
	 * @throws Exception
	 */
	public final void clickSortIconColumn(String eleLocator, boolean oFlag) throws Exception {
		WebElement columnElement = getElement(eleLocator);
		if (oFlag) {
			if (columnElement.getAttribute(CommonVariables.INNERHTML).contains(CommonVariables.STYLE)) {
				if (columnElement.getAttribute(CommonVariables.INNERHTML).contains("180deg")) {
					LOGGER.info("Alredy in Ascending Order");
				} else {
					columnElement.click();
					sleeper(4000); // this sleeper required for click and revert
				}
			} else {
				columnElement.click();
				sleeper(4000); // this sleeper required for click and revert
			}

		} else {
			if (columnElement.getAttribute(CommonVariables.INNERHTML).contains(CommonVariables.STYLE)) {
				if (columnElement.getAttribute(CommonVariables.INNERHTML)
						.contains("style=\"display: inline-block;\"")) {
					LOGGER.info("Alredy in Descening Order");
				} else {
					columnElement.click();
					sleeper(4000); // this sleeper required for click and revert
				}
			} else {
				columnElement.click();
				sleeper(4000); // this sleeper required for click and revert
				columnElement.click();
				sleeper(4000); // this sleeper required for click and revert
			}
		}
	}

	/**
	 * @param dateEpoch - from API call
	 * @return list of converted date in UTC format
	 */
	public final List<String> getConvertedDate(List<String> dateEpoch) {
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		List<String> element = dateEpoch;
		for (String webElement : element) {
			DateFormat pstFormat = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm:ss aa");
			pstFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = new Date(Long.parseLong(webElement));
			String formatted = pstFormat.format(date);
			columnNamesOnPage.add(formatted);
		}
		return columnNamesOnPage;
	}

	/**
	 * @param
	 * @return
	 * @throws ParseException
	 */
	public final String convertDate(String actualDateFormat, String dateToConvertFormat, String dateText)
			throws ParseException {
		// Convert to required date format
		DateFormat originalFormat = new SimpleDateFormat(actualDateFormat, Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat(dateToConvertFormat);
		Date date = originalFormat.parse(dateText);
		String formattedDate = targetFormat.format(date);
		return formattedDate;
	}

	/*
	 * This method browse file explorer and uploads any type of file just you need
	 * to pass your file location.
	 * 
	 * @param filePath : location of the file.
	 * 
	 */
	public final void verifyUploadFile(String filePath) {

		StringSelection fileLoaction = new StringSelection(filePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(fileLoaction, null);
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(3000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			robot.setAutoDelay(3000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			LOGGER.error("Exception occured while Initializing Robot class to Upload CSV file : " + e.getMessage());
		}
	}

	/*
	 *
	 * This method generate randomString upto 11 characters
	 */
	public final String generateRandomString(int numberOfChars) {
		String randomStr = RandomStringUtils.randomAlphabetic(numberOfChars);
		return randomStr;
	}

	public final void resettoDefaultTableConfiguration() throws Exception {
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		sleeper(3000);
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("resettodefault")) {
			if (tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("resettodefault")) {
				tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			}
		}
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(4000);

	}

	/**
	 * This method is for setting table configuration to default.
	 * 
	 * @throws Exception
	 */

	public final void resetTableConfiguration() throws Exception {
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		tableConfigurationPage = tableConfigurationPage.getInstance();
		// refreshPage();
		sleeper(4000);
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		sleeper(3000);
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("resettodefault")) {
			if (tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("resettodefault")) {
				tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			}
		}
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		if (tableConfigurationPage.verifyElementIsEnableOftableConfigurationPage("resettodefault")) {
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
		}
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(4000);
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter"))
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
	}

	/**
	 * This method is used to get status code from given API URL
	 * 
	 * @param api-        api from you which you want the status code
	 * @param body-       request body
	 * @param requestType - type of request
	 * @param environment - environment of execution
	 * @return
	 */
	public final int getStatusCode(String api, String body, String requestType, String environment) {
		int statusCode = 0;
		Response response;
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
					.header("authorization", "Bearer " + mspAuthToken).body(body);
			switch (requestType.toUpperCase()) {
			case "POST":
				response = httpRequest.post(api);
				statusCode = response.statusCode();
				return statusCode;
			case "GET":
				response = httpRequest.get(api);
				statusCode = response.statusCode();
				return statusCode;
			case "DELETE":
				response = httpRequest.delete(api);
				statusCode = response.statusCode();
				return statusCode;
			case "PUT":
				response = httpRequest.put(api);
				statusCode = response.statusCode();
				return statusCode;
			case "PATCH":
				response = httpRequest.patch(api);
				statusCode = response.statusCode();
				return statusCode;
			default:
				LOGGER.info("Provided : " + requestType + " is wrong");
				throw new InputMismatchException("You can use : GET,POST,DELETE,PUT only ");
			}

		} catch (Exception e) {
			LOGGER.error("Exception in getStatusCode" + e.getMessage());
			return 0;
		}

	}

	/*
	 *
	 * This method generate randomNumber upto 10 characters
	 */
	public final String generateRandomNumber() {
		String randomStr = RandomStringUtils.randomNumeric(10);
		return randomStr;
	}

	/**
	 * This is a method to select all checkboxes of a popup
	 * 
	 * @param firstkey  - locator of the list of checkboxes
	 * @param secondKey - locator of the list
	 * @throws Exception
	 */
	public void selectAllCheckboxOfPopup(String firstkey, String secondKey) throws Exception {
		List<WebElement> firstElement = getElementsTillAllElementsPresent(firstkey);
		List<WebElement> secondElement = getElementsTillAllElementsPresent(secondKey);
		if (firstElement.size() > 0) {
			for (int i = 0; i < firstElement.size(); i++) {
				if (!firstElement.get(i).isSelected()) {
					sleeper(500);
					secondElement.get(i).click();
				}
			}
		} else {
			LOGGER.error("Select checkbox failed due to list Size getting less than zero" + firstElement.size());
		}
	}

	/**
	 * This method does below: - takes all filenames from Credentials folder and
	 * convert them to a list. - loop through each filename, split filename using
	 * (.) and take substring of it - compares environment name from pom.xml and and
	 * filename from substring, both in UpperCase. - if both are equal, then assign
	 * filename to finalEnvName variable and return it.
	 * 
	 * @param environment - environment fetched from pom.xml
	 * @return - case converted environment name
	 */

	public static String convertEnvironmentCase(String environment) {
		String filename = "";
		String finalEnvName = "";
		File propertiesFile = new File(ConstantPath.CREDENTIALS_FOLDER_PATH);
		String[] propertiesFileNameList = propertiesFile.list();
		for (int propFileCounter = 0; propFileCounter < propertiesFileNameList.length; propFileCounter++) {
			filename = propertiesFileNameList[propFileCounter];
			filename = filename.substring(0, filename.indexOf('.'));
			if ((filename.toUpperCase()).equals(environment.toUpperCase())) {
				finalEnvName = filename;
				break;
			} else
				LOGGER.error("Environment name fetched from pom.xml & properties file didn't match");
		}
		return finalEnvName;
	}

	/**
	 * This method read data specific to environment
	 * 
	 * @param fileName - properties filename
	 * @param key      - key to read
	 * @return - key specific data
	 * @throws IOException
	 * @throws Exception
	 */
	public final static String getEnvironmentSpecificData(String fileName, String key) {
		Properties envDataPageProperties = null;
		try {
			// Due to case sensitivity on LINUX, convertEnvironmentCase() is used to convert
			// the case environment name in required format to match with properties file
			// name format.
			if (!Strings.isNullOrEmpty(osName) && osName.toUpperCase().contains("LINUX")) {
				fileName = convertEnvironmentCase(fileName);
			}
			try {
				envDataPageProperties = envDataPropertiesReader.getObjectRepository(fileName + "Data");
			} catch (IOException e) {
				LOGGER.error("Exception in getEnvironmnetSpecificData: " + e.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception in specified environment format. It should match environment specific properties file format. "
							+ e.getMessage());
		}
		return envDataPageProperties.getProperty(key);

	}

	/**
	 * This method basically works on drag and drop operation.
	 * 
	 * @param sourceKey      - locator of the source
	 * @param destinationKey - locator of the destination
	 * @throws Exception
	 */
	public final boolean dragAndDropOperaton(WebElement sourceKey, WebElement destinationKey) {
		boolean flag = false;
		try {
			new Actions(PreDefinedActions.getDriver()).moveToElement(sourceKey).clickAndHold(sourceKey)
					.pause(Duration.ofSeconds(5)).moveByOffset(10, 0).moveToElement(destinationKey).moveByOffset(20, 0)
					.pause(Duration.ofSeconds(5)).release().perform();
			flag = true;
		} catch (Exception e) {
			LOGGER.error("Drag and Drop not happening due to this error" + e);
		}
		return flag;
	}

	/**
	 * This method sets property of input type to be used for importing file
	 * 
	 * @param locator
	 */
	public final void setAttributeForImport(String locator) {
		WebElement element = getDriver().findElement(this.getObject(locator));
		jsDriver().executeScript("arguments[0].style='display: block;'", element);
	}

	/**
	 * This method is to verify the toggle for particular chart.
	 * 
	 * @param toggleName: Name of the toggle.
	 * @param emailId:    Name of the user email.
	 * @return boolean value it may be return true or false.
	 * @throws Exception
	 */
	public final boolean toggleVerification(String toggleName, String emailId) throws Exception {
		boolean flag = false;
		LaunchDarkly ldinstance_dash = new LaunchDarkly();
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(mspAuthToken));
		String userId = jsonAuthToken.get("idp_user_id").toString();
		if (ldinstance_dash.enabled(toggleName, userId, emailId, false) == true) {
			flag = true;
			LOGGER.info(toggleName + " Toggle is enabled !!!");
		} else {
			flag = false;
			LOGGER.info(toggleName + " Toggle is disabled !!!");
		}
		return flag;
	}

	public final boolean isFeatureToggleEnabled(String toggleName) throws Exception {
		if (toggleName == null || toggleName.isEmpty()) {
			throw new IllegalArgumentException("Toggle name cannot be null or empty");
		}
		try {
			LaunchDarkly ldInstance = new LaunchDarkly();
			boolean isEnabled = ldInstance.enabled(toggleName, "", "", false);
			LOGGER.info("{} Toggle is {}", toggleName, isEnabled ? "enabled" : "disabled");
			return isEnabled;

		} catch (Exception e) {
			LOGGER.error("Error checking toggle status for '{}': {}", toggleName, e.getMessage());
			throw new Exception("Failed to check toggle status: " + e.getMessage());
		}
	}

	/**
	 * This method is to get value from token for given json key
	 * 
	 * @param jsonKey key for which we need value.
	 * @return String value for given key
	 * @throws Exception
	 */
	public final String getValueFromToken(String jsonKey) throws Exception {
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(getTokenFromLocalStorage("dui_token_e")));
		String value = jsonAuthToken.get(jsonKey).toString();
		return value;
	}

	/*
	 * Common method to verify search functionality inside single select drop down
	 */

	public final boolean verifySingleSelectDropdownByText(String LanguageCode, String textKey, String Text,
			String emptyTextKey, String listKey, String dropdownBoxKey) throws Exception {
		enterText(textKey, Text);
		boolean flag = false;
		sleeper(5000);
		String empty_text = null;

		if (verifyElementIsPresent(emptyTextKey)) {
			empty_text = getTextBy(emptyTextKey);
			if (empty_text.equals(CompaniesVariables.COMPANY_SEARCH_EMPTY_DASHBOARD + " " + Text)) {
				flag = false;
				LOGGER.info("Company not found in drop down list.");
				clearText(textKey);
				pressKey(Keys.ESCAPE);
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(listKey);
			for (WebElement webElement : elements) {
				if (webElement.getText().contains(Text)) {
					String dropdownListText = webElement.getText();
					webElement.click();
					sleeper(3000);
					String text = getTextBy(dropdownBoxKey);
					if (text.equalsIgnoreCase(dropdownListText)
							|| text.contains(Integer.toString(CompaniesVariables.SELECTED_COMPANY_COUNT1) + " "
									+ getTextLanguage(LanguageCode, "daas_ui", "dropdown.selected"))) {
						flag = true;
						pressKey(Keys.ESCAPE);
						break;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 
	 * @param languageCode  : Language code for localization testing
	 * @param parentMenuKey : Root of the side menu key.
	 * @param childMenuKey  : Sub-root of the side menu key.
	 * @throws Exception
	 */
	public final void sideMenuSelection(String languageCode, String parentMenuKey, String childMenuKey)
			throws Exception {
		WebElement parentMenu = getElement(parentMenuKey);
		WebElement childMenu = getElement(childMenuKey);

		if (parentMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.administrative"))) {
			parentMenu.click();
			waitForPageLoaded();
			sleeper(2000);
			LOGGER.info(parentMenu.getText() + " Root menu click successfully !!!");
			if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.root.users"))) {
				childMenu.click();
				waitForPageLoaded();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
			} else if (childMenu.getText()
					.equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.subscriptionManagement"))) {
				childMenu.click();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
			} else if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.whatsNew"))) {
				childMenu.click();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
            } else if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.orders"))) {
                childMenu.click();
                LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
            } else if (childMenu.getText().equals("File Processing")) {
                childMenu.click();
                LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
            } else {
                LOGGER.info(childMenu.getText() + " Sub-Root menu does not match !!!");
            }
		} else if (parentMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.msp"))) {
			parentMenu.click();
			waitForPageLoaded();
			sleeper(2000);
			LOGGER.info(parentMenu.getText() + " Root menu click successfully !!!");
			if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.msp"))) {
				childMenu.click();
				waitForPageLoaded();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
			} else if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.mspUsers"))) {
				childMenu.click();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
			} else {
				LOGGER.info(childMenu.getText() + " Sub-Root menu does not match !!!");
			}
		} else if (parentMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.partners"))) {
			parentMenu.click();
			waitForPageLoaded();
			sleeper(2000);
			LOGGER.info(parentMenu.getText() + " Root menu click successfully !!!");
			if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.partners"))) {
				childMenu.click();
				waitForPageLoaded();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
			} else if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.partnerUsers"))) {
				childMenu.click();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
			} else {
				LOGGER.info(childMenu.getText() + " Sub-Root menu does not match !!!");
			}
		} else if (parentMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.customers"))) {
			parentMenu.click();
			waitForPageLoaded();
			LOGGER.info(parentMenu.getText() + " Root menu click successfully !!!");
			if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.partnerCompanies"))) {
				childMenu.click();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
			} else if (childMenu.getText().equals(getTextLanguage(languageCode, "daas_ui", "sidemenu.customerUsers"))) {
				childMenu.click();
				LOGGER.info(childMenu.getText() + " Sub-Root menu click successfully !!!");
			} else {
				LOGGER.info(childMenu.getText() + " Sub-Root menu does not match !!!");
			}
		} else {
			LOGGER.info(parentMenu.getText() + " Root menu does not match !!!");
		}
	}

	/**
	 * Method to expand menu tab when screen resolution is small
	 * 
	 */
	public final void expandMenuIcon2() throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
		dashboardPage = dashboardPage.getInstance();
		if (!dashboardPage.waitForElementsOfDashboardPage("dashboardTab")) {
			dashboardPage.clickOnElementsOfDashboardPage("menuIcon");
		}
	}

	/**
	 * This is the method to navigate to users tab under partners menu
	 */
	public final void gotoPartnerUsersTab() {
		try {
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIcon2();
			if (!partnerPage.getAttributesOfPartnerPage("partnersTab", "class").contains("expanded")) {
				partnerPage.clickOnElementsOfPartnerPage("partnersTab");
				LOGGER.info("Clicked on partners tab");
			} else {
				LOGGER.info("Partners menu already expanded");
			}
			partnerPage.clickOnElementsOfPartnerPage("partnerUsersTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoPartnerUsersTab " + e.getMessage()));
		}
	}

	/**
	 * This is the method to navigate to users tab under MSP menu
	 */
	public final void gotoMSPUsersTab() {
		try {
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIcon2();
			if (!partnerPage.getAttributesOfPartnerPage("mspTab", "class").contains("expanded")) {
				partnerPage.clickOnElementsOfPartnerPage("mspTab");
				LOGGER.info("Clicked on MSP tab");
			} else {
				LOGGER.info("MSPs menu already expanded");
			}
			partnerPage.clickOnElementsOfPartnerPage("mspUsersTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoMSPUsersTab " + e.getMessage()));
		}
	}

	/**
	 * This is the method to navigate to users tab under companies menu
	 */
	public final void gotoCompaniesUsersTab() throws Exception {
		try {
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIcon2();
			if (!partnerPage.getAttributesOfPartnerPage("customersTab", "class").contains("expanded")) {
				partnerPage.clickOnElementsOfPartnerPage("customersTab");
				LOGGER.info("Clicked on customers tab");
			} else {
				LOGGER.info("Customers menu already expanded");
			}
			partnerPage.clickOnElementsOfPartnerPage("customerUsersTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoCompaniesUsersTab " + e.getMessage()));
		}
	}

	/**
	 * This is the method to navigate to users tab under Administrative menu
	 */
	public final void gotoAdministrativeUsersTab() throws Exception {
		try {
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			expandMenuIcon2();
			if (!partnerPage.getAttributesOfPartnerPage("administrativeTab", "class").contains("expanded")) {
				partnerPage.clickOnElementsOfPartnerPage("administrativeTab");
				LOGGER.info("Clicked on Administrative tab");
			} else {
				LOGGER.info("Administrative menu already expanded");
			}
			partnerPage.clickOnElementsOfPartnerPage("administrativeUsersTab");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method gotoAdministrativeUsersTab " + e.getMessage()));
		}
	}

	/**
	 * This method is for setting table configuration to default For Workflow.
	 * 
	 * @throws Exception
	 */

	public final void resetTableConfigurationForWorkflow() throws Exception {
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.mouseHoverOnTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickByJavaScriptOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickByJavaScriptOnElementsOfTableConfigurationPage("saveButton");
		tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickByJavaScriptOnElementsOfTableConfigurationPage("tableConfigurationButton");
		sleeper(3000);
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("resettodefault"))
			tableConfigurationPage.clickByJavaScriptOnElementsOfTableConfigurationPage("resettodefault");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickByJavaScriptOnElementsOfTableConfigurationPage("saveButton");
		sleeper(4000);
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
		}
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableOverlay");
	}

	/**
	 * This method is to get unique list of elements
	 * 
	 * @param list - list of elements
	 * @return - set of strings
	 * @throws Exception
	 */
	public final HashSet<String> getUniqueList(List<String> list) throws Exception {
		return new HashSet<String>(list);
	}

	/**
	 * This method is used for clearing any filters applied
	 * 
	 * @param clearFilterKey - locator of clear button
	 * @throws Exception
	 */
	public final void clearFilters(String clearFilterKey) throws Exception {
		if (verifyElementIsPresent(clearFilterKey)) {
			click(clearFilterKey);
			sleeper(3000);
			LOGGER.info("Filters cleared successfully");
		} else
			LOGGER.info("No filters are applied");
	}

	/**
	 * This function return the download folder count.
	 * 
	 * @param path:path of download folder.
	 * @return
	 */
	public final int countFolderFile(String path) {
		int count = new File(path).list().length;
		return count;
	}

	/**
	 * This is the method to get current year
	 *
	 */
	public final String getCurrentYear() {
		Date date = new Date();
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy");
		String currentYear = formatTime.format(date);
		return currentYear;
	}

	/**
	 * Method to get browser tab name
	 * 
	 */
	public final String getBrowserTabName() {
		String title = null;
		try {
			title = getDriver().getTitle();
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getBrowserTabName " + e.getMessage()));
		}
		return title;
	}

	/**
	 * This method is used to get response from given API URL
	 * 
	 * @param api-        api from you which you want the status code
	 * @param body-       request body
	 * @param requestType - type of request
	 * @return Response
	 */
	public final String getResponse(String api, String body, String requestType) {
		Response response = null;
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
					.header("authorization", "Bearer " + mspAuthToken).body(body);
			switch (requestType.toUpperCase()) {
			case "POST":
				response = httpRequest.post(api);
				return response.asString();
			case "GET":
				response = httpRequest.get(api);
				return response.asString();
			case "DELETE":
				response = httpRequest.delete(api);
				return response.asString();
			case "PUT":
				response = httpRequest.put(api);
				return response.asString();
			case "PATCH":
				response = httpRequest.patch(api);
				return response.asString();
			default:
				LOGGER.info("Provided : " + requestType + " is wrong");
				throw new InputMismatchException("You can use : GET,POST,DELETE,PUT only ");
			}

		} catch (Exception e) {
			LOGGER.error("Exception in getStatusCode" + e.getMessage());
		}
		return null;
	}

	/**
	 * This method is used to set default dashboard.
	 * 
	 * @param LanguageCode - POM.xml language
	 * @return True or false
	 */
	public final boolean setToDefaultDashboard(String LanguageCode) {
		boolean flag = false;

		try {
			DashboardPage dashboardpage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			dashboardpage.waitForPageLoaded();
			dashboardpage.verifyElementsOfDashboardPage("dashboardListDropdown");
			sleeper(3000);
			if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))) {
				dashboardpage.clickOnElementsOfDashboardPage("editButton");
				dashboardpage.clickOnElementsOfDashboardPage("resetDashboardOption");
				dashboardpage.clickOnElementsOfDashboardPage("resetOkButton");
			} else {
				dashboardpage.clickOnElementsOfDashboardPage("dashboardListDropdown");
				List<WebElement> dashboardListElements = dashboardpage
						.getElementsTillAllElementsVisibleofDashboardPage("dashboardList");
				if (dashboardListElements.size() != 0) {
					for (WebElement we : dashboardListElements) {
						if (we.getText()
								.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))) {
							we.click();
							dashboardpage.clickOnElementsOfDashboardPage("editButton");
							dashboardpage.clickOnElementsOfDashboardPage("resetDashboardOption");
							dashboardpage.clickOnElementsOfDashboardPage("resetOkButton");
							break;
						}
					}
				} else {
					LOGGER.info("Dashboard list is empty");
					flag = false;
				}
			}
			dashboardpage.waitForPageLoaded();
			sleeper(3000);
			if (dashboardpage.waitForElementsOfDashboardPageDynamic("allCompanyTextFlexi", 5)) {
				if (dashboardpage.getTextOfDashboardPage("allCompanyTextFlexi")
						.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
					LOGGER.info("No filter is applied in companies dropdown.");
				} else {
					dashboardpage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				}
			}
			dashboardpage.verifyElementsOfDashboardPage("timeDurationMonth");
			sleeper(3000);
			if (dashboardpage.getTextOfDashboardPage("timeDurationMonth").equalsIgnoreCase(getTextLanguage(LanguageCode,
					"daas_ui", "flexible_dashboard.action_bar.select.placeholder.select_time_duration"))) {
				LOGGER.info("No filter is applied in time duration dropdown.");
			} else {
				dashboardpage.clickOnElementsOfDashboardPage("timeDurationMonth");
				List<WebElement> monthElements = dashboardpage
						.getElementsTillAllElementsVisibleofDashboardPage("selectedMonth");
				if (monthElements.size() != 0) {
					for (WebElement we : monthElements) {
						we.click();
					}
					dashboardpage.waitForPageLoaded();
				}
			}

			if (dashboardpage.waitForElementsOfDashboardPageDynamic("allCompanyTextFlexi", 5)) {
				if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
						.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))
						&& dashboardpage.getTextOfDashboardPage("allCompanyTextFlexi")
								.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))
						&& dashboardpage.getTextOfDashboardPage("timeDurationMonth")
								.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
										"flexible_dashboard.action_bar.select.placeholder.select_time_duration"))) {
					flag = true;
					LOGGER.info("No filters applied, dashboard is reset to default.");
				}
			} else {
				if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
						.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))
						&& dashboardpage.getTextOfDashboardPage("timeDurationMonth")
								.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
										"flexible_dashboard.action_bar.select.placeholder.select_time_duration"))) {
					flag = true;
					LOGGER.info("No filters applied, dashboard is reset to default.");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception is occured in reset default dashboard" + e.getMessage());
			return false;
		}
		return flag;
	}

	/**
	 * This method is used to set default dashboard.
	 * 
	 * @param LanguageCode - POM.xml language
	 * @return True or false
	 */
	public final boolean setToDefaultDashboardGlobalFilter(String LanguageCode) {
		boolean flag = false;

		try {
			DashboardPage dashboardpage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			dashboardpage.waitForPageLoaded();
			dashboardpage.verifyElementsOfDashboardPage("dashboardListDropdown");
			sleeper(3000);
			if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))) {
				dashboardpage.clickOnElementsOfDashboardPage("editButton");
				dashboardpage.clickOnElementsOfDashboardPage("resetDashboardOption");
				sleeper(2000);
				dashboardpage.clickOnElementsOfDashboardPage("resetOkButton");
			} else {
				dashboardpage.clickOnElementsOfDashboardPage("dashboardListDropdown");
				List<WebElement> dashboardListElements = dashboardpage
						.getElementsTillAllElementsVisibleofDashboardPage("dashboardList");
				if (dashboardListElements.size() != 0) {
					for (WebElement we : dashboardListElements) {
						if (we.getText()
								.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))) {
							we.click();
							dashboardpage.clickOnElementsOfDashboardPage("editButton");
							dashboardpage.clickOnElementsOfDashboardPage("resetDashboardOption");
							sleeper(2000);
							dashboardpage.clickOnElementsOfDashboardPage("resetOkButton");
							break;
						}
					}
				} else {
					LOGGER.info("Dashboard list is empty");
					flag = false;
				}
			}
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				dashboardpage.waitForPageLoaded();
				sleeper(3000);
				if (dashboardpage.waitForElementsOfDashboardPageDynamic("allCompanyTextFlexiGlobalFilter", 5)) {
					if (dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
						LOGGER.info("No filter is applied in companies dropdown.");
					} else if (dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "assets.list.bromius.disabled"))) {
						LOGGER.info("Global filter is disabled for the user.");
					} else {
						dashboardpage.clickOnElementsOfDashboardPage("globalCompanyFilter");
						dashboardpage.clickOnElementsOfDashboardPage("clearCompanyLevel0");
						dashboardpage.clickOnElementsOfDashboardPage("globalCompanyFilterApplyButton");
					}
				}
				if (dashboardpage.verifyElementsOfDashboardPage("timeDurationMonth")) {
					sleeper(3000);
					if (dashboardpage.getTextOfDashboardPage("timeDurationMonth").equalsIgnoreCase("")) {
						LOGGER.info("No filter is applied in time duration dropdown.");
					} else {
						dashboardpage.clickOnElementsOfDashboardPage("timeDurationMonth");
						List<WebElement> monthElements = dashboardpage
								.getElementsTillAllElementsVisibleofDashboardPage("selectedMonth");
						if (monthElements.size() != 0) {
							for (int i = 0; i < monthElements.size(); i++) {
								if (monthElements.get(i).getAttribute("aria-selected").equalsIgnoreCase("true")) {
									monthElements.get(i).click();
								}
							}
							dashboardpage.waitForPageLoaded();
							pressKey(Keys.ESCAPE);
						}
					}
				}
				if (dashboardpage.waitForElementsOfDashboardPageDynamic("allCompanyTextFlexiGlobalFilter", 5)) {
					if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))
							&& dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter")
									.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))
							&& dashboardpage.getTextOfDashboardPage("timeDurationMonth").equalsIgnoreCase("")) {
						flag = true;
						LOGGER.info("No filters applied, dashboard is reset to default.");
					} else if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))
							&& dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter").equalsIgnoreCase(
									getTextLanguage(LanguageCode, "daas_ui", "assets.list.bromius.disabled"))
							&& dashboardpage.getTextOfDashboardPage("timeDurationMonth").equalsIgnoreCase("")) {
						flag = true;
						LOGGER.info("Global filter is disabled for user, dashboard is reset to default.");
					}
				} else {
					if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))
							&& dashboardpage.getTextOfDashboardPage("timeDurationMonth").equalsIgnoreCase("")) {
						flag = true;
						LOGGER.info("No filters applied, dashboard is reset to default.");
					}
				}
			} else {
				dashboardpage.waitForPageLoaded();
				sleeper(3000);
				if (dashboardpage.waitForElementsOfDashboardPageDynamic("allCompanyTextFlexiGlobalFilter", 5)) {
					if (dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
						LOGGER.info("No filter is applied in companies dropdown.");
					} else if (dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "assets.list.bromius.disabled"))) {
						LOGGER.info("Global filter is disabled for the user.");
					} else {
						dashboardpage.clickOnElementsOfDashboardPage("clearCompanyFlexiGlobalFilter");
					}
				}
				if (dashboardpage.verifyElementsOfDashboardPage("timeDurationMonth")) {
					sleeper(3000);
					if (dashboardpage.getTextOfDashboardPage("timeDurationMonth")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
									"flexible_dashboard.action_bar.select.placeholder.select_time_duration"))) {
						LOGGER.info("No filter is applied in time duration dropdown.");
					} else {
						dashboardpage.clickOnElementsOfDashboardPage("timeDurationMonth");
						List<WebElement> monthElements = dashboardpage
								.getElementsTillAllElementsVisibleofDashboardPage("selectedMonth");
						if (monthElements.size() != 0) {
							for (WebElement we : monthElements) {
								we.click();
							}
							dashboardpage.waitForPageLoaded();
							pressKey(Keys.ESCAPE);
						}
					}
				}

				if (dashboardpage.waitForElementsOfDashboardPageDynamic("allCompanyTextFlexiGlobalFilter", 5)) {
					if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))
							&& dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter")
									.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))
							&& dashboardpage.getTextOfDashboardPage("timeDurationMonth")
									.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
											"flexible_dashboard.action_bar.select.placeholder.select_time_duration"))) {
						flag = true;
						LOGGER.info("No filters applied, dashboard is reset to default.");
					} else if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))
							&& dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter").equalsIgnoreCase(
									getTextLanguage(LanguageCode, "daas_ui", "assets.list.bromius.disabled"))
							&& dashboardpage.getTextOfDashboardPage("timeDurationMonth")
									.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
											"flexible_dashboard.action_bar.select.placeholder.select_time_duration"))) {
						flag = true;
						LOGGER.info("Global filter is disabled for user, dashboard is reset to default.");
					}
				} else {
					if (dashboardpage.getTextOfDashboardPage("dashboardListDropdown")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "catalog.type.default"))
							&& dashboardpage.getTextOfDashboardPage("timeDurationMonth")
									.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
											"flexible_dashboard.action_bar.select.placeholder.select_time_duration"))) {
						flag = true;
						LOGGER.info("No filters applied, dashboard is reset to default.");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception is occured in reset default dashboard" + e.getMessage());
			return false;
		}
		return flag;
	}

	public void clearCompanyFilterDashboard(String LanguageCode) throws Exception {
		DashboardPage dashboardpage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (dashboardpage.waitForElementsOfDashboardPageDynamic("allCompanyTextFlexiGlobalFilter", 5)) {
			if (dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
				LOGGER.info("No filter is applied in companies dropdown.");
			} else if (dashboardpage.getTextOfDashboardPage("allCompanyTextFlexiGlobalFilter")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.list.bromius.disabled"))) {
				LOGGER.info("Global filter is disabled for the user.");
			} else {
				dashboardpage.clickOnElementsOfDashboardPage("globalCompanyFilter");
				dashboardpage.clickOnElementsOfDashboardPage("clearCompanyLevel0");
				dashboardpage.clickOnElementsOfDashboardPage("globalCompanyFilterApplyButton");
			}
		}
	}

	/**
	 * This method will use dynamic token to get response from api
	 * 
	 * @param token_api    - Token API URL without resource
	 * @param clientId     - client_id key
	 * @param clientSecret - client_secret key
	 * @param grantType    - grant_type
	 * @param resource     - resource
	 * @param api          - API URL
	 * @param requestType  - request type (Get,post,put)
	 * @param tokenType    - token type required
	 * @param body         - payload
	 * @return - return response in string format
	 */
	public final String getToken(String token_api, String clientId, String clientSecret, String grantType,
			String resource, String api, String requestType, String tokenType, String body) {
		Response response = null;
		try {
			Response request = RestAssured.given().baseUri(token_api)
					.contentType(ContentType.URLENC.withCharset("UTF-8")).formParam("client_id", clientId)
					.formParam("client_secret", clientSecret).formParam("grant_type", grantType).post(resource);
			JsonPath js = new JsonPath(request.asString());
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
					.header("authorization", "Bearer " + js.get(tokenType)).body(body);
			switch (requestType.toUpperCase()) {
			case "POST":
				response = httpRequest.post(api);
				return response.asString();
			case "GET":
				response = httpRequest.get(api);
				return response.asString();
			case "DELETE":
				response = httpRequest.delete(api);
				return response.asString();
			case "PUT":
				response = httpRequest.put(api);
				return response.asString();
			case "PATCH":
				response = httpRequest.patch(api);
				return response.asString();
			default:
				LOGGER.info("Provided : " + requestType + " is wrong");
				throw new InputMismatchException("You can use : GET,POST,DELETE,PUT,PATCH only ");
			}

		} catch (Exception e) {
			LOGGER.error("Exception in getStatusCode" + e.getMessage());
		}
		return null;
	}

	/**
	 * This is the method to get current date in MMMMM d, YYYY format
	 * 
	 * @return
	 */
	public final String getCurrentMonthDateYear() {
		Date date = new Date();
		SimpleDateFormat formatTime = new SimpleDateFormat("MMMMM d, YYYY");
		String currentMonthDateYear = formatTime.format(date);
		return currentMonthDateYear;
	}

	/**
	 * This is the method to add days to current date
	 * 
	 * @days - days to add
	 * @return
	 */
	public final String addDaysToCurrentDate(int days) {
		SimpleDateFormat formatTime = new SimpleDateFormat("MMMMM d, YYYY");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		String newDate = formatTime.format(cal.getTime());
		return newDate;
	}

	/**
	 * This is a method to select date from calendar Date Picker
	 * 
	 * @param date                  - current date
	 * @param monthKeyCurrent       - locator of current month
	 * @param rightArrowKey         - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month
	 */
	public final void selectDateFromCalenderDatePicker(String date, String monthKeyCurrent, String rightArrowKey,
			String daysOnCurrentMonthKey) {
		try {
			String[] strArray = date.split(" ");
			String day = strArray[1].replaceAll(",", "");
			sleeper(1000);
			while (!(getTextBy(monthKeyCurrent).contains(strArray[0] + " " + strArray[2]))) {
				click(rightArrowKey);
				sleeper(6000); // this sleeper is needed to allow the calendar to go to the next set of months
			}
			List<WebElement> datesOnCurrentMonth = getElementsTillAllElementsVisible(daysOnCurrentMonthKey);
			int countOfDaysOnRightSide = datesOnCurrentMonth.size();

			if (getTextBy(monthKeyCurrent).contains(strArray[0] + " " + strArray[2])) {
				for (int daysCounter = 0; daysCounter < countOfDaysOnRightSide; daysCounter++) {
					String text = datesOnCurrentMonth.get(daysCounter).getText();
					if (text.equalsIgnoreCase(day)) {
						datesOnCurrentMonth.get(daysCounter).click();
						break;
					}
				}
			} else {
				LOGGER.info("Date not available on calender.");
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderDatePicker " + e.getMessage()));
		}
	}

	/**
	 * This is a method to select date from calendar having month and year separate
	 * drop down
	 * 
	 * @param date                  - current date
	 * @param monthKeyCurrent       - locator of current month
	 * @param rightArrowKey         - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month
	 */
	public final void selectDateFromCalender(String date, String monthKeyCurrent, String rightArrowKey,
			String daysOnCurrentMonthKey) {
		try {
			String[] strArray = date.split(" ");
			String day = strArray[1].replaceAll(",", "");
			sleeper(2000);
			while (!(strArray[0] + " " + strArray[2]).contains(getTextBy(monthKeyCurrent))) {
				click(rightArrowKey);
				sleeper(6000); // this sleeper is needed to allow the calendar to go to the next set of months
			}
			List<WebElement> datesOnCurrentMonth = getAllElements(daysOnCurrentMonthKey);
			int countOfDaysOnRightSide = datesOnCurrentMonth.size();

			if ((strArray[0] + " " + strArray[2]).contains(getTextBy(monthKeyCurrent))) {
				for (int daysCounter = 0; daysCounter < countOfDaysOnRightSide; daysCounter++) {
					String text = datesOnCurrentMonth.get(daysCounter).getText();
					if (text.equalsIgnoreCase(day)) {
						datesOnCurrentMonth.get(daysCounter).click();
						break;
					}
				}
			} else {
				LOGGER.info("Date not available on calender.");
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderDatePicker " + e.getMessage()));
		}
	}

	/*
	 * This is a method to get filenames as per operating system
	 * 
	 * @param key - argument passed for driver and log file
	 * 
	 * @return - filename as per argument and operating system
	 */

	public static String passFileName(String key) {
		try {
			if (!Strings.isNullOrEmpty(osName) && osName.toUpperCase().contains("LINUX")) {
				if (key.equals("chromedriverfile")) {
					fileName = "chromedriver";
				}
				if (key.equals("logfile")) {
					fileName = "log4j.properties";
				}
			} else {
				if (key.equals("chromedriverfile")) {
					fileName = "chromedriver.exe";
				}
				if (key.equals("logfile")) {
					fileName = "Log4j.properties";
				}
			}
		} catch (Exception e) {
			LOGGER.error(("Exception in getting OS name in passFileName() method. " + e.getMessage()));
		}

		return fileName;
	}

	/**
	 * This is a method to select date from calendar Date Picker
	 * 
	 * @param date                  - current date
	 * @param monthKeyCurrent       - locator of current month
	 * @param rightArrowKey         - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month
	 */
	public final void selectDateFromCalenderDatePickerContainer(String date, String monthKeyCurrent,
			String rightArrowKey, String daysOnCurrentMonthKey) {
		try {
			String[] strArray = date.split(" ");
			// String day = strArray[1].replaceAll(",", "");
			sleeper(1000);
			while (!(getTextBy(monthKeyCurrent).contains(strArray[0]))) {
				click(rightArrowKey);
				sleeper(6000); // this sleeper is needed to allow the calendar to go to the next set of months
				// click(rightArrowKey); // click event perform 2 times because datepicker
				// automatic position change bug
			}
			List<WebElement> datesOnCurrentMonth = getAllElements(daysOnCurrentMonthKey);
			int countOfDaysOnRightSide = datesOnCurrentMonth.size();

			if (getTextBy(monthKeyCurrent).contains(strArray[0])) {
				for (int daysCounter = 0; daysCounter < countOfDaysOnRightSide; daysCounter++) {
					String text = datesOnCurrentMonth.get(daysCounter).getAttribute("aria-label");
					if (text.contains(date)) {
						datesOnCurrentMonth.get(daysCounter).click();
						sleeper(3000);
						break;
					}
				}
			} else {
				LOGGER.info("Date not available on calender.");
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderDatePickerContainer " + e.getMessage()));
		}
	}

	/**
	 * This is the method to remove locator filter
	 * 
	 * @throws Exception
	 *
	 */
	public final void removeLocationFilter() throws Exception {
		DashboardPage dashboardpage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardpage.waitForPageLoaded();
		if (dashboardpage.verifyElementsOfDashboardPage("clearLocationFilter"))
			dashboardpage.clickOnElementsOfDashboardPage("clearLocationFilter");
		else
			LOGGER.info("Location filter is empty");
	}

	/**
	 * This method will select multiple MSPs from the dropdown
	 * 
	 * @param mspList of MSPs from the dropdown
	 * @throws Exception
	 */
	public final void selectMSPs(List<WebElement> mspList) throws Exception {
		if (mspList.size() >= 0) {
			for (int i = 0; i < 2; i++) {
				mspList.get(i).click();
			}
		}
	}

	/**
	 * This method is used to verify columns and count between widget and details
	 * grid on dashboard drill down.
	 * 
	 * @param languageCode
	 * @param countOnWidgetLocator
	 * @param detailsLocator
	 * @param widgetType
	 * @param expectedList
	 * @return flag indicating that count and column matched or not in widget and
	 *         details table
	 */
	public boolean verifyDetailsGridForKPIWidgetsOnDashboard(String languageCode, String countOnWidgetLocator,
			String detailsLocator, String widgetType, List<String> expectedList) {
		boolean flag = false;
		// int noOfRows;
		String countOnDetailsInitial;
		try {
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			String countOnWidget = dashboardPage.getTextOfDashboardPage(countOnWidgetLocator);
			if (widgetType.equalsIgnoreCase("win11")) {
				String[] splitArrayOfcountOnWidget = countOnWidget.split(" ");
				countOnWidget = splitArrayOfcountOnWidget[0];
			}
			if (widgetType.equalsIgnoreCase("fleet")) {
				String[] splitArrayOfcountOnWidget = countOnWidget.split("\n");
				countOnWidget = splitArrayOfcountOnWidget[0].replace(",", "");
			}
			dashboardPage.mouseHoverOfDashboardPage(detailsLocator);
			dashboardPage.clickByJavaScriptOnDashboardPage(detailsLocator);
			switchToDifferentTab();
			LOGGER.info("Switched to other tab");
			waitForPageLoaded();
			if (getUrlOfCurrentPage().contains("gridData=")) {
				countOnDetailsInitial = dashboardPage.getTextOfDashboardPage("tableCountOnFooterGrid");
			} else {
				countOnDetailsInitial = dashboardPage.getTextOfDashboardPage("tableCountOnFooterV2");
			}
			String[] splitArrayOfCountOnDetailsInitial = countOnDetailsInitial.split(" ");
			String finalCountOnDetailsFooter = splitArrayOfCountOnDetailsInitial[1];
			List<WebElement> actualList = new ArrayList<WebElement>();
			// List<WebElement> actualNoOfRows = new ArrayList<WebElement>();
			if (getUrlOfCurrentPage().contains("gridData=")) {
				actualList = dashboardPage.getElementsOfDashboardPage("tableColumnListGrid");
			} else {
				actualList = dashboardPage.getElementsOfDashboardPage("tableColumnList");
			}
			if (expectedList.size() == actualList.size()) {
				if (actualList.size() > 0) {
					for (int counter = 0; counter < actualList.size(); counter++) {
						if (!(expectedList.get(counter).trim()
								.equalsIgnoreCase(actualList.get(counter).getText().trim()))) {
							LOGGER.info("Columns mismatch" + actualList.get(counter).getText());
							return flag;
						}
					}
				} else {
					LOGGER.info("Column list is empty");
					return flag;
				}

			} else {
				LOGGER.info("Columns mismatch");
				return flag;
			}
			flag = true;
			// verify that count between widget and table footer is same.
			if (countOnWidget.equalsIgnoreCase(String.valueOf(finalCountOnDetailsFooter))) {
				LOGGER.info("Count matched between widget and details table footer");
			} else {
				LOGGER.info("Count mismatch between widget and details table footer");
				flag = false;
			}

			/*
			 * //verify that count between no of rows and table footer is same.
			 * if(finalCountOnDetailsFooter.equals("0")) {
			 * dashboardPage.verifyElementsOfDashboardPage("tableNoDataToDisplay"); }else {
			 * actualNoOfRows = dashboardPage.getElementsOfDashboardPage("tableNoOfRows");
			 * noOfRows = actualNoOfRows.size();
			 * if(String.valueOf(noOfRows).equalsIgnoreCase(finalCountOnDetailsFooter)) {
			 * LOGGER.
			 * info("Count matched between no of rows in table and details table footer");
			 * }else { LOGGER.
			 * info("Count mismatch between no of rows in table and details table footer");
			 * flag = false; }}
			 */
		} catch (Exception e) {
			flag = false;
			LOGGER.error("Exception occured in columnHeadersTestNew" + e.getMessage());
			return flag;
		} finally {
			switchBackToPreviousTab();
		}
		return flag;
	}

	/*
	 * This is the method to generate current date in given format
	 * 
	 */
	public final String generateCurrentDateInSpecificFormat(String format) {
		Date dNow = new Date();
		SimpleDateFormat formatTime = new SimpleDateFormat(format);
		String datetime = formatTime.format(dNow);
		return datetime;
	}

	/**
	 * This method is used to select a value from dropdown
	 *
	 *
	 * @param textToBeSelected - Text to be selected
	 * @param listOfElements   - Text to be selected
	 * @return
	 */
	public boolean selectValueFromDropdown(String textToBeSelected, List<WebElement> listOfElements) {
		boolean textFound = false;
		List<String> dropDownElements = new ArrayList<String>();

		try {
			// Check if the list is not empty before entering the loop
			if (!listOfElements.isEmpty()) {
				for (WebElement dropDownElement : listOfElements) {
					if (dropDownElement.getText().equals(textToBeSelected)) {
						textFound = true;
						dropDownElement.click();
						break; // Exit the loop once text is selected
					}
				}
			} else {
				// Handle the case when the list is empty
				LOGGER.error("The list is empty");
			}
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectValueFromDropdown " + e.getMessage()));
			return false;
		}

		return textFound;

	}

	/**
	 *
	 * @param tableHeaderListLocator : list locator for headers like
	 *                               table/thead/tr/th
	 * @param headerTitle            : header title whose index is to be found
	 * @param partialDataLocator     : table row locator excluding '[td]' like
	 *                               table/tbody/tr[1]
	 * @param data                   : data to be compared at found header index
	 * @return
	 * @throws Exception
	 */
	public boolean verifyTableData(String tableHeaderListLocator, String headerTitle, String partialDataLocator,
			String data) throws Exception {
		int position = -1;
		List<WebElement> headers = getAllElements(tableHeaderListLocator);
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		for (WebElement Webelement : headers) {
			String text = Webelement.getText();
			columnNamesOnPage.add(text);
		}

		for (int i = 0; i < columnNamesOnPage.size(); i++) {
			if (columnNamesOnPage.get(i).equalsIgnoreCase(headerTitle)) {
				position = i + 1;
				break;
			}

		}
		LOGGER.info("Index for column " + headerTitle + " in table is :" + position);
		if (position < 0) {
			return false;
		}
		if (getTextBy(partialDataLocator + "/td[" + position + "]").equalsIgnoreCase(data)) {
			LOGGER.info("Table data for column " + headerTitle + " at position " + partialDataLocator + "/td["
					+ position + "]" + " is :" + getTextBy(partialDataLocator + "/td[" + position + "]"));
			return true;
		} else {
			return false;
		}
	}

	public int getTableColumnPos(String tableHeaderListLocator, String headerTitle) throws Exception {
		int position = -1;
		List<WebElement> headers = getAllElements(tableHeaderListLocator);
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		for (WebElement Webelement : headers) {
			String text = Webelement.getText();
			columnNamesOnPage.add(text);
		}

		for (int i = 0; i < columnNamesOnPage.size(); i++) {
			if (columnNamesOnPage.get(i).equalsIgnoreCase(headerTitle)) {
				position = i + 1;
				break;
			}

		}
		LOGGER.info("Index for column " + headerTitle + " in table is :" + position);
		return position;
	}

	public final String getReceivedHtmlParserMailSpaceThree(String mailContent) throws Exception {
		String actualMailContent;
		InputStream in = org.apache.commons.io.IOUtils.toInputStream(mailContent, "UTF-8");
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		HtmlParser htmlparser = new HtmlParser();
		htmlparser.parse(in, handler, metadata, pcontext);
		actualMailContent = handler.toString().replaceAll("\\s{3,}", " ").trim();
		return actualMailContent;
	}

	public String verifyReceivedEmailContent(String subject, String environment, String inboxEmailAddress,
			boolean privateDomain) throws Exception {
		String mailContent;
		Mailinator objMailinator = new Mailinator("", inboxEmailAddress.split("@")[0]);
		sleeper(5000);// required wait because script is breaking over here
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(
				getEnvironmentSpecificData(System.getProperty("environment"), subject), inboxEmailAddress.split("@")[0],
				privateDomain);
		if (objMailinatorEmail != null) {
			mailContent = objMailinatorEmail.getBody();
			return getReceivedHtmlParserMailSpaceThree(mailContent);
		} else {
			LOGGER.error("Mail not found");
			return "";
		}
	}

	/**
	 * This method is to verify that left side menu is open or close. if left side
	 * menu is closed then this method will open it
	 */
	public final void leftSideMenuVerification() {
		LaunchDarkly ldinstance = new LaunchDarkly();
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();

			if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
				if ((dashboardPage.verifyElementsOfDashboardPage("toggleButtonClose"))) {
					dashboardPage.clickOnElementsOfDashboardPage("toggleButtonClose");
					Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("toggleButtonOpen"),
							"toggleButton is not open");
				}
			} else {
				LOGGER.info("Due to small screen resolution the side menu panel expand collapse option is not present");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Exception occured on opening Menu button" + e.getMessage());
		}
	}
	
	/**
	 * This method is to verify that left side menu is open or close. if left side
	 * panel is closed then this method will open it
	 */
	public final void openLeftSidePanel() {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			if ((dashboardPage.verifyElementsOfDashboardPage("leftSidePanelOpen"))) {
					dashboardPage.clickOnElementsOfDashboardPage("leftSidePanelOpen");
					Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("leftSidePanelClose"),
							"Fail:Left side panel is not open");
				}
			else {
				LOGGER.info("Left side panel is already open.");
			}
 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Exception occured on opening Menu button" + e.getMessage());
		}
	}
 

	/**
	 * This method designed to show partner with company view and then navigate on
	 * left side menu tabs
	 * 
	 * @param companyName - this argument is to verify according to index
	 * @param modules     - Different tabs that we need to click on left side menu
	 *                    tabs of Dashboard page
	 * @param itemLink    - itemLink is clicked when any menu tabs has drop-down
	 */
	public final void partnerWithCompanyView(String companyName, String modules, String itemLink) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			dashboardPage.actionClickOfDashboardPage("companyFilter");
			List<WebElement> options = dashboardPage.getElementsOfDashboardPage("companySelectionDropdown");
			boolean isCustomerFound = false;
			for (WebElement option : options) {
				String actualCompanyName = option.getText();
				if (actualCompanyName.equalsIgnoreCase(companyName)) {
					isCustomerFound = true;
					clickWebelement(option);
					if (companyName.equalsIgnoreCase("All Customers")) {
						partnerView(modules);
						break;
					} else {
						switch (modules) {
						case "Devices":
							handleDevices(itemLink);
							break;

						case "Remediations":
							handleRemediations(itemLink);
							break;
							
						case "Fleet Management":							 
							handleFleetManagement(itemLink);								 
							break;								 
							
							case "Employee Engagement":		                                         
							handleEmployeeEngagement(itemLink);								 
							break;
							
							case "Alerts":
							handleAlerts(itemLink);
								break;
							
						default:
							LOGGER.info("PartnerwithCompany " + modules + " tab does not match !!!");
						}
						// Once the module handled successfully no need to check with other customer
						// hence breaking the loop here
						break;
					}

				}

			}
			if (!isCustomerFound) {
				LOGGER.error("Expected Customer is not matching with Actual Customer");
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occured on clicking left side menu tabs on " + companyName + " view" + e.getMessage());
		}

	}

	/**
	 * This method designed to show partner with company view and then navigate on
	 * left side menu tabs
	 * 
	 * @param companyName - this argument is to verify according to index
	 * @param modules     - Different tabs that we need to click on left side menu
	 *                    tabs of Dashboard page
	 */
	public final void partnerWithCompanyView(String companyName, String modules) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			Thread.sleep(2000);
			dashboardPage.actionClickOfDashboardPage("companyFilter");
			List<WebElement> options = dashboardPage.getElementsOfDashboardPage("companySelectionDropdown");
			boolean isCustomerFound = false;
			for (WebElement option : options) {
				String actualCompanyName = option.getText();
				if (actualCompanyName.equalsIgnoreCase(companyName)) {
					isCustomerFound = true;
					Thread.sleep(2000);
					clickWebelement(option);
					Thread.sleep(2000);
					if (companyName.equalsIgnoreCase("All Customers")) {
						partnerView(modules);
						break;
					} else {
						switch (modules) {
						case "Home":
							dashboardPage.clickOnElementsOfDashboardPage("companyHome");
							break;
							
						case "Account Management":							 
							dashboardPage.clickOnElementsOfDashboardPage("accountManagement");
							break;
							
						case "Help & Support":						
							dashboardPage.clickOnElementsOfDashboardPage("helpDashboard");
							break;
							
						case "Analytics":
							dashboardPage.clickOnElementsOfDashboardPage("analytics");
							break;

						case "Groups":
							dashboardPage.clickOnElementsOfDashboardPage("analytics");
							break;

						case "Pulses":
							dashboardPage.clickOnElementsOfDashboardPage("pulses");
							break;

						case "Labs":
							dashboardPage.clickOnElementsOfDashboardPage("labs");
							break;

						case "Integrations":
							dashboardPage.clickOnElementsOfDashboardPage("integrations");
							break;

						case "Account":
							dashboardPage.clickOnElementsOfDashboardPage("account");
							break;

						case "Settings":
							dashboardPage.clickOnElementsOfDashboardPage("settingsDashboard");
							break;
							
						case "Logs":	
							dashboardPage.clickOnElementsOfDashboardPage("logsDashboard");	
							break;

						default:
							LOGGER.info("PartnerwithCompany " + modules + " tab does not match !!!");
						}
						break;
					}

				}

			}
			if (!isCustomerFound) {
				LOGGER.error("Expected Customer is not matching with Actual Customer");
			}

		} catch (Exception e) {
			LOGGER.error(
					"Exception occured on clicking left side menu tabs on " + companyName + " view" + e.getMessage());
		}

	}

	/**
	 * This method designed to show company view and then navigate on left side menu
	 * tabs of fleet management and Employee Engagement
	 * 
	 * @param modules  - Different tabs that we need to click on left side menu tabs
	 *                 of dashboard page
	 * @param itemLink - temLink is clicked when any menu tabs has dropdown
	 */
	public final void companyView(String modules, String itemLink) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			switch (modules) {

			case "Alerts":
				handleAlerts(itemLink);
				break;
				
			case "Fleet Management":	
				handleFleetManagement(itemLink);	
				break;	

			case "Employee Engagement":	
				handleEmployeeEngagement(itemLink);	
				break;
				
			case "Devices":
				handleDevices(itemLink);
				break;

			case "Remediations":
				handleRemediations(itemLink);
				break;

			default:
				LOGGER.info("Company " + modules + " tab does not match !!!");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured on clicking left side menu tabs on company view" + e.getMessage());
		}

	}

	/**
	 * This method designed to show company view and then navigate on left side menu
	 * tabs
	 * 
	 * @param modules - Different tabs that we need to click on left side menu tabs
	 *                of dashboard page
	 */
	public final void companyView(String modules) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			switch (modules) {
			case "Home":
				dashboardPage.clickOnElementsOfDashboardPage("companyHome");
				break;

			case "Alerts":				
				dashboardPage.clickOnElementsOfDashboardPage("alertOption");
				break;
			case "Alerts Management":
				dashboardPage.clickOnElementsOfDashboardPage("partnerAlertManagement");
				break;
				
			case "Hardware Support":
				dashboardPage.clickOnElementsOfDashboardPage("companyHardwareSupport");
				break;

			case "Analytics":
				dashboardPage.clickOnElementsOfDashboardPage("analytics");
				break;

			case "Groups":
				dashboardPage.clickOnElementsOfDashboardPage("groups");
				break;

			case "Pulses":
				dashboardPage.clickOnElementsOfDashboardPage("pulses");
				break;

			case "Labs":
				dashboardPage.clickOnElementsOfDashboardPage("labs");
				break;

			case "Integrations":
				dashboardPage.clickOnElementsOfDashboardPage("integrations");
				break;
				
			case "Account Management":
				if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
					dashboardPage.clickOnElementsOfDashboardPage("accountManagement");
				} else {
					dashboardPage.clickByJavaScriptOnDashboardPage("accountManagementNoSidePanel");
				}
				break;
				
			case "Account":
				if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
					dashboardPage.clickOnElementsOfDashboardPage("account");
				} else {
					dashboardPage.clickByJavaScriptOnDashboardPage("accountManagementNoSidePanel");
				}
				break;
      case "Add-ons":
        dashboardPage.clickByJavaScriptOnDashboardPage("addons");
        break;
			case "Settings":
				dashboardPage.clickOnElementsOfDashboardPage("settingsDashboard");
				break;
				
			case "Help & Support":	
				dashboardPage.clickOnElementsOfDashboardPage("helpDashboard");	
				break;
				
			case "Logs":	
				dashboardPage.clickOnElementsOfDashboardPage("logsDashboard");	
			break;	

			default:
				LOGGER.info("Company " + modules + " tab does not match !!!");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured on clicking left side menu tabs on company view" + e.getMessage());
		}

	}

	/**
	 * This method designed to click user icon on top right corner and navigate to
	 * User-Profile, Help & Supportout.
	 * 
	 * @param modules - Different tabs that we need to click on left side menu tabs
	 *                of dashboard page
	 */
	public final boolean navigateToCompanyUserIcon(String modules) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (!dashboardPage.waitForElementsOfDashboardPage("userProfileButton")) {
				LOGGER.info("User Profile button is not available");
				return false;
			}
			dashboardPage.clickOnElementsOfDashboardPage("userProfileButton");

			String buttonToClick;
			switch (modules) {
			case "User Profile":
				buttonToClick = "userProfilePageButton";
				break;
			case "Help & Support":
				buttonToClick = "helpSupportPageButton";
				break;
			default:
				LOGGER.info("Company " + modules + " tab does not match !!!");
				return false;
			}

			dashboardPage.clickOnElementsOfDashboardPage(buttonToClick);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception occured on clicking left side menu tabs on company view" + e.getMessage());
			return false;
		}

	}

	/**
	 * This method designed to show partner view and then navigate on left side menu
	 * tabs
	 * 
	 * @param modules - Different tabs that we need to click on left side menu tabs
	 *                of Dashboard page
	 * 
	 */
	public final void partnerView(String modules) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			switch (modules) {
			case "Home":
				dashboardPage.clickOnElementsOfDashboardPage("companyHome");
				break;

			case "Customers":
				dashboardPage.clickOnElementsOfDashboardPage("customersDashboard");
				break;

			case "Product Catalog":
				dashboardPage.clickOnElementsOfDashboardPage("ProductcatlogDashboard");
				break;

			case "Analytics":
				dashboardPage.clickOnElementsOfDashboardPage("PartnerViewSideMenuAnalytics");
				break;

			case "Integrations":
				dashboardPage.clickOnElementsOfDashboardPage("integrations");
				break;

			case "Account":
				dashboardPage.clickOnElementsOfDashboardPage("account");
				break;

			case "Settings":
				dashboardPage.clickOnElementsOfDashboardPage("settingsDashboard");
				break;
				
			case "Unassigned Devices":
				dashboardPage.clickOnElementsOfDashboardPage("unassignedDevices");
				break;

			default:
				LOGGER.info("Partner " + modules + " tab does not match !!!");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured on clicking left side menu tabs on partner view" + e.getMessage());
		}

	}

	/**
	 * This method designed to click drop-down menu of Fleet Management
	 * 
	 * @param fleetChoice - Different tabs that we need to click on drop-down of
	 *                    left side menu tabs
	 *
	 */
	public final void handleFleetManagement(String fleetChoice) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
				if (!dashboardPage.waitForElementsOfDashboardPage("fleetDevices")) {
					dashboardPage.clickOnElementsOfDashboardPage("fleetManagement");
				}
			} else {
				dashboardPage.clickByJavaScriptOnDashboardPage("fleetManagementNoSidePanel");
			}
			switch (fleetChoice) {
			case "Dashboard":
				dashboardPage.clickOnElementsOfDashboardPage("fleetDashboard");
				break;

			case "Devices":
				dashboardPage.clickOnElementsOfDashboardPage("fleetDevices");
				break;

			case "Groups":
				dashboardPage.clickOnElementsOfDashboardPage("groups");
				break;

			case "Analytics":
				dashboardPage.clickOnElementsOfDashboardPage("fleetAnalytics");
				break;

			case "Scripts":
				dashboardPage.clickOnElementsOfDashboardPage("fleetScripts");
				break;

			case "Main menu":
				dashboardPage.clickOnElementsOfDashboardPage("fleetManagement");
				break;
			case "Activity":
				dashboardPage.clickOnElementsOfDashboardPage("fleetActivity");
				break;

			default:
				LOGGER.info("Under Fleet Management menu choice " + fleetChoice + " tab does not match !!!");

			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occured on clicking left side menu tabs in Fleet Management dropdown" + e.getMessage());
		}

	}

	/**
	 * This method designed to click drop-down menu of Fleet Management
	 * 
	 * @param empChoice - Different tab that we need to click on drop-down of left
	 *                  side menu tabs
	 */
	public final void handleEmployeeEngagement(String empChoice) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
				if (!dashboardPage.waitForElementsOfDashboardPage("employeePulses")) {
					dashboardPage.clickOnElementsOfDashboardPage("employeeEngagement");
				}
			} else {
				dashboardPage.clickByJavaScriptOnDashboardPage("employeeManagementNoSidePanel");
			}
			switch (empChoice) {
			case "Dashboard":
				if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
					dashboardPage.clickOnElementsOfDashboardPage("employeeDashboard");
				} else {
					dashboardPage.clickByJavaScriptOnDashboardPage("employeeDashboardNoSidePanel");
				}
				break;

			case "Pulses":
				dashboardPage.clickOnElementsOfDashboardPage("employeePulses");
				break;

			case "Main menu":
				dashboardPage.clickOnElementsOfDashboardPage("employeeEngagement");
				break;

			default:
				LOGGER.info("Under Employee Engagement menu choice " + empChoice + " tab does not match !!!");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured on clicking left side menu tabs in Employee Engagement dropdown"
					+ e.getMessage());
		}
	}

	/**
	 * This method designed to click drop-down menu of Devices for toggle
	 * wx-navigation-improvements
	 *
	 * @param itemLink - Different tab that we need to click on drop-down of left
	 *                 side menu tabs
	 */
	public final void handleDevices(String itemLink) {
		try {
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
				if (!dashboardPage.waitForElementsOfDashboardPage("devicesPCs")) {
					dashboardPage.actionClickOfDashboardPage("devicesdropDown");
				}
			} else {
				dashboardPage.actionClickOfDashboardPage("devicesNoSidePanel");
			}
			sleeper(2000);
			switch (itemLink) {
			case "PCs":	
				dashboardPage.actionClickOfDashboardPage("devicesPCs");
				break;

			case "Printers":
				dashboardPage.actionClickOfDashboardPage("devicesPrinters");
				break;

			case "Virtual Machines":
				dashboardPage.actionClickOfDashboardPage("devicesVirtualMachines");
				break;
			case "Video Endpoints":
				dashboardPage.actionClickOfDashboardPage("devicesVideoEndpoints");
				break;
			case "Telephones":
				dashboardPage.actionClickOfDashboardPage("devicesTelephone");
				break;
			case "Physical Assets":
				dashboardPage.actionClickOfDashboardPage("devicesAssets");
				break;

			default:
				LOGGER.info("Under Devices menu choice " + itemLink + " tab does not match !!!");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured on clicking left side menu tabs in Devices dropdown" + e.getMessage());
		}
	}

	/**
	 * This method designed to click drop-down menu of Remediations for toggle
	 * wx-navigation-improvements
	 *
	 * @param remediationsChoice - Different tab that we need to click on drop-down
	 *                           of left side menu tabs
	 */
	public final void handleRemediations(String remediationsChoice) {
		try {
			boolean isSidePanelOpen = false;
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
				isSidePanelOpen = true;
				if (!dashboardPage.waitForElementsOfDashboardPage("remediationsPolicies")) {
					dashboardPage.clickOnElementsOfDashboardPage("remediationsdropDown");
				}
			} else {
				dashboardPage.clickByJavaScriptOnDashboardPage("remediationsNoSidePanel");
			}
			switch (remediationsChoice) {
			case "Policies":
				dashboardPage.clickOnElementsOfDashboardPage("remediationsPolicies");
				break;

			case "Scripts":
				if(!isSidePanelOpen) {
					dashboardPage.clickOnElementsOfDashboardPage("remediationsScriptsNoSidePanel");
				} else {
					dashboardPage.clickOnElementsOfDashboardPage("remediationsScripts");
				}
				break;

			case "Secrets":
				dashboardPage.clickOnElementsOfDashboardPage("remediationsSecrets");
				break;

			case "Activity":
				dashboardPage.clickOnElementsOfDashboardPage("remediationsActivity");
				break;

			default:
				LOGGER.info("Under Remediation menu choice " + remediationsChoice + " tab does not match !!!");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured on clicking left side menu tabs in Remediations dropdown" + e.getMessage());
		}
	}

	/**
	 * This method designed to click on companyNames list box and select the
	 * specific customer/company from it
	 * 
	 * @param companyName
	 */
	public final void selectCompany(String companyName) {
		try {
			sleeper(2000);
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			dashboardPage.actionClickOfDashboardPage("companyFilter");
			List<WebElement> options = dashboardPage.getElementsOfDashboardPage("companySelectionDropdown");
			boolean isCustomerFound = false;
			for (WebElement option : options) {
				String actualCompanyName = option.getText();
				if (companyName.equalsIgnoreCase("All Customers")) {
					clickWebelement(option);
					break;
				} else if (actualCompanyName.equalsIgnoreCase(companyName)) {
					isCustomerFound = true;
					clickWebelement(option);
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured on selecting the company fromlist box " + companyName + " view"
					+ e.getMessage());
		}

	}

	/**
	 * This method designed to click drop-down menu of Alert Management
	 * 
	 * @param alertChoice - Different tabs that we need to click on drop-down of
	 *                    left side menu tabs
	 *
	 */
	public final void handleAlerts(String alertChoice) {
		try {
			boolean isSidePanelOpen = false;
			WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton", 5)) {
				isSidePanelOpen = true;
				if(!dashboardPage.waitForElementsOfDashboardPage("alertsMenu")) {
					dashboardPage.clickOnElementsOfDashboardPage("alertDropdown");					
				}
				
			} else {
				dashboardPage.clickByJavaScriptOnDashboardPage("alertNoSidePanel");
			}
			switch (alertChoice) {
				case "Active Alerts":
				if(!isSidePanelOpen) {
					dashboardPage.clickOnElementsOfDashboardPage("activeAlertSidePanel");
				}else {
				dashboardPage.clickByJavaScriptOnDashboardPage("activeAlert");
				}
				break;

			case "Alerts Management":
				if(!isSidePanelOpen) {
					dashboardPage.clickOnElementsOfDashboardPage("alertSidePanel");
				}else {
				dashboardPage.clickByJavaScriptOnDashboardPage("alertManagement");
				}
				break;

			default:
				LOGGER.info("Under Alert menu choice " + alertChoice + " tab does not match !!!");

			}
		} catch (Exception e) {
			LOGGER.error("Exception occured on clicking left side menu tabs in Alert dropdown" + e.getMessage());
		}

	}

	/**
	 * This method designed to click drop-down menu of Remediation's for toggle
	 * wx-navigation-improvements
	 *
	 * @param itemLink - Different tab that we need to click on drop-down of left
	 *                 side menu tabs
	 *//*
		 * public final void handleRemediations(String itemLink) { try {
		 * WEXDashboardPage dashboardPage = new
		 * WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		 * if(dashboardPage.waitForElementsOfDashboardPageDynamic("sideMenuToggleButton"
		 * ,5)) { if (!dashboardPage.waitForElementsOfDashboardPage("fleetScripts")) {
		 * dashboardPage.clickOnElementsOfDashboardPage("remediationsDropDown"); } }
		 * else {
		 * dashboardPage.clickByJavaScriptOnDashboardPage("remediationsNoSidePanel"); }
		 * switch (itemLink) { case "Policies":
		 * dashboardPage.clickOnElementsOfDashboardPage("fleetPolicies"); break;
		 * 
		 * case "Scripts": dashboardPage.clickOnElementsOfDashboardPage("fleetScripts");
		 * break;
		 * 
		 * case "Secrets": dashboardPage.clickOnElementsOfDashboardPage("fleetSecrets");
		 * break;
		 * 
		 * case "Activity":
		 * dashboardPage.clickOnElementsOfDashboardPage("fleetActivity"); break;
		 * 
		 * default: LOGGER.info("Under Devices menu choice " + itemLink +
		 * " tab does not match !!!"); } } catch (Exception e) { LOGGER.
		 * error("Exception occured on clicking left side menu tabs in Devices dropdown"
		 * + e.getMessage()); } }
		 */
	public static void switchUserBasedOnSuite(String testSuiteName) throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver())
				.getInstance();
		String currentUser = WEXCustomerUserListPage.getCurrentUserProfile();
		System.out.println("Current user profile: " + currentUser);
		Thread.sleep(7000);

		if ((testSuiteName.contains("LDK_PRODUCTION")) && !currentUser.contains("LDK")) {
			WEXCustomerUserListPage.switchAccount("listOfSwitchUser", "LDK");

		} else if ((testSuiteName.contains("InitechSolutions")) && !currentUser.contains("Initech")) {
			WEXCustomerUserListPage.switchAccount("listOfSwitchUser", "Initech");

		}
	}

}