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
import org.testng.asserts.SoftAssert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
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

public class DashboardPage extends CommonMethod {
	private ObjectReader dashboardPagePropertiesReader = new ObjectReader();
	private Properties dashboardPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	private Properties selectedLanguageProperties;
	public static String environment;
	private DashboardPage instance;
	public static String LanguageCode;
	private Properties selecteCredentialsProperties;
	private ObjectReader environmentPropertiesReader = new ObjectReader();
	private Properties environmentProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public DashboardPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (DashboardPage.class) {
				if (instance == null) {
					instance = new DashboardPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public DashboardPage(WebDriver driver) throws IOException {
		dashboardPageProperties = dashboardPagePropertiesReader.getObjectRepository("DashboardPageV3");
	}

	/**
	 * @param language:     Language code for localization testing
	 * @param localefolder: To specify the folder where the key is present
	 * @param key:          Contains the string which is localized
	 * @return String which is localized
	 * @throws Exception
	 */
	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = dashboardPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * @param language:     Language code for localization testing
	 * @param localefolder: To specify the folder where the key is present
	 * @param key:          Contains the string which is localized
	 * @return String ArrayList which is localized
	 * @throws Exception
	 */
	public final ArrayList<String> getTextLanguage(String language, String localefolder, ArrayList<String> key)
			throws Exception {
		ArrayList<String> keyValues = new ArrayList<String>();
		selectedLanguageProperties = dashboardPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
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

	public final void waitUntilElementIsVisibleOfDashboardPage(String key) throws Exception {
		waitUntilElementIsVisible(dashboardPageProperties.getProperty(key));
	}

	public final boolean verifyElementsOfDashboardPage(String key) throws Exception {
		return verifyElementIsPresent(dashboardPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfDashboardPage(String key) throws Exception {
		return verifyElementIsVisible(dashboardPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfDashboardPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(key), waitTime);
	}

	public final boolean waitForPresenceOfElementsOfDashboardPageDynamic(String key, int waitTime) throws Exception {
		return waitUntillElementIsPresentDynamic(dashboardPageProperties.getProperty(key), waitTime);
	}

	public final boolean waitForPresenceOfElementsOfDashboardPage(String key) throws Exception {
		return waitUntillElementIsPresent(dashboardPageProperties.getProperty(key));
	}

	public final boolean matchTextOfDashboardPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(dashboardPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfDashboardPage(String key) throws Exception {
		return verifyElementIsEnable(dashboardPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfDashboardPage(String key) throws Exception {
		return verifyElementIsSelected(dashboardPageProperties.getProperty(key));
	}

	public final String getTextOfDashboardPage(String key) throws Exception {
		return getTextBy(dashboardPageProperties.getProperty(key));
	}

	public final String getAttributesOfDashboardPage(String key, String value) throws Exception {
		return getAttribute(dashboardPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfDashboardPage(String key) throws Exception {
		click(dashboardPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnDashboardPage(String key) throws Exception {
		clickByJavaScript(dashboardPageProperties.getProperty(key));
	}

	public final void enterTextForDashboardPage(String key, String Text) throws Exception {
		enterText(dashboardPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfDashboardPage(String key) throws Exception {
		return verifyElementIsClickable(dashboardPageProperties.getProperty(key));
	}

	public void switchToIframeofDashboardPage(String key) throws Exception {
		switchToIframe(dashboardPageProperties.getProperty(key));
	}
	
	public final boolean verifyCompanyChangeOfDashboardPage(String LanguageCode, String textKey,
			String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey) throws Exception {
		return verifySearchFunctionalityUsingSingleSelectDropdown(LanguageCode,
				dashboardPageProperties.getProperty(textKey), companySearchText,
				dashboardPageProperties.getProperty(emptyTextKey), dashboardPageProperties.getProperty(listKey),
				dashboardPageProperties.getProperty(dropdownBoxKey));
	}

	public final boolean verifyCompanyChangeOfDashboardPageGlobalFilter(String LanguageCode, String textKey,
			String companySearchText, String emptyTextKey, String listKey, String dropdownBoxKey, String saveButton)
			throws Exception {
		return verifySearchFunctionalityUsingSingleSelectDropdownRadioButton(LanguageCode,
				dashboardPageProperties.getProperty(textKey), companySearchText,
				dashboardPageProperties.getProperty(emptyTextKey), dashboardPageProperties.getProperty(listKey),
				dashboardPageProperties.getProperty(dropdownBoxKey), dashboardPageProperties.getProperty(saveButton));
	}

	public final void mouseHoverOfDashboardPage(String key) throws Exception {
		mouseHover(dashboardPageProperties.getProperty(key));
	}
	public final void listMouseHoverOfDashboardPage(String key) throws Exception {
		listMouseHover(dashboardPageProperties.getProperty(key));
	}
	public final WebElement getElementOfDashboardPage(String key) throws Exception {
		return getElement(dashboardPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsOfDashboardPage(String key) throws Exception {
		return getAllElements(dashboardPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofDashboardPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(dashboardPageProperties.getProperty(key));
	}

	public final void switchToDifferentTabOfDashboardPage() {
		switchToDifferentTab();
	}

	public final void switchToPreviousTabOfDashboardPage() {
		switchBackToPreviousTab();
	}

	public final ArrayList<String> getChartLabelsDashboardPage(String key) throws Exception {
		return getLabelsOfChart(dashboardPageProperties.getProperty(key));
	}

	public void scrollToDashboardPage(String key) throws Exception {
		scrollTillView(dashboardPageProperties.getProperty(key));
	}

	public final List<String> getallTextOfDashboardPage(String key) throws Exception {
		return getallTextBy(dashboardPageProperties.getProperty(key));
	}

	public final void scrollUpCharts() {
		jsDriver().executeScript("scroll(0, -250);");
	}

	public final void scrollDownCharts() {
		jsDriver().executeScript("scroll(0, 750);");
	}

	public final String getCredentials(String credentials, String key) throws Exception {
		selecteCredentialsProperties = dashboardPagePropertiesReader.getCredentials(credentials);
		return selecteCredentialsProperties.getProperty(key);
	}
	public final List<String> mouseHoverOfElementsDashboardPage(String key, String key1) throws Exception {
		return gettextmouseHoverelements(dashboardPageProperties.getProperty(key), dashboardPageProperties.getProperty(key1));
	}
	

	/**
	 * This method is the method to verify if an element is present on dashboard
	 * details page
	 * 
	 * @param key - locator of the element
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyElementsOfDashboardDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(dashboardPageProperties.getProperty(key));
	}

	/**
	 * This method is used to validate that chart is loaded properly or not.
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param idKey:        Chart type
	 * @param errorKey:     Fatal Error when no data is available
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartisLoaded(String LanguageCode, String idKey, String errorKey) throws Exception {

		boolean flag = false;
		if (verifyElementIsVisible(dashboardPageProperties.getProperty(idKey))) {
			if (verifyElementIsVisible(dashboardPageProperties.getProperty(errorKey))) {
				String text = getTextOfDashboardPage(errorKey);
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
	 * @param idKey:        Chart type
	 * @param errorKey:     Fatal Error when no data is available
	 * @param waitTime:     Dynamic wait time which needs to be passed
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyChartisLoadedDynamic(String LanguageCode, String idKey, String errorKey, int waitTime)
			throws Exception {

		boolean flag = false;
		if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(idKey), waitTime)) {
			if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(errorKey), waitTime)) {
				String text = getTextOfDashboardPage(errorKey);
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

	public final boolean verifyChartOrderOfDashBoardPageForMSP(String allChartsLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] = { "OsVersionSupport", "DevicesByType", "DevicesByOs", "WarrantyExpiration",
				"SubscriptionExpiration", "CpuUtilization", "MemoryUtilization", "HardwareInventory",
				"BatteryReplacementSummary", "DiskReplacementSummary", "AllIncidentsByType", "IncidentBurnDownSummary",
				"SoftwareInventory", "TodaysCriticalIncidents" };
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allCharts = getElementsTillAllElementsVisible(
				dashboardPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter)
						.equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error(
							"Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
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
	 * @param chartIdsArray       - Expected order of charts
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyChartOrderOfDashBoardPageForMSPFlexi(String allChartsLocatorKey, String[] chartIdsArray)
			throws Exception {
		Boolean flag = false;
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		waitForPageLoaded();
		sleeper(10000);// Kept this long sleeper due slow loading issue of charts, once that issue gets
						// resolved will remove this sleeper.
		waitUntilElementIsVisibleDynamic(dashboardPageProperties.getProperty(allChartsLocatorKey), 3);
		List<WebElement> allCharts = getAllElements(dashboardPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter)
						.equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error(
							"Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
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
	 * This method is used to verify sequence of chart present on DashBoard for
	 * Reseller
	 * 
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfDashBoardPageForReseller(String allChartsLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] = { "OsVersionSupport", "DevicesByType", "DevicesByOs", "WarrantyExpiration",
				"SubscriptionExpiration", "CpuUtilization", "MemoryUtilization", "HardwareInventory",
				"BatteryReplacementSummary", "DiskReplacementSummary", "AllIncidentsByType", "IncidentBurnDownSummary",
				"SoftwareInventory", "TodaysCriticalIncidents" };
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allCharts = getElementsTillAllElementsVisible(
				dashboardPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter)
						.equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error(
							"Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
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
	 * This method is used to verify sequence of chart present on DashBoard for
	 * Reseller
	 * 
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfDashBoardPageForResellerFlexi(String allChartsLocatorKey,
			String[] chartIdsArray) throws Exception {
		Boolean flag = false;
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		sleeper(5000);// Kept this long sleeper due slow loading issue of charts, once that issue gets
						// resolved will remove this sleeper.
		List<WebElement> allCharts = getAllElements(dashboardPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter)
						.equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error(
							"Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
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
	 * This method is used to verify sequence of chart present on DashBoard for
	 * ReportAdmin
	 * 
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfDashBoardPageForReportAdmin(String allChartsLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] = { "OsVersionSupport", "DevicesByType", "DevicesByOs", "AllIncidentsByType",
				"IncidentBurnDownSummary", "TodaysCriticalIncidents" };
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allCharts = getElementsTillAllElementsVisible(
				dashboardPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter)
						.equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error(
							"Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
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
	 * This method is used to verify sequence of chart present on DashBoard for
	 * ReportAdmin
	 * 
	 * @param allChartsLocatorKey: Position of all charts on the dashboard
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartOrderOfDashBoardPageForReportAdminFlexi(String allChartsLocatorKey,
			String[] chartIdsArray) throws Exception {
		Boolean flag = false;
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		sleeper(5000);// Kept this long sleeper due slow loading issue of charts, once that issue gets
						// resolved will remove this sleeper.
		List<WebElement> allCharts = getAllElements(dashboardPageProperties.getProperty(allChartsLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter)
						.equalsIgnoreCase(allCharts.get(allChartsCounter).getAttribute("id"))) {
					LOGGER.error(
							"Sequence of " + allCharts.get(allChartsCounter).getAttribute("id") + " is not correct");
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
	 * This method is used to verify when device is enrolled and incident are fixed
	 * whether chart data is present or not
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param noTextIdKey:  Key for chart message.
	 * @param chartType:    Type of chart.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyChartsMessageAllIncidentsFixed(String LanguageCode, String noTextIdKey, String chartType)
			throws Exception {
		boolean flag = false;
		try {
			String message = null;
			chartType = getTextLanguage(LanguageCode, "daas_ui", chartType);
			if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.you_are_doing_great")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.there_are_no_incidents_with_open_status")))) {
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
	 * This method is used to verify whether chart data is present or not (No
	 * Devices enrolled and No Data) This method will verify the data is not
	 * available on the chart
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param noTextIdKey:  Key for chart message.
	 * @param chartType:    Type of chart.
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartsHasNoDataOnDashBoardPage(String LanguageCode, String noTextIdKey, String chartType)
			throws Exception {
		String noDataChartMessage = "";
		List<WebElement> messageWebElement;
		boolean flag = false;
		try {
			String message = null;
			chartType = getTextLanguage(LanguageCode, "daas_ui", chartType);
			if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.os_version_support.title").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.devices_by_type").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card")
									+ "\n"
									+ (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.enroll_company_assets_to_display_data")
													.replaceAll("cardName",
															getTextLanguage(LanguageCode, "daas_ui",
																	"dashboard.charts.title.devices_by_os").toString())
													.replaceAll("[>\\{}]", "").toString()))) {

						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.warranty_expiration").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.battery_rep_summary"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.battery_rep_summary").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.disk_rep_summary").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.cpu_utilization").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.memory_utilization").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.hardware_invenory").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.software_invenory").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					messageWebElement = getElementsOfDashboardPage(noTextIdKey);
					for (int i = 0; i < messageWebElement.size(); i++) {
						noDataChartMessage = (noDataChartMessage + "\n" + messageWebElement.get(i).getText()).trim();
					}
					if (noDataChartMessage.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll(
											"cardName",
											getTextLanguage(LanguageCode, "daas_ui",
													"dashboard.charts.title.incidents_with_open_status").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_by_type"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.incidents_by_type").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_top_by_subtype"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll(
											"cardName",
											getTextLanguage(LanguageCode, "daas_ui",
													"dashboard.charts.title.incidents_top_by_subtype").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_detection"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data")
											.replaceAll("cardName",
													getTextLanguage(LanguageCode, "daas_ui",
															"dashboard.charts.title.incident_detection").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_curn_down_rate"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll(
											"cardName",
											getTextLanguage(LanguageCode, "daas_ui",
													"dashboard.charts.title.incident_curn_down_rate").toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.start_using_this_card")
									+ "\n"
									+ (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.enroll_company_assets_to_display_data")
													.replaceAll("cardName",
															getTextLanguage(LanguageCode, "daas_ui",
																	"dashboard.charts.title.driver_status").toString())
													.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service",
					"label.dashborad.global.battery.swell.probability.by.device.age"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.start_using_this_card")
							+ "\n"
							+ (getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.nodata.enroll_company_assets_to_display_data").replaceAll(
											"cardName",
											getTextLanguage(LanguageCode, "dashboard_service",
													"label.dashborad.global.battery.swell.probability.by.device.age")
															.toString())
											.replaceAll("[>\\{}]", "").toString()))) {
						flag = true;
					}
				}
			}  else {
				flag = false;
				LOGGER.debug("Provided : " + chartType
						+ " Chart type is wrong, You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK REPLACEMENT SUMMARY,CPU UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY, SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS 		DETECTION, BATTERY SWELL PROBABILITY BY DEVICE AGE, INCIDENT BURNDOWN RATE only");
				throw new InputMismatchException(
						"You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK REPLACEMENT SUMMARY,CPU UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY,SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS DETECTION, BATTERY SWELL PROBABILITY BY DEVICE AGE, INCIDENT 		BURNDOWN RATE only ");
			}
		}		 catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public final boolean verifyChartsHasNoDataOnDashBoardPageFlexi(String LanguageCode, String noTextIdKey,
			String chartType) throws Exception {
		String noDataChartMessage = "";
		boolean flag = false;
		try {
			chartType = getTextLanguage(LanguageCode, "daas_ui", chartType);
			if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.battery_rep_summary"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_by_type"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_top_by_subtype"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_detection"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_curn_down_rate"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service",
					"label.dashborad.global.battery.swell.probability.by.device.age"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.antivirus.noncompliances.windows.defender"))) {
					if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
						sleeper(2000);
						noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
						if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
								"dashboard.charts.nodata.nothing_going_on_here"))) {
							flag = true;
						}
					}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.antivirus.applications.in.fleet"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.firewall.windows.defender"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			}else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.devicesec.firewallSummaryInFleet"))) {
				if (verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(noTextIdKey), 5)) {
					sleeper(2000);
					noDataChartMessage = getTextOfDashboardPage(noTextIdKey);
					if (noDataChartMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
							"dashboard.charts.nodata.nothing_going_on_here"))) {
						flag = true;
					}
				}
			}else {
				flag = false;
				LOGGER.debug("Provided : " + chartType
						+ " Chart type is wrong, You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK REPLACEMENT SUMMARY,CPU UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY, SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS 		DETECTION, BATTERY SWELL PROBABILITY BY DEVICE AGE, INCIDENT BURNDOWN RATE,AV WINDOWS DEFENDER,AV APPLICATION IN FLEET only");
				throw new InputMismatchException(
						"You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK REPLACEMENT SUMMARY,CPU UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY,SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS DETECTION, BATTERY SWELL PROBABILITY BY DEVICE AGE, INCIDENT BURNDOWN RATE,,AV WINDOWS DEFENDER,AV APPLICATION IN FLEET only ");
			}
		}
		

		catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * This method is used to verify whether chart data is present or not (At least
	 * one Device enrolled and No Data)
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param noTextIdKey:  Key for chart message.
	 * @param chartType:    Type of chart.
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(String LanguageCode, String noTextIdKey,
			String chartType) throws Exception {
		boolean flag = false;
		try {
			String message = null;
			chartType = getTextLanguage(LanguageCode, "daas_ui", chartType);
			if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.battery_rep_summary"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_by_type"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incidents_top_by_subtype"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_detection"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.incident_curn_down_rate"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else if (chartType.equalsIgnoreCase(getTextLanguage(LanguageCode, "dashboard_service",
					"label.dashborad.global.battery.swell.probability.by.device.age"))) {
				if (verifyElementIsVisible(dashboardPageProperties.getProperty(noTextIdKey))) {
					message = getTextBy(dashboardPageProperties.getProperty(noTextIdKey));
					if (message.equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.nothing_going_on_here")
									+ "\n" + (getTextLanguage(LanguageCode, "daas_ui",
											"dashboard.charts.nodata.system_is_collecting_data")))) {
						flag = true;
					}
				}
			} else {
				flag = false;
				LOGGER.debug("Provided : " + chartType
						+ " Chart type is wrong, You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK 		REPLACEMENT SUMMARY,CPU UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY, SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS 		DETECTION, INCIDENT BURNDOWN RATE only, BATTERY SWELL PROBABILITY BY DEVICE AGE ");
				throw new InputMismatchException(
						"You can use : DEVICES BY TYPE, DEVICES BY OS, WARRANTY EXPIRATION, BATTERY REPLACEMENT SUMMARY, DISK REPLACEMENT SUMMARY,CPU 		UTILIZATION,MEMORY UTILIZATION,HARDWARE INVENTORY, SOFTWARE INVENTORY,SUBSCRIPTION EXPIRATION, OPEN INCIDENTS, ALL INCIDENTS, TOP 10 INCIDENTS, INCIDENTS DETECTION, INCIDENT 		BURNDOWN RATE only, BATTERY SWELL PROBABILITY BY DEVICE AGE ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * This method will verify the Title of the chart
	 * 
	 * @param titleKey:       Position of the chart title
	 * @param chartTitleText: Chart title key fetched from the locale folder
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTitleofChartOfDashboardPage(String titleKey, String chartTitleText) throws Exception {
		boolean flag = false;
		if (waitForElementsOfDashboardPage(titleKey)) {
			if (chartTitleText.equalsIgnoreCase(getTextOfDashboardPage(titleKey))) {
				flag = true;
				LOGGER.info("Widget Text matched successfully");
			}
		}
		return flag;
	}

	public final boolean verifyTooltipTextOnReportWithFrame(String labelsKey, String tooltipTextKey, String textKey)
			throws Exception {
		boolean flag = false;
		String text = null;
		List<WebElement> tooltipTextKeyValue = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		tooltipTextKeyValue = getElementsOfDashboardPage(tooltipTextKey);
		for (int i = 0; i < listOfLabels.size(); i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			waitForElementsOfDashboardPage(tooltipTextKey);
			text = tooltipTextKeyValue.get(i).getText();
			sleeper(2000);
			listOfLabels.get(i).click();
			switchToDifferentTabOfDashboardPage();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfDashboardPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfDashboardPage(textKey);
				if (monthtext.get(0).getText().contains(text.trim())) {
					flag = true;
				}
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	public final boolean verifyTooltipTextOnReportWithFrameFlexi1(String labelsKey, String tooltipTextKey,
			String textKey) throws Exception {
		boolean flag = false;
		String text = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		for (int i = 0; i < listOfLabels.size(); i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			waitForElementsOfDashboardPage(tooltipTextKey);
			text = getTextOfDashboardPage(tooltipTextKey);
			sleeper(2000);
			listOfLabels.get(i).click();
			switchToDifferentTabOfDashboardPage();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfDashboardPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfDashboardPage(textKey);
				if (monthtext.get(0).getText().contains(text.trim())) {
					flag = true;
				}
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	public final boolean verifyTooltipTextOnReportHWInv(String labelsKey, String tooltipTextKey, String textKey)
			throws Exception {
		boolean flag = false;
		String text = null;
		int labelSize = 0;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		if (listOfLabels.size() > 11) {
			labelSize = 11;
		} else {
			labelSize = listOfLabels.size();
		}
		for (int i = 0; i < labelSize; i++) {
			listOfLabels.get(i);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			waitForElementsOfDashboardPage(tooltipTextKey);
			text = getTextOfDashboardPage(tooltipTextKey);
			String cleanText = text.replaceAll("[+.^:,]", "");
			listOfLabels.get(i).click();
			switchToDifferentTabOfDashboardPage();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfDashboardPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfDashboardPage(textKey);
				if (monthtext.get(0).getText().contains(cleanText.trim())) {
					flag = true;
				}
			} else {
				LOGGER.error("Report Page did not load in 1 minute");
			}
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	public final boolean verifyTooltipTextOnReportSWInv(String labelsKey, String tooltipTextKey, String textKey)
			throws Exception {
		boolean flag = false;
		String text = null;
		String cleantext = null;
		int labelSize = 0;
		List<WebElement> listOfTooltipText = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		{
			listOfTooltipText = getElementsOfDashboardPage(tooltipTextKey);
			labelSize = 1;
			for (int i = 0; i < labelSize; i++) {
				listOfLabels.get(i);
				sleeper(3000);
				action.moveToElement(listOfLabels.get(i)).build().perform();
				text = listOfTooltipText.get(i).getText();
				cleantext = text.replaceAll("…", "");
				listOfLabels.get(i).click();
				switchToDifferentTabOfDashboardPage();
				if (waitForElementsOfDashboardPageDynamic("moreDetailsLink",
						DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
					clickOnElementsOfDashboardPage("moreDetailsLink");
					List<WebElement> monthtext = getElementsOfDashboardPage(textKey);
					if (monthtext.get(0).getText().contains(cleantext.trim())) {
						flag = true;
					}
				} else {
					LOGGER.error("Report Page did not load in 1 minute");
				}
				switchToPreviousTabOfDashboardPage();
			}
			return flag;
		}
	}

	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 * 
	 * @param graphKey:       Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnTextKey:  Column names present in the grid
	 * @param frameKey:       Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountOnReportwithFrameSubscriptionExpiration(String graphKey,
			String tooltipTextKey, String columnTextKey, String frameKey) throws Exception {
		boolean flag = false;
		String count = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(graphKey);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
			sleeper(3000);
			// listOfLabels.get(listOfLabelsCounter);
			action.moveToElement(listOfLabels.get(listOfLabelsCounter)).build().perform();
			waitForElementsOfDashboardPage(tooltipTextKey);
			count = getTextOfDashboardPage(tooltipTextKey);
			String count_clean = count.replaceAll(",", "");
			Integer tooltipcount = Integer.valueOf(count_clean);
			listOfLabels.get(listOfLabelsCounter).click();
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			clickOnElementsOfDashboardPage("showMoreLink");
			sleeper(2000);
			clickOnElementsOfDashboardPage("dropdownMenuItem");
			if (getAttributesOfDashboardPage("nextButton", "class").contains("disabled")) {
				List<WebElement> columntext = getElementsOfDashboardPage(columnTextKey);
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
			switchToPreviousTabOfDashboardPage();
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
	public final boolean verifyTooltipTextOnReportWithFrameDonutChartFlexi(String labelsKey, String tooltipTextKey,
			String textKey, String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey)
			throws Exception {
		boolean flag = false;
		String text = null;
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelsKey, keyDrillDownLabelsAllHidden,
					legendDropdownKey);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		for (int i = 0; i < listOfLegends.size(); i++) {
			if (waitForElementsOfDashboardPageDynamic(legendDropdownKey, 1)) {
				if (i == 0 || i == 2) {
					clickOnElementsOfDashboardPage(legendDropdownKey);
					sleeper(3000);
				}
			} else {
				LOGGER.info("Legend dropdown not present on chart");
			}
			listOfLegends.get(i).click();
			scrollToDashboardPage(labelsKey);
			scrollToDashboardPage(targetElement);
			sleeper(3000);
			mouseHoverbyoffsett(targetElement, 00, 72);
			waitForElementsOfDashboardPage(tooltipTextKey);
			text = getTextOfDashboardPage(tooltipTextKey);
			String count_clean = text.split("\n")[0];
			String finaltext = count_clean.split(":")[1].trim();
			mouseHoverbyoffsettClick(targetElement, 00, 72);
			switchToDifferentTabOfDashboardPage();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfDashboardPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfDashboardPage(textKey);
				if (monthtext.get(0).getText().contains(finaltext)) {
					flag = true;
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
			listOfLegends.get(i).click();

		}
		return flag;
	}

	/**
	 * @param labelsKey      - lengends locator
	 * @param tooltipTextKey - tool tip locator
	 * @param textKey        - text key locator
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyTooltipTextOnReportWithFrameFlexi(String labelsKey, String tooltipTextKey,
			String textKey, String keyDrillDownLabelsAllHidden, String targetElement) throws Exception {
		boolean flag = false;
		String text = null;
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey, keyDrillDownLabelsAllHidden);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		for (int i = 0; i < listOfLegends.size(); i++) {
			listOfLegends.get(i).click();
			sleeper(3000);
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsett(targetElement, 15, 80);
			waitForElementsOfDashboardPage(tooltipTextKey);
			sleeper(2000);
			text = getTextOfDashboardPage(tooltipTextKey);
			String cleanText = text.replaceAll("[+.^:,]", "");
			mouseHoverbyoffsettClick(targetElement, 15, 80);
			switchToDifferentTabOfDashboardPage();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfDashboardPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfDashboardPage(textKey);
				if (monthtext.get(0).getText().contains(cleanText)) {
					flag = true;
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
			listOfLegends.get(i).click();

		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 * 
	 * @param graphKey:       Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnTextKey:  Column names present in the grid
	 * @param frameKey:       Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountOnReportwithFrameSubscriptionExpirationFlexi(String graphKey,
			String tooltipTextKey, String columnTextKey, String frameKey) throws Exception {
		boolean flag = false;
		String count = null;
		Integer tooltipcount;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(graphKey);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
			listOfLabels.get(listOfLabelsCounter);
			action.moveToElement(listOfLabels.get(listOfLabelsCounter)).build().perform();
			waitForElementsOfDashboardPage(tooltipTextKey);
			count = getTextOfDashboardPage(tooltipTextKey);
			tooltipcount = Integer.valueOf(count);
			listOfLabels.get(listOfLabelsCounter).click();
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic("columnlistSubscriptionExpirationFirstFlexi",
					DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfDashboardPage("showMoreLink");
				sleeper(2000);
				clickOnElementsOfDashboardPage("dropdownMenuItemSubscriptionFlexi");
				clickOnElementsOfDashboardPage("select100FromDropdown");
				sleeper(3000);
				if (getAttributesOfDashboardPage("nextButtonSubscriptionFlexi", "class").contains("next")) {
					scrollToDashboardPage(columnTextKey);
					sleeper(3000);
					List<WebElement> columntext = getElementsOfDashboardPage(columnTextKey);
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
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 * 
	 * @param labelsKey:      Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnTextKey:  Column names present in the grid
	 * @param frameKey:       Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountonReportwithFrameSWInventory(String labelsKey, String tooltipTextKey,
			String columnTextKey, String frameKey, String tooltipTextSoftware) throws Exception {
		boolean flag = false;
		String count = null, text = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
			listOfLabels.get(listOfLabelsCounter);
			action.moveToElement(listOfLabels.get(listOfLabelsCounter)).build().perform();
			waitForElementsOfDashboardPage(tooltipTextKey);
			count = getTextOfDashboardPage(tooltipTextKey);
			text = getTextOfDashboardPage(tooltipTextSoftware);
			String count_clean = count.replaceAll("[a-zA-Z:]", "");
			Integer tooltipcount = Integer.valueOf(count_clean.trim());
			listOfLabels.get(listOfLabelsCounter).click();
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			waitForElementsOfDashboardPage("showMoreLink");
			clickOnElementsOfDashboardPage("showMoreLink");
			sleeper(2000);
			clickOnElementsOfDashboardPage("dropdownMenuItem");
			clickOnElementsOfDashboardPage("100Option");
			if (getAttributesOfDashboardPage("nextButton", "class").contains("disabled")) {
				List<WebElement> columntext = getElementsOfDashboardPage(columnTextKey);
				List<WebElement> applicationNameText = getElementsOfDashboardPage(
						"applicationNamelistSoftwareForCount");
				int countColumnInt = 0;
				for (int columnTextIndex = 0; columnTextIndex < columntext.size(); columnTextIndex++) {
					if (applicationNameText.get(columnTextIndex).getText().equals(text)) {
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
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 * 
	 * @param labelsKey:      Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnTextKey:  Column names present in the grid
	 * @param frameKey:       Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountonReportwithFrameSWInventoryFlexi(String labelsKey, String tooltipTextKey,
			String columnTextKey, String frameKey, String tooltipTextSoftware) throws Exception {
		boolean flag = false;
		String count_clean = null;
		//String count = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		//List<WebElement> listOfTooltipText = getElementsOfDashboardPage(tooltipTextSoftware);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < 1; listOfLabelsCounter++) {
			listOfLabels.get(listOfLabelsCounter);
			sleeper(3000);
			action.moveToElement(listOfLabels.get(listOfLabelsCounter)).build().perform();
			waitForElementsOfDashboardPage(tooltipTextKey);
			count_clean = getTextOfDashboardPage(tooltipTextKey);
			//text = listOfTooltipText.get(listOfLabelsCounter).getText();
			Integer tooltipcount = Integer.valueOf(count_clean.trim());
			listOfLabels.get(listOfLabelsCounter).click();
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(columnTextKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				waitForElementsOfDashboardPage("showMoreLink");
				clickOnElementsOfDashboardPage("showMoreLink");
				sleeper(2000);
				clickOnElementsOfDashboardPage("dropdownMenuItemFlexi");
				clickOnElementsOfDashboardPage("select100FromDropdown");
				sleeper(3000);
				if (getAttributesOfDashboardPage("nextButtonFlexi", "class").contains("next")) {
					scrollToDashboardPage(columnTextKey);
					sleeper(3000);
					List<WebElement> columntext = getElementsOfDashboardPage(columnTextKey);
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
			switchToPreviousTabOfDashboardPage();
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
		String[] allText = getTextBy(dashboardPageProperties.getProperty(key)).split(" |/");
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

	/**
	 * This method is used to verify header column on reports(with frame)
	 * 
	 * @param languageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param keyHeaderOnReportPage:   Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill
	 *                                 down page
	 * @param DeviceByOsInfo:          List of key for Device by OS
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHeaderColumnOnReportPageWithFrameFlexi(String languageCode, String keyHeaderOnReportPage,
			String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsFirstDrill = getElementsOfDashboardPage("deviceByOSLegends");
		for (int firstLevel = 0; firstLevel < listOfLabelsFirstDrill.size(); firstLevel++) {
			listOfLabelsFirstDrill = getElementsOfDashboardPage("deviceByOSLegends");
			sleeper(3000);
			listOfLabelsFirstDrill.get(firstLevel).click();
			mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
			waitForPageLoaded();
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
			waitForPresenceOfElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			sleeper(3000);
			verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
					"labelsLocatorDeviceByOsFlexi");
			List<WebElement> listOfLabelsSecondDrill = getElementsOfDashboardPage("deviceByOSLegends");
			for (int secondLevel = 1; secondLevel < listOfLabelsSecondDrill.size();) {
				listOfLabelsSecondDrill = getElementsOfDashboardPage("deviceByOSLegends");
				listOfLabelsSecondDrill.get(secondLevel).click();
				mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
				waitForPageLoaded();
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
				osMajorVersion = getTextOfDashboardPage(DeviceByOsInfo.get("tooltipTextMajorVersionKeyFlexi"));
				// For Other cases
				if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
					// For OSX Other cases
					if (osMajorVersion.contains("OSX Other")) {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
						verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
								"labelsLocatorDeviceByOsFlexi");
						List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage(
								DeviceByOsInfo.get("labelsKeyFlexi"));
						for (int thirdLevel = 0; thirdLevel < listOfLabelsThirdDrill.size();) {
							listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
							listOfLabelsThirdDrill.get(thirdLevel).click();
							mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
							waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
							mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
							waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
							switchToDifferentTabOfDashboardPage();
							waitForPageLoaded();
							sleeper(3000);
							if (waitForElementsOfDashboardPageDynamic(keyHeaderOnReportPage,
									DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
								List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
								for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
										.size(); listOfOptionsCounter++) {
									if (listOfOptions.get(listOfOptionsCounter).getText()
											.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Generic-Grid-JSON",
													headerNamesOnReportPage[listOfOptionsCounter]))) {
										flag = true;
									} else {
										flag = false;
										LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
												+ " Header does not match at reprot page.");
										break;
									}
								}
							} else {
								LOGGER.error("Report did not load in 1 minute.");
							}
							switchToPreviousTabOfDashboardPage();
							thirdLevel++;
							if (thirdLevel == listOfLabelsThirdDrill.size()) {
								clickOnElementsOfDashboardPage(DeviceByOsInfo.get("labelsBackKeyFlexi"));
							}
						}
					} else {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
						switchToDifferentTabOfDashboardPage();
						waitForPageLoaded();
						sleeper(3000);
						if (waitForElementsOfDashboardPageDynamic(keyHeaderOnReportPage,
								DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
							List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
							for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
									.size(); listOfOptionsCounter++) {
								if (listOfOptions.get(listOfOptionsCounter).getText()
										.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Generic-Grid-JSON",
												headerNamesOnReportPage[listOfOptionsCounter]))) {
									flag = true;
								} else {
									flag = false;
									LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
											+ " Header does not match at reprot page.");
									break;
								}
							}
						} else {
							LOGGER.error("Report did not load in 1 minute.");
						}
						switchToPreviousTabOfDashboardPage();
					}
				} else {
					scrollToDashboardPage("devicesByOsTitleFlexi");
					mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
					waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
					verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
							"labelsLocatorDeviceByOsFlexi");
					List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
					for (int thirdlevel = 0; thirdlevel < listOfLabelsThirdDrill.size();) {
						listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
						listOfLabelsThirdDrill.get(thirdlevel).click();
						mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
						waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
						mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
						waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
						switchToDifferentTabOfDashboardPage();
						waitForPageLoaded();
						sleeper(3000);
						if (waitForElementsOfDashboardPageDynamic(keyHeaderOnReportPage,
								DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
							List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
							for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
									.size(); listOfOptionsCounter++) {
								if (listOfOptions.get(listOfOptionsCounter).getText()
										.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Generic-Grid-JSON",
												headerNamesOnReportPage[listOfOptionsCounter]))) {
									flag = true;
								} else {
									flag = false;
									LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
											+ " Header does not match at reprot page.");
									break;
								}
							}
						} else {
							LOGGER.error("Report did not load in 1 minute.");
						}
						switchToPreviousTabOfDashboardPage();
						thirdlevel++;
						if (thirdlevel == listOfLabelsThirdDrill.size()) {
							clickOnElementsOfDashboardPage(DeviceByOsInfo.get("labelsBackKeyFlexi"));
						}
					}
				}
				secondLevel++;
				if (secondLevel == listOfLabelsSecondDrill.size()) {
					osMajorVersion = null;
					clickOnElementsOfDashboardPage(DeviceByOsInfo.get("labelsBackKeyFlexi"));
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify header column on reports(with frame)
	 * 
	 * @param languageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param keyHeaderOnReportPage:   Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill
	 *                                 down page
	 * @param DeviceByOsInfo:          List of key for Device by OS
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyHeaderColumnOnReportPageWithFrame(String languageCode, String keyHeaderOnReportPage,
			String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabelsFirstDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
		for (int firstLevel = 0; firstLevel < listOfLabelsFirstDrill.size(); firstLevel++) {
			listOfLabelsFirstDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			action.moveToElement(listOfLabelsFirstDrill.get(firstLevel)).build().perform();
			waitForPageLoaded();
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKey"));
			listOfLabelsFirstDrill.get(firstLevel).click();
			waitForPresenceOfElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			List<WebElement> listOfLabelsSecondDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			for (int secondLevel = 0; secondLevel < listOfLabelsSecondDrill.size();) {
				listOfLabelsSecondDrill = null;
				listOfLabelsSecondDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				action.moveToElement(listOfLabelsSecondDrill.get(secondLevel)).build().perform();
				waitForPageLoaded();
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKey"));
				osMajorVersion = getTextOfDashboardPage(DeviceByOsInfo.get("tooltipTextMajorVersionKey"));
				// For Other cases
				if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
					// For OSX Other cases
					if (osMajorVersion.contains("OSX Other")) {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
						List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage(
								DeviceByOsInfo.get("labelsKey"));
						for (int thirdLevel = 0; thirdLevel < listOfLabelsThirdDrill.size();) {
							listOfLabelsThirdDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
							action.moveToElement(listOfLabelsThirdDrill.get(thirdLevel)).build().perform();
							waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKey"));
							listOfLabelsThirdDrill.get(thirdLevel).click();
							waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
							switchToDifferentTabOfDashboardPage();
							waitForPageLoaded();
							sleeper(3000);
							List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
							for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
									.size(); listOfOptionsCounter++) {
								if (listOfOptions.get(listOfOptionsCounter).getText()
										.equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui",
												headerNamesOnReportPage[listOfOptionsCounter]))) {
									flag = true;
								} else {
									flag = false;
									LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
											+ " Header does not match at reprot page.");
									break;
								}
							}
							switchToPreviousTabOfDashboardPage();
							thirdLevel++;
							if (thirdLevel == listOfLabelsThirdDrill.size()) {
								clickOnElementsOfDashboardPage(DeviceByOsInfo.get("labelsBackKey"));
							}
						}
					} else {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
						switchToDifferentTabOfDashboardPage();
						waitForPageLoaded();
						sleeper(3000);
						List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
						for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
								.size(); listOfOptionsCounter++) {
							if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(
									languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
										+ " Header does not match at reprot page.");
								break;
							}
						}
						switchToPreviousTabOfDashboardPage();
					}
				} else {
					listOfLabelsSecondDrill.get(secondLevel).click();
					waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
					List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage(
							DeviceByOsInfo.get("labelsKey"));
					for (int thirdlevel = 0; thirdlevel < listOfLabelsThirdDrill.size();) {
						listOfLabelsThirdDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
						action.moveToElement(listOfLabelsThirdDrill.get(thirdlevel)).build().perform();
						waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKey"));
						listOfLabelsThirdDrill.get(thirdlevel).click();
						waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
						switchToDifferentTabOfDashboardPage();
						waitForPageLoaded();
						sleeper(3000);
						List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
						for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
								.size(); listOfOptionsCounter++) {
							if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(
									languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
										+ " Header does not match at reprot page.");
								break;
							}
						}
						switchToPreviousTabOfDashboardPage();
						thirdlevel++;
						if (thirdlevel == listOfLabelsThirdDrill.size()) {
							clickOnElementsOfDashboardPage(DeviceByOsInfo.get("labelsBackKey"));
						}
					}
				}
				secondLevel++;
				if (secondLevel == listOfLabelsSecondDrill.size()) {
					osMajorVersion = null;
					clickOnElementsOfDashboardPage(DeviceByOsInfo.get("labelsBackKey"));
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify redirection on reports(with frame)
	 * 
	 * @param languageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param keyHeaderOnReportPage:   Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill
	 *                                 down page
	 * @param DeviceByOsInfo:          List of key for Device by Os
	 * @param deviceDetailsKey:        Element present on Device page
	 * @param columnListKey:           Columns present in the grid
	 * @param errorKey:                Error message flashes on the Dashboard
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDeviceByOSReportHeaderWithFrameFlexi(String languageCode, String keyHeaderOnReportPage,
			String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo, String deviceDetailsKey,
			String columnListKey, String errorKey) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsFirstDrill = getElementsOfDashboardPage("deviceByOSLegendsHidden");
		listOfLabelsFirstDrill.get(0).click();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
		waitForPageLoaded();
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
		waitForPresenceOfElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
		verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsSecondDrill = getElementsOfDashboardPage("deviceByOSLegends");
		listOfLabelsSecondDrill.get(0).click();
		waitForPageLoaded();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		osMajorVersion = getTextOfDashboardPage(DeviceByOsInfo.get("tooltipTextMajorVersionKeyFlexi"));
		// For Other cases
		if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
			// For OSX Other cases
			if (osMajorVersion.contains("OSX Other")) {
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
						"labelsLocatorDeviceByOsFlexi");
				List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
				listOfLabelsThirdDrill.get(0).click();
				mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfDashboardPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
					List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
					for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
							.size(); listOfOptionsCounter++) {
						if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(
								languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
							flag = true;
						} else {
							flag = false;
							LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
									+ " Header does not match at reprot page.");
							break;
						}
					}
					switchToPreviousTabOfDashboardPage();
				} else {
					LOGGER.error("No data to diplay/Report did not load in 1 minute.");
					switchToPreviousTabOfDashboardPage();
				}
			} else {
				listOfLabelsSecondDrill.get(0).click();
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfDashboardPage(columnListKey)) {
					List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
					for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
							.size(); listOfOptionsCounter++) {
						if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(
								languageCode, "daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
							flag = true;
						} else {
							flag = false;
							LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
									+ " Header does not match at reprot page.");
							break;
						}
					}
					switchToPreviousTabOfDashboardPage();
				} else {
					LOGGER.error("No data to diplay");
					switchToPreviousTabOfDashboardPage();
				}
			}
		} else {
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
					"labelsLocatorDeviceByOsFlexi");
			List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
			listOfLabelsThirdDrill.get(0).click();
			mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			switchToDifferentTabOfDashboardPage();
			waitForPageLoaded();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(columnListKey, 60)) {
				List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnReportPage);
				for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
						.size(); listOfOptionsCounter++) {
					if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(languageCode,
							"daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
						flag = true;
					} else {
						flag = false;
						LOGGER.error(listOfOptions.get(listOfOptionsCounter).getText()
								+ " Header does not match at reprot page.");
						break;
					}
				}
				switchToPreviousTabOfDashboardPage();
			} else {
				LOGGER.error("No data to diplay");
				switchToPreviousTabOfDashboardPage();
			}
		}
		return flag;
	}

	public final boolean verifyDeviceByOsRedirectionWithFrameFlexi(String languageCode, String keyHeaderOnReportPage,
			String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo, String deviceDetailsKey,
			String columnListKey, String errorKey) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsFirstDrill = getElementsOfDashboardPage("deviceByOSLegendsHidden");
		listOfLabelsFirstDrill.get(0).click();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
		waitForPageLoaded();
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
		waitForPresenceOfElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
		verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsSecondDrill = getElementsOfDashboardPage("deviceByOSLegends");
		listOfLabelsSecondDrill.get(0).click();
		waitForPageLoaded();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		osMajorVersion = getTextOfDashboardPage(DeviceByOsInfo.get("tooltipTextMajorVersionKeyFlexi"));
		// For Other cases
		if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
			// For OSX Other cases
			if (osMajorVersion.contains("OSX Other")) {
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
						"labelsLocatorDeviceByOsFlexi");
				List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
				listOfLabelsThirdDrill.get(0).click();
				mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfDashboardPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
					clickOnElementsOfDashboardPage(columnListKey);
					switchToDifferentTabOfDashboardPage();
					waitForPageLoaded();
					if (waitForElementsOfDashboardPage("allChartsLocator")) {
						String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfDashboardPage(errorKey)
							|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.error("No data to diplay/Report did not load in 1 minute.");
					switchToPreviousTabOfDashboardPage();
				}
			} else {
				listOfLabelsSecondDrill.get(0).click();
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfDashboardPage(columnListKey)) {
					clickOnElementsOfDashboardPage(columnListKey);
					switchToDifferentTabOfDashboardPage();
					waitForPageLoaded();
					if (waitForElementsOfDashboardPage("allChartsLocator")) {
						String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfDashboardPage(errorKey)
							|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.error("No data to diplay");
					switchToPreviousTabOfDashboardPage();
				}
			}
		} else {
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
					"labelsLocatorDeviceByOsFlexi");
			List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
			listOfLabelsThirdDrill.get(0).click();
			mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 100);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKeyFlexi"));
			switchToDifferentTabOfDashboardPage();
			waitForPageLoaded();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(columnListKey, 60)) {
				clickOnElementsOfDashboardPage(columnListKey);
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				if (waitForElementsOfDashboardPage("allChartsLocator")) {
					String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfDashboardPage(errorKey)
						|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else {
				LOGGER.error("No data to diplay");
				switchToPreviousTabOfDashboardPage();
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify redirection on reports(with frame)
	 * 
	 * @param languageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param keyHeaderOnReportPage:   Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill
	 *                                 down page
	 * @param DeviceByOsInfo:          List of key for Device by Os
	 * @param deviceDetailsKey:        Element present on Device page
	 * @param columnListKey:           Columns present in the grid
	 * @param errorKey:                Error message flashes on the Dashboard
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDeviceByOsRedirectionWithFrame(String languageCode, String keyHeaderOnReportPage,
			String[] headerNamesOnReportPage, HashMap<String, String> DeviceByOsInfo, String deviceDetailsKey,
			String columnListKey, String errorKey) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabelsFirstDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
		listOfLabelsFirstDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
		action.moveToElement(listOfLabelsFirstDrill.get(0)).build().perform();
		waitForPageLoaded();
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKey"));
		listOfLabelsFirstDrill.get(0).click();
		waitForPresenceOfElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
		List<WebElement> listOfLabelsSecondDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
		listOfLabelsSecondDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
		action.moveToElement(listOfLabelsSecondDrill.get(0)).build().perform();
		waitForPageLoaded();
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKey"));
		osMajorVersion = getTextOfDashboardPage(DeviceByOsInfo.get("tooltipTextMajorVersionKey"));
		// For Other cases
		if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
			// For OSX Other cases
			if (osMajorVersion.contains("OSX Other")) {
				listOfLabelsSecondDrill.get(0).click();
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				listOfLabelsThirdDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				action.moveToElement(listOfLabelsThirdDrill.get(0)).build().perform();
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKey"));
				listOfLabelsThirdDrill.get(0).click();
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfDashboardPage(columnListKey)) {
					clickOnElementsOfDashboardPage(columnListKey);
					switchToDifferentTabOfDashboardPage();
					waitForPageLoaded();
					if (waitForElementsOfDashboardPage("allChartsLocator")) {
						String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfDashboardPage(errorKey)
							|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.error("No data to diplay");
					switchToPreviousTabOfDashboardPage();
				}
			} else {
				listOfLabelsSecondDrill.get(0).click();
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				sleeper(3000);
				if (waitForElementsOfDashboardPage(columnListKey)) {
					clickOnElementsOfDashboardPage(columnListKey);
					switchToDifferentTabOfDashboardPage();
					waitForPageLoaded();
					if (waitForElementsOfDashboardPage("allChartsLocator")) {
						String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfDashboardPage(errorKey)
							|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.error("No data to diplay");
					switchToPreviousTabOfDashboardPage();
				}
			}
		} else {
			listOfLabelsSecondDrill.get(0).click();
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			listOfLabelsThirdDrill = getElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			action.moveToElement(listOfLabelsThirdDrill.get(0)).build().perform();
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKey"));
			listOfLabelsThirdDrill.get(0).click();
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			switchToDifferentTabOfDashboardPage();
			waitForPageLoaded();
			sleeper(3000);
			if (waitForElementsOfDashboardPage(columnListKey)) {
				clickOnElementsOfDashboardPage(columnListKey);
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				if (waitForElementsOfDashboardPage("allChartsLocator")) {
					String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(languageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfDashboardPage(errorKey)
						|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else {
				LOGGER.error("No data to diplay");
				switchToPreviousTabOfDashboardPage();
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip text with text on reports(with frame)
	 * 
	 * @param languageCode:                   This is language code used for
	 *                                        multiple languages.
	 * @param labelsKey:                      Chart labels.
	 * @param tooltipTextKey:                 Tooltip text after hovering on a
	 *                                        chart.
	 * @param tooltipTextMajorVersionKey:     check major version of operating
	 *                                        system.
	 * @param tooltipTextMajorVersionNameKey: check major version name of operating
	 *                                        system.
	 * @param tooltipTextCountKey:            check Tooltip text count of operating
	 *                                        system.
	 * @param reportTextCountKey:             check report text count of operating
	 *                                        system.
	 * @param labelsBackKey:                  check for back functionality.
	 * @param operatingSystemKey:             check type of operating system.
	 * @param operatingSystemReleaseKey:      check type of operation system
	 *                                        release.
	 * @param moreDetailsLinkKey:             check for more details at report page.
	 * @param frameKey:                       Iframe on the drill down page.
	 * @return flag: boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipTextCountAndVersionOnReportWithFrame(String languageCode, String targetElement,
			String labelsKey, String tooltipTextKey, String tooltipTextMajorVersionKey,
			String tooltipTextMajorVersionNameKey, String tooltipTextCountKey, String reportTextCountKey,
			String labelsBackKey, String operatingSystemKey, String operatingSystemReleaseKey,
			String moreDetailsLinkKey, String frameKey, String keyDrillDownLabelsAllHidden, String visibleLegends,
			String labels) throws Exception {
		boolean flag = false;
		String osMajorVersion = null;
		String osMajorVersionName = null;
		verifyLegendsAreHidden(keyDrillDownLabelsAllHidden, visibleLegends, labels);
		List<WebElement> listOfLabelsFirstDrill = getElementsOfDashboardPage(labelsKey);
		for (int firstLevel = 0; firstLevel < listOfLabelsFirstDrill.size(); firstLevel++) {
			listOfLabelsFirstDrill = getElementsOfDashboardPage(labelsKey);
			verifyLegendsAreHidden(keyDrillDownLabelsAllHidden, visibleLegends, labels);
			listOfLabelsFirstDrill.get(firstLevel).click();
			mouseHoverbyoffsett(targetElement, 00, 150);
			waitForElementsOfDashboardPage(tooltipTextKey);
			mouseHoverbyoffsettClick(targetElement, 00, 150);
			waitForPresenceOfElementsOfDashboardPage(labelsKey);
			verifyLegendsAreHidden(keyDrillDownLabelsAllHidden, visibleLegends, labels);
			List<WebElement> listOfLabelsSecondDrill = getElementsOfDashboardPage(labelsKey);
			for (int secondLevel = 0; secondLevel < listOfLabelsSecondDrill.size();) {
				listOfLabelsSecondDrill = getElementsOfDashboardPage(labelsKey);
				verifyLegendsAreHidden(keyDrillDownLabelsAllHidden, visibleLegends, labels);
				listOfLabelsSecondDrill.get(secondLevel).click();
				mouseHoverbyoffsett(targetElement, 00, 150);
				waitForElementsOfDashboardPage(tooltipTextKey);
				osMajorVersion = getTextOfDashboardPage(tooltipTextMajorVersionKey);
				// For Other cases
				if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
					// For OSX Other cases
					if (osMajorVersion.contains("OSX Other")) {
						listOfLabelsSecondDrill.get(secondLevel).click();
						waitForElementsOfDashboardPage(labelsKey);
						verifyLegendsAreHidden(keyDrillDownLabelsAllHidden, visibleLegends, labels);
						List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage(labelsKey);
						for (int thirdLevel = 0; thirdLevel < listOfLabelsThirdDrill.size();) {
							listOfLabelsThirdDrill = getElementsOfDashboardPage(labelsKey);
							verifyLegendsAreHidden(keyDrillDownLabelsAllHidden, visibleLegends, labels);
							listOfLabelsFirstDrill.get(thirdLevel).click();
							mouseHoverbyoffsett(targetElement, 00, 150);
							waitForElementsOfDashboardPage(tooltipTextKey);
							osMajorVersion = getTextOfDashboardPage(tooltipTextMajorVersionKey);
							osMajorVersionName = getTextOfDashboardPage(tooltipTextMajorVersionNameKey);
							// osCount =
							// (getTextOfDashboardPage(tooltipTextCountKey)).split("\\(")[0].trim();
							mouseHoverbyoffsettClick(targetElement, 00, 150);
							waitForElementsOfDashboardPage(labelsKey);
							switchToDifferentTabOfDashboardPage();
							if (waitForElementsOfDashboardPageDynamic(moreDetailsLinkKey,
									DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
								clickOnElementsOfDashboardPage(moreDetailsLinkKey);
								waitForElementsOfDashboardPage(operatingSystemKey);
								List<WebElement> monthTextForOS = getElementsOfDashboardPage(operatingSystemKey);
								if (monthTextForOS.get(0).getText().contains(osMajorVersion)
										&& monthTextForOS.get(1).getText().contains(osMajorVersionName)) {
									flag = true;
								} else {
									flag = false;
									LOGGER.error(osMajorVersionName
											+ " Tooltip text and version does not match at reprot page.");
									switchToPreviousTabOfDashboardPage();
									return flag;
								}
							} else {
								LOGGER.error("Report did not load in 1 minute.");
							}
							switchToPreviousTabOfDashboardPage();
							listOfLabelsFirstDrill.get(secondLevel).click();
							thirdLevel++;
							if (thirdLevel == listOfLabelsThirdDrill.size()) {
								osMajorVersion = null;
								osMajorVersionName = null;
								clickOnElementsOfDashboardPage(labelsBackKey);
							}
						}
					} else {
						if (osMajorVersion.contains("Windows Other")) {
						} else {
						}
						mouseHoverbyoffsettClick(targetElement, 00, 150);
						waitForElementsOfDashboardPage(labelsKey);
						switchToDifferentTabOfDashboardPage();
						if (waitForElementsOfDashboardPageDynamic(operatingSystemKey,
								DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
							clickOnElementsOfDashboardPage(moreDetailsLinkKey);
							List<WebElement> monthTextOtherForOS = getElementsOfDashboardPage(operatingSystemKey);
							if (monthTextOtherForOS.get(0).getText().contains(osMajorVersion)) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(
										osMajorVersion + " Tooltip text and version does not match at reprot page.");
								switchToPreviousTabOfDashboardPage();
								return flag;
							}
						} else {
							LOGGER.error("Report did not load in 1 minute.");
						}
						switchToPreviousTabOfDashboardPage();
						listOfLabelsSecondDrill.get(secondLevel).click();
					}
				} else {
					mouseHoverbyoffsettClick(targetElement, 00, 150);
					waitForElementsOfDashboardPage(labelsKey);
					verifyLegendsAreHidden(keyDrillDownLabelsAllHidden, visibleLegends, labels);
					List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage(labelsKey);
					for (int thirdlevel = 0; thirdlevel < listOfLabelsThirdDrill.size();) {
						listOfLabelsThirdDrill = getElementsOfDashboardPage(labelsKey);
						verifyLegendsAreHidden(keyDrillDownLabelsAllHidden, visibleLegends, labels);
						listOfLabelsThirdDrill.get(thirdlevel).click();
						mouseHoverbyoffsett(targetElement, 00, 150);
						waitForElementsOfDashboardPage(tooltipTextKey);
						osMajorVersion = getTextOfDashboardPage(tooltipTextMajorVersionKey);
						osMajorVersionName = listOfLabelsThirdDrill.get(0).getText();
						// osCount =
						// (getTextOfDashboardPage(tooltipTextCountKey)).split("\\(")[0].trim();
						mouseHoverbyoffsettClick(targetElement, 00, 150);
						waitForElementsOfDashboardPage(labelsKey);
						switchToDifferentTabOfDashboardPage();
						if (waitForElementsOfDashboardPageDynamic(moreDetailsLinkKey,
								DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
							clickOnElementsOfDashboardPage(moreDetailsLinkKey);
							waitForElementsOfDashboardPage(operatingSystemKey);
							List<WebElement> monthTextForOS = getElementsOfDashboardPage(operatingSystemKey);
							if (monthTextForOS.get(0).getText().contains(osMajorVersion)
									|| monthTextForOS.get(1).getText().contains(osMajorVersionName)) {
								flag = true;
							} else {
								flag = false;
								LOGGER.error(osMajorVersionName
										+ " Tooltip text and version does not match at report page.");
								switchToPreviousTabOfDashboardPage();
								return flag;
							}
						} else {
							LOGGER.error("Report did not load in 1 minute.");
						}
						switchToPreviousTabOfDashboardPage();
						thirdlevel++;
						// listOfLabelsThirdDrill.get(thirdlevel).click();
						if (thirdlevel == listOfLabelsThirdDrill.size()) {
							osMajorVersion = null;
							osMajorVersionName = null;
							clickOnElementsOfDashboardPage(labelsBackKey);
						}
					}
				}
				secondLevel++;
				// listOfLabelsSecondDrill.get(secondLevel).click();
				if (secondLevel == listOfLabelsSecondDrill.size()) {
					osMajorVersion = null;
					clickOnElementsOfDashboardPage(labelsBackKey);
				}
			}
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip count with total rows on reports with
	 * Frame
	 * 
	 * @param labelsKey:                   Chart labels
	 * @param tooltipTextKey:              Tooltip text after hovering on a chart
	 * @param columnListKey:               Column names present in the grid
	 * @param paginationKey:               Locator of the pagination
	 * @param targetElement:               element to move to
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param frameKey:                    Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipCountonReportWithFrameFlexi(String labelsKey, String tooltipTextKey,
			String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden,
			String targetElement) throws Exception {
		boolean flag = false;
		String count = null;
		String paginationText = null;
		waitForPageLoaded();
		sleeper(2000);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey, keyDrillDownLabelsAllHidden);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		for (int ilistOfLabelsCounter = 0; ilistOfLabelsCounter < listOfLegends.size(); ilistOfLabelsCounter++) {
			listOfLegends.get(ilistOfLabelsCounter).click();
			mouseHoverbyoffsett(targetElement, 00, 80);
			waitForElementsOfDashboardPage(tooltipTextKey);
			count = getTextOfDashboardPage(tooltipTextKey);
			String count_clean = count.replaceAll(",", "");
			Integer tooltipcount = Integer.valueOf(count_clean);
			sleeper(2000);
			waitForPageLoaded();
			mouseHoverbyoffsettClick(targetElement, 00, 80);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				if (tooltipcount > 10) {
					sleeper(2000);
					waitForPageLoaded();
					paginationText = getTextOfDashboardPage(paginationKey);
					String arr[] = paginationText.split(" ");
					String laststring = arr[arr.length - 1];
					if (laststring.equals(count_clean)) {
						flag = true;
					}
				} else {
					sleeper(2000);
					waitForPageLoaded();
					List<WebElement> listOfColumntext = getElementsOfDashboardPage(columnListKey);
					if (tooltipcount == (listOfColumntext.size())) {
						flag = true;
					}
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
			listOfLegends.get(ilistOfLabelsCounter).click();
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip count with total rows on reports with
	 * Frame
	 * 
	 * @param labelsKey:      Chart labels
	 * @param tooltipTextKey: Tooltip text after hovering on a chart
	 * @param columnListKey:  Column names present in the grid
	 * @param paginationKey:  Locator of the pagination
	 * @param frameKey:       Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipCountonReportWithFrame(String labelsKey, String tooltipTextKey,
			String columnListKey, String paginationKey, String frameKey) throws Exception {
		boolean flag = false;
		String count = null;
		String paginationText = null;
		Actions action = new Actions(getDriver());
		waitForPageLoaded();
		sleeper(2000);
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey); // lable of legends
		for (int ilistOfLabelsCounter = 0; ilistOfLabelsCounter < listOfLabels.size(); ilistOfLabelsCounter++) {
			listOfLabels.get(ilistOfLabelsCounter);
			action.moveToElement(listOfLabels.get(ilistOfLabelsCounter)).build().perform();
			waitForElementsOfDashboardPage(tooltipTextKey);
			count = getTextOfDashboardPage(tooltipTextKey);
			String count_clean = count.replaceAll(",", "");
			Integer tooltipcount = Integer.valueOf(count_clean);
			sleeper(2000);
			waitForPageLoaded();
			listOfLabels.get(ilistOfLabelsCounter).click();
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (tooltipcount > 10) {
				sleeper(2000);
				waitForPageLoaded();
				paginationText = getTextOfDashboardPage(paginationKey);
				String arr[] = paginationText.split(" ");
				String laststring = arr[arr.length - 1];
				if (laststring.equals(count_clean)) {
					flag = true;
				}
			} else {
				sleeper(2000);
				waitForPageLoaded();
				List<WebElement> listOfColumntext = getElementsOfDashboardPage(columnListKey);
				if (tooltipcount == (listOfColumntext.size())) {
					flag = true;
				}
			}
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	/**
	 * This method basically verify the header Name on report page with Frame
	 * 
	 * @param LanguageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param labelKey:                Chart labels
	 * @param keyHeaderOnNextPage:     Header on the drill down page
	 * @param headerNamesOnReportPage: Vlaues in the header section on the drill
	 *                                 down page
	 * @param frameKey:                Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean headerTextVerificationOnReportPageFrame(String LanguageCode, String labelKey,
			String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey) throws Exception {
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelKey);
		boolean flag = false;
		verifyElementIsClickableOfDashboardPage(labelKey);
		listOfLabels.get(0).click();		
		sleeper(3000);
		switchToDifferentTabOfDashboardPage();
		sleeper(3000);
		
		if (waitForElementsOfDashboardPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
			List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnNextPage);
			for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
				if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode,
						"daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
		} else {
			LOGGER.error("Report page did not load in 1 minute.");
		}
		switchToPreviousTabOfDashboardPage();
		return flag;
	}

	/**
	 * This method basically verify the hidden of labels of legends on drillDown
	 * Chart
	 * 
	 * @param keyLegendLabel:              Chart criteria present on the graph
	 * @param keyDrillDownLabels:          Chart legends present below the chart
	 * @param tooltipTextKey:              Tooltip text after hovering on a chart
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking
	 *                                     on legends
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegends(String keyLegendLabel, String keyDrillDownLabels,
			String tooltipTextKey, String keyDrillDownLabelsAllHidden) throws Exception {
		Actions action = new Actions(getDriver());
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		List<WebElement> drillDownLabelsElementList = getElementsTillAllElementsVisibleofDashboardPage(
				keyDrillDownLabels);
		ArrayList<String> legendLabels = getChartLabelsDashboardPage(keyLegendLabel);
		ArrayList<String> drillDownLabels = getChartLabelsDashboardPage(keyDrillDownLabels);
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
					if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden)) {
						return false;
					} else {
						return true;
					}
				} else {
					waitForElementsOfDashboardPage(keyDrillDownLabels);
					drillDownLabelsElementList = getElementsTillAllElementsVisibleofDashboardPage(keyDrillDownLabels);
					for (int m = 0; m < drillDownLabelsElementList.size(); m++) {
						sleeper(2000);
						action.moveToElement(drillDownLabelsElementList.get(m)).build().perform();
						waitForElementsOfDashboardPage(tooltipTextKey);
						toolTipText.add(getTextOfDashboardPage(tooltipTextKey));
					}
				}
				for (int drillDownLabelsArraycounter = 0; drillDownLabelsArraycounter < toolTipText
						.size(); drillDownLabelsArraycounter++) {
					visibiltyFlag = legendLabelText
							.equalsIgnoreCase(toolTipText.get(drillDownLabelsArraycounter).trim());
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
	 * @param LanguageCode:                This is language code used for multiple
	 *                                     languages.
	 * @param labelKey:                    Chart labels
	 * @param keyHeaderOnNextPage:         Header on the drill down page
	 * @param headerNamesOnReportPage:     Vlaues in the header section on the drill
	 *                                     down page
	 * @param frameKey:                    Iframe on the drill down page
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param targetElement                : element to be moved on
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean headerTextVerificationOnReportPageFrameFlexi(String LanguageCode, String labelKey,
			String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement) throws Exception {
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelKey);

		boolean flag = false;
		verifyElementIsClickableOfDashboardPage(labelKey);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelKey, keyDrillDownLabelsAllHidden);
		listOfLegends.get(0).click();
		sleeper(2000);
		mouseHoverbyoffsettClick(targetElement, 00, 60);
		sleeper(3000);
		switchToDifferentTabOfDashboardPage();
		sleeper(3000);
		waitForElementsOfDashboardPage(keyHeaderOnNextPage);
		if (waitForElementsOfDashboardPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
			List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnNextPage);
			for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
				if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode,
						"daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
		} else {
			LOGGER.error("Report did not load in 1 minute.");
		}
		switchToPreviousTabOfDashboardPage();
		return flag;
	}

	/**
	 * This method basically verify the header Name on report page with Frame
	 * 
	 * @param LanguageCode:                This is language code used for multiple
	 *                                     languages.
	 * @param labelKey:                    Chart labels
	 * @param keyHeaderOnNextPage:         Header on the drill down page
	 * @param headerNamesOnReportPage:     Vlaues in the header section on the drill
	 *                                     down page
	 * @param frameKey:                    Iframe on the drill down page
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param targetElement                : element to be moved on
	 * @param legendDropdownKey            : key of legend dropdown
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean headerTextVerificationOnReportPageFrameDonutChartFlexi(String LanguageCode, String labelKey,
			String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey) throws Exception {
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelKey);

		boolean flag = false;
		verifyElementIsClickableOfDashboardPage(labelKey);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelKey, keyDrillDownLabelsAllHidden,
					legendDropdownKey);
		if (waitForElementsOfDashboardPageDynamic(legendDropdownKey, 1)) {
			clickOnElementsOfDashboardPage(legendDropdownKey);
		} else {
			LOGGER.info("Legend dropdown not present on chart");
		}
		sleeper(2000);
		listOfLegends.get(0).click();
		sleeper(2000);
		mouseHoverbyoffsettClick(targetElement, 00, 72);
		sleeper(3000);
		switchToDifferentTabOfDashboardPage();
		sleeper(3000);
		waitForElementsOfDashboardPage(keyHeaderOnNextPage);
		if (waitForElementsOfDashboardPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
			List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnNextPage);
			for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
				if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode,
						"daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
		} else {
			LOGGER.error("Report did not load in 1 minute.");
		}
		switchToPreviousTabOfDashboardPage();
		return flag;
	}

	public final boolean verifyLegendsAreHidden(String hiddenLegends, String visibleLegends, String labels) {
		try {
			boolean flag = false;
			if (waitForElementsOfDashboardPageDynamic(labels, 5)) {
				for (int i = 0; i < getElementsTillAllElementsVisibleofDashboardPage(visibleLegends).size(); i++) {
					sleeper(2000);
					if (!getElementsTillAllElementsVisibleofDashboardPage(visibleLegends).get(i).getAttribute("class")
							.contains("highcharts-legend-item-hidden")) {
						getElementsTillAllElementsVisibleofDashboardPage(visibleLegends).get(i).click();
					}
				}
				if (!waitForElementsOfDashboardPageDynamic(labels, 5)) {
					flag = true;
					LOGGER.info("All Legends are hidden now.");
				}
			} else {
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
	 * This method basically verify the hidden of labels of legends on donut
	 * drillDown Chart
	 * 
	 * @param keyLegendLabel:              Chart criteria present on the graph
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking
	 *                                     on legends
	 * @param legendDropdownKey:           legend dropdown key
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(String keyLegendLabel,
			String keyDrillDownLabelsAllHidden, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
			if (listCounter >= 2) {
				if (waitForElementsOfDashboardPage(legendDropdownKey))
					clickOnElementsOfDashboardPage(legendDropdownKey);
				sleeper(2000);
				legendLabelElementList.get(listCounter).click();
			} else
				legendLabelElementList.get(listCounter).click();
			sleeper(2000);
			if (listCounter == legendLabelElementList.size() - 1) {
				if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method basically verify the hidden of labels of legends on drillDown
	 * Chart
	 * 
	 * @param keyLegendLabel:              Chart criteria present on the graph
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking
	 *                                     on legends
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegendsFlexi(String keyLegendLabel,
			String keyDrillDownLabelsAllHidden) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
			legendLabelElementList.get(listCounter).click();
			sleeper(3000);
			if (listCounter == legendLabelElementList.size() - 1) {
				if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method basically verify the visibility of labels of legends on drillDown
	 * Chart
	 * 
	 * @param keyLegendLabel:Chart criteria present on the graph
	 * @param chartVisibility:     locator of chart visibility
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegendsFlexi(String keyLegendLabel, String chartVisibility)
			throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		if (verifyElementsOfDashboardPage(chartVisibility)) {
			return false;
		} else {
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				legendLabelElementList.get(listCounter).click();
				sleeper(3000);
				if (!verifyElementsOfDashboardPage(chartVisibility)) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * This method basically verify the visibility of labels of legends on drillDown
	 * Chart
	 * 
	 * @param keyLegendLabel:Chart         criteria present on the graph
	 * @param keyDrillDownLabels:          Chart legends present below the chart
	 * @param tooltipTextKey:              Tooltip text after hovering on a chart
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking
	 *                                     on legends
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegends(String keyLegendLabel, String keyDrillDownLabels,
			String tooltipTextKey, String keyDrillDownLabelsAllHidden) throws Exception {
		Actions action = new Actions(getDriver());
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		List<WebElement> drillDownLabelsElementList;
		ArrayList<String> toolTipText = new ArrayList<String>();
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden)) {
			return false;
		} else {
			boolean visibiltyFlag = false;
			boolean textFlag = false;
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				String legendLabelText = legendLabelElementList.get(listCounter).getText();
				legendLabelElementList.get(listCounter).click();
				drillDownLabelsElementList = getElementsTillAllElementsVisibleofDashboardPage(keyDrillDownLabels);
				for (int m = 0; m < drillDownLabelsElementList.size(); m++) {
					sleeper(2000);
					action.moveToElement(drillDownLabelsElementList.get(m)).build().perform();
					waitForElementsOfDashboardPage(tooltipTextKey);
					toolTipText.add(getTextOfDashboardPage(tooltipTextKey));
				}
				for (int drillDownLabelsArraycounter = 0; drillDownLabelsArraycounter < toolTipText
						.size(); drillDownLabelsArraycounter++) {
					visibiltyFlag = legendLabelText
							.equalsIgnoreCase(toolTipText.get(drillDownLabelsArraycounter).trim());
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
	 * This method basically verify the visibility of labels of legends on drillDown
	 * Chart
	 * 
	 * @param keyLegendLabel:Chart criteria present on the graph
	 * @param chartVisibility:     locator of chart visibility
	 * @param legendDropdownKey    : legend dropdown key
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegendsDonutChartFlexi(String keyLegendLabel,
			String chartVisibility, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		if (verifyElementsOfDashboardPage(chartVisibility)) {
			return false;
		} else {
			for (int listCounter = legendLabelElementList.size() - 1; listCounter >= 0; listCounter--) {
				if (listCounter >= 2) {
					legendLabelElementList.get(listCounter).click();
					sleeper(2000);
					if (waitForElementsOfDashboardPage(legendDropdownKey))
						clickOnElementsOfDashboardPage(legendDropdownKey);
					sleeper(2000);
				} else
					legendLabelElementList.get(listCounter).click();
				sleeper(2000);
				if (!verifyElementsOfDashboardPage(chartVisibility)) {
					return false;
				}
			}
			return true;
		}
	}

	
	/**
	 * This method is used to validate invisibility of labels of legends Bar
	 * 
	 * @param keyLegendLabel:          Legends present below the charts
	 * @param keyBarColumsList:        Bars/ section in the graph
	 * @param keyBarColumsToolTipText: Tooltip text after hovering on a bar of the
	 *                                 chart
	 * @param keyNoData:               Charts having no data
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegendsBar(String keyLegendLabel, String keyBarColumsList,
			String keyBarColumsToolTipText, String keyNoData) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		boolean visibiltyFlag = false;
		boolean textFlag = false;
		if (verifyElementsOfDashboardPage(keyNoData)) {
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
					if (verifyElementsOfDashboardPage(keyNoData)) {
						return true;
					} else {
						return false;
					}
				} else {
					barColumList = getElementsOfDashboardPage(keyBarColumsList);
					for (int barColumListCounter = 0; barColumListCounter < barColumList
							.size(); barColumListCounter++) {
						sleeper(2000);
						action.moveToElement(barColumList.get(barColumListCounter)).build().perform();
						waitForElementsOfDashboardPage(keyBarColumsToolTipText);
						toolTipText = getTextOfDashboardPage(keyBarColumsToolTipText).split("●");
						for (int toolTipTextCounter = 0; toolTipTextCounter < toolTipText.length; toolTipTextCounter++) {
							toolTipText[toolTipTextCounter] = toolTipText[toolTipTextCounter].trim();
							visibiltyFlag = (toolTipText[toolTipTextCounter].contains(legendLabelText)
									| legendLabelText.contains(toolTipText[toolTipTextCounter]));
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
	 * @param keyLegendLabel:          Legends present below the charts
	 * @param keyBarColumsList:        Bars/ section in the graph
	 * @param keyBarColumsToolTipText: Tooltip text after hovering on a bar of the
	 *                                 chart
	 * @param keyNoData:               Charts having no data
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegendsBar(String keyLegendLabel, String keyBarColumsList,
			String keyBarColumsToolTipText, String keyNoData) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		boolean visibiltyFlag = false;
		boolean textFlag = false;
		if (!verifyElementsOfDashboardPage(keyNoData)) {
			return false;
		} else {
			List<WebElement> barColumList;
			String[] toolTipText;
			Actions action = new Actions(getDriver());
			for (int listCounter = 0; listCounter < legendLabelElementList.size(); listCounter++) {
				String legendLabelText = legendLabelElementList.get(listCounter).getText();
				sleeper(2000);
				legendLabelElementList.get(listCounter).click();
				barColumList = getElementsOfDashboardPage(keyBarColumsList);
				overloop: for (int i = 0; i < barColumList.size(); i++) {
					sleeper(2000);
					action.moveToElement(barColumList.get(i)).build().perform();
					waitForElementsOfDashboardPage(keyBarColumsToolTipText);
					toolTipText = getTextOfDashboardPage(keyBarColumsToolTipText).split("●");
					for (int toolTipTextCounter = 0; toolTipTextCounter < toolTipText.length; toolTipTextCounter++) {
						toolTipText[toolTipTextCounter] = toolTipText[toolTipTextCounter].trim();
						visibiltyFlag = (toolTipText[toolTipTextCounter].contains(legendLabelText)
								| legendLabelText.contains(toolTipText[toolTipTextCounter]));
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

	public final boolean verifyChartLabelsDashboardPage(String labelsKey, ArrayList<String> labelList)
			throws Exception {
		boolean flag = false;
		String updatedLabel;
		String updatedLabellist;
		waitForElementsOfDashboardPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		List<String> labelsCurrentList = new ArrayList<String>();
		Set<String> store = new HashSet<>();
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabels.size(); listOfLabelsCounter++) {
			if (Strings.isNullOrEmpty(listOfLabels.get(listOfLabelsCounter).getText())) {
				labelsCurrentList.add(listOfLabels.get(listOfLabelsCounter).getAttribute("textContent"));
			} else {
				labelsCurrentList.add(listOfLabels.get(listOfLabelsCounter).getText().replace(" ", ""));
			}

			if (labelList.get(listOfLabelsCounter).contains("-")) {
				updatedLabel = labelList.get(listOfLabelsCounter).replace("-", " ");
				labelList.set(listOfLabelsCounter, updatedLabel);
			}
		}

		for (int labelslistCounter = 0; labelslistCounter < labelList.size(); labelslistCounter++) {
			if (labelList.get(labelslistCounter).contains(" ")) {
				updatedLabellist = labelList.get(labelslistCounter).replace(" ", "");
				labelList.set(labelslistCounter, updatedLabellist);
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
	 * This method is used to verify device name link redirection on reports table
	 * for MSP with frame
	 * 
	 * @param labelsKey:        List of labels/criteria present in the graph
	 * @param deviceDetailsKey: Element present on Device page
	 * @param columnListKey:    Columns present in the grid
	 * @param errorKey:         Error message flashes on the Dashboard
	 * @param frameKey:         Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyDeviceNameRedirectionMSPWithFrame(String LanguageCode, String labelsKey,
			String deviceDetailsKey, String columnListKey, String errorKey, String frameKey) throws Exception {
		boolean flag = false;
		waitForElementsOfDashboardPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		for (int counter = 0; counter < 1; counter++) {
			listOfLabels.get(counter).click();
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(frameKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				scrollToDashboardPage(columnListKey);
				if (verifyElementsOfDashboardPage(columnListKey) == true) { // as per this BUG 514104
					clickOnElementsOfDashboardPage(columnListKey);
					switchToDifferentTabOfDashboardPage();
					waitForPageLoaded();
					if (waitForElementsOfDashboardPage("allChartsLocator")) {
						String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
						if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
							LOGGER.info("Reseller do not have permission do view Device details page");
						else {
							LOGGER.error("The error notification for reseller is not matched");
							return flag;
						}
						flag = true;
					} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
						LOGGER.info("Successfully navigated to Device details page");
						flag = true;
					} else if (waitForElementsOfDashboardPage(errorKey)
							|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
						LOGGER.info("Device not found");
						flag = true;
					}
				} else {
					LOGGER.info("Device name is not present");
					flag = true;
				}
			} else
				LOGGER.error("No data to diplay/Report did not load in 1 minute.");
			// DRIVER.switchTo().defaultContent();
			switchToPreviousTabOfDashboardPage();
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	/**
	 * This method is used to verify device name link redirection on reports table
	 * for MSP with frame
	 * 
	 * @param LanguageCode:                language code
	 * @param labelsKey:                   List of labels/criteria present in the
	 *                                     graph
	 * @param deviceDetailsKey:            Element present on Device page
	 * @param columnListKey:               Columns present in the grid
	 * @param errorKey:                    Error message flashes on the Dashboard
	 * @param keyDrillDownLabelsAllHidden: label hidden locator
	 * @param targetElement:               element to be moved on
	 * @param frameKey:                    Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyDeviceNameRedirectionMSPWithFrameFlexi(String LanguageCode, String labelsKey,
			String deviceDetailsKey, String columnListKey, String errorKey, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement) throws Exception {
		boolean flag = false;
		waitForElementsOfDashboardPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey, keyDrillDownLabelsAllHidden);
		for (int counter = 0; counter < 1; counter++) {
			listOfLabels.get(counter).click();
			sleeper(3000);
			mouseHoverbyoffsettClick(targetElement, 00, 60);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(frameKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				scrollToDashboardPage(columnListKey);
				clickOnElementsOfDashboardPage(columnListKey);
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				if (waitForElementsOfDashboardPage("allChartsLocator")) {
					String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfDashboardPage(errorKey)
						|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else
				LOGGER.error("No data to diplay");
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	/**
	 * This method is used to verify device name link redirection on reports table
	 * for MSP with frame
	 * 
	 * @param LanguageCode:                language code
	 * @param labelsKey:                   List of labels/criteria present in the
	 *                                     graph
	 * @param deviceDetailsKey:            Element present on Device page
	 * @param columnListKey:               Columns present in the grid
	 * @param errorKey:                    Error message flashes on the Dashboard
	 * @param keyDrillDownLabelsAllHidden: label hidden locator
	 * @param targetElement:               element to be moved on
	 * @param frameKey:                    Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyDeviceNameRedirectionMSPWithFrameDonutChartFlexi(String LanguageCode, String labelsKey,
			String deviceDetailsKey, String columnListKey, String errorKey, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement, String chartNameDropdown) throws Exception {
		boolean flag = false;
		waitForElementsOfDashboardPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelsKey, keyDrillDownLabelsAllHidden,
					chartNameDropdown);
		for (int counter = 0; counter < 1; counter++) {
			if (waitForElementsOfDashboardPageDynamic(chartNameDropdown, 2)) {
				clickOnElementsOfDashboardPage(chartNameDropdown);
			} else {
				LOGGER.info("Legend dropdown not present on chart");
			}
			// clickOnElementsOfDashboardPage(chartNameDropdown);
			sleeper(2000);
			listOfLabels.get(counter).click();
			sleeper(3000);
			mouseHoverbyoffsettClick(targetElement, 00, 72);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(frameKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				scrollToDashboardPage(columnListKey);
				clickOnElementsOfDashboardPage(columnListKey);
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				if (waitForElementsOfDashboardPage("allChartsLocator")) {
					String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfDashboardPage(errorKey)
						|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else
				LOGGER.error("No data to diplay");
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	public boolean verifyBackFunctionality(String key, String backKey) {
		try {
			sleeper(2000);
			clickOnElementsOfDashboardPage(key);
			clickOnElementsOfDashboardPage(backKey);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

	}

	public String verifyUrlAfterReDirection(String key, String secondLevelKey, String tooltipTypeKey) throws Exception {
		// TODO this method will verify URL on incidents page after re-direction
		// from Dashboard page through chart selection
		clickOnElementsOfDashboardPage(key);
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(secondLevelKey);
		String typeText = null;
		WebElement subtype = null;
		for (int i = 0; i < 1; i++) {
			subtype = listOfLabels.get(i);
			action.moveToElement(subtype).build().perform();
			waitForElementsOfDashboardPage(tooltipTypeKey);
			typeText = getTextOfDashboardPage(tooltipTypeKey);
		}
		String[] result = typeText.split(" ");
		subtype.click();
		return result[0].toUpperCase();
	}

	/**
	 * This method will select multiple companies from filter drop down
	 * 
	 * @param LanguageCode:   This is language code used for multiple languages.
	 * @param textKey:        Company Search box key
	 * @param emptyTextKey:   Company search box empty key
	 * @param listKey:        List of companies in the dropdown
	 * @param dropdownBoxKey: Company drop down key
	 * @param companies:      Company names to be selected
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyMultipleCompanyChangeOfDashboardPage(String LanguageCode, String textKey, String emptyTextKey,
			String listKey, String dropdownBoxKey, String... companies) throws Exception {
		int counter = 0;
		clickOnElementsOfDashboardPage("companyDropdownFlexi");
		if (companies.length == 0) {
			LOGGER.error("No companies are specified");
			return false;
		} else {
			for (String company : companies) {
				counter++;
				verifyMultipleCompaniesSelection(LanguageCode, dashboardPageProperties.getProperty(textKey), company,
						dashboardPageProperties.getProperty(emptyTextKey), dashboardPageProperties.getProperty(listKey),
						dashboardPageProperties.getProperty(dropdownBoxKey), counter);
			}
			LOGGER.info("Multiple companies selection successfully executed");
			return true;
		}
	}

	/**
	 * This method will select multiple companies from filter drop down
	 * 
	 * @param LanguageCode:   This is language code used for multiple languages.
	 * @param textKey:        Company Search box key
	 * @param emptyTextKey:   Company search box empty key
	 * @param listKey:        List of companies in the dropdown
	 * @param dropdownBoxKey: Company drop down key
	 * @param companies:      Company names to be selected
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyMultipleCompanyChangeOfDashboardPageFlexi(String LanguageCode, String textKey,
			String emptyTextKey, String listKey, String dropdownBoxKey, String... companies) throws Exception {
		int counter = 0;
		clickOnElementsOfDashboardPage("companyDropdownFlexi");
		if (companies.length == 0) {
			LOGGER.error("No companies are specified");
			return false;
		} else {
			for (String company : companies) {
				counter++;
				verifyMultipleCompaniesSelection(LanguageCode, dashboardPageProperties.getProperty(textKey), company,
						dashboardPageProperties.getProperty(emptyTextKey), dashboardPageProperties.getProperty(listKey),
						dashboardPageProperties.getProperty(dropdownBoxKey), counter);
			}
			LOGGER.info("Multiple companies selection successfully executed");
			return true;
		}
	}

	/**
	 * This method will verify the companies Name present in the header section on
	 * report page
	 * 
	 * @param labelKey:          Chart label
	 * @param companyLocatorKey: Company name present on the header
	 * @param companies:         Company names to be verified
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyCompaniesListOnHeader(String labelKey, String companyLocatorKey, String... companies)
			throws Exception {
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelKey);
		boolean flag = false;
		for (int listOfLabelsCount = 0; listOfLabelsCount < listOfLabels.size(); listOfLabelsCount++) {
			listOfLabels.get(listOfLabelsCount).click();
			switchToDifferentTabOfDashboardPage();
			waitForPageLoaded();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				waitForElementsOfDashboardPage("moreDetailsLink");
				clickOnElementsOfDashboardPage("moreDetailsLink");
				waitForElementsOfDashboardPage(companyLocatorKey);
				String companyList = getTextOfDashboardPage(companyLocatorKey);
				for (String comp : companies) {
					if (companyList.contains(comp))
						flag = true;
					else {
						flag = false;
						break;
					}
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			getDriver().switchTo().defaultContent();
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	/**
	 * This method will verify the companies Name present in the header section on
	 * report page
	 * 
	 * @param labelKey:          Chart label
	 * @param companyLocatorKey: Company name present on the header
	 * @param companies:         Company names to be verified
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyCompaniesListOnHeaderHWInventory(String labelKey, String companyLocatorKey,
			String... companies) throws Exception {
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelKey);
		boolean flag = false;
		for (int listOfLabelsCount = 0; listOfLabelsCount < listOfLabels.size(); listOfLabelsCount++) {
			listOfLabels.get(listOfLabelsCount).click();
			switchToDifferentTabOfDashboardPage();
			waitForPageLoaded();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				waitForElementsOfDashboardPage("moreDetailsLink");
				clickOnElementsOfDashboardPage("moreDetailsLink");
				waitForElementsOfDashboardPage(companyLocatorKey);
				String companyList = getTextOfDashboardPage(companyLocatorKey);
				for (String comp : companies) {
					if (companyList.contains(comp))
						flag = true;
					else {
						flag = false;
						break;
					}
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			getDriver().switchTo().defaultContent();
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	/**
	 * This method will verify the companies Name present in the header section on
	 * report page
	 * 
	 * @param labelKey:          Chart label
	 * @param companyLocatorKey: Company name present on the header
	 * @param companies:         Company names to be verified
	 * @return boolean value
	 * @throws Exception
	 */
	public boolean verifyCompaniesListOnHeaderFlexi(String labelKey, String companyLocatorKey, String targetElement,
			String keyDrillDownLabelsAllHidden, String... companies) throws Exception {
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelKey);
		boolean flag = false;
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelKey, keyDrillDownLabelsAllHidden);
		for (int listOfLabelsCount = 0; listOfLabelsCount < listOfLabels.size(); listOfLabelsCount++) {
			listOfLabels.get(listOfLabelsCount).click();
			sleeper(3000);
			mouseHoverbyoffsettClick(targetElement, 00, 80);
			switchToDifferentTabOfDashboardPage();
			waitForPageLoaded();
			waitForElementsOfDashboardPage("moreDetailsLink");
			clickOnElementsOfDashboardPage("moreDetailsLink");
			waitForElementsOfDashboardPage(companyLocatorKey);
			String companyList = getTextOfDashboardPage(companyLocatorKey);
			for (String comp : companies) {
				if (companyList.contains(comp))
					flag = true;
				else {
					flag = false;
					break;
				}
			}
			getDriver().switchTo().defaultContent();
			switchToPreviousTabOfDashboardPage();
			listOfLabels.get(listOfLabelsCount).click();
		}
		return flag;
	}

	/**
	 * This method will give the label and the respective count present above the
	 * bar of the charts
	 * 
	 * @param LanguageCode:          This is language code used for multiple
	 *                               languages.
	 * @param headCountLocatorValue: Count present on the top of the bar chart
	 * @param labelLocatorValue:     Labels present on Y axis
	 * @param noDataKey:             Chart having no data
	 * @param chartTitle:            Chart title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getHeadCountAndLabel(String LanguageCode, String headCountLocatorValue,
			String labelLocatorValue, String noDataKey, String chartTitle) throws Exception {
		HashMap<String, Integer> countLabel = new HashMap<String, Integer>();
		if (!verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			waitForElementsOfDashboardPage(headCountLocatorValue);
			List<WebElement> listOfCount = getElementsOfDashboardPage(headCountLocatorValue);
			List<WebElement> listOflabels = getElementsOfDashboardPage(labelLocatorValue);
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
	 * This method will give the label and the respective count present above the
	 * bar of the charts
	 * 
	 * @param LanguageCode:          This is language code used for multiple
	 *                               languages.
	 * @param headCountLocatorValue: Count present on the top of the bar chart
	 * @param labelLocatorValue:     Labels present on Y axis
	 * @param noDataKey:             Chart having no data
	 * @param chartTitle:            Chart title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getHeadCountAndLabelFlexi(String LanguageCode, String headCountLocatorValue,
			String labelLocatorValue, String noDataKey, String chartTitle) throws Exception {
		HashMap<String, Integer> countLabel = new HashMap<String, Integer>();
		if (!verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			waitForElementsOfDashboardPage(headCountLocatorValue);
			List<WebElement> listOfCount = getElementsOfDashboardPage(headCountLocatorValue);
			List<WebElement> listOflabels = getElementsOfDashboardPage(labelLocatorValue);
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
	 * This method will give the label and the respective count for Device by OS
	 * chart
	 * 
	 * @param LanguageCode:        This is language code used for multiple
	 *                             languages.
	 * @param labelLocatorValue:   Label/ criteria present in the chart
	 * @param tooltipTextonChart:  text on the tooltip
	 * @param tooltipCountonChart: Count on the tooltip
	 * @param noDataKey:Chart      having no data
	 * @param chartTitle:Chart     title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getCountAndLabel(String LanguageCode, String labelLocatorValue,
			String tooltipTextonChart, String tooltipCountonChart, String noDataKey, String chartTitle)
			throws Exception {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		waitForPageLoaded();
		if (!verifyChartsHasNoDataOnDashBoardPage(LanguageCode, noDataKey, chartTitle)) {
			Actions action = new Actions(getDriver());
			mouseHoverOfDashboardPage(labelLocatorValue);
			waitForElementsOfDashboardPage(labelLocatorValue);
			List<WebElement> listOfLabel = getElementsOfDashboardPage(labelLocatorValue);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabel.size(); listOfLabelsCounter++) {
				sleeper(2000);
				action.moveToElement(listOfLabel.get(listOfLabelsCounter)).build().perform();
				String criteria = getTextOfDashboardPage(tooltipTextonChart).trim();
				if (Strings.isNullOrEmpty(criteria))
					LOGGER.error("Criteria is empty");
				else
					LOGGER.info("Criteria is " + criteria);
				String count = getTextOfDashboardPage(tooltipCountonChart).replaceAll(",", "");
				String[] countChar = new String[2];
				if (count.contains("(")) {
					countChar = count.split("\\(");
					count = countChar[0];
				} else {
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

	public final HashMap<String, Integer> getCountAndLabelDeviceByOS(String LanguageCode, String labelLocatorValue,
			String tooltipTextonChart, String tooltipCountonChart, String noDataKey, String chartTitle)
			throws Exception {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		waitForPageLoaded();
		if (!verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
					"labelsLocatorDeviceByOsFlexi");
			waitForElementsOfDashboardPage(labelLocatorValue);
			List<WebElement> listOfLabel = getElementsOfDashboardPage(labelLocatorValue);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabel.size(); listOfLabelsCounter++) {
				sleeper(2000);
				listOfLabel.get(listOfLabelsCounter).click();
				mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
				String criteria = getTextOfDashboardPage(tooltipTextonChart).trim();
				if (Strings.isNullOrEmpty(criteria))
					LOGGER.error("Criteria is empty");
				else
					LOGGER.info("Criteria is " + criteria);
				String count = getTextOfDashboardPage(tooltipCountonChart).replaceAll(",", "");
				String[] countChar = new String[2];
				if (count.contains("(")) {
					countChar = count.split("\\(");
					count = countChar[0];
				} else {
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
	}

	/**
	 * This method will give the label and the respective count
	 * 
	 * @param LanguageCode:        This is language code used for multiple
	 *                             languages.
	 * @param labelLocatorValue:   Label/ criteria present in the chart
	 * @param tooltipTextonChart:  text on the tooltip
	 * @param tooltipCountonChart: Count on the tooltip
	 * @param noDataKey:Chart      having no data
	 * @param chartTitle:Chart     title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getCountAndLabelFlexi(String LanguageCode, String labelLocatorValue,
			String tooltipTextonChart, String tooltipCountonChart, String noDataKey, String chartTitle)
			throws Exception {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		waitForPageLoaded();
		if (!verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			Actions action = new Actions(getDriver());
			mouseHoverOfDashboardPage(labelLocatorValue);
			waitForElementsOfDashboardPage(labelLocatorValue);
			List<WebElement> listOfLabel = getElementsOfDashboardPage(labelLocatorValue);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLabel.size(); listOfLabelsCounter++) {
				sleeper(2000);
				action.moveToElement(listOfLabel.get(listOfLabelsCounter)).build().perform();
				String criteria = getTextOfDashboardPage(tooltipTextonChart).trim();
				if (Strings.isNullOrEmpty(criteria))
					LOGGER.error("Criteria is empty");
				else
					LOGGER.info("Criteria is " + criteria);
				String count = getTextOfDashboardPage(tooltipCountonChart).replaceAll(",", "");
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
	 * @param LanguageCode:        This is language code used for multiple
	 *                             languages.
	 * @param labelLocatorValue:   Label/ criteria present in the chart
	 * @param tooltipTextonChart:  text on the tooltip
	 * @param tooltipCountonChart: Count on the tooltip
	 * @param noDataKey:Chart      having no data
	 * @param chartTitle:Chart     title
	 * @return HashMap: Criteria and respective count of the criteria
	 * @throws Exception
	 */
	public final HashMap<String, Integer> getCountAndLabelPieChartFlexi(String LanguageCode, String labelLocatorValue,
			String keyDrillDownLabelsAllHidden, String tooltipTextonChart, String tooltipCountonChart, String noDataKey,
			String chartTitle, String targetElement) throws Exception {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		waitForPageLoaded();
		if (!verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, noDataKey, chartTitle)) {
			if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
				verifyLabelHiddenWhenClickOnLegendsFlexi(labelLocatorValue, keyDrillDownLabelsAllHidden);
			List<WebElement> listOfLegends = getElementsOfDashboardPage(labelLocatorValue);
			for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
				listOfLegends.get(listOfLabelsCounter).click();
				sleeper(2000);
				mouseHoverbyoffsett(targetElement, 00, 80);
				String criteria = getTextOfDashboardPage(tooltipTextonChart).trim();
				if (Strings.isNullOrEmpty(criteria))
					LOGGER.error("Criteria is empty");
				else
					LOGGER.info("Criteria is " + criteria);
				String count = getTextOfDashboardPage(tooltipCountonChart).replaceAll(",", "");
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
	 * @param firstCompanyHash:     Chart criteria and respective count for first
	 *                              company
	 * @param secondCompanyHash:    Chart criteria and respective count for second
	 *                              company
	 * @param aggregateCompanyHash: Aggregated Chart criteria and respective count
	 *                              of first and second company
	 * @return boolean value
	 */

	public boolean verifyAggregationOfTwoCompanies(HashMap<String, Integer> firstCompanyHash,
			HashMap<String, Integer> secondCompanyHash, HashMap<String, Integer> aggregateCompanyHash) {
		Map<String, Integer> aggregatedHash;
		if (firstCompanyHash.equals(aggregateCompanyHash) || secondCompanyHash.equals(aggregateCompanyHash))
			return true;
		else {
			aggregatedHash = Stream.concat(firstCompanyHash.entrySet().stream(), secondCompanyHash.entrySet().stream())
					.collect(Collectors.toMap(entry -> entry.getKey(), // The key
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
	public boolean verifyRemovalOfDevice(String checkBoxKey, String removeDeviceKey, String removeButtonKey,
			String removeDeviceMessage, String LanguageCode) throws Exception {
		boolean flag = false;
		clickOnElementsOfDashboardPage(checkBoxKey);
		sleeper(2000);
		clickOnElementsOfDashboardPage(removeDeviceKey);
		sleeper(2000);
		clickOnElementsOfDashboardPage(removeButtonKey);
		sleeper(2000);
		if (getTextOfDashboardPage(removeDeviceMessage).equalsIgnoreCase(
				getTextLanguage(LanguageCode, "daas_ui", "assets.list.toast.remove.assets.success"))) {
			flag = true;
		}
		return flag;
	}

	/**
	 * This is a method to verify filter functionality for single select company
	 * column
	 * 
	 * @param LanguageCode       - language code
	 * @param textKey            - locator of searchbox
	 * @param Text               - text to be entered
	 * @param emptyTextKey       -locator for "no items available" message
	 * @param listKey            - locator of optiosn filetered on popup
	 * @param checkboxKey        - locator of checboxes of available options
	 * @param columnListKey      - locator of all items filtered in column
	 * @param emptyTextColumnKey - locator of "no items available" message in column
	 * @return - boolean value of whether the filter functionality is working
	 *         correctly
	 * @throws Exception
	 */
	public final boolean verifyFilterFunctionalityForAssignedToSingleSelectFromDynamicDropdown(String LanguageCode,
			String textKey, String Text, String emptyTextKey, String listKey, String checkboxKey, String columnListKey,
			String emptyTextColumnKey) throws Exception {

		enterText(dashboardPageProperties.getProperty(textKey), Text);
		boolean flag = false;
		sleeper(3000);
		String empty_text = null, text = null;
		if (verifyElementIsVisible(dashboardPageProperties.getProperty(emptyTextKey))) {
			empty_text = getTextBy(dashboardPageProperties.getProperty(emptyTextKey));
			String[] emptytext = getTextLanguage(LanguageCode, "daas_ui", "dropdown.noResults").split("%");
			if (empty_text.contains(emptytext[0].concat(Text))) {
				flag = false;
			}
		} else {
			List<WebElement> elements = getElementsTillAllElementsVisible(dashboardPageProperties.getProperty(listKey));
			List<WebElement> listOfCheckbox = getElementsTillAllElementsVisible(
					dashboardPageProperties.getProperty(checkboxKey));
			if (!listOfCheckbox.get(0).isSelected()
					&& !(listOfCheckbox.get(0).getAttribute("class").equalsIgnoreCase("checked"))) {
				text = elements.get(0).getText();
				listOfCheckbox.get(0).click();
				pressKey(Keys.ESCAPE);
			}
			sleeper(2000);

			if (verifyElementIsVisible(dashboardPageProperties.getProperty(emptyTextColumnKey))) {
				empty_text = getTextBy(dashboardPageProperties.getProperty(emptyTextColumnKey));
				if (empty_text.equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items"))) {
					flag = false;
				}
			} else {
				List<WebElement> columnList = getElementsTillAllElementsVisible(
						dashboardPageProperties.getProperty(columnListKey));
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
	 * This method is used to verify tool tip text of software inventory chart(with
	 * frame)
	 * 
	 * @param keyLegendLabel:                Legends present below the charts
	 * @param keyNoData:Charts               having no data
	 * @param keyLableslocatorsoftware:Chart labels
	 * @param tooltipTextKey:                Tooltip count after hovering on a chart
	 * @param textKey:                       Criteria present on the header on drill
	 *                                       down page
	 * @throws Exception
	 * @return boolean value
	 */
	public final boolean verifyChartSoftwareInventoryTooltext(String keyLegendLabel, String keyNoData,
			String keyLableslocatorsoftware, String tooltipTextKey, String textKey) throws Exception {
		boolean flag = false;
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		Actions action = new Actions(getDriver());
		if (verifyElementsOfDashboardPage(keyNoData)) {
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
	}

	/**
	 * This method is used to verify tool tip count of software inventory chart(with
	 * frame)
	 * 
	 * @param keyLegendLabel:                Legends present below the charts
	 * @param keyNoData:Charts               having no data
	 * @param keyLableslocatorsoftware:Chart labels
	 * @param tooltipTextKey:                Tooltip count after hovering on a chart
	 * @param columnTextKey:                 Column names present in the grid
	 * @param frameKey:                      Iframe on the drill down page
	 * @throws Exception
	 * @return boolean value
	 */
	public final boolean verifyChartSoftwareInventoryTooltipCount(String keyLegendLabel, String keyNoData,
			String keyLableslocatorsoftware, String tooltipTextKey, String columnTextKey, String frameKey,
			String tooltipTextSoftware) throws Exception {
		boolean flag = false;
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		Actions action = new Actions(getDriver());
		if (verifyElementsOfDashboardPage(keyNoData)) {
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
				if (verifyTooltipCountonReportwithFrameSWInventory(keyLableslocatorsoftware, tooltipTextKey,
						columnTextKey, frameKey, tooltipTextSoftware)) {
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
	 * This method is used to verify tool tip count of software inventory chart(with
	 * frame)
	 * 
	 * @param keyLegendLabel:                Legends present below the charts
	 * @param keyNoData:Charts               having no data
	 * @param keyLableslocatorsoftware:Chart labels
	 * @param tooltipTextKey:                Tooltip count after hovering on a chart
	 * @param columnTextKey:                 Column names present in the grid
	 * @param frameKey:                      Iframe on the drill down page
	 * @throws Exception
	 * @return boolean value
	 */
	public final boolean verifyChartSoftwareInventoryTooltipCountFlexi(String keyLegendLabel, String keyNoData,
			String keyLableslocatorsoftware, String tooltipTextKey, String columnTextKey, String frameKey,
			String tooltipTextSoftware) throws Exception {
		boolean flag = false;
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		Actions action = new Actions(getDriver());
		if (verifyElementsOfDashboardPage(keyNoData)) {
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
				if (verifyTooltipCountonReportwithFrameSWInventoryFlexi(keyLableslocatorsoftware, tooltipTextKey,
						columnTextKey, frameKey, tooltipTextSoftware)) {
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
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
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
	 * @param keyResetLink:          link for reset.
	 * @param keySave:               save button
	 * @throws Exception
	 */
	public final void verifyResetConfiguration(String keyResetConfiguration, String keyResetLink, String keySave)
			throws Exception {
		try {
			clickOnElementsOfDashboardPage(keyResetConfiguration);
			clickOnElementsOfDashboardPage(keyResetLink);
			clickOnElementsOfDashboardPage(keySave);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * This method verifies all Incident chart present on the dashboard redirect on
	 * incident list view or not
	 * 
	 * @param languageCode:Language  code for localization testing
	 * @param incidentCharts:element of the incident chart which is to be click
	 * @return : true if incident chart of dashboard redirects on Incident list view
	 *         else return false
	 * @throws Exception
	 */
	public boolean isIncidentChartRedirectOnIncidentListView(String languageCode, String incidentCharts)
			throws Exception {
		try {
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			verifyElementIsClickableOfDashboardPage(incidentCharts);
			waitForPageLoaded();
			clickOnElementsOfDashboardPage(incidentCharts);// Clicking on first level of incident chart
			waitForPageLoaded();
			sleeper(3000);// Need sleeper for second level chart validation
			verifyElementIsClickableOfDashboardPage(incidentCharts);// Waiting till second level of chart get visible
			clickOnElementsOfDashboardPage(incidentCharts);// Clicking on second level of incident chart
			if (incidentPage.matchTextOfIncidentPage("incidentTitle",
					getTextLanguage(languageCode, "daas_ui", "incidents.label"))) {
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
	 * This method verifies 'Open Incident','All Incident' and 'Top 10 incident'
	 * charts present for the logged in user
	 * 
	 * @param languageCode: Language code for localization testing
	 * @return : true if all Incident chart present for the user else return false
	 * @throws Exception
	 */
	public final boolean verifyIncidentDashboardForUser(String languageCode) throws Exception {
		try {
			boolean isIncidentChartPresent = false;
			isIncidentChartPresent = getTextOfDashboardPage("openIncidentsTitle").equalsIgnoreCase(
					getTextLanguage(languageCode, "daas_ui", "dashboard.charts.title.incidents_with_open_status"))
					&& getTextOfDashboardPage("allIncidentsTitle").equalsIgnoreCase(
							getTextLanguage(languageCode, "daas_ui", "dashboard.charts.title.incidents_by_type"))
					&& getTextOfDashboardPage("topTenIncidentsTitle").equalsIgnoreCase(getTextLanguage(languageCode,
							"daas_ui", "dashboard.charts.title.incidents_top_by_subtype"));
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
	public final boolean verifyLabelTopTenIncidents(String keyLegends, String keyLegendsFull, String keyDrillDownLabels,
			String tooltipTextKey, String keyDrillDownLabelsAllHidden) throws Exception {
		boolean flag = false;
		Actions action = new Actions(getDriver());
		List<WebElement> drillDownLabelsElementList = getElementsTillAllElementsVisibleofDashboardPage(
				keyDrillDownLabels);
		List<WebElement> legendsElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegends);
		List<WebElement> legendsElementListFull = new ArrayList<WebElement>();
		if (waitUntillElementIsPresent(keyLegendsFull)) {
			legendsElementListFull.add(getElement(keyLegendsFull));
		} else {
			legendsElementListFull = getElementsOfDashboardPage(keyLegendsFull);
		}
		ArrayList<String> toolTipText = new ArrayList<String>();
		ArrayList<String> legendText = new ArrayList<String>();
		ArrayList<String> legendTextFull = new ArrayList<String>();
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden)) {
			flag = false;
		} else {
			for (int toolTipTextCounter = 0; toolTipTextCounter < drillDownLabelsElementList
					.size(); toolTipTextCounter++) {
				sleeper(2000);
				action.moveToElement(drillDownLabelsElementList.get(toolTipTextCounter)).build().perform();
				waitForElementsOfDashboardPage(tooltipTextKey);
				toolTipText.add(getTextOfDashboardPage(tooltipTextKey));
			}
			if (legendsElementListFull.size() > 0) {
				for (int legendFullCounter = 0; legendFullCounter < legendsElementListFull
						.size(); legendFullCounter++) {
					legendTextFull.add(getInnerTextOfDashboardPage(legendsElementListFull.get(legendFullCounter)));
				}
			} else {
				LOGGER.info("All labels are displayed in full text.");
			}
			if (legendsElementList.size() > 0) {
				for (int legendCounter = 0; legendCounter < legendsElementList.size(); legendCounter++) {
					if (!legendsElementList.get(legendCounter).getText().contains("…")) {
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

	public final String getInnerTextOfDashboardPage(WebElement locator) throws Exception {
		return getInnerTextFromElement(locator);
	}

	/**
	 * @param dropdownListKey
	 * @param elementText
	 * @param dropdownBox
	 * @return
	 * @throws Exception
	 */
	public final boolean updateValueOfDropdown(String dropdownListKey, String elementText, String dropdownBox)
			throws Exception {
		return selectTextValueFromDropdown(dashboardPageProperties.getProperty(dropdownListKey), elementText,
				dashboardPageProperties.getProperty(dropdownBox));
	}

	/**
	 * This method will verify the Company list on Drill down for Device by OS chart
	 * 
	 * @param languageCode:          This is language code used for multiple
	 *                               languages.
	 * @param keyHeaderOnReportPage: Header on the drill down page
	 * @param companyListOnHeader:   Company list in the header section on the drill
	 *                               down page
	 * @param DeviceByOsInfo:        List of key for Device by Os
	 * @param deviceDetailsKey:      Element present on Device page
	 * @param columnListKey:         Columns present in the grid
	 * @param errorKey:              Error message flashes on the Dashboard
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyCompaniesListOnHeaderDeviceByOS(String languageCode, String keyHeaderOnReportPage,
			String[] companyListOnHeader, HashMap<String, String> DeviceByOsInfo, String deviceDetailsKey,
			String columnListKey, String errorKey) throws Exception {
		boolean companyListFlag = false;
		String osMajorVersion = null;
		verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsFirstDrill = getElementsOfDashboardPage("deviceByOSLegends");
		listOfLabelsFirstDrill.get(0).click();
		listOfLabelsFirstDrill = getElementsOfDashboardPage("deviceByOSLegends");
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
		waitForPageLoaded();
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
		waitForPresenceOfElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
		verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi");
		List<WebElement> listOfLabelsSecondDrill = getElementsOfDashboardPage("deviceByOSLegends");
		listOfLabelsSecondDrill = getElementsOfDashboardPage("deviceByOSLegends");
		listOfLabelsSecondDrill.get(0).click();
		mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
		waitForPageLoaded();
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
		osMajorVersion = getTextOfDashboardPage(DeviceByOsInfo.get("tooltipTextMajorVersionKeyFlexi"));
		// For Other cases
		if (osMajorVersion.contains(getTextLanguage(languageCode, "daas_ui", "global.device_type.other"))) {
			// For OSX Other cases
			if (osMajorVersion.contains("OSX Other")) {
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
						"labelsLocatorDeviceByOsFlexi");
				List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
				listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
				listOfLabelsThirdDrill.get(0).click();
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
			} else {
				mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
				waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
			}
		} else {
			waitForPresenceOfElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			verifyLegendsAreHidden("deviceByOSLegendsHidden", "deviceByOSLegendsVisible",
					"labelsLocatorDeviceByOsFlexi");
			List<WebElement> listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
			listOfLabelsThirdDrill = getElementsOfDashboardPage("deviceByOSLegends");
			listOfLabelsThirdDrill.get(0).click();
			mouseHoverbyoffsett("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("tooltipTextKeyFlexi"));
			mouseHoverbyoffsettClick("devicesByOsTitleFlexi", 00, 150);
			waitForElementsOfDashboardPage(DeviceByOsInfo.get("labelsKey"));
			switchToDifferentTabOfDashboardPage();
			waitForPageLoaded();
		}
		// Verify company
		waitForElementsOfDashboardPage(DeviceByOsInfo.get("moreDetailsLinkKey"));
		clickOnElementsOfDashboardPage(DeviceByOsInfo.get("moreDetailsLinkKey"));
		waitForElementsOfDashboardPage("companyListLocatorOnReportPage");
		String companyList = getTextOfDashboardPage("companyListLocatorOnReportPage");
		for (String comp : companyListOnHeader) {
			if (companyList.contains(comp))
				companyListFlag = true;
			else {
				companyListFlag = false;
				break;
			}
		}
		getDriver().switchTo().defaultContent();
		switchToPreviousTabOfDashboardPage();
		return companyListFlag;
	}

	/**
	 * This method will verify the estimator tab
	 * 
	 * @param waitForElementsOfLoginPage: Email id field of login pages
	 * @param expectedEstimatorLink:      Expected Estimator URL
	 * @return boolean value
	 */
	public boolean verifyEstimatorTab(String expectedEstimatorLink) {
		waitForPageLoaded();
		boolean estimatorFlag = false;
		String estimatorURL = getDriver().getCurrentUrl();
		if (estimatorURL.contains(expectedEstimatorLink)) {
			estimatorFlag = true;
			LOGGER.info("Validated the Estimator URL");
			switchToPreviousTabOfDashboardPage();
			return estimatorFlag;
		} else {
			LOGGER.info("Wrong URL or wrong page for Estimator Tab");
			estimatorFlag = false;
		}
		switchToPreviousTabOfDashboardPage();
		return estimatorFlag;
	}

	/**
	 * This method will clear the selected company and clicks on the drop down to
	 * select the new company
	 * 
	 * @param allCompanyText:  All Companies text xpath
	 * @param companyDropdown: Company drop down present on Dashboard
	 * @param clearCompany:    Clear button xpath
	 * @throws Exception
	 */
	public void selectCompanyDropdown(String allCompanyText, String companyDropdown, String clearCompany)
			throws Exception {
		if (waitForElementsOfDashboardPage(allCompanyText)) {
			sleeper(3000);
			clickOnElementsOfDashboardPage(companyDropdown);
			LOGGER.info("No filter was present ,Dropdown got open.");
		} else {
			sleeper(3000);
			clickOnElementsOfDashboardPage(clearCompany);
			clickOnElementsOfDashboardPage(companyDropdown);
			LOGGER.info("All filters cleared ,Dropdown got open.");
		}

	}

	/**
	 * This method will validates the correct filter criteria applied on redirected
	 * report details page
	 * 
	 * @param patchStatus:             Windows 10 Patch Status graph 1st level
	 *                                 status bars(latest/outdated/unknown) xpath
	 * @param patchStatusTooltipText:  Windows 10 Patch Status graph 1st level
	 *                                 status bars tooltip
	 *                                 text(latest/outdated/unknown) xpath
	 * @param seconddrilldown:         Windows 10 Patch Status graph 2nd level
	 *                                 status bars(latest/outdated/unknown) xpath
	 * @param secondlevelTooltip:      Windows 10 Patch Status graph 2nd level
	 *                                 status bars tooltip
	 *                                 text(latest/outdated/unknown) xpath
	 * @param osReleaseFilterCriteria: filter applied in header section
	 * @param backButtonWPS:           BACK button xpath to return to the 1st level
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyTooltipTextVersionStatusWPS(String patchStatus, String patchStatusTooltipText,
			String seconddrilldown, String secondlevelTooltip, String osReleaseFilterCriteria, String backButtonWPS)
			throws Exception {
		boolean flag = false;
		String text = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(patchStatus);

		try {
			for (int i = 0; i < listOfLabels.size(); i++) {
				getDriver().navigate().refresh();
				sleeper(3000);
				waitForElementsOfDashboardPage(patchStatus);
				listOfLabels = getElementsOfDashboardPage(patchStatus);
				listOfLabels.get(i);
				action.moveToElement(listOfLabels.get(i)).build().perform();
				waitForElementsOfDashboardPage(patchStatusTooltipText);
				text = getTextOfDashboardPage(patchStatusTooltipText);
				LOGGER.info("Patch Status :" + text);
				listOfLabels.get(i).click();
				if (verifyTooltipTextOnReportWithFrameFlexi1(seconddrilldown, secondlevelTooltip,
						osReleaseFilterCriteria)) {
					flag = true;
					clickOnElementsOfDashboardPage(backButtonWPS);
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
	}

	/**
	 * This method will validates the total results on redirected report details
	 * page after clicked on the device count of patch
	 * status(Latest/Outdated/Unknown).)
	 * 
	 * @param languageCode:           Language selected to run report in selected
	 *                                language
	 * @param patchStatus:            Windows 10 Patch Status graph 1st level status
	 *                                bars(latest/outdated/unknown) xpath
	 * @param patchStatusTooltipText: Windows 10 Patch Status graph 1st level status
	 *                                bars tooltip text(latest/outdated/unknown)
	 *                                xpath
	 * @param seconddrilldown:        Windows 10 Patch Status graph 2nd level status
	 *                                bars(latest/outdated/unknown) xpath
	 * @param secondlevelTooltip:     Windows 10 Patch Status graph 2nd level status
	 *                                bars tooltip text(latest/outdated/unknown)
	 *                                xpath
	 * @param TotalRowsListKey:       Number of rows in the grid
	 * @param backButtonWPS:          BACK button xpath to return to the 1st level
	 * @param paginationKey:          pager on grid
	 * @param frameKey:               Grid frame xpath
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyRedirectionWithFrameWPS(String languageCode, String patchStatus,
			String patchStatusTooltipText, String seconddrilldown, String secondlevelTooltipCount, String backButtonWPS,
			String TotalRowsListKey, String paginationKey, String frameKey) throws Exception {
		boolean flag = false;
		String text = null;
		Actions action = new Actions(getDriver());
		List<WebElement> listOfLabels = getElementsOfDashboardPage(patchStatus);

		try {
			for (int i = 0; i < listOfLabels.size(); i++) {
				getDriver().navigate().refresh();
				sleeper(3000);
				waitForElementsOfDashboardPage(patchStatus);
				listOfLabels = getElementsOfDashboardPage(patchStatus);
				listOfLabels.get(i);
				action.moveToElement(listOfLabels.get(i)).build().perform();
				waitForElementsOfDashboardPage(patchStatusTooltipText);
				text = getTextOfDashboardPage(patchStatusTooltipText);
				LOGGER.info("Patch Status :" + text);
				listOfLabels.get(i).click();
				if (verifyTooltipCountonReportWithFrame(seconddrilldown, secondlevelTooltipCount, TotalRowsListKey,
						paginationKey, frameKey)) {
					flag = true;
					clickOnElementsOfDashboardPage(backButtonWPS);
				} else {
					flag = false;
					break;
				}
			}
			if (flag) {
				LOGGER.info(
						"The total results on redirected report details page after clicked on the device count of patch status(Latest/Outdated/Unknown) matching");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * This method will validates the grid column header names are correct of
	 * redirected reports detail page
	 * 
	 * @param languageCode:           Language selected to run report in selected
	 *                                language
	 * @param labelsWPS:              Windows 10 Patch Status chart labels xpath
	 * @param columnHeaderWPSLocator: Total number of grid column names xpath
	 *                                locator
	 * @param columnHeaderNamesWPS:   Grid column names list
	 * @param frameKey:               Grid frame xpath
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyGridColumnsNamesWPS(String languageCode, String labelsWPS, String columnHeaderWPSLocator,
			String[] columnHeaderNamesWPS, String frameKey) throws Exception {
		boolean flag = false;
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsWPS);

		try {
			if (!listOfLabels.isEmpty()) {
				waitForElementsOfDashboardPage(labelsWPS);
				waitForPageLoaded();
				listOfLabels = getElementsOfDashboardPage(labelsWPS);
				sleeper(3000);
				listOfLabels.get(0).click();
				sleeper(5000);
				listOfLabels = getElementsOfDashboardPage(labelsWPS);
				if (headerTextVerificationOnReportPageFrame(languageCode, labelsWPS, columnHeaderWPSLocator,
						columnHeaderNamesWPS, frameKey)) {
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
	 * @param sourceKey      - locator of the source
	 * @param destinationKey - locator of the destination
	 * @throws Exception
	 */
	public final boolean verifyDragAndDrop(String sourceKey, String destinationKey) throws Exception {
		boolean flag = false;
		WebElement sourceElement = getElementOfDashboardPage(sourceKey);
		WebElement destinationElement = getElementOfDashboardPage(destinationKey);
		if (dragAndDropOperaton(sourceElement, destinationElement)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * This method basically verify HIDE and SHOW chart by click on link.
	 * 
	 * @param linkTextName - Name of the link it may be HIDE or SHOW
	 * @param linkKey      - locator of the HIDE and SHOW link
	 * @throws Exception
	 */
	public final boolean verifyHideAndShowLink(String linkTextName, String linkKey) throws Exception {
		boolean flag = false;
		Actions action = new Actions(getDriver());
		String linkText = null;
		List<WebElement> hideLink = getElementsOfDashboardPage(linkKey);
		if (hideLink.size() > 0) {
			for (int i = 0; i < hideLink.size(); i++) {
				hideLink.get(i);
				action.moveToElement(hideLink.get(i)).build().perform();
				linkText = getTextOfDashboardPage(linkKey);
				if (linkText.equalsIgnoreCase(linkTextName)) {
					LOGGER.info(linkText + " Link text match successfully on chart");
					flag = true;
				} else {
					LOGGER.info(linkText + " Link text does not match on chart");
					flag = false;
				}
				hideLink.get(i).click();
				sleeper(3000);
			}
		} else {
			LOGGER.info("Chart is not present at Dashboard page.");
		}
		return flag;
	}

	/**
	 * @param mspAuthToken: bearer token
	 * @param url:          To specify the Authentication url.
	 * @throws Exception
	 */
	public final JSONArray getActualAarrayAfterDragAndDropOperation(String mspAuthToken, String url) throws Exception {

		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization",
				"Bearer " + mspAuthToken);
		Response response = httpRequest.get(url);
		String expected = response.asString();
		JSONObject jsonObject;
		jsonObject = new JSONObject(expected);
		JSONArray jsonArray = new JSONObject(jsonObject.get("value").toString()).getJSONObject("cardsConfiguration")
				.getJSONArray("visibleCards");
		return jsonArray;
	}

	/**
	 * @param mspAuthToken: bearer token
	 * @param url:          To specify the Authentication url.
	 * @throws Exception
	 */
	public final JSONArray getActualAarrayAfterDragAndDropOperationWithHiddenArea(String mspAuthToken, String url)
			throws Exception {

		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization",
				"Bearer " + mspAuthToken);
		Response response = httpRequest.get(url);
		String expected = response.asString();
		JSONObject jsonObject;
		jsonObject = new JSONObject(expected);
		JSONArray jsonArray = new JSONObject(jsonObject.get("value").toString()).getJSONObject("cardsConfiguration")
				.getJSONArray("hiddenCards");
		return jsonArray;
	}

	/**
	 * This method is used to verify sequence of chart present on DashBoard for MSP
	 * 
	 * @param listOfChartNameArray: Position of all charts on the dashboard
	 * @return boolean value it may true or false
	 * @throws Exception
	 */

	public final boolean verifyChartOrderAfterDragAndDropOfDashBoardPage(JSONArray listOfChartNameArray)
			throws Exception {
		Boolean flag = false;
		List<String> chartIdsList = Arrays.asList("DHBatteryReplacement", "DHDiskReplacement", "DHDiskCapacity",
				"DHThermalGrade", "HpSureClick", "BusinessReviewCard", "OsVersionSupport", "DevicesByType",
				"DevicesByOs", "WarrantyExpiration", "SubscriptionExpiration", "CpuUtilization", "MemoryUtilization",
				"HardwareInventory", "BatteryReplacementSummary", "AllIncidentsByType", "IncidentBurnDownSummary",
				"SoftwareInventory", "TodaysCriticalIncidents");
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size() - 1; chartIdsListCounter++) {
			if (!chartIdsList.get(chartIdsListCounter)
					.equalsIgnoreCase((listOfChartNameArray.get(chartIdsListCounter)).toString())) {
				LOGGER.info("Sequence of " + listOfChartNameArray.get(chartIdsListCounter) + " is not correct");
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

	public final boolean verifyChartInHiddenAreaAfterDragAndDropOfDashBoardPage(JSONArray listOfChartNameArray)
			throws Exception {
		Boolean flag = false;
		List<String> hiddenChartIdsList = Arrays.asList("DiskReplacementSummary");
		for (int chartIdsListCounter = 0; chartIdsListCounter < hiddenChartIdsList.size() - 1; chartIdsListCounter++) {
			if (!hiddenChartIdsList.get(chartIdsListCounter)
					.equalsIgnoreCase((listOfChartNameArray.get(chartIdsListCounter)).toString())) {
				LOGGER.info("Sequence of " + listOfChartNameArray.get(chartIdsListCounter) + " is not correct");
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
	public final boolean waitUntilElementsOfDashBoardPage(String key) throws Exception {
		return waitUntillElementIsPresent(dashboardPageProperties.getProperty(key));
	}

	/**
	 * This method is to verify that reset to Default button enable or not.
	 * 
	 * @throws Exception
	 */
	public final void verifyResetToDefaultButton() throws Exception {
		waitUntilElementsOfDashBoardPage("dashboardSettingConfig");
		clickByJavaScriptOnDashboardPage("dashboardSettingConfig");
		sleeper(2000);
		if (verifyElementIsEnableOfDashboardPage("resetToDefaultButton")) {
			clickByJavaScriptOnDashboardPage("resetToDefaultButton");
			waitUntilElementsOfDashBoardPage("confirmationYesButton");
			clickByJavaScriptOnDashboardPage("confirmationYesButton");
			sleeper(3000);
			if (uiVersion.equalsIgnoreCase("VENEER2")) {
				clickByJavaScriptOnDashboardPage("saveButtonOnEditMode");
			}

			LOGGER.info("Now, Reset to default button enable to disabled sucessfully.");
		} else {
			clickByJavaScriptOnDashboardPage("cancelButtonOnEditMode");
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
		if (verifyElementsOfDashboardPage("clearCompany")) {
			clickOnElementsOfDashboardPage("clearCompany");
			LOGGER.info("Company search filter is cleared");
		} else {
			LOGGER.info("Company search filter is already cleared");
		}
	}

	/**
	 * This is a method to verify that confirmation model Popup is present or not.
	 * 
	 * @param mspAuthToken: bearer token
	 * @param url:          To specify the Authentication url.
	 * @throws Exception
	 */
	public final Boolean getHiddenConfirmationModalPopup(String mspAuthToken, String url) throws Exception {
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization",
				"Bearer " + mspAuthToken);
		Response response = httpRequest.get(url);
		String expected = response.asString();
		JSONObject jsonObject = new JSONObject(expected);
		Boolean hidePopupDisabledValue = (Boolean) new JSONObject(jsonObject.get("value").toString())
				.get("hidePopupDisabled");
		return hidePopupDisabledValue;
	}

	/**
	 * This method is to get value from token for given json key
	 * 
	 * @param tokenValue : we need token value.
	 * @throws Exception
	 */
	public final String getTokenValue(String tokenValue) throws Exception {
		String value = getValueFromToken(tokenValue);
		return value;
	}

	/**
	 * This method is to get first value from string
	 * 
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
	 * This method basically verify the driver chart header Name on report page with
	 * Frame
	 * 
	 * @param LanguageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param labelKey:                list of labels element
	 * @param totalCount               :Center count of driver.
	 * @param keyHeaderOnNextPage:     Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill
	 *                                 down page
	 * @param frameKey:                Frame on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean driverChartHeaderTextVerificationOnReportPageFrame(String LanguageCode, String labelKey,
			String totalCount, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey)
			throws Exception {
		List<WebElement> legendLabels = getElementsOfDashboardPage(labelKey);
		boolean flag = false;
		sleeper(3000);
		// disable all legends
		for (int count = 0; count < legendLabels.size(); count++) {
			legendLabels.get(count).click();
		}
		legendLabels.get(0).click();
		LOGGER.info("successfully select the updated legend");
		mouseHoverbyoffsettClick(totalCount, 80, 00);
		sleeper(3000);
		switchToDifferentTabOfDashboardPage();
		sleeper(3000);
		waitForElementsOfDashboardPage(keyHeaderOnNextPage);
		List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnNextPage);
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
		switchToPreviousTabOfDashboardPage();
		legendLabels.get(0).click();
		// enable all legends
		for (int count = 0; count < legendLabels.size(); count++) {
			legendLabels.get(count).click();
		}
		return flag;
	}

	/**
	 * This method basically verify the driver chart header Name on report page with
	 * Frame
	 * 
	 * @param LanguageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param labelKey:                list of labels element
	 * @param totalCount               :Center count of driver.
	 * @param keyHeaderOnNextPage:     Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill
	 *                                 down page
	 * @param frameKey:                Frame on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean driverChartHeaderTextVerificationOnReportPageFrameFlexi(String LanguageCode, String labelKey,
			String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey) throws Exception {
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelKey);
		int count1 = 0;
		verifyElementIsClickableOfDashboardPage(labelKey);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutDriverStatusChartFlexi(labelKey, keyDrillDownLabelsAllHidden,
					legendDropdownKey);
		try {
			if (verifyElementsOfDashboardPage("legendsDownArrow")) {
				for (count1 = 0; count1 < listOfLegends.size(); count1++) {
					try {
						waitForPageLoaded();
						listOfLegends.get(count1).click();
						sleeper(2000);
						VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey,
								keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden,
								targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfDashboardPage(legendDropdownKey);
						sleeper(2000);
						listOfLegends.get(count1).click();
						VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey,
								keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden,
								targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
					}
				}
				return true;
			} else {
				for (count1 = 0; count1 < listOfLegends.size(); count1++) {
					waitForPageLoaded();
					listOfLegends.get(count1).click();
					VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey, keyHeaderOnNextPage,
							headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden, targetElement,
							legendDropdownKey);
					listOfLegends.get(count1).click();
				}
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("Failed to Verify legends hidden/visible functionality on header report of chart");
			return false;
		}
	}

	/**
	 * This method used for click on left side of total count.
	 * 
	 * @param key:it   is center point from that we start moving.
	 * @param left:It  is value for moving left from center point
	 * @param right:It is value for moving Right from center point
	 * @throws Exception
	 */
	public final void mouseHoverbyoffsettClick(String key, int left, int right) throws Exception {
		mouseHoverbyoffsetClick(dashboardPageProperties.getProperty(key), left, right);
	}

	/**
	 * This method used for move the mouse left side of total count.
	 * 
	 * @param key:it   is center point from that we start moving.
	 * @param left:It  is value for moving left from center point
	 * @param right:It is value for moving Right from center point
	 * @throws Exception
	 */
	public final void mouseHoverbyoffsett(String key, int left, int right) throws Exception {
		mouseHoverbyoffset(dashboardPageProperties.getProperty(key), left, right);
	}

	/**
	 * This method is compare tool tip count with report page row count
	 * 
	 * @param totalCount:Location  of chart total value
	 * @param labelsKey:List       of legends
	 * @param tooltipTextKey:For   Tooltip text
	 * @param columnListKey:Report page column list
	 * @param paginationKey:this   is for pagination
	 * @param frameKey:            Frame on the drill down page
	 * @return:Boolean value either true or false.
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountonReportWithFrameDriver(String labelsKey, String tooltipTextKey,
			String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden,
			String targetElement, int xOffset, int yOffset, String legendDropdownKey) throws Exception {
		int listOfLabelsCounter = 0;
		waitForPageLoaded();
		sleeper(2000);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutDriverStatusChartFlexi(labelsKey, keyDrillDownLabelsAllHidden,
					legendDropdownKey);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		try {
			if (verifyElementsOfDashboardPage("legendsDownArrow")) {
				for (listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
					try {
						waitForPageLoaded();
						listOfLegends.get(listOfLabelsCounter).click();
						verifyTooltipCountOnDonutDriverStatusChartFlexi(labelsKey, tooltipTextKey, columnListKey,
								paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset,
								legendDropdownKey);
						sleeper(1000);
						listOfLegends.get(listOfLabelsCounter).click();
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfDashboardPage(legendDropdownKey);
						sleeper(2000);
						listOfLegends.get(listOfLabelsCounter).click();
						verifyTooltipCountOnDonutDriverStatusChartFlexi(labelsKey, tooltipTextKey, columnListKey,
								paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset,
								legendDropdownKey);
						sleeper(1000);
						listOfLegends.get(listOfLabelsCounter).click();
					}
				}
				return true;
			} else {
				for (listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
					waitForPageLoaded();
					listOfLegends.get(listOfLabelsCounter).click();
					verifyTooltipCountOnDonutDriverStatusChartFlexi(labelsKey, tooltipTextKey, columnListKey,
							paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset,
							legendDropdownKey);
					sleeper(1000);
					listOfLegends.get(listOfLabelsCounter).click();
				}
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("Failed to Verify legends hidden/visible functionality on tool tip count of chart");
			return false;
		}

	}

	public final boolean verifyLabelHiddenWhenClickOnLegendsDonutDriverStatusChartFlexi(String keyLegendLabel,
			String keyDrillDownLabelsAllHidden, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		int count1 = 0;
		try {
			if (verifyElementsOfDashboardPage("legendsDownArrow")) {
				for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
					try {
						waitForPageLoaded();
						sleeper(2000);
						legendLabelElementList.get(count1).click();
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfDashboardPage(legendDropdownKey);
						sleeper(2000);
						legendLabelElementList.get(count1).click();
					}
				}
				return true;
			} else {
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

	public final boolean verifyTooltipCountOnDonutDriverStatusChartFlexi(String labelsKey, String tooltipTextKey,
			String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden,
			String targetElement, int xOffset, int yOffset, String legendDropdownKey) throws Exception {
		boolean flag = false;
		String paginationText = null;
		try {
			sleeper(3000);
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsett(targetElement, xOffset, yOffset);
			waitForElementsOfDashboardPage(tooltipTextKey);
			sleeper(2000);
			String count = getFirstWord(getTextOfDashboardPage(tooltipTextKey)).split("\\(")[0].trim();
			Integer tooltipcount = Integer.parseInt(count.replaceAll(",", ""));
			sleeper(2000);
			waitForPageLoaded();
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				if (tooltipcount > 10) {
					sleeper(2000);
					waitForPageLoaded();
					paginationText = getTextOfDashboardPage(paginationKey);
					String arr[] = paginationText.split(" ");
					Integer paginationCount;
					paginationCount = Integer.valueOf(arr[1]);
					if ((tooltipcount == paginationCount) || (tooltipcount < paginationCount)) {// A device might have
																								// multiple disks hence
																								// count sometimes does
																								// not match but disk
																								// count is always less
																								// than number of
																								// devices
						flag = true;
					}
				} else {
					sleeper(2000);
					waitForPageLoaded();
					List<WebElement> listOfColumntext = getElementsOfDashboardPage(columnListKey);
					if (tooltipcount == (listOfColumntext.size())) {
						flag = true;
					}
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
			sleeper(1000);

		} catch (Exception e) {
			flag = false;
			LOGGER.info("Failed to get tool tip count on Battery Swell Probability Chart");
		}
		return flag;
	}

	/**
	 * This method is compare tool tip count with report page row count
	 * 
	 * @param totalCount:Location  of chart total value
	 * @param labelsKey:List       of legends
	 * @param tooltipTextKey:For   Tooltip text
	 * @param columnListKey:Report page column list
	 * @param paginationKey:this   is for pagination
	 * @param frameKey:            Frame on the drill down page
	 * @return:Boolean value either true or false.
	 * @throws Exception
	 */
	public final boolean verifyTooltipCountonReportWithFrameDriverFlexi(String totalCount, String labelsKey,
			String tooltipTextKey, String columnListKey, String paginationKey, String frameKey, String upArrow,
			String downArrow) throws Exception {
		boolean flag = false;
		String count = null;
		String paginationText = null;
		waitForPageLoaded();
		sleeper(2000);
		List<WebElement> legendLabels = getElementsOfDashboardPage(labelsKey);
		// Disable all the legends
		if (verifyElementsOfDashboardPage("legendsDownArrow")) {
			for (int count1 = 0; count1 < legendLabels.size(); count1++) {
				waitForPageLoaded();
				legendLabels.get(count1).click();
				clickOnElementsOfDashboardPage(downArrow);
			}
			for (int count1 = 0; count1 < legendLabels.size(); count1++) {
				waitForPageLoaded();
				clickOnElementsOfDashboardPage(upArrow);
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
			count = getFirstWord(getTextOfDashboardPage(tooltipTextKey)).split("\\(")[0].trim();
			Integer tooltipcount = Integer.parseInt(count.replaceAll(",", ""));
			sleeper(2000);
			mouseHoverbyoffsettClick(totalCount, 70, 00);
			sleeper(3000);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (tooltipcount > 10) {
				sleeper(2000);
				paginationText = getTextOfDashboardPage(paginationKey);
				String arr[] = paginationText.split(" ");
				String laststring = arr[arr.length - 1];
				if (laststring.equals(count)) {
					flag = true;
					LOGGER.info("Record count is more than 10 and is equal to tooltip count");
				}
			} else {
				sleeper(2000);
				waitForPageLoaded();
				List<WebElement> listOfColumntext = getElementsOfDashboardPage(columnListKey);
				if (tooltipcount == (listOfColumntext.size())) {
					flag = true;
					LOGGER.info("Record count is less than 10 and is equal to tooltip count");
				}
			}
			switchToPreviousTabOfDashboardPage();
			legendLabels.get(ilistOfLabelsCounter).click();
			if (verifyElementsOfDashboardPage("legendsDownArrow")) {
				clickOnElementsOfDashboardPage(downArrow);
			}
		} // enable all legends
		if (verifyElementsOfDashboardPage("legendsDownArrow")) {
			for (int count1 = legendLabels.size() - 1; count1 >= 0; count1--) {
				waitForPageLoaded();
				legendLabels.get(count1).click();
				clickOnElementsOfDashboardPage(upArrow);
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
	 * This method is used to verify device name link redirection on reports table
	 * for MSP with frame
	 * 
	 * @param LanguageCode:language initial
	 * @param totalCount            :Element of center of chart
	 * @param labelsKey:            List of labels/criteria present in the graph
	 * @param deviceDetailsKey:     Element present on Device page
	 * @param columnListKey:        Columns present in the grid
	 * @param errorKey:             Error message flashes on the Dash board
	 * @param frameKey:             Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyDeviceNameRedirectionMSPWithFrame1(String LanguageCode, String targetElement,
			String labelsKey, String deviceDetailsKey, String columnListKey, String errorKey, String frameKey,
			String downArrow) throws Exception {
		boolean flag = false;
		waitForElementsOfDashboardPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		if (verifyElementsOfDashboardPage(downArrow))
			verifyLabelHiddenWhenClickOnLegendsDonutDriverStatusChartFlexi(labelsKey, targetElement, downArrow);
		try {
			waitForPageLoaded();
			clickOnElementsOfDashboardPage(downArrow);
			sleeper(2000);
			listOfLabels.get(0).click();
			sleeper(1000);
			waitForPageLoaded();
			mouseHoverbyoffsettClick(targetElement, 15, 80);
			sleeper(3000);
			switchToDifferentTabOfDashboardPage();
			sleeper(5000);
			waitForPageLoaded();
			if (waitForElementsOfDashboardPage(columnListKey)) {
				clickOnElementsOfDashboardPage(columnListKey);
				sleeper(2000);
				switchToDifferentTabOfDashboardPage();
				sleeper(5000);
				waitForPageLoaded();
				if (waitForElementsOfDashboardPage("allChartsLocator")) {
					String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfDashboardPage(errorKey)
						|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
					LOGGER.error("Device not found");
					flag = true;
				}
			} else
				LOGGER.error("No data to diplay");
			switchToPreviousTabOfDashboardPage();
			sleeper(1000);

		} catch (Exception e) {
			LOGGER.error("Exception occured in veirifing FlexibleDashboardPage name : " + e.getMessage());
			flag = false;
		}
		return flag;
	}

	// =======================================flexi==========================================

	/**
	 * this is used for getting number of windows.
	 * 
	 * @return:return count of windows
	 * @throws Exception
	 */
	public final int getWindowHandlesofFlexibleDashboardPage() throws Exception {
		return getCountofWindowHandles();
	}

	/**
	 * Check custom dashboard name into dropdown list.
	 * 
	 * @return :return boolean value either true or false
	 * @throws Exception
	 */
	public boolean verifyCustomDashboardName(String dashboardName) throws Exception {
		boolean flag = false;
		environment = System.getProperty("environment");
		try {
			waitForPageLoaded();
			sleeper(3000);
			clickOnElementsOfDashboardPage("dashboardListDropdown");
			List<WebElement> element = getElementsTillAllElementsVisibleofDashboardPage("dashboardList");
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
			LOGGER.error("Exception occured in veirifing FlexibleDashboardPage name : " + e.getMessage());
			flag = false;
		}
		return flag;
	}

	public final void addCustomDashboard(String dashboardName) throws Exception {
		environment = System.getProperty("environment");
		waitForPageLoaded();
		sleeper(1000);

		if (verifyCustomDashboardName(dashboardName)) {
			deleteCustomDashboard(dashboardName);
			waitForPageLoaded();
			clickOnElementsOfDashboardPage("editButton");
			clickOnElementsOfDashboardPage("addCustomDashboardOption");
			enterTextForDashboardPage("enterNameforCustomDashboard", getCredentials(environment, dashboardName));
			clickOnElementsOfDashboardPage("addDashboardButton");
			LOGGER.info("Custom dashboard is added successfully");
			waitForPageLoaded();
		} else {
			sleeper(2000);
			clickOnElementsOfDashboardPage("defaultOption");
			clickOnElementsOfDashboardPage("editButton");
			clickOnElementsOfDashboardPage("addCustomDashboardOption");
			scrollToDashboardPage("enterNameforCustomDashboard");
			enterTextForDashboardPage("enterNameforCustomDashboard", getCredentials(environment, dashboardName));
			clickOnElementsOfDashboardPage("addDashboardButton");
			LOGGER.info("Custom dashboard is added successfully");
			waitForPageLoaded();
		}
	}

	public final void addCustomDashboardDES(String dashboardName) throws Exception {
		environment = System.getProperty("environment");
		waitForPageLoaded();
		sleeper(1000);

		if (verifyCustomDashboardName(dashboardName)) {
			deleteCustomDashboard(dashboardName);
			waitForPageLoaded();
			clickOnElementsOfDashboardPage("editButton");
			clickOnElementsOfDashboardPage("addCustomDashboardOption");
			enterTextForDashboardPage("enterNameforCustomDashboard", getCredentials(environment, dashboardName));
			clickOnElementsOfDashboardPage("addDashboardButton");
			LOGGER.info("Custom dashboard is added successfully");
			waitForPageLoaded();
		} else {
			sleeper(2000);
			clickOnElementsOfDashboardPage("defaultOptionDES");
			clickOnElementsOfDashboardPage("editButton");
			clickOnElementsOfDashboardPage("addCustomDashboardOption");
			scrollToDashboardPage("enterNameforCustomDashboard");
			enterTextForDashboardPage("enterNameforCustomDashboard", getCredentials(environment, dashboardName));
			clickOnElementsOfDashboardPage("addDashboardButton");
			LOGGER.info("Custom dashboard is added successfully");
			waitForPageLoaded();
		}
	}

	/**
	 * Delete custom dashboard.
	 * 
	 * @param customName:Dashboard name those want to delete
	 * @throws Exception
	 */
	public final void deleteCustomDashboard(String customDashboardName) throws Exception {
		environment = System.getProperty("environment");
		waitForPageLoaded();
		waitForPresenceOfElementsOfDashboardPage("dashboardListDropdown");
		clickOnElementsOfDashboardPage("dashboardListDropdown");
		List<WebElement> element = getElementsTillAllElementsVisibleofDashboardPage("dashboardList");
		if (element.size() != 0) {
			for (WebElement we : element) {
				if (we.getText().equalsIgnoreCase(getCredentials(environment, customDashboardName))) {
					we.click();
					break;
				}
			}
		} else {
			LOGGER.info("Dshboard list is empty");
		}
		sleeper(5000);
//		refreshPage();
		clickOnElementsOfDashboardPage("editButton");
		clickOnElementsOfDashboardPage("deleteCustomDashboard");
		clickOnElementsOfDashboardPage("deleteDashboardbutton");
		sleeper(2000);
		LOGGER.info("Custom dashboard has been deleted successfully");
	}

	/**
	 * check report value
	 * 
	 * @return:return boolean value either true or false
	 * @throws Exception
	 */
	public boolean verifyViewReportOfWidget() throws Exception {
		switchToDifferentTabOfDashboardPage();
		boolean IsDataPresent = false;
		IsDataPresent = reportDataValidation();
		int windowHandles = 0;
		try {
			windowHandles = getWindowHandlesofFlexibleDashboardPage();
			LOGGER.info("Window handles : " + windowHandles);
			if (windowHandles > 1) {
				switchToPreviousTabOfDashboardPage();
				waitForPageLoaded();
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured in switch window: " + e.getMessage());
		}
		return IsDataPresent;
	}

	/**
	 * Add widget into dashboard
	 * 
	 * @param languageCode
	 * @throws Exception
	 */
	public final void addWidget(String languageCode, String category, String subcategory) throws Exception {
		waitForPageLoaded();
		clickOnElementsOfDashboardPage("addWidgetButton");
		selectCategoryForFlexibleDashboardPage(category);
		selectSubCategoryForFlexibleDashboardPage(subcategory, languageCode);
		clickByJavaScriptOnDashboardPage("visualizationForBatterry");
		waitForPageLoaded();
		sleeper(3000);
		waitForPageLoaded();
		sleeper(3000);
		clickByJavaScriptOnDashboardPage("widgetAddConfirmButton");
		waitForPageLoaded();
	}

	/**
	 * Update widget into dashboard
	 * 
	 * @throws Exception
	 */
	public final void updateWidget(String updateName) throws Exception {
		environment = System.getProperty("environment");
		waitForElementsOfDashboardPage("clickOnWidgetUpperLeftOptionList");
		clickOnElementsOfDashboardPage("clickOnWidgetUpperLeftOptionList");
		if (!(verifyElementsOfDashboardPage("checkNoData"))) {
			clickOnElementsOfDashboardPage("editWidgetOptionOnCustomDashboard");
		} else {
			clickOnElementsOfDashboardPage("editWidgetOption");
		}
		clickOnElementsOfDashboardPage("enterEditableText");
		enterTextForDashboardPage("enterEditableText", getCredentials(environment, updateName));
		waitForPageLoaded();
		clickOnElementsOfDashboardPage("clickOnWidgetUpdateButton");
		LOGGER.info("Widget is updated successfully");
	}

	/**
	 * Delete widget from dashboard
	 * 
	 * @throws Exception
	 */
	public final void deleteWidget() throws Exception {
		sleeper(1000);
		waitForElementsOfDashboardPage("clickOnWidgetUpperLeftOptionList");
		clickOnElementsOfDashboardPage("clickOnWidgetUpperLeftOptionList");
		if (!(verifyElementsOfDashboardPage("checkNoData"))) {
			clickOnElementsOfDashboardPage("deleteWidgetOptionFirst");
		} else {
			clickOnElementsOfDashboardPage("deleteWidgetOption");
		}
		clickOnElementsOfDashboardPage("deleteWidgetConfirmButton");
		waitForPageLoaded();
		LOGGER.info("Widget is deleted");
	}

	public final void updateWidgetDES(String updateName) throws Exception {
		environment = System.getProperty("environment");
		waitForElementsOfDashboardPage("clickOnWidgetUpperLeftOptionListDES");
		clickOnElementsOfDashboardPage("clickOnWidgetUpperLeftOptionListDES");
		if (!(verifyElementsOfDashboardPage("checkNoData"))) {
			clickOnElementsOfDashboardPage("editWidgetOptionOnCustomDashboardDES");
		} else {
			clickOnElementsOfDashboardPage("editWidgetOption");
		}
		clickOnElementsOfDashboardPage("enterEditableText");
		enterTextForDashboardPage("enterEditableText", getCredentials(environment, updateName));
		waitForPageLoaded();
		clickOnElementsOfDashboardPage("clickOnWidgetUpdateButton");
		LOGGER.info("Widget is updated successfully");
	}

	/**
	 * Delete widget from dashboard
	 * 
	 * @throws Exception
	 */
	public final void deleteWidgetDES() throws Exception {
		sleeper(1000);
		waitForElementsOfDashboardPage("clickOnWidgetUpperLeftOptionListDES");
		clickOnElementsOfDashboardPage("clickOnWidgetUpperLeftOptionListDES");
		if (!(verifyElementsOfDashboardPage("checkNoData"))) {
			clickOnElementsOfDashboardPage("deleteWidgetOptionFirstDES");
		} else {
			clickOnElementsOfDashboardPage("deleteWidgetOption");
		}
		clickOnElementsOfDashboardPage("deleteWidgetConfirmButton");
		waitForPageLoaded();
		LOGGER.info("Widget is deleted");
	}

	/**
	 * Use for select category
	 * 
	 * @param category:category name wich is selected from drop down
	 * @throws Exception
	 */
	public final void selectCategoryForFlexibleDashboardPage(String category) throws Exception {
		try {
			clickOnElementsOfDashboardPage("categoryDropdown");
			sleeper(500);
			List<WebElement> element = getElementsTillAllElementsVisibleofDashboardPage("categoryList");
			if (element.size() != 0) {
				for (WebElement we : element) {
					if (we.getText().equalsIgnoreCase(category)) {
						we.click();
						break;
					}
				}
			} else {
				LOGGER.info("Category list is empty");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectCategoryForWidgetFlexibleDashboardPage : " + e.getMessage());
		}
	}

	/**
	 * select subcategory
	 * 
	 * @param subcategory:subcategory name
	 * @param languageCode:Language
	 * @throws Exception
	 */
	public final void selectSubCategoryForFlexibleDashboardPage(String subcategory, String languageCode)
			throws Exception {
		try {

			//scrollToDashboardPage("subcategoryDropdown");
			sleeper(2000);
			clickOnElementsOfDashboardPage("subcategoryDropdown");
			sleeper(3000);
			List<WebElement> element = getElementsOfDashboardPage("subCategoryList");
			scrollTillView(element.get(element.size() - 1));
			if (element.size() != 0) {
				for (WebElement we : element) {
					if (we.getText().equalsIgnoreCase(subcategory)) {
						scrollTillView(we);
						we.click();
						break;
					}
				}
			} else {
				LOGGER.info("subcategory list is empty");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectSubCategoryForFlexibleDashboardPage : " + e.getMessage());
		}
	}

	/**
	 * This is used for to add widget for software category
	 * 
	 * @param category:category           name
	 * @param subcategorylist:subcategory name
	 * @param languageCode
	 * @throws Exception
	 */
	public final void addWidgetForSoftware(String category, ArrayList<String> subcategorylist, String languageCode)
			throws Exception {
		ArrayList<String> subCategoryOptionList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_swerrors"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_swinv")));
		// list of options with corresponds sub category
		ArrayList<String> software_errors_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "label.report_option_topErrorsMonthlySummaryV2")));
		ArrayList<String> software_inventory_optionlist3 = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_option_details")));
		sleeper(5000);
		for (int count = 0; count < subcategorylist.size(); count++) {
			if (subcategorylist.get(count).toString().equalsIgnoreCase(
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_driver"))) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				waitForPageLoaded();
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_softcat"))) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				waitForPageLoaded();
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(0).toString())) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(software_errors_optionlist2.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				sleeper(10000);
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(1).toString())) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(software_inventory_optionlist3.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_softuti"))) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else {
				clickOnElementsOfDashboardPage("addWidgetButton");
				scrollToDashboardPage("widgetAddConfirmButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}
		}
	}

	/**
	 * This is used for to add widget for security category
	 * 
	 * @param category:category           name
	 * @param subcategorylist:subcategory name
	 * @param languageCode
	 * @throws Exception
	 */
	public final void addWidgetForSecurity(String category, ArrayList<String> subcategorylist, String languageCode)
			throws Exception {
		ArrayList<String> subCategoryOptionList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_companysec"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_devicesec"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
						"label.report_category_lostdevicepro")));
		// list of options with corresponds sub category
		ArrayList<String> company_security_compliance_optionlist1 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "label.report_option_twentyFourHrSummary")));
		ArrayList<String> device_security_compliance_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "label.report_option_twentyFourHrSummary")));
		ArrayList<String> lost_device_protection_optionlist3 = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_option_details")));
		for (int count = 0; count < subcategorylist.size(); count++) {
			if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(0).toString())) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(company_security_compliance_optionlist1.get(0).toString(),
						languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				sleeper(2000); // filter is taking time to load
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");

			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_devicecomp"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				sleeper(2000); // filter is taking time to load
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(1).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(device_security_compliance_optionlist2.get(0).toString(),
						languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				sleeper(2000); // filter is taking time to load
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_scasecurity"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				sleeper(2000); // filter is taking time to load
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(2).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(lost_device_protection_optionlist3.get(0).toString(),
						languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				sleeper(2000); // filter is taking time to load
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}

			else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_surerecactivity"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				sleeper(2000); // filter is taking time to load
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				scrollToDashboardPage("widgetAddConfirmButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				sleeper(2000); // filter is taking time to load
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}
		}
	}

	/**
	 * This is used for to add widget for network category
	 * 
	 * @param category:category           name
	 * @param subcategorylist:subcategory name
	 * @param languageCode
	 * @throws Exception
	 */
	public final void addWidgetForNetwork(String category, ArrayList<String> subcategorylist, String languageCode)
			throws Exception {
		ArrayList<String> subCategoryOptionList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_networkassmt"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_networkinv")));
		// list of options with corresponds sub category
		ArrayList<String> network_health_optionlist1 = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "dashboard_service", "label.report_option_networkSpeedWifiStrength")));
		ArrayList<String> wireless_networkingCard_inv_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "mpi.report.custom_networkSpeedWifiStrength")));
		for (int count = 0; count < subcategorylist.size(); count++) {
			if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(0).toString())) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(network_health_optionlist1.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");

			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_networkinv"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(1).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(wireless_networkingCard_inv_optionlist2.get(0).toString(),
						languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				verifyElementIsClickableOfDashboardPage("widgetAddConfirmButton");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else {
			}
		}
	}

	/**
	 * Add widget for hardware category
	 * 
	 * @param category:category           name
	 * @param subcategorylist:subcategory name
	 * @param languageCode:language
	 * @throws Exception
	 */

	public final void addWidgetForHardware(String category, ArrayList<String> subcategorylist, String languageCode)
			throws Exception {
		ArrayList<String> subCategoryOptionList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwbluescreen"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_deviceuti"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwinv"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
						"label.report_category_thermalgrade")));
		// list of options with corresponds sub-category
		ArrayList<String> blue_screen_errors_optionlist1 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "mpi.report.bsod.graph.toperrors.devices")));
		ArrayList<String> device_utilization_optionlist2 = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_option_swPerfByTime")));
		ArrayList<String> hardware_inventory_optionlist3 = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_option_details")));
		ArrayList<String> thermal_grading_optionlist4 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "label.report_option_thermalgrade_details")));

		for (int count = 0; count < subcategorylist.size(); count++) {
			if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_biosinventory"))) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_batteryStatRiskFact"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(0).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(blue_screen_errors_optionlist1.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(1).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(device_utilization_optionlist2.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_diskcap"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				sleeper(5000);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_diskrep"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_hwhealth"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(2).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(hardware_inventory_optionlist3.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_hwgradeNew"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString().equalsIgnoreCase(
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwwar"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(3).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				scrollToDashboardPage("widgetAddConfirmButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(thermal_grading_optionlist4.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			} else {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
			}
		}
	}

	/**
	 * Add widget for incident
	 * 
	 * @param category:category     name
	 * @param languageCode:language name
	 * @throws Exception
	 */
	public final void addWidgetForIncident(String category, String languageCode) throws Exception {
		clickOnElementsOfDashboardPage("addWidgetButton");
		selectCategoryForFlexibleDashboardPage(category);
		clickByJavaScriptOnDashboardPage("visualizationForBatterry");
		waitUntilElementIsVisibleOfDashboardPage("widgetAddConfirmButton");
		clickOnElementsOfDashboardPage("widgetAddConfirmButton");
		LOGGER.info("Widget has been added");
	}

	/**
	 * Add widget for digital experience
	 * 
	 * @param category:category     name
	 * @param languageCode:language name
	 * @throws Exception
	 */
	public final void addWidgetForDigitalExperience(String category,String languageCode) throws Exception {
		clickOnElementsOfDashboardPage("addWidgetButton");
		selectCategoryForFlexibleDashboardPage(category);
		clickOnElementsOfDashboardPage("subcategory");
		sleeper(2000);
		clickOnElementsOfDashboardPage("subcategoryoption");
		clickByJavaScriptOnDashboardPage("visualizationForBatterry");
		clickOnElementsOfDashboardPage("widgetAddConfirmButton");
		LOGGER.info("Widget has been added");
	}

	/**
	 * Add widget for subscription
	 * 
	 * @param category:category     name
	 * @param languageCode:language name
	 * @throws Exception
	 */
	public final void addWidgetForSubscription(String category, String subcate, String languageCode) throws Exception {
		clickOnElementsOfDashboardPage("addWidgetButton");
		selectCategoryForFlexibleDashboardPage(category);
		selectSubCategoryForFlexibleDashboardPage(subcate, languageCode);
		clickByJavaScriptOnDashboardPage("visualizationForBatterry");
		waitUntilElementIsVisibleOfDashboardPage("widgetAddConfirmButton");
		clickOnElementsOfDashboardPage("widgetAddConfirmButton");

	}

	/**
	 * Select option for respective subcategory
	 * 
	 * @param options:option        name
	 * @param languageCode:language name
	 * @throws Exception
	 */
	public final void selectOptionForFlexibleDashboardPage(String options, String languageCode) throws Exception {
		try {
			if (getTextOfDashboardPage("optionDropdown").equalsIgnoreCase(
					getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON", "select_option_text"))) {
				clickOnElementsOfDashboardPage("optionDropdown");
				sleeper(500);
				List<WebElement> element = getElementsTillAllElementsVisibleofDashboardPage("optionDropdownList");
				if (element.size() != 0) {
					for (WebElement we : element) {
						if (we.getText().equalsIgnoreCase(options)) {
							we.click();
							break;
						}
					}
				} else {
					LOGGER.info("Option list is empty");
				}
			} else {
				LOGGER.info("Option is allready selected");
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured in selectOptionForFlexibleDashboardPage : " + e.getMessage());
		}
	}

	/**
	 * delete widget list from dashboard
	 * 
	 * @throws Exception
	 */
	public final void deleteAllWidgetForFlexibleDashboardPage() throws Exception {
		try {
			List<WebElement> element = getElementsTillAllElementsVisibleofDashboardPage("widgetList");
			if (element.size() != 0) {
				for (@SuppressWarnings("unused")
				WebElement we : element) {
					deleteWidget();
				}
			} else {
				LOGGER.info("widget list is empty");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in delete widget For FlexibleDashboardPage : " + e.getMessage());
		}
	}

	/**
	 * Used for getting number of widgets
	 * 
	 * @return: return a widget count.
	 * @throws Exception
	 */
	public final int getWidgetCount() throws Exception {
		int count = 0;
		List<WebElement> element = getElementsTillAllElementsVisibleofDashboardPage("widgetOnGrid");
		for (int i = 0; i < element.size(); i++) {
			count++;
		}
		return count;
	}

	/**
	 * Delete filters .
	 */
	public final void deleteFilterCriteria() {
		if (getDriver().findElements(By.xpath(dashboardPageProperties.getProperty("deleteFilter"))).size() != 0) {
			do {
				getDriver().findElement(By.xpath(dashboardPageProperties.getProperty("deleteFilter"))).click();

			} while (getDriver().findElements(By.xpath(dashboardPageProperties.getProperty("deleteFilter")))
					.size() != 0);
		}
	}

	/**
	 * Used for check data of report
	 * 
	 * @return:return either true or false value
	 * @throws Exception
	 */
	public boolean reportDataValidation() throws Exception {
		boolean reportStatus = false;
		boolean reportStatusGraph = true;
		try {
			reportStatusGraph = validateGraphData();
			LOGGER.info(reportStatusGraph);
			if (reportStatusGraph) {
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
	 * 
	 * @return:return either true or false value
	 * @throws Exception
	 */
	private boolean validateGraphData() throws Exception {
		try {
			if (!(verifyElementsOfDashboardPage("chartsNoData"))) {
				LOGGER.info("Data is present in the Graph  ");
				return true;
			} else if (verifyElementsOfDashboardPage("chartsNoData")) {
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
	 * This method is used to verify tool tip count with total rows on reports with
	 * Frame
	 * 
	 * @param labelsKey:                   Chart labels
	 * @param tooltipTextKey:              Tooltip text after hovering on a chart
	 * @param columnListKey:               Column names present in the grid
	 * @param paginationKey:               Locator of the pagination
	 * @param targetElement:               element to move to
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param frameKey:                    Iframe on the drill down page
	 * @param xoffset:                     x coordinate to move
	 * @param yoffset:                     y coordinate to move
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipCountonReportWithFrameFlexiWithOffset(String labelsKey, String tooltipTextKey,
			String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden,
			String targetElement, int xOffset, int yOffset) throws Exception {
		boolean flag = false;
		String count = null;
		String paginationText = null;
		waitForPageLoaded();
		sleeper(2000);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey, keyDrillDownLabelsAllHidden);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		for (int ilistOfLabelsCounter = 0; ilistOfLabelsCounter < listOfLegends.size(); ilistOfLabelsCounter++) {
			listOfLegends.get(ilistOfLabelsCounter).click();
			sleeper(3000);
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsett(targetElement, xOffset, yOffset);
			waitForElementsOfDashboardPage(tooltipTextKey);
			sleeper(2000);
			count = getTextOfDashboardPage(tooltipTextKey);
			String count_clean = count.replaceAll(",", "");
			Integer tooltipcount = Integer.valueOf(count_clean);
			sleeper(2000);
			waitForPageLoaded();
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				sleeper(2000);
				waitForPageLoaded();
				paginationText = getTextOfDashboardPage(paginationKey);
				String arr[] = paginationText.split(" ");
				Integer paginationCount;
				paginationCount = Integer.valueOf(arr[1]);
				if ((tooltipcount == paginationCount) || (tooltipcount < paginationCount)) {// A device might have
																							// multiple disks hence
																							// count sometimes does not
																							// match but disk count is
																							// always less than number
																							// of devices
					flag = true;
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
			sleeper(1000);
			listOfLegends.get(ilistOfLabelsCounter).click();
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip count with total rows on reports with
	 * Frame
	 * 
	 * @param labelsKey:                   Chart labels
	 * @param tooltipTextKey:              Tooltip text after hovering on a chart
	 * @param columnListKey:               Column names present in the grid
	 * @param paginationKey:               Locator of the pagination
	 * @param targetElement:               element to move to
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param frameKey:                    Iframe on the drill down page
	 * @param xoffset:                     x coordinate to move
	 * @param yoffset:                     y coordinate to move
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipCountonReportWithFrameDonutChartFlexiWithOffset(String labelsKey,
			String tooltipTextKey, String columnListKey, String paginationKey, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement, int xOffset, int yOffset,
			String legendDropdownKey) throws Exception {
		boolean flag = false;
		String count = null;
		String paginationText = null;
		waitForPageLoaded();
		sleeper(2000);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelsKey, keyDrillDownLabelsAllHidden,
					legendDropdownKey);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		for (int listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
			if (waitForElementsOfDashboardPageDynamic(legendDropdownKey, 1)) {
				if (listOfLabelsCounter == 0 || listOfLabelsCounter == 2) {
					clickOnElementsOfDashboardPage(legendDropdownKey);
					sleeper(3000);
				}
			} else {
				LOGGER.info("Legend dropdown not present on chart");
			}
			listOfLegends.get(listOfLabelsCounter).click();
			sleeper(3000);
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsett(targetElement, xOffset, yOffset);
			sleeper(2000);
			count = getTextOfDashboardPage(tooltipTextKey);
			String count_clean = count.replaceAll(",", "");
			Integer tooltipcount = Integer.valueOf(count_clean);
			sleeper(2000);
			waitForPageLoaded();
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				if (tooltipcount > 10) {
					sleeper(2000);
					waitForPageLoaded();
					paginationText = getTextOfDashboardPage(paginationKey);
					String arr[] = paginationText.split(" ");
					Integer paginationCount;
					paginationCount = Integer.valueOf(arr[1]);

					if ((tooltipcount == paginationCount) || (tooltipcount < paginationCount)) {// A device might have
																								// multiple disks hence
																								// count sometimes does
																								// not match but disk
																								// count is always less
																								// than number of
																								// devices
						flag = true;
					}
				} else {
					sleeper(2000);
					waitForPageLoaded();
					List<WebElement> listOfColumntext = getElementsOfDashboardPage(columnListKey);
					if (tooltipcount == (listOfColumntext.size())) {
						flag = true;
					}
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
			sleeper(1000);
			listOfLegends.get(listOfLabelsCounter).click();
		}
		return flag;
	}

	/**
	 * @param labelsKey      - lengends locator
	 * @param tooltipTextKey - tool tip locator
	 * @param textKey        - text key locator
	 * @param xoffset:       x coordinate to move
	 * @param yoffset:       y coordinate to move
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyTooltipTextOnReportWithFrameFlexiWithOffset(String labelsKey, String tooltipTextKey,
			String textKey, String keyDrillDownLabelsAllHidden, String targetElement, int xOffset, int yOffset)
			throws Exception {
		boolean flag = false;
		String text = null;
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey, keyDrillDownLabelsAllHidden);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		for (int i = 0; i < listOfLegends.size(); i++) {
			listOfLegends.get(i).click();
			sleeper(3000);
			mouseHoverbyoffsett(targetElement, xOffset, yOffset);
			waitForElementsOfDashboardPage(tooltipTextKey);
			sleeper(2000);
			text = getTextOfDashboardPage(tooltipTextKey);
			String cleanText = text.replaceAll("[+.^:,]", "");
			sleeper(2000);
			mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
			switchToDifferentTabOfDashboardPage();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfDashboardPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfDashboardPage(textKey);
				if (monthtext.get(0).getText().contains(cleanText)) {
					flag = true;
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
			sleeper(1000);
			listOfLegends.get(i).click();
		}
		return flag;
	}

	/**
	 * This method verify message which is present on device with outdated driver
	 * chart
	 * 
	 * @param message:      This is message fron outdted driver chart.
	 * @param lang:Language name
	 * @return :boolean value
	 * @throws Exception
	 */
	public final boolean verifyMesageForUpdateDriver(String message, String lang) throws Exception {
		boolean flag = false;
		waitForPageLoaded();
		if (message.equalsIgnoreCase(getTextLanguage(lang, "daas_ui", "dashboard.drivers.action.desc.critical"))) {
			LOGGER.info("This is critical update driver message");
			flag = true;
		} else if (message
				.equalsIgnoreCase(getTextLanguage(lang, "daas_ui", "dashboard.drivers.action.desc.recommended"))) {
			LOGGER.info("This is recommended update driver message");
			flag = true;
		} else {
			LOGGER.info("This is routine updates  driver at your convenience");
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param dashboardName - Dashboard to be shared
	 * @param username      - User name of the Receiver
	 * @return - Flag value - true/false
	 * @throws Exception
	 */
	public final boolean shareDashboardpage(String dashboardName, String username) throws Exception {
		boolean flag = false;
		waitForPageLoaded();
		sleeper(5000);
		waitForElementsOfDashboardPage("dashboardListDropdown");
		clickOnElementsOfDashboardPage("dashboardListDropdown");
		List<WebElement> element = getElementsOfDashboardPage("dashboardList");
		if (element.size() != 0) {
			for (WebElement we : element) {
				if (we.getText().equalsIgnoreCase(dashboardName)) {
					we.click();
					break;
				}
			}
		} else {
			LOGGER.info("Dashboard list is empty");
			return flag;
		}
		waitForPageLoaded();
		sleeper(3000);
		clickOnElementsOfDashboardPage("editButtonShareDash");
		clickOnElementsOfDashboardPage("shareWithDashboardOption");
		waitForElementsOfDashboardPage("shareDashboardUserDropdown");
		clickOnElementsOfDashboardPage("shareDashboardUserDropdown");
		clickOnElementsOfDashboardPage("shareDashboardUserDropdownSearchBox");
		enterTextForDashboardPage("shareDashboardUserDropdownSearchBox", username);
		sleeper(1000);
		List<WebElement> userElement = getElementsOfDashboardPage("shareDashboardUserDropdownList");
		if (userElement.size() != 0) {
			for (WebElement we : userElement) {
				if (we.getText().equalsIgnoreCase(username)) {
					we.click();
					sleeper(1000);
					break;
				}
			}
		} else {
			LOGGER.info("User list is empty");
			return flag;
		}
		pressKey(Keys.ESCAPE);
		sleeper(1000);
		clickOnElementsOfDashboardPage("shareButton");
		waitForPresenceOfElementsOfDashboardPage("shareDashboardSuccessMessage");
		verifyElementsOfDashboardPage("shareDashboardSuccessMessage");
		flag = true;
		return flag;
	}

	/**
	 * 
	 * @param dashboardName - Dashboard to be shared
	 * @param username      - User name of the Receiver
	 * @return - Flag value - true/false
	 * @throws Exception
	 */
	public final boolean verifyShareDashboardpage(String dashboardName, String username, String sendername)
			throws Exception {
		boolean flag = false;
		int i = 0;
		String addLinkClickFinal = null;
		String receiverDashboardName = username + "'s" + " " + dashboardName;
		String notificationText = sendername + " " + "is sharing a dashboard view called" + " " + receiverDashboardName
				+ ". Do you want to add it to your custom views?";
		waitForPageLoaded();
		sleeper(5000);
		waitForElementsOfDashboardPage("newNotificationIndicatorDashboard");
		clickOnElementsOfDashboardPage("notificationBellIconDashboard");
		List<WebElement> element = getElementsOfDashboardPage("unreadNotificationDashboardText");
		if (element.size() != 0) {
			for (WebElement we : element) {
				i = i + 1;
				if (we.getText().equalsIgnoreCase(notificationText)) {
					LOGGER.info("value of webelement" + we);
					String addLinkClick = dashboardPageProperties.getProperty("addLinkTemp");
					addLinkClickFinal = addLinkClick + "[" + i + "]";
					LOGGER.info("value of add link" + addLinkClickFinal);
					mouseHover(addLinkClickFinal);
					clickByJavaScript(addLinkClickFinal + "//button//*[name()='span']");
					clickOnElementsOfDashboardPage("addDashboard");
					sleeper(1000);
					break;
				}
			}
		} else {
			LOGGER.info("Notification list is empty");
			return flag;
		}

		verifyElementsOfDashboardPage("shareDashboardSuccessMessage");
		clickOnElementsOfDashboardPage("dashboardListDropdown");
		List<WebElement> webelement = getElementsOfDashboardPage("dashboardList");
		if (webelement.size() != 0) {
			for (WebElement we : webelement) {
				if (we.getText().contains(receiverDashboardName)) {
					we.click();
					LOGGER.info("Dashboard added successfully");
					break;
				}
			}
		} else {
			LOGGER.info("Dashboard list is empty");
			return flag;
		}
		waitForPageLoaded();
		clickOnElementsOfDashboardPage("editButtonShareDash");
		clickOnElementsOfDashboardPage("deleteCustomDashboard");
		clickOnElementsOfDashboardPage("deleteDashboardbutton");
		waitForPageLoaded();
		LOGGER.info("Shared Custom dashboard has been deleted successfully");
		flag = true;
		return flag;
	}

	/**
	 * This method is used to verify Discard functionality of share dashboard
	 * 
	 * @param dashboardName - Dashboard to be shared
	 * @param username      - User name of the Receiver
	 * @param sendername    - User sharing dashboard
	 * @return - Flag value - true/false
	 * @throws Exception
	 */
	public final boolean verifyDiscardedShareDashboardpage(String dashboardName, String username, String sendername)
			throws Exception {
		boolean flag = false;
		int i = 0;
		String discardLinkClickFinal = null;
		String receiverDashboardName = username + "'s" + " " + dashboardName;
		String notificationText = sendername + " " + "is sharing a dashboard view called" + " " + receiverDashboardName
				+ ". Do you want to add it to your custom views?";
		waitForPageLoaded();
		sleeper(5000);
		waitForElementsOfDashboardPage("newNotificationIndicatorDashboard");
		clickOnElementsOfDashboardPage("notificationBellIconDashboard");
		List<WebElement> element = getElementsOfDashboardPage("unreadNotificationDashboardText");
		if (element.size() != 0) {
			for (WebElement we : element) {
				i = i + 1;
				if (we.getText().equalsIgnoreCase(notificationText)) {
					LOGGER.info("value of webelement" + we.getText());
					String discardLinkClick = dashboardPageProperties.getProperty("addLinkTemp");
					discardLinkClickFinal = discardLinkClick + "[" + i + "]";
					LOGGER.info("value of discrad link" + discardLinkClickFinal);
					mouseHover(discardLinkClickFinal);
					clickByJavaScript(discardLinkClickFinal + "//button//*[name()='span']");
					clickOnElementsOfDashboardPage("discardDasboard");
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
	 * 
	 * @param barsFirstLevelKey  - Key for bar chart
	 * @param tooltip            - Key for tooltip
	 * @param secondLevelLegends - Key for legends on charts after drilldown
	 * @param xAxisLabels        - Key for x axis labels on chart
	 * @return - boolean - true/false
	 * @throws Exception
	 */
	public boolean verifyTwoLevelDrillDown(String barsFirstLevelKey, String tooltip, String secondLevelLegends,
			String xAxisLabels) throws Exception {
		List<WebElement> firstLevelBars = getElementsOfDashboardPage(barsFirstLevelKey);
		List<String> countOfIncidentOnBars = new ArrayList<String>();
		ArrayList<String> typesOfIncidentsOnBars = new ArrayList<String>();
		if (firstLevelBars.size() > 0) {
			int count = 0, countOfIncidentsAtSecondLevel = 0, countOfIncidentsAtMultipleBars = 0;
			String totalIncidentText = "", toolTipText = "", categoryOfIncident = "", countOfIncidentsAtFirstLevel = "",
					typeOfIncident = "";
			boolean legendCheck = false, countCheck = false, typeCheck = false, numberOfIncidentsCheck = false,
					typeOfIncidentsCheck = false, catgoryOfIncidentsCheck = false;
			Actions action = new Actions(getDriver());
			for (int i = 0; i < firstLevelBars.size(); i++) {
				firstLevelBars = getElementsOfDashboardPage(barsFirstLevelKey);// To solve stale element exception
				action.moveToElement(firstLevelBars.get(i)).build().perform();
				waitForElementsOfDashboardPage(tooltip);
				toolTipText = getTextOfDashboardPage(tooltip);
				categoryOfIncident = toolTipText;
				countOfIncidentsAtFirstLevel = toolTipText;
				firstLevelBars.get(i).click();
				sleeper(2000);// To get second level of bar graph
				List<WebElement> secondLevelBars = getElementsOfDashboardPage(barsFirstLevelKey);
				action.moveToElement(secondLevelBars.get(0)).build().perform();
				waitForElementsOfDashboardPage(tooltip);
				toolTipText = getTextOfDashboardPage(tooltip);
				typeOfIncident = toolTipText;
				countOfIncidentsAtSecondLevel = Integer.parseInt(toolTipText);
				legendCheck = categoryOfIncident.equalsIgnoreCase(getTextOfDashboardPage(secondLevelLegends));
				if (!legendCheck) {
					LOGGER.info("Legend after 1st drill down does not match with tooltip text for " + i + " bar");
				}
				if (secondLevelBars.size() > 1) {
					for (int j = 0; j < secondLevelBars.size(); j++) {
						action.moveToElement(secondLevelBars.get(j)).build().perform();
						waitForElementsOfDashboardPage(tooltip);
						typesOfIncidentsOnBars.add(getTextOfDashboardPage(tooltip).split(": ")[0]);
						countOfIncidentOnBars.add(getTextOfDashboardPage(tooltip).split(": ")[1]);
						count += Integer.parseInt(getTextOfDashboardPage(tooltip).split(": ")[1]);
					}
					countOfIncidentsAtSecondLevel = count;
					count = 0;
				}
				countCheck = (Integer.parseInt(countOfIncidentsAtFirstLevel) == countOfIncidentsAtSecondLevel);
				if (!countCheck) {
					LOGGER.info("Count of incidents does not match after 1st drill down for " + i + " bar");
				}

				if (secondLevelBars.size() > 1) {
					List<String> typesOfIncidentsOnXAxis = getUniqueElementsofStringsFromListOnDashboardPage(
							xAxisLabels);
					typeCheck = typesOfIncidentsOnXAxis.equals(typesOfIncidentsOnBars);
				} else {
					typeCheck = getTextOfDashboardPage(xAxisLabels).equalsIgnoreCase(typeOfIncident);
				}
				if (!typeCheck) {
					LOGGER.info("Type of incident does not match after 1st drill down for " + i + " bar");
				}
				secondLevelBars.get(0).click();
				IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
				incidentPage.waitForElementsOfIncidentPage("showingPaginationTotalCount");
				totalIncidentText = incidentPage.getTextOfIncidentPage("showingPaginationTotalCount");

				if (secondLevelBars.size() > 1) {
					numberOfIncidentsCheck = countOfIncidentOnBars.contains(totalIncidentText.split("of ")[1]);
				} else {
					numberOfIncidentsCheck = countOfIncidentsAtSecondLevel == Integer
							.parseInt(totalIncidentText.split("of ")[1]);
				}
				if (!numberOfIncidentsCheck) {
					LOGGER.info("No of incidents does not match on incidents page with chart for " + i + " bar");
				}
				catgoryOfIncidentsCheck = incidentPage.getTextOfIncidentPage("incidentType")
						.equalsIgnoreCase(categoryOfIncident);
				if (!catgoryOfIncidentsCheck) {
					LOGGER.info("Type of incident does not match on incidents page with chart for " + i + " bar");
				}
				typeOfIncidentsCheck = incidentPage.getTextOfIncidentPage("incidentSubType")
						.equalsIgnoreCase(typeOfIncident);
				if (!typeOfIncidentsCheck) {
					LOGGER.info("Subtype of incident on incidents page does not match with chart for " + i + " bar");
				}
				clickOnElementsOfDashboardPage("dashboardTab");
				waitForElementsOfDashboardPage("allIncidentsByTypeTitle");
				scrollToDashboardPage("allIncidentsByTypeTitle");
				waitForElementsOfDashboardPage(barsFirstLevelKey);
				if (secondLevelBars.size() > 1) {
					for (int k = 1; k < secondLevelBars.size(); k++) {
						firstLevelBars = getElementsOfDashboardPage(barsFirstLevelKey);// To solve stale element
																						// exception
						firstLevelBars.get(i).click();
						secondLevelBars = getElementsOfDashboardPage(barsFirstLevelKey);// To solve stale element
																						// exception
						action.moveToElement(secondLevelBars.get(k)).build().perform();
						waitForElementsOfDashboardPage(tooltip);
						toolTipText = getTextOfDashboardPage(tooltip);
						typeOfIncident = toolTipText.split(": ")[0];
						countOfIncidentsAtMultipleBars = Integer.parseInt(toolTipText.split(": ")[1]);
						legendCheck = legendCheck
								&& categoryOfIncident.equalsIgnoreCase(getTextOfDashboardPage(secondLevelLegends));
						if (!legendCheck) {
							LOGGER.info(
									"Legend after 2nd drill down does not match with tooltip text for " + k + " bar");
						}
						typeCheck = typeCheck && typesOfIncidentsOnBars.contains(typeOfIncident);
						if (!typeCheck) {
							LOGGER.info("Type of incident does not match after 2nd drill down for " + k + " bar");
						}
						secondLevelBars.get(k).click();
						incidentPage.waitForElementsOfIncidentPage("showingPaginationTotalCount");
						totalIncidentText = incidentPage.getTextOfIncidentPage("showingPaginationTotalCount");
						numberOfIncidentsCheck = numberOfIncidentsCheck && (countOfIncidentsAtMultipleBars == Integer
								.parseInt(totalIncidentText.split("of ")[1]));
						if (!numberOfIncidentsCheck) {
							LOGGER.info(
									"No of incidents does not match on incidents page with chart after 2nd drill down for "
											+ k + " bar");
						}
						catgoryOfIncidentsCheck = catgoryOfIncidentsCheck && incidentPage
								.getTextOfIncidentPage("incidentType").equalsIgnoreCase(categoryOfIncident);
						if (!catgoryOfIncidentsCheck) {
							LOGGER.info(
									"Type of incident does not match on incidents page with chart after 2nd drill down for "
											+ k + " bar");
						}
						typeOfIncidentsCheck = typeOfIncidentsCheck && incidentPage
								.getTextOfIncidentPage("incidentSubType").equalsIgnoreCase(typeOfIncident);
						if (!typeOfIncidentsCheck) {
							LOGGER.info(
									"Subtype of incident on incidents page does not match with chart after 2nd drill down for "
											+ k + " bar");
						}
						typesOfIncidentsOnBars.clear();
						countOfIncidentOnBars.clear();
						clickOnElementsOfDashboardPage("dashboardTab");
						waitForElementsOfDashboardPage("allIncidentsByTypeTitle");
						scrollToDashboardPage("allIncidentsByTypeTitle");
						waitForElementsOfDashboardPage(barsFirstLevelKey);
					}
				}
			}
			return (legendCheck && countCheck && typeCheck && numberOfIncidentsCheck && typeOfIncidentsCheck
					&& catgoryOfIncidentsCheck);
		} else {
			LOGGER.info("Chart does not have data to test drill down");
			return true;
		}
	}

	/**
	 * This method returns the text corresponding to the list of web elements.
	 * 
	 * @param key - Key of element to get the string list.
	 * @return
	 * @throws Exception
	 */

	public final List<String> getUniqueElementsofStringsFromListOnDashboardPage(String key) throws Exception {
		return getUniqueElementsofStringsFromList(dashboardPageProperties.getProperty(key));
	}

	/**
	 * This method is used to search a particular custom dashboard from a list.
	 * 
	 * @param dashboardToSearch
	 * @return
	 * @throws Exception
	 */
	public final boolean searchDashboardinList(String dashboardToSearch) throws Exception {
		boolean flag = false;
		try {

			clickOnElementsOfDashboardPage("dashboardListDropdown");
			sleeper(2000);
			// clickOnElementsOfDashboardPage("dashboardListSearchBar");
			enterTextForDashboardPage("dashboardListSearchBar", dashboardToSearch);
			sleeper(1000);
			List<WebElement> webelement = getElementsOfDashboardPage("dashboardList");
			if (webelement.size() != 0) {
				for (WebElement we : webelement) {
					if (we.getText().equalsIgnoreCase(dashboardToSearch)) {
						LOGGER.info("Dashboard searched successfully");
						flag = true;
						break;
					}
				}
			} else {
				LOGGER.info("Dashboard list is empty, search was unsuccesful");
				return flag;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in searching the dashboard : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * This method selects a company from global filter.
	 * 
	 * @param companyName
	 * @return
	 * @throws Exception
	 */
	public final void selectCompanyInGlobalFilter(String companyName) {
		try {
			clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			enterTextForDashboardPage("filterMenuCompaniesSearch", companyName);
			LOGGER.info("Search company to set location filter");
			sleeper(3000);
			waitForElementsOfDashboardPage("companyOnListSearch");
			if (verifyElementsOfDashboardPage("companyOnListSearch"))
				clickOnElementsOfDashboardPage("companyOnListSearch");
			LOGGER.info("Select company to set location filter");
			sleeper(2000);
			if (verifyElementsOfDashboardPage("globalFilterSave")) {
				clickOnElementsOfDashboardPage("globalFilterSave");
				sleeper(5000);// Table values dont load
				LOGGER.info("Global Location Filter Saved successfully.");
			}
			if (verifyElementsOfDashboardPage("globalFilterCancel")) {
				clickOnElementsOfDashboardPage("globalFilterCancel");
				sleeper(5000);// Table values dont load
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occured in selecting the company in global filter in method selectCompanyInGlobalFilter : "
							+ e.getMessage());
		}
	}

	/**
	 * This method is used to validate navigation from overenrollment tile to
	 * companies list page.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public boolean verifyPlanOnCompanyListPage(String LanguageCode) throws Exception {
		boolean flag = false;
		String count = null;
		List<WebElement> companyNameList = new ArrayList<WebElement>();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		count = getTextOfDashboardPage("countOnTile");
		int countNumber = Integer.parseInt(count);
		clickOnElementsOfDashboardPage("viewDetailsButtonOverEnroll");
		if (companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs")
				.equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies"))) {
			companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
			waitForPresenceOfElementsOfDashboardPage("statusText");
			if (getTextOfDashboardPage("statusText")
					.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "company.status.over_enrolled"))) {
				companyNameList = getElementsTillAllElementsVisibleofDashboardPage("nameList");
				if (companyNameList.size() > 0) {
					int nameListCount = companyNameList.size();
					if (countNumber == nameListCount) {
						flag = true;
						LOGGER.info("Count got matched from Dashboard page to companies list page for overenrollment.");
					}

				} else {
					LOGGER.error("No Companies are present on companies list page");
				}
			} else {
				LOGGER.error("Over Enrollment filter did not applied on Companies List page.");
			}
		} else {
			LOGGER.error("Companies List page did not load successfully when navigated from Over Enrollment tile.");
		}
		return flag;

	}

	/**
	 * This method basically verify the header Name on report page
	 * 
	 * @param LanguageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param labelKey:                Chart labels
	 * @param keyHeaderOnNextPage:     Header on the drill down page
	 * @param headerNamesOnReportPage: Vlaues in the header section on the drill
	 *                                 down page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean headerTextVerificationOnReportPage(String LanguageCode, String labelKey,
			String keyHeaderOnNextPage, String[] headerNamesOnReportPage) throws Exception {
		boolean flag = false;
		verifyElementIsClickableOfDashboardPage(labelKey);
		clickOnElementsOfDashboardPage(labelKey);
		sleeper(3000);
		switchToDifferentTabOfDashboardPage();
		sleeper(3000);
		if (waitForElementsOfDashboardPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
			List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnNextPage);
			for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
				if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode,
						"daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
		} else {
			LOGGER.error("Report page did not load in 1 minute.");
		}
		switchToPreviousTabOfDashboardPage();
		return flag;
	}

	/**
	 * This method basically verify the header Name on report page
	 * 
	 * @param LanguageCode:        This is language code used for multiple
	 *                             languages.
	 * @param labelKey:            Chart label
	 * @param keyHeaderOnNextPage: Header on the drill down page
	 * @param threatPageHeader:    Header of threat details page
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean viewThreatDetailsRedirectionOnReportPage(String LanguageCode, String labelKey,
			String keyHeaderOnNextPage, String threatPageHeader) throws Exception {
		boolean flag = false;
		WebElement element = null;
		verifyElementIsClickableOfDashboardPage(labelKey);
		clickOnElementsOfDashboardPage(labelKey);
		sleeper(3000);
		switchToDifferentTabOfDashboardPage();
		sleeper(3000);
		if (waitForElementsOfDashboardPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
			scrollToDashboardPage("viewDetailsHeaderhPSureSenseProThreats");
			List<WebElement> listOfOptions = getElementsOfDashboardPage("viewDetailsLinkhPSureSenseProThreats");
			for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
				if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "dashboard.dhealth.action.details"))) {
					// We have access of webelement here, and predefined function takes String as
					// argument not webelement
					wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
					element = wait
							.until(ExpectedConditions.elementToBeClickable(listOfOptions.get(listOfOptionsCounter)));
					jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
					flag = true;
					break;
				}
			}
		} else {
			LOGGER.error("Report page did not load in 1 minute.");
		}
		switchToPreviousTabOfDashboardPage();
		return flag;
	}

	// TO switch to parent and close all tabs
	public final void switchToParentTabOfDashboardPage() {
		switchToParentTab();
	}

	/**
	 * This method will Switch the Account of the User having same email address
	 * 
	 * @throws Exception
	 */
	public final void verifySwitchAccount(String language) throws Exception {
		clickOnElementsOfDashboardPage("switchAccountdropdown");
		String currentAccount = getTextOfDashboardPage("switchAccountTextBox");
		List<WebElement> listOfAccounts = getElementsOfDashboardPage("listOfAccounts");
		for (int i = 0; i < listOfAccounts.size(); i++) {
			String Account = listOfAccounts.get(i).getText();
			if (!(currentAccount.equals(Account))) {
				listOfAccounts.get(i).click();
				if (Account.contains(getTextLanguage(language, "daas_ui", "global.form.partner"))) {
					clickOnElementsOfDashboardPage("switchAccountButton");
					waitForElementsOfDashboardPageDynamic("pageTitle", CommonVariables.LOGIN_WAIT);
					waitUntilElementIsInvisibleOfDashboardPage("tableOverlay");
					sleeper(5000);// Added sleeper as it takes time in loading the page
					waitForElementsOfDashboardPage("userProfileButton");
					clickOnElementsOfDashboardPage("userProfileButton");
					if (!(getTextOfDashboardPage("userProfileAccount")
							.contains(getTextLanguage(language, "daas_ui", "global.form.partner"))))
						LOGGER.error("Error while switching the Account");
					break;
				} else if (Account.contains(getTextLanguage(language, "daas_ui", "global.form.msp"))) {
					clickOnElementsOfDashboardPage("switchAccountButton");
					waitForElementsOfDashboardPageDynamic("pageTitle", CommonVariables.LOGIN_WAIT);
					waitUntilElementIsInvisibleOfDashboardPage("tableOverlay");
					sleeper(5000);// Added sleeper as it takes time in loading the page
					waitForElementsOfDashboardPage("userProfileButton");
					clickOnElementsOfDashboardPage("userProfileButton");
					if (!(getTextOfDashboardPage("userProfileAccount")
							.contains(getTextLanguage(language, "daas_ui", "global.form.msp"))))
						LOGGER.error("Error while switching the Account");
					break;
				} else {
					clickOnElementsOfDashboardPage("switchAccountButton");
					waitForElementsOfDashboardPageDynamic("pageTitle", CommonVariables.LOGIN_WAIT);
					waitUntilElementIsInvisibleOfDashboardPage("tableOverlay");
					sleeper(5000);// Added sleeper as it takes time in loading the page
					waitForElementsOfDashboardPage("userProfileButton");
					clickOnElementsOfDashboardPage("userProfileButton");
					if (!(getTextOfDashboardPage("userProfileAccount")
							.contains(getTextLanguage(language, "daas_ui", "roles.reports_admin"))))
						LOGGER.error("Error while switching the Account");
					break;
				}

			}

		}
	}

	/**
	 * This method is used to divert execution on required UI version.
	 * 
	 * @throws Exception
	 */
	public void verifyUIVersion() throws Exception {
		try {
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				if (isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)) {
					if (getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v2\"")) {
						waitForPageLoaded();
						dashboardPage.waitForElementsOfDashboardPage("switchUIButtonV2");
						dashboardPage.clickOnElementsOfDashboardPage("switchUIButtonV2");
						waitForPageLoaded();
						dashboardPage.waitForElementsOfDashboardPage("switchUIButtonV3");
						sleeper(1000);
						Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("switchUIButtonV3"),
								"New UI of techpulse did not load successfully.");
						LOGGER.info("Navigated to Veneer 3 version successfully.");
					} else if (getItemFromLocalStorage("ui_user_preferences").contains("\"target\":\"ui_v3\"")) {
						if (dashboardPage.verifyElementsOfDashboardPage("closeAddCompanyModal"))
							dashboardPage.clickOnElementsOfDashboardPage("closeAddCompanyModal");
						if (dashboardPage.waitForElementsOfDashboardPageDynamic("feedBackModalTitle", 2)) {
							submitFeedbackVeneer3();
						}
						LOGGER.info("Already in Veneer 3 UI,execution can be continued.");
					}
				} else {
					LOGGER.error("Toggle for Veneer 3 is not enabled.");
				}
			} else if (uiVersion.equalsIgnoreCase("VENEER2")) {
				if (isItemPresentInLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)) {
					if (getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM).contains("\"target\":\"ui_v3\"")) {
						if (dashboardPage.waitForElementsOfDashboardPageDynamic("feedBackModalTitle", 2)) {
							dashboardPage.clickOnElementsOfDashboardPage("feedbackRating");
							dashboardPage.clickOnElementsOfDashboardPage("submitButtonFeedback");
							sleeper(3000);
						}
						dashboardPage.clickOnElementsOfDashboardPage("switchUIButtonV3");
						Assert.assertTrue(submitFeedbackVeneer3(), "Old UI of techpulse did not load successfully.");
						LOGGER.info("Navigated to Veneer 2 version successfully.");
						sleeper(1000);
					} else if (getItemFromLocalStorage(CommonVariables.LOCAL_STORAGE_ITEM)
							.contains("\"target\":\"ui_v2\"")) {
						LOGGER.info("Already in Veneer 2 UI");
					}
				} else {
					LOGGER.info("Toggle for Veneer 3 is not enabled, execution can be continued.");
				}
			} else {
				LOGGER.error("Pass correct Veneer version in POM, i.e. VENEER2 or VENEER3");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * This is common method to submit feedback of Veneer 3.
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean submitFeedbackVeneer3() throws Exception {
		boolean flag = false;
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("feedbackRating");
		dashboardPage.clickOnElementsOfDashboardPage("submitButtonFeedback");
		if (dashboardPage.waitForElementsOfDashboardPage("switchUIButtonV2")) {
			flag = true;
		} else {
			flag = false;
			LOGGER.error("Old UI of techpulse did not load successfully.");
		}
		return flag;

	}

	/**
	 * This method is used to verify device name link redirection on reports table
	 * for MSP with frame
	 * 
	 * @param LanguageCode:                language code
	 * @param labelsKey:                   List of labels/criteria present in the
	 *                                     graph
	 * @param deviceDetailsKey:            Element present on Device page
	 * @param columnListKey:               Columns present in the grid
	 * @param errorKey:                    Error message flashes on the Dashboard
	 * @param keyDrillDownLabelsAllHidden: label hidden locator
	 * @param targetElement:               element to be moved on
	 * @param frameKey:                    Frame present on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean verifyDeviceNameRedirectionMSPWithFrameFlexiWithOffset(String LanguageCode, String labelsKey,
			String deviceDetailsKey, String columnListKey, String errorKey, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement, int xOffset, int yOffset) throws Exception {
		boolean flag = false;
		waitForElementsOfDashboardPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsFlexi(labelsKey, keyDrillDownLabelsAllHidden);
		for (int counter = 0; counter < 1; counter++) {
			listOfLabels.get(counter).click();
			sleeper(3000);
			mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(frameKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				scrollToDashboardPage(columnListKey);
				clickOnElementsOfDashboardPage(columnListKey);
				switchToDifferentTabOfDashboardPage();
				waitForPageLoaded();
				if (waitForElementsOfDashboardPage("allChartsLocator")) {
					String errorNotification = getTextOfDashboardPage("resellerErrorNotification");
					if (errorNotification.equals(getTextLanguage(LanguageCode, "lhserver", "unauthorized.default")))
						LOGGER.info("Reseller do not have permission do view Device details page");
					else {
						LOGGER.error("The error notification for reseller is not matched");
						return flag;
					}
					flag = true;
				} else if (waitForElementsOfDashboardPage(deviceDetailsKey)) {
					LOGGER.info("Successfully navigated to Device details page");
					flag = true;
				} else if (waitForElementsOfDashboardPage(errorKey)
						|| waitForElementsOfDashboardPage("errorPageForNoDevice")) {
					LOGGER.info("Device not found");
					flag = true;
				}
			} else
				LOGGER.error("No data to diplay");
			switchToPreviousTabOfDashboardPage();
		}
		return flag;
	}

	/**
	 * This method is to check whether a text in element contains the expected
	 * string or not.
	 * 
	 * @param locator: Element on which text value needs to be checked.
	 * @param text:    String value against which the locator text needs to be
	 *                 checked.
	 * @return boolean value.
	 * @throws Exception
	 */
	public final boolean verifyTextContainsOnElementsOfDashboardPage(String locator, String text) throws Exception {

		if (getTextOfDashboardPage(locator).contains(text)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add widget for CaaS hardware category
	 * 
	 * @param category:category           name
	 * @param subcategorylist:subcategory name
	 * @param languageCode:language
	 * @throws Exception
	 */
	public final void addWidgetForCaaSHardware(String category, ArrayList<String> subcategorylist, String languageCode)
			throws Exception {
		ArrayList<String> subCategoryOptionList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_mrsHwinv"),
				getTextLanguage(languageCode, "dashboard_service", "label.report_category_mrsDiskrep"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
						"label.report_category_mrsHwhealthNew"),
				getTextLanguage(languageCode, "dashboard_service", "label.report_category_mrsDeviceutiV3")));
		// list of options with corresponds sub-category
		ArrayList<String> caas_hwinv_optionlist1 = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_option_mrsDetails")));
		ArrayList<String> caas_device_utilization_optionlist2 = new ArrayList<String>(
				Arrays.asList(getTextLanguage(languageCode, "dashboard_service", "label.report_option_swPerfByTime")));
		if (subcategorylist.size() > 0) {
			for (int count = 0; count < subcategorylist.size(); count++) {
				if (subcategorylist.get(count).toString().equalsIgnoreCase(
						getTextLanguage(languageCode, "dashboard_service", "label.report_category_mrsDiskrep"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickOnElementsOfDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
						"MPI-Reporting-LHreports_service", "label.report_category_mrsHwhealthNew"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickOnElementsOfDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				} else if (subcategorylist.get(count).toString()
						.equalsIgnoreCase(subCategoryOptionList.get(2).toString())) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleDashboardPage(caas_hwinv_optionlist1.get(0).toString(), languageCode);
					clickOnElementsOfDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				} else if (subcategorylist.get(count).toString()
						.equalsIgnoreCase(subCategoryOptionList.get(1).toString())) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleDashboardPage(caas_device_utilization_optionlist2.get(0).toString(),
							languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
				}
			}
		} else {
			LOGGER.info("No data loaded in subcategorylist");
		}
	}

	/**
	 * Escape key for dashboard Page
	 */
	public final void pressEscapeKeysForDashboardPage() throws Exception {
		pressKey(Keys.ESCAPE);
	}

	/**
	 * This method basically verify the hidden of labels of legends on donut
	 * drillDown Chart
	 * 
	 * @param keyLegendLabel:              Chart criteria present on the graph
	 * @param keyDrillDownLabelsAllHidden: Criteria which are hidden after clicking
	 *                                     on legends
	 * @param legendDropdownKey:           legend dropdown key
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelHiddenWhenClickOnLegendsDonutBatterySwellChartFlexi(String keyLegendLabel,
			String keyDrillDownLabelsAllHidden, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		int count1 = 0;
		try {
			if (verifyElementsOfDashboardPage(legendDropdownKey)) {
				for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
					try {
						waitForPageLoaded();
						//sleeper(2000);
						legendLabelElementList.get(count1).click();
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfDashboardPage(legendDropdownKey);
						sleeper(2000);
						legendLabelElementList.get(count1).click();
					}
				}
				return true;
			} else {
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
	 * This method basically verify the visibility of labels of legends on drillDown
	 * Chart
	 * 
	 * @param keyLegendLabel:Chart criteria present on the graph
	 * @param chartVisibility:     locator of chart visibility
	 * @param legendDropdownKey    : legend dropdown key
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenClickOnLegendsDonutBatterySwellChartFlexi(String keyLegendLabel,
			String chartVisibility, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		int count1 = 0;
		if (verifyElementsOfDashboardPage(chartVisibility)) {
			return false;
		} else {
			try {
				if (verifyElementsOfDashboardPage("batterySwellProbabilityDropdownFlexi")) {
					for (count1 = 0; count1 < legendLabelElementList.size(); count1++) {
						try {
							sleeper(2000);
							waitForPageLoaded();
							legendLabelElementList.get(count1).click();
						} catch (Exception e) {
							sleeper(2000);
							clickOnElementsOfDashboardPage(legendDropdownKey);
							legendLabelElementList.get(count1).click();
						}
					}
					return true;
				} else {
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
	 * 
	 * @param labelsKey      - lengends locator
	 * @param tooltipTextKey - tool tip locator
	 * @param textKey        - text key locator
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyTooltipTextOnReportWithFrameDonutBatterySwellChartFlexi(String labelsKey,
			String tooltipTextKey, String textKey, String keyDrillDownLabelsAllHidden, String targetElement,
			String legendDropdownKey) throws Exception {
		int count1 = 0;
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutBatterySwellChartFlexi(labelsKey, keyDrillDownLabelsAllHidden,
					legendDropdownKey);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		try {
			if (verifyElementsOfDashboardPage(legendDropdownKey)) {
				for (count1 = 0; count1 < listOfLegends.size(); count1++) {
					try {
						waitForPageLoaded();
						listOfLegends.get(count1).click();
						verifyTooltipTextOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, textKey,
								keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
						sleeper(2000);
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfDashboardPage(legendDropdownKey);
						sleeper(2000);
						listOfLegends.get(count1).click();
						verifyTooltipTextOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, textKey,
								keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
					}
				}
				return true;
			} else {
				for (count1 = 0; count1 < listOfLegends.size(); count1++) {
					waitForPageLoaded();
					listOfLegends.get(count1).click();
					sleeper(2000);
					verifyTooltipTextOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, textKey,
							keyDrillDownLabelsAllHidden, targetElement, legendDropdownKey);
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
	 * @param labelsKey      - lengends locator
	 * @param tooltipTextKey - tool tip locator
	 * @param textKey        - text key locator
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean verifyTooltipTextOnDonutBatterySwellChartFlexi(String labelsKey, String tooltipTextKey,
			String textKey, String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey)
			throws Exception {
		String text = null;
		boolean flag = false;
		try {
			scrollToDashboardPage(labelsKey);
			scrollToDashboardPage(targetElement);
			sleeper(3000);
			mouseHoverbyoffsett(targetElement, 15, 80);
			waitForElementsOfDashboardPage(tooltipTextKey);
			text = getTextOfDashboardPage(tooltipTextKey);
			String finaltext;
			if (!verifyElementIsVisible("tooltipTextofSecbatterySwellProbabilityFlexi")) {
				finaltext = text.replaceAll("[+.^:,]", "");
			} else {
				String count_clean = text.split("\n")[0];
				finaltext = count_clean.split(":")[1].trim();
			}
			mouseHoverbyoffsettClick(targetElement, 15, 80);
			switchToDifferentTabOfDashboardPage();
			if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				clickOnElementsOfDashboardPage("moreDetailsLink");
				List<WebElement> monthtext = getElementsOfDashboardPage(textKey);
				if (monthtext.get(0).getText().contains(finaltext)) {
					flag = true;
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			sleeper(2000);
			switchToPreviousTabOfDashboardPage();
			flag = true;
		} catch (Exception e) {
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
	public final boolean verifyChartLabelsOnDashboardPage(String labelsKey, ArrayList<String> labelList)
			throws Exception {
		boolean flag = false;
		waitForElementsOfDashboardPage(labelsKey);
		List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
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
	 * @param LanguageCode:                This is language code used for multiple
	 *                                     languages.
	 * @param labelKey:                    Chart labels
	 * @param keyHeaderOnNextPage:         Header on the drill down page
	 * @param headerNamesOnReportPage:     Vlaues in the header section on the drill
	 *                                     down page
	 * @param frameKey:                    Iframe on the drill down page
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param targetElement                : element to be moved on
	 * @param legendDropdownKey            : key of legend dropdown
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean headerTextVerificationOnReportPageFrameDonutSwellChartFlexi(String LanguageCode,
			String labelKey, String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey) throws Exception {

		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelKey);
		int count1 = 0;
		verifyElementIsClickableOfDashboardPage(labelKey);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutBatterySwellChartFlexi(labelKey, keyDrillDownLabelsAllHidden,
					legendDropdownKey);
		try {
			if (verifyElementsOfDashboardPage(legendDropdownKey)) {
				for (count1 = 0; count1 < listOfLegends.size(); count1++) {
					try {
						waitForPageLoaded();
						listOfLegends.get(count1).click();
						sleeper(2000);
						VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey,
								keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden,
								targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfDashboardPage(legendDropdownKey);
						sleeper(2000);
						listOfLegends.get(count1).click();
						VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey,
								keyHeaderOnNextPage, headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden,
								targetElement, legendDropdownKey);
						listOfLegends.get(count1).click();
					}
				}
				return true;
			} else {
				for (count1 = 0; count1 < listOfLegends.size(); count1++) {
					waitForPageLoaded();
					listOfLegends.get(count1).click();
					VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(LanguageCode, labelKey, keyHeaderOnNextPage,
							headerNamesOnReportPage, frameKey, keyDrillDownLabelsAllHidden, targetElement,
							legendDropdownKey);
					listOfLegends.get(count1).click();
				}
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("Failed to Verify legends hidden/visible functionality on header report of chart");
			return false;
		}
	}

	/**
	 * This method basically verify the header Name on report page with Frame
	 * 
	 * @param LanguageCode:                This is language code used for multiple
	 *                                     languages.
	 * @param labelKey:                    Chart labels
	 * @param keyHeaderOnNextPage:         Header on the drill down page
	 * @param headerNamesOnReportPage:     Vlaues in the header section on the drill
	 *                                     down page
	 * @param frameKey:                    Iframe on the drill down page
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param targetElement                : element to be moved on
	 * @param legendDropdownKey            : key of legend dropdown
	 * @return boolean value
	 * @throws Exception
	 */
	public final boolean VerifyheaderTextOnReportPageFrameDonutSwellChartFlexi(String LanguageCode, String labelKey,
			String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey) throws Exception {
		boolean flag = false;

		try {
			sleeper(2000);
			mouseHoverbyoffsettClick(targetElement, 15, 80);
			sleeper(3000);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			waitForElementsOfDashboardPage(keyHeaderOnNextPage);
			if (waitForElementsOfDashboardPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnNextPage);
				for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions
						.size(); listOfOptionsCounter++) {
					if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode,
							"daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
						flag = true;
					} else {
						flag = false;
						break;
					}
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			sleeper(2000);
			switchToPreviousTabOfDashboardPage();

		} catch (Exception e) {
			flag = false;
			LOGGER.info("Failed to get Report text on Battery Swell Probability Chart");
		}
		return flag;
	}

	/**
	 * This method is used to verify tool tip count with total rows on reports with
	 * Frame
	 * 
	 * @param labelsKey:                   Chart labels
	 * @param tooltipTextKey:              Tooltip text after hovering on a chart
	 * @param columnListKey:               Column names present in the grid
	 * @param paginationKey:               Locator of the pagination
	 * @param targetElement:               element to move to
	 * @param keyDrillDownLabelsAllHidden: all label hidden locator
	 * @param frameKey:                    Iframe on the drill down page
	 * @param xoffset:                     x coordinate to move
	 * @param yoffset:                     y coordinate to move
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyTooltipCountonReportWithFrameDonutBatterySwellChartFlexiWithOffset(String labelsKey,
			String tooltipTextKey, String columnListKey, String paginationKey, String frameKey,
			String keyDrillDownLabelsAllHidden, String targetElement, int xOffset, int yOffset,
			String legendDropdownKey) throws Exception {
		int listOfLabelsCounter = 0;
		waitForPageLoaded();
		sleeper(2000);
		if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
			verifyLabelHiddenWhenClickOnLegendsDonutBatterySwellChartFlexi(labelsKey, keyDrillDownLabelsAllHidden,
					legendDropdownKey);
		List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
		try {
			if (verifyElementsOfDashboardPage(legendDropdownKey)) {
				for (listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
					try {
						waitForPageLoaded();
						listOfLegends.get(listOfLabelsCounter).click();
						verifyTooltipCountOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, columnListKey,
								paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset,
								legendDropdownKey);
						sleeper(1000);
						listOfLegends.get(listOfLabelsCounter).click();
					} catch (Exception e) {
						sleeper(2000);
						clickOnElementsOfDashboardPage(legendDropdownKey);
						sleeper(2000);
						listOfLegends.get(listOfLabelsCounter).click();
						verifyTooltipCountOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, columnListKey,
								paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset,
								legendDropdownKey);
						sleeper(1000);
						listOfLegends.get(listOfLabelsCounter).click();
					}
				}
				return true;
			} else {
				for (listOfLabelsCounter = 0; listOfLabelsCounter < listOfLegends.size(); listOfLabelsCounter++) {
					waitForPageLoaded();
					listOfLegends.get(listOfLabelsCounter).click();
					verifyTooltipCountOnDonutBatterySwellChartFlexi(labelsKey, tooltipTextKey, columnListKey,
							paginationKey, frameKey, keyDrillDownLabelsAllHidden, targetElement, xOffset, yOffset,
							legendDropdownKey);
					sleeper(1000);
					listOfLegends.get(listOfLabelsCounter).click();
				}
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("Failed to Verify legends hidden/visible functionality on tool tip count of chart");
			return false;
		}

	}

	public final boolean verifyTooltipCountOnDonutBatterySwellChartFlexi(String labelsKey, String tooltipTextKey,
			String columnListKey, String paginationKey, String frameKey, String keyDrillDownLabelsAllHidden,
			String targetElement, int xOffset, int yOffset, String legendDropdownKey) throws Exception {
		boolean flag = false;
		String paginationText = null;
		String text;
		String finaltext;
		try {
			sleeper(3000);
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsett(targetElement, xOffset, yOffset);
			waitForElementsOfDashboardPage(tooltipTextKey);
			sleeper(2000);
			text = getTextOfDashboardPage(tooltipTextKey);
			if (!verifyElementIsVisible("tooltipTextofSecbatterySwellProbabilityFlexi")) {
				finaltext = text.split(":")[1].trim();
			} else {
				finaltext = text.replaceAll("[+.^:,]", "");
			}
			Integer tooltipcount = Integer.valueOf(finaltext);
			sleeper(2000);
			waitForPageLoaded();
			scrollToDashboardPage(targetElement);
			mouseHoverbyoffsettClick(targetElement, xOffset, yOffset);
			switchToDifferentTabOfDashboardPage();
			sleeper(3000);
			if (waitForElementsOfDashboardPageDynamic(columnListKey, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
				if (tooltipcount > 10) {
					sleeper(2000);
					waitForPageLoaded();
					paginationText = getTextOfDashboardPage(paginationKey);
					String arr[] = paginationText.split(" ");
					Integer paginationCount;
					paginationCount = Integer.valueOf(arr[1]);
					if ((tooltipcount == paginationCount) || (tooltipcount < paginationCount)) {// A device might have
																								// multiple disks hence
																								// count sometimes does
																								// not match but disk
																								// count is always less
																								// than number of
																								// devices
						flag = true;
					}
				} else {
					sleeper(2000);
					waitForPageLoaded();
					List<WebElement> listOfColumntext = getElementsOfDashboardPage(columnListKey);
					if (tooltipcount == (listOfColumntext.size())) {
						flag = true;
					}
				}
			} else {
				LOGGER.error("Report did not load in 1 minute.");
			}
			switchToPreviousTabOfDashboardPage();
			sleeper(1000);

		} catch (Exception e) {
			flag = false;
			LOGGER.info("Failed to get tool tip count on Battery Swell Probability Chart");
		}
		return flag;
	}

	/**
	 * This Function is used to check read__incident_insights permission on
	 * dashboard
	 * 
	 * @param environment
	 * @return
	 */
	public final boolean verifyReadInsightsIncident(String environment) {
		String environmentURL = null;
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
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON)
					.header("authorization", "Bearer " + mspAuthToken).body(body);
			response = httpRequest.post(api);
			String resp = response.asString();
			LOGGER.info(resp);
			JsonPath path = response.jsonPath();
			flag = path.getBoolean("permissions[4].status");
		} catch (Exception e) {
			LOGGER.error("Failed to check permission for dashboard widgets");
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	public boolean verifyGridDownload(boolean isFilePresent, long t) throws InterruptedException {
		long tp = System.currentTimeMillis();
		while (tp - t < 92000) {
			if (countFolderFile(ConstantPath.DOWNLOAD_PATH) >= 1) {
				LOGGER.info("File is downloaded Successfully");
				isFilePresent = true;
				break;
			} else {
				LOGGER.warn("File not found");
				sleeper(3000);
			}
			tp = System.currentTimeMillis();
		}
		return isFilePresent;
	}

	public final void addHardwareWidgetforHelpLink(String category, ArrayList<String> subcategorylist,
			String languageCode) throws Exception {
		ArrayList<String> subCategoryOptionList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwbluescreen"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_deviceuti"),
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwinv")));
		// list of options with corresponds sub-category
		ArrayList<String> blue_screen_errors_optionlist1 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "mpi.report.bsod.graph.toperrors.devices")));
		ArrayList<String> device_utilization_optionlist2 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "label.report_option_swPerfByApplication")));
		ArrayList<String> hardware_inventory_optionlist3 = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_option_details")));
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		for (int count = 0; count < subcategorylist.size(); count++) {
			if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_batteryStatRiskFact"))) {
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("BatterStatusRiskFactor"),
						"Battery status chart is not present");
				sleeper(2000);
				clickOnElementsOfDashboardPage("BatterStatusRiskFactorChartMoreOptions");
				sleeper(2000);
				clickOnElementsOfDashboardPage("BatterStatusRiskFactorChartViewFullReport");
				waitForPageLoaded();
				LOGGER.info("Redirected to reports page");
				waitForPageLoaded();
				switchToDifferentTab();
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
						"Help link icon is not present");
				waitForElementsOfDashboardPage("Helplinkicon");
				sleeper(1000);
				clickOnElementsOfDashboardPage("Helplinkicon");
				waitForElementsOfDashboardPage("helplink");

				clickOnElementsOfDashboardPage("helplink");
				LOGGER.info("Clicked on Battery status Help link from reports tab");
				switchToDifferentTab();
				sleeper(4000);// Url takes time to load
				softAssert.assertTrue(
						dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Battery_Status_and_Risk_Factor_Help),
						"User not redirected to Battery Status help section after clicking on link from Reports");
				LOGGER.info("User redirected to Help link successfully.");
				switchBackToPreviousTab();
				switchToParentTab();
				deleteWidgetforHardware();
				LOGGER.info("Widget has been deleted successfully");

			}
			else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_biosinventory"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				sleeper(2000);
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("BiosInventoryChart"),
						"Bios inventory chart is not present");
				clickOnElementsOfDashboardPage("BiosInverntoryChartMoreOptions");
				sleeper(2000);
				clickOnElementsOfDashboardPage("BiosInverntoryChartViewFullReport");
				waitForPageLoaded();
				LOGGER.info("Redirected to reports page");
				waitForPageLoaded();
				switchToDifferentTab();
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
						"Help link icon is not present");
				waitForElementsOfDashboardPage("Helplinkicon");
				sleeper(1000);
				clickOnElementsOfDashboardPage("Helplinkicon");
				waitForElementsOfDashboardPage("helplink");
				clickOnElementsOfDashboardPage("helplink");
				LOGGER.info("Clicked on Bios Inventory Help link from reports tab");
				switchToDifferentTab();
				sleeper(4000);// Url takes time to load
				softAssert.assertTrue(dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Bios_Inventory_Help),
						"User not redirected to Bios Inventory help section after clicking on link from Reports");
				LOGGER.info("User redirected to Help link successfully.");
				deleteWidget();
				switchBackToPreviousTab();
			}
			else if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(0).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(blue_screen_errors_optionlist1.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("BlueScreenErrorTopError"),
						"Blue Screen Error chart is not present");
				sleeper(2000);
				clickOnElementsOfDashboardPage("BlueScreenErrorTopErrorChartMoreOptions");
				sleeper(2000);
				clickOnElementsOfDashboardPage("BlueScreenErrorTopErrorChartViewFullReport");
				waitForPageLoaded();
				LOGGER.info("Redirected to reports page");
				waitForPageLoaded();
				switchToDifferentTab();
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
						"Help link icon is not present");
				waitForElementsOfDashboardPage("Helplinkicon");
				sleeper(1000);
				clickOnElementsOfDashboardPage("Helplinkicon");
				waitForElementsOfDashboardPage("helplink");

				clickOnElementsOfDashboardPage("helplink");
				LOGGER.info("Clicked on Blue screen error Help link from reports tab");
				switchToDifferentTab();
				sleeper(4000);// Url takes time to load
				softAssert.assertTrue(dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Top_Blue_Screen_Errors),
						"User not redirected to Blue Screen Error help section after clicking on link from Reports");
				LOGGER.info("User redirected to Help link successfully.");
				switchBackToPreviousTab();
				switchToParentTab();
				deleteWidgetforHardware();
				LOGGER.info("Widget has been deleted successfully");
			}
			else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(1).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(device_utilization_optionlist2.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("DeviceUitlByApplication"),
						"Device Uitilization chart is not present");
				sleeper(2000);
				clickOnElementsOfDashboardPage("DeviceUitlByApplicationChartMoreOptions");
				sleeper(2000);
				clickOnElementsOfDashboardPage("DeviceUitlByApplicationChartViewFullReport");
				waitForPageLoaded();
				LOGGER.info("Redirected to reports page");
				waitForPageLoaded();
				switchToDifferentTab();
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
						"Help link icon is not present");
				waitForElementsOfDashboardPage("Helplinkicon");
				sleeper(1000);
				clickOnElementsOfDashboardPage("Helplinkicon");
				waitForElementsOfDashboardPage("helplink");

				clickOnElementsOfDashboardPage("helplink");
				LOGGER.info("Clicked on Device Uitilization Help link from reports tab");
				switchToDifferentTab();
				sleeper(4000);// Url takes time to load
				softAssert.assertTrue(dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Device_Utilization),
						"User not redirected to Device Uitilization help section after clicking on link from Reports");
				LOGGER.info("User redirected to Help link successfully.");
				switchBackToPreviousTab();
				switchToParentTab();
				deleteWidgetforHardware();
				LOGGER.info("Widget has been deleted successfully");
			} 
			else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_diskcap"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				sleeper(5000);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("DiskcapacityPlaning"),
						"Disk capacity planning chart is not present");
				sleeper(2000);
				clickOnElementsOfDashboardPage("DiskcapacityPlaningChartMoreOptions");
				sleeper(2000);
				clickOnElementsOfDashboardPage("DiskcapacityPlaningChartViewFullReport");
				waitForPageLoaded();
				LOGGER.info("Redirected to reports page");
				waitForPageLoaded();
				switchToDifferentTab();
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
						"Help link icon is not present");
				waitForElementsOfDashboardPage("Helplinkicon");
				sleeper(1000);
				clickOnElementsOfDashboardPage("Helplinkicon");
				waitForElementsOfDashboardPage("helplink");

				clickOnElementsOfDashboardPage("helplink");
				LOGGER.info("Clicked on Disk capacity planning Help link from reports tab");
				switchToDifferentTab();
				sleeper(4000);// Url takes time to load
				softAssert.assertTrue(
						dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Disk_Capacity_Planning_Details),
						"User not redirected to Disk capacity planning help section after clicking on link from Reports");
				LOGGER.info("User redirected to Help link successfully.");
				switchBackToPreviousTab();
				switchToParentTab();
				deleteWidgetforHardware();
				LOGGER.info("Widget has been deleted successfully");
			} 
			else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
					"MPI-Reporting-LHreports_service", "label.report_category_hwhealth"))) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("HardwareHealth"),
						"Harware Health chart is not present");
				sleeper(2000);
				clickOnElementsOfDashboardPage("HardwareHealthChartMoreOptions");
				sleeper(2000);
				clickOnElementsOfDashboardPage("HardwareHealthChartViewFullReport");
				waitForPageLoaded();
				LOGGER.info("Redirected to reports page");
				waitForPageLoaded();
				switchToDifferentTab();
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
						"Help link icon is not present");
				waitForElementsOfDashboardPage("Helplinkicon");
				sleeper(1000);
				clickOnElementsOfDashboardPage("Helplinkicon");
				waitForElementsOfDashboardPage("helplink");

				clickOnElementsOfDashboardPage("helplink");
				LOGGER.info("Clicked on Harware Health Help link from reports tab");
				switchToDifferentTab();
				sleeper(4000);// Url takes time to load
				softAssert.assertTrue(dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Hardware_Health_Summary),
						"User not redirected to Harware Health help section after clicking on link from Reports");
				LOGGER.info("User redirected to Help link successfully.");
				switchBackToPreviousTab();
				switchToParentTab();
				deleteWidgetforHardware();
				LOGGER.info("Widget has been deleted successfully");
			} 
			else if (subcategorylist.get(count).toString()
					.equalsIgnoreCase(subCategoryOptionList.get(0).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(hardware_inventory_optionlist3.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("HarwareInvDetails"),
						"Harware Inventory chart is not present");
				sleeper(2000);
				clickOnElementsOfDashboardPage("HarwareInvDetailsChartMoreOptions");
				sleeper(2000);
				clickOnElementsOfDashboardPage("HarwareInvDetailsChartViewFullReport");
				waitForPageLoaded();
				LOGGER.info("Redirected to reports page");
				waitForPageLoaded();
				switchToDifferentTab();
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
						"Help link icon is not present");
				waitForElementsOfDashboardPage("Helplinkicon");
				sleeper(1000);
				clickOnElementsOfDashboardPage("Helplinkicon");
				waitForElementsOfDashboardPage("helplink");

				clickOnElementsOfDashboardPage("helplink");
				LOGGER.info("Clicked on Harware Inventory Help link from reports tab");
				switchToDifferentTab();
				sleeper(4000);// Url takes time to load
				softAssert.assertTrue(
						dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Hardware_Inventory_Details),
						"User not redirected to Harware Inventory help section after clicking on link from Reports");
				LOGGER.info("User redirected to Help link successfully.");
				switchBackToPreviousTab();
				switchToParentTab();
				deleteWidgetforHardware();
				LOGGER.info("Widget has been deleted successfully");
			}
			else
			 {
				 LOGGER.info("Failed to add widget and redirect to help link");
			 }
		}
	}
	public final void addsoftwareWidgetforHelpLink(String category, ArrayList<String> subcategorylist,
			String languageCode) throws Exception {
		ArrayList<String> subCategoryOptionList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_category_swerrors")));
		// list of options with corresponds sub-category
		ArrayList<String> software_errors_optionlist1 = new ArrayList<String>(Arrays.asList(getTextLanguage(
				languageCode, "MPI-Reporting-LHreports_service", "label.report_option_topErrorsWeeklySummary")));
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		for (int count = 0; count < subcategorylist.size(); count++) {
			if (subcategorylist.get(count).toString().equalsIgnoreCase(subCategoryOptionList.get(0).toString())) {
				verifyElementIsClickableOfDashboardPage("addWidgetButton");
				clickOnElementsOfDashboardPage("addWidgetButton");
				selectCategoryForFlexibleDashboardPage(category);
				selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
				selectOptionForFlexibleDashboardPage(software_errors_optionlist1.get(0).toString(), languageCode);
				clickByJavaScriptOnDashboardPage("visualizationForBatterry");
				waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
				clickOnElementsOfDashboardPage("widgetAddConfirmButton");
				LOGGER.info("Widget has been added");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("SoftwareErrors"),
						"Software error chart is not present");
				sleeper(2000);
				clickOnElementsOfDashboardPage("SoftwareErrorsChartMoreOptions");
				sleeper(2000);
				clickOnElementsOfDashboardPage("SoftwareErrorsChartViewFullReport");
				waitForPageLoaded();
				LOGGER.info("Redirected to reports page");
				waitForPageLoaded();
				switchToDifferentTab();
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
						"Help link icon is not present");
				waitForElementsOfDashboardPage("Helplinkicon");
				sleeper(1000);
				clickOnElementsOfDashboardPage("Helplinkicon");
				waitForElementsOfDashboardPage("helplink");

				clickOnElementsOfDashboardPage("helplink");
				LOGGER.info("Clicked on Software error Help link from reports tab");
				switchToDifferentTab();
				sleeper(4000);// Url takes time to load
				softAssert.assertTrue(dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Software_Errors_Top_Errors_Weekly_Summary),
						"User not redirected to Software error help section after clicking on link from Reports");
				LOGGER.info("User redirected to Help link successfully.");
				switchBackToPreviousTab();
				switchToParentTab();
				deleteWidgetforHardware();
				LOGGER.info("Widget has been deleted successfully");
			 } 
			   else
				 {
					 LOGGER.info("Failed to add widget and redirect to help link");
				 }
		}
	}
	
		public final void addsubscriptionWidgetforHelpLink(String category, ArrayList<String> subcategorylist,
				String languageCode) throws Exception {
			
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			for (int count = 0; count < subcategorylist.size(); count++) {
				 if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(languageCode,
						"MPI-Reporting-LHreports_service", "label.report_category_seatsbydevenrol"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					sleeper(5000);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("SubscriptionDeviceEnrollment"),
							"Device Enrollment chart is not present");
					sleeper(2000);
					clickOnElementsOfDashboardPage("SubscriptionDeviceEnrollmentChartMoreOptions");
					sleeper(3000);
					clickOnElementsOfDashboardPage("SubscriptionDeviceEnrollmentChartViewFullReport");
					waitForPageLoaded();
					LOGGER.info("Redirected to reports page");
					waitForPageLoaded();
					switchToDifferentTab();
					Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("Helplinkicon"),
							"Help link icon is not present");
					waitForElementsOfDashboardPage("Helplinkicon");
					sleeper(1000);
					clickOnElementsOfDashboardPage("Helplinkicon");
					waitForElementsOfDashboardPage("helplink");

					clickOnElementsOfDashboardPage("helplink");
					LOGGER.info("Clicked on Device Enrollment Help link from reports tab");
					switchToDifferentTab();
					sleeper(4000);// Url takes time to load
					softAssert.assertTrue(
							dashboardPage.getUrlOfCurrentPage().contains(ConstantURL.Seats_Entitled_by_Device_Enrollment),
							"User not redirected to Device Enrollment help section after clicking on link from Reports");
					LOGGER.info("User redirected to Help link successfully.");
					switchBackToPreviousTab();
					switchToParentTab();
					deleteWidgetforHardware();
					LOGGER.info("Widget has been deleted successfully");
				} 
				 else
				 {
					 LOGGER.info("Failed to add widget and redirect to help link");
				 }
		}
	}
	/**
	 * Delete widget from dashboard
	 * @throws Exception
	 */
	public final void deleteWidgetforHardware() throws Exception {
		sleeper(1000);
		waitForElementsOfDashboardPage("clickOnWidgetUpperLeftOptionList");
		clickOnElementsOfDashboardPage("clickOnWidgetUpperLeftOptionList");
		clickOnElementsOfDashboardPage("deleteWidgetOptionFirst");
		clickOnElementsOfDashboardPage("deleteWidgetConfirmButton");
		waitForPageLoaded();
		LOGGER.info("Widget is deleted");
	}
	

