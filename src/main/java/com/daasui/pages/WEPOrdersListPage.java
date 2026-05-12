package com.daasui.pages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.basesource.action.CommonMethod;
import com.basesource.utils.AzureUtil;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.ObjectReader;
import com.basesource.utils.CSVFileReader.ACGenerationVariant;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;

public class WEPOrdersListPage extends CommonMethod {
	
	private Properties selectedLanguageProperties;
	private ObjectReader orderslistPagePropertiesReader = new ObjectReader();
	private Properties ordersListPageProperties;
	private static Logger LOGGER = LogManager.getLogger(WEPOrdersListPage.class);
	
	private WEPOrdersListPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public WEPOrdersListPage getInstance() throws IOException {
		if (instance==null) {
			synchronized(WEPOrdersListPage.class) {
				if(instance==null) {
					instance = new WEPOrdersListPage(DRIVER);
				}
				
			}
			
		}
		return instance;
	}
	
	public WEPOrdersListPage(WebDriver driver) throws IOException {
		ordersListPageProperties = orderslistPagePropertiesReader.getObjectRepository("WEPOrdersListPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = orderslistPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}
	
	public final int getTotalRowCount() throws Exception {
		return getCountOfRows(ordersListPageProperties.getProperty("allRowCount"));
	}
	
	public final void clickOnElementsOfOrdersListPage(String key) throws Exception {
		click(ordersListPageProperties.getProperty(key));
	}
	
	public final String getTextOfOrderListPage(String key) throws Exception {
		return getTextBy(ordersListPageProperties.getProperty(key));
	}
	
	public final boolean verifyElementsOfordersListPage(String key) throws Exception {
		return verifyElementIsPresent(ordersListPageProperties.getProperty(key));
	}
	
	public final boolean verifyElementIsVisibleOfordersListPage(String key) throws Exception {
		return verifyElementIsVisible(ordersListPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsInvisibleOfordersListPage(String key) throws Exception {
		return verifyElementIsinvisibile(ordersListPageProperties.getProperty(key));
	}
	
	public final boolean verifyTextPresentOnElementOforderListPage(String key, String text) throws Exception {
		return verifyTextPresentOnElement(ordersListPageProperties.getProperty(key), text);
	}
	
	public final void clickByJavaScriptOnElementOforderListPage(String key) throws Exception {
		clickByJavaScript(ordersListPageProperties.getProperty(key));
		
	}
	
	public final boolean verifyElementIsClickableOforderListPage(String key) throws Exception {
		return verifyElementIsClickable(ordersListPageProperties.getProperty(key));
	}
	
	
	public final void enterTextwithoutclearOforderListPage(String key, String text) throws Exception {
		enterTextwithoutclear(ordersListPageProperties.getProperty(key), text);
	}
	
	public final void enterTextOforderListPage(String key, String text) throws Exception {
		enterText(ordersListPageProperties.getProperty(key), text);
	}
	
	public final boolean selectElementFromDropDownOrderListPage(String dropdownId, String key, String text) throws Exception {
		return selectFromDropdown(ordersListPageProperties.getProperty(dropdownId), ordersListPageProperties.getProperty(key), text);
	}
	
	public final boolean selectDropdownOptionsOrderListPage(String key, String text) throws Exception {
		return selectDropdownOptions(ordersListPageProperties.getProperty(key), text);
	}
	
	
	/*
	 * This method verifies if user is on Orders List Page.
	 * @return True. False if webelements are not visible
	 * @throws Exception
	 */
    public final boolean isAtOrdersListPage() throws Exception {
        waitForPageLoaded();
        if (!verifyElementIsVisibleOfordersListPage("allRowCount")) {
            return false;
        }
        if (!verifyElementIsVisibleOfordersListPage("addButton") && System.getProperty("environment").startsWith("US")) {
            return false;
        }
        if (!verifyElementIsVisibleOfordersListPage("columnHeaderNames")) {
            return false;
        }
        return true;
    }
	
	public final boolean matchTextOfOrdersListPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElementOforderListPage(ordersListPageProperties.getProperty(key), Text);
	}
	
	
	/**
	 * This method is used to click Add button on Orders List Page.
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 */
	public boolean ClickAddButtonOrdersListPage() {
		try {
			if(verifyElementIsVisibleOfordersListPage("addButton")) {
				clickOnElementsOfOrdersListPage("addButton");
			}
			return verifyElementIsVisibleOfordersListPage("newOrderOption");
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportOrders " + e.getMessage()));
			return false;
		}
		
	}
	
	
	/**
	 * This method is used to validate contents of Add Order modal
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 */
	public boolean verifyAddOrdersModal(String languageCode) {
		try {
			
			if(!verifyElementIsVisibleOfordersListPage("addOrderTitle")) {
				LOGGER.info("Add Orders modal is not visible");
				return false;
				}
			if(!verifyTextPresentOnElementOforderListPage("addOrderTitle",getTextLanguage(languageCode, "daas_ui", "orders.add.orders.title"))) {
				LOGGER.info("Mismatch in Title on Add Orders modal.");
				return false;
			}
			if(!verifyTextPresentOnElementOforderListPage("addOrderSubtitle",getTextLanguage(languageCode, "daas_ui", "orders.add.orders.sub.title"))) {
				LOGGER.info("Mismatch in Sub-Title on Add Orders modale");
				return false;
			}
			if(!verifyTextPresentOnElementOforderListPage("addOrderDescription",getTextLanguage(languageCode, "daas_ui", "orders.add.orders.sub.discription"))) {
				LOGGER.info("Mismatch in Orders description on Add Orders modal");
				return false;
			}
			if(!verifyElementIsClickableOforderListPage("downloadSampleFileLink")) {
				LOGGER.info("Download sample link is clickable");
				return false;
			}
			if(!verifyElementIsVisibleOfordersListPage("cancelOrderButton")) {
				LOGGER.info("Cancel order button is not visible");
				return false;
			}
			if(!verifyElementIsVisibleOfordersListPage("importOrderButton")) {
				LOGGER.info("Import order button is not visible");
				return false;
			}
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyAddOrdersModal " + e.getMessage()));
			return false;
		}
	}
	
	/**
	 * This method is used to validate contents of Add Order modal based on order
	 * type
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 */
	// In WEPOrdersListPage (add near other modal utilities)
	public boolean validateImportModalForOrderType(String orderType) {
	    try {
			
	        // Handle order types with separate buttons
	        switch (orderType) {
	        	case "Device Transfer":
					if(verifyElementIsVisibleOfordersListPage("deviceTransferOption")) {
                		clickOnElementsOfOrdersListPage("deviceTransferOption");
            		}	
	        		break;
	        	case "Reseller Update":
	        		if(verifyElementIsVisibleOfordersListPage("resellerUpdateOption")) {
                		clickOnElementsOfOrdersListPage("resellerUpdateOption");
            		}
	        		break;
				case "Remove License":
	        		if(verifyElementIsVisibleOfordersListPage("removeLicenseOption")) {
                		clickOnElementsOfOrdersListPage("removeLicenseOption");
            		}
	        		break;
	        	case "Data Collection":
	        		if(verifyElementIsVisibleOfordersListPage("dataCollectionOption")) {
                		clickOnElementsOfOrdersListPage("dataCollectionOption");
            		}
	        		break;
	        	default:
	        		// Open Add modal for dropdown order types
		        	if(!ClickAddButtonOrdersListPage()){
						LOGGER.info("Unable to click Add button on Orders page.");
						}
		        	sleeper(200);
					if(this.verifyElementIsVisibleOfordersListPage("addButtonOptions")){
						selectDropdownOptionsOrderListPage("addButtonOptions", orderType);
					}
					break;
	        }
	        // Expected UI texts (hard-coded English fallback)
	        String expectedTitle = null;
	        String expectedSubDesc = null;
	        String expectedLongDesc = null;
	        String expectSampleFileLinkText = null;
	        String expectSampleFileName = null;
	        
	        switch (orderType) {
	            case "New Order - Active Care":
	                expectedTitle = "Add Orders";
	                expectedSubDesc = "Upload device orders CSV file from WWCAMS.";
	                expectedLongDesc = "Import a list of orders. Adding orders across multiple companies is supported.";
	                expectSampleFileLinkText = "click here to download the WWCAMS sample file";
	                expectSampleFileName = "Sample_Care_Pack_Registration.csv";
	                break;
	            case "New Order - Premium":
	                expectedTitle = "Add Orders";
	                expectedSubDesc = "Upload device orders CSV file from WWCAMS.";
	                expectedLongDesc = "Import a list of orders. Adding orders across multiple companies is supported.";
	                expectSampleFileLinkText = "click here to download the Premium sample file";
	                expectSampleFileName = "Sample_Care_Pack_Registration.csv";
	                break;
	            case "New Order - Premium Plus":
	                expectedTitle = "Add Orders";
	                expectedSubDesc = "Upload device orders CSV file from WWCAMS.";
	                expectedLongDesc = "Import a list of orders. Adding orders across multiple companies is supported.";
	                expectSampleFileLinkText = "click here to download the Premium Plus sample file";
	                expectSampleFileName = "Sample_Care_Pack_Registration.csv";
	                break;
	            case "New Order - Out Of Band Remediation":
	                expectedTitle = "Add Out Of Band Remediation Order";
	                expectSampleFileLinkText = "click here to download the Out Of Band Remediation sample file";
	                expectSampleFileName = "Sample_Care_Pack_Registration.csv";
	                break;
				case "New Order - Essentials":
	                expectedTitle = "Add Orders";
	                expectedSubDesc = "Upload device orders CSV file from WWCAMS.";
	                expectedLongDesc = "Import a list of orders. Adding orders across multiple companies is supported.";
	                expectSampleFileLinkText = "click here to download the Essentials sample file";
	                expectSampleFileName = "Sample_Care_Pack_Registration.csv";
	                break;
	            case "Device Transfer":
	            	expectedTitle = "Add Device Transfers Order";
	            	expectedSubDesc = "Adding orders across multiple companies is supported";
	                expectSampleFileLinkText = "click here to download the Device Transfer sample file";
	                expectSampleFileName = "Sample_Device_Transfer.csv";
	                break;
	            case "Reseller Update":
	            	expectedTitle = "Add Reseller Update Order";
	                expectSampleFileLinkText = "click here to download the Reseller Update sample file";
	                expectSampleFileName = "Sample_Reseller_Update.csv";
	                break;
				case "Remove License":
	            	expectedTitle = "Add Remove License Order";
	                expectSampleFileLinkText = "click here to download the Remove License sample file";
	                expectSampleFileName = "Sample_Remove_License.csv";
	                break;
	            case "Data Collection":
	            	expectedTitle = "Add Data Collection Order";
	                expectSampleFileLinkText = "click here to download the Data Collection sample file";
	                expectSampleFileName = "Sample_Data_Collection.csv";
	                break;
	            default:
	                LOGGER.info("Unsupported order type: {}", orderType);
	                return false;
	        }

	        if (expectedTitle != null) {
	            if (!verifyTextPresentOnElementOforderListPage("addOrderTitle", expectedTitle)) {
	                LOGGER.info("Modal Title mismatch for order type {}", orderType);
	                return false;
	            }
	            LOGGER.info("Modal Title matches for order type {}", orderType);
	        }

	        if (expectedSubDesc != null) {
	            if (!verifyTextPresentOnElementOforderListPage("addOrderSubtitle", expectedSubDesc)) {
	                LOGGER.info("Sub-description mismatch for order type {}", orderType);
	                return false;
	            }
	            LOGGER.info("Sub-description matches for order type {}", orderType);
	        }

	        if (expectedLongDesc != null) {
	            if (!verifyTextPresentOnElementOforderListPage("addOrderDescription", expectedLongDesc)) {
	                LOGGER.info("Long description mismatch for order type {}", orderType);
	                return false;
	            }
	            LOGGER.info("Long description matches for order type {}", orderType);
	        }
	        
	        if (expectSampleFileLinkText != null) {
	            if (!verifyTextPresentOnElementOforderListPage("downloadSampleFileLink", expectSampleFileLinkText)) {
	                LOGGER.info("Sample File download link text mismatch for order type {}", orderType);
	                return false;
	            }
	            LOGGER.info("Sample File download link text matches for order type {}", orderType);
	        }

	        if (!verifyElementIsClickableOforderListPage("downloadSampleFileLink")) {
	            LOGGER.info("Sample CSV hyperlink not clickable.");
	            return false;
	        }
	        
	        return downloadAndVerifySampleFile(expectSampleFileName);
	        
	        
	    } catch (Exception e) {
	        LOGGER.error("Exception in validateImportModalForOrderType: ", e);
	        return false;
	    }
	}


	// java
	// Add inside `WEPOrdersListPage`
	public boolean downloadAndVerifySampleFile(String expectedFileName) {
	    try {
	        if (expectedFileName == null) {
	            LOGGER.error("expectedFileName is null");
	            return false;
	        }
	        
	        Path downloadDir = Paths.get(ConstantPath.DOWNLOAD_FOLDER_PATH);
	        // Prepare download directory
	        if (!Files.exists(downloadDir)) {
	            Files.createDirectories(downloadDir);
	        }

	        Path target = downloadDir.resolve(expectedFileName);

	        // Remove stale previous file
	        deleteIfExistsSilently(target);

	        // Trigger download
	        clickOnElementsOfOrdersListPage("downloadSampleFileLink");

	        // Wait for exact file (30s)
	        Path downloaded = waitForExactFile(target, 30_000);
	        if (downloaded == null) {
	            LOGGER.error("Timed out waiting for file: {}", expectedFileName);
	            return false;
	        }

	        // Basic validations
	        if (!Files.isRegularFile(downloaded)) {
	            LOGGER.error("Downloaded path is not a regular file: {}", downloaded);
	            return false;
	        }
	        if (Files.size(downloaded) == 0) {
	            LOGGER.error("Downloaded file is empty: {}", downloaded);
	            return false;
	        }

	        LOGGER.info("Sample file downloaded: {}", downloaded.getFileName());

	        // Delete after verification
	        deleteIfExistsSilently(downloaded);
	        LOGGER.info("Sample file deleted: {}", expectedFileName);
	        
	        if (verifyElementIsVisibleOfordersListPage("cancelOrderButton")) {
	            clickOnElementsOfOrdersListPage("cancelOrderButton");
	        }
	        return true;
	    } catch (Exception e) {
	        LOGGER.error("Exception in downloadAndVerifySampleFile", e);
	        return false;
	    }
	}

	
	private void deleteIfExistsSilently(Path p) {
	    try {
	        if (Files.exists(p)) {
	            Files.delete(p);
	        }
	    } catch (IOException ex) {
	        LOGGER.warn("Failed deleting existing file: {}", p);
	    }
	}

	private Path waitForExactFile(Path expected, long timeoutMs) throws InterruptedException {
	    long start = System.currentTimeMillis();
	    while (System.currentTimeMillis() - start < timeoutMs) {
	        boolean partial =
	            Files.exists(Path.of(expected.toString() + ".crdownload")) ||
	            Files.exists(Path.of(expected.toString() + ".download")) ||
	            Files.exists(Path.of(expected.toString() + ".tmp"));

	        if (Files.exists(expected) && !partial) {
	            return expected;
	        }
	        sleeper(500);
	    }
	    return null;
	}

	
	/**
	 * This method is used to validate flow of importing the orders
	 * 
	 * @param orderType
	 * @param languageCode
	 * @param fileName
	 */
	public boolean verifyImportCSVOrdersListPage(String languageCode, String orderType, String fileName) {
		try {

            switch(orderType){
                case "Device Transfer":
                    if (!verifyElementIsVisibleOfordersListPage("addDeviceTransferTitle")) {
                        this.clickDeviceTransferButtonOrdersListPage();
                        LOGGER.info("Clicked on Device Transfer");
                        sleeper(200);
                    }
                    break;
				case "Reseller Update":
                    this.clickResellerUpdateButtonOrdersListPage();
                    LOGGER.info("Clicked on Reseller Update");
                    sleeper(200);
                    break;
                default:
                    if(!verifyElementIsVisibleOfordersListPage("addOrderTitle")){
                        this.ClickAddButtonOrdersListPage();
                        LOGGER.info("Clicked on Add button");
                        sleeper(200);
                        if(this.verifyElementIsVisibleOfordersListPage("addButtonOptions")){
                            selectDropdownOptions(ordersListPageProperties.getProperty("addButtonOptions"), orderType);
                        }
                    }
            }
			
//			this.enterTextwithoutclearOforderListPage("fileImport", ConstantPath.IMPORT_PATH+fileName);
			WebElement addFile = getElement(ordersListPageProperties.getProperty("fileImport"));
			addFile.sendKeys(ConstantPath.IMPORT_PATH+fileName);
			sleeper(200);
			clickOnElementsOfOrdersListPage("importOrderButton");
			LOGGER.info("Clicked on submit import button");
			sleeper(5000);
			refreshPage();
			waitForPageLoaded();
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportOrders " + e.getMessage()));
			return false;
		}
		
	}

	
	
	/**
	 * This method is used to fetch row number if import record is present & displayed in orders list.
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 */
	public int getImportRecordRowNumberInList(String languageCode, String fileName) {
		int i = 0;
		try {
			int row_count=0;
			if(verifyElementIsVisibleOfordersListPage("allRowCount")) {
				row_count = getTotalRowCount();
				
			}else {
				LOGGER.info("User is not on Orders List Page");
				return i;
			}
			
			for(int j=1; j<= row_count; j++) {
				if(fileName.equals(getTextBy(String.format(ordersListPageProperties.getProperty("eachRowFileName"), String.valueOf(j))))) {
					LOGGER.info(fileName + " record is present in OrdersList Page.");
					return j;
					}
			}
			LOGGER.info(fileName + " record is not present in OrdersList Page.");
			return i;
			} 
		catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyImportOrders " + e.getMessage()));
			return i;
			}
		
		}
	
	
	/**
	 * This method is used to click import record if present & displayed in orders list.
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 * @throws Exception 
	 */
	public boolean clickImportRecordInList(int row_number) throws Exception {
		
		if (row_number !=0 && verifyElementIsVisible(String.format(ordersListPageProperties.getProperty("eachRowFileName"), String.valueOf(row_number)))) {
			click(String.format(ordersListPageProperties.getProperty("eachRowFileName"), String.valueOf(row_number)));
			return true;
		}else {
			LOGGER.info("Import record is not present in orders list");
			return false;
		}
	}
	
	
	/**
	 * This method is used to click import record if present & displayed in orders list.
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 * @throws Exception 
	 */
	public boolean checkOrderListPageUIDetails(int row_number, String file_row_count, String filename) throws Exception {
		boolean flag = true;
		String filename_locator = String.format(ordersListPageProperties.getProperty("eachRowFileName"), String.valueOf(row_number));
		this.scrollTillView(filename_locator);
		if (row_number !=0 && verifyElementIsVisible(filename_locator)) {
			String actualFilename = this.getTextBy(filename_locator);
			if(!actualFilename.equals(filename)) {
				LOGGER.info("File name does not match in OrdersListPage.");
				flag=false;}
			String num_orders_locator = String.format(ordersListPageProperties.getProperty("eachRowNumberOfOrders"), String.valueOf(row_number));
			if(!this.getTextBy(num_orders_locator).equals(file_row_count)) {
				LOGGER.info("Orders count does not match in OrdersListPage.");
				flag=false;}
			String source_locator = String.format(ordersListPageProperties.getProperty("eachRowSource"), String.valueOf(row_number));
			if(!this.getTextBy(source_locator).equals("Manual")) {
				LOGGER.info("Source does not match in OrdersListPage.");
				flag=false;}
			String date_time_locator = String.format(ordersListPageProperties.getProperty("eachRowDateTime"), String.valueOf(row_number));
			if(!verifyElementIsVisible(date_time_locator)) {
				LOGGER.info("DateTime column value is not visible on OrderList page.");
				flag=false;}
			String status_locator = String.format(ordersListPageProperties.getProperty("eachRowStatus"), String.valueOf(row_number));
			if(!verifyElementIsVisible(status_locator)) {
				LOGGER.info("Status column value is not visible on OrderList page.");
				flag=false;}
		}else {
			flag = false;
		}
		if(!flag) {
			LOGGER.info("User is not on OrderList page.");
			return flag;
		}
		LOGGER.info("File uploaded record matches with OrderListPage UI data.");
		return flag;
	}
	

	/**
	 * This method is used to search import record if present & displayed in orders list.
	 * 
	 * @param filename
	 * @param languageCode
	 * @param fileName
	 * @throws Exception 
	 */
	public int searchOrder(String languageCode, String filename){
		int row_number = 0;
		try {
		if(!this.verifyElementIsVisibleOfordersListPage("searchField")) {
			LOGGER.info("Search field is not visible on Orders list page.");
			return 0;
		}
		if(this.verifyElementIsVisibleOfordersListPage("clearfilters")){
			this.clickOnElementsOfOrdersListPage("clearfilters");
			this.verifyElementIsinvisibile(ordersListPageProperties.getProperty("clearfilters"));
		}
		this.enterTextOforderListPage("searchField", filename);
		sleeper(1000);
		row_number = this.getImportRecordRowNumberInList(languageCode, filename);
		if(row_number==0) {
			LOGGER.info("Filename not found in Orders list.");
			return 0;
		}
		}
		catch(Exception e) {
            LOGGER.error("Exception in retryWaitUntilInVisibleOrdersListPage method :" + e.toString());
            return 0;
		}
		LOGGER.info("Searched record is found in Orders list.");
		return row_number;
		}
	
	/**
	 * This method is used to search import record if present & displayed in orders list.
	 * 
	 * @param filename
	 * @param languageCode
	 * @param fileName
	 * @throws Exception 
	 */
	public boolean filterOrder(String status, String type) throws Exception {
		if(this.verifyElementIsVisibleOfordersListPage("clearfilters")){
			this.clickOnElementsOfOrdersListPage("clearfilters");
			this.verifyElementIsinvisibile(ordersListPageProperties.getProperty("clearfilters"));
		}
		sleeper(500);
		if(!this.verifyElementIsVisibleOfordersListPage("statusDropdown")) {
			LOGGER.info("Source dropdown field is not visible on Orders list page.");
			return false;
		}
		if(this.verifyElementIsVisibleOfordersListPage("statusDropdown")){
			this.clickByJavaScriptOnElementOforderListPage("statusDropdown");
			this.selectElementFromDropDownOrderListPage("statusDropdown", "statusDropdownOptions", status);
			
		}
		sleeper(1000);
		if(this.verifyElementIsVisibleOfordersListPage("typeDropdown")){
			this.clickByJavaScriptOnElementOforderListPage("typeDropdown");
			this.selectElementFromDropDownOrderListPage("typeDropdown", "statusDropdownOptions", type);
		}
		LOGGER.info("Orders list is filtered successfully.");
		return true;
		
		}
	


	/**
	 * This method is used to search import record if present & displayed in orders list.
	 * 
	 * @param filename
	 * @param languageCode
	 * @param fileName
	 * @throws Exception 
	 */
	public boolean downloadOrderSummaryFile(int row_number) throws Exception {
		this.scrollTillView(String.format(ordersListPageProperties.getProperty("eachrowOrderSummaryFile"), String.valueOf(row_number)));
		sleeper(100);
		if (row_number !=0 && verifyElementIsVisible(String.format(ordersListPageProperties.getProperty("eachrowOrderSummaryFile"), String.valueOf(row_number)))) {
			this.clickByJavaScript(String.format(ordersListPageProperties.getProperty("eachrowOrderSummaryFile"), String.valueOf(row_number)));
			if(!this.verifyElementIsVisibleOfordersListPage("downloadToastMessage")) {	
			LOGGER.info("Order Summary File did not Download successfully.");
			return false;
			}
			LOGGER.info("Order Summary File Download successfully.");
			return true;
		}else {
			LOGGER.info("Import record is not present in orders list");
			return false;
		}
	}
		
	/**
	 * @param crsid
	 * @throws Exception
	 */
	public final boolean retryWaitUntilInVisibleOrdersListPage(String locator, int wait) {
        try {
            this.retryWaitUntilInVisible(ordersListPageProperties.getProperty(locator), wait);
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception in retryWaitUntilInVisibleOrdersListPage method :" + e.toString());
            return false;
        }
		
	}
	
	/**
	 * @param crsid
	 * @throws Exception
	 */
	public final boolean retryWaitUntilVisibleOrdersListPage(String locator, int wait) {
        try {
            this.retryWaitUntilVisible(ordersListPageProperties.getProperty(locator), wait);
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception in retryWaitUntilVisibleOrdersListPage method :" + e.toString());
            return false;
        }
		
	}
	
	/**
	 * @param crsid
	 * @throws Exception
	 */
	public final void scrollTillViewOrdersListPage(String locator) throws Exception {
        this.scrollTillView(ordersListPageProperties.getProperty(locator));
	}
	
	/**
	 * @param fieldname
	 * @return
	 * @throws Exception
	 */
	public String[] getCSVFieldValue(String fieldname, String filenamekey) throws Exception {
		try {
			
			File f = new File(System.getProperty("user.dir") + "/import/"
					+ getEnvironmentSpecificData(System.getProperty("environment"), filenamekey));

			String[][] arr = CSVFileReader.getInstance().getDataWithHeader(f);
			String[] cellValueFromAllRows = new String[arr.length-1];
			
			int fieldColumnIndex = 0;
			for (int i=1; i<arr.length; i++) {
				for (int j = 0; j < arr[0].length; j++) {
					if (arr[0][j].equalsIgnoreCase(fieldname)) {
						fieldColumnIndex = j;
						break;
					}
				}

				if (fieldname.equals("FCPKServiceStartDate") || fieldname.equals("FCPKServiceEndDate")) {
					cellValueFromAllRows[i-1] = convertDate("dd-MMM-yy", "MM/dd/yyyy", arr[i][fieldColumnIndex]);
				} else {
					cellValueFromAllRows[i-1] = arr[i][fieldColumnIndex];
				}
			}
			return cellValueFromAllRows;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getCSVFieldValue " + e.getMessage()));
			return null;
		}

	}
	
	
	/**
	 * update csv fields with random values
	 * 
	 * @throws Exception
	 */
	public final void updateActiveCareCSVFieldValue(String csvFile, String[] columnNamesToUpdate) throws Exception {
		try {
			File f = new File(System.getProperty("user.dir") + "/import/"
					+ getEnvironmentSpecificData(System.getProperty("environment"), csvFile));

			String[][] arr = CSVFileReader.getInstance().getDataWithHeader(f);
			for (int i = 0; i < columnNamesToUpdate.length; i++) {
				int fieldColumnIndex = 0;
				for (int j = 0; j < arr[0].length; j++) {
					if (arr[0][j].equalsIgnoreCase(columnNamesToUpdate[i])) {
						fieldColumnIndex = j;
						break;
					}
				}
				for (int k = 1; k < arr.length; k++) {
					if (columnNamesToUpdate[i].equals("EndCustomerPrimaryEmail")) {

						CSVFileReader.getInstance().updateCSV(f,
								RandomStringUtils.randomAlphanumeric(8) + "@mailinator.com", k, fieldColumnIndex);
					}

					else if (columnNamesToUpdate[i].equals("ObjectOfServiceSerialNumber")) {
						CSVFileReader.getInstance().updateCSV(f, "CPAUTO" + RandomStringUtils.randomAlphanumeric(4).toUpperCase(), k,
								fieldColumnIndex);
					}

					else if (columnNamesToUpdate[i].equals("EndCustomerName")) {
						CSVFileReader.getInstance().updateCSV(f, "NEWCOMPANY" + RandomStringUtils.randomAlphanumeric(4).toUpperCase(),
								k, fieldColumnIndex);
					}

				}
			}

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getCSVFieldValue " + e.getMessage()));

		}
	}
	
	/**
	 * This method is used to create Active Care Test CSV file
	 * 
	 * @param orderType
	 * @param rowCount
	 * @param serialNumber
	 * @param endCustomerName
	 * @param variant
	 * @param mandatoryColumnsToBlank
	 * @return Map<String, Object>
	 * @throws IOException
	 */
	public static Map<String, Object> createActiveCareTestCSV_v3(
			String orderType,
	        int rowCount,
	        String serialNumber,
	        String endCustomerName,
	        ACGenerationVariant variant,
	        Collection<String> mandatoryColumnsToBlank 
		) throws IOException {
		Map<String, Object> createActiveCareTestCSV = CSVFileReader.generateActiveCareCsvByOrderType_v3(orderType, rowCount, serialNumber, endCustomerName,variant, mandatoryColumnsToBlank);
		return createActiveCareTestCSV;
	}
	
	/**
	 * This method is used to delete Active Care Test CSV file
	 * 
	 * @param filename
	 * @return boolean
	 * @throws IOException
	 */
	public boolean deleteActiveCareTestCSV(String filename) throws IOException {
		String inputPath = (System.getProperty("user.dir") + "/import/");
//		String fileNamePAttern = getEnvironmentSpecificData(System.getProperty("environment"), filename).replaceFirst("(?i)\\.csv$", "") + "_";
		return CSVFileReader.deleteCSVFiles(inputPath, filename);

	}
	
	/**
	 * This method is used to get file name after split based on environment
	 * 
	 * @param env
	 * @param filename
	 * @return String
	 * @throws IOException
	 */
	public static String getFileNameAfterSplit(String env, String filename) throws IOException {
        LOGGER.info("Global file created: {}", filename);
		if (filename == null || filename.isEmpty()) {
	        throw new IllegalArgumentException("filename must not be null or empty");
	    }
	    String prefix = (env != null && env.toUpperCase().contains("US")) ? "US_" : "EU_";
	    return prefix + filename; 
	}
	
	/**
	 * This method is used to check if global file is processed
	 * 
	 * @param languageCode
	 * @param filename
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean checkIfGlobalFileIsProcessed(String languageCode, String filename) throws Exception {
		int retry = 10;
		int rowNum = searchOrder(languageCode,filename);
		boolean isProcessed = false;
		while(retry != 0 & rowNum !=0) {
			if(!"Processed".equalsIgnoreCase(getTextOfOrderListPage("firstRowState").trim())) {
				refreshPage();
				waitForPageLoaded();
				retry--;
				}
			else {
				isProcessed = true;
				break;
			}
			}
		return isProcessed;
	}
	
	/**
	 * This method is used to check if split file is processed
	 * 
	 * @param languageCode
	 * @param filename
	 * @return boolean
	 * @throws Exception
	 */
    public final boolean checkIfSplitFileIsProcessed(String languageCode, String filename) throws Exception {
        int retry = 8;
        int rowNum = searchOrder(languageCode, filename);
        if (rowNum == 0) {
            LOGGER.warn("Split file {} not found in Orders list", filename);
            return false;
        }
        boolean isProcessed = false;
		String orderType = getTextOfOrderListPage("firstRowOrderType");
		sleeper(20000);
		if(!orderType.equalsIgnoreCase("Reseller Update")){
			clickOnElementsOfOrdersListPage("firstRowFileName");
			verifyElementIsVisibleOfordersListPage("orderDetailsFileType");
			sleeper(20000);
		}
        while (retry-- > 0) {
            refreshPage();
            if (isFileProcessed(orderType)) {
                isProcessed = true;
                break;
            }
			if (orderType.equalsIgnoreCase("Device Transfer")
					|| orderType.equalsIgnoreCase("Reseller Update")
					|| orderType.equalsIgnoreCase("Remove License")
					|| orderType.equalsIgnoreCase("Data Collection")) {
				sleeper(8000); //need to add longer sleep as these order types takes longer time and are not dependent on CRON job.
				if (isFileProcessed(orderType)) {
                	isProcessed = true;
                	break;
				}
			}else {
				triggerProcessOrdersCRONJob();
            	waitForFirstRowStateVisible();
            	if (isFileProcessed(orderType)) {
                	isProcessed = true;
                	break;
            	}
			}
        }
        return isProcessed;
    }
	
	/**
	 * This method is used to make an entry in SNR table and return the serial number.
	 * 
	 * @param languageCode
	 * @param filename
	 * @return boolean
	 * @throws Exception
	 */
	public final boolean returnToOrderListPage() throws Exception {
		clickOnElementsOfOrdersListPage("returnToOrderListPageButton");
		return isAtOrdersListPage();
	}


	/**
	 * This method is used to make an entry in SNR table and return the serial number.
	 * 
	 * @param languageCode
	 * @param filename
	 * @return boolean
	 * @throws Exception
	 */
	public final String makeEntryInSNRTable() throws Exception {
		Random rnd = new Random();
		String serialNumber = "CPAUTO" + (100000 + rnd.nextInt(900000));
		triggerMakeSNREntryCRONJob(serialNumber);
		return serialNumber;
	}
	
	/**
	 * Trigger AZURE pipeline to process orders
	 * 
	 * @throws Exception
	 */
	public final void triggerProcessOrdersCRONJob() throws Exception {
		String hostURL=getEnvironmentSpecificData(System.getProperty("environment"), "hostURL");
		String processOrderEndPoint= ConstantURL.CRONJOBENDPOINT_PROCESSORDERS;
		String envName=getEnvironmentSpecificData(System.getProperty("environment"), "hostEnviroment");
		LOGGER.error("Trigger Process Latest Orders CRON Job." );
		AzureUtil.triggerAzurePipelineToProcessOrder(hostURL, processOrderEndPoint, envName);
	}
	
	/**
	 * Trigger AZURE pipeline to Mark orders Complete
	 * 
	 * @throws Exception
	 */
	public final void triggerMarkOrdersCompleteCRONJob() throws Exception {
		String hostURL=getEnvironmentSpecificData(System.getProperty("environment"), "hostURL");
		String processOrderEndPoint= ConstantURL.CRONJOBENDPOINT_COMPLETEORDERS;
		String envName=getEnvironmentSpecificData(System.getProperty("environment"), "hostEnviroment");
		LOGGER.error("Trigger Mark Orders Complete CRON Job." );
		AzureUtil.triggerAzurePipelineToProcessOrder(hostURL, processOrderEndPoint, envName);
	}
	
	/**
	 * Trigger AZURE pipeline to Make entry in SNR table
	 * 
	 * @throws Exception
	 */
	public final void triggerMakeSNREntryCRONJob(String serialnumber) throws Exception {
		String hostURL=getEnvironmentSpecificData(System.getProperty("environment"), "hostURL");
		String processOrderEndPoint= ConstantURL.CRONJOBENDPOINT_MAKESNRENTRY;
		String envName=getEnvironmentSpecificData(System.getProperty("environment"), "hostEnviroment");
		LOGGER.error("Trigger Make SNR entry CRON Job." );
		AzureUtil.triggerAzurePipelineToMakeSNREntry(hostURL, processOrderEndPoint, envName, serialnumber);
	}

    /**
     * This method is used to click Add button on Orders List Page.
     *
     */
    public boolean clickDeviceTransferButtonOrdersListPage() {
        try {
            if(verifyElementIsVisibleOfordersListPage("deviceTransferOption")) {
                clickOnElementsOfOrdersListPage("deviceTransferOption");
            }
            return verifyElementIsVisibleOfordersListPage("deviceTransferOption");
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyImportOrders " + e.getMessage()));
            return false;
        }

    }

    /**
     * This method is used to click Reseller Update on Orders List Page.
     *
     */
    public boolean clickResellerUpdateButtonOrdersListPage() {
        try {
            if(verifyElementIsVisibleOfordersListPage("resellerUpdateOption")) {
                clickOnElementsOfOrdersListPage("resellerUpdateOption");
            }
            return verifyElementIsVisibleOfordersListPage("resellerUploadLabel");
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method verifyImportOrders " + e.getMessage()));
            return false;
        }
    }

    /**
     * Clicks on an uploaded file in the orders list after verifying the file name.
     *
     * @param rowNumber the row number of the uploaded file to click
     * @param expectedFileName the expected file name to validate before clicking
     * @throws Exception if an error occurs during file verification or click action
     */
    public final void clickOnUploadedFile(int rowNumber, String expectedFileName) throws Exception {
        sleeper(200);
        String filenameLocator = String.format(ordersListPageProperties.getProperty("eachRowFileName"), rowNumber);
        if (!this.getTextBy(filenameLocator).equals(expectedFileName)) {
            LOGGER.error("File name mismatch. Expected: {}, Actual: {}", expectedFileName, this.getTextBy(filenameLocator));
        }
        click(filenameLocator);
        waitForPageLoaded();
    }

    private Optional<String> tryGetTextOfOrdersListPage(String key) {
        try {
			scrollTillViewOrdersListPage(key);
            return Optional.ofNullable(getTextOfOrderListPage(key));
        } catch (Exception ex) {
            LOGGER.warn("Unable to read {} on Orders List page", key, ex);
            return Optional.empty();
        }
    }

    private boolean waitForFirstRowStateVisible() {
        waitForPageLoaded();
        try {
            if (verifyElementIsVisibleOfordersListPage("firstRowState")) {
                return true;
            }
        } catch (Exception ignore) {
            // fall through to retry helper
        }
        return retryWaitUntilVisibleOrdersListPage("firstRowState", 10);
    }

    private boolean isFileProcessed(String orderType) {
		// For Reseller Update order type there is no order details page and the state is displayed in the first column of orders list page, so we can directly check the state there.
		// for other order types, we need to click into order details page to check the state.
		String stateKey = "Reseller Update".equalsIgnoreCase(orderType)
				? "firstRowState"
				: "orderDetailsState";

		String state = tryGetTextOfOrdersListPage(stateKey)
				.map(String::trim)
				.orElse("");

		if ("Reseller Update".equalsIgnoreCase(orderType)) {
			return "Processed".equalsIgnoreCase(state)
					|| "Rejected".equalsIgnoreCase(state)
					|| "Failed".equalsIgnoreCase(state);
		}

		return "Completed".equalsIgnoreCase(state)
				|| "Skipped".equalsIgnoreCase(state);
	}

}