package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;

public class PreConfiguredDashboardPage extends CommonMethod {
	private Properties selecteCredentialsProperties;
	private Properties selectedLanguageProperties;
	private ObjectReader preConfiguredDashboardPropertiesReader = new ObjectReader();
	private Properties preConfiguredDashboardPageProperties;
	private ObjectReader dashboardPagePropertiesReader = new ObjectReader();
	private final static Logger LOGGER = LogManager.getLogger(PreConfiguredDashboardPage.class);
	private PreConfiguredDashboardPage instance;
	// private Object reportFlag;
	public static String uiVersion = System.getProperty("uiVersion");
	public static String environment;
	public PreConfiguredDashboardPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (PreConfiguredDashboardPage.class) {
				if (instance == null) {
					instance = new PreConfiguredDashboardPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public PreConfiguredDashboardPage(WebDriver driver) throws IOException {
		preConfiguredDashboardPageProperties = preConfiguredDashboardPropertiesReader
				.getObjectRepository("PreConfiguredDashboardPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = preConfiguredDashboardPropertiesReader.getLanguageObjectRepository(localefolder,
				language);
		return selectedLanguageProperties.getProperty(key);
	}


	/**
	 * @param language-language code
	 * @param localefolder      -locale folder name
	 * @param key               - list of text
	 * @return arraylist object
	 * @throws Exception
	 */
	public final ArrayList<String> getTextLanguage(String language, String localefolder, String[] key)
			throws Exception {
		ArrayList<String> keyValues = new ArrayList<String>();
		selectedLanguageProperties = preConfiguredDashboardPropertiesReader.getLanguageObjectRepository(localefolder,
				language);
		for (int keyCounter = 0; keyCounter < key.length; keyCounter++) {
			keyValues.add(selectedLanguageProperties.getProperty(key[keyCounter]).trim());
		}
		return keyValues;
	}

	public final boolean waitForElementsOfPreConfiguredDashboardPage(String key) throws Exception {
		return verifyElementIsVisible(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsPresentOfPreConfiguredDashboardPage(String locator)
			throws Exception {
		return getElementsTillAllElementsPresent(preConfiguredDashboardPageProperties.getProperty(locator));
	}

	public final boolean verifyElementsOfPreConfiguredDashboardPage(String key) throws Exception {
		return verifyElementIsPresent(preConfiguredDashboardPageProperties.getProperty(key));

	}

	public final boolean verifyElementIsVisibleOfPreConfiguredDashboardPage(String key) throws Exception {
		return verifyElementIsVisible(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final boolean matchTextOfPreConfiguredDashboardPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(preConfiguredDashboardPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfPreConfiguredDashboardPage(String key) throws Exception {
		return verifyElementIsEnable(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfPreConfiguredDashboardPage(String key) throws Exception {
		return verifyElementIsSelected(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final String getTextOfPreConfiguredDashboardPage(String key) throws Exception {
		return getTextBy(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final String getAttributesOfPreConfiguredDashboardPage(String key, String value) throws Exception {
		return getAttribute(preConfiguredDashboardPageProperties.getProperty(key), value);
	}

	public final void waitUntilElementIsVisibleOfPreConfiguredDashboardPage(String key) throws Exception {
		waitUntilElementIsVisible(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfPreConfiguredDashboardPage(String key) throws Exception {
		click(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnPreConfiguredDashboardPage(String key) throws Exception {
		clickByJavaScript(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final void enterTextForPreConfiguredDashboardPage(String key, String Text) throws Exception {
		enterText(preConfiguredDashboardPageProperties.getProperty(key), Text);
	}

	public final void scrollTillViewPreConfiguredDashboardPage(String locator) throws Exception {
		scrollTillView(preConfiguredDashboardPageProperties.getProperty(locator));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleOfPreConfiguredDashboardPage(String locator)
			throws Exception {
		return getElementsTillAllElementsVisible(preConfiguredDashboardPageProperties.getProperty(locator));
	}

	public final int getWindowHandlesOfPreConfiguredDashboardPage() throws Exception {
		return getCountofWindowHandles();
	}

	public final List<WebElement> getElementsOfPreConfiguredDashboardPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final WebElement getElementOfPreConfiguredDashboardPage(String key) throws Exception {
		return getElement(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final boolean waitForPresenceOfElementsOfPreConfiguredDashboardPage(String key) throws Exception {
		return waitUntillElementIsPresent(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final boolean verifyIfElementIsClickableOfPreConfiguredDashboardPage(String key) throws Exception {
		return verifyElementIsClickable(preConfiguredDashboardPageProperties.getProperty(key));
	}

	public final void scrollUpPage() {
		jsDriver().executeScript("scroll(0, -750);");
	}

	public final void scrollDownPage() {
		jsDriver().executeScript("scroll(0, 1000);");
	}

	public final boolean waitForElementsOfPreConfiguredDashboardPageDynamic(String key, int wait) throws Exception {
		return verifyElementIsVisibleDynamic(preConfiguredDashboardPageProperties.getProperty(key), wait);
	}

	public final boolean waitUntilElementIsVisibleOfPreConfiguredDashboardPageDynamic(String key, int wait)
			throws Exception {
		return waitUntilElementIsVisibleDynamic(preConfiguredDashboardPageProperties.getProperty(key), wait);
	}

	public final String getCredentials(String credentials, String key) throws Exception {
		selecteCredentialsProperties = dashboardPagePropertiesReader.getCredentials(credentials);
		return selecteCredentialsProperties.getProperty(key);
	}

	/**
	 * Mouse Hover for PreConfiguredDashboardPage
	 * @param key
	 * @throws Exception
	 */
	public final void mouseHoverForPreConfiguredDashboardPage(String key) throws Exception {
		mouseHover(preConfiguredDashboardPageProperties.getProperty(key));
	}
	
	/**
	 * Check custom dashboard name into dropdown list.
	 * @return :return boolean value either true or false
	 * @throws Exception 
	 */
public boolean verifyCustomDashboardName(String dashboardName) throws Exception {
String actualDashboardName = getEnvironmentSpecificData(System.getProperty("environment"), dashboardName);
   String expectedDashboardName = actualDashboardName + " " + "*";
	boolean flag = false;
	try {
		waitForPageLoaded();
		sleeper(3000);
		clickOnElementsOfPreConfiguredDashboardPage("dashboardListDropdown");
		List<WebElement> element = getElementsTillAllElementsVisibleOfPreConfiguredDashboardPage("dashboardList");
		if (element.size() != 0) {
			for (WebElement we : element) {
				if (we.getText().equalsIgnoreCase(expectedDashboardName)) {
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
		LOGGER.error("Exception occurred in verifying FlexibleDashboardPage name : " + e.getMessage());
		flag = false;
	}
	return flag;
}

public final void addCustomPreConfiguredDashboard(String dashboardName) throws Exception
{
	
	waitForPageLoaded();
	sleeper(1000);

	if(verifyCustomDashboardName(dashboardName)){
			deleteCustomDashboard(dashboardName);
			waitForPageLoaded();
			sleeper(2000);
			clickOnElementsOfPreConfiguredDashboardPage("editButton");
			clickOnElementsOfPreConfiguredDashboardPage("addCustomDashboardOption");
			enterTextForPreConfiguredDashboardPage("enterNameforCustomDashboard",getEnvironmentSpecificData(System.getProperty("environment") ,dashboardName));
			clickOnElementsOfPreConfiguredDashboardPage("addDashboardButton");
			LOGGER.info("Custom dashboard is added successfully");
			waitForPageLoaded();	
		}else{
			sleeper(2000);
			clickOnElementsOfPreConfiguredDashboardPage("defaultOption");
			clickOnElementsOfPreConfiguredDashboardPage("editButton");
			clickOnElementsOfPreConfiguredDashboardPage("addCustomDashboardOption");
			enterTextForPreConfiguredDashboardPage("enterNameforCustomDashboard",getEnvironmentSpecificData(System.getProperty("environment") ,dashboardName));
			clickOnElementsOfPreConfiguredDashboardPage("addDashboardButton");
			LOGGER.info("Custom dashboard is added successfully");
			waitForPageLoaded();
		}
}

public final boolean setcustomdashboardAsdefault(String LanguageCode ,String dashboardName) {
	boolean flag = false;

	try {
       	PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
		preDashPage.waitForPageLoaded();
		preDashPage.verifyElementIsSelectedOfPreConfiguredDashboardPage("dashboardListDropdown");
		sleeper(3000);
		if (preDashPage.getTextOfPreConfiguredDashboardPage("dashboardListDropdown").equalsIgnoreCase(getEnvironmentSpecificData(System.getProperty("environment") ,dashboardName))) {
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("editButton");
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("setdashboarddeafult");
			sleeper(2000);
			preDashPage.clickByJavaScriptOnPreConfiguredDashboardPage("resetOkButton");
			flag = true;
		} else {
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("dashboardListDropdown");
			List<WebElement> dashboardListElements = preDashPage.getElementsTillAllElementsVisibleOfPreConfiguredDashboardPage("dashboardList");
			if (dashboardListElements.size() != 0) {
				for (WebElement we : dashboardListElements) {
					if (we.getText().equalsIgnoreCase(getEnvironmentSpecificData(System.getProperty("environment") ,dashboardName))) {
						we.click();
						preDashPage.clickOnElementsOfPreConfiguredDashboardPage("editButton");
						preDashPage.clickOnElementsOfPreConfiguredDashboardPage("setdashboarddeafult");
						sleeper(2000);
						preDashPage.clickOnElementsOfPreConfiguredDashboardPage("resetOkButton");
						LOGGER.info("Custom dashboard set to default successfully");
						flag = true;
						break;

					}
				}
			} else {
				LOGGER.info("Dashboard list is empty");
				flag = false;
			}
		}
	}
	 catch (Exception e) {
			LOGGER.error("Exception is occurred in reset default dashboard" + e.getMessage());
			return false;
		}
	return flag;
}

public final void deleteCustomDashboard( String customDashboardName) throws Exception
{
	String actualDashboardName = getEnvironmentSpecificData(System.getProperty("environment"), customDashboardName);
	String expectedDashboardName = actualDashboardName + " " + "*";
	environment = System.getProperty("environment");
	waitForPageLoaded();
	waitForPresenceOfElementsOfPreConfiguredDashboardPage("dashboardListDropdown");
	clickOnElementsOfPreConfiguredDashboardPage("dashboardListDropdown");
	List<WebElement> element = getElementsTillAllElementsVisibleOfPreConfiguredDashboardPage("dashboardList");
	if(element.size()!=0){
	for (WebElement we : element) {
	if (we.getText().equalsIgnoreCase(expectedDashboardName)) {
				we.click();	
			break;
		}
	}
	}else{
		LOGGER.info("Dshboard list is empty");
	}
	sleeper(5000);
	clickOnElementsOfPreConfiguredDashboardPage("editButton");
	clickOnElementsOfPreConfiguredDashboardPage("deleteCustomDashboard");
	clickOnElementsOfPreConfiguredDashboardPage("deleteDashboardbutton");
	waitForPageLoaded();
	LOGGER.info("Custom dashboard has been deleted successfully");
}

/**
 * This method is used to set Asset Management dashboard.
 * 
 * @param LanguageCode - POM.xml language
 * @return True or false
 */
public final boolean setToPreConfiguredDashboard(String LanguageCode ,String dashboardToBeSelected) {
	boolean flag = false;

	try {
		PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
		preDashPage.waitForPageLoaded();
		preDashPage.verifyElementIsSelectedOfPreConfiguredDashboardPage("dashboardListDropdown");
		verifyDashboardName(LanguageCode, dashboardToBeSelected);
		sleeper(3000);
		if (preDashPage.getTextOfPreConfiguredDashboardPage("dashboardListDropdown").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui",dashboardToBeSelected))) {
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("editButton");
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("resetDashboardOption");
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("resetOkButton");
			LOGGER.info("Dashboard is set to dashboardToBeSelected.");
			flag = true;
		} else {
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("dashboardListDropdown");
			List<WebElement> dashboardListElements = preDashPage.getElementsTillAllElementsVisibleOfPreConfiguredDashboardPage("dashboardList");
			if (dashboardListElements.size() != 0) {
				for (WebElement we : dashboardListElements) {
					if (we.getText().equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui",dashboardToBeSelected))) {
						we.click();
						preDashPage.clickOnElementsOfPreConfiguredDashboardPage("editButton");
						preDashPage.clickOnElementsOfPreConfiguredDashboardPage("resetDashboardOption");
						preDashPage.clickOnElementsOfPreConfiguredDashboardPage("resetOkButton");
						LOGGER.info("Dashboard is set to dashboardToBeSelected.");
						flag = true;
						break;
					}
				}
			} else {
				LOGGER.info("Dashboard list is empty");
				flag = false;
			}
		}
	} catch (Exception e) {
		LOGGER.error("Exception is occurred in reset default dashboard" + e.getMessage());
		return false;
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

public final boolean verifyChartOrderOfDashBoard(String allChartsLocatorKey,String[] chartIdsArray) throws Exception {
	Boolean flag = false;
	ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
	sleeper(5000);// Kept this long sleeper due slow loading issue of charts, once that issue gets resolved will remove this sleeper.
	List<WebElement> allCharts = getAllElements(preConfiguredDashboardPageProperties.getProperty(allChartsLocatorKey));
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
 * Check custom dashboard name into dropdown list.
 * @return :return boolean value either true or false
 * @throws Exception 
 */
		public boolean verifyDashboardName(String LanguageCode ,String dashboardName) throws Exception {
		boolean flag = false;
		try {
			waitForPageLoaded();
			sleeper(3000);
			clickOnElementsOfPreConfiguredDashboardPage("dashboardListDropdown");
			List<WebElement> element = getElementsTillAllElementsVisibleOfPreConfiguredDashboardPage("dashboardList");
			if (element.size() != 0) {
				for (WebElement we : element) {
					if (we.getText().equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui",dashboardName))) {
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
			LOGGER.error("Exception occurred in verifying FlexibleDashboardPage name : " + e.getMessage());
			flag = false;
		}
		return flag;
		}

	/**
	 * Check delete dashboard option is not present in more options.
	 * @return :return boolean value either true or false
	 * @throws Exception
	 */
	public boolean verifyDeleteDashboardNotPresent(String removeWidgetOption, String widgetToCheck) throws Exception {
		boolean flag = false;
		try {
			waitForPageLoaded();
			sleeper(3000);
			mouseHoverForPreConfiguredDashboardPage(widgetToCheck);
			clickByJavaScriptOnPreConfiguredDashboardPage(widgetToCheck);
			List<WebElement> element = getElementsOfPreConfiguredDashboardPage("moreOptionsList");
				if (element.size() != 0) {
					for (WebElement web : element) {
						if (web.getText().equalsIgnoreCase(removeWidgetOption)) {
							web.click();
							flag = true;
							LOGGER.info("Dashboard is present");
							break;
						}
					}
				} else {
					LOGGER.info("Dashboard list is empty");
				}
		} catch (Exception e) {
			LOGGER.error("Exception occurred in Functional Flow : " + e.getMessage());
			flag = true;
		}
		return flag;
	}
}
