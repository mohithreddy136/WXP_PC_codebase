package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.daasui.pages.WEPAccountsLicensesPage;

public final class WEPAccountsLicensesTest extends CommonTest {
  private static Logger LOGGER = LogManager.getLogger(WEPAccountsLicensesTest.class);
  
  @Test(priority = 1, groups = { "REGRESSION_SUBSCRIBE", "PRODUCTION_SUBSCRIBE" }, description = "TestCase ID : ")
  public final void verifyWEPAccountLicensesForStarterPlan() throws Exception {
    WEPAccountsLicensesPage page = new WEPAccountsLicensesPage();
    login("WXP_STARTER_COMPANY_EMAIL", "WXP_STARTER_COMPANY_PASSWORD");
    waitForPageLoaded();
    Assert.assertTrue(page.navigateToCustomerLicensesTab(),
        "Able to navigate to Licenses tab.");
    waitForPageLoaded();
    page.assertLicensesElements(false, false, true, "Workforce Experience Starter", "Starter");
  }

  @Test(priority = 1, groups = { "REGRESSION_SUBSCRIBE", "PRODUCTION_SUBSCRIBE" }, description = "TestCase ID : ")
  public final void verifyWEPAccountLicensesForStandardPlan() throws Exception {
    WEPAccountsLicensesPage page = new WEPAccountsLicensesPage();
    login("WXP_STANDARD_COMPANY_EMAIL", "WXP_STANDARD_COMPANY_PASSWORD");
    waitForPageLoaded();
    Assert.assertTrue(page.navigateToCustomerLicensesTab(),
        "Able to navigate to Licenses tab.");
    page.assertLicensesElements(true, true, false,"Workforce Experience Standard", "Standard");
  }

  @Test(priority = 1, groups = { "REGRESSION_SUBSCRIBE", "PRODUCTION_SUBSCRIBE" }, description = "TestCase ID : ", enabled = false)
  public final void verifyWEPAccountLicensesForProTrialPlan() throws Exception {
    WEPAccountsLicensesPage page = new WEPAccountsLicensesPage();
    login("WEP_COMPANY_PLAN_PRO_TRIAL_EMAIL", "WEP_COMPANY_PLAN_PRO_TRIAL_PASSWORD");
    waitForPageLoaded();
    Assert.assertTrue(page.navigateToCustomerLicensesTab(),
        "Able to navigate to Licenses tab.");
    waitForPageLoaded();
    page.assertLicensesElements(true, true, false, "Workforce Experience Pro Trial", "Pro");
  }

  @Test(priority = 1, groups = { "REGRESSION_SUBSCRIBE", "PRODUCTION_SUBSCRIBE" }, description = "TestCase ID : ")
  public final void verifyWEPAccountLicensesForProPlan() throws Exception {
    WEPAccountsLicensesPage page = new WEPAccountsLicensesPage();
    login("WXP_PRO_COMPANY_EMAIL", "WXP_PRO_COMPANY_PASSWORD");
    waitForPageLoaded();
    Assert.assertTrue(page.navigateToCustomerLicensesTab(),
        "Able to navigate to Licenses tab.");
    waitForPageLoaded();
    page.assertLicensesElements(true, true, false, "Workforce Experience Pro", "Pro");
  }

  @Test(priority = 1, groups = { "REGRESSION_SUBSCRIBE", "PRODUCTION_SUBSCRIBE" }, description = "TestCase ID : ")
  public final void verifyWEPAccountLicensesForElitePlan() throws Exception {
    WEPAccountsLicensesPage page = new WEPAccountsLicensesPage();
    login("WXP_ELITE_COMPANY_EMAIL", "WXP_ELITE_COMPANY_PASSWORD");
    waitForPageLoaded();
    Assert.assertTrue(page.navigateToCustomerLicensesTab(),
        "Able to navigate to licenses tab");
    waitForPageLoaded();
    page.assertLicensesElements(true, true, false, "Workforce Experience Elite", "Elite");
  }

  @Test(priority = 1, groups = { "REGRESSION_SUBSCRIBE", "PRODUCTION_SUBSCRIBE" }, description = "TestCase ID : ")
  public final void verifyWEPAccountLicensesForRootAdminAccount() throws Exception {
    WEPAccountsLicensesPage page = new WEPAccountsLicensesPage();
    login("ROOT_ADMIN_EMAIL_WEP", "ROOT_ADMIN_PASSWORD_WEP");
    waitForPageLoaded();
    impersonateWorkflowCompanyByRootAdmin("WXP_PRO_COMPANY_EMAIL");
    waitForPageLoaded();
    Assert.assertTrue(page.navigateToRootAdminLicensesTab(),
        "Able to navigate to licenses tab as root");
    waitForPageLoaded();
    page.assertLicensesElements(false, true, false, "Workforce Experience Pro", "Pro");
  }
}
