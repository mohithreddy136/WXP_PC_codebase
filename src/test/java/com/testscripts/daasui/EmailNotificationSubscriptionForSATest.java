package com.testscripts.daasui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CompanyPin;
import com.basesource.utils.EnrollFakeDevice;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.UserPage;

/**
 * EmailNotificationSubscriptionForSATest : For testing email notification functionality of incident addition This test case is a part of feature 123739([MPI] Email notification to
 * SS for new incidents)
 * 
 * @author kumargup
 *
 */
public class EmailNotificationSubscriptionForSATest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(EmailNotificationSubscriptionForSATest.class);

	/**
	 * When SA has subscribed for email notification for incidents This test scenarios validates whether email is getting triggered in this case and email content has following fields
	 * as per feature information: Subject: Case ID - Title Body: Case ID: Title: Description: Type: Subtype: Created on: Created by: Priority: Status: Company: User name: Computer
	 * name: In addition to above validation this test scenario also covers that different links given in the generated emails are redirecting to their corresponding pages and details
	 * are matching to the submitted incident this includes: Company/Username/Computer name hyperlinks in the email
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION" }, description = "Feature 123739:[MPI] Email notification to SS for new incidents",enabled=false)
	public final void emailNotificationSubscriptionForSATest() throws Exception {
		try {
			commonSteps(false);
			gotoCompaniesTab();
			LOGGER.info("Step 5: Impersonate test company for adding a fake device under it.");
			impersonateCompanyByEmail(getCredentials(environment, "MSP_REPORT_ADMIN_EMAIL"));
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			
			LOGGER.info("Step 6: Capture company details");
			String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
			LOGGER.info("CompanyName is: " + companyName);
			String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
			LOGGER.info("Company EmailId is: " + companyEmailId);
			CompanyPin companypin = new CompanyPin();
			String companyPin = companypin.getCompanyPin();
			LOGGER.info("companyPin is : " + companyPin);
			LOGGER.info("Step 7: Go to preference tab and validate whether incident submission is allowed or not");
			companiesPage.gotoPreferencesTab();
			companiesPage.checkSubmitIncidentToggle();
			LOGGER.info("Step 8: Enroll a fake device for this company");
			HashMap<String, String> deviceDetails = new HashMap<>();
			deviceDetails = EnrollFakeDevice.enrollFakeDevice(companyName, companyPin, companyEmailId);
			String deviceName = deviceDetails.get("deviceName");
			LOGGER.info(deviceName + " fake Device is Enrolled Successfully");
			String deviceId = deviceName.split(" ")[2];
			DeviceDetailsPage devicepage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			
			LOGGER.info("Step 9: Validate whether added fake device is showing on device page");
			gotoDeviceTab();
			devicepage.waitForElementsOfDeviceDetailsPage("deviceListTable");
			devicepage.enterTextForDeviceDetailsPage("deviceSearchField", deviceId);
			sleeper(2000); // After search it's take some time to get exact text from table.
			devicepage.waitForElementsOfDeviceDetailsPage("deviceListTable");
			sleeper(2000);
			String deviceNameAfterSearch = devicepage.getTextOfDeviceDetailsPage("founddeviceinsearchresult");
			Assert.assertEquals(deviceNameAfterSearch.contains(deviceId), true);
			LOGGER.info("Device is present on Devices page.");
			LOGGER.info("Step 10: Go to added device page and add an incident");
			LOGGER.info("Now click on this enrolled device link shown in the search result table");
			devicepage.clickOnElementsOfDeviceDetailsPage("founddeviceinsearchresult");
			String incidentTitle = "Test Incident for enrolled device " + deviceId;
			String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account");
			String subtype = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.account");
			Map<String, String> incidentDetails = devicepage.submitCase(type, subtype, incidentTitle, "");
			incidentDetails.put("incidentCreatedBy", getCredentials(environment, "MSP_ADMIN_US_EMAIl_04"));
			incidentDetails.put("incidentPriority", devicepage.getTextOfDeviceDetailsPage("incidentPriorityInBottomTable"));
			incidentDetails.put("incidentStatus", devicepage.getTextOfDeviceDetailsPage("incidentStatusInBottomTable"));
			incidentDetails.put("incidentCompanyName", companyName);
			incidentDetails.put("incidentUserName", getCredentials(environment, "MSP_REPORT_ADMIN_EMAIL"));
			incidentDetails.put("incidentComputerName", deviceName);
			LOGGER.info("Validate added incident id in the bottom table");
			Assert.assertTrue(devicepage.getTextOfDeviceDetailsPage("incidentIDInBottomTable").equalsIgnoreCase(incidentDetails.get("incidentid")), "Expected incident id in the bottom table: " + incidentDetails.get("incidentid") + " ,Actual value of incident id in the bottom table: " + devicepage.getTextOfDeviceDetailsPage("incidentIDInBottomTable"));
			LOGGER.info("Step 11: Create mailinator object and use it to get all the emails of the associated inbox of the User");
			/*Mailinator objMailinator = new Mailinator("", getCredentials(environment, "MSP_ADMIN_US_EMAIl_04"));
			MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(incidentDetails.get("incidentid"));
			LOGGER.info("Step 12: Validate whether details on the received incident email is same as the submitted incident");
			Assert.assertNotNull(objMailinatorEmail, "Test failed as email is not received for submitted incident");
			validateIncidentEmailContent(incidentDetails, objMailinatorEmail, LanguageCode);

			LOGGER.info("Step 13: Validate incident case URL given in the email is redirecting to the right page and details are same as submitted incident.");
			IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
			
			String incidentCaseIDURL = objMailinator.getIncidentCaseIDLink(incidentDetails.get("incidentid"), objMailinatorEmail, LanguageCode);
			getDriver().get(incidentCaseIDURL);
			Assert.assertTrue(getUrlOfCurrentPage().contains(getEnvironment(System.getProperty("environment"))), "Navigated incident case URL is different from the expected URL");
			Assert.assertTrue(incidentDetails.get("incidentid").equalsIgnoreCase(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("incidentId")), "Incident ID " + incidentDetails.get("incidentid") + " not found on open incident details page. Expected: " + incidentDetails.get("incidentid") + ", Actual:" + incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("incidentId"));

			LOGGER.info("Step 14: Get the incident company URL from the email and validate it is redirecting to the right page and details are same as submitted incident.");
			String incidentCompanyURL = objMailinator.getIncidentCompanyLink(incidentDetails.get("incidentCompanyName"), objMailinatorEmail, LanguageCode);
			getDriver().get(incidentCompanyURL);
			Assert.assertTrue(getUrlOfCurrentPage().contains(getEnvironment(System.getProperty("environment"))), "Navigated company page URL is different from the expected URL");
			sleeper(2000);
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			
			dashboardPage.clickOnElementsOfDashboardPage("dropdownBoxCompany");
			dashboardPage.waitForElementsOfDashboardPage("firstSelectedCompanyInCompanyDropdown");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("firstSelectedCompanyInCompanyDropdown").equalsIgnoreCase(incidentDetails.get("incidentCompanyName")), "Company " + incidentDetails.get("incidentCompanyName") + " not found on open dashboard page. Expected string to find: " + incidentDetails.get("incidentCompanyName") + ", Actual string to be searched:" + dashboardPage.getTextOfDashboardPage("firstSelectedCompanyInCompanyDropdown"));

			LOGGER.info("Step 15: Get the user name url from the email and validate that is redirecting to the right page and details are same as submitted incident.");
			String incidentUserNameURL = objMailinator.getIncidentUserNameLink(objMailinatorEmail, LanguageCode);
			getDriver().get(incidentUserNameURL);
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			
			Assert.assertTrue(getUrlOfCurrentPage().contains(getEnvironment(System.getProperty("environment"))), "Navigated User page URL is different from the expected URL");
			Assert.assertTrue(userPage.getTextOfUserPage("companyNameShow").equalsIgnoreCase(incidentDetails.get("incidentCompanyName")), "Company " + incidentDetails.get("incidentCompanyName") + " not found on User Details page. Expected string to find: " + incidentDetails.get("incidentCompanyName") + ", Actual:" + userPage.getTextOfUserPage("companyNameShow"));

			LOGGER.info("Step 16: Get the devive type url from the email and validate that it is redirecting to the right page and details are same as submitted incident.");
			String incidentComputerNameURL = objMailinator.getIncidentComputerNameLink(incidentDetails.get("incidentComputerName"), objMailinatorEmail, LanguageCode);
			getDriver().get(incidentComputerNameURL);
			Assert.assertTrue(getUrlOfCurrentPage().contains(getEnvironment(System.getProperty("environment"))), "Navigated Computer Name URL is different from the expected URL");
			Assert.assertTrue(devicepage.getTextOfDeviceDetailsPage("deviceType").equalsIgnoreCase(incidentDetails.get("incidentComputerName")), "Device Type " + incidentDetails.get("incidentComputerName") + " not found on Device details page. Expected string to find: " + incidentDetails.get("incidentComputerName") + ", Actual:" + devicepage.getTextOfDeviceDetailsPage("deviceType"));*/
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in tesREGRESSIONt method emailNotificationSubscriptionForSATest:" + ex.toString());
			throw ex;
		}
	}

	/**
	 * When SA has not subscribed for email notification for incidents This test scenarios validates that no email notification should be triggered in this case
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION" }, description = "Feature 123739:[MPI] Email notification to SS for new incidents",enabled=false)
	public final void noEmailNotificationSubscriptionForSATest() throws Exception {
		try {
			commonSteps(true);
			gotoCompaniesTab();
			LOGGER.info("Step 5: Impersonate test company for adding a fake device under it.");
			impersonateCompanyByEmail(getCredentials(environment, "MSP_REPORT_ADMIN_EMAIL"));
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			
			LOGGER.info("Step 6: Capture company details");
			String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
			LOGGER.info("CompanyName is: " + companyName);
			String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
			LOGGER.info("Company EmailId is: " + companyEmailId);
			CompanyPin companypin = new CompanyPin();
			String companyPin = companypin.getCompanyPin();
			LOGGER.info("companyPin is : " + companyPin);
			LOGGER.info("Step 7: Go to preference tab and validate whether incident submission is allowed or not");
			companiesPage.gotoPreferencesTab();
			companiesPage.checkSubmitIncidentToggle();
			LOGGER.info("Step 8: Enroll a fake device for this company");
			HashMap<String, String> deviceDetails = new HashMap<>();
			deviceDetails = EnrollFakeDevice.enrollFakeDevice(companyName, companyPin, companyEmailId);
			String deviceName = deviceDetails.get("deviceName");
			LOGGER.info(deviceName + " fake Device is Enrolled Successfully");
			String deviceId = deviceName.split(" ")[2];
			DeviceDetailsPage devicepage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
			
			LOGGER.info("Step 9: Validate whether added fake device is showing on device page");
			gotoDeviceTab();
			devicepage.waitForElementsOfDeviceDetailsPage("deviceListTable");
			devicepage.enterTextForDeviceDetailsPage("deviceSearchField", deviceId);
			sleeper(2000); // After search it's take some time to get exact text from table.
			devicepage.waitForElementsOfDeviceDetailsPage("deviceListTable");
			sleeper(2000);
			String deviceNameAfterSearch = devicepage.getTextOfDeviceDetailsPage("founddeviceinsearchresult");
			Assert.assertEquals(deviceNameAfterSearch.contains(deviceId), true);
			LOGGER.info("Device is present on Devices page.");
			LOGGER.info("Step 10: Go to added device page and add an incident");
			LOGGER.info("Now click on this enrolled device link shown in the search result table");
			devicepage.clickOnElementsOfDeviceDetailsPage("founddeviceinsearchresult");
			String incidentTitle = "Test Incident for enrolled device " + deviceId;
			String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account");
			String subtype = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.account");
			Map<String, String> incidentDetails = devicepage.submitCase(type, subtype, incidentTitle, "");
			incidentDetails.put("incidentCreatedBy", getCredentials(environment, "MSP_ADMIN_US_EMAIl_04"));
			incidentDetails.put("incidentPriority", devicepage.getTextOfDeviceDetailsPage("incidentPriorityInBottomTable"));
			incidentDetails.put("incidentStatus", devicepage.getTextOfDeviceDetailsPage("incidentStatusInBottomTable"));
			incidentDetails.put("incidentCompanyName", companyName);
			incidentDetails.put("incidentUserName", getCredentials(environment, "MSP_REPORT_ADMIN_EMAIL"));
			incidentDetails.put("incidentComputerName", deviceName);
			LOGGER.info("Validate added incident id in the bottom table");
			Assert.assertTrue(devicepage.getTextOfDeviceDetailsPage("incidentIDInBottomTable").equalsIgnoreCase(incidentDetails.get("incidentid")), "Expected incident id in the bottom table: " + incidentDetails.get("incidentid") + " ,Actual value of incident id in the bottom table: " + devicepage.getTextOfDeviceDetailsPage("incidentIDInBottomTable"));
			LOGGER.info("Step 11: Create mailinator object and use it to get all the emails of the associated inbox of the User");
			/*Mailinator objMailinator = new Mailinator("", getCredentials(environment, "MSP_ADMIN_US_EMAIl_04"));
			MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(incidentDetails.get("incidentid"));
			LOGGER.info("Step 12: Validate no email is received for the submitted incident");
			Assert.assertNull(objMailinatorEmail, "Test failed as email is received for submitted incident");*/
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in test method noEmailNotificationSubscriptionForSATest:" + ex.toString());
			throw ex;
		}
	}

	/**
	 * As per review comments moved common steps to this method that to be used by above test cases
	 * 
	 * @throws Exception
	 */
	public void commonSteps(boolean emailNotificationToBeChecked) throws Exception {
		try {
			login("MSP_ADMIN_US_EMAIl_04", "MSP_ADMIN_US_PASSWORD_04");
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			LOGGER.info("Step 1: Clicked on top right User Icon to get user details popup menu");
			sleeper(2000);
			dashboardPage.clickOnElementsOfDashboardPage("userDetailsLink");
			LOGGER.info("Step 2: Clicked on user details link in the shown popup menu");
			SoftAssert sa = new SoftAssert();
			LOGGER.info("Step 3: Validate whether we have reached to User Details page or not");
			sa.assertTrue(userPage.verifyElementsOfUserPage("userTitle"), "User Title is available at the top of the page, means we have reached to User Page.");
			LOGGER.info("Step 4: Validate whether email notification checkbox is checked or not, if yes, then we need to uncheck it");
			if (emailNotificationToBeChecked) {
				if (userPage.verifyElementIsSelectedOfUserPage("emailNotificationCheckbox")) {
					LOGGER.info("To check email notification checkbox we need to click on the edit button first");
					userPage.clickOnElementsOfUserPage("editIncidentNotificationsButton");
					LOGGER.info("Now click on the email notification checkbox to check it");
					userPage.clickOnElementsOfUserPage("emailNotificationCheckbox");
					LOGGER.info("Save the made changes with email notification");
					userPage.clickOnElementsOfUserPage("saveIncidentNotificationsButton");
				}
			} else {
				if (!userPage.verifyElementIsSelectedOfUserPage("emailNotificationCheckbox")) {
					LOGGER.info("To check email notification checkbox we need to click on the edit button first");
					userPage.clickOnElementsOfUserPage("editIncidentNotificationsButton");
					LOGGER.info("Now click on the email notification checkbox to check it");
					userPage.clickOnElementsOfUserPage("emailNotificationCheckbox");
					LOGGER.info("Save the made changes with email notification");
					userPage.clickOnElementsOfUserPage("saveIncidentNotificationsButton");
				}
			}

		} catch (Exception ex) {
			LOGGER.error("Exception thrown in test method commonSteps:" + ex.toString());
			throw ex;
		}
	}
	
	/**
	 * This method is used to validate email content whether email content contain the expected incident information
	 * 
	 * @param incidentDetails
	 * @param objMailinatorEmail
	 * @throws Exception
	 */
	public void validateIncidentEmailContent(Map<String, String> incidentDetails, MailinatorMail objMailinatorEmail, String languageCode) throws Exception {
		try {
			String emailBody = objMailinatorEmail.getBody();
			SoftAssert objSoftAssert = new SoftAssert();
			for (Map.Entry<String, String> entry : incidentDetails.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				switch (key.toLowerCase()) {
				case "incidentid":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.case.id") + ":.*" + value), "Validate Incident ID in the mailinator email: " + value);
					break;
				case "incidentdescription":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.discription") + ":.*" + value), "Validate Description in the mailinator email: " + value);
					break;
				case "incidenttitle":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.title") + ":.*" + value), "Validate Title in the mailinator email: " + value);
					break;
				case "incidenttype":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.type") + ":.*" + value), "Validate Type in the mailinator email: " + value);
					break;
				case "incidentsubtype":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.subtype") + ":.*" + value), "Validate Subtype in the mailinator email: " + value);
					break;
				case "incidentcreatedon":
					String[] splittedDate = value.split(" ");
					if (splittedDate[0].contains("，")) {
						splittedDate[0] = splittedDate[0].split("，")[0];
					}
					if (!splittedDate[0].contains("/")) {
						value = convertCreatedOnDateFormat(splittedDate[0] + " " + splittedDate[1] + " " + splittedDate[2]); // Added this to take care of date format difference
						// between application and reports
					} else {
						value = splittedDate[0];
					}

					if (languageCode.toLowerCase().equals("ja")) {
						splittedDate = value.split("/");
						value = splittedDate[1] + "/" + splittedDate[2] + "/" + splittedDate[0];// To handle the special case for Japan locale
					}

					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.created_on") + ":.*" + value), "Validate Created on in the mailinator email: " + value);
					break;
				case "incidentcreatedby":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.created_by") + ":.*" + value), "Validate Created by in the mailinator email: " + value);
					break;
				case "incidentpriority":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.priority") + ":.*" + value), "Validate Priority in the mailinator email: " + value);
					break;
				case "incidentstatus":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.incident.status") + ":.*" + value), "Validate Status in the mailinator email: " + value);
					break;
				case "incidentcompanyname":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.company") + ":.*" + value), "Validate Company in the mailinator email: " + value);
					break;
				case "incidentusername":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.username") + ":.*" + value), "Validate User name in the mailinator email: " + value);
					break;
				case "incidentcomputername":
					objSoftAssert.assertTrue(findStringUsingRegEx(emailBody, getTextLanguage(languageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.mail.computername") + ":.*" + value), "Validate Computer name in the mailinator email: " + value);
					break;
				default:
					LOGGER.error("Incorrect incident parameter is passed for this method, please check " + key);
					break;
				}
			}

		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method validateIncidentEmailContent:" + ex.toString());
			throw ex;
		}
	}
	
	/**
	 * For checking the existence of one string in other string
	 * 
	 * @param mainString
	 * @param patternString
	 * @return true or false
	 * @throws Exception
	 */
	public boolean findStringUsingRegEx(String mainString, String patternString) throws Exception {
		boolean foundFlag = false;
		try {
			Pattern p = Pattern.compile(patternString); // the pattern to search for
			Matcher m = p.matcher(mainString);

			// now try to find at least one match
			if (m.find()) {
				LOGGER.info(patternString + " found in " + mainString);
				foundFlag = true;
			} else {
				LOGGER.info(patternString + " not found in " + mainString);
				foundFlag = false;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method findStringUsingRegEx" + ex.toString());
			foundFlag = false;
			throw ex;
		}
		return foundFlag;
	}
	
	/**
	 * For getting incident created on date format in dd/mm/yyyy from dd-MMM-yyyy
	 * 
	 * @param incidentCreatedOn -incident Created On date in dd-MMM-yyyy format
	 * @return
	 * @throws Exception
	 */
	public static String convertCreatedOnDateFormat(String incidentCreatedOn) throws Exception {
		String incidentCreatedOnConverted = "";
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("dd MMM yyyy").parse(incidentCreatedOn));
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			incidentCreatedOnConverted = sdf.format(cal.getTime());
			LOGGER.info(incidentCreatedOn + " converted by convertCreatedOnDateFormat to " + sdf.format(cal.getTime()));
		} catch (Exception ex) {
			LOGGER.error("Exception thrown in method convertCreatedOnDateFormat:" + ex.toString());
			throw ex;
		}
		return incidentCreatedOnConverted;
	}
}
