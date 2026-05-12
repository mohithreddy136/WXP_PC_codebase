package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WEPPartnerCustomersPage;
import com.daasui.pages.WEXPartnerDashboardPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Set;

public class WXPPartnerAddonsTest extends CommonTest {
    private static final Logger LOGGER = LogManager.getLogger(com.testscripts.daasui.WXPPartnerAddonsTest.class);

    /**
     * This method will verify the addons banner on partner dashboard
     */
    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 44395131")
    public final void verifyPartnerAddonsBanner() throws Exception {
        login("ADD_ONS_PARTNER_EMAIL_WXP", "ADD_ONS_PARTNER_PASS_WXP");
        SoftAssert softAssert = new SoftAssert();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("addonsBannerIcon"), "Addons banner icon is not present on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("addonsBannerTitle", getTextLanguage(LanguageCode, "daas_ui", "wxp.upgradePlanCard.partner.title")), "Addons banner title text does not match expected value");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("addonsBannerDescription", getTextLanguage(LanguageCode, "daas_ui", "wxp.upgradePlanCard.partner.desc")), "Addons banner description text does not match expected value");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("addonsBannerButton", getTextLanguage(LanguageCode, "daas_ui", "global.learn.more")), "Addons banner button text does not match expected value");

        // Click on addons banner button and verify redirection
        String parentWindow = PreDefinedActions.getDriver().getWindowHandle();
        softAssert.assertTrue(wexPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("addonsBannerButton"), "Failed to click on addons banner button");

        // Switch to new tab
        switchToDifferentTab();

        // Verify URL contains "-platform-overview/"
        String currentUrl = PreDefinedActions.getDriver().getCurrentUrl();
        softAssert.assertTrue(currentUrl.contains("/platform-overview/"), "URL does not contain '/platform-overview/', actual URL: " + currentUrl);

        // Verify text on redirected page
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("addonsLearnMoreButtonRedir"), "Addons learn more redirection page element is not present");

        // Close new tab and switch back to parent window
        PreDefinedActions.getDriver().close();
        PreDefinedActions.getDriver().switchTo().window(parentWindow);

        softAssert.assertAll();
        LOGGER.info("Partner addons banner verified successfully");
    }
    /**
     * This method will verify the Endpoints Growth Widget on partner dashboard
     */
    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID: 44466846")
    public final void verifyEndpointsGrowthWidget() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("ADD_ONS_PARTNER_EMAIL_WXP", "ADD_ONS_PARTNER_PASS_WXP");
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("customersByLocationLegend1");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("endpointsGrowthTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.partner_dashboard.tile.title.endpoints_by_subscription")),"Endpoints Growth card is not available on partner dashboard");

        /*
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("endpointsGrowthXaxis"), "endpointsGrowthXaxis locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("endpointsGrowthYaxis"), "endpointsGrowthYaxis locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("endpointsGrowthWidget"), "endpointsGrowthFullGraph4 locator is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("endpointsGrowthFullGraphPlot"), "endpointsGrowthFullGraph6 locator is not present");
        */

        softAssert.assertAll();
        LOGGER.info("The endpoints growth widget verified successfully");
    }

    /**
     * This method will verify the Customers by Location Widget on partner dashboard
     */
    @Test(priority = 3, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID: 44468599")
    public final void verifyCustomersByLocationWidget() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("ADD_ONS_PARTNER_EMAIL_WXP", "ADD_ONS_PARTNER_PASS_WXP");
        WEXPartnerDashboardPage wexPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
        WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();

        softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
        wexPartnerDashboardPage.scrollTillViewOfWEXPartnerDashboardPage("customersByLocationLegend1");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("customersByLocationTitle", getTextLanguage(LanguageCode, "daas_ui", "wex.partner_dashboard.tile.title.customers_by_location")),"Customers by Location widget is not available on partner dashboard");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("customersByLocationWidget"), "customersByLocationWidget  is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("customersByLocationFullMap"), "customersByLocationFullMap  is not present");
        softAssert.assertTrue(wexPartnerDashboardPage.verifyElementsOfWEXPartnerDashboardPage("customersByLocationMapWidget"), "customersByLocationMapWidget  is not present");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("customersByLocationLegend1", getTextLanguage(LanguageCode, "daas_ui", "customer.bylocation.lessfifty.endpoint")), "<50 endpoints is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("customersByLocationLegend2", getTextLanguage(LanguageCode, "daas_ui", "customer.bylocation.betweenfiftyhundred.endpoint")),"50-100 endpoints is not available on partner dashboard");
        Assert.assertTrue(wexPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("customersByLocationLegend3", getTextLanguage(LanguageCode, "daas_ui", "customer.bylocation.greaterhundred.endpoint")),">100 endpoints is not available on partner dashboard");

        softAssert.assertAll();
        LOGGER.info("The Customers by Location widget verified successfully");
    }

}
