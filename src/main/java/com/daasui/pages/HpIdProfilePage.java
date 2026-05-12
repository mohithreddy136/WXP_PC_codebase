package com.daasui.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;

/**
 * This class contains all methods used in HpIdTest class
 * 
 * @author ajit
 *
 */
public class HpIdProfilePage extends CommonMethod {
	private ObjectReader hpIdProfilePagePropertiesReader = new ObjectReader();
	private Properties hpIdProfilePagePropertiesPageProperties;
	public static String environment;
	private Properties selectedLanguageProperties;
	private static Properties environmentPageProperties;
	private Properties selecteCredentialsProperties;
	private ObjectReader commonTestPropertiesReader = new ObjectReader();
	private static ObjectReader environmentPropertiesReader = new ObjectReader();
	public static String LanguageCode;
	private HpIdProfilePage instance;

	public HpIdProfilePage getInstance() throws IOException {
		if (instance == null) {
			synchronized (HpIdProfilePage.class) {
				if (instance == null) {
					instance = new HpIdProfilePage(DRIVER);

				}
			}
		}
		return instance;
	}

	public HpIdProfilePage(WebDriver driver) throws IOException {
		hpIdProfilePagePropertiesPageProperties = hpIdProfilePagePropertiesReader.getObjectRepository("HpIdProfilePage");
	}

