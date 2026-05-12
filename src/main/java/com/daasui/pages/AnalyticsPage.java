package com.daasui.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ExcelReader;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.WorkflowVariables;

/**
 * This class implements all the functions related to reports tests.
 * 
 * @author umang
 *
 */
public class AnalyticsPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader analyticsPagePropertiesReader = new ObjectReader();
	private Properties analyticsPagePropertiesPageProperties;
	private Properties analyticsDataProperties;
	private Properties analyticsTabTitleProperties;
	private final static Logger LOGGER = LogManager.getLogger(AnalyticsPage.class);
	private AnalyticsPage instance;
	//private Object reportFlag;
	public static String uiVersion = System.getProperty("uiVersion");
	public AnalyticsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (AnalyticsPage.class) {
				if (instance == null) {
					instance = new AnalyticsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public AnalyticsPage(WebDriver driver) throws IOException {
		analyticsDataProperties = analyticsPagePropertiesReader.getObjectRepository("AnalyticsData");
		analyticsTabTitleProperties = analyticsPagePropertiesReader.getObjectRepository("AnalyticsTabTitles");
		analyticsPagePropertiesPageProperties = analyticsPagePropertiesReader.getObjectRepository("AnalyticsPage");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = analyticsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * This method retrieves all widgets on the dashboard page and clicks on each one.
	 * It ensures that all widgets are interacted with.
	 *
	 * If an exception occurs during the process, it logs the error message.
	 */

	public final void getWidgetByCategory(){
		try {
			List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("allWidgets");
			for (WebElement we : element) {
				{
					we.click();

				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in getWidgetByCategory : " + e.getMessage());
		}
	}

	/**
	 * @param language-langugae code
	 * @param localefolder -locale folder name
	 * @param key - list of text
	 * @return arraylist object
	 * @throws Exception
	 */
	public final ArrayList<String> getTextLanguage(String language, String localefolder, String[] key) throws Exception {
		ArrayList<String> keyValues = new ArrayList<String>();
		selectedLanguageProperties = analyticsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		for (int keyCounter = 0; keyCounter < key.length; keyCounter++) {
			keyValues.add(selectedLanguageProperties.getProperty(key[keyCounter]).trim());
		}
		return keyValues;
	}
	
	public final boolean waitForElementsOfAnalyticsPage(String key) throws Exception {
		return verifyElementIsVisible(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsPresentAnalyticsPage(String locator) throws Exception {
		return getElementsTillAllElementsPresent(analyticsPagePropertiesPageProperties.getProperty(locator));
	}

	public final boolean verifyElementsOfAnalyticsPage(String key) throws Exception {
		return verifyElementIsPresent(analyticsPagePropertiesPageProperties.getProperty(key));

	}

	public final boolean verifyElementIsVisibleOfAnalyticsPage(String key) throws Exception {
		return verifyElementIsVisible(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean matchTextOfAnalyticsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(analyticsPagePropertiesPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfAnalyticsPage(String key) throws Exception {
		return verifyElementIsEnable(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfAnalyticsPage(String key) throws Exception {
		return verifyElementIsSelected(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	public final String getTextOfAnalyticsPage(String key) throws Exception {
		return getTextBy(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	@Override
	public boolean verifyElementIsVisible(String locator) throws Exception {
		return super.verifyElementIsVisible(locator);
	}

	public final String getAttributesOfAnalyticsPage(String key, String value) throws Exception {
		return getAttribute(analyticsPagePropertiesPageProperties.getProperty(key), value);
	}

	public final void waitUntilElementIsVisibleOfAnalyticsPage(String key) throws Exception {
		waitUntilElementIsVisible(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfAnalyticsPage(String key) throws Exception {
		click(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	public final void mouseHoverAndClickOfAnalyticsPage(String key) throws Exception {
		actionClick(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnAnalyticsPage(String key) throws Exception {
		clickByJavaScript(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	public final void enterTextForAnalyticsPage(String key, String Text) throws Exception {
		enterText(analyticsPagePropertiesPageProperties.getProperty(key), Text);
	}

	public final void scrollTillViewAnalyticsPage(String key) throws Exception {
		scrollTillView(analyticsPagePropertiesPageProperties.getProperty(key));
	}


	public final List<WebElement> getElementsTillAllElementsVisibleAnalyticsPage(String locator) throws Exception {
		return getElementsTillAllElementsVisible(analyticsPagePropertiesPageProperties.getProperty(locator));
	}

	public final int getWindowHandlesofAnalyticsPage() throws Exception {
		return getCountofWindowHandles();
	}
	
	public final List<WebElement> getElementsOfAnalyticsPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(analyticsPagePropertiesPageProperties.getProperty(key));
	}
	
	public final WebElement getElementOfAnalyticsPage(String key) throws Exception {
		return getElement(analyticsPagePropertiesPageProperties.getProperty(key));
	}
	
	public final boolean waitForPresenceOfElementsOfAnalyticsPage(String key) throws Exception {
		return waitUntillElementIsPresent(analyticsPagePropertiesPageProperties.getProperty(key));
	}
	
	public final boolean verifyIfElementIsClickableOfAnalyticsPage(String key) throws Exception {
		return verifyElementIsClickable(analyticsPagePropertiesPageProperties.getProperty(key));
	}
	
	public final void scrollUpPage() {
		jsDriver().executeScript("scroll(0, -750);");
	}
	
	public final void scrollDownPage() {
		jsDriver().executeScript("scroll(0, 1000);");
	}
	
	public final boolean waitForElementsOfAnalyticsPageDynamic(String key, int wait) throws Exception {
		return verifyElementIsVisibleDynamic(analyticsPagePropertiesPageProperties.getProperty(key), wait);
	}
	
	public final boolean waitUntilElementIsVisibleOfAnalyticsPageDynamic(String key, int wait) throws Exception {
		return waitUntilElementIsVisibleDynamic(analyticsPagePropertiesPageProperties.getProperty(key), wait);
	}

	/**
	 * This is a method to wait until an element is invisible
	 *
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfAnalyticsPage(String key) {
		try {
			verifyElementIsinvisibile(analyticsPagePropertiesPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitUntilElementIsInvisibleOfAnalyticsPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to select option from drop down of pagination
	 * 
	 * @param dropdownId - Id of pagination dropdown
	 * @param key - This is the key for values on dropdown
	 * @param text - This is the values text to select from dropdown
	 * @return true
	 * @throws Exception
	 */
	public final boolean selectElementFromDropDownOfAnalyticsPage(String dropdownId, String key, String text) {
		try {
			click(analyticsPagePropertiesPageProperties.getProperty(dropdownId));
			if(dropdownId.equalsIgnoreCase("paginationDropdown")) {
					jsDriver().executeScript("scroll(0, 750);");
					sleeper(2000);
			}
			return selectFromDropdown(analyticsPagePropertiesPageProperties.getProperty(dropdownId), analyticsPagePropertiesPageProperties.getProperty(key), text);
		} catch (Exception e) {
			LOGGER.error("Exception occured while selectElementFromDropDownOfUserPage " + e.getMessage());
			return false;
		}
		
	}

	/**
	 * This method selects the category for generating the report.
	 * 
	 * @param category
	 * @throws Exception
	 */
	public final void selectCategoryForAnalyticsPage(String category) throws Exception {
		try {
			//Scroll up in a vertical direction
			scrollUpInVertical();
			clickOnElementsOfAnalyticsPage("categoryDropdown");
			List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("categoryList");
			for (WebElement we : element) {
				if (we.getText().equalsIgnoreCase(category)) {
					we.click();
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectCategoryForAnalyticsPage : " + e.getMessage());
		}
	}

	/**
	 * This method selects the sub-category for generating the report.
	 * 
	 * @param subcategory
	 * @throws Exception
	 */
	public final void selectSubCategoryForAnalyticsPage(String subcategory, String languageCode) throws Exception {
		try {
			if (getTextOfAnalyticsPage("subcategoryDropdown").equalsIgnoreCase(
					getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON", "select_option_text"))
					|| !(getTextOfAnalyticsPage("subcategoryDropdown").equalsIgnoreCase(subcategory))) {
				clickOnElementsOfAnalyticsPage("subcategoryDropdown");
				List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("subCategoryList");
				for (WebElement we : element) {
					scrollTillView(we);
					if (we.getText().equalsIgnoreCase(subcategory)) {
						we.click();
						break;
					}
				}
			} else {
				LOGGER.info("Report sub category is selected by default");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectSubCategoryForAnalyticsPage : " + e.getMessage());
		}
	}
	
	/**
	 * This method selects the sub-category for generating pdf & xls report.
	 * 
	 * @param category
	 * @param subcategory
	 * @param languageCode
	 * @throws Exception
	 */
	public final boolean selectSubCategoryForAnalyticsPagePDFXLS(String category, String subcategory, String languageCode)
			throws Exception {
		try {
			// Scroll up in a vertical direction
			scrollUpInVertical();
			if ((category.equalsIgnoreCase(
					getTextLanguage(languageCode, "MPI-Reporting-LHreports_service", "label.report_type_Hardware")))
					|| (category.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
							"label.report_type_Security")))
					|| (category.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
							"label.report_type_Software")))
					|| (category.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
							"label.report_type_Deployment")))
					|| (category.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
							"label.report_type_Subscription")))
					|| (category.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
							"label.report_type_Conference_Service")))) {
				clickOnElementsOfAnalyticsPage("subcategoryDropdown");
				Thread.sleep(1000);
				List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("subCategoryList");
				for (WebElement we : element) {
					scrollTillView(we);
					if (we.getText().equalsIgnoreCase(subcategory)) {
						we.click();
						break;
					}
				}
				// Check presence of Subcategory
				if (!getTextOfAnalyticsPage("subcategoryDropdown").equalsIgnoreCase(subcategory)) {
					LOGGER.info("Failed to select subcategory :" + subcategory);
					if (verifyElementsOfAnalyticsPage("closeSubCatDropDown")) {
						// To click on blank area
						if (uiVersion.equalsIgnoreCase("VENEER3")) {
							clickOnElementsOfAnalyticsPage("blankArea");
						} else {
							clickOnElementsOfAnalyticsPage("closeSubCatDropDown");
						}
					}
					return false;
				}
			} else {
				LOGGER.info("Report sub category is selected by default");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectSubCategoryForAnalyticsPagePDFXLS : " + e.getMessage());
		}
		return true;
	}

	/**
	 * This method selects the filter-Option for Report Page
	 *
	 * @param criteriaOption
	 * @throws Exception
	 */

	public final void selectFilterCriteriaForAnalyticsPage(String criteriaOption){
		try{
			clickOnElementsOfAnalyticsPage("addFilterCriteria");
			waitForElementsOfAnalyticsPage("criteriaFieldDropdown");
			clickOnElementsOfAnalyticsPage("criteriaFieldDropdown");
			sleeper(1000);
			boolean isVisibleFilterOption = verifyElementIsVisibleOfAnalyticsPage("filterFieldOptionList");
			if (isVisibleFilterOption == true){
				selectTextValueFromDropdownWorkflow(analyticsPagePropertiesPageProperties.getProperty("filterFieldOptionList"), criteriaOption,
						analyticsPagePropertiesPageProperties.getProperty("criteriaFieldDropdown"));
				clickOnElementsOfAnalyticsPage("criteriaValueDropdown");
				sleeper(1000);
				boolean isVisibleFilterValue=verifyElementIsVisibleOfAnalyticsPage("filterFieldValueOptionList");

				if (isVisibleFilterValue == true){
					String filterValue = selectFirstValueFromDropdown(analyticsPagePropertiesPageProperties.getProperty("filterFieldValueOptionList"));
					LOGGER.info("Criteria : "+criteriaOption+ " is applied with value "+filterValue);
				}else {
					waitForElementsOfAnalyticsPage("deleteCriteriaFilter");
					clickOnElementsOfAnalyticsPage("deleteCriteriaFilter");
					sleeper(3000);
					LOGGER.info("Filter option is deleted as Filter Value is not available");
					LOGGER.info("CriteriaFilterValue is not present in Filter Criteria");
				}
			}else{
				waitForElementsOfAnalyticsPage("deleteCriteriaFilter");
				clickOnElementsOfAnalyticsPage("deleteCriteriaFilter");
				sleeper(3000);
				LOGGER.info("Filter option is deleted as FilterOption is not available");
				LOGGER.info("FilterCriteriaOption is not present in Filter Criteria");
			}
		}catch (Exception e){
			LOGGER.error("Exception occurred in selectFilterOptionForAnalyticsPage : "+ e.getMessage());
		}
	}

	/**
	 * This method selects the sub-category for show hide test.
	 * 
	 * @param subcategory
	 * @throws Exception
	 */
	public final void selectSubCategoryForAnalyticsPageShowHide(String subcategory, String languageCode) throws Exception {
		try {
			clickOnElementsOfAnalyticsPage("subcategoryDropdown");
			List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("subCategoryList");
			for (WebElement we : element) {
				if (we.getText().equalsIgnoreCase(subcategory)) {
					we.click();
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectSubCategoryForAnalyticsPage : " + e.getMessage());
		}
	}

	/**
	 * This method selects the option for generating the report.
	 * 
	 * @param options
	 * @throws Exception
	 */
	public final void selectOptionForAnalyticsPage(String options, String languageCode) throws Exception {
		try {
			if (getTextOfAnalyticsPage("optionDropdown").equalsIgnoreCase(
					getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON", "select_option_text"))
					|| !(getTextOfAnalyticsPage("optionDropdown").equalsIgnoreCase(options))) {
				clickOnElementsOfAnalyticsPage("optionDropdown");
				List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("optionList");
				for (WebElement we : element) {
					scrollTillView(we);
					if (we.getText().equalsIgnoreCase(options)) {
						we.click();
						break;
					}
				}
			} else {
				LOGGER.info("Report option is selected by default");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectOptionForAnalyticsPage : " + e.getMessage());
		}
	}
	
	/**
	 * This method selects the option for show hide test.
	 * 
	 * @param options
	 * @throws Exception
	 */
	public final void selectOptionForAnalyticsPageShowHide(String options, String languageCode) throws Exception {
		try {
			scrollTillViewAnalyticsPage("subcategoryDropdown");
			clickOnElementsOfAnalyticsPage("optionDropdown");
			List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("optionList");
			for (WebElement we : element) {
				scrollTillView(we);
				if (we.getText().equalsIgnoreCase(options)) {
					we.click();
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectOptionForAnalyticsPageShowHide : " + e.getMessage());
		}
	}

	/**
	 * This method creates the report with specified parameters..
	 * 
	 * @param Category
	 * @param subcategory
	 * @param options
	 * @param reportName
	 * @return
	 * @throws Exception
	 */
	public final ArrayList<String> createReport(String Category, String subcategory, String options, String reportName,
			String reportcompanyvalue, String languageCode) throws Exception {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportNameLatest = reportName + timeStamp;
		ArrayList<String> reportParameters = new ArrayList<String>();
		// Getting logged in username
		try {
			verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
			if (verifyElementsOfAnalyticsPage("userMenuButtonWithoutImage")) {
				clickOnElementsOfAnalyticsPage("userMenuButtonWithoutImage");
			} else {
				clickOnElementsOfAnalyticsPage("userMenuButtonWithImage");
			}
			String createdByValue = getTextOfAnalyticsPage("loggedUserNew");
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				clickOnElementsOfAnalyticsPage("browseTab");
			}
			clickOnElementsOfAnalyticsPage("createButton");
			// Select Category
			sleeper(5000);
			selectCategoryForAnalyticsPage(Category);
			// Select Sub - Category
			selectSubCategoryForAnalyticsPage(subcategory, languageCode);
			// Select Options
			sleeper(1000);
			selectOptionForAnalyticsPage(options, languageCode);
			// Entering report name
			clickOnElementsOfAnalyticsPage("reportName");
			if (getAttributesOfAnalyticsPage("reportName", "value").isEmpty())
				enterTextForAnalyticsPage("reportName", reportNameLatest);
			else
				reportNameLatest = getAttributesOfAnalyticsPage("reportName", "value");
			// Getting value for company name
			String companyValue = reportcompanyvalue;
			// Adding all the values to the arraylist
			String categoryValue = getTextOfAnalyticsPage("categoryDropdown");
			reportParameters.add(categoryValue);
			String subCategoryValue = getTextOfAnalyticsPage("subcategoryDropdown");
			reportParameters.add(subCategoryValue);
			String optionsValue = getTextOfAnalyticsPage("optionDropdown");
			reportParameters.add(optionsValue);
			reportParameters.add(companyValue);
			reportParameters.add(createdByValue);
			reportParameters.add(reportNameLatest);
			// Removing the filters if any
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			sleeper(3000);
			if (!getDriver().findElements(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
					.isEmpty()) {
				do {
					scrollTillViewAnalyticsPage("optionDropdown");
					getDriver().findElement(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
							.click();
				} while (getDriver()
						.findElements(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
						.size() != 0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred in creating Report : " + e.getMessage());
		}
		return reportParameters;
	}

/**
 * This method is used to verifing filter value
 * @param Category: category name
 * @param subcategory: subcategory name
 * @param options:  Option value
 * @param languageCode: language value
 * @return: return value true or false
 * @throws Exception
 */
	public final boolean verifyWebReportFilter(String languageCode) throws Exception {
		boolean flag = false;
		if (getTextOfAnalyticsPage("chatTypeFilter")
				.equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", "Global.label.charttype.bar"))) {
			LOGGER.info("Bydefault chart type value is : Bar ");
			scrollTillViewAnalyticsPage("optionDropdown");
			clickOnElementsOfAnalyticsPage("chartTypeButton");
			if (getTextOfAnalyticsPage("chartTypeTree").equalsIgnoreCase(
					getTextLanguage(languageCode, "daas_reports_ui", "Global.label.charttype.treemap"))) {
				flag = true;
				LOGGER.info("Tree Map is also available in chart type filter");
			}
			//To click on blank area
			clickOnElementsOfAnalyticsPage("blankArea");
		} else {
			LOGGER.info("Bydefault chart type value is not Bar");
		}
		return flag;
	}
	
	/**
	 * This method creates the report with mandatory filters and also selecting
	 * filter values
	 * 
	 * @return boolean value
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public final boolean verifyMandateFilter(String optionListofFilterValue) throws InterruptedException, Exception {
		int filters = getElementsTillAllElementsPresentAnalyticsPage("filtercriteria").size();
		scrollTillViewAnalyticsPage("optionDropdown");
		while (filters > 0) {
			if (verifyElementIsVisibleOfAnalyticsPage("mandateFilter")) {
				if (verifyElementIsVisibleOfAnalyticsPage("selectOptionDisabled")) {
					// Data not present to create report.
					return false;
				} else {
					clickOnElementsOfAnalyticsPage("selectOption");
					sleeper(2000);
					List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage(optionListofFilterValue);
					element.get(0).click();
					if (uiVersion.equalsIgnoreCase("VENEER2")) {
						clickOnElementsOfAnalyticsPage("selectFilterValueButton");
					}
					else if (uiVersion.equalsIgnoreCase("VENEER3")) {
						//To click on blank area
						clickOnElementsOfAnalyticsPage("blankArea");
					}
				}
			}
			filters--;
		}
		return true;
	}	

	/**
	 * This method is used to verifing filter value
	 * 
	 * @param Category:     category name
	 * @param subcategory:  subcategory name
	 * @param options:      Option value
	 * @param languageCode: language value
	 * @return: return value true or false
	 * @throws Exception
	 */
	public final boolean verifyDateDimensionFilter(String Category, String subcategory, String options,
			String languageCode) throws Exception {
		boolean flag = false;
		// Select Category
		sleeper(3000);
		selectCategoryForAnalyticsPage(Category);
		// Select Sub - Category
		selectSubCategoryForAnalyticsPage(subcategory, languageCode);
		// Select Options
		sleeper(1000);
		selectOptionForAnalyticsPage(options, languageCode);
		LOGGER.info("Time Aggregation filter value check for " + Category + "->" + subcategory + "->" + options);
		waitUntilElementIsVisibleOfAnalyticsPage("spinner");
		scrollTillViewAnalyticsPage("optionDropdown");
		if (getTextOfAnalyticsPage("datedimensionfilter")
				.equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", "Global.label.No"))) {
			LOGGER.info("By default Time Aggregation value is : No ");
			clickOnElementsOfAnalyticsPage("datedimestionfilterbutton");
			if (getTextOfAnalyticsPage("datedimensionYes")
					.equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui", "Global.label.Yes"))) {
				flag = true;
				LOGGER.info("Time Aggregation option is available in filter : Yes");
			}
			// To click on blank area
			clickOnElementsOfAnalyticsPage("blankArea");
		}
		return flag;
	}

	/**
	 * This function verify graph type 
	 * @param graphtype:graph type value
	 * @param languageCode:language value
	 * @return:return true or false value
	 * @throws Exception
	 */
	public final boolean verifyGraphType(String graphtype, String languageCode) throws Exception {
		boolean flag = false;
		if (getTextOfAnalyticsPage("barGraph").equalsIgnoreCase(getTextLanguage(languageCode, "daas_reports_ui",
				"graphHeaders.Web_Application.Utilization_by_site_visits.title"))) {
			LOGGER.info("Tree Map report is available ");
			flag = true;
		} else {
			LOGGER.info("This is not a Bar report");
		}
		return flag;
	}
	/**
	 * This method creates the report with Filter Criteria specified within parameters..
	 *
	 * @param Category
	 * @param subcategory
	 * @param options
	 * @param filterCriteria
	 * @param reportName
	 * @return
	 * @throws Exception
	 */
	public final ArrayList<String> createReportWithFilterCriteria(String Category, String subcategory, String options, String filterCriteria ,String reportName, String reportcompanyvalue, String languageCode) throws Exception {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportNameLatest = reportName + timeStamp;
		ArrayList<String> reportParameters = new ArrayList<String>();
		// Getting logged in username
		try {
			verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
			if(verifyElementsOfAnalyticsPage("userMenuButtonWithImage")) {
				clickOnElementsOfAnalyticsPage("userMenuButtonWithImage");
			}else {
				clickOnElementsOfAnalyticsPage("userMenuButtonWithoutImage");
			}
			String createdByValue = getTextOfAnalyticsPage("loggedUserNew");
			clickOnElementsOfAnalyticsPage("createButton");
			// Select Category
			sleeper(5000);
			selectCategoryForAnalyticsPage(Category);
			// Select Sub - Category
			selectSubCategoryForAnalyticsPage(subcategory, languageCode);
			// Select Options
			sleeper(1000);
			selectOptionForAnalyticsPage(options, languageCode);
			// Entering report name
			clickOnElementsOfAnalyticsPage("reportName");
			if(getAttributesOfAnalyticsPage("reportName", "value").isEmpty())
				enterTextForAnalyticsPage("reportName", reportNameLatest);
			else
				reportNameLatest=getAttributesOfAnalyticsPage("reportName", "value");
			// Getting value for company name
			String companyValue = reportcompanyvalue;
			// Adding all the values to the arraylist
			String categoryValue = getTextOfAnalyticsPage("categoryDropdown");
			reportParameters.add(categoryValue);
			String subCategoryValue = getTextOfAnalyticsPage("subcategoryDropdown");
			reportParameters.add(subCategoryValue);
			String optionsValue = getTextOfAnalyticsPage("optionDropdown");
			reportParameters.add(optionsValue);
			reportParameters.add(companyValue);
			reportParameters.add(createdByValue);
			reportParameters.add(reportNameLatest);
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			// sleeper(8000);
			selectFilterCriteriaForAnalyticsPage(filterCriteria);
		} catch (Exception e) {
			LOGGER.error("Exception occurred in creating Report : " + e.getMessage());
		}
		return reportParameters;
	}
	

	/**
	 * This method checks child company list on report page
	 * 
	 * @param Category-Category of report to create
	 * @param subcategory-Sub Category of report to create
	 * @param options- Detailed/Summary report to create
	 * @param reportName-Name of the report
	 * @param languageCode-Language code
	 * @return
	 * @throws Exception
	 */
	public final boolean checkChildCompaniesOnAnalyticsPage(String Category, String subcategory, String options, String reportName, String languageCode){

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportNameLatest = reportName + timeStamp;
		// Getting logged in username
		try {
			verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
			clickOnElementsOfAnalyticsPage("userMenuButtonWorkflowNew");
			clickOnElementsOfAnalyticsPage("createButton");
			// Select Category
			sleeper(5000);//Needed for drop downs to become accessible
			selectCategoryForAnalyticsPage(Category);
			// Select Sub - Category
			selectSubCategoryForAnalyticsPage(subcategory, languageCode);
			// Select Options
			sleeper(1000);//Needed for drop downs to become accessible
			selectOptionForAnalyticsPage(options, languageCode);
			// Entering report name
			clickOnElementsOfAnalyticsPage("reportName");
			enterTextForAnalyticsPage("reportName", reportNameLatest);
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			clickOnElementsOfAnalyticsPage("addFilterCriteria");
			waitForElementsOfAnalyticsPage("filterFieldForCompanyPreference");
			clickOnElementsOfAnalyticsPage("filterFieldForCompanyPreference");
			selectTextValueFromDropdownWorkflow(analyticsPagePropertiesPageProperties.getProperty("filterFieldOptionList"), "Company Preference Key", analyticsPagePropertiesPageProperties.getProperty("filterFieldForCompanyPreference"));
			sleeper(1000);
			clickOnElementsOfAnalyticsPage("filterFieldForCompanyPreferenceValue");
			sleeper(1000);
		} catch (Exception e) {
			LOGGER.error("Exception occured in checkChildCompaniesOnAnalyticsPage : " + e.getMessage());
		}
		return true;
	}
	
	/**
	 * Method to verify child company from MIZE API to dashboard for workflow
	 * @param response: locator of chart titles
	 * @return boolean
	 */
	public boolean verifyChildCompanyInResponseFromMizeAPI(List<String> response) {
		boolean chartCheckLabel=true;
		try {
			List<WebElement> listOfCompaniesOptions = getElementsOfAnalyticsPage("filterFieldValueOptionList");
			ArrayList<String> listOfCompanies=new ArrayList<String>();
			ArrayList<String> listOfCompaniesInApiResponse=new ArrayList<String>();
			for(int listCounter=0;listCounter<listOfCompaniesOptions.size();listCounter++) {
				listOfCompanies.add(listOfCompaniesOptions.get(listCounter).getText().trim());
			}
			for(int listCounter=0;listCounter<response.size();listCounter++) {
				listOfCompaniesInApiResponse.add(response.get(listCounter));
			}
			Collections.sort(listOfCompanies);
			Collections.sort(listOfCompaniesInApiResponse);
			chartCheckLabel=listOfCompanies.equals(listOfCompaniesInApiResponse);
			
		} catch (Exception e) {
			LOGGER.error("Exception occured in orderOfCharts method "+e.getMessage());
		}
		return chartCheckLabel;
	}


	/**
	 * This method verify the values on HTML report for category , subcategory, options, created by, company name.
	 * 
	 * @param reportGenerateValues
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyRunReportAndData(ArrayList<String> reportGenerateValues, String LanguageCode) throws Exception {
		try {
			String categoryKey = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "category");
			String subcategoryKey = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "subcategory");
			String optionKey = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "report_option");
			String createdByKey = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "report_created_by");

			clickOnElementsOfAnalyticsPage("reportMoreDetails");
			LOGGER.info("Clicked on More Details");
			sleeper(3000);
			String categoryValueRunReport = getDriver().findElement(By.xpath("//div[contains(text(),'" + categoryKey + "')]/following-sibling::div")).getText();
			String subCategoryValueRunReport = getDriver().findElement(By.xpath("//div[contains(text(),'" + subcategoryKey + "')]/following-sibling::div")).getText();
			String optionValueRunReport = getDriver().findElement(By.xpath("//div[contains(text(),'" + optionKey + "')]/following-sibling::div")).getText();
			String createdByValueRunReport = getDriver().findElement(By.xpath("//div[contains(text(),'" + createdByKey + "')]/following-sibling::div")).getText();
			if (((reportGenerateValues.get(0)).equalsIgnoreCase(categoryValueRunReport)) && ((reportGenerateValues.get(1)).equalsIgnoreCase(subCategoryValueRunReport)) && ((reportGenerateValues.get(2)).equalsIgnoreCase(optionValueRunReport)) && ((reportGenerateValues.get(4)).equalsIgnoreCase(createdByValueRunReport))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("EXCEPTION OCCURED IN MATCHING THE VALUES ON HTML REPORT OR RUNNING REPORT : " + e.getMessage());
			return false;		
		}
	}
	
	/**
	 * This method verify the values on HTML report for category , subcategory, options, created by, company name. for workflow child company
	 * 
	 * @param reportGenerateValues
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyRunReportAndDataChildCompany(ArrayList<String> reportGenerateValues, String LanguageCode) throws Exception {
		int windowHandles = 0;
		try {
			clickOnElementsOfAnalyticsPage("reportMoreDetails");
			sleeper(3000);
			String categoryValueRunReport = getTextOfAnalyticsPage("categoryOnReportDetails");
			String subCategoryValueRunReport = getTextOfAnalyticsPage("subcategoryOnReportDetails");
			String optionValueRunReport = getTextOfAnalyticsPage("optionOnReportDetails");
			String createdByValueRunReport = getTextOfAnalyticsPage("createdByOnReportDetails");
			String companyPreferenceValueRunReport = getTextOfAnalyticsPage("companyReferenceKeyReportDetails");
			if (((reportGenerateValues.get(0)).equalsIgnoreCase(categoryValueRunReport)) && ((reportGenerateValues.get(1)).equalsIgnoreCase(subCategoryValueRunReport)) && ((reportGenerateValues.get(2)).equalsIgnoreCase(optionValueRunReport)) && ((reportGenerateValues.get(4)).equalsIgnoreCase(createdByValueRunReport)) && (companyPreferenceValueRunReport.contains(reportGenerateValues.get(6).toUpperCase()))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("EXCEPTION OCCURED IN MATCHING THE VALUES ON HTML REPORT OR RUNNING REPORT : " + e.getMessage());
			windowHandles = getWindowHandlesofAnalyticsPage();
			LOGGER.info("Window handles : " + windowHandles);
			if (windowHandles > 1) {
				LOGGER.info("Window handles > 1 ");
				switchBackToPreviousTab();
				LOGGER.info("after switch back previous window");
				return false;
			} else {
				return false;
			}
		}
	}

	/**
	 * This method read reportData.properties file and return tab's name and details.
	 * 
	 * @param reportTabs
	 * @return
	 * @throws Exception
	 */
	public String[][] getReportTabDetails(String[] reportTabs) throws Exception {
		String[][] tabDetails = new String[2][7];
		String[] temp;
		try {
			for (int i = 0; i < reportTabs.length; i++) {
				temp = analyticsDataProperties.getProperty(reportTabs[i]).split(":");
				tabDetails[0][i] = (String) temp[0];
				tabDetails[1][i] = (String) temp[1];
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in getting values of tab and their attributes : " + e.getMessage());
			return tabDetails;
		}
		return tabDetails;
	}

	/**
	 * This method is for complete report tab and data validations.
	 * 
	 * @param tabNameDetails
	 * @param columnNameKeys
	 * @param LanguageCode
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public boolean reportDataValidation(String[][] tabNameDetails, String[] columnNameKeys, String LanguageCode,
			String folderName) throws Exception {
		boolean reportStatus = false;
		boolean reportStatusGraphBromium = false;
		boolean reportStatusGraph = true;
		boolean reportStatusGridBromium = false;
		boolean reportStatusColumnBromium = false;
		boolean reportStatusGrid = true;
		boolean reportStatusColumn = true;
		String reportTabName;
		String[] columnNamekey;
		ArrayList<String> tabName = new ArrayList<String>();
		ArrayList<String> tabDetails = new ArrayList<String>();
		int index;
		try {
			LOGGER.info("inside reportDataValidation");
			for (index = 0; tabNameDetails[0][index] != null; index++) {
				tabName.add(tabNameDetails[0][index]);
				tabDetails.add(tabNameDetails[1][index]);
			}
			scrollTillViewAnalyticsPage("repprtTabslist");
			List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("repprtTabslist");
			for (index = 0; index < tabName.size(); index++) {
				for (WebElement tab : element) {
					if (tab.getText()
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", tabName.get(index)))) {
						tab.click();
						reportTabName = getTextLanguage(LanguageCode, "daas_reports_ui", tabName.get(index));
						for (int i = 0; i <= (columnNameKeys.length) - 1; i++) {
							columnNamekey = (columnNameKeys[i]).split(",");
							if ((tabDetails.get(index).equalsIgnoreCase("GRAPH"))
									&& folderName.equalsIgnoreCase("MPI-Reporting-LHreports_service")) {
								i++;
								reportStatusGraphBromium = validateGraphData_bromium(reportTabName);
								LOGGER.info(reportStatusGraphBromium);
							} else if ((tabDetails.get(index).equalsIgnoreCase("GRAPH"))
									&& folderName != ("MPI-Reporting-LHreports_service")) {
								reportStatusGraph = validateGraphData(reportTabName);
								LOGGER.info(reportStatusGraph);
							} else if (tabDetails.get(index).equalsIgnoreCase("GRID")
									&& folderName.equalsIgnoreCase("MPI-Reporting-LHreports_service")) {
								reportStatusGridBromium = validateGridData_bromium(reportTabName, LanguageCode);
								LOGGER.info(reportStatusGridBromium);
								reportStatusColumnBromium = validateColumnNames_bromium(columnNamekey, LanguageCode,
										folderName, reportTabName);
								LOGGER.info(reportStatusColumnBromium);
							} else if ((tabDetails.get(index).equalsIgnoreCase("GRID"))
									&& folderName != ("MPI-Reporting-LHreports_service")) {
								sleeper(3000);
								reportStatusGrid = validateGridData(reportTabName);
								LOGGER.info(reportStatusGrid);
								reportStatusColumn = validateColumnNames(columnNamekey, LanguageCode, folderName,
										reportTabName);
								LOGGER.info(reportStatusColumn);
							}
						}
					}
				}
			}
			if (folderName.equalsIgnoreCase("MPI-Reporting-LHreports_service")) {
				if (reportStatusGraphBromium && reportStatusGridBromium && reportStatusColumnBromium) {
					reportStatus = true;
				}
			} else {
				if (reportStatusGraph && reportStatusGrid && reportStatusColumn) {
					reportStatus = true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating grid and graph : " + e.getMessage());
			LOGGER.info("Exception in reportDataValidation");
			return reportStatus;
		}
		LOGGER.info("Final Report Status is : " + reportStatus);
		return reportStatus;
	}
	
	/**
	 * This method is for validating the Grid(table) data present in the reports.
	 * 
	 * @param reportTabName
	 * @return
	 * @throws Exception
	 */
	public boolean validateGridData(String reportTabName) throws Exception {
		try {
			if (uiVersion.equalsIgnoreCase("VENEER2")
					&& getDriver().findElements(By.xpath("//div[@id='" + reportTabName + "']//tr/td")).size() >= 3) {
				LOGGER.info("Data is present in the Grid under tab -" + reportTabName);
				return true;
			} else if (uiVersion.equalsIgnoreCase("VENEER3") && getDriver()
					.findElements(By.cssSelector("div[id='" + reportTabName + "']  td div[title]")).size() >= 1) {
				LOGGER.info("Data is present in the Grid under tab -" + reportTabName);
				return true;
			} else if (uiVersion.equalsIgnoreCase("VENEER2") && getDriver()
					.findElement(
							By.xpath("//div[@id='" + reportTabName + "']//td[contains(@class,'no-items--scrollable')]"))
					.isDisplayed()) {
				LOGGER.info("Data is not present in the Grid under tab -" + reportTabName);
				return true;
			} else if (uiVersion.equalsIgnoreCase("VENEER3") && getDriver()
					.findElement(
							By.xpath("//div[@id='" + reportTabName + "']//div[@role = 'img']//following-sibling::span"))
					.isDisplayed()) {
				LOGGER.info("Data is not present in the Grid under tab -" + reportTabName);
				return true;

			} else {
				LOGGER.info("Grid tab is not loaded due to some error");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating grid : " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is for validating the Grid(table) data present in the bromium reports.
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean validateGridData_bromium(String TabName, String LanguageCode) throws Exception {
		try {

			if(TabName.equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON","details"))) {

				if (getElementsTillAllElementsVisibleAnalyticsPage("gridElementsListBromium").size() > 1 ) {
					LOGGER.info("Data is present in the Grid");
					return true;
				} else if (verifyElementIsVisibleOfAnalyticsPage("gridNoDataBromium")) {
					LOGGER.info("Data is not present in the Grid");
					return true;
				} else {
					LOGGER.error("Grid tab is not loaded due to some error");
					return false;
				}

			}	else {				    
				if (getDriver().findElements(By.xpath("//div[@id='" + TabName + "']//tr[contains(@class,'tablerow')]")).size() >= 1 ) {
					LOGGER.info("Data is present in the Grid");
					return true;
				} else if (getDriver().findElements(By.xpath("//div[@id='" + TabName + "']//td[contains(@class,'no-items')]")).size() > 0) {
					LOGGER.info("Data is not present in the Grid");
					return true;
				} else {
					LOGGER.error("Grid tab is not loaded due to some error");
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating grid : " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is for validating the Graph(charts) data present in the reports.
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean validateGraphData(String reportTabName) throws Exception {
		try {
			if (!(verifyElementIsVisibleOfAnalyticsPage("chartsNoData"))) {
				LOGGER.info("Data is present in the Graph under tab - "+reportTabName);
				return true;
			} else if (verifyElementIsVisibleOfAnalyticsPage("chartsNoData")) {
				LOGGER.info("Data is not present in the Graph under tab - "+reportTabName);
				return true;
			} else {
				LOGGER.info("Data is not loaded due to some error in the graph under tab - "+reportTabName);
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating graph : " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is for validating the Graph(charts) data present in the bromium reports.
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean validateGraphData_bromium(String reportTabName) throws Exception {
		try {
			if (!(verifyElementIsVisibleOfAnalyticsPage("chartsNoData"))) {
				LOGGER.info("Data is present in the Graph under tab - "+reportTabName);
				return true;
			} else if (verifyElementIsVisibleOfAnalyticsPage("chartsNoData")) {
				LOGGER.info("Data is not present in the Graph under tab - "+reportTabName);
				return true;
			}else {
				LOGGER.info("Data is not loaded due to some error in the graph under tab - "+reportTabName);
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating graph : " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is for validating the column names of the tables generated in the
	 * reports.
	 * 
	 * @param columnNameKey
	 * @param LanguageCode
	 * @param folderName
	 * @param reportTabName
	 * @return
	 * @throws Exception
	 */
	public boolean validateColumnNames(String[] columnNameKey, String LanguageCode, String folderName,
			String reportTabName) throws Exception {
		try {
			String[] expectedColumnNameList;

			for (int i = 0; i <= (columnNameKey.length) - 1; i++) {
				expectedColumnNameList = analyticsTabTitleProperties.getProperty(columnNameKey[i]).split(":");
				List<WebElement> uiReportColumnList = null;
				if (uiVersion.equalsIgnoreCase("VENEER3")) {
					uiReportColumnList = getDriver()
							.findElements(By.cssSelector("div[id='" + reportTabName + "'] th[id]"));				
				} else if (uiVersion.equalsIgnoreCase("VENEER2")) {
					uiReportColumnList = getDriver().findElements(By.xpath("//div[@id='" + reportTabName
							+ "']//tr[contains(@class,'tableheader__row')]//th//div//div"));
				}

				if (expectedColumnNameList.length != uiReportColumnList.size()) {
					LOGGER.info(
							"Tab-" + reportTabName + " Total no. of columns not matching with the expected, Expected-"
									+ expectedColumnNameList.length + " Actual-" + uiReportColumnList.size());
					return false;
				}

				ArrayList<String> columnNames = getTextLanguage(LanguageCode, folderName, expectedColumnNameList);
				for (int counter = 0; counter < expectedColumnNameList.length; counter++) {
					scrollTillView(uiReportColumnList.get(counter));
					String columnNameUi = uiReportColumnList.get(counter).getText();
					//columnNameUi = columnNameUi.replaceAll("\n[ \t]+", "");
					columnNameUi = columnNameUi.split("\n")[0];
					if (columnNameUi.equalsIgnoreCase(columnNames.get(counter))) {
					} else {
						LOGGER.info("Mismatch in column titles - Expected-" + columnNames.get(counter) + " Actual -"
								+ columnNameUi);
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception occured in validating column names : " + e.getMessage());
			LOGGER.info("Column titles are not successfully verified");
			return false;
		}
		LOGGER.info("Column titles are successfully verified of grid-" + reportTabName);
		return true;
	}

	/**
	 * This method is for validating the column names of the tables generated in the bromium reports.
	 * 
	 * @param columnNameKey
	 * @param LanguageCode
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public boolean validateColumnNames_bromium(String[] columnNameKey, String LanguageCode, String folderName, String tabName) throws Exception {
		String[] columnNameList;

		ArrayList<String> uiColumnName = new ArrayList<String>();
		int counter = 0;
		List<WebElement> element = null;
		try {
			for (int i = 0; i <= (columnNameKey.length) - 1; i++) {				
				columnNameList = analyticsTabTitleProperties.getProperty(columnNameKey[i]).split(":");															
				if(tabName.equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON","details"))) {					
					element = getElementsTillAllElementsPresentAnalyticsPage("gridTitleListBromium");	
				}else {
		element = getDriver().findElements(By.xpath("//div[@id='" + tabName + "']//tr[contains(@class,'tableheader__row')]//th//div//div"));											
                    getDriver().findElement(By.xpath("//div[@role='tablist']//a[contains(text(),'" + tabName + "')]")).click();
				}
				ArrayList<String> columnName = new ArrayList<String>();
				if (columnNameList.length != element.size()) {
					LOGGER.error("Number of grid column names does not match with the element size of columns");
					return false;
				}
				for (counter = 0; counter < columnNameList.length; counter++) {
					columnName.add(getTextLanguage(LanguageCode, folderName, columnNameList[counter]));
					uiColumnName.add(element.get(counter).getText());
					if (uiColumnName.get(counter).equalsIgnoreCase(columnName.get(counter))) {
					} else {
						LOGGER.error("Mismatch in column titles" + uiColumnName.get(counter) + columnName.get(counter));
						return false;
					}					
				}
				uiColumnName.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception occured in validating column names : " + e.getMessage());
			LOGGER.error("Column titles are not successfully verified");
			return false;
		}
		LOGGER.info("Column titles are successfully verified");
		return true;
	}	

	/**
	 * This method deletes the report created.
	 * 
	 * @param reportname
	 * @return boolean
	 */
	public boolean deleteRecentReport(String reportname) {
		int windowHandles = 0;
		try {
			windowHandles = getWindowHandlesofAnalyticsPage();
			LOGGER.info("Window handles : " + windowHandles);
			if (windowHandles > 1) {
				switchBackToPreviousTab();
				waitForPageLoaded();
				if (uiVersion.equalsIgnoreCase("VENEER3")) {
					String reportName = "[css]:#report-list-tab-browse span>div[title='" + reportname + "']";
					mouseHoverForAnalyticsPage(reportName);
					getDriver().findElement(By.xpath("//*[@id='report-list-tab-browse']//td//*[name()='span']/div[@title='"
							+ reportname + "']/ancestor::td/following-sibling::td/button"))
							.click();
				} else if (uiVersion.equalsIgnoreCase("VENEER2")) {
					getDriver().findElement(By.xpath(".//a[contains(text(),'" + reportname + "')]/ancestor::div[contains(@class,'list-item__content--middle')]/following-sibling::div/div/span/div"))
							.click();
				}
				clickOnElementsOfAnalyticsPage("deleteButton");
				verifyElementIsVisibleOfAnalyticsPage("okButtonPopup");
				clickOnElementsOfAnalyticsPage("okButtonPopup");
				waitForPageLoaded();
				verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
				LOGGER.info("Report deleted successfully");
			} else {
				waitForPageLoaded();
				if (uiVersion.equalsIgnoreCase("VENEER3")) {
					String reportName = "[css]:#report-list-tab-browse span>div[title='" + reportname + "']";
					mouseHoverForAnalyticsPage(reportName);
					getDriver().findElement(By.xpath("//*[@id='report-list-tab-browse']//td//*[name()='span']/div[@title='"
							+ reportname + "']/ancestor::td/following-sibling::td/button"))
							.click();
				} else if (uiVersion.equalsIgnoreCase("VENEER2")) {
					getDriver().findElement(By.xpath(".//a[contains(text(),'" + reportname
							+ "')]/ancestor::div[contains(@class,'list-item__content--middle')]/following-sibling::div/div/span/div"))
							.click();
				}
				clickOnElementsOfAnalyticsPage("deleteButton");
				verifyElementIsVisibleOfAnalyticsPage("okButtonPopup");
				clickOnElementsOfAnalyticsPage("okButtonPopup");
				verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
				LOGGER.info("Report deleted successfully");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in deleting the report" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * This method calculates the time taken by reports to load.
	 * 
	 * @param reportGenerateValues
	 * @return
	 * @throws Exception
	 */
	public boolean calculateLoadTimeForReports(ArrayList<String> reportGenerateValues) throws Exception {
		try {
			// Save Report
			boolean isClickable = verifyIfElementIsClickableOfAnalyticsPage("saveButton");
			if (isClickable == false) {
				throw new NoSuchElementException();
			} else {
				clickOnElementsOfAnalyticsPage("saveButton");
				verifyElementsOfAnalyticsPage("customReportsCreatedList");
				LOGGER.info("Report saved successfully");
			}
			// Run Report from list Page.
			scrollToTop();
			if (uiVersion.equalsIgnoreCase("VENEER2")) {
				String reportToRun = "[xpath]:.//a[contains(text(),'" + reportGenerateValues.get(5)
						+ "')]/ancestor::div[contains(@class,'list-item__content--middle')]/following-sibling::div/div/button";
				mouseHoverForAnalyticsPage(reportToRun);
				getDriver().findElement(By.xpath(".//a[contains(text(),'" + reportGenerateValues.get(5)
						+ "')]/ancestor::div[contains(@class,'list-item__content--middle')]/following-sibling::div/div/button"))
						.click();
			} else {
				// VENEER3 run report
				// To click on newly created report
				if (!sortLastAccessColumnInDescOrder(reportGenerateValues.get(5))) {
					// Failed to run report
					return false;
				}
			}
			long start = System.currentTimeMillis();
			LOGGER.info("Time at the start of report loading - " + start + " " + "secs");
			switchToDifferentTab();
			sleeper(1000);
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			waitForPageLoaded();
			long finish = System.currentTimeMillis();
			LOGGER.info("Time at which report loaded completely - " + finish + " " + "secs");
			long totalTime = (int) (((finish - start) / 1000) - 1);
			LOGGER.info("Total Time taken for reports to load - " + totalTime + " " + "secs");
		} catch (NoSuchElementException e) {
			scrollDownPage();
			LOGGER.error("Exception occured in saving report, Save button is disabled");
			return false;

		} catch (Exception e) {
			LOGGER.error("EXCEPTION OCCURED IN LOADING REPORT : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * This method helps to click on newly created report by sorting Last Access
	 * column in DESC order
	 * 
	 * @param reportName - Newly created report name
	 * @return boolean value
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public final boolean sortLastAccessColumnInDescOrder(String reportName) throws InterruptedException, Exception {

		boolean flag = false;
		try {

			// 1st condition
			if (getTextOfAnalyticsPage("reportNameOnTop").equalsIgnoreCase(reportName)) {
				clickOnElementsOfAnalyticsPage("reportNameOnTop");
				flag = true;
				return flag;
			} else {
				// 2nd condition ------Sort Last Access column in DESC order
				if (verifyElementsOfAnalyticsPage("lastAccess")) {
					//Sort the column
					clickOnElementsOfAnalyticsPage("lastAccess");
					sleeper(2000);
					//Verify if first report matches or not
					if (getTextOfAnalyticsPage("reportNameOnTop").equalsIgnoreCase(reportName)) {
						clickOnElementsOfAnalyticsPage("reportNameOnTop");
						flag = true;
						return flag;
						//3rd condition ------>again sort the column and match the text
					} else {
						clickOnElementsOfAnalyticsPage("lastAccess");
						sleeper(2000);
						//Verify if first report matches or not
						if (getTextOfAnalyticsPage("reportNameOnTop").equalsIgnoreCase(reportName)) {
							clickOnElementsOfAnalyticsPage("reportNameOnTop");
							flag = true;
							return flag;
						}
					}
				} else {
					// When 'Last Access' column not present
					clickOnElementsOfAnalyticsPage("tableConfig");
					clickOnElementsOfAnalyticsPage("resetButton");
					clickOnElementsOfAnalyticsPage("saveColumns");
					clickOnElementsOfAnalyticsPage("lastAccess");
					sleeper(2000);
					if (getTextOfAnalyticsPage("reportNameOnTop").equalsIgnoreCase(reportName)) {
						clickOnElementsOfAnalyticsPage("reportNameOnTop");
						flag = true;
						return flag;
					} else {
						clickOnElementsOfAnalyticsPage("lastAccess");
						sleeper(2000);
						if (getTextOfAnalyticsPage("reportNameOnTop").equalsIgnoreCase(reportName)) {
							clickOnElementsOfAnalyticsPage("reportNameOnTop");
							flag = true;
							return flag;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception in method sortLastAccessColumnInDescOrder" + e.getMessage());
			return flag;
		}
		LOGGER.info("Failed to Run report");
		return flag;
	}

	/**
	 * Mouse Hover for reports
	 * @param locator
	 * @throws Exception
	 */
	public final void mouseHoverForAnalyticsPage(String locator) throws Exception {
		WebElement element = getDriver().findElement(this.getObject(locator));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		Actions action = new Actions(getDriver());
		action.moveToElement(element).perform();
	}	
	
	/**
	 * This method will generate business report
	 * 
	 * @param generateReportThrough: Specify the report format to be generated
	 */
	public void generateBussinessReport(String generateReportThrough) {
		try {
			clickOnElementsOfAnalyticsPage("startDateDropdownDashboard");
			List<WebElement> startDateElement = getElementsTillAllElementsPresentAnalyticsPage("startDateList");
			WebElement weFirst = startDateElement.get(0);
			weFirst.click();

			clickOnElementsOfAnalyticsPage("endDateDropdownDashboard");
			List<WebElement> endDateElement = getElementsTillAllElementsPresentAnalyticsPage("endDateList");
			WebElement weLast = endDateElement.get(endDateElement.size() - 1);
			weLast.click();
			
			clickOnElementsOfAnalyticsPage(generateReportThrough);
			clickOnElementsOfAnalyticsPage("generateReportButtonOnPopup");
		} catch (Exception e) {
			LOGGER.error("Exception occured in deleting the report" + e.getMessage());
		}
	}

	/**
	 * This method will verify the message for email generation of the report or no device enroll message
	 * 
	 * @param LanguageCode: This is language code used for multiple languages.
	 * @param actualReportMessage: Message which prompts on the UI after generating the report
	 * @return boolean value
	 */
	public boolean verifyReportMessage(String LanguageCode, String actualReportMessage) {
		try {
			if ((actualReportMessage.equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.business_review.export.error.company_not_found")) || (actualReportMessage.equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.business_review.export.sucess"))))) {
				LOGGER.info("Success message is verified");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifying the message" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * This is a method to get the text of a list
	 * 
	 * @param key
	 * @return List of text
	 * @throws Exception
	 */
	public final List<String> getTextOfListOnAnalyticsPage(String key) throws Exception {
		return getTextOfList(analyticsPagePropertiesPageProperties.getProperty(key));
	}

	/**
	 * @param LanguageCode -Language
	 * @param dropdownOptionKey - drop down option list key
	 * @param expectedOptionsList - expected drop down option
	 * @return true /false
	 * @throws Exception
	 */
	public final boolean verifyDropOptionListContainOnAnalyticsPage(String LanguageCode, String localeFolder, String dropdownOptionKey, List<String> expectedOptionsList) throws Exception {
		boolean checkFlag = false;
		List<String> dropdownOptionsText = getTextOfListOnAnalyticsPage(dropdownOptionKey);
		for (String expectedOption : expectedOptionsList) {
			for (String dropdownOptionText : dropdownOptionsText) {
				if (dropdownOptionText.contains(getTextLanguage(LanguageCode, localeFolder, expectedOption))) {
					checkFlag = true;
					break;
				} else {
					checkFlag = false;
				}
			}
		}
		return checkFlag;
	}

	/**
	 * @param languageCode - Language
	 * @param categorySubcategoryMap - HashMap contains Category and its respective subcategory of the reports
	 * @return boolean value
	 */
	public boolean verifyListOfReports(String languageCode, HashMap<String, List<String>> categorySubcategoryMap) {
		boolean reportFlag = false;
		String subCategoryList = null;
		try {
			clickOnElementsOfAnalyticsPage("createButton");
			sleeper(5000);
			for (Entry<String, List<String>> categorySubcategoryEntry : categorySubcategoryMap.entrySet()) {
				String reportCategory = categorySubcategoryEntry.getKey();
				List<String> reportSubcategory = categorySubcategoryEntry.getValue();
				// Select Category
				selectCategoryForAnalyticsPage(reportCategory);
				// Select SubCategory if not disabled
				if (verifyElementIsVisibleOfAnalyticsPage("subcategoryEnabledDropdown")) {
					clickOnElementsOfAnalyticsPage("subcategoryDropdown");
					subCategoryList = "subCategoryList";
				} else
					subCategoryList = "subcategoryDisabledLink";
				// Compare the reports listed under the Category
				if (verifyDropOptionListContainOnAnalyticsPage(languageCode, "MPI-Reporting-LHreports_service", subCategoryList, reportSubcategory)) {
					reportFlag = true;
					LOGGER.info("Category " + reportCategory + " verified successfully for Partner");
				} else {
					reportFlag = false;
					LOGGER.info("Category " + reportCategory + " failed to verify.");
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifying the reports " + e.getMessage());
		}
		return reportFlag;
	}

	/**
	 * This method verifies UI records after toggles on companies page
	 * 
	 * @param Expected records
	 */
	public final boolean verifyReportTypesDisplayedOnUI(List<String> expectedReportElements) {
		int listRecordAvailable = 0;
		List<WebElement> reportTypeList;
		try {
			

			if (expectedReportElements.size() == 4) {
				reportTypeList = getElementsTillAllElementsPresentAnalyticsPage("optionList");
				for (WebElement report : reportTypeList) {
					if (report.getText().equalsIgnoreCase(expectedReportElements.get(0))) {
						listRecordAvailable += 1;
						continue;
					} else if (report.getText().equalsIgnoreCase(expectedReportElements.get(1))) {
						listRecordAvailable += 1;
						continue;
					} else if (report.getText().equalsIgnoreCase(expectedReportElements.get(2))) {
						listRecordAvailable += 1;
						continue;
					} else if (report.getText().equalsIgnoreCase(expectedReportElements.get(3))) {
						listRecordAvailable += 1;
						continue;
					}
				}
			} else if (expectedReportElements.size() == 1) {
				reportTypeList = getElementsTillAllElementsPresentAnalyticsPage("subCategoryList");
				for (WebElement report : reportTypeList) {
					if (report.getText().equalsIgnoreCase(expectedReportElements.get(0))) {
						listRecordAvailable += 1;
						break;
					}
				}
			}

			if (listRecordAvailable == 4 && expectedReportElements.size() == 4)
				return true;
			else if (listRecordAvailable == 1 && expectedReportElements.size() == 1)
				return true;
			else
				return false;

		} catch (Exception e) {

			LOGGER.error("Exception occured in verifyReportTypesDisplayedOnUI : " + e.getMessage());
			return false;
		}
	}

	/**
	* This method basically verify the header Name on report page with Frame
	* 
	* @param LanguageCode : This is language code used for multiple languages.
	* @param columnHeaderNamesOnAnalyticsPage : Column headers on details page
	* @param leftLabelList : List of labels on details page on left side of more details section
	* @param rightLabelList : List of labels on details page on right side of more details section
	* @param reportNameKey : Key of report name on details page
	* @param labelListLeft : Key for list of labels on details page on left side of more details section
	* @param valueListLeft : Key for values on details page on left side of more details section
	* @param labelListRight : Key for list of labels on details page on right side of more details section
	* @param valueListRight : Key for values on details page on right side of more details section
	* @param filterCriteria : Key for filter criteria label
	* @param reportName : Key for report name on details page.
	* @return boolean value
	* @throws Exception
	*/

	public final boolean headerTextVerificationOnAnalyticsPage(String LanguageCode, String[] columnHeaderNamesOnAnalyticsPage, String[] leftLabelList, String[] rightLabelList, String reportNameKey, String labelListLeft, String valueListLeft, String labelListRight, String valueListRight, String filterCriteria, String reportName) throws Exception {
		switchToDifferentTab();
		waitForPageLoaded();
		waitUntilElementIsVisibleOfAnalyticsPage("spinner");
		boolean reportNamelabelFlag = true;
		boolean filterCriterialabelFlag = true;
		boolean leftSidelabelsFlag = true;
		boolean rightSidelabelsFlag = true;
		boolean leftSideValuesFlag = true;
		boolean rightSideValuesFlag = true;
		boolean columnHeadersFlag = true;
		clickByJavaScriptOnAnalyticsPage("reportMoreDetails");
		if(!getTextOfAnalyticsPage(reportNameKey).equalsIgnoreCase(reportName.trim())) {
			LOGGER.error("Report name "+ getTextOfAnalyticsPage(reportNameKey) + " not as expected "+reportName);
			reportNamelabelFlag= false;
		}
		if(!getTextOfAnalyticsPage(filterCriteria).equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "reports.add_report.report_criteria"))) {
			LOGGER.error("Filter Criteria label not as expected");
			filterCriterialabelFlag = false;
		}
		List<WebElement> listOfLeftLabels = getElementsOfAnalyticsPage(labelListLeft);
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfLeftLabels.size(); listOfOptionsCounter++) {
			if(!listOfLeftLabels.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", leftLabelList[listOfOptionsCounter]))) {
				LOGGER.error("Details' label on left not as expected");
				leftSidelabelsFlag= false;
			}
		}
		List<WebElement> listOfRightLabels = getElementsOfAnalyticsPage(labelListRight);
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfRightLabels.size(); listOfOptionsCounter++) {
			if(!listOfRightLabels.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", rightLabelList[listOfOptionsCounter]))) {
				LOGGER.error("Details' label on right not as expected");
				rightSidelabelsFlag = false;
			}
		}
		List<WebElement> listOfLeftValues = getElementsOfAnalyticsPage(valueListLeft);
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfLeftValues.size(); listOfOptionsCounter++) {
			if(listOfLeftValues.get(listOfOptionsCounter).getText().isEmpty()) {
				LOGGER.error("Details' label on left values not as expected");
				leftSideValuesFlag = false;
			}
		}
		List<WebElement> listOfRightValues = getElementsOfAnalyticsPage(valueListRight);
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfRightValues.size(); listOfOptionsCounter++) {
			if(listOfRightValues.get(listOfOptionsCounter).getText().isEmpty()) {
				LOGGER.error("Details' label on right values not as expected");
				rightSideValuesFlag = false;
			}
		}
		List<WebElement> listOfColumnHeaders = getElementsOfAnalyticsPage("columnHeaderList");
		for (int listOfOptionsCounter = 0; listOfOptionsCounter < listOfColumnHeaders.size(); listOfOptionsCounter++) {
			if(!listOfColumnHeaders.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", columnHeaderNamesOnAnalyticsPage[listOfOptionsCounter]))) {
				if(!listOfColumnHeaders.get(listOfOptionsCounter).getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", columnHeaderNamesOnAnalyticsPage[listOfOptionsCounter]))) {
				LOGGER.info("listOfColumnHeaders***"+listOfColumnHeaders.get(listOfOptionsCounter).getText());
				LOGGER.info("getTextLanguage***"+getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", columnHeaderNamesOnAnalyticsPage[listOfOptionsCounter]));
				LOGGER.error("Grid Table columns not as expected");
				columnHeadersFlag = false;
				}
			}
		}
		switchBackToPreviousTab();
		return (reportNamelabelFlag&&filterCriterialabelFlag&&leftSidelabelsFlag&&rightSidelabelsFlag&&leftSideValuesFlag&&rightSideValuesFlag&&columnHeadersFlag);
	}
	
	/**
	 * This method schedules the report with specified parameters..
	 * @param languageCode - Language code
	 * @param category - Category of report to be saved
	 * @param subcategory - subcategory of report to be saved
	 * @param options - options of report to be saved
	 * @param reportName - reportName of report to be saved
	 * @param recurrence - recurrence of report generation
	 * @param reportFormat - format of report to be scheduled
	 * @param recurrenceStringOnCustomTab - Scheduled time for the report
	 * @return String
	 * @throws Exception
	 */
	public final Object[][] scheduleReport(String languageCode, String category, String subcategory, String options, String reportName, String reportFormat, String recurrence, String recurrenceStringOnCustomTab) throws Exception {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportNameLatest = reportName + timeStamp;
		String day=null;String time="";
		Object[][] returnObj=new Object[1][2];
		returnObj[0][0]=true;
		try {
			waitForPageLoaded();
			waitForPresenceOfElementsOfAnalyticsPage("createButton");
			sleeper(1000);
			clickOnElementsOfAnalyticsPage("createButton");
			// Select Category
			sleeper(5000);
			selectCategoryForAnalyticsPage(category);
			// Select Sub - Category
			selectSubCategoryForAnalyticsPage(subcategory, languageCode);
			// Select Options
			sleeper(1000);
			selectOptionForAnalyticsPage(options, languageCode);
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			// Entering report name
			if(getAttributesOfAnalyticsPage("reportName", "value").isEmpty()) {
				enterTextForAnalyticsPage("reportName", reportNameLatest);
				returnObj[0][1]=reportNameLatest;
			}
			else {
				reportNameLatest=getAttributesOfAnalyticsPage("reportName", "value");
			    returnObj[0][1]=reportNameLatest;
			}
			// Removing the filters if any
			if(verifyElementIsVisibleOfAnalyticsPage("deleteFilterWorkflow")) {
				List<WebElement> crossButtons=getElementsTillAllElementsPresentAnalyticsPage("deleteFilterWorkflow"); 
				for(int buttonCounter=0;buttonCounter<crossButtons.size();buttonCounter++) {
					clickOnElementsOfAnalyticsPage("deleteFilterWorkflow");
				}
			}
			if(reportFormat.equalsIgnoreCase("pdf")) {
				clickByJavaScriptOnAnalyticsPage("pdfFormatRadioButton");
			}else {
				clickByJavaScriptOnAnalyticsPage("xlsxFormatRadioButton");
			}
			clickByJavaScriptOnAnalyticsPage("schedulePeriodicReports");
			waitForPresenceOfElementsOfAnalyticsPage("recurrenceDropDownBox");
			clickOnElementsOfAnalyticsPage("recurrenceDropDownBox");
			selectTextValueFromDropdown(analyticsPagePropertiesPageProperties.getProperty("recurrenceDropDownList"), recurrence, analyticsPagePropertiesPageProperties.getProperty("recurrenceDropDownBox"));
			clickByJavaScriptOnAnalyticsPage("reportPasswordCheckbox");
			enterTextForAnalyticsPage("reportPassword", "Testing123");
			clickByJavaScriptOnAnalyticsPage("allowEmailNotification");
			if(recurrence.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "reports.add_report.automatic_reports.recurrence.month"))) {
				day=getAttributesOfAnalyticsPage("datePicker", "value");
				String[] halfString = day.split(" ");
				String[] DateString = halfString[1].split(",");
				day = DateString[0];
			}else {
				day=getTextOfAnalyticsPage("dayDropDown");
			}
			time=getTextOfAnalyticsPage("timeDropDown");
			clickOnElementsOfAnalyticsPage("saveButton");
			verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
			waitForPresenceOfElementsOfAnalyticsPage("reportNameOnCustomTab");
			mouseHoverOnWorkflowAnalyticsPage("reportElementForHovering");
			if(matchTextOfAnalyticsPage("reportNameOnCustomTab",reportNameLatest)) {
				LOGGER.info("Report name matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Report name does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(matchTextOfAnalyticsPage("categoryOnCustomTab",category)) {
				LOGGER.info("Category matches on custom tab for "+category+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Category does not match on custom tab for "+category+" recurring "+recurrence +" type "+reportFormat);
			}
			if(matchTextOfAnalyticsPage("subCategoryOnCustomTab",subcategory)) {
				LOGGER.info("sub Category matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("sub Category does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(matchTextOfAnalyticsPage("optionsOnCustomTab",options)) {
				LOGGER.info("options matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("options does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(getTextOfAnalyticsPage("reportFormatOnCustomTab").equalsIgnoreCase(reportFormat)) {
				LOGGER.info("report Format matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("report Format does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(getTextOfAnalyticsPage("lastAccessOn").contains(getTextLanguage(languageCode, "daas_ui", "reports.list.last_access_on"))) {
				LOGGER.info("Last access string matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Last access string does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(getTextOfAnalyticsPage("recurrenceOnCustomTab").replaceAll("(?<=([1-9]|[1-3][0-1]))(st|nd|rd|th)", "").replace(".", "").equalsIgnoreCase(recurrenceStringOnCustomTab+" on "+day+" at "+time)) {
				LOGGER.info("Recurrence string matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Recurrence string does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(verifyIfElementIsClickableOfAnalyticsPage("runReportLinkOnCustomTab")) {
				LOGGER.info("Run report button is clickable on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Run report button is not clickable on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in scheduling Report : " + e.getMessage());
		}
		sleeper(1000);
		return returnObj;
	}
	
	/**
	 * This method schedules the report with specified parameters for a child company
	 * @param languageCode - Language code
	 * @param category - Category of report to be saved
	 * @param subcategory - subcategory of report to be saved
	 * @param options - options of report to be saved
	 * @param reportName - reportName of report to be saved
	 * @param recurrence - recurrence of report generation
	 * @param reportFormat - format of report to be scheduled
	 * @param recurrenceStringOnCustomTab - Scheduled time for the report
	 * @param childComp - Child company preference key
	 * @return String
	 * @throws Exception
	 */
	public final Object[][] scheduleReportChildCompany(String languageCode, String category, String subcategory, String options, String reportName, String reportFormat, String recurrence, String recurrenceStringOnCustomTab, String childComp) throws Exception {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportNameLatest = reportName + timeStamp;
		String day=null;
		Object[][] returnObj=new Object[1][2];
		returnObj[0][0]=true;
		returnObj[0][1]=reportNameLatest;
		try {
			waitForPageLoaded();
			waitForPresenceOfElementsOfAnalyticsPage("createButton");
			sleeper(1000);
			clickOnElementsOfAnalyticsPage("createButton");
			// Select Category
			sleeper(5000);
			selectCategoryForAnalyticsPage(category);
			// Select Sub - Category
			selectSubCategoryForAnalyticsPage(subcategory, languageCode);
			// Select Options
			sleeper(1000);
			selectOptionForAnalyticsPage(options, languageCode);
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			// Entering report name
			if(getAttributesOfAnalyticsPage("reportName", "value").isEmpty()) {
				enterTextForAnalyticsPage("reportName", reportNameLatest);
				returnObj[0][1]=reportNameLatest;
			}
			else {
				reportNameLatest=getAttributesOfAnalyticsPage("reportName", "value");
			    returnObj[0][1]=reportNameLatest;
			}

			clickOnElementsOfAnalyticsPage("addFilterCriteria");
			waitForElementsOfAnalyticsPage("filterFieldForCompanyPreference");
			clickOnElementsOfAnalyticsPage("filterFieldForCompanyPreference");
			selectTextValueFromDropdownWorkflow(analyticsPagePropertiesPageProperties.getProperty("filterFieldOptionList"), "Company Preference Key", analyticsPagePropertiesPageProperties.getProperty("filterFieldForCompanyPreference"));
			sleeper(1000);
			clickOnElementsOfAnalyticsPage("filterFieldForCompanyPreferenceValue");
			selectTextValueFromDropdownWorkflow(analyticsPagePropertiesPageProperties.getProperty("filterFieldValueOptionList"), childComp, analyticsPagePropertiesPageProperties.getProperty("filterFieldForCompanyPreferenceValue"));
			sleeper(1000);
			
			if(reportFormat.equalsIgnoreCase("pdf")) {
				clickByJavaScriptOnAnalyticsPage("pdfFormatRadioButton");
			}else {
				clickByJavaScriptOnAnalyticsPage("xlsxFormatRadioButton");
			}
			clickByJavaScriptOnAnalyticsPage("schedulePeriodicReports");
			waitForPresenceOfElementsOfAnalyticsPage("recurrenceDropDownBox");
			clickOnElementsOfAnalyticsPage("recurrenceDropDownBox");
			selectTextValueFromDropdown(analyticsPagePropertiesPageProperties.getProperty("recurrenceDropDownList"), recurrence, analyticsPagePropertiesPageProperties.getProperty("recurrenceDropDownBox"));
			clickByJavaScriptOnAnalyticsPage("reportPasswordCheckbox");
			enterTextForAnalyticsPage("reportPassword", "Testing123");
			clickByJavaScriptOnAnalyticsPage("allowEmailNotification");
			if(recurrence.equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "reports.add_report.automatic_reports.recurrence.month"))) {
				day=getAttributesOfAnalyticsPage("datePicker", "value");
				String[] halfString = day.split(" ");
				String[] DateString = halfString[1].split(",");
				day = DateString[0];
			}else {
				day=getTextOfAnalyticsPage("dayDropDown");
			}
			String time=getTextOfAnalyticsPage("timeDropDown");
			clickOnElementsOfAnalyticsPage("saveButton");
			verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
			waitForPresenceOfElementsOfAnalyticsPage("reportNameOnCustomTab");
			mouseHoverOnWorkflowAnalyticsPage("reportElementForHovering");
			if(matchTextOfAnalyticsPage("reportNameOnCustomTab",reportNameLatest)) {
				LOGGER.info("Report name matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Report name does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(matchTextOfAnalyticsPage("categoryOnCustomTab",category)) {
				LOGGER.info("Category matches on custom tab for "+category+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Category does not match on custom tab for "+category+" recurring "+recurrence +" type "+reportFormat);
			}
			if(matchTextOfAnalyticsPage("subCategoryOnCustomTab",subcategory)) {
				LOGGER.info("sub Category matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("sub Category does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(matchTextOfAnalyticsPage("optionsOnCustomTab",options)) {
				LOGGER.info("options matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("options does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(getTextOfAnalyticsPage("reportFormatOnCustomTab").equalsIgnoreCase(reportFormat)) {
				LOGGER.info("report Format matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("report Format does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(getTextOfAnalyticsPage("lastAccessOn").contains(getTextLanguage(languageCode, "daas_ui", "reports.list.last_access_on"))) {
				LOGGER.info("Last access string matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Last access string does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(getTextOfAnalyticsPage("recurrenceOnCustomTab").replaceAll("(?<=([1-9]|[1-3][0-1]))(st|nd|rd|th)", "").replace(".", "").equalsIgnoreCase(recurrenceStringOnCustomTab+" on "+day+" at "+time)) {
				LOGGER.info("Recurrence string matches on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Recurrence string does not match on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
			if(verifyIfElementIsClickableOfAnalyticsPage("runReportLinkOnCustomTab")) {
				LOGGER.info("Run report button is clickable on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}else {returnObj[0][0]=(boolean)returnObj[0][0] && false;
				LOGGER.error("Run report button is not clickable on custom tab for "+subcategory+" recurring "+recurrence +" type "+reportFormat);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in scheduling Report : " + e.getMessage());
		}
		sleeper(1000);
		return returnObj;
	}
	
	/**
	 * @param key-Key of element to hover on
	 * @throws Exception
	 */
	public final void mouseHoverOnWorkflowAnalyticsPage(String key) throws Exception {
		mouseHover(analyticsPagePropertiesPageProperties.getProperty(key));
	}
	
	/**
	 * 
	 * @param reportName-Name of the report
	 * @param category-Category of report
	 * @param subCategory-sub category of report
	 * @param option-option to generate report
	 * @param reportFormat- format of report to generate
	 * @param reportType-type of report to be generated
	 * @return boolean
	 */
	public final boolean historyTabValidation(String reportName,String category, String subCategory,String option,String reportFormat,String reportType) {
		boolean nameFlag=false;
		boolean categoryFlag=false;
		boolean subCategoryFlag=false;
		boolean optionsFlag=false;
		boolean reportFlag=false;
		boolean reportTypeFlag=false;
		boolean dateFlag = false;
		try {
			clickByJavaScriptOnAnalyticsPage("historyTab");
			waitForPageLoaded();
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			waitForPresenceOfElementsOfAnalyticsPage("reportNameHistoryTab");
			scrollTillViewAnalyticsPage("reportNameHistoryTab");
			nameFlag=matchTextOfAnalyticsPage("reportNameHistoryTab",reportName);
			scrollTillViewAnalyticsPage("categoryHistoryTab");
			categoryFlag=matchTextOfAnalyticsPage("categoryHistoryTab",category);
			scrollTillViewAnalyticsPage("subCategoryHistoryTab");
			subCategoryFlag=matchTextOfAnalyticsPage("subCategoryHistoryTab",subCategory);
			scrollTillViewAnalyticsPage("optionsHistoryTab");
			optionsFlag=matchTextOfAnalyticsPage("optionsHistoryTab",option);
			scrollTillViewAnalyticsPage("reportFormatHistoryTab");
			reportFlag=matchTextOfAnalyticsPage("reportFormatHistoryTab",reportFormat);
			scrollTillViewAnalyticsPage("reportTypeHistoryTab");
			reportTypeFlag=matchTextOfAnalyticsPage("reportTypeHistoryTab",reportType);
			String timeStamp = new SimpleDateFormat("dd MMM YYYY").format(new Date());
			scrollTillViewAnalyticsPage("dateHistoryTab");
			dateFlag=getTextOfAnalyticsPage("dateHistoryTab").equalsIgnoreCase(timeStamp);
		}catch(Exception e) {
			LOGGER.error("Exception occured in history Tab Validation " + e.getMessage());
		}
		
		return (dateFlag&&nameFlag&&categoryFlag&&subCategoryFlag&&optionsFlag&&reportFlag&&reportTypeFlag);
	}
	
	/**
	 * THis methods validates reports present on Standard tab
	 * @return boolean
	 */
	public final boolean verifyReportNameOnStandardTab() {
		boolean nameFlag = false;
		ArrayList<String> reportNames = new ArrayList<String>();
		try {
			List<WebElement> reportNamesKey = getElementsTillAllElementsPresentAnalyticsPage("reportNameListStandardTab");
			for (int nameCounter=0;nameCounter<reportNamesKey.size();nameCounter++) {
				reportNames.add(reportNamesKey.get(nameCounter).getText());
			}
			Collections.sort(reportNames);
			Collections.sort(WorkflowVariables.REPORT_NAME_LIST);
			nameFlag=WorkflowVariables.REPORT_NAME_LIST.equals(reportNames);
		}catch(Exception e) {
			LOGGER.error("Exception occured in standard Tab Validation " + e.getMessage());
		}
		return nameFlag;
	}
	
	/**
	 * THis methods selects number of records to be shown on Report page
	 */
	public final void selectNumberOfReportsToBeVisibleWorkflow(String dropDownOptions,String dropDown,String optionToSelect) {
		try {
			selectTextValueFromDropdownWorkflow(analyticsPagePropertiesPageProperties.getProperty(dropDownOptions), optionToSelect, analyticsPagePropertiesPageProperties.getProperty(dropDown));
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
		}catch(Exception e) {
			LOGGER.error("Exception occured in selectNumberOfReportsToBeVisibleWorkflow " + e.getMessage());
		}
	}
	
	public final void enterTextForAnalyticsPageUsingJavaScript(String key, String Text) throws Exception {
		enterTextUsingJavaScript(analyticsPagePropertiesPageProperties.getProperty(key), Text);
	}

	/**
	 * This method click on showing link and select last option on Report page 
	 * @return boolean
	 */
	public final boolean selectStandardReportType() {
		boolean flag = false;
		try {
			if(verifyElementsOfAnalyticsPage("clearReportFilter"))
			{
				clickOnElementsOfAnalyticsPage("clearReportFilter");
			}
			clickOnElementsOfAnalyticsPage("reportType");
			clickOnElementsOfAnalyticsPage("standardList");
			clickOnElementsOfAnalyticsPage("paginationDropdownMenu");
			clickOnElementsOfAnalyticsPage("selectPageOption");
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception occured in Click on showing link " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This methods compare report list.
	 * @paramreportName:report name list locator
	 * @return boolean
	 */
	public final boolean verifyReportNameList(ArrayList<String> reportNames, String subscriptionPlan) {
		boolean flag = false;
		try {
			ArrayList<String> uiReportNames = new ArrayList<String>();
			List<WebElement> reportNamesKey;
			reportNamesKey = getElementsTillAllElementsPresentAnalyticsPage("reportList");
			for (int nameCounter = 0; nameCounter < reportNamesKey.size(); nameCounter++) {
				uiReportNames.add(reportNamesKey.get(nameCounter).getText());
			}
			Collections.sort(reportNames);
			Collections.sort(uiReportNames);
			flag = reportNames.containsAll(uiReportNames);
			if(flag==true)
			{
				LOGGER.info("List of reports on standard tab displaying correctly for plan-"+subscriptionPlan);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in compareing report list." + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This function is used to generate the pdf and xlsx report.
	 * 
	 * @param category
	 * @param subcategory
	 * @param optionlist
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean downloadPdfXlsReport(String category, String subcategory, ArrayList<String> optionlist,
			String languageCode) throws Exception {
		boolean flag = false;
		String option = null;
		try {
			// Select sub-category
			LOGGER.info("Select subcategory :" + subcategory);
			if (selectSubCategoryForAnalyticsPagePDFXLS(category, subcategory, languageCode)) {
				if (verifyIfElementIsClickableOfAnalyticsPage("optionDropdownClick")) {
					if (optionlist.size() > 0) {
						for (int count = 0; count < optionlist.size(); count++) {
							// Select Option
							clearOptionDropdownText(languageCode);
							Thread.sleep(2000);
							if (uiVersion.equalsIgnoreCase("VENEER3")) {
								scrollTillViewAnalyticsPage("subcategoryDropdown");
							}
							sleeper(1000);
							selectOptionForAnalyticsPage(optionlist.get(count).toString(), languageCode);
							option = optionlist.get(count).toString();
							// Select mandatory filter, If data not present in the company then skip run
							// report with below message.
							if (subcategory.equalsIgnoreCase(getTextLanguage(languageCode,
									"MPI-Reporting-LHreports_service", "label.report_category_hwperfcomparison"))) {
								if (!verifyMandateFilter("selectOptionList")) {
									LOGGER.info("Could not create report-" + subcategory + " " + option
											+ ", Configuartion data not present for the selected company");
									flag = true;
									continue;
								}
							}
							// select report type
							List<WebElement> reportRadioButton = getElementsOfAnalyticsPage("pdfandxlsx");
							flag = selectReportTypePreviewButton(reportRadioButton, subcategory, option, languageCode);
							sleeper(2000);
						}
					}
				} else {
					// select report type when default single report option is present
					option = optionlist.get(0).toString();
					List<WebElement> reportRadioButton = getElementsOfAnalyticsPage("pdfandxlsx");
					flag = selectReportTypePreviewButton(reportRadioButton, subcategory, option, languageCode);
					sleeper(1000);
				}
			} else {
				flag = false;
			}
		} catch (Exception e) {
			LOGGER.error(
					"Download of Report " + subcategory + " " + option + " is fail because of : " + e.getMessage());
			return flag;
		}
		return flag;
	}

	/**
	 * This method is for select format type and download report
	 * 
	 * @param reportType
	 * @param subCategory
	 * @param optionName
	 * @return
	 */
	public final boolean selectReportTypePreviewButton(List<WebElement> reportType, String subCategory,
			String optionName, String languageCode) {
		int noOfWindowHandles;
		int counter = 0;
		for (int count1 = 1; count1 < reportType.size(); count1++) {
			try {
				// When report with only XLSX file type
				if (optionName.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
						"label.report_option_hwperfcompanyconfigdetails"))) {
					count1 = 2;
				}
				// When filter criteria is not present for the report
				if (subCategory
						.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
								"label.report_category_datahealth"))
						|| subCategory.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
								"label.report_category_mstelemetry"))
						|| subCategory.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
								"label.report_category_devicecomp"))
						|| subCategory.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
								"label.report_category_surerecactivity"))
						|| subCategory.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
								"label.report_category_surerecsettings"))
						|| subCategory.equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-LHreports_service",
								"label.report_category_surestart"))) {

				}
				// When filter is present for the report
				else {
					waitForPresenceOfElementsOfAnalyticsPage("filterSection");
					sleeper(1000);
					scrollTillViewAnalyticsPage("filterSection");
				}
				scrollTillViewAnalyticsPage("filterTypeLabel");

				// select report type radio button of PDF/XLSX
				reportType.get(count1).click();
				// click on preview button for download
				sleeper(2000);
				waitForElementsOfAnalyticsPage("reportPreviewButton");
				clickOnElementsOfAnalyticsPage("reportPreviewButton");
				switchToDifferentTab();
				sleeper(1000);
				waitForPresenceOfElementsOfAnalyticsPage("spinnerForPDFDownload");

				while (counter <= 90) {
					noOfWindowHandles = getWindowHandlesofAnalyticsPage();
					if (noOfWindowHandles == 1)
						break;
					Thread.sleep(1000);
					counter++;
				}

				if (getWindowHandlesofAnalyticsPage() == 1) {
					switchBackToParentWithoutCloseTab();
					LOGGER.info("Successfully Download the " + subCategory + " " + optionName + " "
							+ reportType.get(count1).getText() + " Report");
				} else {
					LOGGER.error(" Report downloading failed for : " + subCategory + " " + optionName);
					return false;
				}
			} catch (Exception e) {
				LOGGER.error(" Report downloading failed for : " + subCategory + " " + optionName + e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method clear option drop down text
	 * 
	 * @param languageCode:languge code
	 * @throws Exception
	 */
	public final void clearOptionDropdownText(String languageCode) throws Exception {
		if (!(getTextOfAnalyticsPage("optionDropdown").equalsIgnoreCase(
				getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON", "select_option_text")))) {
			scrollToTop();
			clickOnElementsOfAnalyticsPage("subCategoryOptionCrossButton");
		}
	}	
	
	/**
	 * This method verifies if there is a change in report name when sub category is changed
	 * 
	 * @param Category-Category of report to create
	 * @param subcategory-Sub Category of report selected second
	 * @param subcategoryOriginal-Sub Category of report selected first
	 * @param languageCode-Language code
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyReportNameChange(String Category, String subcategoryOriginal,String subcategory, String languageCode) throws Exception {
		boolean reportNameFlag=false;
		try {
			verifyElementIsVisibleOfAnalyticsPage("createButton");
			clickOnElementsOfAnalyticsPage("createButton");
			// Select Category
			sleeper(3000);//Needed for drop downs to become accessible
			selectCategoryForAnalyticsPage(Category);
			// Select Sub - Category
			selectSubCategoryForAnalyticsPage(subcategoryOriginal, languageCode);
			enterTextForAnalyticsPage("reportName", "AutomationReport");
			LOGGER.info("Report name entered");
			String originalReportName=getAttributesOfAnalyticsPage("reportName", "value");
			clickOnElementsOfAnalyticsPage("subcategoryDropdownWorkflow");
			selectTextValueFromDropdownWorkflow(analyticsPagePropertiesPageProperties.getProperty("subcategoryDropdownListWorkflow"), subcategory, analyticsPagePropertiesPageProperties.getProperty("subcategoryDropdownWorkflow"));
			LOGGER.info("Sub category is changed to ="+subcategory+" from "+subcategoryOriginal);
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			reportNameFlag=getAttributesOfAnalyticsPage("reportName", "value").equalsIgnoreCase(originalReportName);
			} catch (Exception e) {
			LOGGER.error("Exception occured in verifyReportNameChange : " + e.getMessage());
		}
		return reportNameFlag;
	}
	
	/**
	 * This method is for complete report tab and data validations for workflow.
	 * 
	 * @param tabNameDetails
	 * @param columnNameKeys
	 * @param LanguageCode
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public boolean reportDataValidationForWorkflow(String[][] tabNameDetails, String[] columnNameKeys, String LanguageCode, String folderName) throws Exception {
		boolean reportStatus = false;
		boolean reportStatusGraphBromium = false;
		boolean reportStatusGraph = true;
		boolean reportStatusGridBromium = false;
		boolean reportStatusColumnBromium = false;
		boolean reportStatusGrid = true;
		boolean reportStatusColumn = true;
		String reportTabName;
		String[] columnNamekey;
		ArrayList<String> tabName = new ArrayList<String>();
		ArrayList<String> tabDetails = new ArrayList<String>();
		int index;
		try {
			LOGGER.info("inside reportDataValidation");
			for (index = 0; tabNameDetails[0][index] != null; index++) {
				tabName.add(tabNameDetails[0][index]);
				tabDetails.add(tabNameDetails[1][index]);
			}
			scrollTillViewAnalyticsPage("repprtTabslistWorkflow");
			List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("repprtTabslistWorkflow");
			for (index = 0; index < tabName.size(); index++) {
				for (WebElement tab : element) {
					if (tab.getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", tabName.get(index)))) {
						tab.click();
						reportTabName = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", tabName.get(index));
						for (int i = 0; i <= (columnNameKeys.length) - 1; i++) {							
							columnNamekey = (columnNameKeys[i]).split(",");
							if ((tabDetails.get(index).equalsIgnoreCase("GRAPH")) && folderName.equalsIgnoreCase("MPI-Reporting-LHreports_service")) {
								i++;
								reportStatusGraphBromium = validateGraphData_bromium(reportTabName);
								LOGGER.info(reportStatusGraphBromium);
							} else if ((tabDetails.get(index).equalsIgnoreCase("GRAPH")) && folderName != ("MPI-Reporting-LHreports_service")) {
								reportStatusGraph = validateGraphData(reportTabName);
								LOGGER.info(reportStatusGraph);
							} else if (tabDetails.get(index).equalsIgnoreCase("GRID") && folderName.equalsIgnoreCase("MPI-Reporting-LHreports_service")) {								
								reportStatusGridBromium = validateGridData_bromium(reportTabName,LanguageCode);
								LOGGER.info(reportStatusGridBromium);
								reportStatusColumnBromium = validateColumnNames_bromium(columnNamekey, LanguageCode, folderName, reportTabName);
								LOGGER.info(reportStatusColumnBromium);
							} else if ((tabDetails.get(index).equalsIgnoreCase("GRID")) && folderName != ("MPI-Reporting-LHreports_service")) {
								sleeper(3000);
								reportStatusGrid = validateGridData(reportTabName);
								LOGGER.info(reportStatusGrid);
								reportStatusColumn = validateColumnNamesForWorkflow(columnNamekey, LanguageCode, folderName, reportTabName);
								LOGGER.info(reportStatusColumn);
							}
						}
					}
				}
			}
			if (folderName.equalsIgnoreCase("MPI-Reporting-LHreports_service")) {
				if (reportStatusGraphBromium && reportStatusGridBromium && reportStatusColumnBromium) {
					reportStatus = true;
				}
			} else {
				if (reportStatusGraph && reportStatusGrid && reportStatusColumn) {
					reportStatus = true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating grid and graph : " + e.getMessage());
			LOGGER.info("Exception in reportDataValidation");
			return reportStatus;
		}
		LOGGER.info("Final Report Status is : " + reportStatus);
		return reportStatus;
	}
	
	/**
	 * This method is for validating the column names of the tables generated in the reports for workflows.
	 * 
	 * @param columnNameKey
	 * @param LanguageCode
	 * @param folderName
	 * @param reportTabName
	 * @return
	 * @throws Exception
	 */
	public boolean validateColumnNamesForWorkflow(String[] columnNameKey, String LanguageCode, String folderName, String reportTabName) throws Exception {
		try {
			String[] columnNameList;
			ArrayList<String> uiColumnName = new ArrayList<String>();
			int counter = 0;

			for (int i = 0; i <= (columnNameKey.length) - 1; i++) {
				columnNameList = analyticsTabTitleProperties.getProperty(columnNameKey[i]).split(":");
				List<WebElement> element = getDriver().findElements(By.xpath("//div[@id='" + reportTabName + "']//tr[contains(@class,'tableheader__row')]//th//div"));
				ArrayList<String> columnName = new ArrayList<String>();
				if (columnNameList.length != element.size()) {
					LOGGER.info("The actual column size doesnot match with expected column size");
					return false;
				}
				for (counter = 0; counter < columnNameList.length; counter++) {
					if(getTextLanguage(LanguageCode, folderName, columnNameList[counter])==null) {
						columnName.add(getTextLanguage(LanguageCode, "daas_ui", columnNameList[counter]));
					}else {
						columnName.add(getTextLanguage(LanguageCode, folderName, columnNameList[counter]));
					}
					
					String columnNameUi = element.get(counter).getText();
					columnNameUi = columnNameUi.replaceAll("\n[ \t]+", "");
					uiColumnName.add(columnNameUi);
					if (uiColumnName.get(counter).equalsIgnoreCase(columnName.get(counter))) {
					} else {
						LOGGER.info("Mismatch in column titles" + uiColumnName.get(counter) + columnName.get(counter));
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception occured in validating column names : " + e.getMessage());
			LOGGER.info("Column titles are not successfully verified");
			return false;
		}
		LOGGER.error("Column titles are successfully verified");
		return true;
	}
	
	
	/**
	 * This method validates the sub-category for checking if Security reports are present in the list or not based on the Proactive Security Reports UI flag (under Company Preferences).
	 * 
	 * @param subcategory
	 * @throws Exception
	 */
	public final boolean validateSecurityReportsSubcategory(String subcategory, String languageCode) throws Exception {
		boolean status = false;
		try {
			if (getTextOfAnalyticsPage("subcategoryDropdown").equalsIgnoreCase(getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON",
					"select_option_text"))) {
				clickOnElementsOfAnalyticsPage("subcategoryDropdown");
				List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("subCategoryList");
				for (WebElement we : element) {
					if (we.getText().equalsIgnoreCase(subcategory)) {
						status = true;		
						LOGGER.info("Security report " + subcategory + " is present in the sub category list");	
						break;
					}					
				}				
			} else {				
				LOGGER.info("Security reports are not present in the sub category list");					
			}		
		} catch (Exception e) {
			LOGGER.error("Exception occured in validateSecurityReportsSubcategory : " + e.getMessage());
		}
		return status;
	}
	
	/**
	 * This method is used to test the columns in show hide window which are by
	 * default disabled.
	 * 
	 * @param columnNme
	 * @return boolean flag
	 */
	public final boolean verifyColumnisDisabledShowHideWindow(String columnNme) {
		boolean flag = false;
		try {
			scrollTillViewAnalyticsPage("showHideButton");
			clickOnElementsOfAnalyticsPage("showHideButton");
			List<WebElement> columnListElement = getElementsTillAllElementsPresentAnalyticsPage("showHideColumnList");
			List<WebElement> checkboxListElement = getElementsTillAllElementsPresentAnalyticsPage("showHideCheckboxList");
			for (int i = 0; i < checkboxListElement.size(); i++) {
				if (!(checkboxListElement.get(i).isSelected())) {
					if (columnListElement.get(i).getText().equalsIgnoreCase(columnNme)) {
						String result = columnListElement.get(i).getText();
						LOGGER.info("Column and checkbox successfuly verified , passed :" + result);
						flag = true;
						break;
					}
				}
			}
			clickOnElementsOfAnalyticsPage("showHideClose");
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyColumnisDisabledShowHideWindow : " + e.getMessage());
		}
		return flag;
	}
	
	public final boolean checkReportShowHideColumns() {
		try {
			scrollTillViewAnalyticsPage("showHideButton");
			clickOnElementsOfAnalyticsPage("showHideButton");
			List<WebElement> columnListElement = getElementsTillAllElementsPresentAnalyticsPage("showHideColumnList");
			List<WebElement> checkboxListElement = getElementsTillAllElementsPresentAnalyticsPage("showHideCheckboxList");
			for (int i = 0; i < checkboxListElement.size(); i++) {
				if (!(checkboxListElement.get(i).isSelected())) {
					columnListElement.get(i).click();
				}
			}
			clickOnElementsOfAnalyticsPage("showHideSaveButton");
		} catch (Exception e) {
			LOGGER.error("Exception occured in checkReportShowHideColumns : " + e.getMessage());
		}
		return true;
	}
			
		

	/**
	 * This method is used to test the columns in show hide window which are by
	 * default enabled.
	 * @param columnNme
	 * @return boolean flag
	 */
	public final boolean verifyColumnIsEnabledInShowHideWindow(String columnNme) {
		boolean flag = false;
		try {
			List<WebElement> columnListElement = getElementsTillAllElementsPresentAnalyticsPage("showHideColumnList");
			List<WebElement> checkboxListElement = getElementsTillAllElementsPresentAnalyticsPage("showHideCheckboxList");
			for (int i = 0; i < checkboxListElement.size(); i++) {
				if ((checkboxListElement.get(i).isSelected())) {
					if (columnListElement.get(i).getText().equalsIgnoreCase(columnNme)) {
						columnListElement.get(i).click();
						String result = columnListElement.get(i).getText();
						LOGGER.info("Column and checkbox successfully verified , passed :" + result+" is deselected");
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating column name and checkbox property : " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method verifies the expected reports visible to the logged in user based on subscription
	 */
	public final boolean verifyReportsVisibleForUser(List<WebElement> reportVisible,List<String> reportExpected) {
	boolean flag = false;
	try {
		for (WebElement we : reportVisible) {
			for (String ex : reportExpected) {
				if(we.getText().equalsIgnoreCase(ex)) {
					LOGGER.info("Secure DaaS reports" + ex + "is visible for Proactive Endpoint Management subscription");
					flag = true;
				    
				}
			}
		}
	return flag;
	}catch (Exception e) {
		LOGGER.error("Exception occured in validating reports visible : " + e.getMessage());
	}
	LOGGER.info("Secure DaaS reports are not visible for Proactive Endpoint Management subscription");
	return flag;	
	}
	
	/**
	 * This method is used to verify drill down when clicked on the graph @tabName,
	 * Graph to be test under the report tab @drillDownTab, Tab name after drill
	 * down from the graph @graphElementhover, Mouse hover over the
	 * graph @graphElementClick, Click on the graph element @languageCode,
	 * Localization handled for report tab names
	 * 
	 * @throws Exception
	 */
	public final boolean verifyGraphDrillDown(String tabName, String drillDownTab, String graphElementhover,
			String graphElementClick, String languageCode) throws Exception {
		try {
			String reportTabName = getTextLanguage(languageCode, "daas_reports_ui", tabName);
			String drillDownTabname = getTextLanguage(languageCode, "daas_reports_ui", drillDownTab);
			if (getTextOfAnalyticsPage("reportTab").equalsIgnoreCase(reportTabName)) {
				if (!(verifyElementIsVisibleOfAnalyticsPage("chartsNoData")))// true when no data to be chk
				{
					LOGGER.info("Data is present in the Graph under tab - " + reportTabName);
					Actions action = new Actions(getDriver());
					WebElement element = getElementOfAnalyticsPage(graphElementhover);
					action.moveToElement(element).build().perform();
					mouseHoverbyoffsettClick(graphElementClick, 00, 80);
					waitUntilElementIsVisibleOfAnalyticsPage("spinner");
					if (getTextOfAnalyticsPage("drillDownTabname").equalsIgnoreCase(drillDownTabname)) {
						if (getElementsTillAllElementsVisibleAnalyticsPage("detailsTable").size() >= 1) {
							LOGGER.info("Data is present in the Grid " + drillDownTab);
							return true;
						} else {
							LOGGER.info("Missing data in the grid after drill down from graph");
							return false;
						}
					}
				} else if (verifyElementIsVisibleOfAnalyticsPage("chartsNoData")) {
					LOGGER.info("Graph has no data under tab - " + reportTabName);
					return true;
				} else {
					LOGGER.info("Data is not loaded due to some error in the graph under tab - " + reportTabName);
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating graph drill down : " + e.getMessage());
			return false;
		}
		return false;
	}
	
	/**
	 * This method used for click on left side of total count.
	 * @param key:it is center point from that we start moving.
	 * @param left:It is value for moving left from center point
	 * @param right:It is value for moving Right from center point
	 * @throws Exception
	 */
	public final void mouseHoverbyoffsettClick(String key,int left,int right) throws Exception {
		mouseHoverbyoffsetClick(analyticsPagePropertiesPageProperties.getProperty(key),left,right);
    }
	
	
	/**
	 * This method verify and validate data of VRE report
	 * 
	 * @param reportTabs:     List of tabs on report details page
	 * @param columnNameKeys: List of column names in the grid
	 * @param languageCode:   Selected language while running test case
	 * @param folderName:     Referred keys from the folder to handle localization
	 * @throws Exception
	 */
	public final boolean reportDataValidationVRE(String[] reportTabs, String[] columnNameKeys, String languageCode,
			String folderName) throws Exception {
		int count = 0;
		boolean reportStatusGraph = false, reportStatusGrid = false, reportStatusColumn = false;
		try {
			List<WebElement> reportTabList = getElementsTillAllElementsPresentAnalyticsPage("repprtTabslist");
			ArrayList<String> expectedReportTabs = getTextLanguage(languageCode, folderName, reportTabs);
			for (WebElement we : reportTabList) {
				String reportTabName = expectedReportTabs.get(count);
				we.click();
				// Verify report tab name
				if (we.getText().equalsIgnoreCase(reportTabName)) {
					// Verify presence of graph
					reportStatusGraph = validateGraphData(reportTabName);
					LOGGER.info(reportStatusGraph);
					// Verify presence of grid
					reportStatusGrid = validateGridData(reportTabName);
					LOGGER.info(reportStatusGrid);
					// Verify column titles
					reportStatusColumn = validateColumnNames(columnNameKeys, languageCode, folderName, reportTabName);
					LOGGER.info(reportStatusColumn);
				} else {
					LOGGER.info("Report tab name mismatched - Expected:" + reportTabName + " Actual :" + we.getText());
					return false;
				}
				if (!(reportStatusGraph && reportStatusGrid && reportStatusColumn)) {
					return false;
				}
				count++;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validating graph and grid of VRE : " + e.getMessage());
			LOGGER.info("Exception in reportDataValidationVRE");
		}
		return true;
	}
	
	/**
	 * This method to verify the description of the grid under the tab
	 * 
	 * @param titleLocatorKey: Locator of the description
	 * @param titleText:       Description of the grid
	 * @throws Exception
	 */
	public boolean verifyTitleofGridPage(String titleLocatorKey, String titleText) {
		boolean flag = false;
		try {
			if (verifyElementIsVisible(analyticsPagePropertiesPageProperties.getProperty(titleLocatorKey))) {
				if (titleText.equalsIgnoreCase(getTextOfAnalyticsPage(titleLocatorKey))) {
					flag = true;
				} else {
					LOGGER.info("Exception in reportDataValidationVRE");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyTitleofGridPage method" + e.getMessage());
			return flag;
		}
		return flag;
	}
	
	/**
	 * This method to verify the graph data is in the order of old to latest
	 * 
	 * @param LanguageCode    - Selected language
	 * @param reportTab       - report tab name
	 * @param reportTabGraphs - Array of total no. of graphs on the tab
	 * @throws Exception
	 */
	public boolean verifyGraphDataSort(String LanguageCode, String reportTab, String[] reportTabGraphs)
			throws Exception {
		boolean flag = false;
		LinkedHashSet<String> months = new LinkedHashSet<String>();
		List<WebElement> reportTabList = getElementsTillAllElementsPresentAnalyticsPage("repprtTabslist");
		for (WebElement tab : reportTabList) {
			if (tab.getText().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", reportTab))) {
				tab.click();
				break;
			}
		}

		for (String graph : reportTabGraphs) {
			if (verifyElementsOfAnalyticsPage(graph)) {
				List<WebElement> xAxisxElements = getElementsTillAllElementsPresentAnalyticsPage(graph);
				months.clear();
				for (WebElement index : xAxisxElements) {
					String xAxismonths = index.getText().substring(0, 3);
					months.add(xAxismonths);
				}
				ArrayList<String> monthArr = new ArrayList<String>(months);
				Calendar cal = Calendar.getInstance();
				DateFormat fmt = new SimpleDateFormat("MMM");
				SimpleDateFormat outputFormat = new SimpleDateFormat("MMM");
				Date d = fmt.parse(monthArr.get(0));
				cal.setTime(d);
				// matching code
				for (int i = 1; i < monthArr.size(); i++) {
					cal.add(Calendar.MONTH, 1);
					if (monthArr.get(i).equalsIgnoreCase(outputFormat.format(cal.getTime()).toString())) {
						flag = true;
					} else {
						flag = false;
						LOGGER.info("Missing data in graph or not showing old to latest from left to right -" + graph);
						return flag;
					}
				}
				LOGGER.info("Graph data showing old to latest from left to right -" + graph);
			} else if (getDriver().findElements(By.cssSelector("g[class*='highcharts-no-data'] text")).size()>=1) {
				flag = true;
				LOGGER.info("Graph has no data- " + graph + ", On Report tab- " + reportTab);
			} else {
				flag = false;
				LOGGER.info("Missing graph -" + graph + ", On report tab- " + reportTab);
			}
		}
		return flag;
	}

	/**
	 * This method to create report and call function to verify the graph data in
	 * the order of old to latest
	 * 
	 * @param LanguageCode - Selected Language
	 * @param category     - Report category name
	 * @param subcat       - Report subcategory name
	 * @param reportOption - Report option name
	 * @return boolean - To handle mandatory report filter
	 * @throws Exception
	 */
	public boolean runReportPreview(String LanguageCode, String category, String subcat, String reportOption) {
		boolean flag = true;
		try {
			switchToParentTab();
			clickOnElementsOfAnalyticsPage("reportTab");
			sleeper(2000);
			createReport(category, subcat, reportOption, "Report", "Company", LanguageCode);
			LOGGER.info("Report creation for the given category,subcategory and option was successfull " + category
					+ subcat + reportOption);
			// Select mandatory filter, If data not present in the company then skip run
			// report with below message.
			if (subcat.equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service",
					"label.report_category_hwperfcomparison"))) {
				if (!verifyMandateFilter("selectOptionList")) {
					LOGGER.info("Could not create report-" + subcat + " " + reportOption
							+ ", Configuartion data not present for the selected company");
					flag = false;
					return flag;
				}
			}
			// click on preview button
			waitForElementsOfAnalyticsPage("reportPreviewButton");
			clickOnElementsOfAnalyticsPage("reportPreviewButton");
			sleeper(2000);
			switchToDifferentTab();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * This method verify Self Reporting
	 * 
	 * @param userEmail email id to which email will be send.
	 * @throws Exception
	 */
	public final boolean verifySelfService(String userEmail) {
		boolean flag = false;
		try {
			waitForPageLoaded();
			List<WebElement> tables = getElementsTillAllElementsPresentAnalyticsPage("selfRTableList");
			// Select 1st 5 tables
			for (int i = 0; i <= 4; i++) {
				tables.get(i).click();
			}
			// Select date range
			selectDateDelfService();
			LOGGER.info("Date Selected");
			// Upload file
			sleeper(1000);
			clickOnElementsOfAnalyticsPage("selfBrowse");
			sleeper(1000);
			LOGGER.info("Clicked on browse button");
			Robot robot = new Robot();
			// copying File path to Clipboard
			StringSelection path = new StringSelection(ConstantPath.SELF_REPORTING_CSV + "devicesn.csv");
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);
			// press Contol+V for pasting
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			// release Contol+V for pasting
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			// for pressing and releasing Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			waitForPageLoaded();
			LOGGER.info("File uploaded successfully");
			// Enter email
			enterTextForAnalyticsPage("selfEmail", userEmail);
			clickOnElementsOfAnalyticsPage("selfCyberSec");
			clickByJavaScriptOnAnalyticsPage("selfSubmit");
			Thread.sleep(1000);
			switchToDifferentTab();
			clickByJavaScriptOnAnalyticsPage("selfDone");
			LOGGER.info("Request Submitted Successfully");
			flag = true;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifySelfService method " + e.getMessage());
			return flag;
		}
		return flag;
	}

	/**
	 * This method is to select Self Reporting date
	 * 
	 * @param
	 * @throws Exception
	 */
	public final boolean selectDateDelfService() {
		boolean flag = false;
		boolean isSelect = false;
		try {
			String startDate, endDate;
			Calendar cals = Calendar.getInstance();
			Calendar cale = Calendar.getInstance();
			cals.add(Calendar.DATE, -4);
			cale.add(Calendar.DATE, -1);
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				startDate = new SimpleDateFormat("MMM dd, YYYY").format(cals.getTime());
				endDate = new SimpleDateFormat("MMM dd, YYYY").format(cale.getTime());
			} else {
				startDate = new SimpleDateFormat("MMMM dd, YYYY").format(cals.getTime());
				endDate = new SimpleDateFormat("MMMM dd, YYYY").format(cale.getTime());
			}
			LOGGER.info("Start Date: " + startDate + " End date: " + endDate);
			ArrayList<String> seDays = new ArrayList<String>(Arrays.asList(startDate, endDate));
			clickOnElementsOfAnalyticsPage("selfStartDate");
			for (String seDate : seDays) {
				String DateArray[] = seDate.replace(",", "").split(" ");
				List<WebElement> monthDays = getElementsTillAllElementsPresentAnalyticsPage("selfDateList");
				for (WebElement monthDay : monthDays) {
					if ((monthDay.getText().equalsIgnoreCase("01".replaceAll("^0+(?!$)", ""))) && (isSelect == false)) {
						monthDay.click();
						isSelect = true;
					}
					if ((monthDay.getText().equalsIgnoreCase(DateArray[1].replaceAll("^0+(?!$)", "")))
							&& (isSelect == true)) {
						System.out.println(monthDay + "  " + seDate);
						Thread.sleep(2000);
						monthDay.click();
						Thread.sleep(2000);
						LOGGER.info("Selected Date successfully -" + DateArray[1] + " " + DateArray[0]);
						break;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectDateDelfService method" + e.getMessage());
			return flag;
		}
		return flag;
	}

	/**
	 * This method is for validating the column names of the tables generated in the
	 * reports.
	 *
	 * @param columnNameKey
	 * @param LanguageCode
	 * @param folderName
	 * @param reportTabName
	 * @return
	 * @throws Exception
	 */
	public boolean validateTableColumnNames(String[] columnNameKey, String LanguageCode, String folderName,
									   String reportTabName) throws Exception {
		try {
			String[] expectedColumnNameList;

			for (int i = 0; i <= (columnNameKey.length) - 1; i++) {
				expectedColumnNameList = analyticsTabTitleProperties.getProperty(columnNameKey[i]).split(":");
				List<WebElement> uiReportColumnList = null;
				uiReportColumnList=getElementsOfAnalyticsPage("NetworkSpeedcolumnNameKeys");

				if (expectedColumnNameList.length != uiReportColumnList.size()) {
					LOGGER.info(
							"Tab-" + reportTabName + " Total no. of columns not matching with the expected, Expected-"
									+ expectedColumnNameList.length + " Actual-" + uiReportColumnList.size());
					return false;
				}

				ArrayList<String> columnNames = getTextLanguage(LanguageCode, folderName, expectedColumnNameList);
				for (int counter = 0; counter < expectedColumnNameList.length; counter++) {
					scrollTillView(uiReportColumnList.get(counter));
					String columnNameUi = uiReportColumnList.get(counter).getText();
					columnNameUi = columnNameUi.replaceAll("\n[ \t]+", "");
					if (columnNameUi.equalsIgnoreCase(columnNames.get(counter))) {
					} else {
						LOGGER.info("Mismatch in column titles - Expected-" + columnNames.get(counter) + " Actual -"
								+ columnNameUi);
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception occured in validating column names : " + e.getMessage());
			LOGGER.info("Column titles are not successfully verified");
			return false;
		}
		LOGGER.info("Column titles are successfully verified of grid-" + reportTabName);
		return true;
	}
	
	/**
	 * This method creates the report with specified parameters..
	 * 
	 * @param Category
	 * @param subcategory
	 * @param options
	 * @param reportName
	 * @return
	 * @throws Exception
	 */
	public final ArrayList<String> createReortforHelpLink(String Category, String subcategory, String options, String reportName,
			String reportcompanyvalue, String languageCode) throws Exception {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportNameLatest = reportName + timeStamp;
		ArrayList<String> reportParameters = new ArrayList<String>();
		// Getting logged in username
		try {
			verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
			if (verifyElementsOfAnalyticsPage("userMenuButtonWithoutImage")) {
				clickOnElementsOfAnalyticsPage("userMenuButtonWithoutImage");
			} else {
				clickOnElementsOfAnalyticsPage("userMenuButtonWithImage");
			}
			String createdByValue = getTextOfAnalyticsPage("loggedUserNew");
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				clickOnElementsOfAnalyticsPage("browseTab");
			}
			clickOnElementsOfAnalyticsPage("createButton");
			// Select Category
			sleeper(5000);
			selectCategoryForAnalyticsPage(Category);
			// Select Sub - Category
			selectSubCategoryForAnalyticsPage(subcategory, languageCode);
			// Select Options
			sleeper(1000);
			selectOptionForAnalyticsPagetest(options, languageCode);
			// Entering report name
			clickOnElementsOfAnalyticsPage("reportName");
			if (getAttributesOfAnalyticsPage("reportName", "value").isEmpty())
				enterTextForAnalyticsPage("reportName", reportNameLatest);
			else
				reportNameLatest = getAttributesOfAnalyticsPage("reportName", "value");
			// Getting value for company name
			String companyValue = reportcompanyvalue;
			// Adding all the values to the arraylist
			String categoryValue = getTextOfAnalyticsPage("categoryDropdown");
			reportParameters.add(categoryValue);
			String subCategoryValue = getTextOfAnalyticsPage("subcategoryDropdown");
			reportParameters.add(subCategoryValue);
			String optionsValue = getTextOfAnalyticsPage("optionDropdown");
			reportParameters.add(optionsValue);
			reportParameters.add(companyValue);
			reportParameters.add(createdByValue);
			reportParameters.add(reportNameLatest);
			// Removing the filters if any
			waitUntilElementIsVisibleOfAnalyticsPage("spinner");
			sleeper(3000);
			if (!getDriver().findElements(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
					.isEmpty()) {
				do {
					scrollTillViewAnalyticsPage("optionDropdown");
					getDriver().findElement(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
							.click();
				} while (getDriver()
						.findElements(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
						.size() != 0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred in creating Report : " + e.getMessage());
		}
		return reportParameters;
	}


/**
 * This method selects the option for generating the report.
 * 
 * @param options
 * @throws Exception
 */
public final void selectOptionForAnalyticsPagetest(String options, String languageCode) throws Exception {
	try {
		if (getTextOfAnalyticsPage("optionDropdown").equalsIgnoreCase(
				getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON", "select_option_text"))
				|| !(getTextOfAnalyticsPage("optionDropdown").equalsIgnoreCase(options))) {
			clickOnElementsOfAnalyticsPage("optionDropdown");
			List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage("optionList");
			for (WebElement we : element) {
				scrollTillView(we);
				if (we.getText().equalsIgnoreCase(options)) {
					we.click();
					break;
				}
			}
		} else {
			LOGGER.info("Report option is selected by default");
		}
	} catch (Exception e) {
		LOGGER.error("Exception occured in selectOptionForAnalyticsPage : " + e.getMessage());
	}
}

/**
 * This method verify the values on HTML report for category , subcategory, options, created by, company name.
 * 
 * @param reportGenerateValues
 * @param LanguageCode
 * @return
 * @throws Exception
 */

public final boolean verifyRunReportAndDatatest(ArrayList<String> reportGenerateValues, String LanguageCode) throws Exception {
	try {
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		
		String categoryKey = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "category");
		String subcategoryKey = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "subcategory");
		String optionKey = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "report_option");
		String createdByKey = getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "report_created_by");

		clickOnElementsOfAnalyticsPage("reportMoreDetails");
		LOGGER.info("Clicked on More Details");
		sleeper(3000);
		String categoryValueRunReport = getDriver().findElement(By.xpath("//div[contains(text(),'" + categoryKey + "')]/following-sibling::div")).getText();
		String subCategoryValueRunReport = getDriver().findElement(By.xpath("//div[contains(text(),'" + subcategoryKey + "')]/following-sibling::div")).getText();
		String optionValueRunReport = getDriver().findElement(By.xpath("//div[contains(text(),'" + optionKey + "')]/following-sibling::div")).getText();
		String createdByValueRunReport = getDriver().findElement(By.xpath("//div[contains(text(),'" + createdByKey + "')]/following-sibling::div")).getText();
		if (((reportGenerateValues.get(0)).equalsIgnoreCase(categoryValueRunReport)) && ((reportGenerateValues.get(1)).equalsIgnoreCase(subCategoryValueRunReport)) &&
				((reportGenerateValues.get(2)).equalsIgnoreCase(optionValueRunReport)) && ((reportGenerateValues.get(4)).equalsIgnoreCase(createdByValueRunReport))) {
		
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("Helplinkicon"),
				"Help link icon is not present");
		waitForElementsOfAnalyticsPage("Helplinkicon");
		sleeper(1000);
		clickOnElementsOfAnalyticsPage("Helplinkicon");
		clickByJavaScriptOnAnalyticsPage("helplink");
		LOGGER.info("Clicked on " + subCategoryValueRunReport + " Help link from reports tab");
		//LOGGER.info("Clicked on Software error Help link from reports tab");
		switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		if (subCategoryValueRunReport.toString().equalsIgnoreCase(getTextLanguage(LanguageCode,
				"MPI-Reporting-LHreports_service", "label.report_category_hwbluescreen"))) {
		softAssert.assertTrue(analyticsPage.getUrlOfCurrentPage().contains(ConstantURL.Top_Blue_Screen_Errors),
				"User not redirected to" + subCategoryValueRunReport +" help section after clicking on link from Reports");}
		else if 
		(subCategoryValueRunReport.toString().equalsIgnoreCase(getTextLanguage(LanguageCode,
				"MPI-Reporting-LHreports_service", "label.report_category_seatsbydevenrol"))) {
		softAssert.assertTrue(analyticsPage.getUrlOfCurrentPage().contains(ConstantURL.Seats_Entitled_by_Device_Enrollment),
				"User not redirected to" + subCategoryValueRunReport +" help section after clicking on link from Reports");
		}
		else if 
		(subCategoryValueRunReport.toString().equalsIgnoreCase(getTextLanguage(LanguageCode,
				"MPI-Reporting-LHreports_service", "label.report_category_incidentmgmt"))) {
		softAssert.assertTrue(analyticsPage.getUrlOfCurrentPage().contains(ConstantURL.Incident_Management),
				"User not redirected to" + subCategoryValueRunReport +" help section after clicking on link from Reports");
		}
		else
		{
			LOGGER.info("Selected option is incorrect");
		}
		LOGGER.info("User redirected to Help link successfully.");
		switchBackToPreviousTab();
		switchToParentTab();
		return true;
		} 
		else {
			return false;
		}
	} catch (Exception e) {
		LOGGER.error("EXCEPTION OCCURED IN MATCHING THE VALUES ON HTML REPORT OR RUNNING REPORT : " + e.getMessage());
		return false;		
	}
}


/**
 * This method selects the category for generating the report.
 * 
 * @param category
 * @throws Exception
 */
public final void selectAddFilterFieldForAnalyticsPage(String fieldname, String dropdownkey, String optionlistkey)
		throws Exception {
	try {
		// Scroll up in a vertical direction
		scrollUpInVertical();
		clickOnElementsOfAnalyticsPage(dropdownkey);
		List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage(optionlistkey);
		for (WebElement we : element) {
			if (we.getText().equalsIgnoreCase(fieldname)) {
				we.click();
				break;
			}
		}
	} catch (Exception e) {
		LOGGER.error("Exception occured in selectCategoryForAnalyticsPage : " + e.getMessage());
	}
}

/**
 * @param tabname
 * @param tablistkey
 * @return
 */
public final boolean verifyDifferentTabsDisplayedOnHtmlRunPreviewPage(List<String> tabname, String tablistkey) {
	List<String> lst = new ArrayList<>();
	try {
		List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage(tablistkey);
		for (WebElement ele : element) {
			lst.add(ele.getText());
		}
		return tabname.containsAll(lst);
	} catch (Exception e) {
		LOGGER.error("Exception occured in selectCategoryForAnalyticsPage : " + e.getMessage());
		return false;
	}

}

/**
 * @param LanguageCode
 *            -Language
 * @param dropdownOptionKey
 *            - drop down option list key
 * @param expectedOptionsList
 *            - expected drop down option
 * @return true /false
 * @throws Exception
 */
public final boolean verifyDropOptionListContainOnAnalyticsPage(String LanguageCode, String dropdownOptionKey,
		List<String> expectedOptionsList, String dropdownkey) throws Exception {
	boolean checkFlag = false;
	clickOnElementsOfAnalyticsPage(dropdownkey);
	List<String> dropdownOptionsList = getTextAsListOnAnalyticsPage(dropdownOptionKey);
	for (String expectedOption : expectedOptionsList) {
		for (String dropdownOptionText : dropdownOptionsList) {
			if (dropdownOptionText.contains(expectedOption)) {
				checkFlag = true;
				break;
			} else {
				checkFlag = false;
			}
		}
	}
	clickOnElementsOfAnalyticsPage(dropdownkey);
	return checkFlag;
}

/**
 * This method selects the option for generating the report.
 * 
 * @param options
 * @throws Exception
 */
public final List<String> selectOptionForAnalyticsPage(String options, String languageCode, String dropdownkey,
		String dropdownoptionlist) throws Exception {
	List<String> strOptionValues = null;
	try {

		if (getTextOfAnalyticsPage(dropdownkey).equalsIgnoreCase(
				getTextLanguage(languageCode, "MPI-Reporting-template-list-UI-JSON", "select_option_text"))
				|| !(getTextOfAnalyticsPage(dropdownkey).equalsIgnoreCase(options))) {
			clickOnElementsOfAnalyticsPage(dropdownkey);
			List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage(dropdownoptionlist);
			strOptionValues = getTextAsListOnAnalyticsPage(dropdownoptionlist);
			for (WebElement we : element) {
				scrollTillView(we);
				if (we.getText().equalsIgnoreCase(options)) {
					we.click();
					break;
				}
			}
		} else {
			LOGGER.info("Report option is selected by default");
		}

		return strOptionValues;
	} catch (Exception e) {
		LOGGER.error("Exception occured in selectOptionForAnalyticsPage : " + e.getMessage());
		return null;
	}
}

/**
 * This method returns the text of a list of elements
 */
public final ArrayList<String> getTextAsListOnAnalyticsPage(String key) throws Exception {
	ArrayList<String> columnNamesOnPage = new ArrayList<>();
	try {
		List<WebElement> element = getElementsTillAllElementsPresentAnalyticsPage(key);

		for (WebElement webElement : element) {
			String columnName = webElement.getText();
			columnName = columnName.replaceAll("\n[ \t]+", "");
			columnNamesOnPage.add(columnName);
		}
		return columnNamesOnPage;
	} catch (Exception e) {
		LOGGER.error("Exception occured in getTextAsListOnAnalyticsPage : " + e.getMessage());
		return null;
	}
}

/**
 * @param excelReader
 * @param sheetname
 * @param rownumber
 * @return
 */
public final List<String> getReportFileColumnNames(ExcelReader excelReader, String sheetname, int rownumber) {
	List<String> actualListofColumnNames = new ArrayList<String>();
	try {
		int colCount = excelReader.getSheetColumns(sheetname, 0);
		for (int i = 0; i < colCount; i++) {
			actualListofColumnNames.add(excelReader.getCellData(sheetname, i, rownumber));
		}
		return actualListofColumnNames;
	} catch (Exception e) {
		LOGGER.error("Exception occured in getReportFileColumnNames : " + e.getMessage());
		return null;
	}
}
/**
 * This method creates the report with specified parameters..
 * 
 * @param Category
 * @param subcategory
 * @param options
 * @param reportName
 * @return
 * @throws Exception
 */
public final ArrayList<String> CreatePDFreport(String Category, String subcategory, String options, String reportName,String reportcompanyvalue, String languageCode) throws Exception {

	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	String reportNameLatest = reportName + timeStamp;
	ArrayList<String> reportParameters = new ArrayList<String>();
	// Getting logged in username
	try {
		verifyElementIsVisibleOfAnalyticsPage("customReportsCreatedList");
		if (verifyElementsOfAnalyticsPage("userMenuButtonWithoutImage")) {
			clickOnElementsOfAnalyticsPage("userMenuButtonWithoutImage");
		} else {
			clickOnElementsOfAnalyticsPage("userMenuButtonWithImage");
		}
		String createdByValue = getTextOfAnalyticsPage("loggedUserNew");
		if (uiVersion.equalsIgnoreCase("VENEER3")) {
			clickOnElementsOfAnalyticsPage("browseTab");
		}
		clickOnElementsOfAnalyticsPage("createButton");
		// Select Category
		sleeper(5000);
		selectCategoryForAnalyticsPage(Category);
		// Select Sub - Category
		selectSubCategoryForAnalyticsPage(subcategory, languageCode);
		// Select Options
		sleeper(1000);
		selectOptionForAnalyticsPage(options, languageCode);
		// Entering report name
		clickOnElementsOfAnalyticsPage("reportName");
		if (getAttributesOfAnalyticsPage("reportName", "value").isEmpty())
			enterTextForAnalyticsPage("reportName", reportNameLatest);
		else
			reportNameLatest = getAttributesOfAnalyticsPage("reportName", "value");
		// Getting value for company name
		String companyValue = reportcompanyvalue;
		// Adding all the values to the arraylist
		String categoryValue = getTextOfAnalyticsPage("categoryDropdown");
		reportParameters.add(categoryValue);
		String subCategoryValue = getTextOfAnalyticsPage("subcategoryDropdown");
		reportParameters.add(subCategoryValue);
		String optionsValue = getTextOfAnalyticsPage("optionDropdown");
		reportParameters.add(optionsValue);
		reportParameters.add(companyValue);
		reportParameters.add(createdByValue);
		reportParameters.add(reportNameLatest);
		// Removing the filters if any
		waitUntilElementIsVisibleOfAnalyticsPage("spinner");
		sleeper(3000);
		if (!getDriver().findElements(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
				.isEmpty()) {
			do {
				scrollTillViewAnalyticsPage("optionDropdown");
				getDriver().findElement(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
						.click();
			} while (getDriver()
					.findElements(By.xpath(analyticsPagePropertiesPageProperties.getProperty("deleteFilter")))
					.size() != 0);
		}
		clickByJavaScriptOnAnalyticsPage("pdfradiobutton");
		LOGGER.info("PDF Report option is selected");
		sleeper(3000);
		clickByJavaScriptOnAnalyticsPage("optout");
		LOGGER.info("Do not show device details table in report checkbox checked");
		try {
			// Save Report
			boolean isClickable = verifyIfElementIsClickableOfAnalyticsPage("saveButton");
			if (isClickable == false) {
				throw new NoSuchElementException();
			} else {
				clickOnElementsOfAnalyticsPage("saveButton");
				verifyElementsOfAnalyticsPage("customReportsCreatedList");
			}
		}
			catch (NoSuchElementException e) {
				scrollDownPage();
				LOGGER.error("Exception occured in saving report, Save button is disabled");
			}

		//clickOnElementsOfAnalyticsPage("reportName");
	} catch (Exception e) {
		LOGGER.error("Exception occurred in creating Report : " + e.getMessage());
	}
	return reportParameters;
}


/**
 * @param tab
 * @param columnname
 * @param value
 * @return
 */
public final boolean verifyGridColumnValues(String tab, String columnname, String value) {
	List<String> actualListofColumnNames = new ArrayList<String>();
	List<String> actualListofColumnValues = new ArrayList<String>();
	int index = 0;
	boolean ispresent = false;
	String gridColumnValueRows = null;

	try {

		if (tab.equals("Details")) {
			actualListofColumnNames = getTextAsListOnAnalyticsPage("detailsTabHseaderList");
			gridColumnValueRows = "[xpath]://div[@id='reportGrid2']//tbody/tr//td[{index}]/div";
		} else if (tab.equals("byUpdate")) {
			actualListofColumnNames = getTextAsListOnAnalyticsPage("byupdateheaderList");
			gridColumnValueRows = "[xpath]://div[@id='reportGrid1']//tbody/tr//td[{index}]/div";
		}
		for (int i = 0; i < actualListofColumnNames.size(); i++) {
			if (actualListofColumnNames.get(i).equalsIgnoreCase(columnname)) {
				index = i + 2;
				break;
			}
		}
		String strColumnName = gridColumnValueRows.replace("{index}", Integer.toString(index));
		moveToElements(strColumnName);
		actualListofColumnValues = getallTextBy(strColumnName);
		for (String stractualListofColumnValues : actualListofColumnValues) {
			if (stractualListofColumnValues.equals(value)) {
				ispresent = true;
			} else {
				ispresent = false;
				break;
			}
		}

		return ispresent;
	} catch (Exception e) {
		LOGGER.error("Exception occured in verifyGridColumnValues : " + e.getMessage());
		return false;
	}
}
	public final boolean verifyExportFunctionality(String Languagecode,String notificationMessage)
	{
		boolean flag = false;
		try {
			clickOnElementsOfAnalyticsPage("exportButton");
			clickOnElementsOfAnalyticsPage("exportBsodDevices");
			sleeper(3000);
			if(getTextOfAnalyticsPage(notificationMessage).equalsIgnoreCase(getTextLanguage(Languagecode,"daas_ui","campaign.response.export.data.success"))){
				flag=true;
				LOGGER.info("Toast Notification generated successfully for Export of BSOD Devices.");
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

	/**
	 * This method is used to verify the number of Top Installed Apps/Apps with Most Installed Versions in chart.
	 * @param listOfInstalledAppsAndVersions: This is the column name on which sorting is to be done.
	 * @return
	 */
	public final boolean verifyNoInstalledAppsAndVersions(String listOfInstalledAppsAndVersions) {
		try {
			List<WebElement> listOfItems = getElementsOfAnalyticsPage(listOfInstalledAppsAndVersions);
			if (listOfItems.size() <= 5) {
				LOGGER.info("Correct number of Top Installed Apps/Apps with Most Installed Versions are present in chart.");
				return true;
			} else {
				LOGGER.error("Incorrect number of Top Installed Apps/Apps with Most Installed Versions are present in chart.");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred while validating number of Top Installed Apps/Apps with Most Installed Versions.", e);
			return false;
		}
	}

	/** This method will validate count of devices from chart to Grid.
	 * @param noOfDevices
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyCountOfDevices(int noOfDevices) throws Exception {
		boolean flag = false;
		sleeper(5000);
		waitUntilElementIsVisibleOfAnalyticsPage("reactSkelaton");
		String paginationText = getTextOfAnalyticsPage("paginationCount");
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

	public final boolean verifyNewExportFunctionality(String Languagecode,String notificationMessage)
	{
		boolean flag = false;
		try {
			clickByJavaScriptOnAnalyticsPage("exportButton");
			clickByJavaScriptOnAnalyticsPage("xlsxButtonInExport");
			sleeper(3000);
			if(getTextOfAnalyticsPage(notificationMessage).equalsIgnoreCase(getTextLanguage(Languagecode,"daas_ui","campaign.response.export.data.success"))){
				flag=true;
				LOGGER.info("Toast Notification generated successfully for Export of BSOD Devices.");
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

	public final boolean verifyExportFunctionalityforApplicationInventory(String Languagecode,String notificationMessage)
	{
		boolean flag = false;
		try {
			clickByJavaScriptOnAnalyticsPage("exportButton");
			sleeper(3000);
			if(getTextOfAnalyticsPage(notificationMessage).equalsIgnoreCase(getTextLanguage(Languagecode,"daas_ui","campaign.response.export.data.success"))){
				flag=true;
				LOGGER.info("Toast Notification generated successfully for Export of Applications Inventory");
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

	/**
	 * This method is used to delete the custom dashboard.
	 *
	 * @return true/false
	 */

	public final boolean deleteCustomDashboard() {
		LOGGER.info("Attempting to delete custom dashboard...");
		try {
			sleeper(2000);
			clickByJavaScriptOnAnalyticsPage("dashboardellipis");

			clickOnElementsOfAnalyticsPage("deleteDashboard");
			sleeper(1000);

			clickByJavaScriptOnAnalyticsPage("deleteDashboardButton");

			LOGGER.info("dashboard deleted successfully");
			return true;
		} catch (Exception e) {
			LOGGER.error("Failed to delete system custom dashboard", e);
			return false;
		}
	}
	
	public boolean isWidgetPresent(String key) {
		boolean isPresent = false;
		try {
			isPresent = verifyElementsOfAnalyticsPage(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!isPresent) {
		LOGGER.info(key + " is not present");
		}
		return isPresent;
		}

	/**
	 * * @param expectedColumnList - List of expected column names
	 * @param key - Locator key for column elements
	 * @return true if all expected columns are found, false otherwise
	 * @throws Exception
	 */
	public boolean verifyTableColumns(List<String> expectedColumnList, String key) throws Exception {
		try {
			List<WebElement> actualColumnList = getElementsOfAnalyticsPage(key);
			List<String> foundColumns = new ArrayList<>();
			
			// Get column text from elements
			for (WebElement we : actualColumnList) {
				foundColumns.add(we.getText().trim());
			}
			for (String expectedColumn : expectedColumnList) {
				boolean found = false;
				for (String actualColumn : foundColumns) {
					if (actualColumn.equalsIgnoreCase(expectedColumn)) {
						found = true;
						break;
					}
				}
				if (!found) {
					LOGGER.error("Column not found: " + expectedColumn);
					return false;
				}
			}
			LOGGER.info("All columns verified successfully");
			return true;
			
		} catch (Exception e) {
			LOGGER.error("Error verifying columns: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This method verifies the presence of a list of elements on the Analytics page.
	 *
	 * @param key - Locator key for the list of elements
	 * @return true if all elements are present and displayed, false otherwise
	 */
	public boolean verifyListofElementsOfAnalyticsPage(String key) {
		boolean flag= true;
		try
		{
			List<WebElement> we = getElementsTillAllElementsPresent(analyticsPagePropertiesPageProperties.getProperty(key));
			for(WebElement w: we)
			{
				if(w.isDisplayed() == false) {				
					flag= false;
					break;
				}
				else {
					jsDriver().executeScript("arguments[0].style.border='3px solid red'", w);
					LOGGER.info("All Elelments are present");
				}
			}	
		}
		catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyListofElementsOfWEXEEDashboardPage " + e.getMessage()));
			//flag =false;
		}
		return flag;

	}
	/**
	 * This method verifies if the page size is a positive integer.
	 *
	 * @param key - Locator key for the page size element
	 * @return true if page size is a positive integer, false otherwise
	 * @throws Exception
	 */
	public boolean verifyPageSize(String key) throws Exception {
		String pageSize = getTextBy(analyticsPagePropertiesPageProperties.getProperty(key));
		 int size = Integer.parseInt(pageSize);
		 boolean flag = false;
		try {
		   if (size >0) {
			   flag= true; 
		    }
		   
		} catch (NumberFormatException e) {
			LOGGER.error("Exception occurred while parsing page size: " + e.getMessage());
			flag= false;
		}
		return flag;
	}
    	/**
	 * This method selects a question from a list of elements on the Analytics page.
	 *
	 * @param key - Locator key for the list of elements
	 * @return true if a question is successfully selected, false otherwise
	 */
	public boolean selectQuestion(String key) {
		boolean flag= true;
		try
		{
			List<WebElement> we = getElementsTillAllElementsPresent(analyticsPagePropertiesPageProperties.getProperty(key));
			for(WebElement w: we)
			{
				if(w.isDisplayed() == false) {				
					flag= false;
					break;
				}
				else {
					jsDriver().executeScript("arguments[0].style.border='3px solid red'", w);
					LOGGER.info("All Elelments are present");
					w.click();
					break;
				}
			}	
		}
		catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyListofElementsOfWEXEEDashboardPage " + e.getMessage()));
			//flag =false;
		}
		return flag;
		
	}
	/**
	 * This method verifies if the score values in a specified column fall within the expected range for a given category.
	 *
	 * @param category - The score category ("Great", "Fair", "Poor")
	 * @param columnValuesLocator - Locator key for the column values
	 * @return true if all values fall within the expected range, false otherwise
	 * @throws Exception
	 */
	public final boolean verifyScoreCategory(String category, String columnValuesLocator) throws Exception {
		try {
		boolean flag = false;
		sleeper(3000);
		scrollToAnalyticsPage("dexGridTable");
		List<WebElement> columnValues = getElementsOfAnalyticsPage(columnValuesLocator);
		Set<WebElement> uniqueColumnValues = new HashSet<>(columnValues);
		
					for (WebElement element : uniqueColumnValues) {
					    String value = element.getText().trim();
					
					         // Clean and validate the input string
					if (value.isEmpty() || !value.matches("-?\\d+(\\.\\d+)?")) {
					LOGGER.error("Invalid numeric value: " + value);
					             return false;
					         }
					
					   float numericValue = Float.parseFloat(value);
					
					   switch (category.trim().toLowerCase()) {
					    case "great":
					  if (numericValue < 85) {
					     flag = false;
					     break;
					     } else {
					     flag = true;
					     }
					     break;
					
					     case "fair":
					if (numericValue <= 55 || numericValue >= 85) {
					    flag = false;
					    break;
					} else {
					    flag = true;
					}
					break;
					
					case "poor":
					if (numericValue > 55) {
					    flag = false;
					    break;
					} else {
					    flag = true;
					}
					break;
		
				 default:
				 LOGGER.error("Invalid category: " + category);
				throw new InputMismatchException("You can use: Great, Fair, Poor only");
				    }
			}
			     return flag;
			     		
			 } catch (NumberFormatException e) {
			     LOGGER.error("NumberFormatException occurred while parsing numeric value: " + e.getMessage());
			     return false;
			 } catch (Exception e) {
			     e.printStackTrace();
			     return false;
			 }
	 }
		
	 public void scrollToAnalyticsPage(String key) throws Exception {
	    scrollTillView(analyticsPagePropertiesPageProperties.getProperty(key));
	 }
	 /**
	  * This method verifies the count of devices from a chart to a grid.
	  *
	  * @param noOfDevices - The expected number of devices
	  * @param LanguageCode - The language code for localization
	  * @return true if the count matches, false otherwise
	  * @throws Exception
	  */		 
	 public final boolean verifyCountOfDevices(int noOfDevices,String LanguageCode) throws Exception {
	     boolean flag = false;
		     sleeper(5000);
		     String biosCount = null;
		     String paginationText = getTextOfAnalyticsPage("paginationCount");
			 String [] paginationsubString = paginationText.split(" ");
			 LOGGER.info("Noofdevices: "+noOfDevices);
			 LOGGER.info("SubString: "+paginationsubString[0]+" "+paginationsubString[1]);
			 String paginationClean = paginationsubString[1].replace(",","");
			 LOGGER.info("parsint: "+Integer.parseInt(paginationClean));
			 if(waitForPresenceOfElementsOfAnalyticsPage("biosTile")){
			 if(getTextOfAnalyticsPage("biosTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "wex.fleet.mgmt.recommended.missing_critical_bios_updates"))){
		     biosCount = getTextOfAnalyticsPage("raTileImpactCount");
		     if (biosCount != null && noOfDevices==(Integer.parseInt(biosCount))) {
		         LOGGER.info("Filter from View Devices button is working correctly.");
		         flag = true;
		     }else
		     {
		         LOGGER.error("Filter from View Devices button is not working correctly. Count from Charts page did not match in Grid page.");
		     }
			 } }else if(noOfDevices==Integer.parseInt(paginationClean)) {
			     flag = true;
			 }
			 else {
			     flag = false;
			     LOGGER.error("Count did not match from chart to Grid.");
			    }
			
			     return flag;
	 }
	/**
	  * This method verifies the Analytics Issues Chart and validates the count of devices from the chart to the grid.
	  *
	  * @param LanguageCode - The language code for localization
	  * @return true if the chart is verified and count matches, false otherwise
	  * @throws Exception
	  */		
	 public final boolean verifyAnalyticsIssuesChart(String LanguageCode) throws Exception {
	   boolean flag= false;
	   String noOfDevices,text= null;
	   int noOfDevicesInt=0;
			Actions action = new Actions(getDriver());
			if(verifyElementsOfAnalyticsPage("issuesChartBarLocator")) {
			List<WebElement> chartBars = getElementsOfAnalyticsPage("issuesChartBarLocator");
			sleeper(2000);
			action.moveToElement(chartBars.get(0)).build().perform();
			sleeper(5000);
			text= getTextOfAnalyticsPage("barChartTootTipText0");
				if(text.contains("BSOD")){
				action.moveToElement(chartBars.get(1)).build().perform();
				sleeper(5000);
				noOfDevices = getTextOfAnalyticsPage("barChartTootTipText1");
				noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
				chartBars.get(1).click();
					}else {
					    noOfDevices = getTextOfAnalyticsPage("barChartTootTipText1");
					    noOfDevicesInt = Integer.parseInt(noOfDevices.trim());
					    chartBars.get(0).click();
					}
				     scrollToAnalyticsPage("paginationCount");
				if (verifyCountOfDevices(noOfDevicesInt,LanguageCode)) {
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
	  /**
	  * This method validates the Installed Application Score section on the Analytics page.
	  *
	  * @return true if all elements in the section are verified, false otherwise
	  * @throws Exception
	  */
	 
	 public boolean validateInstalledApplicationScoreSection() throws Exception {
		return verifyElementsOfAnalyticsPage("installedApplicationScore") &&
			   verifyElementsOfAnalyticsPage("appWithMostCrashes1") &&
			   verifyElementsOfAnalyticsPage("appWithMostFreezes1") &&
			   verifyElementsOfAnalyticsPage("crashesAndFreezesOverTime1");
			}
	  
	  /**
	   * This method retrieves the text of a specified element on the WEX Score page.
	   * @param key
	   * @return
	   * @throws Exception
	   */
	 public final String getTextOfWEXScore(String key) throws Exception {
			return getTextBy(analyticsPagePropertiesPageProperties.getProperty(key));
	 }
}

