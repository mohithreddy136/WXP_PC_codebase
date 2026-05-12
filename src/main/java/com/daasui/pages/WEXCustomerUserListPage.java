package com.daasui.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.WEPCustomerVariables;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class WEXCustomerUserListPage extends CommonMethod {

	private WEXCustomerUserListPage instance;
	private ObjectReader WEXCustomerUserListPagePropertiesReader = new ObjectReader();
	private Properties WEXCustomerUserListPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEXCustomerUserListPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXCustomerUserListPage.class) {
				if (instance == null) {
					instance = new WEXCustomerUserListPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEXCustomerUserListPage(WebDriver driver) throws IOException {
		WEXCustomerUserListPageProperties = WEXCustomerUserListPagePropertiesReader.getObjectRepository("WEXCustomerUserListPage");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEXCustomerUserListPage(String key) throws Exception {
		click(WEXCustomerUserListPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEXCustomerUserListPage(String key) {
		try {
			return verifyElementIsVisible(WEXCustomerUserListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEXCustomerUserListPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEXCustomerUserListPage(String key) {
		try {
			return verifyElementIsPresent(WEXCustomerUserListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEXCustomerUserListPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEXCustomerUserListPage(String key) throws Exception {
		return getTextBy(WEXCustomerUserListPageProperties.getProperty(key));
	}

	public void refreshPageOfWEXCustomerUserListPage() throws Exception {
		refreshPage();
	}
	
	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEXCustomerUserListPage(String key, String Text) {
		try {
			enterText(WEXCustomerUserListPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXCustomerUsersListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEXCustomerUserListPage(String key) {
		try {
			scrollTillView(WEXCustomerUserListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEXCustomerUserListPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEXCustomerUserListPage(String key) {
		try {
			mouseHover(WEXCustomerUserListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEXCustomerUserListPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEXCustomerUserListPage(String key) throws Exception {
		clickByJavaScript(WEXCustomerUserListPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOnWEXCustomerUserListPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXCustomerUserListPageProperties.getProperty(key), Text);
	}
	
	public final boolean matchValueOnWEXCustomerUserListPage(String key, String Text) throws Exception {
		if(getValueOfField(key).equals(Text))
			return true;
		else
			return false;
	}
	
	public final List<WebElement> getElementsTillAllElementsVisibleOnWEXCustomerUserListPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEXCustomerUserListPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWEXCustomerUserListPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEXCustomerUserListPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void actionClickOnWEXCustomerUserListPage(String key) throws Exception {
		actionClick(WEXCustomerUserListPageProperties.getProperty(key));
	}
	
	public final void selectValueFromDropdownOnWEXCustomerUserListPage(String dropDownInput, String inputField, String text, String value) throws Exception {
		actionClickOnWEXCustomerUserListPage(dropDownInput);
		enterTextOfWEXCustomerUserListPage(inputField,text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleOnWEXCustomerUserListPage(value);
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
		objMailinator.disableSslVerification();
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(subject, inboxEmailAddress.split("@")[0], privateDomain);
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
            LOGGER.error("Exception in body" + mspAuthToken);
			String body = "{\"query\":\"{\\\"_source\\\":{},\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"must\\\":[{\\\"match\\\":{\\\"userName\\\":{\\\"query\\\":\\\"" + UserEmail + "\\\",\\\"operator\\\":\\\"AND\\\"}}}]}},{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"must_not\\\":{\\\"match\\\":{\\\"mappedStatus.en.name.keyword\\\":\\\"INACTIVE\\\"}}}}]}},{\\\"bool\\\":{\\\"must\\\":[{\\\"exists\\\":{\\\"field\\\":\\\"userName\\\"}},{\\\"bool\\\":{\\\"must_not\\\":{\\\"match\\\":{\\\"userName.keyword\\\":\\\"\\\"}}}}]}}]}},\\\"size\\\":100,\\\"from\\\":0,\\\"sort\\\":[{\\\"userName.sort_en\\\":{\\\"order\\\":\\\"ASC\\\",\\\"unmapped_type\\\":\\\"string\\\"}},{\\\"meta.created\\\":{\\\"order\\\":\\\"DESC\\\",\\\"unmapped_type\\\":\\\"long\\\"}}]}\",\"index_list\":[\"idmusers\"],\"search_type\":\"tenanted\",\"tenant_ids\":[]}";
            LOGGER.error("Exception in body" + body);
            RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.post(api);
            LOGGER.error("Exception in body" + response);
			if (response.getStatusCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(response.body().asString());
				if (null!=rootNode) {									
					jsonResponse = rootNode.get("hits").get("hits").get(0).get(key).asText();
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
	
	public String getValueOfField(String key) throws Exception {
	    WebElement element = getElement(WEXCustomerUserListPageProperties.getProperty(key));
	    return element.getAttribute("value");
	}
	
	public String getCurrentUserProfile() throws Exception {
		   
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("profile");
		sleeper(2000);
		waitForPageLoaded();
		 String CurrentUser= getTextOfWEXCustomerUserListPage("currentAccountName");
		 return CurrentUser.trim();
	   
	}
	
	public final void switchUser(String dropdownListKey, String searchUser) throws Exception {
		String currentUserName = getValueOfField(searchUser);
		LOGGER.info("\n" + currentUserName);
		actionClickOnWEXCustomerUserListPage(searchUser);
		sleeper(2000);
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEXCustomerUserListPage(dropdownListKey);
		for (WebElement user : listOfOptions) {
			if (!user.getText().equals(currentUserName)) {
				LOGGER.info("\nUserList: " + user.getText());
				user.click();
				break;
			}
		}
		
	}
	public final void switchUser(String userSwitchDropDown, String userToSwitch, String searchUser) throws Exception {
		
		actionClickOnWEXCustomerUserListPage(searchUser);
		sleeper(2000);
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEXCustomerUserListPage(userSwitchDropDown);
		for (WebElement user : listOfOptions) {
			if (user.getText().contains(userToSwitch)) {
				LOGGER.info("\nUserList: " + user.getText());
				user.click();
				break;
			}
		}
		String currentUserName = getValueOfField(searchUser);
		LOGGER.info("\n" + currentUserName);
		
	}
	public final void switchAccount(String userSwitchDropDown, String userToSwitch) throws Exception {
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		try {
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("switchAccount");
			sleeper(2000);
			waitForPageLoaded();
			LOGGER.info("Clicked on the Switch Account option");
			WEXCustomerUserListPage.switchUser(userSwitchDropDown, userToSwitch, "switchSelectdropDown");	
			WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("switchButton");
			waitForPageLoaded();
			LOGGER.info("Switch User successfully");
		}
		catch(Exception e) {
			Assert.fail("Exception occured in SwitchAccount " + e.getMessage());	
		}
	}
	
	
	/**
	 * @param key - Locator of element
	 * @return - - boolean value of whether the element is selected or not
	 */
	public final boolean verifyElementsIsSelectedOfWEXUserListPage(String key) {
	try {
		return verifyElementIsSelected(WEXCustomerUserListPageProperties.getProperty(key));
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method verifyElementsOfWEPPulsesCreationPage " + e.getMessage()));
		return false;
	}
	}
	
	
	private boolean verifyElementIsPresentOfWEXUserListPage(String key) throws Exception {
		return verifyElementIsPresent(WEXCustomerUserListPageProperties.getProperty(key));
	}

		/**
	 * This method returns the text of a list of elements
	 * @throws Exception 
	 */
	public ArrayList<String> getTextOfListOfElementsWEXUsersListPage(String key) throws Exception {
		return getList(WEXCustomerUserListPageProperties.getProperty(key));
			
	}
	
	/**
	 * This method extracts the link from the email content.
	 * @param UserEmail
	 * @return extracted link
	 */

	public final String extractLink(String actualMailContent) {
		environment = System.getProperty("environment");
		String regex = null;
		try {
            // Define the regex pattern to match the URL
			
            switch (environment) {
                case "US-STABLE-WEP":
                	regex = "https:\\\\/\\\\/(usdevms\\-workforce|eudevms\\-workforce)\\.hppipeline\\.com\\\\/services\\\\/oauth_handler\\\\/onecloud\\\\/accept-invite\\?invitationCode=[a-zA-Z0-9\\-]+";
                    break;

                case "EU-STABLE-WEP":
                	regex = "https:\\\\/\\\\/(usdevms\\-workforce|eudevms\\-workforce)\\.hppipeline\\.com\\\\/services\\\\/oauth_handler\\\\/onecloud\\\\/accept-invite\\?invitationCode=[a-zA-Z0-9\\-]+";
                    break;

                case "US-STAGE-WEP":
                	regex = "https:\\\\/\\\\/(eustagingms|usstagingms)\\.workforceexperience\\.hp\\.com\\\\/services\\\\/oauth_handler\\\\/onecloud\\\\/accept-invite\\?invitationCode=[a-zA-Z0-9\\-]+";
                    break;

                case "EU-STAGE-WEP":
                	regex = "https:\\\\/\\\\/(eustagingms|usstagingms)\\.workforceexperience\\.hp\\.com\\\\/services\\\\/oauth_handler\\\\/onecloud\\\\/accept-invite\\?invitationCode=[a-zA-Z0-9\\-]+";
                    break;

                case "US-PROD-WEP":
                	regex = "https:\\\\/\\\\/(eu\\.)?workforceexperience\\.hp\\.com\\\\/services\\\\/oauth_handler\\\\/onecloud\\\\/accept-invite\\?invitationCode=[a-zA-Z0-9\\-]+";
                    break;

                case "EU-PROD-WEP":
                	regex = "https:\\\\/\\\\/(eu\\.)?workforceexperience\\.hp\\.com\\\\/services\\\\/oauth_handler\\\\/onecloud\\\\/accept-invite\\?invitationCode=[a-zA-Z0-9\\-]+";
                    break;

                case "US-VENEER-WEP":
                    regex = "https:\\\\/\\\\/(eustagingms|usstagingms)\\.workforceexperience\\.hp\\.com\\\\/services\\\\/oauth_handler\\\\/onecloud\\\\/accept-invite\\?invitationCode=[a-zA-Z0-9\\-]+";
                    break;

                default:
                    System.out.println("Unknown Environment: " + environment );
            }
            System.out.println("valid URL: " + regex);
        } catch (Exception e) {
            System.out.println("Invalid URL: " + regex);
        }

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(actualMailContent);

        // Find and print the URL
        String cleanedUrl = null;
        if (matcher.find()) {
            String extractedLink = matcher.group();
            System.out.println("extractedLink: " + extractedLink);
            cleanedUrl = extractedLink.replace("\\/", "/");
            // Print the cleaned URL
            System.out.println("Cleaned URL: " + cleanedUrl);

        } else {
            System.out.println("No link found.");
        }
        return cleanedUrl;
    }
	
	
	public String invitationMail(String subject, String environment, String inboxEmailAddress, boolean privateDomain) throws Exception {
		String mailContent;
		Mailinator objMailinator = new Mailinator("", inboxEmailAddress.split("@")[0]);
		sleeper(5000);//required wait because script is breaking over here
		objMailinator.disableSslVerification();
		mailContent = objMailinator.getRawEmailResponse(subject, inboxEmailAddress.split("@")[0], privateDomain);
		if (mailContent != null) {
			return mailContent;}
		else {
			LOGGER.error("Mail not found");
			return "";
		}
	}
	
	
	/**
	 * This method is used to get the actual mail content.
	 * @param UserEmail
	 * @return actual mail content
	 */
	public final String getActualMailContent(String UserEmail, String Subject) {
		try {
			
			String actualMailContent = invitationMail(Subject, environment, UserEmail, true);
			LOGGER.info("actualMailContent : ", actualMailContent);
			int count = 0;
			while (count < 5 && actualMailContent == "") {
				sleeper(1000);
				count++;
				LOGGER.info("Email not received : ", count);
				actualMailContent = invitationMail(Subject, environment, UserEmail, true);
			}
			return actualMailContent;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getActualMailContent " + e.getMessage()));
			return null;
		}
	}
	
	public String extractVerificationCode(String emailContent) {
		// Define the regex pattern to match a 6-digit code
		String regex = "\\b\\d{6}\\b";

		// Compile the pattern
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(emailContent);

		// Find and return the first match
		if (matcher.find()) {
			System.out.println("Verification code is: " + matcher.group());
			return matcher.group();
		} else {
			return "Verification code not found.";
		}
	}
	
	public final String getVerificationEmail(String UserEmail, String Subject) {
		try {
			String actualMailContent = verifyEmailContent(Subject, environment, UserEmail, true);
			System.out.println("actualMailContent :" + actualMailContent);
			int count = 0;
			while (count < 5 && actualMailContent == "") {
				sleeper(1000);
				count++;
				System.out.println(count + " : Email not received");
				actualMailContent = verifyEmailContent(Subject, environment, UserEmail, true);
			}
			return actualMailContent;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getVerificationEmail " + e.getMessage()));
			return null;
		}
	}
	
	public Boolean onBoardInvitedUser(String UserEmail) throws Exception {
		
		try {
            sleeper(2000);
            clickByJavaScriptOnWEXCustomerUserListPage("usePasswordButton");
            sleeper(3000);
			clickByJavaScriptOnWEXCustomerUserListPage("passowrdTextBox");
			enterTextOfWEXCustomerUserListPage("passowrdTextBox", WEPCustomerVariables.CUSTOMER_PWD);
			sleeper(2000);
			if (verifyElementIsPresentOfWEXUserListPage("createBtn")) {
				actionClickOnWEXCustomerUserListPage("createBtn");
			}else {
				actionClickOnWEXCustomerUserListPage("signinBtn");
			}
			LOGGER.info("User login password created!");
			sleeper(3000);
			if (verifyElementIsPresentOfWEXUserListPage("codeTextBox")) {
				//Verifying email address
				String subjectVerificationCode = "Verify your email address";
				String verificationEmailContent = getVerificationEmail(UserEmail, subjectVerificationCode);
				String code = extractVerificationCode(verificationEmailContent);
				waitForPageLoaded();
				clickByJavaScriptOnWEXCustomerUserListPage("codeTextBox");
				enterTextOfWEXCustomerUserListPage("codeTextBox", code);
				clickByJavaScriptOnWEXCustomerUserListPage("verifyButton");
				sleeper(7000);
				LOGGER.info("Verified email address!");
				waitForPageLoaded();
			}
			sleeper(10000);
//			waitForElementsOfWEXCustomerUserListPage("termAndConditionCheckbox");
//			clickByJavaScriptOnWEXCustomerUserListPage("termAndConditionCheckbox");
//			LOGGER.info("TnC checkbox clicked!");
//			waitForPageLoaded();
//			verifyElementsOfWEXCustomerUserListPage("AcceptBtn");
//			clickByJavaScriptOnWEXCustomerUserListPage("AcceptBtn");
//			waitForPageLoaded();
//			LOGGER.info("TnC accepted for the user" + UserEmail);
			
			verifyElementsOfWEXCustomerUserListPage("welcomePopupSkipButton");
			clickByJavaScriptOnWEXCustomerUserListPage("welcomePopupSkipButton");
			LOGGER.info("Accepted the invite for user :" + UserEmail);
			return true;
		}catch (Exception e) {
			LOGGER.error(("Exception occured in method onBoardInvitedUser " + e.getMessage()));
			return false;
		}
		
	}		

	/**
	 * File Import in V3
	 * @param filename
	 * @throws Exception
	 */
	public void fileImportInV3(String fileName) throws Exception {
		StringSelection path = new StringSelection(fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);   
		Robot robot = new Robot();
		robot.setAutoDelay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		sleeper(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public void uploadCSVOnWEXUserPage() throws Exception {
		
		String filenm = ConstantPath.IMPORT_PATH + WEPCustomerVariables.Import_CSV_UPLOAD;
		LOGGER.info(filenm);
		fileImportInV3(filenm);
		}
	
	public Boolean verifyCSVuploadOnUserlistPage() throws Exception {
		try {
			enterTextOfWEXCustomerUserListPage("emailInput",WEPCustomerVariables.USER_NAME);
			waitForElementsOfWEXCustomerUserListPage("tableOverlay");
			sleeper(2000);
			LOGGER.info("Customer User name validated on User List page successfully");
			if (!verifyElementsIsSelectedOfWEXUserListPage("selectAllCheckbox"))
					clickByJavaScriptOnWEXCustomerUserListPage("selectAllCheckbox");
			sleeper(1000);
			waitForElementsOfWEXCustomerUserListPage("selectedCount");
			verifyElementsOfWEXCustomerUserListPage("selectedCount");
			String text = getTextOfWEXCustomerUserListPage("selectedCount").substring(0,1);
			Integer countValue = Integer.parseInt(text);
			LOGGER.info(countValue + " Users imported");
			if(countValue >= WEPCustomerVariables.USER_COUNT)
				return true;
			else
				return false;
			}catch (Exception e) {
				LOGGER.error(("Exception occured in method verifyCSVuploadOnUserlistPage " + e.getMessage()));
				return null;
			}	
	}
	
	public Boolean verifyDeleteOnCSVuploadedUsers() throws Exception {
		try {
		
			clickByJavaScriptOnWEXCustomerUserListPage("selectAllCheckbox");
			waitForElementsOfWEXCustomerUserListPage("deleteText");
			clickByJavaScriptOnWEXCustomerUserListPage("deleteText");
			sleeper(2000);
			waitForPageLoaded();
			verifyElementsOfWEXCustomerUserListPage("deleteparaText");
	        String secutitycode = getTextOfWEXCustomerUserListPage("securityCode");
	        enterTextOfWEXCustomerUserListPage("securityInput", secutitycode);
	        clickByJavaScriptOnWEXCustomerUserListPage("resendsubmit");
	        sleeper(3000);
	   		waitForPageLoaded();
			sleeper(2000);
			if(verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")) {
				LOGGER.info("CSV uploaded Users removed successfully");
				return true;
			} else
				return false;
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method verifyDeleteOnCSVuploadedUsers " + e.getMessage()));
				return null;
			}
	}
	
	public String verifyDeleteUser(String UserEmail) throws Exception {
		try {
			enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			waitForElementsOfWEXCustomerUserListPage("tableOverlay");
			waitForElementsOfWEXCustomerUserListPage("emailInputValue");
			sleeper(2000);
			clickByJavaScriptOnWEXCustomerUserListPage("checkbox");
			waitForElementsOfWEXCustomerUserListPage("deleteText");
			clickByJavaScriptOnWEXCustomerUserListPage("deleteText");
			sleeper(2000);
			waitForPageLoaded();
			verifyElementsOfWEXCustomerUserListPage("popuptext");
			verifyElementsOfWEXCustomerUserListPage("deleteparaText");
			verifyElementsOfWEXCustomerUserListPage("resendsubmit");
			verifyElementsOfWEXCustomerUserListPage("resendCancel");
		    String secutitycode =  getTextOfWEXCustomerUserListPage("securityCode");
		    enterTextOfWEXCustomerUserListPage("securityInput", secutitycode);
		    clickByJavaScriptOnWEXCustomerUserListPage("resendsubmit");
		    sleeper(3000);
		    verifyElementsOfWEXCustomerUserListPage("emailInput");
			enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
			waitForPageLoaded();
			sleeper(2000);
			if(verifyElementsOfWEXCustomerUserListPage("noItemsAvailable")) {
				LOGGER.info(UserEmail + " user deleted");
				return getTextOfWEXCustomerUserListPage("noItemsAvailable");
			} else {
				LOGGER.info(UserEmail + " user deletion failed");
				return null;
			}	
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method verifyDeleteUser " + e.getMessage()));
				return null;
			}
		}
	public final void verifyElementisClickableonWEXCustomerUserListPage(String key) throws Exception {
		verifyElementIsClickable(WEXCustomerUserListPageProperties.getProperty(key));
	}
	
	public final void waitUntillElementIsPresentonWEXCustomerUserListPage(String key) throws Exception {
		verifyElementIsClickable(WEXCustomerUserListPageProperties.getProperty(key));
	}
	
	public void selectCustomRoleFromDropdown(String ITAdminRole) throws Exception {
		    verifyElementsOfWEXCustomerUserListPage("userRoleDropDown"); // Wait until visible
		   
	        verifyElementisClickableonWEXCustomerUserListPage("roleClearButton");
	        actionClickOnWEXCustomerUserListPage("roleClearButton");
		    verifyElementisClickableonWEXCustomerUserListPage("userRoleDropDown"); // Wait for dropdown to be clickable

		    actionClickOnWEXCustomerUserListPage("userRoleDropDown");
		    waitUntillElementIsPresentonWEXCustomerUserListPage("roleSearchInput"); // Wait for search input to be visible

		    enterTextOfWEXCustomerUserListPage("roleSearchInput", ITAdminRole);
		    waitUntillElementIsPresentonWEXCustomerUserListPage("selectRoleOption"); // Wait for options to be visible
		    clickOnElementsOfWEXCustomerUserListPage("selectRoleOption");
	}
	
	/**
     * Checks if the login page is currently open by verifying the presence of a unique login element.
     * @return true if login page is open, false otherwise
     */
    public boolean isLoginPageOpen() {
        try {
            // Replace "loginButton" with the actual key for your login page element in the properties file
            return verifyElementIsPresent(WEXCustomerUserListPageProperties.getProperty("loginButton"));
        } catch (Exception e) {
            LOGGER.error("Exception in isLoginPageOpen: " + e.getMessage());
            return false;
        }
    }
	
}