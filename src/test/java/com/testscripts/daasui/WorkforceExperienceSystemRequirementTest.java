package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantURL;
import com.daasui.pages.WorkforceExperienceSystemRequirementsPage;

public class WorkforceExperienceSystemRequirementTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WorkforceExperienceSystemRequirementTest.class);
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_WEP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		return data;
	}

	@Test(priority = 1, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42805881")
	public final void verifySystemRequirementPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WorkforceExperienceSystemRequirementsPage systemRequirement = new WorkforceExperienceSystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		systemRequirement.clickOnElementsOfSytemRequirementPage("cookieAccept");
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		systemRequirement.clickOnElementsOfSytemRequirementPage("systemHelpAndSupport");
		sleeper(5000);
	    systemRequirement.verifyElementsOfSytemRequirementPage("systemRequirementLabel");
		systemRequirement.clickOnElementsOfSytemRequirementPage("systemRequirementLabel");
		systemRequirement.switchToDifferentTab();
		waitForPageLoaded();
		String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.SYSTEM_REQUIREMENTS + "/" + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		softAssert.assertTrue(url.equals(systemRequirement.getUrlOfCurrentPage()), "Terms and Condition URL not matching");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("systemWexLogo"), "Hp logo is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("systemProfieIcon"), "Profile icon is not present on System Requirement page ");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("systemRequirementTitle"), "System Title is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("titleBelowDesc"), "Title below Description is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("pcOperatingLabel"), "Pc operating label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("pcBelowDesc"), "Pc Below Description is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("windowsLabel"), "Window Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("windows10Label"), "Window10 Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("windows11Label"), "Window11 Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("WindowsServer"),"Window Server Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("macOslabel"), "macOS Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("macOsDesc"), "macOS Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("chromeLabel"), "chrome Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("chromeDesc"), "chrome Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("ChromeDesc"), "chrome end Desc 1 is not present on System Requirement page");
	    systemRequirement.scrollOnSytemRequirementPage("mobileOsLabel");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("mobileOsLabel"), "mobile Os Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("MobileOSDesc"), "mobile os Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("andriodLabel"), "Andriod Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("mobileBelowText"), "Mobile below Text is not present on System Requirement page");
		systemRequirement.scrollOnSytemRequirementPage("networkLabel");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("networkLabel"), "Network Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("networkBelowDesc"), "Network Below Desc is not present on System Requirement page");
		systemRequirement.scrollOnSytemRequirementPage("webBrowserLabel");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("webBrowserLabel"), "WebBrowser Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("webBrowserDesc"), "Web Browser Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("pcLabel"), "Web Browser Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("googleLabel"), "Google Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("msEdgeLabel"), "Ms Edge Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("pcBelowNote"), "Pc Below Noteis not present on System Requirement page");
		systemRequirement.scrollOnSytemRequirementPage("uninstallTitle");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("uninstallTitle"), "Uninstall Title is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("uninstallLabel"), "Uniinstall Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("uninstallDesc"), "Uninstall Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("disableLabel"), "Disable Label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("disableServiceDesc"), "Disable Desc is not present on System Requirement page");
		systemRequirement.scrollOnSytemRequirementPage("hpReatilLabel");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("hpReatilLabel"), "HP Retail label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("SupportPlatform"),"Support Platform label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("Windows11"), "Windows 11 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("11Enterprise"), "Windows 11 enterprise is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("SupportPeripheral"),"Support Peripheral label is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("hpretailist1"), "HP Retail list 1 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("hpretailist2"), "HP Retail list 2 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("hpretailist3"), "HP Retail list 3 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("hpretailist4"), "HP Retail list 4 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("hpRposNotes1"), "HP RP notes1 Desc is not present on System Requirement page");
		systemRequirement.scrollOnSytemRequirementPage("printProxyTitle");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("printProxyTitle"), "Print proxy Title is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("printProxyText1"), "Print proxy text1 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("printProxyText2"), "Print proxy text1 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("serverHeaderTitle"), "Server Header Title  is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("serverText1"), "Server Text1 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("serverText2"), "Server Text2 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("serverId1"), "Server ID1 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("serverId2"), "Server ID2 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("serverId3"), "Server ID3 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("serverId4"), "Server ID4 is not present on System Requirement page");
		systemRequirement.scrollOnSytemRequirementPage("networkHeader");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("networkHeader"), "Network Header is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("NetworkText")," Network Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("networkText1"), "NetworkText1 Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("networkText2"), "NetworkText2 Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("networkText3"), "NetworkText3 Desc is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("networkText4"), "NetworkText4 Desc is not present on System Requirement page");
		softAssert.assertAll();
}
	
	@Test(priority = 2, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42805883")
	public final void verifyLinksSystemRequirementPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WorkforceExperienceSystemRequirementsPage systemRequirement = new WorkforceExperienceSystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		systemRequirement.clickOnElementsOfSytemRequirementPage("cookieAccept");
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		systemRequirement.clickOnElementsOfSytemRequirementPage("systemHelpAndSupport");
		sleeper(5000);
		systemRequirement.verifyElementsOfSytemRequirementPage("systemRequirementLabel");
		systemRequirement.clickOnElementsOfSytemRequirementPage("systemRequirementLabel");
		systemRequirement.switchToDifferentTab();
		waitForPageLoaded();
		systemRequirement.scrollOnSytemRequirementPage("manualliuninstallLink");
		systemRequirement.clickOnElementsOfSytemRequirementPage("manualliuninstallLink");
		systemRequirement.switchToDifferentTab();
		sleeper(5000);
		systemRequirement.switchBackToPreviousTab();
		systemRequirement.scrollOnSytemRequirementPage("uninstallLink");
		systemRequirement.clickOnElementsOfSytemRequirementPage("uninstallLink");
		systemRequirement.switchToDifferentTab();
		sleeper(5000);
		systemRequirement.switchBackToPreviousTab();
		systemRequirement.scrollOnSytemRequirementPage("uninstallGroupLink");
		systemRequirement.clickOnElementsOfSytemRequirementPage("uninstallGroupLink");
		systemRequirement.switchToDifferentTab();
		sleeper(5000);
		systemRequirement.switchBackToPreviousTab();
		systemRequirement.scrollOnSytemRequirementPage("disableServiceLink");
		systemRequirement.clickOnElementsOfSytemRequirementPage("disableServiceLink");
		systemRequirement.switchToDifferentTab();
		sleeper(5000);
		systemRequirement.switchBackToPreviousTab();
		softAssert.assertAll();		
	}
	@Test(priority = 3, groups = {"REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42806119")
	public final void verifytextofSystemRequirementPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		WorkforceExperienceSystemRequirementsPage systemRequirement = new WorkforceExperienceSystemRequirementsPage(PreDefinedActions.getDriver()).getInstance();
		getUrl( SetTestEnvironments.getEnvironment(System.getProperty("environment")) + "/" + ConstantURL.SYSTEM_REQUIREMENTS + "/" + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode")));
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("systemWexLogo"), "Hp logo is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("langaugeDrodown"), "Language Dropdown is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("systemRequirementTitle"), "System Title is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("titleBelowDesc").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.title.description_content2")), "System requirement subtitle did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("pcOperatingLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.title")), "PC operating systems label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("pcBelowDesc").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.desc")), "PC operating systems below text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("windowsLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.proSecurity.category1.heading1")), "Windows Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("windows10Label").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.windows.line3.updated")), "windows 10 Label  text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("windows11Label").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.windows.note")), "windows11 label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("macOslabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "azureonboard.marketing.platforms.macos.title")), "Macos Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("macOsDesc").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.macOS11.line1")), "Macos Below Desc text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("chromeLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "global.device_os.chromeOS")), "Chrome Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("chromeDesc").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.pcOS.chromeOS.desc")), "Chrome Desc text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("mobileOsLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobileOS.title")), "Mobile Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("MobileOSDesc").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobileOS.desc")), "Mobile Below Desctext did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("andriodLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobile_device.android.updated")), "Andriod Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("mobileBelowText").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.mobile_device.note")), "Mobile Below text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("networkLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.network.title")), "Network Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("webBrowserLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.extensions.title")), "Web Browser Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("webBrowserDesc").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.web_browsers.extensions.desc")), "Web Browser text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("googleLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "global.browser.google_chrome")), "Google Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("msEdgeLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "global.browser.microsoft_edge")), "MS Edge Label text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("uninstallTitle").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.uninstall_disable")), "Uninistall Title text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("disableLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.service_scan.disable")), "DisableLabel  text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("disableServiceDesc").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.service_scan.uninstall")), "Disable Service Desc text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("hpReatilLabel").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.title1")), "HP RetailLabel text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("SupportPlatform").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.title2")), "HP RetailLabel 2 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("Windows11").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.windowsos1")), "Windows 11 pro text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("11Enterprise").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.windowsos2")), "Windows 10 enterprise  text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("SupportPeripheral").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.title3")), "HP RetailLabel text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("hpRposNotes1").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.desc3")), "HP RPos Notes1  text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("hpretailist1").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.label1")), "HP Retaillist 1 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("hpretailist2").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.label2")), "HP Retaillist 2 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("hpretailist3").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_retail.label3")), "HP Retaillist 3 text did not match");
		softAssert.assertTrue(systemRequirement.verifyElementsOfSytemRequirementPage("hpretailist4"), "HP Retail list 4 is not present on System Requirement page");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("printProxyTitle").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.title")), "Print Proxy Title  text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("printProxyText1").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.desc1")), "Print Proxy Text 1 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("printProxyText2").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.desc2")), "Print Proxy Text 2 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("serverHeaderTitle").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.serverhardware")), "Server Header Title text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("serverText1").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.desc3")), "Server Tex1 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("serverText2").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.desc4")), "Server Tex2 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("serverId1").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.hp-sh1")), "Sever ID1 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("serverId2").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.hp-sh2")), "Sever ID2 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("serverId3").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.hp-sh3")), "Sever ID3 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("serverId4").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.hp-sh4")), "Sever ID4 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("networkHeader").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.networkandfirewall")), "Network Header text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("networkText1").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.desc5")), "Network Text1 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("networkText2").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.hp-nf1")), "Network Text2 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("networkText3").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.hp-nf2")), "Network Text3 text did not match");
		softAssert.assertTrue(systemRequirement.getTextOfSytemRequirementPage("networkText4").equals(systemRequirement.getTextLanguage(LanguageCode, "daas_ui", "requirements.list.hp_print_fleetproxy.desc6")), "Network Text4 text did not match");
		softAssert.assertAll();	
	}
}
