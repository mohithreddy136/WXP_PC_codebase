package com.testscripts.daasui;
import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class WEPPartnerLicensesFlowTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WEPPartnerLicensesFlowTest.class);


    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4439515876")
    public final void verifyServiceSpecialistLicenseTierElite() throws Exception {
        login("SERVICE_SPECIALIST_EMAIL", "SERVICE_SPECIALIST_PASSWORD");
        SoftAssert softAssert = new SoftAssert();

        WEPPartnerLicensesFlowPage wexPartnerLicensesPage = new WEPPartnerLicensesFlowPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers tab text is not matching");
        LOGGER.info("Customers tab text has been successfully verified.");

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomersTab"), "Customers tab is not clickable");
        LOGGER.info("Navigated successfully to Customers page");


        String[] licenseTiers = {"DNDELITE"};

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerSearch"), "Customers search box is not clickable");
        LOGGER.info("Customers search box clicked");

        for (String tier : licenseTiers) {
            wexPartnerLicensesPage.enterTextForWEPPartnerLicensesFlowPage("CustomerSearch", tier);
            LOGGER.info("Entered search term: " + tier);
            waitForPageLoaded();

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("EliteCustomer"), "Cannot Find DNDElite Customer");
            LOGGER.info("Elite customer identified on the Customer List page.");
            waitForPageLoaded();

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("CustomerLicense", getTextLanguage(LanguageCode, "daas_ui", "partner.licenses")), "License Tab for customer not available on customer detail page");
            LOGGER.info("Successfully switched to the Licenses tab on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerLicense"), "License Tab for customer not available on customer detail page");

            Assert.assertFalse(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgrade", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade button available on customer detail page");
            LOGGER.info("Successfully verified that the Elite Plan does not display the Upgrade Tier button on the Customer Details page.");

            softAssert.assertAll();
        }
    }
    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4439515876")
    public final void verifyServiceSpecialistLicenseTierStandard() throws Exception {
        login("SERVICE_SPECIALIST_EMAIL", "SERVICE_SPECIALIST_PASSWORD");
        SoftAssert softAssert = new SoftAssert();

        WEPPartnerLicensesFlowPage wexPartnerLicensesPage = new WEPPartnerLicensesFlowPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers tab text is not matching");
        LOGGER.info("Customers tab text has been successfully verified.");

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomersTab"), "Customers tab is not clickable");
        LOGGER.info("Navigated successfully to Customers page");

        String[] licenseTiers1 = {"DNDStandard"};

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerSearch"), "Customers search box is not clickable");
        LOGGER.info("Customers search box clicked");

        for (String tier1 : licenseTiers1) {
            wexPartnerLicensesPage.enterTextForWEPPartnerLicensesFlowPage("CustomerSearch", tier1);
            LOGGER.info("Entered search term: " + tier1);
            waitForPageLoaded();

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("DNDStandard"), "Cannot Find DNDStandard Customer");
            LOGGER.info("Standard customer identified on the Customer List page.");
            waitForPageLoaded();

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("CustomerLicense", getTextLanguage(LanguageCode, "daas_ui", "partner.licenses")), "License Tab for customer not available on customer detail page");
            LOGGER.info("Successfully switched to the Licenses tab on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerLicense"), "License Tab for customer not available on customer detail page");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgrade", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade button available on customer detail page");
            LOGGER.info("Successfully verified that the Standard Plan display the Upgrade Tier button on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("TierUpgrade"), "Tier Upgrade button is not clickable");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgradeHeading", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade Heading not available on modal");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgradeDesc", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.desc")), "Tier Upgrade Heading not available on modal");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_standard.short")), "Standard Heading not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.desc")), "upgrade.modal.standard_card.desc Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.hardware_software_inventory")), "hardware_software_inventory Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.windows_11_readiness")), "windows_11_readiness Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.api_access")), "features.api_access Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.remediations&automations")), "remediations&automations Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription6", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.experience_analytics")), "experience_analytics Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription7", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.employee_survey&notifications")), "employee_survey&notifications Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription8", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.third_party-integrations")), "third_party-integrations Description not available on modal");

            LOGGER.info("Successfully Validated all description for Standard Plan on License tier Modal.");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_pro.short")), "Pro Heading not available on modal");