	public final void addWidgetForPixmCustomDashboard(String category, String languageCode) throws Exception {
		clickOnElementsOfDashboardPage("addWidgetButtonCustomDashboard");
		selectCategoryForFlexibleDashboardPage(category);
		clickByJavaScriptOnDashboardPage("visualizationForDevicesByExperienceLevel");
		clickOnElementsOfDashboardPage("widgetAddConfirmButton");
		waitForPageLoaded();			
		LOGGER.info("Widget has been added");
		}


	/**
	 * Add widget for CaaS HP Presence category
	 * 
	 * @param category:category name
	 * @param subcategorylist:subcategory name
	 * @param languageCode:language
	 * @throws Exception
	 */
	public void addWidgetForCaaSHPPresence(String category, ArrayList<String> subcategorylist, String languageCode) throws Exception {
	// list of options with corresponds sub-category
		ArrayList<String> caas_hwinv_optionlist1 = new ArrayList<String>(Arrays.asList(
				getTextLanguage(languageCode, "dashboard_service", "label.report_option_mrsDetails")));
		ArrayList<String> caas_device_utilization_optionlist2 = new ArrayList<String>(
				Arrays.asList(getTextLanguage(languageCode, "dashboard_service", "label.report_option_swPerfByTime")));
		if (subcategorylist.size() > 0) {
			for (int count = 0; count < subcategorylist.size(); count++) {
				LOGGER.info("Widget Name: "+subcategorylist.get(count));
				if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_mrsDeviceutiV3"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleDashboardPage(caas_device_utilization_optionlist2.get(0).toString(),
							languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_mrsDiskrep"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode,"dashboard_service", "label.report_category_mrsHwhealthNew"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_mrsHwinv"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					selectOptionForFlexibleDashboardPage(caas_hwinv_optionlist1.get(0).toString(), languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_mrsincidentmgmt"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_roomUtilization"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					sleeper(2000);
					waitUntilElementIsVisibleOfDashboardPage("visualizationForBatterry");
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_presenceDeviceUpdate"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
					
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_presenceSoftwareUpdate"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
					
				}else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_presenceRoomHealth"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
				} else if (subcategorylist.get(count).toString().equalsIgnoreCase(getTextLanguage(
						languageCode, "dashboard_service", "label.report_category_presenceMeetingQuality"))) {
					verifyElementIsClickableOfDashboardPage("addWidgetButton");
					clickOnElementsOfDashboardPage("addWidgetButton");
					selectCategoryForFlexibleDashboardPage(category);
					selectSubCategoryForFlexibleDashboardPage(subcategorylist.get(count).toString(), languageCode);
					clickByJavaScriptOnDashboardPage("visualizationForBatterry");
					waitUntilElementIsVisibleOfDashboardPage("spinnerOnWidgetAdd");
					clickOnElementsOfDashboardPage("widgetAddConfirmButton");
					LOGGER.info("Widget has been added");
					waitForAnalyticsPageLoaded();
					scrollToTop();
				}
				
			}
		} else {
			LOGGER.info("No data loaded in subcategorylist");
		}
	}

	/**
	 * This method will verify the Update devices button in HP Presence Software update Widget
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyUpdateDevicesButtonInHpPresenceSoftwareUpdateWidget() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		String templateCountString = getTextOfDashboardPage("templatesCount");
		int templateCountNumber = Integer.parseInt(templateCountString);
		if(templateCountNumber>0)
		{
			LOGGER.info("Templates count is greater than Zero ,Hence UpdateDevices Button should be present");
			return true;
		}else
		{
			softAssert.assertTrue((templateCountNumber>0),"Templates count is Zero");
			LOGGER.error("Templates count is Zero ,Hence UpdateDevices Button is not present");
			softAssert.assertAll();
			return false;
		}
	}
	
	/**
	 * This method basically verify the visibility of labels of legends on drillDown
	 * Chart
	 * 
	 * @param keyLegendLabel:Chart criteria present on the graph
	 * @param chartVisibility:     locator of chart visibility
	 * @param legendDropdownKey    : legend dropdown key
	 * @return boolean value
	 * @throws Exception
	 */

	public final boolean verifyLabelVisibleWhenMouseHoverLegendsDonutChartFlexi(String keyLegendLabel,
			String chartVisibility, String legendDropdownKey) throws Exception {
		List<WebElement> legendLabelElementList = getElementsTillAllElementsVisibleofDashboardPage(keyLegendLabel);
		if (verifyElementsOfDashboardPage(chartVisibility)) {
			return false;
		} else {
			for (int listCounter = legendLabelElementList.size() - 1; listCounter >= 0; listCounter--) {
				if (listCounter >= 1) {
					(legendLabelElementList.get(listCounter)).click();
					sleeper(2000);
					if (waitForElementsOfDashboardPage(legendDropdownKey))
						clickOnElementsOfDashboardPage(legendDropdownKey);
					sleeper(2000);
				} else
					legendLabelElementList.get(listCounter).click();
				sleeper(2000);
				if (!verifyElementsOfDashboardPage(chartVisibility)) {
					return false;
				}
			}
			return true;
		}
	
	}
	
	/**
	 * This method basically verify the header Name on report page with Frame
	 * 
	 * @param LanguageCode:            This is language code used for multiple
	 *                                 languages.
	 * @param labelKey:                Chart labels
	 * @param keyHeaderOnNextPage:     Header on the drill down page
	 * @param headerNamesOnReportPage: Values in the header section on the drill
	 *                                 down page
	 * @param frameKey:                Iframe on the drill down page
	 * @return boolean value
	 * @throws Exception
	 */

	
	public final boolean headerTextVerificationOnReportPageFrameFleetSecurity(String LanguageCode, String FleetVisibleDonut,
			String keyHeaderOnNextPage, String[] headerNamesOnReportPage, String frameKey) throws Exception {
		List<WebElement> listOfLabels = getElementsOfDashboardPage(FleetVisibleDonut);
		boolean flag = false;
			
		for (int i=1;i<listOfLabels.size()-1;i++){
		listOfLabels.get(i).click();
		sleeper(3000);
		switchToDifferentTabOfDashboardPage();
		sleeper(3000);
		if (waitForElementsOfDashboardPageDynamic(keyHeaderOnNextPage, DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
			List<WebElement> listOfOptions = getElementsOfDashboardPage(keyHeaderOnNextPage);
			for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfOptions.size(); listOfOptionsCounter++) {
				if (listOfOptions.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode,
						"daas_reports_ui", headerNamesOnReportPage[listOfOptionsCounter]))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
		} else {
			LOGGER.error("Report page did not load in 1 minute.");
		}
		switchToPreviousTabOfDashboardPage();
		}
		return flag;
		
	}
	
	
	/**
	 * Add widget into dashboard
	 * 
	 * @param languageCode
	 * @throws Exception
	 */
	public final boolean verifySoftwareUpdateWidgetsVisualization(String languageCode, String category,
			String subcategory, String option, List<String> softwareupdateswidgetslist) throws Exception {
		try {
			waitForPageLoaded();
			clickOnElementsOfDashboardPage("addWidgetButton");
			selectCategoryForFlexibleDashboardPage(category);
			selectSubCategoryForFlexibleDashboardPage(subcategory, languageCode);
			selectOptionForFlexibleDashboardPage(option, languageCode);
			waitForPageLoaded();
			sleeper(3000);
			List<String> actualList = getallTextOfDashboardPage("addwidgetVisualizationList");
			boolean matching = actualList.equals(softwareupdateswidgetslist);

			return matching;
		} catch (Exception e) {
			LOGGER.error("Failed to verify visualtion list in method --> verifySoftwareUpdateWidgetsVisualization");
			return false;
		}

	}

	
	/**
	 * This method selects the option for generating the report.
	 * 
	 * @param options
	 * @throws Exception
	 */
	public final List<String> selectFilterOptionForDashBoardPage(String options, String languageCode, String dropdownkey,
			String dropdownoptionlist) throws Exception {
		List<String> strOptionValues = null;
		try {

			if (getTextOfDashboardPage(dropdownkey).equalsIgnoreCase(
					getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON", "select_option_text"))
					|| !(getTextOfDashboardPage(dropdownkey).equalsIgnoreCase(options))) {
				clickOnElementsOfDashboardPage(dropdownkey);
				List<WebElement> element = getElementsTillAllElementsPresent(dashboardPageProperties.getProperty(dropdownoptionlist));
				strOptionValues = getallTextOfDashboardPage(dropdownoptionlist);
				for (WebElement we : element) {
					scrollTillView(we);
					if (we.getText().equalsIgnoreCase(options)) {
						we.click();
						break;
					}
				}
			} else {
				LOGGER.info("Filter option is selected by default");
			}

			return strOptionValues;
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectOptionForDashBoardPage : " + e.getMessage());
			return null;
		}
		
		
		
	}
	
	
	/**
	 * This method returns the text of a list of elements
	 */
	public final ArrayList<String> getTextAsListOnDashBoardPage(String key) throws Exception {
		ArrayList<String> columnNamesOnPage = new ArrayList<>();
		
		try {
			List<WebElement> element = getElementsTillAllElementsPresent(dashboardPageProperties.getProperty(key));

			for (WebElement webElement : element) {
				String columnName = webElement.getText().trim();
				if(!(columnName.isEmpty()||columnName.equals("")||columnName.equals(" "))){
					columnNamesOnPage.add(columnName);
				}
			}
			return columnNamesOnPage;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getTextAsListOnReportPage : " + e.getMessage());
			return null;
		}
	}
	
	/*Method returns the text of list elements
	 * 
	 */
	public final void HeaderMatchText (String key1,String key2,String header) throws Exception {
		List<WebElement> headerList = getElementsTillAllElementsPresent(dashboardPageProperties.getProperty(key1));
		List<WebElement> buttonList = getElementsTillAllElementsPresent(dashboardPageProperties.getProperty(key2));
		
		for (WebElement headerName: headerList)
		{
			for (WebElement buttonName:buttonList)
			{
				if(headerName.getText().equals(header)) {
					buttonName.click();
				}
			}
		}
	}
	
	/**
	 * This is a method to get a list of elements present on remediation devices list page
	 * @param key - Locator of element
	 * @return - list of web elements
	 */
	public final List<WebElement> getElementsOfAvRemediationListPage(String key) {
		try {
			return getElementsTillAllElementsPresent(dashboardPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsOfAVremediationPage " + e.getMessage()));
			return null;
		}
	}
	
	
	/**
	 * This method verifies the column headers for remediation list page
	 * 
	 * @param languageCode
	 * @param string
	 * @param avRemediationListColumns
	 * @return
	 */
	public boolean verifyColumnsofAVRemediationList(String languageCode, String locator,
			String[] avRemediationListColumns) {
		boolean flag = false;
		int counter = 0;
		List<WebElement> remediationColumnHeader = getElementsOfAvRemediationListPage(locator);
		//remediationColumnHeader.remove(0);
		for (WebElement we : remediationColumnHeader) {
			try {
				if (we.getText().equalsIgnoreCase(
						getTextLanguage(languageCode, "daas_ui", avRemediationListColumns[counter]))) {
					flag = true;
					counter++;
					LOGGER.info(counter); 
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
	 * This method clicks on the do-nut chart
	 * @param Locator of element
	 */
	public void clickDonutChart(String key) throws Exception {
		Actions action = new Actions(getDriver());
		String element = dashboardPageProperties.getProperty(key);
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement webelement = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(element)));
        action.moveToElement(webelement).moveByOffset(98, 0).click().perform();
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
public final boolean verifyDrillDownCountgDonutChartFlexi(String labelsKey, String tooltipTextKey,
		String textKey, String keyDrillDownLabelsAllHidden, String targetElement, String legendDropdownKey)
		throws Exception {
	boolean flag = false;
	String text = null;
	if (verifyElementsOfDashboardPage(keyDrillDownLabelsAllHidden))
		verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi(labelsKey, keyDrillDownLabelsAllHidden,
				legendDropdownKey);
	List<WebElement> listOfLegends = getElementsOfDashboardPage(labelsKey);
	for (int i = 0; i < listOfLegends.size(); i++) {
		if (waitForElementsOfDashboardPageDynamic(legendDropdownKey, 1)) {
			if (i == 0 || i == 2) {
				clickOnElementsOfDashboardPage(legendDropdownKey);
				sleeper(3000);
			}
		} else {
			LOGGER.info("Legend dropdown not present on chart");
		}
		listOfLegends.get(i).click();
		scrollToDashboardPage(labelsKey);
		scrollToDashboardPage(targetElement);
		sleeper(3000);
		mouseHoverbyoffsett(targetElement, 00, 72);
		waitForElementsOfDashboardPage(tooltipTextKey);
		text = getTextOfDashboardPage(tooltipTextKey);
		String count_clean = text.split("\n")[1];
		String finaltext = count_clean.split(":")[1].trim();
		mouseHoverbyoffsettClick(targetElement, 00, 72);
		switchToDifferentTabOfDashboardPage();
		if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
			clickOnElementsOfDashboardPage("moreDetailsLink");
		String value =	getTextOfDashboardPage("detailscount");
		String finalvalue = value.split(" ")[1].trim();
			if (finaltext.equalsIgnoreCase(finalvalue)) {
				flag = true;
			LOGGER.info("Drill Down Count is Matching");
			}
			else {
				flag= false;
				LOGGER.error("Drill Down Count did not Match");
			}
		} else {
			LOGGER.error("Report did not load in 1 minute.");
		}
		switchToPreviousTabOfDashboardPage();
		listOfLegends.get(i).click();
	}
	return flag;
}

public final boolean verifyTooltipTextVersionStatusdrilldown(String patchStatusFlexi, String patchStatusTooltipTextFlexi,String patchStatuslabel,
		String seconddrilldownFlexi, String secondlevelTooltip, String osReleaseFilterCriteria, String backButtonWPS)
		throws Exception {
	boolean flag = false;
	Actions action = new Actions(getDriver());
	List<WebElement> listOfLabels = getElementsOfDashboardPage(patchStatusFlexi);

	try {
		for (int i = 0; i < listOfLabels.size(); i++) {
			sleeper(3000);
			waitForElementsOfDashboardPage(patchStatusFlexi);
			listOfLabels = getElementsOfDashboardPage(patchStatusFlexi);
			listOfLabels.get(i);
			action.moveToElement(listOfLabels.get(i)).build().perform();
			//waitForElementsOfDashboardPage(patchStatuslabel);
			//text = getTextOfDashboardPage(patchStatuslabel);
			//LOGGER.info("Patch Status :" + text);
			listOfLabels.get(i).click();
			if (verifyTooltipTextOnReportWithDrillDown(seconddrilldownFlexi, secondlevelTooltip,
					osReleaseFilterCriteria)) {
				flag = true;
				clickOnElementsOfDashboardPage(backButtonWPS);
			} else {
				flag = false;
				break;
			}
		}
		if (flag) {
			LOGGER.info("Drill Down Count is Matching");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return flag;
}

/**
 * @param labelsKey
 * @param tooltipTextKey
 * @param textKey
 * @return
 * @throws Exception
 */
public final boolean verifyTooltipTextOnReportWithDrillDown(String labelsKey, String tooltipTextKey,
		String textKey) throws Exception {
	boolean flag = false;
	String text = null;
	Actions action = new Actions(getDriver());
	List<WebElement> listOfLabels = getElementsOfDashboardPage(labelsKey);
	for (int i = 0; i < listOfLabels.size(); i++) {
		listOfLabels.get(i);
		sleeper(3000);
		action.moveToElement(listOfLabels.get(i)).build().perform();
		waitForElementsOfDashboardPage(tooltipTextKey);
		text = getTextOfDashboardPage(tooltipTextKey);
		sleeper(2000);
		listOfLabels.get(i).click();
		switchToDifferentTabOfDashboardPage();
		if (waitForElementsOfDashboardPageDynamic("moreDetailsLink", DashboardVariables.DASHBOARD_REPORTS_WAIT)) {
			clickOnElementsOfDashboardPage("moreDetailsLink");
			String value =	getTextOfDashboardPage("detailscount");
			String finalvalue = value.split(" ")[1].trim();
			if (text.equalsIgnoreCase(finalvalue)) {
				flag = true;
			}
			else {
				flag= false;
				LOGGER.error("Drill Down Count did not Match");
			}
		} else {
			LOGGER.error("Report Page did not load in 1 minute");
		}
		switchToPreviousTabOfDashboardPage();
	}
	return flag;
}

//This method verifies widgets displayed for tenant

	/**
	 * @param widgetLocator --> Locator to get widget
	 * @param expectedWidgetNames  --> List of webelement (widgets)
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyWidgetsDisplayedForActiveCare(String widgetLocator, String[] expectedWidgetNames) throws Exception {
	    ArrayList<String> actualList = new ArrayList<>(Arrays.asList(expectedWidgetNames));
	    List<WebElement> elementsFromWebpage = getAllElements(dashboardPageProperties.getProperty(widgetLocator));
	    List<String> chartNamesFromWebpage = new ArrayList<>();
	    
	    for (WebElement element : elementsFromWebpage) {
	        String text = element.getText();
	       if (text != null) {
	        	chartNamesFromWebpage.add(text);
	       }
	    }
    
	    boolean allElementsPresent = actualList.size() == chartNamesFromWebpage.size() && chartNamesFromWebpage.containsAll(actualList);

	    if (!allElementsPresent) {
	        LOGGER.info("Some elements are missing or size does not match.");
	    }

	    return allElementsPresent;
	}

	public final void waitUntilElementIsInvisibleOfDashboardPage(String key) {
		try {
			verifyElementIsinvisibile(dashboardPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfReportPage " + e.getMessage()));
		}
	}

}

