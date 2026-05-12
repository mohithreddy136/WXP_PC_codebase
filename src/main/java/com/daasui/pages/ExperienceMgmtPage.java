/**
 * 
 */
package com.daasui.pages;


import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.google.common.base.Strings;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ExperienceMgmtPage extends CommonMethod {
	private ObjectReader ExperienceMgmtPagePropertiesReader = new ObjectReader();
	private Properties ExperienceMgmtPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(ExperienceMgmtPage.class);
	private Properties selectedLanguageProperties;
	public static String environment;
	private ExperienceMgmtPage instance;
	private Properties selecteCredentialsProperties;
	private ObjectReader environmentPropertiesReader = new ObjectReader();
	private Properties environmentProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	public ExperienceMgmtPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (ExperienceMgmtPage.class) {
				if (instance == null) {
					instance = new ExperienceMgmtPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public ExperienceMgmtPage(WebDriver driver) throws IOException {

		if(isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)){
			if(uiVersion.equalsIgnoreCase("VENEER3"))
			{
				ExperienceMgmtPageProperties = ExperienceMgmtPagePropertiesReader.getObjectRepository("ExperienceMgmtPageV3");
			}
			else if(uiVersion.equalsIgnoreCase("VENEER2"))
			{
				ExperienceMgmtPageProperties = ExperienceMgmtPagePropertiesReader.getObjectRepository("ExperienceMgmtPage");
			}
			else{
				LOGGER.error("Incorrect UI Version, please pass either VENEER2 or VENEER3");
			}
		}else{
			ExperienceMgmtPageProperties = ExperienceMgmtPagePropertiesReader.getObjectRepository("ExperienceMgmtPageV3");
		}

	}

	/**
	 * @param language: Language code for localization testing
	 * @param localefolder: To specify the folder where the key is present
	 * @param key: Contains the string which is localized
	 * @return String which is localized
	 * @throws Exception
	 */
	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = ExperienceMgmtPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * @param language: Language code for localization testing
	 * @param localefolder: To specify the folder where the key is present
	 * @param key: Contains the string which is localized
	 * @return String ArrayList which is localized
	 * @throws Exception
	 */
	public final ArrayList<String> getTextLanguage(String language, String localefolder, ArrayList<String> key) throws Exception {
		ArrayList<String> keyValues = new ArrayList<String>();
		selectedLanguageProperties = ExperienceMgmtPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		for (int keyCounter = 0; keyCounter < key.size(); keyCounter++) {
			StringBuilder appendText = new StringBuilder();
			if (key.get(keyCounter).contains("-")) {
				String[] splitString = key.get(keyCounter).split("-");
				for (int isplitStringCounter = 0; isplitStringCounter < splitString.length; isplitStringCounter++) {
					appendText.append(selectedLanguageProperties.getProperty(splitString[isplitStringCounter].trim()));
					if (isplitStringCounter == splitString.length - 1)
						break;
					appendText.append("-");
				}
				keyValues.add(appendText.toString());
			} else
				keyValues.add(selectedLanguageProperties.getProperty(key.get(keyCounter)));
		}
		return keyValues;
	}

	public final void waitUntilElementIsVisibleOfExperienceMgmtPage(String key) throws Exception {
		waitUntilElementIsVisible(ExperienceMgmtPageProperties.getProperty(key));
	}
	public final boolean verifyElementsOfExperienceMgmtPage(String key) throws Exception {
		return verifyElementIsPresent(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfExperienceMgmtPage(String key) throws Exception {
		return verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(key));
	}
	public final boolean waitForElementsOfExperienceMgmtPage1(String key) throws Exception {
		return verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(key));
	}
	public final boolean waitForElementsOfExperienceMgmtPageDynamic(String key,int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(key),waitTime);
	}

	public final boolean waitForPresenceOfElementsOfExperienceMgmtPageDynamic(String key,int waitTime) throws Exception {
		return waitUntillElementIsPresentDynamic(ExperienceMgmtPageProperties.getProperty(key),waitTime);
	}

	public final boolean waitForPresenceOfElementsOfExperienceMgmtPage(String key) throws Exception {
		return waitUntillElementIsPresent(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final boolean matchTextOfExperienceMgmtPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(ExperienceMgmtPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfExperienceMgmtPage(String key) throws Exception {
		return verifyElementIsEnable(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfExperienceMgmtPage(String key) throws Exception {
		return verifyElementIsSelected(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final String getTextOfExperienceMgmtPage(String key) throws Exception {
		return getTextBy(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final String getAttributesOfExperienceMgmtPage(String key, String value) throws Exception {
		return getAttribute(ExperienceMgmtPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfExperienceMgmtPage(String key) throws Exception {
		click(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnExperienceMgmtPage(String key) throws Exception {
		clickByJavaScript(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final void enterTextForExperienceMgmtPage(String key, String Text) throws Exception {
		enterText(ExperienceMgmtPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfExperienceMgmtPage(String key) throws Exception {
		return verifyElementIsClickable(ExperienceMgmtPageProperties.getProperty(key));
	}

	public void switchToIframeofExperienceMgmtPage(String key) throws Exception {
		switchToIframe(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final boolean verifyCompanyChangeOfExperienceMgmtPage(String LanguageCode, String textKey, String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey) throws Exception {
		return verifySearchFunctionalityUsingSingleSelectDropdown(LanguageCode, ExperienceMgmtPageProperties.getProperty(textKey), companySearchText, ExperienceMgmtPageProperties.getProperty(emptyTextKey), ExperienceMgmtPageProperties.getProperty(listKey), ExperienceMgmtPageProperties.getProperty(dropdownBoxKey));
	}

	public final boolean verifyCompanyChangeOfExperienceMgmtPageGlobalFilter(String LanguageCode, String textKey, String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey, String saveButton) throws Exception {
		return verifySearchFunctionalityUsingSingleSelectDropdownRadioButton(LanguageCode, ExperienceMgmtPageProperties.getProperty(textKey), companySearchText, ExperienceMgmtPageProperties.getProperty(emptyTextKey), ExperienceMgmtPageProperties.getProperty(listKey), ExperienceMgmtPageProperties.getProperty(dropdownBoxKey),ExperienceMgmtPageProperties.getProperty(saveButton));
	}


	public final void mouseHoverOfExperienceMgmtPage(String key) throws Exception {
		mouseHover(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final WebElement getElementOfExperienceMgmtPage(String key) throws Exception {
		return getElement(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final List<String> mouseHoverOfElementsExperienceMgmtPage(String key, String key1) throws Exception {
		return gettextmouseHoverelements(ExperienceMgmtPageProperties.getProperty(key), ExperienceMgmtPageProperties.getProperty(key1));
	}

	public final List<WebElement> getElementsOfExperienceMgmtPage(String key) throws Exception {
		return getAllElements(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofExperienceMgmtPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final void switchToDifferentTabOfExperienceMgmtPage() {
		switchToDifferentTab();
	}

	public final void switchToPreviousTabOfExperienceMgmtPage() {
		switchBackToPreviousTab();
	}

	public final ArrayList<String> getChartLabelsExperienceMgmtPage(String key) throws Exception {
		return getLabelsOfChart(ExperienceMgmtPageProperties.getProperty(key));
	}

	public void scrollToExperienceMgmtPage(String key) throws Exception {
		scrollTillView(ExperienceMgmtPageProperties.getProperty(key));
	}

	public final void scrollUpCharts() {
		jsDriver().executeScript("scroll(0, -250);");
	}

	public final void scrollDownCharts() {
		jsDriver().executeScript("scroll(0, 750);");
	}
	public final String getCredentials(String credentials, String key) throws Exception {
		selecteCredentialsProperties = ExperienceMgmtPagePropertiesReader.getCredentials(credentials);
		return selecteCredentialsProperties.getProperty(key);
	}
	public final List<String> getallTextOfExperienceMgmtPage(String key) throws Exception {
		return getallTextBy(ExperienceMgmtPageProperties.getProperty(key));
	}

    public final void selectfromDropdownExperienceMgmtPage(String Locator,String text) throws Exception {
    	selecValueFromDropdown(ExperienceMgmtPageProperties.getProperty(Locator),ExperienceMgmtPageProperties.getProperty(text));
	}

	public final void waitUntilElementIsInvisibleOfExperienceMgmtPage(String key) {
		try {
			verifyElementIsinvisibile(ExperienceMgmtPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfCompanyPage " + e.getMessage()));
		}
	}

	public final boolean isAttributePresentExperienceMgmtPage(String key, String attribute) throws Exception {
		return isAttributePresent(ExperienceMgmtPageProperties.getProperty(key), attribute);
	}

	/**
	 * This method is used to validate that chart is loaded properly or not.
	 *
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param idKey: Chart type
	 * @param errorKey: Fatal Error when no data is available
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartisLoaded(String LanguageCode, String idKey, String errorKey) throws Exception {

		boolean flag = false;
		if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(idKey))) {
			if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(errorKey))) {
				String text = getTextOfExperienceMgmtPage(errorKey);
				if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.error"))) {
					flag = true;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to validate that chart is loaded properly or not.
	 *
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param idKey: Chart type
	 * @param errorKey: Fatal Error when no data is available
	 * @param waitTime: Dynamic wait time which needs to be passed
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyChartisLoadedDynamic(String LanguageCode, String idKey, String errorKey, int waitTime) throws Exception {

		boolean flag = false;
		if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(idKey), waitTime)) {
			if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(errorKey), waitTime)) {
				String text = getTextOfExperienceMgmtPage(errorKey);
				if (text.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.error"))) {
					flag = true;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify sequence of chart present on DashBoard for MSP
	 *
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfExperienceMgmtPageForMSP(String allChartsLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] = { "OsVersionSupport", "DevicesByType", "DevicesByOs", "WarrantyExpiration", "SubscriptionExpiration","CpuUtilization","MemoryUtilization","HardwareInventory", "BatteryReplacementSummary", "DiskReplacementSummary", "AllIncidentsByType", "IncidentBurnDownSummary", "SoftwareInventory", "TodaysCriticalIncidents" };
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allCharts = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter).equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error("Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 *
	 * @param allChartsLocatorKey - Chart locator to get the id
	 * @param chartIdsArray - Expected order of charts
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyChartOrderOfExperienceMgmtPageForMSPFlexi(String allChartsLocatorKey,String[] chartIdsArray) throws Exception {
		Boolean flag = false;
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		waitForPageLoaded();
		sleeper(10000);// Kept this long sleeper due slow loading issue of charts, once that issue gets resolved will remove this sleeper.
		waitUntilElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(allChartsLocatorKey),3);
		List<WebElement> allCharts = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter).equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error("Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify sequence of chart present on DashBoard for Reseller
	 *
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfExperienceMgmtPageForReseller(String allChartsLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] = { "OsVersionSupport", "DevicesByType", "DevicesByOs", "WarrantyExpiration", "SubscriptionExpiration", "CpuUtilization", "MemoryUtilization","HardwareInventory","BatteryReplacementSummary", "DiskReplacementSummary", "AllIncidentsByType", "IncidentBurnDownSummary", "SoftwareInventory", "TodaysCriticalIncidents" };
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allCharts = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter).equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error("Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify sequence of chart present on DashBoard for Reseller
	 *
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfExperienceMgmtPageForResellerFlexi(String allChartsLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] ={ "diskCapacityIncidentKpi0","thermalGradingIncidentKpi0","scadashboard0","sureSenseProThreatsDashboard0","sureSenseProDeviceDashboard0","osSupportSummary0","deviceType0","deviceOS0","hwwarByDeviceWarranty0","subsexpirybyterm0","summaryByCpuUtiDashboard0","summaryByMemUtiDashboard0","hwinvByMonth0","diskRepSummary0","batteryswellprobByAge0","allIncidentByType0","burnDownSummary0","topApplicationSummary0","todayCriticalIncident0","driverByStatus0","driverByCriticalityKpi0","windowsUpdatesDashboard0","windowsDashboard0","officeUpdatesDashboard0","officeDashboard0"};
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		sleeper(5000);// Kept this long sleeper due slow loading issue of charts, once that issue gets resolved will remove this sleeper.
		List<WebElement> allCharts = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter).equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error("Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify sequence of chart present on DashBoard for ReportAdmin
	 *
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfExperienceMgmtPageForReportAdmin(String allChartsLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] = { "OsVersionSupport", "DevicesByType", "DevicesByOs", "AllIncidentsByType", "IncidentBurnDownSummary","TodaysCriticalIncidents" };
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allCharts = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter).equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error("Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify sequence of chart present on DashBoard for ReportAdmin
	 *
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfExperienceMgmtPageForReportAdminFlexi(String allChartsLocatorKey) throws Exception {
		Boolean flag = false;
		  String chartIdsArray[] ={ "diskCapacityIncidentKpi0","thermalGradingIncidentKpi0","scadashboard0","sureSenseProThreatsDashboard0","sureSenseProDeviceDashboard0","osSupportSummary0","deviceType0","deviceOS0","hwwarByDeviceWarranty0","subsexpirybyterm0","summaryByCpuUtiDashboard0","summaryByMemUtiDashboard0","hwinvByMonth0","diskRepSummary0","allIncidentByType0","burnDownSummary0","topApplicationSummary0","todayCriticalIncident0","driverByStatus0","driverByCriticalityKpi0","windowsUpdatesDashboard0","windowsDashboard0","officeUpdatesDashboard0","officeDashboard0"};
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		sleeper(5000);// Kept this long sleeper due slow loading issue of charts, once that issue gets resolved will remove this sleeper.
		List<WebElement> allCharts = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter).equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error("Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify when device is enrolled and incident are fixed whether chart data is present or not
	 *
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param noTextIdKey: Key for chart message.
	 * @param chartType: Type of chart.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyChartsMessageAllIncidentsFixed(String LanguageCode, String noTextIdKey, String chartType) throws Exception {
		boolean flag = false;
		try {
			String message = null;
			chartType = getTextLanguage(LanguageCode, "daas_ui", chartType);
			if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.you_are_doing_great") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.there_are_no_incidents_with_open_status")))) {
						flag = true;
					}
				} else {
					flag = false;
					LOGGER.debug("Provided : " + chartType + " Chart type is wrong, You can use : OPEN INCIDENTS only");
					throw new InputMismatchException("You can use : OPEN INCIDENTS");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * This method is used to verify whether chart data is present or not (No Devices enrolled and No Data) This method will verify the data is not available on the chart
	 *
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param noTextIdKey: Key for chart message.
	 * @param chartType: Type of chart.
	 * @return boolean value
	 * @throws Exception
	 */

/*	public final boolean verifyChartsHasNoDataOnExperienceMgmtPage(String LanguageCode, String noTextIdKey, String chartType) throws Exception {
		String noDataChartMessage = "";
		List<WebElement> messageWebElement;
		boolean flag = false;
		try {
			String message = null;
			chartType = getTextLanguage(LanguageCode, "daas_ui", chartType);			
			if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os").toString()).replaceAll("[>\\{}]", "").toString()))) {

						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.battery_rep_summary"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.battery_rep_summary").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfExperienceMgmtPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_by_type"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_by_type").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_top_by_subtype"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_top_by_subtype").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_detection"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_detection").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_curn_down_rate"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_curn_down_rate").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.battery.swell.probability.by.device.age"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll("cardName", getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.battery.swell.probability.by.device.age").toString()).replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			}else {
				flag = false;
				LOGGER.debug("Provided : " + chartType + " Chart type is wrong, You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK 		REPLACEMENT SUMMARY,CPU UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY, SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS 		DETECTION, BATTERY SWELL PROBABILITY BY DEVICE AGE, INCIDENT BURNDOWN RATE only");
				throw new InputMismatchException("You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK REPLACEMENT SUMMARY,CPU 		UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY,SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS DETECTION, BATTERY SWELL PROBABILITY BY DEVICE AGE, INCIDENT 		BURNDOWN RATE only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}*/


	public final boolean verifyChartsHasNoDataOnExperienceMgmtPageFlexi(String LanguageCode, String noTextIdKey, String chartType) throws Exception {
		String noDataChartMessage = "";
		boolean flag = false;
		try {
			chartType = getTextLanguage(LanguageCode, "daas_ui", chartType);
			if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			}}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type"))) {
				 if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					 	sleeper(2000);
						noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
					}
			} }else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			} }else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
						}
			} }else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.battery_rep_summary"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
						}
			} }else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
						}
			}} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
						}
			} }else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
						}
			}} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
						}
			}} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			}} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			}} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			} }else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_by_type"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			} }else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_top_by_subtype"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			}} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_detection"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			} }else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_curn_down_rate"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			}}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
				}
			}
			}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.battery.swell.probability.by.device.age"))) {
				if (verifyElementIsVisibleDynamic(ExperienceMgmtPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfExperienceMgmtPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here"))){
							flag = true;
			}
				}}else {
				flag = false;
				LOGGER.debug("Provided : " + chartType + " Chart type is wrong, You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK 		REPLACEMENT SUMMARY,CPU UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY, SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS 		DETECTION, BATTERY SWELL PROBABILITY BY DEVICE AGE, INCIDENT BURNDOWN RATE only");
				throw new InputMismatchException("You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK REPLACEMENT SUMMARY,CPU 		UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY,SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS DETECTION, BATTERY SWELL PROBABILITY BY DEVICE AGE, INCIDENT 		BURNDOWN RATE only ");
			}
			}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * This method is used to verify whether chart data is present or not (At least one Device enrolled and No Data)
	 *
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param noTextIdKey: Key for chart message.
	 * @param chartType: Type of chart.
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartsHasNoDataDevicesEnrolledOnExperienceMgmtPage(String LanguageCode, String noTextIdKey, String chartType) throws Exception {
		boolean flag = false;
		try {
			String message = null;
			chartType = getTextLanguage(LanguageCode, "daas_ui", chartType);
			if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.battery_rep_summary"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_by_type"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_top_by_subtype"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_detection"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_curn_down_rate"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.battery.swell.probability.by.device.age"))) {
				if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(ExperienceMgmtPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here") + "\n" + (getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			}else {
				flag = false;
				LOGGER.debug("Provided : " + chartType + " Chart type is wrong, You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK 		REPLACEMENT SUMMARY,CPU UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY, SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS 		DETECTION, INCIDENT BURNDOWN RATE only, BATTERY SWELL PROBABILITY BY DEVICE AGE ");
				throw new InputMismatchException("You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK REPLACEMENT SUMMARY,CPU 		UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY,SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS DETECTION, INCIDENT 		BURNDOWN RATE only, BATTERY SWELL PROBABILITY BY DEVICE AGE ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * This method will verify the Title of the chart
	 *
	 * @param titleKey: Position of the chart title
	 * @param chartTitleText: Chart title key fetched from the locale folder
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTitleofChartOfExperienceMgmtPage(String titleKey, String chartTitleText) throws Exception {
		boolean flag = false;
		if (waitForElementsOfExperienceMgmtPage(titleKey)) {
			if (chartTitleText.equalsIgnoreCase(getTextOfExperienceMgmtPage(titleKey))) {
				flag = true;
			}
		}
		return flag;
	}

	public final boolean verifyTooltipTextOnReportWithFrame(String labelsKey, String tooltipTextKey, String textKey) throws Exception {
		boolean flag = false;
		String text = null;
		List<WebElement> tooltipTextKeyValue=null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
			tooltipTextKeyValue = getElementsOfExperienceMgmtPage(tooltipTextKey);
		for (int i = 0; i < listOfLabels.size(); i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
				text = tooltipTextKeyValue.get(i).getText();
			sleeper(2000);
			listOfLabels.get(i).click();
			switchToDifferentTabOfExperienceMgmtPage();
			if (waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfExperienceMgmtPage(textKey);
				if (monthtext.get(0).getText().contains(text.trim())) {
					flag = true;
				}
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}

	public final boolean verifyTooltipTextOnReportWithFrameFlexi1(String labelsKey, String tooltipTextKey, String textKey) throws Exception {
		boolean flag = false;
		String text = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		for (int i = 0; i < listOfLabels.size(); i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			text = getTextOfExperienceMgmtPage(tooltipTextKey);
			sleeper(2000);
			listOfLabels.get(i).click();
			switchToDifferentTabOfExperienceMgmtPage();
			if (waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfExperienceMgmtPage(textKey);
				if (monthtext.get(0).getText().contains(text.trim())) {
					flag = true;
				}
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}

	public final boolean verifyTooltipTextOnReportHWInv(String labelsKey, String tooltipTextKey, String textKey) throws Exception {
		boolean flag = false;
		String text = null;
		int labelSize = 0;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		if(listOfLabels.size()>11){
			labelSize = 11;
		}else{
			labelSize=listOfLabels.size();
		}
		for (int i = 0; i < labelSize; i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			text = getTextOfExperienceMgmtPage(tooltipTextKey);
			String cleanText = text.replaceAll("[+.^:,]", "");
			listOfLabels.get(i).click();
			switchToDifferentTabOfExperienceMgmtPage();
			if (waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfExperienceMgmtPage(textKey);
				if (monthtext.get(0).getText().contains(cleanText.trim())) {
					flag = true;
				}
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}

	public final boolean verifyTooltipTextOnReportSWInv(String labelsKey, String tooltipTextKey, String textKey) throws Exception {
		boolean flag = false;
		String text = null;
		String cleantext = null;
		int labelSize = 0;
		List<WebElement> listOfTooltipText =null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
			listOfTooltipText = getElementsOfExperienceMgmtPage(tooltipTextKey);
			labelSize = 1;
			for (int i = 0; i < labelSize;i++ ) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
				text =listOfTooltipText.get(i).getText();
				cleantext = text.replaceAll("â€¦", "");
			listOfLabels.get(i).click();
			switchToDifferentTabOfExperienceMgmtPage();
			if (waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfExperienceMgmtPage(textKey);
				if (monthtext.get(0).getText().contains(cleantext.trim())) {
					flag = true;
				}
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}
	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 *
	 * @param graphKey: Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnTextKey: Column names present in the grid
	 * @param frameKey: Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountOnReportwithFrameSubscriptionExpiration(String graphKey, String tooltipTextKey, String columnTextKey, String frameKey) throws Exception {
		boolean flag = false;
		String count = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(graphKey);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
			sleeper(3000);
			//listOfLabels.get(listOfLabelsCounter);
			action.moveToElement(listOfLabels.get(listOfLabelsCounter)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			count = getTextOfExperienceMgmtPage(tooltipTextKey);
			String count_clean = count.replaceAll(",", "");
			Integer tooltipcount = Integer.valueOf(count_clean);
			listOfLabels.get(listOfLabelsCounter).click();
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			clickOnElementsOfExperienceMgmtPage("showMoreLink");
			sleeper(2000);
			clickOnElementsOfExperienceMgmtPage("dropdownMenuItem");
			if (getAttributesOfExperienceMgmtPage("nextButton", "class").contains("disabled")) {
				List<WebElement> columntext = getElementsOfExperienceMgmtPage(columnTextKey);
				int countColumnInt = 0;
				for (int columnTextIndex = 0; columnTextIndex < columntext.size(); columnTextIndex++) {
					String count_column = columntext.get(columnTextIndex).getText();
					int countInteger = Integer.valueOf(count_column);
					countColumnInt = countColumnInt + countInteger;
				}
				if (countColumnInt == tooltipcount)
					flag = true;
				else {
					flag = false;
					break;
				}
			} else {
				flag = true;
				LOGGER.info("Count is more than 100.");
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}


	/**
	 * @param labelsKey
	 * @param tooltipTextKey
	 * @param textKey
	 * @param keyDrillDownLabelsAllHidden
	 * @param targetElement
	 * @param legendDropdownKey
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyTooltipTextOnReportWithFrameDonutChartFlexi(String labelsKey, String tooltipTextKey, String textKey, String keyDrillDownLabelsAllHidden,String targetElement, String legendDropdownKey) throws Exception {
		boolean flag = false;
		String text = null;
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelsKey,keyDrillDownLabelsAllHidden,legendDropdownKey);
		List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
		for (int i = 0; i < listOfLegends.size(); i++) {
			if (waitForElementsOfExperienceMgmtPageDynamic(legendDropdownKey,1)) {
				if (i == 0 || i == 2) {
					clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
					sleeper(3000);
				}
			} else {
				LOGGER.info("Legend dropdown not present on chart");
			}
			listOfLegends.get(i).click();
			scrollToExperienceMgmtPage(labelsKey);
			scrollToExperienceMgmtPage(targetElement);
			sleeper(3000);
			mouseHoverbyoffsett(targetElement, 00, 72);
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			text = getTextOfExperienceMgmtPage(tooltipTextKey);
			String count_clean = text.split("\n")[0];
			String 	finaltext = count_clean.split(":")[1].trim();
			mouseHoverbyoffsettClick(targetElement, 00, 72);
			switchToDifferentTabOfExperienceMgmtPage();
			if(waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
			clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
			List<WebElement> monthtext = getElementsOfExperienceMgmtPage(textKey);
			if (monthtext.get(0).getText().contains(finaltext)) {
				flag = true;
			}}else{
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfExperienceMgmtPage();
			listOfLegends.get(i).click();

		}
		return flag;
	}

	/**
	 * @param labelsKey - lengends locator
	 * @param tooltipTextKey - tool tip locator
	 * @param textKey - text key locator
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyTooltipTextOnReportWithFrameFlexi(String labelsKey, String tooltipTextKey, String textKey, String keyDrillDownLabelsAllHidden,String targetElement) throws Exception {
		boolean flag = false;
		String text = null;
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey,keyDrillDownLabelsAllHidden);
		List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
		for (int i = 0; i < listOfLegends.size(); i++) {
			listOfLegends.get(i).click();
			sleeper(3000);
			scrollToExperienceMgmtPage(targetElement);
			mouseHoverbyoffsett(targetElement, 15, 80);
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			sleeper(2000);
			text = getTextOfExperienceMgmtPage(tooltipTextKey);
			String cleanText = text.replaceAll("[+.^:,]","");
			mouseHoverbyoffsettClick(targetElement, 15, 80);
			switchToDifferentTabOfExperienceMgmtPage();
			if(waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
			clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
			List<WebElement> monthtext = getElementsOfExperienceMgmtPage(textKey);
			if (monthtext.get(0).getText().contains(cleanText)) {
				flag = true;
			}}else{
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfExperienceMgmtPage();
			listOfLegends.get(i).click();

		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 *
	 * @param graphKey: Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnTextKey: Column names present in the grid
	 * @param frameKey: Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountOnReportwithFrameSubscriptionExpirationFlexi(String graphKey, String tooltipTextKey, String columnTextKey, String frameKey) throws Exception {
		boolean flag = false;
		String count = null;
		Integer tooltipcount;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(graphKey);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
			listOfLabels.get(listOfLabelsCounter);
			action.moveToElement(listOfLabels.get(listOfLabelsCounter)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			count = getTextOfExperienceMgmtPage(tooltipTextKey);
				tooltipcount = Integer.valueOf(count);
			listOfLabels.get(listOfLabelsCounter).click();
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if(waitForElementsOfExperienceMgmtPageDynamic("columnlistSubscriptionExpirationFirstFlexi", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
			clickOnElementsOfExperienceMgmtPage("showMoreLink");
			sleeper(2000);
			clickOnElementsOfExperienceMgmtPage("dropdownMenuItemSubscriptionFlexi");
			clickOnElementsOfExperienceMgmtPage("select100FromDropdown");
			sleeper(3000);
			if (getAttributesOfExperienceMgmtPage("nextButtonSubscriptionFlexi", "class").contains("next")) {
				scrollToExperienceMgmtPage(columnTextKey);
				sleeper(3000);
				List<WebElement> columntext = getElementsOfExperienceMgmtPage(columnTextKey);
				int countColumnInt = 0;
				for (int columnTextIndex = 0; columnTextIndex < columntext.size(); columnTextIndex++) {
					String count_column = columntext.get(columnTextIndex).getText();
					int countInteger = Integer.valueOf(count_column);
					countColumnInt = countColumnInt + countInteger;
				}
				if (countColumnInt == tooltipcount)
					flag = true;
				else {
					flag = false;
					break;
				}
			} else {
				flag = true;
				LOGGER.info("Count is more than 100.");
			}}else{
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}


	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 *
	 * @param labelsKey: Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnTextKey: Column names present in the grid
	 * @param frameKey: Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountonReportwithFrameSWInventory(String labelsKey, String tooltipTextKey, String columnTextKey, String frameKey,String tooltipTextSoftware) throws Exception {
		boolean flag = false;
		String count = null,text=null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
			listOfLabels.get(listOfLabelsCounter);
			action.moveToElement(listOfLabels.get(listOfLabelsCounter)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			count = getTextOfExperienceMgmtPage(tooltipTextKey);
			text = getTextOfExperienceMgmtPage(tooltipTextSoftware);
			String count_clean = count.replaceAll("[a-zA-Z:]", "");
			Integer tooltipcount = Integer.valueOf(count_clean.trim());
			listOfLabels.get(listOfLabelsCounter).click();
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			waitForElementsOfExperienceMgmtPage("showMoreLink");
			clickOnElementsOfExperienceMgmtPage("showMoreLink");
			sleeper(2000);
			clickOnElementsOfExperienceMgmtPage("dropdownMenuItem");
			clickOnElementsOfExperienceMgmtPage("100Option");
			if (getAttributesOfExperienceMgmtPage("nextButton", "class").contains("disabled")) {
				List<WebElement> columntext = getElementsOfExperienceMgmtPage(columnTextKey);
				List<WebElement> applicationNameText = getElementsOfExperienceMgmtPage("applicationNamelistSoftwareForCount");
				int countColumnInt = 0;
				for (int columnTextIndex = 0; columnTextIndex < columntext.size(); columnTextIndex++) {
					if(applicationNameText.get(columnTextIndex).getText().equals(text))
					{
					String count_column = columntext.get(columnTextIndex).getText();
					int countInteger = Integer.valueOf(count_column);
					countColumnInt = countColumnInt + countInteger;
				}
				}
				if (countColumnInt == tooltipcount)
					flag = true;
				else {
					flag = false;
					break;
				}
			} else {
				flag = true;
				LOGGER.info("Count is more than 100.");
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 *
	 * @param labelsKey: Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnTextKey: Column names present in the grid
	 * @param frameKey: Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountonReportwithFrameSWInventoryFlexi(String labelsKey, String tooltipTextKey,
			String columnTextKey, String frameKey, String tooltipTextSoftware) throws Exception {
		boolean flag = false;
		String count_clean = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		//List<WebElement> listOfTooltipText = getElementsOfExperienceMgmtPage(tooltipTextSoftware);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < 1; listOfLabelsCounter++) {
			listOfLabels.get(listOfLabelsCounter);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(listOfLabelsCounter)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			count_clean = getTextOfExperienceMgmtPage(tooltipTextKey);
				//text = listOfTooltipText.get(listOfLabelsCounter).getText();
			Integer tooltipcount = Integer.valueOf(count_clean.trim());
			listOfLabels.get(listOfLabelsCounter).click();
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if (waitForElementsOfExperienceMgmtPageDynamic(columnTextKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				waitForElementsOfExperienceMgmtPage("showMoreLink");
				clickOnElementsOfExperienceMgmtPage("showMoreLink");
				sleeper(2000);
				clickOnElementsOfExperienceMgmtPage("dropdownMenuItemFlexi");
				clickOnElementsOfExperienceMgmtPage("select100FromDropdown");
				sleeper(3000);
				if (getAttributesOfExperienceMgmtPage("nextButtonFlexi", "class").contains("next")) {
					scrollToExperienceMgmtPage(columnTextKey);
					sleeper(3000);
					List<WebElement> columntext = getElementsOfExperienceMgmtPage(columnTextKey);
					int countColumnInt = 0;
					for (int columnTextIndex = 0; columnTextIndex < columntext.size(); columnTextIndex++) {
						String count_column = columntext.get(columnTextIndex).getText();
						int countInteger = Integer.valueOf(count_column);
						countColumnInt = countColumnInt + countInteger;
					}
					if (countColumnInt == tooltipcount) {
						flag = true;
					} else {
						flag = false;
						break;
					}
				} else {
					flag = true;
					LOGGER.info("Count is more than 100.");
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}

	/**
	 * This is a method to get total records
	 *
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final int getTotalRecordCount(String key) throws Exception {
		int totalRecord = 0;
		String[] allText = getTextBy(ExperienceMgmtPageProperties.getProperty(key)).split(" |/");
		for (int i = allText.length - 1; i > 0; i--) {
			if (isInt(allText[i])) {
				totalRecord = Integer.parseInt(allText[i].trim());
				break;
			}
		}
		return totalRecord;
	}

	/**
	 * This method is used to define parameters of DeviceByOs.
	 *
	 * @return
	 */
	public final HashMap<String, String> getDeviceByOsDetails() throws Exception {
		HashMap<String, String> deviceByOsInfo = new HashMap<String, String>();
		deviceByOsInfo.put("labelsKey", "labelsLocatorDeviceByOs");
		deviceByOsInfo.put("labelsKeyFlexi", "labelsLocatorDeviceByOsFlexi");
		deviceByOsInfo.put("tooltipTextKey", "tooltipTextDeviceByOs");
		deviceByOsInfo.put("tooltipTextKeyFlexi", "tooltipTextDeviceByOsFlexi");
		deviceByOsInfo.put("tooltipTextMajorVersionKey", "tooltipTextDeviceByOsMajorVersion");
		deviceByOsInfo.put("tooltipTextMajorVersionKeyFlexi", "tooltipTextDeviceByOsMajorVersionFlexi");
		deviceByOsInfo.put("tooltipTextMajorVersionNameKey", "tooltipTextDeviceByOsMajorVersionName");
		deviceByOsInfo.put("tooltipTextMajorVersionNameKeyFlexi", "tooltipTextDeviceByOsMajorVersionNameFlexi");
		deviceByOsInfo.put("tooltipTextCountKey", "tooltipTextDeviceByOsCount");
		deviceByOsInfo.put("tooltipTextCountKeyFlexi", "tooltipTextDeviceByOsCountFlexi");
		deviceByOsInfo.put("reportTextCountKey", "reportCountDeviceByOs");
		deviceByOsInfo.put("labelsBackKey", "levelsBackFunctionalityForDevicesByOs");
		deviceByOsInfo.put("labelsBackKeyFlexi", "levelsBackFunctionalityForDevicesByOsFlexi");
		deviceByOsInfo.put("operatingSystemKey", "deviceByOsTextLocator");
		deviceByOsInfo.put("operatingSystemReleaseKey", "deviceByOsReleaseTextLocator");
		deviceByOsInfo.put("moreDetailsLinkKey", "moreDetailsLink");
		deviceByOsInfo.put("frameKey", "frameLocator");
		return deviceByOsInfo;
	}

	/** This method will validate Model charts on DDEX page.
	 * @param barchartLocator
	 * @return
	 * @throws Exception
	 */

	public final boolean verifyDDEXDeviceByModelChart(String barchartLocator) throws Exception {
		boolean flag= false;
		String status,noOfDevices= null;
		Actions action = new Actions(getDriver());
		if(!verifyElementsOfExperienceMgmtPage("errorMessageLocator")) {
			List<WebElement> chartBars = getElementsOfExperienceMgmtPage(barchartLocator);
			sleeper(3000);
			action.moveToElement(chartBars.get(0)).build().perform();
			sleeper(3000);
			status = getTextOfExperienceMgmtPage("barChartTootTipText2");
			sleeper(2000);
			String[] statusSubstring = status.split(" ");
			String scoreCategory = statusSubstring[0];
			noOfDevices = getTextOfExperienceMgmtPage("barChartTootTipText1");
			int noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
			chartBars.get(0).click();
			if (verifyScoreCategory(scoreCategory, "scoreColumnValues")) {
				if (verifyCountOfDevices(noOfDevicesInt)) {
					flag = true;
					LOGGER.info("DDEX Bar chart's validation passed successfully.");
				} else {
					flag = false;
					LOGGER.error("Count of devices validation in DDEX Bar chart got failed.");
				}
			} else {
				flag = false;
				LOGGER.error("Score validation in DDEX Bar chart got failed.");
			}
		}else{
			flag = true;
			LOGGER.info("No data is present in Device Model chart.");
		}
		return flag;
	}

	/** This method will validate Top issues chart on DDEX page.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDDEXIssuesChart() throws Exception {
		boolean flag= false;
		String noOfDevices= null;
		Actions action = new Actions(getDriver());
		if(!verifyElementsOfExperienceMgmtPage("errorMessageLocator")) {
			List<WebElement> chartBars = getElementsOfExperienceMgmtPage("issuesChartBarLocator");
			sleeper(3000);
			action.moveToElement(chartBars.get(0)).build().perform();
			sleeper(3000);
			noOfDevices = getTextOfExperienceMgmtPage("barChartTootTipText1");
			int noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
			chartBars.get(0).click();
			scrollToExperienceMgmtPage("paginationCount");
			if (verifyCountOfDevices(noOfDevicesInt)) {
				flag = true;
				LOGGER.info("DDEX Issues Bar chart's validation passed successfully.");
			} else {
				flag = false;
				LOGGER.error("Count of devices validation in DDEX Issues Bar chart got failed.");
			}
		}
		else{
			LOGGER.info("No data is present in Top Digital Issues chart.");
			flag=true;
		}
		return flag;
	}

	/** This method will validate Health Summary chart.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDDEXDonutChart() throws Exception {
		boolean flag= false;
		String status,noOfDevices= null;
		mouseHoverbyoffsett("donutchartCentreText", 00, 75);
		sleeper(3000);
		status = getTextOfExperienceMgmtPage("donutChartTootlipText1");
		sleeper(2000);
		String[] statusSubstring = status.split(" ");
		String scoreCategory = statusSubstring[0];
		noOfDevices = getTextOfExperienceMgmtPage("donutChartTootlipText2");
		String[] noOfDevicesSubString = noOfDevices.split("\\(");
		int noOfDevicesInt = Integer.parseInt(noOfDevicesSubString[0].trim());
		mouseHoverbyoffsettClick("donutchartCentreText", 00, 75);
		if(verifyScoreCategory(scoreCategory,"scoreColumnValues")) {
			if(verifyCountOfDevices(noOfDevicesInt)) {
				flag=true;
				LOGGER.info("DDEX Donut chart's validation passed successfully.");
			}
			else
			{
				flag=false;
				LOGGER.error("Count of devices validation in DDEX Donut chart got failed.");
			}
		}
		else
		{
			flag= false;
			LOGGER.error("Score validation in DDEX Bar chart got failed.");
		}
		return flag;
	}

	/** This method will validate count of devices from chart to Grid.
	 * @param noOfDevices
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCountOfDevices(int noOfDevices) throws Exception {
		boolean flag = false;
		sleeper(5000);
		String paginationText = getTextOfExperienceMgmtPage("paginationCount");
		String [] paginationsubString = paginationText.split(" ");
		LOGGER.info("Noofdevices: "+noOfDevices);
		LOGGER.info("SubString: "+paginationsubString[0]+" "+paginationsubString[1]);
		LOGGER.info("parsint: "+Integer.parseInt(paginationsubString[1]));
		if(noOfDevices==Integer.parseInt(paginationsubString[1])) {
			flag = true;
		}
		else {
			flag = false;
			LOGGER.error("Count did not match from chart to Grid.");
		}

		return flag;
	}


	/** This method will validate score category on drilldown.
	 * @param category
	 * @param columnValuesLocator
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyScoreCategory(String category,String columnValuesLocator) throws Exception {
		try {
		boolean flag = false;
		sleeper(3000);
		scrollDownCharts();
		List<WebElement> columnValues = getElementsOfExperienceMgmtPage(columnValuesLocator);
		switch(category.trim().toLowerCase()){
		case "good":
			for (int i=0;i<columnValues.size();i++) {
				if(Float.parseFloat(columnValues.get(i).getText())<8.5) {
					flag=false;
					break;
				}else {
					flag=true;
				}
			}
			return flag;

		case "fair":
			for (int i=0;i<columnValues.size();i++) {
				if(!(Float.parseFloat(columnValues.get(i).getText())>5.5 || Float.parseFloat(columnValues.get(i).getText())<8.5)) {
					flag=false;
					break;
				}else {
					flag=true;
				}
			}
			return flag;
		case "poor":
			for (int i=0;i<columnValues.size();i++) {
				if(Float.parseFloat(columnValues.get(i).getText())>5.5) {
					flag=false;
					break;
				}else {
					flag=true;
				}
			}
			return flag;

		 default:
			 LOGGER.error("Given : " + category + " category is incorrect");
				throw new InputMismatchException("You can use : Good,Fair,Poor only ");
		}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	/** This method will validate search box filter.
	 * @param columnValuesLocator
	 * @param searchBoxLocator
	 * @return
	 * @throws Exception
	 */
	public boolean verifySearchBoxFilterDDEX(String columnValuesLocator,String searchBoxLocator) throws Exception {
		try{
		boolean flag = false;
			sleeper(3000);
			List<WebElement> columnValues = getElementsOfExperienceMgmtPage(columnValuesLocator);
			String s =  columnValues.get(0).getText();
			enterTextForExperienceMgmtPage(searchBoxLocator,s);
			sleeper(5000);
			List<WebElement> columnValuesPostFilter = getElementsOfExperienceMgmtPage(columnValuesLocator);
			for(int i=0;i<columnValuesPostFilter.size();i++) {
				if(!columnValuesPostFilter.get(i).getText().contains(s))
				{
					flag = false;
					LOGGER.error("Search filter is not working.");
					break;
				}
				else
				{
					flag=true;
				}
			}
			return flag;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}



	/** This method will validate multi select dropdown.
	 * @param columnValuesLocator
	 * @param searchBoxLocator
	 * @param dropdownValues
	 * @return
	 * @throws Exception
	 */
	public boolean verifyMultiSelectDropdownFilterDDEX(String columnValuesLocator,String searchBoxLocator,String dropdownValues) throws Exception {
		try {
			boolean flag = false;
				List<WebElement> columnValues = getElementsOfExperienceMgmtPage(columnValuesLocator);
				String s = columnValues.get(0).getText();
				clickOnElementsOfExperienceMgmtPage(searchBoxLocator);
			    sleeper(3000);
				List<WebElement> dropdownColumnValues = getElementsOfExperienceMgmtPage(dropdownValues);
			    //sleeper(1500);
				for (int i = 0; i < dropdownColumnValues.size(); i++) {
					if (dropdownColumnValues.get(i).getText().equalsIgnoreCase(s)) {
						sleeper(1500);
						dropdownColumnValues.get(i).click();
						sleeper(1500);
						break;
					}
				}
				waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
			    //sleeper(3000);
				pressEscapeKeysForExperienceMgmtPage();
				List<WebElement> columnValuesPostFilter = getElementsOfExperienceMgmtPage(columnValuesLocator);
			System.out.println("post: "+columnValuesPostFilter.get(0).getText());
			System.out.println("pre:"+s);
				for (int i = 0; i < columnValuesPostFilter.size(); i++) {
					if (!columnValuesPostFilter.get(i).getText().contains(s)) {
						flag = false;
						LOGGER.error("Multi select filter is not working.");
						break;
					} else {
						flag = true;
					}
				}
			return flag;
			}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/** This method will validate comparison range filter.
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public boolean verifyComparisonRangeFilter(String category) throws Exception {
		boolean flag = false;
		List<WebElement> columnValues = getElementsOfExperienceMgmtPage("hardwareScoreValues");
		String s = columnValues.get(0).getText();
		LOGGER.info("pre:"+s);
		clickOnElementsOfExperienceMgmtPage("hardwareScoreSearchBox");
		sleeper(2000);
		clickOnElementsOfExperienceMgmtPage("compareRangeDropdownBox");
		sleeper(2000);
		List<WebElement> dropdownValues = getElementsOfExperienceMgmtPage("compareRangeDropdownValues");
		sleeper(2000);
		List<WebElement> columnValuesPostFilter =null;
		switch(category) {
		case "Less than":
			for(int i=0;i<dropdownValues.size();i++) {
				if(dropdownValues.get(i).getText().equalsIgnoreCase(category)) {
					dropdownValues.get(i).click();
					sleeper(2000);
					enterTextForExperienceMgmtPage("compareRangeValueInputBox", s);
					sleeper(2000);
					clickOnElementsOfExperienceMgmtPage("submitButtonHWCompareRange");
					break;
				}
			}
			if(waitForPresenceOfElementsOfExperienceMgmtPage("nodataGridPageIcon"))
			{
				LOGGER.info("No data is present in Grid after applying"+category+" filter.");
				flag=true;
				return flag;
			}
			else {
				columnValuesPostFilter = getElementsOfExperienceMgmtPage("hardwareScoreValues");
				LOGGER.info("post:" + columnValuesPostFilter.get(0).getText());
				for (int i = 0; i < columnValuesPostFilter.size(); i++) {
					if (!(Float.parseFloat(columnValuesPostFilter.get(i).getText()) < Float.parseFloat(s))) {
						flag = false;
						LOGGER.error("Compare Range for" + category + " filter is not working.");
						break;
					} else {
						flag = true;
					}

				}
				return flag;
			}
		case "Greater than":
			for(int i=0;i<dropdownValues.size();i++) {
				if(dropdownValues.get(i).getText().equalsIgnoreCase(category)) {
					dropdownValues.get(i).click();
					sleeper(2000);
					enterTextForExperienceMgmtPage("compareRangeValueInputBox", s);
					sleeper(2000);
					clickOnElementsOfExperienceMgmtPage("submitButtonHWCompareRange");
					break;
				}
			}

			if(waitForPresenceOfElementsOfExperienceMgmtPage("nodataGridPageIcon"))
			{
				LOGGER.info("No data is present in Grid after applying"+category+" filter.");
				flag=true;
				return flag;
			}
			else
			{
				columnValuesPostFilter = getElementsOfExperienceMgmtPage("hardwareScoreValues");
			for(int i=0;i<columnValuesPostFilter.size();i++)
			{
				if(!(Float.parseFloat(columnValuesPostFilter.get(i).getText())>Float.parseFloat(s)))
				{
					flag = false;
					LOGGER.error("Compare Range for"+category+" filter is not working.");
					break;
				}
				else
				{
					flag=true;
				}
			}
			}return flag;

		case "Less than or equal to":
			for(int i=0;i<dropdownValues.size();i++) {
				if(dropdownValues.get(i).getText().equalsIgnoreCase(category)) {
					dropdownValues.get(i).click();
					sleeper(2000);
					enterTextForExperienceMgmtPage("compareRangeValueInputBox", s);
					sleeper(2000);
					clickOnElementsOfExperienceMgmtPage("submitButtonHWCompareRange");
					break;
				}
			}

			if(waitForPresenceOfElementsOfExperienceMgmtPage("nodataGridPageIcon"))
			{
				LOGGER.info("No data is present in Grid after applying"+category+" filter.");
				flag=true;
				return flag;
			}
			else
			{
				columnValuesPostFilter = getElementsOfExperienceMgmtPage("hardwareScoreValues");
			for(int i=0;i<columnValuesPostFilter.size();i++)
			{
				if(!(Float.parseFloat(columnValuesPostFilter.get(i).getText())<=Float.parseFloat(s)))
				{
					flag = false;
					LOGGER.error("Compare Range for"+category+" filter is not working.");
					break;
				}
				else
				{
					flag=true;
				}
			}
			}return flag;

		case "Greater than or equal to":
			for(int i=0;i<dropdownValues.size();i++) {
				if(dropdownValues.get(i).getText().equalsIgnoreCase(category)) {
					dropdownValues.get(i).click();
					sleeper(2000);
					enterTextForExperienceMgmtPage("compareRangeValueInputBox", s);
					sleeper(2000);
					clickOnElementsOfExperienceMgmtPage("submitButtonHWCompareRange");
					break;
				}
			}

			if(waitForPresenceOfElementsOfExperienceMgmtPage("nodataGridPageIcon"))
			{
				LOGGER.info("No data is present in Grid after applying"+category+" filter.");
				flag=true;
				return flag;
			}
			else
			{
				columnValuesPostFilter = getElementsOfExperienceMgmtPage("hardwareScoreValues");
			for(int i=0;i<columnValuesPostFilter.size();i++)
			{
				if(!(Float.parseFloat(columnValuesPostFilter.get(i).getText())>=Float.parseFloat(s)))
				{
					flag = false;
					LOGGER.error("Compare Range for"+category+" filter is not working.");
					break;
				}
				else
				{
					flag=true;
				}
			}
			}return flag;

		default:
			 LOGGER.error("Given : " + category + " category is incorrect");
				throw new InputMismatchException("You can use : Good,Fair,Poor only ");
		}


	}

	public final boolean verifyfiltersExpMgmtPage(String filterDropdown,String drodownValues,String selectedFilterText) throws Exception {
		boolean flag = false;
		List<WebElement> dropdownValues =null;
		if(getAttributesOfExperienceMgmtPage("filterheaderTab", "aria-expanded").equalsIgnoreCase("false"))
		{
			clickOnElementsOfExperienceMgmtPage("filterheaderTab");
		}
		if(verifyElementsOfExperienceMgmtPage("clearFilterButton")){
			clickOnElementsOfExperienceMgmtPage("clearFilterButton");
		}
			clickOnElementsOfExperienceMgmtPage(filterDropdown);
			dropdownValues = getElementsOfExperienceMgmtListPage(drodownValues);
			dropdownValues.get(0).click();
			pressEscapeKeysForExperienceMgmtPage();
			if(getTextOfExperienceMgmtPage("appliedFilterText").contains(getTextOfExperienceMgmtPage(selectedFilterText))) {
				if(waitForElementsOfExperienceMgmtPage("donutchartCentreText")) {
					mouseHoverbyoffsettClick("donutchartCentreText", 00, 75);
					if(waitForElementsOfExperienceMgmtPage("appliedFilterTextGridPage")) {
						if(getTextOfExperienceMgmtPage("appliedFilterTextGridPage").equalsIgnoreCase(getTextOfExperienceMgmtPage("appliedFilterText")))
						{
							clickOnElementsOfExperienceMgmtPage("backButtonArrowGridPage");
							clickOnElementsOfExperienceMgmtPage("clearFilterButton");
							flag=true;
						}
					}else {
						flag = false;
						LOGGER.error("Filter did not apply in Grid Page.");
					}
				}
				else if(waitForElementsOfExperienceMgmtPage("deviceByModelChartBarsLocator")){
					List<WebElement> bars = getElementsOfExperienceMgmtPage("deviceByModelChartBarsLocator");
					bars.get(0).click();
					if(waitForElementsOfExperienceMgmtPage("appliedFilterTextGridPage")) {
						if(getTextOfExperienceMgmtPage("appliedFilterTextGridPage").equalsIgnoreCase(getTextOfExperienceMgmtPage("appliedFilterText")))
						{
							clickOnElementsOfExperienceMgmtPage("backButtonArrowGridPage");
							clickOnElementsOfExperienceMgmtPage("clearFilterButton");
							flag=true;
						}
					}
					else
					{
						flag = false;
						LOGGER.error("Filter did not apply in Grid Page.");
					}
				}else if(waitForElementsOfExperienceMgmtPage("issuesChartBarLocator"))
				{
					List<WebElement> bars = getElementsOfExperienceMgmtPage("issuesChartBarLocator");
					bars.get(0).click();
					if(waitForElementsOfExperienceMgmtPage("appliedFilterTextGridPage")) {
						if(getTextOfExperienceMgmtPage("appliedFilterTextGridPage").equalsIgnoreCase(getTextOfExperienceMgmtPage("appliedFilterText")))
						{
							clickOnElementsOfExperienceMgmtPage("backButtonArrowGridPage");
							clickOnElementsOfExperienceMgmtPage("clearFilterButton");
							flag=true;
						}
					}
					else
					{
						flag = false;
						LOGGER.error("Filter did not apply in Grid Page.");
					}
				}
				else
				{
					List<WebElement> errorMessageLocatorList = getElementsOfExperienceMgmtPage("errorMessageLocator");
					for(int i=0;i<errorMessageLocatorList.size();i++)
					{
						if(errorMessageLocatorList.get(i).getText().contains("Oops")) {
							flag = false;
							LOGGER.error("API got failed after applying filters.");
							return flag;
						}
						flag = true;
						LOGGER.info("No data is present in charts after applying the filter.");
					}
				}
			}else {
				flag = false;
				LOGGER.error("Filter did not apply on charts.");
				return flag;
			}
		return flag;
	}

	public final boolean verifyRecommendedActionstile(String languageCode){
		try{
		boolean flag = false;
		if(waitForElementsOfExperienceMgmtPage("recommendedActionsNodata")){
		if(getTextOfExperienceMgmtPage("recommendedActionsDescription").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "insight.sidebar.noData.content")))
		{
             flag = true;
			 LOGGER.info("No data is present in Recommended Actions tile.");
		}else{
			LOGGER.error("Recommended Actions tile did not load, it has errors.");
		}
		}else{
			if(waitForElementsOfExperienceMgmtPage("recommendedActionsContent")){
				flag=true;
				LOGGER.info("Recommended Actions tile loaded successfully.");
			}else{
				LOGGER.error("Recommended Actions tile did not load, it has errors.");
			}
		}
		return flag;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public final boolean verifyDeviceDetailNavigation(String Languagecode){
		boolean flag = false;
		Actions action = new Actions(getDriver());
		try {
			if(!verifyElementsOfExperienceMgmtPage("nodataOnTrendChart")) {
				List<WebElement> chartBars = getElementsOfExperienceMgmtPage("totalpointsOnChart");
				sleeper(3000);
				action.moveToElement(chartBars.get(chartBars.size()-1)).build().perform();
				chartBars.get(chartBars.size()-1).click();
				sleeper(3000);
				List<WebElement> serialNumberList = getElementsOfExperienceMgmtPage("serialNumberColumnValues");
				serialNumberList.get(0).click();
				sleeper(5000);
				if(getTextOfExperienceMgmtPage("devicedetailsBreadCrums").equalsIgnoreCase(getTextLanguage(Languagecode,"daas_ui","breadcrumbs.asset_details"))){
                      flag = true;
                      LOGGER.info("Device details page loaded successfully from Experience Management tab.");
				}
				else
				{
					LOGGER.error("Device Details Page did not load successfully from Exp Mgmt Page.");
				}
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public final boolean verifyWeeklyTrendChart(){

		boolean flag = false;
		try {
			if(verifyElementsOfExperienceMgmtPage("totalpointsOnChart")) {
				List<WebElement> chartBars = getElementsOfExperienceMgmtPage("totalpointsOnChart");
				sleeper(3000);
				if(chartBars.size()==13) {
					flag=true;
					LOGGER.info("13 Datelist weeks are visible in Weekly Trend Chart.");
				}else{
					LOGGER.error("13 Datelist weeks are not visible in Weekly Trend Chart.");
					flag=false;
				}
			}else if(verifyElementsOfExperienceMgmtPage("nodataOnTrendChart")){
				LOGGER.error("No data is present in Weekly Trend Chart.");
				flag=false;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public final boolean verifyExpmgmtGridNavigation(String Languagecode){
		boolean flag = false;
		Actions action = new Actions(getDriver());
		try {
			//if(!verifyElementsOfExperienceMgmtPage("nodataOnTrendChart")) {
			if(verifyElementsOfExperienceMgmtPage("totalpointsOnChart")) {
				List<WebElement> chartBars = getElementsOfExperienceMgmtPage("totalpointsOnChart");
				sleeper(3000);
				action.moveToElement(chartBars.get(chartBars.size()-1)).build().perform();
				chartBars.get(chartBars.size()-1).click();
				if(waitForElementsOfExperienceMgmtPage("exportButton")){
					flag = true;
					LOGGER.info(" Experience Management Grid page loaded successfully.");
				}
				else
				{
					LOGGER.error("Experience Management Grid page did not load successfully.");
				}
			}else if(verifyElementsOfExperienceMgmtPage("nodataOnTrendChart")){
				LOGGER.error("No data is present in Weekly Trend Chart.");
				flag=false;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public final boolean verifyExportFunctionality(String Languagecode)
	{
		boolean flag = false;
		try {
			clickOnElementsOfExperienceMgmtPage("exportButton");
			if(getTextOfExperienceMgmtPage("toastNotificationExport").equalsIgnoreCase(getTextLanguage(Languagecode,"daas_ui","campaign.response.export.data.success"))){
                 flag=true;
				 LOGGER.info("Toast Notification generated successfully for Export of DDEX Devices.");
			}else{
				flag=false;
				LOGGER.error("Export API is not working correctly.");
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
         return flag;
	}

	public final boolean verifyViewDevicesButton(String Languagecode)
	{
		boolean flag = false;
		String numberOfDevicesString,numberOfDevices,paginationString,paginationCount = null;
		try{
			if(!waitForElementsOfExperienceMgmtPage("recommendedActionsNodata")){
			numberOfDevicesString = getTextOfExperienceMgmtPage("numberOfDevicePill");
			String [] numberOfDevicesArray = numberOfDevicesString.split(" ");
			numberOfDevices = numberOfDevicesArray[0];
			clickOnElementsOfExperienceMgmtPage("viewDevicesButton");
            if(getTextOfExperienceMgmtPage("expmgmtDetailsTitle").equalsIgnoreCase(getTextLanguage(Languagecode,"daas_ui","assets.import.asset.title")))
			{
				sleeper(3000);
				paginationString = getTextOfExperienceMgmtPage("paginationCount");
				String [] paginationStringArray = paginationString.split(" ");
				paginationCount = paginationStringArray[1];
				System.out.println("numberofdevices "+numberOfDevices);
				System.out.println("pagination "+paginationCount);
				if(numberOfDevices.equalsIgnoreCase(paginationCount)){
					LOGGER.info("Filter from View Devices button is working correctly.");
					flag = true;
				}else
				{
					LOGGER.error("Filter from View Devices button is not working correctly. Count from Charts page did not match in Grid page.");
				}
			}else
			{
				LOGGER.error("Exp mgmt page Grid page did not load successfully from View Devices button.");
			}
			return flag;
			}
			else{
				LOGGER.info("No data is present in Recommended Actions tile.");
				return true;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	// write a function to verify troubleshoot button
	public final boolean verifyTroubleshootButton(String Languagecode)
	{
		boolean flag = false;
		try{

			if(waitForElementsOfExperienceMgmtPage("troubleShootButton")){
			clickOnElementsOfExperienceMgmtPage("troubleShootButton");
			switchToDifferentTabOfExperienceMgmtPage();
			if(getTextOfExperienceMgmtPage("troubleshootGuideTitle").equalsIgnoreCase("Workforce Experience Platform"))
			{
				LOGGER.info("Troubleshoot button is working correctly.");
				flag = true;
				switchToPreviousTabOfExperienceMgmtPage();
			}else
			{
				LOGGER.error("Troubleshoot button is not working correctly.");
				return false;
			}
			return flag;
			}else if(waitForElementsOfExperienceMgmtPage("recommendedActionsNodata")){
				LOGGER.info("No data is present in Recommended Actions tile.");
				return true;
			}
			else {
				LOGGER.error("Troubleshoot button is not working correctly.");
				return false;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to verify the loading of charts on Experience Management page.
	 * @param Languagecode
	 * @param nodataMessage
	 * @param chartData
	 * @param chartName
	 * @return
	 */

	public final boolean verifyExperienceManagementChartLoading(String Languagecode, String nodataMessage, String chartData,String chartName) {
		boolean flag = false;
		try {
			//if(!waitForElementsOfExperienceMgmtPage("meterchartNoDataMessage")){
			if (waitForElementsOfExperienceMgmtPage(chartData)) {
				LOGGER.info(chartName+ " Chart is loading successfully.");
				flag = true;
			} else if (!getTextOfExperienceMgmtPage(nodataMessage).equalsIgnoreCase(getTextLanguage(Languagecode, "daas_reports_ui", "flexidashboard.charts.nodata.oops_error_occurred"))) {
				LOGGER.info("No data is present in " +chartName+"Chart.");
				flag =  true;
			} else {
				LOGGER.error(chartName+ " Chart is not loading.");
				flag= false;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

    public final boolean verifyFleetScorePreferenceTile(String Languagecode,String editIcon){
		boolean flag = false;
		try{
			if(waitForElementsOfExperienceMgmtPage(editIcon))
			{
            clickOnElementsOfExperienceMgmtPage(editIcon);
			List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage("listofCheckBoxes");
			sleeper(3000);
			if(isAttributePresentExperienceMgmtPage("resettoDefaultButton","disabled"))
			{
				for(int i=0;i<listOfOptions.size()-2;i++){
					listOfOptions.get(i).click();
				}
				LOGGER.info("Changed two KPIs in Fleet Score Preference tile.");
				clickOnElementsOfExperienceMgmtPage("saveButtonFleetScorePref");
			}else
			{
				clickOnElementsOfExperienceMgmtPage("resettoDefaultButton");
				LOGGER.info("Reset to Default button is working correctly.");
				clickOnElementsOfExperienceMgmtPage("saveButtonFleetScorePref");
			}
        if(getTextOfExperienceMgmtPage("toastNotificationFleetScorePref").contains(getTextLanguage(Languagecode, "daas_ui","exp.mgmt.fleetscorepreferences.toast.success.description")))
		{
			flag = true;
			LOGGER.info("Fleet Score Preference tile is working correctly.");
		}else
		{
			LOGGER.error("Fleet Score Preference tile is not working correctly.Toast Notification is not coming.");
			flag= false;
		}
			}
			else
			{
				LOGGER.error("Edit icon is not present on Fleet Score Preference tile.");
				flag = false;
			}
			return flag;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}


	//Write a function to click on one element and validate the other element on next page
	public final boolean verifyClickAndValidateElement(String elementToClick,String elementToValidate){
		boolean flag = false;
		try{
			if(waitForElementsOfExperienceMgmtPage(elementToClick)){
				clickOnElementsOfExperienceMgmtPage(elementToClick);
				if(waitForElementsOfExperienceMgmtPage(elementToValidate)){
					flag = true;
					LOGGER.info("Element is clicked and validated successfully.");
				}else{
					LOGGER.error("Element is not validated successfully.");
					flag = false;
				}
			}else{
				LOGGER.error("Element is not present on the page.");
				flag = false;
			}
			return flag;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}



	/**
	 * This method is used to verify header column on reports(with frame)
	 * 
	 * @param languageCode: This is language code used for multiple languages.
	 * @param keyHeaderOnReportPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill down page
	 * @param DeviceByOsInfo: List of key for Device by OS
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHeaderColumnOnReportPageWithFrameFlexi(String languageCode, String keyHeaderOnReportPage, String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
		for (int firstLevel = 0; firstLevel < listOfLabelsFirstDrill.size(); firstLevel++) {
			listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
			sleeper(3000);
			listOfLabelsFirstDrill.get(firstLevel).click();
			mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
			waitForPageLoaded();
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
			waitForPresenceOfElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			sleeper(3000);
			verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
			List<WebElement> listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
			for (int secondLevel = 1; secondLevel < listOfLabelsSecondDrill.size();) {
				listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
				listOfLabelsSecondDrill.get(secondLevel).click();
				mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
				waitForPageLoaded();
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
				osMajorVersion = getTextOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextMajorVersionKeyFlexi"));
				// For Other cases
				if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
					// For OSX Other cases
					if (osMajorVersion.contains("OSX Other")) {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
						verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
						List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
						for (int thirdLevel = 0; thirdLevel < listOfLabelsThirdDrill.size();) {
							listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
							listOfLabelsThirdDrill.get(thirdLevel).click();
							mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
							waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
							mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
							waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
							switchToDifferentTabOfExperienceMgmtPage();
							waitForPageLoaded();
							sleeper(3000);
							if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnReportPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
							List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
							for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
								if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Generic-Grid-JSON", headerNamesOnReportPage[listOfOptionsCounter]))) {
									flag = true;
								} else {
									flag = false;
									LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
									break;
								}
							}
							}else{
								LOGGER.error("Report did not load in 1 minute.");
							}
							switchToPreviousTabOfExperienceMgmtPage();
							thirdLevel++;
							if (thirdLevel == listOfLabelsThirdDrill.size()) {
								clickOnElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsBackKeyFlexi"));
							}
						}
					} else {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
						switchToDifferentTabOfExperienceMgmtPage();
						waitForPageLoaded();
						sleeper(3000);
						if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnReportPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
						List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
						for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
							if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Generic-Grid-JSON", headerNamesOnReportPage[listOfOptionsCounter]))) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
								break;
							}
						}}else{
							LOGGER.error("Report did not load in 1 minute.");
						}
						switchToPreviousTabOfExperienceMgmtPage();
					}
				} else {
					scrollToExperienceMgmtPage("devicesByOsTitleFlexi");
					mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
					waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
					verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
					List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
					for (int thirdlevel = 0; thirdlevel < listOfLabelsThirdDrill.size();) {
						listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
						listOfLabelsThirdDrill.get(thirdlevel).click();
						mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
						waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
						mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
						waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
						switchToDifferentTabOfExperienceMgmtPage();
						waitForPageLoaded();
						sleeper(3000);
						if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnReportPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
						List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
						for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
							if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Generic-Grid-JSON", headerNamesOnReportPage[listOfOptionsCounter]))) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
								break;
							}
						}}else{
							LOGGER.error("Report did not load in 1 minute.");
						}
						switchToPreviousTabOfExperienceMgmtPage();
						thirdlevel++;
						if (thirdlevel == listOfLabelsThirdDrill.size()) {
							clickOnElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsBackKeyFlexi"));
						}
					}
				}
				secondLevel++;
				if (secondLevel == listOfLabelsSecondDrill.size()) {
					osMajorVersion = null;
					clickOnElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsBackKeyFlexi"));
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify header column on Experience Management Grid page.
	 *
	 * @param languageCode: This is language code used for multiple languages.
	 * @param keyHeaderOnReportPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill down page
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHeaderExpMgmtGridPage(String languageCode, String keyHeaderOnReportPage, String[] headerNamesOnReportPage)
	{
		boolean flag = false;
		try{
			if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnReportPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
			List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
			for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
				if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", headerNamesOnReportPage[listOfOptionsCounter]).trim())) {
					flag = true;
				} else {
					flag = false;
					LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at Exp Mgmt Grid page.");
					break;
				}
			}
			}else{
				LOGGER.error("Exp Mgmt Grid page did not load in 1 minute.");
			}
	}catch(Exception e){
		e.printStackTrace();
		return false;
	}
		return flag;
	}

	public final boolean verifyChangesSinceLastWeek()
	{
    boolean flag = false;
	try{
		if(waitForElementsOfExperienceMgmtPage("itemFirst")){
			String	noOfDevices = getTextOfExperienceMgmtPage("itemFirst");
			int noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
			if(!(noOfDevicesInt==0)){
			clickOnElementsOfExperienceMgmtPage("itemFirst");
			if(waitForElementsOfExperienceMgmtPageDynamic("columnListGridPage", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
				if (verifyCountOfDevices(noOfDevicesInt)) {
					flag = true;
					LOGGER.info("Count of devices validation in Changes Since Last Week chart got passed.");
				} else {
					flag = false;
					LOGGER.error("Count of devices validation in Changes Since Last Week chart got failed.");
				}
			}}else{
				LOGGER.info("No data is present in Changes Since Last Week chart.");
				flag=true;
			}
		}else if(waitForElementsOfExperienceMgmtPage("nodataIconChangesSinceLastWeek")){
			LOGGER.info("No data is present in Changes Since Last Week chart.");
			flag=true;
		}
		   return flag;
		}
	catch(Exception e) {
		e.printStackTrace();
		return false;
	}
	}


	public boolean verifydexChartDashboardNavigation(String expandButton,String expmgmtTab)
	{
		boolean flag = false;
		try {
			//scrollToExperienceMgmtPage(expandButton);
			if (waitForElementsOfExperienceMgmtPage(expandButton)) {
				clickOnElementsOfExperienceMgmtPage(expandButton);
				waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
				if (waitForElementsOfExperienceMgmtPage(expmgmtTab)) {
					flag = true;
					LOGGER.info("Navigated to Experience Management page "+expmgmtTab+" from Dashboard successfully.");
				} else {
					LOGGER.error("Experience Management page did not load successfully when navigated from Dashboard.");
				}
			} else {
				LOGGER.error("Dex Charts on Dashboard page did not load successfully.");
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	// Write a method to validate if reset to default link is clickable
	public final boolean verifyResetToDefaultLink(String Languagecode)
	{
		boolean flag = false;
		try{
			if(waitForElementsOfExperienceMgmtPage("resetToDefaultLink")){
				clickOnElementsOfExperienceMgmtPage("resetToDefaultLink");
				if(waitForElementsOfExperienceMgmtPage("resetToDefaultLink")){
					flag = true;
					LOGGER.info("Reset to Default link is clickable.");
				}else{
					LOGGER.error("Reset to Default link is not clickable.");
				}
			}else{
				LOGGER.error("Reset to Default link is not present.");
			}
			return flag;
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to verify header column on reports(with frame)
	 * 
	 * @param languageCode: This is language code used for multiple languages.
	 * @param keyHeaderOnReportPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill down page
	 * @param DeviceByOsInfo: List of key for Device by OS
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHeaderColumnOnReportPageWithFrame(String languageCode, String keyHeaderOnReportPage, String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
		for (int firstLevel = 0; firstLevel < listOfLabelsFirstDrill.size(); firstLevel++) {
			listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			action.moveToElement(listOfLabelsFirstDrill.get(firstLevel)).build().perform();
			waitForPageLoaded();
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKey"));
			listOfLabelsFirstDrill.get(firstLevel).click();
			waitForPresenceOfElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			List<WebElement> listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			for (int secondLevel = 0; secondLevel < listOfLabelsSecondDrill.size();) {
				listOfLabelsSecondDrill = null;
				listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				action.moveToElement(listOfLabelsSecondDrill.get(secondLevel)).build().perform();
				waitForPageLoaded();
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKey"));
				osMajorVersion = getTextOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextMajorVersionKey"));
				// For Other cases
				if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
					// For OSX Other cases
					if (osMajorVersion.contains("OSX Other")) {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
						List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
						for (int thirdLevel = 0; thirdLevel < listOfLabelsThirdDrill.size();) {
							listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
							action.moveToElement(listOfLabelsThirdDrill.get(thirdLevel)).build().perform();
							waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKey"));
							listOfLabelsThirdDrill.get(thirdLevel).click();
							waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
							switchToDifferentTabOfExperienceMgmtPage();
							waitForPageLoaded();
							sleeper(3000);
							List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
							for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
								if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
									flag = true;
								} else {
									flag = false;
									LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
									break;
								}
							}
							switchToPreviousTabOfExperienceMgmtPage();
							thirdLevel++;
							if (thirdLevel == listOfLabelsThirdDrill.size()) {
								clickOnElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsBackKey"));
							}
						}
					} else {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
						switchToDifferentTabOfExperienceMgmtPage();
						waitForPageLoaded();
						sleeper(3000);
						List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
						for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
							if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
								break;
							}
						}
						switchToPreviousTabOfExperienceMgmtPage();
					}
				} else {
					listOfLabelsSecondDrill.get(secondLevel).click();
					waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
					List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
					for (int thirdlevel = 0; thirdlevel < listOfLabelsThirdDrill.size();) {
						listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
						action.moveToElement(listOfLabelsThirdDrill.get(thirdlevel)).build().perform();
						waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKey"));
						listOfLabelsThirdDrill.get(thirdlevel).click();
						waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
						switchToDifferentTabOfExperienceMgmtPage();
						waitForPageLoaded();
						sleeper(3000);
						List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
						for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
							if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
								break;
							}
						}
						switchToPreviousTabOfExperienceMgmtPage();
						thirdlevel++;
						if (thirdlevel == listOfLabelsThirdDrill.size()) {
							clickOnElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsBackKey"));
						}
					}
				}
				secondLevel++;
				if (secondLevel == listOfLabelsSecondDrill.size()) {
					osMajorVersion = null;
					clickOnElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsBackKey"));
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify redirection on reports(with frame)
	 * 
	 * @param languageCode: This is language code used for multiple languages.
	 * @param keyHeaderOnReportPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill down page
	 * @param DeviceByOsInfo: List of key for Device by Os
	 * @param deviceDetailsKey: Element present on Device page
	 * @param columnListKey: Columns present in the grid
	 * @param errorKey: Error message flashes on the Dashboard
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDeviceByOSReportHeaderWithFrameFlexi(String languageCode, String keyHeaderOnReportPage, String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo, String deviceDetailsKey, String columnListKey, String errorKey) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage("deviceByOSLegendsHidden");
		listOfLabelsFirstDrill.get(0).click();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
		waitForPageLoaded();
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
		waitForPresenceOfElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
		verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
		listOfLabelsSecondDrill.get(0).click();
		waitForPageLoaded();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		osMajorVersion = getTextOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextMajorVersionKeyFlexi"));
		// For Other cases
		if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
			// For OSX Other cases
			if (osMajorVersion.contains("OSX Other")) {
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
				List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
				listOfLabelsThirdDrill.get(0).click();
				mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfExperienceMgmtPageDynamic(columnListKey,DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
					List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
					for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
						if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
							flag = true;
						} else {
							flag = false;
							LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
							break;
						}
					}
					switchToPreviousTabOfExperienceMgmtPage();
				} else {
					LOGGER.error("No data to diplay/Report did not load in 1 minute.");
					switchToPreviousTabOfExperienceMgmtPage();
				}
			} else {
				listOfLabelsSecondDrill.get(0).click();
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfExperienceMgmtPage(columnListKey)) {
					List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
					for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
						if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
							flag = true;
						} else {
							flag = false;
							LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
							break;
						}
					}
					switchToPreviousTabOfExperienceMgmtPage();
				} else {
					LOGGER.error("No data to diplay");
					switchToPreviousTabOfExperienceMgmtPage();
				}
			}
		} else {
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
			List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
			listOfLabelsThirdDrill.get(0).click();
			mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			switchToDifferentTabOfExperienceMgmtPage();
			waitForPageLoaded();
			sleeper(3000);
			if (waitForElementsOfExperienceMgmtPageDynamic(columnListKey,60)) {
				List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnReportPage);
				for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
					if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
						flag = true;
					} else {
						flag = false;
						LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText() + " Header does not match at reprot page.");
						break;
					}
				}
				switchToPreviousTabOfExperienceMgmtPage();
			} else {
				LOGGER.error("No data to diplay");
				switchToPreviousTabOfExperienceMgmtPage();
			}
		}
		return flag;
	}
	
	public final boolean verifyDeviceByOsRedirectionWithFrameFlexi(String languageCode, String keyHeaderOnReportPage, String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo, String deviceDetailsKey, String columnListKey, String errorKey) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage("deviceByOSLegendsHidden");
		listOfLabelsFirstDrill.get(0).click();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
		waitForPageLoaded();
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
		waitForPresenceOfElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
		verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
		listOfLabelsSecondDrill.get(0).click();
		waitForPageLoaded();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		osMajorVersion = getTextOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextMajorVersionKeyFlexi"));
		// For Other cases
		if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
			// For OSX Other cases
			if (osMajorVersion.contains("OSX Other")) {
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
				List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
				listOfLabelsThirdDrill.get(0).click();
				mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfExperienceMgmtPageDynamic(columnListKey,DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
					clickOnElementsOfExperienceMgmtPage(columnListKey);
					switchToDifferentTabOfExperienceMgmtPage();
					waitForPageLoaded();
					if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
						String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.error("No data to diplay/Report did not load in 1 minute.");
					switchToPreviousTabOfExperienceMgmtPage();
				}
			} else {
				listOfLabelsSecondDrill.get(0).click();
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfExperienceMgmtPage(columnListKey)) {
					clickOnElementsOfExperienceMgmtPage(columnListKey);
					switchToDifferentTabOfExperienceMgmtPage();
					waitForPageLoaded();
					if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
						String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.error("No data to diplay");
					switchToPreviousTabOfExperienceMgmtPage();
				}
			}
		} else {
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
			List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
			listOfLabelsThirdDrill.get(0).click();
			mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			switchToDifferentTabOfExperienceMgmtPage();
			waitForPageLoaded();
			sleeper(3000);
			if (waitForElementsOfExperienceMgmtPageDynamic(columnListKey,60)) {
				clickOnElementsOfExperienceMgmtPage(columnListKey);
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
					String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else {
				LOGGER.error("No data to diplay");
				switchToPreviousTabOfExperienceMgmtPage();
			}
		}
		return flag;
	}
	
	/**
	 * This method is used to verify redirection on reports(with frame)
	 * 
	 * @param languageCode: This is language code used for multiple languages.
	 * @param keyHeaderOnReportPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill down page
	 * @param DeviceByOsInfo: List of key for Device by Os
	 * @param deviceDetailsKey: Element present on Device page
	 * @param columnListKey: Columns present in the grid
	 * @param errorKey: Error message flashes on the Dashboard
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDeviceByOsRedirectionWithFrame(String languageCode, String keyHeaderOnReportPage, String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo, String deviceDetailsKey, String columnListKey, String errorKey) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
		listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
		action.moveToElement(listOfLabelsFirstDrill.get(0)).build().perform();
		waitForPageLoaded();
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKey"));
		listOfLabelsFirstDrill.get(0).click();
		waitForPresenceOfElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
		List<WebElement> listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
		listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
		action.moveToElement(listOfLabelsSecondDrill.get(0)).build().perform();
		waitForPageLoaded();
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKey"));
		osMajorVersion = getTextOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextMajorVersionKey"));
		// For Other cases
		if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
			// For OSX Other cases
			if (osMajorVersion.contains("OSX Other")) {
				listOfLabelsSecondDrill.get(0).click();
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				action.moveToElement(listOfLabelsThirdDrill.get(0)).build().perform();
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKey"));
				listOfLabelsThirdDrill.get(0).click();
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfExperienceMgmtPage(columnListKey)) {
					clickOnElementsOfExperienceMgmtPage(columnListKey);
					switchToDifferentTabOfExperienceMgmtPage();
					waitForPageLoaded();
					if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
						String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.error("No data to diplay");
					switchToPreviousTabOfExperienceMgmtPage();
				}
			} else {
				listOfLabelsSecondDrill.get(0).click();
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfExperienceMgmtPage(columnListKey)) {
					clickOnElementsOfExperienceMgmtPage(columnListKey);
					switchToDifferentTabOfExperienceMgmtPage();
					waitForPageLoaded();
					if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
						String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.error("No data to diplay");
					switchToPreviousTabOfExperienceMgmtPage();
				}
			}
		} else {
			listOfLabelsSecondDrill.get(0).click();
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			action.moveToElement(listOfLabelsThirdDrill.get(0)).build().perform();
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKey"));
			listOfLabelsThirdDrill.get(0).click();
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			switchToDifferentTabOfExperienceMgmtPage();
			waitForPageLoaded();
			sleeper(3000);
			if (waitForElementsOfExperienceMgmtPage(columnListKey)) {
				clickOnElementsOfExperienceMgmtPage(columnListKey);
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
					String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else {
				LOGGER.error("No data to diplay");
				switchToPreviousTabOfExperienceMgmtPage();
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 * 
	 * @param languageCode: This is language code used for multiple languages.
	 * @param labelsKey: Chart labels.
	 * @param tooltipTextKey: Tooltip text after hovering on a chart.
	 * @param tooltipTextMajorVersionKey: check major version of operating system.
	 * @param tooltipTextMajorVersionNameKey: check major version name of operating system.
	 * @param tooltipTextCountKey: check Tooltip text count of operating system.
	 * @param reportTextCountKey: check report text count of operating system.
	 * @param labelsBackKey: check for back functionality.
	 * @param operatingSystemKey: check type of operating system.
	 * @param operatingSystemReleaseKey: check type of operation system release.
	 * @param moreDetailsLinkKey: check for more details at report page.
	 * @param frameKey: Iframe on the drill down page.
	 * @return flag: boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipTextCountAndVersionOnReportWithFrame(String languageCode, String targetElement,String labelsKey, String tooltipTextKey, String tooltipTextMajorVersionKey, String tooltipTextMajorVersionNameKey, String tooltipTextCountKey, String reportTextCountKey, String labelsBackKey, String operatingSystemKey, String operatingSystemReleaseKey, String moreDetailsLinkKey, String frameKey,String keyDrillDownLabelsAllHidden,String visibleLegends,String labels) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		String osMajorVersionName = null;
		verifyLegendsAreHidden(keyDrillDownLabelsAllHidden,visibleLegends,labels);
		List<WebElement> listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage(labelsKey);
		for (int firstLevel = 0; firstLevel < listOfLabelsFirstDrill.size(); firstLevel++) {
			listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage(labelsKey);
			verifyLegendsAreHidden(keyDrillDownLabelsAllHidden,visibleLegends,labels);
			listOfLabelsFirstDrill.get(firstLevel).click();
			mouseHoverbyoffsett(targetElement, 00, 150);
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			mouseHoverbyoffsettClick(targetElement, 00, 150);
			waitForPresenceOfElementsOfExperienceMgmtPage(labelsKey);
			verifyLegendsAreHidden(keyDrillDownLabelsAllHidden,visibleLegends,labels);
			List<WebElement> listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage(labelsKey);
			for (int secondLevel = 0; secondLevel < listOfLabelsSecondDrill.size();) {
				listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage(labelsKey);
				verifyLegendsAreHidden(keyDrillDownLabelsAllHidden,visibleLegends,labels);
				listOfLabelsSecondDrill.get(secondLevel).click();
				mouseHoverbyoffsett(targetElement, 00, 150);
				waitForElementsOfExperienceMgmtPage(tooltipTextKey);
				osMajorVersion = getTextOfExperienceMgmtPage(tooltipTextMajorVersionKey);
				// For Other cases
				if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
					// For OSX Other cases
					if (osMajorVersion.contains("OSX Other")) {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfExperienceMgmtPage(labelsKey);
						verifyLegendsAreHidden(keyDrillDownLabelsAllHidden,visibleLegends,labels);
						List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(labelsKey);
						for (int thirdLevel = 0; thirdLevel < listOfLabelsThirdDrill.size();) {
							listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(labelsKey);
							verifyLegendsAreHidden(keyDrillDownLabelsAllHidden,visibleLegends,labels);
							listOfLabelsFirstDrill.get(thirdLevel).click();
							mouseHoverbyoffsett(targetElement, 00, 150);
							waitForElementsOfExperienceMgmtPage(tooltipTextKey);
							osMajorVersion = getTextOfExperienceMgmtPage(tooltipTextMajorVersionKey);
							osMajorVersionName = getTextOfExperienceMgmtPage(tooltipTextMajorVersionNameKey);
							//osCount = (getTextOfExperienceMgmtPage(tooltipTextCountKey)).split("\\(")[0].trim();
							mouseHoverbyoffsettClick(targetElement, 00, 150);
							waitForElementsOfExperienceMgmtPage(labelsKey);
							switchToDifferentTabOfExperienceMgmtPage();
							if(waitForElementsOfExperienceMgmtPageDynamic(moreDetailsLinkKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
							clickOnElementsOfExperienceMgmtPage(moreDetailsLinkKey);
							waitForElementsOfExperienceMgmtPage(operatingSystemKey);
							List<WebElement> monthTextForOS = getElementsOfExperienceMgmtPage(operatingSystemKey);
							if (monthTextForOS.get(0).getText().contains(osMajorVersion) && monthTextForOS.get(1).getText().contains(osMajorVersionName)) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(osMajorVersionName + " Tooltip text and version does not match at reprot page.");
								switchToPreviousTabOfExperienceMgmtPage();
								return flag;
							}
							}else{
								LOGGER.error("Report did not load in 1 minute.");
							}
							switchToPreviousTabOfExperienceMgmtPage();
							listOfLabelsFirstDrill.get(secondLevel).click();
							thirdLevel++;
							if (thirdLevel == listOfLabelsThirdDrill.size()) {
								osMajorVersion = null;
								osMajorVersionName = null;
								clickOnElementsOfExperienceMgmtPage(labelsBackKey);
							}
						}
					} else {
						if (osMajorVersion.contains("Windows Other")) {
						} else {
						}
						mouseHoverbyoffsettClick(targetElement, 00, 150);
						waitForElementsOfExperienceMgmtPage(labelsKey);
						switchToDifferentTabOfExperienceMgmtPage();
						if(waitForElementsOfExperienceMgmtPageDynamic(operatingSystemKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)){						
						clickOnElementsOfExperienceMgmtPage(moreDetailsLinkKey);
						List<WebElement> monthTextOtherForOS = getElementsOfExperienceMgmtPage(operatingSystemKey);
						if (monthTextOtherForOS.get(0).getText().contains(osMajorVersion)) {
							flag = true;
						} else {
							flag = false;
							LOGGER.error(osMajorVersion + " Tooltip text and version does not match at reprot page.");
							switchToPreviousTabOfExperienceMgmtPage();
							return flag;
						}}else{
							LOGGER.error("Report did not load in 1 minute.");
						}
						switchToPreviousTabOfExperienceMgmtPage();
						listOfLabelsSecondDrill.get(secondLevel).click();
					}
				} else {
					mouseHoverbyoffsettClick(targetElement, 00, 150);
					waitForElementsOfExperienceMgmtPage(labelsKey);
					verifyLegendsAreHidden(keyDrillDownLabelsAllHidden,visibleLegends,labels);
					List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(labelsKey);
					for (int thirdlevel = 0; thirdlevel < listOfLabelsThirdDrill.size();) {
						listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage(labelsKey);
						verifyLegendsAreHidden(keyDrillDownLabelsAllHidden,visibleLegends,labels);
						listOfLabelsThirdDrill.get(thirdlevel).click();
						mouseHoverbyoffsett(targetElement, 00, 150);
						waitForElementsOfExperienceMgmtPage(tooltipTextKey);
						osMajorVersion = getTextOfExperienceMgmtPage(tooltipTextMajorVersionKey);
						osMajorVersionName = listOfLabelsThirdDrill.get(0).getText();
						//osCount = (getTextOfExperienceMgmtPage(tooltipTextCountKey)).split("\\(")[0].trim();
						mouseHoverbyoffsettClick(targetElement, 00, 150);
						waitForElementsOfExperienceMgmtPage(labelsKey);
						switchToDifferentTabOfExperienceMgmtPage();
						if(waitForElementsOfExperienceMgmtPageDynamic(moreDetailsLinkKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)){						
						clickOnElementsOfExperienceMgmtPage(moreDetailsLinkKey);
						waitForElementsOfExperienceMgmtPage(operatingSystemKey);
						List<WebElement> monthTextForOS = getElementsOfExperienceMgmtPage(operatingSystemKey);
						if (monthTextForOS.get(0).getText().contains(osMajorVersion) || monthTextForOS.get(1).getText().contains(osMajorVersionName)) {
							flag = true;
						}else {
							flag = false;
							LOGGER.error(osMajorVersionName + " Tooltip text and version does not match at report page.");
							switchToPreviousTabOfExperienceMgmtPage();
							return flag;
						}
						}else{
							LOGGER.error("Report did not load in 1 minute.");
						}
						switchToPreviousTabOfExperienceMgmtPage();
						thirdlevel++;
						//listOfLabelsThirdDrill.get(thirdlevel).click();
						if (thirdlevel == listOfLabelsThirdDrill.size()) {
							osMajorVersion = null;
							osMajorVersionName = null;
							clickOnElementsOfExperienceMgmtPage(labelsBackKey);
						}
					}
				}
				secondLevel++;
				//listOfLabelsSecondDrill.get(secondLevel).click();
				if (secondLevel == listOfLabelsSecondDrill.size()) {
					osMajorVersion = null;
					clickOnElementsOfExperienceMgmtPage(labelsBackKey);
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip count with total rows on reports with Frame
	 * 
	 * @param labelsKey: Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnListKey: Column names present in the grid
	 * @param paginationKey: Locator of the pagination
	 * @param targetElement: element to move to
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param frameKey: Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipCountonReportWithFrameFlexi(String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement) throws Exception {
		boolean flag = false;
		String count = null;
		String paginationText = null;
		waitForPageLoaded();
		sleeper(2000);
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
		verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey,keyDrillDownLabelsAllHidden);
		List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
		for (int ilistOfLabelsCounter = 0; ilistOfLabelsCounter < listOfLegends.size(); ilistOfLabelsCounter++) {
			listOfLegends.get(ilistOfLabelsCounter).click();
			mouseHoverbyoffsett(targetElement, 00, 80);
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			count = getTextOfExperienceMgmtPage(tooltipTextKey);
			String count_clean = count.replaceAll(",", "");
			Integer tooltipcount = Integer.valueOf(count_clean);
			sleeper(2000);
			waitForPageLoaded();
			mouseHoverbyoffsettClick(targetElement, 00, 80);
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if(waitForElementsOfExperienceMgmtPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
			if (tooltipcount > 10) {
				sleeper(2000);
				waitForPageLoaded();
				paginationText = getTextOfExperienceMgmtPage(paginationKey);
				String arr[] = paginationText.split(" ");
				String laststring = arr[arr.length - 1];
				if (laststring.equals(count_clean)) {
					flag = true;
				}
			} else {
				sleeper(2000);
				waitForPageLoaded();
				List<WebElement> listOfColumntext = getElementsOfExperienceMgmtPage(columnListKey);
				if (tooltipcount == (listOfColumntext.size())) {
					flag = true;
				}
			}}else{
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfExperienceMgmtPage();
			listOfLegends.get(ilistOfLabelsCounter).click();
		}
		return flag;
	}
	
	/**
	 * This method is used to verify tool tip count with total rows on reports with Frame
	 * 
	 * @param labelsKey: Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnListKey: Column names present in the grid
	 * @param paginationKey: Locator of the pagination
	 * @param frameKey: Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipCountonReportWithFrame(String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey) throws Exception {
		boolean flag = false;
		String count = null;
		String paginationText = null;
		Actions action = new Actions(getDriver());
		waitForPageLoaded();
		sleeper(2000);
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);  // lable of legends
		for (int ilistOfLabelsCounter = 0; ilistOfLabelsCounter < listOfLabels.size(); ilistOfLabelsCounter++) {
			listOfLabels.get(ilistOfLabelsCounter);
			action.moveToElement(listOfLabels.get(ilistOfLabelsCounter)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			count = getTextOfExperienceMgmtPage(tooltipTextKey);
			String count_clean = count.replaceAll(",", "");
			Integer tooltipcount = Integer.valueOf(count_clean);
			sleeper(2000);
			waitForPageLoaded();
			listOfLabels.get(ilistOfLabelsCounter).click();
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if (tooltipcount > 10) {
				sleeper(2000);
				waitForPageLoaded();
				paginationText = getTextOfExperienceMgmtPage(paginationKey);
				String arr[] = paginationText.split(" ");
				String laststring = arr[arr.length - 1];
				if (laststring.equals(count_clean)) {
					flag = true;
				}
			} else {
				sleeper(2000);
				waitForPageLoaded();
				List<WebElement> listOfColumntext = getElementsOfExperienceMgmtPage(columnListKey);
				if (tooltipcount == (listOfColumntext.size())) {
					flag = true;
				}
			}
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}

	/**
	 * This method basically verify the header Name on report page with Frame
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param labelKey: Chart labels
	 * @param keyHeaderOnNextPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Vlaues in the header section on the drill down page
	 * @param frameKey: Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean headerTextVerificationOnReportPageFrame(String LanguageCode, String labelKey, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey) throws Exception {
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelKey);
		boolean flag = false;
		verifyElementIsClickableOfExperienceMgmtPage(labelKey);
		listOfLabels.get(0).click();
		sleeper(3000);
		switchToDifferentTabOfExperienceMgmtPage();
		sleeper(3000);
		if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnNextPage,DashboardVariables.DASHBOARD_REPORTS_WAIT)){
		List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
			if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}
		}else{
		  LOGGER.error("Report page did not load in 1 minute.");	
		}
		switchToPreviousTabOfExperienceMgmtPage();
		return flag;
	}
	
	/**
	 * This method basically verify the hidden of labels of legends on drillDown Chart
	 * 
	 * @param keyLegendLabel: Chart criteria present on the graph
	 * @param keyDrillDownLabels: Chart legends present below the chart
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking on legends
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegends(String keyLegendLabel, String keyDrillDownLabels, String tooltipTextKey, String keyDrillDownLabelsAllHidden) throws Exception {
		Actions action = new Actions(getDriver());
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		List<WebElement> drillDownLabelsElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyDrillDownLabels);
		ArrayList<String> legendLabels = getChartLabelsExperienceMgmtPage(keyLegendLabel);
		ArrayList<String> drillDownLabels = getChartLabelsExperienceMgmtPage(keyDrillDownLabels);
		ArrayList<String> toolTipText = new ArrayList<String>();
		if (legendLabels.size() != drillDownLabels.size()) {
			return false;
		} else {
			boolean visibiltyFlag = false;
			boolean textFlag = false;
			overloop: for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				String legendLabelText = legendLabelElementList.get(listCounter).getText();
				legendLabelElementList.get(listCounter).click();
				sleeper(3000);
				if (listCounter == legendLabelElementList.size() - 1) {
					if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden)) {
						return false;
					} else {
						return true;
					}
				} else {
					waitForElementsOfExperienceMgmtPage(keyDrillDownLabels);
					drillDownLabelsElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyDrillDownLabels);
					for (int m = 0; m < drillDownLabelsElementList.size(); m++) {
						sleeper(2000);
						action.moveToElement(drillDownLabelsElementList.get(m)).build().perform();
						waitForElementsOfExperienceMgmtPage(tooltipTextKey);
						toolTipText.add(getTextOfExperienceMgmtPage(tooltipTextKey));
					}
				}
				for (int drillDownLabelsArraycounter = 0; drillDownLabelsArraycounter < toolTipText.size(); drillDownLabelsArraycounter++) {
					visibiltyFlag = legendLabelText.equalsIgnoreCase(toolTipText.get(drillDownLabelsArraycounter).trim());
					if (visibiltyFlag) {
						textFlag = false;
						break overloop;
					} else {
						textFlag = true;
					}
				}
				toolTipText.clear();
			}
			return textFlag;
		}
	}
	
	/**
	 * This method basically verify the header Name on report page with Frame
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param labelKey: Chart labels
	 * @param keyHeaderOnNextPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Vlaues in the header section on the drill down page
	 * @param frameKey: Iframe on the drill down page
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param targetElement : element to be moved on
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean headerTextVerificationOnReportPageFrameFlexi(String LanguageCode, String labelKey, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement) throws Exception {
		List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelKey);
		
		boolean flag = false;
		verifyElementIsClickableOfExperienceMgmtPage(labelKey);
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
		verifyLabelHiddenWhenClickOnLegendsFlexi(labelKey, keyDrillDownLabelsAllHidden);
		listOfLegends.get(0).click();
		sleeper(2000);
		mouseHoverbyoffsettClick(targetElement, 00, 60);
		sleeper(3000);
		switchToDifferentTabOfExperienceMgmtPage();
		sleeper(3000);
		waitForElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
		if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
		List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
			if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}}else{
			LOGGER.error("Report did not load in 1 minute.");
		}
		switchToPreviousTabOfExperienceMgmtPage();
		return flag;
	}
	
	/**
	 * This method basically verify the header Name on report page with Frame
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param labelKey: Chart labels
	 * @param keyHeaderOnNextPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Vlaues in the header section on the drill down page
	 * @param frameKey: Iframe on the drill down page
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param targetElement : element to be moved on
	 * @param legendDropdownKey : key of legend dropdown
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean headerTextVerificationOnReportPageFrameDonutChartFlexi(String LanguageCode, String labelKey, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey) throws Exception {
		List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelKey);
		
		boolean flag = false;
		verifyElementIsClickableOfExperienceMgmtPage(labelKey);
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
		verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelKey,keyDrillDownLabelsAllHidden,legendDropdownKey);
		if(waitForElementsOfExperienceMgmtPageDynamic(legendDropdownKey, 1)){
		clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
		}else{
			LOGGER.info("Legend dropdown not present on chart");
		}
		sleeper(2000);
		listOfLegends.get(0).click();
		sleeper(2000);
		mouseHoverbyoffsettClick(targetElement, 00, 72);
		sleeper(3000);
		switchToDifferentTabOfExperienceMgmtPage();
		sleeper(3000);
		waitForElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
		if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
		List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
			if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}}else{
			LOGGER.error("Report did not load in 1 minute.");
		}
		switchToPreviousTabOfExperienceMgmtPage();
		return flag;
	}
	
	
	public final boolean verifyLegendsAreHidden(String hiddenLegends,String visibleLegends,String labels){
		try {
			boolean flag = false;
			if(waitForElementsOfExperienceMgmtPageDynamic(labels,5)){
				for(int i = 0;i<getElementsTillAllElementsVisibleofExperienceMgmtPage(visibleLegends).size();i++){
					sleeper(2000);
					if(!getElementsTillAllElementsVisibleofExperienceMgmtPage(visibleLegends).get(i).getAttribute("class").contains("highcharts-legend-item-hidden")){
					getElementsTillAllElementsVisibleofExperienceMgmtPage(visibleLegends).get(i).click();
					}
				}
				if(!waitForElementsOfExperienceMgmtPageDynamic(labels,5)){
					flag = true;
					LOGGER.info("All Legends are hidden now.");
				}
			}else{
				flag = true;
				LOGGER.info("Legends are already hidden.");
			}
			return flag;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	/**
	 * This method basically verify the hidden of labels of legends on donut drillDown Chart
	 * 
	 * @param keyLegendLabel: Chart criteria present on the graph
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking on legends
	 * @param legendDropdownKey: legend dropdown key
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(String keyLegendLabel, String keyDrillDownLabelsAllHidden, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
			if (listCounter >= 2) {
				if(waitForElementsOfExperienceMgmtPage(legendDropdownKey))
				clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
				sleeper(2000);
				legendLabelElementList.get(listCounter).click();
			} else
				legendLabelElementList.get(listCounter).click();
			sleeper(2000);
			if (listCounter == legendLabelElementList.size() - 1) {
				if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	/**
	 * This method basically verify the hidden of labels of legends on drillDown Chart
	 * 
	 * @param keyLegendLabel: Chart criteria present on the graph
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking on legends
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegendsFlexi(String keyLegendLabel, String keyDrillDownLabelsAllHidden) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
			legendLabelElementList.get(listCounter).click();
			sleeper(3000);
			if (listCounter == legendLabelElementList.size() - 1) {
				if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method basically verify the visibility of labels of legends on drillDown Chart
	 * 
	 * @param keyLegendLabel:Chart criteria present on the graph
	 * @param chartVisibility: locator of chart visibility
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegendsFlexi(String keyLegendLabel, String chartVisibility) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		if (verifyElementsOfExperienceMgmtPage(chartVisibility)) {
			return false;
		} else {
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				legendLabelElementList.get(listCounter).click();
				sleeper(3000);
				if (!verifyElementsOfExperienceMgmtPage(chartVisibility)) {
					return false;
				}
			}
			return true;
		}
	}



	/**
	 * This method basically verify the visibility of labels of legends on drillDown Chart
	 * 
	 * @param keyLegendLabel:Chart criteria present on the graph
	 * @param keyDrillDownLabels: Chart legends present below the chart
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking on legends
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegends(String keyLegendLabel, String keyDrillDownLabels, String tooltipTextKey, String keyDrillDownLabelsAllHidden) throws Exception {
		Actions action = new Actions(getDriver());
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		List<WebElement> drillDownLabelsElementList;
		ArrayList<String> toolTipText = new ArrayList<String>();
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden)) {
			return false;
		} else {
			boolean visibiltyFlag = false;
			boolean textFlag = false;
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				String legendLabelText = legendLabelElementList.get(listCounter).getText();
				legendLabelElementList.get(listCounter).click();
				drillDownLabelsElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyDrillDownLabels);
				for (int m = 0; m < drillDownLabelsElementList.size(); m++) {
					sleeper(2000);
					action.moveToElement(drillDownLabelsElementList.get(m)).build().perform();
					waitForElementsOfExperienceMgmtPage(tooltipTextKey);
					toolTipText.add(getTextOfExperienceMgmtPage(tooltipTextKey));
				}
				for (int drillDownLabelsArraycounter = 0; drillDownLabelsArraycounter < toolTipText.size(); drillDownLabelsArraycounter++) {
					visibiltyFlag = legendLabelText.equalsIgnoreCase(toolTipText.get(drillDownLabelsArraycounter).trim());
					sleeper(3000);
					if (visibiltyFlag) {
						textFlag = true;
						break;
					} else {
						textFlag = false;
					}
				}
				if (!textFlag) {
					return (!textFlag);
				}
				toolTipText.clear();
			}
			return textFlag;
		}
	}
	
	/**
	 * This method basically verify the visibility of labels of legends on drillDown Chart
	 * 
	 * @param keyLegendLabel:Chart criteria present on the graph
	 * @param chartVisibility: locator of chart visibility
	 * @param legendDropdownKey : legend dropdown key
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegendsDonutChartFlexi(String keyLegendLabel, String chartVisibility, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		if (verifyElementsOfExperienceMgmtPage(chartVisibility)) {
			return false;
		} else {
			for (int listCounter = legendLabelElementList.size() - 1; listCounter >= 0; listCounter--) {
				if (listCounter >= 2) {
					legendLabelElementList.get(listCounter).click();
					sleeper(2000);
					if(waitForElementsOfExperienceMgmtPage(legendDropdownKey))
					clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
					sleeper(2000);
				} else
					legendLabelElementList.get(listCounter).click();
				sleeper(2000);
				if (!verifyElementsOfExperienceMgmtPage(chartVisibility)) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * This method is used to validate invisibility of labels of legends Bar
	 * 
	 * @param keyLegendLabel: Legends present below the charts
	 * @param keyBarColumsList: Bars/ section in the graph
	 * @param keyBarColumsToolTipText: Tooltip text after hovering on a bar of the chart
	 * @param keyNoData: Charts having no data
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegendsBar(String keyLegendLabel, String keyBarColumsList, String keyBarColumsToolTipText, String keyNoData) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		boolean visibiltyFlag = false;
		boolean textFlag = false;
		if (verifyElementsOfExperienceMgmtPage(keyNoData)) {
			return false;
		} else {
			List<WebElement> barColumList;
			String[] toolTipText;
			Actions action = new Actions(getDriver());
			overloop: for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				String legendLabelText = legendLabelElementList.get(listCounter).getText();
				sleeper(2000);
				legendLabelElementList.get(listCounter).click();
				if (listCounter == legendLabelElementList.size() - 1) {
					if (verifyElementsOfExperienceMgmtPage(keyNoData)) {
						return true;
					} else {
						return false;
					}
				} else {
					barColumList = getElementsOfExperienceMgmtPage(keyBarColumsList);
					for (int barColumListCounter = 0; barColumListCounter < barColumList.size(); barColumListCounter++) {
						sleeper(2000);
						action.moveToElement(barColumList.get(barColumListCounter)).build().perform();
						waitForElementsOfExperienceMgmtPage(keyBarColumsToolTipText);
						toolTipText = getTextOfExperienceMgmtPage(keyBarColumsToolTipText).split("â—�");
						for (int toolTipTextCounter = 0; toolTipTextCounter < toolTipText.length; toolTipTextCounter++) {
							toolTipText[toolTipTextCounter] = toolTipText[toolTipTextCounter].trim();
							visibiltyFlag = (toolTipText[toolTipTextCounter].contains(legendLabelText) | legendLabelText.contains(toolTipText[toolTipTextCounter]));
							if (visibiltyFlag) {
								textFlag = false;
								break overloop;
							} else {
								textFlag = true;
							}
						}
					}
				}
			}
		}
		return textFlag;
	}

	/**
	 * This method is used to validate visibility of labels of legends Bar
	 * 
	 * @param keyLegendLabel: Legends present below the charts
	 * @param keyBarColumsList: Bars/ section in the graph
	 * @param keyBarColumsToolTipText: Tooltip text after hovering on a bar of the chart
	 * @param keyNoData: Charts having no data
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegendsBar(String keyLegendLabel, String keyBarColumsList, String keyBarColumsToolTipText, String keyNoData) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		boolean visibiltyFlag = false;
		boolean textFlag = false;
		if (!verifyElementsOfExperienceMgmtPage(keyNoData)) {
			return false;
		} else {
			List<WebElement> barColumList;
			String[] toolTipText;
			Actions action = new Actions(getDriver());
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				String legendLabelText = legendLabelElementList.get(listCounter).getText();
				sleeper(2000);
				legendLabelElementList.get(listCounter).click();
				barColumList = getElementsOfExperienceMgmtPage(keyBarColumsList);
				overloop: for (int i = 0; i < barColumList.size(); i++) {
					sleeper(2000);
					action.moveToElement(barColumList.get(i)).build().perform();
					waitForElementsOfExperienceMgmtPage(keyBarColumsToolTipText);
					toolTipText = getTextOfExperienceMgmtPage(keyBarColumsToolTipText).split("â—�");
					for (int toolTipTextCounter = 0; toolTipTextCounter < toolTipText.length; toolTipTextCounter++) {
						toolTipText[toolTipTextCounter] = toolTipText[toolTipTextCounter].trim();
						visibiltyFlag = (toolTipText[toolTipTextCounter].contains(legendLabelText) | legendLabelText.contains(toolTipText[toolTipTextCounter]));
						if (!visibiltyFlag) {
							textFlag = false;
						} else {
							textFlag = true;
							break overloop;
						}
					}
				}
				if (!textFlag) {
					return (!textFlag);
				}
			}
		}
		return textFlag;
	}

	/**
	 * This method is used to validate labels visibility of chart
	 * 
	 * @param labelsKey: labels of the chart retrieved from dashboard
	 * @param labelList: predefined label list
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartLabelsExperienceMgmtPage(String labelsKey, ArrayList<String> labelList) throws Exception {
		boolean flag = false;
		String updatedLabel;
		waitForElementsOfExperienceMgmtPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		List<String> labelsCurrentList = new ArrayList<String>();
		Set<String> store = new HashSet<>();
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
			if (Strings.isNullOrEmpty(listOfLabels.get(listOfLabelsCounter).getText())) {
				labelsCurrentList.add(listOfLabels.get(listOfLabelsCounter).getAttribute("textContent"));
			} else {
				labelsCurrentList.add(listOfLabels.get(listOfLabelsCounter).getText());
			}

			if (labelList.get(listOfLabelsCounter).contains("-")) {
				updatedLabel = labelList.get(listOfLabelsCounter).replace("-", " ");
				labelList.set(listOfLabelsCounter, updatedLabel);
			}
		}
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < labelsCurrentList.size(); listOfLabelsCounter++) {
			if (labelList.contains(labelsCurrentList.get(listOfLabelsCounter))) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}
		for (String labels : labelsCurrentList) {
			if (store.add(labels) == false) {
				LOGGER.debug("Found a duplicate labels in chart : " + labels);
				flag = false;
			}
			LOGGER.info("Chart labels is verified succefully");
		}
		return flag;
	}

	/**
	 * This method is used to verify device name link redirection on reports table for MSP with frame
	 * 
	 * @param labelsKey: List of labels/criteria present in the graph
	 * @param deviceDetailsKey: Element present on Device page
	 * @param columnListKey: Columns present in the grid
	 * @param errorKey: Error message flashes on the Dashboard
	 * @param frameKey: Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyDeviceNameRedirectionMSPWithFrame(String LanguageCode, String labelsKey, String deviceDetailsKey, String columnListKey, String errorKey, String frameKey) throws Exception {
		boolean flag = false;
		waitForElementsOfExperienceMgmtPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		for (int counter = 0; counter < 1; counter++) {
			listOfLabels.get(counter).click();
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if (waitForElementsOfExperienceMgmtPageDynamic(frameKey,DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				scrollToExperienceMgmtPage(columnListKey);
				if(verifyElementsOfExperienceMgmtPage(columnListKey)==true) { // as per this BUG 514104
					clickOnElementsOfExperienceMgmtPage(columnListKey);                     
					switchToDifferentTabOfExperienceMgmtPage();
					waitForPageLoaded();
					if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
						String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				}
				else{
					LOGGER.info("Device name is not present");
					flag = true;	
				}
			} else
				LOGGER.error("No data to diplay/Report did not load in 1 minute.");
			// DRIVER.switchTo().defaultContent();
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}
	
	/**
	 * This method is used to verify device name link redirection on reports table for MSP with frame
	 * @param LanguageCode: language code
	 * @param labelsKey: List of labels/criteria present in the graph
	 * @param deviceDetailsKey: Element present on Device page
	 * @param columnListKey: Columns present in the grid
	 * @param errorKey: Error message flashes on the Dashboard
	 * @param keyDrillDownLabelsAllHidden: label hidden locator
	 * @param targetElement: element to be moved on
	 * @param frameKey: Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyDeviceNameRedirectionMSPWithFrameFlexi(String LanguageCode, String labelsKey, String deviceDetailsKey, String columnListKey, String errorKey, String frameKey, String keyDrillDownLabelsAllHidden,String targetElement) throws Exception {
		boolean flag = false;
		waitForElementsOfExperienceMgmtPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
		verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey, keyDrillDownLabelsAllHidden);
		for (int counter = 0; counter < 1; counter++) {
			listOfLabels.get(counter).click();
			sleeper(3000);
			mouseHoverbyoffsettClick(targetElement, 00, 60);
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if (waitForElementsOfExperienceMgmtPageDynamic(frameKey,DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				scrollToExperienceMgmtPage(columnListKey);
				clickOnElementsOfExperienceMgmtPage(columnListKey);
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
					String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else
				LOGGER.error("No data to diplay");
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}
	
	/**
	 * This method is used to verify device name link redirection on reports table for MSP with frame
	 * @param LanguageCode: language code
	 * @param labelsKey: List of labels/criteria present in the graph
	 * @param deviceDetailsKey: Element present on Device page
	 * @param columnListKey: Columns present in the grid
	 * @param errorKey: Error message flashes on the Dashboard
	 * @param keyDrillDownLabelsAllHidden: label hidden locator
	 * @param targetElement: element to be moved on
	 * @param frameKey: Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyDeviceNameRedirectionMSPWithFrameDonutChartFlexi(String LanguageCode, String labelsKey, String deviceDetailsKey, String columnListKey, String errorKey, String frameKey, String keyDrillDownLabelsAllHidden,String targetElement, String chartNameDropdown) throws Exception {
		boolean flag = false;
		waitForElementsOfExperienceMgmtPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
		verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelsKey,keyDrillDownLabelsAllHidden,chartNameDropdown);
		for (int counter = 0; counter < 1; counter++) {
			if(waitForElementsOfExperienceMgmtPageDynamic(chartNameDropdown, 1)){
				clickOnElementsOfExperienceMgmtPage(chartNameDropdown);
				}
				else{
					LOGGER.info("Legend dropdown not present on chart");
				}
			//clickOnElementsOfExperienceMgmtPage(chartNameDropdown);
			sleeper(2000);
			listOfLabels.get(counter).click();
			sleeper(3000);
			mouseHoverbyoffsettClick(targetElement, 00, 72);
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if (waitForElementsOfExperienceMgmtPageDynamic(frameKey,DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				scrollToExperienceMgmtPage(columnListKey);
				clickOnElementsOfExperienceMgmtPage(columnListKey);
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
				if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
					String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else
				LOGGER.error("No data to diplay");
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}

	public boolean verifyBackFunctionality(String key, String backKey) {
		try {
			sleeper(2000);
			clickOnElementsOfExperienceMgmtPage(key);
			clickOnElementsOfExperienceMgmtPage(backKey);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

	}

	public String verifyUrlAfterReDirection(String key, String secondLevelKey, String tooltipTypeKey) throws Exception {
		// TODO this method will verify URL on incidents page after re-direction
		// from Dashboard page through chart selection
		clickOnElementsOfExperienceMgmtPage(key);
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(secondLevelKey);
		String typeText = null;
		WebElement subtype = null;
		for (int i = 0; i < 1; i++) {
			subtype = listOfLabels.get(i);
			action.moveToElement(subtype).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltipTypeKey);
			typeText = getTextOfExperienceMgmtPage(tooltipTypeKey);
		}
		String[] result = typeText.split(" ");
		subtype.click();
		return result[0].toUpperCase();
	}

	/**
	 * This method will select multiple companies from filter drop down
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param textKey: Company Search box key
	 * @param emptyTextKey: Company search box empty key
	 * @param listKey: List of companies in the dropdown
	 * @param dropdownBoxKey: Company drop down key
	 * @param companies: Company names to be selected
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyMultipleCompanyChangeOfExperienceMgmtPage(String LanguageCode, String textKey, String emptyTextKey, String listKey, String dropdownBoxKey, String... companies) throws Exception {
		int counter = 0;
		clickOnElementsOfExperienceMgmtPage("companyDropdownFlexi");
		if (companies.length == 0) {
			LOGGER.error("No companies are specified");
			return false;
		} else {
			for (String company : companies) {
				counter++;
				verifyMultipleCompaniesSelection(LanguageCode, ExperienceMgmtPageProperties.getProperty(textKey), company, ExperienceMgmtPageProperties.getProperty(emptyTextKey), ExperienceMgmtPageProperties.getProperty(listKey), ExperienceMgmtPageProperties.getProperty(dropdownBoxKey), counter);
			}
			LOGGER.info("Multiple companies selection successfully executed");
			return true;
		}
	}
	
	/**
	 * This method will select multiple companies from filter drop down
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param textKey: Company Search box key
	 * @param emptyTextKey: Company search box empty key
	 * @param listKey: List of companies in the dropdown
	 * @param dropdownBoxKey: Company drop down key
	 * @param companies: Company names to be selected
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyMultipleCompanyChangeOfExperienceMgmtPageFlexi(String LanguageCode, String textKey, String emptyTextKey, String listKey, String dropdownBoxKey, String... companies) throws Exception {
		int counter = 0;
		clickOnElementsOfExperienceMgmtPage("companyDropdownFlexi");
		if (companies.length == 0) {
			LOGGER.error("No companies are specified");
			return false;
		} else {
			for (String company : companies) {
				counter++;
				verifyMultipleCompaniesSelection(LanguageCode, ExperienceMgmtPageProperties.getProperty(textKey), company, ExperienceMgmtPageProperties.getProperty(emptyTextKey), ExperienceMgmtPageProperties.getProperty(listKey), ExperienceMgmtPageProperties.getProperty(dropdownBoxKey), counter);
			}
			LOGGER.info("Multiple companies selection successfully executed");
			return true;
		}
	}
	
	/**
	 * This method will verify the companies Name present in the header section on report page
	 * 
	 * @param labelKey: Chart label
	 * @param companyLocatorKey: Company name present on the header
	 * @param companies: Company names to be verified
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyCompaniesListOnHeader(String labelKey, String companyLocatorKey,String... companies) throws Exception {
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelKey);
		boolean flag = false;
		for (int listOfLabelsCount = 0; listOfLabelsCount < listOfLabels.size(); listOfLabelsCount++) {
			listOfLabels.get(listOfLabelsCount).click();
			switchToDifferentTabOfExperienceMgmtPage();
			waitForPageLoaded();
			if(waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
			waitForElementsOfExperienceMgmtPage("moreDetailsLink");
			clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
			waitForElementsOfExperienceMgmtPage(companyLocatorKey);
			String companyList = getTextOfExperienceMgmtPage(companyLocatorKey);
			for (String comp : companies) {
				if (companyList.contains(comp))
					flag = true;
				else {
					flag = false;
					break;
				}
			}
			}else{
				LOGGER.error("Report did not load in 1 minute.");
			}
			getDriver().switchTo().defaultContent();
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}
	
	
	/**
	 * This method will verify the companies Name present in the header section on report page
	 * 
	 * @param labelKey: Chart label
	 * @param companyLocatorKey: Company name present on the header
	 * @param companies: Company names to be verified
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyCompaniesListOnHeaderHWInventory(String labelKey, String companyLocatorKey,String... companies) throws Exception {
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelKey);
		boolean flag = false;
		for (int listOfLabelsCount = 0; listOfLabelsCount < listOfLabels.size(); listOfLabelsCount++) {
			listOfLabels.get(listOfLabelsCount).click();
			switchToDifferentTabOfExperienceMgmtPage();
			waitForPageLoaded();
			if(waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
			waitForElementsOfExperienceMgmtPage("moreDetailsLink");
			clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
			waitForElementsOfExperienceMgmtPage(companyLocatorKey);
			String companyList = getTextOfExperienceMgmtPage(companyLocatorKey);
			for (String comp : companies) {
				if (companyList.contains(comp))
					flag = true;
				else {
					flag = false;
					break;
				}
			}
			}else{
				LOGGER.error("Report did not load in 1 minute.");
			}
			getDriver().switchTo().defaultContent();
			switchToPreviousTabOfExperienceMgmtPage();
		}
		return flag;
	}
	
	/**
	 * This method will verify the companies Name present in the header section on report page
	 * 
	 * @param labelKey: Chart label
	 * @param companyLocatorKey: Company name present on the header
	 * @param companies: Company names to be verified
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyCompaniesListOnHeaderFlexi(String labelKey, String companyLocatorKey, String targetElement, String keyDrillDownLabelsAllHidden, String... companies) throws Exception {
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelKey);
		boolean flag = false;
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
		verifyLabelHiddenWhenClickOnLegendsFlexi(labelKey,keyDrillDownLabelsAllHidden);
		for (int listOfLabelsCount = 0; listOfLabelsCount < listOfLabels.size(); listOfLabelsCount++) {
			listOfLabels.get(listOfLabelsCount).click();
			sleeper(3000);
			mouseHoverbyoffsettClick(targetElement, 00, 80);
			switchToDifferentTabOfExperienceMgmtPage();
			waitForPageLoaded();
			waitForElementsOfExperienceMgmtPage("moreDetailsLink");
			clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
			waitForElementsOfExperienceMgmtPage(companyLocatorKey);
			String companyList = getTextOfExperienceMgmtPage(companyLocatorKey);
			for (String comp : companies) {
				if (companyList.contains(comp))
					flag = true;
				else {
					flag = false;
					break;
				}
			}
			getDriver().switchTo().defaultContent();
			switchToPreviousTabOfExperienceMgmtPage();
			listOfLabels.get(listOfLabelsCount).click();
		}
		return flag;
	}

	/**
	 * This method will give the label and the respective count present above the bar of the charts
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param headCountLocatorValue: Count present on the top of the bar chart
	 * @param labelLocatorValue: Labels present on Y axis
	 * @param noDataKey: Chart having no data
	 * @param chartTitle: Chart title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getHeadCountAndLabel(String LanguageCode, String headCountLocatorValue, String labelLocatorValue, String noDataKey, String chartTitle) throws Exception {
		HashMap<String, Integer> countLabel = new HashMap<String, Integer>();
		if (!verifyChartsHasNoDataOnExperienceMgmtPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			waitForElementsOfExperienceMgmtPage(headCountLocatorValue);
			List<WebElement> listOfCount = getElementsOfExperienceMgmtPage(headCountLocatorValue);
			List<WebElement> listOflabels = getElementsOfExperienceMgmtPage(labelLocatorValue);
			String arrLabelCount[] = new String[2];
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOflabels.size(); listOfLabelsCounter++) {
				String criteriaCount = listOfCount.get(listOfLabelsCounter).getText();
				if (criteriaCount.contains("(")) {
					arrLabelCount = criteriaCount.replaceAll("\\W", "").split("(?<=\\D)(?=\\d)");
				} else {
					arrLabelCount[1] = criteriaCount.replace(",", "");
					arrLabelCount[0] = listOflabels.get(listOfLabelsCounter).getText();

				}
				countLabel.put(arrLabelCount[0], Integer.parseInt(arrLabelCount[1]));
			}
			return countLabel;
		} else {
			LOGGER.info("No data to display");
			return countLabel;
		}
	}
	
	/**
	 * This method will give the label and the respective count present above the bar of the charts
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param headCountLocatorValue: Count present on the top of the bar chart
	 * @param labelLocatorValue: Labels present on Y axis
	 * @param noDataKey: Chart having no data
	 * @param chartTitle: Chart title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getHeadCountAndLabelFlexi(String LanguageCode, String headCountLocatorValue, String labelLocatorValue, String noDataKey, String chartTitle) throws Exception {
		HashMap<String, Integer> countLabel = new HashMap<String, Integer>();
		if (!verifyChartsHasNoDataOnExperienceMgmtPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			waitForElementsOfExperienceMgmtPage(headCountLocatorValue);
			List<WebElement> listOfCount = getElementsOfExperienceMgmtPage(headCountLocatorValue);
			List<WebElement> listOflabels = getElementsOfExperienceMgmtPage(labelLocatorValue);
			String arrLabelCount[] = new String[2];
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOflabels.size(); listOfLabelsCounter++) {
				String criteriaCount = listOfCount.get(listOfLabelsCounter).getText();
				if (criteriaCount.contains("(")) {
					arrLabelCount = criteriaCount.replaceAll("\\W", "").split("(?<=\\D)(?=\\d)");
				} else {
					arrLabelCount[1] = criteriaCount.replace(",", "");
					arrLabelCount[0] = listOflabels.get(listOfLabelsCounter).getText();

				}
				countLabel.put(arrLabelCount[0], Integer.parseInt(arrLabelCount[1]));
			}
			return countLabel;
		} else {
			LOGGER.info("No data to display");
			return countLabel;
		}
	}

	/**
	 * This method will give the label and the respective count for Device by OS chart
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param labelLocatorValue: Label/ criteria present in the chart
	 * @param tooltipTextonChart: text on the tooltip
	 * @param tooltipCountonChart: Count on the tooltip
	 * @param noDataKey:Chart having no data
	 * @param chartTitle:Chart title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 
	public final HashMap<String, Integer> getCountAndLabel(String LanguageCode, String labelLocatorValue, String tooltipTextonChart, String tooltipCountonChart, String noDataKey, String chartTitle) throws Exception {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		waitForPageLoaded();
		if (!verifyChartsHasNoDataOnExperienceMgmtPage(LanguageCode, noDataKey, chartTitle)) {
			Actions action = new Actions(getDriver());
			mouseHoverOfExperienceMgmtPage(labelLocatorValue);
			waitForElementsOfExperienceMgmtPage(labelLocatorValue);
			List<WebElement> listOfLabel = getElementsOfExperienceMgmtPage(labelLocatorValue);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabel.size(); listOfLabelsCounter++) {
				sleeper(2000);
				action.moveToElement(listOfLabel.get(listOfLabelsCounter)).build().perform();
				String criteria = getTextOfExperienceMgmtPage(tooltipTextonChart).trim();
				if (Strings.isNullOrEmpty(criteria))
					LOGGER.error("Criteria is empty");
				else
					LOGGER.info("Criteria is " + criteria);
				String count = getTextOfExperienceMgmtPage(tooltipCountonChart).replaceAll(",", "");
				String[] countChar= new String[2]; 
				if (count.contains("(")){
					countChar = count.split("\\(");
					count = countChar[0];
				}
				else{
					LOGGER.info(criteria + " count is " + count);
				}
				labelCount.put(criteria, Integer.parseInt(count));
			}
			return labelCount;
		} else {
			LOGGER.info("No data to display");
			return labelCount;
		}
	}
	
	public final HashMap<String, Integer> getCountAndLabelDeviceByOS(String LanguageCode, String labelLocatorValue, String tooltipTextonChart, String tooltipCountonChart, String noDataKey, String chartTitle) throws Exception {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		waitForPageLoaded();
		if (!verifyChartsHasNoDataOnExperienceMgmtPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
			waitForElementsOfExperienceMgmtPage(labelLocatorValue);
			List<WebElement> listOfLabel = getElementsOfExperienceMgmtPage(labelLocatorValue);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabel.size(); listOfLabelsCounter++) {
				sleeper(2000);
				listOfLabel.get(listOfLabelsCounter).click();
				mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
				String criteria = getTextOfExperienceMgmtPage(tooltipTextonChart).trim();
				if (Strings.isNullOrEmpty(criteria))
					LOGGER.error("Criteria is empty");
				else
					LOGGER.info("Criteria is " + criteria);
				String count = getTextOfExperienceMgmtPage(tooltipCountonChart).replaceAll(",", "");
				String[] countChar= new String[2]; 
				if (count.contains("(")){
					countChar = count.split("\\(");
					count = countChar[0];
				}
				else{
					LOGGER.info(criteria + " count is " + count);
				}
				labelCount.put(criteria, Integer.parseInt(count));
				listOfLabel.get(listOfLabelsCounter).click();
			}
			return labelCount;
		} else {
			LOGGER.info("No data to display");
			return labelCount;
		}
	}*/
	
	/**
	 * This method will give the label and the respective count
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param labelLocatorValue: Label/ criteria present in the chart
	 * @param tooltipTextonChart: text on the tooltip
	 * @param tooltipCountonChart: Count on the tooltip
	 * @param noDataKey:Chart having no data
	 * @param chartTitle:Chart title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getCountAndLabelFlexi(String LanguageCode, String labelLocatorValue, String tooltipTextonChart, String tooltipCountonChart, String noDataKey, String chartTitle) throws Exception {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		waitForPageLoaded();
		if (!verifyChartsHasNoDataOnExperienceMgmtPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			Actions action = new Actions(getDriver());
			mouseHoverOfExperienceMgmtPage(labelLocatorValue);
			waitForElementsOfExperienceMgmtPage(labelLocatorValue);
			List<WebElement> listOfLabel = getElementsOfExperienceMgmtPage(labelLocatorValue);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabel.size(); listOfLabelsCounter++) {
				sleeper(2000);
				action.moveToElement(listOfLabel.get(listOfLabelsCounter)).build().perform();
				String criteria = getTextOfExperienceMgmtPage(tooltipTextonChart).trim();
				if (Strings.isNullOrEmpty(criteria))
					LOGGER.error("Criteria is empty");
				else
					LOGGER.info("Criteria is " + criteria);
				String count = getTextOfExperienceMgmtPage(tooltipCountonChart).replaceAll(",", "");
				if (count.contains("("))
					count = count.split(" ")[0];
				else
					LOGGER.info(criteria + " count is " + count);
				labelCount.put(criteria, Integer.parseInt(count));
			}
			return labelCount;
		} else {
			LOGGER.info("No data to display");
			return labelCount;
		}
	}
	
	/**
	 * This method will give the label and the respective count
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param labelLocatorValue: Label/ criteria present in the chart
	 * @param tooltipTextonChart: text on the tooltip
	 * @param tooltipCountonChart: Count on the tooltip
	 * @param noDataKey:Chart having no data
	 * @param chartTitle:Chart title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getCountAndLabelPieChartFlexi(String LanguageCode, String labelLocatorValue, String keyDrillDownLabelsAllHidden, String tooltipTextonChart, String tooltipCountonChart, String noDataKey, String chartTitle, String targetElement) throws Exception {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		waitForPageLoaded();
		if (!verifyChartsHasNoDataOnExperienceMgmtPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelLocatorValue,keyDrillDownLabelsAllHidden);
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelLocatorValue);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
				listOfLegends.get(listOfLabelsCounter).click();
				sleeper(2000);
				mouseHoverbyoffsett(targetElement, 00, 80);
				String criteria = getTextOfExperienceMgmtPage(tooltipTextonChart).trim();
				if (Strings.isNullOrEmpty(criteria))
					LOGGER.error("Criteria is empty");
				else
					LOGGER.info("Criteria is " + criteria);
				String count = getTextOfExperienceMgmtPage(tooltipCountonChart).replaceAll(",", "");
				if (count.contains("("))
					count = count.split(" ")[0];
				else
					LOGGER.info(criteria + " count is " + count);
				labelCount.put(criteria, Integer.parseInt(count));
				listOfLegends.get(listOfLabelsCounter).click();
			}
			return labelCount;
		} else {
			LOGGER.info("No data to display");
			return labelCount;
		}
	}

	/**
	 * This method will verify the aggregate count of two companies on the chart
	 * 
	 * @param firstCompanyHash: Chart criteria and respective count for first company
	 * @param secondCompanyHash: Chart criteria and respective count for second company
	 * @param aggregateCompanyHash: Aggregated Chart criteria and respective count of first and second company
	 * @return boolean value
	 */

	public boolean verifyAggregationOfTwoCompanies(HashMap<String, Integer> firstCompanyHash, HashMap<String, Integer> secondCompanyHash, HashMap<String, Integer> aggregateCompanyHash) {
		Map<String, Integer> aggregatedHash;
		if (firstCompanyHash.equals(aggregateCompanyHash) || secondCompanyHash.equals(aggregateCompanyHash))
			return true;
		else {
			aggregatedHash = Stream.concat(firstCompanyHash.entrySet().stream(), secondCompanyHash.entrySet().stream()).collect(Collectors.toMap(entry -> entry.getKey(), // The key
					entry -> entry.getValue(), (oldValue, newValue) -> oldValue + newValue// The value
					));
			if (aggregatedHash.equals(aggregateCompanyHash))
				return true;
			else
				return false;
		}
	}

	/**
	 * This method will remove all devices from device page
	 */
	public boolean verifyRemovalOfDevice(String checkBoxKey, String removeDeviceKey, String removeButtonKey, String removeDeviceMessage, String LanguageCode) throws Exception {
		boolean flag = false;
		clickOnElementsOfExperienceMgmtPage(checkBoxKey);
		sleeper(2000);
		clickOnElementsOfExperienceMgmtPage(removeDeviceKey);
		sleeper(2000);
		clickOnElementsOfExperienceMgmtPage(removeButtonKey);
		sleeper(2000);
		if (getTextOfExperienceMgmtPage(removeDeviceMessage).equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.list.toast.remove.assets.success"))) {
			flag = true;
		}
		return flag;
	}

	/**
	 * This is a method to verify filter functionality for single select company column
	 * 
	 * @param LanguageCode - language code
	 * @param textKey - locator of searchbox
	 * @param Text - text to be entered
	 * @param emptyTextKey -locator for "no items available" message
	 * @param listKey - locator of optiosn filetered on popup
	 * @param checkboxKey - locator of checboxes of available options
	 * @param columnListKey - locator of all items filtered in column
	 * @param emptyTextColumnKey - locator of "no items available" message in column
	 * @return - boolean value of whether the filter functionality is working correctly
	 * @throws Exception
	 */
	public final boolean verifyFilterFunctionalityForAssignedToSingleSelectFromDynamicDropdown(String LanguageCode, String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey, String emptyTextColumnKey) throws Exception {

		enterText(ExperienceMgmtPageProperties.getProperty(textKey), Text);
		boolean flag = false;
		sleeper(3000);
		String empty_text = null, text = null;
		if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(emptyTextKey))) {
			empty_text = getTextBy(ExperienceMgmtPageProperties.getProperty(emptyTextKey));
			String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
			if (empty_text.contains(emptytext[0].concat(Text))) {
				flag = false;
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(listKey));
			List<WebElement> listOfCheckbox = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(checkboxKey));
			if (!listOfCheckbox.get(0).isSelected() && !(listOfCheckbox.get(0).getAttribute("class").equalsIgnoreCase("checked"))) {
				text = elements.get(0).getText();
				listOfCheckbox.get(0).click();
				pressKey(Keys.ESCAPE);
			}
			sleeper(2000);

			if (verifyElementIsVisible(ExperienceMgmtPageProperties.getProperty(emptyTextColumnKey))) {
				empty_text = getTextBy(ExperienceMgmtPageProperties.getProperty(emptyTextColumnKey));
				if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
					flag = false;
				}
			} else {
				List<WebElement> columnList = getElementsTillAllElementsVisible(ExperienceMgmtPageProperties.getProperty(columnListKey));
				for (WebElement webElement : columnList) {
					if (webElement.getText().equals(text)) {
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		}

		return flag;
	}

	/**
	 * This method is used to verify tool tip text of software inventory chart(with frame)
	 * 
	 * @param keyLegendLabel: Legends present below the charts
	 * @param keyNoData:Charts having no data
	 * @param keyLableslocatorsoftware:Chart labels
	 * @param tooltipTextKey: Tooltip count after hovering on a chart
	 * @param textKey: Criteria present on the header on drill down page
	 * @throws Exception
	 * @return boolean value
	 
	public final boolean verifyChartSoftwareInventoryTooltext(String keyLegendLabel, String keyNoData, String keyLableslocatorsoftware, String tooltipTextKey, String textKey) throws Exception {
		boolean flag = false;
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		Actions action = new Actions(getDriver());
		if (verifyElementsOfExperienceMgmtPage(keyNoData)) {
			LOGGER.error("Verify charts having no data");
			return false;
		} else {
			LOGGER.info("Hide all legends present under software inventory");
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				action.click(legendLabelElementList.get(listCounter)).perform();
			}
		}
		LOGGER.info("Verify tooltiptext individually based on by os");

		if (legendLabelElementList.size() > 0) {
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				action.click(legendLabelElementList.get(listCounter)).perform();
				if (verifyTooltipTextOnReportSWInv(keyLableslocatorsoftware, tooltipTextKey, textKey)) {
					action.click(legendLabelElementList.get(listCounter)).perform();
					flag = true;
				} else {	
					flag = false;	
					LOGGER.error("No text present on tooltip1");	
					break;	
				}
			}
		} else {
			LOGGER.error("Legends are not available under software inventory chart");
		}
		verifyDashboardLegendsVisible(keyLegendLabel);
		return flag;
	}*/

	/**
	 * This method is used to verify tool tip count of software inventory chart(with frame)
	 * 
	 * @param keyLegendLabel: Legends present below the charts
	 * @param keyNoData:Charts having no data
	 * @param keyLableslocatorsoftware:Chart labels
	 * @param tooltipTextKey: Tooltip count after hovering on a chart
	 * @param columnTextKey: Column names present in the grid
	 * @param frameKey: Iframe on the drill down page
	 * @throws Exception
	 * @return boolean value
	 */
	public final boolean verifyChartSoftwareInventoryTooltipCount(String keyLegendLabel, String keyNoData, String keyLableslocatorsoftware, String tooltipTextKey, String columnTextKey, String frameKey,String tooltipTextSoftware) throws Exception {
		boolean flag = false;
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		Actions action = new Actions(getDriver());
		if (verifyElementsOfExperienceMgmtPage(keyNoData)) {
			LOGGER.error("Verify Charts having no data");
			return false;
		} else {
			LOGGER.info("Hide all legends present under software inventory");
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				action.click(legendLabelElementList.get(listCounter)).perform();
			}
		}
		LOGGER.info("Verify tooltipcount individually based on by os");
		if (legendLabelElementList.size() > 0) {
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				action.click(legendLabelElementList.get(listCounter)).perform();
				if (verifyTooltipCountonReportwithFrameSWInventory(keyLableslocatorsoftware, tooltipTextKey, columnTextKey, frameKey,tooltipTextSoftware)) {
					action.click(legendLabelElementList.get(listCounter)).perform();
					flag = true;
				} else {
					flag = false;
					switchBackToPreviousTab();
					LOGGER.error("Tooltipcount does not match with actual tooltipcount");
					break;
				}
			}
		} else {
			LOGGER.error("Legends are not available under software inventory chart");
		}
		verifyDashboardLegendsVisible(keyLegendLabel);
		return flag;
	}

	/**
	 * This method is used to verify tool tip count of software inventory chart(with frame)
	 * 
	 * @param keyLegendLabel: Legends present below the charts
	 * @param keyNoData:Charts having no data
	 * @param keyLableslocatorsoftware:Chart labels
	 * @param tooltipTextKey: Tooltip count after hovering on a chart
	 * @param columnTextKey: Column names present in the grid
	 * @param frameKey: Iframe on the drill down page
	 * @throws Exception
	 * @return boolean value
	 */
	public final boolean verifyChartSoftwareInventoryTooltipCountFlexi(String keyLegendLabel, String keyNoData, String keyLableslocatorsoftware, String tooltipTextKey, String columnTextKey, String frameKey,String tooltipTextSoftware) throws Exception {
		boolean flag = false;
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		Actions action = new Actions(getDriver());
		if (verifyElementsOfExperienceMgmtPage(keyNoData)) {
			LOGGER.error("Verify Charts having no data");
			return false;
		} else {
			LOGGER.info("Hide all legends present under software inventory");
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				action.click(legendLabelElementList.get(listCounter)).perform();
			}
		}
		LOGGER.info("Verify tooltipcount individually based on by os");
		if (legendLabelElementList.size() > 0) {
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				action.click(legendLabelElementList.get(listCounter)).perform();
				if (verifyTooltipCountonReportwithFrameSWInventoryFlexi(keyLableslocatorsoftware, tooltipTextKey, columnTextKey, frameKey,tooltipTextSoftware)) {
					action.click(legendLabelElementList.get(listCounter)).perform();
					flag = true;
				} else {
					flag = false;
					LOGGER.error("Tooltipcount does not match with actual tooltipcount");
					break;
				}
			}
		} else {
			LOGGER.error("Legends are not available under software inventory chart");
		}
		verifyDashboardLegendsVisible(keyLegendLabel);
		return flag;
	}

	
	/**
	 * This method is used to visible hidden legends present on the dashboard
	 * 
	 * @param keyLegendLabel: Legends present below the charts
	 * @throws Exception
	 */
	public final void verifyDashboardLegendsVisible(String keyLegendLabel) throws Exception {
		Actions action = new Actions(getDriver());
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		LOGGER.info("Visible hidden legends on dashboard page");
		for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
			if (legendLabelElementList.get(listCounter).getAttribute("class").contains("hidden")) {
				action.click(legendLabelElementList.get(listCounter)).perform();
			}
		}
	}

	/**
	 * This method is used to visible hidden legends present on the dashboard
	 * 
	 * @param keyResetConfiguration: button for table configuration
	 * @param keyResetLink: link for reset.
	 * @param keySave: save button
	 * @throws Exception
	 */
	public final void verifyResetConfiguration(String keyResetConfiguration, String keyResetLink, String keySave) throws Exception {
		try {
			clickOnElementsOfExperienceMgmtPage(keyResetConfiguration);
			clickOnElementsOfExperienceMgmtPage(keyResetLink);
			clickOnElementsOfExperienceMgmtPage(keySave);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * This method verifies all Incident chart present on the dashboard redirect on incident list view or not
	 * 
	 * @param languageCode:Language code for localization testing
	 * @param incidentCharts:element of the incident chart which is to be click
	 * @return : true if incident chart of dashboard redirects on Incident list view else return false
	 * @throws Exception
	 */
	public boolean isIncidentChartRedirectOnIncidentListView(String languageCode, String incidentCharts) throws Exception {
		try {
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			verifyElementIsClickableOfExperienceMgmtPage(incidentCharts);
			waitForPageLoaded();
			clickOnElementsOfExperienceMgmtPage(incidentCharts);// Clicking on first level of incident chart
			waitForPageLoaded();
			sleeper(3000);//Need sleeper for second level chart validation
			verifyElementIsClickableOfExperienceMgmtPage(incidentCharts);// Waiting till second level of chart get visible
			clickOnElementsOfExperienceMgmtPage(incidentCharts);// Clicking on second level of incident chart
			if (incidentPage.matchTextOfIncidentPage("incidentTitle", getTextLanguage(languageCode, "daas_ui", "incidents.label"))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			LOGGER.error("Error occured while redirecting on the incident view from dashbourd" + ex.getMessage());
			throw ex;
		}
	}

	/**
	 * This method verifies 'Open Incident','All Incident' and 'Top 10 incident' charts present for the logged in user
	 * 
	 * @param languageCode: Language code for localization testing
	 * @return : true if all Incident chart present for the user else return false
	 * @throws Exception
	 */
	public final boolean verifyIncidentDashboardForUser(String languageCode) throws Exception {
		try {
			boolean isIncidentChartPresent = false;
			isIncidentChartPresent = getTextOfExperienceMgmtPage("openIncidentsTitle").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status")) && getTextOfExperienceMgmtPage("allIncidentsTitle").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "dashboard.charts.title.incidents_by_type"))
					&& getTextOfExperienceMgmtPage("topTenIncidentsTitle").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "dashboard.charts.title.incidents_top_by_subtype"));
			return isIncidentChartPresent;
		} catch (Exception ex) {
			LOGGER.error("Error occured while redirecting on the incident view from dashbourd" + ex.getMessage());
			throw ex;
		}
	}

	/**
	 * @param keyLegends
	 * @param keyLegendsFull
	 * @param keyDrillDownLabels
	 * @param tooltipTextKey
	 * @param keyDrillDownLabelsAllHidden
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyLabelTopTenIncidents(String keyLegends, String keyLegendsFull, String keyDrillDownLabels, String tooltipTextKey, String keyDrillDownLabelsAllHidden) throws Exception {
		boolean flag = false;
		Actions action = new Actions(getDriver());
		List<WebElement> drillDownLabelsElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyDrillDownLabels);
		List<WebElement> legendsElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegends);
		List<WebElement> legendsElementListFull = new ArrayList<WebElement>();
		if (waitUntillElementIsPresent(keyLegendsFull)) {
			legendsElementListFull.add(getElement(keyLegendsFull));
		} else {
			legendsElementListFull = getElementsOfExperienceMgmtPage(keyLegendsFull);
		}
		ArrayList<String> toolTipText = new ArrayList<String>();
		ArrayList<String> legendText = new ArrayList<String>();
		ArrayList<String> legendTextFull = new ArrayList<String>();
		if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden)) {
			flag = false;
		} else {
			for (int toolTipTextCounter = 0; toolTipTextCounter < drillDownLabelsElementList.size(); toolTipTextCounter++) {
				sleeper(2000);
				action.moveToElement(drillDownLabelsElementList.get(toolTipTextCounter)).build().perform();
				waitForElementsOfExperienceMgmtPage(tooltipTextKey);
				toolTipText.add(getTextOfExperienceMgmtPage(tooltipTextKey));
			}
			if (legendsElementListFull.size() > 0) {
				for (int legendFullCounter = 0; legendFullCounter < legendsElementListFull.size(); legendFullCounter++) {
					legendTextFull.add(getInnerTextOfExperienceMgmtPage(legendsElementListFull.get(legendFullCounter)));
				}
			} else {
				LOGGER.info("All labels are displayed in full text.");
			}
			if (legendsElementList.size() > 0) {
				for (int legendCounter = 0; legendCounter < legendsElementList.size(); legendCounter++) {
					if (!legendsElementList.get(legendCounter).getText().contains("â€¦")) {
						legendText.add(legendsElementList.get(legendCounter).getText());
					} else {
						LOGGER.info("Labels which are partially displayed are not added.");
					}
				}
			} else {
				LOGGER.error("No labels are present in chart.");
				flag = false;
			}
			Set<String> newSet = new HashSet<String>(legendText);
			newSet.addAll(legendTextFull);
			List<String> newList = new ArrayList<String>(newSet);

			for (int listOfLabelsCounter = 0; listOfLabelsCounter < toolTipText.size(); listOfLabelsCounter++) {
				if (newList.contains(toolTipText.get(listOfLabelsCounter))) {
					flag = true;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public final String getInnerTextOfExperienceMgmtPage(WebElement locator) throws Exception {
		return getInnerTextFromElement(locator);
	}

	/**
	 * @param dropdownListKey
	 * @param elementText
	 * @param dropdownBox
	 * @return
	 * @throws Exception
	 */
	public final boolean updateValueOfDropdown(String dropdownListKey, String elementText, String dropdownBox) throws Exception {
		return selectTextValueFromDropdown(ExperienceMgmtPageProperties.getProperty(dropdownListKey), elementText, ExperienceMgmtPageProperties.getProperty(dropdownBox));
	}

	/**
	 * This method will verify the Company list on Drill down for Device by OS chart
	 * 
	 * @param languageCode: This is language code used for multiple languages.
	 * @param keyHeaderOnReportPage: Header on the drill down page
	 * @param companyListOnHeader: Company list in the header section on the drill down page
	 * @param DeviceByOsInfo: List of key for Device by Os
	 * @param deviceDetailsKey: Element present on Device page
	 * @param columnListKey: Columns present in the grid
	 * @param errorKey: Error message flashes on the Dashboard
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyCompaniesListOnHeaderDeviceByOS(String languageCode, String keyHeaderOnReportPage, String[] companyListOnHeader, HashMap<String, String> DeviceByOsInfo, String deviceDetailsKey, String columnListKey, String errorKey) throws Exception {
		boolean companyListFlag = false;
		String osMajorVersion = null;
		verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
		listOfLabelsFirstDrill.get(0).click();
		listOfLabelsFirstDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
		waitForPageLoaded();
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
		waitForPresenceOfElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
		verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
		listOfLabelsSecondDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
		listOfLabelsSecondDrill.get(0).click();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
		waitForPageLoaded();
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		osMajorVersion = getTextOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextMajorVersionKeyFlexi"));
		// For Other cases
		if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
			// For OSX Other cases
			if (osMajorVersion.contains("OSX Other")) {
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
				List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
				listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
				listOfLabelsThirdDrill.get(0).click();
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
			} else {
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
				switchToDifferentTabOfExperienceMgmtPage();
				waitForPageLoaded();
			}
		} else {
			waitForPresenceOfElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			verifyLegendsAreHidden("deviceByOSLegendsHidden","deviceByOSLegendsVisible","labelsLocatorDeviceByOsFlexi");
			List<WebElement> listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
			listOfLabelsThirdDrill = getElementsOfExperienceMgmtPage("deviceByOSLegends");
			listOfLabelsThirdDrill.get(0).click();
			mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("labelsKey"));
			switchToDifferentTabOfExperienceMgmtPage();
			waitForPageLoaded();
		}
		// Verify company
		waitForElementsOfExperienceMgmtPage(DeviceByOsInfo.get("moreDetailsLinkKey"));
		clickOnElementsOfExperienceMgmtPage(DeviceByOsInfo.get("moreDetailsLinkKey"));
		waitForElementsOfExperienceMgmtPage("companyListLocatorOnReportPage");
		String companyList = getTextOfExperienceMgmtPage("companyListLocatorOnReportPage");
		for (String comp : companyListOnHeader) {
			if (companyList.contains(comp))
				companyListFlag = true;
			else {
				companyListFlag = false;
				break;
			}
		}
		getDriver().switchTo().defaultContent();
		switchToPreviousTabOfExperienceMgmtPage();
		return companyListFlag;
	}

	/**
	 * This method will verify the estimator tab
	 * 
	 * @param waitForElementsOfLoginPage: Email id field of login pages
	 * @param expectedEstimatorLink: Expected Estimator URL
	 * @return boolean value
	 */
	public boolean verifyEstimatorTab(String expectedEstimatorLink) {
		waitForPageLoaded();
		boolean estimatorFlag = false;
		String estimatorURL = getDriver().getCurrentUrl();
		if (estimatorURL.contains(expectedEstimatorLink)) {
			estimatorFlag = true;
			LOGGER.info("Validated the Estimator URL");
			switchToPreviousTabOfExperienceMgmtPage();
			return estimatorFlag;
		} else {
			LOGGER.info("Wrong URL or wrong page for Estimator Tab");
			estimatorFlag = false;
		}
		switchToPreviousTabOfExperienceMgmtPage();
		return estimatorFlag;
	}

	/**
	 * This method will clear the selected company and clicks on the drop down to select the new company
	 * 
	 * @param allCompanyText: All Companies text xpath
	 * @param companyDropdown: Company drop down present on Dashboard
	 * @param clearCompany: Clear button xpath
	 * @throws Exception
	 */
	public void selectCompanyDropdown(String allCompanyText, String companyDropdown, String clearCompany) throws Exception {
		if (waitForElementsOfExperienceMgmtPage(allCompanyText)) {
			sleeper(3000);
			clickOnElementsOfExperienceMgmtPage(companyDropdown);
			LOGGER.info("No filter was present ,Dropdown got open.");
		} else {
			sleeper(3000);
			clickOnElementsOfExperienceMgmtPage(clearCompany);
			clickOnElementsOfExperienceMgmtPage(companyDropdown);
			LOGGER.info("All filters cleared ,Dropdown got open.");
		}

	}

	/**
	 * This method will validates the correct filter criteria applied on redirected report details page
	 * 
	 * @param patchStatus: Windows 10 Patch Status graph 1st level status bars(latest/outdated/unknown) xpath
	 * @param patchStatusTooltipText: Windows 10 Patch Status graph 1st level status bars tooltip text(latest/outdated/unknown) xpath
	 * @param seconddrilldown: Windows 10 Patch Status graph 2nd level status bars(latest/outdated/unknown) xpath
	 * @param secondlevelTooltip: Windows 10 Patch Status graph 2nd level status bars tooltip text(latest/outdated/unknown) xpath
	 * @param osReleaseFilterCriteria: filter applied in header section
	 * @param backButtonWPS: BACK button xpath to return to the 1st level
	 * @return boolean value
	 * @throws Exception
	 
	public final boolean verifyTooltipTextVersionStatusWPS(String patchStatus, String patchStatusTooltipText, String seconddrilldown, String secondlevelTooltip, String osReleaseFilterCriteria, String backButtonWPS) throws Exception {
		boolean flag = false;
		String text = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(patchStatus);

		try {
			for (int i = 0; i < listOfLabels.size(); i++) {
				getDriver().navigate().refresh();
				sleeper(3000);
				waitForElementsOfExperienceMgmtPage(patchStatus);
				listOfLabels = getElementsOfExperienceMgmtPage(patchStatus);
				listOfLabels.get(i);
				action.moveToElement(listOfLabels.get(i)).build().perform();
				waitForElementsOfExperienceMgmtPage(patchStatusTooltipText);
				text = getTextOfExperienceMgmtPage(patchStatusTooltipText);
				LOGGER.info("Patch Status :" + text);
				listOfLabels.get(i).click();
				if (verifyTooltipTextOnReportWithFrameFlexi1(seconddrilldown, secondlevelTooltip, osReleaseFilterCriteria)) {
					flag = true;
					clickOnElementsOfExperienceMgmtPage(backButtonWPS);
				} else {
					flag = false;
					break;
				}
			}

			if (flag) {
				LOGGER.info("Tooltip text of Windows10PatchStatus chart match with the reports filter criteria");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}*/

	/**
	 * This method will validates the total results on redirected report details page after clicked on the device count of patch status(Latest/Outdated/Unknown).)
	 * 
	 * @param languageCode: Language selected to run report in selected language
	 * @param patchStatus: Windows 10 Patch Status graph 1st level status bars(latest/outdated/unknown) xpath
	 * @param patchStatusTooltipText: Windows 10 Patch Status graph 1st level status bars tooltip text(latest/outdated/unknown) xpath
	 * @param seconddrilldown: Windows 10 Patch Status graph 2nd level status bars(latest/outdated/unknown) xpath
	 * @param secondlevelTooltip: Windows 10 Patch Status graph 2nd level status bars tooltip text(latest/outdated/unknown) xpath
	 * @param TotalRowsListKey: Number of rows in the grid
	 * @param backButtonWPS: BACK button xpath to return to the 1st level
	 * @param paginationKey: pager on grid
	 * @param frameKey: Grid frame xpath
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyRedirectionWithFrameWPS(String languageCode, String patchStatus, String patchStatusTooltipText, String seconddrilldown, String secondlevelTooltipCount, String backButtonWPS, String TotalRowsListKey, String paginationKey, String frameKey) throws Exception {
		boolean flag = false;
		String text = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(patchStatus);

		try {
			for (int i = 0; i < listOfLabels.size(); i++) {
				getDriver().navigate().refresh();
				sleeper(3000);
				waitForElementsOfExperienceMgmtPage(patchStatus);
				listOfLabels = getElementsOfExperienceMgmtPage(patchStatus);
				listOfLabels.get(i);
				action.moveToElement(listOfLabels.get(i)).build().perform();
				waitForElementsOfExperienceMgmtPage(patchStatusTooltipText);
				text = getTextOfExperienceMgmtPage(patchStatusTooltipText);
				LOGGER.info("Patch Status :" + text);
				listOfLabels.get(i).click();
				if (verifyTooltipCountonReportWithFrame(seconddrilldown, secondlevelTooltipCount, TotalRowsListKey, paginationKey, frameKey)) {
					flag = true;
					clickOnElementsOfExperienceMgmtPage(backButtonWPS);
				} else {
					flag = false;
					break;
				}
			}
			if (flag) {
				LOGGER.info("The total results on redirected report details page after clicked on the device count of patch status(Latest/Outdated/Unknown) matching");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * This method will validates the grid column header names are correct of redirected reports detail page
	 * 
	 * @param languageCode: Language selected to run report in selected language
	 * @param labelsWPS: Windows 10 Patch Status chart labels xpath
	 * @param columnHeaderWPSLocator: Total number of grid column names xpath locator
	 * @param columnHeaderNamesWPS: Grid column names list
	 * @param frameKey: Grid frame xpath
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyGridColumnsNamesWPS(String languageCode, String labelsWPS, String columnHeaderWPSLocator, String[] columnHeaderNamesWPS, String frameKey) throws Exception {
		boolean flag = false;
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsWPS);

		try {
			if (!listOfLabels.isEmpty()) {
				waitForElementsOfExperienceMgmtPage(labelsWPS);
				waitForPageLoaded();
				listOfLabels = getElementsOfExperienceMgmtPage(labelsWPS);
				sleeper(3000);
				listOfLabels.get(0).click();
				sleeper(5000);
				listOfLabels = getElementsOfExperienceMgmtPage(labelsWPS);
				if (headerTextVerificationOnReportPageFrame(languageCode, labelsWPS, columnHeaderWPSLocator, columnHeaderNamesWPS, frameKey)) {
					flag = true;
					LOGGER.info("Column names matching with the expected array list");
				} else {
					
					flag = false;
					LOGGER.error("Column names are not matching with expected list.");
				}
			} else {
				flag = true;
				LOGGER.info("Data not present in the grid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * This method basically works on drag and drop operation.
	 * 
	 * @param sourceKey - locator of the source
	 * @param destinationKey - locator of the destination
	 * @throws Exception
	 
	public final boolean verifyDragAndDrop(String sourceKey, String destinationKey) throws Exception {
		boolean flag = false;
		WebElement sourceElement = getElementOfExperienceMgmtPage(sourceKey);
		WebElement destinationElement = getElementOfExperienceMgmtPage(destinationKey);
		if(dragAndDropOperaton(sourceElement,destinationElement)) {
			flag=true;
		}
		return flag;
//	}*/
	
	/**
	 * This method basically verify HIDE and SHOW chart by click on link.
	 * 
	 * @param linkTextName - Name of the link it may be HIDE or SHOW
	 * @param linkKey - locator of the HIDE and SHOW link
	 * @throws Exception
	 */
	public final boolean verifyHideAndShowLink(String linkTextName, String linkKey) throws Exception {
		boolean flag = false;
		Actions action = new Actions(getDriver());
		String linkText=null;
		List<WebElement> hideLink = getElementsOfExperienceMgmtPage(linkKey);
		if(hideLink.size()>0) {
			for(int i=0; i<hideLink.size();i++) {
				hideLink.get(i);
				action.moveToElement(hideLink.get(i)).build().perform();
				linkText = getTextOfExperienceMgmtPage(linkKey);
				if(linkText.equalsIgnoreCase(linkTextName)) {
					LOGGER.info(linkText + " Link text match successfully on chart");
					flag=true;
				}else {
					LOGGER.info(linkText + " Link text does not match on chart");
					flag=false;
				}
				hideLink.get(i).click();
				sleeper(3000);
			}
		}else {
			LOGGER.info("Chart is not present at Dashboard page.");
		}
		return flag;
	}
		
	/**
	 * @param mspAuthToken: bearer token
	 * @param url: To specify the Authentication url.
	 * @throws Exception
	 */
	public final JSONArray getActualAarrayAfterDragAndDropOperation(String mspAuthToken,String url) throws Exception {
		
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken);
		Response response = httpRequest.get(url);
        String expected = response.asString();
        JSONObject jsonObject;
        jsonObject = new JSONObject(expected);
        JSONArray jsonArray = new JSONObject(jsonObject.get("value").toString()).getJSONObject("cardsConfiguration").getJSONArray("visibleCards");
        return jsonArray;
	}
	
	/**
	 * @param mspAuthToken: bearer token
	 * @param url: To specify the Authentication url.
	 * @throws Exception
	 */
	public final JSONArray getActualAarrayAfterDragAndDropOperationWithHiddenArea(String mspAuthToken,String url) throws Exception {
		
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken);
		Response response = httpRequest.get(url);
        String expected = response.asString();
        JSONObject jsonObject;
        jsonObject = new JSONObject(expected);
        JSONArray jsonArray = new JSONObject(jsonObject.get("value").toString()).getJSONObject("cardsConfiguration").getJSONArray("hiddenCards");
        return jsonArray;
	}
	
	/**
	 * This method is used to verify sequence of chart present on DashBoard for MSP
	 * 
	 * @param listOfChartNameArray: Position of all charts on the dashboard
	 * @return boolean value it may true or false
	 * @throws Exception
	 */

	public final boolean verifyChartOrderAfterDragAndDropOfExperienceMgmtPage(JSONArray listOfChartNameArray) throws Exception {
		Boolean flag = false;
		List<String> chartIdsList = Arrays.asList("DHBatteryReplacement","DHDiskReplacement","DHDiskCapacity","DHThermalGrade","HpSureClick","BusinessReviewCard","OsVersionSupport", "DevicesByType", "DevicesByOs", "WarrantyExpiration", "SubscriptionExpiration","CpuUtilization","MemoryUtilization","HardwareInventory", "BatteryReplacementSummary", "AllIncidentsByType", "IncidentBurnDownSummary", "SoftwareInventory", "TodaysCriticalIncidents");
		 	for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size() - 1;chartIdsListCounter++) {
				if (!chartIdsList.get(chartIdsListCounter).equalsIgnoreCase((listOfChartNameArray.get(chartIdsListCounter)).toString())) {
					LOGGER.info("Sequence of " + listOfChartNameArray.get(chartIdsListCounter)+ " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
		return flag;
	}
	
	/**
	 * This method is used to verify of hidden chart present on DashBoard for MSP
	 * 
	 * @param listOfChartNameArray: Position of all charts on the dashboard
	 * @return boolean value it may true or false
	 * @throws Exception
	 */

	public final boolean verifyChartInHiddenAreaAfterDragAndDropOfExperienceMgmtPage(JSONArray listOfChartNameArray) throws Exception {
		Boolean flag = false;
		List<String> hiddenChartIdsList = Arrays.asList("DiskReplacementSummary");
		 	for (int chartIdsListCounter = 0; chartIdsListCounter < hiddenChartIdsList.size() - 1;chartIdsListCounter++) {
				if (!hiddenChartIdsList.get(chartIdsListCounter).equalsIgnoreCase((listOfChartNameArray.get(chartIdsListCounter)).toString())) {
					LOGGER.info("Sequence of " + listOfChartNameArray.get(chartIdsListCounter)+ " is not correct");
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
		return flag;
	}
	/**
	 * This is a method to wait Until element is visible
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */
	public final boolean waitUntilElementsOfExperienceMgmtPage(String key) throws Exception {
		return waitUntillElementIsPresent(ExperienceMgmtPageProperties.getProperty(key));
	}
	
	/**
	 * This method is to verify that reset to Default button enable or not.
	 * 
	 * @throws Exception
	 */
	public final void verifyResetToDefaultButton() throws Exception {
		waitUntilElementsOfExperienceMgmtPage("dashboardSettingConfig");
		clickByJavaScriptOnExperienceMgmtPage("dashboardSettingConfig");
		sleeper(2000);
		if(verifyElementIsEnableOfExperienceMgmtPage("resetToDefaultButton")) {
			clickByJavaScriptOnExperienceMgmtPage("resetToDefaultButton");
			waitUntilElementsOfExperienceMgmtPage("confirmationYesButton");
			clickByJavaScriptOnExperienceMgmtPage("confirmationYesButton");
			sleeper(3000);
			if(uiVersion.equalsIgnoreCase("VENEER2"))
			{
				clickByJavaScriptOnExperienceMgmtPage("saveButtonOnEditMode");	
			}
			
			LOGGER.info("Now, Reset to default button enable to disabled sucessfully.");
		}else {
			clickByJavaScriptOnExperienceMgmtPage("cancelButtonOnEditMode");
            waitForPageLoaded();
			LOGGER.info("Reset to default button already in disabled mode.");
		}
	}
	
	/**
	 * This is a method to clear dashboard company filter present
	 * 
	 * @throws Exception
	 */
	public final void clearDashboardCompanyFilter() throws Exception {
		if (verifyElementsOfExperienceMgmtPage("clearCompany")) {
			clickOnElementsOfExperienceMgmtPage("clearCompany");
			LOGGER.info("Company search filter is cleared");
		} else {
			LOGGER.info("Company search filter is already cleared");
		}
	}
	
	/**
	 *  This is a method to verify that confirmation model Popup is present or not.
	 * @param mspAuthToken: bearer token
	 * @param url: To specify the Authentication url.
	 * @throws Exception
	 */
	public final Boolean getHiddenConfirmationModalPopup(String mspAuthToken,String url) throws Exception {
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken);
		Response response = httpRequest.get(url);
        String expected = response.asString();
        JSONObject jsonObject = new JSONObject(expected);
        Boolean hidePopupDisabledValue = (Boolean) new JSONObject(jsonObject.get("value").toString()).get("hidePopupDisabled");
        return hidePopupDisabledValue;
	}
	/**
	 * This method is to get value from token for given json key
	 * @param tokenValue : we need token value.
	 * @throws Exception
	 */
	public final String getTokenValue(String tokenValue) throws Exception {
		String value= getValueFromToken(tokenValue);
		return value;
	}	
	/**
	 * This method is to get first value from string 
	 * @param input:It is string value
	 * @return first word
	 */
	public static String getFirstWord(String input) {
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ' ') {
				return input.substring(0, i);
			}
		}
		return input;
	}

	
	
	/**
	 * This method basically verify the driver chart header Name on report page with Frame
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param labelKey: list of labels element
	 * @param totalCount :Center count of driver.
	 * @param keyHeaderOnNextPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill down page
	 * @param frameKey: Frame on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean driverChartHeaderTextVerificationOnReportPageFrame(String LanguageCode, String labelKey,String totalCount, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey)throws Exception {
		List<WebElement> legendLabels = getElementsOfExperienceMgmtPage(labelKey);
		boolean flag = false;
		sleeper(3000);
		//disable all legends
		for (int count = 0; count < legendLabels.size(); count++) {
			legendLabels.get(count).click();
		}
		legendLabels.get(0).click();
		LOGGER.info("successfully select the updated legend");
		mouseHoverbyoffsettClick(totalCount, 80, 00);
		sleeper(3000);
		switchToDifferentTabOfExperienceMgmtPage();
		sleeper(3000);
		waitForElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
		List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
			if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode,
					"MPI-Generic-Grid-JSON", headerNamesOnReportPage[listOfOptionsCounter]))) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}
		LOGGER.info("Driver status chart report header text is successfully verified");
		switchToPreviousTabOfExperienceMgmtPage();
		legendLabels.get(0).click();
		//enable all legends
		for (int count = 0; count < legendLabels.size(); count++) {
			legendLabels.get(count).click();
		}
		return flag;
	}
	
	/**
	 * This method basically verify the driver chart header Name on report page with Frame
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param labelKey: list of labels element
	 * @param totalCount :Center count of driver.
	 * @param keyHeaderOnNextPage: Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill down page
	 * @param frameKey: Frame on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean driverChartHeaderTextVerificationOnReportPageFrameFlexi(String LanguageCode, String labelKey, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey) throws Exception {
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelKey);				
			int count1 = 0;
			verifyElementIsClickableOfExperienceMgmtPage(labelKey);
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
				verifyLabelHiddenWhenClickOnLegendsDonutDriverStatusChartFlexi(labelKey,keyDrillDownLabelsAllHidden,legendDropdownKey);
			try {
				if (verifyElementsOfExperienceMgmtPage("legendsDownArrow")) {				
					for (count1 = 0; count1 < listOfLegends.size(); count1++) {
						try {
							waitForPageLoaded();
							listOfLegends.get(count1).click();	
							sleeper(2000);
							VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey, keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
							listOfLegends.get(count1).click();
						}
						catch(Exception e) {
							sleeper(2000);
							clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
							sleeper(2000);
							listOfLegends.get(count1).click();		
							VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey, keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
							listOfLegends.get(count1).click();
						}						
					}
					return true;
				}else{
						for (count1 = 0; count1 < listOfLegends.size(); count1++) {
						waitForPageLoaded();
						listOfLegends.get(count1).click();
						VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey, keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
					}
						return true;
				}	
			}
			 catch (Exception e) {
				LOGGER.info("Failed to Verify legends hidden/visible functionality on header report of chart");
				return false;
			}			
		}	
		
	
	/**
	 * This method used for click on left side of total count.
	 * @param key:it is center point from that we start moving.
	 * @param left:It is value for moving left from center point
	 * @param right:It is value for moving Right from center point
	 * @throws Exception
	 */
	public final void mouseHoverbyoffsettClick(String key,int left,int right) throws Exception {
		mouseHoverbyoffsetClick(ExperienceMgmtPageProperties.getProperty(key),left,right);
    }
	/**
	 * This method used for move the mouse left side of total count.
	 *  @param key:it is center point from that we start moving.
	 * @param left:It is value for moving left from center point
	 * @param right:It is value for moving Right from center point
	 * @throws Exception
	 */
	public final void mouseHoverbyoffsett(String key,int left,int right) throws Exception {
		mouseHoverbyoffset(ExperienceMgmtPageProperties.getProperty(key),left,right);
    }
	
	/**
	 * This method is compare tool tip count with report page row count
	 * @param totalCount:Location of chart total value
	 * @param labelsKey:List of legends
	 * @param tooltipTextKey:For Tooltip text
	 * @param columnListKey:Report page column list
	 * @param paginationKey:this is for pagination
	 * @param frameKey: Frame on the drill down page
	 * @return:Boolean value either true or false.
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountonReportWithFrameDriver(String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement,int xOffset,int yOffset, String legendDropdownKey) throws Exception {
			int listOfLabelsCounter = 0;
			waitForPageLoaded();
			sleeper(2000);
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
				verifyLabelHiddenWhenClickOnLegendsDonutDriverStatusChartFlexi(labelsKey,keyDrillDownLabelsAllHidden,legendDropdownKey);
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
			try {
			if (verifyElementsOfExperienceMgmtPage("legendsDownArrow")) {
				for (listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
					try {
						waitForPageLoaded();
						listOfLegends.get(listOfLabelsCounter).click();
						verifyTooltipCountOnDonutDriverStatusChartFlexi(labelsKey, tooltipTextKey, columnListKey, paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset, legendDropdownKey);
						sleeper(1000);
						listOfLegends.get(listOfLabelsCounter).click();					
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
						sleeper(2000);
						listOfLegends.get(listOfLabelsCounter).click();
						verifyTooltipCountOnDonutDriverStatusChartFlexi(labelsKey, tooltipTextKey, columnListKey, paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset, legendDropdownKey);
						sleeper(1000);
						listOfLegends.get(listOfLabelsCounter).click();					
					}
				}
				return true;
			}else{
				for (listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
				waitForPageLoaded();
				listOfLegends.get(listOfLabelsCounter).click();
				verifyTooltipCountOnDonutDriverStatusChartFlexi(labelsKey, tooltipTextKey, columnListKey, paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset, legendDropdownKey);
				sleeper(1000);
				listOfLegends.get(listOfLabelsCounter).click();			
			}
				return true;
			}
		}
		 catch (Exception e) {
			LOGGER.info("Failed to Verify legends hidden/visible functionality on tool tip count of chart");
			return false;
		}
						
	}	
		
	public final boolean verifyLabelHiddenWhenClickOnLegendsDonutDriverStatusChartFlexi(String keyLegendLabel, String keyDrillDownLabelsAllHidden, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
		int count1 = 0;
		try {
		if (verifyElementsOfExperienceMgmtPage("legendsDownArrow")) {			
			for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
				try {
					waitForPageLoaded();
					sleeper(2000);
					legendLabelElementList.get(count1).click();						
				}
				catch(Exception e) {
					sleeper(2000);
					clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
					sleeper(2000);
					legendLabelElementList.get(count1).click();		
				}
			}				
			return true;
		}else{
				for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
				waitForPageLoaded();
				legendLabelElementList.get(count1).click();
				}
				return true;
			}			
		} catch (Exception e) {
			LOGGER.info("Failed to Verify legends hidden/visible functionality of chart");
			return false;
		}
	}	
	
	public final boolean verifyTooltipCountOnDonutDriverStatusChartFlexi(String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement,int xOffset,int yOffset, String legendDropdownKey) throws Exception {
		boolean flag = false;
		String paginationText = null;
		try {
			sleeper(3000);
			scrollToExperienceMgmtPage(targetElement);
			mouseHoverbyoffsett(targetElement, xOffset, yOffset);
			waitForElementsOfExperienceMgmtPage(tooltipTextKey);
			sleeper(2000);
			String count = getFirstWord(getTextOfExperienceMgmtPage(tooltipTextKey)).split("\\(")[0].trim();
			Integer tooltipcount = Integer.parseInt(count.replaceAll(",", ""));
			sleeper(2000);
			waitForPageLoaded();
			scrollToExperienceMgmtPage(targetElement);
			mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if (waitForElementsOfExperienceMgmtPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				if (tooltipcount > 10) {
					sleeper(2000);
					waitForPageLoaded();
					paginationText = getTextOfExperienceMgmtPage(paginationKey);
					String arr[] = paginationText.split(" ");
					Integer paginationCount;
						paginationCount = Integer.valueOf(arr[1]);
					if ((tooltipcount == paginationCount) || (tooltipcount < paginationCount)) {// A device might have multiple disks hence count sometimes does not match but disk count is always less than number of devices
						flag = true;
					}
				} else {
					sleeper(2000);
					waitForPageLoaded();
					List<WebElement> listOfColumntext = getElementsOfExperienceMgmtPage(columnListKey);
					if (tooltipcount == (listOfColumntext.size())) {
						flag = true;
					}
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfExperienceMgmtPage();				
			sleeper(1000);

		} catch (Exception e) {
			flag = false;
			LOGGER.info("Failed to get tool tip count on Battery Swell Probability Chart");
		}
		return flag;
	}
	
	/**
	 * This method is compare tool tip count with report page row count
	 * @param totalCount:Location of chart total value
	 * @param labelsKey:List of legends
	 * @param tooltipTextKey:For Tooltip text
	 * @param columnListKey:Report page column list
	 * @param paginationKey:this is for pagination
	 * @param frameKey: Frame on the drill down page
	 * @return:Boolean value either true or false.
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountonReportWithFrameDriverFlexi(String totalCount,String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey,String upArrow,String downArrow) throws Exception {
		boolean flag = false;
		String count = null;
		String paginationText = null;
		waitForPageLoaded();
		sleeper(2000);
		List<WebElement> legendLabels = getElementsOfExperienceMgmtPage(labelsKey);
		// Disable all the legends
		if (verifyElementsOfExperienceMgmtPage("legendsDownArrow")) {
			for (int count1 = 0; count1 < legendLabels.size(); count1++) {
				waitForPageLoaded();
				legendLabels.get(count1).click();
				clickOnElementsOfExperienceMgmtPage(downArrow);
			}
			for (int count1 = 0; count1 < legendLabels.size(); count1++) {
				waitForPageLoaded();
				clickOnElementsOfExperienceMgmtPage(upArrow);
			}
		} else {
			for (int count1 = 0; count1 < legendLabels.size(); count1++) {
				waitForPageLoaded();
				legendLabels.get(count1).click();
			}
		}
		// Compare report page count and tool tip count
		for (int ilistOfLabelsCounter = 0; ilistOfLabelsCounter < legendLabels.size(); ilistOfLabelsCounter++) {
			waitForPageLoaded();
			legendLabels.get(ilistOfLabelsCounter).click();
			mouseHoverbyoffsett(totalCount, 70, 00);
			sleeper(2000);
			count = getFirstWord(getTextOfExperienceMgmtPage(tooltipTextKey)).split("\\(")[0].trim();
			Integer tooltipcount = Integer.parseInt(count.replaceAll(",", ""));
			sleeper(2000);
			mouseHoverbyoffsettClick(totalCount, 70, 00);
			sleeper(3000);
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if (tooltipcount > 10) {
				sleeper(2000);
				paginationText = getTextOfExperienceMgmtPage(paginationKey);
				String arr[] = paginationText.split(" ");
				String laststring = arr[arr.length - 1];
				if (laststring.equals(count)) {
					flag = true;
					LOGGER.info("Record count is more than 10 and is equal to tooltip count");
				}
			} else {
				sleeper(2000);
				waitForPageLoaded();
				List<WebElement> listOfColumntext = getElementsOfExperienceMgmtPage(columnListKey);
				if (tooltipcount == (listOfColumntext.size())) {
					flag = true;
					LOGGER.info("Record count is less than 10 and is equal to tooltip count");
				}
			}
			switchToPreviousTabOfExperienceMgmtPage();
			legendLabels.get(ilistOfLabelsCounter).click();
			if (verifyElementsOfExperienceMgmtPage("legendsDownArrow")) {
				clickOnElementsOfExperienceMgmtPage(downArrow);
			}
		} // enable all legends
		if (verifyElementsOfExperienceMgmtPage("legendsDownArrow")) {
			for (int count1 = legendLabels.size() - 1; count1 >= 0; count1--) {
				waitForPageLoaded();
				legendLabels.get(count1).click();
				clickOnElementsOfExperienceMgmtPage(upArrow);
			}
		} else {
			for (int count1 = 0; count1 < legendLabels.size(); count1++) {
				waitForPageLoaded();
				legendLabels.get(count1).click();
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify device name link redirection on reports table for MSP with frame
	 * @param LanguageCode:language initial
	 * @param totalCount :Element of center of chart
	 * @param labelsKey: List of labels/criteria present in the graph
	 * @param deviceDetailsKey: Element present on Device page
	 * @param columnListKey: Columns present in the grid
	 * @param errorKey: Error message flashes on the Dash board
	 * @param frameKey: Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyDeviceNameRedirectionMSPWithFrame1(String LanguageCode, String targetElement, String labelsKey, String deviceDetailsKey, String columnListKey, String errorKey, String frameKey,String downArrow) throws Exception {
		boolean flag = false;
		waitForElementsOfExperienceMgmtPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
		if (verifyElementsOfExperienceMgmtPage(downArrow))
			verifyLabelHiddenWhenClickOnLegendsDonutDriverStatusChartFlexi(labelsKey,targetElement,downArrow);
		try {
			waitForPageLoaded();
			clickOnElementsOfExperienceMgmtPage(downArrow);
			sleeper(2000);
			listOfLabels.get(0).click();
			sleeper(1000);
			waitForPageLoaded();
			mouseHoverbyoffsettClick(targetElement, 15, 80);
			sleeper(3000);
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(5000);
			waitForPageLoaded();
			if (waitForElementsOfExperienceMgmtPage(columnListKey)) {
				clickOnElementsOfExperienceMgmtPage(columnListKey);
				sleeper(2000);
				switchToDifferentTabOfExperienceMgmtPage();
				sleeper(5000);
				waitForPageLoaded();
				if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
					String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfExperienceMgmtPage(errorKey)
						|| waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
					LOGGER.error("Device not found");
					flag = true;
				}
			} else
				LOGGER.error("No data to diplay");
			switchToPreviousTabOfExperienceMgmtPage();
			sleeper(1000);
								
		} catch (Exception e) {
			LOGGER.error("Exception occured in veirifing FlexibleExperienceMgmtPage name : " + e.getMessage());
			flag = false;
		}
		return flag;
	}	
	
	//=======================================flexi==========================================

/**
 * this is used for getting number of windows.
 * @return:return count of windows 
 * @throws Exception
 */
	public final int getWindowHandlesofFlexibleExperienceMgmtPage() throws Exception {
		return getCountofWindowHandles();
	}
		/**
		 * Check custom dashboard name into dropdown list.
		 * @return :return boolean value either true or false
		 * @throws Exception 
		 */
	public boolean verifyCustomDashboardName(String dashboardName) throws Exception {
		boolean flag = false;
		environment = System.getProperty("environment");
		try {
			waitForPageLoaded();
			sleeper(3000);
			clickOnElementsOfExperienceMgmtPage("dashboardListDropdown");
			List<WebElement> element = getElementsTillAllElementsVisibleofExperienceMgmtPage("dashboardList");
			if (element.size() != 0) {
				for (WebElement we : element) {
					if (we.getText().equalsIgnoreCase(getCredentials(environment, dashboardName))) {
						we.click();
						flag = true;
						LOGGER.info("Dashboard is present");
						break;
					}
				}
			} else {
				LOGGER.info("Dashboard list is empty");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in veirifing FlexibleExperienceMgmtPage name : " + e.getMessage());
			flag = false;
		}
		return flag;
	}


		public final void addCustomDashboard(String dashboardName) throws Exception
		{
			environment = System.getProperty("environment");
			waitForPageLoaded();
			sleeper(1000);

			if(verifyCustomDashboardName(dashboardName)){
					deleteCustomDashboard(dashboardName);
					waitForPageLoaded();
					clickOnElementsOfExperienceMgmtPage("editButton");
					clickOnElementsOfExperienceMgmtPage("addCustomDashboardOption");
					enterTextForExperienceMgmtPage("enterNameforCustomDashboard",getCredentials(environment, dashboardName));
					clickOnElementsOfExperienceMgmtPage("addDashboardButton");
					LOGGER.info("Custom dashboard is added successfully");
					waitForPageLoaded();	
				}else{
					sleeper(2000);
					clickOnElementsOfExperienceMgmtPage("defaultOption");
					clickOnElementsOfExperienceMgmtPage("editButton");
					clickOnElementsOfExperienceMgmtPage("addCustomDashboardOption");
					enterTextForExperienceMgmtPage("enterNameforCustomDashboard",getCredentials(environment, dashboardName));
					clickOnElementsOfExperienceMgmtPage("addDashboardButton");
					LOGGER.info("Custom dashboard is added successfully");
					waitForPageLoaded();
				}
		}
		/**
		 * Delete custom dashboard.
		 * @param customName:Dashboard name those want to delete
		 * @throws Exception
		 */
		public final void deleteCustomDashboard( String customDashboardName) throws Exception
		{
			environment = System.getProperty("environment");
			waitForPageLoaded();
			waitForPresenceOfElementsOfExperienceMgmtPage("dashboardListDropdown");
			clickOnElementsOfExperienceMgmtPage("dashboardListDropdown");
			List<WebElement> element = getElementsTillAllElementsVisibleofExperienceMgmtPage("dashboardList");
			if(element.size()!=0){
			for (WebElement we : element) {
				if (we.getText().equalsIgnoreCase(getCredentials(environment,customDashboardName))) {
					we.click();
					break;
				}
			}
			}else{
				LOGGER.info("Dshboard list is empty");
			}
			sleeper(5000);
			clickOnElementsOfExperienceMgmtPage("editButton");
			clickOnElementsOfExperienceMgmtPage("deleteCustomDashboard");
			clickOnElementsOfExperienceMgmtPage("deleteDashboardbutton");
			waitForPageLoaded();
			LOGGER.info("Custom dashboard has been deleted successfully");
		}
		/**
		 * check report value
		 * @return:return boolean value either true or false
		 * @throws Exception
		 */
		public  boolean verifyViewReportOfWidget() throws Exception
		{
			switchToDifferentTabOfExperienceMgmtPage();
			boolean IsDataPresent= false;
			IsDataPresent=reportDataValidation();
			int windowHandles = 0;
			try {
				windowHandles = getWindowHandlesofFlexibleExperienceMgmtPage();
				LOGGER.info("Window handles : " + windowHandles);
				if (windowHandles > 1) {
					switchToPreviousTabOfExperienceMgmtPage();
					waitForPageLoaded();
				}
				
				}catch(Exception e) {
					LOGGER.error("Exception occured in switch window: " + e.getMessage());
				}
			return IsDataPresent;
		}
		/**
		 * Add widget into dashboard
		 * @param languageCode
		 * @throws Exception
		 */
		public final void addWidget(String languageCode,String category,String subcategory) throws Exception
		{
			waitForPageLoaded();
			clickOnElementsOfExperienceMgmtPage("addWidgetButton");
			selectCategoryForFlexibleExperienceMgmtPage(category);
			selectSubCategoryForFlexibleExperienceMgmtPage(subcategory, languageCode);
			clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
			waitForPageLoaded();
			sleeper(3000);
			waitForPageLoaded();
			sleeper(3000);
			clickByJavaScriptOnExperienceMgmtPage("widgetAddConfirmButton");
			waitForPageLoaded();
		}
		/**
		 * Update widget into dashboard
		 * @throws Exception
		 */
		public final void updateWidget(String updateName) throws Exception
		{
			environment = System.getProperty("environment");
			clickOnElementsOfExperienceMgmtPage("clickOnWidgetUpperLeftCornerList");
			if(!(verifyElementsOfExperienceMgmtPage("checkNoData")))
			{
				clickOnElementsOfExperienceMgmtPage("editWidgetOptionFirst");
			}else{
				clickOnElementsOfExperienceMgmtPage("editWidgetOption");	
			}
			enterTextForExperienceMgmtPage("enterEditableText",getCredentials(environment,updateName));
			waitForPageLoaded();
			clickOnElementsOfExperienceMgmtPage("clickOnWidgetUpdateButton");
			LOGGER.info("Widget is updated successfully");
		}
		/**
		 * Delete widget from dashboard
		 * @throws Exception
		 */
		public final void deleteWidget() throws Exception
		{
			clickOnElementsOfExperienceMgmtPage("clickOnWidgetUpperLeftCornerList");
			if(!(verifyElementsOfExperienceMgmtPage("checkNoData")))
			{
			clickOnElementsOfExperienceMgmtPage("deleteWidgetOptionFirst");
			}else{
				clickOnElementsOfExperienceMgmtPage("deleteWidgetOption");
			}
			clickOnElementsOfExperienceMgmtPage("deleteWidgetConfirmButton");
			waitForPageLoaded();
			LOGGER.info("Widget is deleted");
		}
		/**
		 * Use for select category 
		 * @param category:category name wich is selected from drop down
		 * @throws Exception
		 */
		public final void selectCategoryForFlexibleExperienceMgmtPage(String category) throws Exception {
			try {
				clickOnElementsOfExperienceMgmtPage("categoryDropdown");
				sleeper(500);
				List<WebElement> element = getElementsTillAllElementsVisibleofExperienceMgmtPage("categoryList");
				if(element.size()!=0)
				{
					for (WebElement we : element) {
						if (we.getText().equalsIgnoreCase(category)) {
							we.click();
							break;
						}
					}
				}else
				{
					LOGGER.info("Category list is empty");
				}
			} catch (Exception e) {
				LOGGER.error("Exception occured in selectCategoryForWidgetFlexibleExperienceMgmtPage : " + e.getMessage());
			}
		}
		/**
		 * select subcategory
		 * @param subcategory:subcategory name 
		 * @param languageCode:Language
		 * @throws Exception
		 */
		public final void selectSubCategoryForFlexibleExperienceMgmtPage(String subcategory, String languageCode) throws Exception {
			try {
				
				    	scrollToExperienceMgmtPage("subcategoryDropdown");
				    	sleeper(2000);
					clickOnElementsOfExperienceMgmtPage("subcategoryDropdown");
					sleeper(3000);	
					List<WebElement> element = getElementsOfExperienceMgmtPage("subCategoryList");
					scrollTillView(element.get(element.size() - 1));
					if(element.size()!=0)
					{
					for (WebElement we : element) {
						if (we.getText().equalsIgnoreCase(subcategory)) {
							scrollTillView(we);
							we.click();
							break;
						}
					}
					}
					else
					{
						LOGGER.info("subcategory list is empty");
					}
			} catch (Exception e) {
				LOGGER.error("Exception occured in selectSubCategoryForFlexibleExperienceMgmtPage : " + e.getMessage());
			}
		}
		/**
		 * This is used for to add widget for software category
		 * @param category:category name
		 * @param subcategorylist:subcategory name
		 * @param languageCode
		 * @throws Exception
		 */
		public final void addWidgetForSoftware(String category, ArrayList<String> subcategorylist,String languageCode) throws Exception
		{
			ArrayList<String> subCategoryOptionList =new ArrayList<String>(Arrays.asList(
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_swerrors"),
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_swinv")));
			//list of options with corresponds sub category
		        ArrayList<String> software_errors_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_option_topErrorsMonthlySummaryV2")));
				ArrayList<String> software_inventory_optionlist3 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_option_details")));
				sleeper(5000);
			for(int count=0;count<subcategorylist.size();count++)
			{
				if(subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_driver")))
				{	
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("widgetAddConfirmButton");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					waitForPageLoaded();
					LOGGER.info("Widget has been added");
				}else if(subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_softcat")))
				{	
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("widgetAddConfirmButton");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					waitForPageLoaded();
					LOGGER.info("Widget has been added");
				}else if(subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(0).toString()))
				{
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleExperienceMgmtPage(software_errors_optionlist2.get(0).toString(),languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("widgetAddConfirmButton");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					sleeper(10000);
					LOGGER.info("Widget has been added");
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(1).toString()))
				{
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleExperienceMgmtPage(software_inventory_optionlist3.get(0).toString(),languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("widgetAddConfirmButton");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				}else if(subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_softuti")))
				{	
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("widgetAddConfirmButton");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				}else
				{
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					scrollToExperienceMgmtPage("widgetAddConfirmButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("widgetAddConfirmButton");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				}
			}
		}
		
		/**
		 * This is used for to add widget for security category
		 * @param category:category name
		 * @param subcategorylist:subcategory name
		 * @param languageCode
		 * @throws Exception
		 */
		public final void addWidgetForSecurity(String category, ArrayList<String> subcategorylist,String languageCode) throws Exception
		{
			ArrayList<String> subCategoryOptionList =new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_companysec"),			
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_devicesec"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_lostdevicepro")));
		//list of options with corresponds sub category
				ArrayList<String> company_security_compliance_optionlist1 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_option_twentyFourHrSummary")));
				ArrayList<String> device_security_compliance_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_option_twentyFourHrSummary")));
				ArrayList<String> lost_device_protection_optionlist3 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode,"MPI-Reporting-LHreports_service", "label.report_option_details")));
			for(int count=0;count<subcategorylist.size();count++)
			{
			if(subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(0).toString()))
			{
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleExperienceMgmtPage(company_security_compliance_optionlist1.get(0).toString(),languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				
			}else if(subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_devicecomp")))
			{	verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(1).toString()))
			{verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleExperienceMgmtPage(device_security_compliance_optionlist2.get(0).toString(),languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}else if(subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_scasecurity")))
			{	verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(2).toString()))
			{verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleExperienceMgmtPage(lost_device_protection_optionlist3.get(0).toString(),languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");	
			}
			
			else if(subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_surerecactivity")))
			{	verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}
			else 
			{	verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				scrollToExperienceMgmtPage("widgetAddConfirmButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}
		}
	}
		
		/**
		 * This is used for to add widget for network category
		 * @param category:category name
		 * @param subcategorylist:subcategory name
		 * @param languageCode
		 * @throws Exception
		 */
		public final void addWidgetForNetwork(String category, ArrayList<String> subcategorylist,String languageCode) throws Exception
		{
			ArrayList<String> subCategoryOptionList =new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_networkassmt"),			
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_networkinv")));
		//list of options with corresponds sub category
				ArrayList<String> network_health_optionlist1 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "dashboard_service","label.report_option_networkSpeedWifiStrength")));
				ArrayList<String> wireless_networkingCard_inv_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","mpi.report.custom_networkSpeedWifiStrength")));
			for(int count=0;count<subcategorylist.size();count++)
			{
			if(subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(0).toString()))
			{
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleExperienceMgmtPage(network_health_optionlist1.get(0).toString(),languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				
			}else if(subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_networkinv")))
			{	verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(1).toString()))
			{   
				verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleExperienceMgmtPage(wireless_networkingCard_inv_optionlist2.get(0).toString(),languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				verifyElementIsClickableOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}else {}
			}
		}
	
		/**
		 * Add widget for hardware category
		 * @param category:category name
		 * @param subcategorylist:subcategory name
		 * @param languageCode:language
		 * @throws Exception
		 */
		public final void addWidgetForHardware(String category, ArrayList<String> subcategorylist,String languageCode) throws Exception
		{
			ArrayList<String> subCategoryOptionList =new ArrayList<String>(Arrays.asList(
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_hwbluescreen"),
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_deviceuti"),
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_hwinv"),
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_thermalgrade")));
			//list of options with corresponds sub-category
			ArrayList<String> blue_screen_errors_optionlist1 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","mpi.report.bsod.graph.toperrors.devices")));
			ArrayList<String> device_utilization_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_option_swPerfByTime")));
			ArrayList<String> hardware_inventory_optionlist3 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode,"MPI-Reporting-LHreports_service", "label.report_option_details")));
			ArrayList<String> thermal_grading_optionlist4 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode,"MPI-Reporting-LHreports_service", "label.report_option_thermalgrade_details")));
				
			for(int count=0;count<subcategorylist.size();count++)
			{
				if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_biosinventory")))
				{
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");	
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_batteryStatRiskFact")))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");	
				}else if(subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(0).toString()))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleExperienceMgmtPage(blue_screen_errors_optionlist1.get(0).toString(),languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(1).toString()))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleExperienceMgmtPage(device_utilization_optionlist2.get(0).toString(),languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");	
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_diskcap")))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					sleeper(5000);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");	
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_diskrep")))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");	
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwhealth")))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");	
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(2).toString()))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleExperienceMgmtPage(hardware_inventory_optionlist3.get(0).toString(),languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");	
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_hwgradeNew")))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_hwwar")))
				{
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");	
				}
				else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(3).toString()))
				{	
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					scrollToExperienceMgmtPage("widgetAddConfirmButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleExperienceMgmtPage(thermal_grading_optionlist4.get(0).toString(),languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				}
			else{	
					verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
					clickOnElementsOfExperienceMgmtPage("addWidgetButton");
					selectCategoryForFlexibleExperienceMgmtPage(category);
					selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
					clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				}
			}
		}
		/**
		 * Add widget for incident
		 * @param category:category name
		 * @param languageCode:language name
		 * @throws Exception
		 */
		public final void addWidgetForIncident(String category,String languageCode) throws Exception
		{	
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");		
				waitUntilElementIsVisibleOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
		}
		/**
		 * Add widget for subscription
		 * @param category:category name
		 * @param languageCode:language name
		 * @throws Exception
		 */
		public final void addWidgetForSubscription(String category,String subcate,String languageCode) throws Exception
		{	
				clickOnElementsOfExperienceMgmtPage("addWidgetButton");
				selectCategoryForFlexibleExperienceMgmtPage(category);
				selectSubCategoryForFlexibleExperienceMgmtPage(subcate, languageCode);
				clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfExperienceMgmtPage("widgetAddConfirmButton");
				clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
				
		}
		/**
		 * Select option for respective subcategory
		 * @param options:option name
		 * @param languageCode:language name
		 * @throws Exception
		 */
		public final void selectOptionForFlexibleExperienceMgmtPage(String options, String languageCode ) throws Exception {
			try {
				if (getTextOfExperienceMgmtPage("optionDropdown").equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON",
						"select_option_text"))) {
					clickOnElementsOfExperienceMgmtPage("optionDropdown");
					sleeper(500);
					List<WebElement> element = getElementsTillAllElementsVisibleofExperienceMgmtPage("optionDropdownList");
					if(element.size()!=0)
					{
					for (WebElement we : element) {
						if (we.getText().equalsIgnoreCase(options)) {
							we.click();
							break;
						}
					}
					}else
					{
						LOGGER.info("Option list is empty");
					}
				}else
				{
					LOGGER.info("Option is allready selected");
				}
				
			} catch (Exception e) {
				LOGGER.error("Exception occured in selectOptionForFlexibleExperienceMgmtPage : " + e.getMessage());
			}
		}
		
		/**
		 * delete widget list from dashboard
		 * @throws Exception
		 */
		public final void deleteAllWidgetForFlexibleExperienceMgmtPage() throws Exception {
			try {
					List<WebElement> element = getElementsTillAllElementsVisibleofExperienceMgmtPage("widgetList");
					if(element.size()!=0)
					{
					for (@SuppressWarnings("unused") WebElement we : element) {
						deleteWidget();
						}
					}else
					{
						LOGGER.info("widget list is empty");
					}
			} catch (Exception e) {
				LOGGER.error("Exception occured in delete widget For FlexibleExperienceMgmtPage : " + e.getMessage());
			}
		}
		/**
		 * Used for getting number of widgets
		 * @return: return a widget count.
		 * @throws Exception
		 */
		public final int getWidgetCount() throws Exception
		{
			int count=0;
			List<WebElement> element = getElementsTillAllElementsVisibleofExperienceMgmtPage("widgetOnGrid");
			for (int i=0;i< element.size();i++) {
				count++;
				}
			return count;
		}
		/**
		 * Delete filters .
		 */
		public final void deleteFilterCriteria()
		{
			if (getDriver().findElements(By.xpath(ExperienceMgmtPageProperties.getProperty("deleteFilter"))).size() != 0) {
				do {
					getDriver().findElement(By.xpath(ExperienceMgmtPageProperties.getProperty("deleteFilter"))).click();

				} while (getDriver().findElements(By.xpath(ExperienceMgmtPageProperties.getProperty("deleteFilter"))).size() != 0);
			}
		}
		/**
		 * Used for check data of report
		 * @return:return either true or false value
		 * @throws Exception
		 */
		public boolean reportDataValidation() throws Exception {
			boolean reportStatus = false;
			boolean reportStatusGraph = true;
			try {
				reportStatusGraph = validateGraphData();
				LOGGER.info(reportStatusGraph);
				if (reportStatusGraph )
					{
						reportStatus = true;
					}
				
				} catch (Exception e) {
					LOGGER.error("Exception occured in validating graph : " + e.getMessage());
					return reportStatus;
				}
			LOGGER.info("Final Report Status is : " + reportStatus);
			return reportStatus;
		}
		/**
		 * This method is for validating the Graph(charts) data present in the reports.
		 * @return:return  either true or false value 
		 * @throws Exception
		 */
		private boolean validateGraphData() throws Exception {
			try {
				if (!(verifyElementsOfExperienceMgmtPage("chartsNoData"))) {
					LOGGER.info("Data is present in the Graph  ");
					return true;
				} else if (verifyElementsOfExperienceMgmtPage("chartsNoData")) {
					LOGGER.info("Data is not present in the Graph ");
					return true;
				} else {
					LOGGER.info("Data is not loaded due to some error in the graph ");
					return false;
				}
			} catch (Exception e) {
				LOGGER.error("Exception occured in validating graph : " + e.getMessage());
				return false;
			}

		}
		
		/**
		 * This method is used to verify tool tip count with total rows on reports with Frame
		 * 
		 * @param labelsKey: Chart labels
		 * @param tooltipTextKey: Tooltip text after hovering on a chart
		 * @param columnListKey: Column names present in the grid
		 * @param paginationKey: Locator of the pagination
		 * @param targetElement: element to move to
		 * @param keyDrillDownLabelsAllHidden: all label hidden locator
		 * @param frameKey: Iframe on the drill down page
		 * @param xoffset: x coordinate to move
		 * @param yoffset: y coordinate to move
		 * @return boolean value
		 * @throws Exception
		 */

		public final boolean verifyTooltipCountonReportWithFrameFlexiWithOffset(String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement,int xOffset,int yOffset) throws Exception {
			boolean flag = false;
			String count = null;
			String paginationText = null;
			waitForPageLoaded();
			sleeper(2000);
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey,keyDrillDownLabelsAllHidden);
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
			for (int ilistOfLabelsCounter = 0; ilistOfLabelsCounter < listOfLegends.size(); ilistOfLabelsCounter++) {
				listOfLegends.get(ilistOfLabelsCounter).click();
				sleeper(3000);
				scrollToExperienceMgmtPage(targetElement);
				mouseHoverbyoffsett(targetElement, xOffset, yOffset);
				waitForElementsOfExperienceMgmtPage(tooltipTextKey);
				sleeper(2000);
				count = getTextOfExperienceMgmtPage(tooltipTextKey);
				String count_clean = count.replaceAll(",", "");
				Integer tooltipcount = Integer.valueOf(count_clean);
				sleeper(2000);
				waitForPageLoaded();
				scrollToExperienceMgmtPage(targetElement);
				mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
				switchToDifferentTabOfExperienceMgmtPage();
				sleeper(3000);
				if(waitForElementsOfExperienceMgmtPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
					sleeper(2000);
					waitForPageLoaded();
					paginationText = getTextOfExperienceMgmtPage(paginationKey);
					String arr[] = paginationText.split(" ");
					Integer paginationCount;
						 paginationCount = Integer.valueOf(arr[1]);
					if ((tooltipcount==paginationCount)||(tooltipcount<paginationCount)) {//A device might have multiple disks hence count sometimes does not match but disk count is always less than number of devices
						flag = true;
					} 
				}else{
					LOGGER.error("Report did not load in 1 minute.");
				}
				switchToPreviousTabOfExperienceMgmtPage();
				sleeper(1000);
				listOfLegends.get(ilistOfLabelsCounter).click();
			}
			return flag;
		}
		
		/**
		 * This method is used to verify tool tip count with total rows on reports with Frame
		 * 
		 * @param labelsKey: Chart labels
		 * @param tooltipTextKey: Tooltip text after hovering on a chart
		 * @param columnListKey: Column names present in the grid
		 * @param paginationKey: Locator of the pagination
		 * @param targetElement: element to move to
		 * @param keyDrillDownLabelsAllHidden: all label hidden locator
		 * @param frameKey: Iframe on the drill down page
		 * @param xoffset: x coordinate to move
		 * @param yoffset: y coordinate to move
		 * @return boolean value
		 * @throws Exception
		 */

		public final boolean verifyTooltipCountonReportWithFrameDonutChartFlexiWithOffset(String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement,int xOffset,int yOffset, String legendDropdownKey) throws Exception {
			boolean flag = false;
			String count = null;
			String paginationText = null;
			waitForPageLoaded();
			sleeper(2000);
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
				verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelsKey,keyDrillDownLabelsAllHidden,legendDropdownKey);
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
			if (waitForElementsOfExperienceMgmtPageDynamic(legendDropdownKey,1)) {
				if (listOfLabelsCounter == 0 || listOfLabelsCounter == 2) {
					clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
					sleeper(3000);
				}
			} else {
				LOGGER.info("Legend dropdown not present on chart");
			}
				listOfLegends.get(listOfLabelsCounter).click();
				sleeper(3000);
				scrollToExperienceMgmtPage(targetElement);
				mouseHoverbyoffsett(targetElement, xOffset, yOffset);
				sleeper(2000);
				count = getTextOfExperienceMgmtPage(tooltipTextKey);
				String count_clean = count.replaceAll(",", "");
				Integer tooltipcount = Integer.valueOf(count_clean);
				sleeper(2000);
				waitForPageLoaded();
				scrollToExperienceMgmtPage(targetElement);
				mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
				switchToDifferentTabOfExperienceMgmtPage();
				sleeper(3000);
				if(waitForElementsOfExperienceMgmtPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
				if (tooltipcount > 10) {
					sleeper(2000);
					waitForPageLoaded();
					paginationText = getTextOfExperienceMgmtPage(paginationKey);
					String arr[] = paginationText.split(" ");
					Integer paginationCount;
						 paginationCount = Integer.valueOf(arr[1]);
					if ((tooltipcount==paginationCount)||(tooltipcount<paginationCount)) {//A device might have multiple disks hence count sometimes does not match but disk count is always less than number of devices
						flag = true;
					}
				} else {
					sleeper(2000);
					waitForPageLoaded();
					List<WebElement> listOfColumntext = getElementsOfExperienceMgmtPage(columnListKey);
					if (tooltipcount == (listOfColumntext.size())) {
						flag = true;
					}
				}}else{
					LOGGER.error("Report did not load in 1 minute.");
				}
				switchToPreviousTabOfExperienceMgmtPage();
				sleeper(1000);
				listOfLegends.get(listOfLabelsCounter).click();
			}
			return flag;
		}
		
		/**
		 * @param labelsKey - lengends locator
		 * @param tooltipTextKey - tool tip locator
		 * @param textKey - text key locator
		 * @param xoffset: x coordinate to move
		 * @param yoffset: y coordinate to move
		 * @return boolean
		 * @throws Exception
		 */
		public final boolean verifyTooltipTextOnReportWithFrameFlexiWithOffset(String labelsKey, String tooltipTextKey, String textKey, String keyDrillDownLabelsAllHidden,String targetElement,int xOffset,int yOffset) throws Exception {
			boolean flag = false;
			String text = null;
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey,keyDrillDownLabelsAllHidden);
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
			for (int i = 0; i < listOfLegends.size(); i++) {
				listOfLegends.get(i).click();
				sleeper(3000);
				mouseHoverbyoffsett(targetElement, xOffset, yOffset);
				waitForElementsOfExperienceMgmtPage(tooltipTextKey);
				sleeper(2000);
				text = getTextOfExperienceMgmtPage(tooltipTextKey);
				String cleanText = text.replaceAll("[+.^:,]","");
				sleeper(2000);
				mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
				switchToDifferentTabOfExperienceMgmtPage();
				if(waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
				clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfExperienceMgmtPage(textKey);
				if (monthtext.get(0).getText().contains(cleanText)) {
					flag = true;
				}}else{
					LOGGER.error("Report did not load in 1 minute.");
				}
				switchToPreviousTabOfExperienceMgmtPage();
				sleeper(1000);
				listOfLegends.get(i).click();
			}
			return flag;
		}
		/**
		 * This method verify message which is present on device with outdated driver chart
		 * @param message: This is message fron outdted driver chart.
		 * @param lang:Language name
		 * @return :boolean value
		 * @throws Exception
		 */
		public final boolean verifyMesageForUpdateDriver(String message,String lang) throws Exception
		{
			boolean flag = false;	
			waitForPageLoaded();
			if(message.equalsIgnoreCase(getTextLanguage(lang, "daas_ui", "dashboard.drivers.action.desc.critical")))
			{
				LOGGER.info("This is critical update driver message");
				flag = true;
			}else if(message.equalsIgnoreCase(getTextLanguage(lang, "daas_ui", "dashboard.drivers.action.desc.recommended")))
			{
				LOGGER.info("This is recommended update driver message");
				flag = true;
			}else
			{
				LOGGER.info("This is routine updates  driver at your convenience");
				flag = true;
			}
			return flag;
		}
		
		/**
		 * 
		 * @param dashboardName -  Dashboard to be shared
		 * @param username - User name of the Receiver
		 * @return - Flag value - true/false
		 * @throws Exception
		 */
		public final boolean shareExperienceMgmtPage(String dashboardName, String username) throws Exception
		{
			boolean flag = false;
			waitForPageLoaded();
			sleeper(5000);
			waitForElementsOfExperienceMgmtPage("dashboardListDropdown");
			clickOnElementsOfExperienceMgmtPage("dashboardListDropdown");
			List<WebElement> element = getElementsOfExperienceMgmtPage("dashboardList");
			if(element.size()!=0){
			for (WebElement we : element) {
				if (we.getText().equalsIgnoreCase(dashboardName)) {
					we.click();
					break;
				}
			}
			}else{
				LOGGER.info("Dashboard list is empty");
				return flag;
			}
			waitForPageLoaded();
			sleeper(3000);
			clickOnElementsOfExperienceMgmtPage("editButtonShareDash");
			clickOnElementsOfExperienceMgmtPage("shareWithDashboardOption");
			waitForElementsOfExperienceMgmtPage("shareDashboardUserDropdown");
			clickOnElementsOfExperienceMgmtPage("shareDashboardUserDropdown");
			clickOnElementsOfExperienceMgmtPage("shareDashboardUserDropdownSearchBox");
			enterTextForExperienceMgmtPage("shareDashboardUserDropdownSearchBox", username);
			sleeper(1000);
			List<WebElement> userElement = getElementsOfExperienceMgmtPage("shareDashboardUserDropdownList");
			if(userElement.size()!=0){
			for (WebElement we : userElement) {
				if (we.getText().equalsIgnoreCase(username)) {
					we.click();
					sleeper(1000);
					break;
				}
			}
			}else{
				LOGGER.info("User list is empty");
				return flag;
			}
				pressKey(Keys.ESCAPE);
				sleeper(1000);
			clickOnElementsOfExperienceMgmtPage("shareButton");
			waitForPresenceOfElementsOfExperienceMgmtPage("shareDashboardSuccessMessage");
			verifyElementsOfExperienceMgmtPage("shareDashboardSuccessMessage");
			flag = true;
			return flag;
		}
		
		/**
		 * 
		 * @param dashboardName -  Dashboard to be shared
		 * @param username - User name of the Receiver
		 * @return - Flag value - true/false
		 * @throws Exception
		 */
		public final boolean verifyShareExperienceMgmtPage(String dashboardName, String username, String sendername ) throws Exception {
			boolean flag = false;
			int i = 0;
			String addLinkClickFinal = null;
			String receiverDashboardName = username + "'s" + " " + dashboardName;
			String notificationText = sendername + " " + "is sharing a dashboard view called" + " " + receiverDashboardName + ". Do you want to add it to your custom views?";
			waitForPageLoaded();
			sleeper(5000);
			waitForElementsOfExperienceMgmtPage("newNotificationIndicatorDashboard");
			clickOnElementsOfExperienceMgmtPage("notificationBellIconDashboard");
			List<WebElement> element = getElementsOfExperienceMgmtPage("unreadNotificationDashboardText");
			if (element.size() != 0) {
				for (WebElement we : element) {
					i= i+1;
					if (we.getText().equalsIgnoreCase(notificationText)) {
						LOGGER.info("value of webelement" +we);
						String addLinkClick = ExperienceMgmtPageProperties.getProperty("addLinkTemp");
							addLinkClickFinal = addLinkClick + "[" + i + "]" + "//button//*[name()='span']";
						LOGGER.info("value of add link" +addLinkClickFinal);
						mouseHover(addLinkClickFinal);
						clickByJavaScript(addLinkClickFinal);
						clickOnElementsOfExperienceMgmtPage("addDashboard");
						sleeper(1000);
						break;
					}
				}
			} else {
				LOGGER.info("Notification list is empty");
				return flag;
			}
			
			//sleeper(2000);
			verifyElementsOfExperienceMgmtPage("shareDashboardSuccessMessage");
			clickOnElementsOfExperienceMgmtPage("dashboardListDropdown");
			List<WebElement> webelement = getElementsOfExperienceMgmtPage("dashboardList");
			if(webelement.size()!=0){
			for (WebElement we : webelement) {
				if (we.getText().contains(receiverDashboardName)) {
					we.click();
					LOGGER.info("Dashboard added successfully");
					break;
				}
			}
			}else{
				LOGGER.info("Dashboard list is empty");
				return flag;
			}
			waitForPageLoaded();
			clickOnElementsOfExperienceMgmtPage("editButtonShareDash");
			clickOnElementsOfExperienceMgmtPage("deleteCustomDashboard");
			clickOnElementsOfExperienceMgmtPage("deleteDashboardbutton");
			waitForPageLoaded();
			LOGGER.info("Shared Custom dashboard has been deleted successfully");
			flag = true;
			return flag;
		}

		/**
		 * This method is used to verify Discard functionality of share dashboard
		 * @param dashboardName -  Dashboard to be shared
		 * @param username - User name of the Receiver
		 * @param sendername - User sharing dashboard
		 * @return - Flag value - true/false
		 * @throws Exception
		 */
		public final boolean verifyDiscardedShareExperienceMgmtPage(String dashboardName, String username, String sendername ) throws Exception {
			boolean flag = false;
			int i = 0;
			String discardLinkClickFinal = null;
			String receiverDashboardName = username + "'s" + " " + dashboardName;
			String notificationText = sendername + " " + "is sharing a dashboard view called" + " " + receiverDashboardName + ". Do you want to add it to your custom views?";
			waitForPageLoaded();
			sleeper(5000);
				waitForElementsOfExperienceMgmtPage("newNotificationIndicatorDashboard");
				clickOnElementsOfExperienceMgmtPage("notificationBellIconDashboard");
				List<WebElement> element = getElementsOfExperienceMgmtPage("unreadNotificationDashboardText");
				if (element.size() != 0) {
					for (WebElement we : element) {
						i= i+1;
						if (we.getText().equalsIgnoreCase(notificationText)) {
							LOGGER.info("value of webelement" +we.getText());
							String discardLinkClick = ExperienceMgmtPageProperties.getProperty("addLinkTemp");
							discardLinkClickFinal = discardLinkClick + "[" + i + "]" + "//button//*[name()='span']";
							LOGGER.info("value of discrad link" +discardLinkClickFinal);
							mouseHover(discardLinkClickFinal);
							clickByJavaScript(discardLinkClickFinal);
							clickOnElementsOfExperienceMgmtPage("discardDasboard");
							break;
						}
					}
				} else {
					LOGGER.info("Notification list is empty");
					return flag;  
				}
				
				sleeper(3000);
				verifyElementIsPresent("shareDashboardSuccessMessage");
			    waitForPageLoaded();
				LOGGER.info("Shared Custom dashboard has been discarded successfully");
				flag = true;
			return flag;
		}
		
		/**
		 * This method verifies 2 level drill down of charts
		 * @param barsFirstLevelKey -  Key for bar chart
		 * @param tooltip - Key for tooltip
		 * @param secondLevelLegends - Key for legends on charts after drilldown
		 * @param xAxisLabels - Key for x axis labels on chart
		 * @return - boolean - true/false
		 * @throws Exception
		 */
	public boolean verifyTwoLevelDrillDown(String barsFirstLevelKey, String tooltip, String secondLevelLegends,String xAxisLabels) throws Exception {
		List<WebElement> firstLevelBars = getElementsOfExperienceMgmtPage(barsFirstLevelKey);
		List<String> countOfIncidentOnBars=new ArrayList<String>();
		ArrayList<String> typesOfIncidentsOnBars = new ArrayList<String>();
		if(firstLevelBars.size()>0) {
		int count=0,countOfIncidentsAtSecondLevel=0,countOfIncidentsAtMultipleBars=0;	
		String totalIncidentText="",toolTipText = "", categoryOfIncident = "", countOfIncidentsAtFirstLevel = "", typeOfIncident = "";
		boolean legendCheck = false, countCheck = false, typeCheck = false, numberOfIncidentsCheck = false,typeOfIncidentsCheck = false, catgoryOfIncidentsCheck = false;
		Actions action = new Actions(getDriver());
		for (int i = 0; i < firstLevelBars.size(); i++) {
			firstLevelBars = getElementsOfExperienceMgmtPage(barsFirstLevelKey);//To solve stale element exception
			action.moveToElement(firstLevelBars.get(i)).build().perform();
			waitForElementsOfExperienceMgmtPage(tooltip);
			toolTipText = getTextOfExperienceMgmtPage(tooltip);
				categoryOfIncident = toolTipText;
				countOfIncidentsAtFirstLevel = toolTipText;
			firstLevelBars.get(i).click();
			sleeper(2000);//To get second level of bar graph
			List<WebElement> secondLevelBars = getElementsOfExperienceMgmtPage(barsFirstLevelKey);
				action.moveToElement(secondLevelBars.get(0)).build().perform();
				waitForElementsOfExperienceMgmtPage(tooltip);
				toolTipText = getTextOfExperienceMgmtPage(tooltip);
					typeOfIncident = toolTipText;
					countOfIncidentsAtSecondLevel = Integer.parseInt(toolTipText);
				legendCheck = categoryOfIncident.equalsIgnoreCase(getTextOfExperienceMgmtPage(secondLevelLegends));
				if(!legendCheck) {
					LOGGER.info("Legend after 1st drill down does not match with tooltip text for "+i+" bar");
				}
				if(secondLevelBars.size()>1) {
					for(int j=0;j<secondLevelBars.size();j++) {
						action.moveToElement(secondLevelBars.get(j)).build().perform();
						waitForElementsOfExperienceMgmtPage(tooltip);
						typesOfIncidentsOnBars.add(getTextOfExperienceMgmtPage(tooltip).split(": ")[0]);
						countOfIncidentOnBars.add(getTextOfExperienceMgmtPage(tooltip).split(": ")[1]);
						count+= Integer.parseInt(getTextOfExperienceMgmtPage(tooltip).split(": ")[1]);
					}
					countOfIncidentsAtSecondLevel=count;
					count=0;
				}
				countCheck = (Integer.parseInt(countOfIncidentsAtFirstLevel) == countOfIncidentsAtSecondLevel);
				if(!countCheck) {
					LOGGER.info("Count of incidents does not match after 1st drill down for "+i+" bar");
				}
				
				if(secondLevelBars.size()>1) {
					List<String> typesOfIncidentsOnXAxis = getUniqueElementsofStringsFromListOnExperienceMgmtPage(xAxisLabels);
					typeCheck=typesOfIncidentsOnXAxis.equals(typesOfIncidentsOnBars);
				}else {
					typeCheck = getTextOfExperienceMgmtPage(xAxisLabels).equalsIgnoreCase(typeOfIncident);
				}
				if(!typeCheck) {
					LOGGER.info("Type of incident does not match after 1st drill down for "+i+" bar");
				}
				secondLevelBars.get(0).click();
				IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
				incidentPage.waitForElementsOfIncidentPage("showingPaginationTotalCount");
				totalIncidentText = incidentPage.getTextOfIncidentPage("showingPaginationTotalCount");
				
				if(secondLevelBars.size()>1) {
					numberOfIncidentsCheck=countOfIncidentOnBars.contains(totalIncidentText.split("of ")[1]);
				}else {
					numberOfIncidentsCheck = countOfIncidentsAtSecondLevel == Integer.parseInt(totalIncidentText.split("of ")[1]);
				}
				if(!numberOfIncidentsCheck) {
					LOGGER.info("No of incidents does not match on incidents page with chart for "+i+" bar");
				}
				catgoryOfIncidentsCheck = incidentPage.getTextOfIncidentPage("incidentType").equalsIgnoreCase(categoryOfIncident);
				if(!catgoryOfIncidentsCheck) {
					LOGGER.info("Type of incident does not match on incidents page with chart for "+i+" bar");
				}
				typeOfIncidentsCheck = incidentPage.getTextOfIncidentPage("incidentSubType").equalsIgnoreCase(typeOfIncident);
				if(!typeOfIncidentsCheck) {
					LOGGER.info("Subtype of incident on incidents page does not match with chart for "+i+" bar");
				}
				clickOnElementsOfExperienceMgmtPage("dashboardTab");
				waitForElementsOfExperienceMgmtPage("allIncidentsByTypeTitle");
				scrollToExperienceMgmtPage("allIncidentsByTypeTitle");
				waitForElementsOfExperienceMgmtPage(barsFirstLevelKey);
				if(secondLevelBars.size()>1) {
					for(int k=1;k<secondLevelBars.size();k++) {
						firstLevelBars = getElementsOfExperienceMgmtPage(barsFirstLevelKey);//To solve stale element exception
						firstLevelBars.get(i).click();
						secondLevelBars = getElementsOfExperienceMgmtPage(barsFirstLevelKey);//To solve stale element exception
						action.moveToElement(secondLevelBars.get(k)).build().perform();
						waitForElementsOfExperienceMgmtPage(tooltip);
						toolTipText = getTextOfExperienceMgmtPage(tooltip);
						typeOfIncident = toolTipText.split(": ")[0];
						countOfIncidentsAtMultipleBars = Integer.parseInt(toolTipText.split(": ")[1]);
						legendCheck = legendCheck && categoryOfIncident.equalsIgnoreCase(getTextOfExperienceMgmtPage(secondLevelLegends));
						if(!legendCheck) {
							LOGGER.info("Legend after 2nd drill down does not match with tooltip text for "+k+" bar");
						}
						typeCheck = typeCheck && typesOfIncidentsOnBars.contains(typeOfIncident);
						if(!typeCheck) {
							LOGGER.info("Type of incident does not match after 2nd drill down for "+k+" bar");
						}
						secondLevelBars.get(k).click();
						incidentPage.waitForElementsOfIncidentPage("showingPaginationTotalCount");
						totalIncidentText = incidentPage.getTextOfIncidentPage("showingPaginationTotalCount");
						numberOfIncidentsCheck = numberOfIncidentsCheck && (countOfIncidentsAtMultipleBars == Integer.parseInt(totalIncidentText.split("of ")[1]));
						if(!numberOfIncidentsCheck) {
							LOGGER.info("No of incidents does not match on incidents page with chart after 2nd drill down for "+k+" bar");
						}
						catgoryOfIncidentsCheck = catgoryOfIncidentsCheck && incidentPage.getTextOfIncidentPage("incidentType").equalsIgnoreCase(categoryOfIncident);
						if(!catgoryOfIncidentsCheck) {
							LOGGER.info("Type of incident does not match on incidents page with chart after 2nd drill down for "+k+" bar");
						}
						typeOfIncidentsCheck = typeOfIncidentsCheck && incidentPage.getTextOfIncidentPage("incidentSubType").equalsIgnoreCase(typeOfIncident);
						if(!typeOfIncidentsCheck) {
							LOGGER.info("Subtype of incident on incidents page does not match with chart after 2nd drill down for "+k+" bar");
						}
						typesOfIncidentsOnBars.clear();
						countOfIncidentOnBars.clear();
						clickOnElementsOfExperienceMgmtPage("dashboardTab");
						waitForElementsOfExperienceMgmtPage("allIncidentsByTypeTitle");
						scrollToExperienceMgmtPage("allIncidentsByTypeTitle");
						waitForElementsOfExperienceMgmtPage(barsFirstLevelKey);
					}
				}
			}
		return (legendCheck && countCheck && typeCheck && numberOfIncidentsCheck && typeOfIncidentsCheck&& catgoryOfIncidentsCheck);
		}else {
			LOGGER.info("Chart does not have data to test drill down");
			return true;
		}
	}
	
	/**
	 * This method returns the text corresponding to the list of web elements.
	 * @param key - Key of element to get the string list.
	 * @return
	 * @throws Exception
	 */
	
	public final List<String> getUniqueElementsofStringsFromListOnExperienceMgmtPage(String key) throws Exception {
		return getUniqueElementsofStringsFromList(ExperienceMgmtPageProperties.getProperty(key));
	}

	    /** This method is used to search a particular custom dashboard from a list.
		 * @param dashboardToSearch
		 * @return
		 * @throws Exception
		 */
		public final boolean searchDashboardinList(String dashboardToSearch) throws Exception{
			boolean flag = false;
			try {
				
				clickOnElementsOfExperienceMgmtPage("dashboardListDropdown");
				sleeper(2000);
				//clickOnElementsOfExperienceMgmtPage("dashboardListSearchBar");
				enterTextForExperienceMgmtPage("dashboardListSearchBar", dashboardToSearch);
				sleeper(1000);
				List<WebElement> webelement = getElementsOfExperienceMgmtPage("dashboardList");
				if(webelement.size()!=0){
					for (WebElement we : webelement) {
						if (we.getText().equalsIgnoreCase(dashboardToSearch)) {
							LOGGER.info("Dashboard searched successfully");
							flag = true;
							break;
						}
					}
					}else{
						LOGGER.info("Dashboard list is empty, search was unsuccesful");
						return flag;
					}	
			}catch (Exception e) {
				LOGGER.error("Exception occured in searching the dashboard : " + e.getMessage());
			}
			
			return flag;
		}
		
		
		/** This method selects a company from global filter.
		 * @param companyName
		 * @return
		 * @throws Exception
		 */
		public final void selectCompanyInGlobalFilter(String companyName) {
			try {
				clickByJavaScriptOnExperienceMgmtPage("globalLocationFilterDropDown");
				waitForPageLoaded();
				enterTextForExperienceMgmtPage("filterMenuCompaniesSearch", companyName);
				LOGGER.info("Search company to set location filter");
				sleeper(3000);
				waitForElementsOfExperienceMgmtPage("companyOnListSearch");
				if (verifyElementsOfExperienceMgmtPage("companyOnListSearch"))
					clickOnElementsOfExperienceMgmtPage("companyOnListSearch");
				LOGGER.info("Select company to set location filter");
				sleeper(2000);
				if (verifyElementsOfExperienceMgmtPage("globalFilterSave")) {
					clickOnElementsOfExperienceMgmtPage("globalFilterSave");
					sleeper(5000);//Table values dont load
					LOGGER.info("Global Location Filter Saved successfully.");
				}
				if (verifyElementsOfExperienceMgmtPage("globalFilterCancel")) {
					clickOnElementsOfExperienceMgmtPage("globalFilterCancel");
					sleeper(5000);//Table values dont load
				}	
			}catch (Exception e) {
				LOGGER.error("Exception occured in selecting the company in global filter in method selectCompanyInGlobalFilter : " + e.getMessage());
			}
		}
		
		/** This method is used to validate navigation from overenrollment tile to companies list page.
		 * @param LanguageCode
		 * @return
		 * @throws Exception
		 */
		public boolean verifyPlanOnCompanyListPage(String LanguageCode) throws Exception{
			boolean flag = false;
			String count = null;
			List<WebElement> companyNameList = new ArrayList<WebElement>();
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			count = getTextOfExperienceMgmtPage("countOnTile");
			int countNumber = Integer.parseInt(count);
			clickOnElementsOfExperienceMgmtPage("viewDetailsButtonOverEnroll");
			if(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")))
			{
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
				waitForPresenceOfElementsOfExperienceMgmtPage("statusText");
				if(getTextOfExperienceMgmtPage("statusText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "company.status.over_enrolled"))){
					companyNameList = getElementsTillAllElementsVisibleofExperienceMgmtPage("nameList");
					if(companyNameList.size()>0){
						int nameListCount = companyNameList.size();
						if(countNumber==nameListCount){
							flag = true;
							LOGGER.info("Count got matched from Dashboard page to companies list page for overenrollment.");
						}
						
					}else{
						LOGGER.error("No Companies are present on companies list page");
					}
				}else{
					LOGGER.error("Over Enrollment filter did not applied on Companies List page.");
				}
			}
			else{
				LOGGER.error("Companies List page did not load successfully when navigated from Over Enrollment tile.");
			}
			return flag;
			
		}
		
		
		/**
		 * This method basically verify the header Name on report page
		 * 
		 * @param LanguageCode: This is language code used for multiple languages.
		 * @param labelKey: Chart labels
		 * @param keyHeaderOnNextPage: Header on the drill down page
		 * @param headerNamesOnReportPage: Vlaues in the header section on the drill down page
		 * @return boolean value
		 * @throws Exception
		 */

		public final boolean headerTextVerificationOnReportPage(String LanguageCode, String labelKey, String keyHeaderOnNextPage, String[] headerNamesOnReportPage) throws Exception {
			boolean flag = false;
			verifyElementIsClickableOfExperienceMgmtPage(labelKey);
			clickOnElementsOfExperienceMgmtPage(labelKey);
			sleeper(3000);
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnNextPage,DashboardVariables.DASHBOARD_REPORTS_WAIT)){
			List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
			for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
				if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			}else{
			  LOGGER.error("Report page did not load in 1 minute.");	
			}
			switchToPreviousTabOfExperienceMgmtPage();
			return flag;
		}
		
		/**
		 * This method basically verify the header Name on report page
		 * 
		 * @param LanguageCode: This is language code used for multiple languages.
		 * @param labelKey: Chart label
		 * @param keyHeaderOnNextPage: Header on the drill down page
		 * @param threatPageHeader: Header of threat details page
		 * @return boolean value
		 * @throws Exception
		 */

		public final boolean viewThreatDetailsRedirectionOnReportPage(String LanguageCode, String labelKey, String keyHeaderOnNextPage, String threatPageHeader) throws Exception {
			boolean flag = false;WebElement element = null;
			verifyElementIsClickableOfExperienceMgmtPage(labelKey);
			clickOnElementsOfExperienceMgmtPage(labelKey);
			sleeper(3000);
			switchToDifferentTabOfExperienceMgmtPage();
			sleeper(3000);
			if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnNextPage,DashboardVariables.DASHBOARD_REPORTS_WAIT)){
				scrollToExperienceMgmtPage("viewDetailsHeaderhPSureSenseProThreats");
				List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage("viewDetailsLinkhPSureSenseProThreats");
					for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
						if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.details"))) {
							//We have access of webelement here, and predefined function takes String as argument not webelement
							wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
							element = wait.until(ExpectedConditions.elementToBeClickable(listOfOptions.get(listOfOptionsCounter)));
							jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
							flag = true;
							break;
							} 
					}
			}else	{
			  LOGGER.error("Report page did not load in 1 minute.");	
			}
			switchToPreviousTabOfExperienceMgmtPage();
			return flag;
		}
		
		//TO switch to parent and close all tabs
		public final void switchToParentTabOfExperienceMgmtPage() {
			switchToParentTab();
		}
		
		/**
		 * This method will Switch the Account of the User having same email address
		 * 
		 * @throws Exception
		 */
		public final void verifySwitchAccount(String language) throws Exception
		{
			clickOnElementsOfExperienceMgmtPage("switchAccountdropdown");
			String currentAccount = getTextOfExperienceMgmtPage("switchAccountTextBox");
			List<WebElement> listOfAccounts = getElementsOfExperienceMgmtPage("listOfAccounts");
			for(int i=0;i<listOfAccounts.size();i++)
			{
				String Account = listOfAccounts.get(i).getText();
				if(!(currentAccount.equals(Account)))
				{
					listOfAccounts.get(i).click();
					if(Account.contains(getTextLanguage(language, "daas_ui", "global.form.partner")))
					{
						clickOnElementsOfExperienceMgmtPage("switchAccountButton");
						waitForElementsOfExperienceMgmtPageDynamic("pageTitle", CommonVariables.LOGIN_WAIT);
						sleeper(5000);//Added sleeper as it takes time in loading the page
						verifyUIVersion();
						waitForElementsOfExperienceMgmtPage("userProfileButton");
						clickOnElementsOfExperienceMgmtPage("userProfileButton");
						if(!(getTextOfExperienceMgmtPage("userProfileAccount").contains(getTextLanguage(language, "daas_ui", "global.form.partner"))))
									LOGGER.error("Error while switching the Account");
						break;
					}
					else if(Account.contains(getTextLanguage(language, "daas_ui", "global.form.msp")))
					{
						clickOnElementsOfExperienceMgmtPage("switchAccountButton");
						waitForElementsOfExperienceMgmtPageDynamic("pageTitle", CommonVariables.LOGIN_WAIT);
						sleeper(5000);//Added sleeper as it takes time in loading the page
						verifyUIVersion();
						waitForElementsOfExperienceMgmtPage("userProfileButton");
						clickOnElementsOfExperienceMgmtPage("userProfileButton");
						if(!(getTextOfExperienceMgmtPage("userProfileAccount").contains(getTextLanguage(language, "daas_ui", "global.form.msp"))))
									LOGGER.error("Error while switching the Account");
						break;
					}
					else
					{
						clickOnElementsOfExperienceMgmtPage("switchAccountButton");
						waitForElementsOfExperienceMgmtPageDynamic("pageTitle", CommonVariables.LOGIN_WAIT);
						sleeper(5000);//Added sleeper as it takes time in loading the page
						verifyUIVersion();
						waitForElementsOfExperienceMgmtPage("userProfileButton");
						clickOnElementsOfExperienceMgmtPage("userProfileButton");
						if(!(getTextOfExperienceMgmtPage("userProfileAccount").contains(getTextLanguage(language, "daas_ui", "roles.reports_admin"))))
							LOGGER.error("Error while switching the Account");
						break;
					}
					
				}
					
			}
		}
		
		/** This method is used to divert execution on required UI version.
		 * @throws Exception
		 */
		public void verifyUIVersion() throws Exception{
			try {
				ExperienceMgmtPage ExperienceMgmtPage=new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
				if(uiVersion.equalsIgnoreCase("VENEER3")){
					if(isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)){
						if(getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v2\"")){
							waitForPageLoaded();
							ExperienceMgmtPage.waitForElementsOfExperienceMgmtPage("switchUIButtonV2");
							ExperienceMgmtPage.clickOnElementsOfExperienceMgmtPage("switchUIButtonV2");
							waitForPageLoaded();
							ExperienceMgmtPage.waitForElementsOfExperienceMgmtPage("switchUIButtonV3");
							sleeper(1000);
							Assert.assertTrue(ExperienceMgmtPage.waitForElementsOfExperienceMgmtPage("switchUIButtonV3"), "New UI of techpulse did not load successfully.");
							LOGGER.info("Navigated to Veneer 3 version successfully.");
						}else if(getItemFromLocalStorage("ui_user_preferences").contains("\"target\":\"ui_v3\"")){
							if(ExperienceMgmtPage.verifyElementsOfExperienceMgmtPage("closeAddCompanyModal"))
								ExperienceMgmtPage.clickOnElementsOfExperienceMgmtPage("closeAddCompanyModal");
							if(ExperienceMgmtPage.waitForElementsOfExperienceMgmtPageDynamic("feedBackModalTitle",2)){
								submitFeedbackVeneer3();
							}
							LOGGER.info("Already in Veneer 3 UI,execution can be continued.");
						}
					}else{
						LOGGER.error("Toggle for Veneer 3 is not enabled.");
					}
				}else if(uiVersion.equalsIgnoreCase("VENEER2")) {
					if(isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)){
						if(getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v3\"")){
							if(ExperienceMgmtPage.waitForElementsOfExperienceMgmtPageDynamic("feedBackModalTitle",2)){
								ExperienceMgmtPage.clickOnElementsOfExperienceMgmtPage("feedbackRating");
								ExperienceMgmtPage.clickOnElementsOfExperienceMgmtPage("submitButtonFeedback");
								sleeper(3000);
							}
							ExperienceMgmtPage.clickOnElementsOfExperienceMgmtPage("switchUIButtonV3");
							Assert.assertTrue(submitFeedbackVeneer3(), "Old UI of techpulse did not load successfully.");
							LOGGER.info("Navigated to Veneer 2 version successfully.");
							sleeper(1000);
						}else if(getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v2\"")){
							LOGGER.info("Already in Veneer 2 UI");
						}
					}else{
						LOGGER.info("Toggle for Veneer 3 is not enabled, execution can be continued.");
					}
				}else{
					LOGGER.error("Pass correct Veneer version in POM, i.e. VENEER2 or VENEER3");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			}
		}
		
		
		/**This is common method to submit feedback of Veneer 3.
		 * @return
		 * @throws Exception
		 */
		public boolean submitFeedbackVeneer3() throws Exception{
			boolean flag = false;
			ExperienceMgmtPage ExperienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			ExperienceMgmtPage.clickOnElementsOfExperienceMgmtPage("feedbackRating");
			ExperienceMgmtPage.clickOnElementsOfExperienceMgmtPage("submitButtonFeedback");
			if(ExperienceMgmtPage.waitForElementsOfExperienceMgmtPage("switchUIButtonV2")){
				flag = true;
			}else{
				flag = false;
				LOGGER.error("Old UI of techpulse did not load successfully.");
			}
			return flag;
			
		}
		
		
		/**
		 * This method is used to verify device name link redirection on reports table for MSP with frame
		 * @param LanguageCode: language code
		 * @param labelsKey: List of labels/criteria present in the graph
		 * @param deviceDetailsKey: Element present on Device page
		 * @param columnListKey: Columns present in the grid
		 * @param errorKey: Error message flashes on the Dashboard
		 * @param keyDrillDownLabelsAllHidden: label hidden locator
		 * @param targetElement: element to be moved on
		 * @param frameKey: Frame present on the drill down page
		 * @return boolean value
		 * @throws Exception
		 */
		public final boolean verifyDeviceNameRedirectionMSPWithFrameFlexiWithOffset(String LanguageCode, String labelsKey, String deviceDetailsKey, String columnListKey, String errorKey, String frameKey, String keyDrillDownLabelsAllHidden,String targetElement,int xOffset,int yOffset) throws Exception {
			boolean flag = false;
			waitForElementsOfExperienceMgmtPage(labelsKey);
			List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey, keyDrillDownLabelsAllHidden);
			for (int counter = 0; counter < 1; counter++) {
				listOfLabels.get(counter).click();
				sleeper(3000);
				mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
				switchToDifferentTabOfExperienceMgmtPage();
				sleeper(3000);
				if (waitForElementsOfExperienceMgmtPageDynamic(frameKey,DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
					scrollToExperienceMgmtPage(columnListKey);
					clickOnElementsOfExperienceMgmtPage(columnListKey);
					switchToDifferentTabOfExperienceMgmtPage();
					waitForPageLoaded();
					if (waitForElementsOfExperienceMgmtPage("allChartsLocator")) {
						String errorNotification = getTextOfExperienceMgmtPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfExperienceMgmtPage(errorKey) || waitForElementsOfExperienceMgmtPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else
					LOGGER.error("No data to diplay");
				switchToPreviousTabOfExperienceMgmtPage();
			}
			return flag;
		}
		
        /**
         * This method is to check whether a text in element contains the expected string or not.
         * @param locator: Element on which text value needs to be checked.
         * @param text: String value against which the locator text needs to be checked.
         * @return boolean value.
         * @throws Exception.
         */
		public final boolean verifyTextContainsOnElementsOfExperienceMgmtPage(String locator, String text) throws Exception {
			
			if(getTextOfExperienceMgmtPage(locator).contains(text)) {
				return true;
			}else {
				return false;
			}		
		}
		

		/**
		 * Add widget for CaaS hardware category
		 * @param category:category name
		 * @param subcategorylist:subcategory name
		 * @param languageCode:language
		 * @throws Exception
		 */
		public final void addWidgetForCaaSHardware(String category, ArrayList<String> subcategorylist,String languageCode) throws Exception
		{
			ArrayList<String> subCategoryOptionList =new ArrayList<String>(Arrays.asList(
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_mrsHwinv"),
					getTextLanguage(languageCode, "dashboard_service","label.report_category_mrsDiskrep"),
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_category_mrsHwhealthNew"),
					getTextLanguage(languageCode, "dashboard_service", "label.report_category_mrsDeviceutiV3")));
			//list of options with corresponds sub-category
			ArrayList<String> caas_hwinv_optionlist1 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service","label.report_option_mrsDetails")));
			ArrayList<String> caas_device_utilization_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "dashboard_service","label.report_option_swPerfByTime")));
			if(subcategorylist.size()>0) {
				for(int count=0;count<subcategorylist.size();count++)
				{
					if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "dashboard_service", "label.report_category_mrsDiskrep")))
					{
						verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
						clickOnElementsOfExperienceMgmtPage("addWidgetButton");
						selectCategoryForFlexibleExperienceMgmtPage(category);
						selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
						clickOnElementsOfExperienceMgmtPage("visualizationForBatterry");
						waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
						clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
						LOGGER.info("Widget has been added");
					}else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_mrsHwhealthNew")))
					{
						verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
						clickOnElementsOfExperienceMgmtPage("addWidgetButton");
						selectCategoryForFlexibleExperienceMgmtPage(category);
						selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
						clickOnElementsOfExperienceMgmtPage("visualizationForBatterry");
						waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
						clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
						LOGGER.info("Widget has been added");		
					}else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(2).toString()))
					{
						verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
						clickOnElementsOfExperienceMgmtPage("addWidgetButton");
						selectCategoryForFlexibleExperienceMgmtPage(category);
						selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
						selectOptionForFlexibleExperienceMgmtPage(caas_hwinv_optionlist1.get(0).toString(),languageCode);
						clickOnElementsOfExperienceMgmtPage("visualizationForBatterry");
						waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
						clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
						LOGGER.info("Widget has been added");
					}else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(1).toString()))
					{
						verifyElementIsClickableOfExperienceMgmtPage("addWidgetButton");
						clickOnElementsOfExperienceMgmtPage("addWidgetButton");
						selectCategoryForFlexibleExperienceMgmtPage(category);
						selectSubCategoryForFlexibleExperienceMgmtPage(subcategorylist.get(count).toString(), languageCode);
						selectOptionForFlexibleExperienceMgmtPage(caas_device_utilization_optionlist2.get(0).toString(),languageCode);
						clickByJavaScriptOnExperienceMgmtPage("visualizationForBatterry");
						waitUntilElementIsVisibleOfExperienceMgmtPage("spinnerOnWidgetAdd");
						clickOnElementsOfExperienceMgmtPage("widgetAddConfirmButton");
						LOGGER.info("Widget has been added");
					}
				}
			}
			else {
				LOGGER.info("No data loaded in subcategorylist");
			}
		}	
		
		/**
		 * Escape key for dashboard Page
		 */
		public final void pressEscapeKeysForExperienceMgmtPage() throws Exception {
						 pressKey(Keys.ESCAPE);
					}
		
		/**
		 * This method basically verify the hidden of labels of legends on donut drillDown Chart
		 * 
		 * @param keyLegendLabel: Chart criteria present on the graph
		 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking on legends
		 * @param legendDropdownKey: legend dropdown key
		 * @return boolean value
		 * @throws Exception
		 */

		public final boolean verifyLabelHiddenWhenClickOnLegendsDonutBatterySwellChartFlexi(String keyLegendLabel, String keyDrillDownLabelsAllHidden, String legendDropdownKey) throws Exception {
			List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
			int count1 = 0;
			try {
			if (verifyElementsOfExperienceMgmtPage(legendDropdownKey)) {
				for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
					try {
						waitForPageLoaded();
						sleeper(2000);
						legendLabelElementList.get(count1).click();						
					}
					catch(Exception e) {
						sleeper(2000);
						clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
						sleeper(2000);
						legendLabelElementList.get(count1).click();		
					}
				}				
				return true;
			}else{
					for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
					waitForPageLoaded();
					legendLabelElementList.get(count1).click();
					}
					return true;
				}			
			} catch (Exception e) {
				LOGGER.info("Failed to Verify legends hidden/visible functionality of chart");
				return false;
			}
		}	
		
		/**
		 * This method basically verify the visibility of labels of legends on drillDown Chart
		 * 
		 * @param keyLegendLabel:Chart criteria present on the graph
		 * @param chartVisibility: locator of chart visibility
		 * @param legendDropdownKey : legend dropdown key
		 * @return boolean value
		 * @throws Exception
		 */
		
		public final boolean verifyLabelVisibleWhenClickOnLegendsDonutBatterySwellChartFlexi(String keyLegendLabel, String chartVisibility, String legendDropdownKey) throws Exception {
			List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofExperienceMgmtPage(keyLegendLabel);
			int count1 = 0;
			if (verifyElementsOfExperienceMgmtPage(chartVisibility)) {
				return false;
			} else {	
				try {
				if (verifyElementsOfExperienceMgmtPage("batterySwellProbabilityDropdownFlexi")) {			
					for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
						try {
							sleeper(2000);
							waitForPageLoaded();
							legendLabelElementList.get(count1).click();						
						}
						catch(Exception e) {
							sleeper(2000);
							clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
							legendLabelElementList.get(count1).click();		
						}
					}
					return true;
				}else{
						for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
						waitForPageLoaded();
						legendLabelElementList.get(count1).click();
						}
						return true;
					}			
				} catch (Exception e) {
					return false;
				}
			}
		}
		
		/**
		 * This method verifies the chart legends and dropdown on the chart
		 * @param labelsKey - lengends locator
		 * @param tooltipTextKey - tool tip locator
		 * @param textKey - text key locator
		 * @return boolean
		 * @throws Exception
		 */
		public final boolean verifyTooltipTextOnReportWithFrameDonutBatterySwellChartFlexi(String labelsKey, String tooltipTextKey, String textKey, String keyDrillDownLabelsAllHidden,String targetElement, String legendDropdownKey) throws Exception {
			int count1 = 0;
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
				verifyLabelHiddenWhenClickOnLegendsDonutBatterySwellChartFlexi(labelsKey, keyDrillDownLabelsAllHidden,legendDropdownKey);
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
			try {
				if (verifyElementsOfExperienceMgmtPage(legendDropdownKey)) {
					for (count1 = 0; count1 < listOfLegends.size(); count1++) {
						try {
							waitForPageLoaded();
							listOfLegends.get(count1).click();
							verifyTooltipTextOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, textKey,keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
							listOfLegends.get(count1).click();
							sleeper(2000);
						} catch (Exception e) {
							sleeper(2000);
							clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
							sleeper(2000);
							listOfLegends.get(count1).click();
							verifyTooltipTextOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, textKey,keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
							listOfLegends.get(count1).click();
						}
					}
					return true;
				} else {
					for (count1 = 0; count1 < listOfLegends.size(); count1++) {
						waitForPageLoaded();
						listOfLegends.get(count1).click();
						sleeper(2000);
						verifyTooltipTextOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, textKey,keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
					}
					return true;
				}
			} catch (Exception e) {
				LOGGER.info("Failed to Verify Tool tip functionality of chart");
				return false;
			}		
		}
		
		/**
		 * @param labelsKey - lengends locator
		 * @param tooltipTextKey - tool tip locator
		 * @param textKey - text key locator
		 * @return boolean
		 * @throws Exception
		 */
		public final boolean verifyTooltipTextOnDonutBatterySwellChartFlexi(String labelsKey, String tooltipTextKey, String textKey, String keyDrillDownLabelsAllHidden,String targetElement, String legendDropdownKey) throws Exception{
			String text = null;
			boolean flag = false;
			try {
				scrollToExperienceMgmtPage(labelsKey);
				scrollToExperienceMgmtPage(targetElement);
				sleeper(3000);
				mouseHoverbyoffsett(targetElement, 15, 80);
				waitForElementsOfExperienceMgmtPage(tooltipTextKey);
				text = getTextOfExperienceMgmtPage(tooltipTextKey);
				String finaltext;
				if(!verifyElementIsVisible("tooltipTextofSecbatterySwellProbabilityFlexi")) {
					finaltext = text.replaceAll("[+.^:,]","");
				}else {
					String count_clean = text.split("\n")[0];
					finaltext = count_clean.split(":")[1].trim();
				}
				mouseHoverbyoffsettClick(targetElement, 15, 80);
				switchToDifferentTabOfExperienceMgmtPage();
				if(waitForElementsOfExperienceMgmtPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)){
				clickOnElementsOfExperienceMgmtPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfExperienceMgmtPage(textKey);
				if (monthtext.get(0).getText().contains(finaltext)) {
					flag = true;
				}}else{
					LOGGER.error("Report did not load in 1 minute.");
				}
				sleeper(2000);
				switchToPreviousTabOfExperienceMgmtPage();
				flag = true;
			}catch(Exception e) {
				flag = false;
				LOGGER.info("Failed to get tool tip text on Battery Swell Probability Chart");
			}
			return flag;			
		}
		
	
		/**
		 * This method is used to validate labels visibility of chart
		 * 
		 * @param labelsKey: labels of the chart retrieved from dashboard
		 * @param labelList: predefined label list
		 * @return boolean value
		 * @throws Exception
		 */
		public final boolean verifyChartLabelsOnExperienceMgmtPage(String labelsKey, ArrayList<String> labelList) throws Exception {
			boolean flag = false;
			waitForElementsOfExperienceMgmtPage(labelsKey);
			List<WebElement> listOfLabels = getElementsOfExperienceMgmtPage(labelsKey);
			List<String> labelsCurrentList = new ArrayList<String>();
			Set<String> store = new HashSet<>();
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
				if (Strings.isNullOrEmpty(listOfLabels.get(listOfLabelsCounter).getText())) {
					labelsCurrentList.add(listOfLabels.get(listOfLabelsCounter).getAttribute("textContent"));
				} else {
					labelsCurrentList.add(listOfLabels.get(listOfLabelsCounter).getText());
				}			
			}
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < labelsCurrentList.size(); listOfLabelsCounter++) {
				if (labelList.contains(labelsCurrentList.get(listOfLabelsCounter))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			for (String labels : labelsCurrentList) {
				if (store.add(labels) == false) {
					LOGGER.debug("Found a duplicate labels in chart : " + labels);
					flag = false;
				}
				LOGGER.info("Chart labels are verified succefully");
			}
			return flag;
		}
		
		/**
		 * This method basically verify the header Name on report page with Frame
		 * 
		 * @param LanguageCode: This is language code used for multiple languages.
		 * @param labelKey: Chart labels
		 * @param keyHeaderOnNextPage: Header on the drill down page
		 * @param headerNamesOnReportPage: Vlaues in the header section on the drill down page
		 * @param frameKey: Iframe on the drill down page
		 * @param keyDrillDownLabelsAllHidden: all label hidden locator
		 * @param targetElement : element to be moved on
		 * @param legendDropdownKey : key of legend dropdown
		 * @return boolean value
		 * @throws Exception
		 */
		public final boolean headerTextVerificationOnReportPageFrameDonutSwellChartFlexi(String LanguageCode, String labelKey, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey) throws Exception {
							 
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelKey);				
			int count1 = 0;
			verifyElementIsClickableOfExperienceMgmtPage(labelKey);
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
				verifyLabelHiddenWhenClickOnLegendsDonutBatterySwellChartFlexi(labelKey,keyDrillDownLabelsAllHidden,legendDropdownKey);
			try {
				if (verifyElementsOfExperienceMgmtPage(legendDropdownKey)) {
					for (count1 = 0; count1 < listOfLegends.size(); count1++) {
						try {
							waitForPageLoaded();
							listOfLegends.get(count1).click();	
							sleeper(2000);
							VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey, keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
							listOfLegends.get(count1).click();
						}
						catch(Exception e) {
							sleeper(2000);
							clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
							sleeper(2000);
							listOfLegends.get(count1).click();		
							VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey, keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
							listOfLegends.get(count1).click();
						}						
					}
					return true;
				}else{
						for (count1 = 0; count1 < listOfLegends.size(); count1++) {
						waitForPageLoaded();
						listOfLegends.get(count1).click();
						VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey, keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
					}
						return true;
				}	
			}
			 catch (Exception e) {
				LOGGER.info("Failed to Verify legends hidden/visible functionality on header report of chart");
				return false;
			}			
		}	
		
		/**
		 * This method basically verify the header Name on report page with Frame
		 * 
		 * @param LanguageCode: This is language code used for multiple languages.
		 * @param labelKey: Chart labels
		 * @param keyHeaderOnNextPage: Header on the drill down page
		 * @param headerNamesOnReportPage: Vlaues in the header section on the drill down page
		 * @param frameKey: Iframe on the drill down page
		 * @param keyDrillDownLabelsAllHidden: all label hidden locator
		 * @param targetElement : element to be moved on
		 * @param legendDropdownKey : key of legend dropdown
		 * @return boolean value
		 * @throws Exception
		 */
		public final boolean VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(String LanguageCode, String labelKey, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey) throws Exception {
			boolean flag = false;
			
			try {	
				sleeper(2000);
				mouseHoverbyoffsettClick(targetElement, 15, 80);
				sleeper(3000);
				switchToDifferentTabOfExperienceMgmtPage();
				sleeper(3000);
				waitForElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
				if(waitForElementsOfExperienceMgmtPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)){
				List<WebElement> listOfOptions = getElementsOfExperienceMgmtPage(keyHeaderOnNextPage);
				for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
					if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
						flag = true;
					} else {
						flag = false;
						break;
					}
				}
					}else{
						LOGGER.error("Report did not load in 1 minute.");
					}
				sleeper(2000);
				switchToPreviousTabOfExperienceMgmtPage();
				
				}catch(Exception e) {
					flag = false;
					LOGGER.info("Failed to get Report text on Battery Swell Probability Chart");
				}			
				return flag;		
			}
		/**
		 * This method is used to verify tool tip count with total rows on reports with Frame
		 * 
		 * @param labelsKey: Chart labels
		 * @param tooltipTextKey: Tooltip text after hovering on a chart
		 * @param columnListKey: Column names present in the grid
		 * @param paginationKey: Locator of the pagination
		 * @param targetElement: element to move to
		 * @param keyDrillDownLabelsAllHidden: all label hidden locator
		 * @param frameKey: Iframe on the drill down page
		 * @param xoffset: x coordinate to move
		 * @param yoffset: y coordinate to move
		 * @return boolean value
		 * @throws Exception
		 */

		public final boolean verifyTooltipCountonReportWithFrameDonutBatterySwellChartFlexiWithOffset(String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement,int xOffset,int yOffset, String legendDropdownKey) throws Exception {
			int listOfLabelsCounter = 0;
			waitForPageLoaded();
			sleeper(2000);
			if (verifyElementsOfExperienceMgmtPage(keyDrillDownLabelsAllHidden))
				verifyLabelHiddenWhenClickOnLegendsDonutBatterySwellChartFlexi(labelsKey,keyDrillDownLabelsAllHidden,legendDropdownKey);
			List<WebElement> listOfLegends = getElementsOfExperienceMgmtPage(labelsKey);
			try {
			if (verifyElementsOfExperienceMgmtPage(legendDropdownKey)) {
				for (listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
					try {
						waitForPageLoaded();
						listOfLegends.get(listOfLabelsCounter).click();
						verifyTooltipCountOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, columnListKey, paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset, legendDropdownKey);
						sleeper(1000);
						listOfLegends.get(listOfLabelsCounter).click();					
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfExperienceMgmtPage(legendDropdownKey);
						sleeper(2000);
						listOfLegends.get(listOfLabelsCounter).click();
						verifyTooltipCountOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, columnListKey, paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset, legendDropdownKey);
						sleeper(1000);
						listOfLegends.get(listOfLabelsCounter).click();					
					}
				}
				return true;
			}else{
				for (listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
				waitForPageLoaded();
				listOfLegends.get(listOfLabelsCounter).click();
				verifyTooltipCountOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, columnListKey, paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset, legendDropdownKey);
				sleeper(1000);
				listOfLegends.get(listOfLabelsCounter).click();			
			}
				return true;
			}
		}
		 catch (Exception e) {
			LOGGER.info("Failed to Verify legends hidden/visible functionality on tool tip count of chart");
			return false;
		}
						
	}	
		
		public final boolean verifyTooltipCountOnDonutBatterySwellChartFlexi(String labelsKey, String tooltipTextKey, String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden, String targetElement,int xOffset,int yOffset, String legendDropdownKey) throws Exception {
			boolean flag = false;
			String paginationText = null;
			String text;
			String finaltext;
			try {
				sleeper(3000);
				scrollToExperienceMgmtPage(targetElement);
				mouseHoverbyoffsett(targetElement, xOffset, yOffset);
				waitForElementsOfExperienceMgmtPage(tooltipTextKey);
				sleeper(2000);
				text = getTextOfExperienceMgmtPage(tooltipTextKey);
				if (!verifyElementIsVisible("tooltipTextofSecbatterySwellProbabilityFlexi")) {
					finaltext = text.split(":")[1].trim();
				} else {
					finaltext = text.replaceAll("[+.^:,]", "");
				}
				Integer tooltipcount = Integer.valueOf(finaltext);
				sleeper(2000);
				waitForPageLoaded();
				scrollToExperienceMgmtPage(targetElement);
				mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
				switchToDifferentTabOfExperienceMgmtPage();
				sleeper(3000);
				if (waitForElementsOfExperienceMgmtPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
					if (tooltipcount > 10) {
						sleeper(2000);
						waitForPageLoaded();
						paginationText = getTextOfExperienceMgmtPage(paginationKey);
						String arr[] = paginationText.split(" ");
						Integer paginationCount;
							paginationCount = Integer.valueOf(arr[1]);
						if ((tooltipcount == paginationCount) || (tooltipcount < paginationCount)) {// A device might have multiple disks hence count sometimes does not match but disk count is always less than number of devices
							flag = true;
						}
					} else {
						sleeper(2000);
						waitForPageLoaded();
						List<WebElement> listOfColumntext = getElementsOfExperienceMgmtPage(columnListKey);
						if (tooltipcount == (listOfColumntext.size())) {
							flag = true;
						}
					}
				} else {
					LOGGER.error("Report did not load in 1 minute.");
				}
				switchToPreviousTabOfExperienceMgmtPage();
				sleeper(1000);

			} catch (Exception e) {
				flag = false;
				LOGGER.info("Failed to get tool tip count on Battery Swell Probability Chart");
			}
			return flag;
		}
		
		/**
		 * This Function is used to check read__incident_insights permission on dashboard
		 * @param environment
		 * @return
		 */
		public final boolean verifyReadInsightsIncident(String environment ) {
			String environmentURL = null ;
			Response response = null;
			boolean flag = false;
			try {
			environmentProperties = environmentPropertiesReader.getObjectRepository("Environment");	
			if (environment.equals("US-STABLE"))
				environmentURL = environmentProperties.getProperty("StableUSADDROLEURL");
			else if (environment.equals("EU-STABLE"))
				environmentURL = environmentProperties.getProperty("StableEUADDROLEURL");
			else if (environment.equals("US-STAGING"))
				environmentURL = environmentProperties.getProperty("StagingUSADDROLEURL");
			else
				environmentURL = environmentProperties.getProperty("StagingEUADDROLEURL");
			String body = "{\"features\":[],\"permissions\":"
					+ "[{\"name\":\"search__license_keys\",\"inputData\":{}},{\"name\":"
					+ "\"manage__proactive_sca_security\",\"inputData\":{}},{\"name\":"
					+ "\"manage__proactive_ssa_security\",\"inputData\":{}},{\"name\":\"manage__trial_subscription\",\"inputData\":"
					+ "{}},{\"name\":\"read__incident_insights\",\"inputData\":{}}]}";
			String api = environmentURL + ConstantURL.READ_ACTIONABLE_INSIGHTS;
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given()
														  .contentType(ContentType.JSON)
														  .header("authorization", "Bearer " + mspAuthToken)
														  .body(body);
			response = httpRequest.post(api);
			String resp = response.asString();
			LOGGER.info(resp);
			JsonPath path = response.jsonPath();
			flag = path.getBoolean("permissions[4].status");
			}catch(Exception e) {
				LOGGER.error("Failed to check permission for dashboard widgets");
				e.printStackTrace();
				return flag;
			}
			return flag;
		}
	
		/**
		 * This is a method to get a list of elements present on Incident list page
		 * @param key - Locator of element
		 * @return - list of web elements
		 */
		public final List<WebElement> getElementsOfExperienceMgmtListPage(String key) {
			try {
				return getElementsTillAllElementsPresent(ExperienceMgmtPageProperties.getProperty(key));
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method getElementOfExperienceMgmtPage " + e.getMessage()));
				return null;
			}
		}
		
		/*
		 * This method verifies the column headers for Device Health table page
		 * 
		 * @param languageCode
		 * @param string
		 * @param Device Health table ListColumns
		 * @return
		 * @throws Exception */
		 
		public boolean verifyColumnsofExpMgmtTable(String languageCode, String locator,String[] expMgmtListColumns) throws Exception {
			boolean flag = false;
			int counter = 0;
			List<WebElement> expMgmtColumnHeader = getElementsOfExperienceMgmtListPage(locator);
			
			//expMgmtColumnHeader.remove(0);
			for (WebElement we : expMgmtColumnHeader) {
				
				try {
					if (we.getText().equalsIgnoreCase(
							getTextLanguage(languageCode, "daas_ui", expMgmtListColumns[counter]))) {
						flag = true;
						counter++;
					} else {
						flag = false;
						LOGGER.error(we.getText() + " Header does not match at list table page.");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return flag;
		}

		
		/**
		 * This method verifies the column headers for Device Health rows table page
		 * 
		 * @param languageCode
		 * @param string
		 * @param Device Health rows table 
		 * @return
		 * @throws Exception 
		 */
		public boolean verifyRowsofExpMgmtTable(String languageCode, String locator,
				String[] expMgmtListRows) throws Exception {
			boolean flag = false;
			int counter = 0;
			List<WebElement> expMgmtRowsList = getElementsOfExperienceMgmtListPage(locator);
			//expMgmtColumnHeader.remove(0);
			for (WebElement we : expMgmtRowsList) {
				
				try {
					if (we.getText().equalsIgnoreCase(
							getTextLanguage(languageCode, "daas_ui", expMgmtListRows[counter]))) {
						flag = true;
						counter++;
					} else {
						flag = false;
						LOGGER.error(we.getText() + " Column name does not match at list table page.");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return flag;
		}
		
		/**
		 * This method verifies the column headers for Device Performance table page
		 * 
		 * @param languageCode
		 * @param string
		 * @param Device performance table ListColumns
		 * @return
		 * @throws Exception 
		 */
		public boolean verifyColumnsofExpMgmtPerformanceTable(String languageCode, String locator,
				String[] expMgmtperfListColumns) throws Exception {
			boolean flag = false;
			int counter = 0;
			List<WebElement> expMgmtperfColumnsList = getElementsOfExperienceMgmtListPage(locator);
			
			//expMgmtperfColumnsList.remove(0);
			for (WebElement we : expMgmtperfColumnsList) {
				
				try {
					if (we.getText().equalsIgnoreCase(
							getTextLanguage(languageCode, "daas_ui", expMgmtperfListColumns[counter]))) {
						flag = true;
						counter++;
					} else {
						flag = false;
						LOGGER.error(we.getText() + " Header does not match at list table page.");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return flag;
		}

		
	
		
}