//            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.desc")), "modal.pro_card.desc description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.subdescription")), "pro_card.subdescription description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.experience_analytics")), "experience_analytics description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.employee_survey&notifications")), "employee_survey&notifications description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.configurable_monitoring&alerting")), "configurable_monitoring&alerting description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription6", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.smart_device_refresh")), "smart_device_refreshdescription not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription7", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.natural_language_search")), "smart_device_refresh description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription8", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.ai_enabled")), "ai_enabled description not available on modal");

            LOGGER.info("Successfully Validated all description for Pro Plan on License tier Modal.");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_elite.short")), "Standard Heading not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.desc")), "elite_card.desc Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.subdescription")), "elite_card.subdescription Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.features.natural_language_search")), "elite_card.features.natural_language_search Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.features.free_text_analysis")), "elite_card.features.free_text_analysis Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.ai_enabled")), "ai_enabled Description not available on modal");

            LOGGER.info("Successfully Validated all description for Elite Plan on License tier Modal.");

            softAssert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("SeeDetailsButton", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.buttonText")), "See Details button not available");

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("SeeDetailsButton"), "See details button not clickable");
            sleeper(2000);
            wexPartnerLicensesPage.switchToDifferentTab();
            waitForPageLoaded();// Url takes time to load
            String Actual_URL = wexPartnerLicensesPage.getUrlOfCurrentPage();
            String Expected_URL = ConstantURL.SEE_DETAILS_LINK;
            softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Pricing Page is not matching");

            String ActualPage_Title = wexPartnerLicensesPage.getBrowserTabName();
            String Expected_Title = "Pricing - HP Workforce Experience Platform";
            softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Pricing - HP Workforce Experience Platform is not matching");
            LOGGER.info("Actual Title is :" + ActualPage_Title);

            wexPartnerLicensesPage.switchBackToPreviousTab();
            LOGGER.info("Validation of Pricing - HP Workforce Experience Platform.");
            LOGGER.info("Actual URL is :" + Actual_URL);
            LOGGER.info("Expected URL is :" + Expected_URL);
            LOGGER.info("Successfully validated Pricing - HP Workforce Experience Platform URL");

            softAssert.assertAll();
            LOGGER.info("Verified Pricing - HP Workforce Experience Platform successfully");

            softAssert.assertAll();
        }
    }
    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4439515876")
    public final void verifyServiceSpecialistLicenseTierUpgradePro() throws Exception {
        login("SERVICE_SPECIALIST_EMAIL", "SERVICE_SPECIALIST_PASSWORD");
        SoftAssert softAssert = new SoftAssert();

        WEPPartnerLicensesFlowPage wexPartnerLicensesPage = new WEPPartnerLicensesFlowPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers tab text is not matching");
        LOGGER.info("Customers tab text has been successfully verified.");

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomersTab"), "Customers tab is not clickable");
        LOGGER.info("Navigated successfully to Customers page");

        String[] licenseTiers1 = {"DNDPro"};

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerSearch"), "Customers search box is not clickable");
        LOGGER.info("Customers search box clicked");

        for (String tier1 : licenseTiers1) {
            wexPartnerLicensesPage.enterTextForWEPPartnerLicensesFlowPage("CustomerSearch", tier1);
            LOGGER.info("Entered search term: " + tier1);
            waitForPageLoaded();

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("DNDPro"), "Cannot Find DNDPro Customer");
            LOGGER.info("Elite customer identified on the Customer List page.");
            waitForPageLoaded();

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("CustomerLicense", getTextLanguage(LanguageCode, "daas_ui", "partner.licenses")), "License Tab for customer not available on customer detail page");
            LOGGER.info("Successfully switched to the Licenses tab on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerLicense"), "License Tab for customer not available on customer detail page");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgrade", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade button available on customer detail page");
            LOGGER.info("Successfully verified that the Pro Plan display the Upgrade Tier button on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("TierUpgrade"), "Tier Upgrade button is not clickable");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgradeHeading", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade Heading not available on modal");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgradeDesc", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.desc")), "Tier Upgrade Heading not available on modal");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_standard.short")), "Standard Heading not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.desc")), "upgrade.modal.standard_card.desc Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.hardware_software_inventory")), "hardware_software_inventory Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.windows_11_readiness")), "windows_11_readiness Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.api_access")), "features.api_access Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.remediations&automations")), "remediations&automations Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription6", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.experience_analytics")), "experience_analytics Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription7", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.employee_survey&notifications")), "employee_survey&notifications Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription8", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.third_party-integrations")), "third_party-integrations Description not available on modal");

            LOGGER.info("Successfully Validated all description for Standard Plan on License tier Modal.");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_pro.short")), "Pro Heading not available on modal");
