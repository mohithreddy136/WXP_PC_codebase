package com.daasui.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

/**
 * This class implements all the functions related to Benchmark tests.
 * 
 * @author karpev
 *
 */
public class BenchmarkPage extends CommonMethod {
	private Properties selectedLanguageProperties;
	private ObjectReader benchmarkPagePropertiesReader = new ObjectReader();
	private Properties benchmarkPageProperties;
	private Properties benchmarkTableFieldsProperties;
	private final static Logger LOGGER = LogManager.getLogger(BenchmarkPage.class);
	private BenchmarkPage instance;
	public static String uiVersion = System.getProperty("uiVersion");

	public BenchmarkPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (BenchmarkPage.class) {
				if (instance == null) {
					instance = new BenchmarkPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public BenchmarkPage(WebDriver driver) throws IOException {
		
		benchmarkPageProperties = benchmarkPagePropertiesReader.getObjectRepository("BenchmarkPageV3");
		benchmarkTableFieldsProperties = benchmarkPagePropertiesReader.getObjectRepository("BenchmarkTableFields");

	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = benchmarkPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	/**
	 * @param              language-langugae code
	 * @param localefolder -locale folder name
	 * @param key          - list of text
	 * @return arraylist object
	 * @throws Exception
	 */
	public final ArrayList<String> getTextLanguage(String language, String localefolder, String[] uibenchmarklistcolmn)
			throws Exception {
		ArrayList<String> keyValues = new ArrayList<String>();
		selectedLanguageProperties = benchmarkPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		for (int keyCounter = 0; keyCounter < uibenchmarklistcolmn.length; keyCounter++) {
			keyValues.add(selectedLanguageProperties.getProperty(uibenchmarklistcolmn[keyCounter]));
		}
		return keyValues;
	}

	public final boolean waitForElementsOfBenchmarkPage(String key) throws Exception {
		return verifyElementIsVisible(benchmarkPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsPresentBenchmarkPage(String locator) throws Exception {
		return getElementsTillAllElementsPresent(benchmarkPageProperties.getProperty(locator));
	}
	
	public final List<WebElement> getElementsBenchmarkPage(String locator) throws Exception {
		return getAllElements(benchmarkPageProperties.getProperty(locator));
	}

	public final boolean verifyElementsOfBenchmarkPage(String key) throws Exception {
		return verifyElementIsPresent(benchmarkPageProperties.getProperty(key));

	}

	public final boolean verifyElementIsVisibleOfBenchmarkPage(String key) throws Exception {
		return verifyElementIsVisible(benchmarkPageProperties.getProperty(key));
	}

	public final boolean matchTextOfBenchmarkPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(benchmarkPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfBenchmarkPage(String key) throws Exception {
		return verifyElementIsEnable(benchmarkPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfBenchmarkPage(String key) throws Exception {
		return verifyElementIsSelected(benchmarkPageProperties.getProperty(key));
	}

	public final String getTextOfBenchmarkPage(String key) throws Exception {
		return getTextBy(benchmarkPageProperties.getProperty(key));
	}

	public final String getAttributesOfBenchmarkPage(String key, String value) throws Exception {
		return getAttribute(benchmarkPageProperties.getProperty(key), value);
	}

	public final void waitUntilElementIsVisibleOfBenchmarkPage(String key) throws Exception {
		waitUntilElementIsVisible(benchmarkPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfBenchmarkPage(String key) throws Exception {
		click(benchmarkPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnBenchmarkPage(String key) throws Exception {
		clickByJavaScript(benchmarkPageProperties.getProperty(key));
	}

	public final void enterTextForBenchmarkPage(String key, String Text) throws Exception {
		enterText(benchmarkPageProperties.getProperty(key), Text);
	}

	public final void scrollTillViewBenchmarkPage(String locator) throws Exception {
		scrollTillView(benchmarkPageProperties.getProperty(locator));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleBenchmarkPage(String locator) throws Exception {
		return getElementsTillAllElementsVisible(benchmarkPageProperties.getProperty(locator));
	}

	public final int getWindowHandlesofBenchmarkPage() throws Exception {
		return getCountofWindowHandles();
	}

	public final List<WebElement> getElementsOfBenchmarkPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(benchmarkPageProperties.getProperty(key));
	}

	public final WebElement getElementOfBenchmarkPage(String key) throws Exception {
		return getElement(benchmarkPageProperties.getProperty(key));
	}

	public final boolean waitForPresenceOfElementsOfBenchmarkPage(String key) throws Exception {
		return waitUntillElementIsPresent(benchmarkPageProperties.getProperty(key));
	}

	public final boolean verifyIfElementIsClickableOfBenchmarkPage(String key) throws Exception {
		return verifyElementIsClickable(benchmarkPageProperties.getProperty(key));
	}
	
	/**
	 * This is a method to hover mouse on an element
	 * 
	 * @param key - Locator of element
	 */
	public final void mousehoverOnBenchmarkPage(String key) {
		try {
			mouseHover(benchmarkPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnBenchmarkPage " + e.getMessage()));
		}
	}
	
	/**
	 * This is a method to scroll on Benchmark page
	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnBenchmarkPage(String key) {
		try {
			scrollTillView(benchmarkPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getWebElementsOfBenchmarkPage" + e.getMessage()));
		}
	}

	
	

	/**
	 * This method creates the benchmark with specified parameters..
	 * 
	 * @param category     - Category of benchmark
	 * @param languageCode - Language code
	 * @return Created benchmark name
	 * @throws Exception
	 */
	public final String createBenchmark(String category, String languageCode) {
		String timeStamp = new SimpleDateFormat("MMMMM yyyy HH mm ss").format(new Date());
		String benchmarkName = category + timeStamp;
		try {
			clickOnElementsOfBenchmarkPage("benchmarkAdd");
			verifyElementsOfBenchmarkPage("addBenchmarkForm");
			LOGGER.info("Redirection to Add benchmark page successfull");
			clickOnElementsOfBenchmarkPage("benchmarkNameField");
			enterTextForBenchmarkPage("benchmarkNameField", benchmarkName);
			selectCategoryOfBenchmark(category);
			LOGGER.info("Selected benchmark category");
			// When data not present to create benchmark
			if (verifyElementsOfBenchmarkPage("subCategoryNoData")) {
				return null;
			}
			scrollOnBenchmarkPage("filterCriteria");
			selectSubcategoryOfBenchmark();
			sleeper(1000);
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				clickByJavaScriptOnBenchmarkPage("popOver");
			} else {
				clickByJavaScriptOnBenchmarkPage("closeLevel");
			}
						
			// verify presence of potential progress bar
			if (verifyElementsOfBenchmarkPage("potentialProgressBar")) {
				LOGGER.info("Potential progress bar present");
			} else {
				LOGGER.info("Missing potential progress bar");
			}
			// select start date,Start Date must not be date less than today -30 days
			// select today
			clickOnElementsOfBenchmarkPage("openCalenderStartDate");
			clickOnElementsOfBenchmarkPage("selectToday");
			// select end date
			selectEndDate(languageCode);
			clickOnElementsOfBenchmarkPage("saveButton");
		} catch (Exception e) {
			LOGGER.error("Exception occured in createBenchmark : " + e.getMessage());
		}
		return benchmarkName;
	}

	/**
	 * This method select end date of benchmark with condition end date should not
	 * be today+30 days
	 * 
	 * @param languageCode - Language code
	 * @throws Exception
	 */
	public final void selectEndDate(String languageCode) throws Exception {
		String dateAfter30Days;
		clickOnElementsOfBenchmarkPage("openCalenderEndDate");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +30);
		if (uiVersion.equalsIgnoreCase("VENEER3")) {
			dateAfter30Days = new SimpleDateFormat("MMM dd, YYYY", new Locale(languageCode)).format(cal.getTime());
		} else {
			dateAfter30Days = new SimpleDateFormat("MMMM dd, YYYY", new Locale(languageCode)).format(cal.getTime());
		}
		LOGGER.info("End date to be select (Today+30Days)-" + dateAfter30Days);
		selectDateBenchmark(dateAfter30Days, languageCode);
	}

	/**
	 * This method select date
	 * 
	 * @param selectDate   - Selects date from the Calendar
	 * @param languageCode - Language code
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean selectDateBenchmark(String selectDate, String languageCode) throws Exception {
		try {
			Calendar cal = Calendar.getInstance();

			String currentMonth = new SimpleDateFormat("MMMM", new Locale(languageCode)).format(cal.getTime());
			String DateArray[] = selectDate.replace(",", "").split(" ");

			if ((uiVersion.equalsIgnoreCase("VENEER3")
					&& getDriver().findElement(By.xpath("//button[@aria-label='" + currentMonth + "']")).isDisplayed())
					|| (uiVersion.equalsIgnoreCase("VENEER2") && getDriver()
							.findElement(By.xpath("//div[@title='" + currentMonth + "']")).isDisplayed())) {

				if (uiVersion.equalsIgnoreCase("VENEER3")) {
					getDriver().findElement(By.xpath("//button[@aria-label='" + currentMonth + "']")).click();
				} else {
					getDriver().findElement(By.xpath("//div[@title='" + currentMonth + "']")).click();
				}

				List<WebElement> months = getElementsTillAllElementsPresentBenchmarkPage("monthList");
				for (WebElement month : months) {
					if (month.getText().equalsIgnoreCase(DateArray[0])) {
						month.click();
						LOGGER.info("Selected Month " + DateArray[0]);
						waitForElementsOfBenchmarkPage("dateList");
						List<WebElement> monthDays = getElementsTillAllElementsPresentBenchmarkPage("dateList");
						for (WebElement monthDay : monthDays) {
							if (monthDay.getText().equalsIgnoreCase(DateArray[1].replaceAll("^0+(?!$)", ""))) {
								monthDay.click();
								LOGGER.info("Selected Date successfully -" + DateArray[1] + " " + DateArray[0]);
								return true;
							}
						}
					}
				}
			} else {
				List<WebElement> monthDays = getElementsTillAllElementsPresentBenchmarkPage("dateList");
				for (WebElement monthDay : monthDays) {
					if (monthDay.getText().equalsIgnoreCase(DateArray[1].replaceAll("^0+(?!$)", ""))) {
						monthDay.click();
						LOGGER.info("Selected Date successfully -" + DateArray[1] + " " + DateArray[0]);
						return true;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectDateBenchmark : " + e.getMessage());
		}
		LOGGER.info("Failed to select date");
		return false;
	}

	/**
	 * This method selects SubCategory of benchmark
	 * 
	 * @throws Exception
	 */
	public final void selectSubcategoryOfBenchmark() throws Exception {
		try {
			List<WebElement> element = getElementsTillAllElementsPresentBenchmarkPage("subCategoryList");
			for (WebElement we : element) {
				sleeper(3000);
				we.click();
				waitForElementsOfBenchmarkPage("selectSubCategory");
				clickOnElementsOfBenchmarkPage("selectSubCategory");
			}
			LOGGER.info("Selected all subcategories");
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectSubcategoryOfBenchmark : " + e.getMessage());
		}
	}

	/**
	 * This method selects the category to create benchmark.
	 * 
	 * @param category - Category of benchmark
	 * @throws Exception
	 */
	public final void selectCategoryOfBenchmark(String category) throws Exception {
		try {
			clickOnElementsOfBenchmarkPage("categoryDropdown");
			List<WebElement> element = getElementsTillAllElementsPresentBenchmarkPage("categoryList");
			for (WebElement we : element) {
				if (we.getText().equalsIgnoreCase(category)) {
					we.click();
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in selectCategoryOfBenchmark : " + e.getMessage());
		}
	}

	/**
	 * This method validate benchmark created successfully by searching created
	 * benchmark in the list.
	 * 
	 * @param benchmark - Created benchmark name
	 * @throws Exception
	 */
	public final boolean validateBenchmarkCreation(String benchmark) throws Exception {
		try {
			waitForPresenceOfElementsOfBenchmarkPage("benchmarkTable");

			if ((uiVersion.equalsIgnoreCase("VENEER3") && verifyElementsOfBenchmarkPage("clearSelection"))) {
				clickOnElementsOfBenchmarkPage("clearSelection");
			}
			clickOnElementsOfBenchmarkPage("benchmarkNameColumn");
			sleeper(2000);
			enterTextForBenchmarkPage("benchmarkNameColumn", benchmark);
			sleeper(3000);
			if (verifyElementsOfBenchmarkPage("benchmarkNameInList")) {
				LOGGER.info("Created benchmark displaying in the list -" + benchmark);
				return true;
			} else {
				LOGGER.info("Created benchmark not displaying in the list -" + benchmark);
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in validateBenchmarkCreation : " + e.getMessage());
		}
		return false;
	}

	/**
	 * This method creates new duplicate benchmark and verify created benchmark in
	 * the list.
	 * 
	 * @param benchmark    - Created benchmark name
	 * @param languageCode - Selected language code
	 * @throws Exception
	 */
	public final boolean verifyduplicateBenchmarkCreation(String benchmark, String languageCode) {
		try {
			// To create duplicate benchmark name, start date and end date can be changed
			waitForPresenceOfElementsOfBenchmarkPage("benchmarkNameInList");

			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				mousehoverOnBenchmarkPage("firstCheckbox");
			}

			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				clickByJavaScriptOnBenchmarkPage("selectBenchmarkInList");
			} else {
				clickOnElementsOfBenchmarkPage("selectBenchmarkInList");
			}
			
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
			    clickOnElementsOfBenchmarkPage("informationPanel");
			}
			
			waitForPresenceOfElementsOfBenchmarkPage("duplicateButton");
			clickOnElementsOfBenchmarkPage("duplicateButton");
			LOGGER.info("Clicked on DUPLICATE button");
			clickOnElementsOfBenchmarkPage("benchmarkNameField");
			String duplicateBenchmarkName = getAttributesOfBenchmarkPage("benchmarkNameField", "value");

			if (!(verifyElementsOfBenchmarkPage("disabledCategory")
					&& verifyElementsOfBenchmarkPage("disabledSubCategory"))) {
				LOGGER.info("Fields should not be enabled");
				return false;
			}
			// select start date,Start Date must not be date less than today -30 days
			// select today
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				scrollOnBenchmarkPage("duration");
			}
			clickOnElementsOfBenchmarkPage("openCalenderStartDate");
			clickOnElementsOfBenchmarkPage("selectToday");
			// select end date
			selectEndDate(languageCode);
			clickOnElementsOfBenchmarkPage("saveButton");
			if (validateBenchmarkCreation(duplicateBenchmarkName)) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyduplicateBenchmarkCreation : " + e.getMessage());
		}
		return false;
	}

	/**
	 * This method deletes stopped benchmark and verify benchmark deleted from the
	 * list.
	 * 
	 * @param benchmark    - Created benchmark name
	 * @param languageCode - Selected language code
	 * @throws Exception
	 */
	public final boolean verifyBenchmarkDelete(String benchmark) {
		try {
			// To delete benchmark, stopped ongoing benchmark by clicking on END BENCHMARK
			// NOW button
			waitForPresenceOfElementsOfBenchmarkPage("benchmarkNameInList");
			clickOnElementsOfBenchmarkPage("benchmarkNameInList");
			waitForPageLoaded();
			clickOnElementsOfBenchmarkPage("endBenchmark");
			waitForPresenceOfElementsOfBenchmarkPage("benchmarkNameInList");
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				mousehoverOnBenchmarkPage("firstCheckbox");
			}
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				clickByJavaScriptOnBenchmarkPage("selectBenchmarkInList");
			} else {
				clickOnElementsOfBenchmarkPage("selectBenchmarkInList");
			}
			waitForPresenceOfElementsOfBenchmarkPage("deleteBenchmark");
			clickOnElementsOfBenchmarkPage("deleteBenchmark");
			LOGGER.info("Clicked on DELETE button");
			clickOnElementsOfBenchmarkPage("deleteConfirmation");
			LOGGER.info("Clicked on DELETE confirmation button");
			waitForPageLoaded();
			if (!validateBenchmarkCreation(benchmark)) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyBenchmarkDelete : " + e.getMessage());
		}
		return false;
	}

	/**
	 * This method - Verify presence of all the columns Verify column names are
	 * correct or not
	 * 
	 * @param              columnNameKeys- List of expected column names in the
	 *                     table
	 * @param languageCode - Selected language code
	 * @param folderName   - localization keys for column names referred from the
	 *                     folder
	 * @throws Exception
	 */
	public final boolean verifyBenchmarkListFields(String columnNameKeys, String languageCode, String folderName) {
		try {
			String uiBenchmarkListColumn;
			waitForPresenceOfElementsOfBenchmarkPage("benchmarkTable");
			clickOnElementsOfBenchmarkPage("tableConfig");

			if (verifyElementIsEnableOfBenchmarkPage("resetButton")) {

				clickOnElementsOfBenchmarkPage("resetButton");
				LOGGER.info("Clicked on reset default button");
				clickOnElementsOfBenchmarkPage("saveColulmns");
				waitForPageLoaded();
				clickOnElementsOfBenchmarkPage("tableConfig");
			}

			List<WebElement> element1 = getElementsBenchmarkPage("tableConfigColumncheckboxList");
			List<WebElement> element2 = getElementsBenchmarkPage("tableConfiglistOfColumns");
			for (int count = 0; count < element1.size(); count++) {
				if (!element1.get(count).isSelected()) {
					element2.get(count).click();
				}
			}
			clickOnElementsOfBenchmarkPage("saveColulmns");
			List<WebElement> uibenchmarklistColmn = getElementsTillAllElementsPresentBenchmarkPage(
					"benchmarkListcolumns");
			String[] expectedBenchmarklistcolmns = benchmarkTableFieldsProperties.getProperty(columnNameKeys)
					.split(":");
			ArrayList<String> expectedBenchmarklistcolumns = getTextLanguage(languageCode, folderName,
					expectedBenchmarklistcolmns);
			if (uibenchmarklistColmn.size() == expectedBenchmarklistcolumns.size()) {
				LOGGER.info("Total no. of columns are matching with the expected");
			} else {
				LOGGER.info("Total no. of columns not matching with the expected, Expected-"
						+ expectedBenchmarklistcolumns.size() + " Actual-" + uibenchmarklistColmn.size());
				return false;
			}

			for (int counter = 0; counter < expectedBenchmarklistcolumns.size(); counter++) {
				sleeper(500);
				uiBenchmarkListColumn = uibenchmarklistColmn.get(counter).getText();
				if (expectedBenchmarklistcolumns.get(counter).equalsIgnoreCase(uiBenchmarkListColumn)) {
				} else {
					LOGGER.info("Mismatch in column titles - Expected-" + expectedBenchmarklistcolumns.get(counter)
							+ " Actual -" + uiBenchmarkListColumn);
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyBenchmarkListFields : " + e.getMessage());
		}
		LOGGER.info("All column titles are successfully verified");
		return true;
	}

	/**
	 * This method - Click on the benchmark name to verify tabs- OVERVIEW, BY MODEL,
	 * BY OS, TOP 10, DETAILS
	 * 
	 * @param expectedtabName - List of expected column names in the table
	 * @param tabName         - Name of tab to be selected
	 * 
	 * @throws Exception
	 */
	public final boolean verifyBenchmarkDetails(String expectedtabName, String tabName) throws Exception {
		waitForPresenceOfElementsOfBenchmarkPage("benchmarkName");
		clickOnElementsOfBenchmarkPage("benchmarkName");
		// Verify tab name
		if (uiVersion.equalsIgnoreCase("VENEER2")) {
			getDriver().navigate().refresh();
			sleeper(3000);
			waitForPresenceOfElementsOfBenchmarkPage("tabName");
		}
		clickOnElementsOfBenchmarkPage(tabName);
		if (!getTextOfBenchmarkPage(tabName).equalsIgnoreCase(expectedtabName)) {
			LOGGER.info("Tab name not matched Expected -" + expectedtabName + " Actual -"
					+ getTextOfBenchmarkPage(tabName));
			return false;
		}
		LOGGER.info("Clicked on Tab -" + expectedtabName);
		return true;
	}

	/**
	 * This method - Verify OVERVIEW tab Verify health Metric title Verify presence
	 * of health Metrics Verify correct benchmark details showing on the tab
	 * 
	 * @param benchmark    - Created benchmark name
	 * @param languageCode - Selected language code
	 * @param folderName   - localization keys for column names referred from the
	 *                     folder
	 * 
	 * @throws Exception
	 */
	public final boolean verifyBenchmarkOverviewtabDetails(String benchmark, String languageCode, String folderName)
			throws Exception {
		// Verify health metric title
		if (getTextOfBenchmarkPage("healthMetricTitle").equalsIgnoreCase(
				getTextLanguage(languageCode, folderName, "benchmark.overview.health-metrics.panel-title"))) {
			LOGGER.info("Health Metrics title matched");
		} else {
			LOGGER.info("Health Metrics title not matched");
			return false;
		}
		// Verify presence of health metrics
		if (verifyElementsOfBenchmarkPage("healthMetric")) {
			LOGGER.info("Health metrics present");
		} else {
			LOGGER.info("Missing health metrics");
			return false;
		}
		// Verify Overview tab showing correct benchmark details title
		if (getTextOfBenchmarkPage("overviewBenchtitle").equalsIgnoreCase(benchmark)) {
			LOGGER.info("Overview tab, benchmark title matched");
		} else {
			LOGGER.info("Overview tab, benchmark title not matched");
			return false;
		}
		return true;
	}

	/**
	 * This method - Verify TOP 10 tab Verify TOP 10 table titles Verify all tables
	 * columns
	 * 
	 * @param benchmark    - Created benchmark name
	 * @param languageCode - Selected language code
	 * @param folderName   - localization keys for column names referred from the
	 *                     folder
	 * 
	 * @throws Exception
	 */
	public final boolean verifyBenchmarkTOP10Details(String benchmark, String languageCode, String folderName)
			throws Exception {
		// Verify TOP 10 table titles
		List<WebElement> uiTop10TableColumns;
		String[] top10TableNames = { "benchmark.top10.table_header.os", "benchmark.top10.table_header.bios",
				"benchmark.top10.table_header.driver", "benchmark.top10.table_header.software_application" };
		ArrayList<String> expectedtop10TableNameList = getTextLanguage(languageCode, folderName, top10TableNames);
		List<WebElement> uiTop10TableNames = getElementsTillAllElementsPresentBenchmarkPage("top10TableTitles");
		for (int count = 0; count < uiTop10TableNames.size(); count++) {
			if (!uiTop10TableNames.get(count).getText().equalsIgnoreCase(expectedtop10TableNameList.get(count))) {
				LOGGER.info("TOP 10 table title mismatched- Expected -" + expectedtop10TableNameList.get(count)
						+ " Actual -" + uiTop10TableNames.get(count).getText());
				return false;
			}
		}
		LOGGER.info("TOP 10 table titles are matched with expected");
		// Verify table columns
		String[] expectedTop10TableColumns = { "benchmark.top10.table.software_name",
				"benchmark.top10.table.software_version", "benchmarkByOS.list.deviceCount" };
		ArrayList<String> expectedTop10TableColumnList = getTextLanguage(languageCode, folderName,
				expectedTop10TableColumns);
		for (int counter = 1; counter <= expectedtop10TableNameList.size(); counter++) {
			if (uiVersion.equalsIgnoreCase("VENEER3")) {
				uiTop10TableColumns = getDriver().findElements(
						By.xpath("(//div[@class='benchmark-top10']/div[1]//table[1])[" + counter + "]//tr/th[@id]"));
			} else {
				uiTop10TableColumns = getDriver()
					.findElements(By.xpath("//div[@class='benchmark-top10']/div[" + counter + "]//table//th[@id]"));
			}
			for (int counter1 = 0; counter1 < uiTop10TableColumns.size(); counter1++) {
				if (uiTop10TableColumns.get(counter1).getText()
						.equalsIgnoreCase(expectedTop10TableColumnList.get(counter1))) {
				} else if (counter == 2) // BIOS table
				{
					if (uiTop10TableColumns.get(counter1).getText()
							.equalsIgnoreCase(expectedTop10TableColumnList.get(counter1 + 1))) {
					} else {
						LOGGER.info(
								"Column names are not matched for table -" + expectedtop10TableNameList.get(counter1));
						LOGGER.info("Expected -" + expectedTop10TableColumnList.get(counter1 + 1));
						LOGGER.info("Actual -" + uiTop10TableColumns.get(counter1).getText());
						return false;
					}
				} else {
					LOGGER.info("Column names are not matched for table -" + expectedtop10TableNameList.get(counter1));
					LOGGER.info("Expected -" + expectedTop10TableColumnList.get(counter1));
					LOGGER.info("Actual -" + uiTop10TableColumns.get(counter1).getText());
					return false;
				}
			}
		}
		LOGGER.info("All tables column names are matched on TOP 10 tab");
		return true;
	}
}
