package com.testscripts.daasui;

import java.util.List;

import com.daasui.constants.CommonVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.pages.WEPEmptyDashboardPage;

public class WEPEmptyDashboardTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEPEmptyDashboardTest.class);

	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C48459283")
	public final void verifyElementsOfEmptyDashboardPage() throws Exception {

		SoftAssert sa = new SoftAssert();
		WEPEmptyDashboardPage WEPEmptyDashboardPage = new WEPEmptyDashboardPage(PreDefinedActions.getDriver())
				.getInstance();
		WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("cookieAccept");
		LOGGER.info("Landing page loaded");
		login("COMPANY_EMAIL_WEP", "COMPANY_PASSWORD_WEP");
		waitForPageLoaded();
		LOGGER.info("Home page loaded");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("Resoucrecenterbutton"),
				"Resourece cnter button is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("notificationButton"),
				"notification Button is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("profileIcon"),
				"profile Icon is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("welcomeHeader"),
				"Welcome Header is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("headerBelowText"),
				"Header Below Text is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("guidedTourButton"),
				"Guided Tour Button is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("setupHeaderLabel"),
				"Setup Header Label is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("addDevicesCardHeader"),
				"Add Devices Card Header is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("addDevicesCardPara"),
				"Add Devices Card Para is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("addDevicesCardButton"),
				"Add Devices Card Button is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("addUserCardHeader"),
				"Add User Card Header is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("addUserCardPara"),
				"Add User Card Para is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("addUserCardButton"),
				"Add User Card Button is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("completeCompanyProfileCardHeader"),
				"Complete Company Profile Card Header is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("completeCompanyProfileCardPara"),
				"Complete Company Profile Card Para is not present on Home page");
		if (WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("completeCompanyProfileCardButton")) {
			sa.assertTrue(true, "Complete Company Profile Card Button is present");
		}
		else {sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("RevisitProfilebutton"),
					"Neither Complete nor Revisit Company Profile button is present on Home page");
		}
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("integrationCardHeader"),
				"Integration Card Header is not present on Home page");
		sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("integrationCardPara"),
				"Integration Card Para is not present on Home page");
		if (WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("integrationCardButton")) {
			sa.assertTrue(true, "Integration Card Button is present");
		}
		else {sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("RevisitIntegrationbutton"),
					"Neither Integrate nor Revisit Integration button is present on Home page");
		}
		sa.assertAll();
		
		LOGGER.info("Validation for empty dashboard text completed");
	}

	@Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C48459283")
	public final void verifyTextofElementsOfEmptyDashboardPage() throws Exception {

		SoftAssert sa = new SoftAssert();
		WEPEmptyDashboardPage WEPEmptyDashboardPage = new WEPEmptyDashboardPage(PreDefinedActions.getDriver())
				.getInstance();
		WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("cookieAccept");
		LOGGER.info("Landing page loaded");
		login("COMPANY_EMAIL_WEP", "COMPANY_PASSWORD_WEP");
		waitForPageLoaded();
		LOGGER.info("Home page loaded");
		sa.assertTrue(
				WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("welcomeHeader")
						.equals(WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui",
								"wex.onboarding.dashboard.welcometext")),
				"Welcome Header text is not matched on Home page");
		sa.assertTrue(
				WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("headerBelowText")
						.equals(WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui",
								"wex.onboarding.dashboard.desc")),
				"Header Below Text text is not matched on Home page");
		sa.assertTrue(
				WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("guidedTourButton")
						.equals(WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui",
								"wex.onboarding.dashboard.guided.tourBtn")),
				"Guided Tour Button text is not matched on Home page");
		sa.assertTrue(
				WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("setupHeaderLabel")
						.equals(WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui",
								"wex.onboarding.ecosystemSuccess")),
				"Setup Header Label text is not matched on Home page");
		/*sa.assertTrue(
				WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("addDevicesCardHeader")
						.equals(WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui",
								"wex.onboarding.dashboard.addDevice.title")),
				"Add Devices Card Header text is not matched on Home page");*/
		sa.assertTrue(
				WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("addDevicesCardPara")
						.equals(WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui",
								"wex.onboarding.dashboard.addDevice.desc")),
				"Add Devices Card Para text is not matched on Home page");
		sa.assertTrue(
				WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("addUserCardHeader")
						.equals(WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui",
								"wex.onboarding.dashboard.addUser.title")),
				"Add User Card Header text is not matched on Home page");
		sa.assertTrue(
				WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("addUserCardPara")
						.equals(WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui",
								"wex.onboarding.dashboard.addUser.desc")),
				"Add User Card Para text is not matched on Home page");
		String actualProfileHeader = WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("completeCompanyProfileCardHeader");
		String actualProfileDesc   = WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("completeCompanyProfileCardPara");
		String expectedHeaderNotCompleted = WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.complete.profile");
		String expectedDescNotCompleted = WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.completeProfile.desc");
		String expectedHeaderCompleted = WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.completeProfile.completedTitle");
		String expectedDescCompleted = WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.completeProfile.completedDesc");
		if (actualProfileHeader.equals(expectedHeaderNotCompleted)) {
			// NOT COMPLETED STATE
			sa.assertTrue(actualProfileDesc.equals(expectedDescNotCompleted), "Complete Profile description text is not matched for NOT COMPLETED state");
		}
		else {
			// COMPLETED STATE
			sa.assertTrue(actualProfileHeader.equals(expectedHeaderCompleted), "Complete Profile header text is not matched for COMPLETED state");
			sa.assertTrue(actualProfileDesc.equals(expectedDescCompleted), "Complete Profile description text is not matched for COMPLETED state");
		}
		String actualIntegrationHeader = WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("integrationCardHeader");
		String actualIntegrationDesc   = WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("integrationCardPara");
		String expectedIntegrationHeaderNotCompleted = WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.integrations.title");
		String expectedIntegrationDescNotCompleted = WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.integrations.desc");
		String expectedIntegrationHeaderCompleted = WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.integrations.completedTitle");
		String expectedIntegrationDescCompleted = WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.integrations.completedDesc");
		if (actualIntegrationHeader.equals(expectedIntegrationHeaderNotCompleted)) {
			// NOT COMPLETED STATE
			sa.assertTrue(actualIntegrationDesc.equals(expectedIntegrationDescNotCompleted), "Integration description text is not matched for NOT COMPLETED state");
		}
		else {
			// COMPLETED STATE
			sa.assertTrue(actualIntegrationHeader.equals(expectedIntegrationHeaderCompleted), "Integration header text is not matched for COMPLETED state");
			sa.assertTrue(actualIntegrationDesc.equals(expectedIntegrationDescCompleted), "Integration description text is not matched for COMPLETED state");
		}
		sa.assertAll();
		
		LOGGER.info("Validation for empty dashboard text completed");
	}

	@Test(priority = 3, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C48459282")
	public final void verifyCoachmarkOfElementsOfEmptyDashboardPage() throws Exception {

		SoftAssert sa = new SoftAssert();
		WEPEmptyDashboardPage WEPEmptyDashboardPage = new WEPEmptyDashboardPage(PreDefinedActions.getDriver())
				.getInstance();
		WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("cookieAccept");
		LOGGER.info("Landing page loaded");
		login("COMPANY_EMAIL_WEP", "COMPANY_PASSWORD_WEP");
		waitForPageLoaded();
		LOGGER.info("Home page loaded");

		WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("guidedTourButton");
        //WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("taketourbutton");

		if (!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "COMPANY_EMAIL_WEP"))) {
			//Alerts Coachmark Validation
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("AlertTtile"),
					"Coachmark Header is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("AlertMessage"),
					"Coachmark Message is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkCloseButton"),
					"Coachmark Close Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkNextButton"),
					"Coachmark Next Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("AlertPegination"),
					"Coachmark Page Number is not present on Home page");
			sa.assertTrue(
					WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("AlertMessage").equals(
							WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.alerts.desc")),
					"Coachmark Message text Alerts is not matched on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("AlertPegination").equals("1 of 5"),
					"Coachmark Page Number text is not matched on Home page");
			WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("coachmarkNextButton");
			//Fleet Analytics Coachmark Validation
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkHeader"),
					"Coachmark Header is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkMessage"),
					"Coachmark Message is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkCloseButton"),
					"Coachmark Close Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkNextButton"),
					"Coachmark Next Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("FleetAnalyticsPagination"),
					"Coachmark Page Number is not present on Home page");
			sa.assertTrue(
					WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("coachmarkMessage").equals(
							WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.analytics.desc")),
					"Coachmark Message text Analytics is not matched on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("FleetAnalyticsPagination").equals("2 of 5"),
					"Coachmark Page Number text is not matched on Home page");
			WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("coachmarkNextButton");
			//Remediation Coachmark Validation
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkPreviousButton"),
					"Coachmark Previous Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("RemediationTitle"),
					"Coachmark Header is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("RemediationMessage"),
					"Coachmark Message is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkCloseButton"),
					"Coachmark Close Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("RemediationPegination"),
					"Coachmark Page Number is not present on Home page");
			sa.assertTrue(
					WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("RemediationMessage").equals(
							WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.remediations.desc")),
					"Coachmark Message text for Remediation is not matched on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("RemediationPegination").equals("3 of 5"),
					"Coachmark Page Number text is not matched on Home page");
			WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("coachmarkNextButton");
			//Pulses Coachmark Validation
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkPreviousButton"),
					"Coachmark Previous Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("PulseTitle"),
					"Coachmark Header is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("PulseMessage"),
					"Coachmark Message is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkCloseButton"),
					"Coachmark Close Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("PulsePegination"),
					"Coachmark Page Number is not present on Home page");
			sa.assertTrue(
					WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("PulseMessage").equals(
							WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.ee.desc")),
					"Coachmark text Pulses is not matched on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("PulsePegination").equals("4 of 5"),
					"Coachmark Page Number text is not matched on Home page");
			WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("coachmarkNextButton");
			//Onboarding Tasks Coachmark Validation
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkPreviousButton"),
					"Coachmark Previous Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("onboardingtitle"),
					"Coachmark Header is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("onboardingmessage"),
					"Coachmark Message is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkCloseButton"),
					"Coachmark Close Button is not present on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("onboardingpegination"),
					"Coachmark Page Number is not present on Home page");
			sa.assertTrue(
					WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("onboardingtitle").equals(
							WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.onboarding.title")),
					"Coachmark Header text is not matched on Home page");
			sa.assertTrue(
					WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("onboardingmessage").equals(
							WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.onboarding.desc")),
					"Coachmark Message text is not matched on Home page");
			sa.assertTrue(WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("onboardingpegination").equals("5 of 5"),
					"Coachmark Page Number text is not matched on Home page");
			WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("coachmarkCloseButton");
			sa.assertAll();

			LOGGER.info("Validation guided tour is completed");
		}
		else{
				sa.assertTrue(
						WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("coachmarkHeader").equals(
								WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.analytics.title")),
						"Coachmark Message title text for analytics is not matched on Home page");
				sa.assertTrue(
						WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("coachmarkMessage").equals(
								WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.analytics.desc")),
						"Coachmark Message text is not matched on Home page");
				sa.assertTrue(WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("fleetanaticspegination").equals("1 of 3"),
						"Coachmark Page Number text is not matched on Home page");
				WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkNextButton");
				WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("coachmarkNextButton");
			sleeper(2000);
				sa.assertTrue(
						WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("RemediationTitle").equals(
							WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.remediations.title")),
					"Coachmark Message title text for remediations is not matched on Home page");
				sa.assertTrue(
					WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("RemediationMessage").equals(
							WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.remediations.desc")),
					"Coachmark Message title desc text for remediations is not matched on Home page");
				sa.assertTrue(WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("RemediationPegination").equals("2 of 3"),
					"Coachmark Page Number text is not matched on Home page");
				WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkNextButton");
				WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("coachmarkNextButton");
				sleeper(2000);
				sa.assertTrue(
						WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("onboardingtitle").equals(
								WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.onboarding.title")),
						"Coachmark Message title text for remediations is not matched on Home page");
				sa.assertTrue(
						WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("onboardingmessage").equals(
								WEPEmptyDashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.onboarding.desc")),
						"Coachmark Message description text for remediations is not matched on Home page");
				sa.assertTrue(WEPEmptyDashboardPage.getTextOfWEPEmptyDashboardPage("onboardingpegination").equals("3 of 3"),
						"Coachmark Page Number text is not matched on Home page");
				WEPEmptyDashboardPage.verifyElementsOfWEPEmptyDashboardPage("coachmarkPreviousButton");
				WEPEmptyDashboardPage.clickOnElementsOfWEPEmptyDashboardPage("coachmarkCloseButton");
			sa.assertAll();
			LOGGER.info("Validation new guided tour is completed");
		}
	}

}
