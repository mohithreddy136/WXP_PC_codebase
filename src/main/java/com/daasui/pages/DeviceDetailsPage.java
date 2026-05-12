package com.daasui.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.ExcelReader;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DeviceVariables;
import com.daasui.constants.PreferenceVariables;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeviceDetailsPage extends CommonMethod {
	 // Global filter togglename
    public static final String Device_Grouping_toggle = "techpulse-grouping-service";
	private Properties selectedLanguageProperties;
	private ObjectReader deviceDetailsPagePropertiesReader = new ObjectReader();
	private Properties deviceDetailsPageProperties;
	private Properties environmentProperties;
	private static Logger LOGGER = LogManager.getLogger(DeviceDetailsPage.class);
	public List<String> listOfValuesSaved = new ArrayList<String>();
	public String serialNumberCustom = null;
	public static String uiVersion = System.getProperty("uiVersion");
	private DeviceDetailsPage instance;
	
	public DeviceDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (DeviceDetailsPage.class) {
				if (instance == null) {
					instance = new DeviceDetailsPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public DeviceDetailsPage(WebDriver driver) throws IOException {
		deviceDetailsPageProperties = deviceDetailsPagePropertiesReader.getObjectRepository("DeviceDetailsPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = deviceDetailsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfDeviceDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(deviceDetailsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfDeviceDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(deviceDetailsPageProperties.getProperty(key));
	}

	public final boolean matchTextOfDeviceDetailsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(deviceDetailsPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfDeviceDetailsPage(String key) throws Exception {
		return verifyElementIsEnable(deviceDetailsPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfDeviceDetailsPage(String key) throws Exception {
		return verifyElementIsSelected(deviceDetailsPageProperties.getProperty(key));
	}

	public final String getTextOfDeviceDetailsPage(String key) throws Exception {
		return getTextBy(deviceDetailsPageProperties.getProperty(key));
	}
	
	public final List<String> getAllTextOfDeviceDetailsPage(String key) throws Exception {
		return getallTextBy(deviceDetailsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfDeviceDetailsPageForinvisibile(String key) throws Exception {
		return verifyElementIsinvisibile(deviceDetailsPageProperties.getProperty(key));
	}

	public final String getAttributesOfDeviceDetailsPage(String key, String value) throws Exception {
		return getAttribute(deviceDetailsPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfDeviceDetailsPage(String key) throws Exception {
		click(deviceDetailsPageProperties.getProperty(key));
	}
	
	public final void multipleclickOnElementsOfDeviceDetailsPage(String key, Integer count) throws Exception {
		multipleclick(deviceDetailsPageProperties.getProperty(key), count);
	}

	public final void rightclickOnElementsOfDeviceDetailsPage(String key) throws Exception {
		rightclick(deviceDetailsPageProperties.getProperty(key));
	}

	

	/**
	 * This is a method to click on an element by javascript
	 * 
	 * @param key - Locator of element
	 */
	public final void clickByJavaScriptOnDeviceDetailsPage(String key) {
		try {
			clickByJavaScript(deviceDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnDeviceDetailsPage " + e.getMessage()));
		}
	}

	public final void enterTextForDeviceDetailsPage(String key, String Text) throws Exception {
		enterText(deviceDetailsPageProperties.getProperty(key), Text);
	}
	
	public final void enterTextwithoutclearForDeviceDetailsPage(String key, String Text) throws Exception {
		enterTextwithoutclear(deviceDetailsPageProperties.getProperty(key), Text);
	}
	

	public final void clearTextRefreshFromDeviceDetailsTextField(String key) throws Exception {
		clearTextRefresh(deviceDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to scroll on incident page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void scrollOndeviceDetailsPage(String key) throws Exception {
		scrollTillView(deviceDetailsPageProperties.getProperty(key));
	}

	/**
	 * @param key
	 * @param text
	 * @throws Exception
	 */
	public final void enterTextForDeviceDetailsPageUsingJavaScript(String key, String text) throws Exception {
		enterTextUsingJavaScript(deviceDetailsPageProperties.getProperty(key), text);
	}

	public final boolean verifyElementIsClickableOfDeviceDetailsPage(String key) throws Exception {
		return verifyElementIsClickable(deviceDetailsPageProperties.getProperty(key));
	}

	/**
	 * 
	 * This method returns List of web elements present under the locater value provide
	 * 
	 * This method returns List of web elements present under the locater value provide
	 * 
	 * 
	 * @param key = key from properties file
	 * @return = list of elements
	 * @throws Exception
	 */
	public final List<WebElement> getElementsOfDeviceDetailsPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(deviceDetailsPageProperties.getProperty(key));
	}

	public final void selectElementFromDropDownOfDeviceDetailsPage(String dropdownId, String key, String text) throws Exception {
		click(deviceDetailsPageProperties.getProperty(dropdownId));
		selectFromDropdown(deviceDetailsPageProperties.getProperty(dropdownId), deviceDetailsPageProperties.getProperty(key), text);
	}

	public final void switchToIframeofDeviceDetailsPage(String key) throws Exception {
		switchToIframeById(deviceDetailsPageProperties.getProperty(key));
	}

	public final ArrayList<String> getTextOfColumns(String key) throws Exception {
		return getTextOfList(deviceDetailsPageProperties.getProperty(key));
	}

	public final void clickOnDevice(String key, int index) throws Exception {
		click(deviceDetailsPageProperties.getProperty(key) + "[" + index + "]//td[3]//span");
	}

	public final int getNumberOfWarranties(String key) throws Exception {
		return getCountOfRows(deviceDetailsPageProperties.getProperty(key));
	}
	
	public final int getNumberOfPlans(String key) throws Exception {
		return getCountOfRows(deviceDetailsPageProperties.getProperty(key));
	}
	
	public final WebElement getElementDeviceDetailsPage(String key) throws Exception {
		return getElement(deviceDetailsPageProperties.getProperty(key));
	}
	
	/**
	 * This method hovers on devicedetails elements
	 * 
	 * @param key
	 */
	public final void mousehoverOnDeviceDetailsPage(String key) {
		try {
			mouseHover(deviceDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnDeviceDetailsPage " + e.getMessage()));
		}
	}

	/**
	 * This method submits an incident using APIs for user submitted incident based on type and subtype given and this is overridden method of the above method and facilitates the user
	 * to pass customized title and description so that same can be validated in the received email and other related sources
	 * 
	 * @param type of the incident
	 * @param subtype of the incident
	 * @param incidentTitle of the incident
	 * @param incidentDescription of the incident
	 * @param host of the environment
	 * @param tenantId of the company
	 * @param userAuthToken of the logged in User
	 * @param companyEmailId of the company under which device is enrolled
	 * @param deviceId of the enrolled device
	 * @return HashMap having information about the created incident
	 * @throws Exception
	 */
	public String submitCaseUsingAPI(String type, String subtype, String incidentTitle, String incidentDescription, String host, String tenantId,  String userAuthToken, String deviceId, String UserID) throws Exception {
		
		userAuthToken= getTokenFromLocalStorage("dui_token_e");
		JSONObject parsedJwtToken = EnrollFakeDevice.jwtTokenParse(userAuthToken);
		UserID = parsedJwtToken.getString("user");
		String incidentId = "";
		try {
			String serverUrl = "";
			if (Strings.isNullOrEmpty(incidentTitle)) {
				incidentTitle = "Incident for " + type + "";
			}
			if (Strings.isNullOrEmpty(incidentDescription)) {
				incidentDescription = "New incident added for type " + type + " and subtype  " + subtype + " by automation script";
			}

			type = type.toUpperCase();
			subtype = subtype.toUpperCase();

			if (host.toLowerCase().contains("usdev")) {
				serverUrl = environmentProperties.getProperty("StableUSSS");
			} else if (host.toLowerCase().contains("eudev")) {
				serverUrl = environmentProperties.getProperty("StableEUSS");
			} else if (host.toLowerCase().contains("usstagingms")) {
				serverUrl = environmentProperties.getProperty("StagingUS");
			} else if (host.toLowerCase().contains("eustagingms")) {
				serverUrl = environmentProperties.getProperty("StagingEU");
			} else if (host.toLowerCase().contains("www.hpdaas.com")) {
				serverUrl = environmentProperties.getProperty("ProdUSSS");
			} else if (host.toLowerCase().contains("eu.hpdaas.com")) {
				serverUrl = environmentProperties.getProperty("ProdEUSS");
			} else {
				LOGGER.error("Incorrect host name given, please check the host name!!." + host + " is not a valid entry.");
				return incidentId;
			}
			
			String apiUrl = serverUrl + "services/ccc-incidents/1.0/tenants/" + tenantId + "/incidents";
			URL url = new URL(apiUrl);
			// Make a device authentication post call
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("Authorization", "Bearer " + userAuthToken);
			String body = "{  \"description\": \"" + incidentDescription + "\",  \"deviceId\": \"" + deviceId + "\",  \"errorcode\": \"string\",  \"sourceId\": \"string\",  \"sourceLocation\": \"string\",  \"status\": \"NEW\",  \"subtype\": \"" + subtype + "\",  \"tenantId\": \"" + tenantId + "\",  \"title\": \"" + incidentTitle + "\",  \"type\": \"" + type + "\",  \"userId\":\""+UserID+"\",\"errorcode\":\"332\",\"cmeasure\":\"CLIENT_DISABLED|27\"}";
			httpConnection.setDoOutput(true);
			byte[] outputInBytes = body.getBytes("UTF-8");
			OutputStream os = httpConnection.getOutputStream();
			os.write(outputInBytes);
			os.close();
			// Get the response code for the incident submission call
			int responseCode = httpConnection.getResponseCode();
			LOGGER.info("Response code for incident submission: " + responseCode);
			// Validation for the submit incident API response code
			if (responseCode == 201) {
				String response = getResponse(responseCode, httpConnection);
				JSONObject json = new JSONObject(response.toString());
				incidentId = json.getString("displayId");

			} else {
				LOGGER.error("Incident couldn't be submitted, expected response code: 201, actual response code for API call: " + responseCode);
				return incidentId;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method submitCaseUsingAPI: " + ex.getMessage());
			return null;
		}
		return incidentId;
	}

	public static JSONObject jwtTokenParse(String userAuthToken) throws UnsupportedEncodingException {
		// Need to split the auth token based on the "." so that the body received in the auth token can be decoded
		String[] splitAuth = userAuthToken.split("\\.");
		String b64payload = splitAuth[1];
		// Decoding the body received in the auth token
		String value = new String(Base64.decodeBase64(b64payload), "UTF-8");
		JSONObject jsonString = new JSONObject(value);
		return jsonString;
	}
	/**
	 * This method will validate the response code and convert the response body into string
	 * 
	 * @param responseCode - The response code for the desired API
	 * @param con - HttpConnection object of the desired API
	 * @return response - response converted into String format
	 */
	public static String getResponse(int responseCode, HttpURLConnection con) throws Exception {
		StringBuffer response = new StringBuffer();
		try {
			if (responseCode == HttpURLConnection.HTTP_CREATED) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = bufferedReader.readLine()) != null) {
					response.append(inputLine);
				}
				bufferedReader.close();
				LOGGER.info("Response from desired API: " + response.toString());
			} else {
				LOGGER.error("API for submitting an incident did not work");
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method getResponse: " + ex.getMessage());
		}
		return response.toString();
	}

	/**
	 * This method submits an incident base on type and subtype given and this is overridden method of the above method and facilitates the user to pass customized title and
	 * description so that same can be validated in the received email and other related sources
	 * 
	 * @param type of the incident
	 * @param subtype of the incident
	 * @param incidentTitle of the incident
	 * @param incidentDescription of the incident
	 * @return HashMap having information about the created incident
	 * @throws Exception
	 */
	public final HashMap<String, String> submitCase(String type, String subtype, String incidentTitle, String incidentDescription) throws Exception {
		try {
			if (incidentTitle.trim().equals("")) {
				incidentTitle = "Incident for " + type + "";
			}
			if (incidentDescription.trim().equals("")) {
				incidentDescription = "New incident added for type " + type + " and subtype  " + subtype + " by automation script";
			}
			// clickOnElementsOfDevicePage("firstdevicefromdevicelist");
			clickOnElementsOfDeviceDetailsPage("submitcasebuttonid");
			verifyElementIsClickableOfDeviceDetailsPage("cancelButton");
			switchToIframeofDeviceDetailsPage("iframeIdOfSubmitcasePopUp");
			selectElementFromDropDownOfDeviceDetailsPage("typeOfIncident", "typeOfIncidentOption", type);
			selectElementFromDropDownOfDeviceDetailsPage("subTypeOfIncident", "subTypeOfIncidentOption", subtype);
			enterTextForDeviceDetailsPage("titleOfIncident", incidentTitle);
			enterTextForDeviceDetailsPageUsingJavaScript("titleOfIncident", incidentTitle);
			sleeper(2000);
			enterTextForDeviceDetailsPage("commentOfIncident", incidentDescription);
			enterTextForDeviceDetailsPageUsingJavaScript("commentOfIncident", incidentDescription);
			clickOnElementsOfDeviceDetailsPage("submitcase");
			waitForElementsOfDeviceDetailsPage("successfullSubmitCaseIcon");
			boolean isIncidentSubmitted = verifyElementsOfDeviceDetailsPage("successfullSubmitCaseIcon");
			HashMap<String, String> incidetData = new HashMap<>();
			if (isIncidentSubmitted) {
				waitForElementsOfDeviceDetailsPage("incidentId");
				String incidentId = getTextOfDeviceDetailsPage("incidentId");
				String Id = incidentId.substring(incidentId.lastIndexOf(" ") + 1);
				Id = Id.replaceAll("\\.", "").replaceAll("。", "").replaceAll("ケース番号は", "").replaceAll("です", "").trim(); // Replaced the special characters for China/Japan locale
				incidetData.put("incidentid", Id);
				incidetData.put("incidentdescription", incidentDescription);
				incidetData.put("incidentTitle", incidentTitle);
				incidetData.put("incidentType", type);
				incidetData.put("incidentSubType", subtype);
			} else {
				LOGGER.error("Incident was not submitted successfully.");
				return incidetData;
			}
			clickOnElementsOfDeviceDetailsPage("doneButtonOfIncident");
			sleeper(2000);
			String incidentCreatedOn = getTextOfDeviceDetailsPage("incidentCreatedOn");
			incidentCreatedOn = incidentCreatedOn.replace("(UTC)", "");
			incidetData.put("incidentCreatedOn", incidentCreatedOn);
			return incidetData;
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in the method submitCase." + ex.toString());
			throw ex;
		}
	}

	public final boolean verifyWarrantyOnDeviceDetailsPage(ArrayList<String> type) throws Exception {
		boolean inWarrantyChecked = false;
		boolean outWarrantyChecked = false;
		int listTableCounter = 1;
		verifyElementIsVisible(deviceDetailsPageProperties.getProperty("deviceList"));
		for (String devicetype : type) {
			if (devicetype.contains("Notebook")) {
				clickOnDevice("deviceListTable", listTableCounter);
				verifyElementIsVisible(deviceDetailsPageProperties.getProperty("warrantyTile"));
				click(deviceDetailsPageProperties.getProperty("warrantyTile"));
				// check whether warranty details are present on warranty tile.
				if (verifyTextPresentOnElement(deviceDetailsPageProperties.getProperty("warrantyTitle"), "Title")) {
					ArrayList<String> warrantyText = getTextOfList(deviceDetailsPageProperties.getProperty("warranties"));
					int noOfWarranties = getNumberOfWarranties("allWarranties");
					for (int i = 0, index = 3; i < noOfWarranties; i++) {
						// check if "remaining days" text is present and checks If in-warranty is
						// already checked
						if (warrantyText.get(index).contains("days") && !inWarrantyChecked) {
							if (getTextOfDeviceDetailsPage("warrantyFieldValue").contains("In")) {
								inWarrantyChecked = true;
								break;
							} else
								return false;
						} else if (getTextOfDeviceDetailsPage("warrantyFieldValue").contains("Out") && !outWarrantyChecked)
							outWarrantyChecked = true;
						index += 4;
					}
				}
				clickOnElementsOfDeviceDetailsPage("devicesLink");
				if (outWarrantyChecked && inWarrantyChecked)
					return true;
			}
			listTableCounter++;
		}
		System.out.println("Unable to validate the warranty status due insufficient data.");
		return false;
	}

	/**
	 * 
	 * Verifies whether 'Bromium Integration' field is present for Windows devices in DaaS Test name: verifyBromiumIntegrationOnDeviceDetailsPage
	 * 
	 * @param type=the Device Type in DaaS (Notebook, Desktop, Tablet, Smart phone etc.) This test is applicable only to Notebook and Desktop
	 * @return boolean Boolean true indicates test is successful whereas false indicates a failed test
	 * 
	 * 
	 * @throws Exception
	 */
	public final boolean verifyBromiumIntegrationOnDeviceDetailsPage(ArrayList<String> type, String LanguageCode) throws Exception {
		boolean flagFound = false;
		int listTableCounter = 1;
		boolean deviceListFound = verifyElementIsVisible(deviceDetailsPageProperties.getProperty("deviceList"));

		if (deviceListFound) {
			for (String devicetype : type) {
				String Notebook = getTextLanguage(LanguageCode, "daas_ui", "users.details.device_type.notebook");
				String Desktop = getTextLanguage(LanguageCode, "daas_ui", "users.details.device_type.desktop");
				if (devicetype.contains(Notebook) || devicetype.contains(Desktop)) {
					clickOnDevice("deviceListTable", listTableCounter);

					// Check Operating System of the device
					if (verifyTextPresentOnElement(deviceDetailsPageProperties.getProperty("osValue"), "Windows")) {

						flagFound = verifyElementIsVisible(deviceDetailsPageProperties.getProperty("bromiumIntegrationLabel"));

						if (flagFound) {
							LOGGER.info("This " + devicetype + " has Bromium Integration status field present. ");
							clickOnElementsOfDeviceDetailsPage("deviceTab");
						}
					} else {
						LOGGER.info("This " + devicetype + " is not a windows device");
						clickOnElementsOfDeviceDetailsPage("deviceTab");
					}
				}
				listTableCounter++;
			}

			LOGGER.info("Bromium integration status validated for the devices successfully");
			return true;
		} else {
			LOGGER.info("Unable to validate bromium integration status since there is no compatible device present.");
			return false;
		}
	}

	/**
	 * 
	 * Verifies whether BIOS Version is present for HP manufactured desktop and notebooks in DaaS
	 * 
	 * @param type=the Device Type in DaaS (Notebook, Desktop, Tablet, Smart phone etc.) This test is applicable only to Notebook and Desktop
	 * @return boolean Boolean true indicates test is successful whereas false indicates a failed test
	 * 
	 * @throws Exception
	 */
	public final boolean verifyBIOSVersionForHPDevices(ArrayList<String> type, String LanguageCode) throws Exception {
		boolean biosVersionForHPDevice = false;

		int listTableCounter = 1;
		if (verifyElementIsVisible(deviceDetailsPageProperties.getProperty("deviceList"))) {
			for (String devicetype : type) {
				String Notebook = getTextLanguage(LanguageCode, "daas_ui", "users.details.device_type.notebook");
				String Desktop = getTextLanguage(LanguageCode, "daas_ui", "users.details.device_type.desktop");
				if (devicetype.contains(Notebook) || devicetype.contains(Desktop)) {
					clickOnDevice("deviceListTable", listTableCounter);
					verifyElementIsVisible(deviceDetailsPageProperties.getProperty("hardwareTab"));
					verifyElementIsVisible(deviceDetailsPageProperties.getProperty("deviceMfg"));
					if (getTextOfDeviceDetailsPage("deviceMfg").contains("HP") || getTextOfDeviceDetailsPage("deviceMfg").contains("Hewlet")) {
						click(deviceDetailsPageProperties.getProperty("hardwareTab"));

						biosVersionForHPDevice = getTextOfDeviceDetailsPage("biosVersion").contains("BIOS") || getTextOfDeviceDetailsPage("biosVersion").contains("ios");
						if (biosVersionForHPDevice) {
							LOGGER.info("This HP " + devicetype + " has BIOS version present. ");
							clickOnElementsOfDeviceDetailsPage("deviceTab");
						}
					} else {

						LOGGER.info("This " + devicetype + " is not a HP device");
						clickOnElementsOfDeviceDetailsPage("deviceTab");
					}

				}
				listTableCounter++;
			}

			LOGGER.info("BIOS Version validated for HP devices successfully");
			return true;
		}
		LOGGER.info("Unable to validate BIOS version since required devices are not present.");
		return false;
	}

	public final boolean checkIncidentIdIsHyperlink(String incidentId) {
		try {
			List<WebElement> colValues = new ArrayList<WebElement>();
			colValues = getElementsOfDeviceDetailsPage("incidentIDColumnValue");
			if (colValues.size() > 0) {
				for (WebElement element : colValues) {
					if (element.findElement(By.tagName("a")).getText().equals(incidentId)) {
						if (!element.findElement(By.tagName("a")).getAttribute("href").isEmpty())
							break;
						else
							return false;
					} else
						return false;
				}
				return true;
			} else
				return false;
		} catch (Exception e) {
			LOGGER.error("Exception while checking incident Id is hyperlink" + e.getMessage());
			return false;
		}
	}

	public final boolean checkIncidentIdIsNotHyperlink(String incidentId) {
		try {
			List<WebElement> colValues = new ArrayList<WebElement>();
			matchTextOfDeviceDetailsPage("incidentIdText", incidentId);
			colValues = getElementsOfDeviceDetailsPage("incidentIDColumnValue");
			if (colValues.size() > 0) {
				outerloop: for (WebElement element : colValues) {
					List<WebElement> spanValues = element.findElements(By.tagName("span"));
					if (spanValues.size() > 0) {
						for (WebElement spanValue : spanValues) {
							if (spanValue.getText().equals(incidentId))
								break outerloop;
							else
								continue;
						}
					} else
						return false;
				}
			} else
				return false;

			return true;
		} catch (Exception e) {
			LOGGER.error("Exception while checking incident Id is not hyperlink" + e.getMessage());
			return false;
		}
	}

	/**
	 * This method add text value in respective text box.
	 * 
	 * @param editButton: Locator of edit box
	 * @param text: Value which is to be entered
	 * @param headerText: Edit box header value
	 * @throws Exception
	 */
	public final void editTextBoxVal(String editButton, String text, String headerText) throws Exception {
		clickByJavaScriptOnDeviceDetailsPage(editButton);
		matchTextOfDeviceDetailsPage("editBoxHeader", headerText);
		enterTextForDeviceDetailsPage("editBox", text);
		clickOnElementsOfDeviceDetailsPage("editSave");
		waitForElementsOfDeviceDetailsPageForinvisibile("dialogueBox");
	}

	/**
	 * This method add text value in respective text box.
	 * 
	 * @param editButton: Locator of edit box
	 * @param headerText: Edit box header value
	 * @param cancelButton: Locator of cancel button
	 * @throws Exception
	 */
	public final void validateCancelButtonFunctionality(String editButton, String headerText, String cancelButton) throws Exception {
		clickOnElementsOfDeviceDetailsPage(editButton);
		matchTextOfDeviceDetailsPage("editBoxHeader", headerText);
		enterTextForDeviceDetailsPage("editBox", generateRandomString(11));
		clickOnElementsOfDeviceDetailsPage(cancelButton);
	}

	/**
	 * This method set null(-) device type value to "Select an option"
	 * 
	 * @param value: Current value of the device type
	 */
	public final String dropDownValueCheck(String value) throws Exception {
		if (value.equals("-")) {
			value = "Select an option";
		}
		return value;
	}

	/**
	 * This method set drop down value other than current one
	 * 
	 * @param deviceType: Current value of the device type
	 * @param button: Save,Cancel or Close button
	 * @param languageCode: Language Code
	 */
	public final void setDropdownValue(String deviceType, String button, String languageCode) throws Exception {
		int index = 0;
		sleeper(3000);
		waitForElementsOfDeviceDetailsPage("editButtonType");
		clickOnElementsOfDeviceDetailsPage("editButtonType");
		waitForElementsOfDeviceDetailsPage("dropDownButton");
		clickOnElementsOfDeviceDetailsPage("dropDownButton");

		List<WebElement> deviceTypeList = getElementsTillAllElementsPresent(deviceDetailsPageProperties.getProperty("dropDownList"));

		for (int i = 0; i < deviceTypeList.size(); i++) {
			if (deviceType.equals(deviceTypeList.get(i).getText())) {
				if (i == deviceTypeList.size() - 1) {
					index = i - 1;
					break;
				} else {
					index = i + 1;
					break;
				}
			}
		}

		deviceTypeList.get(index).click();
		clickOnElementsOfDeviceDetailsPage(button);
		//waitForElementsOfDeviceDetailsPageForinvisibile("dialogueBox");

	}

	/**
	 * This method is used to read enrolled devices data
	 * 
	 * @return enrolled devices data
	 * @throws Exception
	 */
	public final ArrayList<String> getEnrollDevices() throws Exception {
		ArrayList<String> enrollDevices = new ArrayList<String>();
		List<WebElement> enrollDevicesList = getElementsTillAllElementsPresent(deviceDetailsPageProperties.getProperty("enrollmentName"));
		sleeper(2000);
		for (WebElement element : enrollDevicesList) {
			String value = element.getText();
			enrollDevices.add(value);
		}
		return enrollDevices;
	}
	
	/**
	 * This method is used to read enrolled Plans data
	 * 
	 * @return enrolled Plans
	 * @throws Exception
	 */
	public final ArrayList<String> getEnrollPlans() throws Exception {
		ArrayList<String> enrollPlans = new ArrayList<String>();
		List<WebElement> enrollPlansList = getElementsTillAllElementsPresent(deviceDetailsPageProperties.getProperty("enrollmentPlan"));
		for (WebElement element : enrollPlansList) {
			String value = element.getText();
			enrollPlans.add(value);
		}
		return enrollPlans;
	}

	/**
	 * This method verifies if button OPEN IN EMM TOOL
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyEmmToolButton() throws Exception {
		waitForElementsOfDeviceDetailsPage("openInEmmToolButton");
		if (verifyElementsOfDeviceDetailsPage("openInEmmToolButton")) {
			LOGGER.info("OPEN IN EMM TOOL button is present");
			return true;
		} else {
			LOGGER.error("OPEN IN EMM TOOL button is not present");
			return false;
		}
	}

	/**
	 * This method verifies the redirection on clicking OPEN IN EMM TOOL button
	 * 
	 * @param id
	 * @param password
	 * @param emmType
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyEmmToolRedirection(String id, String password, String emmType) throws Exception {
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		clickOnElementsOfDeviceDetailsPage("openInEmmToolButton");
		switchToDifferentTab();
		if (emmType.equals("Intune")) {
			refreshPage();
			waitForPageLoaded();
			if (getDriver().getCurrentUrl().contains(ConstantURL.AZURE_PORTAL_URL) || getDriver().getCurrentUrl().contains(ConstantURL.INTUNE_URL)) {
				if (verifyElementsOfDeviceDetailsPage("intuneSignInPanel")) {
					if (preferencesPage.loginToIntunePortal(id, password)) {
						LOGGER.info("Successfully login to the Intune Portal");
					} else {
						LOGGER.error("On cliking EMM link, user is not redirected to the Intune portal");
						return false;
					}
				} else if (waitForElementsOfDeviceDetailsPage("intuneDeviceDetailsPanel")) {
					LOGGER.info("Already logged in to Intune Portal redirected to the Intune url : " + getDriver().getCurrentUrl());
					waitForPageLoaded();
					verifyElementsOfDeviceDetailsPage("intuneDeviceDetailsPanel");
				} else {
					LOGGER.error("Error : Intune redirection failed redirected to : " + getDriver().getCurrentUrl());
				}
			}
		} else if (emmType.equals("ChromeBook")) {
			waitForPageLoaded();
			if (getDriver().getCurrentUrl().contains(PreferenceVariables.CHROME_DEVICE_DETAILS)) {
				LOGGER.info("Successsfully Redirected to the Google Admin Portal");
			} else {
				LOGGER.error("Google Admin Portal redirection failed");
				return false;
			}
		} else {
			LOGGER.error("Invalid EMM type is entered to check EMM Tool button redirection");
			return false;
		}
		switchBackToPreviousTab();
		return true;
	}

	/**
	 * This method fetch Software Inventory of iOS device present on Intune portal
	 * 
	 * @return ArrayList
	 */
	public final ArrayList<String> fetchIntuneiOSSoftwareInventory() {
		ArrayList<String> intuneApps = new ArrayList<String>();
		try {
			PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
			preferencesPage.setIntuneDeviceFilter(DeviceVariables.IOS_OS);
			waitForElementsOfDeviceDetailsPage("intuneFirstDevice");
			clickOnElementsOfDeviceDetailsPage("intuneFirstDevice");
			sleeper(2000); // It's mandatory to load complete device details page of Intune portal
			clickOnElementsOfDeviceDetailsPage("intuneDiscoveredApps");
			sleeper(2000); // It's mandatory to load Software Inventory of Intune portal
			waitForElementsOfDeviceDetailsPage("intuneAppsTable");
			List<WebElement> intuneAppList = getElementsOfDeviceDetailsPage("intuneAppsTable");
			for (WebElement intuneAppWebElement : intuneAppList) {
				String appNames = intuneAppWebElement.findElement(By.tagName("td")).getText();
				intuneApps.add(appNames);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception in fetchIntuneiOSSoftwareInventory" + ex.getMessage());
		}
		return intuneApps;
	}

	/**
	 * This method fetch Software Inventory of iOS device present on DaaS portal
	 * 
	 * @param languageCode
	 * @return ArrayList
	 */
	public final ArrayList<String> fetchDaasiOSSoftwareInventory(String languageCode) {
		ArrayList<String> daasSoftwareInventory = new ArrayList<String>();
		try {
			clickOnElementsOfDeviceDetailsPage("softwareTab");
			sleeper(2000); // It's mandatory to load complete Software Inventory on DaaS portal
			if (getTextOfDeviceDetailsPage("firstRowOfInventory").equals(getTextLanguage(languageCode, "daas_ui", "list.no_items"))) {
				return daasSoftwareInventory;
			} else {
				clickOnElementsOfDeviceDetailsPage("paginationLink");
				sleeper(1000); // It's mandatory to load element as page is already loaded
				clickOnElementsOfDeviceDetailsPage("paginationLastOption");
				sleeper(2000); // It's mandatory to load element as page is already loaded
				List<WebElement> softwareInventoryList = getElementsOfDeviceDetailsPage("daasSoftwareInventoryTable");
				for (WebElement webElement : softwareInventoryList) {
					daasSoftwareInventory.add(webElement.getText());
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured in fetchDaasiOSSoftwareInventory" + ex.getMessage());
		}
		return daasSoftwareInventory;
	}

	/**
	 * This method match the Software Inventory of iOS device from both Intune and DaaS portal
	 * 
	 * @param company
	 * @param languageCode
	 * @return boolean
	 */
	public final boolean matchSoftwareInvetoryOfiOSDevice(String company, String languageCode) {
		try {
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			createAndSwitchToNewTab();
			getUrl(ConstantURL.AZURE_PORTAL_URL);
			ArrayList<String> intuneApps = fetchIntuneiOSSoftwareInventory();
			switchBackToPreviousTab();
			waitForPageLoaded();
			ArrayList<String> daasApps = new ArrayList<>();
			if (deviceListPage.clickOnFirstDeviceBySelectingCompany(company, languageCode)) {
				daasApps = fetchDaasiOSSoftwareInventory(languageCode);
			} else {
				return false;
			}
			LOGGER.info("Apps present on Intune portal : " + intuneApps);
			LOGGER.info("Apps present on DaaS portal : " + daasApps);
			if (intuneApps.size() > 0) {
				if (daasApps.size() > 0) {
					for (int counter = 0; counter < daasApps.size(); counter++) {
						if (daasApps.contains(intuneApps.get(counter))) {
							LOGGER.info(intuneApps.get(counter) + " present on DaaS portal");
						} else {
							LOGGER.error("Error : " + intuneApps.get(counter) + " is not present on DaaS portal");
							return false;
						}
					}
				} else {
					LOGGER.error("Error : There is no Software Inventory present on DaaS portal");
					return false;
				}
			} else {
				LOGGER.error("Error : There is no Software Inventory present on Intune portal.");
				return false;
			}
		} catch (Exception e) {
		}
		return true;
	}

	/**
	 * This method is used to read api data for the warranty tile on staging
	 * 
	 * @param api - URL from you which you want the data
	 * @param body - request body
	 * @param index - label index
	 * @param data - event name required
	 * @throws Exception
	 */
	public final String getWarrantyTileInfoStaging(String api, String body, int index, String data) throws Exception {
		String jsonResponse = null;
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
		Response response = httpRequest.post(api);
		String expected = response.asString();
		JSONObject jsonObject, obj;
		jsonObject = new JSONObject(expected);
		JSONArray jsonArray = jsonObject.getJSONArray("warranty");
		obj = new JSONObject(jsonArray.get(index).toString());
		jsonResponse = new JSONObject(obj.get("table").toString()).get(data).toString();
		return jsonResponse;
	}

	/**
	 * @param api - URL from you which you want the data
	 * @param body - request body
	 * @param event - event name required
	 * @throws IOException
	 */
	public final String getSecurityTileInformation(String api, String body, String event) {
		String jsonResponse = null;
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			String expected = response.asString();
			JSONObject jsonObject, obj;
			jsonObject = new JSONObject(expected);
			JSONArray jsonArray = jsonObject.getJSONArray("resources").getJSONObject(0).getJSONArray("eventPayload");
			switch (event) {
			case "BitLocker Drive Encryption":
				if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("bitLocker")) {
					obj = new JSONObject(jsonArray.get(0).toString());
					jsonResponse = new JSONObject(obj.get("payload").toString()).getJSONObject("os_drive").getJSONObject("computed_data").get("status").toString();
				} else {
					LOGGER.error("BitLocker Drive Encryption is not present for selected device");
				}
				break;

			case "Antivirus and Threat Protection":
				if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("antivirus")) {
					obj = new JSONObject(jsonArray.get(1).toString());
					jsonResponse = new JSONObject(obj.get("payload").toString()).get("state").toString();
				} else {
					LOGGER.error("Antivirus and Threat Protection is not present for selected device");
				}
				break;

			case "Firewall and Network Protection":
				if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("fireWall")) {
					obj = new JSONObject(jsonArray.get(3).toString());
					jsonResponse = new JSONObject(obj.get("payload").toString()).get("state").toString();
				} else {
					LOGGER.error("Firewall and Network Protection is not present for selected device");
				}
				break;

			default:
				LOGGER.error("Invalid event name passed");
				break;
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getSecurityTileInformation" + e.getMessage());
		}

		if (jsonResponse.equalsIgnoreCase("Enabled")) {
			jsonResponse = "On";
		} else if (jsonResponse.equalsIgnoreCase("Disabled")) {
			jsonResponse = "Off";

		}
		return jsonResponse;
	}

	/**
	 * This method verify google chrome client application on device details page
	 * 
	 * @param deviceSerialNumber - chrome device for verifying client application
	 * @param languageCode - This is used as code for multiple languages.
	 * @return true - Boolean value return either true or false
	 */
	public final boolean verifyChromeClientApplicationDeviceDetails(String deviceSerialNumber, String languageCode) {
		boolean flag = true;
		try {
			DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			EnrollFakeDevice.checkDeviceDetailsBySelectingSerialNumber(deviceSerialNumber);
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("enrollmentTile");
			if (deviceDetailsPage.getTextOfDeviceDetailsPage("googleAdminConsoleLabel").equalsIgnoreCase(DeviceVariables.CLIENT_APPLICATION_GOOGLE_CHROME)) {
				LOGGER.info("Verified string : " + DeviceVariables.CLIENT_APPLICATION_GOOGLE_CHROME + " is localized");
			} else {
				LOGGER.info("Failed to validate string : " + DeviceVariables.CLIENT_APPLICATION_GOOGLE_CHROME);
			}
			String googleAdminVersion = getTextOfDeviceDetailsPage("googleAdminVersion");
			String[] googleAdminVersionArray = googleAdminVersion.split("\\s");
			for (String arrayString : googleAdminVersionArray) {
				switch (arrayString) {
				case DeviceVariables.CLIENT_APPLICATION_CHROME_UNKNOWN:
					LOGGER.info("Verified string : Version");
					break;
				case DeviceVariables.CLIENT_APPLICATION_CHROME_VERSION:
					if (arrayString.contains(getTextLanguage(languageCode, "lhserver", "devices_list.software_table.versionColumn"))) {
						LOGGER.info(DeviceVariables.CLIENT_APPLICATION_CHROME_VERSION + " is localized");
					} else {
						LOGGER.info(DeviceVariables.CLIENT_APPLICATION_CHROME_VERSION + "failed to localized");
						flag = false;
					}
					break;
				default:
					LOGGER.info(arrayString + "is not present on device details page for Chromebook");
					flag = false;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in verifyChromeClientApplication" + e.getMessage());
			flag = false;
		}
		return flag;
	}

	/** This method used to apply company filter in Device list page.
	 * @param LanguageCode
	 * @param textKey
	 * @param companySearchText
	 * @param emptyTextKey
	 * @param listKey
	 * @param dropdownBoxKey
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCompanyChangeOfDeviceListPage(String LanguageCode, String textKey, String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey) throws Exception {
		return verifySingleSelectDropdownByText(LanguageCode, deviceDetailsPageProperties.getProperty(textKey), companySearchText, deviceDetailsPageProperties.getProperty(emptyTextKey), deviceDetailsPageProperties.getProperty(listKey), deviceDetailsPageProperties.getProperty(dropdownBoxKey));
	}

	/**
	 * This method verifies newly added custom fields on device details page.
	 * 
	 * @param currentCustomFields - custom fields added on companies page
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCustomFieldsOnDeviceDetailsPage(ArrayList<String> currentCustomFields) throws Exception {
		boolean flag = false;
		List<WebElement> listOfSerialNumbers = getElementsOfDeviceDetailsPage("serialNumberList");
		List<String> listOfFields = new ArrayList<String>();
		waitForElementsOfDeviceDetailsPage("serialNumberList");
		serialNumberCustom = listOfSerialNumbers.get(0).getText();
		listOfSerialNumbers.get(0).click();
		//This sleeper is added so that it should wait for the redirection to device details page
		sleeper(3000);
		if (verifyElementsOfDeviceDetailsPage("deviceDetailsTitle")) {
			List<WebElement> listOfFieldNames = getElementsOfDeviceDetailsPage("listOfFieldsDetails");
			for (int i = 0; i < getElementsOfDeviceDetailsPage("listOfFieldsDetails").size(); i++) {
				listOfFields.add(listOfFieldNames.get(i).getText().toLowerCase());
			}
		} else {
			LOGGER.error("Device details page did not load successfully.");
		}
		if (listOfFields.equals(currentCustomFields)) {
			LOGGER.info("Newly added custom fields got reflected on device details page.");
			flag = true;
		} else {
			LOGGER.error("Fields did not reflected correctly on device details page.");
		}
		return flag;
	}

	/**
	 * This method verifies custom field values on device details page.
	 * 
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCustomfieldValues(String languageCode) throws Exception {
		boolean flag = false;
		int randomNumber = 0;
		Random random = new Random();
		String customField = null;
		List<String> listOfValues = new ArrayList<String>();
		if (verifyElementsOfDeviceDetailsPage("customFieldRow")) {
			List<WebElement> listOfeditIcons = getElementsOfDeviceDetailsPage("editIconFields");
			scrollDownByIndex();
			for (int i = 0; i < listOfeditIcons.size(); i++) {
				listOfeditIcons.get(i).click();
				randomNumber = random.nextInt(100);
				String randomString = String.valueOf(randomNumber);
				customField = getTextOfDeviceDetailsPage("customFieldName");
				enterText(deviceDetailsPageProperties.getProperty("fieldValue"), "Value" + randomString);
				listOfValues.add("Value" + randomString);
				clickOnElementsOfDeviceDetailsPage("saveButtonCustomField");
				String successMessage = getTextOfDeviceDetailsPage("toastNotification");
				if ((getTextLanguage(languageCode, "daas_ui", "asset.details.update.success")).replace("{name}", customField).equalsIgnoreCase(successMessage)) {
					clickOnElementsOfDeviceDetailsPage("closeToastNotification");
					sleeper(2000);//This sleeper is added so that it should wait for closing the toast notification before clicking on another edit button
				} else {
					LOGGER.error("Toast notification received after saving values of custom fields is incorrect.");
				}
			}
			List<WebElement> listOfFieldValues = getElementsOfDeviceDetailsPage("listOfValuesDetails");
			for (int j = 0; j < listOfFieldValues.size(); j++) {
				listOfValuesSaved.add(listOfFieldValues.get(j).getText());
			}
			if (listOfValuesSaved.equals(listOfValues)) {
				LOGGER.info("Values of newly added custom fields got reflected on device list page.");
				flag = true;
			} else {
				LOGGER.error("Values on device details page did not reflected.");
			}
		}
		return flag;
	}

	public final void scrollDownByIndex() {
		jsDriver().executeScript("scroll(0, 650);");
	}

	/**
	 * This method validates custom fields on list page.
	 * 
	 * @param currentCustomFields - custom fields added on companies page
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCustomfieldDeviceListPage(String languageCode, ArrayList<String> currentCustomFields) throws Exception {
		boolean flag = false;
		enterText(deviceDetailsPageProperties.getProperty("serialNumberSearchBox"), serialNumberCustom);
		sleeper(3000);
		List<WebElement> listOfColumnHeaders = getElementsOfDeviceDetailsPage("listOfHeadersDeviceList");
		List<WebElement> listOfFirstRow = getElementsOfDeviceDetailsPage("listOfValuesFirstRow");
		List<String> listOfColumnHeaderValues = new ArrayList<String>();
		List<String> listOfFirstRowValues = new ArrayList<String>();
		for (int i = 0; i < listOfColumnHeaders.size(); i++) {
			listOfColumnHeaderValues.add(listOfColumnHeaders.get(i).getText().toLowerCase());
			listOfFirstRowValues.add(listOfFirstRow.get(i).getText());
		}
		if (listOfColumnHeaderValues.containsAll(currentCustomFields) && listOfFirstRowValues.containsAll(listOfValuesSaved)) {
			flag = true;
			LOGGER.info("Custom fields and values are getting reflected on list page.");
		}
		return flag;
	}
	
	/**
	 * This method validates custom fields on Table configuration pop up.
	 * 
	 * @param currentCustomFields - custom fields added on companies page
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCustomfieldsOnTableConfigurationPopup(String languageCode, ArrayList<String> currentCustomFields) throws Exception {
		boolean flag = false;
		List<WebElement> listOfColumns = getElementsOfDeviceDetailsPage("listOfcheckedColumnsOnPopup");
		List<String> listOfColumnsValues = new ArrayList<String>();
		for (int i = 0; i < listOfColumns.size(); i++) {
			listOfColumnsValues.add(listOfColumns.get(i).getText().toLowerCase());
		}
		if (listOfColumnsValues.containsAll(currentCustomFields)) {
			flag = true;
			LOGGER.info("Custom fields are getting reflected on Table Configuration page.");
		}
		return flag;
	}
	
	/**
	 * This method used to verify drivers status
	 * 
	 * @param body - api body
	 * @param languageCode - language code
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyDriversStatus(String languageCode , String body) throws Exception {
		String jsonResponse = null;
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + getTokenFromLocalStorage("dui_token_e")).body(body);
		Response response = httpRequest.post(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ConstantURL.OUTDATED_CRITICAL_DRIVERS);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(response.body().asString());
		String[] data = "resources.driverName".split("\\.");
		List<JsonNode> jsonNodes = rootNode.findValues(data[1]);
		if (!(jsonNodes.isEmpty())) {
			jsonResponse = jsonNodes.get(0).asText().replaceAll("\\\\", "");
		}

		if (jsonResponse != null) {
			LOGGER.info("Verified driver name column is present");
			return verifyElementsOfDeviceDetailsPage("driverNameColumn");
		} else if (jsonNodes.isEmpty()) {
			LOGGER.info("Verified no critical drivers found text");
				return getTextOfDeviceDetailsPage("noDriversFoundText").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.critical_drivers_error"));	
		} else if (response.getStatusCode() == 404) {
			LOGGER.info("Verified critical drivers error text");
			return getTextOfDeviceDetailsPage("noDriversFoundText").equals(getTextLanguage(languageCode, "daas_ui", "devicedetail.tab.bios_and_drivers.critical_drivers_error"));
		} else {
			LOGGER.error("Outdated critical drivers tile is not present");
			return false;
		}
	}
	
	/**
	 * This method is used to select value from a drop down on device details page
	 * 
	 * @param dropdownId - locator of dropdown options
	 * @param text - current value on dropdown
	 * @return - newly selected value on dropdown
	 * @throws Exception
	 */
	public final String selectValueOnDropDownOfDeviceDetailsPage(String dropdownId, String text) throws Exception {
		return selectValueOnPopup(deviceDetailsPageProperties.getProperty(dropdownId), text);
	}
	
	/**
	 * This is a method to get element present on device details page
	 * 
	 * @param key - Locator of element
	 * @return - web element
	 */
	public final WebElement getElementOfDeviceDetailsPage(String key) {
		try {
			return getElement(deviceDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementOfDeviceDetailsPage " + e.getMessage()));
			return null;
		}
	}
	
	/**
	 * This method is used to validate the flow before the notification is generated
	 * 
	 * @return - integer value of unread notifications
	 */
	public int preNotificationCheckDeviceDetailsPage() {
		try {
			int countUnreadNotification = 0;
			String count = null;

			if (verifyElementsOfDeviceDetailsPage("notificationCount")) {
				count = getTextOfDeviceDetailsPage("notificationCount");
				countUnreadNotification = Integer.valueOf(count);

				waitForElementsOfDeviceDetailsPage("notificationBellIcon");
				clickOnElementsOfDeviceDetailsPage("notificationBellIcon");
				LOGGER.info("Clicked on notification bell icon");

				if (verifyElementsOfDeviceDetailsPage("unreadNotification")) {
					Actions action = new Actions(getDriver());
					action.moveToElement(getElementOfDeviceDetailsPage("unreadNotification")).build().perform();
					sleeper(5000);
					if (verifyElementsOfDeviceDetailsPage("notificationCount")) {
						count = getTextOfDeviceDetailsPage("notificationCount");
						countUnreadNotification = Integer.valueOf(count);
					}
				} else {
					LOGGER.info("First Notification is already read.");
				}

			} else {
				countUnreadNotification = 0;
			}
			LOGGER.info("Unread notification count is fetched.");
			return countUnreadNotification;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method preNotificationCheck " + e.getMessage()));
			return 0;
		}
	}
	
	/**
	 * This method is used to verify sequence of charts present on Device Details
	 * page
	 *
	 * @param allChartsLocatorKey: Position of all charts on the Device Details page at runtime
	 * @param chartIdsArray: Predefined position of all charts on the Device Details page
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyChartOrderOfDeviceDetailsPageSoftware(String allChartsLocatorKey, String[] chartIdsArray)
			throws Exception {
		Boolean flag = false;
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allCharts = getElementsTillAllElementsVisible(
				deviceDetailsPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter)
						.equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error(
							"Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
					break;
				}
			}
		}
		if (flag)
			LOGGER.info("Sequence of charts on software tab of Details Page is correct");
		return flag;
	}
	
	/**
	 * This method is used to verify presence(Fail in case of grid not loaded) of 
	 * data on software tab of Device Details page
	 * 
	 * @param id: Specify table name
	 * @param SWWindowsUpdatesData: Grid with data
	 * @param SWWindowsUpdatesNoData: Grid with no data(that is No data present)
	 * @return boolean value false when SWWindowsUpdatesData or SWWindowsUpdatesNoData not present
	 * @throws Exception
	 */

	public boolean verifyPresenceOfSWDataOnDeviceDetailsPage(String id, String SWUpdatesData, String SWUpdatesNoData)
			throws Exception {
		Boolean flag = false;

		if (verifyElementsOfDeviceDetailsPage(SWUpdatesData)) {
			flag = true;
			LOGGER.info("Data present under " + id);
		} else if (verifyElementsOfDeviceDetailsPage(SWUpdatesNoData)) {
			flag = true;
			LOGGER.info("Data not present under " + id);
		}
		return flag;
	}

	/**
	 * This method is used to verify columns in the both SW Updates tables -Windows
	 * and Office updates
	 * 
	 * @param reportTabName:  Specify table name
	 * @param columnNameKeys: Predefined list of columns in SW Updates tables
	 * @param LanguageCode:   language code
	 * @param folderName:     Folder name in which columnNameKeys present
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyColumnNamesInSWTable(String reportTabName, String[] columnNameKeys, String LanguageCode,
			String folderName) throws Exception {
		boolean flag = false;
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		if (analyticsPage.validateColumnNames(columnNameKeys, LanguageCode, folderName, reportTabName)) {
			flag = true;
		}
		return flag;
	}
	
	/** 
	 * This is a method to select date from calendar filter
	 * @param date - current date
	 * @param monthKeyCurrent - locator of current month
	 * @param rightArrowKey - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month 
	 */
	public final void selectDateFromCalenderOnDeviceDetailpage(String date, String monthKeyCurrent, String rightArrowKey, String daysOnCurrentMonthKey) {
		try {
			selectDateFromCalender(date, deviceDetailsPageProperties.getProperty(monthKeyCurrent), deviceDetailsPageProperties.getProperty(rightArrowKey), deviceDetailsPageProperties.getProperty(daysOnCurrentMonthKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderOnDeviceDetailpage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to get a list of elements of device details page
	 * 
	 * @param key - Locator of list
	 * @return - list of webElements
	 */
	public final List<WebElement> getElementsTillAllElementsVisibleofDeviceDetailpage(String key) {
		try {
			return getElementsTillAllElementsVisible(deviceDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofDeviceDetailpage " + e.getMessage()));
			return null;
		}
	}
	
	/**
	 * This method will validate warranty removal
	 * 
	 * @param deleteWarrantyLocator - list locator of remove warranty
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean validateWarrantyRemoval(String deleteWarrantyLocator) throws Exception {
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(2000);
		scrollOndeviceDetailsPage("warrantyTile");
		if (deviceDetailsPage.verifyElementsOfDeviceDetailsPage("removeWarranty")) {
			List<WebElement> warrantyList = getElementsTillAllElementsVisibleofDeviceDetailpage(deleteWarrantyLocator);
			for (int i = 0; i < warrantyList.size(); i++) {
					getDriver().findElement(By.xpath("//*[contains(@actionId,'remove-warranty" + i +"')]")).click();
				deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("deleteWarrantyPopUp");
				waitForElementsOfDeviceDetailsPage("toastNotificationKey");
				clickByJavaScriptOnDeviceDetailsPage("closeToastNotification");
			}
			return !deviceDetailsPage.verifyElementsOfDeviceDetailsPage("removeWarranty");
		} else {
			LOGGER.info("No warranties present on details page");
			return true;
		}
	}
	
	/**
	 * This method will validate Create Group Functionality
	 * 
	 * @param deviceserialnumberlist - Device Serial number list from the Device list Page'
	 * @param LanguageCode:   language code
	 * @return boolean
	 */
	public final boolean verifyCreateParentGroup(String MethodDropDownLocator, String propertyName, String propertyDropDownLocator,List<String> devicelist, String LanguageCode) {
	boolean flag = true;
		try {
			clickOnElementsOfDeviceDetailsPage("creategroupButton");
			waitForPageLoaded();
			//verification of details section in create group page
			String GroupName = "Static_Group" + generateRandomNumber();
			String GroupDescription = "Automation-Group-Creation" + generateRandomString(15);
			Assert.assertEquals(getTextOfDeviceDetailsPage("pageheaderbreadcrumb"),(getTextLanguage(LanguageCode, "daas_ui", "groups.breadcrumbs.create_group")), "Create Group Brudcrum Text is not equal");
			Assert.assertEquals(getTextOfDeviceDetailsPage("groupheader"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.create.header")), "Create Static Group header Text is not equal");
			Assert.assertEquals(getTextOfDeviceDetailsPage("groupsdetailsheader"),(getTextLanguage(LanguageCode, "daas_ui", "incident.detail.details")), "Details header Text is not equal");
			//verification of Group name field by providing a valid & unique name
			enterTextForDeviceDetailsPage("groupnamefield", GroupName);
			sleeper(2000);
			enterTextForDeviceDetailsPage("groupdescription", GroupDescription);
			LOGGER.info("Details section verfied sucessfully");
			//verification of expiration policy section in create group page
			scrollOndeviceDetailsPage("expirationpolicyheader");
			Assert.assertEquals(getTextOfDeviceDetailsPage("expirationpolicyheader"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.expiration.policy.label")), "Expiration Policy header is not equal");
			if(verifyElementIsClickableOfDeviceDetailsPage("enddatefield")==false) {
				clickOnElementsOfDeviceDetailsPage("expirationEndDatecheckbox");
				// Verification of Expiration date selection by giving more than 6 months 
				clickOnElementsOfDeviceDetailsPage("enddatefield");
				selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(182), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
				Assert.assertEquals(getTextOfDeviceDetailsPage("enddateerrormessage"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.expiration.max.month.error")),"End Date error message is not equal");
				clickOnElementsOfDeviceDetailsPage("enddateclear");
				// Verification of Expiration date selection by giving less than or equal to 6 months 
				clickOnElementsOfDeviceDetailsPage("enddatefield");
				selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(10), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			}
			LOGGER.info("Expiration policy section verfied sucessfully");
			//verification of Hierarchy section
			scrollOndeviceDetailsPage("hierarchyheader");
			Assert.assertEquals(getTextOfDeviceDetailsPage("hierarchyheader"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.hierarchy.label")), "Hierarchy header is not equal");
			LOGGER.info("Hierarchy section verfied sucessfully");
			//verification of select method section
			scrollOndeviceDetailsPage("selectmethodheader");
			clickOnElementsOfDeviceDetailsPage("groupmethodDD");
			sleeper(2000);
			String Methodvalue = getTextOfDeviceDetailsPage(MethodDropDownLocator);
			clickOnElementsOfDeviceDetailsPage(MethodDropDownLocator);
			Assert.assertEquals(getTextOfDeviceDetailsPage("selectmethodheader"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.method.label")), "Select Method header is not equal");
			clickOnElementsOfDeviceDetailsPage("propertydropdown");
			sleeper(2000);
			List<WebElement> propertyList = getElementsTillAllElementsVisibleofDeviceDetailpage(propertyDropDownLocator);
			for (int i = 0; i < propertyList.size(); i++) {
				if (propertyName.equals(propertyList.get(i).getText())) {
					propertyList.get(i).click();
					break;
				}
			}
			if(Methodvalue.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "groups.method.option.manual_import"))){
			for(String propertyvalue:devicelist) {
				enterTextwithoutclearForDeviceDetailsPage("methodvalue",propertyvalue+",");
				}
			}else {
				for(String propertyvalue:devicelist) {
				uploadCSVFunctionality(devicelist);
				sleeper(20000); // wait time until the devices are getting added
				verifyExpectedFieldsAvailableInExportedDeviceDetailsFile("Errors",propertyvalue);
				}
			}
			LOGGER.info("Select method section verfied sucessfully");
			clickOnElementsOfDeviceDetailsPage("createbutton");
			//verify the created group name is getting reflected on the group list
			waitForElementsOfDeviceDetailsPage("toastNotification");
			Assert.assertTrue(verifyElementsOfDeviceDetailsPage("toastNotification"),"Toast notification did not received for Create Group Functionality");
			waitForPageLoaded();
			enterTextForDeviceDetailsPage("searchgroupfield", GroupName);
			sleeper(3000);
			clickOnElementsOfDeviceDetailsPage("searchcleartext");
			Assert.assertTrue(getAllTextOfDeviceDetailsPage("grouplist").contains(GroupName));
			LOGGER.info("Created Group name gets displayed on group side panel in Device List page");
			LOGGER.info("Parent-Root Group Creation Verifed Sucessfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating group" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	
	/**
	 * This method will validate Device List, count & Device details group section after group creation
	 * 
	 * @param deviceserialnumberlist - Device Serial number list from the Device list Page'
	 * @param LanguageCode:   language code
	 * @return boolean
	 */
	public final boolean verifyDevicecountandlistaftergroupcreation(List<String> devicelist) {
		boolean flag = true;
			try {
				//Creating a group with devices mapped in it
				scrollOndeviceDetailsPage("lastgroup");
				String groupname = getTextOfDeviceDetailsPage("lastgroup");
				sleeper(8000);
				clickOnElementsOfDeviceDetailsPage("lastgroup");
				String Tile = getTextOfDeviceDetailsPage("groupnametile");
				//Verification of created group name tile after clicking on group
				Assert.assertEquals(groupname,Tile,"Group name tile not getting matched on the devices list for the selected group");
				LOGGER.info("Group name tile is getting matched with the created group name");
				List<String> listaftercreation = getAllTextOfDeviceDetailsPage("deviceserialnumberfield");
				//Verification of device count after creation of the group
				Assert.assertEquals(listaftercreation.size(),devicelist.size(),"Device count doesnot match when compared after creation");
				LOGGER.info("Device count is getting matched when compared after creation");
				//Verification of device device details page whether the created group name exists in the list
				clickOnElementsOfDeviceDetailsPage("firstdevicetext");
				scrollOndeviceDetailsPage("devicegroupheader");
				sleeper(4000);
				String Groupsname = getAttributesOfDeviceDetailsPage("devicegroupsectiontexts","innerText");
				Assert.assertTrue(Groupsname.contains(Tile));
				LOGGER.info("Newly added Group name is present on the device details page under group section");
			} catch (Exception e) {
				LOGGER.error("Exception occured while Verifying the created group" + e.getMessage());
				return false;
			}
			return flag;
			}
			
	
	/**
	 * This method will validate Edit Group Functionality
	 * 
	 * @param LanguageCode:   language code
	 * @return boolean
	 */
	public final boolean verifyeditGroupfunctionality(String LanguageCode) {
	boolean flag = true;
	String GroupName = "Parent-Group_" + generateRandomNumber();
	String GroupDescription = "Description_Parent-Group_" + generateRandomString(6);
		//String nodatetext = getTextLanguage(LanguageCode, "daas_ui", "group-expiration-no-end-date-selection");
		try {
			scrollOndeviceDetailsPage("lastgroup");
			rightclickOnElementsOfDeviceDetailsPage("lastgroup");
			clickOnElementsOfDeviceDetailsPage("editgroupinformation");
			Assert.assertEquals(getTextOfDeviceDetailsPage("pageheaderbreadcrumb"),(getTextLanguage(LanguageCode, "daas_ui", "groups.breadcrumbs.edit_group")), "Edit Group Brudcrum Text is not equal");
			getTextOfDeviceDetailsPage("groupheader");
			Assert.assertEquals(getTextOfDeviceDetailsPage("groupsdetailsheader"),(getTextLanguage(LanguageCode, "daas_ui", "incident.detail.details")), "Details header Text is not equal");
			//Updating the group name field
			clearTextRefreshFromDeviceDetailsTextField("groupnamefield");
			//verification of Group name field by providing a valid & unique name
			enterTextForDeviceDetailsPage("groupnamefield", GroupName);
			LOGGER.info("Group name field section updated sucessfully");
			scrollOndeviceDetailsPage("groupnamefield");
			//Updating the group description field
			clearTextRefreshFromDeviceDetailsTextField("groupdescription");
			enterTextForDeviceDetailsPage("groupdescription", GroupDescription);
			LOGGER.info("Group description field section updated sucessfully");
			LOGGER.info("Details section verfied sucessfully");
			//verification of expiration policy section in create group page
			Assert.assertEquals(getTextOfDeviceDetailsPage("expirationpolicyheader"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.expiration.policy.label")), "Expiration Policy header is not equal");
			scrollOndeviceDetailsPage("expirationpolicyheader");
			if(verifyElementIsClickableOfDeviceDetailsPage("enddatefield")==false) {
				clickOnElementsOfDeviceDetailsPage("expirationEndDatecheckbox");
				// Verification of Expiration date selection by updating the date by less than or equal to 6 months from the current date
				clickOnElementsOfDeviceDetailsPage("enddatefield");
				selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(30), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			}else {
				clickOnElementsOfDeviceDetailsPage("enddatefieldclearbutton");
				clickOnElementsOfDeviceDetailsPage("enddatefield");
				selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(30), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			}
			LOGGER.info("Expiration Date field updated sucessfully");
		    String ExpirationDate= getAttributesOfDeviceDetailsPage("enddatefield","value");
			LOGGER.info("Expiration policy section verfied sucessfully");
			
			Assert.assertEquals(getTextOfDeviceDetailsPage("hierarchyheader"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.hierarchy.label")), "Hierarchy header is not equal");
			scrollOndeviceDetailsPage("hierarchyheader");
			LOGGER.info("Hierarchy section verfied sucessfully");
			Assert.assertFalse(verifyElementsOfDeviceDetailsPage("selectmethodheader"), "Select Method header is present");
			clickOnElementsOfDeviceDetailsPage("savebutton");
			Assert.assertTrue(verifyElementsOfDeviceDetailsPage("toastNotification"),"Toast notification did not received for Create Group Functionality");
			sleeper(2000);
			scrollOndeviceDetailsPage("lastgroup");
				rightclickOnElementsOfDeviceDetailsPage("lastgroup");
				clickOnElementsOfDeviceDetailsPage("editgroupinformation");
				//verification of Name,description & Expiration End-Date field as updated field texts
				waitForPageLoaded();
				Assert.assertEquals(getAttributesOfDeviceDetailsPage("groupnamefield","value"),(GroupName),"Group name did not match on the edit configuration page");
				LOGGER.info("Edited group name value matched successfully");
				scrollOndeviceDetailsPage("groupnamefield");
				Assert.assertEquals(getTextOfDeviceDetailsPage("groupdescription"),(GroupDescription),"Group Description did not match on the edit configuration page");
				LOGGER.info("Edited group description value matched successfully");
				Assert.assertEquals(getAttributesOfDeviceDetailsPage("enddatefield","value"),(ExpirationDate),"Expiration EndDate did not match on the edit configuration page");
				LOGGER.info("Edited group Expiration Date value matched successfully");
				sleeper(2000);
				scrollOndeviceDetailsPage("expirationpolicyheader");
				sleeper(2000);
				clickOnElementsOfDeviceDetailsPage("discardbutton");
			LOGGER.info("Edit Group funtionality verified successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured while editing group" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * This method will validate ADD Device to Existing Group Functionality
	 * 
	 * @param firstdevicedetail - First Device Detail from the Device list Page
	 * @param LanguageCode:   language code
	 * @return boolean
	 */
	public final boolean verifyadddevicestogroupfunctionality(List<String> devicelist, String propertyName, String propertyDropDownLocator, String LanguageCode) {
		boolean flag = true;
		try {
			    //Verification of Add Device to group functionality through Device Selection method
				waitForPageLoaded();
				sleeper(2000);
				clickOnElementsOfDeviceDetailsPage("allgroupscheckbox");
				//scrollOndeviceDetailsPage("newgroupondevicepage");
				rightclickOnElementsOfDeviceDetailsPage("firstgroup");
				clickOnElementsOfDeviceDetailsPage("addtogroup");
				waitForElementsOfDeviceDetailsPage("toastNotification");
				Assert.assertTrue(verifyElementsOfDeviceDetailsPage("toastNotification"),"Toast notification did not received for Add Device to Group Functionality");
				LOGGER.info("Add Device to group functionality through Device Selection method Verified successfully.");
			    //Verification of Add Device to group functionality through normal Add to Group method
				sleeper(2000);
				clickOnElementsOfDeviceDetailsPage("allgroupscheckbox");
				scrollOndeviceDetailsPage("lastgroup");
				rightclickOnElementsOfDeviceDetailsPage("lastgroup");
				clickOnElementsOfDeviceDetailsPage("addtogroup");
				Assert.assertEquals(getTextOfDeviceDetailsPage("pageheaderbreadcrumb"),(getTextLanguage(LanguageCode, "daas_ui", "groups.action.add_to_group")), "Edit Group Brudcrum Text is not equal");
				Assert.assertEquals(getTextOfDeviceDetailsPage("groupheader"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.add_to_group.header")), "Edit Static Group header Text is not equal");
				Assert.assertEquals(getTextOfDeviceDetailsPage("groupsdetailsheader"),(getTextLanguage(LanguageCode, "daas_ui", "incident.detail.details")), "Details header Text is not equal");
				scrollOndeviceDetailsPage("addtodevicemethodheader");
				clickOnElementsOfDeviceDetailsPage("groupmethodDD");
				//verification of select method section
				clickOnElementsOfDeviceDetailsPage("methodpropertyvalue");
				clickOnElementsOfDeviceDetailsPage("propertydropdown");
				sleeper(2000);
				List<WebElement> propertyList = getElementsTillAllElementsVisibleofDeviceDetailpage(propertyDropDownLocator);
				for (int i = 0; i < propertyList.size(); i++) {
					if (propertyName.equals(propertyList.get(i).getText())) {
						propertyList.get(i).click();
						break;
					}
				}
				for(String devicedetail:devicelist) {
					enterTextwithoutclearForDeviceDetailsPage("addtogroupmethodvalue",devicedetail+",");
					}
				LOGGER.info("Select method section verfied sucessfully");
				clickOnElementsOfDeviceDetailsPage("savebutton");
				waitForElementsOfDeviceDetailsPage("toastNotification");
				Assert.assertTrue(verifyElementsOfDeviceDetailsPage("toastNotification"),"Toast notification did not received for Add Device to Group Functionality");
				sleeper(6000);
				LOGGER.info("Add Device to group functionality through normal Add to Group method Verified successfully.");
			LOGGER.info("Add Device to Existing Group Functionality verified successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating group" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	
	/**
	 * This method will validate Create Hierarchy Group Functionality
	 * 
	 * @param firstdevicedetail - First Device Detail from the Device list Page
	 * @param LanguageCode:   language code
	 * @return boolean
	 */
	public final boolean verifyHierarchyGroupCreation(String firstdevicedetail,String propertyName, String propertyDropDownLocator,  String LanguageCode) {
	boolean flag = true;
		try {
			for(int i = 0; i<8;i++) {
				clickOnElementsOfDeviceDetailsPage("creategroupButton");
				waitForPageLoaded();
			//verification of details section in create group page
			String GroupName = "Test-Group-Creation" + generateRandomNumber();
			String GroupDescription = "Automation-Group-Creation" + generateRandomString(30);
			enterTextForDeviceDetailsPage("groupnamefield", GroupName);
			sleeper(2000);
			enterTextForDeviceDetailsPage("groupdescription", GroupDescription);
			LOGGER.info("Details section verfied sucessfully for level " + i);
			//verification of expiration policy section in create group page
			scrollOndeviceDetailsPage("expirationpolicyheader");
			if(verifyElementIsClickableOfDeviceDetailsPage("enddatefield")==false) {
				clickOnElementsOfDeviceDetailsPage("expirationEndDatecheckbox");
				// Verification of Expiration date selection by giving no of days to expire parent group
				clickOnElementsOfDeviceDetailsPage("enddatefield");
				selectDateFromCalenderOnDeviceDetailpage(addDaysToCurrentDate(5), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
				}
			LOGGER.info("Expiration policy section verfied sucessfully for level " + i);
			//verification of Hierarchy section for all 6 levels
			scrollOndeviceDetailsPage("hierarchyheader");
			if(i==1) {
				scrollOndeviceDetailsPage("newgroupname");
				clickOnElementsOfDeviceDetailsPage("newgroupname");
			}if(i>=2 && i<7){
			    scrollOndeviceDetailsPage("lastexpandicon");
			    multipleclickOnElementsOfDeviceDetailsPage("lastexpandicon",i);
				sleeper(2000);
				scrollOndeviceDetailsPage("subgroupname");
				clickOnElementsOfDeviceDetailsPage("subgroupname");
			}if(i==7) {
			    scrollOndeviceDetailsPage("lastexpandicon");
			    multipleclickOnElementsOfDeviceDetailsPage("lastexpandicon",i);
			    sleeper(2000);
				scrollOndeviceDetailsPage("subgroupname");
				clickOnElementsOfDeviceDetailsPage("subgroupname");
				Assert.assertEquals(getTextOfDeviceDetailsPage("higherlevelerrormsg"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.hierarchy.max_group_level_error")), "Higher level error message did not match");
				clickOnElementsOfDeviceDetailsPage("cancelbutton");
				clickOnElementsOfDeviceDetailsPage("lastgroup");
				String parentgroupname = getTextOfDeviceDetailsPage("lastgroup");
				//Verify Delete Parent Group with respective child
				Assert.assertTrue(deletegroup());
				clickOnElementsOfDeviceDetailsPage("firstdevicetext");
				scrollOndeviceDetailsPage("devicegroupheader");
				sleeper(4000);
				String Groupsnames = getAttributesOfDeviceDetailsPage("devicegroupsectiontexts","innerText");
				Assert.assertTrue(!Groupsnames.contains(parentgroupname));
			}
			LOGGER.info("Hierarchy section verfied sucessfully for level " + i);
			//verification of select method section
			if(i!=7) {
				//verification of select method section
				scrollOndeviceDetailsPage("selectmethodheader");
				clickOnElementsOfDeviceDetailsPage("groupmethodDD");
				clickOnElementsOfDeviceDetailsPage("methodpropertyvalue");
				Assert.assertEquals(getTextOfDeviceDetailsPage("selectmethodheader"),(getTextLanguage(LanguageCode, "daas_ui", "groups.form.method.label")), "Select Method header is not equal");
				clickOnElementsOfDeviceDetailsPage("propertydropdown");
				sleeper(2000);
				List<WebElement> propertyList = getElementsTillAllElementsVisibleofDeviceDetailpage(propertyDropDownLocator);
				for (int j = 0; j < propertyList.size(); j++) {
					if (propertyName.equals(propertyList.get(j).getText())) {
						propertyList.get(j).click();
						break;
					}
				}
			enterTextForDeviceDetailsPage("methodvalue", firstdevicedetail);
			clickOnElementsOfDeviceDetailsPage("createbutton");
			LOGGER.info("Select method section verfied sucessfully for level " + i);
			sleeper(5000);
			  }
			}
			LOGGER.info("Hierarchy Group Creation Verifed Sucessfully");
		}catch (Exception e) {
			LOGGER.error("Exception occured while creating group" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * This method will validate Remove Device from group Functionality
	 * @return boolean
	 * @throws IOException 
	 */
	public final boolean verifyremovefromdevicefromgroup(String Languagecode) throws IOException {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		boolean flag = true;
		try {
			 //Verification of Remove Device from group Functionality
			scrollOndeviceDetailsPage("lastgroup");
			clickOnElementsOfDeviceDetailsPage("lastgroup");
			LOGGER.info("Clicked on the group name which is recently created");
			String Tile = getTextOfDeviceDetailsPage("groupnametile");
			if(verifyElementsOfDeviceDetailsPage("Nodevicefoundtext")==false) {
				clickOnElementsOfDeviceDetailsPage("allgroupscheckbox");
				LOGGER.info("Clicked on all checkbox on the device list after a particular group is selected");
				sleeper(2000);
				rightclickOnElementsOfDeviceDetailsPage("lastgroup");
				clickOnElementsOfDeviceDetailsPage("removefromgroup");
				LOGGER.info("Clicked on the remove from group option");
				sleeper(2000);
				clickOnElementsOfDeviceDetailsPage("groupdeselection");
				waitForPageLoaded();
				deviceListPage.verifyonlyactivedevicesdatafetchforstaticgroups(Languagecode,"clearListPageFilter","activestatusheader","devicestatusDD","devicestatusddvalues","firstcolumnheader");
				clickByJavaScriptOnDeviceDetailsPage("globalLocationFilterDropDown");
				waitForPageLoaded();
				LOGGER.info("Search company to set location filter");
				enterTextForDeviceDetailsPage("filterMenuCompaniesSearch", CommonVariables.Company_Device_Grouping);
				sleeper(4000);
				waitForElementsOfDeviceDetailsPage("companyOnListSearch");
				Assert.assertTrue(verifyElementsOfDeviceDetailsPage("companyOnListSearch"));
				clickByJavaScriptOnDeviceDetailsPage("companyOnListSearch");
				clickByJavaScriptOnDeviceDetailsPage("globalFilterSave");
				LOGGER.info("Global Location Filter Saved successfully.");
				clickOnElementsOfDeviceDetailsPage("firstdevicetext");
				scrollOndeviceDetailsPage("devicegroupheader");
				sleeper(4000);
				String Groupsname = getAttributesOfDeviceDetailsPage("devicegroupsectiontexts","innerText");
				Assert.assertTrue(!Groupsname.contains(Tile));	
				LOGGER.info("Removed Group name is not present on the details page");
				//verification of removal notification in notification center
				String notificationText = null;
				sleeper(15000); // time required for the notification message to pop on notification window
				waitForElementsOfDeviceDetailsPage("notificationBellIcon");
				clickOnElementsOfDeviceDetailsPage("notificationBellIcon");
				LOGGER.info("Clicked on notification bell icon");
				sleeper(30000); // time required for the notification message to load
				if (verifyElementsOfDeviceDetailsPage("latestNotification")) {
					notificationText = getTextOfDeviceDetailsPage("latestNotification");
					if (notificationText.contains("devices successfully removed from group")) {
						flag = true;
						LOGGER.error("Verified Message on notification window it's getting matched");
					} else {
						LOGGER.error("Message on notification window is incorrect");
					}
				} else {
					LOGGER.error("Notification for Removal of Device groups did not display/delay in notification for more than 30 seconds");
				}
				LOGGER.info("Remove Device from group functionality verified successfully");
			}else {
				LOGGER.info("No Devices present in the selected groups");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating group" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * This method will validate Delete group Functionality
	 * @return boolean
	 */
	public final boolean deletegroup() {
		boolean flag = true;
		try {
			 //Verification of Delete group Functionality
			scrollOndeviceDetailsPage("lastgroup");
			String createdgroup = getTextOfDeviceDetailsPage("lastgroup");
			clickOnElementsOfDeviceDetailsPage("lastgroup");
			LOGGER.info("Clicked on the group name which is recently created");
			rightclickOnElementsOfDeviceDetailsPage("lastgroup");
			clickOnElementsOfDeviceDetailsPage("deletegroup");
			LOGGER.info("Clicked on the delete group option");
			String Securitycode = getTextOfDeviceDetailsPage("securityCode");
			enterTextForDeviceDetailsPage("scuritycodeinputbox", Securitycode);
			LOGGER.info("security code Entered sucessfully");
			sleeper(2000);
			clickOnElementsOfDeviceDetailsPage("deletebutton");
			sleeper(2000);
			Assert.assertTrue(verifyElementsOfDeviceDetailsPage("toastNotification"));
			sleeper(2000);
			enterTextForDeviceDetailsPage("searchgroupfield", createdgroup);
			getTextOfDeviceDetailsPage("nodatamessage");
			LOGGER.info("Group Deleted successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured while deleting the created group" + e.getMessage());
			return false;
		}
		return flag;
		}
	/**
	 * This method is used to test exported file on device details page.
	 * @param - Sheet name whose columns data need to be verify
	 * @param - Column name which should be available in exported file
	 * @return - boolean value of whether the expected column is available in Exported file or not.
	 */
	public boolean verifyExpectedFieldsAvailableInExportedDeviceDetailsFile(String sheetName, String expectedColumnName) {
		try {

			boolean flag = true;
			File f = new File(ConstantPath.DOWNLOAD_PATH);
			if (f.exists() == false) {
				File file = new File(ConstantPath.DOWNLOAD_PATH);
				FileUtils.forceMkdir(file);
			}
			if (f.listFiles().length > 0) {
				for (File file : f.listFiles()) {
					if (file.isFile()) {

						ExcelReader excelReader = new ExcelReader(file);
						String columnList = excelReader.getHeaderDataOfExpectedSheet(file, sheetName);
						if(!columnList.contains(expectedColumnName))
							flag = false;
						if(!flag)
							LOGGER.info("Exported file does not contains expected column data in the sheet of the XLS file.");
						LOGGER.info("Device Details exported file verification successfully.");
					}
				}
			} else {
				flag = false;
				LOGGER.info("There is no file inside the folder");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyExpectedFieldsAvailableInExportedDeviceDetailsFile " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * Upload user via CSV
	 * @param inviteUserFirstName
	 * @param inviteUser_email
	 * @throws Exception
	 */
	public void uploadCSVFunctionality(List<String> devicelist) throws Exception {
		CSVFileReader csvFileReader = new CSVFileReader();
		File csvFile = new File(ConstantPath.IMPORT_PATH + DeviceVariables.ImportDevice_Group_UPLOAD);
		csvFileReader.writeDataCSVmultiple(csvFile, devicelist);
		fileImportInV3(ConstantPath.IMPORT_PATH + DeviceVariables.ImportDevice_Group_UPLOAD);
	}
	
	/**
	 * File Import in V3
	 * @param filename
	 * @throws Exception
	 */
	public void fileImportInV3(String fileName) throws Exception {
		sleeper(2000);
		WebElement addFile = getElementDeviceDetailsPage("browserInput");
		addFile.sendKeys(fileName);
		sleeper(3000);
	}

	/**
	 * This method is used to validate the notification flow after the devices have been imported for veneer version 3
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean postNotificationCheckDevicegroupsForSuccessfull() {
		try {
			boolean flag = false;
			String notificationText = null;
			sleeper(15000); // time required for the notification message to pop on notification window
			waitForElementsOfDeviceDetailsPage("notificationBellIcon");
			clickOnElementsOfDeviceDetailsPage("notificationBellIcon");
			LOGGER.info("Clicked on notification bell icon");
			sleeper(30000); // time required for the notification message to load
			if (verifyElementsOfDeviceDetailsPage("latestNotification")) {
				notificationText = getTextOfDeviceDetailsPage("latestNotification");
				if (notificationText.contains("devices successfully added to group")) {
					flag = true;
					LOGGER.error("Verified Message on notification window it's getting matched");
				} else {
					LOGGER.error("Message on notification window is incorrect");
				}
			} else {
				LOGGER.error("Notification for Device groups did not display/delay in notification for more than 30 seconds");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckDevicegroupsForSuccessfull " + e.getMessage()));
			return false;
		}
	}
	
	
	/**
	 * This method is used to validate the notification flow after the devices have been imported for veneer version 3
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean postNotificationCheckDevicegroupsForFailuredownload() {
		try {
			boolean flag = false;
			String notificationText = null;
			//Assert.assertEquals(getTextOfDeviceDetailsPage("toastNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "device.details.export.device.data.success")), "Toast Notification text does not match");
			sleeper(30000); // time required for the notification message to load
			if (verifyElementsOfDeviceDetailsPage("latestNotification")) {
				notificationText = getTextOfDeviceDetailsPage("latestNotification");
				if (notificationText.contains("Devices were not added to group")) {
					waitForElementsOfDeviceDetailsPage("firstNotification");
					mousehoverOnDeviceDetailsPage("hamburgerMenuOnNotification");
					clickByJavaScriptOnDeviceDetailsPage("hamburgerMenuOnNotification");
					clickByJavaScriptOnDeviceDetailsPage("failuredownload");
					LOGGER.error("Clicked on the Download failure data option successfully");
					sleeper(10000);// wait till file gets downloaded.
					flag = true;
					LOGGER.error("Verified Message on failure notification window it's getting matched and downloaded the failure data successfully");
				} else {
					LOGGER.error("Message on notification window is incorrect");
				}
			} else {
				LOGGER.error("Notification for Device groups did not display/delay in notification for more than 30 seconds");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method postNotificationCheckDevicegroupsForFailuredownload " + e.getMessage()));
			return false;
			}
		}
	
	/**
	 * This method is used verify Automatic Health Diagnostics
	 * 
	 * @param statusVal - Automatic Health Diagnostics value
	 * @return boolean
	 */
	public boolean verifyAutomaticHealthDiagnosticsonDeviceDetails(String statusVal) {
		if(statusVal.equalsIgnoreCase("Enabled") || statusVal.equalsIgnoreCase("Disabled"))
			return true;
		else
			return false;	
	}
	
	/**
	 * Compare for all plans available in device detail page
	 * @param key
	 * @param subTypes
	 * @return
	 * @throws Exception
	 */
	public final boolean compareDeviceDetailPlans(String key, ArrayList<String> subTypes) throws Exception {
		return comparePlansList(deviceDetailsPageProperties.getProperty(key), subTypes);
	}
	
	public final boolean comparePlansList(String key, ArrayList<String> array) throws Exception {
		ArrayList<String> menuTabs = new ArrayList<String>();
		List<WebElement> menuList = getElementsTillAllElementsPresent(key);
		menuList.remove(menuList.size() - 1);
		
		for (WebElement listItem : menuList) {
			String value = listItem.getText();
			menuTabs.add(value);
		}
		
		if (array.containsAll(menuTabs)) {
			return true;
		}

		return false;
	}
	
	public final String getSystemTileInformation(String api, String body) {
		String jsonResponseId = null;
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			String expected = response.asString();
			JSONObject jsonObject, obj;
			jsonObject = new JSONObject(expected);
			JSONArray jsonArray = jsonObject.getJSONArray("resources");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				obj = new JSONObject(jsonArray.get(i).toString());
				jsonResponseId = new JSONObject(obj.toString()).get("bornOnDate").toString();
			}
			LOGGER.info(jsonResponseId);
		} catch (Exception e) {
			LOGGER.error("Exception in getSecurityTileInformation" + e.getMessage());
		}

		return jsonResponseId;
	}
	
	public final String getSystemTileAPIInformation(String api, String body) {
		String jsonResponseId = null;
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			LOGGER.info(response.statusCode());
			jsonResponseId = response.jsonPath().getString("bornOnDate");
			LOGGER.info(jsonResponseId);
		} catch (Exception e) {
			LOGGER.error("Exception in getSystemTileAPIInformation" + e.getMessage());
		}

		return jsonResponseId;
	}
	
	/**
	 * This method returns Device-ID from URL in Device details page.
	 * @return string deviceID
	 */
	public final String getDeviceIDfromURL() {
		
		String url = getUrlOfCurrentPage();
		String[] arrOfStr = url.split("/");
		String deviceID = arrOfStr[arrOfStr.length -1];
		return deviceID;
	}

	
	/**
	 * @param languageCode
	 * @param currentCustomFields
	 * @param headers
	 * @return
	 * @throws Exception
	 * This method compares verify the headers of the Network error table
	 */
	public final boolean verifyheadersofDeviceDetailspage(String languageCode, ArrayList<String> currentCustomFields, String headers, String hyperlinkDetails) throws Exception {
		try{
			boolean flag = false;
		List<String> listOfHeaderValues = new ArrayList<String>();
		List<WebElement> summaryheaders = getElementsOfDeviceDetailsPage(headers);
		for(WebElement headerunique:summaryheaders) {
			String headertext = headerunique.getText();
			listOfHeaderValues.add(headertext);
		}
		sleeper(2000);
		if(listOfHeaderValues.equals(currentCustomFields)) {
		LOGGER.info("Headers verified successfully");
		flag=true;	
		}else {
		LOGGER.info("Verification of column headers failed");
		}
		if(verifyElementsOfDeviceDetailsPage(hyperlinkDetails)) {
			clickOnElementsOfDeviceDetailsPage(hyperlinkDetails);
			switchToNextTabOfDeviceDetailsPage();
			switchToPreviousTabOfDeviceDetailsPage();	
		}else
		{
			LOGGER.info("No hyperlinks present.");
		}
	return flag;
	}catch(Exception e){
		LOGGER.error("Exception occured in method verifyheadersofDeviceDetailspage" + e.getMessage());
		return false;
	}
	}
	 
	/**
	 * This method is used to switch to previous tab of device details page
	 */
	public final void switchToPreviousTabOfDeviceDetailsPage() {
		switchBackToPreviousTab();
	}
	
	/**
	 * This method is used to switch to Next tab of device details page
	 */
	public final void switchToNextTabOfDeviceDetailsPage() {
		switchToDifferentTab();
	}
	
	/**
	 * @param languageCode
	 * @param currentCustomFields
	 * @param headers
	 * @return
	 * @throws Exception
	 * This method will navigate to Network Error tab and will click on the summary tab
	 */
	public final boolean verifynavigationToNetworkErrortab(String networkTab, String networkError, String networkerrorsheader, String Summary) throws Exception {
		try{
			boolean flag = false;
			LOGGER.info("Redirected to device details page");
			// Click on network tab
			clickOnElementsOfDeviceDetailsPage(networkTab);
			LOGGER.info("Clicked on network tab");
			waitForElementsOfDeviceDetailsPage(networkError);
			clickOnElementsOfDeviceDetailsPage(networkError);
			LOGGER.info("clicked on network error");
			scrollOndeviceDetailsPage(networkerrorsheader);
			waitForElementsOfDeviceDetailsPage(Summary);
			if(verifyElementsOfDeviceDetailsPage(Summary)) {
				clickOnElementsOfDeviceDetailsPage(Summary);
				LOGGER.info("Network error card is present in Network Tab");
				flag=true;
			}
			else {
				LOGGER.info("Network error card is Not present in Network Tab");
				flag=false;
			}
	return flag;
	}catch(Exception e){
		LOGGER.error("Exception occured in method verifynavigationToNetworkErrortab" + e.getMessage());
		return false;
	}

	}
}