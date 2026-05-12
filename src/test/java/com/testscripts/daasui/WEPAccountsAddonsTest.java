package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.daasui.pages.WEPAccountsAddonsPage;

public final class WEPAccountsAddonsTest extends CommonTest {
    private static final Logger LOGGER = LogManager.getLogger(WEPAccountsAddonsTest.class);
    public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
    public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
    public static String UserEmail = getEnvironmentSpecificData(System.getProperty("environment"),
            "WEP_USER_EMAIL_SEARCH");

    /**
     * This test case is to verify addon Customer Login
     *
     * @throws Exception
     */
    @Test(priority = 1, groups = {"REGRESSION_SUBSCRIBE", "PRODUCTION_SUBSCRIBE"}, description = "TestCase ID : ")
    public final void verifyWEPAccountAddonsPages() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        WEPAccountsAddonsPage page = new WEPAccountsAddonsPage();

        login("WXP_PRO_COMPANY_EMAIL", "WXP_PRO_COMPANY_PASSWORD");
        leftSideMenuVerification();
        Assert.assertTrue(page.navigateToAddonsMenu(), "Able to navigate to Addons tab.");

        // Check all 3 addons are present
        // 1. HP Anyware
        var anywarePresent = page.verifyAddonsElementIsPresent("addonAnyware");
        softAssert.assertTrue(anywarePresent, "HP Anyware addon listing is present on addons page.");
        if (anywarePresent) {
            LOGGER.info("Verified HP Anyware addon listing present. Checking addon details page.");
            Assert.assertTrue(page.navigateToAddonDetails("addonAnyware"), "Able to navigate to HP Anyware addon details page.");
            page.assertAnywareAddonDetails();
            Assert.assertTrue(page.navigateToAddonsMenu(), "Able to navigate to Addons tab.");

        }
        // 2. Vyopta
        var vyoptaPresent = page.verifyAddonsElementIsPresent("addonVyopta");
        softAssert.assertTrue(vyoptaPresent, "Vyopta addon listing is present on addons page.");
        if (vyoptaPresent) {
            // Validate that connect feature exists for Vyopta addon

            LOGGER.info("Verified Vyopta addon listing present. Checking addon details page.");
            Assert.assertTrue(page.navigateToAddonDetails("addonVyopta"),
                    "Able to navigate to Vyopta addon details page.");

            page.assertVyoptaAddonDetails();
            Assert.assertTrue(page.navigateToAddonsMenu(), "Able to navigate to Addons tab.");
       }

        // 3. WPT
        var wptPresent = page.verifyAddonsElementIsPresent("addonWPT");
        softAssert.assertTrue(wptPresent, "HP WPT addon listing is present on addons page.");
        if (wptPresent) {
            LOGGER.info("Verified HP WPT addon listing present. Checking addon details page.");
            Assert.assertTrue(page.navigateToAddonDetails("addonWPT"),
                    "Able to navigate to HP WPT addon details page.");
            page.assertWPTAddonDetails();
            Assert.assertTrue(page.navigateToAddonsMenu(), "Able to navigate to Addons tab.");
        }

        // 4. BTG TBA

        softAssert.assertAll();
    }
}
