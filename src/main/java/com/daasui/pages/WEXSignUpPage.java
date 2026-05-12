package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;

public class WEXSignUpPage extends CommonMethod {
	
	private WEXSignUpPage instance;
	private ObjectReader signUpPagePropertiesReader = new ObjectReader();
	private Properties signUpPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	public static String envUrl = getEnvironmentSpecificData(System.getProperty("environment"), "ENVIORMENT_URL");
	LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
	
	public WEXSignUpPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXSignUpPage.class) {
				if (instance == null) {
					instance = new WEXSignUpPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEXSignUpPage(WebDriver driver) throws IOException {
		signUpPageProperties = signUpPagePropertiesReader.getObjectRepository("WEXSignUpPage");
	}
	public final boolean verifyElementsOfSignUpPage(String key) throws Exception {
		return verifyElementIsPresent(signUpPageProperties.getProperty(key));
	}
	public final void clickOnElementsOfSignUpPage(String key) throws Exception {
		click(signUpPageProperties.getProperty(key));
	}
	public final void mouseHoverOfSignUpPage(String key) throws Exception {
		mouseHover(signUpPageProperties.getProperty(key));
	}
	public final void actionClickOfSignUpPage(String key) throws Exception {
		actionClick(signUpPageProperties.getProperty(key));
	}
	public final List<WebElement> getElementsOfSignUpPage(String key) throws Exception {
		return getAllElements(signUpPageProperties.getProperty(key));
	}
	public final void clickByJavaScriptOnSignUpPage(String key) throws Exception {
		clickByJavaScript(signUpPageProperties.getProperty(key));
	}
	public final void listMouseHoverOfSignUpPage(String key) throws Exception {
		listMouseHover(signUpPageProperties.getProperty(key));
	}
	public final WebElement getElementOfSignUpPage(String key) throws Exception {
		return getElement(signUpPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofSignUpPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(signUpPageProperties.getProperty(key));
	}

	public final void switchToDifferentTabOfSignUpPage() {
		switchToDifferentTab();
	}

	public final void switchToPreviousTabOfSignUpPage() {
		switchBackToPreviousTab();
	}

	public final ArrayList<String> getChartLabelsSignUpPage(String key) throws Exception {
		return getLabelsOfChart(signUpPageProperties.getProperty(key));
	}

	public void scrollToSignUpPage(String key) throws Exception {
		scrollTillView(signUpPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOnSignUpPage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(signUpPageProperties.getProperty(key), textToMatch);
	}
	
	public final void enterTextForSignUpPage(String key, String Text) throws Exception {
		enterText(signUpPageProperties.getProperty(key), Text);
	}
	
	public final boolean waitForElementsOfSignUpPage(String key) throws Exception {
		return verifyElementIsVisible(signUpPageProperties.getProperty(key));
	}
	
	public final void waitUntilElementIsInvisibleOfOfSignUpPage(String key) {
		try {
			verifyElementIsinvisibile(signUpPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfUserDetailsPage " + e.getMessage()));
		}
	}
	
	public final String getAttributesOfSignUpPage(String key, String value) throws Exception {
		return getAttribute(signUpPageProperties.getProperty(key), value);
	}
	
	public final void clearTextOnSignUpPage(String key) throws Exception {
		clearText(signUpPageProperties.getProperty(key));
	}
	
	public final String selectFirstValueFromDropdownOnSignUpPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleofSignUpPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}
	
	public final void selectValueFromDropdownOnSignUpPage(String dropDownInput, String inputField, String text, String value) throws Exception {
		actionClickOfSignUpPage(dropDownInput);
		enterTextForSignUpPage(inputField,text);
		List<WebElement> countryList = getElementsTillAllElementsVisibleofSignUpPage(value);
		for (WebElement country : countryList) {
			if (country.getText().equalsIgnoreCase(text)) {
				country.click();
				break;
			}
		}
	}
	
	public final String getTextOnSignUpPage(String key) throws Exception {
		return getTextBy(signUpPageProperties.getProperty(key));
	}
	
	/**
	 * Verify WEX SignUp Screen
	 * @param LanguageCode
	 * @return
	 */
	public final boolean verifySignUpScreen(String LanguageCode) {
		boolean flag = false;
		try {
			Assert.assertTrue(matchTextOnSignUpPage("getStartedHeading", getTextLanguage(LanguageCode, "daas_ui", "adminx.onboarding.onecloud.getStarted.header.text1").replace("{appName}",CommonVariables.APP_NAME)),"Get started with the Workforce Experience Platform heading is not displayed");
			Assert.assertTrue(matchTextOnSignUpPage("signUpSubHeading", getTextLanguage(LanguageCode, "daas_ui", "adminx.onboarding.onecloud.getStarted.header.text2")),"Sign Up is free, and takes less than 5 minutes. sub heading is not displayed");
			Assert.assertTrue(matchTextOnSignUpPage("companyFullNameLabel", getTextLanguage(LanguageCode, "daas_ui", "adminx.onboarding.onecloud.getStarted.form.company_label")),"Sign Up is free, and takes less than 5 minutes. sub heading is not displayed");
			Assert.assertTrue(matchTextOnSignUpPage("countryLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.list.country")),"Country label is not displayed");
			Assert.assertTrue(matchTextOnSignUpPage("emailLabel", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.email")),"Email label is not displayed");
			Assert.assertTrue(matchTextOnSignUpPage("microsoftRadioButtonText", CommonVariables.MICROSOFT_AZURE_AD),"Microsoft Azure AD radio button is not displayed");
			Assert.assertTrue(matchTextOnSignUpPage("hpidRadioButtonText", CommonVariables.HPID),"HPID radio button is not displayed");
			Assert.assertTrue(matchTextOnSignUpPage("termsConditionText", getTextLanguage(LanguageCode, "daas_ui", "adminx.onboarding.onecloud.getStarted.termsAndCondition").replace("<a target='_blank' href={termsLink}>","").replace("</a>", "").replace("<a target='_blank' href={privacyLink}>","").replace("</a>","").replace("{appName}",CommonVariables.APP_NAME)),"Email label is not displayed");
			Assert.assertTrue(verifyElementsOfSignUpPage("cancelButton"), "Cancel button is not displayed");
			Assert.assertTrue(verifyElementsOfSignUpPage("nextButton"), "Next button is not displayed");
			flag = true;	
			LOGGER.info("WEX SignUp Screen verfied Successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in method verifySignUpScreen " + e.getMessage());
		}return flag;
	}
	
	/**
	 * Validate WEX SignUp Screen
	 * @param companyfullName
	 * @param companyEmail
	 * @param country
	 * @return
	 * @throws Exception
	 */
	public final boolean validateGetStartedScreen(String companyfullName, String companyEmail, String country) throws Exception {
		boolean flag = false;
	try {
		enterTextForSignUpPage("companyFullNameInput", companyfullName);
		selectValueFromDropdownOnSignUpPage("countryDropDown","countryInput",country,"countryValues");
		enterTextForSignUpPage("emailInput", companyEmail);
		actionClickOfSignUpPage("hpidRadioButton");
		actionClickOfSignUpPage("termsConditionCheckBox");
		clickOnElementsOfSignUpPage("nextButton");
		flag = true;
		LOGGER.error("WEX SignUp Screen validated Successfully");
	} catch (Exception e) {
		LOGGER.error("Exception occured in method validateGetStartedScreen " + e.getMessage());
	}
		return flag;
	}
	
	/**
	 * Enter WEX Password
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public final boolean enterPasswordToSignUp(String password) throws Exception {
        boolean flag = false;
        try {
            waitForPageLoaded();
            Thread.sleep(5000);
            if (loginPage.verifyElementsOfLoginPage("nextbuttonNewUI")) {
        		loginPage.clickOnElementsOfLoginPage("nextbuttonNewUI");
        	}
        	loginPage.waitForElementsOfLoginPage("passwordInputField");
        	loginPage.enterTextForLoginPage("passwordInputField", password);
        	loginPage.clickOnElementsOfLoginPage("submitButtonNewUI");
            flag = true;
            LOGGER.info("WEX Password Entered Successfully");
        } catch (Exception e) {
            LOGGER.error("Exception occured in method enterPasswordToSignUp " + e.getMessage());
        }
        return flag;
    }
	
	/**
	 * Validate WEX Account Linked Screen
	 * @param LanguageCode
	 * @return
	 * @throws Exception 
	 */
	public final boolean validateAccountLinkedScreen(String LanguageCode) throws Exception {
		boolean flag = false;
		if(getTextOnSignUpPage("accountLinkedHeading").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "adminx.Onboard.accountLinked.create_account.text1"))){
		try {
			Assert.assertTrue(matchTextOnSignUpPage("accountLinkedHeading", getTextLanguage(LanguageCode, "daas_ui", "adminx.Onboard.accountLinked.create_account.text1")),"Account Linked heading is not displayed");
			Assert.assertTrue(matchTextOnSignUpPage("accountLinkedSubHeading", getTextLanguage(LanguageCode, "daas_ui", "adminx.Onboard.accountLinked.create_account.text2")),"Account Linked Sub heading is not displayed");
			Assert.assertTrue(verifyElementsOfSignUpPage("signOutButton"), "Sign Out button is not displayed");
			Assert.assertTrue(verifyElementsOfSignUpPage("createButton"), "Create button is not displayed");
			clickOnElementsOfSignUpPage("createButton");
			flag = true;	
			LOGGER.info("WEX Account Linked Screen validated Successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in method validateAccountLinkedScreen " + e.getMessage());
		}
		} else {
            LOGGER.error("WEX Account Linked Screen is not displayed");
        }
		return flag;
	}
	
	/**
	 * Validate WEX Company Create Screen
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param timezone
	 * @param companyname
	 * @param country
	 * @param address
	 * @param city
	 * @param zipcode
	 * @param state
	 * @param tncLink
	 * @throws Exception
	 */
	public final boolean companyCreate(String firstname, String lastname,String email, String timezone, String companyname, String country, String address, String city, String zipcode, String state, String tncLink) throws Exception {
	 try {
		enterTextForSignUpPage("firstName", firstname);
		enterTextForSignUpPage("lastName", lastname);
		String emailID = getAttributesOfSignUpPage("emailAddress", "value");
		Assert.assertTrue(emailID.equalsIgnoreCase(email), "Email not matching");
		selectValueFromDropdownOnSignUpPage("timeZone","countryInput",timezone,"companySizelistvalues");
		String companyNameText = getAttributesOfSignUpPage("companyName", "value");
		Assert.assertTrue(companyNameText.equalsIgnoreCase(companyname), "Company Name is not matching");
		actionClickOfSignUpPage("companySize");
		selectFirstValueFromDropdownOnSignUpPage("companySizelistvalues");
		Assert.assertTrue(matchTextOnSignUpPage("countryValueText", country), "Country is not matching");
		enterTextForSignUpPage("address1",address);
		enterTextForSignUpPage("city",city);
		enterTextForSignUpPage("zipcode",zipcode);
		enterTextForSignUpPage("state",state);
		clickByJavaScriptOnSignUpPage("tncCheckBox");
		Assert.assertTrue(matchTextOnSignUpPage("termsConditionText", tncLink), "Terms and Condition text is incorrect");
		Assert.assertTrue(verifyElementsOfSignUpPage("signoutBtn_accountLink"),"Signout button is not displayed");
		clickByJavaScriptOnSignUpPage("nextBtn_accountLink");
		matchTextOnSignUpPage("companyLoadingMsgHeading",CommonVariables.ONBOARDING_COMPANY_LOADING_MSG);
		matchTextOnSignUpPage("companyLoadingMsgText",CommonVariables.ONBOARDING_COMPANY_LOADING_TEXT);
		waitUntilElementIsInvisibleOfOfSignUpPage("companyLoadingMsgHeading");
		sleeper(10000);
		waitForPageLoaded();
		sleeper(1000);
		waitForElementsOfSignUpPage("companyCreateSucc");
		LOGGER.info("WEX Company created successfully");
		return true;
	  }catch(Exception e) {
		  LOGGER.error("Exception occured in method companyCreate " + e.getMessage());
		  return false;
	  }
	}
	
	/**
	 * Validate WEX Company Create Success
	 * @param LanguageCode
	 * @param companyName
	 * @return
	 */
	public final boolean validateCompanyCreateSuccessScreen(String LanguageCode, String companyName) {
		boolean flag = false;
		try {
			Assert.assertTrue(matchTextOnSignUpPage("companyCreateSucc", getTextLanguage(LanguageCode, "daas_ui", "company.created.success.text1").replace("{companyName}",companyName)),"Company Name is incorrect");
			Assert.assertTrue(matchTextOnSignUpPage("companyCreateSuccText", getTextLanguage(LanguageCode, "daas_ui", "company.created.success.text2")),"Company Name is incorrect");
			clickByJavaScriptOnSignUpPage("companyCreateSuccNextBtn");
			waitForPageLoaded();
			sleeper(1000);
			waitForElementsOfSignUpPage("inviteUsersHeading");
			flag = true;	
			LOGGER.info("WEX Company Create Success Screen validated Successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in method validateCompanyCreateSuccessScreen " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Validate WEX Add Users Screen
	 * @param LanguageCode
	 * @return
	 */
	public final boolean validateAddUsersScreen(String LanguageCode) {
		boolean flag = false;
		try {
		Assert.assertTrue(matchTextOnSignUpPage("inviteUsersHeading", getTextLanguage(LanguageCode, "daas_ui", "users.import.new.title")), "Invite user heading is incorrect");
		Assert.assertTrue(matchTextOnSignUpPage("inviteUsersText", getTextLanguage(LanguageCode, "daas_ui", "users.add.manually.heading")), "Invite user heading text is incorrect");
		Assert.assertTrue(matchTextOnSignUpPage("firstNameText", getTextLanguage(LanguageCode, "daas_ui", "users.details.first_name")), "First Name label is incorrect");
		Assert.assertTrue(matchTextOnSignUpPage("lastNameText", getTextLanguage(LanguageCode, "daas_ui", "users.details.last_name")), "Last Name label is incorrect");
		Assert.assertTrue(matchTextOnSignUpPage("emailText", getTextLanguage(LanguageCode, "daas_ui", "users.list.label.email")), "Email label is incorrect");
		Assert.assertTrue(matchTextOnSignUpPage("rolesText", getTextLanguage(LanguageCode, "daas_ui", "users.list.label.roles")), "Roles label is incorrect");
		Assert.assertTrue(matchTextOnSignUpPage("addAnotherUser", getTextLanguage(LanguageCode, "daas_ui", "users.add.add_another")), "Add another user button is incorrect");
		Assert.assertTrue(matchTextOnSignUpPage("editRoleIcon", getTextLanguage(LanguageCode, "daas_ui", "users.details.roles.modal.title")), "Edit Role button is incorrect");
		verifyElementsOfSignUpPage("skipButton");
		verifyElementsOfSignUpPage("nextBtn");
		flag = true;	
		LOGGER.info("WEX Add Users Screen validated Successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in method validateAddUsersScreen " + e.getMessage());
		}	
		return flag;
	}
	
	/**
	 * Add Users with data
	 * @throws Exception 
	 */
	public final boolean addUserFunctionality(String name, String lastname, String inviteUser_email, String role,String LanguageCode) throws Exception {
		boolean flag = false;
		try {
			enterTextForSignUpPage("firstNameOnUsersPage", name);
			enterTextForSignUpPage("lastNameOnUsersPage", lastname);
			enterTextForSignUpPage("emailOnUsersPage", inviteUser_email);
			actionClickOfSignUpPage("selectidpType");
			selectFirstValueFromDropdownOnSignUpPage("idpTypeValues");
			matchTextOnSignUpPage("noneAssignedText","None assigned");
			clickOnElementsOfSignUpPage("editRoleIcon");
			sleeper(1000);
			Assert.assertTrue(matchTextOnSignUpPage("assignRoles", getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.assign_roles")), "Assign Roles Test is not displayed");
			verifyElementsOfSignUpPage("cancelBtn");
			actionClickOfSignUpPage("selectRoleDropDown");  
			selectValueFromDropdownOnSignUpPage("roledropdownoption","roledropdownoption", role, "roleDropDownList");
			pressKey(Keys.TAB);
			pressKey(Keys.TAB);
			clickOnElementsOfSignUpPage("doneButton");
			clickOnElementsOfSignUpPage("nextBtn");
			flag = true;	
			LOGGER.info("WEX Add Users Screen verification is Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in method addUserFunctionality " + e.getMessage());
		}	
		return flag;
	}
	
	/**
	 * Invite user with email preview
	 * @param body
	 * @throws Exception
	 */
	public final boolean invitationEmailScreen(String body,String companyName, String languageCode, String appName) throws Exception {
	 try {	
		waitForElementsOfSignUpPage("CustomizedHeading");
		Assert.assertTrue(matchTextOnSignUpPage("sampleText", getTextLanguage(languageCode, "daas_ui", "adminx.create_invite.default_msg").replace("{appName}", appName)), "Sample Text is incorrect.");
		clickOnElementsOfSignUpPage("sampleText");
		clearTextOnSignUpPage("sampleText");
		enterTextForSignUpPage("sampleText",body);
		LOGGER.info("Customixed text entered successfully");
		Assert.assertTrue(matchTextOnSignUpPage("CustomizedHeading", getTextLanguage(languageCode, "daas_ui", "adminx.create_invite.header.text1")), "Create Invite header : Customize Invitation Message text is incorrect");
	    Assert.assertTrue(matchTextOnSignUpPage("optionalMessageHeading", getTextLanguage(languageCode, "daas_ui", "adminx.create_invite.header.text2")), "Create Invite header : Optional Personal Message text is incorrect");
	    Assert.assertTrue(getTextOnSignUpPage("subject").contains(getTextLanguage(languageCode, "daas_ui", "adminx.Onboard.Welcome").replace("{appName}", appName )), "Subject is Incorrect");
	    Assert.assertTrue(getTextOnSignUpPage("toEmail").contains(getTextLanguage(languageCode, "daas_ui", "adminx.create_invite.mail.to")), "To Email is Incorrect");
	    Assert.assertTrue(matchTextOnSignUpPage("inviteText", getTextLanguage(languageCode, "daas_ui", "adminx.create_invite.desc").replace("{userName}", companyName).replace("{appName}", appName)), "Invite heading text is incorrect");
	    Assert.assertTrue(matchTextOnSignUpPage("helloText", getTextLanguage(languageCode, "daas_ui", "MEMOnboard.welcomeMail.GreetingText").replace("{0}", "<Customer First Name>")), "Hello heading text is incorrect");
	    Assert.assertTrue(matchTextOnSignUpPage("characterLimit", getTextLanguage(languageCode, "daas_ui", "adminx.create_invite.msg.helper_text").replace("{num}", "400")), "Max Character Count is incorrect");
		Assert.assertTrue(matchTextOnSignUpPage("helperText", getTextLanguage(languageCode, "daas_ui", "adminx.create_invite.accept_link_text")), "Invitation using the below link: text is incorrect");
        verifyElementsOfSignUpPage("signInButton");
        Assert.assertTrue(matchTextOnSignUpPage("signInLink", getTextLanguage(languageCode, "daas_ui", "adminx.create_invite.links.helper_text")), "Invitation link Text is incorrect");
        Assert.assertTrue(matchTextOnSignUpPage("thankYou",getTextLanguage(languageCode, "daas_ui", "adminx.signature_thanks")), "Thank you signature is incorrect");
        Assert.assertTrue(matchTextOnSignUpPage("wexTeam",getTextLanguage(languageCode, "daas_ui", "global.app_team").replace("{app}", appName)), "Thank you signature is incorrect");		
        verifyElementsOfSignUpPage("backBtnEmail");
        verifyElementsOfSignUpPage("skipBtnEmail");
        clickOnElementsOfSignUpPage("sendBtnEmail");
		sleeper(2000);
		waitForElementsOfSignUpPage("emailSentText");
		LOGGER.info("Preivew text matched");
		return true;
	 }catch(Exception e) {
		LOGGER.error("Exception occured in method invitationEmailScreen " + e.getMessage());
		return false;
	 }
	}
	
	/**
	 * Invitation sent success screen
	 * @return
	 */
	public final boolean verifyInviteSentSuccessScreen(String languageCode) {
		try {	
			Assert.assertTrue(matchTextOnSignUpPage("emailSentMessage", getTextLanguage(languageCode, "daas_ui", "adminx.invitationSent.header1")), "Invitation sent heading is incorrect");
			Assert.assertTrue(matchTextOnSignUpPage("emailSentText", getTextLanguage(languageCode, "daas_ui", "adminx.invitationSent.header2")), "Invitation sent text is incorrect");
			verifyElementsOfSignUpPage("logoutBtn_emailsend");
			clickOnElementsOfSignUpPage("nextBtn_emailsend");
			LOGGER.info("Email sent successfully");
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyInviteSentSuccessScreen " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * Welcome Wizard screen validation
	 * @return
	 */
	public final boolean verifyWelcomeWizard(String languageCode, String appName) {
		try {
			Assert.assertTrue(matchTextOnSignUpPage("welcomeToWFheading", getTextLanguage(languageCode, "daas_ui", "adminx.Onboard.Welcome").replace("{appName}", "the "+appName+".")), "Welcome Wizard heading is incorrect");
			verifyElementsOfSignUpPage("closeBtn");
			Assert.assertTrue(matchTextOnSignUpPage("welcomeToWFtext", getTextLanguage(languageCode, "daas_ui", "adminx.welcome_popup.content").replace("{appName}", appName)), "Welcome Wizard text is incorrect");
			clickByJavaScriptOnSignUpPage("getStartedBtn");
			sleeper(500);
			LOGGER.info("Welcome wizard verified");
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyWelcomeWizard " + e.getMessage()));
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
	
}