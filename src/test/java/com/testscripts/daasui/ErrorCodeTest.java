package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.ErrorCodePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ErrorCodeTest extends CommonTest {
    private static Logger LOGGER = LogManager.getLogger(ErrorCodeTest.class);

    @Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C56357290")
    public final void verify404ErrorCodepage() throws Exception {

        getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.ERROR_CODE_404);
        sleeper(3000);
        ErrorCodePage ErrorCodePage = new ErrorCodePage(PreDefinedActions.getDriver());
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("404imgerrorcode"), "Image is not present on 404 error code page");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("404texterrorocode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.heading.404")), "404 error code heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("404headingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line1.404")), "404 error page heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("404subheadingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line2.404")), "404 error text subheading is not present");
        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("404backbutton"), "Back button on 404 error code page not found");
        softAssert.assertAll();

        LOGGER.info("Verified 404 error code pages string and tiles");
    }

    @Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C56357292")
    public final void verify401ErrorCodepage() throws Exception {


        getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.ERROR_CODE_401);
        sleeper(3000);
        ErrorCodePage ErrorCodePage = new ErrorCodePage(PreDefinedActions.getDriver());
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("401imgerrorcode"), "Image is not present on 404 error code page");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("401texterrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.heading.401")), "401 error code heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("401headingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line1.401")), "401 error heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("401subheadingerrorcode").trim().equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line2.401").trim()), "401 error text line 1 subheading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("401subheadingerrorcode2").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line3.401")), "401 error text line 2 subheading is not present");
        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("401backbutton"), "Back button on 401 error code page not found");
        softAssert.assertAll();

        LOGGER.info("Verified 401 error code pages string and tiles");
    }

    @Test(priority = 3, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C56357293")
    public final void verify403ErrorCodepage() throws Exception {


        getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.ERROR_CODE_403);
        sleeper(3000);
        ErrorCodePage ErrorCodePage = new ErrorCodePage(PreDefinedActions.getDriver());
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("403imgerrorcode"), "Image is not present on 403 error code page");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("403texterrorocode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.heading.403")), "403 error code heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("403headingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.403")), "403 error heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("403subheadingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.403")), "403 error text subheading is not present");
        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("403backbutton"), "Back button on 403 error code page not found");
        softAssert.assertAll();

        LOGGER.info("Verified 403 error code pages string and tiles");

    }

    @Test(priority = 4, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C56357294")
    public final void verify412ErrorCodepage() throws Exception {


        getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.ERROR_CODE_412);
        sleeper(3000);
        ErrorCodePage ErrorCodePage = new ErrorCodePage(PreDefinedActions.getDriver());
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("412imgerrorcode"), "Image is not present on 412 error code page");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("412texterrorocode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.heading.412")), "412 error code heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("412headingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.412")), "412 error heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("412subheadingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.412")), "412 error text subheading is not present");
        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("412backbutton"), "Back button on 412 error code page not found");
        softAssert.assertAll();

        LOGGER.info("Verified 412 error code pages string and tiles");
    }

    @Test(priority = 5, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C56357295")
    public final void verify412_hpErrorCodepage() throws Exception {


        getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.ERROR_CODE_412_hp);
        sleeper(3000);
        ErrorCodePage ErrorCodePage = new ErrorCodePage(PreDefinedActions.getDriver());
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("412_hpimgerrorcode"), "Image is not present on 412_hp error code page");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("412_hptexterrorocode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.heading.412_hp")), "412_hp error code heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("412_hpheadingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.412_hp")), "412_hp error heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("412_hpsubheadingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.412_hp")), "412_hp error text subheading is not present");
        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("412_hpbackbutton"), "Back button on 412_hp error code page not found");
        softAssert.assertAll();

        LOGGER.info("Verified 412_hp error code pages string and tiles");
    }

    @Test(priority = 6, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C56357296")
    public final void verify500ErrorCodepage() throws Exception {


        getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.ERROR_CODE_500);
        sleeper(3000);
        ErrorCodePage ErrorCodePage = new ErrorCodePage(PreDefinedActions.getDriver());
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("500imgerrorcode"), "Image is not present on 500 error code page");

        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("500texterrorocode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.heading.500")), "500 error code heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("500headingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line1.500")), "500 error heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("500subheadingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line2.500")), "500 error text subheading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("500subheadingerrorcode2").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line3.500")), "500 error text subheading is not present");
        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("500backbutton"), "Back button on 500 error code page not found");
        softAssert.assertAll();

        LOGGER.info("Verified 500 error code pages string and tiles");
    }

    @Test(priority = 7, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C56357297")
    public final void verify503ErrorCodepage() throws Exception {


        getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.ERROR_CODE_503);
        sleeper(3000);
        ErrorCodePage ErrorCodePage = new ErrorCodePage(PreDefinedActions.getDriver());
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("503imgerrorcode"), "Image is not present on 503 error code page");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("503texterrorocode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.heading.503")), "503 error code heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("503headingerrorcode").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line1.503")), "503 error heading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("503subheadingerrorcode").trim().equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line2.503").trim()), "503 error text line 1 subheading is not present");
        softAssert.assertTrue(ErrorCodePage.getTextOfErrorCodePage("503subheadingerrorcode2").equals(ErrorCodePage.getTextLanguage(LanguageCode, "daas_ui", "wex.global.error_desc.line3.503")), "503 error text line 2 subheading is not present");
        softAssert.assertTrue(ErrorCodePage.verifyElementsOfErrorCodePage("503backbutton"), "Back button on 503 error code page not found");
        softAssert.assertAll();

        LOGGER.info("Verified 503 error code pages string and tiles");
    }
}
