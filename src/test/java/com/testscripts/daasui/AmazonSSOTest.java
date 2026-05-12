package com.testscripts.daasui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.LaunchDarkly;
import com.daasui.constants.AmazonSSOVariables;
import com.daasui.pages.AmazonSSOPage;

public class AmazonSSOTest extends CommonTest {
	
	private static Logger LOGGER = LogManager.getLogger(AmazonSSOTest.class);	
	public static String amazonEmail = getEnvironmentSpecificData(System.getProperty("environment"), "AMAZON_USER_EMAIL");
	public static String amzzonPassword = getEnvironmentSpecificData(System.getProperty("environment"), "AMAZON_USER_PASSWORD");
	public static String techpulseURL = getEnvironmentSpecificData(System.getProperty("environment"), "TECHPULSE_USDEV_URL");
	public static String amazonFirttimeUserEmail = getEnvironmentSpecificData(System.getProperty("environment"), "AMAZON_FIRSTTIME_USER_EMAIL");
	public static String amzzonFirttimeUserPassword = getEnvironmentSpecificData(System.getProperty("environment"), "AMAZON_FIRSTTIME_USER_PASSWORD");
	LaunchDarkly ldinstance = new LaunchDarkly();


	@Test(priority = 1 , groups = { "REGRESSION" } , description = "Test Case ID :")
	public final void verifyAmazonSsoLogin() throws Exception{
		AmazonSSOPage amazonsso = new AmazonSSOPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
				
		getUrl(getEnvironment(System.getProperty("environment"))+"amazon");
		amazonsso.waitForPageLoaded();
		acceptCookiesConsent();

		amazonsso.clickByJavaScriptOnAmazonSsoPage("signinwithamazon");

		String currentURL = amazonsso.getUrlOfCurrentPage();

		softAssert.assertTrue(currentURL.contains(AmazonSSOVariables.AMAZON_URL),
					"User did redirected to amazon portals for login");
		amazonsso.enterTextForAmazonSsoPage("enteramazonemail",amazonEmail );
		amazonsso.enterTextForAmazonSsoPage("enteramazonpassowrd",amzzonPassword );
		amazonsso.clickByJavaScriptOnAmazonSsoPage("amazonsigninbutton");
		waitForPageLoaded();

		String daasURL = amazonsso.getUrlOfCurrentPage();
		softAssert.assertTrue(daasURL.contains(techpulseURL),
				"User did redirected to DaaS portal");
		LOGGER.info("Amazon SSO verified successfully");
	}

	@Test(priority = 2 , groups = { "REGRESSION" } , description = "Test Case ID :")
	public final void verifyAmazonSsoLoginForFirsttimeUser() throws Exception{
		AmazonSSOPage amazonsso = new AmazonSSOPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		getUrl(getEnvironment(System.getProperty("environment"))+"amazon");
		amazonsso.waitForPageLoaded();
		acceptCookiesConsent();
		amazonsso.clickByJavaScriptOnAmazonSsoPage("signinwithamazon");
		String currentURL = amazonsso.getUrlOfCurrentPage();

		softAssert.assertTrue(currentURL.contains(AmazonSSOVariables.AMAZON_URL),
				"User did redirected to amazon portals for login");

		amazonsso.enterTextForAmazonSsoPage("enteramazonemail",amazonFirttimeUserEmail );
		amazonsso.enterTextForAmazonSsoPage("enteramazonpassowrd",amzzonFirttimeUserPassword );
		amazonsso.clickByJavaScriptOnAmazonSsoPage("amazonsigninbutton");
		waitForPageLoaded();

		String tandcPage = amazonsso.getUrlOfCurrentPage();

		softAssert.assertTrue(tandcPage.contains(AmazonSSOVariables.TANDC_URL),
				"User did redirected to terms and conditions page");
		softAssert.assertTrue(amazonsso.verifyElementsOfAmazonSsoPage("agreeTNCCheckbox"),
				"Terms and conditions check box is visible");
		amazonsso.clickByJavaScriptOnAmazonSsoPage("agreeTNCCheckbox");
		softAssert.assertTrue(amazonsso.verifyElementsOfAmazonSsoPage("tandCAcceptButton"),
				"Accept button is enabled");
		softAssert.assertTrue(amazonsso.verifyElementsOfAmazonSsoPage("tandCCancelButton"),
				"cancel button is present");
		amazonsso.clickByJavaScriptOnAmazonSsoPage("tandCCancelButton");
		softAssert.assertTrue(amazonsso.verifyElementsOfAmazonSsoPage("tandCCancelModal"),
				"TandC popup displayed");
		amazonsso.clickByJavaScriptOnAmazonSsoPage("signOutbutton");
		String signOutURL = amazonsso.getUrlOfCurrentPage();
		softAssert.assertTrue(signOutURL.contains(AmazonSSOVariables.signOutURL_URL),
				"User did redirected to amazon portal after logout");
		LOGGER.info("New User flow for amazon is verified with following URL" + signOutURL);
	}

}