//            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.desc")), "modal.pro_card.desc description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.subdescription")), "pro_card.subdescription description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.experience_analytics")), "experience_analytics description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.employee_survey&notifications")), "employee_survey&notifications description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.configurable_monitoring&alerting")), "configurable_monitoring&alerting description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription6", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.smart_device_refresh")), "smart_device_refreshdescription not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription7", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.natural_language_search")), "smart_device_refresh description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription8", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.ai_enabled")), "ai_enabled description not available on modal");

            LOGGER.info("Successfully Validated all description for Pro Plan on License tier Modal.");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_elite.short")), "Standard Heading not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.desc")), "elite_card.desc Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.subdescription")), "elite_card.subdescription Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.features.natural_language_search")), "elite_card.features.natural_language_search Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.features.free_text_analysis")), "elite_card.features.free_text_analysis Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.ai_enabled")), "ai_enabled Description not available on modal");

            LOGGER.info("Successfully Validated all description for Elite Plan on License tier Modal.");

            softAssert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("SeeDetailsButton", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.buttonText")), "See Details button not available");

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("SeeDetailsButton"), "See details button not clickable");
            sleeper(2000);
            wexPartnerLicensesPage.switchToDifferentTab();
            waitForPageLoaded();// Url takes time to load
            String Actual_URL = wexPartnerLicensesPage.getUrlOfCurrentPage();
            String Expected_URL = ConstantURL.SEE_DETAILS_LINK;
            softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Pricing Page is not matching");

            String ActualPage_Title = wexPartnerLicensesPage.getBrowserTabName();
            String Expected_Title = "Pricing - HP Workforce Experience Platform";
            softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Pricing - HP Workforce Experience Platform is not matching");
            LOGGER.info("Actual Title is :" + ActualPage_Title);

            wexPartnerLicensesPage.switchBackToPreviousTab();
            LOGGER.info("Validation of Pricing - HP Workforce Experience Platform.");
            LOGGER.info("Actual URL is :" + Actual_URL);
            LOGGER.info("Expected URL is :" + Expected_URL);
            LOGGER.info("Successfully validated Pricing - HP Workforce Experience Platform URL");

            softAssert.assertAll();
            LOGGER.info("Verified Pricing - HP Workforce Experience Platform successfully");
            softAssert.assertAll();
        }
    }

    @Test(priority = 4, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4439515876")
    public final void verifyPartnerAdminLicenseTierUpgradePro() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEPPartnerLicensesFlowPage wexPartnerLicensesPage = new WEPPartnerLicensesFlowPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers tab text is not matching");
        LOGGER.info("Customers tab text has been successfully verified.");

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomersTab"), "Customers tab is not clickable");
        LOGGER.info("Navigated successfully to Customers page");

        String[] licenseTiers1 = {"DNDPro"};

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerSearch"), "Customers search box is not clickable");
        LOGGER.info("Customers search box clicked");

        for (String tier1 : licenseTiers1) {
            wexPartnerLicensesPage.enterTextForWEPPartnerLicensesFlowPage("CustomerSearch", tier1);
            LOGGER.info("Entered search term: " + tier1);
            waitForPageLoaded();

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("DNDPro"), "Cannot Find DNDPro Customer");
            LOGGER.info("Elite customer identified on the Customer List page.");
            waitForPageLoaded();

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("CustomerLicense", getTextLanguage(LanguageCode, "daas_ui", "partner.licenses")), "License Tab for customer not available on customer detail page");
            LOGGER.info("Successfully switched to the Licenses tab on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerLicense"), "License Tab for customer not available on customer detail page");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgrade", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade button available on customer detail page");
            LOGGER.info("Successfully verified that the Pro Plan does not display the Upgrade Tier button on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("TierUpgrade"), "Tier Upgrade button is not clickable");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgradeHeading", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade Heading not available on modal");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgradeDesc", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.desc")), "Tier Upgrade Heading not available on modal");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_standard.short")), "Standard Heading not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.desc")), "upgrade.modal.standard_card.desc Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.hardware_software_inventory")), "hardware_software_inventory Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.windows_11_readiness")), "windows_11_readiness Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.api_access")), "features.api_access Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.remediations&automations")), "remediations&automations Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription6", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.experience_analytics")), "experience_analytics Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription7", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.employee_survey&notifications")), "employee_survey&notifications Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription8", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.third_party-integrations")), "third_party-integrations Description not available on modal");

            LOGGER.info("Successfully Validated all description for Standard Plan on License tier Modal.");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_pro.short")), "Pro Heading not available on modal");
