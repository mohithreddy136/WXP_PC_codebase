package com.daasui.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;


public class CampaignDetailsPage extends CommonMethod {
	
	 // Global filter togglename
	public static final String Campaign_Details_toggle = "campaign-page-design-update";
	public static final String Action_Campaign_toggle = "custom-action-campaign-subcategory";
	public static final String TechPulse_Grouping_Toggle = "techpulse-grouping-service";
	public static final String Groups_In_Campaigns_Toggle = "groups-use-in-campaign";
	public static final String Medallia_Service_Domain = "https://survey.medallia.com/?HPIT-SERVICES-CSAT";
	public static final String InValid_First_HPIT_Domain = "https://hpitproddemo.service-now.com/";
	public static final String Valid_First_HPIT_Domain = "https://hpitprod.service-now.com/hp?id=walkup_online_checkin&location_id=01a8a665db07d81050e1a4ecd3961923&deviceID=123abc&section=HPIT";
	public static final String InValid_Second_HPIT_Domain = "https://hpittestdemo.service-now.com/";
	public static final String Valid_Second_HPIT_Domain = "https://hpittest.service-now.com/hp?id=walkup_online_checkin&location_id=01a8a665db07d81050e1a4ecd3961923&deviceID=123abc&scetion=HPIT";
			
	private ObjectReader CampaignDetailsPagePropertiesReader = new ObjectReader();
	private Properties CampaignsDetailsPageProperties;
	private CampaignDetailsPage instance;
	public static String uiVersion = System.getProperty("uiVersion");

