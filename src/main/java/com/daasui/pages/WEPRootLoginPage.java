package com.daasui.pages;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class WEPRootLoginPage extends CommonMethod {

	private WEPRootLoginPage instance;
	private ObjectReader WEPRootLoginPagePropertiesReader = new ObjectReader();
	private Properties WEPRootLoginPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPRootLoginPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPRootLoginPage.class) {
				if (instance == null) {
					instance = new WEPRootLoginPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEPRootLoginPage(WebDriver driver) throws IOException {
		WEPRootLoginPageProperties = WEPRootLoginPagePropertiesReader.getObjectRepository("WEPRootLoginPage");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEPRootLoginPage(String key) throws Exception {
		click(WEPRootLoginPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEPRootLoginPage(String key) {
		try {
			return verifyElementIsVisible(WEPRootLoginPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEPRootLoginPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPRootLoginPage(String key) {
		try {
			return verifyElementIsPresent(WEPRootLoginPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPRootLoginPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEPRootLoginPage(String key) throws Exception {
		return getTextBy(WEPRootLoginPageProperties.getProperty(key));
	}

	public void refreshPageOfWEPRootLoginPage() throws Exception {
		refreshPage();
	}
	
	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEPRootLoginPage(String key, String Text) {
		try {
			enterText(WEPRootLoginPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForfWEPRootLoginPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEPRootLoginPage(String key) {
		try {
			scrollTillView(WEPRootLoginPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEPRootLoginPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEPRootLoginPage(String key) {
		try {
			mouseHover(WEPRootLoginPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEPRootLoginPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEPRootLoginPage(String key) throws Exception {
		clickByJavaScript(WEPRootLoginPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOnWEPRootLoginPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEPRootLoginPageProperties.getProperty(key), Text);
	}

	public final List<WebElement> getElementsTillAllElementsVisibleOnWEPRootLoginPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEPRootLoginPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWEPRootLoginPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEPRootLoginPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		System.out.println(listOfOptions + " : list");
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEPRootLoginPage(String key) throws Exception {
		actionClick(WEPRootLoginPageProperties.getProperty(key));
	}
	
	public final void sideMenuSelectionWEPRootLoginPage(String lang,String parentmenu, String childmenu) throws Exception {
		if(!verifyElementsOfWEPRootLoginPage("sideRootMenuCollapseState")) {
			clickByJavaScriptOnWEPRootLoginPage("sideRootMenuExpandButton");
		}
		sideMenuSelection(lang, WEPRootLoginPageProperties.getProperty(parentmenu), WEPRootLoginPageProperties.getProperty(childmenu));
		System.out.println("chilmenu : " + childmenu);
	}
	
	public final void selectValueFromDropdownOnWEPRootLoginPage(String dropDownInput, String inputField, String text, String value) throws Exception {
		actionClickOnWEPRootLoginPage(dropDownInput);
		enterTextOfWEPRootLoginPage(inputField,text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleOnWEPRootLoginPage(value);
		for (WebElement country : countryList) {
			if (country.getText().equalsIgnoreCase(text)) {
				country.click();
				break;
			}
		}
	}
	
	/**
	 * Delete User via API
	 * @param environment
	 * @param tenantID
	 * @param userID
	 * @param environmentURL
	 * @return
	 */
	public final boolean removeInactiveCustomerUser(String environment, String tenantID, String userID, String environmentURL) {
		try {
			boolean flag = false;
			int code = getStatusCode(environmentURL + ConstantURL.DELETE_WEX_INACTIVE_USER, 
					"{\"tenantId\": \"" + tenantID + "\",\"invitedUserIds\": [\"" + userID + "\"]}","DELETE", environment);
			if (code != CommonVariables.CODEOK) {
				flag = false;
				LOGGER.error("Delete API got failed while removing Users.");
			} else
				flag = true;

			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeNonCompanyUser: " + e.getMessage());
			return false;
		}
	}

	public String verifyEmailContent (String subject, String environment, String inboxEmailAddress, boolean privateDomain) throws Exception {
		String mailContent;
		Mailinator objMailinator = new Mailinator("",inboxEmailAddress.split("@")[0]);
		sleeper(5000);//required wait because script is breaking over here
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(getEnvironmentSpecificData(System.getProperty("environment"), subject), inboxEmailAddress.split("@")[0], privateDomain);
		if (objMailinatorEmail != null) {
			mailContent = objMailinatorEmail.getBody();
			return getReceivedHtmlParserMailSpaceThree(mailContent);
		} else {
			LOGGER.error("Mail not found");
			return "";
		}
	}
	
	public String getInviationcode(String api,String key,String tenantId,String UserEmail) throws Exception {
		String jsonResponse = null;
		try {
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			String body = "{\"query\":\"{\\\"_source\\\":{},\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"must\\\":[{\\\"match\\\":{\\\"userName\\\":{\\\"query\\\":\\\"" + UserEmail + "\\\",\\\"operator\\\":\\\"AND\\\"}}}]}},{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"must_not\\\":{\\\"match\\\":{\\\"mappedStatus.en.name.keyword\\\":\\\"INACTIVE\\\"}}}}]}},{\\\"bool\\\":{\\\"must\\\":[{\\\"exists\\\":{\\\"field\\\":\\\"userName\\\"}},{\\\"bool\\\":{\\\"must_not\\\":{\\\"match\\\":{\\\"userName.keyword\\\":\\\"\\\"}}}}]}}]}},\\\"size\\\":100,\\\"from\\\":0,\\\"sort\\\":[{\\\"userName.sort_en\\\":{\\\"order\\\":\\\"ASC\\\",\\\"unmapped_type\\\":\\\"string\\\"}},{\\\"meta.created\\\":{\\\"order\\\":\\\"DESC\\\",\\\"unmapped_type\\\":\\\"long\\\"}}]}\",\"index_list\":[\"idmusers\"],\"search_type\":\"tenanted\",\"tenant_ids\":[]}";
			System.out.println("body:" + body);
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
			if (response.getStatusCode() == 200) {				
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(response.body().asString());
				if (null!=rootNode) {									
					jsonResponse = rootNode.get("hits").get("hits").get(0).get(key).asText();
					System.out.println("jsonResponse: " + jsonResponse);
				} else {
					jsonResponse = "";
				}
			}
			else {
				System.out.println("Response code: " + response.getStatusCode());
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getDataFromApiPost" + e.getMessage());
		}
		return jsonResponse;
	}
	
	public final boolean removeInactiveCustomerMSP(String environment, String userID, String environmentURL) {
		try {
			boolean flag = false;
			int code = getStatusCode(environmentURL + ConstantURL.DELETE_WEX_INACTIVE_MSP + userID, 
					  "{}" , "DELETE" , environment);
			System.out.println("code: " + code);
			if (code != CommonVariables.CODEOK) {
				flag = false;
				LOGGER.error("Delete API got failed while removing Users.");
			} else
				flag = true;

			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeNonCompanyUser: " + e.getMessage());
			return false;
		}
	}
}

