package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.WEXSignUpPage;


public class WEXSelfSignupTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXSelfSignupTest.class);
	public static String companyFullName = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_SELFSIGNUP_COMPANY_NAME");
	public static String companyEmail = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_SELFSIGNUP_COMPANY_EMAIL");
	public static String country = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COUNTRY");
	public static String companyFN = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_FN");
	public static String companyLN = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_LN");
	public static String timeZone = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_TIMEZONE");
	public static String companyAddress = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_ADDRESS");
	public static String city = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_CITY");
	public static String zipCode = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_ZIPCODE");
	public static String state = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_COMPANY_STATE");
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "WEX_USER_LN");
	public static String tenantID, currentUrl;
	
	@Test(priority = 1, groups = { "REGRESSION_WEX" })
	public final void verifySelfSignUpHPIDFlow() throws Exception {
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		WEXSignUpPage signUpPage = new WEXSignUpPage(PreDefinedActions.getDriver()).getInstance();
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		login("ROOT_ADMIN_EMAIL", "ROOT_ADMIN_PASSWORD");
		
		try {
			Assert.assertTrue(companyAvailableForRoot(companyEmail), "Company not present");	
				
			Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("emailList", companyEmail), "Company didn't match");
			companiesPage.clickByJavaScriptOnCompaniesPage("companySearchListForRootClick");
			waitForPageLoaded();
			LOGGER.info("Redirected to details page");	
			currentUrl = companyDetailsPage.getUrlOfCurrentPage();
			tenantID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
			LOGGER.info("Captured tenantID");
				
			// Remove created company
			Assert.assertTrue(removeCompanyUsingAPI(tenantID), "Company removal failed");	
			LOGGER.info("Company has been deleted");
			
		}catch(Throwable error) {
			LOGGER.info("Company didn't exist");
		}
			
		logout();
		sleeper(10000);
		LOGGER.info("Logged out");                
		sleeper(5000);
		getUrl(getEnvironment(System.getProperty("environment")) + CommonVariables.REG_TOKEN);
		environment = System.getProperty("environment");
		
		Assert.assertTrue(signUpPage.verifySignUpScreen(LanguageCode),"Sign up screen validation failed");		
		Assert.assertTrue(signUpPage.validateGetStartedScreen(companyFullName, companyEmail, country),"Sign up screen verification failed");		
		Assert.assertTrue(signUpPage.enterPasswordToSignUp(getCredentials(environment, "WEX_HPID_US_PASSWORD")),"Password screen verification failed");		
		waitForPageLoaded();
		
		signUpPage.validateAccountLinkedScreen(LanguageCode);		
		String ActualLink = getTextLanguage(LanguageCode, "daas_ui", "adminx.onboarding.termsAndCondition").replace("{appName}",CommonVariables.APP_NAME).replace("<a target='_blank' href={termsLink}>","").replace("</a>", "").replace("<a target='_blank' href={privacyLink}>","").replace("</a>","")+"*";
		Assert.assertTrue(signUpPage.companyCreate(companyFN,companyLN,companyEmail,timeZone,companyFullName,country,companyAddress,city,zipCode,state,ActualLink),"Company creation failed");	
		
		Assert.assertTrue(signUpPage.validateCompanyCreateSuccessScreen(LanguageCode, companyFullName),"Company create Success screen validation failed");		
		Assert.assertTrue(signUpPage.validateAddUsersScreen(LanguageCode),"Add User screen validation failed");
		Assert.assertTrue(signUpPage.addUserFunctionality(UserFirstname,UserLastname,UserEmail,"IT Admin",LanguageCode),"Add User screen verification failed");
		Assert.assertTrue(signUpPage.invitationEmailScreen("WEX User Invitation", companyFullName, LanguageCode, CommonVariables.APP_NAME),"Invite email screen validation failed");
		Assert.assertTrue(signUpPage.verifyInviteSentSuccessScreen(LanguageCode),"Invitation sent screen validation failed");
		waitForPageLoaded();
		sleeper(5000);
		String actualMailContent = signUpPage.verifyEmailContent("INVITED_USER_EMAIL_SUBJECT", environment, UserEmail,true);
		int count = 0;
	      while (count < 5 && actualMailContent == "") {
	            sleeper(1000);
	            count++;
	            System.out.println(count + " : Email not received on Add User during Onboarding flow");
	            actualMailContent = signUpPage.verifyEmailContent("INVITED_USER_EMAIL_SUBJECT", environment, UserEmail,true);
	      }
		
		System.out.println("Invited user Mail content" +actualMailContent);
		Assert.assertTrue(signUpPage.verifyWelcomeWizard(LanguageCode,CommonVariables.APP_NAME),"Welcome wizard screen validation failed");
	}	
	
}