package com.testscripts.daasui;

import java.util.List;

import com.daasui.pages.WorkforceExperienceTermsAndConditionsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WelcomePage;
import com.daasui.pages.WorkforceExperienceLandingPage;

public class workforceExperienceLandingPageTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(workforceExperienceLandingPageTest.class);
	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCaseID:C42208116, C43522597", enabled=false)
	public final void verifyElementsOfLandingPage() throws Exception {

		SoftAssert sa = new SoftAssert();
		WorkforceExperienceLandingPage workforcelandingpage = new WorkforceExperienceLandingPage(PreDefinedActions.getDriver()).getInstance();
		workforcelandingpage.clickOnElementsOfLandingPage("cookieAccept");
		LOGGER.info("Landing page loaded");
		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("WorkforceLogo"),
				"Hp logo is not present on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementIsClickableOfLandingPage("Signin"),
				"Sign in button is not clickable on Landing page page");
		sa.assertTrue(workforcelandingpage.verifyElementIsClickableOfLandingPage("UpLearnMore"),
				"UpLearnMore button is not clickable on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementIsClickableOfLandingPage("Platform"),
				"Platform button is not clickable on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementIsClickableOfLandingPage("Partners"),
				"Partners button is not clickable on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementIsClickableOfLandingPage("Resources"),
				"Resources button is not clickable on Landing page");
		workforcelandingpage.mouseHoverOnLandingpage("Resources");
		sa.assertTrue(workforcelandingpage.verifyElementIsClickableOfLandingPage("AboutUs"),
				"AboutUs button is not clickable on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementIsClickableOfLandingPage("Blog"),
				"Blog button is not clickable on Landing page");
		LOGGER.info("Verified Buttons clickable");

		workforcelandingpage.clickOnElementsOfLandingPage("Platform");
		workforcelandingpage.waitForPageLoaded();
		sleeper(3000);
		String platformUrl = SetTestEnvironments.getEnvironment(System.getProperty("environment"))
				+ ConstantURL.Platform;
		sa.assertTrue(platformUrl.equals(workforcelandingpage.getUrlOfCurrentPage()), "Platform URL not matching");
		workforcelandingpage.waitForPageLoaded();
		LOGGER.info("Verified Platform Url");

		workforcelandingpage.clickOnElementsOfLandingPage("Partners");
		workforcelandingpage.waitForPageLoaded();
		sleeper(3000);
		String partnerUrl = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.Partner;
		sa.assertTrue(partnerUrl.equals(workforcelandingpage.getUrlOfCurrentPage()), "Partner URL not matching");
		workforcelandingpage.waitForPageLoaded();
		LOGGER.info("Verified Partners Url");

		workforcelandingpage.clickOnElementsOfLandingPage("Resources");
		workforcelandingpage.waitForPageLoaded();
		sleeper(3000);
		String resourceUrl = SetTestEnvironments.getEnvironment(System.getProperty("environment"))
				+ ConstantURL.Resources;
		sa.assertTrue(resourceUrl.equals(workforcelandingpage.getUrlOfCurrentPage()), "Resource URL not matching");
		workforcelandingpage.waitForPageLoaded();
		LOGGER.info("Verified Resources Url");

		workforcelandingpage.mouseHoverOnLandingpage("Resources");
		workforcelandingpage.clickOnElementsOfLandingPage("AboutUs");
		workforcelandingpage.waitForPageLoaded();
		sleeper(3000);
		String aboutUsUrl = SetTestEnvironments.getEnvironment(System.getProperty("environment"))
				+ ConstantURL.About_Us;
		sa.assertTrue(aboutUsUrl.equals(workforcelandingpage.getUrlOfCurrentPage()), "About Us URL not matching");
		workforcelandingpage.waitForPageLoaded();
		LOGGER.info("AboutUs Platform Url");

		workforcelandingpage.mouseHoverOnLandingpage("Resources");
		workforcelandingpage.clickOnElementsOfLandingPage("Blog");
		workforcelandingpage.waitForPageLoaded();
		sleeper(3000);
		sa.assertTrue(resourceUrl.equals(workforcelandingpage.getUrlOfCurrentPage()), "Blog URL not matching");
		workforcelandingpage.waitForPageLoaded();
		LOGGER.info("Blog Platform Url");

		sa.assertAll();
	}

	@Test(priority = 1, groups = {"REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCaseID: C43522597", enabled=false)
	public final void verifyElementsOfMarketingAdLandingPage() throws Exception {

		SoftAssert sa = new SoftAssert();
		WorkforceExperienceLandingPage workforcelandingpage = new WorkforceExperienceLandingPage(PreDefinedActions.getDriver()).getInstance();

		workforcelandingpage.clickOnElementsOfLandingPage("cookieAccept");
		LOGGER.info("Landing page loaded successfully");

		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("learnMore"),
				"UpLearnMore button is not clickable on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementIsClickableOfLandingPage("learnMore"),
				"LearnMore button is not clickable on Landing page");

		workforcelandingpage.clickOnElementsOfLandingPage("learnMore");
		workforcelandingpage.waitForPageLoaded();
		sleeper(3000);
		LOGGER.info("Verified Learn more tab");
		workforcelandingpage.switchToIframeofOfLandingPage("titleFrame");
		workforcelandingpage.clickOnElementsOfLandingPage("iWantToSign");
		workforcelandingpage.selectValuesFromDropDown("iWantToSign", "Sign up for updates");

		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("aboutU"),
				"About you option is not present on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("firstName"),
				"First name option is not present on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("lastName"),
				"Last name option is not present on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("CountryRegion"),
				"Email option is not present on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("email"),
				"Email option is not present on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("state"),
				"Email option is not present on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("company"),
				"Email option is not present on Landing page");
		sa.assertTrue(workforcelandingpage.verifyElementsOfLandingpagePage("jobTitle"),
				"Email option is not present on Landing page");
		workforcelandingpage.switchToDefaultContentofOfLandingPage();
		LOGGER.info("Verified all details from Learn more tab");
		sa.assertAll();
	}

}
