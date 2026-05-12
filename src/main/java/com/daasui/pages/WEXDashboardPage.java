package com.daasui.pages;

import java.io.IOException;
import java.util.*;

import com.daasui.constants.DashboardVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;

public class WEXDashboardPage extends CommonMethod {
	
	private WEXDashboardPage instance;
	private ObjectReader dashboardPagePropertiesReader = new ObjectReader();
	private Properties dashboardPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");
	
	
	public WEXDashboardPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXDashboardPage.class) {
				if (instance == null) {
					instance = new WEXDashboardPage(DRIVER);

				}
			}
		}
		return instance;
	}
	
	public WEXDashboardPage(WebDriver driver) throws IOException {
		dashboardPageProperties = dashboardPagePropertiesReader.getObjectRepository("WEXDashboardPage");
	}
	public final boolean verifyElementsOfDashboardPage(String key) throws Exception {
		return verifyElementIsPresent(dashboardPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfDashboardPage(String key) throws Exception {
		click(dashboardPageProperties.getProperty(key));
	}
	public final void mouseHoverOfDashboardPage(String key) throws Exception {
		mouseHover(dashboardPageProperties.getProperty(key));
	}
	public final void actionClickOfDashboardPage(String key) throws Exception {
		actionClick(dashboardPageProperties.getProperty(key));
	}
	
	public final boolean verifyElementisinvisibleofDashboardPage(String key) throws Exception {
		return verifyElementIsinvisibile(dashboardPageProperties.getProperty(key));
	}
	
	public final List<WebElement> getElementsOfDashboardPage(String key) throws Exception {
		return getAllElements(dashboardPageProperties.getProperty(key));
	}
	
	public final void clickByJavaScriptOnDashboardPage(String key) throws Exception {
		clickByJavaScript(dashboardPageProperties.getProperty(key));
	}
	public final void listMouseHoverOfDashboardPage(String key) throws Exception {
		listMouseHover(dashboardPageProperties.getProperty(key));
	}
	public final WebElement getElementOfDashboardPage(String key) throws Exception {
		return getElement(dashboardPageProperties.getProperty(key));
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
	
	/**
	 * This method designed to get the  Text of WebElement from web page
	 * @param key
	 */
	public final String getTextOfWexDashboardPage(String key) throws Exception {
		return getTextBy(dashboardPageProperties.getProperty(key));
	}
	
	public final boolean waitForElementsOfDashboardPage(String key) throws Exception {
		return verifyElementIsVisible(dashboardPageProperties.getProperty(key));
	}
	
	public final boolean matchTextOfDashboardPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(dashboardPageProperties.getProperty(key), Text);
	}
	public final boolean waitForElementsOfDashboardPageDynamic(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(dashboardPageProperties.getProperty(key), waitTime);
	}
	
	public final boolean waitUntilElementIsInvisibleOfDashboardPage(String key){
		return verifyElementIsinvisibile(dashboardPageProperties.getProperty(key));
	}
	
	public final boolean waitUntilAllElementIsInvisibleOfDashboardPage(List<WebElement> key){
		return verifyAllElementIsinvisibile(key);
	}

	public final void enterTextForDashboardPage(String key, String Text) {
		try {
			enterText(dashboardPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForDashboardPage " + e.getMessage()));
		}
	}

	/**
	 * This method is to mouse hover and click through javascript for a webelement
	 * @param key- webelement
	 * @throws Exception
	 */
	public final void mouseHoverclickOfDashboardPage(WebElement key) throws Exception {
		mouseHoverclick(key);
	}
	/**
	 * This method will Switch the Account of the User having same email address
	 * 
	 * @throws Exception
	 */
	public final void verifySwitchAccount(String language) throws Exception {
		String currentAccount = getTextOfWexDashboardPage("switchAccountTextBox");
		sleeper(2000);
		actionClickOfDashboardPage("switchAccountdropdown");
		LOGGER.info("switchAccountdropdown is clicked");
		waitForElementsOfDashboardPage("listOfAccounts");
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
					if (!(getTextOfWexDashboardPage("userProfileAccount")
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
					if (!(getTextOfWexDashboardPage("userProfileAccount")
							.contains(getTextLanguage(language, "daas_ui", "global.form.msp"))))
						LOGGER.error("Error while switching the Account");
					break;
				} else {
					clickOnElementsOfDashboardPage("switchAccountButton");
					waitForPageLoaded();
					waitForElementsOfDashboardPageDynamic("pageTitle", CommonVariables.LOGIN_WAIT);
					waitUntilElementIsInvisibleOfDashboardPage("tableOverlay");
					actionClickOfDashboardPage("userProfileButton");
					if (!(getTextOfWexDashboardPage("userProfileAccount")
							.contains(getTextLanguage(language, "daas_ui", "roles.it_admin"))))
						LOGGER.error("Error while switching the Account");
					break;
				}

			}

		}
		actionClickOfDashboardPage("userProfileButton");
	}

	/**
	 * This method used for move the mouse left side of total count.
	 *  @param key:it is center point from that we start moving.
	 * @param left:It is value for moving left from center point
	 * @param right:It is value for moving Right from center point
	 * @throws Exception
	 */
	public final void mouseHoverbyoffsett(String key,int left,int right) throws Exception {
		mouseHoverbyoffset(dashboardPageProperties.getProperty(key),left,right);
	}

	/**
	 * This method used for click on left side of total count.
	 * @param key:it is center point from that we start moving.
	 * @param left:It is value for moving left from center point
	 * @param right:It is value for moving Right from center point
	 * @throws Exception
	 */
	public final void mouseHoverbyoffsettClick(String key,int left,int right) throws Exception {
		mouseHoverbyoffsetClick(dashboardPageProperties.getProperty(key),left,right);
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
			//scrollDownCharts();
			scrollToDashboardPage("exportButton");
			List<WebElement> columnValues = getElementsOfDashboardPage(columnValuesLocator);
			Set<WebElement> uniquecolumnValues = new HashSet<WebElement>(columnValues);
			switch(category.trim().toLowerCase()){
				case "great":
					for (int i=0;i<uniquecolumnValues.size();i++) {
						if(Float.parseFloat(columnValues.get(i).getText())<85) {
							flag=false;
							break;
						}else {
							flag=true;
						}
					}
					return flag;

				case "fair":
					for (int i=0;i<columnValues.size();i++) {
						if(!(Float.parseFloat(columnValues.get(i).getText())>55 || Float.parseFloat(columnValues.get(i).getText())<85)) {
							flag=false;
							break;
						}else {
							flag=true;
						}
					}
					return flag;
				case "poor":
					for (int i=0;i<columnValues.size();i++) {
						if(Float.parseFloat(columnValues.get(i).getText())>55) {
							flag=false;
							break;
						}else {
							flag=true;
						}
					}
					return flag;

				default:
					LOGGER.error("Given : " + category + " category is incorrect");
					throw new InputMismatchException("You can use : Great,Fair,Poor only ");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/** This method will validate count of devices from chart to Grid.
	 * @param noOfDevices
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCountOfDevices(int noOfDevices) throws Exception {
		boolean flag = false;
		sleeper(5000);
		waitUntilElementIsInvisibleOfDashboardPage("reactSkelaton");
		String paginationText = getTextOfWexDashboardPage("paginationCount");
		String [] paginationsubString = paginationText.split(" ");
		LOGGER.info("Noofdevices: "+noOfDevices);
		LOGGER.info("SubString: "+paginationsubString[0]+" "+paginationsubString[1]);
		String paginationClean = paginationsubString[1].replace(",","");
		LOGGER.info("parsint: "+Integer.parseInt(paginationClean));
		if(noOfDevices==Integer.parseInt(paginationClean)) {
			flag = true;
		}
		else {
			flag = false;
			LOGGER.error("Count did not match from chart to Grid.");
		}

		return flag;
	}


	/** This method will validate Health Summary chart.
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyDashBoardDonutChart() throws Exception {
		boolean flag= false;
		String status,noOfDevices= null;
		mouseHoverbyoffsett("donutchartCentreText", 00, 75);
		sleeper(3000);
		status = getTextOfWexDashboardPage("donutChartTootlipText1");
		sleeper(2000);
		String[] statusSubstring = status.split(" ");
		String scoreCategory = statusSubstring[0];
		noOfDevices = getTextOfWexDashboardPage("donutChartTootlipText2");
		String[] noOfDevicesSubString = noOfDevices.split("\\(");
		int noOfDevicesInt = Integer.parseInt(noOfDevicesSubString[0].trim());
		mouseHoverbyoffsettClick("donutchartCentreText", 00, 75);
		if(verifyScoreCategory(scoreCategory,"scoreColumnValues")) {
			if(verifyCountOfDevices(noOfDevicesInt)) {
				flag=true;
				LOGGER.info("Dashboard Donut chart's validation passed successfully.");
			}
			else
			{
				flag=false;
				LOGGER.error("Count of devices validation in Dashboard Donut chart got failed.");
			}
		}
		else
		{
			flag= false;
			LOGGER.error("Score validation in Dashboard Bar chart got failed.");
		}
		return flag;
	}
	/**
	 * This method will put the different status of the widget into a hashmap and return the hashmap
	 *
	 * @param pcStatusInventoriesListElements webelement for legends of widget
	 * @return hashmap having legend with count
	 * @throws Exception
	 */
	public HashMap<String, Integer> getStatusBasedPCCount(List<WebElement> pcStatusInventoriesListElements) throws Exception {
		int newCount = 0;
		int totalCountOfDevices = Integer.parseInt(getTextOfWexDashboardPage("pcWidgetCenterVal"));
		HashMap<String, Integer> differentPcsStatus = new HashMap<>();
		for (WebElement element : pcStatusInventoriesListElements) {
			if (!element.getText().isEmpty()) {
				mouseHoverclickOfDashboardPage(element);
				String latestCountOfRemainPcs = getTextOfWexDashboardPage("pcWidgetCenterVal");
				if(!latestCountOfRemainPcs.isEmpty()){
					String status = element.getText();
					int resultToTest= totalCountOfDevices - Integer.parseInt(latestCountOfRemainPcs)-newCount;
					differentPcsStatus.put(status,resultToTest);
					newCount += resultToTest;
				}
			}
		}
		return differentPcsStatus;
	}

	/**
	 * This method verifies if count is matching
	 * @param locator - locator to check
	 * @param finalCountToVerify -number to verify against
	 * @return true if count is matching false otherwise
	 * @throws Exception
	 */
	public boolean isCountMatching(String locator, int finalCountToVerify) throws Exception {
		int finalCount = Integer.parseInt(getTextOfWexDashboardPage(locator));
		return finalCountToVerify == finalCount;
	}

	/**
	 * This method gets the total pagination count
	 * @param wepDeviceListPage
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public int getTotalNumbersFromPagination(WEPDeviceListPage wepDeviceListPage, String locator) throws Exception {
		String totalDeviceText = wepDeviceListPage.getTextOfWEPDeviceListPage(locator);
		return Integer.parseInt(totalDeviceText.replace(",","").split("of ")[1]);
	}

	/**
	 * This method is used to navigate to the device list page after clicking
	 * from the pc inventory widget
	 * @param svg webelement for the widget
	 */
	public void navigateToDeviceListPagefromWidget(WebElement svg){
		int xOffset = 0;
		int yOffset = -98;
		calculateCoordinatesAndClickOnWidget(svg,xOffset,yOffset);

	}
}

