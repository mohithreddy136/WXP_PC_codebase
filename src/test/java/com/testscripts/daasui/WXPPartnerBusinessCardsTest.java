package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WEPPartnerCustomersPage;
import com.daasui.pages.WEXPartnerDashboardPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WXPPartnerBusinessCardsTest extends CommonTest {

    private static final Logger LOGGER = LogManager.getLogger(com.testscripts.daasui.WXPPartnerBusinessCardsTest.class);

    @DataProvider
    public Object[][] partnerEmailProvider() {
        return new Object[][] {
                { "PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP" },
                { "ADD_ONS_PARTNER_EMAIL_WXP", "ADD_ONS_PARTNER_PASS_WXP" },
        };
    }


    /**
     * This method will verify the Enablement Cards on partner dashboard
     */
    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 427350855", dataProvider = "partnerEmailProvider")
    public final void VerifyPartnerEnablementCardsUnlockWXP(String email, String password) throws Exception {
        login(email, password);
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        LOGGER.info("Partner login verified successfully");

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("EnablementCards");
        sleeper(2000);
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EnablementCards", getTextLanguage(LanguageCode, "daas_ui", "wxp.partner.dashboard.certification.card.title")), "Boost your expertise Title mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("EnablementCardsDes", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.certifications.card.description")), "Boost your expertise Card Description mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("LearnMoreButton"), "Learn more button is not available");
        LOGGER.info("Learn More button present for Boost your expertise with HP Solutions certifications card");

        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LearnMoreButton"),"Learn More is not clickable");
        LOGGER.info("Redirected to Certifications Tab in Accounts");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("CertificationsTab1", getTextLanguage(LanguageCode, "daas_ui", "partner.certifications")), "Certifications not found on Accounts Tab ");
        softAssert.assertAll();
        LOGGER.info("Verified Boost your expertise with HP Solutions certifications card successfully");
    }


    /**
     * This method will verify the Watch HP WXP in Action Card on partner dashboard
     */
    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 427350855", dataProvider = "partnerEmailProvider")
    public final void VerifyWatchHPWXPActionPartnerEnablementCards(String email, String password) throws Exception {
       login(email, password);
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        LOGGER.info("Partner login verified successfully");

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("WatchHPCard");
        sleeper(2000);
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("WatchHPCard", getTextLanguage(LanguageCode, "daas_ui", "wxp.partner.dashboard.inAction.card.title")), "Watch HP WXP in Action Title mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("WatchHPCardDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.watchAction.card.description")), "Watch HP WXP in Action Card Description mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("WatchWXP"), "Watch button is not available");
        LOGGER.info("Watch button present for Watch HP WXP in Action card");

        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("WatchWXP"),"Watch button is not clickable");
        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();
        String Expected_URL = ConstantURL.WXP_Watch;
        softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for WXP Action  is not matching");

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "Platform Demos | HP Workforce Experience Platform (WXP)";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Learn More is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of WXP Action card completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);
        LOGGER.info("Expected URL is :" + Expected_URL);
        LOGGER.info("Successfully validated WXP URL");
        softAssert.assertAll();
        LOGGER.info("Verified Watch HP WXP in Action card successfully");
    }

    /**
     * This method will verify the Stay in the know Card on partner dashboard
     */
    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4273508597", dataProvider = "partnerEmailProvider")
    public final void VerifyStayInKnowPartnerEnablementCards(String email, String password) throws Exception {
       login(email, password);
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        LOGGER.info("Partner login verified successfully");

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("StayinknowCard");
        sleeper(2000);

        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("StayinknowCard", getTextLanguage(LanguageCode, "daas_ui", "wxp.partner.dashboard.stayInKnow.card.title")), "Stay in the know Title mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("StayinknowCardDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.stayInKnow.card.description")), "Stay in the know Card Description mismatches");
        waitForPageLoaded();
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("LearMore2"), "Learn more button is not available");
        LOGGER.info("Learn More button present for Stay in the know card");

        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LearMore2"),"Learn more button is not available");
        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();
        String Expected_URL = ConstantURL.Stay_In_Know_LearMore;
        softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Learn More is not matching");

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "My Subscription Preferences - HP Partner Portal";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Learn More is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of Stay in the know card completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);
        LOGGER.info("Expected URL is :" + Expected_URL);
        LOGGER.info("Successfully validated Learn more URL");
        softAssert.assertAll();
        LOGGER.info("Verified Watch Stay in the know card successfully");
    }

    /**
     * This method will verify the See the latest posts from our experts Card on partner dashboard
     */
    @Test(priority = 4, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 4273508598", dataProvider = "partnerEmailProvider")
    public final void VerifySeeLatestPostsPartnerEnablementCards(String email, String password) throws Exception {
       login(email, password);
        SoftAssert softAssert = new SoftAssert();

        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        LOGGER.info("Partner login verified successfully");

        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("SeeLatestPostsCard");
        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("ScrollButton"),"Scroll Button is not available");
        sleeper(2000);

        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SeeLatestPostsCard", getTextLanguage(LanguageCode, "daas_ui", "wxp.partner.dashboard.blog.card.title")), "See the latest posts from our experts Title mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("SeeLatestPostsDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.empty.state.dashboard.latestPosts.card.description")), "See the latest posts from our experts Card Description mismatches");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementIsEnableOfWEXPartnerDashboardPage("LearMore3"), "Learn more button is not available");
        LOGGER.info("Learn More button present for See the latest posts from our experts card");

        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("LearMore3"),"Learn more button is not available");
        sleeper(2000);
        wexPartnerDashboardPage.switchToDifferentTab();
        waitForPageLoaded();// Url takes time to load
        String Actual_URL = wexPartnerDashboardPage.getUrlOfCurrentPage();
        String Expected_URL = ConstantURL.See_Latest_Post;
        softAssert.assertTrue((Actual_URL.contains(Expected_URL)), "URL for Learn More is not matching");

        String ActualPage_Title = wexPartnerDashboardPage.getBrowserTabName();
        String Expected_Title = "The HP Workforce Experience Blog";
        softAssert.assertTrue((ActualPage_Title.equals(Expected_Title)), "Title for Learn More is not matching");
        LOGGER.info("Actual Title is :" + ActualPage_Title);

        wexPartnerDashboardPage.switchBackToPreviousTab();
        LOGGER.info("Validation of See the latest posts from our experts card completed.");
        LOGGER.info("Actual URL is :" + Actual_URL);
        LOGGER.info("Expected URL is :" + Expected_URL);
        LOGGER.info("Successfully validated Learn more URL");

        softAssert.assertAll();
        LOGGER.info("Verified See the latest posts from our experts Card successfully");
    }
}
