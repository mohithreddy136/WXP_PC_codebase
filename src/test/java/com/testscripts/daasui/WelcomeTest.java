package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.WelcomePage;
import com.daasui.pages.WhatsNewPage;

/**
 * This class implements test related to welcome page
 * 
 * 
 */
public class WelcomeTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WelcomeTest.class);

	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "PRODUCTION_CI" },enabled=false)
	public final void verifyElementsOfWelcomePage() throws Exception {
		SoftAssert sa = new SoftAssert();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		
		welcomePage.clickOnElementsOfWelcomePage("cookieAccept");
		LOGGER.info("Cookie pop up accepted");
		
		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("hpLogo"), "Hp logo is not present on welcome page");

		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("signInButton"), "Sign in button is not present on welcome page");

		sleeper(2000);
		waitForPageLoaded();
		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("welcomePageTitle"), "title is not present on welcome page");
		sa.assertTrue(welcomePage.getTextOfWelcomePage("welcomePageTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "welcome.daas")), "title does not match on welcome page");

		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("welcomePageInfo"), "Information is not present on welcome page");
		sleeper(1000);
		waitForPageLoaded();
		sa.assertTrue(welcomePage.getTextOfWelcomePage("welcomePageInfo").equals(getTextLanguage(LanguageCode, "daas_ui", "welcome.banner.title")), "banner does not match on welcome page");

		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("welcomePageDescription"), "description is not present on welcome page");
		sleeper(1000);
		waitForPageLoaded();
		sa.assertTrue(welcomePage.getTextOfWelcomePage("welcomePageDescription").equals("HP "+getTextLanguage(LanguageCode, "daas_ui", "welcome.banner.description")), "description does not match on welcome page");

		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("hpPlans"), "hp plans is not present on welcome page");
		sleeper(1000);
		waitForPageLoaded();
		sa.assertTrue(welcomePage.getTextOfWelcomePage("hpPlans").equals(getTextLanguage(LanguageCode, "daas_ui", "welcome.daas.presence.terms")), "HP terms information does not match on welcome page");

		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("welcomeFooter"), "footer  is not present on welcome page");
		waitForPageLoaded();
		sa.assertTrue(welcomePage.getTextOfWelcomePage("welcomeFooter").equals(getTextLanguage(LanguageCode, "daas_ui", "welcome.copyright").replace("{year}", "2024")), "Hp footer does not match on welcome page");

		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("languageDropdown"), "language dropdown  is not present on welcome page");

		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("privacyLink"), "privacy Link is not present on welcome page");
		welcomePage.scrollOnWelcomePage("privacyLink");
		welcomePage.clickOnElementsOfWelcomePage("privacyLink");
		sleeper(3000); // need to redirect to third party URL
		switchToDifferentTab();
		sa.assertTrue(welcomePage.getUrlOfCurrentPage().equalsIgnoreCase(ConstantURL.PRIVACY_PAGE_URL_WelcomePage), "After clicking on privacy link its not redirecting to privacy page");
		//sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("privacyPage"), "After clicking on privacy link its not redirecting to privacy page");
		switchBackToPreviousTab();
		
		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("learnMoreButton"), "privacy Link is not present on welcome page");
		welcomePage.scrollOnWelcomePage("learnMoreButton");
		welcomePage.clickOnElementsOfWelcomePage("learnMoreButton");
		sleeper(3000); // need to redirect to third party URL
		switchToDifferentTab();
		sa.assertTrue(welcomePage.getUrlOfCurrentPage().contains("services/techpulse"), "After clicking on privacy link its not redirecting to privacy page");

