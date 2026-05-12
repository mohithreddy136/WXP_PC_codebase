package com.testscripts.daasui;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.SystemRequirementsPage;

/**
 * This class implements test cases related to system requirements page
 *
 */
public class SystemRequirementsTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(SystemRequirementsTest.class);

	/**
	 * This test will verify Windows PC operating systems tile information
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_CI" }, description = "TC323874")
	public final void verifyPcOperatingSystemsTile() throws Exception {
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SYSTEM_REQUIREMENTS);
		SystemRequirementsPage systemRequirementPage = new SystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		LOGGER.info("verifyPcOperatingSystemsTile test case validation started");
		softAssert.assertTrue(systemRequirementPage.verifyElementsOfSystemRequirementsPage("hpIcon"), "HP icon is not present on system requirement page");
		softAssert.assertTrue(systemRequirementPage.verifyElementsOfSystemRequirementsPage("hpTechPulse"), "TechPulse text is not present on system requirement page");
		softAssert.assertTrue(systemRequirementPage.verifyElementsOfSystemRequirementsPage("language"), "Language selection icon is not present on system requirement page");

		waitForPageLoaded();
		sleeper(2000);
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("systemRequirementTitle").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.title.updated1").replaceAll("[>\\{}]", "").toString().replaceAll("appName","TechPulse")), "System requirement title did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("systemRequirementSubtitle").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.title.description_content").replaceAll("[>\\{}]", "").toString().replaceAll("appName","HP Insights")), "System requirement subtitle did not match");

		// Verify PC operating systems tile
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("pcOsText").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.title")), "PC operating systems text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("pcOsDesc").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.desc")), "PC operating systems text description did not match");
		
		// Windows
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsLabel").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "global.device_os.windows")), "Windows label did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsLine1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.windows.line3.updated")), "Windows bullet1 text did not match");

		// macOS
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("macOSLabel").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "global.device_os.macOS")), "MacOS Label did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("macOSLine").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.macOS.line1")), "MacOS bullet text did not match");
		
		// Chrome OS
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("chromeOsLabel").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "global.device_os.chromeOS")), "ChromeOS Label did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("chromeOsDesc").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.chromeOS.desc")), "ChromeOS desc text did not match");
		/*softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("chromeOsLine1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.chrome.line3")), "ChromeOS bullet1 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("chromeOsLine2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.chrome.line1")), "ChromeOS bullet2 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("chromeOsLine3").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.chrome.line2")), "ChromeOS bullet3 text did not match");*/
		
		//Support Restrictions section
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportRestrictionsLabel").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.supported.restriction")), "Support restrictions label did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("pcOsnoteSupport").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.note")), "PC OS note text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportRestrictionsText1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.note2")), "Support restrictions text1 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportRestrictionsText2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.note3")), "Support restrictions text2 did not match");

		softAssert.assertAll();
		LOGGER.info("Verified PC operating systems tile strings");

	}

	/**
	 * This test will verify Mobile device operating systems tile information
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TC323874")
	public final void verifyMobileOperatingSystemsTile() throws Exception {
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SYSTEM_REQUIREMENTS);
		SystemRequirementsPage systemRequirementPage = new SystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();

		// Verify Mobile device operating systems tile
		LOGGER.info("verifyMobileOperatingSystemsTile test case validation started");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("mobileDeviceOsText").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobileOS.title")), "Mobile operating systems title did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("mobileDeviceOsDesc").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobileOS.desc")), "Mobile operating systems description text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("mobileDeviceLine1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobile_device.android.updated")), "Mobile operating systems bullet1 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("mobileDeviceLine2").contains(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobile_device.ios.updated")), "Mobile operating systems bullet2 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("mobileDeviceOsNote").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobile_device.note")), "Mobile operating systems note text did not match");
		LOGGER.info("Verified Mobile operating systems tile strings");

		softAssert.assertAll();
		LOGGER.info("verifyMobileOperatingSystemsTile test case is successfully completed");
	}

	/**
	 * This test will verify HP Retail Point of Sale Systems tile information
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TC323874")
	public final void verifyHpRetailPointOfSaleSystemsTile() throws Exception {
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SYSTEM_REQUIREMENTS);
		SystemRequirementsPage systemRequirementPage = new SystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();

		// Verify HP Retail Point of Sale Systems tile
		LOGGER.info("VerifyHpRetailPointOfSaleSystemsTile test case validation started");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposText").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.title")), "HP Retail Point of Sale Systems text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposPara").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.desc")), "HP Retail Point of Sale Systems description text did not match");
		
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposBullet1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.hp-rp9")), "HP Retail Point of Sale Systems bullet1 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposBullet2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.hp-mp9")), "HP Retail Point of Sale Systems bullet2 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposBullet3").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.hp-rp5")), "HP Retail Point of Sale Systems bullet3 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposBullet4").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.hp-engage-one")), "HP Retail Point of Sale Systems bullet4 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposBullet5").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.hp-engage-pro")), "HP Retail Point of Sale Systems bullet5 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposBullet6").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.hp-engage-pro-c")), "HP Retail Point of Sale Systems bullet6 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposBullet7").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.hp-engage-go")), "HP Retail Point of Sale Systems bullet7 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposBullet8").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.line8")), "HP Retail Point of Sale Systems bullet8 text did not match");
		
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposNote").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.note.updated")), "HP Retail Point of Sale Systems note text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposNote2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.note2")), "HP Retail Point of Sale Systems note2 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hpRposNote3").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.note3")), "HP Retail Point of Sale Systems note3 text did not match");
		
		LOGGER.info("Verified HP Retail Point of Sale Systems strings");

		softAssert.assertAll();
		LOGGER.info("verifyHpRetailPointOfSaleSystemsTile test case is successfully completed");
	}

	/**
	 * This test will verify Network requirements tile information
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TC323874")
	public final void verifyNetworkRequirementsTile() throws Exception {
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SYSTEM_REQUIREMENTS);
		SystemRequirementsPage systemRequirementPage = new SystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();

		// Verify Network requirements tile
		LOGGER.info("VerifyNetworkRequirementsTile test case validation started");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("networkRequirementsText").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.network.title")), "Network requirements text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("networkRequirementsPara").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.network.descroption")), "Network requirements description did not match");
		LOGGER.info("Verified Network requirements strings");

		softAssert.assertAll();
		LOGGER.info("verifyNetworkRequirementsTile test case is successfully completed");
	}
	
	/**
	 * This test will verify Web browser tile information
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TC323874")
	public final void verifyWebBrowsersTile() throws Exception {
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SYSTEM_REQUIREMENTS);
		SystemRequirementsPage systemRequirementPage = new SystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();

		// Verify Web Browser tile
		LOGGER.info("verifyWebBrowsersTile test case validation started");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("webBrowserText").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.title")), "Web Browser label did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("webBrowserDesc").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.desc").replaceAll("[>\\{}]", "").toString().replaceAll("appName","HP Insights")), "Web Browser description did not match");
		
		// PC
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("pcLabel").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "roles.account_owner_initial")), "PC label did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("pcLine1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.pc.line1.updated")), "PC bullet1 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("pcLine2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.pc.line2.updated")), "PC bullet2 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("pcLine3").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.pc.line3.updated")), "PC bullet3 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("pcLine4").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.pc.line5.updated")), "PC bullet4 text did not match");
	
		// Mobile
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("mobileLabel").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.mobile")), "Mobile label did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("mobileLine1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.mobile.line1.updated")), "Mobile bullet1 text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("mobileLine2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobile_device.sub.browsers.safari-ios.updated")), "Mobile bullet2 text did not match");

		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("webBrowserNote").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.pc.note").replaceAll("[>\\{}]", "").toString().replaceAll("appName","HP Insights")), "PC Web Browser note text did not match");

		softAssert.assertAll();
		LOGGER.info("verifyWebBrowsersTile test case is successfully completed");
	}
	
	@Test(priority = 6,groups = { "REGRESSION", "REGRESSION_CI" },description = "TC 250386, 354371",enabled = false) 
	public final void verifyProactiveSecurityTileOnRequirementsPage() throws Exception {
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SYSTEM_REQUIREMENTS);
		SystemRequirementsPage systemRequirementPage = new SystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();

		// Heading
		LOGGER.info("verifyProactiveSecurityTile test case validation started");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("proactiveSecurityTitle").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "proactive.requirements.title.updated")), "Proactive Security title not present");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("proactiveSecurityText").equals((systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.desc")).replace("{productName}", "HP TechPulse")), "Proactive security title text did not match");
		
		//Verify Sure Sense Advanced system requirements tile
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("sureSenesAdvancedTitle").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category1.updated")), "Sure Sense Pro title text did not match");

		// Windows
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsOSRequirementTitle").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category1.heading1.updated")), "Windows Operating System requirement title did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsText1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category1.windowsLine1")), "Windows text1 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowstext2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category1.windowsLine2")), "Windows text2 did not match");

		//Verify Sure Click Advanced system requirements tile
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("sureClickAdvancedTitle").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category2.updated")), "Sure Click Pro title text did not match");

		// Device hardware
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportedProcessorTitle").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category2DeviceHW.subCat1")), "Supported processor title text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportedProcessorText1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category2DeviceHW.subCat1Line1")), "Supported processor title text1 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportedProcessorText2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category2DeviceHW.subCat1Line2")), "Supported processor title text2 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("deviceHardwareText2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category2DeviceHW.subCat2")), "Device hardware text2 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("deviceHardwareText3").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category2DeviceHW.subCat3")), "Device hardware text3 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("deviceHardwareText4").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category2DeviceHW.subCat4")), "Device hardware text4 did not match");

		// Windows
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsTiletext1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category1.heading1.updated")), "Windows text1 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsTiletext2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windowsContent")), "Windows text2 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsVersion1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.point6")), "Windows version text1 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsVersion2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.point5")), "Windows version text2 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsVersion3").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.point1")), "Windows version text3 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsVersion4").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.point2.supportLifeCycle")), "Windows version text4 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("windowsRequirementsText").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.supportLifecycle.windows")), "Windows requirements text did not match");

		//Verify Support life cycle policy content
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportLifeCyclePolicyLink").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.support.lifecycle.show")), "Show support life cycle policy text did not match");
		systemRequirementPage.clickOnElementsOfSystemRequirementsPage("supportLifeCyclePolicyLink");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("hideLifeCyclePolicyLink").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.support.lifecycle.hide")), "Hide support life cycle policy text did not match");
		systemRequirementPage.verifyElementsOfSystemRequirementsPage("supportLifeCycleContent");
		softAssert.assertTrue(systemRequirementPage.verifyElementsOfSystemRequirementsPage("supportLifeCycleContent"), "Support Life Cycle policy tile did not appeared after clicking on show life cycle policy link ");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportLifeCycleHeader").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.supportLifecycle.heading")), "Support life cycle policy header text did not match");

		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportLifeCycleContent1").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.supportLifecycle.content1")), "Support life cycle policy text1 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportLifeCycleContent2").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.supportLifecycle.content3")), "Support life cycle policy text3 did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("supportLifeCycleContent5").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.windows.supportLifecycle.content7")), "Support life cycle policy text4 did not match");

		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("identityProtectionHeader").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category.identityProtection.title")), "Identity protection header did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("identityProtectionDescription").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category.identityProtection.summary")), "Identity protection description did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("googleChromeVersionText").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category.identityProtection.subCat1")), "Google chrome version text did not match");
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("microsoftEdgeVersionText").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category.identityProtection.subCat2")), "Microsoft edge version text did not match");
		// 3rd party sw requirements
		softAssert.assertTrue(systemRequirementPage.getTextOfSystemRequirementsPage("thirdPartySw").equals(systemRequirementPage.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category2.3rdPartySwReqLine1")), "3rd party sw requirements text did not match");
  
		//Verify Windows OS release information link
		systemRequirementPage.clickOnElementsOfSystemRequirementsPage("windowsRealeaseInformationLink");
		switchToDifferentTab();
		softAssert.assertTrue(getUrlOfCurrentPage().contains(ConstantURL.WINDOWS_RELEASE_URL), "Windows release information page did not open successfully..");
        
		softAssert.assertAll();
		LOGGER.info("verifyProactiveSecurityTile test case is successfully completed");
	}
	
	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI" }, description = "928400", enabled = true)
	public final void verifyInvisibilityOfWolfProTile() throws Exception {
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SYSTEM_REQUIREMENTS);
		SystemRequirementsPage systemRequirementPage = new SystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();

		// Verify HP Wolf Pro Security System Requirements should not be present on requirements page
		LOGGER.info("Verify that HP Wolf Pro and sure sense Security System Requirements should not be present on requirements page");
		softAssert.assertFalse(systemRequirementPage.verifyElementsOfSystemRequirementsPage("proactiveSecurityText"),"Wolf Pro security system requirement tile is still present");
		softAssert.assertFalse(systemRequirementPage.verifyElementsOfSystemRequirementsPage("sureSenesAdvancedTitle"),"Sure sense pro system requirement tile is still present");
		
		softAssert.assertAll();
		LOGGER.info("Verified that HP Wolf Pro and sure sense Security System Requirements is not present on requirements page");
	}

}
