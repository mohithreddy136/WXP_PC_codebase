package com.testscripts.daasui;

import com.daasui.constants.CommonVariables;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WEPNewLoginpage;

public class WEPNewLoginPageTest extends CommonTest {
	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C48677237")
	public final void verifyElementsOfNewLoginPage() throws Exception {

		SoftAssert sa = new SoftAssert();
		WEPNewLoginpage WEPNewLoginpage = new WEPNewLoginpage(PreDefinedActions.getDriver()).getInstance();
		WEPNewLoginpage.clickOnElementsOfNewLoginPage("cookieAccept");
		if(!isFeatureToggleEnabled(CommonVariables.WX_NEW_LOGIN_PAGE_TOGGLE)){
		if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
				System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP") ||
				System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")||
            System.getProperty("environment").equalsIgnoreCase("US-VENEER-WEP")){
		WEPNewLoginpage.clickOnElementsOfNewLoginPage("signInButton");
		}else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
				System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
				System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")){
			Assert.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("emailInputBox"), "Login page did not open successfully");
		}
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("hpLogo"), "Hp logo is not present on Login page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("languageHeader"),
				"Language dropdown is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("signinTitle"),
				"Signin Title is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("signinBelowText"),
				"Siginin below Text is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("emailInputBox"),
				"Email Inputbox is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("remeberMeText"),
				"RememberMe text is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("remenbermecheckBox"),
				"Checkbox is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("continueButton"),
				"Continue Button is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("belowfootersection"),
				"Footersection is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("sliderHeaderText"),
				"Slider Header text is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("sliderDescText"),
				"Slider desc text is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("sliderImg"),
				"Slider Img is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("leftSliderBtn"),
				"Left Slider Button is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("rightSliderBtn"),
				"Right Slider Button is not present on Login Page");
		sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("sliderCircleBtn"),
				"Slider Circle Button  is not present on Login Page");
		sa.assertAll();
		}
		else {
			if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")||
            System.getProperty("environment").equalsIgnoreCase("US-VENEER-WEP")){
				WEPNewLoginpage.clickOnElementsOfNewLoginPage("signInButton");
			}else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")){
				Assert.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("emailInputBox"), "Login page did not open successfully");
			}
			sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("Hplogologinpage"),
					"Language dropdown is not present on Login Page");
			sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("languagedropdown"),
					"Language dropdown is not present on Login Page");
			sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("applicationheading"),
					"Signin Title is not present on Login Page");
			if(isFeatureToggleEnabled(CommonVariables.WX_SHOW_SELF_LINK_TOGGLE)){
			sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("createaccounttext"),
					"Create account text is not present on Login Page");
			String Createaccountlink = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/"
					+ ConstantURL.SIGNUP;
			WEPNewLoginpage.clickOnElementsOfNewLoginPage("createaccountlink");
			WEPNewLoginpage.switchToDifferentTab();
			sleeper(5000);
			sa.assertTrue(WEPNewLoginpage.getUrlOfCurrentPage().equals(Createaccountlink),
					"Create account Url is not matched on Login Page");
			WEPNewLoginpage.switchBackToPreviousTab();
			}
			sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("emailtextbox1"),
					"Email Inputbox is not present on Login Page");
			sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("remembermetext"),
					"RememberMe text is not present on Login Page");
			sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("remenbermecheckBox"),
					"Checkbox is not present on Login Page");
			sa.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("signinbutton1"),
					"Signin button is not present on Login Page");
			sa.assertAll();
		}
	}

	@Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C48677237")
	public final void verifyElementsTextOfNewLoginPage() throws Exception {

		SoftAssert sa = new SoftAssert();
		WEPNewLoginpage WEPNewLoginpage = new WEPNewLoginpage(PreDefinedActions.getDriver()).getInstance();
		WEPNewLoginpage.clickOnElementsOfNewLoginPage("cookieAccept");
		if(!isFeatureToggleEnabled(CommonVariables.WX_NEW_LOGIN_PAGE_TOGGLE)) {
			if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")||
                    System.getProperty("environment").equalsIgnoreCase("US-VENEER-WEP")) {
				WEPNewLoginpage.clickOnElementsOfNewLoginPage("signInButton");
			} else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")) {
				Assert.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("emailInputBox"), "Login page did not open successfully");
			}

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderHeaderText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.title.1")),
					"Slider1 Header text is not matched on Login Page");

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderDescText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.desc.1")),
					"Slider1 desc text is not matched on Login Page");

			WEPNewLoginpage.clickByJavaScriptOnElementsOfNewLoginPage("rightSliderBtn");
			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderHeaderText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.title.2")),
					"Slider2 Header text is not matched on Login Page");

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderDescText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.desc.2")),
					"Slider2 desc text is not matched on Login Page");

			WEPNewLoginpage.clickOnElementsOfNewLoginPage("rightSliderBtn");
			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderHeaderText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.title.3")),
					"Slider3 Header text is not matched on Login Page");

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderDescText").trim().equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.desc.3").trim()),
					"Slider3 desc text is not matched on Login Page");

			WEPNewLoginpage.clickOnElementsOfNewLoginPage("rightSliderBtn");
			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderHeaderText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.title.4")),
					"Slider4 Header text is not matched on Login Page");

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderDescText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.desc.4")),
					"Slider4 desc text is not matched on Login Page");

			WEPNewLoginpage.clickOnElementsOfNewLoginPage("rightSliderBtn");
			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderHeaderText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.title.5")),
					"Slider5 Header text is not matched on Login Page");

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("sliderDescText").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.slider.desc.5")),
					"Slider5 desc text is not matched on Login Page");

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("signinTitle").equals(
							WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "adminx.idplogin.onecloud.header").replace("{appName}", "HP Workforce Experience Platform")),
					"Signin Title is not matched on Login Page");

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("signinBelowText").replaceAll("\\s+", "")
							.equals(WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui",
									"wex.onboarding.signin.desc").replaceAll("\\s+", "")),
					"Sign In Below Text text is not matched on Login Page");

			sa.assertTrue(
					WEPNewLoginpage.getTextOfNewLoginPage("remeberMeText")
							.equals(WEPNewLoginpage.getTextLanguage(LanguageCode, "daas_ui", "wex.onboarding.remember_me")),
					"RememberMe text is not matched on Login Page");

			String privacyUrl = ConstantURL.PRIVACY_PAGE_URL_WelcomePage;
			WEPNewLoginpage.clickOnElementsOfNewLoginPage("privacyLink");
			WEPNewLoginpage.switchToDifferentTab();
			sleeper(5000);
			sa.assertTrue(WEPNewLoginpage.getUrlOfCurrentPage().equals(privacyUrl),
					"Privacy Page Url is not matched on Login Page");
			WEPNewLoginpage.switchBackToPreviousTab();
            if(System.getProperty("environment").contains("VENEER")){
                sa.assertTrue(
                                (SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/"
                                        + ConstantURL.TERMS_AND_CONDITIONS_VENEER + "/"
                                        + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")))
                                        .equals(WEPNewLoginpage.getUrlOfCurrentPage()),
                                "Terms and Condition URL not matching");
            }else {
                String termsAndConditionUrl = SetTestEnvironments.getEnvironment(System.getProperty("environment")) +
                         ConstantURL.TERMS_AND_CONDITIONS + "/"
                        + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
                WEPNewLoginpage.clickOnElementsOfNewLoginPage("termsandconditionlink1newpage");
                WEPNewLoginpage.switchToDifferentTab();
                sleeper(5000);
                sa.assertTrue(WEPNewLoginpage.getUrlOfCurrentPage().equals(termsAndConditionUrl),
                        "Terms And Condition Page Url is not matched on Login Page");
            }
			WEPNewLoginpage.switchBackToPreviousTab();
			sa.assertAll();
		}
		else {
			if (System.getProperty("environment").equalsIgnoreCase("US-STAGE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("US-STABLE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("US-PROD-WEP")||
                    System.getProperty("environment").equalsIgnoreCase("US-VENEER-WEP")) {
				WEPNewLoginpage.clickOnElementsOfNewLoginPage("signInButton");
			} else if (System.getProperty("environment").equalsIgnoreCase("EU-STAGE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("EU-STABLE-WEP") ||
					System.getProperty("environment").equalsIgnoreCase("EU-PROD-WEP")) {
				Assert.assertTrue(WEPNewLoginpage.verifyElementsOfNewLoginPage("emailInputBox"), "Login page did not open successfully");
			}
			String privacyUrl = ConstantURL.PRIVACY_PAGE_URL_WelcomePage;
			WEPNewLoginpage.clickOnElementsOfNewLoginPage("privacylinknewpage");
			WEPNewLoginpage.switchToDifferentTab();
			sleeper(5000);
			sa.assertTrue(WEPNewLoginpage.getUrlOfCurrentPage().equals(privacyUrl),
					"Privacy Page Url is not matched on Login Page");
			WEPNewLoginpage.switchBackToPreviousTab();
            sleeper(3000);
            WEPNewLoginpage.clickOnElementsOfNewLoginPage("termsandconditionlink1newpage");
            WEPNewLoginpage.switchToDifferentTab();
            if(System.getProperty("environment").contains("VENEER") || System.getProperty("environment").contains("STAGE")){
                sa.assertTrue(
                        (SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/"
                                + ConstantURL.TERMS_AND_CONDITIONS_VENEER + "/"
                                + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")))
                                .equals(WEPNewLoginpage.getUrlOfCurrentPage()),
                        "Terms and Condition URL not matching");
            }else {
                String termsAndConditionUrl = SetTestEnvironments.getEnvironment(System.getProperty("environment")) +
                        ConstantURL.TERMS_AND_CONDITIONS + "/"
                        + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
                sleeper(5000);
                sa.assertTrue(WEPNewLoginpage.getUrlOfCurrentPage().equals(termsAndConditionUrl),
                        "Terms And Condition Page Url is not matched on Login Page");
            }
			WEPNewLoginpage.switchBackToPreviousTab();
			sa.assertAll();
		}
	}
}
