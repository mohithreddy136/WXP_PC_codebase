package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.EMMToolVariables;
import com.daasui.constants.PreferenceVariables;

public class EMMToolPage extends CommonMethod {

	private ObjectReader emmToolPagePropertiesReader = new ObjectReader();
	private Properties emmToolPagePropertiesPage;
	private static Logger LOGGER = LogManager.getLogger(EMMToolPage.class);
	private EMMToolPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public EMMToolPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (EMMToolPage.class) {
				if (instance == null) {
					instance = new EMMToolPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public EMMToolPage(WebDriver driver) throws IOException {
		emmToolPagePropertiesPage = emmToolPagePropertiesReader.getObjectRepository("EMMToolPageV3");
	}

	public final boolean verifyElementsOfEMMToolPage(String key) throws Exception {
		return verifyElementIsPresent(emmToolPagePropertiesPage.getProperty(key));
	}

	public final boolean waitForElementsOfEMMToolPage(String key) throws Exception {
		return verifyElementIsVisible(emmToolPagePropertiesPage.getProperty(key));
	}

	public final void clickOnElementsOfEMMToolPage(String key) throws Exception {
		click(emmToolPagePropertiesPage.getProperty(key));
	}

	public final boolean matchTextOfEMMToolPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(emmToolPagePropertiesPage.getProperty(key), Text);
	}

	public final void scrollOnEMMToolPage(String key) throws Exception {
		scrollTillView(emmToolPagePropertiesPage.getProperty(key));
	}

	public final void enterTextForEMMToolPage(String key, String Text) throws Exception {
		enterText(emmToolPagePropertiesPage.getProperty(key), Text);
	}

	public final String getTextOfEMMToolPage(String key) throws Exception {
		return getTextBy(emmToolPagePropertiesPage.getProperty(key));
	}

	public final void clickByJavaScriptOnEMMToolPage(String key) throws Exception {
		clickByJavaScript(emmToolPagePropertiesPage.getProperty(key));
	}

	public final void selectElementFromDropDownOfDeviceDetailsPage(String dropdownId, String key, String text) throws Exception {
		click(emmToolPagePropertiesPage.getProperty(dropdownId));
		selectFromDropdown(emmToolPagePropertiesPage.getProperty(dropdownId), emmToolPagePropertiesPage.getProperty(key), text);
	}

	/**
	 * This method verify punch out redirection url strings on emm tool tab
	 * 
	 * @param languageCode - This is used as code for multiple languages
	 * @param EMMToolType - specifies EMM Tool sub tabs expected values - Chrome enterprise , Airwatch/Intune
	 * @return - true - Boolean value return either true or false
	 */
	public final boolean verifyEmmToolTabPunchOutStrings(String languageCode, String EMMToolType) {
		boolean flag = false;
		try {
			if (EMMToolType.equalsIgnoreCase(EMMToolVariables.CHROME_ENTERPRISE_TAB)) {
				if (getTextOfEMMToolPage("emmToolRestApiLabel").contains(getTextLanguage(languageCode, "daas_ui", "companies.details.chromebook.title") + getTextLanguage(languageCode, "daas_ui", "global.url")) && ((getTextOfEMMToolPage("emmToolRestApiUrlLabel").contains(PreferenceVariables.CHROME_PORTAL_URL) || (getTextOfEMMToolPage("emmToolRestApiUrlLabel").contains(PreferenceVariables.CHROME_ACCOUNT_PORTAL_URL))))) {
					LOGGER.info("Verified " + EMMToolType + "  Punchout redirection strings");
					flag = true;
				} else {
					LOGGER.error("Unable to verify Punchout redirection strings");
					return flag;
				}
			} else if (EMMToolType.equalsIgnoreCase(EMMToolVariables.AIRWATCH_INTUNE_TAB)) {
				if (getTextOfEMMToolPage("emmToolRestApiLabel").contains(getTextLanguage(languageCode, "lhserver", "settings.preferences.emm.emm_rest_api")) && ((getTextOfEMMToolPage("emmToolRestApiUrlLabel").contains(PreferenceVariables.AIRWATCH_URL_US)) || (getTextOfEMMToolPage("emmToolRestApiUrlLabel").contains(ConstantURL.INTUNE_URL)) || (getTextOfEMMToolPage("emmToolRestApiUrlLabel").contains(ConstantURL.AZURE_PORTAL_URL)))) {
					LOGGER.info("Verified " + EMMToolType + "  Punchout redirection strings");
					flag = true;
				} else {
					LOGGER.error("Unable to verify Punchout redirection strings");
					return flag;
				}
			} else {
				LOGGER.error("Invalid EMM Tool Type");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred in Test verifyEmmToolTabPunchOutStrings : " + e.getStackTrace());
		}
		return flag;
	}

	/**
	 * This method verify emm tool punch out links
	 * 
	 * @param languageCode - This is used as code for multiple languages
	 * @param companyName - specify tenant name for impersonation
	 * @param EMMToolType - specifies EMM Tool sub tabs expected values - Chrome enterprise , Airwatch/Intune
	 * @return - true - Boolean value return either true or false
	 */
	public final boolean EMMToolTabPunchOutLink(String languageCode, String companyName, String EMMToolType) {
		boolean flag = false;
		try {
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			if (selectAndValidateEMMToolTab(languageCode, companyName, EMMToolType)) {
				sleeper(10000); // Takes time for redirecting on new tab and fetching third party url
				waitForPageLoaded();
				switchToDifferentTab();
				if (EMMToolType.equalsIgnoreCase(PreferenceVariables.CHROME)) {
					if (getUrlOfCurrentPage().toString().contains(PreferenceVariables.CHROME_PORTAL_URL) || getUrlOfCurrentPage().toString().contains(PreferenceVariables.CHROME_ACCOUNT_PORTAL_URL)) {
						LOGGER.info("Redirected to " + getUrlOfCurrentPage().toString());
						if (deviceListPage.verifyElementsOfDeviceListPage("googleAdminPanel")) {
							switchBackToPreviousTab();
							verifyEmmToolTabPunchOutStrings(languageCode, EMMToolType);
							flag = true;
						} else {
							LOGGER.error("Google Admin Portal Dashborad validation failed");
							return flag;
						}
					} else {
						LOGGER.error("Google Admin Portal redirection failed");
						return flag;
					}
				} else if (EMMToolType.equalsIgnoreCase(PreferenceVariables.INTUNE) || EMMToolType.equalsIgnoreCase(PreferenceVariables.AIRWATCH)) {
					LOGGER.info("Successsfully Redirected to " + getUrlOfCurrentPage().toString());
					if (getUrlOfCurrentPage().toString().contains(ConstantURL.AZURE_PORTAL_URL) || getUrlOfCurrentPage().toString().contains(ConstantURL.INTUNE_URL) || getUrlOfCurrentPage().toString().contains(PreferenceVariables.AIRWATCH_URL_US)) {
						LOGGER.info("Successsfully Redirected to the Emm Portal");
						switchBackToPreviousTab();
						verifyEmmToolTabPunchOutStrings(languageCode, EMMToolType);
						flag = true;
					} else {
						LOGGER.error("Emm Portal redirection failed");
						return flag;
					}
				} else {
					return flag;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred in Test EMMToolTabPunchOutLink : " + e.getStackTrace());
			flag = false;
		}
		return flag;
	}

	/**
	 * This method verify EMM tool error message for non emm configured tenant
	 * 
	 * @param languageCode - This is used as code for multiple languages
	 * @param companyName - specify tenant name for impersonation
	 * @param EMMToolType - specifies EMM Tool sub tabs expected values - Chrome enterprise , Airwatch/Intune
	 * @return - true - Boolean value return either true or false
	 */
	public final boolean EMMToolNonConfigurationMessage(String languageCode, String companyName, String EMMToolType) {
		boolean flag = false;
		try {
			if (selectAndValidateEMMToolTab(languageCode, companyName, EMMToolType)) {
					if (getTextOfEMMToolPage("EMMToolNotConfigured").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "chromebook.no_config.title")) || getTextOfEMMToolPage("EMMToolNotConfigured").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "intune.no_config.title")) || getTextOfEMMToolPage("EMMToolNotConfigured").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "airwatch.no_config.title")) ) {
					LOGGER.info("'" + getTextOfEMMToolPage("EMMToolNotConfigured") + "'" + " message for non-emm configured tenant is displayed");
					pressKey(Keys.ESCAPE);
					flag = true;
				} else {
					LOGGER.error("Error message is not dipalyed");
					return flag;
				}
			} else {
				LOGGER.error("Unable to select Emm Tool Type " + EMMToolType + " for tenant " + companyName);
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred in Test verifyEmmToolTabPunchOutStrings : " + e.getStackTrace());
		}
		return flag;
	}

	/**
	 * This method verify company impersonation by selecting emm tool tab
	 * 
	 * @param languageCode - This is used as code for multiple languages
	 * @param companyName - specify tenant name for impersonation
	 * @param EMMToolType - specifies EMM Tool sub tabs expected values - Chrome enterprise , Airwatch/Intune
	 * @return - true - Boolean value return either true or false
	 */
	public final boolean selectAndValidateEMMToolTab(String languageCode, String companyName, String EMMToolType) {
		boolean flag = false;
		try {
			switch (EMMToolType) {
			case  PreferenceVariables.CHROME:
				LOGGER.info("Clicking " + PreferenceVariables.CHROME + " Tab ");
				sleeper(2000);
				clickOnElementsOfEMMToolPage("emmToolBtnClick");
				clickOnElementsOfEMMToolPage("chromeEnterpriseBtn");
				break;
			case PreferenceVariables.INTUNE:
				LOGGER.info("Clicking " + PreferenceVariables.CHROME + " Tab ");
				sleeper(2000);
				clickOnElementsOfEMMToolPage("emmToolBtnClick");
				clickOnElementsOfEMMToolPage("intuneBtn");	
				break;
				
			case PreferenceVariables.AIRWATCH:
				LOGGER.info("Clicking " + EMMToolVariables.AIRWATCH_INTUNE_TAB + " Tab ");
				sleeper(2000);
				clickOnElementsOfEMMToolPage("emmToolBtnClick");
				clickOnElementsOfEMMToolPage("airwatchBtn");	
				break;
				
			default:
				LOGGER.info("Invalid EMM Tool type : " + EMMToolType);
				break;
			}
			if (getTextOfEMMToolPage("emmToolSubTabPopUpHeader").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "sidemenu.emmTools")) || getTextOfEMMToolPage("emmToolSubTabPopUpHeader").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "sidemenu.chrome")) || getTextOfEMMToolPage("emmToolSubTabPopUpHeader").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "sidemenu.intune")) || getTextOfEMMToolPage("emmToolSubTabPopUpHeader").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "sidemenu.airwatch")) ) {
				LOGGER.info(getTextOfEMMToolPage("emmToolSubTabPopUpHeader") + " pop-up opened");
				sleeper(3000); // mandatory required as ui itself takes time to enable dropdown menu
				waitForElementsOfEMMToolPage("selectCompanyDropdownButton");
				clickByJavaScriptOnEMMToolPage("selectCompanyDropdownButton");
				waitForElementsOfEMMToolPage("selectCompanySearch");
				enterTextForEMMToolPage("selectCompanySearch", companyName);
				if (!verifyElementsOfEMMToolPage("selectCompanyNoResultsMatched")) {
					if (getTextOfEMMToolPage("companyDropdownItems").equals(companyName)) {
						clickOnElementsOfEMMToolPage("companyDropdownItems");
						LOGGER.info("Company " + companyName + " is selected");
						waitForPageLoaded();
						return flag = true;
					} else {
						LOGGER.error("Company" + companyName + " does not match");
						return flag;
					}
				} else {
					LOGGER.error("No results found for company " + companyName);
					return flag;
				}
			} else {
				LOGGER.error("Failed to open pop-up");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred in Test verifyEmmToolTabPunchOutStrings : " + e.getStackTrace());
		}
		return flag;
	}
}
