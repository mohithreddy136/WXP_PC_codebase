package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.WelcomePage;
import com.daasui.pages.WhatsNewPage;

public class WhatsNewTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WhatsNewTest.class);

	public static List<String> listOfIds = new ArrayList<String>();
	public static String environment = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH");
	public static String environmentCatalyst = getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL");

	@DataProvider
	public Object[][] loginDataForWhatNew() {
		Object[][] data = new Object[3][2];
		data[0][0] = "MSP_HELP_AND_SUPPORT_EMAIL";
		data[0][1] = "MSP_HELP_AND_SUPPORT_EMAIL_PASSWORD";
		data[1][0] = "RESELLER_HELP_AND_SUPPORT_EMAIL";
		data[1][1] = "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD";
		data[2][0] = "IT_ADMIN_HELP_AND_SUPPORT_EMAIL"; 
		data[2][1] = "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD";
		return data;
	}
	
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI","PRODUCTION_CI", "PRODUCTION_SANITY","FOUNDATIONACCOUNT_PRODUCTIONCI" })
	public final void verifyWhatsNewTitle() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoWhatsNewTabOfRootAdmin();
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		
		waitForPageLoaded();
		Assert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "whats_new.title")), "What's New title text is incorrect");
		sleeper(2000);
		Assert.assertTrue(whatsNewPage.verifyReleaseButton(), "Button for Draft a New release is not displayed. ");
		LOGGER.info("What's New title is matched");
	}

	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI","STABILIZATION_STAGING" })
	public final void verifyNewFutureRelease() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoWhatsNewTabOfRootAdmin();
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		

		softAssert.assertTrue(whatsNewPage.removeAllReleases(environmentCatalyst), "Delete API got failed while removing releases.");
		Assert.assertTrue(whatsNewPage.addNewFutureRelease(LanguageCode), "New Release for Future could not be added successfully.");
		logout();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoWhatsNewTabOfMspRA();
		Assert.assertTrue(whatsNewPage.verifyFutureReleaseMspRA(), "New Release for Future reflected back on MSP/RA's What's New page.");
//		logout();
//		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
//		gotoWhatsNewTabOfRootAdmin();
//		Assert.assertTrue(whatsNewPage.verifyRemoveRelease(LanguageCode,WhatsNewPage.versionTextFuture), "New Release for Future could not be removed successfully.");
		softAssert.assertAll();
		LOGGER.info("Validation of newly added Future Release got completed.");
	}

	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI","STABILIZATION_STAGING" })
	public final void verifyNewLatestRelease() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoWhatsNewTabOfRootAdmin();
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(whatsNewPage.removeAllReleases(environmentCatalyst), "Delete API got failed while removing releases.");
		boolean add_release = whatsNewPage.addNewLatestRelease(LanguageCode);
		sleeper(3000);
		Assert.assertTrue(add_release, "New Latest Release could not be added successfully.");
		listOfIds = whatsNewPage.getLatestReleaseDetails(environmentCatalyst + ConstantURL.GET_API_WHATSNEW, "{}", 0, "id");
		logout();
	    sleeper(2000);
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoWhatsNewTabOfMspRA();
		sleeper(2000);
		softAssert.assertTrue(whatsNewPage.verifyNewLatestReleaseMSP(), "New Latest Release did not get reflected on MSP's What's New Page.");
