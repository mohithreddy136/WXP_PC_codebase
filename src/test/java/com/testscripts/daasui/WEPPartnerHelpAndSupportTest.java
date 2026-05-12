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
import java.sql.SQLOutput;

public class WEPPartnerHelpAndSupportTest extends CommonTest {

    private static Logger LOGGER = LogManager.getLogger(WEPPartnerHelpAndSupportTest.class);

    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX","PRODUCTION_PARTNERCX"}, description = "TestCase ID : 101")
    public final void verifyHelpAndSupportCard() throws Exception {

        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
                .getInstance();
        SoftAssert softAssert = new SoftAssert();
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
        waitForPageLoaded();
        LOGGER.info("Redirected to the help and support page");

        // Validating the section may assistance
        softAssert.assertTrue(
                helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("NeedassistancewithWEX",
                        getTextLanguage(LanguageCode, "daas_ui", "help.support.section.heading.title")),
                "Need assistance with WEX tile is incorrect");
        softAssert.assertTrue(
                helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("EmailDescription",
                        getTextLanguage(LanguageCode, "daas_ui", "help.support.section.Email.description")),
                " Email description is incorrect");

        LOGGER.info("Validation for the Need assistance with WEX is completed");
    }


    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 102")
    public final void verifyHelpAndSupportLinks() throws Exception {

        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
                .getInstance();
        SoftAssert softAssert = new SoftAssert();
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
        waitForPageLoaded();
        LOGGER.info("Redirected to the help and support page");
        waitForPageLoaded();

        // Validation for the getting started

        softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("GettingStartedLabel"),
                "Getting Started Title is incorrect");
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("GettingStartedLabel");
        sleeper(2000);
        helpAndSupportPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        String Actual_URL= helpAndSupportPage.getUrlOfCurrentPage();
        String Expected_URL = ConstantURL.Get_Started_Partner;
        softAssert.assertTrue(
                (Actual_URL.contains( Expected_URL)),
                "URL for Getting Started  is not matching");
        helpAndSupportPage.switchBackToPreviousTab();
        LOGGER.info("Validation of Getting Started Tile completed.");
        System.out.println("Actual URL is :" + Actual_URL);
        System.out.println(Expected_URL);
        waitForPageLoaded();

        // Validation for software download tile

        softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("SoftwareDownloadLabel"),
                "Software download Title is incorrect");
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("SoftwareDownloadLabel");
        sleeper(2000);
        helpAndSupportPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        softAssert.assertTrue(
                (getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.SOFTWARE_DOWNLOAD + "/"
                        + LanguageCode).equals(helpAndSupportPage.getUrlOfCurrentPage()),
                "URL for software download link is not matching");
        helpAndSupportPage.switchBackToPreviousTab();
        LOGGER.info("Validation of Software Download Tile completed.");
        waitForPageLoaded();

        // Validation for knowledge base tile

        softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("KnowledgeBaseLabel"),
                "Knowledge Base Title is incorrect");
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("KnowledgeBaseLabel");
        sleeper(2000);
        helpAndSupportPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        String Actual_URL1= helpAndSupportPage.getUrlOfCurrentPage();
        String Expected_URL1 = ConstantURL.KnowLEDGE_BASE_Partner;
        softAssert.assertTrue(
                (Actual_URL1.contains( Expected_URL1)),
                "URL for Knowledge Base  is not matching");
        helpAndSupportPage.switchBackToPreviousTab();
        LOGGER.info("Validation of Knowledge Base Tile completed.");
        waitForPageLoaded();

        // Validation for software requirement tile

        softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("SystemRequirementsLabel"),
                "System Requirements Title is incorrect");
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("SystemRequirementsLabel");
        sleeper(2000);
        helpAndSupportPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        softAssert.assertTrue(
                (getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.SYSTEM_REQUIREMENTS + "/"
                        + LanguageCode).equals(helpAndSupportPage.getUrlOfCurrentPage()),
                "URL for System Requirements link is not matching");
        helpAndSupportPage.switchBackToPreviousTab();
        LOGGER.info("Validation of System Requirements Tile completed.");
        waitForPageLoaded();

        // Validation for the Send Feedback card on Help and Support page.
        softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("Supportcase"),
                "Supportcase Title is incorrect");
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("Supportcase");
        sleeper(2000);
        helpAndSupportPage.switchToDifferentTab();
        waitForPageLoaded();
        String url = helpAndSupportPage.getUrlOfCurrentPage();
        softAssert.assertTrue((url).equals(ConstantURL.Support_Case_Form));
        helpAndSupportPage.switchBackToPreviousTab();
        softAssert.assertAll();
        LOGGER.info("Validation of Support Cases from Dashboard has been completed successfully.");
        waitForPageLoaded();

        // Validation for What's New tile

        softAssert.assertTrue(helpAndSupportPage.verifyElementsOfWEXHelpAndSupportPage("whatsNewTileLabel"),
                "whats new Title is incorrect");
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("whatsNewTileLabel");
        sleeper(2000);
        helpAndSupportPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        softAssert.assertTrue(
                (getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.WHATS_NEW + "/"
                        + LanguageCode).equals(helpAndSupportPage.getUrlOfCurrentPage()),
                "URL for Whats New link is not matching");
        LOGGER.info("Validation of the What's New Tile completed.");
        waitForPageLoaded();

    }

    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX","PRODUCTION_PARTNERCX"}, description = "TestCase ID : 4439513")
    public final void verifyHelpAndSupportTab() throws Exception {

        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
                .getInstance();
        SoftAssert softAssert = new SoftAssert();
        helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
        waitForPageLoaded();
        LOGGER.info("Redirected to help and support page");
        waitForPageLoaded();

        softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("getsupporttile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.get_started.title")), "get support tile are not loading on Help and Support page.");
        softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("systemrequirementstile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.system_requirements.title")), "System requirement tile are not loading on Help and Support page.");
        softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("knowledgebasetile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.academy.title")), "knowlodge base tile are not loading on Help and Support page.");
        softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("softwaredownload").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.software_download.title")), "software page tile are not loading on Help and Support page.");
        softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("whatsnewtile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.whats_new.title")), "what's new tile are not loading on Help and Support page.");
        softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("SupportCaseTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.my_support_cases.title")), "Send Feedback description are not loading on Help and Support page.");

    }
}

