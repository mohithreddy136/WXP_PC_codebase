package com.daasui.pages;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OrdersListPage extends CommonMethod {
	
	private Properties selectedLanguageProperties;
	private ObjectReader orderslistPagePropertiesReader = new ObjectReader();
	private Properties ordersListPageProperties;
	private static Logger LOGGER = LogManager.getLogger(OrdersListPage.class);
	
	private OrdersListPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public OrdersListPage getInstance() throws IOException {
		if (instance==null) {
			synchronized(OrdersListPage.class) {
				if(instance==null) {
					instance = new OrdersListPage(DRIVER);
				}
				
			}
			
		}
		return instance;
	}
	
	public OrdersListPage(WebDriver driver) throws IOException {
		ordersListPageProperties = orderslistPagePropertiesReader.getObjectRepository("OrdersListPageV3");
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

	/*
	 * This method verifies if user is on Orders List Page.
	 * @return True. False if webelements are not visible
	 * @throws Exception
	 */
	public final boolean isAtOrdersListPage() throws Exception {
		
		waitForPageLoaded();
		if(!verifyElementIsVisible(ordersListPageProperties.getProperty("allRowCount"))) {
			return false;
		}
		if(!verifyElementIsVisible(ordersListPageProperties.getProperty("addButton"))) {
			return false;
		}
		if(!verifyElementIsVisible(ordersListPageProperties.getProperty("columnHeaderNames"))) {
			return false;
		}
		return true;
	}
	
	public final boolean matchTextOfOrdersListPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(ordersListPageProperties.getProperty(key), Text);
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
			if(verifyElementIsVisible(ordersListPageProperties.getProperty("addButton"))) {
				click(ordersListPageProperties.getProperty("addButton"));
			}
			return verifyElementIsVisible(ordersListPageProperties.getProperty("addOrderTitle"));
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
			if(!verifyElementIsVisible(ordersListPageProperties.getProperty("addOrderTitle"))) {
				LOGGER.info("Add Orders modal is not visible");
				return false;
				}
			if(!verifyTextPresentOnElement(ordersListPageProperties.getProperty("addOrderTitle"),getTextLanguage(languageCode, "daas_ui", "orders.add.orders.title"))) {
				LOGGER.info("Mismatch in Title on Add Orders modal.");
				return false;
			}
			if(!verifyTextPresentOnElement(ordersListPageProperties.getProperty("addOrderSubtitle"),getTextLanguage(languageCode, "daas_ui", "orders.add.orders.sub.title"))) {
				LOGGER.info("Mismatch in Sub-Title on Add Orders modale");
				return false;
			}
			if(!verifyTextPresentOnElement(ordersListPageProperties.getProperty("addOrderDescription"),getTextLanguage(languageCode, "daas_ui", "orders.add.orders.sub.discription"))) {
				LOGGER.info("Mismatch in Orders description on Add Orders modal");
				return false;
			}
			if(!verifyElementIsClickable(ordersListPageProperties.getProperty("downloadSampleFileLink"))) {
				LOGGER.info("Download sample link is clickable");
				return false;
			}
			if(!verifyElementIsVisible(ordersListPageProperties.getProperty("cancelOrderButton"))) {
				LOGGER.info("Cancel order button is not visible");
				return false;
			}
			if(!verifyElementIsVisible(ordersListPageProperties.getProperty("importOrderButton"))) {
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
	 * This method is used to validate flow of importing the orders
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 */
	public boolean verifyImportCSVOrdersListPage(String languageCode, String fileName) {
		try {
			if(!verifyElementIsVisible(ordersListPageProperties.getProperty("addOrderTitle"))){
			this.ClickAddButtonOrdersListPage();
			}
			LOGGER.info("Clicked on browse button");
			sleeper(3000);
			WebElement addFile = getElement(ordersListPageProperties.getProperty("fileImport"));
			addFile.sendKeys(ConstantPath.IMPORT_PATH+fileName);
			sleeper(3000);
			click(ordersListPageProperties.getProperty("importOrderButton"));
			LOGGER.info("Clicked on submit import button");
			sleeper(10000);
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
			waitForPageLoaded();
			if(verifyElementIsVisible(ordersListPageProperties.getProperty("allRowCount"))) {
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
		String num_orders_locator = String.format(ordersListPageProperties.getProperty("eachRowNumberOfOrders"), String.valueOf(row_number));
		String source_locator = String.format(ordersListPageProperties.getProperty("eachRowSource"), String.valueOf(row_number));
		String date_time_locator = String.format(ordersListPageProperties.getProperty("eachRowDateTime"), String.valueOf(row_number));
		String status_locator = String.format(ordersListPageProperties.getProperty("eachRowStatus"), String.valueOf(row_number));
		if (row_number !=0 && verifyElementIsVisible(filename_locator)) {
			String a = this.getTextBy(filename_locator);
			if(!this.getTextBy(filename_locator).equals(filename)) {
				LOGGER.info("File name does not match in OrdersListPage.");
				flag=false;}
			if(!this.getTextBy(num_orders_locator).equals(file_row_count)) {
				LOGGER.info("Orders count does not match in OrdersListPage.");
				flag=false;}
			if(!this.getTextBy(source_locator).equals("Manual")) {
				LOGGER.info("Source does not match in OrdersListPage.");
				flag=false;}
			if(!verifyElementIsVisible(date_time_locator)) {
				LOGGER.info("DateTime column value is not visible on OrderList page.");
				flag=false;}
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
	public int searchOrder(String languageCode, String filename) throws Exception {
		if(!this.verifyElementIsVisible(ordersListPageProperties.getProperty("searchField"))) {
			LOGGER.info("Search field is not visible on Orders list page.");
			return 0;
		}
		if(this.verifyElementIsVisible(ordersListPageProperties.getProperty("clearfilters"))){
			this.clickOnElementsOfOrdersListPage(ordersListPageProperties.getProperty("clearfilters"));
			this.verifyElementIsinvisibile(ordersListPageProperties.getProperty("clearfilters"));
		}
		this.enterText(ordersListPageProperties.getProperty("searchField"), filename);
		int row_number = this.getImportRecordRowNumberInList(languageCode, filename);
		if(row_number!=0) {
			LOGGER.info("Filename not found in Orders list.");
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
		public boolean downloadOrderSummaryFile(int row_number) throws Exception {
			this.scrollTillView(String.format(ordersListPageProperties.getProperty("eachrowOrderSummaryFile"), String.valueOf(row_number)));
			sleeper(100);
			if (row_number !=0 && verifyElementIsVisible(String.format(ordersListPageProperties.getProperty("eachrowOrderSummaryFile"), String.valueOf(row_number)))) {
				this.click(String.format(ordersListPageProperties.getProperty("eachrowOrderSummaryFile"), String.valueOf(row_number)));
				if(!this.verifyElementIsVisible(ordersListPageProperties.getProperty("downloadToastMessage"))) {	
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
	
	
}