	public final boolean verifyElementsOfHpIdProfilePage(String key) throws Exception {
		return verifyElementIsPresent(hpIdProfilePagePropertiesPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfHpIdProfilePage(String key) throws Exception {
		return verifyElementIsVisible(hpIdProfilePagePropertiesPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfHpIdProfilePageForClick(String key) throws Exception {
		return verifyElementIsClickable(hpIdProfilePagePropertiesPageProperties.getProperty(key));
	}

	public final boolean matchTextOfHpIdProfilePage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(hpIdProfilePagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfHpIdProfilePage(String key) throws Exception {
		return verifyElementIsEnable(hpIdProfilePagePropertiesPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfHpIdProfilePage(String key) throws Exception {
		return verifyElementIsSelected(hpIdProfilePagePropertiesPageProperties.getProperty(key));
	}

	public final String getTextOfHpIdProfilePage(String key) throws Exception {
		return getTextBy(hpIdProfilePagePropertiesPageProperties.getProperty(key));
	}

	public final String getAttributesOfHpIdProfilePage(String key, String value) throws Exception {
		return getAttribute(hpIdProfilePagePropertiesPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfHpIdProfilePage(String key) throws Exception {
		click(hpIdProfilePagePropertiesPageProperties.getProperty(key));
	}

	public final void enterTextForHpIdProfilePage(String key, String Text) throws Exception {
		enterText(hpIdProfilePagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfHpIdProfilePage(String key) throws Exception {
		return verifyElementIsClickable(hpIdProfilePagePropertiesPageProperties.getProperty(key));
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = hpIdProfilePagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final String getCredentials(String credentials, String key) throws Exception {
		selecteCredentialsProperties = commonTestPropertiesReader.getCredentials(credentials);
		return selecteCredentialsProperties.getProperty(key);
	}

	/**
	 * This method is used for DaaS logout
	 * 
	 * @throws Exception
	 */
	public final void logout() throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(2000);
		getDriver().get(getDaaSUrl());
		dashboardPage.waitForElementsOfDashboardPage("deviceByTypeChart");
		dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
		sleeper(2000);
		dashboardPage.clickOnElementsOfDashboardPage("signoutButton");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		loginPage.waitForElementsOfLoginPage("loginTitle");
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("loginTitle"), "HP WebApp not open successfully");

	}

	/**
	 * This method returns the HpId url according to DaaS environment
	 * 
	 * @param enviornment
	 * @return
	 * @throws IOException
	 */
	public static String getHpIdEnviornment(String enviornment) throws IOException {
		environmentPageProperties = environmentPropertiesReader.getObjectRepository("environment");
		if (enviornment.equalsIgnoreCase("LATEST") || enviornment.equalsIgnoreCase("US-STABLE") || enviornment.equalsIgnoreCase("EU-STABLE")) {
			return environmentPageProperties.getProperty("HpId_Staging");
		} else {
			return environmentPageProperties.getProperty("HpId_Prod");
		}
	}

	/**
	 * This method returns the HPID profile page url according to the system environment
	 * 
	 * @return
	 * @throws IOException
	 */
	public final String getHpIdUrl() throws IOException {
		return getHpIdEnviornment(System.getProperty("environment"));
	}

	public static String getEnvironment(String enviornment) {
		try {
			environmentPageProperties = environmentPropertiesReader.getObjectRepository("environment");
			switch (enviornment.toUpperCase()) {
			case "LATEST":
				return environmentPageProperties.getProperty("Latest");

			case "US-STABLE":
				return environmentPageProperties.getProperty("StableUS");

			case "US-PEM":
				return environmentPageProperties.getProperty("PEMUS");

			case "EU-STABLE":
				return environmentPageProperties.getProperty("StableEU");

			case "US-STAGING":
				return environmentPageProperties.getProperty("StagingUS");

			case "EU-STAGING":
				return environmentPageProperties.getProperty("StagingEU");

			case "US-PRODUCTION":
				return environmentPageProperties.getProperty("ProdUS");

			case "EU-PRODUCTION":
				return environmentPageProperties.getProperty("ProdEU");

			default:
				LOGGER.info("Provided : " + enviornment + " Provided enviornment is wrong");
				throw new InputMismatchException("You can use : US-STABLE, EU-STABLE, US-PRODUCTION, EU-PRODUCTION, US-STAGING, EU-STAGING, LATEST only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method returns the DaaS url according to the system environment
	 * 
	 * @return
	 * @throws IOException
	 */
	public final String getDaaSUrl() throws IOException {
		return getEnvironment(System.getProperty("environment")) + "/home";
	}

	/**
	 * HPID Login method
	 * 
	 * @param email
	 * @param password
	 * @throws Exception
	 */
	public final void loginHpIdProfile(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver()).getInstance();
		getUrl(hpIdProfilePage.getHpIdUrl());
		loginPage.waitForElementsOfLoginPage("emailInputField");
		loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
		loginPage.clickOnElementsOfLoginPage("nextbutton");
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitle"), "Password page did not open successfully");
		loginPage.waitForElementsOfLoginPage("passwordInputField");
		loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, password));
		loginPage.clickOnElementsOfLoginPage("submitButton");
		hpIdProfilePage.waitForElementsOfHpIdProfilePage("hpidProfileTitle");
		Assert.assertTrue(hpIdProfilePage.verifyElementsOfHpIdProfilePage("hpidProfileTitle"), "HPID WebApp not login successfully");
	}

	/**
	 * HPID Logout method
	 * 
	 * @throws Exception
	 */
	public final void logoutHpIdProfile() throws Exception {
		HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver()).getInstance();
		getUrl(hpIdProfilePage.getHpIdUrl());
		hpIdProfilePage.clickOnElementsOfHpIdProfilePage("logoutIcon");
		hpIdProfilePage.waitForElementsOfHpIdProfilePage("signOut");
		hpIdProfilePage.clickOnElementsOfHpIdProfilePage("signOut");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		loginPage.waitForElementsOfLoginPage("loginTitle");
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("loginTitle"), "HP WebApp not open successfully");
	}

	/**
	 * This is function for validating HPID signin
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean checkHPIDSignIn() throws Exception {
		boolean flag;
		createAndSwitchToNewTab();
		getUrl(getHpIdUrl());
		if (waitForElementsOfHpIdProfilePage("menu")) {
			flag = true;
			logoutHpIdProfile();
			switchBackToPreviousTab();
		} else {
			flag = false;
			switchBackToPreviousTab();
		}
		return flag;
	}

	/**
	 * This is function for validating DaaS signin
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean checkDaaSSignIn() throws Exception {
		boolean flag;
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		createAndSwitchToNewTab();
		String newWindow = getDriver().getWindowHandle();
		loginHpIdProfile("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		if (waitForElementsOfHpIdProfilePage("menu")) {
			switchBackToParentWithoutCloseTab();
			getUrl(getDaaSUrl());
			dashboardPage.waitForElementsOfDashboardPage("dashboardTitle");
			if (dashboardPage.verifyElementsOfDashboardPage("dashboardTitle")) {
				flag = true;
				switchToDifferentTab();
				logoutHpIdProfile();
				switchBackToPreviousTab();
			} else {
				flag = false;
				switchToDifferentTab();
				logoutHpIdProfile();
				switchBackToPreviousTab();
			}

		} else {
			flag = false;
			switchBackToPreviousTab();

		}
		closeNewWindow(newWindow);
		return flag;

	}

	/**
	 * This is function for validating HPID signout
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean checkHPIDSignOut() throws Exception {
		boolean flag;
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		createAndSwitchToNewTab();
		String newWindow = getDriver().getWindowHandle();
		getUrl(getHpIdUrl());
		if (!waitForElementsOfHpIdProfilePage("menu")) {
			closeNewWindow(newWindow);
			return false;
		}
		logoutHpIdProfile();
		switchBackToPreviousTab();
		refreshPage();
		if (dashboardPage.waitForElementsOfDashboardPage("dashboardTitle")) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * This is function for validating DaaS signout
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean checkDaaSSignOut() throws Exception {

		createAndSwitchToNewTab();
		String newWindow = getDriver().getWindowHandle();
		getUrl(getHpIdUrl());
		if (waitForElementsOfHpIdProfilePage("menu")) {
			switchBackToParentWithoutCloseTab();
			logout();
			switchToDifferentTab();
			refreshPage();
			LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
			if (loginPage.waitForElementsOfLoginPage("loginTitle")) {
				switchBackToPreviousTab();
				return true;
			} else {
				closeNewWindow(newWindow);
				return false;
			}
		} else {
			closeNewWindow(newWindow);
			return false;
		}

	}

	/**
	 * This method is used for closing created new window if exist
	 * 
	 * @param newWindow
	 * @throws Exception
	 */
	public final void closeNewWindow(String newWindow) throws Exception {
		Set<String> allWindows = getDriver().getWindowHandles();
		if (allWindows.contains(newWindow)) {
			getDriver().switchTo().window(newWindow).close();
			getDriver().switchTo().defaultContent();
		}

	}

	/**
	 * HPID Upload New image method for uploading user profile image
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean validateHpIdProfilePageUploadImage() throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		userPage.waitForElementsOfUserPage("imageId");
		userPage.waitForElementsOfUserPageForClick("edit");
		String srcBeforeUpload = userPage.getAttributesOfUserPage("imageId", "src");
		userPage.verifyElementIsClickableOfUserPage("changePicture");
		userPage.clickOnElementsOfUserPage("changePicture");
		String filePath = System.getProperty("user.dir") + "\\profileimage\\Profile1.jpg";
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		sleeper(3000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		userPage.waitForElementsOfUserPage("confirmUpload");
		userPage.clickOnElementsOfUserPage("confirmUpload");
		userPage.waitForElementsOfUserPage("imageId");
		String srcAfterUpload = userPage.getAttributesOfUserPage("imageId", "src");
		if (srcBeforeUpload.equalsIgnoreCase(srcAfterUpload)) {
			return false;
		} else

		{
			return true;
		}

	}

	/**
	 * HPID generate character random strings for given length
	 * 
	 * @param length
	 * @return
	 */
	public String generateRandomCharacterStringHPIDPage(String lang, int length) {
		char[] chars = null;
		if (lang.equalsIgnoreCase("English")) {
			chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		}
		if (lang.equalsIgnoreCase("Japanese")) {
			chars = "たていすかんなにらせちとしはきくのまりれけめるねもみこひそさつむ".toCharArray();
		}
		if (lang.equalsIgnoreCase("Chinese")) {
			chars = "手田水口廿卜山戈人心中大十竹土火木尸日難女月弓".toCharArray();
		}
		StringBuilder sb = new StringBuilder(20);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();

		return output;
	}

	/**
	 * HPID generate alphanumeric random strings for given length
	 * 
	 * @param length
	 * @return
	 */
	public String generateRandomAlphanumericStringHPIDPage(int length) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		StringBuilder sb = new StringBuilder(20);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();

		return output;
	}

	/**
	 * HPID generate Numeric random strings for given length
	 * 
	 * @param length
	 * @return
	 */
	public String generateRandomNumericStringHPIDPage(int length) {
		char[] chars = "0123456789".toCharArray();
		StringBuilder sb = new StringBuilder(20);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();

		return output;
	}

	/**
	 * This method is used to verify user data on HPID page and user data on DaaS profile page is matching
	 * 
	 * @return
	 */
	public boolean validateHpIdProfileData() {
		try {
			HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver()).getInstance();
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
			userPage.waitForElementsOfUserPageForClick("edit");
			String firstName = userPage.getTextOfUserPage("firstName");
			String lastName = userPage.getTextOfUserPage("lastName");
			String userEmail = userPage.getTextOfUserPage("activeUserEmail");
			String addLine1 = userPage.getTextOfUserPage("addLine1");
			String addLine2 = userPage.getTextOfUserPage("addLine2");
			String country = userPage.getTextOfUserPage("country");
			String state = userPage.getTextOfUserPage("state");
			String city = userPage.getTextOfUserPage("city");
			String zip = userPage.getTextOfUserPage("zip");
			createAndSwitchToNewTab();
			getUrl(hpIdProfilePage.getHpIdUrl());
			hpIdProfilePage.waitForElementsOfHpIdProfilePage("menu");
			if (!(firstName.equals(hpIdProfilePage.getTextOfHpIdProfilePage("hpidFirstName"))) || !(lastName.equals(hpIdProfilePage.getTextOfHpIdProfilePage("hpidLastName"))) || !(userEmail.equals(hpIdProfilePage.getTextOfHpIdProfilePage("hpidEmail")))) {
				switchBackToPreviousTab();
				return false;
			}
			LOGGER.info("Firstname, Lastname and email matched successfully");
			hpIdProfilePage.clickOnElementsOfHpIdProfilePage("editAddress");
			hpIdProfilePage.waitForElementsOfHpIdProfilePage("editLink");
			hpIdProfilePage.clickOnElementsOfHpIdProfilePage("editLink");
			sleeper(3000);
			if (!country.equals(hpIdProfilePage.getTextOfHpIdProfilePage("hpidCountry")) || !(addLine1.equals(hpIdProfilePage.getAttributesOfHpIdProfilePage("hpidAddressLine1", "value"))) || !(addLine2.equals(hpIdProfilePage.getAttributesOfHpIdProfilePage("hpidAddressLine2", "value"))) || !(state.equals(hpIdProfilePage.getAttributesOfHpIdProfilePage("hpidState", "value"))) || !(city.equals(hpIdProfilePage.getAttributesOfHpIdProfilePage("hpidCity", "value")))
					|| !(zip.equals(hpIdProfilePage.getAttributesOfHpIdProfilePage("hpidPostalCode", "value")))) {
				switchBackToPreviousTab();
				return false;
			} else {
				switchBackToPreviousTab();
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test : " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is for selecting random country
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectRandomCountry() throws Exception {
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		List<String> countries = Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba");
		Random rand = new Random();
		int randomIndex = rand.nextInt(countries.size());
		String randomCountry = countries.get(randomIndex);
		sleeper(2000);
		Select oSelect = new Select(userPage.getElementOfUserPage("countryDropDown"));
		oSelect.selectByVisibleText(randomCountry);
		return randomCountry;
	}

	/**
	 * This method is used for setting data on HPID page
	 * 
	 * @throws Exception
	 */
	public void setDataOnHPIDPage() throws Exception {
		try {
			clickOnElementsOfHpIdProfilePage("editAddress");
			clickOnElementsOfHpIdProfilePage("editLink");
			sleeper(3000);
			clickOnElementsOfHpIdProfilePage("selectCountry");
			List<String> countries = Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba");
			Random rand = new Random();
			int randomIndex = rand.nextInt(countries.size());
			String randomCountry = countries.get(randomIndex);
			sleeper(2000);
			getDriver().findElement(By.xpath("//a[contains(@class,'vn-dropdown_options__option-link') and text() = '" + randomCountry + "']")).click();
			clickOnElementsOfHpIdProfilePage("selectType");
			clickOnElementsOfHpIdProfilePage("selectTypeSelected");
			clickOnElementsOfHpIdProfilePage("hpidState");
			enterTextForHpIdProfilePage("hpidState", generateRandomCharacterStringHPIDPage("English", 10));
			clickOnElementsOfHpIdProfilePage("hpidCity");
			enterTextForHpIdProfilePage("hpidCity", generateRandomCharacterStringHPIDPage("English", 8));
			clickOnElementsOfHpIdProfilePage("hpidAddressLine1");
			enterTextForHpIdProfilePage("hpidAddressLine1", generateRandomAlphanumericStringHPIDPage(8));
			clickOnElementsOfHpIdProfilePage("hpidAddressLine2");
			enterTextForHpIdProfilePage("hpidAddressLine2", generateRandomAlphanumericStringHPIDPage(8));
			clickOnElementsOfHpIdProfilePage("hpidPostalCode");
			enterTextForHpIdProfilePage("hpidPostalCode", generateRandomNumericStringHPIDPage(6));
			clickOnElementsOfHpIdProfilePage("selectOK");
			navigateToBack();
			waitForElementsOfHpIdProfilePage("menu");
			clickOnElementsOfHpIdProfilePage("editFirstNameIcon");
			enterTextForHpIdProfilePage("name", generateRandomCharacterStringHPIDPage("English", 8));
			clickOnElementsOfHpIdProfilePage("selectOK");
			sleeper(2000);
			clickOnElementsOfHpIdProfilePage("editLastNameIcon");
			enterTextForHpIdProfilePage("name", generateRandomCharacterStringHPIDPage("English", 8));
			clickOnElementsOfHpIdProfilePage("selectOK");
		} catch (Exception e) {
			switchBackToPreviousTab();
			LOGGER.error("Exception occured in Test : " + e.getMessage());
		}

	}

	/**
	 * This method is for verifying local when user signout DaaS
	 * 
	 * @param language
	 * @return
	 * @throws Exception
	 */
	public boolean checkLocal(String language) throws Exception {
		try {
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
			dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
			userPage.waitForElementsOfUserPage("edit");
			sleeper(3000);
			userPage.clickOnElementsOfUserPage("edit");
			userPage.selectValueFromDropDownOfUserPage("selectLanguage", language);
			userPage.clickOnElementsOfUserPage("save");
			userPage.waitForElementsOfUserPageForClick("edit");
			String cookieDaaS = getItemFromLocalStorage("lang");
			logout();
			String cookieHPID = getItemFromLocalStorage("appContext");
			String hpidLocal = cookieHPID.substring(17, 22);
			if ((language == "pt_BR") || (language == "pt_PT") || (language == "zh_CN")) {

				hpidLocal = hpidLocal.replace("-", "_");
				if ((language.equals(cookieDaaS)) && (hpidLocal.equals(cookieDaaS))) {

					return true;
				} else {

					return false;
				}
			} else {
				if ((language.equalsIgnoreCase(cookieDaaS)) && (hpidLocal.toLowerCase().contains(cookieDaaS.toLowerCase()))) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in Test : " + e.getMessage());
			return false;
		}
	}

	/**
	 * Login method
	 * 
	 * @param email
	 * @param password
	 * @throws Exception
	 */
	public final void login(String email, String password) throws Exception {
		environment = System.getProperty("environment");
		getDriver().get(getEnvironment(System.getProperty("environment")) + "/home");
		LoginPage loginPage = new LoginPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		loginPage.waitForElementsOfLoginPage("emailInputField");
		loginPage.enterTextForLoginPage("emailInputField", getCredentials(environment, email));
		loginPage.clickOnElementsOfLoginPage("nextbutton");
		Assert.assertTrue(loginPage.verifyElementsOfLoginPage("passwordTitle"), "Password page did not open successfully");
		loginPage.waitForElementsOfLoginPage("passwordInputField");
		loginPage.enterTextForLoginPage("passwordInputField", getCredentials(environment, password));
		loginPage.clickOnElementsOfLoginPage("submitButton");
		dashboardPage.waitForElementsOfDashboardPage("dashboardTitle");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("dashboardTitle"), "HP WebApp not login successfully");
		
	}

	/**
	 * This method is verifying Tooltip on userprofile page
	 * 
	 * @param language
	 * @return
	 * @throws Exception
	 */
	public boolean checkToolTip(String language) throws Exception {
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		login("PROFILE_USER_EMAIL", "PROFILE_USER_PASSWORD");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		sleeper(3000);
		userPage.waitForElementsOfUserPageForClick("edit");
		userPage.clickOnElementsOfUserPage("edit");
		userPage.selectValueFromDropDownOfUserPage("selectLanguage", language);
		userPage.clickOnElementsOfUserPage("save");
		if (userPage.waitForElementsOfUserPage("notificationSuccess")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method is verifying Language in DaaS and HPID is same
	 * 
	 * @param language
	 * @return
	 * @throws Exception
	 */
	public boolean checkHPIDLanguage(String language) throws Exception {
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		HpIdProfilePage hpIdProfilePage = new HpIdProfilePage(PreDefinedActions.getDriver()).getInstance();
		switchBackToParentWithoutCloseTab();
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		userPage.clickOnElementsOfUserPage("edit");
		userPage.selectValueFromDropDownOfUserPage("selectLanguage", language);
		userPage.clickOnElementsOfUserPage("save");
		userPage.waitForElementsOfUserPageForClick("edit");
		switchToDifferentTab();
		refreshPage();
		hpIdProfilePage.waitForElementsOfHpIdProfilePage("HPIDLanguage");
		String HPIDlanguage = hpIdProfilePage.getTextOfHpIdProfilePage("HPIDLanguage");
		switchBackToParentWithoutCloseTab();
		switch (language) {
		case "en":
			if (HPIDlanguage.equals("English (United States)")) {
				return true;
			} else {
				return false;
			}
		case "fr":
			if (HPIDlanguage.equals("Français (France)")) {
				return true;
			} else {
				return false;
			}
		case "ja":
			if (HPIDlanguage.equals("日本語")) {
				return true;
			} else {
				return false;
			}
		case "de":
			if (HPIDlanguage.equals("Deutsch (Österreich)")) {
				return true;
			} else {
				return false;
			}
		case "es":
			if (HPIDlanguage.equals("Español (España)")) {
				return true;
			} else {
				return false;
			}
		case "pt_BR":
			if (HPIDlanguage.equals("Português (Brasil)")) {
				return true;
			} else {
				return false;
			}
		case "pt_PT":
			if (HPIDlanguage.equals("Português (Portugal)")) {
				return true;
			} else {
				return false;
			}
		case "zh_CN":
			if (HPIDlanguage.equals("中文 (中国)")) {
				return true;
			} else {
				return false;
			}
		case "nl":
			if (HPIDlanguage.equals("Nederlands (Nederland)")) {
				return true;
			} else {
				return false;
			}
		case "sv":
			if (HPIDlanguage.equals("Svenska")) {
				return true;
			} else {
				return false;
			}
		case "it":
			if (HPIDlanguage.equals("Italiano")) {
				return true;
			} else {
				return false;
			}
		case "da":
			if (HPIDlanguage.equals("Dansk")) {
				return true;
			} else {
				return false;
			}
		case "fi":
			if (HPIDlanguage.equals("Suomi")) {
				return true;
			} else {
				return false;
			}
		case "no":
			if (HPIDlanguage.equals("Norsk")) {
				return true;
			} else {
				return false;
			}
		default:
			return false;

		}

	}
}