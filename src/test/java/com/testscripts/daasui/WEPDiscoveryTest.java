package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WEPDiscoveryPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WEPDiscoveryTest  extends CommonTest {

    private static Logger LOGGER = LogManager.getLogger(WEPPartnerHelpAndSupportTest.class);


    @Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCase ID : C57593903")
    public final void verifyAlertsDiscoverPage() throws Exception {

        login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");
        WEPDiscoveryPage WEPDiscoveryPage = new WEPDiscoveryPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("dicoverypagetitle", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.unlock_upgrade.title")), "Side menu discovery pages tile is incorrect");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Alertstab");
        WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("Alertstab");
        waitForPageLoaded();
        LOGGER.info("Redirected to the Alerts discovery page");

        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Alertsdiscoverypagetitle", getTextLanguage(LanguageCode, "daas_ui", "alerts.discoveryPage.title")), "Alerts discovery page title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Alertsdiscoverypagesubtitle", getTextLanguage(LanguageCode, "daas_ui", "alerts.discoveryPage.description")), "Alerts discovery page descriptiom is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Alertsdicoverypagecard1image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Alertsdicoverypagecard1title", getTextLanguage(LanguageCode, "daas_ui", "alerts.discoveryPage.card_1.title")), "Alerts discovery page card 1 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Alertsdicoverypagecard1description", getTextLanguage(LanguageCode, "daas_ui", "alerts.discoveryPage.card_1.description")), "Alerts discovery page card 1 description is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Alertsdicoverypagecard2image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Alertsdicoverypagecard2title", getTextLanguage(LanguageCode, "daas_ui", "alerts.discoveryPage.card_2.title")), "Alerts discovery page card 2 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Alertsdicoverypagecard2description", getTextLanguage(LanguageCode, "daas_ui", "alerts.discoveryPage.card_2.description")), "Alerts discovery page card 2 description is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Alertsdicoverypagecard3image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Alertsdicoverypagecard3title", getTextLanguage(LanguageCode, "daas_ui", "alerts.discoveryPage.card_3.title")), "Alerts discovery page card 3 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Alertsdicoverypagecarddescription", getTextLanguage(LanguageCode, "daas_ui", "alerts.discoveryPage.card_3.description")), "Alerts discovery page card 3 description is incorrect");
        sleeper(2000);
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("learnmorebutton", getTextLanguage(LanguageCode, "daas_ui", "global.learn.more")), "learn more button title is incorrect");
        if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")) {
            WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("learnmorebutton");
            switchToDifferentTab();
            waitForPageLoaded();
            String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.Platform;
            softAssert.assertTrue(url.equals(WEPDiscoveryPage.getUrlOfCurrentPage()), "learn more URL not matching");
            switchBackToPreviousTab();
        }
        else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")){
            WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("learnmorebutton");
            switchToDifferentTab();
            waitForPageLoaded();
            String ActurlOfCurrentPage  = WEPDiscoveryPage.getUrlOfCurrentPage();
            Assert.assertEquals(ActurlOfCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_LEARN_MORE_URL"),"URL of the Policy List Page is not matching for New flow");
            switchBackToPreviousTab();
        }

        softAssert.assertAll();
        LOGGER.info("Alerts discovery pages verification is completed");
    }


    @Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCase ID : C57593904")
    public final void verifyScriptsDiscoverPage() throws Exception {

        login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");
        WEPDiscoveryPage WEPDiscoveryPage = new WEPDiscoveryPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("scriptstab");
        WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("scriptstab");
        waitForPageLoaded();
        LOGGER.info("Redirected to the Scripts discovery page");

        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptsdiscoverypagetitle", getTextLanguage(LanguageCode, "daas_ui", "scripts.discoveryPage.title")), "Scripts discovery page title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptsdiscoverypagesubtitle", getTextLanguage(LanguageCode, "daas_ui", "scripts.discoveryPage.description")), "Scripts discovery page descriptiom is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("scriptsdicoverypagecard1image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptsdicoverypagecard1title", getTextLanguage(LanguageCode, "daas_ui", "scripts.discoveryPage.card_1.title")), "scripts discovery page card 1 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptsdicoverypagecard1description", getTextLanguage(LanguageCode, "daas_ui", "scripts.discoveryPage.card_1.description")), "scripts discovery page card 1 description is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("scriptsdicoverypagecard2image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptsdicoverypagecard2title", getTextLanguage(LanguageCode, "daas_ui", "scripts.discoveryPage.card_2.title")), "scripts discovery page card 2 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptsdicoverypagecard2description", getTextLanguage(LanguageCode, "daas_ui", "scripts.discoveryPage.card_2.description")), "scripts discovery page card 2 description is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("scriptsdicoverypagecard3image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptsdicoverypagecard3title", getTextLanguage(LanguageCode, "daas_ui", "scripts.discoveryPage.card_3.title")), "scripts discovery page card 3 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptsdicoverypagecarddescription", getTextLanguage(LanguageCode, "daas_ui", "scripts.discoveryPage.card_3.description")), "scripts discovery page card 3 description is incorrect");
        sleeper(2000);
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("scriptslearnmorebutton", getTextLanguage(LanguageCode, "daas_ui", "global.learn.more")), "learn more button title is incorrect");

        if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")) {
            WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("scriptslearnmorebutton");
            switchToDifferentTab();
            waitForPageLoaded();
            String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.Platform;
            softAssert.assertTrue(url.equals(WEPDiscoveryPage.getUrlOfCurrentPage()), "learn more URL not matching");
            switchBackToPreviousTab();
        }
        else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")){
            WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("scriptslearnmorebutton");
            switchToDifferentTab();
            waitForPageLoaded();
            String ActurlOfCurrentPage  = WEPDiscoveryPage.getUrlOfCurrentPage();
            Assert.assertEquals(ActurlOfCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_LEARN_MORE_URL"),"URL of the Policy List Page is not matching for New flow");
            switchBackToPreviousTab();
        }

        softAssert.assertAll();
        LOGGER.info("scripts discovery pages verification is completed");
    }

    @Test(priority = 3, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCase ID : C57593905")
    public final void verifyPulsesDiscoverPage() throws Exception {

        login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");
        WEPDiscoveryPage WEPDiscoveryPage = new WEPDiscoveryPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Pulsestab");
        WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("Pulsestab");
        waitForPageLoaded();
        LOGGER.info("Redirected to the pulses discovery page");

        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulsesdiscoverypagetitle", getTextLanguage(LanguageCode, "daas_ui", "pulse.discoveryPage.title")), "pulse discovery page title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulsesdiscoverypagesubtitle", getTextLanguage(LanguageCode, "daas_ui", "pulse.discoveryPage.description")), "pulse discovery page descriptiom is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Pulsesdicoverypagecard1image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulsesdicoverypagecard1title", getTextLanguage(LanguageCode, "daas_ui", "pulse.discoveryPage.card_1.title")), "pulse discovery page card 1 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulsesdicoverypagecard1description", getTextLanguage(LanguageCode, "daas_ui", "pulse.discoveryPage.card_1.description")), "pulse discovery page card 1 description is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Pulsesdicoverypagecard2image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulsesdicoverypagecard2title", getTextLanguage(LanguageCode, "daas_ui", "pulse.discoveryPage.card_2.title")), "pulse discovery page card 2 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulsesdicoverypagecard2description", getTextLanguage(LanguageCode, "daas_ui", "pulse.discoveryPage.card_2.description")), "pulse discovery page card 2 description is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Pulsesdicoverypagecard3image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulsesdicoverypagecard3title", getTextLanguage(LanguageCode, "daas_ui", "pulse.discoveryPage.card_3.title")), "pulse discovery page card 3 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulsesdicoverypagecarddescription", getTextLanguage(LanguageCode, "daas_ui", "pulse.discoveryPage.card_3.description")), "pulse discovery page card 3 description is incorrect");
        sleeper(2000);
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Pulseslearnmorebutton", getTextLanguage(LanguageCode, "daas_ui", "global.learn.more")), "learn more button title is incorrect");

        if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")) {
            WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("Pulseslearnmorebutton");
            switchToDifferentTab();
            waitForPageLoaded();
            String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.Platform;
            softAssert.assertTrue(url.equals(WEPDiscoveryPage.getUrlOfCurrentPage()), "learn more URL not matching");
            switchBackToPreviousTab();
        }
        else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")){
            WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("Pulseslearnmorebutton");
            switchToDifferentTab();
            waitForPageLoaded();
            String ActurlOfCurrentPage  = WEPDiscoveryPage.getUrlOfCurrentPage();
            Assert.assertEquals(ActurlOfCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_LEARN_MORE_URL"),"URL of the Policy List Page is not matching for New flow");
            switchBackToPreviousTab();
        }

        softAssert.assertAll();
        LOGGER.info("Pulses discovery pages verification is completed");
    }

    @Test(priority = 4, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCase ID : C57593906")
    public final void verifyLabsDiscoverPage() throws Exception {

        login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");
        WEPDiscoveryPage WEPDiscoveryPage = new WEPDiscoveryPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Labstab");
        WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("Labstab");
        waitForPageLoaded();
        LOGGER.info("Redirected to the labs discovery page");

        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labsdiscoverypagetitle", getTextLanguage(LanguageCode, "daas_ui", "labs.discoveryPage.title")), "Scripts discovery page title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labsdiscoverypagesubtitle", getTextLanguage(LanguageCode, "daas_ui", "labs.discoveryPage.description")), "Scripts discovery page descriptiom is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Labsdicoverypagecard1image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labsdicoverypagecard1title", getTextLanguage(LanguageCode, "daas_ui", "labs.discoveryPage.card_1.title")), "scripts discovery page card 1 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labsdicoverypagecard1description", getTextLanguage(LanguageCode, "daas_ui", "labs.discoveryPage.card_1.description")), "scripts discovery page card 1 description is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Labsdicoverypagecard2image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labsdicoverypagecard2title", getTextLanguage(LanguageCode, "daas_ui", "labs.discoveryPage.card_2.title")), "scripts discovery page card 2 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labsdicoverypagecard2description", getTextLanguage(LanguageCode, "daas_ui", "labs.discoveryPage.card_2.description")), "scripts discovery page card 2 description is incorrect");
        sleeper(2000);
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("Labsdicoverypagecard3image");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labsdicoverypagecard3title", getTextLanguage(LanguageCode, "daas_ui", "labs.discoveryPage.card_3.title")), "scripts discovery page card 3 title is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labsdicoverypagecarddescription", getTextLanguage(LanguageCode, "daas_ui", "labs.discoveryPage.card_3.description")), "scripts discovery page card 3 description is incorrect");
        sleeper(2000);
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Labslearnmorebutton", getTextLanguage(LanguageCode, "daas_ui", "global.learn.more")), "learn more button title is incorrect");

        if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")) {
            WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("Labslearnmorebutton");
            switchToDifferentTab();
            waitForPageLoaded();
            String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.Platform;
            softAssert.assertTrue(url.equals(WEPDiscoveryPage.getUrlOfCurrentPage()), "learn more URL not matching");
            switchBackToPreviousTab();
        }
        else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
                System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")){
            WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("Labslearnmorebutton");
            switchToDifferentTab();
            waitForPageLoaded();
            String ActurlOfCurrentPage  = WEPDiscoveryPage.getUrlOfCurrentPage();
            Assert.assertEquals(ActurlOfCurrentPage,getEnvironmentSpecificData(System.getProperty("environment"),"WEX_LEARN_MORE_URL"),"URL of the Policy List Page is not matching for New flow");
            switchBackToPreviousTab();
        }

        softAssert.assertAll();
        LOGGER.info("Pulses discovery pages verification is completed");
    }


    @Test(priority = 5, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCase ID : C57593908")
    public final void verifyDashboardwidgetsequenceForStandardPlan() throws Exception {

        login("WXP_STANDARD_COMPANY_EMAIL", "WXP_STANDARD_COMPANY_PASSWORD");
        WEPDiscoveryPage WEPDiscoveryPage = new WEPDiscoveryPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        waitForPageLoaded();
        LOGGER.info("Redirected home dashboard page");

        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("experiencescorestandardplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("experienceovertimestandardplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("recommendedactionsliststandardplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("fleetinventorystandardplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("appwithmostcrashesstandardplan");
        sleeper(2000);
        WEPDiscoveryPage.clickOnElementsOfWEPDiscoveryPage("appwithmostcrashessampledatastandardplan");
        WEPDiscoveryPage.MouseOverOfWEPDiscoveryPage("appwithmostcrashessampledatastandardplan");
        for (int i = 0; i < 3; i++) {
            if (WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("sampledatatooltip")) {
                break;
            }
            Thread.sleep(500);
        }

        String actual = WEPDiscoveryPage.getTextOfWEPDiscoveryPage("sampledatatooltip");
        String expected = getTextLanguage(LanguageCode, "daas_ui", "wex.global.sample_data_tooltip");
        softAssert.assertTrue(actual.trim().equalsIgnoreCase(expected.trim()), "Sample data tooltip is incorrect");
        softAssert.assertAll();
    }


    @Test(priority = 6, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCase ID : C57593908")
    public final void verifyDashboardwidgetsequenceForProPlan() throws Exception {

        login("WXP_PRO_COMPANY_EMAIL", "WXP_PRO_COMPANY_PASSWORD");
        WEPDiscoveryPage WEPDiscoveryPage = new WEPDiscoveryPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        waitForPageLoaded();
        LOGGER.info("Redirected home dashboard page");

        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("experiencescoreProplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("experienceovertimeproplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("recommendedactionslistproplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("fleetinventoryproplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("appwithmostcrashesproplan");
        softAssert.assertAll();
    }


    @Test(priority = 7, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCase ID : C57593908")
    public final void verifyDashboardwidgetsequenceForElitePlan() throws Exception {

        login("WXP_ELITE_COMPANY_EMAIL", "WXP_ELITE_COMPANY_PASSWORD");
        WEPDiscoveryPage WEPDiscoveryPage = new WEPDiscoveryPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        waitForPageLoaded();
        LOGGER.info("Redirected home dashboard page");

        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("experiencescoreProplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("experienceovertimeproplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("recommendedactionslistproplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("fleetinventoryproplan");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("appwithmostcrashesproplan");
        softAssert.assertAll();
    }


    @Test(priority = 8, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCase ID : C57593908")
    public final void verifyDashboardwidgetsequenceForStarterPlan() throws Exception {

        login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");
        WEPDiscoveryPage WEPDiscoveryPage = new WEPDiscoveryPage(PreDefinedActions.getDriver()).getInstance();
        SoftAssert softAssert = new SoftAssert();
        waitForPageLoaded();
        LOGGER.info("Redirected home dashboard page");

        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Bannercontentheading", getTextLanguage(LanguageCode, "daas_ui", "wxp.customer.dashboard.starter.banner.content.heading")), "Starter plan banner heading is incorrect");
        softAssert.assertTrue(WEPDiscoveryPage.matchTextOfWEPDiscoveryPage("Bannercontentheadingdescription", getTextLanguage(LanguageCode, "daas_ui", "wxp.customer.dashboard.starter.banner.content.description")), "Starter plan banner description is incorrect");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("hardwaresupportincident");
        WEPDiscoveryPage.verifyElementsOfWEPDiscoveryPage("fleetinventorywidget");
        softAssert.assertAll();
    }
}
