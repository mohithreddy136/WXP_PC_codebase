package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ObjectReader;

public class MEMDetailsPage extends CommonMethod {
	private ObjectReader MEMPagePropertiesReader = new ObjectReader();
	private Properties MEMPageProperties;
	private Properties selecteCredentialsProperties;
	public static String environment;
	
	private MEMDetailsPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	public MEMDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (MEMDetailsPage.class) {
				if (instance == null) {
					instance = new MEMDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public MEMDetailsPage(WebDriver driver) throws IOException {
		MEMPageProperties = MEMPagePropertiesReader.getObjectRepository("MEMDetailsPageV3");
	}
	
	public final String getCredentials(String credentials, String key) throws Exception {
		selecteCredentialsProperties = MEMPagePropertiesReader.getCredentials(credentials);
		return selecteCredentialsProperties.getProperty(key);
	}

	public final void clickByJavaScriptOnMEMPage(String key) {
		try {
			clickByJavaScript(MEMPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method clickByJavaScriptOnMEMPage " + e.getMessage()));
		}
	}
	
	public boolean azureLogin(String username, String password) throws Exception {
		environment = System.getProperty("environment");
		try {
			boolean flag = false;
			enterTextForMEMPage("userNameAzure", getCredentials(environment, username));
			LOGGER.info("Email is provided for Azure.");
			clickOnMEMPage("nextButtonAzure");
			waitForPageLoaded();
			sleeper(3000);
			enterTextForMEMPage("passwordAzure", getCredentials(environment, password));
			LOGGER.info("Password is provided for Azure.");
			clickOnMEMPage("nextButtonAzure");
			waitForPageLoaded();
			sleeper(3000);
			LOGGER.info("User Logged in successfully.");
			return flag;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method azureLogin " + e.getMessage()));
			return false;
		}
	}
	
	
	public final void clickOnMEMPage(String key) throws Exception {
		click(MEMPageProperties.getProperty(key));
	}

	
	public final boolean verifyLinksoOfAzurePage(String key) {
		try {
			return verifyElementIsPresent(MEMPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyLinksoOfAzurePage " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This is a method to get a list of elements of campaign details page
	 * 
	 * @param key - Locator of list
	 * @return - list of webElements
	 */	
	public final List<WebElement> getElementsOfWidgetListPage(String key) {
		try {
			return getElementsTillAllElementsPresent(MEMPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("sfsfsException occured in method getElementsOfWidgetListPage " + e.getMessage()));
			return null;
		}
	}
	
	public final void clickCheckboxInWidget(List<WebElement> widgetList, List<WebElement> widgetCheckboxList) throws Exception {

		Iterator<WebElement> widgetListIterator = widgetList.iterator();
		Iterator<WebElement> widgetCheckboxIterator = widgetCheckboxList.iterator();
		while (widgetListIterator.hasNext()) {
			WebElement element = widgetListIterator.next();
			WebElement checkBox = widgetCheckboxIterator.next();
			mouseHover(checkBox);
			element.click();
		}
	}
	
	public final boolean verifyCheckboxesOrderOnWidgetPage(String allCheckboxesLocatorKey) throws Exception {
		Boolean flag = false;
		String checkboxesIdsArray[] = { "Battery health","Hard disk health","Right sizing your fleet","BIOS updates","Driver updates","OS updates and Device Inventory","Security policies","Warranty status","Application performance","Installed apps","Device lifecycle management","Thermal health","Other"};
		
		ArrayList<String> chartIdsList = new ArrayList<String>(Arrays.asList(checkboxesIdsArray));
		List<WebElement> allCharts = getElementsTillAllElementsVisible(MEMPageProperties.getProperty(allCheckboxesLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < chartIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allCharts.size() - 1;) {
				if (!chartIdsList.get(chartIdsListCounter).equalsIgnoreCase(allCharts.get(allChartsCounter).getText())) {
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

	public final boolean matchTextOnAzureDetailsPage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(MEMPageProperties.getProperty(key), textToMatch);
	}
	
	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfMEMPage(String key) throws Exception {
		return verifyElementIsVisible(MEMPageProperties.getProperty(key));
	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfMEMPage(String key) throws Exception {
		click(MEMPageProperties.getProperty(key));
	}
	
	/**
	 * This method is the method to verify if an element is present on MEM page
	 * 
	 * @param key - locator of the element
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyElementsOfMEMPage(String key) throws Exception {
		return verifyElementIsPresent(MEMPageProperties.getProperty(key));
	}
	
	/**
	 * This method is the method to get the text on MEM page
	 * 
	 * @param key - locator of the element
	 * @return
	 * @throws Exception
	 */
	public final String getTextOfMEMPage(String key) throws Exception {
		return getTextBy(MEMPageProperties.getProperty(key));
	}
	
	public final void scrollOnMEMPage(String key) throws Exception {
		scrollTillView(MEMPageProperties.getProperty(key));
	}
	
	/**
	 * This is a method to enter text on an element
	 * 
	 * @param key
	 * @throws Exception
	 */

	public final void enterTextForMEMPage(String key, String textToenter) throws Exception {
		enterText(MEMPageProperties.getProperty(key), textToenter);
	}
	
	/**
	 * This is a method to get the text of a list as a list itself
	 * 
	 * @param key - locator of element
	 * @return - arraylist of the text of all elements present in the list
	 */

	public final ArrayList<String> getTextOfListOfMEMPage(String key) {
		try {
			return getTextOfList(MEMPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getTextOfListOfMEMPage " + e.getMessage()));
			return null;
		}
	}
	
	public final String selectFirstValueFromDropdownMEMPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsMEMPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}
	
	/**
	 * This is the method to select the element based on text from a drop down
	 * 
	 * @param dropdownListKey: List of values in drop down.
	 * @param elementText: Element text which is to be clicked.
	 * @param dropdownBox: Drop down box locator.
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean selectSpecificValueFromDropdownOnMEMPage(String dropdownListKey, String elementText, String dropdownBox) {
		try {
			return selectTextValueFromDropdown(MEMPageProperties.getProperty(dropdownListKey), elementText, MEMPageProperties.getProperty(dropdownBox));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectSpecificValueFromDropdownOnMEMPage " + e.getMessage()));
			return false;
		}
	}

	public final List<WebElement> getElementsTillAllElementsMEMPage(String locator) throws Exception {
		return getElementsTillAllElementsPresent(MEMPageProperties.getProperty(locator));
	}
	
	public final String getAttributesOfMEMPage(String key, String value) throws Exception {
		return getAttribute(MEMPageProperties.getProperty(key), value);
	}
	
	/**
	 * Verifies the drop down list of Industry
	 * 
	 * @param allLocatorKey
	 * @return
	 * @throws Exception
	 */
	public final boolean verifydropdownOrderOfMEMPage(String allLocatorKey) throws Exception {
		Boolean flag = false;
		String chartIdsArray[] ={ "Aerospace & Defense","Automotive","Banking","Chemicals & Petroleum","Computer Services","Consumer Products","Education","Electronics","Energy & Utilities","Exclusions","Financial Markets","Government, Central/Federal","Government, State/Provincial/Local","Healthcare","Industrial Products","Insurance","Life Sciences","Media & Entertainment","Professional Services"};
		ArrayList<String> IndustryIdsList = new ArrayList<String>(Arrays.asList(chartIdsArray));
		List<WebElement> allIndustryIdsList = getElementsTillAllElementsVisible(MEMPageProperties.getProperty(allLocatorKey));
		for (int chartIdsListCounter = 0; chartIdsListCounter < IndustryIdsList.size(); chartIdsListCounter++) {
			for (int allChartsCounter = chartIdsListCounter; allChartsCounter < allIndustryIdsList.size() - 1;) {
				if (!IndustryIdsList.get(chartIdsListCounter).equalsIgnoreCase(allIndustryIdsList.get(allChartsCounter).getText())) {
					LOGGER.error("Sequence of " + allIndustryIdsList.get(allChartsCounter).getText() + " is not correct");
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
	 * Verifies the drop down list of Country
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	
	public final boolean verifyCompanySizedropdownOfMEMPage(String LanguageCode) throws Exception {
		boolean companySizeMatched= false;
		ArrayList<String> companySizeUI = getTextOfListOfMEMPage("companySizelistvalues");
		ArrayList<String> companySizeMaestro= new ArrayList<>(Arrays.asList(
			getTextLanguage(LanguageCode, "lhserver","support_admin.companies.add.sizes.0"),
			getTextLanguage(LanguageCode, "lhserver","support_admin.companies.add.sizes.1"),				
			getTextLanguage(LanguageCode, "lhserver","support_admin.companies.add.sizes.2"), 
			getTextLanguage(LanguageCode, "lhserver","support_admin.companies.add.sizes.3"),
			getTextLanguage(LanguageCode, "lhserver","support_admin.companies.add.sizes.4"),
			getTextLanguage(LanguageCode, "lhserver","support_admin.companies.add.sizes.5"),
			getTextLanguage(LanguageCode, "lhserver","support_admin.companies.add.sizes.6")));
		for (String sizeUI : companySizeUI) {
			for(String sizeMaestro: companySizeMaestro)
			{
				if(sizeUI.equalsIgnoreCase(sizeMaestro)) {
					companySizeMatched=true;
				}
			}
		}
		return companySizeMatched;
		}
	
	/**
	 * Logout method for MEM Flow
	 * 
	 */
	public final void MEMlogout() throws Exception {
		sleeper(3000);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		refreshPage();
		if (dashboardPage.verifyElementsOfDashboardPage("logoutButton")) {
			sleeper(3000);
			dashboardPage.clickByJavaScriptOnDashboardPage("logoutButton");
			sleeper(3000);
			dashboardPage.waitForPresenceOfElementsOfDashboardPage("signoutButton");	
			dashboardPage.clickByJavaScriptOnDashboardPage("signoutButton");
		} else if (dashboardPage.verifyElementsOfDashboardPage("logoutButtonWithoutImage")) {
			sleeper(3000);
			dashboardPage.clickByJavaScriptOnDashboardPage("logoutButtonWithoutImage");
			sleeper(3000);
			dashboardPage.waitForPresenceOfElementsOfDashboardPage("signoutButton");
			dashboardPage.clickByJavaScriptOnDashboardPage("signoutButton");
		} else {
			LOGGER.error("Logout button not present");
		}
	}
	
}