//		logout();
//		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
//		gotoWhatsNewTabOfRootAdmin();
//		sleeper(2000);
//		boolean remove_release =whatsNewPage.verifyRemoveRelease(LanguageCode,WhatsNewPage.versionTextLatest);
//		sleeper(3000);
//		Assert.assertTrue(remove_release, "New Release for Future could not be removed successfully.");
//		logout();
//		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
//		gotoWhatsNewTabOfMspRA();
//		sleeper(2000);
//		softAssert.assertTrue(whatsNewPage.checkDeletedRelease(), "Release not deleted for MSP");
		softAssert.assertAll();
		LOGGER.info("Validation of Latest Release got completed.");
	}

	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyEditRemoveOfFutureRelease() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoWhatsNewTabOfRootAdmin();
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();


		softAssert.assertTrue(whatsNewPage.removeAllReleases(environmentCatalyst), "Delete API got failed while removing releases.");
		boolean add_release = whatsNewPage.addNewLatestRelease(LanguageCode);
		Assert.assertTrue(whatsNewPage.verifyEditFutureRelease(LanguageCode), "New Release for Future could not be edited successfully.");
		Assert.assertTrue(whatsNewPage.verifyRemoveRelease(LanguageCode,WhatsNewPage.versionTextFuture), "New Release for Future could not be removed successfully.");
		softAssert.assertAll();
		LOGGER.info("Validation of Edit and Remove Functionality for Future Release got completed.");
	}

	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyEditRemoveOfLatestRelease() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoWhatsNewTabOfRootAdmin();
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		

		softAssert.assertTrue(whatsNewPage.removeAllReleases(environmentCatalyst), "Delete API got failed while removing releases.");
		Assert.assertTrue(whatsNewPage.addNewLatestRelease(LanguageCode), "New Latest Release could not be added successfully.");
		Assert.assertTrue(whatsNewPage.verifyEditLatestRelease(LanguageCode), "New Release for Future could not be edited successfully.");
		softAssert.assertAll();
		LOGGER.info("Validation of Edit and Remove Functionality for Latest Release got completed.");
	}
	
	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyReleaseAddFirst() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		gotoWhatsNewTabOfRootAdmin();
		
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(whatsNewPage.addNewLatestRelease(LanguageCode), "Add new release functionality failed");
		LOGGER.info("New Release added successfully. ");
	}
	
	@Test(priority = 7, dataProvider = "loginDataForWhatNew",groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" },description = "Test Case ID : 353530",enabled = false)
	public final void verifyWhatsNewTab(String username, String password) throws Exception {
		login(username, password);
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		gotoWhatsNewTabOfMspRA();
		waitForPageLoaded();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))) {
			Assert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "whats_new.title")), "What's New title text is incorrect");
		}else {
			Assert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "whats_new.title")), "What's New title text is incorrect");
		}
		sleeper(2000);
		Assert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewTitlePartner").equals(getTextLanguage(LanguageCode, "daas_ui", "whats.new.partner.title")), "What's New header text is incorrect");
		LOGGER.info("Validation of What's New Tab completed successfully");
	}
	
	/**
	 * This method verifies the What's new pop alert for new release
	 * 
	 * @throws Exception
	 * Disabled this test case since "Whats-New-Alerts" LD toggle is off for all users in stage
	 * Will enable it once toggle is ON fr staging users
	 */
	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI" }, description = "Test Case = 619381", enabled = false)
	public final void verifyWhatsNewPopupForNewRelease() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");

		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		//Verify what's new email alert toggle
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/profile/communicationPreferencesPanel");
		whatsNewPage.waitForElementsOfWhatsNewPage("tableOverlay");
		whatsNewPage.waitForElementsOfWhatsNewPage("emailAlertsText");
		whatsNewPage.MoveoverElementForWhatsNewPage("emailAlertsText");
		softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("emailAlertsText").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.whatsnew_mail")), "What's New email alerts text did not match");
		if(whatsNewPage.getTextOfWhatsNewPage("emailAlertsToggleText").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			whatsNewPage.clickOnElementsOfWhatsNewPage("emailAlertToggle");
			whatsNewPage.waitForElementsOfWhatsNewPage("tableOverlay");
			softAssert.assertTrue(whatsNewPage.matchTextOfWhatsNewPage("emailAlertsToggleText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "What's new email alert toggle text did not changed to after disabling the toggle.");
			LOGGER.info("Disabled What's new popup alert toggle");
		} else {
			whatsNewPage.clickOnElementsOfWhatsNewPage("emailAlertToggle");
			whatsNewPage.waitForElementsOfWhatsNewPage("tableOverlay");
			softAssert.assertTrue(whatsNewPage.matchTextOfWhatsNewPage("emailAlertsToggleText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "What's new email alert toggle text did not changed to after enabling the toggle.");
		}
		LOGGER.info("Verified what's new email alert toggle");

		//Verify What's new popup alert toggle
		softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("popupAlertText").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.whatsnew_alert")), "What's New popup alerts text did not match");
		if(whatsNewPage.getTextOfWhatsNewPage("popupAlertsToggleText").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			whatsNewPage.clickOnElementsOfWhatsNewPage("popupAlertToggle");
			whatsNewPage.waitForElementsOfWhatsNewPage("tableOverlay");
			softAssert.assertTrue(whatsNewPage.matchTextOfWhatsNewPage("popupAlertsToggleText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "What's new popup alert toggle text did not changed to after disabling the toggle.");
			LOGGER.info("Disabled What's new popup alert toggle");
		}
		logout();

		//Add new release through root login
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoWhatsNewTabOfRootAdmin();
		softAssert.assertTrue(whatsNewPage.removeAllReleases(environment), "Delete API got failed while removing releases.");
		Assert.assertTrue(whatsNewPage.addNewLatestRelease(LanguageCode), "Add new release functionality failed cannot proceed further.");
		LOGGER.info("Added new what's new release");
		logout();

		//verify what's new popup not appering when what's new popup alerts toggle is disabled
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/dashboard");
		loginCustom("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		softAssert.assertFalse(whatsNewPage.verifyElementsOfWhatsNewPage("whatsNewPopup"), "What's new release popup is appering even if popup alert toggle is disabled.");	

		//enable what's new popup alert toggle
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/profile/communicationPreferencesPanel");
		whatsNewPage.waitForElementsOfWhatsNewPage("tableOverlay");
		whatsNewPage.waitForElementsOfWhatsNewPage("popupAlertText");
		whatsNewPage.MoveoverElementForWhatsNewPage("popupAlertText");
		whatsNewPage.clickOnElementsOfWhatsNewPage("popupAlertToggle");
		whatsNewPage.waitForElementsOfWhatsNewPage("tableOverlay");
		softAssert.assertTrue(whatsNewPage.matchTextOfWhatsNewPage("popupAlertsToggleText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "What's new popup alert text did not changed to after enabling the toggle.");
		LOGGER.info("Enabled what's new popup alert toggle");
		logout();

		//Add new release through root login
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoWhatsNewTabOfRootAdmin();
		Assert.assertTrue(whatsNewPage.addNewLatestRelease(LanguageCode), "Add new release functionality failed cannot proceed further.");
		LOGGER.info("Added new what's new release");
		logout();

		//Verify what's new popup alert is appering when toggle is enabled
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/dashboard");
		loginCustom("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("whatsNewPopup"), "What's new release popup is not appering even if popup alert toggle is enabled.");
		softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewTitleOnPopup").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.title")), "What's new title text did not match");
		softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewDescriptionOnPopup").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "welcome.whats_new.modal.description")), "What's new description text on popup did not match");

		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("releaseVersionOnPopup"), "What's new release version not present on popup");
		softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("customersNotes").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.customers")), "What's new Customers text did not match");
		softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("partnersNotes").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.partners")), "What's new Partners text did not match");

		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("customersProductEnhancementData"), "What's new product enhancement data not for customers not present on popup");
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("partnersProductEnhancementData"), "What's new product enhancement data not for partners not present on popup");
		LOGGER.info("Verified What's new popup alert");

		//verify user redirects to communication preferences panel after clicking on manage notification link
		whatsNewPage.clickOnElementsOfWhatsNewPage("manageNotificationLink");
		whatsNewPage.waitForElementsOfWhatsNewPage("tableOverlay");
		softAssert.assertTrue(whatsNewPage.getUrlOfCurrentPage().contains("communicationPreferencesPanel"), "Page not redirected to communication preferences page after clicking on manage notifications link");

		softAssert.assertAll();
		LOGGER.info("Test cases for verifyWhatsNewPopupForNewRelease passed successfully.");
	}

	/**
	 * This method verifies the What's new section on welcome page
	 *
	 * @throws Exception
	 */
	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI" }, description = "Test Case = 620518")
	public final void verifyWhatsNewSectionOnWelcomePage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();

		//Verify what's new section on welcome page
 		if(welcomePage.waitForElementsOfWelcomePageDynamic("cookieAccept", 20))
			welcomePage.clickOnElementsOfWelcomePage("cookieAccept");
		welcomePage.scrollOnWelcomePage("whatsNewSection");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("whatsNewSection"), "What's new section not present on welcome page");
		softAssert.assertTrue(welcomePage.getTextOfWelcomePage("whatsNewHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "welcome.whats_new.title")), "What's new title did not match on welcome page");
		if(getDriver().getCurrentUrl().contains("admin")) {
			softAssert.assertTrue(welcomePage.getTextOfWelcomePage("whatsNewDescription").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "welcome.whats_new.modal.description").replaceAll("[>\\{}]", "").toString().replaceAll("appName","Workforce Experience")), "What's new description did not match on welcome page");
		}else{
			softAssert.assertTrue(welcomePage.getTextOfWelcomePage("whatsNewDescription").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "welcome.whats_new.section.text")+"."), "What's new description did not match on welcome page");
		}
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("seeMoreButton"), "See more button not present on welcome page");
		softAssert.assertTrue(welcomePage.getTextOfWelcomePage("seeMoreButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.link.see_more")),"See more button text did not match");
		welcomePage.clickOnElementsOfWelcomePage("seeMoreButton");
		waitForPageLoaded();

		//Verify exposed what's new page
		String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.WHATS_NEW;
		softAssert.assertTrue(url.equals(whatsNewPage.getUrlOfCurrentPage()), "URL not matching");
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("hpIcon"), "HP icon not present on what's new page");
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("hpDaas"), "HP Daas not present on what's new page");
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("languageDropDown"), "Language drop down not present what's new page");
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("signInButton"), "Sign In button not present on what's new page");
		if(getDriver().getCurrentUrl().contains("admin"))
		{
			softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewSectionHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "whats.new.partner.title.adminx.central").replaceAll("[>\\{}]", "").toString().replaceAll("appName","Workforce Experience")), "What's new section header did not match on whatsNew page");

		}else {
			softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewSectionHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "whats.new.partner.title")), "What's new section header did not match on whatsNew page");
		}
		softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewDescription").equals(getTextLanguage(LanguageCode, "daas_ui", "whats_new.description")), "What's new section description did not match on whatsNew page");
		navigateToBack();
		waitForPageLoaded();

		//Verify what's new page when user is logged in
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(url);
		waitForPageLoaded();
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("hpIcon"), "HP icon not present on what's new page");
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("hpDaas"), "HP Daas not present on what's new page");
		softAssert.assertFalse(whatsNewPage.verifyElementsOfWhatsNewPage("languageDropDown"), "Language drop down present on what's new page even if user is currently logged in");
		softAssert.assertFalse(whatsNewPage.verifyElementsOfWhatsNewPage("signInButton"), "Sign In button present on what's new page even if user is currently logged in");
		if(getDriver().getCurrentUrl().contains("admin"))
		{
			softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewSectionHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "whats.new.partner.title.adminx.central").replaceAll("[>\\{}]", "").toString().replaceAll("appName","Workforce Experience")), "What's new section header did not match on whatsNew page");

		}else {
			softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewSectionHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "whats.new.partner.title")), "What's new section header did not match on whatsNew page");
		}		softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewDescription").equals(getTextLanguage(LanguageCode, "daas_ui", "whats_new.description")), "What's new section description did not match on whatsNew page");

		softAssert.assertAll();
		LOGGER.info("Test case verifyWhatsNewSectionOnWelcomePage passed successfully");
	}

	/**
	 * This method verifies the What's alert toggles on user profile page
	 *
	 * @throws Exception
	 */
	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI" }, description = "Test Case = 619381")
	public final void verifyWhatsNewAlertTogglesOnProfilePage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String tenantID = null;
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		String companyName = generateRandomString(5);
		SoftAssert softAssert = new SoftAssert();
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		try {
			//create new company
			sleeper(3000);
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(companyName, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "WHATS_NEW_TEST_COMPANY_EMAIL"), CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(companyName + " company created");
			logout();

			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/dashboard");
			welcomePage.waitForElementsOfWelcomePage("signInButton");
			welcomePage.clickOnElementsOfWelcomePage("signInButton");
			loginCustom("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
			dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
			companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			LOGGER.info("Login with new company");
			tenantID = getValueFromToken("tenant");
			waitForPageLoaded();

			companiesPage.waitForElementsOfCompaniesPageDynamic("welcomeTitle", 10);
			companiesPage.waitForElementsOfCompaniesPage("onboardingPopupCloseButton");
			companiesPage.clickOnElementsOfCompaniesPage("onboardingPopupCloseButton");
			if (whatsNewPage.waitForElementsOfWhatsNewPage("whatsNewPopupCloseButton"))
				whatsNewPage.clickOnElementsOfWhatsNewPage("whatsNewPopupCloseButton");

			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/profile/communicationPreferencesPanel");
			companiesPage.waitUntilElementInvisibleOfCompanyPage("companyListPageSpiner");
			companiesPage.scrollOnCompaniesPage("popupAlertsToggleText");
			if (toggleVerification(CommonVariables.WHATS_NEW_EMAIL_TOGGLE, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl")))
				softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("emailAlertsToggleText").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),"Email alert toggle is not enabled by default for new user");
			softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("popupAlertsToggleText").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.enabled")),"Popup alert toggle is not enabled by default for new user");
			softAssert.assertAll();
			LOGGER.info("Test case verifyWhatsAlertTogglesOnProfilePage passed successfully");
		} catch (Exception e) {
			LOGGER.info("Exception occured in method verifyWhatsAlertTogglesOnProfilePage " + e.getMessage());
		} finally {
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			companiesPage.removeCompanyUsingAPI(tenantID);
		}
	}
	
	/**
	 * This test case validates the context sensitive help links on Whatsnew screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 11, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksOnWhatsnew() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for Whatsnew tab
		waitForPageLoaded();
		gotoWhatsNewTabOfMspRA();
		LOGGER.info("Redirected to Whatsnew tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on Whatsnew tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Whatsnew tab");
		waitForPageLoaded();
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs")
				.equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Whatsnew tab");
		gotoWhatsNewTabOfMspRA();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on Whatsnew passed successfully.");
	}
	@Test(priority = 12,groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" },description = "Test Case ID : 353530",enabled=false)
	public final void verifyWhatsNewTabLink() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		gotoWhatsNewTabOfRootAdmin();
		waitForPageLoaded();
		sleeper(2000);
		String whatsNewURL = whatsNewPage.getTextOfWhatsNewPage("whatsNewLink");
		whatsNewPage.clickOnElementsOfWhatsNewPage("whatsNewLink");
		whatsNewPage.switchToDifferentTab();
	    String whatsNewTabUrl = whatsNewPage.getUrlOfCurrentPage();
	    Assert.assertEquals(whatsNewURL, whatsNewTabUrl);
		LOGGER.info("Validation of What's New Tab completed successfully");
		
		
	}
	
	@Test(priority = 12,groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" },description = "Test Case ID : 353530",enabled = false)
	public final void AddNewRelease () throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		gotoWhatsNewTabOfRootAdmin();
		waitForPageLoaded();
		sleeper(2000);
		String whatsNewURL = whatsNewPage.getTextOfWhatsNewPage("whatsNewLink");
		whatsNewPage.clickOnElementsOfWhatsNewPage("whatsNewLink");
		whatsNewPage.switchToDifferentTab();
	    String whatsNewTabUrl = whatsNewPage.getUrlOfCurrentPage();
	    Assert.assertEquals(whatsNewURL, whatsNewTabUrl);
		LOGGER.info("Validation of What's New Tab completed successfully");
		
		
	}
}
