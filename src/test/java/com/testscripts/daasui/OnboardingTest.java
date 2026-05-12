package com.testscripts.daasui;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.OnboardingVariables;
import com.daasui.pages.OnboardingPage;

public class OnboardingTest extends CommonTest {
	
	private static Logger LOGGER = LogManager.getLogger(OnboardingTest.class);
	
	
	/**
	 * This test will verify Windows PC operating systems tile information
	 */
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_SANITY" })
	public final void verifyOnboardingSetupPositiveFlow() throws Exception {

		String expectedMailContent = null, mailContent = null,subscriptionKey = generateRandomString(11),companyName= generateRandomString(11),firstName= generateRandomString(11),lastName= generateRandomString(11),phoneNumber= generateRandomNumber(),partnerName= generateRandomString(11),partnerFirstName= generateRandomString(11),partnerLastName= generateRandomString(11),partnerPhoneNumber= generateRandomNumber(),secureSubscription= generateRandomString(11);
		List<WebElement> stepperElements = null;
		getUrl(getEnvironment(System.getProperty("environment"))  + ConstantURL.ONBOARDING);
		sleeper(10000);
		OnboardingPage onboardingPage = new OnboardingPage(PreDefinedActions.getDriver()).getInstance();
		onboardingPage.waitForElementsOfOnboardingPage("welcomeText");
		SoftAssert softAssert = new SoftAssert();		
		Assert.assertTrue(onboardingPage.setAutomationVariable(), "Variable setting failed");

		//verification of step 1
		softAssert.assertTrue(onboardingPage.verifyElementsOfOnboardingPage("language"), "Language selection icon is not present on Onboarding page");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("welcomeText", getTextLanguage(LanguageCode, "daas_ui", "onboarding.welcome.title")), "Welcome text does not match");
		softAssert.assertTrue(onboardingPage.verifyElementsOfOnboardingPage("welcomeIcon"), "Welcome Icon not present");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("signupTextLine1", getTextLanguage(LanguageCode, "daas_ui", "onboarding.welcome.line1")), "Sign up text first line does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("accountSetupTitle", getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.setup")), "Account setup label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("accountSetupDescription", getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.setup_desc")), "Account setup Description does not match");
		sleeper(10000);
		onboardingPage.clickOnElementsOfOnboardingPage("startButton");		
		LOGGER.info("Clicked on Start button.");

		//verification of step 2 Company Information
		softAssert.assertTrue(onboardingPage.verifyElementsOfOnboardingPage("stepImage"), "Step Image is not present on Onboarding page");            
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepBannerDescription", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.description")), "Stepper description does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.title")), "Company Information Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label1", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.subscripiton_key_or_order_number")), "Subscription Key Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label2", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.name")), "Company Name Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label3", getTextLanguage(LanguageCode, "daas_ui", "global.address1")), "Address Line 1 Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label4", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.city")), "City Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label5", getTextLanguage(LanguageCode, "daas_ui", "subscription.list.state")), "State/Province Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label6", getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.zip-code")), "Zip code Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label7", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.country")), "Country Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("it_admin_header", getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.it_admin")), "IT Admin header does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("it_admin_description", getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.it_admin_desc")), "Description Of IT admin does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("user_header", getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.users")), "Users header does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("user_decription", getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.users_desc")), "Description Of Users does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("channel_partner_header", getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.partner")), "Channel partner header does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("channer_partner_decription", getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.partner_desc")), "Description Of channel does not match");	
		
		LOGGER.info("Add Company Information screen");
		onboardingPage.enterTextForOnboardingPage("subscription_key", OnboardingVariables.Subscription_Key);
		onboardingPage.enterTextForOnboardingPage("companyNameOnPopup", CommonVariables.COMPANY_NAME);
		onboardingPage.enterTextForOnboardingPage("addressOnPopup", CommonVariables.STREET_ADDRESS);
		onboardingPage.enterTextForOnboardingPage("cityOnPopup", CommonVariables.CITY);
		onboardingPage.enterTextForOnboardingPage("stateOnCompPopup", CommonVariables.STATE);
		onboardingPage.enterTextForOnboardingPage("zipCodeOnCompPopup", CommonVariables.ZIP_CODE);
		onboardingPage.selectElementFromDropDownofOnboardingPage("dropDown", "country_list", OnboardingVariables.COUNTRYUK);
		LOGGER.info("Country selected.");
		sleeper(10000);
		onboardingPage.clickByJavaScriptOnOnboardingPage("acknowledgeCheckbox");
		onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
		LOGGER.info("Clicked on Next button from company information page..");

		//verification of step 3 IT Administrator
		softAssert.assertTrue(onboardingPage.verifyElementsOfOnboardingPage("stepImage"), "Step Image is not present on Onboarding page");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepBannerDescription", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.description")), "Stepper description does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.title")), "IT Administrator Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label1", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.first_name")), "IT Admin First name Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label2", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.last_name")), "IT Admin Last name Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label3", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.email")), "IT Admin Email label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label4", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.phone")), "IT Admin phone Number label does not match");
		sleeper(10000);
		
		String compName = generateRandomString(10);
		String compEmail = compName.toLowerCase() + "@hpmsqa.mailinator.com";	
		onboardingPage.addCompanies(firstName, lastName, compEmail, phoneNumber);
		onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
		LOGGER.info("Clicked on Next button from IT Admin page.");
		sleeper(10000);
		//verification of step 4 Add Users
		softAssert.assertTrue(onboardingPage.verifyElementsOfOnboardingPage("stepImage"), "Step Image is not present on Onboarding page");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepBannerDescription", getTextLanguage(LanguageCode, "daas_ui", "onboarding.users.description")), "Stepper description does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.users.title")), "Add User Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label1", getTextLanguage(LanguageCode, "daas_ui", "onboarding.users.enter_emails")), "Add Email addresses Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("helperText", getTextLanguage(LanguageCode, "daas_ui", "onboarding.users.enter_emails_desc")), "Helper text Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("helperCount", OnboardingVariables.HELPPERCOUNT), "Helper character count does not match");
		onboardingPage.enterTextForOnboardingPage("addUsers", OnboardingVariables.USERSFIRSTMAILID + ";" + OnboardingVariables.USERSSECONDMAILID + ";" + OnboardingVariables.USERSTHIRDMAILID + ";" + OnboardingVariables.USERSFOURTHMAILID + ";" + OnboardingVariables.USERSFIFTHMAILID);
		sleeper(10000);
		onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
		LOGGER.info("Clicked on Next button from Users page.");

		//verification of step 5 Channel Partner Information 
		softAssert.assertTrue(onboardingPage.verifyElementsOfOnboardingPage("stepImage"), "Step Image is not present on Onboarding page");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepBannerDescription", getTextLanguage(LanguageCode, "daas_ui", "onboarding.partner.description")), "Stepper description does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.partner.title")), "Channel partner Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label1", getTextLanguage(LanguageCode, "daas_ui", "onboarding.partner.partner_name")), "Partner name Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("partnerContactInformation", getTextLanguage(LanguageCode, "daas_ui", "onboarding.partner.primary_contact_info")), "Partner Contact Information Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label2", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.first_name")), "Partner First name Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label3", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.last_name")), "Partner Last name Label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label4", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.email")), "Partner Email label does not match");
		softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("label5", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.phone")), "Partner phone Number label does not match");
		String partnerFirstName1 = generateRandomString(10);
		String partnerEmail = partnerFirstName1.toLowerCase() + "@hpmsqa.mailinator.com";						
		onboardingPage.addPartner(partnerName, partnerFirstName1, partnerLastName, partnerEmail, phoneNumber);
		
		onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
		sleeper(10000);
		
		onboardingPage.waitForElementsOfOnboardingPage("completeIcon");
		if(onboardingPage.matchTextOfOnboardingPage("completeText", getTextLanguage(LanguageCode, "daas_ui", "onboarding.failed.title")))
		{
		LOGGER.info("Onboarding process failed");
		}
	else {
		
			LOGGER.info("Onboarding process completed");
		}
		softAssert.assertAll();
		LOGGER.info("verifyOnboardingSetupPositiveFlow test case is successfully completed");
	}
	
	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI"}, enabled = false)
	public final void verifyOnboardingSetupPositiveFlowRussia() throws Exception {
		
			String expectedMailContent = null, mailContent = null,subscriptionKey = generateRandomString(11),companyName= generateRandomString(11),firstName= generateRandomString(11),lastName= generateRandomString(11),phoneNumber= generateRandomNumber(),partnerName= generateRandomString(11),partnerFirstName= generateRandomString(11),partnerLastName= generateRandomString(11),partnerPhoneNumber= generateRandomNumber(),secureSubscription= generateRandomString(11);
			getUrl(getEnvironment(System.getProperty("environment")) + ConstantURL.ONBOARDING);
			OnboardingPage onboardingPage = new OnboardingPage(PreDefinedActions.getDriver()).getInstance();
		
			SoftAssert softAssert = new SoftAssert();
			
			Assert.assertTrue(onboardingPage.setAutomationVariable(), "Variable setting failed");
			
			//verification of step 1
			
			onboardingPage.verifyElementsOfOnboardingPage("accountSteupNext");
			onboardingPage.clickOnElementsOfOnboardingPage("startButton");
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.title")), "Company Information Label does not match");
			onboardingPage.enterTextForOnboardingPage("subscriptionKeyTextBox", subscriptionKey);
			onboardingPage.enterTextForOnboardingPage("companyNameTextBox", companyName);
			onboardingPage.selectElementFromDropDownofOnboardingPage("dropDown", "dropDownList", OnboardingVariables.COUNTRYRUSSIA);
			onboardingPage.clickByJavaScriptOnOnboardingPage("acknowledgeCheckbox");
			onboardingPage.clickByJavaScriptOnOnboardingPage("acknowledgeCheckboxRussia");
			onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.title")), "IT Administrator Label does not match");
			onboardingPage.enterTextForOnboardingPage("FirstName", firstName);
			onboardingPage.enterTextForOnboardingPage("LastName", lastName);
			onboardingPage.enterTextForOnboardingPage("EmailName", OnboardingVariables.ITEMAIL);
			onboardingPage.enterTextForOnboardingPage("PhoneName", phoneNumber);
			onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
			
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.users.title")), "Add User Label does not match");
			
			onboardingPage.enterTextForOnboardingPage("addUsers", OnboardingVariables.USERSFIRSTMAILID + ";" + OnboardingVariables.USERSSECONDMAILID + ";" + OnboardingVariables.USERSTHIRDMAILID + ";" + OnboardingVariables.USERSFOURTHMAILID + ";" + OnboardingVariables.USERSFIFTHMAILID);
			onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.partner.title")), "Channel Partner Information Label does not match");
			onboardingPage.enterTextForOnboardingPage("partnerName", partnerName);
			onboardingPage.enterTextForOnboardingPage("FirstName", partnerFirstName);
			onboardingPage.enterTextForOnboardingPage("LastName", partnerLastName);
			onboardingPage.enterTextForOnboardingPage("EmailName", OnboardingVariables.PARTNEREMAIL);
			onboardingPage.enterTextForOnboardingPage("PhoneName", partnerPhoneNumber);
			onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.security.title")), "Secure Your Devices Label does not match");
			onboardingPage.enterTextForOnboardingPage("subscriptionKeyTextBox", secureSubscription);
			onboardingPage.clickOnElementsOfOnboardingPage("nextButton");

			
			onboardingPage.waitForElementsOfOnboardingPage("completeIcon");
			if(onboardingPage.matchTextOfOnboardingPage("completeText", getTextLanguage(LanguageCode, "daas_ui", "onboarding.failed.title")))
			{
				LOGGER.info("Onboarding process failed");
			}
			else {
				mailContent = onboardingPage.getMailContent();	
				expectedMailContent = "<div><p><b>Company Information</b></p><p><span>Company Name</span><b>:</b> <span>" + companyName + "</span></p><p><span>Country</span><b>:</b> <span>" + OnboardingVariables.COUNTRYRUSSIA + "</span></p><p><span>Subscripton Key</span><b>:</b> <span>" + subscriptionKey + "</span></p><p><span>DaaS Terms and Conditions accepted</span><b>:</b> <span>Yes</span></p><p><span>Order on Personal Data Processing accepted</span><b>:</b> <span>Yes</span></p><p><b>IT Administrator</b></p><p><span>First Name</span><b>:</b> <span>" + firstName + "</span></p><p><span>Last Name</span><b>:</b> <span>" + lastName + "</span></p><p><span>Email</span><b>:</b> <span>" + OnboardingVariables.ITEMAIL + "</span></p><p><span>Phone Number</span><b>:</b> <span>" + phoneNumber + "</span></p><p><b>Data Analysts</b></p><p><span><span><p>" + OnboardingVariables.USERSFIRSTMAILID + "</p><p>" + OnboardingVariables.USERSSECONDMAILID + "</p><p>" + OnboardingVariables.USERSTHIRDMAILID + "</p><p>" + OnboardingVariables.USERSFOURTHMAILID + "</p><p>" + OnboardingVariables.USERSFIFTHMAILID + "</p></span></p><p><b>Channel Partner Information</b></p><p><span>Partner Name</span><b>:</b> <span>" + partnerName + "</span></p><p><span>Primary Contact First Name</span><b>:</b> <span>" + partnerFirstName + "</span></p><p><span>Primary Contact Last Name</span><b>:</b> <span>" + partnerLastName + "</span></p><p><span>Primary Contact Email Address</span><b>:</b> <span>" + OnboardingVariables.PARTNEREMAIL + "</span></p><p><span>Primary Contact Phone Number</span><b>:</b> <span>" + partnerPhoneNumber + "</span></p><p><b>Security Add-On</b></p><p><span>Subscription Key</span><b>:</b> <span>" + secureSubscription + "</span></p></div>";
				softAssert.assertTrue(mailContent.equals(expectedMailContent), "Mail content does not match");
				
			}
			
			softAssert.assertAll();
			LOGGER.info("verifyOnboardingSetupPositiveFlowRussia test case is successfully completed");
	
	}
	
	
	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_SANITY"}, enabled = false)
	public final void verifyOnboardingSetupPositiveFlowWithSkipSteps() throws Exception {
		
			String expectedMailContent = null, mailContent = null,subscriptionKey = generateRandomString(11),companyName= generateRandomString(11),firstName= generateRandomString(11),lastName= generateRandomString(11),phoneNumber= generateRandomNumber();
			getUrl(getEnvironment(System.getProperty("environment")) + ConstantURL.ONBOARDING);
			OnboardingPage onboardingPage = new OnboardingPage(PreDefinedActions.getDriver()).getInstance();
			
			SoftAssert softAssert = new SoftAssert();
			
			Assert.assertTrue(onboardingPage.setAutomationVariable(), "Variable setting failed");
			
			//verification of step 1
			
			onboardingPage.verifyElementsOfOnboardingPage("accountSteupNext");
			onboardingPage.clickOnElementsOfOnboardingPage("startButton");
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.company.title")), "Company Information Label does not match");
			onboardingPage.enterTextForOnboardingPage("subscriptionKeyTextBox", subscriptionKey);
			onboardingPage.enterTextForOnboardingPage("companyNameTextBox", companyName);
			onboardingPage.selectElementFromDropDownofOnboardingPage("dropDown", "dropDownList", OnboardingVariables.COUNTRYUK);
			onboardingPage.clickByJavaScriptOnOnboardingPage("acknowledgeCheckbox");
			onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.it_admin.title")), "IT Administrator Label does not match");
			onboardingPage.enterTextForOnboardingPage("FirstName", firstName);
			onboardingPage.enterTextForOnboardingPage("LastName", lastName);
			onboardingPage.enterTextForOnboardingPage("EmailName", OnboardingVariables.ITEMAIL);
			onboardingPage.enterTextForOnboardingPage("PhoneName", phoneNumber);
			onboardingPage.clickOnElementsOfOnboardingPage("nextButton");
			
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.users.title")), "Add User Label does not match");
			onboardingPage.clickOnElementsOfOnboardingPage("skipButton");
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.partner.title")), "Channel Partner Information Label does not match");
			onboardingPage.clickOnElementsOfOnboardingPage("skipButton");
			
			softAssert.assertTrue(onboardingPage.matchTextOfOnboardingPage("stepHeader", getTextLanguage(LanguageCode, "daas_ui", "onboarding.security.title")), "Secure Your Devices Label does not match");
			onboardingPage.clickOnElementsOfOnboardingPage("skipButton");

			
			onboardingPage.waitForElementsOfOnboardingPage("completeIcon");
			if(onboardingPage.matchTextOfOnboardingPage("completeText", getTextLanguage(LanguageCode, "daas_ui", "onboarding.failed.title")))
			{
				LOGGER.info("Onboarding process failed");
			}
			else {
				mailContent = onboardingPage.getMailContent();	
				expectedMailContent = "<div><p><b>Company Information</b></p><p><span>Company Name</span><b>:</b> <span>" + companyName + "</span></p><p><span>Country</span><b>:</b> <span>" + OnboardingVariables.COUNTRYUK + "</span></p><p><span>Subscripton Key</span><b>:</b> <span>" + subscriptionKey + "</span></p><p><span>DaaS Terms and Conditions accepted</span><b>:</b> <span>Yes</span></p><p><b>IT Administrator</b></p><p><span>First Name</span><b>:</b> <span>" + firstName + "</span></p><p><span>Last Name</span><b>:</b> <span>" + lastName + "</span></p><p><span>Email</span><b>:</b> <span>" + OnboardingVariables.ITEMAIL + "</span></p><p><span>Phone Number</span><b>:</b> <span>" + phoneNumber + "</span></p></div>";
				softAssert.assertTrue(mailContent.equals(expectedMailContent), "Mail content does not match");
				
			}
			
			softAssert.assertAll();
			LOGGER.info("verifyOnboardingSetupPositiveFlowWithSkipSteps test case is successfully completed");
		
	}
}