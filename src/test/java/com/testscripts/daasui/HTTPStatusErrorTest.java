package com.testscripts.daasui;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.ErrorPage;

public class HTTPStatusErrorTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(IncidentTest.class);

	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verify404ErrorPage() throws Exception {
		SoftAssert sa = new SoftAssert();

		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/error/" + CommonVariables.PAGENOTFOUNDERROR);
		waitForPageLoaded();
		errorPage.waitForElementsOfErrorPage("errorIcon");
		sa.assertTrue(errorPage.verifyElementsOfErrorPage("errorIcon"), "Hp icon is not present on the 404 error page");
		sa.assertTrue(errorPage.matchTextOfErrorPage("errorMessage", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.heading.404")), "Error message on 404 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.404")), "No permission title 1 on 404 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle2", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.404")), "No permission title 2 on 404 error page is incorrect");
		errorPage.clickOnElementsOfErrorPage("goBackSignIn");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyDropdown");
		sa.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "After clicking on GO TO HOME PAGE button, user is not redirected to dashboard page");

		sa.assertAll();
		LOGGER.info("All test cases of 404 error page have passed");
	}

	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verify401ErrorPage() throws Exception {
		SoftAssert sa = new SoftAssert();

		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/error/" + CommonVariables.UNAUTHORISEDERROR);
		waitForPageLoaded();
		errorPage.waitForElementsOfErrorPage("errorIcon");
		sa.assertTrue(errorPage.verifyElementsOfErrorPage("errorIcon"), "Hp icon is not present on the 401 error page");
		sa.assertTrue(errorPage.matchTextOfErrorPage("errorMessage", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.heading.401")), "Error message on 401 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.401")), "No permission title 1 on 401 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle2", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.401")), "No permission title 2 on 401 error page is incorrect");
		errorPage.clickOnElementsOfErrorPage("goBackSignIn");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyDropdown");
		sa.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "After clicking on GO TO HOME PAGE button, user is not redirected to dashboard page");

		sa.assertAll();
		LOGGER.info("All test cases of 401 error page have passed");
	}

	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verify500ErrorPage() throws Exception {
		SoftAssert sa = new SoftAssert();

		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/error/" + CommonVariables.INTERNALSERVERERROR);
		waitForPageLoaded();
		errorPage.waitForElementsOfErrorPage("errorIcon");
		sa.assertTrue(errorPage.verifyElementsOfErrorPage("errorIcon"), "Hp icon is not present on the 500 error page");
		sa.assertTrue(errorPage.matchTextOfErrorPage("errorMessage", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.heading.500")), "Error message on 500 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.500")), "No permission title 1 on 500 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle2", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.500")), "No permission title 2 on 500 error page is incorrect");
		errorPage.clickOnElementsOfErrorPage("goBackSignIn");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyDropdown");
		sa.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "After clicking on TRY AGAIN button, user is not redirected to dashboard page");

		sa.assertAll();
		LOGGER.info("All test cases of 500 error page have passed");
	}

	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verify503ErrorPage() throws Exception {
		SoftAssert sa = new SoftAssert();

		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/error/" + CommonVariables.SERVICEUNAVAILABLEERROR);
		waitForPageLoaded();
		errorPage.waitForElementsOfErrorPage("errorIcon");
		sa.assertTrue(errorPage.verifyElementsOfErrorPage("errorIcon"), "Hp icon is not present on the 503 error page");
		sa.assertTrue(errorPage.matchTextOfErrorPage("errorMessage", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.heading.503")), "Error message on 503 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.503")), "No permission title 1 on 503 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle2", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.503")), "No permission title 2 on 503 error page is incorrect");
		errorPage.clickOnElementsOfErrorPage("goBackSignIn");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyDropdown");
		sa.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "After clicking on TRY AGAIN button, user is not redirected to dashboard page");

		sa.assertAll();
		LOGGER.info("All test cases of 503 error page have passed");
	}
	
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verify404ErrorTest() throws Exception {
		SoftAssert sa = new SoftAssert();

		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment")) + "12345");
		waitForPageLoaded();
		errorPage.waitForElementsOfErrorPage("errorIcon");
		sa.assertTrue(errorPage.verifyElementsOfErrorPage("errorIcon"), "Hp icon is not present on the 404 error page");
		sa.assertTrue(errorPage.matchTextOfErrorPage("errorMessage", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.heading.404")), "Error message on 404 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.404")), "No permission title 1 on 404 error page is incorrect");
		sa.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle2", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.404")), "No permission title 2 on 404 error page is incorrect");
		errorPage.clickOnElementsOfErrorPage("goBackSignIn");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyDropdown");
		sa.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "After clicking on GO TO HOME PAGE button, user is not redirected to dashboard page");

		sa.assertAll();
		LOGGER.info("All test cases of 404 error page have passed");
	}

}
