package com.testscripts.daasui;

import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ScriptVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.pages.OnboardingChecklistFlowPage;

public class WEPOnboardingChecklistFlowTest extends CommonTest {
private static Logger LOGGER = LogManager.getLogger(OnboardingChecklistFlowPage.class);
	
	
	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C48459289")
	   public final void verifyOnboardingFirstStepsChecklistflow() throws Exception {
			
		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("Navigating to the onboarding checklist");
			
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("onboardingchecklistheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.firstStepsCompletedTitle")), "First checkilst title is not correct");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("firstsectiontitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.sectionTitle.firstSteps")), "First steps title is incorrect");
			LOGGER.info("Validated the title");

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("letsaddfirstdevice", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("letsaddfirstdevicecheckmark", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("letsadddevicesclick");
			waitForPageLoaded();
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Coachmarkheaderadddevices", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkstextadddevices", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarktextnextclickadddevices", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

			LOGGER.info("validation completed for first add devices journey");
		}
		else {
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("onboardingchecklistheader");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("firstsectiontitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.sectionTitle.firstSteps")), "First steps title is incorrect");
			LOGGER.info("Validated the title");

			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("letsaddfirstdevice");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("letsadddevicesclick");
			waitForPageLoaded();

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("adddevicescoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.devices.title")), "Add device coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("adddevicescoachmarksdescription", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.add.devices.navigate")), "Add device coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("adddevicescoachmarksnextstepdescription");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pcpegination").equals("1 of 4"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("devicesextensionbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("pcsclick");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("adddevicescoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.devices.title")), "Add new device coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("addnewdevicescoachmarksdescription", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.add.devices.desc")), "Add new device coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("addnewdevicescoachmarksnextstepdescription");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("adddevicepegination").equals("2 of 4"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("addbuttonclick");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("adddevicescoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.devices.title")), "Add new device coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("adddevicestelemetrycoachmarksdescription", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.add.devices.SlectConnectionType")), "Add new device telemetry coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("adddevicestelemetrycoachmarksnextstepdescription");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("telemetrypegination").equals("3 of 4"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("telemetrycardclick");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("adddevicescoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.devices.title")), "Add new device coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("telemetricdescription", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.add.devices.AddOption")), "Add new device telemetry coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("agentpegination").equals("4 of 4"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			softAssert.assertAll();
			LOGGER.info("validation completed for first add devices new onboarding journey");
		}
			
	}
	
	@Test(priority = 2, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C48459289")
	   public final void verifyOnboardingInviteYourTeamChecklistflow() throws Exception {
			
		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("Navigating to the onboarding checklist");
			
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("inviteyouteam", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			if (OnboardingChecklistFlowPage.waitForElementsOfWEXOnboardingChecklistFlowPage("inviteyourteamcheckmark")) {
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("inviteyourteamcheckmark", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.sectiontitle")), "Need assistance with WEX tile is incorrect");
			}
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("inviteuserclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkheaderadduser", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarktextadduser", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("Adduserclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkheaderadduserbutton", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarktextadduserbutton", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.letaddfirstdevice")), "Need assistance with WEX tile is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

			LOGGER.info("validation completed for invite you tean journey");
		}
		else{
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("inviteyouteam");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("inviteuserclick");

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("inviteuserheadercoachmark", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.addUser.title")), "invite team user coachmarks header is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("inviteuserdescription");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("inviteuserdescriptionstep");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("Accountmanagementpegination").equals("1 of 3"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("accountmanagementtab");

			sleeper(2000);
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("inviteuserheadercoachmark", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.addUser.title")), "invite team user coachmarks header is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("addinviteuserdescription");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("userlistpegination").equals("2 of 3"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("Adduserclick");

			sleeper(2000);
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("inviteuserheadercoachmark", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.addUser.title")), "invite team user button coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("addinviteuserbuttondescription", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.account.users")), "invite team user button coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("addinviteuserbuttonnextdescription");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("adduserpegination").equals("3 of 3"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			softAssert.assertAll();
			LOGGER.info("validation completed for new invite you team journey");

		}
	}
	
	@Test(priority = 3, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C48459289")
	   public final void verifyOnboardingCompanyProfileChecklistflow() throws Exception {
			
		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("Navigating to the onboarding checklist");
			
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("completeCompanyProfile", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CompanyProfile")), "CompanyProfile text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("completeCompanyProfilecheckmark", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CompanyProfile")), "CompanyProfile text is incorrect");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("companyprofileclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkheaderaccountmanagement", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CompanyProfile")), "CompanyProfile text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarktextaccountmanagement", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CompanyProfile")), "CompanyProfile text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarknextclickaccountmanagement", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CompanyProfile")), "CompanyProfile text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			sleeper(1000);
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("accountmanagementtab");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkheadercompanyprofile", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CompanyProfile")), "CompanyProfile text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarktextcompanyprofile", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CompanyProfile")), "CompanyProfile text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			LOGGER.info("validation completed for company profile journey journey");
		}
		else {
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("completeCompanyProfile");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("companyprofileclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("companyprofileheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.complete.profile")), "Complete your profile coachmarks header is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("companyprofiledescription");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			softAssert.assertAll();

			LOGGER.info("validation completed for complete your profile journey");
		}
			
	}
	
	@Test(priority = 4, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C48459289")
	   public final void verifyOnboardingSetupIntegrationChecklistflow() throws Exception {
			
		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("Navigating to the onboarding checklist");
			
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
				OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("setupintergationclick");
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkheaderintegration", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.SetupIntegration")), "SetupIntegration text is incorrect");
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkstextintegration", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.SetupIntegration")), "SetupIntegration text is incorrect");
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksnextintegration", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.SetupIntegration")), "SetupIntegration text is incorrect");
				OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
				OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("setupintegrationtab");
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksheaderintegrationpage", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.SetupIntegration")), "SetupIntegration text is incorrect");
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkstextintegrationpage", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.SetupIntegration")), "SetupIntegration text is incorrect");
				OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
				OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

				LOGGER.info("Completed completed for setup integration journey");
			}

		else {
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("setupIntegrations");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("setupintergationclick");
            sleeper(2000);
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("integrationcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.integrations.title")), "explorer integration coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("integrationcoachmarksdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.integrations.desc1")), "explorer integration coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("integrationcoachmarksdesc2");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("setupintegrationpegination").equals("1 of 2"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("setupintegrationtab");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("integrationcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.integrations.title")), "explorer integration coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("integrationsecondcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.integration.desc")), "explorer integration second coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("browsepegintaion").equals("2 of 2"), "Coachmark Page Number text is not matched on Home page");
			sleeper(2000);
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			softAssert.assertAll();

			LOGGER.info("validation completed for explorer integration journey");
		}

	}
	
	@Test(priority = 5, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C48459289")
	   public final void verifyOnboardingImproveworkforceExperienceChecklistflow() throws Exception {
			
		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("Navigating to the onboarding checklist");
			
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");
			
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("discovermoretitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.sectionTitle.discoverMore")), "Discover more title text is incorrect");

			if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("improveExpScore", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.ImproveworkforceExperience")), "Improve workforce Experience text is incorrect");
			if (OnboardingChecklistFlowPage.waitForElementsOfWEXOnboardingChecklistFlowPage("improveExpScorecheckmark")) {
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("improveExpScorecheckmark", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.ImproveworkforceExperience")), "Improve workforce Experience text is incorrect");
			}
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("imporveintegrationclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkheadersystemhealth", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.ImproveworkforceExperience")), "Improve workforce Experience text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkstextsystemhealth", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.ImproveworkforceExperience")), "Improve workforce Experience text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksnextsystemhealth", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.ImproveworkforceExperience")), "Improve workforce Experience text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("systemhealthclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksheadersystemhealthscore", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.ImproveworkforceExperience")), "Improve workforce Experience text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkstextsystemhealthscore", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.ImproveworkforceExperience")), "Improve workforce Experience text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

			LOGGER.info("validation Completed for imporve the workforce experience score journey");
		}
		else {

				OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("improveExpScore");
				OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("improveexperiencescoreclick");
				sleeper(2000);

				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("improverexpcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.expScore.title")), "improve experience coachmarks header is incorrect");
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("improveexpcoachmarksdesc1", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.expScoreDesc")), "improve experience coachmarks description is incorrect");
				OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("improvenextstepcoachmarks");
				softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("improvescroepegination").equals("1 of 3"), "Coachmark Page Number text is not matched on Home page");
				OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("systemhealthclick");
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("improverexpcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.expScore.title")), "improve experience coachmarks header is incorrect");
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("systeamhealthdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.systemHealthDesc")), "system health coachmarks description is incorrect");
				OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("systemhealthnextstepcoachmarks");
				softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("systemhealythpegination").equals("2 of 3"), "Coachmark Page Number text is not matched on Home page");
				sleeper(2000);
				OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
				OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
				if(OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("recomendedactionlist")) {
					sleeper(2000);
					softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("improverexpcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.expScore.title")), "improve experience coachmarks header is incorrect");
					softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("recomendedactiondesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.expScore.ra")), "Recommended actions description is incorrect");
					OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("recomendedactiondescnextstep");
					softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("recomendedactionpegination").equals("3 of 3"), "Coachmark Page Number text is not matched on Home page");
					OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
					OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
				}
