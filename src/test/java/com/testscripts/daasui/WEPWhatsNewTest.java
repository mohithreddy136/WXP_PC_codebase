package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.daasui.constants.CompaniesVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ScriptVariables;
import com.daasui.pages.WEPWhatsNewPage;

public class WEPWhatsNewTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEPWhatsNewTest.class);
	private static final String Whatsnew_Category_Both = "Both";
	String description = generateRandomString(11);

	public static List<String> listOfIds = new ArrayList<String>();
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_WEP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		data[2][0] = "ROOT_ADMIN_USER_US";
		data[2][1] = "ROOT_PASSWORD_US";
		return data;
	}
	
	
	@Test(priority = 1, groups = {"PRODUCTION_PLATFORMCX","REGRESSION_PLATFORMCX"}, description = "C51923307")
	public final void verifyWhatsNewTitle() throws Exception {
		WEPWhatsNewPage whatsNewPage = new WEPWhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		whatsNewPage.clickOnElementsOfWEPWhatsNewPage("cookieAccept");
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("whatsNewTabRoot");
			waitForPageLoaded();
			Assert.assertTrue(whatsNewPage.getTextOfWEPWhatsNewPage("whatsNewTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "whats_new.title")), "What's New title text is incorrect");
			Assert.assertTrue(whatsNewPage.waitForElementsOfWEPWhatsNewPage("whatsNewHeading"),"What's New Header are not loading.");
			LOGGER.info("What's New title and Header is matched page loaded successfully.");
		}else {
			// Clicking on "Help & Support" icon 
			whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("helpIcon");
			whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("whatsNewtile");
			whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("whatsNewTabRoot");
			waitForPageLoaded();
			Assert.assertTrue(whatsNewPage.getTextOfWEPWhatsNewPage("whatsNewTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "whats_new.title")), "What's New title text is incorrect");
			Assert.assertTrue(whatsNewPage.waitForElementsOfWEPWhatsNewPage("whatsNewHeading"),"What's New Header are not loading.");
			LOGGER.info("What's New title and Header is matched page loaded successfully.");
		}

	}

	@Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "C51573333")
	public final void verifyWhatsNewDraft() throws Exception {
		SoftAssert softAssert = new SoftAssert();

		WEPWhatsNewPage whatsNewPage = new WEPWhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
		LOGGER.info("Opened whats new in root");
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("WhatsNewIcon");
		softAssert.assertTrue(whatsNewPage.verifyElementsOfWEPWhatsNewPage("DraftButton"), "Draft a new release button is not present");
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("DraftButton");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("DraftNewText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.draft_new_release")), "Draft a new release heading is not present");
		whatsNewPage.mouseHoverclickOfWhatsNewPage(whatsNewPage.getElementOfWhatsNewPage("SelectDropDown"));
		whatsNewPage.selectTextValueFromDropdownOfWhatsNewPage("catorgorydropdownlistboxwhatsnew",
				Whatsnew_Category_Both, "SelectDropDown");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("ReleaseVersionText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.release_version")), "Release version text is not present");
		whatsNewPage.generateAndEnterRandomVersion();
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("SelectdateText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.select_date")), "Select text is not present");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("CustomerNotesText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.cust_notes")), "Select text is not present");
		whatsNewPage.enterTextForOfWhatsNewPage("customernotestextbox", CompaniesVariables.WHATS_NEW_CUSTOMER_NOTES);
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("PartnerNotesText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.partner_notes")), "Select text is not present");
		whatsNewPage.enterTextForOfWhatsNewPage("partnernotestextbox", CompaniesVariables.WHATS_NEW_PARTNER_NOTES);
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("ReleaseVersionText");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("CancelButton", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.cancel")), "Cancel button is not present");
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("SaveButton");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("releasesuccessfulpopup", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.toast.new_release")), "New Release version pop up is not present");
		sleeper(3000);
		refreshPage();
		softAssert.assertAll();
		LOGGER.info("New draft for whats new has been released");
	}

	@Test(priority = 3, groups = {"REGRESSION_PLATFORMCX"}, description = "C51573333")
	public final void verifyWhatsNewEditDelete() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEPWhatsNewPage whatsNewPage = new WEPWhatsNewPage(PreDefinedActions.getDriver()).getInstance();
		login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
		LOGGER.info("Opened whats new in root admin");
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("WhatsNewIcon");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("editbutton", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.edit")), "Edit button is not present");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("deletebutton", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.remove")), "Delete button is not present");
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("deletebutton");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("DeleteRelease", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.remove_release")), "Delete release text is not present");
		whatsNewPage.verifyElementsOfWEPWhatsNewPage("deletepopup");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("DeleteReleaseDeletebutton", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.remove")), "Delete button is not present");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("DeleteReleaseCancelbutton", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.cancel")), "Cancel button is not present");
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("DeleteReleaseCancelbutton");
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("editbutton");
		whatsNewPage.verifyElementsOfWEPWhatsNewPage("ReleaseText");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("ReleaseVersionText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.release_version")), "Release text is not present");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("SelectdateText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.select_date")), "Select text is not present");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("CustomerNotesText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.cust_notes")), "Select text is not present");
		whatsNewPage.enterTextForOfWhatsNewPage("customernotestextbox", CompaniesVariables.WHATS_NEW_CUSTOMER_NOTES_EDIT);
		whatsNewPage.clickOnElementsOfWEPWhatsNewPage("PartnerNotesText");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("PartnerNotesText", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "whats_new.partner_notes")), "Select text is not present");
		whatsNewPage.verifyElementsOfWEPWhatsNewPage("PartnernoteTextbox");
		softAssert.assertTrue(whatsNewPage.matchTextOfWEPWhatsNewPage("CancelButton", whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "global.cancel")), "Cancel button is not present");
		whatsNewPage.clickOnWebelementOfWEPWhatsNewPage("SaveButton");
		whatsNewPage.verifyElementsOfWEPWhatsNewPage("updatedtext");
		sleeper(3000);
		refreshPage();
		softAssert.assertAll();
		LOGGER.info("Edit and delete has been verified");
	}

}
