package com.testscripts.daasui;


import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.WEPCustomerVariables;
import com.daasui.pages.WEPCreateCompanyPage;
import com.daasui.pages.WEPPartnerCustomizeHelpandSupportPage;
import com.daasui.pages.WEXPartnerPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WEPPartnerCustomizeHelpandSupportTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WEPPartnerCustomizeHelpandSupportTest.class);
    public static final String CUSTOM_SUPPORT_URL = "https://www.google.com/";
    public static final String companyEmailAddress = WEPCustomerVariables.CUSTOMER_NAME + WEXPartnerPage.getddMMMyyyyFormattedDate().toLowerCase() + "@hpmsqa.mailinator.com";


    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0001")
    public final void verifyCustomURLHelpandSupport() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerCustomizeHelpandSupportPage WEPPartnerCustomizeHelpandSupportPage = new WEPPartnerCustomizeHelpandSupportPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("whatsNewPopupClosedButton")) {
            WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        if (!WEPPartnerCustomizeHelpandSupportPage.matchTextOfWEPPartnerCustomizeHelpandSupportPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerCustomizeHelpandSupportPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            LOGGER.info("Partner has selected the All Customers view");
        }
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("settingsideMenu")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")), "Settings side menu Header doesn't match");
        LOGGER.info("Settings sidemenu header is matched");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("settingsideMenu");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("CustomizeHelpandSupportPageTab");
        LOGGER.info("Navigated successfully to Customization page");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("CustomerHelpandSupportPageTitle")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "partner.details.customer_help_support")), "Customer Help And support title doesn't match");
        LOGGER.info("Customer help and support title is matched");
        WEPPartnerCustomizeHelpandSupportPage.checkCustomHelpAndSupportToggle("submitHelpToggle");
        WEPPartnerCustomizeHelpandSupportPage.ensureOptionIsSelected("CustomURLBox");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("SupportURLLabel"), "Support URL header is not present");
        WEPPartnerCustomizeHelpandSupportPage.clickByJavaScriptOnWEPPartnerCustomizeHelpandSupportPage("SupportURLedit");
        WEPPartnerCustomizeHelpandSupportPage.enterTextOfWEPPartnerCustomizeHelpandSupportPage("CustomTextBox", CUSTOM_SUPPORT_URL);
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("CustomURLHeader")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.custom_url")), "Custom URL Header doesn't match");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("CustomSubHeader")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.custom_url_label")), "Custom URL Sub Header doesn't match");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("CustomerURlHeader")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.textbox_label_url")), "Customer URL Header doesn't match");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("CancelButtonCustom"), "Cancel button is not present");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("SaveButtonCustom");
        LOGGER.info("Custom URL is saved successfully");
        softAssert.assertAll();
    }

    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0002")
    public final void verifyCustomURLHelpandSupportThroughCustomer() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerCustomizeHelpandSupportPage WEPPartnerCustomizeHelpandSupportPage = new WEPPartnerCustomizeHelpandSupportPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("whatsNewPopupClosedButton")) {
            WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        selectCompany(getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"));
        waitForPageLoaded();
        sleeper(5000);
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("HelpandSupportMenu");
        LOGGER.info("Clicked on Help and Support menu from Customer's side");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("NeedAssistanceText")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.title")), "Need Assistance Header doesn't match");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("NeedAssistanceSubText")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.desc")), "Need Assistance Sub Header doesn't match");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("GetSupportButton");
        WEPPartnerCustomizeHelpandSupportPage.switchToDifferentTab();
        waitForPageLoaded();
        softAssert.assertTrue(CUSTOM_SUPPORT_URL.equals(WEPPartnerCustomizeHelpandSupportPage.getUrlOfCurrentPage()),
                "URL for Custom URL is not matching");
        WEPPartnerCustomizeHelpandSupportPage.switchBackToPreviousTab();
        LOGGER.info("Validation of Custom URL completed.");
        if (!WEPPartnerCustomizeHelpandSupportPage.matchTextOfWEPPartnerCustomizeHelpandSupportPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerCustomizeHelpandSupportPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            LOGGER.info("Partner has selected the All Customers view");
        }
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("settingsideMenu");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("CustomizeHelpandSupportPageTab");
        WEPPartnerCustomizeHelpandSupportPage.checkCustomHelpAndSupportToggle("submitHelpToggle");
        WEPPartnerCustomizeHelpandSupportPage.clickByJavaScriptOnWEPPartnerCustomizeHelpandSupportPage("submitHelpToggle");
        LOGGER.info("Custom URL option is disabled successfully from partner's side.");
        softAssert.assertAll();
    }

    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0003")
    public final void verifySupportRequestHelpandSupport() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerCustomizeHelpandSupportPage WEPPartnerCustomizeHelpandSupportPage = new WEPPartnerCustomizeHelpandSupportPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("whatsNewPopupClosedButton")) {
            WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        if (!WEPPartnerCustomizeHelpandSupportPage.matchTextOfWEPPartnerCustomizeHelpandSupportPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerCustomizeHelpandSupportPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            LOGGER.info("Partner has selected the All Customers view");
        }
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("settingsideMenu")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")), "Settings side menu Header doesn't match");
        LOGGER.info("Settings sidemenu header is matched");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("settingsideMenu");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("CustomizeHelpandSupportPageTab");
        LOGGER.info("Navigated successfully to Customization page");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("CustomerHelpandSupportPageTitle")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "partner.details.customer_help_support")), "Customer Help And support title doesn't match");
        LOGGER.info("Customer help and support title is matched");
        WEPPartnerCustomizeHelpandSupportPage.checkCustomHelpAndSupportToggle("submitHelpToggle");
        WEPPartnerCustomizeHelpandSupportPage.ensureOptionIsSelected("SupportRequestBox");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("SupportTeamEmailLabel"), "Support Team header is not present");
        WEPPartnerCustomizeHelpandSupportPage.clickByJavaScriptOnWEPPartnerCustomizeHelpandSupportPage("SupportTeamedit");
        WEPPartnerCustomizeHelpandSupportPage.enterTextOfWEPPartnerCustomizeHelpandSupportPage("SupportTeamEmailText", companyEmailAddress);
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("SupportTeamEmailText");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("SupportTeamEmailHeader")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.support_team_email")), "Support Team Header doesn't match");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("SupportTeamEmailSubHeader")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.custom_email_label")), "Support Team Sub Header doesn't match");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("CustomerSupportHeader")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.textbox_label_email")), "Customer Support Header doesn't match");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("CancelButtonCustom"), "Cancel button is not present");
        sleeper(1000);
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("SaveButtonCustom");
        LOGGER.info("Support Team Email is saved successfully");
        softAssert.assertAll();
    }

    @Test(priority = 4, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0004")
    public final void verifySupportTeamEmailHelpandSupportThroughCustomer() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerCustomizeHelpandSupportPage WEPPartnerCustomizeHelpandSupportPage = new WEPPartnerCustomizeHelpandSupportPage(PreDefinedActions.getDriver()).getInstance();
        WEPCreateCompanyPage WEPCreateCompanyPage = new WEPCreateCompanyPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("whatsNewPopupClosedButton")) {
            WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        selectCompany(getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"));
        waitForPageLoaded();
        sleeper(5000);
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("HelpandSupportMenu");
        LOGGER.info("Clicked on Help and Support menu from Customer's side");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.getTextOfWEPPartnerCustomizeHelpandSupportPage("SupportTeamMainHeader")
                .equals(WEPPartnerCustomizeHelpandSupportPage.getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.title")), "Need Assistance Header doesn't match");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("SubjectHeader"), "Subject header is not present");
        WEPPartnerCustomizeHelpandSupportPage.enterTextOfWEPPartnerCustomizeHelpandSupportPage("SubjectTextBox", "Test Subject");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("CategoryHeader"), "Category header is not present");
        WEPPartnerCustomizeHelpandSupportPage.selectDropdownOptionByKey("CategoryDropdown", "DropDownSecondOption");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("SupportTypeHeader"), "Support Type header is not present");
        WEPPartnerCustomizeHelpandSupportPage.selectDropdownOptionByKey("SupportTypeDropdown","DropDownSecondOption");
        //softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("SeverityHeader"), "Severity Type header is not present");
        //WEPPartnerCustomizeHelpandSupportPage.selectFirstValueFromDropdownOnWEPPartnerCustomizeHelpandSupportPage("SeverityDropdown");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("UploadFileHeader"), "Upload file header is not present");
        softAssert.assertTrue(WEPPartnerCustomizeHelpandSupportPage.verifyElementsOfWEPPartnerCustomizeHelpandSupportPage("DescriptionHeader"), "Description header is not present");
        WEPPartnerCustomizeHelpandSupportPage.enterTextOfWEPPartnerCustomizeHelpandSupportPage("DescriptionTextBox", "This is test description");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("SubmitRequestButton");
        LOGGER.info("Support Request is submitted successfully through Customer's help and support page.");

        //Email content verification
        String subjectInvitation = "Support Request";
        String mailContent  = WEPCreateCompanyPage.getActualMailContent(companyEmailAddress, subjectInvitation);
        softAssert.assertTrue(mailContent.contains("Support Request"));
        softAssert.assertTrue(mailContent.contains("Test Subject"));
        softAssert.assertTrue(mailContent.contains("This is test description"));
        softAssert.assertTrue(mailContent.contains("Feedback & suggestions"));
        if (!WEPPartnerCustomizeHelpandSupportPage.matchTextOfWEPPartnerCustomizeHelpandSupportPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerCustomizeHelpandSupportPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            LOGGER.info("Partner has selected the All Customers view");
        }
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("settingsideMenu");
        WEPPartnerCustomizeHelpandSupportPage.clickOnElementsOfWEPPartnerCustomizeHelpandSupportPage("CustomizeHelpandSupportPageTab");
        WEPPartnerCustomizeHelpandSupportPage.checkCustomHelpAndSupportToggle("submitHelpToggle");
        WEPPartnerCustomizeHelpandSupportPage.clickByJavaScriptOnWEPPartnerCustomizeHelpandSupportPage("submitHelpToggle");
        LOGGER.info("Custom URL option is disabled successfully from partner's side.");
        softAssert.assertAll();
    }
}
