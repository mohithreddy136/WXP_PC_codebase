package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEXHelpAndSupportPage;

import dev.failsafe.internal.util.Assert;

public class WEXHelpAndSupportTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXHelpAndSupportTest.class);
	private static final String Email_Category_Account = "Account";
	private static final String Email_Category_Licensing = "Licensing";
	private static final String Subject_Feedback  = "Feedback & suggestions";
	String description = generateRandomString(11);
	public static final String Email_Address_for_Another_User = CommonVariables.SUPPORT_EMAIL_FOR_ANOTHER_USER;

	@Test(priority = 1, groups = { "REGRESSION_ACCOUNTS", "PRODUCTION_LDK","INITECH_SOLUTIONS" }, description = "TestCaseID:C41392702")
	public final void verifyNeedAssistanceWithWEX() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert softAssert = new SoftAssert();
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
		waitForPageLoaded();
		LOGGER.info("Redirected to help and support page");

		// Validating the section may assistance	
		softAssert.assertTrue(
				helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("NeedassistancewithWEX",
						getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.title")),
				"Need assistance with WEX tile is incorrect");
		softAssert.assertTrue(
				helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("EmailDescription",
						getTextLanguage(LanguageCode, "daas_ui", "partner.help_and_support.description")),
				" Email description is incorrect");

		LOGGER.info("Validation for the Need assistance with WEX is completed");
		softAssert.assertAll();
	}

	@Test(priority = 2, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
	public final void verifyEmailSupportForMyself() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert softAssert = new SoftAssert();
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
		waitForPageLoaded();
		LOGGER.info("Redirected to help and support page");

		helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("EmailSupporButton");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("EmailSupporButton");
		softAssert.assertTrue(
				helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("EmailSupportlabel",
						getTextLanguage(LanguageCode, "daas_ui", "new.help.title.contact.us")),
				"Email support Title is incorrect");
		softAssert.assertTrue(
				helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("EmailSupportquestion",
						getTextLanguage(LanguageCode, "daas_ui", "new.help.email.modal.change_user.label")),
				"Email label Support questtion is incorrect");
		softAssert.assertTrue(
				helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("Myselflabel",
						getTextLanguage(LanguageCode, "daas_ui","help.email.modal.user.label")),
				"Myself radio button label is incorrect");
		softAssert.assertTrue(
				helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("NeedHelpdrdopdowntext",
						getTextLanguage(LanguageCode, "daas_ui","help.support_box.selectCategory")),
				"Need help question label is incorrect");
		helpAndSupportPage.mouseHoverclickOfWEXHelpAndSupportPage(
				helpAndSupportPage.getElementOfWEXHelpAndSupportPage("emailcategorydropdown"));
		sleeper(2000);
		helpAndSupportPage.selectTextValueFromDropdownOfWEXHelpAndSupportPage("categorydropdownlistboxaccount",
                Email_Category_Account, "categorydropdown");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("emailcategorydropdown");
		softAssert.assertTrue(
				helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("emaillabel",
						getTextLanguage(LanguageCode, "daas_ui", "help.support_box.email.title")),
				"Email label is incorrect");
		softAssert.assertTrue(
				helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("Emailsubjectlabel",
						getTextLanguage(LanguageCode, "daas_ui", "help.email.modal.email_subject.label")),
				"Email subject label is incorrect");
		helpAndSupportPage.mouseHoverclickOfWEXHelpAndSupportPage(helpAndSupportPage.getElementOfWEXHelpAndSupportPage("Emailsubjecttextbox"));
		helpAndSupportPage.enterTextForWEXHelpAndSupportPage("Emailsubjecttextbox", description);
		sleeper(2000);
		helpAndSupportPage.mouseHoverclickOfWEXHelpAndSupportPage(
				helpAndSupportPage.getElementOfWEXHelpAndSupportPage("supportDropdown"));
		sleeper(2000);
		
		helpAndSupportPage.selectTextValueFromDropdownOfWEXHelpAndSupportPage("supportDropdownList",
				Subject_Feedback, "supportDropdown");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("supportDropdown");
		softAssert.assertTrue(
		helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("Emaildescriptionlabel",
				getTextLanguage(LanguageCode, "daas_ui","global.form.description")),
				"Email description labe is incorrect");
		helpAndSupportPage.enterTextForWEXHelpAndSupportPage("EmailDescriptiontextarea", description);
		helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("EmailSupportSubmitButton");
		helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("EmailSupportCancelButton");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("EmailSupportSubmitButton");
		sleeper(4000);
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("toastNotification"),
				"Toast notification is incorrect");
		LOGGER.info("Validation for myself user email support");
		softAssert.assertAll();
	}

	@Test(priority = 3, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:,C41392377")
	public final void verifyEmailSupportForAnotherUser() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert softAssert = new SoftAssert();
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
		waitForPageLoaded();
		LOGGER.info("Redirected to help and support page");

		helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("EmailSupporButton");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("EmailSupporButton");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("EmailSupportlabel"),
				"Email support Title is incorrect");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("EmailSupportquestion"),
				"Email label Support questtion is incorrect");
		helpAndSupportPage.actionClickOfWEXHelpAndSupportPage("Anotheruserradiobutton");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("Anotheruserlabel"),
				"Another user label verification is incorrect");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("NeedHelpdrdopdowntext"),
				"Need help question label is incorrect");
		helpAndSupportPage.mouseHoverclickOfWEXHelpAndSupportPage(
				helpAndSupportPage.getElementOfWEXHelpAndSupportPage("emailcategorydropdown"));
		sleeper(2000);
		helpAndSupportPage.selectTextValueFromDropdownOfWEXHelpAndSupportPage("categorydropdownlistbox",
				Email_Category_Licensing, "categorydropdown");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("emaillabel"),
				"Email label is incorrect");
		helpAndSupportPage.enterTextForWEXHelpAndSupportPage("emailtextbox", Email_Address_for_Another_User);
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("Emailsubjectlabel"),
				"Email subject label is incorrect");
		helpAndSupportPage.enterTextForWEXHelpAndSupportPage("Emailsubjecttextbox", description);
		sleeper(2000);
		helpAndSupportPage.mouseHoverclickOfWEXHelpAndSupportPage(
				helpAndSupportPage.getElementOfWEXHelpAndSupportPage("supportDropdown"));
		sleeper(2000);
		helpAndSupportPage.selectTextValueFromDropdownOfWEXHelpAndSupportPage("supportDropdownList",
				Subject_Feedback, "supportDropdown");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("supportDropdown");
		sleeper(2000);
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("Emaildescriptionlabel"),
				"Email description labe is incorrect");
		helpAndSupportPage.enterTextForWEXHelpAndSupportPage("EmailDescriptiontextarea", description);
		helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("EmailSupportSubmitButton");
		helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("EmailSupportCancelButton");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("EmailSupportSubmitButton");
		sleeper(4000);
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("toastNotification"),
				"Toast notification is incorrect");
		softAssert.assertAll();
		LOGGER.info("Validation for another user email support");
	}

	@Test(priority = 4, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:C42806818")
	public final void verifyLookingForSomethingElseSectionWEX() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert softAssert = new SoftAssert();
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
		waitForPageLoaded();
		LOGGER.info("Redirected to help and support page");

		// Validation for the getting started
		
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("GettingStartedLabel"),
				"Getting Started Title is incorrect");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("GettingStartedLabel");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		softAssert.assertTrue(
				(helpAndSupportPage.getUrlOfCurrentPage().contains(ConstantURL.WEX_GETTING_STARTED_GUIDE)),
				"URL for Getting Started  is not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Getting Started Tile completed.");

		// Validation for software download tile
		
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("SoftwareDownloadLabel"),
				"Software download Title is incorrect");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("SoftwareDownloadLabel");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load

		softAssert.assertTrue(
				(getEnvironment(System.getProperty("environment")).replaceFirst(ConstantURL.UI_IDPLOGIN_REMOVE, "") + "/" + ConstantURL.SOFTWARE_DOWNLOAD + "/" + LanguageCode).equals(helpAndSupportPage.getUrlOfCurrentPage()),
				"URL for software download link is not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Software Download Tile completed.");

		// Validation for knowledge base tile
		
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("KnowledgeBaseLabel"),
				"Knowledge Base Title is incorrect");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("KnowledgeBaseLabel");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		softAssert.assertTrue(
				(helpAndSupportPage.getUrlOfCurrentPage().contains(ConstantURL.WEX_KNOWLEDGE_BASE)),"URL for Knowledge Base  is not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Getting Started Tile completed.");

		// Validation for software requirement tile
	 
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("SystemRequirementsLabel"),
				"System Requirements Title is incorrect");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("SystemRequirementsLabel");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		softAssert.assertTrue(
				(getEnvironment(System.getProperty("environment")).replaceFirst(ConstantURL.UI_IDPLOGIN_REMOVE, "") + "/" + ConstantURL.SYSTEM_REQUIREMENTS + "/"
						+ LanguageCode).equals(helpAndSupportPage.getUrlOfCurrentPage()),
				"URL for System Requirements link is not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of System Requirements Tile completed.");

		// Legal Information Tile
		// Privacy link
		helpAndSupportPage.scrollTillViewWEXHelpAndSupportPage("PrivacyLink");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("PrivacyLink"),
				"Privacy link is not present on Help and Support Page");
		helpAndSupportPage.waitForElementsOfWEXHelpAndSupportPage("PrivacyLink");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("PrivacyLink");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded(); // Url takes time to load
		softAssert.assertTrue(helpAndSupportPage.getUrlOfCurrentPage().contains(ConstantURL.WEX_PRIVACY_LINK),"Privacy Title matching failed");
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Privacy completed.");

		// Terms and Condition link
		helpAndSupportPage.scrollTillViewWEXHelpAndSupportPage("TermsandConditionsLink");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("TermsandConditionsLink"),
				"Terms and condition link is not present on Help and Support Page");
		helpAndSupportPage.waitForElementsOfWEXHelpAndSupportPage("TermsandConditionsLink");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("TermsandConditionsLink");
		helpAndSupportPage.switchToDifferentTab();
		sleeper(2000);// Url takes time to load
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("printButton"),
				"Print Button not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("HpWEXLogo"),
				"HP WEX con not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("HPheaderOn"),
				"HP WEX header is not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("userProfileIcon"),
				"User profile icon not present");
		waitForPageLoaded();
        if(System.getProperty("environment").contains("VENEER") || System.getProperty("environment").contains("STAGE")){
            softAssert
                    .assertTrue(
                            (SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/"
                                    + ConstantURL.TERMS_AND_CONDITIONS_VENEER + "/"
                                    + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")))
                                    .equals(helpAndSupportPage.getUrlOfCurrentPage()),
                            "Terms and Condition URL not matching");
        }else {
            softAssert
                    .assertTrue(
                            (SetTestEnvironments.getEnvironment(System.getProperty("environment")).replaceFirst(ConstantURL.UI_IDPLOGIN_REMOVE, "") + "/"
                                    + ConstantURL.TERMS_AND_CONDITIONS + "/"
                                    + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")))
                                    .equals(helpAndSupportPage.getUrlOfCurrentPage()),
                            "Terms and Condition URL not matching");
        }
		helpAndSupportPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Terms and Condition completed.");

		// Service Level Agreement
		helpAndSupportPage.scrollTillViewWEXHelpAndSupportPage("ServiceLevelAgreementLink");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("ServiceLevelAgreementLink"),
				"Terms and condition link is not present on Help and Support Page");
		helpAndSupportPage.waitForElementsOfWEXHelpAndSupportPage("ServiceLevelAgreementLink");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("ServiceLevelAgreementLink");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("printButton"),
				"Print Button not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("HpWEXLogo"),
				"HP icon not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("SLAHeader"),
				"HP Daas not present");
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("userProfileIcon"),
				"User profile icon not present");
		softAssert
				.assertTrue(
						(SetTestEnvironments.getEnvironment(System.getProperty("environment")).replaceFirst(ConstantURL.UI_IDPLOGIN_REMOVE, "")+ "/" + ConstantURL.SLA+ "/"
								+ SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")))
								.equals(helpAndSupportPage.getUrlOfCurrentPage()),
						"Service Level Agreement URL not matching");
		helpAndSupportPage.switchBackToPreviousTab();
		waitForPageLoaded();
		LOGGER.info("Validation of Service Level Agreement completed.");

		// Use Of Cookies
		softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("UseofCookiesLink"),
				"Use of cookies is not present on Help and Support Page");
		helpAndSupportPage.waitForElementsOfWEXHelpAndSupportPage("UseofCookiesLink");
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("UseofCookiesLink");
		sleeper(2000);
		helpAndSupportPage.switchToDifferentTab();
		waitForPageLoaded();// Url takes time to load
		softAssert.assertTrue(
				helpAndSupportPage.getUrlOfCurrentPage().contains(
						helpAndSupportPage.getTextLanguage(LanguageCode, "daas_ui", "welcome.privacy").toLowerCase()),
				"Use of Cookies Title matching failed");
		helpAndSupportPage.switchBackToPreviousTab();
		softAssert.assertAll();
		LOGGER.info("Validation of Use of Cookies completed.");

		LOGGER.info("Validation of Looking for something new section on help and support page completed");
	}

	
	@Test(priority = 5, groups = {"REGRESSION_ACCOUNTS", "PRODUCTION_ACCOUNTS", "PRODUCTION_LDK","INITECH_SOLUTIONS" }, description = "TestCaseID:C41392702")
	 public final void verifyHelpAndSupportTab() throws Exception {
		
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
		waitForPageLoaded();
		LOGGER.info("Redirected to help and support page");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("getsupporttile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.get_started.title")),"get support tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("getSupporttileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.get_started.section")),"get support tile below text are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("systemrequirementstile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.system_requirements.title")),"Syetem requirement tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("systemRequirementstileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.system_requirements.section")),"Syetem requirement tile below text are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("knowledgebasetile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.academy.title")),"knowlodge base tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("knowledgebasetileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.academy.section")),"knowlodge base tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("softwaredownload").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.software_download.title")),"software page tile below text are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("softwareDownloadbelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.software_download.section")),"software page tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("whatsnewtile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.whats_new.title")),"what's new tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("wahtsNewTileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.whats_new.section")),"what's new tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("mysupporttile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.my_support_cases.title")),"My support description are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("MysupportTileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.my_support_cases.section")),"My support description text are not loading on Help and Support page.");
		softAssert.assertAll();
	}

}