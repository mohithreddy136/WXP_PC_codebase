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
import com.daasui.pages.WEPPartnerSubscriptionPage;
import com.daasui.pages.WEXCustomerUserDetailsPage;
import com.daasui.pages.WEXCustomerUserListPage;
import com.daasui.pages.WEXDashboardPage;


public class WEPPartnerSubscriptionTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXCustomerUserTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	public static String UserEmail = getEnvironmentSpecificData(System.getProperty("environment"), "WEP_USER_EMAIL_SEARCH");
	public static String CompanyFullName = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_FULLNAME");
	public static String CompanyNameEmail = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME");
	public static String PartnerFullName = getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_FULLNAME");


	/**
	 * This test case is to verify subscription page on partner login.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"REGRESSION_SUBSCRIBE","PRODUCTION_SUBSCRIBE"}, description = "TestCase ID : ")
	public final void verifyWEPSubscriptionTestInPartnerLogin() throws Exception {
		
		WEPPartnerSubscriptionPage WEPPartnerSubscriptionPage = new WEPPartnerSubscriptionPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		
		login("COMPANY_SUBSCRIBE_EMAIL_WEP", "COMPANY_SUBSCRIBE_PASSWORD_WEP");	
		leftSideMenuVerification();
		WEPPartnerSubscriptionPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
	waitForPageLoaded();
	LOGGER.info("Redirected Account management-Overview tab page");
	
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("wepSubscriptionTab"));
	WEPPartnerSubscriptionPage.clickOnElementsOfWEPPartnerSubscriptionPage("wepSubscriptionTab");
	
	LOGGER.info("Clicked on the Subscription tab.");
	
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("currentPlan"));
	softAssert.assertEquals(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("currentPlan"),getTextLanguage(LanguageCode, "daas_ui", "account_management.subscription.current_plans"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("currentPlanName"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("usedSeats"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("planStatus"));
	LOGGER.info("Verify the plan status");
	
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("future-wep-offerings"));
	softAssert.assertEquals(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("future-wep-offerings"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.future.WEP"));
	softAssert.assertEquals(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("future-wep-subtext"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.future.WEP.subtext"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("basicPlan"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("basicPlanCost"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("wepBasicPlanIncludes"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("assetInventory"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("warrantyStatus"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("PCBIOSPolicy"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("PCWindows11Upgradestatus"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("antivirusStatus"));
	LOGGER.info("Verify the WEP basic plan status");
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("proPlan"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("proPlanCost"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("proPlanText"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("PCInsightstoActions"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("EnterprisePrintTelemetry"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("collaborationTelemetry"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("peripheralInventory"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("automatedPulses"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("configurableScoring"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("actionableChoices"));
	LOGGER.info("Verify the WEP pro plan status");
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("wepEnterprise"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("wepEnterprisetext"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("contact-hp-sales"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("actionableChoices"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("subscriptionMgt"));
	softAssert.assertEquals(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("subscriptionMgmtTitle"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.subscription_management_title"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("subscriptionMgtSubtitle"));
	softAssert.assertEquals(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("subscriptionMgtSubtitle"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.subscription_management_subtitle"));
	softAssert.assertTrue(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("gotoAwsMarketplace"));
	softAssert.assertEquals(WEPPartnerSubscriptionPage.verifyElementsOfWEPPartnerSubscriptionPage("gotoAwsMarketplace"),getTextLanguage(LanguageCode, "daas_ui","account_management.subscription.AWS_button"));
	WEPPartnerSubscriptionPage.actionClickOnWEPPartnerSubscriptionPage("gotoAwsMarketplace");
	LOGGER.info("Clicked on the AWS MarketPlace.");
	WEPPartnerSubscriptionPage.switchBackToPreviousTab();
	}

}





		