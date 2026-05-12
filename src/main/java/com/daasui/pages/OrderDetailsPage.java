package com.daasui.pages;

import java.io.File;
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

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.CSVFileReader;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OrderDetailsPage extends CommonMethod {
	
	private Properties selectedLanguageProperties;
	private ObjectReader ordersDetailsPagePropertiesReader = new ObjectReader();
	private Properties ordersDetailsPageProperties;
	private static Logger LOGGER = LogManager.getLogger(OrderDetailsPage.class);
	private OrderDetailsPage instance;
	public static String uiVersion = System.getProperty("uiVersion");
	
	public OrderDetailsPage getInstance() throws IOException {
		if (instance==null) {
			synchronized(OrdersListPage.class) {
				if(instance==null) {
					instance = new OrderDetailsPage(DRIVER);
				}
				
			}
			
		}
		return instance;
	}
	
	public OrderDetailsPage(WebDriver driver) throws IOException {
		ordersDetailsPageProperties = ordersDetailsPagePropertiesReader.getObjectRepository("OrdersDetailsPageV3");
	}

	public final String getTextLanguage(String language, String localefolder, String key) throws Exception {
		selectedLanguageProperties = ordersDetailsPagePropertiesReader.getLanguageObjectRepository(localefolder, language);
		return selectedLanguageProperties.getProperty(key);
	}
	
	public final int getTotalRowCount() throws Exception {
		return getCountOfRows(ordersDetailsPageProperties.getProperty("allRowCount"));
	}
	
	public final int getTotalColumnCount() throws Exception {
		return getCountOfRows(ordersDetailsPageProperties.getProperty("allColumnHeadersText"));
	}
	
	/**
	 * This method verifies if user is on Orders Details Page.
	 * @return True. False if webelements are not visible
	 * @throws Exception
	 */
	public final boolean isAtOrdersDetailsPage() throws Exception {
		
		waitForPageLoaded();
		if(!verifyElementIsVisible(ordersDetailsPageProperties.getProperty("orderListPageLink"))) {
			return false;
		}
		if(!verifyElementIsVisible(ordersDetailsPageProperties.getProperty("allRowData"))) {
			return false;
		}
		return true;
	}
	
	public final boolean matchTextOfOrdersDetailsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(ordersDetailsPageProperties.getProperty(key), Text);
	}
	
	
	/**
	 * This method is used to fetch row number if import record is present & displayed in orders list.
	 * 
	 * @param getImportOrdersInfo
	 * @param languageCode
	 * @param fileName
	 * @throws Exception 
	 */
	public String[][] getImportDataFromOrdersDetailsPage() throws Exception {
		int row_count = getTotalRowCount();
		int column_count = getTotalColumnCount();
		String[][] arr = new String[row_count][column_count];
		
		for(int i=1; i<=row_count; i++) {
			for(int j=1;j<=column_count; j++) {
				String a = String.format(ordersDetailsPageProperties.getProperty("eachRowCellData"), String.valueOf(i), String.valueOf(j));
				scrollTillView(a);
				if(!verifyElementIsVisible(a)) {
					scrollTillView(a);
					arr[i-1][j-1]=getAttribute(a, "title");
				}else {
					arr[i-1][j-1]=getAttribute(a, "title");
				}
				
			}
		}
		return arr;
		}
	
	
	/**
	 * update csv fields with random values
	 * 
	 * @throws Exception
	 */
	public String[][] getTestDataFromImportCSV(String csvFile) throws Exception {
		try {
			File f = new File(System.getProperty("user.dir") + "/import/"
					+ getEnvironmentSpecificData(System.getProperty("environment"), csvFile));
			String[][] arr = CSVFileReader.getInstance().getDataWithHeader(f);
			return arr;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getCSVFieldValue " + e.getMessage()));
		}
		return null;

	}
	

	
	
}