//            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.desc")), "modal.pro_card.desc description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.subdescription")), "pro_card.subdescription description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.experience_analytics")), "experience_analytics description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.employee_survey&notifications")), "employee_survey&notifications description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.configurable_monitoring&alerting")), "configurable_monitoring&alerting description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription6", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.smart_device_refresh")), "smart_device_refreshdescription not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription7", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.natural_language_search")), "smart_device_refresh description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription8", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.ai_enabled")), "ai_enabled description not available on modal");

            LOGGER.info("Successfully Validated all description for Pro Plan on License tier Modal.");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_elite.short")), "Standard Heading not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.desc")), "elite_card.desc Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.subdescription")), "elite_card.subdescription Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.features.natural_language_search")), "elite_card.features.natural_language_search Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.features.free_text_analysis")), "elite_card.features.free_text_analysis Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.ai_enabled")), "ai_enabled Description not available on modal");

            LOGGER.info("Successfully Validated all description for Elite Plan on License tier Modal.");

            softAssert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("SeeDetailsButton", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.buttonText")), "See Details button not available");

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("SeeDetailsButton"), "See details button not clickable");
            sleeper(2000);
            wexPartnerLicensesPage.switchToDifferentTab();
            waitForPageLoaded();// Url takes time to load
            String Actual_URL = wexPartnerLicensesPage.getUrlOfCurrentPage();
            String Expected_URL = ConstantURL.SEE_DETAILS_LINK;
            softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Pricing Page is not matching");

            String ActualPage_Title = wexPartnerLicensesPage.getBrowserTabName();
            String Expected_Title = "Pricing - HP Workforce Experience Platform";
            softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Pricing - HP Workforce Experience Platform is not matching");
            LOGGER.info("Actual Title is :" + ActualPage_Title);

            wexPartnerLicensesPage.switchBackToPreviousTab();
            LOGGER.info("Validation of Pricing - HP Workforce Experience Platform.");
            LOGGER.info("Actual URL is :" + Actual_URL);
            LOGGER.info("Expected URL is :" + Expected_URL);
            LOGGER.info("Successfully validated Pricing - HP Workforce Experience Platform URL");

            softAssert.assertAll();
            LOGGER.info("Verified Pricing - HP Workforce Experience Platform successfully");
            softAssert.assertAll();
        }
    }
    @Test(priority = 5, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4439515876")
    public final void verifyPartnerAdminLicenseTierElite() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEPPartnerLicensesFlowPage wexPartnerLicensesPage = new WEPPartnerLicensesFlowPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers tab text is not matching");
        LOGGER.info("Customers tab text has been successfully verified.");

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomersTab"), "Customers tab is not clickable");
        LOGGER.info("Navigated successfully to Customers page");


        String[] licenseTiers = {"DNDELITE"};

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerSearch"), "Customers search box is not clickable");
        LOGGER.info("Customers search box clicked");

        for (String tier : licenseTiers) {
            wexPartnerLicensesPage.enterTextForWEPPartnerLicensesFlowPage("CustomerSearch", tier);
            LOGGER.info("Entered search term: " + tier);
            waitForPageLoaded();

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("EliteCustomer"), "Cannot Find DNDElite Customer");
            LOGGER.info("Elite customer identified on the Customer List page.");
            waitForPageLoaded();

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("CustomerLicense", getTextLanguage(LanguageCode, "daas_ui", "partner.licenses")), "License Tab for customer not available on customer detail page");
            LOGGER.info("Successfully switched to the Licenses tab on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerLicense"), "License Tab for customer not available on customer detail page");

            Assert.assertFalse(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgrade", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade button available on customer detail page");
            LOGGER.info("Successfully verified that the Elite Plan does not display the Upgrade Tier button on the Customer Details page.");

            softAssert.assertAll();
        }
    }

    @Test(priority = 6, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4439515876")
    public final void verifyPartnerAdministartorLicenseTierStandard() throws Exception {
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        SoftAssert softAssert = new SoftAssert();

        WEPPartnerLicensesFlowPage wexPartnerLicensesPage = new WEPPartnerLicensesFlowPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");

        Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CustomersTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers")), "Customers tab text is not matching");
        LOGGER.info("Customers tab text has been successfully verified.");

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomersTab"), "Customers tab is not clickable");
        LOGGER.info("Navigated successfully to Customers page");

        String[] licenseTiers1 = {"DNDStandard"};

        softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerSearch"), "Customers search box is not clickable");
        LOGGER.info("Customers search box clicked");

        for (String tier1 : licenseTiers1) {
            wexPartnerLicensesPage.enterTextForWEPPartnerLicensesFlowPage("CustomerSearch", tier1);
            LOGGER.info("Entered search term: " + tier1);
            waitForPageLoaded();

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("DNDStandard"), "Cannot Find DNDStandard Customer");
            LOGGER.info("Standard customer identified on the Customer List page.");
            waitForPageLoaded();

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("CustomerLicense", getTextLanguage(LanguageCode, "daas_ui", "partner.licenses")), "License Tab for customer not available on customer detail page");
            LOGGER.info("Successfully switched to the Licenses tab on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("CustomerLicense"), "License Tab for customer not available on customer detail page");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgrade", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade button available on customer detail page");
            LOGGER.info("Successfully verified that the Standard Plan display the Upgrade Tier button on the Customer Details page.");

            Assert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("TierUpgrade"), "Tier Upgrade button is not clickable");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgradeHeading", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade")), "Tier Upgrade Heading not available on modal");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("TierUpgradeDesc", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.desc")), "Tier Upgrade Heading not available on modal");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_standard.short")), "Standard Heading not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.desc")), "upgrade.modal.standard_card.desc Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.hardware_software_inventory")), "hardware_software_inventory Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.windows_11_readiness")), "windows_11_readiness Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.api_access")), "features.api_access Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.remediations&automations")), "remediations&automations Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription6", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.experience_analytics")), "experience_analytics Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription7", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.employee_survey&notifications")), "employee_survey&notifications Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("StandardDescription8", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.standard_card.features.third_party-integrations")), "third_party-integrations Description not available on modal");

            LOGGER.info("Successfully Validated all description for Standard Plan on License tier Modal.");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_pro.short")), "Pro Heading not available on modal");
