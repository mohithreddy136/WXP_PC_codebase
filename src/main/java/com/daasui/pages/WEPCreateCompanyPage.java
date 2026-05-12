package com.daasui.pages;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.WEPCustomerVariables;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class WEPCreateCompanyPage extends CommonMethod {

	private WEPCreateCompanyPage instance;
	private ObjectReader WEPCreateCompanyPagePropertiesReader = new ObjectReader();
	private Properties WEPCreateCompanyPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPCreateCompanyPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPCreateCompanyPage.class) {
				if (instance == null) {
					instance = new WEPCreateCompanyPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEPCreateCompanyPage(WebDriver driver) throws IOException {
		WEPCreateCompanyPageProperties = WEPCreateCompanyPagePropertiesReader.getObjectRepository("WEPCreateCompanyPage");
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfWEPCreateCompanyPage(String key) throws Exception {
		click(WEPCreateCompanyPageProperties.getProperty(key));
	}

	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfWEPCreateCompanyPage(String key) {
		try {
			return verifyElementIsVisible(WEPCreateCompanyPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEPCreateCompanyPage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfWEPCreateCompanyPage(String key) {
		try {
			return verifyElementIsPresent(WEPCreateCompanyPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfWEPCreateCompanyPage " + e.getMessage()));
			return false;
		}
	}

	public final String getTextOfWEPCreateCompanyPage(String key) throws Exception {
		return getTextBy(WEPCreateCompanyPageProperties.getProperty(key));
	}

	public void refreshPageOfWEPCreateCompanyPage() throws Exception {
		refreshPage();
	}
	
	/**
	 * This is a method to enter text on an element
	 *
	 * @param key - Locator of element
	 * @param text - The text to be entered
	 */
	public final void enterTextOfWEPCreateCompanyPage(String key, String Text) {
		try {
			enterText(WEPCreateCompanyPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForfWEPCreateCompanyPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to scroll on WEX Customer User List Page
	 *
	 * @param key - Locator of element
	 */
	public final void scrollOnWEPCreateCompanyPage(String key) {
		try {
			scrollTillView(WEPCreateCompanyPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWEPCreateCompanyPage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnWEPCreateCompanyPage(String key) {
		try {
			mouseHover(WEPCreateCompanyPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnWEPCreateCompanyPage " + e.getMessage()));
		}
	}

	public final void clickByJavaScriptOnWEPCreateCompanyPage(String key) throws Exception {
		clickByJavaScript(WEPCreateCompanyPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOnWEPCreateCompanyPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEPCreateCompanyPageProperties.getProperty(key), Text);
	}

	public final List<WebElement> getElementsTillAllElementsVisibleOnWEPCreateCompanyPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEPCreateCompanyPageProperties.getProperty(key));
	}

	public final int countOfAllElementOnWEPCreateCompanyPage(String key) throws Exception {
		return getAllElements(WEPCreateCompanyPageProperties.getProperty(key)).size();
	}
	public final String selectFirstValueFromDropdownOnWEPCreateCompanyPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEPCreateCompanyPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}
	public final void actionClickOnWEPCreateCompanyPage(String key) throws Exception {
		actionClick(WEPCreateCompanyPageProperties.getProperty(key));
	}
	
	public final void sideMenuSelectionWEPCreateCompanyPage(String lang,String parentmenu, String childmenu) throws Exception {
		sideMenuSelection(lang, WEPCreateCompanyPageProperties.getProperty(parentmenu), WEPCreateCompanyPageProperties.getProperty(childmenu));
		System.out.println("chilmenu : " + childmenu);
	}
	
	public final void selectValueFromDropdownOnWEPCreateCompanyPage(String dropDownInput, String inputField, String text, String value) throws Exception {
		actionClickOnWEPCreateCompanyPage(dropDownInput);
		enterTextOfWEPCreateCompanyPage(inputField,text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleOnWEPCreateCompanyPage(value);
		for (WebElement country : countryList) {
			if (country.getText().equalsIgnoreCase(text)) {
				country.click();
				break;
			}
		}
	}
	
	public final String selectPlanDropdownOnWEPCreateCompanyPage(String dropdownListKey,String planName) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEPCreateCompanyPage(dropdownListKey);
		String text;
		if(planName=="WXP Pro Trial") {
		text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		}else {
		text = listOfOptions.get(1).getText();
		listOfOptions.get(1).click();
		}
		return text;
	}
	/**
	 * Find if the element is clickable or not
	 * 
	 */
	public final boolean verifyElementIsClickableOnCreateCompanyPage(String locator) throws Exception {
		return verifyElementIsClickable(WEPCreateCompanyPageProperties.getProperty(locator));
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
	
	   public final boolean selectTextValueFromDropdownOfWEPCreateCompanyPage(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
			return selectTextValueFromDropdown(WEPCreateCompanyPageProperties.getProperty(dropdownListKey), elementText, WEPCreateCompanyPageProperties.getProperty(dropdownBox));
		}

	/**
	 * This is a method to add company.
	 * 
	 * @param languageCode - languageCode for the localization testing
	 * @param compName     - Name of the company
	 * @param country      - Name of the country
	 * @param MSPName      - Name of the MSP which is going to select
	 * @param partnerName  - Name of the partner which is going to select
	 * @param partnerFlag  - if flag is true then parter will be selected, if
	 *                     partner flag is false then partner will not be selcted.
	 * @return - boolean value it should be return either true or false
	 * @throws Exception
	 */

	public final boolean addCompanies(String languageCode, String compName, String compEmail, String country,
			String planName, String MSPName, String partnerName, String firstName, String lastName, boolean partnerFlag,
			boolean loginFlag, boolean MSPFlag) throws Exception {

		ArrayList<String> infoLabels = new ArrayList<>(Arrays.asList(
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.msp_id").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.street_address").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.city").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.state").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.zip_code").toLowerCase().trim(),
				getTextLanguage(languageCode, "daas_ui", "create_company.msp.country").toLowerCase().trim()));

		ArrayList<String> MSPDetails = new ArrayList<>(Arrays.asList(
				getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID").toLowerCase().trim(),
				CommonVariables.STREET_ADDRESS.toLowerCase().trim(), CommonVariables.CITY.toLowerCase().trim(),
				CommonVariables.STATE.toLowerCase().trim(), CommonVariables.ZIP_CODE, country.toLowerCase().trim()));

		boolean flag = false;

		try {
			clickOnElementsOfWEPCreateCompanyPage("addCompany");
			if (loginFlag) {
				clickOnElementsOfWEPCreateCompanyPage("addNxtBtn");
			}

			LOGGER.info("Filling Customer Info");
			enterTextOfWEPCreateCompanyPage("CompanyName", compName);
			enterTextOfWEPCreateCompanyPage("Address1", CommonVariables.STREET_ADDRESS);
			enterTextOfWEPCreateCompanyPage("city", CommonVariables.CITY);
			enterTextOfWEPCreateCompanyPage("region", CommonVariables.STATE);
			enterTextOfWEPCreateCompanyPage("zipcode", CommonVariables.ZIP_CODE);
			waitForElementsOfWEPCreateCompanyPage("country");
			actionClickOnWEPCreateCompanyPage("country");
			selectTextValueFromDropdownOfWEPCreateCompanyPage("countrylt", country, "country");
			clickOnElementsOfWEPCreateCompanyPage("addNxtBtn");
			LOGGER.info("Filling Customer Info completed");

			LOGGER.info("Filling IT Admin Info");
			enterTextOfWEPCreateCompanyPage("firstItadmn", firstName);
			enterTextOfWEPCreateCompanyPage("lastItadmn", lastName);
			enterTextOfWEPCreateCompanyPage("emailItadmin", compEmail);
			enterTextOfWEPCreateCompanyPage("phoneNumber", CommonVariables.PHONE_NUMBER);
			actionClickOnWEPCreateCompanyPage("IdpTypedrop");
			selectFirstValueFromDropdownOnWEPCreateCompanyPage("IDPlist");
			waitForElementsOfWEPCreateCompanyPage("SelectLanguage");
			actionClickOnWEPCreateCompanyPage("SelectLanguage");
			selectTextValueFromDropdownOfWEPCreateCompanyPage("selectEngLanguage", "English (United States)", "SelectLanguage");

// Plan Selection

			scrollOnWEPCreateCompanyPage("selectLanguageText");
			waitForElementsOfWEPCreateCompanyPage("SelectPlan");
			actionClickOnWEPCreateCompanyPage("SelectPlan");
			selectTextValueFromDropdownOfWEPCreateCompanyPage("premiumPlan", planName, "SelectPlan");
			clickOnElementsOfWEPCreateCompanyPage("addNxtBtn");
			LOGGER.info("Filling IT Admin Info completed");

			LOGGER.info("MSP Selection Page");
			if (!loginFlag) {
				waitForElementsOfWEPCreateCompanyPage("mspInfo");
				if (MSPFlag) {
					LOGGER.info("Selecting MSP from list");
					verifyElementsOfWEPCreateCompanyPage("mspDropDownArrow");
					waitForElementsOfWEPCreateCompanyPage("mspDropDownArrow");
					sleeper(3000);
					actionClickOnWEPCreateCompanyPage("mspDropDownArrow");
					// enterTextOfWEPCreateCompanyPage("search", MSPName);
					selectTextValueFromDropdownOfWEPCreateCompanyPage("msplist", MSPName, "mspDropDownArrow");
					// actionClickOnWEPCreateCompanyPage("mspSelect");
					clickOnElementsOfWEPCreateCompanyPage("addNxtBtn");
					flag = true;
				} else {
					if (getTextOfWEPCreateCompanyPage("selectedMSP").equals(MSPName)) {
						if (getTextOfWEPCreateCompanyPage("infomationOfMSP")
								.equals(getTextLanguage(languageCode, "lhserver",
										"root_admin.companies.headings.company_information"))
								&& getTextOfWEPCreateCompanyPage("infoLabelsMSP").equals(infoLabels)
								&& getTextOfWEPCreateCompanyPage("infoValuesMSP").equals(MSPDetails)) {
							scrollOnWEPCreateCompanyPage("addNxtBtn");
							clickOnElementsOfWEPCreateCompanyPage("addNxtBtn");
							flag = true;
						} else {
							LOGGER.error("MSP info validation failed.");
							return false;
						}
					}
				}
			}

			LOGGER.info("Partner Selection Page");
			if (partnerFlag) {
				LOGGER.info("Selecting Partner");
				waitForElementsOfWEPCreateCompanyPage("partnerAssigned");
				sleeper(1000);
				actionClickOnWEPCreateCompanyPage("partnerArrowOnPopup");
				enterTextOfWEPCreateCompanyPage("search", partnerName);
				actionClickOnWEPCreateCompanyPage("partnerSelectOnPopup");
			}

			LOGGER.info("Creating Company");
			actionClickOnWEPCreateCompanyPage("createBtn");

			if (!retryWaitUntilInVisible(WEPCreateCompanyPageProperties.getProperty("createBtn"), 5000)) {
				LOGGER.warn("Company creation took too long.");
				return false;
			}

			LOGGER.info("Company created successfully.");
		} catch (Exception e) {
			LOGGER.error("Exception occurred in addCompanies: " + e.getMessage(), e);
			return false;
		}

		return flag;
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
                    System.out.println("Unknown Environment: " + environment);
            }

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
			LOGGER.info("actualMailContent : {}", actualMailContent);
			int count = 0;
			while (count < 5 && actualMailContent == "") {
				sleeper(1000);
				count++;
				LOGGER.info("Email not received : {}", count);
				actualMailContent = invitationMail(Subject, environment, UserEmail, true);
			}
			return actualMailContent;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getActualMailContent " + e.getMessage()));
			return null;
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
			System.out.println("verification code is: " + matcher.group());
			return matcher.group();
		} else {
			return "Verification code not found.";
		}
	}
	


	// In `src/main/java/com/daasui/pages/WEPCreateCompanyPage.java`
	/**
	 * Verifies the email for the given company and completes the onboarding flow.
	 * Returns true if all steps succeed; false otherwise. All failures are logged.
	 * @throws Exception 
	 */
	public final boolean verifyEmailAndCompleteOnboarding(String companyEmail) throws Exception {
	    
		String subjectVerificationCode = "Verify your email address";
		//Validating customer admin email
		String subjectInvitation = "You have been invited to the HP Workforce Experience Platform (WXP)!";
	
		String invitationEmailContent = getActualMailContent(companyEmail, subjectInvitation);
		String loginLink = extractLink(invitationEmailContent);
		getDriver().navigate().to(loginLink);
		waitForPageLoaded();
		clickByJavaScriptOnWEPCreateCompanyPage("usePasswordButton");
		sleeper(500);
		clickByJavaScriptOnWEPCreateCompanyPage("passowrdTextBox");
		enterTextOfWEPCreateCompanyPage("passowrdTextBox", WEPCustomerVariables.CUSTOMER_PWD);
		sleeper(2000);
		actionClickOnWEPCreateCompanyPage("createButton");
		LOGGER.info("partner login password created!");
		sleeper(2000);
		LOGGER.info("Invitation email link is: {}", loginLink);
	    try {
	        if (companyEmail == null || companyEmail.isBlank()) {
	            LOGGER.error("Company email is blank");
	            return false;
	        }

	        String emailContent = getVerificationEmail(companyEmail, subjectVerificationCode);
	        if (emailContent == null || emailContent.isBlank()) {
	            LOGGER.error("Verification email not received");
	            return false;
	        }

	        String code = extractVerificationCode(emailContent);
	        if (code == null || code.isBlank() || code.contains("not found")) {
	            LOGGER.error("Verification code extraction failed");
	            return false;
	        }

	        waitForPageLoaded();
	        clickByJavaScriptOnWEPCreateCompanyPage("codeTextBox");
	        enterTextOfWEPCreateCompanyPage("codeTextBox", code);
	        clickByJavaScriptOnWEPCreateCompanyPage("verifyButton");
	        sleeper(7000);
	        waitForPageLoaded();

	        // Onboarding sequence
	        sleeper(7000);
	        if (!waitForElementsOfWEPCreateCompanyPage("verifyOnboardingLogo")) {
	            LOGGER.error("Onboarding logo missing");
	            return false;
	        }
	        
	        if (!verifyElementsOfWEPCreateCompanyPage("clickOnCompanySizeButton")) {
	            LOGGER.error("Company size button missing");
	            return false;
	        }
	        actionClickOnWEPCreateCompanyPage("clickOnCompanySizeButton");
	        clickByJavaScriptOnWEPCreateCompanyPage("clickOnCompanyActualSize");

	        if (!verifyElementsOfWEPCreateCompanyPage("termAndConditionCheckbox")) {
	            LOGGER.error("Terms checkbox missing");
	            return false;
	        }
	        clickByJavaScriptOnWEPCreateCompanyPage("termsCheckbox");
	        waitForPageLoaded();
	        clickByJavaScriptOnWEPCreateCompanyPage("verifyOnboardingNextButton");
			sleeper(7000);
	        waitForPageLoaded();

	        if (!verifyElementsOfWEPCreateCompanyPage("welcomePopupSkipButton")) {
	            LOGGER.error("Welcome popup skip button missing");
	            return false;
	        }
	        clickByJavaScriptOnWEPCreateCompanyPage("welcomePopupSkipButton");

	        LOGGER.info("Email verified and onboarding completed");
			if (!verifyElementsOfWEPCreateCompanyPage("tourOnboardingCoach")) {
	            LOGGER.error("Onboarding coach mark close button missing");
	            return false;
	        }
	        clickByJavaScriptOnWEPCreateCompanyPage("closeOnboardingCoachMarkButton");
			
	        return true;
	    } catch (Exception e) {
	        LOGGER.error("Exception in verifyEmailAndCompleteOnboarding: {}", e.getMessage(), e);
	        return false;
	    }
	}


	 
}


	
