package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WorkforceExperienceTermsAndConditionsPage;

public class WorkforceExperienceTermsAndConditionsTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WorkforceExperienceTermsAndConditionsTest.class);
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_WEP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		return data;
	}

	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42806818")
	public final void verifyTermsandconditionsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WorkforceExperienceTermsAndConditionsPage termsAndConditions = new WorkforceExperienceTermsAndConditionsPage(PreDefinedActions.getDriver()).getInstance();
		termsAndConditions.clickOnElementsOfTermsandconditionsPage("cookieAccept");
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		termsAndConditions.clickOnElementsOfTermsandconditionsPage("helpAndSupport");
		sleeper(5000);
		termsAndConditions.scrollOnTermsandconditionsPage("termsAndConditions");
		sleeper(5000);
		termsAndConditions.clickOnElementsOfTermsandconditionsPage("termsAndConditions");
		termsAndConditions.switchToDifferentTab();
		waitForPageLoaded();
		String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.TERMS_AND_CONDITIONS + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		softAssert.assertTrue(url.equals(termsAndConditions.getUrlOfCurrentPage()), "Terms and Condition URL not matching");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("wexLogo"), "Hp logo is not present on Terms and conditions page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("printButton"), "Print button is not present on Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementIsClickableOfTermsandconditionsPage("printButton"), "Print Button is not clickable on Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("notificationBell"), "notification bell is not present on Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("profieIcon"), "Hp logo is not present on  Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("hpWorkforceMainHeader"), "HpWork header is not present on Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("hpWorkforceMainHeaderBelowPara1"), "hpWorkforceMainHeaderBelowPara1 is not present on Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("serviceUseTermsHeader"), "serviceUseTermsHeader is not present on Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("additionalTermsHeader"), "additionalTermsHeader is not present on Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("documentText"), "documentText is not present on Terms and condition page");
		softAssert.assertAll();
	}
	
	@Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C44673079" )
	public final void verifyLinksOfTermsAndConditionsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WorkforceExperienceTermsAndConditionsPage termsAndConditions = new WorkforceExperienceTermsAndConditionsPage(PreDefinedActions.getDriver()).getInstance();
		termsAndConditions.clickOnElementsOfTermsandconditionsPage("cookieAccept");
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		termsAndConditions.clickOnElementsOfTermsandconditionsPage("helpAndSupport");
		sleeper(5000);
		termsAndConditions.scrollOnTermsandconditionsPage("termsAndConditions");
		sleeper(5000);
		termsAndConditions.clickOnElementsOfTermsandconditionsPage("termsAndConditions");
		termsAndConditions.switchToDifferentTab();
		waitForPageLoaded();
		final String currentUrl = termsAndConditions.getUrlOfCurrentPage();
		softAssert.assertTrue(termsAndConditions.getUrlOfCurrentPage().equals(currentUrl), "Page not redirected to Termsandconditions link page");
		termsAndConditions.scrollOnTermsandconditionsPage("softwareHrefLink");
		termsAndConditions.clickOnElementsOfTermsandconditionsPage("softwareHrefLink");
		termsAndConditions.switchToDifferentTab();
		sleeper(5000);
		termsAndConditions.switchBackToPreviousTab();
		sleeper(5000);
		termsAndConditions.scrollOnTermsandconditionsPage("privacyHrefLink");
		termsAndConditions.clickOnElementsOfTermsandconditionsPage("privacyHrefLink");
		termsAndConditions.switchToDifferentTab();
		sleeper(5000);
		termsAndConditions.switchBackToPreviousTab();
		softAssert.assertAll();
	}
	
	@Test(priority = 3, groups = {"REGRESSION_PLATFORMCX"}, description ="TestCaseID:C44673099")
	public final void verifyDirectlinkOfTermsandconditionsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WorkforceExperienceTermsAndConditionsPage termsAndConditions = new WorkforceExperienceTermsAndConditionsPage(PreDefinedActions.getDriver()).getInstance();
		getUrl( SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.TERMS_AND_CONDITIONS + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")));
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("wexLogo"), "Hp logo is not present on Terms and conditions page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("printButton"), "Print button is not present on Terms and condition page");
		softAssert.assertTrue(termsAndConditions.verifyElementsOfTermsandconditionsPage("langaugeDrodown"), "Language is not present on Terms and condition page");
		softAssert.assertAll();	
		
	}
}

