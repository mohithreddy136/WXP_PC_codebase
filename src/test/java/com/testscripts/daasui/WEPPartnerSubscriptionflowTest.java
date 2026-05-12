package com.testscripts.daasui;

import com.daasui.pages.WEPPartnerlicenseAndSubscriptionPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;

import java.sql.SQLOutput;

public class WEPPartnerSubscriptionflowTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WEPPartnerSubscriptionflowTest.class);

    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID :")
    public final void verifyAssignLicenseAndSubscription() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerlicenseAndSubscriptionPage WEPPartnerlicenseAndSubscriptionPage = new WEPPartnerlicenseAndSubscriptionPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerlicenseAndSubscriptionPage.verifyElementsOfWEPPartnerlicenseAndSubscriptionPage("whatsNewPopupClosedButton")) {
            WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        if (!WEPPartnerlicenseAndSubscriptionPage.matchTextOnWEPPartnerlicenseAndSubscriptionPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerlicenseAndSubscriptionPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            LOGGER.info("Partner has selected the All Customers view");
        }
        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.getTextOfWEPPartnerlicenseAndSubscriptionPage("liscenceAndSubscriptionSideMenu")
                .equals(WEPPartnerlicenseAndSubscriptionPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.tab.LicensesAndSubscriptions")));
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("liscenceAndSubscriptionSideMenu");
        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.getTextOfWEPPartnerlicenseAndSubscriptionPage("subscriptiontab")
                .equals(WEPPartnerlicenseAndSubscriptionPage.getTextLanguage(LanguageCode, "daas_ui", "partner.subscriptions")));
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("subscriptiontab");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("assignSubscriptionButton");
        WEPPartnerlicenseAndSubscriptionPage.waitForElementToBeClickable("ChoosecustomerDropdown");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("customertitle");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("arrowcust");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("editcompanysub");
        String companyName = "DNDAutomationcompany1311";
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("PlanText");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("Planarrow");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("planoption");

        WEPPartnerlicenseAndSubscriptionPage.selectStartDate();
        WEPPartnerlicenseAndSubscriptionPage.selectEndDate();
        WEPPartnerlicenseAndSubscriptionPage.generateAndEnterRandomNumber();
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("Savebutton");
        softAssert.assertAll();
    }

    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID :")
    public final void verifyEditSubscription() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerlicenseAndSubscriptionPage WEPPartnerlicenseAndSubscriptionPage = new WEPPartnerlicenseAndSubscriptionPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerlicenseAndSubscriptionPage.verifyElementsOfWEPPartnerlicenseAndSubscriptionPage("whatsNewPopupClosedButton")) {
            WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        if (!WEPPartnerlicenseAndSubscriptionPage.matchTextOnWEPPartnerlicenseAndSubscriptionPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerlicenseAndSubscriptionPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("liscenceAndSubscriptionSideMenu");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("subscriptiontab");

        WEPPartnerlicenseAndSubscriptionPage.searchCompany("DNDAutomationcompany1311");
        WEPPartnerlicenseAndSubscriptionPage.clickByJavaScriptOnWEPPartnerlicenseAndSubscriptionPage("searchresultfirstrow");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("editSubscButton");

        WEPPartnerlicenseAndSubscriptionPage.increaseEndpointValueByOne();
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("editSavebutton");
        WEPPartnerlicenseAndSubscriptionPage.waitForElementsOfWEPPartnerlicenseAndSubscriptionPage("editToastNotification");

        softAssert.assertAll();
    }

    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID :")
    public final void verifyRevokeSubscription() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerlicenseAndSubscriptionPage WEPPartnerlicenseAndSubscriptionPage = new WEPPartnerlicenseAndSubscriptionPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerlicenseAndSubscriptionPage.verifyElementsOfWEPPartnerlicenseAndSubscriptionPage("whatsNewPopupClosedButton")) {
            WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        if (!WEPPartnerlicenseAndSubscriptionPage.matchTextOnWEPPartnerlicenseAndSubscriptionPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerlicenseAndSubscriptionPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("liscenceAndSubscriptionSideMenu");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("subscriptiontab");
        WEPPartnerlicenseAndSubscriptionPage.searchCompany("DNDAutomationcompany1311");
        waitForPageLoaded();
        Thread.sleep(1500);
        WEPPartnerlicenseAndSubscriptionPage.selectCompanyForRevoke("searchresultfirstrow");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("Revokebutton");

        String securityCode = WEPPartnerlicenseAndSubscriptionPage.getTextOfWEPPartnerlicenseAndSubscriptionPage("Securitycode");
        WEPPartnerlicenseAndSubscriptionPage.enterTextOfWEPPartnerlicenseAndSubscriptionPage("securitycodeinput", securityCode);
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("revokebuttoninside");

        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.getTextOfWEPPartnerlicenseAndSubscriptionPage("revokesubscriptitle")
                .equals(WEPPartnerlicenseAndSubscriptionPage.getTextLanguage(LanguageCode, "daas_ui", "partner.subscriptions_revoke_success.title")));

        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.getTextOfWEPPartnerlicenseAndSubscriptionPage("revokesubtext")
                .equals(WEPPartnerlicenseAndSubscriptionPage.getTextLanguage(LanguageCode, "daas_ui", "partner.subscriptions_revoke_success.subTitle")));

        softAssert.assertAll();
    }

    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0002")
    public final void verifycompanyflowLicenseAndSubscription() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerlicenseAndSubscriptionPage WEPPartnerlicenseAndSubscriptionPage = new WEPPartnerlicenseAndSubscriptionPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerlicenseAndSubscriptionPage.verifyElementsOfWEPPartnerlicenseAndSubscriptionPage("whatsNewPopupClosedButton")) {
            WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        if (!WEPPartnerlicenseAndSubscriptionPage.matchTextOnWEPPartnerlicenseAndSubscriptionPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerlicenseAndSubscriptionPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            LOGGER.info("Partner has selected the All Customers view");
        }
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("companytabsidemenu");
        waitForPageLoaded();
        if (WEPPartnerlicenseAndSubscriptionPage.verifyElementsOfWEPPartnerlicenseAndSubscriptionPage("clearfiltercust")) {
            LOGGER.info("Clear Filter button is present. Clicking on it.");
            WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("clearfiltercust");}
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("companynametocheck");
        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.getTextOfWEPPartnerlicenseAndSubscriptionPage("subscriptiontabaccount")
                .equals(WEPPartnerlicenseAndSubscriptionPage.getTextLanguage(LanguageCode, "daas_ui", "partner.subscriptions")),"Subscription tab Header doesn't match");
        LOGGER.info("Subscription tab is matched");
        WEPPartnerlicenseAndSubscriptionPage.clickOnElementsOfWEPPartnerlicenseAndSubscriptionPage("subscriptiontabaccount");
        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.getTextOfWEPPartnerlicenseAndSubscriptionPage("subscrpTitle")
                .equals(WEPPartnerlicenseAndSubscriptionPage.getTextLanguage(LanguageCode, "daas_ui", "partner.subscriptions")), "Subscriptions title text doesn't match");
        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.verifyElementsOfWEPPartnerlicenseAndSubscriptionPage("subscriptionCard"), "Subscription card is not present");
        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.verifyElementsOfWEPPartnerlicenseAndSubscriptionPage("SubscriptionDetailheader"), "Subscription details header is not present");
        softAssert.assertTrue(WEPPartnerlicenseAndSubscriptionPage.verifyElementsOfWEPPartnerlicenseAndSubscriptionPage("Activestatus"), "Active status is not present");
        softAssert.assertAll();
    }

}
