package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.WEPPartnerExperienceSnapshotPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WEPPartnerExperienceSnapshotTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WEPPartnerExperienceSnapshotTest.class);

    @Test(priority = 1, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0001")
    public final void verifyExperienceSnapshot() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerExperienceSnapshotPage WEPPartnerExperienceSnapshotPage = new WEPPartnerExperienceSnapshotPage(PreDefinedActions.getDriver()).getInstance();
        if (WEPPartnerExperienceSnapshotPage.verifyElementsOfWEPPartnerExperienceSnapshotPage("whatsNewPopupClosedButton")) {
            WEPPartnerExperienceSnapshotPage.clickOnElementsOfWEPPartnerExperienceSnapshotPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        if (!WEPPartnerExperienceSnapshotPage.matchTextOfWEPPartnerExperienceSnapshotPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerExperienceSnapshotPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            LOGGER.info("Partner has selected the All Customers view");
        }
        softAssert.assertTrue(WEPPartnerExperienceSnapshotPage.verifyElementsOfWEPPartnerExperienceSnapshotPage("experienceSnapshotHeader"), "Experience Snapshot header is not displayed");
        WEPPartnerExperienceSnapshotPage.waitForElementsOnWEPPartnerExperienceSnapshotPage("GreatExperienceTab");
        WEPPartnerExperienceSnapshotPage.waitForElementsOnWEPPartnerExperienceSnapshotPage("FairExperienceTab");
        WEPPartnerExperienceSnapshotPage.waitForElementsOnWEPPartnerExperienceSnapshotPage("PoorExperienceTab");
        softAssert.assertTrue(WEPPartnerExperienceSnapshotPage.verifyElementsOfWEPPartnerExperienceSnapshotPage("GreatExperienceTab"), "Great Experience Tab is not displayed");
        softAssert.assertTrue(WEPPartnerExperienceSnapshotPage.verifyElementsOfWEPPartnerExperienceSnapshotPage("FairExperienceTab"), "Fair Experience Tab is not displayed");
        softAssert.assertTrue(WEPPartnerExperienceSnapshotPage.verifyElementsOfWEPPartnerExperienceSnapshotPage("PoorExperienceTab"), "Poor Experience Tab is not displayed");
        int greatCount = WEPPartnerExperienceSnapshotPage.getExperienceCount("GreatExperienceCount");
        softAssert.assertTrue(greatCount >= 0, "Great experience count is negative or not displayed");
        int fairCount = WEPPartnerExperienceSnapshotPage.getExperienceCount("FairExperienceCount");
        softAssert.assertTrue(fairCount >= 0, "Fair experience count is negative or not displayed");
        int poorCount = WEPPartnerExperienceSnapshotPage.getExperienceCount("PoorExperienceCount");
        softAssert.assertTrue(poorCount >= 0, "Poor experience count is negative or not displayed");
        LOGGER.info("Experience counts are as follows: Great - " + greatCount + ", Fair - " + fairCount + ", Poor - " + poorCount);
        int totalFromGreat = WEPPartnerExperienceSnapshotPage.getTotalCustomers("GreatTotalCustomers");
        int totalFromFair = WEPPartnerExperienceSnapshotPage.getTotalCustomers("FairTotalCustomers");
        int totalFromPoor = WEPPartnerExperienceSnapshotPage.getTotalCustomers("PoorTotalCustomers");
        softAssert.assertEquals(totalFromGreat, totalFromFair, "Total customer count mismatch between Great and Fair");
        softAssert.assertEquals(totalFromGreat, totalFromPoor, "Total customer count mismatch between Great and Poor");
        int sum = greatCount + fairCount + poorCount;
        softAssert.assertTrue(sum <= totalFromGreat, "Sum of Great, Fair and Poor exceeds total customers");
        if (sum < totalFromGreat) {
            int expectedRemaining = totalFromGreat - sum;
            softAssert.assertTrue(
                    WEPPartnerExperienceSnapshotPage.verifyElementsOfWEPPartnerExperienceSnapshotPage("RemainingInfoButton"),
                    "Remaining customers info button is not displayed");
            WEPPartnerExperienceSnapshotPage.hoverOnElementOfWEPPartnerExperienceSnapshotPage("RemainingInfoButton");
        }
        softAssert.assertAll();
    }

    @Test(priority = 2, groups = {"REGRESSION_PARTNERCX"}, description = "TestCase ID : 0002")
    public final void verifyExperienceTabSelection() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
        WEPPartnerExperienceSnapshotPage WEPPartnerExperienceSnapshotPage = new WEPPartnerExperienceSnapshotPage(PreDefinedActions.getDriver()).getInstance();

        if (WEPPartnerExperienceSnapshotPage.verifyElementsOfWEPPartnerExperienceSnapshotPage("whatsNewPopupClosedButton")) {
            WEPPartnerExperienceSnapshotPage.clickOnElementsOfWEPPartnerExperienceSnapshotPage("whatsNewPopupClosedButton");
        }
        leftSideMenuVerification();
        if (!WEPPartnerExperienceSnapshotPage.matchTextOfWEPPartnerExperienceSnapshotPage("SelectedCustomer", CommonVariables.ALL_CUSTOMER)) {
            WEPPartnerExperienceSnapshotPage.partnerWithCompanyView(CommonVariables.ALL_CUSTOMER, CommonVariables.PARTNER_Home);
            LOGGER.info("Partner has selected the All Customers view");
        }
        //Great Experience Tab Verification
        WEPPartnerExperienceSnapshotPage.waitForElementsOnWEPPartnerExperienceSnapshotPage("GreatExperienceTab");
        int greatCount = WEPPartnerExperienceSnapshotPage.getExperienceCount("GreatExperienceCount");
        if (greatCount>0) {
            WEPPartnerExperienceSnapshotPage.clickOnElementsOfWEPPartnerExperienceSnapshotPage("GreatExperienceTab");
            WEPPartnerExperienceSnapshotPage.waitForElementsOnWEPPartnerExperienceSnapshotPage("ExperienceTable");
            sleeper(5000);
            int greatRowCount = WEPPartnerExperienceSnapshotPage.getExperienceTableRowCount("ExperienceTableRows");
            LOGGER.info("Great Experience Home Page Count: " + greatCount + ", Table Row Count: " + greatRowCount);
            softAssert.assertEquals(greatRowCount, greatCount, "Great experience count does not match table rows");
        } else {
            LOGGER.info("Great Experience count is zero; skipping tab selection verification.");
        }
        //fair Experience Tab Verification
        WEPPartnerExperienceSnapshotPage.clickOnElementsOfWEPPartnerExperienceSnapshotPage("HomePageTab");
        int fairCount = WEPPartnerExperienceSnapshotPage.getExperienceCount("FairExperienceCount");
        if (fairCount > 0) {
            WEPPartnerExperienceSnapshotPage.clickOnElementsOfWEPPartnerExperienceSnapshotPage("FairExperienceTab");
            WEPPartnerExperienceSnapshotPage.waitForElementsOnWEPPartnerExperienceSnapshotPage("ExperienceTable");
            sleeper(5000);
            int fairRowCount = WEPPartnerExperienceSnapshotPage.getExperienceTableRowCount("ExperienceTableRows");
            LOGGER.info("Fair Experience Home Page Count: " + fairCount + ", Table Row Count: " + fairRowCount);
            softAssert.assertEquals(fairRowCount, fairCount, "Fair experience count does not match table rows");
        } else {
            LOGGER.info("Fair Experience count is zero; skipping tab selection verification.");
        }
        //Poor Experience Tab Verification
        WEPPartnerExperienceSnapshotPage.clickOnElementsOfWEPPartnerExperienceSnapshotPage("HomePageTab");
        int poorCount = WEPPartnerExperienceSnapshotPage.getExperienceCount("PoorExperienceCount");
        if (poorCount > 0) {
            WEPPartnerExperienceSnapshotPage.clickOnElementsOfWEPPartnerExperienceSnapshotPage("PoorExperienceTab");
            WEPPartnerExperienceSnapshotPage.waitForElementsOnWEPPartnerExperienceSnapshotPage("ExperienceTable");
            sleeper(5000);
            int poorRowCount = WEPPartnerExperienceSnapshotPage.getExperienceTableRowCount("ExperienceTableRows");
            LOGGER.info("Poor Experience Home Page Count: " + poorCount + ", Table Row Count: " + poorRowCount);
            softAssert.assertEquals(poorRowCount, poorCount, "Poor experience count does not match table rows");
        } else {
            LOGGER.info("Poor Experience count is zero; skipping tab selection verification.");
        }
        WEPPartnerExperienceSnapshotPage.clickOnElementsOfWEPPartnerExperienceSnapshotPage("CustomerTab");
        if (WEPPartnerExperienceSnapshotPage.verifyElementsOfWEPPartnerExperienceSnapshotPage("ClearfilterExperience")) {
            WEPPartnerExperienceSnapshotPage.clickByJavaScriptOnWEPPartnerExperienceSnapshotPage("ClearfilterExperience");
            LOGGER.info("Cleared Experience Score filter from Customer page");
        } else {
            LOGGER.info("Clear Experience filter button not present; skipping cleanup.");
        }
        softAssert.assertAll();
    }
}

