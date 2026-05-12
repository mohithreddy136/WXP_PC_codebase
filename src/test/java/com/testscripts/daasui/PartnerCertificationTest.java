package com.testscripts.daasui;
import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class PartnerCertificationTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(PartnerCertificationTest.class);

    /**
     * This test will verify Windows PC operating systems tile information
     */
    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void CertificationsTab() throws Exception {
        login("NO_DATA_PARTNER_EMAIL_WEP", "NO_DATA_PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerCertificationsPage wexPartnerCertificationsPage = new WEXPartnerCertificationsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        LOGGER.info("Sign in with partner");
        wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("AccountManagementTab");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertificationsTab", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications")), "Certifications not found on Accounts Tab ");
        softAssert.assertTrue(wexPartnerCertificationsPage.clickOnElementsOfWEXPartnerCertificationsPage("CertificationsTab"), "Certifications Tab is not clickable");
        waitForPageLoaded();

        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertificationsCard1", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.wex.card.title")), "HP Workforce Experience Platform Text mismatches ");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertificationsCard2", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.anyware.card.title")), "HP Anyware Text mismatches ");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertificationsCard3", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.premium.card.title")), "Premium+ Support: Setup and Monitor Text mismatches ");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertificationsCard4", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.premium.manage_and_deliver.card.title")), "Premium+ Support: Setup, Monitor and Deliver Text mismatches ");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("HPLearningTile", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.footer.title")), "HP Learning Center Text mismatches ");

    }

    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void RootCertificationsToggle() throws Exception {
        login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEPPartnerAndCustomerCreationPage wEPPartnerAndCustomerCreationPage = new WEPPartnerAndCustomerCreationPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerCertificationsPage wexPartnerCertificationsPage = new WEXPartnerCertificationsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        wEPPartnerAndCustomerCreationPage.sideMenuSelectionWEPRootLoginPage(LanguageCode, "PartnerMenu", "Partners");

        waitForPageLoaded();

        if (wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("ClearFilterButton")) {
            wexPartnerCertificationsPage.clickOnElementsOfWEXPartnerCertificationsPage("ClearFilterButton");

            Assert.assertFalse(wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("ClearFilterButton"), "\"Clear Filter\" button is not available after clearing all filters");
        }
        waitForPageLoaded();
        String environment = System.getProperty("environment");
        String partnerEmail;
        String partnerKey;

        if ("EU-Stage-WEP".equalsIgnoreCase(environment) || "EU-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "euwexautomationstagepartnerwithnodata@workforceqa.ext.hp.com";
            partnerKey = "EUPartner";
        } else if ("US-Stage-WEP".equalsIgnoreCase(environment) || "US-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "uswexautomationstagepartnerwithnodata@workforceqa.ext.hp.com";
            partnerKey = "USPartner";
        } else {
            throw new IllegalArgumentException("Unsupported environment: " + environment);
        }

        wexPartnerCertificationsPage.enterTextForWEXPartnerCertificationsPage("EmailSerchBox", partnerEmail);
        softAssert.assertTrue(
                wexPartnerCertificationsPage.clickOnElementsOfWEXPartnerCertificationsPage(partnerKey),
                partnerKey + " not found on Root"
        );
        wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("CertificationsRoot");
        if (!wexPartnerCertificationsPage.verifyElementIsSelectedOfWEXPartnerCertificationsPage("Toggle2")){
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("Toggle2");
            waitForPageLoaded();
        }
        if (!wexPartnerCertificationsPage.verifyElementIsSelectedOfWEXPartnerCertificationsPage("Toggle3")){
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("Toggle3");
            waitForPageLoaded();
        }
        if (!wexPartnerCertificationsPage.verifyElementIsSelectedOfWEXPartnerCertificationsPage("Toggle4")){
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("Toggle4");
            waitForPageLoaded();
        }
        if (!wexPartnerCertificationsPage.verifyElementIsSelectedOfWEXPartnerCertificationsPage("Toggle5")){
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("Toggle5");
            waitForPageLoaded();
        }

        sleeper(2000);
        softAssert.assertAll();
        LOGGER.info("Successfully logged out from Root Admin");
    }

    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void VerifyCertifiedTiles() throws Exception {
        login("NO_DATA_PARTNER_EMAIL_WEP", "NO_DATA_PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEXPartnerPage wexPartnerPage = new WEXPartnerPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerCertificationsPage wexPartnerCertificationsPage = new WEXPartnerCertificationsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("AccountManagementTab");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertificationsTab", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications")), "Certifications not found on Accounts Tab ");
        softAssert.assertTrue(wexPartnerCertificationsPage.clickOnElementsOfWEXPartnerCertificationsPage("CertificationsTab"), "Certifications Tab is not clickable");
        waitForPageLoaded();
        sleeper(2000);

        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertifiedTitle1", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.certified_btn")), "Certified not found on Certifications");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertifiedTitle2", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.certified_btn")), "Certified not found on Certifications");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertifiedTitle3", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.certified_btn")), "Certified not found on Certifications");
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertifiedTitle4", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications.certified_btn")), "Certified not found on Certifications");

        sleeper(2000);
        softAssert.assertAll();
        LOGGER.info("Successfully verified all ceritified tiles on Certifications page");
    }

    @Test(priority = 4, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void RootCertificationsToggleDisable() throws Exception {
        login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();
        WEPPartnerAndCustomerCreationPage wEPPartnerAndCustomerCreationPage = new WEPPartnerAndCustomerCreationPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerCertificationsPage wexPartnerCertificationsPage = new WEXPartnerCertificationsPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        wepPartnerCustomersPage.sideMenuExpansionForPartner();
        wEPPartnerAndCustomerCreationPage.sideMenuSelectionWEPRootLoginPage(LanguageCode, "PartnerMenu", "Partners");

        waitForPageLoaded();

        if (wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("ClearFilterButton")) {
            wexPartnerCertificationsPage.clickOnElementsOfWEXPartnerCertificationsPage("ClearFilterButton");

            Assert.assertFalse(wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("ClearFilterButton"), "\"Clear Filter\" button is not available after clearing all filters");
        }
        waitForPageLoaded();
        String environment = System.getProperty("environment");
        String partnerEmail;
        String partnerKey;

        if ("EU-Stage-WEP".equalsIgnoreCase(environment) || "EU-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "euwexautomationstagepartnerwithnodata@workforceqa.ext.hp.com";
            partnerKey = "EUPartner";
        } else if ("US-Stage-WEP".equalsIgnoreCase(environment) || "US-VENEER-WEP".equalsIgnoreCase(environment)) {
            partnerEmail = "uswexautomationstagepartnerwithnodata@workforceqa.ext.hp.com";
            partnerKey = "USPartner";
        } else {
            throw new IllegalArgumentException("Unsupported environment: " + environment);
        }

        wexPartnerCertificationsPage.enterTextForWEXPartnerCertificationsPage("EmailSerchBox", partnerEmail);
        softAssert.assertTrue(
                wexPartnerCertificationsPage.clickOnElementsOfWEXPartnerCertificationsPage(partnerKey),
                partnerKey + " not found on Root"
        );
        Assert.assertTrue(wexPartnerCertificationsPage.matchTextOfWEXPartnerCertificationsPage("CertificationsRoot", getTextLanguage(LanguageCode, "daas_ui", "root.certifications")), "Certifications Text mismatches ");
        wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("CertificationsRoot");

        if (wexPartnerCertificationsPage.verifyElementIsSelectedOfWEXPartnerCertificationsPage("Toggle2")) {
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("Toggle2");
            waitForPageLoaded();
        }
        if (wexPartnerCertificationsPage.verifyElementIsSelectedOfWEXPartnerCertificationsPage("Toggle3")) {
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("Toggle3");
            softAssert.assertTrue(wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("DisableCertificateDeliverToggle"), "Delete Certificate for Premium+ Support: Setup, Monitor and Deliver is NOT present");
            softAssert.assertTrue(wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("DisableCertificateMonitorToggle"), "Delete Certificate Description for Premium+ Support: Setup, Monitor and Deliver is NOT present");
            String securityCode2 = wexPartnerCertificationsPage.getTextOfWEXPartnerCertificationsPage("SecurityCodeCertificate");
            wexPartnerCertificationsPage.enterTextForWEXPartnerCertificationsPage("securitycodecertificateinputdelivertoggle", securityCode2);
            softAssert.assertTrue(wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("deleteButtonCertificateToggleManage"), "Cancel button is NOT present");
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("deleteButtonCertificateToggleManage");
            waitForPageLoaded();
        }
        if (wexPartnerCertificationsPage.verifyElementIsSelectedOfWEXPartnerCertificationsPage("Toggle4")) {
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("Toggle4");
            softAssert.assertTrue(wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("DisableCertificateDeliverToggle"), "Delete Certificate for Premium+ Support: Setup, Monitor and Deliver is NOT present");
            softAssert.assertTrue(wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("DisableCertificateDescriptionDeliverToggle"), "Delete Certificate Description for Premium+ Support: Setup, Monitor and Deliver is NOT present");
            String securityCode2 = wexPartnerCertificationsPage.getTextOfWEXPartnerCertificationsPage("SecurityCodeCertificate");
            wexPartnerCertificationsPage.enterTextForWEXPartnerCertificationsPage("securitycodecertificateinputdelivertoggle", securityCode2);
            softAssert.assertTrue(wexPartnerCertificationsPage.verifyElementsOfWEXPartnerCertificationsPage("deleteButtonCertificateToggleDeliver"), "Cancel button is NOT present");
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("deleteButtonCertificateToggleDeliver");
            waitForPageLoaded();
        }
        if (wexPartnerCertificationsPage.verifyElementIsSelectedOfWEXPartnerCertificationsPage("Toggle5")) {
            wexPartnerCertificationsPage.actionClickOnWEXPartnerCertificationsPage("Toggle5");
            waitForPageLoaded();
        }
        sleeper(2000);
        softAssert.assertAll();
        LOGGER.info("Successfully logged out from Root Admin");
    }
}
