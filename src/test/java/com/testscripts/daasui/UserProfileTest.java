package com.testscripts.daasui;
import com.basesource.action.PreDefinedActions;
import com.daasui.pages.UserProfilePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UserProfileTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(WEXHelpAndSupportTest.class);
    SoftAssert softAssert = new SoftAssert();


    @Test(priority = 1, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C53347643")
    public final void verifyEditTimeZOneField() throws Exception {

        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        UserProfilePage UserProfilePage = new UserProfilePage(PreDefinedActions.getDriver()).getInstance();
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        waitForPageLoaded();
        UserProfilePage.verifyElementsWEPTimeZonePagePage("Timezoneonuserprofile");
        UserProfilePage.verifyElementsWEPTimeZonePagePage("eidttimezoneclick");
        UserProfilePage.clickOnElementsWEPTimeZonePage("eidttimezoneclick");
        softAssert.assertTrue(
                UserProfilePage.matchTextOfWEPTimeZonePage("edittimezonelabel",
                        getTextLanguage(LanguageCode, "daas_ui", "edit.time.zone")),
                " Edit time description is incorrect");
        softAssert.assertTrue(UserProfilePage.matchTextOfWEPTimeZonePage("togglesystemtimezone", getTextLanguage(LanguageCode, "daas_ui", "detect_time_zone_automatically")), " detect time zone automatically description is incorrect");
        UserProfilePage.clickOnElementsWEPTimeZonePage("togglesystimezoneclick");
        softAssert.assertTrue(
                UserProfilePage.matchTextOfWEPTimeZonePage("selecttimezonelabel",
                        getTextLanguage(LanguageCode, "daas_ui", "select_time_zone")),
                " select time zone description is incorrect");
        softAssert.assertTrue(
                UserProfilePage.matchTextOfWEPTimeZonePage("dropdowntimezoneselect",
                        getTextLanguage(LanguageCode, "daas_ui", "select_time_zone_dropdown")),
                " select time zone dropdown description is incorrect");
        UserProfilePage.verifyElementsWEPTimeZonePagePage("savebuttontimezone");
        UserProfilePage.verifyElementsWEPTimeZonePagePage("cancelbuttonrimezone");
        UserProfilePage.clickOnElementsWEPTimeZonePage("savebuttontimezone");
    }

    @Test(priority = 2, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C53365566")
    public final void verifyDefaultTimeZoneIsDisabled() throws Exception {

        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        UserProfilePage UserProfilePage = new UserProfilePage(PreDefinedActions.getDriver()).getInstance();
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        waitForPageLoaded();
        UserProfilePage.clickOnElementsWEPTimeZonePage("eidttimezoneclick");
        waitForPageLoaded();
        Thread.sleep(1000);
        if(UserProfilePage.verifyElementsWEPTimeZonePagePage("togglecheckbasedonvaluefordisbaled")) {
            UserProfilePage.clickOnElementsWEPTimeZonePage("togglesystimezoneclick");
            Thread.sleep(800);
        }
        String TimezoneIsDisabled = UserProfilePage.getAttributesOfWEPTimeZonePage("dropdowntimezoneselect", "readonly");
        softAssert.assertTrue(TimezoneIsDisabled.equalsIgnoreCase("true"), "Time zone dropdown field is not disabled");
        UserProfilePage.clickOnElementsWEPTimeZonePage("savebuttontimezone");
        waitForPageLoaded();
        logout();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        Thread.sleep(1000);
        if(!UserProfilePage.verifyElementsWEPTimeZonePagePage("Timezoneonuserprofile")){
            UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
            UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        }
        waitForPageLoaded();
        UserProfilePage.clickOnElementsWEPTimeZonePage("eidttimezoneclick");
        waitForPageLoaded();
        Thread.sleep(1000);
        String TimezoneIsDisabled2 = UserProfilePage.getAttributesOfWEPTimeZonePage("dropdowntimezoneselect", "readonly");
        softAssert.assertTrue(TimezoneIsDisabled2.equalsIgnoreCase("true"), "Time zone dropdown field is not disabled");
        LOGGER.info("Timezone field is disabled is getting saved as per the preview value after logout");
    }

    @Test(priority = 3, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C41392702")
    public final void verifyDefaultTimeZoneIsenabled() throws Exception {

        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        UserProfilePage UserProfilePage = new UserProfilePage(PreDefinedActions.getDriver()).getInstance();
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        waitForPageLoaded();
        UserProfilePage.clickOnElementsWEPTimeZonePage("eidttimezoneclick");
        if(UserProfilePage.verifyElementsWEPTimeZonePagePage("togglecheckbasedonvalueforEnabled")) {
            UserProfilePage.clickOnElementsWEPTimeZonePage("togglesystimezoneclick");
            Thread.sleep(800);
        }
        String TimezoneIsDisabled = UserProfilePage.getAttributesOfWEPTimeZonePage("dropdowntimezoneselect", "disabled");
        boolean isDisabled = TimezoneIsDisabled != null && TimezoneIsDisabled.equalsIgnoreCase("true");
        softAssert.assertTrue(!isDisabled, "Time zone dropdown field is not enabled");
        UserProfilePage.clickOnElementsWEPTimeZonePage("savebuttontimezone");
        waitForPageLoaded();
        logout();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        Thread.sleep(1000);
        if(!UserProfilePage.verifyElementsWEPTimeZonePagePage("Timezoneonuserprofile")){
            UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
            UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        }
        waitForPageLoaded();
        UserProfilePage.clickOnElementsWEPTimeZonePage("eidttimezoneclick");
        waitForPageLoaded();
        Thread.sleep(1000);
        String TimezoneIsDisabled2 = UserProfilePage.getAttributesOfWEPTimeZonePage("dropdowntimezoneselect", "disabled");
        boolean isDisabled2 = TimezoneIsDisabled2 != null && TimezoneIsDisabled2.equalsIgnoreCase("true");
        softAssert.assertTrue(!isDisabled2, "Time zone dropdown field is not enabled");
        LOGGER.info("Timezone field is enabled is getting saved as per the preview value after logout");
    }

    @Test(priority = 4, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C53347643")
    public final void verifyEditPerferedLanguageField() throws Exception {

        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        UserProfilePage UserProfilePage = new UserProfilePage(PreDefinedActions.getDriver()).getInstance();
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        waitForPageLoaded();
        UserProfilePage.verifyElementsWEPTimeZonePagePage("perferedlanguageuserprofile");
        UserProfilePage.verifyElementsWEPTimeZonePagePage("editperferedlanguageclick");
        UserProfilePage.clickOnElementsWEPTimeZonePage("editperferedlanguageclick");
        softAssert.assertTrue(
                UserProfilePage.matchTextOfWEPTimeZonePage("perferedlanguagelabel",
                        getTextLanguage(LanguageCode, "daas_ui", "edit.time.zone")),
                " Edit time description is incorrect");
        softAssert.assertTrue(UserProfilePage.matchTextOfWEPTimeZonePage("togglesystemperferedlanguage", getTextLanguage(LanguageCode, "daas_ui", "users.langaugae.select_automatically")), " detect Perfered language automatically description is incorrect");
        UserProfilePage.clickOnElementsWEPTimeZonePage("togglesystemperferedlanguageclick");
        softAssert.assertTrue(
                UserProfilePage.matchTextOfWEPTimeZonePage("selectperferedlanguagelabel",
                        getTextLanguage(LanguageCode, "daas_ui", "wpt.lang.dropdown.placeholder")),
                " select time zone description is incorrect");
        softAssert.assertTrue(
                UserProfilePage.matchTextOfWEPTimeZonePage("dropdownperferedlanguageselect",
                        getTextLanguage(LanguageCode, "daas_ui", "select_time_zone_dropdown")),
                " select time zone dropdown description is incorrect");
        UserProfilePage.verifyElementsWEPTimeZonePagePage("savebuttonperferedlanguage");
        UserProfilePage.verifyElementsWEPTimeZonePagePage("cancelbuttonperferedlanguage");
        UserProfilePage.clickOnElementsWEPTimeZonePage("savebuttonperferedlanguage");
    }

    @Test(priority = 5, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C53365566")
    public final void verifyDefaultPerferedLanguageIsDisabled() throws Exception {

        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        UserProfilePage UserProfilePage = new UserProfilePage(PreDefinedActions.getDriver()).getInstance();
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        waitForPageLoaded();
        UserProfilePage.clickOnElementsWEPTimeZonePage("editperferedlanguageclick");
        if(!UserProfilePage.verifyElementsWEPTimeZonePagePage("togglecheckbasedonvaluefordisbaledperferedlanuguage")) {
            UserProfilePage.clickOnElementsWEPTimeZonePage("togglesystemperferedlanguageclick");
        }
        String perferedlanguageDisabled = UserProfilePage.getAttributesOfWEPTimeZonePage("dropdownperferedlanguageselect", "disabled");
        softAssert.assertTrue(perferedlanguageDisabled.equalsIgnoreCase("true"), "Time zone dropdown field is not disabled");
        UserProfilePage.clickOnElementsWEPTimeZonePage("savebuttonperferedlanguage");
        waitForPageLoaded();
        logout();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        if(!UserProfilePage.verifyElementsWEPTimeZonePagePage("perferedlanguageuserprofile")){
            UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
            UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        }
        waitForPageLoaded();
        UserProfilePage.clickOnElementsWEPTimeZonePage("editperferedlanguageclick");
        String perferedlanguageDisable2 = UserProfilePage.getAttributesOfWEPTimeZonePage("dropdownperferedlanguageselect", "readonly");
        softAssert.assertTrue(perferedlanguageDisable2.equalsIgnoreCase("true"), "Time zone dropdown field is not disabled");
        LOGGER.info("Perfered Language field is disabled is getting saved as per the previews value after logout");
    }


    @Test(priority = 6, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C53365566")
    public final void verifyDefaultPerferedLanguageIsEnabled() throws Exception {

        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        UserProfilePage UserProfilePage = new UserProfilePage(PreDefinedActions.getDriver()).getInstance();
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
        UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        waitForPageLoaded();
        UserProfilePage.clickOnElementsWEPTimeZonePage("editperferedlanguageclick");
        if(UserProfilePage.verifyElementsWEPTimeZonePagePage("togglecheckbasedonvalueforEnabledperferedlanuguage")) {
            UserProfilePage.clickOnElementsWEPTimeZonePage("togglesystemperferedlanguageclick");
        }
        String disabled = UserProfilePage.getAttributesOfWEPTimeZonePage("dropdowntimezoneselect", "disabled");
        boolean isDisabled = disabled != null && !disabled.isEmpty();
        softAssert.assertTrue(!isDisabled, "Perfered Language dropdown field is not enabled");
        UserProfilePage.clickOnElementsWEPTimeZonePage("savebuttonperferedlanguage");
        waitForPageLoaded();
        logout();
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        if(!UserProfilePage.verifyElementsWEPTimeZonePagePage("perferedlanguageuserprofile")){
            UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiledropdown");
            UserProfilePage.clickOnElementsWEPTimeZonePage("userprofiletext");
        }
        waitForPageLoaded();
        UserProfilePage.clickOnElementsWEPTimeZonePage("editperferedlanguageclick");
        String TimezoneIsDisabled2 = UserProfilePage.getAttributesOfWEPTimeZonePage("dropdownperferedlanguageselect", "disabled");
        boolean isEnabled = (TimezoneIsDisabled2 == null || TimezoneIsDisabled2.isEmpty());
        softAssert.assertTrue(isEnabled, "Preferred Language dropdown is not enabled");
        LOGGER.info("Perfered Language field is enabled is getting saved as per the previews value after logout");
    }
}