//		sa.assertTrue(welcomePage.verifyElementsOfWelcomePage("servicesPageText"), "After clicking on learn more link its not redirecting to services page");
		switchBackToPreviousTab();
		
		sa.assertAll();
		LOGGER.info("All elements of welcome page matches successfully");
	}

	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verifyWelcomePageFunctionalityAfterLoginWithCompanyUser() throws Exception {
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if(getUrlOfCurrentPage().contains("admin")){
			getUrl(getUrlOfCurrentPage().replaceAll("/ui/view/dashboard","") + "/welcome");
		}else {
			getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "welcome");
		}
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("dashboardTitleOnBreadcrumbs"), "when we hit welcome url after login , it does not redirect to dashboard page ");
	}

	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verifyWelcomePageFunctionalityAfterLoginWithRootAdmin() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();

		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "welcome");
		Assert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("partnerTitle"), "when we hit welcome url after login , it does not redirect to partner page ");
	}

	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verifyWelcomePageFunctionalityForSpecificUSUser() throws Exception {

		if (environment.equals("US-STAGING")) {
			login("MSP_DEVICE_LIST_EMAIL_COMPANIES", "MSP_DEVICE_LIST_PASSWORD_COMPANIES");
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

			Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("dashboardTitle"), "After login with specific user it does not redirect to dashboard");

		} else {
			LOGGER.info("environment we are using is other than us-staging.This test case is for US-Staging only");
		}
	}

	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verifyWelcomePageFunctionalityForSpecificEUUser() throws Exception {

		if (environment.equals("EU-STAGING")) {
			login("MSP_DEVICE_LIST_EMAIL_COMPANIES", "MSP_DEVICE_LIST_PASSWORD_COMPANIES");
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

			Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("dashboardTitle"), "After login with specific user it does not redirect to dashboard");

		} else {
			LOGGER.info("environment we are using is other than eu-staging.This test case is for eu-Staging only");
		}
	}
	
	/**
	 * This test case will validate cookie consent pop in techpulse
	 * 
	 * @throws Exception
	 */
	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verifycookiePopUp() throws Exception {
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		Assert.assertTrue(welcomePage.verifyElementsOfWelcomePage("cookiePopUp"), "Cookie-consent pop is missing on welcome page");
		welcomePage.clickOnElementsOfWelcomePage("cookieAccept");
		LOGGER.info("Cookie pop up accepted");
	}
	
	@Test(priority = 7, groups = { "PRODUCTION_CI", "REGRESSION" },enabled = false)
	public final void verifyCookiesPopUp() throws Exception {
		Assert.assertTrue(acceptCookiesConsent(), "Cookies consent pop up is not visible.");
	}

	/**
	 * This test case validated help links on welcome page
	 * User Story 887226: [DaaS][UI] Show link to Terms & Conditions page from hpdaas.com landing page footer
	 * @throws Exception
	 */
	@Test(priority = 8, groups = {"REGRESSION"}, description = "Test case ID : 904345")
	public final void verifyTermsAndConditionsLinksOnWelcomePage() throws Exception {
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		if(welcomePage.waitForElementsOfWelcomePageDynamic("cookieAccept", 20))
			welcomePage.clickOnElementsOfWelcomePage("cookieAccept");
		welcomePage.scrollOnWelcomePage("welcomePageFooter");

		//Verify help links on welcome page
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("welcomePageFooter"), "Terms and conditions links not present on welcome page");

		//Verify terms and conditions link redirection
		welcomePage.waitForElementsOfWelcomePage("termsAndConditions");
		welcomePage.clickOnElementsOfWelcomePage("termsAndConditions");
		welcomePage.switchToDifferentTab();
		waitForPageLoaded();
		softAssert.assertTrue(welcomePage.getTextOfWelcomePage("termsAndConditionsTitle").contains(welcomePage.getTextLanguage(LanguageCode, "daas_ui", "help.info.legal.terms.text")), "Title matching failed");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("printButton"), "Print Button not present");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("hpIcon"), "HP icon not present");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("hpDaas"), "HP Daas not present");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("language"), "Language drop down not present");
		String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.TERMS_AND_CONDITIONS + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		softAssert.assertTrue(url.equals(welcomePage.getUrlOfCurrentPage()), "URL not matching");
		welcomePage.switchBackToPreviousTab();
		LOGGER.info("Validation of Terms and Conditions completed.");

		//Verify Privacy link redirection
		welcomePage.waitForElementsOfWelcomePage("privacy");
		welcomePage.clickOnElementsOfWelcomePage("privacy");
		welcomePage.switchToDifferentTab();
		sleeper(10000); // Sleeper cannot be avoided as Page is taking time to load
		softAssert.assertTrue(welcomePage.getUrlOfCurrentPage().contains(welcomePage.getTextLanguage(LanguageCode, "daas_ui", "welcome.privacy").toLowerCase()), "Page not redirected to Privacy page");
		welcomePage.switchBackToPreviousTab();
		LOGGER.info("Validation of Privacy completed.");

		//Verify SLA link redirection
		welcomePage.waitForElementsOfWelcomePage("service");
		welcomePage.clickOnElementsOfWelcomePage("service");
		welcomePage.switchToDifferentTab();
		waitForPageLoaded();
		softAssert.assertTrue(welcomePage.getTextOfWelcomePage("serviceTitle").contains(welcomePage.getTextLanguage(LanguageCode, "daas_ui", "help.info.legal.sla.text")), "Title matching failed");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("printButton"), "Print Button not present");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("hpIcon"), "HP icon not present");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("hpDaas"), "HP Daas not present");
		softAssert.assertTrue(welcomePage.verifyElementsOfWelcomePage("language"), "Language drop down not present");
		url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SLA + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		softAssert.assertTrue(url.equals(welcomePage.getUrlOfCurrentPage()), "URL not matching");
		welcomePage.switchBackToPreviousTab();
		LOGGER.info("Validation of Service Level Agreement completed.");

		softAssert.assertAll();
		LOGGER.info("Test case verifyTermsAndConditionsLinksOnWelcomePage passed successfully.");
	}

}