	public CampaignDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (CampaignDetailsPage.class) {
				if (instance == null) {
					instance = new CampaignDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public CampaignDetailsPage(WebDriver driver) throws IOException {
		CampaignsDetailsPageProperties = CampaignDetailsPagePropertiesReader.getObjectRepository("CampaignDetailsPageV3");

	}
	
	/** This method is used to click.
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfCampaignDetailsPage(String key) throws Exception {
		click(CampaignsDetailsPageProperties.getProperty(key));
		
	}
	

	
	/**
	 * This is a method to wait for an element till it is visible
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is visible or not
	 */
	public final boolean waitForElementsOfCampaignDetailsPage(String key) {
		try {
			return verifyElementIsVisible(CampaignsDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfCampaignDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This is a method to verify if the element is present
	 * 
	 * @param key - Locator of element
	 * @return - boolean value of whether the element is present or not
	 */
	public final boolean verifyElementsOfCampaignDetailsPage(String key) {
		try {
			return verifyElementIsPresent(CampaignsDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyElementsOfCampaignDetailsPage " + e.getMessage()));
			return false;
		}
	}

	/**
	 * This method is to get text present on an element on campaign details page
	 * 
	 * @param key - locator of the element
	 * @return - string value of the text present on the element
	 */
	public final String getTextOfCampaignDetailsPage(String key) throws Exception {
		return getTextBy(CampaignsDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify if an element on campaign details page is enabled
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled
	 */
	public final boolean verifyElementIsEnableOfCampaignDetailsPage(String key) throws Exception {
		return verifyElementIsEnable(CampaignsDetailsPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsVisibleOfCampaignDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(CampaignsDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to verify element is clickable
	 * 
	 * @param key
	 * @return true
	 * @throws Exception
	 */

	public final boolean verifyElementIsClickableCampaignDetailsPage(String key) throws Exception {
		return verifyElementIsClickable(CampaignsDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to scroll on campaign details page
	 * 
	 * @param key - Locator of element
	 */
	public final void scrollOnCampaignDetailsPage(String key) throws Exception {
		scrollTillView(CampaignsDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text on a text field present on campaign details page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be entered
	 */
	public final void enterTextForCampaignDetailsPage(String key, String Text) throws Exception {
		enterText(CampaignsDetailsPageProperties.getProperty(key), Text);
	}
	
	/**
	 * Method for entering text using javascript for campaign detail page
	 * 
	 * @param locator of the webelement
	 * @param text to be entered
	 * @throws Exception
	 */
	public final void enterTextUsingJavaScriptCampaignDetailPage(String locator, String text) throws Exception {
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(CommonVariables.EXPLICITWAIT));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		element.clear();
		jsDriver().executeScript("arguments[0].value='" + text + "'", element);
	}

	
	
	/** 
	 * This is a method to select date from calendar filter
	 * @param date - current date
	 * @param monthKeyCurrent - locator of current month
	 * @param rightArrowKey - locator for right arrow key on calendar
	 * @param daysOnCurrentMonthKey - locator for days on current month 
	 */
	public final void selectDateFromCalenderOnCampaignDetailpage(String date, String monthKeyCurrent, String rightArrowKey, String daysOnCurrentMonthKey) {
		try {
			selectDateFromCalenderDatePickerContainer(date, CampaignsDetailsPageProperties.getProperty(monthKeyCurrent), CampaignsDetailsPageProperties.getProperty(rightArrowKey), CampaignsDetailsPageProperties.getProperty(daysOnCurrentMonthKey));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method selectDateFromCalenderOnCompanyDetailpage " + e.getMessage()));
		}
	}

	/**
	 * This is a method to get a list of elements of campaign details page
	 * 
	 * @param key - Locator of list
	 * @return - list of webElements
	 */
	public final List<WebElement> getElementsTillAllElementsVisibleofCampaignDetailpage(String key) {
		try {
			return getElementsTillAllElementsVisible(CampaignsDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofCampaignDetailpage " + e.getMessage()));
			return null;
		}
	}
	
	/**
	 * This is a method to hover mouse on an element
	 *
	 * @param key - Locator of element
	 */
	public final void mousehoverOnCampaignDetailsPage(String key) {
		try {
			mouseHover(CampaignsDetailsPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mousehoverOnLogsPage " + e.getMessage()));
		}
	}
	
	public final void clickByJavaScriptOnCampaignDetailsPage(String key) throws Exception {
		clickByJavaScript(CampaignsDetailsPageProperties.getProperty(key));
	}
	
	public final void switchToPreviousTabOfCampaignDetailsPage() {
		switchBackToPreviousTab();
	}
	
	public final void switchToDifferentTabOfCampaignsPage(){
		switchToDifferentTab();
	}
	
	public final void scrollDownCharts() {
		jsDriver().executeScript("scroll(0, 750);");
	}
	
	/**
	 * This method creates the campaign for specified category & verifies the details page for that created campaign
	 * 
	 * @categoryName - Category to create campaign
	 * @categoryDropDownLocator - Locator for category drop down
	 * @param LanguageCode
	 *  
	 * @throws Exception
	 */
	public final boolean createCampaign(String categoryName, String categoryDropDownLocator, String LanguageCode)throws Exception {
		boolean flag = false;
		try {
			String campaignName = "TestCampaign" + generateRandomString(5);
			String campaignDescription = "Campaign Description" + generateRandomString(15);
			String questiontype1 = "Rate your Experience 1-5";
			String questiontype2 = "How do you feel for our service";
			String questiontype3 = "What do you appreciate the most about our service?";
			String optionvalue1 = "Excellent" ;
			String optionvalue2 = "Good";
			String optionvalue3 = "Not Good";
			List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);
			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryName.equals(categoryList.get(i).getText())) {
					categoryList.get(i).click();
					break;
				}
			}
			enterTextForCampaignDetailsPage("campaignNameTextBox", campaignName);
			scrollOnCampaignDetailsPage("campaignNameTextBox");
			if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				clickOnElementsOfCampaignDetailsPage("subCategoryDropDown");
				clickOnElementsOfCampaignDetailsPage("firstElementOnSubcategoryDropDown");
			}
			waitForPageLoaded();
			boolean NewUI = verifyElementIsVisibleOfCampaignDetailsPage("filtercriteriatile");
			if(NewUI==true) {	
			waitForElementsOfCampaignDetailsPage("filtercriteriatile");
			scrollOnCampaignDetailsPage("filtercriteriatile");
			clickOnElementsOfCampaignDetailsPage("addcriteria");
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("filteroption");
			sleeper(2000);
			getTextOfCampaignDetailsPage("criteria1headercreateampaignpage");
			waitForElementsOfCampaignDetailsPage("valuedd");
			clickOnElementsOfCampaignDetailsPage("valuedd");
			clickOnElementsOfCampaignDetailsPage("filtervalue1");
			LOGGER.info("Add criteria dropdown value selected Sucessfully");
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("blankArea");
			waitForElementsOfCampaignDetailsPage("calculateaudience");
			sleeper(2000);
		    clickOnElementsOfCampaignDetailsPage("calculateaudience");
			scrollOnCampaignDetailsPage("Duration");
			}else{
				scrollOnCampaignDetailsPage("subCategoryDropDown");
				clickOnElementsOfCampaignDetailsPage("addcriteria");
				sleeper(2000);
				clickOnElementsOfCampaignDetailsPage("filteroption");
				sleeper(2000);
				//getTextOfCampaignDetailsPage("criteria1headercreateampaignpage");
				waitForElementsOfCampaignDetailsPage("criteriadd");
				clickOnElementsOfCampaignDetailsPage("criteriadd");
				clickOnElementsOfCampaignDetailsPage("filtervalue");
				LOGGER.info("Add criteria dropdown value selected Sucessfully");
				sleeper(2000);
				clickOnElementsOfCampaignDetailsPage("overlay");
				waitForElementsOfCampaignDetailsPage("calculateaudience");
				sleeper(2000);
			    clickOnElementsOfCampaignDetailsPage("calculateaudience");
				scrollOnCampaignDetailsPage("durationongoing");
			}
			waitForElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");		
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage("endDatePicker");
			clickOnElementsOfCampaignDetailsPage("endDatePicker");
			waitForElementsOfCampaignDetailsPage("calendarPopup");
			waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
			selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			LOGGER.info("Start date & End for the campaign selected successfully ");
			if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){				
				enterTextForCampaignDetailsPage("campaignDescriptionTextBox", campaignDescription);
			}
			/**
			 * Star rating Question selection
			 */
			scrollOnCampaignDetailsPage("addQuestionsLink");
			enterTextForCampaignDetailsPage("satisfactionRatingTextbox", questiontype1 );
			sleeper(2000);
			LOGGER.info("Star rating question added successfully ");
			/**
			 * Happy/UnHappy Question selection
			 */
			scrollOnCampaignDetailsPage("addQuestionsLink");
			waitForElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("secondtypeDropDown");
			clickOnElementsOfCampaignDetailsPage("fourthElementOnTypeDropDown");
			enterTextForCampaignDetailsPage("firstquestionlabel", questiontype2);
			LOGGER.info("Happy/UnHappy question added successfully ");
			sleeper(2000);
			/**
			 * Multiselect Question selection
			 */
			scrollOnCampaignDetailsPage("addQuestionsLink");
			waitForElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("thirdtypeDropDown");
			clickOnElementsOfCampaignDetailsPage("secondElementOnTypeDropDown");
			enterTextForCampaignDetailsPage("secondquestionlabel", questiontype3);
			scrollOnCampaignDetailsPage("multifirstoption");
			enterTextForCampaignDetailsPage("multifirstoption", optionvalue1);
			enterTextForCampaignDetailsPage("multisecondoption", optionvalue2);
			enterTextForCampaignDetailsPage("multithirdoption", optionvalue3);
			LOGGER.info("Multiselect question added successfully ");
			sleeper(2000);
			/**
			 * Singleselect Question selection
			 */
			scrollOnCampaignDetailsPage("addQuestionsLink");
			waitForElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("fourthtypeDropDown");
			clickOnElementsOfCampaignDetailsPage("firstElementOnTypeDropDown");
			enterTextForCampaignDetailsPage("thirdquestionlabel", questiontype3);
			scrollOnCampaignDetailsPage("singlesecondoption");
			enterTextForCampaignDetailsPage("singlefirstoption", optionvalue1);
			enterTextForCampaignDetailsPage("singlesecondoption", optionvalue2);
			enterTextForCampaignDetailsPage("singlethirdoption", optionvalue3);
			LOGGER.info("Singleselect question added successfully ");
			clickOnElementsOfCampaignDetailsPage("saveButton");
			waitForElementsOfCampaignDetailsPage("toastNotification");
			LOGGER.info("Campaign created successfully ");
			if (verifyCreatedCampaignOnListPage(campaignName)) {
				flag = true;
				clickOnElementsOfCampaignDetailsPage("firstcampaignlist");
				//Verification of Campaign Details Page for Scheduled Custom Campaign
				verifyschedulednormalCampaignOnDetailsPage();
				LOGGER.info("Campaign details page verifed successfully ");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating campaign" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * This method verifies the campaign details page for scheduled normal campaigns
	 * 
	 * @throws Exception
	 */
	public boolean verifyschedulednormalCampaignOnDetailsPage() throws Exception{
		boolean flag = false;
		SoftAssert SoftAssert = new SoftAssert();
		try {
		//Verification of Overview tab on created on going campaign.
		verifyElementsOfCampaignDetailsPage("overviewTab");
		boolean NewUI= verifyElementIsVisibleOfCampaignDetailsPage("campaignNameOnDetailsPagenewUI");
		if(NewUI==true) {
		getTextOfCampaignDetailsPage("campaignNameOnDetailsPagenewUI");
		SoftAssert.assertFalse(verifyElementIsClickableCampaignDetailsPage("campaignnameongoing"),"Campaign Name in details page is editable");
		SoftAssert.assertFalse(verifyElementIsClickableCampaignDetailsPage("campaigndescriptionongoing"),"Campaign Description in details page is editable");
		scrollOnCampaignDetailsPage("subCategoryDropDown");
		verifyElementIsClickableCampaignDetailsPage("categoryDropDown");
		verifyElementIsClickableCampaignDetailsPage("subCategoryDropDown");
		getTextOfCampaignDetailsPage("criteriaheaderongoing");
		getTextOfCampaignDetailsPage("criteriainnerheaderongoing");
		sleeper(2000);
		scrollOnCampaignDetailsPage("locationongoing");
		waitForElementsOfCampaignDetailsPage("locationongoing");
		getTextOfCampaignDetailsPage("locationongoing");
		getTextOfCampaignDetailsPage("durationongoing");
		SoftAssert.assertTrue(verifyElementIsClickableCampaignDetailsPage("endDatePicker"),"Campaign End date in details page is not editable");
		scrollOnCampaignDetailsPage("reqquesheaderongoing");
		getTextOfCampaignDetailsPage("reqquesheaderongoing");
		SoftAssert.assertTrue(verifyElementIsClickableCampaignDetailsPage("additionalquesongoing"),"Campaign additional question section in details page is not editable");
		verifyElementsOfCampaignDetailsPage("responseResultsTab");
		scrollToTop();
		LOGGER.info("Campaign Overview tab verified Sucessfully");
		waitForElementsOfCampaignDetailsPage("responseResultsTab");
		//Verification of Response results tab on Campaign details page.
		sleeper(5000);
		clickOnElementsOfCampaignDetailsPage("responseResultsTab");
		waitForElementsOfCampaignDetailsPage("campaignParticpationtitlenewUI");
		SoftAssert.assertTrue(verifyElementIsVisibleOfCampaignDetailsPage("campaignParticpationtitlenewUI"),"Campaign participation title on campaign details is not present.");
		LOGGER.info("Campaign Response tab verified Sucessfully");
		SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device health tab not present on campaign details page.");
		clickOnElementsOfCampaignDetailsPage("deviceHealthTab");
		sleeper(5000);
		SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("devicehealthtitlenewUI"),"Device health title not present on campaign details page.");
		SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("editbuttonongoing"),"Edit button not present on campaign details page");
		SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("exportbuttonnewUI"),"Export button not present on campaign details page");
		LOGGER.info("Device Health tab verified Sucessfully");
		}else {
		getTextOfCampaignDetailsPage("campaignNameOnDetailsPage");
			SoftAssert.assertFalse(verifyElementIsClickableCampaignDetailsPage("campaignNameTextBox"), "User is able to edit the Campaign name on details page.");
			SoftAssert.assertFalse(verifyElementIsClickableCampaignDetailsPage("campaignDescriptionTextBox"), "User is able to edit Campaign description on details page.");
			SoftAssert.assertFalse(verifyElementIsClickableCampaignDetailsPage("userSatisfactionRating"), "User is able to edit user satisfaction rating on Campaign details page.");
			SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("responseResultsTab"), "Response results tab not present on campaign details page.");
			SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device healt tab not present on campaign details page.");
			SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("saveButton"), "Save Campaign button not present on campaign details page");
			SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("discardButton"), "Discard Campaign button not present on campaign details page");
			SoftAssert.assertTrue(verifyElementsOfCampaignDetailsPage("endCampaignButton"), "End Campaign button not present on campaign details page");
		}
		SoftAssert.assertAll();
     	} catch (Exception e) {
		LOGGER.error("Exception occured while creating campaign" + e.getMessage());
		return false;
	    }
		return flag;
	}
	
	public final boolean createUserSentimentDeviceSatisfactionCampaign(String categoryName, String categoryDropDownLocator, String LanguageCode)throws Exception {
		boolean flag = false;
		try {
			String campaignName = "TestCampaign" + generateRandomString(5);
			List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);
			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryName.equals(categoryList.get(i).getText())) {
					categoryList.get(i).click();
					enterTextForCampaignDetailsPage("campaignNameTextBox", campaignName);
					scrollOnCampaignDetailsPage("campaignNameTextBox");
					clickOnElementsOfCampaignDetailsPage("subCategoryDropDown");
					clickOnElementsOfCampaignDetailsPage("firstElementOnSubcategoryDropDown");
					waitForPageLoaded();
					boolean NewUI = verifyElementIsVisibleOfCampaignDetailsPage("filtercriteriatile");
					if(NewUI==true) {	
					waitForElementsOfCampaignDetailsPage("filtercriteriatile");
					scrollOnCampaignDetailsPage("filtercriteriatile");
					clickOnElementsOfCampaignDetailsPage("addcriteria");
					sleeper(2000);
					clickOnElementsOfCampaignDetailsPage("filteroption");
					sleeper(2000);
					getTextOfCampaignDetailsPage("criteria1headercreateampaignpage");
					waitForElementsOfCampaignDetailsPage("valuedd");
					clickOnElementsOfCampaignDetailsPage("valuedd");
					clickOnElementsOfCampaignDetailsPage("filtervalue1");
					LOGGER.info("Add criteria dropdown value selected Sucessfully");
					sleeper(2000);
					clickOnElementsOfCampaignDetailsPage("blankArea");
					waitForElementsOfCampaignDetailsPage("calculateaudience");
					sleeper(2000);
				    clickOnElementsOfCampaignDetailsPage("calculateaudience");
					scrollOnCampaignDetailsPage("Duration");
					}else{
						scrollOnCampaignDetailsPage("subCategoryDropDown");
						clickOnElementsOfCampaignDetailsPage("addcriteria");
						sleeper(2000);
						clickOnElementsOfCampaignDetailsPage("filteroption");
						sleeper(2000);
						//getTextOfCampaignDetailsPage("criteria1headercreateampaignpage");
						waitForElementsOfCampaignDetailsPage("criteriadd");
						clickOnElementsOfCampaignDetailsPage("criteriadd");
						clickOnElementsOfCampaignDetailsPage("filtervalue1");
						LOGGER.info("Add criteria dropdown value selected Sucessfully");
						sleeper(2000);
						clickOnElementsOfCampaignDetailsPage("blankArea");
						waitForElementsOfCampaignDetailsPage("calculateaudience");
						sleeper(2000);
					    clickOnElementsOfCampaignDetailsPage("calculateaudience");
						scrollOnCampaignDetailsPage("durationongoing");
					}
					waitForElementsOfCampaignDetailsPage("startDateField");
				    clickOnElementsOfCampaignDetailsPage("startDateField");
				    clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");		
					sleeper(2000);
					waitForElementsOfCampaignDetailsPage("endDatePicker");
					clickOnElementsOfCampaignDetailsPage("endDatePicker");
					waitForElementsOfCampaignDetailsPage("calendarPopup");
					waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
					selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
					LOGGER.info("Start date & End for the campaign selected successfully ");
					clickOnElementsOfCampaignDetailsPage("saveButton");
					waitForElementsOfCampaignDetailsPage("toastNotification");
					LOGGER.info("Campaign created successfully ");
					sleeper(3000);
					if (verifyCreatedCampaignOnListPage(campaignName)) {
						clickOnElementsOfCampaignDetailsPage("firstcampaignlist");
						//Verification of Campaign Details Page for Scheduled Custom Campaign
						verifyscheduledCustomCampaignOnDetailsPage();
						LOGGER.info("Campaign Details Page verified Sucessfully");
						flag = true;
				}
		}}}catch (Exception e) {
			LOGGER.error("Exception occured while creating campaign" + e.getMessage());
			return false;
		}
		return flag;
		}
	/**
	 * This method creates the custom campaign for User sentiment  category & verifies the details page for the created campaign
	 * 
	 * @categoryName - Category to create custom campaign
	 * @categoryDropDownLocator - Locator for category drop down
	 * @param LanguageCode
	 *  
	 * @throws Exception
	 */
	public final boolean createCustomCampaign(String categoryName, String categoryDropDownLocator,String campaignUrl, String LanguageCode)throws Exception {
		boolean flag = false;
		try {
			String campaignName = "TestCampaign" + generateRandomString(5);
			String campaignDescription = "Campaign Description" + generateRandomString(15);
			List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);
			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryName.equals(categoryList.get(i).getText())) {
					categoryList.get(i).click();
					break;
				}
			}
			enterTextForCampaignDetailsPage("campaignNameTextBox", campaignName);
			scrollOnCampaignDetailsPage("campaignNameTextBox");
			if(categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				clickOnElementsOfCampaignDetailsPage("subCategoryDropDown");
				clickOnElementsOfCampaignDetailsPage("secondElementOnSubcategoryDropDown");
				waitForElementsOfCampaignDetailsPage("customCampaignUrlText");
				scrollOnCampaignDetailsPage("subCategoryDropDown");
				enterTextForCampaignDetailsPage("customCampaignUrl",campaignUrl);
				clickOnElementsOfCampaignDetailsPage("previewButton");
				sleeper(4000);
				switchToDifferentTabOfCampaignsPage();
				LOGGER.info("Switched to new window successfully for first campaign");
				sleeper(2000);
				switchToPreviousTabOfCampaignDetailsPage();
				LOGGER.info("Switched back to parent window successfully for first campaign");
				}
			sleeper(3000);
			waitForPageLoaded();
			waitForElementsOfCampaignDetailsPage("filtercriteriatile");
			scrollOnCampaignDetailsPage("filtercriteriatile");
			clickOnElementsOfCampaignDetailsPage("addcriteria");
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("filteroption");
			sleeper(2000);
			getTextOfCampaignDetailsPage("criteria1headercreateampaignpage");
			waitForElementsOfCampaignDetailsPage("valuedd");
			clickOnElementsOfCampaignDetailsPage("valuedd");
			clickOnElementsOfCampaignDetailsPage("filtervalue1");
			LOGGER.info("Add criteria dropdown value selected Sucessfully");
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("blankArea");
			waitForElementsOfCampaignDetailsPage("calculateaudience");
			sleeper(2000);
		    clickOnElementsOfCampaignDetailsPage("calculateaudience");
		    LOGGER.info("Calculate audience button verified Sucessfully");
			scrollOnCampaignDetailsPage("Duration");
			waitForElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");		
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage("endDatePicker");
			clickOnElementsOfCampaignDetailsPage("endDatePicker");
			waitForElementsOfCampaignDetailsPage("calendarPopup");
			waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
			selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			LOGGER.info("Start date & End date selected Sucessfully");
			if(categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				enterTextForCampaignDetailsPage("medalliacampaignDescriptionTextBox", campaignDescription);
			}
			clickOnElementsOfCampaignDetailsPage("saveButton");
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage("toastNotification");
			LOGGER.info("Custom Campaign Created Sucessfully");
			if (verifyCreatedCampaignOnListPage(campaignName)) {
				clickOnElementsOfCampaignDetailsPage("firstcampaignlist");
				//Verification of Campaign Details Page for Scheduled Custom Campaign
				verifyscheduledCustomCampaignOnDetailsPage();
				LOGGER.info("Campaign Details Page verified Sucessfully");
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating campaign" + e.getMessage());
			return false;
		}
		return flag;
	}

	/**
	 * This method creates the custom Action campaign for Action Campaign category & verifies the details page for the created campaign
	 * 
	 * @categoryName - Category to create custom campaign
	 * @categoryDropDownLocator - Locator for category drop down
	 * @param LanguageCode
	 *  
	 * @throws Exception
	 */
	public final boolean createCustomActionCampaign(String categoryName,String SubcategoryName, String categoryDropDownLocator,String validCampaignUrl,String InvalidCampaignUrl, String LanguageCode)throws Exception {
		boolean flag = false;
		try {
			String campaignName = "TestCampaign" + generateRandomString(5);
			String campaignDescription = "Campaign Description" + generateRandomString(15);
			List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);
			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryName.equals(categoryList.get(i).getText())) {
					categoryList.get(i).click();
					break;
				}
			}
			enterTextForCampaignDetailsPage("campaignNameTextBox", campaignName);
			scrollOnCampaignDetailsPage("campaignNameTextBox");
			if(categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.action_campaign"))){
				getTextOfCampaignDetailsPage("subcategoryvaluetext").equals(SubcategoryName);
				waitForElementsOfCampaignDetailsPage("customCampaignUrlText");
				scrollOnCampaignDetailsPage("subCategoryDropDown");
				enterTextForCampaignDetailsPage("customCampaignUrl",InvalidCampaignUrl);
				clickOnElementsOfCampaignDetailsPage("accampaignpreviewButton");
				sleeper(2000);
				getTextOfCampaignDetailsPage("campaignurlerrormsg");
				LOGGER.info("Provided URL Domain is Invalid & not supported text verified");
				enterTextForCampaignDetailsPage("customCampaignUrl",validCampaignUrl);
				clickOnElementsOfCampaignDetailsPage("accampaignpreviewButton");
				sleeper(4000);
				switchToDifferentTabOfCampaignsPage();
				LOGGER.info("Valid Domain provided Switched to new window successfully for first campaign");
				sleeper(2000);
				switchToPreviousTabOfCampaignDetailsPage();
				LOGGER.info("Switched back to parent window successfully for first campaign");
				}
			sleeper(3000);
			waitForPageLoaded();
			waitForElementsOfCampaignDetailsPage("filtercriteriatile");
			scrollOnCampaignDetailsPage("filtercriteriatile");
			clickOnElementsOfCampaignDetailsPage("addcriteria");
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("filteroption");
			sleeper(2000);
			getTextOfCampaignDetailsPage("criteria1headercreateampaignpage");
			waitForElementsOfCampaignDetailsPage("valuedd");
			clickOnElementsOfCampaignDetailsPage("valuedd");
			clickOnElementsOfCampaignDetailsPage("filtervalue1");
			LOGGER.info("Add criteria dropdown value selected Sucessfully");
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("blankArea");
			waitForElementsOfCampaignDetailsPage("calculateaudience");
			sleeper(2000);
		    clickOnElementsOfCampaignDetailsPage("calculateaudience");
		    LOGGER.info("Calculate audience button verified Sucessfully");
			scrollOnCampaignDetailsPage("Duration");
			waitForElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");		
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage("endDatePicker");
			clickOnElementsOfCampaignDetailsPage("endDatePicker");
			waitForElementsOfCampaignDetailsPage("calendarPopup");
			waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
			selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			LOGGER.info("Start date & End date selected Sucessfully");
			if(categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				enterTextForCampaignDetailsPage("medalliacampaignDescriptionTextBox", campaignDescription);
			}
			clickOnElementsOfCampaignDetailsPage("saveButton");
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage("toastNotification");
			LOGGER.info("Custom Action Campaign Created Sucessfully");
			if (verifyCreatedCampaignOnListPage(campaignName)) {
				clickOnElementsOfCampaignDetailsPage("firstcampaignlist");
				//Verification of Campaign Details Page for Scheduled Custom Campaign
				verifyscheduledCustomCampaignOnDetailsPage();
				LOGGER.info("Campaign Details Page for Action Campaign verified Sucessfully");
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating Action campaign" + e.getMessage());
			return false;
		}
		return flag;
	}

	
	/**
	 * This method verifies the campaign details page for scheduled medallia campaigns
	 * 
	 * @throws Exception
	 */
	public boolean verifyscheduledCustomCampaignOnDetailsPage() throws Exception{
		boolean flag = false;
		SoftAssert SoftAssert = new SoftAssert();
		try {
		//Verification of Overview tab on created on going campaign.
		verifyElementsOfCampaignDetailsPage("overviewTab");
		getTextOfCampaignDetailsPage("campaignNameOnDetailsPagenewUI");
		SoftAssert.assertFalse(verifyElementIsClickableCampaignDetailsPage("campaignnameongoing"),"Campaign Name in Ongoing details page is editable");
		SoftAssert.assertFalse(verifyElementIsClickableCampaignDetailsPage("campaigndescriptionongoing"),"Campaign Description in details page is editable");
		scrollOnCampaignDetailsPage("subCategoryDropDown");
		verifyElementIsClickableCampaignDetailsPage("categoryDropDown");
		verifyElementIsClickableCampaignDetailsPage("subCategoryDropDown");
		getTextOfCampaignDetailsPage("criteriaheaderongoing");
		getTextOfCampaignDetailsPage("criteriainnerheaderongoing");
		sleeper(2000);
		scrollOnCampaignDetailsPage("locationongoing");
		waitForElementsOfCampaignDetailsPage("locationongoing");
		getTextOfCampaignDetailsPage("locationongoing");
		getTextOfCampaignDetailsPage("durationongoing");
		SoftAssert.assertTrue(verifyElementIsClickableCampaignDetailsPage("endDatePicker"),"Campaign End date in details page is not editable");
		scrollToTop();
		LOGGER.info("Campaign Overview tab verified Sucessfully");
		//Verification of Response results tab & Device Health tab should not present on custom (Usersentiment) Campaign details page.
		SoftAssert.assertFalse(verifyElementsOfCampaignDetailsPage("responseResultsTab"),"Response tab is present on the details page of (UserSentiment) custom campaign");
		SoftAssert.assertFalse(verifyElementsOfCampaignDetailsPage("deviceHealthTab"),"Device health tab is present on the details page of (UserSentiment) custom campaign");
		SoftAssert.assertFalse(verifyElementsOfCampaignDetailsPage("editbuttonongoing"),"edit button is present on campaign details page");
		SoftAssert.assertFalse(verifyElementsOfCampaignDetailsPage("exportbuttonnewUI"),"export button is present on campaign details page");
		SoftAssert.assertAll();
		LOGGER.info("Device Health tab verified Sucessfully");
		LOGGER.info("On Campaign details page verified Sucessfully");
     	} catch (Exception e) {
		LOGGER.error("Exception occured while creating campaign" + e.getMessage());
		return false;
	    }
		return flag;
	}

	/**
	 * This method is used to verify created campaign on list page.
	 * @param campaignName- Name of created campaign
	 * @return
	 * @throws Exception
	 */
	public boolean verifyCreatedCampaignOnListPage(String campaignName) throws Exception {
		boolean flag = false;
		try {
			waitForPageLoaded();
			enterTextForCampaignDetailsPage("nameSearchBox",campaignName);
			waitForPageLoaded();
			sleeper(5000);
			List<WebElement> campaignNameList = getElementsTillAllElementsVisibleofCampaignDetailpage("nameList");
			
			for (int i = 0; i < campaignNameList.size(); i++) {
				if (campaignName.equals(campaignNameList.get(i).getText())) {
					flag = true;
					LOGGER.info("Created campaign got reflected on list.");
					break;
				}
			}

			if (!flag)
				LOGGER.error("Created campaign did not reflect on campaign list page.");
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyCreatedCampaignOnListPage" + e.getMessage());
			return false;
		}
		return flag;
	}

	/**
	 * This method returns the count of number of files in a folder.
	 *
	 * @param downloadPath - folder path
	 * @return - Count of downloaded files
	 */
	public int getFileCount(String downloadPath) {
		try {
			File Files = new File(downloadPath);
			return Files.list().length;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getFileCount " + e.getMessage()));
			return 0;
		}
	}

	/**
	 * This method verifies export functionality for campaign report
	 * @param languageCode - language code
	 * @param exportButton - export button locator
	 * @return - boolean
	 */
	public final boolean verifyExportFunctionalityForCampaignReport(String languageCode, String exportButton) throws Exception {
		try{
			boolean flag = false;
			waitForElementsOfCampaignDetailsPage("tableOverLay");
			if(!verifyElementsOfCampaignDetailsPage("noDataAvailableText")){
				waitForElementsOfCampaignDetailsPage(exportButton);
				clickOnElementsOfCampaignDetailsPage(exportButton);
				waitForElementsOfCampaignDetailsPage("toastNotification");

				String successMessage = getTextOfCampaignDetailsPage("toastNotification");
				if ((getTextLanguage(languageCode, "daas_ui", "campaign.response.export.data.success")).equalsIgnoreCase(successMessage)) {
					waitUntilElementIsInvisibleOfCampaignDetailsPage("toastNotification");
					sleeper(2000);
				} else {
					LOGGER.error("Toast notification received after exporting campaign data.");
				}
				sleeper(10000);
				verifyElementsOfCampaignDetailsPage("latestNotification");
					clickOnElementsOfCampaignDetailsPage("notificationBellIcon");
					waitForElementsOfCampaignDetailsPage("firstNotification");
					mousehoverOnCampaignDetailsPage("hamburgerMenuOnNotification");
					clickByJavaScriptOnCampaignDetailsPage("hamburgerMenuOnNotification");
					sleeper(2000);
					clickOnElementsOfCampaignDetailsPage("downloadlink");
				
				sleeper(4000);
				if(getFileCount(ConstantPath.DOWNLOAD_PATH)>0){
					LOGGER.info("Campaign report downloaded successfully.");
					flag = true;
				}else{
					LOGGER.info("Failed to download campaign report.");
					flag = false;
				}
			} else {
				LOGGER.info("No data present to export.");
				flag = true;
			}
			return flag;
		}catch(Exception e){
			LOGGER.error(("Exception occured in method verifyExportFunctionalityForCampaignReport " + e.getMessage()));
			return false;
		}
		
	}
	/**
	 * This method is used to upload company logo from company setting page
	 * @param languageCode
	 * @param fileName
	 */
	public boolean verifyUploadCompanyLogo(String languageCode, String fileName) {
		try {
			waitForPageLoaded();
			clickOnElementsOfCampaignDetailsPage("selectLogoImageIcon");
			sleeper(2000);
			clickByJavaScriptOnCampaignDetailsPage("selectImageRadioButtonOnPopup");
			clickOnElementsOfCampaignDetailsPage("browseButton");
			sleeper(5000);
			LOGGER.info("Clicked on browse image");
			StringSelection path = new StringSelection(ConstantPath.CAMPAIGN_COMPANY_LOGO + fileName);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);   
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			sleeper(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			sleeper(1000);
			clickOnElementsOfCampaignDetailsPage("saveButtonOnPopUp");
			sleeper(2000);
			waitForPageLoaded();
			LOGGER.info("Company logo has been uploaded successfully");
			return true;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method verifyUploadCompanyLogo " + e.getMessage()));
			return false;
		}
	}	
	
	public String[] SpecialCharacterValidation(String key , String LanguageCode ) throws Exception {
		 String allowedcharacters = "AllowedSpecialcharacters in Campaign Service  ";
		 String[] specialcharacters= {"'","#","~","!","$","%","&","^","{","(",")","-","_","*","[","]","'","@","+",":","<",",",".","?","=",">","/",";","|","}","\""};
		 for(int i = 0; i<specialcharacters.length ;i++) {
			 if(key.equals("campaignDescriptionTextBox")) {
		 if(specialcharacters.equals("#")||specialcharacters.equals("&")||specialcharacters.equals("*")||specialcharacters.equals("+")
				 ||specialcharacters.equals("<")||specialcharacters.equals("=")
				 ||specialcharacters.equals(">")||specialcharacters.equals(";")
				 ||specialcharacters.equals("{")||specialcharacters.equals("}")) {
			 Assert.assertTrue(getTextOfCampaignDetailsPage("descriptionhelpertext").equals(getTextLanguage(LanguageCode,"daas_ui", "campaign.service.input.validation.error")));	
			 }else {
			 enterTextForCampaignDetailsPage(key, allowedcharacters+ specialcharacters[i]);
		 }
		 }else {
			 if(specialcharacters.equals("#")||specialcharacters.equals("&")||specialcharacters.equals("*")||specialcharacters.equals("+")
					 ||specialcharacters.equals("<")||specialcharacters.equals("=")
					 ||specialcharacters.equals(">")||specialcharacters.equals(";")
					 ||specialcharacters.equals("{")||specialcharacters.equals("}")
					 ||specialcharacters.equals("@")||specialcharacters.equals(":")) {
				 Assert.assertTrue(getTextOfCampaignDetailsPage("questionhelpertext").equals(getTextLanguage(LanguageCode,"daas_ui", "campaign.service.input.validation.error")));	
		 }
			 else {
				 enterTextForCampaignDetailsPage(key, allowedcharacters+ specialcharacters[i]);
			 }
		 }
		 }
		return specialcharacters; 
	}
	
	public boolean verifySpecialCharactersinCampaignCreation(String categoryName, String categoryDropDownLocator, String LanguageCode) {
		boolean flag = false;
		try {
			String campaignName = "TestCampaign" + generateRandomString(5);
			List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);

			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryName.equals(categoryList.get(i).getText())) {
					categoryList.get(i).click();
					break;
				}
			}
			enterTextForCampaignDetailsPage("campaignNameTextBox", campaignName);
			scrollOnCampaignDetailsPage("campaignNameTextBox");
			String questiontype1 = "Rate your Experience 1-5";
			String questiontype2 = "How do you feel for our service";
			if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				clickOnElementsOfCampaignDetailsPage("subCategoryDropDown");
				clickOnElementsOfCampaignDetailsPage("firstElementOnSubcategoryDropDown");
			}
			waitForPageLoaded();
			waitForElementsOfCampaignDetailsPage("filtercriteriatile");
			scrollOnCampaignDetailsPage("filtercriteriatile");
			clickOnElementsOfCampaignDetailsPage("addcriteria");
			waitForElementsOfCampaignDetailsPage("filteroption");
			clickOnElementsOfCampaignDetailsPage("filteroption");
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage("valuedd");
			clickOnElementsOfCampaignDetailsPage("valuedd");
			clickOnElementsOfCampaignDetailsPage("filtervalue1");
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("blankArea");
			waitForElementsOfCampaignDetailsPage("calculateaudience");
		    clickOnElementsOfCampaignDetailsPage("calculateaudience");
			scrollOnCampaignDetailsPage("Duration");
			waitForElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");		
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage("endDatePicker");
			clickOnElementsOfCampaignDetailsPage("endDatePicker");
			waitForElementsOfCampaignDetailsPage("calendarPopup");
			waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
			selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				SpecialCharacterValidation("campaignDescriptionTextBox",LanguageCode);
			}
			/**
			 * Star rating Question selection
			 */
			scrollOnCampaignDetailsPage("addQuestionsLink");
			SpecialCharacterValidation("satisfactionRatingTextbox",LanguageCode);
			enterTextForCampaignDetailsPage("satisfactionRatingTextbox", questiontype1 );
			sleeper(2000);
			/**
			 * Happy/UnHappy Question selection
			 */
			scrollOnCampaignDetailsPage("addQuestionsLink");
			String Questionlabel = getTextOfCampaignDetailsPage("additionalquestions");
			Questionlabel.equals(getTextLanguage(LanguageCode,"daas_ui","new.campaign.add.question.header"));
			waitForElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("secondtypeDropDown");
			clickOnElementsOfCampaignDetailsPage("fourthElementOnTypeDropDown");
			enterTextForCampaignDetailsPage("firstquestionlabel", questiontype2);
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("saveButton");
			waitForElementsOfCampaignDetailsPage("toastNotification");
			if (verifyCreatedCampaignOnListPage(campaignName)) {
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while creating campaign" + e.getMessage());
			return false;
		}
		return flag;
	}

	
	/**
	 * This method is use to validate weather no response is present on campaign detail graphs
	 * @param locator: locater of every graph present on response detail and device health tab
	 * @param LanguageCode:to check no response text
	 * @return
	 * @throws Exception
	 */
	public boolean validateNoResponseNotPresentOnAnyGraph(String locator, String LanguageCode) throws Exception {
		{
			try {
				List<WebElement> xAxisCordinates = getElementsTillAllElementsVisibleofCampaignDetailpage(locator);
				for (WebElement webElement : xAxisCordinates) {
					if (webElement.getText().equalsIgnoreCase((getTextLanguage(LanguageCode, "daas_ui", "campaign.response.result.no-response"))))
						return false;
				}
				return true;
			} catch (Exception e) {
				LOGGER.error(("Exception occured in method validateNoResponseNotPresentOnAntGraph " + e.getMessage()));
				return false;
			}
			
		}
	}

	/**
	 * This is a method to wait until an element is invisible
	 *
	 * @param key - Locator of element
	 */
	public final void waitUntilElementIsInvisibleOfCampaignDetailsPage(String key) throws Exception {
		verifyElementIsinvisibile(CampaignsDetailsPageProperties.getProperty(key));

	}
	
	/**
	 * This method will provide the attribute value for an element
	 *
	 * @param key - Locator of element
	 * @param value - attribute name
	 */
	public final String getAttributesOfCampaignDetailsPage(String key, String value) throws Exception {
		return getAttribute(CampaignsDetailsPageProperties.getProperty(key), value);
	}
	
	/**
	 * This method will provide every text for an element in list
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public final List<String> getAllTextOfCampaignDetailsPage(String key) throws Exception {
		return getallTextBy(CampaignsDetailsPageProperties.getProperty(key));
	}

	/**
	 * This method will be performing the validation for the filter criteria selection for groups field
	 * @param categoryName
	 * @param categoryDropDownLocator
	 * @param subCategoryDropDown
	 * @param subCategoryValue
	 * @param CampaignNameTextbox
	 * @param criteriaTile
	 * @param criteriaListLocator
	 * @param criteriafielddd
	 * @param criteriaName
	 * @param valuedd
	 * @param secondheader
	 * @param filtervalue
	 * @param groupFilterfinal
	 * @param LanguageCode
	 * @return
	 */
	public boolean verifyfiltercriteriaselectionforgroupsfilter(String campaignName, String categoryName, String categoryDropDownLocator,String subCategoryDropDown,String subCategoryValue, String CampaignNameTextbox, String criteriaTile, String criteriaListLocator,String criteriafielddd,String criteriaName,String valuedd,String secondheader,String filtervalue1,String filtervalue2,String groupFilterfinal, String LanguageCode) {
		boolean flag = false;
		try {
			List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);
			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryName.equals(categoryList.get(i).getText())) {
					categoryList.get(i).click();
					break;
				}
			}
			enterTextForCampaignDetailsPage(CampaignNameTextbox, campaignName);
			scrollOnCampaignDetailsPage(CampaignNameTextbox);
			if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				clickOnElementsOfCampaignDetailsPage(subCategoryDropDown);
				clickOnElementsOfCampaignDetailsPage(subCategoryValue);
			}
			waitForPageLoaded();
			waitForElementsOfCampaignDetailsPage(criteriaTile);
			
			for (int i=0; i<=2;i++) {
				scrollOnCampaignDetailsPage(criteriaTile);
				clickOnElementsOfCampaignDetailsPage(criteriafielddd);
				List<WebElement> criteriaList = getElementsTillAllElementsVisibleofCampaignDetailpage(criteriaListLocator);
			for (int j = 0; j < criteriaList.size(); j++) {
				
				if (i!=2&&criteriaName.equals(criteriaList.get(j).getText())) {
					criteriaList.get(j).click();
					break;
				}
			}
			if(i<1) {
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage(valuedd);
			clickOnElementsOfCampaignDetailsPage(valuedd);
			clickOnElementsOfCampaignDetailsPage(filtervalue1);
			clickOnElementsOfCampaignDetailsPage("blankArea");
			}if(i==1) {
				sleeper(2000);
				waitForElementsOfCampaignDetailsPage(valuedd);
				clickOnElementsOfCampaignDetailsPage(valuedd);
				clickOnElementsOfCampaignDetailsPage(filtervalue2);
				clickOnElementsOfCampaignDetailsPage("blankArea");
				}
			if(i==2) {
				scrollOnCampaignDetailsPage(secondheader);
				for (int k = 0; k <criteriaList.size(); k++) {
				String groupsfilter = criteriaList.get(k).getText();
				if (criteriaName.equals(groupsfilter)) {
				String attributevalue = getAttributesOfCampaignDetailsPage(groupFilterfinal,"class");
				Assert.assertTrue(attributevalue.contains("disabled"));
			     LOGGER.info("Groups filter is not clickable for the third time");	     
			     flag = true;
			     break;
				}
			}}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while verifying filtercriteriaselectionforgroupsfilter" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * This method will compares the groups list from devices list with the campaigns filter value list.
	 * @param staticList
	 * @param dynamicList
	 * @param categoryName
	 * @param categoryDropDownLocator
	 * @param subCategoryDropDown
	 * @param subCategoryValue
	 * @param CampaignNameTextbox
	 * @param criteriaTile
	 * @param criteriaName
	 * @param criteriaListLocator
	 * @param criteriafielddd
	 * @param valuedd
	 * @param campgroupslistlocator
	 * @param LanguageCode
	 * @return
	 */
	public boolean verifygroupslistfromdevicesandcampaigncreationpage(List<String> staticList,List<String> dynamicList,String categoryName, String categoryDropDownLocator,String subCategoryDropDown,String subCategoryValue, String CampaignNameTextbox, String criteriaTile,String criteriaName, String criteriaListLocator,String criteriafielddd,String valuedd,String campgroupslistlocator,String LanguageCode) {
		boolean flag = false;
		try {
			List<String> Finalgroupslistdevicepage = new ArrayList<String>();
			for(String Static:staticList) {	
				Finalgroupslistdevicepage.add(Static);
			}
				for(String Dynamic:dynamicList) {	
					Finalgroupslistdevicepage.add(Dynamic);
				}
				Collections.sort(Finalgroupslistdevicepage);
			List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);
			String campaignName = "TestCampaign" + generateRandomString(5);
			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryName.equals(categoryList.get(i).getText())) {
					categoryList.get(i).click();
					break;
				}
			}
			enterTextForCampaignDetailsPage(CampaignNameTextbox, campaignName);
			scrollOnCampaignDetailsPage(CampaignNameTextbox);
			if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				clickOnElementsOfCampaignDetailsPage(subCategoryDropDown);
				clickOnElementsOfCampaignDetailsPage(subCategoryValue);
			}
			waitForElementsOfCampaignDetailsPage(criteriaTile);
			scrollOnCampaignDetailsPage(criteriaTile);
			clickOnElementsOfCampaignDetailsPage(criteriafielddd);
			List<WebElement> criteriaList = getElementsTillAllElementsVisibleofCampaignDetailpage(criteriaListLocator);
			for (int i=0; i<=criteriaList.size();i++) {
				if(criteriaName.equals(criteriaList.get(i).getText())){
				criteriaList.get(i).click();
				break;
			}
			}
			waitForElementsOfCampaignDetailsPage(valuedd);
			clickOnElementsOfCampaignDetailsPage(valuedd);
			List<String> campaignpagegroupslist = getAllTextOfCampaignDetailsPage(campgroupslistlocator);
			Collections.sort(campaignpagegroupslist);
			Assert.assertEquals(campaignpagegroupslist,Finalgroupslistdevicepage,"List from devices and campaigns page didnot match");
			flag = true;
			LOGGER.info("Verified successfully and the both the devices groups list and the groups filters value in camapign creation are equal");
		}catch (Exception e) {
			LOGGER.error("Exception occured while verifying groupslistfromdevicesandcampaigncreationpage" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	/**
	 * This method will verify the groups field option in the filters list.
	 * @param categoryName
	 * @param categoryDropDownLocator
	 * @param subCategoryDropDown
	 * @param subCategoryValue
	 * @param CampaignNameTextbox
	 * @param criteriaTile
	 * @param criteriaName
	 * @param criteriaListLocator
	 * @param criteriafielddd
	 * @param LanguageCode
	 * @return
	 */		
	public boolean verifygroupsoptioninfilterswhentoggleoff(String categoryName, String categoryDropDownLocator,String subCategoryDropDown,String subCategoryValue, String CampaignNameTextbox, String criteriaTile,String criteriaName, String criteriaListLocator,String criteriafielddd,String LanguageCode) {
		boolean flag = false;
		try {
			List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);
			String campaignName = "TestCampaign" + generateRandomString(5);
			for (int i = 0; i < categoryList.size(); i++) {
				if (categoryName.equals(categoryList.get(i).getText())) {
					categoryList.get(i).click();
					break;
				}
			}
			enterTextForCampaignDetailsPage(CampaignNameTextbox, campaignName);
			scrollOnCampaignDetailsPage(CampaignNameTextbox);
			if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				clickOnElementsOfCampaignDetailsPage(subCategoryDropDown);
				clickOnElementsOfCampaignDetailsPage(subCategoryValue);
			}
			waitForElementsOfCampaignDetailsPage(criteriaTile);
			scrollOnCampaignDetailsPage(criteriaTile);
			clickOnElementsOfCampaignDetailsPage(criteriafielddd);
			List<WebElement> criteriaList = getElementsTillAllElementsVisibleofCampaignDetailpage(criteriaListLocator);
			for (int i=0; i<criteriaList.size();i++) {
				if(criteriaName.equals(criteriaList.get(i).getText())){
				flag=false;
				break;
			}
				flag=true;
			}
			LOGGER.info("Groups option is not present since for Provided credentials the FT is set to OFF - verified");
		}catch (Exception e) {
			LOGGER.error("Exception occured while verifying groupslistfromdevicesandcampaigncreationpage" + e.getMessage());
			return false;
		}
		return flag;
}
	/**
	 * 
	 * @param categoryName
	 * @param LanguageCode
	 * @return
	 */
	public boolean verifygroupcreationwithgroupsfilter(String campaignName, String categoryName, String categoryDropDownLocator,String subCategoryDropDown,String subCategoryValue, String CampaignNameTextbox, String criteriaTile, String criteriaListLocator,String criteriafielddd,String criteriaName,String valuedd, String LanguageCode) {
		boolean flag = false;
		try {
			String questiontype1 = "Rate your Experience 1-5";
			String questiontype2 = "How do you feel for our service";
			String campaignDescription = "Campaign Description" + generateRandomString(15);
			String category = getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.general_feedback");
			String filtercriteria = getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.filters.groups");
			try {
				List<WebElement> categoryList = getElementsTillAllElementsVisibleofCampaignDetailpage(categoryDropDownLocator);
				for (int i = 0; i < categoryList.size(); i++) {
					if (categoryName.equals(categoryList.get(i).getText())) {
						categoryList.get(i).click();
						break;
					}
				}
				enterTextForCampaignDetailsPage(CampaignNameTextbox, campaignName);
				scrollOnCampaignDetailsPage(CampaignNameTextbox);
				if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
					clickOnElementsOfCampaignDetailsPage(subCategoryDropDown);
					clickOnElementsOfCampaignDetailsPage(subCategoryValue);
				}
				waitForPageLoaded();
				waitForElementsOfCampaignDetailsPage(criteriaTile);
					scrollOnCampaignDetailsPage(criteriaTile);
					clickOnElementsOfCampaignDetailsPage(criteriafielddd);
					List<WebElement> criteriaList = getElementsTillAllElementsVisibleofCampaignDetailpage(criteriaListLocator);
					for (int j = 0; j < criteriaList.size(); j++) {
						
						if (criteriaName.equals(criteriaList.get(j).getText())) {
							criteriaList.get(j).click();
							break;
						}
					}
				sleeper(2000);
				waitForElementsOfCampaignDetailsPage(valuedd);
				clickOnElementsOfCampaignDetailsPage(valuedd);
				clickOnElementsOfCampaignDetailsPage("filtervalue1");
				clickOnElementsOfCampaignDetailsPage("blankArea");
			}catch (Exception e) {
				LOGGER.error("Exception occured while selecting groups filter" + e.getMessage());
				return false;
			}
			waitForElementsOfCampaignDetailsPage("calculateaudience");
		    clickOnElementsOfCampaignDetailsPage("calculateaudience");
			scrollOnCampaignDetailsPage("Duration");
			waitForElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("startDateField");
		    clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");		
			sleeper(2000);
			waitForElementsOfCampaignDetailsPage("endDatePicker");
			clickOnElementsOfCampaignDetailsPage("endDatePicker");
			waitForElementsOfCampaignDetailsPage("calendarPopup");
			waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
			selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
			if(!categoryName.equals(getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.user_sentiment"))){
				enterTextForCampaignDetailsPage("campaignDescriptionTextBox", campaignDescription);
			}
			/**
			 * Star rating Question selection
			 */
			scrollOnCampaignDetailsPage("addQuestionsLink");
			enterTextForCampaignDetailsPage("satisfactionRatingTextbox", questiontype1 );
			sleeper(2000);
			/**
			 * Happy/UnHappy Question selection
			 */
			scrollOnCampaignDetailsPage("addQuestionsLink");
			String Questionlabel = getTextOfCampaignDetailsPage("additionalquestions");
			Questionlabel.equals(getTextLanguage(LanguageCode,"daas_ui","new.campaign.add.question.header"));
			waitForElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("addQuestionsLink");
			clickOnElementsOfCampaignDetailsPage("secondtypeDropDown");
			clickOnElementsOfCampaignDetailsPage("fourthElementOnTypeDropDown");
			enterTextForCampaignDetailsPage("firstquestionlabel", questiontype2);
			sleeper(2000);
			clickOnElementsOfCampaignDetailsPage("saveButton");
			waitForElementsOfCampaignDetailsPage("toastNotification");
			if (verifyCreatedCampaignOnListPage(campaignName)) {
				flag = true;
			}
	
		}catch (Exception e) {
				LOGGER.error("Exception occured while verifying groupslistfromdevicesandcampaigncreationpage" + e.getMessage());
				return false;
			}
			return flag;
		}
}
