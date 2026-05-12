package com.daasui.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;

public class WEPAlertsDashboardPage extends CommonMethod {
	private WEPAlertsDashboardPage instance;
	private ObjectReader WEPAlertsDashboardPagePropertiesReader = new ObjectReader();
	private Properties WEPAlertsDashboardPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");

	public WEPAlertsDashboardPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPAlertsDashboardPage.class) {
				if (instance == null) {
					instance = new WEPAlertsDashboardPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public WEPAlertsDashboardPage(WebDriver driver) throws IOException {
		WEPAlertsDashboardPageProperties = WEPAlertsDashboardPagePropertiesReader
				.getObjectRepository("WEPAlertsDashboardPage");
	}

	public final boolean verifyElementsOfAlertPage(String key) throws Exception {
		return verifyElementIsPresent(WEPAlertsDashboardPageProperties.getProperty(key));
	}
	
	public final void actionClickOfAlertPage(String key) throws Exception {
		actionClick(WEPAlertsDashboardPageProperties.getProperty(key));
	}


	public final boolean verifyElementIsClickableOfAlertPage(String key) throws Exception {
		return verifyElementIsClickable(WEPAlertsDashboardPageProperties.getProperty(key));
	}

	public final void scrollOnAlertPage(String key) throws Exception {
		scrollTillView(WEPAlertsDashboardPageProperties.getProperty(key));
	}

	public final String getTextOfAlertPage(String key) throws Exception {
		return getTextBy(WEPAlertsDashboardPageProperties.getProperty(key));

	}

	public final void clickOnElementsOfAlertPage(String key) throws Exception {
		click(WEPAlertsDashboardPageProperties.getProperty(key));
	}
	
	public final void clickByJavaScriptOnAlertPage(String key) throws Exception {
		clickByJavaScript(WEPAlertsDashboardPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfAlertPage(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WEPAlertsDashboardPageProperties.getProperty(key), waitTime);
	}

	public void switchToIframeofOfAlertPage(String key) throws Exception {
		switchToIframe(WEPAlertsDashboardPageProperties.getProperty(key));
	}

	public void switchToDefaultContentofAlertPage() throws Exception {
		switchToDefaultContent();
	}

	public final List<WebElement> getllAllElementsVisibleofAlertPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEPAlertsDashboardPageProperties.getProperty(key));
	}

	public final void mouseHoverOnAlertPage(String key) throws Exception {
		mouseHover(WEPAlertsDashboardPageProperties.getProperty(key));
	}

	public final List<String> getllAllElementsTextVisibleofAlertPage(String key) throws Exception {
		return getUniqueElementsofStringsFromList(WEPAlertsDashboardPageProperties.getProperty(key));
	}

	public final void enterTextOfAlertPage(String key, String Text) {
		try {
			enterText(WEPAlertsDashboardPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXCustomerUsersDetailsPage " + e.getMessage()));
		}
	}

	public final String getTextOfElementOnAlertPage(String key, String i) throws Exception {
		return getTextBy(WEPAlertsDashboardPageProperties.getProperty(key).replace("[i]", i));
	}

	public final void clickOnSpecificElementsOfAlertPage(String key, String i) throws Exception {
		click(WEPAlertsDashboardPageProperties.getProperty(key).replace("[i]", i));
	}

	public boolean validateActiveAlertPageData() throws Exception {
		try {
			if (!(verifyElementsOfAlertPage("ColumnData"))) {
				verifyTrue(verifyElementsOfAlertPage("noAlertMessage"),
						"No Alert Message is not present on Active Alert Page");
				verifyTrue(verifyElementsOfAlertPage("noAlertSubText"),
						"No Alert sub message is not present on Active Alert Page");
			} else {
				verifyText("of " + getllAllElementsVisibleofAlertPage("ColumnData").size(),
						getTextOfAlertPage("pageCount"), "Data count and pagination is not match in Active Alert Page");
				for (int i = 1; i < getllAllElementsVisibleofAlertPage("ColumnData").size() + 1; i++) {
					String Severity = getTextOfElementOnAlertPage("severityColumnData", "[" + i + "]");
					String Alert = getTextOfElementOnAlertPage("alertColumnData", "[" + i + "]");
					String AlertDescription = getTextOfElementOnAlertPage("alertDescriptionData", "[" + i + "]");
					String DevicesImpacted = getTextOfElementOnAlertPage("deviceImpactedColumnData", "[" + i + "]");
					String Fleet = getTextOfElementOnAlertPage("fleetColumnData", "[" + i + "]");

					clickOnSpecificElementsOfAlertPage("alertColumnData", "[" + i + "]");
					waitForPageLoaded();
					sleeper(3000);

					if (Alert.contains(CommonVariables.Bios_Alert) || Alert.contains(CommonVariables.Bsod_Alert)) {
						waitForPageLoaded();
						actionClickOfAlertPage("breadCrumbActiveAlert");
						sleeper(3000);
						waitForPageLoaded();
					} else {
						verifyText(Alert, getTextOfAlertPage("breadcrumbsAlertTitle"),
								"Active Alert Details Page Breadcrumbs did not match with selected alert title");
						verifyText(Severity, getTextOfAlertPage("alertSeverity"),
								"Severity did not match with Active Alert Details Page");
						verifyText(Alert, getTextOfAlertPage("alertTitleText"),
								"Alert Title text did not matched on Active Alert Details Page");
						verifyText(AlertDescription, getTextOfAlertPage("alertTitleDesc"),
								"Alert Description did not matched on Active Alert Details Page");
						verifyText(DevicesImpacted, getTextOfAlertPage("alertImpactedDevices"),
								"Device Impacted data did not matched on Active Alert Details Page");
						verifyText(Fleet, getTextOfAlertPage("alertFleet"),
								"Fleet data did not matched on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("alertImpactedDevicesText"),
								" Impacted Devicesis not present on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("alertFleetText"),
								"Percentage of Fleet is not present on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("remediationGuide"),
								"Remediation Guide header is not present on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("remediationGuideDownPara"),
								"Remediation Guide subheader text did not present on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("viewMoreButton"),
								"viewMore Button is not present on Active Alert Details Page");
						sleeper(3000);
						verifyText("of " + getTextOfAlertPage("alertImpactedDevices"), getTextOfAlertPage("pageCount"),
								"On " + getTextOfAlertPage("breadcrumbsAlertTitle")
										+ " alert, Data count and pagination is not match in Active Alert Details Page");

						verifyTrue(verifyElementsOfAlertPage("exportButton"),
								"Export button is not present on Active Alert Details Page");
						clickOnElementsOfAlertPage("exportButton");
						sleeper(3000);
						verifyTrue(verifyElementsOfAlertPage("toastNotificationExport"),
								"Export Notification is not present on Active Alert Details Page");
						actionClickOfAlertPage("breadCrumbActiveAlert");
						waitForPageLoaded();
					}
				}
			}
		} catch (VerificationException e) {
			LOGGER.error(("Exception occured in method validateActiveAlertPageData \n" + e.getMessage()));
			return false;
		}
		return true;
	}

	public boolean validateActiveAlertDataFromHomePage() throws Exception {
		try {
			if (!(verifyElementsOfAlertPage("homeAlerts"))) {
				verifyTrue(verifyElementsOfAlertPage("homeNoAlertMessage"),
						"No Alert Message is not present on Home Page");
				verifyTrue(verifyElementsOfAlertPage("homeNoAlertSubText"),
						"No Alert sub message is not present on Home Page");
				verifyTrue(verifyElementsOfAlertPage("alertManagementBtn"),
						"Alert Management Button is not present on Home Page");
			} else {
				for (int i = 1; i < getllAllElementsVisibleofAlertPage("homeAlerts").size() + 1; i++) {
					String Severity = getTextOfElementOnAlertPage("homeAlertSeverity", "[" + i + "]");
					String Alert = getTextOfElementOnAlertPage("homeAlertTitle", "[" + i + "]");

					clickOnSpecificElementsOfAlertPage("homeAlertTitle", "[" + i + "]");
					waitForPageLoaded();
					sleeper(3000);

					if (Alert.contains(CommonVariables.Bios_Alert) || Alert.contains(CommonVariables.Bsod_Alert)) {
						clickOnElementsOfAlertPage("homeButton");
						sleeper(3000);
						waitForPageLoaded();
					} else {
						verifyText(Alert, getTextOfAlertPage("breadcrumbsAlertTitle"),
								"Active Alert Details Page Breadcrumbs did not match with selected alert title from home");
						verifyText(Severity, getTextOfAlertPage("alertSeverity"),
								"Home Severity did not match with Active Alert Details Page");
						verifyText(Alert, getTextOfAlertPage("alertTitleText"),
								"Home Alert Title text did not matched on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("alertImpactedDevicesText"),
								" Impacted Devicesis not present on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("alertFleetText"),
								"Percentage of Fleet is not present on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("remediationGuide"),
								"Remediation Guide header is not present on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("remediationGuideDownPara"),
								"Remediation Guide subheader text did not present on Active Alert Details Page");
						verifyTrue(verifyElementsOfAlertPage("viewMoreButton"),
								"viewMore Button is not present on Active Alert Details Page");
						verifyText("of " + getTextOfAlertPage("alertImpactedDevices"), getTextOfAlertPage("pageCount"),
								"On " + getTextOfAlertPage("breadcrumbsAlertTitle")
										+ " alert, Data count and pagination is not match in Active Alert Details Page");

						actionClickOfAlertPage("homeButton");
						waitForPageLoaded();
					}
				}
				verifyTrue(verifyElementsOfAlertPage("viewFullOfList"),
						"View Full List Of Alert text not present on Home Page");
			}
		} catch (VerificationException e) {
			LOGGER.error(("Exception occured in method validateActiveAlertDataFromHomePage \n" + e.getMessage()));
			return false;
		}
		return true;
	}

	public boolean validateAlertTabInNotificationCenter() throws Exception {
		try {
			waitForPageLoaded();
			clickOnElementsOfAlertPage("notificationButton");
			boolean listPresent = verifyElementsOfAlertPage("notificationAlertList");
			clickOnElementsOfAlertPage("notificationButton");
			if (!listPresent) {
				clickOnElementsOfAlertPage("notificationButton");
				waitForPageLoaded();
				verifyTrue(verifyElementsOfAlertPage("noAlertNotificationMessage"),
						"No Alert Message is not present on Notification center");
				verifyTrue(verifyElementsOfAlertPage("noAlertNotificationSubText"),
						"No Alert sub message is not present on Notification center");
			} else {
				if (!verifyElementsOfAlertPage("ColumnData")) {

				} else {
					List<String> AlertList = getllAllElementsTextVisibleofAlertPage("ColumnData");
					if (!(verifyElementsOfAlertPage("notificationAlertTabTitle"))) {
						clickOnElementsOfAlertPage("notificationButton");
					}
					int listSize = getllAllElementsVisibleofAlertPage("notificationAlertList").size();
					for (int i = 1; i < listSize + 1; i++) {
						if (!(verifyElementsOfAlertPage("notificationAlertTabTitle"))) {
							clickOnElementsOfAlertPage("notificationButton");
						}
						String Alert = getTextOfElementOnAlertPage("notificationAlertTitle", "[" + i + "]");
						clickOnSpecificElementsOfAlertPage("notificationAlertTitle", "[" + i + "]");
						waitForPageLoaded();
						sleeper(5000);
						if (Alert.contains(CommonVariables.Bios_Alert) || Alert.contains(CommonVariables.Bsod_Alert)) {

						} else {
							if (AlertList.contains(Alert)) {
								verifyText(Alert, getTextOfAlertPage("breadcrumbsAlertTitle"),
										"Active Alert Details Page Breadcrumbs did not match with selected alert title from home");
								verifyText(Alert, getTextOfAlertPage("alertTitleText"),
										"Home Alert Title text did not matched on Active Alert Details Page");
							} else {
								waitForPageLoaded();
								verifyTrue(verifyElementsOfAlertPage("ColumnData"),
										"Should navigate to Alert page when click notification for old alert");
								verifyTrue(verifyElementsOfAlertPage("toastNotificationExport"),
										"Toast Message did not populated");
								clickOnElementsOfAlertPage("searchInput");
							}
						}
					}
				}
				if (!verifyElementsOfAlertPage("notificationAlertTabTitle")) {
					clickOnElementsOfAlertPage("notificationButton");
				}
				int listSize = getllAllElementsVisibleofAlertPage("notificationAlertList").size();
				mouseHoverOnAlertPage("notificationAlertOption");
				clickOnElementsOfAlertPage("notificationAlertOption");
				verifyTrue(verifyElementsOfAlertPage("markAsUnreadbtn"),
						"Mark as Unread button is not present on Notification center");
				verifyTrue(verifyElementsOfAlertPage("dismissBtn"),
						"Dismiss button is not present on Notification center");
				clickOnElementsOfAlertPage("dismissBtn");
				verifyNumber(listSize - 1, getllAllElementsVisibleofAlertPage("notificationAlertList").size(),
						"Notification did not deleted after clear");
			}
		} catch (VerificationException e) {
			LOGGER.error(("Exception occured in method validateAlertTabInNotificationCenter \n" + e.getMessage()));
			return false;
		}
		return true;
	}

	public static class VerificationException extends Exception {
		public VerificationException(String message) {
			super(message);
		}
	}

	public void verifyNumber(int actualNumber, int expectedNumber, String message) throws VerificationException {
		if (!(actualNumber == expectedNumber)) {
			throw new VerificationException("{" + actualNumber + " : " + expectedNumber + "}" + " \n" + message);
		}
	}

	public void verifyText(String actualText, String expectedText, String message) throws VerificationException {
		if (!actualText.equals(expectedText)) {
			throw new VerificationException("{" + actualText + " : " + expectedText + "}" + " \n" + message);
		}
	}

	public void verifyTrue(boolean value, String message) throws VerificationException {
		if (!value) {
			throw new VerificationException(message);
		}
	}
	public final boolean waitForElementsOfWEPAlertPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WEPAlertsDashboardPageProperties.getProperty(key), waitTime);
	}
}
