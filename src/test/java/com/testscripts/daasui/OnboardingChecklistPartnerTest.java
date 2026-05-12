package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class OnboardingChecklistPartnerTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(OnboardingChecklistPartnerTest.class);

    /**
     * This test will verify Windows PC operating systems tile information
     */
    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void OnbaordingChecklistJourney() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("OnboardingChecklist", getTextLanguage(LanguageCode, "daas_ui", "global.onboarding")), "Onboarding checklist not found");
        softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("OnboardingChecklist"), "Onbaording checklist is not clickable");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("FirstSteps", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.sectionTitle.firstSteps")), "First Steps text is matching");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_1", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.title")), "Customize your brand text is matching");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_2", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.helpandsupport.title")), "Customize Help and Support text is matching");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_3", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.title")), "Invite Team member text is matching");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_4", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customeraccount.add.title")), "Create a customer account text is matching");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("DiscoverMore", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.checklist.sectionTitle.discoverMore")), "Discover More text is matching");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_5", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.title")), "Enroll customer devices text is matching");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_6", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.integrations.title")), "Explore integration text is matching");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_7", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.assignlicenses.title")), "Assign licenses text is matching");
        softAssert.assertAll();
    }

    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void CustomizeYourBrand() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        Assert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("OnboardingChecklist"), "Onboarding checklist is not clickable");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_1", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.title")), "Customize your brand text is matching");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("label_1");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.title")), "Customize your brand text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingsTooltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.settings")), "Lets navigate to settings text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingsTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")), "Settings text mismatch");
        waitForPageLoaded();
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SettingsTab");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Customization", getTextLanguage(LanguageCode, "daas_ui", "Customization.label")), "Customization text mismatch");
        waitForPageLoaded();
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomizationTootltipTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.title")), "Customize your brand text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomizationTootltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.customization")), "Let's navigate to customization tab mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("Customization");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Branding", getTextLanguage(LanguageCode, "daas_ui", "branding.label")), "Branding text mismatch");
        waitForPageLoaded();
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("BrandingTootltipTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.branding.title")), "Branding text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("BrandingTootltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.branding.desc")), "Branding Text mismatch");

        softAssert.assertAll();
        LOGGER.info("Customize your brand Journey successfully completed");
    }

    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void CustomizeHelpSupport() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        Assert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("OnboardingChecklist"), "Onboarding checklist is not clickable");
        waitForPageLoaded();


        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_2", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.helpandsupport.title")), "Customize Help & Support is matching");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("label_2");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.helpandsupport.title")), "Customize Help & Support text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingHelpNsupport", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customizebrand.settings")), "Lets navigate to settings text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SettingsTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.settings")), "Settings text mismatch");
        waitForPageLoaded();
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SettingsTab");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Customization", getTextLanguage(LanguageCode, "daas_ui", "Customization.label")), "Customization text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomizationTootltipTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.helpandsupport.title")), "Customize Help & Support text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomizationHelpSupport", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.helpandsupport.customization")), "Select 'Customization' to continue text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("Customization");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustHelpSupport", getTextLanguage(LanguageCode, "daas_ui", "partner.details.customer_help_support")), "Customer Help and support text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SupportTootltipTitlle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.helpandsupport.support.title")), " Set up your customers' technical support model text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SupportTootltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.helpandsupport.support")), "Description Customization Text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SupportTootltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.helpandsupport.supportNext")), "Description Customization Text mismatch");

        softAssert.assertAll();
        LOGGER.info("Customize your brand Journey successfully completed");
    }


    @Test(priority = 4, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void InviteTeamMembers() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        Assert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("OnboardingChecklist"), "Onboarding checklist is not clickable");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_3", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.title")), "Invite Team member text is matching");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("label_3");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountTootltipTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.title")), "Customize your brand text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountTootltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.account")), "Lets navigate to settings text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AccountManagementTab", getTextLanguage(LanguageCode, "daas_ui", "global.account")), "Accounts Tab text mismatch");
        waitForPageLoaded();
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("AccountManagementTab");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("UserTab", getTextLanguage(LanguageCode, "daas_ui", "global.admins")), "Users text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("UsersTooltipTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.title")), "Select the 'Users' tab text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("UsersTooltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.users")), "Select the 'Users' tab text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("UserTab");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("InviteUsers", getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.invite_user")), "Invite Users text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("InviteUsersTooltipTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.title")), "Invite team members text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("InviteUsersTooltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.addUser")), "Select 'Invite user' Text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("InviteUsers");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Adduserstootltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.inviteteam.selectOption")), "Invite Users tootltip text mismatch");
        waitForPageLoaded();
        softAssert.assertAll();
        LOGGER.info("Customize your brand Journey successfully completed");

    }

    @Test(priority = 5, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void CreateCustomer() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        Assert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("OnboardingChecklist"), "Onboarding checklist is not clickable");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_4", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customeraccount.add.title")), "Customize your brand text is matching");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("label_4");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers text mismatch");
        waitForPageLoaded();
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CreateCustomer", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customeraccount.title")), "Create customer account text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("TourCustomers", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customeraccount.desc")), "Let's navigate to Customers text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("CustomersTab");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Addcustomertour", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customeraccount.add.desc1")), "Create customer text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AddCustomerTooltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customeraccount.add.desc2")), "Create customer text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AddCustomer", getTextLanguage(LanguageCode, "daas_ui", "global.add")), "Add customers text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("AddCustomer");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AddCustomers", getTextLanguage(LanguageCode, "daas_ui", "customer.add.popup.title")), "Add customer text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Addcustomertour2", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.customeraccount.add.customer.info.desc")), "Branding text mismatch");

        softAssert.assertAll();
        LOGGER.info("Create a customer account Journey successfully completed");

    }


    @Test(priority = 6, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void ExploreIntegrations() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        Assert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("OnboardingChecklist"), "Onboarding checklist is not clickable");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_6", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.dashboard.integrations.title")), "Explore Integration text is matching");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("label_6");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("IntegrationTab", getTextLanguage(LanguageCode, "daas_ui", "integrations.tab.integrations")), "Customers text mismatch");
        waitForPageLoaded();
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("IntegrationTooltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.discovermore.integrations.title")), "Integration account text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Integrationtooltip2", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.discovermore.integrations.desc1")), "Integration Desc1 text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Integrationtooltip2", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.discovermore.integrations.desc2")), "Integration desc2 text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("IntegrationTab");
        waitForPageLoaded();


        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("IntegrationHeading", getTextLanguage(LanguageCode, "daas_ui", "integrations.header.title")), "Integration Heading mismatch");
        waitForPageLoaded();
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("IntegrationTooltip3", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.integrations.browse.desc1")), "Create customer text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("BrowseIntegration", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.integrations.browse.title")), "Create customer text mismatch");
        waitForPageLoaded();

        softAssert.assertAll();
        LOGGER.info("Explore Integrations Journey successfully completed");

    }

    @Test(priority = 7, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void AssignLicenses() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        Assert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("OnboardingChecklist"), "Onboarding checklist is not clickable");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_7", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.assignlicenses.title")), "Assign Licenses text is matching");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("label_7");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AssignLicensesTootltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.assignlicenses.title")), "Licenses tooltip text mismatch");
        waitForPageLoaded();
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("LicensesTootltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.assignlicenses.desc")), "Let's Navigate to licenses text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LicenseSubscription");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Licensestooltip1", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.assignlicenses.management.desc")), "Tootltip2 text mismatch");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("StatusLicense", getTextLanguage(LanguageCode, "daas_ui", "subscription.list.status")), "Status text mismatch");
        waitForPageLoaded();
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("StatusDropDown");
        waitForPageLoaded();
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("Status");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("NewOption");

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LicensesCheckbox");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("licensestooltip2", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.assignlicenses.management.next")), "Tooltip 3 text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("AssignLicenseButton");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Licensestooltip3", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.assignlicenses.management.select.desc")), "ToolTip 4 test mismatches");
        waitForPageLoaded();

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("cancellicenses");
        waitForPageLoaded();
        softAssert.assertAll();
        LOGGER.info("Assign Licenses Journey successfully completed");

    }

    @Test(priority = 8, groups = {"REGRESSION_PARTNERCX", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : ")
    public final void EnrollCustomerDevices() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();

        // If the what's new popup is available, close it
        if (wepPartnerCustomersPage.verifyElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton"))
            wepPartnerCustomersPage.clickOnElementsOfWEPPartnerCustomersPage("whatsNewPopupClosedButton");

        leftSideMenuVerification();
        waitForPageLoaded();
        wepPartnerCustomersPage.waitForElementsOfWEPPartnerCustomersPage("customerFilter");

        // If the All customer is not selected, select the All customer
        if (!wepPartnerCustomersPage.matchTextOfWEPPartnerCustomersPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            wepPartnerCustomersPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
        }

        Assert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("OnboardingChecklist"), "Onboarding checklist is not clickable");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("label_5", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.title")), "Enroll customer devices text is matching");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("label_5");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EnrollToolTip2Title", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.title")), "Enroll customer devices text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EnrollToolTip1", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.desc")), "TootlTip 1  text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SelectCustomer");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EnrollToolTip2Title", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.title")), "Enroll customer devices text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EnrollToolTip2", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.allCustomers.desc")), "Select 'All Customers' to continue text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("SelectCustomer1");
        waitForPageLoaded();

        waitForPageLoaded();
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("DevicesTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.devices.title")), "Add Devices text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("DevicesTooltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.devices.desc1")), "Tootltip3 text mismatch");
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("DevicesTooltip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.add.devices.pcUnderDevices")), "Tootltip3 text mismatch");
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("DevicesTab");
        waitForPageLoaded();

        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("PCSideMenu");
        waitForPageLoaded();

        waitForPageLoaded();
        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("AddPCToolTip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.pcs.desc1")), "Tootltip3 text mismatch");
        waitForPageLoaded();
        WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("AddButton");
        waitForPageLoaded();

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("ChooseToolTip", getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.coachmark.enrolldevices.finalstep")), "Tootltip4 text mismatch");
        softAssert.assertAll();
        LOGGER.info("Enroll Your Devices your brand Journey successfully completed");
    }
}