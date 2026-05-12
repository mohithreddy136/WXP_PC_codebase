package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WorkforceExperienceServiceLevelAgreementPage;

public class WorkforceExperienceServiceLevelAgreementTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WorkforceExperienceServiceLevelAgreementTest.class);
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_WEP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		return data;
	}

	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42806827")
	public final void verifySLAPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WorkforceExperienceServiceLevelAgreementPage slaPage = new WorkforceExperienceServiceLevelAgreementPage(PreDefinedActions.getDriver()).getInstance();
		slaPage.clickOnElementsOfServiceLevelAgreement("cookieAccept");
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		slaPage.clickOnElementsOfServiceLevelAgreement("helpAndSupport");
		sleeper(5000);
		slaPage.scrollOnServiceLevelAgreementPage("slaLink");
		sleeper(5000);
		slaPage.clickOnElementsOfServiceLevelAgreement("slaLink");
		slaPage.switchToDifferentTab();
		waitForPageLoaded();
		String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.SLA + "/" + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		softAssert.assertTrue(url.equals(slaPage.getUrlOfCurrentPage()), "Service Level Agreement Page URL not matching");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("wexLogo"), "Hp logo is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("printButton"), "print button is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementIsClickableOfServiceLevelAgreementPage("printButton"), "Print Button is not clickable on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("profieIcon"), "Profile icon is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("serviceHeader"), "service headeris not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("lastUpdated"), "Last updated header is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("lastUpdatedBelowPara"), "Last Updated Below Para is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("definationsHeader"), "Defination header is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("definationHeaderBelowPara"), "Defination Header Below Para is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("serviceCommitment"), "service header is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("serviceCommitmentBelowPara1"), "Service Commitment Below Para1 is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("serviceCommitmentBelowPara2"), "Service Commitment Below Para2 is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("exclusionsHeader"), "Exclusion header is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("exclusionsHeaderBelowPara1"), "Exclusions Header Below Para1 is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("exclusionsHeaderBelowPara2"), "Exclusions Header Below Para2 is not present on SLA page");
		softAssert.assertAll();
	}
	
	@Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID :C44672836")
	public final void verifyDirectLinkOfSLAPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WorkforceExperienceServiceLevelAgreementPage slaPage = new WorkforceExperienceServiceLevelAgreementPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.SLA + "/" + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")));
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("wexLogo"), "Hp logo is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("printButton"), "print button is not present on SLA page");
		softAssert.assertTrue(slaPage.verifyElementsOfServiceLevelAgreementPage("langaugeDrodown"), "Language is not present on SLA page");
		softAssert.assertAll();
			
	}

}
