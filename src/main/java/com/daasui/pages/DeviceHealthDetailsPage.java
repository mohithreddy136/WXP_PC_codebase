package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class DeviceHealthDetailsPage extends CommonMethod {
	private ObjectReader deviceHealthDetailsPropertiesReader = new ObjectReader();
	private Properties deviceHealthDetailsPageProperties;
	private Properties selectedLanguageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private DeviceHealthDetailsPage instance;

	public DeviceHealthDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (DeviceHealthDetailsPage.class) {
				if (instance == null) {
					instance = new DeviceHealthDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public DeviceHealthDetailsPage(WebDriver driver) throws IOException {
		deviceHealthDetailsPageProperties = deviceHealthDetailsPropertiesReader.getObjectRepository("DeviceHealthDetailsPageV3");
	}

	public final boolean verifyElementsOfDeviceHealthDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(deviceHealthDetailsPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfDeviceHealthDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(deviceHealthDetailsPageProperties.getProperty(key));
	}

	public final boolean matchTextOfDeviceHealthDetailsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(deviceHealthDetailsPageProperties.getProperty(key), Text);
	}

	public final void clickOnElementsOfDeviceHealthDetailsPage(String key) throws Exception {
		click(deviceHealthDetailsPageProperties.getProperty(key));
	}
	
	public final String getTextOfElementsOfDeviceHealthDetailsPage(String key) throws Exception {
		return getTextBy(deviceHealthDetailsPageProperties.getProperty(key));
	}
	
	public final void scrollTillViewOfDeviceHealthDetailsPage(String key) throws Exception {
		scrollTillView(deviceHealthDetailsPageProperties.getProperty(key));
	}
	public void scrollToDeviceHealthDetailsPage(String key) throws Exception {
		scrollTillView(deviceHealthDetailsPageProperties.getProperty(key));
	}
	
	/**
	 * This is a method to hover mouse on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void mousehoverOnDeviceHealthDetailsPage(String key) {
		try {
			mouseHover(deviceHealthDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnDeviceHealthDetailsPage " + e.getMessage()));
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
		selectedLanguageProperties = deviceHealthDetailsPropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}
	
	/**
	 * This is a method to get a list of elements present on device list page
	 * 
	 * @param key - Locator of element
	 * @return - list of web elements
	 */
	public final List<WebElement> getElementsOfDeviceHealthPage(String key) throws Exception {
	
			return getElementsTillAllElementsPresent(deviceHealthDetailsPageProperties.getProperty(key));
		
	}
	
	public final boolean columnHeadersTest(String languageCode) throws Exception {
		boolean flag = true;
		try {
			clickOnElementsOfDeviceHealthDetailsPage("batteryReplacementViewDetailButton");
			switchToDifferentTab();
			LOGGER.info("Switched to other tab");
			waitForPageLoaded();
			List<WebElement> actualList = new ArrayList<WebElement>();
			ArrayList<String> expectedList = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.companyName"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.deviceManufacturer"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.operatingSystem"),
					getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.lastSeen"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.manufacture_date"),
					getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.incidentId"), getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.incidentDate"), getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.incidentType"), getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.incidentSubType"),
					getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.deviceWarStatus"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.priority"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.status")));

			actualList = getElementsOfDeviceHealthPage("tableColumnList");		
			if(expectedList.size()==actualList.size()) {
				if(actualList.size()>0 ) {
					for(int counter=0;counter<actualList.size();counter++) {
						if(!(expectedList.get(counter).equalsIgnoreCase(actualList.get(counter).getText()))) {
							flag = false;
							LOGGER.info("Columns mismatch"+actualList.get(counter).getText());
						}
					}
				}else {
					flag = false;
					LOGGER.info("Column list is empty");
				}

			}else
				LOGGER.info("Columns mismatch");


		} catch (Exception e) {
			flag = false;
			LOGGER.error("Exception occured in columnHeadersTest"+e.getMessage());
		}
		return flag;	
	}
	
	/**
	 * @author raiu(Umang Rai)
	 * @param languageCode
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public boolean columnHeadersTestNew(String languageCode, String countOnWidgetLocator, String detailsLocator) throws Exception {
		boolean flag = true;
		int noOfRows;
		try {
			String countOnWidget = getTextOfElementsOfDeviceHealthDetailsPage(countOnWidgetLocator);
			mousehoverOnDeviceHealthDetailsPage(detailsLocator);
			clickOnElementsOfDeviceHealthDetailsPage(detailsLocator);
			switchToDifferentTab();
			LOGGER.info("Switched to other tab");
			waitForPageLoaded();
			String countOnDetailsInitial = getTextOfElementsOfDeviceHealthDetailsPage("tableCountOnFooter");
			String[] splitArrayOfCountOnDetailsInitial = countOnDetailsInitial.split(" ");
			String finalCountOnDetailsFooter = splitArrayOfCountOnDetailsInitial[1];
			List<WebElement> actualList = new ArrayList<WebElement>();
			List<WebElement> actualNoOfRows = new ArrayList<WebElement>();
			List<String> expectedList = new ArrayList<String>(Arrays.asList(getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.companyName"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.deviceManufacturer"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.operatingSystem"),
					getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"), getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Workflow.Details.incident_count"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.lastSeen"), 
					getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.manufacture_date"), getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.incidentDate"), getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.incidentType"), getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.incidentSubType"),
					getTextLanguage(languageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.deviceWarStatus"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.priority"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.location1"), 
					getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.location2"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.location3"), getTextLanguage(languageCode, "daas_reports_ui", "Global.gridHeaders.location4")));

			actualList = getElementsOfDeviceHealthPage("tableColumnList");		
			if(expectedList.size()==actualList.size()) {
				if(actualList.size()>0 ) {
					for(int counter=0;counter<actualList.size();counter++) {
						if(!(expectedList.get(counter).equalsIgnoreCase(actualList.get(counter).getText()))) {
							flag = false;
							LOGGER.info("Columns mismatch"+actualList.get(counter).getText());
						}
					}
				}else {
					flag = false;
					LOGGER.info("Column list is empty");
				}

			}else {
				LOGGER.info("Columns mismatch");
				flag = false;
			}

			//verify that count between widget and table footer is same.
			if(countOnWidget.equalsIgnoreCase(String.valueOf(finalCountOnDetailsFooter))) {
				LOGGER.info("Count matched between widget and details table footer");
			}else {
				LOGGER.info("Count mismatch between widget and details table footer");
				flag = false;
			}

			//verify that count between no of rows and table footer is same.
			actualNoOfRows = getElementsOfDeviceHealthPage("tableNoOfRows");
			noOfRows = actualNoOfRows.size();
			if(String.valueOf(noOfRows).equalsIgnoreCase(finalCountOnDetailsFooter)) {
				LOGGER.info("Count matched between no of rows in table and details table footer");
			}else {
				LOGGER.info("Count mismatch between no of rows in table and details table footer");
				flag = false;
			}

		} catch (Exception e) {
			flag = false;
			LOGGER.error("Exception occured in columnHeadersTestNew"+e.getMessage());
		}
		return flag;
	}
}