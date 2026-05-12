package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.ErrorPage;
import com.daasui.pages.HpIdProfilePage;
import com.daasui.pages.LoginPage;
import com.daasui.pages.UserPage;

public class HpIdTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(DashboardTest.class);

	/**
	 * This test is verifying when language is changed in user profile page then properly tooltip is coming or not
	 */

	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyToolTipInUserProfile() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(hpIdProfilePage.checkToolTip("en"), "Tool tip is not displayed successfully");
			LOGGER.info("Tool tip is displayed successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyToolTipInUserProfile(): " + e.getMessage());
		}
	}

	/**
	 * This test is verifying login with Gmail to DaaS.
	 * 
	 */

	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkGmailLogin() {
		try {
			Assert.assertTrue(gmailLogin("GMAIL_USER_EMAIL", "GMAIL_USER_PASSWORD"), "Gmail login Failed");
			LOGGER.info("Gmail login  is Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkGmailLogin(): " + e.getMessage());
		}
	}

	/**
	 * This test is verifying login with Facebook to DaaS
	 */

	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkFacebookLogin() {
		try {
			Assert.assertTrue(facebookLogin("FACEBOOK_USER_EMAIL", "FACEBOOK_USER_PASSWORD"), "Facebook login Failed");
			LOGGER.info("Facebook login  is Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkFacebookLogin(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating Edit profile link is not present on user profile page in DaaS.
	 */

	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyEditProfileLink() {
		try {
			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			Assert.assertFalse(userPage.verifyElementsOfUserPage("editProfileLink"), "Edit Profile link is present test is Failed.");
			LOGGER.info("Edit profile link is present.");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyEditProfileLink(): " + e.getMessage());

		}
	}

	/**
	 * This test is validating when user is logged in DaaS profile and change profile picture by uploading a new picture the new picture gets successfully updated.
	 */

	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkImageUpload() {
		try {
			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();
			Assert.assertTrue(hpIdProfilePage.validateHpIdProfilePageUploadImage());
			LOGGER.info("Image is uploaded successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkImageUpload(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating two Phone number fields accepting proper input.
	 */

	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyPhoneNumber() {
		try {
			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();

			SoftAssert sa = new SoftAssert();
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			userPage.waitForElementsOfUserPageForClick("edit");
			userPage.clickOnElementsOfUserPage("edit");
			String officePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(6);
			userPage.enterTextForUserPage("officePhoneInput", officePhone);
			String mobilePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(6);
			userPage.enterTextForUserPage("mobilePhoneInput", mobilePhone);
			userPage.clickOnElementsOfUserPage("save");
			sa.assertTrue(userPage.verifyElementsOfUserPage("officePhoneError"), "Error message for 6 digit officePhone not found");
			sa.assertTrue(userPage.verifyElementsOfUserPage("mobilePhoneError"), "Error message for 6 digit mobilePhone not found");
			officePhone = hpIdProfilePage.generateRandomAlphanumericStringHPIDPage(8);
			userPage.enterTextForUserPage("officePhoneInput", officePhone);
			mobilePhone = hpIdProfilePage.generateRandomAlphanumericStringHPIDPage(8);
			userPage.enterTextForUserPage("mobilePhoneInput", officePhone);
			userPage.clickOnElementsOfUserPage("save");
			sa.assertTrue(userPage.verifyElementsOfUserPage("officePhoneError"), "Error message for Alphanumeric String digit officePhone not found");
			sa.assertTrue(userPage.verifyElementsOfUserPage("mobilePhoneError"), "Error message for Alphanumeric String digit mobilePhone not found");
			officePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(9);
			userPage.enterTextForUserPage("officePhoneInput", officePhone);
			mobilePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(9);
			userPage.enterTextForUserPage("mobilePhoneInput", officePhone);
			userPage.clickOnElementsOfUserPage("save");
			sa.assertFalse(userPage.verifyElementsOfUserPage("officePhoneError"));
			sa.assertFalse(userPage.verifyElementsOfUserPage("mobilePhoneError"));
			sa.assertAll();
			LOGGER.info("officePhone and mobilePhone fields working fine.");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyPhoneNumber(): " + e.getMessage());
		}
	}

	/**
	 * This test is verifying localization parameter in DaaS and HPID /
	 */
	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyLanguageInDaaS() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();
			SoftAssert sa = new SoftAssert();
			sa.assertTrue(hpIdProfilePage.checkLocal("en"), "Failed to verify en language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("fr"), "Failed to verify fr language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("ja"), "Failed to verify ja language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("de"), "Failed to verify de language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("es"), "Failed to verify es language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("pt_BR"), "Failed to verify pt_BR language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("pt_PT"), "Failed to verify pt_PT language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("zh_CN"), "Failed to verify zh_CN language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("nl"), "Failed to verify nl language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("sv"), "Failed to verify sv language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("it"), "Failed to verify it language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("da"), "Failed to verify da language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("fi"), "Failed to verify fi language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("no"), "Failed to verify no language.");
			sa.assertTrue(hpIdProfilePage.checkLocal("en"), "Failed to verify en language.");
			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			sa.assertAll();
			LOGGER.info("Localisation verification of User profile is successfull");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyLanguageInDaaS(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when user is logged in to DaaS and in the same browser if user opens HPID profile then user gets login automatically.
	 */

	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkSignInOfHpIdProfilePage() {
		try {
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			Assert.assertTrue(hpIdProfilePage.checkHPIDSignIn(), "Sign In to HPID profile page when user is logged in to DaaS is Failed");
			LOGGER.info("Sign In to HPID profile page when user is logged in to DaaS is Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkSignInOfHpIdProfilePage(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when user is logged in to HPID profile and in the same browser if user opens DaaS then user gets login automatically.
	 */

	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkSignInOfDaaSPage() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			Assert.assertTrue(hpIdProfilePage.checkDaaSSignIn(), "SignIn to DaaS when user is logged in to HPID profile is Failed.");
			LOGGER.info("SignIn to DaaS when user is logged in to HPID profile is Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkSignInOfDaaSPage(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating error message when user has HPID and also exist in DaaS but role of user is user so this kind of user are not allowed to access DaaS will get error
	 * message.
	 */

	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkErrorMessageForNormalUser() {
		try {
			environment = System.getProperty("environment");
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			
			ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver());
			errorPage = errorPage.getInstance();	
			LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver());
			loginPage = loginPage.getInstance();
			getUrl(hpIdProfilePage.getDaaSUrl());
			loginPage.waitForElementsOfLoginPage("emailInputField");
			loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, "NORMAL_USER_EMAIL"));
			loginPage.clickOnElementsOfLoginPage("nextbutton");
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitle"), "Password page did not open successfully");
			loginPage.waitForElementsOfLoginPage("passwordInputField");
			loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, "NORMAL_USER_PASSWORD"));
			loginPage.clickOnElementsOfLoginPage("submitButton");
			Assert.assertTrue(errorPage.waitForElementsOfErrorPage("errorMessage"));
			Assert.assertTrue(errorPage.verifyElementsOfErrorPage("errorMessage"));
			errorPage.clickOnElementsOfErrorPage("signIn");
			Assert.assertTrue(loginPage.waitForElementsOfLoginPage("emailInputField"));
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			LOGGER.info("Error message for normal user is displayed Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkErrorMessageForNormalUser(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating error message when user does not exist in DaaS but have HPID.
	 */

	@Test(priority = 11, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkErrorMessageForOnlyHpIdUser() {
		try {
			environment = System.getProperty("environment");
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			
			ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver());
			errorPage = errorPage.getInstance();	
			LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver());
			loginPage = loginPage.getInstance();
			getUrl(hpIdProfilePage.getDaaSUrl());
			loginPage.waitForElementsOfLoginPage("emailInputField");
			loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, "ONLYHPID_USER_EMAIL"));
			loginPage.clickOnElementsOfLoginPage("nextbutton");
			Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitle"), "Password page did not open successfully");
			loginPage.waitForElementsOfLoginPage("passwordInputField");
			loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, "ONLYHPID_USER_PASSWORD"));
			loginPage.clickOnElementsOfLoginPage("submitButton");
			Assert.assertTrue(errorPage.waitForElementsOfErrorPage("noPermissionTitle"));
			Assert.assertTrue(errorPage.verifyElementsOfErrorPage("noPermissionTitle2"));
			Assert.assertTrue(errorPage.verifyElementsOfErrorPage("goBackSignIn"));
			Assert.assertTrue(errorPage.verifyElementsOfErrorPage("contactSupport"));
			errorPage.clickOnElementsOfErrorPage("goBackSignIn");
			Assert.assertTrue(loginPage.waitForElementsOfLoginPage("emailInputField"));
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			LOGGER.info("Error message for only HPID user is displayed Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkErrorMessageForOnlyHpIdUser(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when user is logged in in DaaS and HPID profile in same browser in two different tabs then if user sign out from HPID profile page user will not get
	 * signed out from DaaS.
	 */

	@Test(priority = 12, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkSignOutFromHpIdProfilePage() {
		try {
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			Assert.assertTrue(hpIdProfilePage.checkHPIDSignOut(), "Sign out from HPID profile page when user is logged in to DaaS is Successful Failed.");
			LOGGER.info("Sign out from HPID profile page when user is logged in to DaaS is Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkSignOutFromHpIdProfilePage(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when user is logged in in DaaS and HPID profile in same browser in two different tabs then if user sign out from DaaS user will get sign out from HPID
	 * profile also.
	 */

	@Test(priority = 13, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkSignOutFromDaaS() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			Assert.assertTrue(hpIdProfilePage.checkDaaSSignOut(), "Sign Out from HPID profile page when user is logged in to DaaS is Successful Failed.");
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			LOGGER.info("Sign Out from HPID profile page when user is logged in to DaaS is Successful");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test checkSignOutFromDaaS(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating user data on DaaS profile page and HPID profile page
	 */

	@Test(priority = 14, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyProfileInformation() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();
			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			Assert.assertTrue(hpIdProfilePage.validateHpIdProfileData());
			LOGGER.info("Profile data on HPID page and DaaD is matched successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyProfileInformation(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when user profile data is updated in DaaS then same data is updated in HPID profile page
	 */

	@Test(priority = 15, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyUpdatedDaaSProfileInformation() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();

			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.clickOnElementsOfUserPage("edit");
			userPage.enterTextForUserPage("firstNameInput", hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8));
			userPage.enterTextForUserPage("lastNameInput", hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8));
			userPage.enterTextForUserPage("addLine1Input", hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8));
			userPage.enterTextForUserPage("addLine2Input", hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8));
			userPage.enterTextForUserPage("stateInput", hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8));
			userPage.enterTextForUserPage("cityInput", hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8));
			userPage.enterTextForUserPage("zipInput", hpIdProfilePage.generateRandomNumericStringHPIDPage(6));
			hpIdProfilePage.selectRandomCountry();
			userPage.clickOnElementsOfUserPage("save");
			userPage.waitForElementsOfUserPage("edit");
			Assert.assertTrue(hpIdProfilePage.validateHpIdProfileData());
			LOGGER.info("Profile data on HPID page and DaaD for test verifyUpdatedDaaSProfileInformation() is matched successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyUpdatedDaaSProfileInformation(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when user profile data is updated in HPID then same data is updated in DaaS profile page
	 */

	@Test(priority = 16, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyUpdatedHPIDProfileInformation() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();
			createAndSwitchToNewTab();
			hpIdProfilePage.loginHpIdProfile("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			Assert.assertTrue(hpIdProfilePage.waitForElementsOfHpIdProfilePage("menu"));
			hpIdProfilePage.setDataOnHPIDPage();
			switchBackToPreviousTab();
			getUrl(hpIdProfilePage.getDaaSUrl());
			dashboardPage.waitForElementsOfDashboardPage("dashboardTitle");
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			sleeper(3000);
			Assert.assertTrue(hpIdProfilePage.validateHpIdProfileData());
			LOGGER.info("Profile data on HPID page and DaaD for test verifyUpdatedDaaSProfileInformation() is matched successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyUpdatedHPIDProfileInformation(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when MSP profile data is updated in DaaS whether it is getting properly updated or not.
	 */

	@Test(priority = 17, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyUpdatedDaaSProfileInformationForMSP() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			
			login("MSP_USER_PROFILE_EMAIL", "MSP_USER_PROFILE_PASSWORD");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();
			SoftAssert sa = new SoftAssert();
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.clickOnElementsOfUserPage("edit");
			String firstName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8);
			String lastName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8);
			String addLine1 = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8);
			String addLine2 = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8);
			String state = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8);
			String city = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8);
			String zip = hpIdProfilePage.generateRandomNumericStringHPIDPage(6);
			String officePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(8);
			String mobilePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(8);
			String title = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 4);
			userPage.enterTextForUserPage("firstNameInput", firstName);
			userPage.enterTextForUserPage("lastNameInput", lastName);
			userPage.enterTextForUserPage("addLine1Input", addLine1);
			userPage.enterTextForUserPage("addLine2Input", addLine2);
			userPage.enterTextForUserPage("stateInput", state);
			userPage.enterTextForUserPage("cityInput", city);
			userPage.enterTextForUserPage("zipInput", zip);
			userPage.enterTextForUserPage("officePhoneInput", officePhone);
			userPage.enterTextForUserPage("mobilePhoneInput", mobilePhone);
			userPage.enterTextForUserPage("titleInput", title);
			String country = hpIdProfilePage.selectRandomCountry();
			userPage.clickOnElementsOfUserPage("save");
			userPage.waitForElementsOfUserPageForClick("edit");
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.waitForElementsOfUserPage("firstName");
			userPage.waitForElementsOfUserPage("addLine1");
			userPage.waitForElementsOfUserPage("state");
			sa.assertTrue(firstName.equals(userPage.getTextOfUserPage("firstName")), "firstName is not matched.");
			sa.assertTrue(lastName.equals(userPage.getTextOfUserPage("lastName")), "lastName is not matched.");
			sa.assertTrue(addLine1.equals(userPage.getTextOfUserPage("addLine1")), "addLine1 is not matched.");
			sa.assertTrue(addLine2.equals(userPage.getTextOfUserPage("addLine2")), "addLine2 is not matched.");
			sa.assertTrue(state.equals(userPage.getTextOfUserPage("state")), "state is not matched.");
			sa.assertTrue(city.equals(userPage.getTextOfUserPage("city")), "city is not matched.");
			sa.assertTrue(zip.equals(userPage.getTextOfUserPage("zip")), "zip is not matched.");
			sa.assertTrue(officePhone.equals(userPage.getTextOfUserPage("officePhoneStrong")), "officePhone is not matched.");
			sa.assertTrue(mobilePhone.equals(userPage.getTextOfUserPage("mobilePhoneStrong")), "mobilePhone is not matched.");
			sa.assertTrue(country.equals(userPage.getTextOfUserPage("country")), "country is not matched.");
			sa.assertTrue(title.equals(userPage.getTextOfUserPage("title")), "Title is not matched.");
			sa.assertAll();
			LOGGER.info("Profile data for MSP is matched successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyUpdatedDaaSProfileInformationForMSP(): " + e.getMessage());
		}
	}

	/**
	 * This test is verifying Communication preferences
	 */

	@Test(priority = 18, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void checkCommunicationPreferences() {
		try {
			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.verifyElementsOfUserPage("communicationPreferencesEdit");
			userPage.clickOnElementsOfUserPage("communicationPreferencesEdit");
			Assert.assertTrue(userPage.verifyElementIsEnableOfUserPage("communicationPreferencesCheckBox"));
			LOGGER.info("Verification of Communication Preferences Successful");
		}

		catch (Exception e) {
			LOGGER.error("Exception occured in Test checkCommunicationPreferences(): " + e.getMessage());
		}
	}

	/**
	 * This test is verifying localization in DaaS and HPID
	 * 
	 * @throws Exception
	 */

	@Test(priority = 19, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyLanguageInDaaSAndHPID() throws Exception {
		HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
		hpIdProfilePage = hpIdProfilePage.getInstance();
		String newWindow = null;
		try {
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();
			SoftAssert sa = new SoftAssert();
			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			newWindow = createAndSwitchToNewTab();
			getUrl(hpIdProfilePage.getHpIdUrl());
			hpIdProfilePage.waitForElementsOfHpIdProfilePage("menu");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("en"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("fr"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("ja"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("en"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("de"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("es"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("pt_BR"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("pt_PT"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("zh_CN"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("nl"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("sv"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("it"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("da"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("fi"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("no"), "en language is not reflected Successfuly");
			sa.assertTrue(hpIdProfilePage.checkHPIDLanguage("en"), "en language is not reflected Successfuly");
			switchToDifferentTab();
			switchBackToPreviousTab();
			sa.assertAll();
			LOGGER.info("Localisation verification of HPID page is successfull");
		} catch (Exception e) {
			hpIdProfilePage.closeNewWindow(newWindow);
			LOGGER.error("Exception occured in Test verifyLanguageInDaaSAndHPID(): " + e.getMessage());
		}
	}

	/**
	 * This test is verifying updation of inactive user in DaaS
	 */

	@Test(priority = 20, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyAddedInactiveUser() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			SoftAssert sa = new SoftAssert();
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			impersonateCompanyByEmail(CompaniesVariables.COMPANY_EMAIL_REPORTS);
			gotoUserTab();
			userPage.waitForElementsOfUserPage("addUser");
			userPage.clickOnElementsOfUserPage("addUser");
			userPage.waitForElementsOfUserPage("addManually");
			userPage.clickOnElementsOfUserPage("addManually");
			String firstName = CommonVariables.FIRSTNAME;
			firstName = firstName + hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 2);
			String lastName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8);
			String title = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8);
			userPage.enterTextForUserPage("addUserName", firstName);
			userPage.clickOnElementsOfUserPage("addUserCheckBox");
			userPage.clickOnElementsOfUserPage("submit");
			userPage.waitForElementsOfUserPage("downloadPin");
			gotoUserTab();
			userPage.waitForElementsOfUserPageForClick("serchBox");
			userPage.enterTextForUserPage("serchBox", firstName);
			sleeper(3000);
			userPage.waitForElementsOfUserPageForClick("inactiveUser");
			userPage.clickOnElementsOfUserPage("inactiveUser");
			userPage.waitForElementsOfUserPageForClick("edit");
			userPage.clickOnElementsOfUserPage("edit");
			String email = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8) + "@mailinator.com";
			userPage.enterTextForUserPage("firstNameInput", firstName);
			userPage.enterTextForUserPage("lastNameInput", lastName);
			userPage.enterTextForUserPage("userEmail", email);
			userPage.enterTextForUserPage("titleInput", title);
			userPage.clickOnElementsOfUserPage("save");
			userPage.waitForElementsOfUserPageForClick("edit");
			sa.assertTrue(firstName.equals(userPage.getTextOfUserPage("firstName")), "firstName is not matched.");
			sa.assertTrue(lastName.equals(userPage.getTextOfUserPage("lastName")), "lastName is not matched.");
			sa.assertTrue(title.equals(userPage.getTextOfUserPage("title")), "lastName is not matched.");
			sa.assertTrue(email.equals(userPage.getAttributesOfUserPage("emailStrong", "title")), "Email is not matched.");
			userPage.clickOnElementsOfUserPage("removeUser");
			userPage.clickOnElementsOfUserPage("removeUser1");
			userPage.waitForElementsOfUserPage("addUser");
			sa.assertAll();
			LOGGER.info("Inactive User updation is verified Successfuly");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyAddedInactiveUser(): " + e.getMessage());
		}
	}

	/**
	 * This test is verifying updation of Japnese inactive user in DaaS
	 */
	@Test(priority = 21, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyAddedJapaneseInactiveUser() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();			
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			SoftAssert sa = new SoftAssert();
			login("HPID_COMPANY_USER_EMAIL", "HPID_COMPANY_USER_PASSWORD");
			gotoUserTab();
			userPage.waitForElementsOfUserPage("addUser");
			userPage.clickOnElementsOfUserPage("addUser");
			userPage.waitForElementsOfUserPage("addManually");
			userPage.clickOnElementsOfUserPage("addManually");
			String firstName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 6);
			String lastName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 6);
			String name = firstName + " " + lastName;
			String email = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8).concat("@mailinator.com");
			userPage.enterTextForUserPage("addUserName", name);
			userPage.waitForElementsOfUserPage("inactiveEmail");
			userPage.enterTextForUserPage("inactiveEmail", email);
			userPage.clickOnElementsOfUserPage("addUserCheckBox");
			userPage.clickOnElementsOfUserPage("submit");
			userPage.waitForElementsOfUserPage("downloadPin");
			gotoUserTab();
			userPage.enterTextForUserPage("serchBox", email);
			sleeper(2000);
			userPage.clickOnElementsOfUserPage("inactiveUser");
			userPage.waitForElementsOfUserPageForClick("edit");
			userPage.clickOnElementsOfUserPage("edit");
			userPage.clickOnElementsOfUserPage("save");
			userPage.waitForElementsOfUserPageForClick("edit");
			sa.assertTrue(firstName.equals(userPage.getTextOfUserPage("firstName")), "firstName is not matched.");
			sa.assertTrue(lastName.equals(userPage.getTextOfUserPage("lastName")), "lastName is not matched.");
			userPage.clickOnElementsOfUserPage("removeUser");
			userPage.clickOnElementsOfUserPage("removeUser1");
			userPage.waitForElementsOfUserPage("addUser");
			sa.assertAll();
			LOGGER.info("Inactive User updation is verified Successfuly");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyAddedInactiveUser(): " + e.getMessage());
		}
	}

	/**
	 * This test is verifying updation of Chinese inactive user in DaaS
	 */
	@Test(priority = 22, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyAddedChainiesInactiveUser() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			SoftAssert sa = new SoftAssert();
			login("HPID_COMPANY_USER_EMAIL", "HPID_COMPANY_USER_PASSWORD");
			gotoUserTab();
			userPage.waitForElementsOfUserPage("addUser");
			userPage.clickOnElementsOfUserPage("addUser");
			userPage.waitForElementsOfUserPage("addManually");
			userPage.clickOnElementsOfUserPage("addManually");
			String firstName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 6);
			String lastName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 6);
			String name = firstName + " " + lastName;
			String email = hpIdProfilePage.generateRandomCharacterStringHPIDPage("English", 8).concat("@mailinator.com");
			userPage.enterTextForUserPage("addUserName", name);
			userPage.waitForElementsOfUserPage("inactiveEmail");
			userPage.enterTextForUserPage("inactiveEmail", email);
			userPage.clickOnElementsOfUserPage("addUserCheckBox");
			userPage.clickOnElementsOfUserPage("submit");
			userPage.waitForElementsOfUserPage("downloadPin");
			gotoUserTab();
			userPage.enterTextForUserPage("serchBox", email);
			sleeper(2000);
			userPage.clickOnElementsOfUserPage("inactiveUser");
			userPage.waitForElementsOfUserPageForClick("edit");
			userPage.clickOnElementsOfUserPage("edit");
			userPage.clickOnElementsOfUserPage("save");
			userPage.waitForElementsOfUserPageForClick("edit");
			sa.assertTrue(firstName.equals(userPage.getTextOfUserPage("firstName")), "firstName is not matched.");
			sa.assertTrue(lastName.equals(userPage.getTextOfUserPage("lastName")), "lastName is not matched.");
			userPage.clickOnElementsOfUserPage("removeUser");
			userPage.clickOnElementsOfUserPage("removeUser1");
			userPage.waitForElementsOfUserPage("addUser");
			sa.assertAll();
			LOGGER.info("Inactive User updation is verified Successfuly");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyAddedInactiveUser(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when user profile data is updated for Chinese in DaaS whether it is getting properly updated or not.
	 */
	@Test(priority = 23, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyUpdatedDaaSProfileInformationForChinese() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();
			login("HPID_COMPANY_USER_EMAIL1", "HPID_COMPANY_USER_PASSWORD1");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();

			SoftAssert sa = new SoftAssert();
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.clickOnElementsOfUserPage("edit");
			String firstName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 8);
			String lastName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 8);
			String addLine1 = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 8);
			String addLine2 = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 8);
			String state = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 8);
			String city = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 8);
			String zip = hpIdProfilePage.generateRandomNumericStringHPIDPage(6);
			String officePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(8);
			String mobilePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(8);
			String title = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Chinese", 4);
			userPage.enterTextForUserPage("firstNameInput", firstName);
			userPage.enterTextForUserPage("lastNameInput", lastName);
			userPage.enterTextForUserPage("addLine1Input", addLine1);
			userPage.enterTextForUserPage("addLine2Input", addLine2);
			userPage.enterTextForUserPage("stateInput", state);
			userPage.enterTextForUserPage("cityInput", city);
			userPage.enterTextForUserPage("zipInput", zip);
			userPage.enterTextForUserPage("officePhoneInput", officePhone);
			userPage.enterTextForUserPage("mobilePhoneInput", mobilePhone);
			userPage.enterTextForUserPage("titleInput", title);
			String country = hpIdProfilePage.selectRandomCountry();
			userPage.clickOnElementsOfUserPage("save");
			userPage.waitForElementsOfUserPageForClick("edit");
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.waitForElementsOfUserPage("firstName");
			userPage.waitForElementsOfUserPage("addLine1");
			userPage.waitForElementsOfUserPage("state");
			sa.assertTrue(firstName.equals(userPage.getTextOfUserPage("firstName")), "firstName is not matched.");
			sa.assertTrue(lastName.equals(userPage.getTextOfUserPage("lastName")), "lastName is not matched.");
			sa.assertTrue(addLine1.equals(userPage.getTextOfUserPage("addLine1")), "addLine1 is not matched.");
			sa.assertTrue(addLine2.equals(userPage.getTextOfUserPage("addLine2")), "addLine2 is not matched.");
			sa.assertTrue(state.equals(userPage.getTextOfUserPage("state")), "state is not matched.");
			sa.assertTrue(city.equals(userPage.getTextOfUserPage("city")), "city is not matched.");
			sa.assertTrue(zip.equals(userPage.getTextOfUserPage("zip")), "zip is not matched.");
			sa.assertTrue(officePhone.equals(userPage.getTextOfUserPage("officePhoneStrong")), "officePhone is not matched.");
			sa.assertTrue(mobilePhone.equals(userPage.getTextOfUserPage("mobilePhoneStrong")), "mobilePhone is not matched.");
			sa.assertTrue(country.equals(userPage.getTextOfUserPage("country")), "country is not matched.");
			sa.assertTrue(title.equals(userPage.getTextOfUserPage("title")), "Title is not matched.");
			sa.assertAll();
			LOGGER.info("Profile data for MSP is matched successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyUpdatedDaaSProfileInformationForMSP(): " + e.getMessage());
		}
	}

	/**
	 * This test is validating when user profile data is updated for Japanese in DaaS whether it is getting properly updated or not.
	 */
	@Test(priority = 24, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyUpdatedDaaSProfileInformationForJapanese() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver());
			hpIdProfilePage = hpIdProfilePage.getInstance();
			login("HPID_COMPANY_USER_EMAIL1", "HPID_COMPANY_USER_PASSWORD1");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver());
			userPage = userPage.getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver());
			dashboardPage = dashboardPage.getInstance();

			SoftAssert sa = new SoftAssert();
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.clickOnElementsOfUserPage("edit");
			String firstName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 8);
			String lastName = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 8);
			String addLine1 = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 8);
			String addLine2 = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 8);
			String state = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 8);
			String city = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 8);
			String zip = hpIdProfilePage.generateRandomNumericStringHPIDPage(6);
			String officePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(8);
			String mobilePhone = hpIdProfilePage.generateRandomNumericStringHPIDPage(8);
			String title = hpIdProfilePage.generateRandomCharacterStringHPIDPage("Japanese", 4);
			userPage.enterTextForUserPage("firstNameInput", firstName);
			userPage.enterTextForUserPage("lastNameInput", lastName);
			userPage.enterTextForUserPage("addLine1Input", addLine1);
			userPage.enterTextForUserPage("addLine2Input", addLine2);
			userPage.enterTextForUserPage("stateInput", state);
			userPage.enterTextForUserPage("cityInput", city);
			userPage.enterTextForUserPage("zipInput", zip);
			userPage.enterTextForUserPage("officePhoneInput", officePhone);
			userPage.enterTextForUserPage("mobilePhoneInput", mobilePhone);
			userPage.enterTextForUserPage("titleInput", title);
			String country = hpIdProfilePage.selectRandomCountry();
			userPage.clickOnElementsOfUserPage("save");
			userPage.waitForElementsOfUserPageForClick("edit");
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.waitForElementsOfUserPage("firstName");
			userPage.waitForElementsOfUserPage("addLine1");
			userPage.waitForElementsOfUserPage("state");
			sa.assertTrue(firstName.equals(userPage.getTextOfUserPage("firstName")), "firstName is not matched.");
			sa.assertTrue(lastName.equals(userPage.getTextOfUserPage("lastName")), "lastName is not matched.");
			sa.assertTrue(addLine1.equals(userPage.getTextOfUserPage("addLine1")), "addLine1 is not matched.");
			sa.assertTrue(addLine2.equals(userPage.getTextOfUserPage("addLine2")), "addLine2 is not matched.");
			sa.assertTrue(state.equals(userPage.getTextOfUserPage("state")), "state is not matched.");
			sa.assertTrue(city.equals(userPage.getTextOfUserPage("city")), "city is not matched.");
			sa.assertTrue(zip.equals(userPage.getTextOfUserPage("zip")), "zip is not matched.");
			sa.assertTrue(officePhone.equals(userPage.getTextOfUserPage("officePhoneStrong")), "officePhone is not matched.");
			sa.assertTrue(mobilePhone.equals(userPage.getTextOfUserPage("mobilePhoneStrong")), "mobilePhone is not matched.");
			sa.assertTrue(country.equals(userPage.getTextOfUserPage("country")), "country is not matched.");
			sa.assertTrue(title.equals(userPage.getTextOfUserPage("title")), "Title is not matched.");
			sa.assertAll();
			LOGGER.info("Profile data for MSP is matched successfully");
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test verifyUpdatedDaaSProfileInformationForMSP(): " + e.getMessage());
		}
	}
}