//				else {
//					softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("improverexpcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.expScore.title")), "improve experience coachmarks header is incorrect");
//					softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("recomendedactiondesc", getTextLanguage(LanguageCode, "daas_ui", "")), "system health coachmarks description is incorrect");
//					OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("recomendedactiondescnextstep");
//					softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("Coachmarkpagenumber").equals("3 of 3"), "Coachmark Page Number text is not matched on Home page");
//				}

				softAssert.assertAll();
				LOGGER.info("validation Completed for imporve the workforce experience score journey");
		}
			
	}


	@Test(priority = 6, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C48459289")
	   public final void verifyOnboardingCreateCustomPulsetChecklistflow() throws Exception {
			
		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("Redirected to onboarding checklist");
			
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("createCustomPulse");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("createCustomPulse", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			if (OnboardingChecklistFlowPage.waitForElementsOfWEXOnboardingChecklistFlowPage("createCustomPulsecheckmark")) {
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("createCustomPulsecheckmark", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			}
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("createcustomepulseclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkfmtitle", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksfmtext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("employeeengagementtab");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkspulseheader", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksoulsetext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("pulseclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkscreatbuttontitle", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkscreatebuttontext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("createbuttonpulseclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkscustompulsetitle", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkscustomerpulsetext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

			LOGGER.info("validation completeded for create custom pulse journey");
		}
		else {
			OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("createCustomPulse");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("createCustomPulse");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("createcustomepulseclick");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("customepulsecoachmarkheader", getTextLanguage(LanguageCode, "daas_ui", "ee_page_title_custom")), "custom pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("customerpulsefirstcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.emp.dashboard.desc")), "custom pulse first coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("customerpulsenextstep");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulselistpegination").equals("1 of 7"), "Coachmark Page Number text is not matched on Home page");
			sleeper(2000);
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("customepulsecoachmarkheader", getTextLanguage(LanguageCode, "daas_ui", "ee_page_title_custom")), "custom pulse coachmarks header is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("createbuttoncoachmarksdesc");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("createpulsepegination").equals("2 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("createbuttonpulseclick");
			sleeper(2000);
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("customepulsecoachmarkheader", getTextLanguage(LanguageCode, "daas_ui", "ee_page_title_custom")), "custom pulse coachmarks header is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("createbuttoncoachmarknextstep");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("createcustomepulsepegination").equals("3 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("custompulseclick");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("custompulseclick");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("customepulsecoachmarkheader", getTextLanguage(LanguageCode, "daas_ui", "ee_page_title_custom")), "custom pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("pulsecontentdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.custom.add.content")), "custom pulse content coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulsecontentpegintaion").equals("4 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("customepulsecoachmarkheader", getTextLanguage(LanguageCode, "daas_ui", "ee_page_title_custom")), "custom pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("pulseschduledesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.custom.add.schedule")), "custom pulse schdule coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulsescedulepegination").equals("5 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("customepulsecoachmarkheader", getTextLanguage(LanguageCode, "daas_ui", "ee_page_title_custom")), "custom pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("pulseaudiencedesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.custom.add.audience")), "custom pulse audience coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("pulsaudiencecochmarkslist");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulseaudiencepegination").equals("6 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarkspreviousbutton");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("customepulsecoachmarkheader", getTextLanguage(LanguageCode, "daas_ui", "ee_page_title_custom")), "custom pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("pulsepublichbutton", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.custom.add.publish")), "custom pulse publilsh coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulsepublishpegination").equals("7 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarkspreviousbutton");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
		//	OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			sleeper(2000);

			LOGGER.info("validation completed for new create custom pulse journey");

		}
			
	}
	
	@Test(priority = 7, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C48459289")
	   public final void verifyOnboardingCreateSetimentPulsetChecklistflow() throws Exception {
			
		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("Redirected to onboarding checklist");
			
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");
		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("createSentimentPulse");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("createSentimentPulse", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.Sentimentpulse")), "sentiment pulse text is incorrect");
			if (OnboardingChecklistFlowPage.waitForElementsOfWEXOnboardingChecklistFlowPage("createSentimentPulsecheckmark")) {
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("createSentimentPulsecheckmark", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.Sentimentpulse")), "sentiment pulse text is incorrect");
			}
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("cretesentimentpulseclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkfmtitle", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksfmtext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("employeeengagementtab");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkspulseheader", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksoulsetext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("pulseclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkscreatbuttontitle", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkscreatebuttontext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.CustomPulse")), "Custom Pulse text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("createbuttonpulseclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkssentimentpulsetitle", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.Sentimentpulse")), "sentiment pulse text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkssentimentpulsetext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.Sentimentpulse")), "sentiment pulse text is incorrect");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

			LOGGER.info("validation Completed for create sentiment pulse journey");
		}
		else {
			OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("createSentimentPulse");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("createSentimentPulse");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("cretesentimentpulseclick");

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Sentimentcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "ee_dashboard_creatPulseText")), "Sentiment pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("sentimentfirstcoachmarksdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.sentiment.desc")), "sentiment pulse first coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("customerpulsenextstep");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulselistpegination").equals("1 of 7"), "Coachmark Page Number text is not matched on Home page");
			sleeper(2000);
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Sentimentcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "ee_dashboard_creatPulseText")), "Sentiment pulse coachmarks header is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("createbuttondesc");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("createpulsepegination").equals("2 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("createbuttonpulseclick");
			sleeper(2000);
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Sentimentcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "ee_dashboard_creatPulseText")), "Sentiment pulse coachmarks header is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("sentimentpulsecoachmarknextstep");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("sentimentpulsepegination").equals("3 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("sentimentpulseclick");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("sentimentpulseclick");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Sentimentcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "ee_dashboard_creatPulseText")), "Sentiment pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("sentimentpulsecontentdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.sentiment.content")), "sentiment pulse content coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulsecontentpegintaion").equals("4 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("sentimentpulsenextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("sentimentpulsenextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Sentimentcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "ee_dashboard_creatPulseText")), "Sentiment pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("sentimentpulseschduledesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.sentiment.schedule")), "sentiment pulse schdule coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulsescedulepegination").equals("5 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("sentimentpulsenextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("sentimentpulsenextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Sentimentcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "ee_dashboard_creatPulseText")), "Sentiment pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("sentimentpulseaudiencedesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.custom.add.audience")), "sentiment pulse audience coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("pulsaudiencecochmarkslist");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulseaudiencepegination").equals("6 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("sentimentpulsepreviousbutton");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("sentimentpulsenextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("sentimentpulsenextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Sentimentcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "ee_dashboard_creatPulseText")), "Sentiment pulse coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("sentimentpulsepublishbutton", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.pulse.custom.add.publish")), "sentiment pulse publilsh coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("pulsepublishpegination").equals("7 of 7"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("sentimentpulsepreviousbutton");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

			softAssert.assertAll();
			LOGGER.info("validation completed for new create sentiment pulse journey");


		}
	}
	
	
	@Test(priority = 8, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C42273810")
	   public final void verifyOnboardingHelpAndSupportChecklistflow() throws Exception {
			
		    login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			leftSideMenuVerification();
			waitForPageLoaded();
			LOGGER.info("Redirected to onboarding checklist");
			
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
			OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");

		if(!toggleVerification(CommonVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("checklist_item_getHelp");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("checklist_item_getHelp", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.helpandsupport")), "Help and support text is incorrect");
			if (OnboardingChecklistFlowPage.waitForElementsOfWEXOnboardingChecklistFlowPage("checklist_item_getHelpcheckmark")) {
				softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("checklist_item_getHelpcheckmark", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.helpandsupport")), "Help and support text is incorrect");
			}
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("helpandsupportclick");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkfeedbacktitle", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.helpandsupport")), "Help and support text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarksfeedbacktext", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.helpandsupport")), "NHelp and support text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkhelpandsupporttitleonappbar", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.helpandsupport")), "Help and support text is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("coachmarkhelpandsupportdescriptiononappbar", getTextLanguage(LanguageCode, "daas_ui", "onboardingchecklist.helpandsupport")), "Help and support text is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

			LOGGER.info("validation Completed for help and support journey");
		}
		else {
			OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("checklist_item_getHelp");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("gethelpsupportonbardingtitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.get.help")), "Help and support text is incorrect");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("helpandsupportclick");

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("getsupportcoachmarkheader1", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.get.help")), "Help and support coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("helpandsupportfirstcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.support.desc")), "Help and support coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("helpandsupportfirstcoachmarknextstep");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("helpandsupportpegination").equals("1 of 2"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("getsupportcoachmarkheader2", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.get.help")), "Help and support coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("helpandsupportcoachmarksdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.provide.feedback.desc")), "Help and support provide coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("supportpegination").equals("2 of 2"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarkspreviousbutton");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("closecoachmarkhelpandsupport");
			sleeper(2000);
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("closecoachmarkhelpandsupport");

			softAssert.assertAll();
			LOGGER.info("validation Completed for new help and support journey");

		}
	}

	@Test(priority = 8, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C42273810")
	public final void verifyOnboardingStayUpToDateAlertsChecklistflow() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		waitForPageLoaded();
		LOGGER.info("Redirected to onboarding checklist");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
		OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");

		if(OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("noalertstile")){
		OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("stayuptodatealertsonboarding");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("stayuptodatealertsonboardingtitle");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("stayuptodatealertsonboarding");

		}
		else if(OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Recommendedactiontile")) {

			OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("stayuptodatealertsonboarding");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("stayuptodatealertsonboardingtitle");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("stayuptodatealertsonboarding");

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("stayuptodatetitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.alert.title")), "Stay up to date alerts coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("stayuptodatecoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.alerts.viewAlerts")), "Stay up to date alerts coachmarks description is incorrect");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("stayuptodatecoachmarknextstep");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("Coachmarkpagenumber").equals("1 of 3"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("alertstabexpandbutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("alertstabexpandbutton");
			sleeper(2000);

			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("activealertslink");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("activealertslink");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("stayuptodatetitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.alert.title")), "Stay up to date alerts coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("stayuptodatealertselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.alerts.active.desc")), "Stay up to date alerts select coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("stayuptodatealertselectcoachmarknextstep", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.alerts.active.click")), "Stay up to date alerts click coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("Coachmarkpagenumber").equals("2 of 3"), "Coachmark Page Number text is not matched on Home page");
			sleeper(2000);

			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("alertsclick");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("alertsclick");
			sleeper(2000);

			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("stayuptodatetitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.alert.title")), "Stay up to date alerts coachmarks header is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("alertslaststepcoachmarksdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.alertsDetails.desc")), "Stay up to date alerts details coachmarks description is incorrect");
			softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("Coachmarkpagenumber").equals("3 of 3"), "Coachmark Page Number text is not matched on Home page");
			OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
			OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

			softAssert.assertAll();

			LOGGER.info("validation Completed for stay up to date alerts journey with alerts are present ");



		}


	}

	@Test(priority = 8, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C42273810")
	public final void verifyOnboardingAddAScriptChecklistflow() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String scriptName = ScriptVariables.SCRIPT_NAME + System.currentTimeMillis();
		OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		waitForPageLoaded();
		LOGGER.info("Redirected to onboarding checklist");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
		OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");

		sleeper(2000);
		OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("Addscriptonboardongtitle");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Addscriptonboardongtitle");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("onboardingchecklistaddscriptclick");

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Addscriptcoachmarksheader", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.addScript")), "Add a script coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("Addscriptselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.addScript.script")), "Add a script coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Addscriptselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("scriptpegination").equals("1 of 8"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("remediationstoggle");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("remediationstoggle");
		sleeper(2000);

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("remediationclick");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("remediationclick");
		sleeper(2000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("addscripttitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.addScript")), "Add a script coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("addscriptbuttonselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.addScript.add")), "Add a script button coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("addscriptbuttonselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("addpegintaion").equals("2 of 8"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("addscriptsbutton");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("addscriptsbutton");
		sleeper(2000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("filuploadtitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.addScript")), "Add a script coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("PS1fileuploadselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.addScript.upload")), "Add a script upload coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("PS1fileuploadselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("uploadpegination").equals("3 of 8"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.importScriptFile(ConstantPath.IMPORT_PATH+ ScriptVariables.SAMPLESCRIPT_UPLOAD);
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("nextButtonaddscript");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("nextButtonaddscript");
		sleeper(2000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("proptitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.addScript")), "Add a script coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("propsselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.addScript.props")), "Add a script props coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("propsselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("propspegination").equals("4 of 8"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("nextButtonaddscript");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("nextButtonaddscript");
		sleeper(2000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("infotitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.addScript")), "Add a script coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("infosselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.addScript.info")), "Add a script info coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("infosselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("infopegination").equals("5 of 8"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.enterTextOnScriptsPageOfOnboardingChecklistFlowPage("scriptNameonboarding", scriptName);
		OnboardingChecklistFlowPage.enterTextOnScriptsPageOfOnboardingChecklistFlowPage("scriptSynopsisonboarding", ScriptVariables.SCRIPT_SYNOPSIS);
		OnboardingChecklistFlowPage.enterTextOnScriptsPageOfOnboardingChecklistFlowPage("scriptDescriptiononboarding", ScriptVariables.SCRIPT_DESCRIPTION);

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Addbuttonsciptpage");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("Addbuttonsciptpage");
		sleeper(3000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("listtitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.addScript")), "Add a script coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("listsselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.addScript.list")), "Add a script list coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("listsselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("listpegination").equals("6 of 8"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("scriptdetailsclick");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("scriptdetailsclick");
		sleeper(3000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("actiontitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.addScript")), "Add a script coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("actionsselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.addScript.action")), "Add a script action coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("actionsselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("actionpagination").equals("7 of 8"), "Coachmark Page Number text is not matched on Home page");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarknextbutton");
		sleeper(3000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("activitytitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.addScript")), "Add a script coachmarks header is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("activitysselectcoachmarkdesc");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("activitypagination").equals("8 of 8"), "Coachmark Page Number text is not matched on Home page");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

		softAssert.assertAll();
		LOGGER.info("validation Completed for add a scripts journey");

	}

	@Test(priority = 9, groups = {"REGRESSION_PLATFORMCX", "PRODUCTION_PLATFORMCX"}, description = "TestCaseID:C42273810")
	public final void verifyOnboardingTroubleshootdevicesIssueChecklistflow() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String scriptName = ScriptVariables.SCRIPT_NAME + System.currentTimeMillis();
		OnboardingChecklistFlowPage OnboardingChecklistFlowPage = new OnboardingChecklistFlowPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		waitForPageLoaded();
		LOGGER.info("Redirected to onboarding checklist");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Onboardingbutton");
		OnboardingChecklistFlowPage.clickOnElementByJavaScriptOfOnboardingChecklistFlowPage("Onboardingbutton");

		sleeper(2000);
		OnboardingChecklistFlowPage.scrollTillViewWEXOnboardingChecklistFlowPage("Troubleshootdevicetitle");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("Troubleshootdevicetitle");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("troubleshootonoardingchecklist");

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("troubleshootpcstitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.deviceIssues")), "troubleshoot devices issue coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("troubleshootdeviespcssselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.troubleshootDevice.desc")), "troubleshoot devices issue coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("troubleshootdeviespcssselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("Coachmarkpagenumber").equals("1 of 5"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("devicesextensionbutton");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("pcsclick");
		sleeper(2000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("troubleserialtitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.deviceIssues")), "troubleshoot devices issue coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("troubleserialselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.troubleshootDevice.serial")), "troubleshoot devices issue serial coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("troubleserialselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("Serialpagination").equals("2 of 5"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("pcsserialnumberclick");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("pcsserialnumberclick");
		sleeper(2000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("timelinetitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.deviceIssues")), "troubleshoot devices issue coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("timelineselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.troubleshootDevice.timeline")), "troubleshoot devices issue timeline coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("timelineselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("timelinepegination").equals("3 of 5"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("timelineclick");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("timelineclick");
		sleeper(2000);


		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("healthprotectiontitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.deviceIssues")), "troubleshoot devices issue coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("healthprotectionselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.troubleshootDevice.health")), "troubleshoot devices issue health coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("healthprotectionselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("healthpagination").equals("4 of 5"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("healthclick");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("healthclick");
		sleeper(2000);

		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("softwaretitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.deviceIssues")), "troubleshoot devices issue coachmarks header is incorrect");
		softAssert.assertTrue(OnboardingChecklistFlowPage.matchTextOfOnboardingChecklistFlowPage("softwareselectcoachmarkdesc", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.troubleshootDevice.software")), "troubleshoot devices issue software coachmarks description is incorrect");
		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("softwareselectcoachmarknextstep");
		softAssert.assertTrue(OnboardingChecklistFlowPage.getTextOfOnboardingChecklistFlowPage("softwarepegination").equals("5 of 5"), "Coachmark Page Number text is not matched on Home page");

		OnboardingChecklistFlowPage.verifyElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");
		OnboardingChecklistFlowPage.clickOnElementsOfOnboardingChecklistFlowPage("coachmarksclosebutton");

		softAssert.assertAll();
		LOGGER.info("validation Completed for trubleshoot devies issue journey");

	}

}
