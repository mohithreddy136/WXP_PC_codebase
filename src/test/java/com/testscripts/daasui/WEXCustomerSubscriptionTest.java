package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.WEXCustomerSubscriptionPage;
import com.daasui.pages.WEXSignUpPage;


public class WEXCustomerSubscriptionTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXCustomerSubscriptionTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	public static String UserEmail = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_USER_EMAIL_SEARCH");
	
	/**
	 * This test case is to verify Subscription tab in Customer Login
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"PLATFORM-CX_PRODUCTION_CI", "PLATFORM-CX_REGRESSION_CI"}, description = "TestCase ID : ")
	public final void verifyWEXSubscriptionInCustomerLogin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerSubscriptionPage WEXCustomerSubscriptionPage = new WEXCustomerSubscriptionPage(PreDefinedActions.getDriver()).getInstance();
		//try {
			login("COMPANY_SUB_EMAIL", "COMPANY_SUB_PASSWORD");	
			leftSideMenuVerification();
		WEXCustomerSubscriptionPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");
		
    	softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("wexSubscriptionTab"));
		//WEXCustomerSubscriptionPage.actionClickOnWEXCustomerSubscriptionPage("subscriptionTab");
		WEXCustomerSubscriptionPage.clickOnElementsOfWEXCustomerSubscriptionPage("wexSubscriptionTab");
		
		LOGGER.info("Clicked on the Subscription tab.");
		
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("currentPlan"));
		softAssert.assertEquals(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("currentPlan"),getTextLanguage(LanguageCode, "daas_ui", "account_management.subscription.current_plans"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("currentPlanName"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("usedSeats"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("planStatus"));
		LOGGER.info("Verify the plan status");
		
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("future-wex-offerings"));
		softAssert.assertEquals(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("future-wex-offerings"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.future.wex"));
		softAssert.assertEquals(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("future-wex-subtext"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.future.wex.subtext"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("basicPlan"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("basicPlanCost"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("WEXBasicPlanIncludes"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("assetInventory"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("warrantyStatus"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("PCBIOSPolicy"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("PCWindows11Upgradestatus"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("antivirusStatus"));
		LOGGER.info("Verify the WEX basic plan status");
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("proPlan"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("proPlanCost"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("proPlanText"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("PCInsightstoActions"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("EnterprisePrintTelemetry"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("collaborationTelemetry"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("peripheralInventory"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("automatedPulses"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("configurableScoring"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("actionableChoices"));
		LOGGER.info("Verify the WEX pro plan status");
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("wexEnterprise"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("wexEnterprisetext"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("contact-hp-sales"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("actionableChoices"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("subscriptionMgt"));
		softAssert.assertEquals(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("subscriptionMgmtTitle"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.subscription_management_title"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("subscriptionMgtSubtitle"));
		softAssert.assertEquals(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("subscriptionMgtSubtitle"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.subscription_management_subtitle"));
		softAssert.assertTrue(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("gotoAwsMarketplace"));
		softAssert.assertEquals(WEXCustomerSubscriptionPage.verifyElementsOfWEXCustomerSubscriptionPage("gotoAwsMarketplace"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.AWS_button"));
		WEXCustomerSubscriptionPage.actionClickOnWEXCustomerSubscriptionPage("gotoAwsMarketplace");
		LOGGER.info("Clicked on the AWS MarketPlace.");
		WEXCustomerSubscriptionPage.switchBackToPreviousTab();
		}
	
	}

