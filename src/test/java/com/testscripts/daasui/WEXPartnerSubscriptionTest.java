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
import com.daasui.pages.WEXPartnerSubscriptionPage;
import com.daasui.pages.WEXCustomerUserDetailsPage;
import com.daasui.pages.WEXCustomerUserListPage;
import com.daasui.pages.WEXDashboardPage;


public class WEXPartnerSubscriptionTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXCustomerUserTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	public static String UserEmail = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_USER_EMAIL_SEARCH");
	public static String CompanyFullName = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_FULLNAME");
	public static String CompanyNameEmail = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME");
	public static String PartnerFullName = getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_FULLNAME");


	/**
	 * This test case is to verify subscription page on partner login.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"PLATFORM-CX_PRODUCTION_CI", "PLATFORM-CX_REGRESSION_CI"}, description = "TestCase ID : ")
	public final void verifyWEXSubscriptionTestInPartnerLogin() throws Exception {
		
		WEXPartnerSubscriptionPage WEXPartnerSubscriptionPage = new WEXPartnerSubscriptionPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		
		login("COMPANY_SUB_EMAIL", "COMPANY_SUB_PASSWORD");	
		leftSideMenuVerification();
	WEXPartnerSubscriptionPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
	waitForPageLoaded();
	LOGGER.info("Redirected Account management-Overview tab page");
	
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("wexSubscriptionTab"));
	//WEXPartnerSubscriptionPage.actionClickOnWEXPartnerSubscriptionPage("subscriptionTab");
	WEXPartnerSubscriptionPage.clickOnElementsOfWEXPartnerSubscriptionPage("wexSubscriptionTab");
	
	LOGGER.info("Clicked on the Subscription tab.");
	
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("currentPlan"));
	softAssert.assertEquals(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("currentPlan"),getTextLanguage(LanguageCode, "daas_ui", "account_management.subscription.current_plans"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("currentPlanName"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("usedSeats"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("planStatus"));
	LOGGER.info("Verify the plan status");
	
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("future-wex-offerings"));
	softAssert.assertEquals(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("future-wex-offerings"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.future.wex"));
	softAssert.assertEquals(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("future-wex-subtext"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.future.wex.subtext"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("basicPlan"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("basicPlanCost"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("WEXBasicPlanIncludes"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("assetInventory"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("warrantyStatus"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("PCBIOSPolicy"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("PCWindows11Upgradestatus"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("antivirusStatus"));
	LOGGER.info("Verify the WEX basic plan status");
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("proPlan"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("proPlanCost"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("proPlanText"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("PCInsightstoActions"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("EnterprisePrintTelemetry"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("collaborationTelemetry"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("peripheralInventory"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("automatedPulses"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("configurableScoring"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("actionableChoices"));
	LOGGER.info("Verify the WEX pro plan status");
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("wexEnterprise"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("wexEnterprisetext"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("contact-hp-sales"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("actionableChoices"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("subscriptionMgt"));
	softAssert.assertEquals(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("subscriptionMgmtTitle"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.subscription_management_title"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("subscriptionMgtSubtitle"));
	softAssert.assertEquals(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("subscriptionMgtSubtitle"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.subscription_management_subtitle"));
	softAssert.assertTrue(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("gotoAwsMarketplace"));
	softAssert.assertEquals(WEXPartnerSubscriptionPage.verifyElementsOfWEXPartnerSubscriptionPage("gotoAwsMarketplace"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.AWS_button"));
	WEXPartnerSubscriptionPage.actionClickOnWEXPartnerSubscriptionPage("gotoAwsMarketplace");
	LOGGER.info("Clicked on the AWS MarketPlace.");
	WEXPartnerSubscriptionPage.switchBackToPreviousTab();
	}

}





		