//            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.desc")), "modal.pro_card.desc description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.subdescription")), "pro_card.subdescription description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.experience_analytics")), "experience_analytics description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.employee_survey&notifications")), "employee_survey&notifications description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.configurable_monitoring&alerting")), "configurable_monitoring&alerting description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription6", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.smart_device_refresh")), "smart_device_refreshdescription not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription7", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.natural_language_search")), "smart_device_refresh description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("ProDescription8", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.ai_enabled")), "ai_enabled description not available on modal");

            LOGGER.info("Successfully Validated all description for Pro Plan on License tier Modal.");

            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteHeading", getTextLanguage(LanguageCode, "daas_ui", "subscriptions.plan.workforce_elite.short")), "Standard Heading not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.desc")), "elite_card.desc Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.subdescription")), "elite_card.subdescription Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription3", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.features.natural_language_search")), "elite_card.features.natural_language_search Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription4", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.elite_card.features.free_text_analysis")), "elite_card.features.free_text_analysis Description not available on modal");
            Assert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("EliteDescription5", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.pro_card.features.ai_enabled")), "ai_enabled Description not available on modal");

            LOGGER.info("Successfully Validated all description for Elite Plan on License tier Modal.");

            softAssert.assertTrue(wexPartnerLicensesPage.matchTextOfWEPPartnerLicensesFlowPage("SeeDetailsButton", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.tier_upgrade.modal.buttonText")), "See Details button not available");

            softAssert.assertTrue(wexPartnerLicensesPage.clickOnElementsOfWEPPartnerLicensesFlowPage("SeeDetailsButton"), "See details button not clickable");
            sleeper(2000);
            wexPartnerLicensesPage.switchToDifferentTab();
            waitForPageLoaded();// Url takes time to load
            String Actual_URL = wexPartnerLicensesPage.getUrlOfCurrentPage();
            String Expected_URL = ConstantURL.SEE_DETAILS_LINK;
            softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Pricing Page is not matching");

            String ActualPage_Title = wexPartnerLicensesPage.getBrowserTabName();
            String Expected_Title = "Pricing - HP Workforce Experience Platform";
            softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Pricing - HP Workforce Experience Platform is not matching");
            LOGGER.info("Actual Title is :" + ActualPage_Title);

            wexPartnerLicensesPage.switchBackToPreviousTab();
            LOGGER.info("Validation of Pricing - HP Workforce Experience Platform.");
            LOGGER.info("Actual URL is :" + Actual_URL);
            LOGGER.info("Expected URL is :" + Expected_URL);
            LOGGER.info("Successfully validated Pricing - HP Workforce Experience Platform URL");

            softAssert.assertAll();
            LOGGER.info("Verified Pricing - HP Workforce Experience Platform successfully");

            softAssert.assertAll();
        }
    }
}