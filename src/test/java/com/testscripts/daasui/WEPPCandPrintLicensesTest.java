package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.WEPPCPrintPlansConstants;
import com.daasui.pages.WEPAccountsLicensesPage;
import com.daasui.pages.WEPPCandPrintLicensesPage;
import com.daasui.pages.WEXDashboardPage;

public class WEPPCandPrintLicensesTest extends CommonTest {
	
	private static Logger LOGGER = LogManager.getLogger(WEPCustomerHomeTest.class);
	
	/**
	 * This method  Verifies the end-to-end behavior of PC Pro + Print Pro plans for tenant
	 *
	 * @throws Exception if any UI interaction or assertion fails during execution
	 */
	 @Test(priority = 1, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCProPrintProPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_PRO_PRINT_PRO", "ITADMIN_PASSWORD_WEP_PRO_PRINT_PRO");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    pcandPrintLicensesPage.verifyPrintersFleetInventory(LanguageCode);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_PRO);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_PRO);

        //Verify Workforce Experience Pro license and over-enrollment banner
        LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO);
        pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO);
        
        //Navigate to Devices -> Printers and verify tabs
        Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
        	    "Devices Printers section verification failed");
		 
		 //Navigate to Groups -> Printers and verify group
        Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
        	    "Printers Groups section verification failed");
		 
		 //Navigate to Remediations -> Policies and verify policies
        Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
        	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
		 Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		 
             
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Standard + Print Standard plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 2, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCStdPrintStdPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_STD_PRINT_STD", "ITADMIN_PASSWORD_WEP_STD_PRINT_STD");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_STD);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_STD);

       //Verify Workforce Experience Pro license and over-enrollment banner
       LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_STD);
       pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_STD);
       
       //Navigate to Devices -> Printers and verify tabs
     //Navigate to Devices -> Printers and verify tabs
       Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
       	    "Devices Printers section verification failed");
		 
		 //Navigate to Groups -> Printers and verify group
       Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
       	    "Printers Groups section verification failed");
		 
		 //Navigate to Remediations -> Policies and verify policies
       Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
       	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
		 Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Elite + Print Pro plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */ 
	 @Test(priority = 3, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCElitePrintProPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_ELITE_PRINT_PRO", "ITADMIN_PASSWORD_WEP_ELITE_PRINT_PRO");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_PRO);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_PRO);

      //Verify Workforce Experience Pro license and over-enrollment banner
      LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_ELITE);
      pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_ELITE);
      
   
     //Navigate to Devices -> Printers and verify tabs
      Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
      	    "Devices Printers section verification failed");
		 
		 //Navigate to Groups -> Printers and verify group
      Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
      	    "Printers Groups section verification failed");
		 
		 //Navigate to Remediations -> Policies and verify policies
      Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
      	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
		 Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Pro + Print Std plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 4, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCProPrintStdPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_PRO_PRINT_STD", "ITADMIN_PASSWORD_WEP_PRO_PRINT_STD");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_STD);
	    //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_STD);

	     //Verify Workforce Experience Pro license and over-enrollment banner
	     LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO);
	     pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO);
	     
	     //Navigate to Devices -> Printers and verify tabs
	     Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	     	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	     Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	     	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	     Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	     	    "Remediation Printers Policies section verification failed");
			 
		 //Navigate to Analytics -> Fleet Management and verify widgets
		 Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
		        	    "Fleet Management Printers section verification failed");
		          
	  }
	 /**
	  * This method  Verifies the end-to-end behavior of PC Std + Print Pro plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 5, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCStdPrintProPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_STD_PRINT_PRO", "ITADMIN_PASSWORD_WEP_STD_PRINT_PRO");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_PRO);
	    //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_PRO);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_STD);
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_STD);
	    
	    //Navigate to Devices -> Printers and verify tabs
	    Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	    	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	    Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	    	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	    Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	    	    "Remediation Printers Policies section verification failed");
		 
	    //Navigate to Analytics -> Fleet Management and verify widgets
	    Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Elite + Print Std plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 6, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCElitePrintStdPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_ELITE_PRINT_STD", "ITADMIN_PASSWORD_WEP_ELITE_PRINT_STD");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_STD);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_STD);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	   LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_ELITE);
	   pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_ELITE);
	   
	   //Navigate to Devices -> Printers and verify tabs
	   //Navigate to Devices -> Printers and verify tabs
	   Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	   	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	   Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	   	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	   Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	   	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
	   Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Pro Trial + Print Trial plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 7, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCProTrialPrintTrialPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_PROTRIAL_PRINT_TRIAL", "ITADMIN_PASSWORD_WEP_PROTRIAL_PRINT_TRIAL");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	   LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO_TRIAL);
	   pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO_TRIAL);
	   
	   //Navigate to Devices -> Printers and verify tabs
	   //Navigate to Devices -> Printers and verify tabs
	   Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	   	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	   Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	   	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	   Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	   	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
	   Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Pro Trial + Print Std for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 8, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCProTrialPrintStdPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_PROTRIAL_PRINT_STD", "ITADMIN_PASSWORD_WEP_PROTRIAL_PRINT_STD");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_STD);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_STD);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	   LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO_TRIAL);
	   pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO_TRIAL);
	   
	   //Navigate to Devices -> Printers and verify tabs
	   //Navigate to Devices -> Printers and verify tabs
	   Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	   	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	   Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	   	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	   Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	   	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
	   Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Pro Trial + Print Pro plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 9, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCProTrialPrintProPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_PROTRIAL_PRINT_PRO", "ITADMIN_PASSWORD_WEP_PROTRIAL_PRINT_PRO");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_PRO);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_PRO);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	   LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO_TRIAL);
	   pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO_TRIAL);
	   
	   //Navigate to Devices -> Printers and verify tabs
	   //Navigate to Devices -> Printers and verify tabs
	   Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	   	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	   Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	   	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	   Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	   	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
	   Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Pro + Print Trial plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 10, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCProPrintTrialPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_PRO_PRINT_TRIAL", "ITADMIN_PASSWORD_WEP_PRO_PRINT_TRIAL");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	   LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO);
	   pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO);
	   
	   //Navigate to Devices -> Printers and verify tabs
	   //Navigate to Devices -> Printers and verify tabs
	   Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	   	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	   Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	   	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	   Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	   	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
	   Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Std + Print Trial plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 11, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCStdPrintTrialPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_STD_PRINT_TRIAL", "ITADMIN_PASSWORD_WEP_STD_PRINT_TRIAL");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	   LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_STD);
	   pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_STD);
	   
	   //Navigate to Devices -> Printers and verify tabs
	   //Navigate to Devices -> Printers and verify tabs
	   Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	   	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	   Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	   	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	   Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	   	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
	   Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Elite + Print Trial plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 12, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCElitePrintTrialPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_ELITE_PRINT_TRIAL", "ITADMIN_PASSWORD_WEP_ELITE_PRINT_TRIAL");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	   LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_ELITE);
	   pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_ELITE);
	   
	   //Navigate to Devices -> Printers and verify tabs
	   //Navigate to Devices -> Printers and verify tabs
	   Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	   	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	   Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	   	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	   Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	   	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
	   Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }
	 
	 /**
	  * This method  Verifies the end-to-end behavior of PC Pro + Print Trial + addon plans for tenant
	  *
	  * @throws Exception if any UI interaction or assertion fails during execution
	  */
	 @Test(priority = 13, groups = { "REGRESSION_PRINTERPCPLAN" }, description = "TestCase ID : ")
	  public final void verifyWEPAccountLicensesForPCProPrintTrialAddonPlan() throws Exception {
	    WEPAccountsLicensesPage licensePage = new WEPAccountsLicensesPage();
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    WEPPCandPrintLicensesPage pcandPrintLicensesPage = new WEPPCandPrintLicensesPage(PreDefinedActions.getDriver()).getInstance();
	    login("ITADMIN_EMAIL_WEP_PRO_PRINT_TRIAL_ADDON", "ITADMIN_PASSWORD_WEP_PRO_PRINT_TRIAL_ADDON");
	    waitForPageLoaded();
	    dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
	    Assert.assertTrue(licensePage.navigateToCustomerLicensesTab(),
	        "Able to navigate to Licenses tab.");
	    waitForPageLoaded();
	    
	    LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);
	  //Verify WXP Print Management Pro license and over-enrollment banner
	    pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.PRINT_MANAGEMENT_TRIAL);

	    //Verify Workforce Experience Pro license and over-enrollment banner
	   LOGGER.info("Starting verification for: " + WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO);
	   pcandPrintLicensesPage.verifyLicenseOverEnrollment(WEPPCPrintPlansConstants.WORKFORCE_EXPERIENCE_PRO);
	   
	   //Navigate to Devices -> Printers and verify tabs
	   //Navigate to Devices -> Printers and verify tabs
	   Assert.assertTrue(pcandPrintLicensesPage.verifyDevicesPrintersSection(), 
	   	    "Devices Printers section verification failed");
			 
			 //Navigate to Groups -> Printers and verify group
	   Assert.assertTrue(pcandPrintLicensesPage.verifyGroupsPrintersSection(), 
	   	    "Printers Groups section verification failed");
			 
			 //Navigate to Remediations -> Policies and verify policies
	   Assert.assertTrue(pcandPrintLicensesPage.verifyRemediationsPoliciesSection(), 
	   	    "Remediation Printers Policies section verification failed");
		 
		 //Navigate to Analytics -> Fleet Management and verify widgets
	   Assert.assertTrue(pcandPrintLicensesPage.verifyAnalyticsFleetManagementSection(), 
	        	    "Fleet Management Printers section verification failed");
		          
	  }

}
