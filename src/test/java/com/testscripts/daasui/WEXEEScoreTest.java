package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.WEPPulsesCreationPageVariables;
import com.daasui.pages.WEXDetailsPage;
import com.daasui.pages.WEXEEDashboardPage;
import com.daasui.pages.WEXEEPulseResultPage;

public class WEXEEScoreTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEXEEScoreTest.class);

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL";
		data[0][1] = "PARTNER_PASSWORD";
		data[1][0] = "COMPANY_EMAIL";
		data[1][1] = "COMPANY_PASSWORD";
		return data;
	}

	/**
	 * This method will verify the presence of widgets on EE dashboard screen
	 *
	 * @throws Exception
	 */

	@Test(priority = 1, groups = { "REGRESSION_EE_SCORE","PRODUCTION_PULSES_EE_SUSTAIN" }, description = "TestCase ID : C51280598")
	public final void verifyPresenceOfWidgetsForWEXEEDashboardPage() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEDashboardPage WEXEEDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			//Navigation check
			WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			WEXEEDashboardPage.actionClickOnWEXEEDashboardPage("employeeEngagementDashboardSelection");
			WEXEEDashboardPage.actionClickOnWEXEEDashboardPage("ViewReport");
		}
		
		Assert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScoreWidget"),
				"SentimentScore Widget is not present");
		Assert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("ResponsesWidget"),
				"Responses Widget is not present");
		Assert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentscoreOvertimeWidget"),
				"Sentiment Score over time Widget is not present");
		Assert.assertTrue(
				WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("TopIssuesImpactingSentimentWidget"),
				"TopIssuesImpactingSentiment Widget is not present");
		Assert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("AllPulsesWidget"),
				"AllPulses Widget is not present");
		LOGGER.info("Employee Engagement dashboard widgets are validated");

	}


	/**
	 * This method will verify the Sentiment score widget on EE dashboard screen
	 *
	 * @throws Exception
	 */

	@Test(priority = 2, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280598")
	public final void verifySentimentScoreWidgetForWEXEEDashboardPage() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEDashboardPage wexeeDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("employeeEngagementDashboardSelection");
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("ViewReport");
		}
		
		waitForPageLoaded();
		Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScoreWidget"),
				"SentimentScore Widget is not present");
		Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScoreIcon"),
				"Sentiment Icon is not present");
		//Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScoreProgressScorePercent"),"Sentiment score progress percentage is not showing");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsOfWEXEEDashboardPage("SentimentScoreProgressBar"),
				"Sentiment score progress bars are not showing");
		Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScore"),
				"Sentiment Score is not showing");
		Assert.assertTrue(
				wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScoreBenchmark"),
				"Sentiment score benchmark is not present");
		Assert.assertTrue(wexeeDashboardPage.matchTextOfWEXEEDashboardpage("SentimentScoreTitle", getTextLanguage(LanguageCode, "daas_ui","ee_sentiment")), "Sentiment score header title is incorrect");
		wexeeDashboardPage.clickOnElementsOfWEXEEDashboardPage("SentimentToolTip");
		Assert.assertTrue(wexeeDashboardPage.matchTextOfWEXEEDashboardpage("SentimentscoreTooltipheader", getTextLanguage(LanguageCode, "daas_ui","ee_dashboard_SentiScoreCalculator")), "Sentiment score tool tip header title is incorrect");
		LOGGER.info("Employee Engagement dashboard widgets are validated");

	}
	
	/**
	 * This method will verify the Responses sentiment pulse widget on EE dashboard screen
	 *
	 * @throws Exception
	 */

	@Test(priority = 3, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280598")
	public final void verifyResponsesSentimentPulseWidgetForWEXEEDashboardPage() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEDashboardPage wexeeDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("employeeEngagementDashboardSelection");
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("ViewReport");
		}
		waitForPageLoaded();
		Assert.assertTrue(wexeeDashboardPage.matchTextOfWEXEEDashboardpage("ResponsesWidgetTitle", getTextLanguage(LanguageCode, "daas_ui","ee_dashboard_employeeResponse")), "Responses -sentiment pulse header title is incorrect");
		Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("ResponsesWidgetIcon"),
				"Sentiment responses Icon is not present");
		Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("ResponsesWidgetcount"),
				"Sentiment responses count is not present");

	}
	
	/**
	 * This method will verify the Top issues impacting sentiment widget on EE dashboard screen
	 *
	 * @throws Exception
	 */

	@Test(priority = 4, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280598")
	public final void verifyTopIssuesImapctingSentimentWidgetForWEXEEDashboardPage() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEDashboardPage wexeeDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("employeeEngagementDashboardSelection");
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("ViewReport");
		}
		waitForPageLoaded();
		Assert.assertTrue(wexeeDashboardPage.matchTextOfWEXEEDashboardpage("TopIssuesImpactingSentimentWidgetTitle", getTextLanguage(LanguageCode, "daas_ui","ee_dashboard_sentimentTopIssue")), "Top Issues impacting sentiment header title is incorrect");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsOfWEXEEDashboardPage("YaxisOptionsTIIS"),
				"Yaxis labels are not present");
		Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("TopIssuesChart"),
				"Top Issues Chart is not present");

	}
	
	/**
	 * This method will verify the All pulses widget on EE dashboard screen
	 *
	 * @throws Exception
	 */

	@Test(priority = 5, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280598")
	public final void verifyAllPulsesWidgetForWEXEEDashboardPage() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEDashboardPage wexeeDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("employeeEngagementDashboardSelection");
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("ViewReport");
		}
		waitForPageLoaded();
		Assert.assertTrue(wexeeDashboardPage.matchTextOfWEXEEDashboardpage("AllPulsesWidgetTitle", getTextLanguage(LanguageCode, "daas_ui","ee_dashboard_EmployeeEngPulses")), "All Pulses header title is incorrect");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsWithCountOfWEXEEDashboardPage("AllpulseKPI",WEPPulsesCreationPageVariables.EE_Dashboard_AllPulses_KPI_COUNT),
				"All pulse KPI's are not present not present");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsWithCountOfWEXEEDashboardPage("AllpulseKPIValue",WEPPulsesCreationPageVariables.EE_Dashboard_AllPulses_KPI_COUNT),
				"All pulse KPI's values are not present");
		wexeeDashboardPage.clickOnElementsOfWEXEEDashboardPage("ViewDetails");
		Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("PulseListpageBreadcrumbs"),
				"view details link on All pulse widget did not land on pulse list page");

	}
	
	/**
	 * This method will verify the Sentiment Score OverTime Widget widget on EE dashboard screen
	 *
	 * @throws Exception
	 */

	@Test(priority = 6, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280598")
	public final void verifySentimentScoreOverTimeWidgetForWEXEEDashboardPage() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEDashboardPage wexeeDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("employeeEngagementDashboardSelection");
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("ViewReport");
		}
		waitForPageLoaded();
		Assert.assertTrue(wexeeDashboardPage.matchTextOfWEXEEDashboardpage("SentimentscoreOvertimeWidgetTitle", getTextLanguage(LanguageCode, "daas_ui","ee_sentiment_over_time_heading")), "Sentiment experience over time header title is incorrect");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsWithCountOfWEXEEDashboardPage("OvertimeLabelsTitle",WEPPulsesCreationPageVariables.EE_Dashboard_OverTime_Labels_COUNT),
				"OverTime indicator labels are not present");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsWithCountOfWEXEEDashboardPage("OvertimeLabelsValue",WEPPulsesCreationPageVariables.EE_Dashboard_OverTime_Labels_COUNT),
				"OverTime indicator Values are not present");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsOfWEXEEDashboardPage("OvertimeGraphLine"),
				"OverTime Graph lines are not present");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsOfWEXEEDashboardPage("OvertimeXaxisLabel"),
				"OverTime Graph lines are not present");
		Assert.assertTrue(wexeeDashboardPage.verifyElementsOfWEXEEDashboardPage("DateRangeBox"));
		//wexeeDashboardPage.clickOnElementsOfWEXEEDashboardPage("DateRangeBoxArrow");
		if(wexeeDashboardPage.waitForElementsOfWEXEEDashboardPage("DateRangeBoxArrow"));
		{
		wexeeDashboardPage.actionClickOnWEXEEDashboardPage("DateRangeBoxArrow");
		}
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsWithCountOfWEXEEDashboardPage("DateRangeBoxOptions",WEPPulsesCreationPageVariables.EE_Dashboard_OverTime_DateRangeOption_COUNT),
				"Date range filter options are not present");
		
		
	}
	
	/**
	 * This method will verify the filter UI on EE dashboard screen
	 *
	 * @throws Exception
	 */

	@Test(priority = 7, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280598")
	public final void verifyFilterForWEXEEDashboardPage() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEDashboardPage wexeeDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			wexeeDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("employeeEngagementDashboardSelection");
			wexeeDashboardPage.actionClickOnWEXEEDashboardPage("ViewReport");
		}
		waitForPageLoaded();
		wexeeDashboardPage.clickOnElementsOfWEXEEDashboardPage("FilterBtn");
	//	Assert.assertTrue(wexeeDashboardPage.matchTextOfWEXEEDashboardpage("FilterinfoText", getTextLanguage(LanguageCode, "daas_ui","ee.dashboard.filters")), "Filter info text is incorrect");
		wexeeDashboardPage.actionClickOnWEXEEDashboardPage("DateRangeArrow");
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsWithCountOfWEXEEDashboardPage("DateRangeOptions", WEPPulsesCreationPageVariables.Experience_dashboard_filter_dateRange_options_COUNT), "Date range option is missing from filter");
		Assert.assertTrue(wexeeDashboardPage.MatchFilterCountOnWEXEEDashboardPage());
		Assert.assertTrue(wexeeDashboardPage.verifyListofElementsWithCountOfWEXEEDashboardPage("GlobalFilterLabel", 2), "Global filter label is not present on Either Sentiment overtime or Pulse All widget");
		wexeeDashboardPage.clickOnElementsOfWEXEEDashboardPage("FilterBtn");
        wexeeDashboardPage.actionClickOnWEXEEDashboardPage("ClearAllBtn");	        
        wexeeDashboardPage.actionClickOnWEXEEDashboardPage("ApplyBtn");	        
	
		
	}

	/**
	 * This method will verify the presence of widgets on Experience details screen
	 *
	 * @throws Exception
	 */

	@Test(priority = 8, groups = { "REGRESSION_EE_SCORE", "PRODUCTION_PULSES_EE_SUSTAIN" }, description = "TestCase ID : C51280594")
	public final void verifyPresenceOfWidgetsForWEXDetailsPage() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXDetailsPage WEXDetailsPage = new WEXDetailsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		waitForPageLoaded(); 
		WEXDetailsPage.clickOnElementsOfWEXDetailsPage("WEXviewDetail");
		waitForPageLoaded();
		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("ExperienceDetailPage"),"User did not land on experience details page");
		
		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("topIssueimpactSentiment"),"Top issues impacting sentiment widget is not present");
		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("TopIssueSentimentChart"),"Top issues impacting sentiment chart is not loaded");

		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("TopDigitalIssuesHealth"),"Top Digital Issues Health widget is not present");
		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("TopDigitalIssuesHealthEmpState"),"Top Digital Issues Health widget is not loaded");

		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("TopDigitalIssuesPerformance"),"Top Digital Issues Performance widget is not present");
		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("TopDigitalIssuesPerformanceEmpState"),"Top Digital Issues Performance widget is not loaded");

		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("ExperienceOverTime"),"Experience over time widget is not present");
		Assert.assertTrue(WEXDetailsPage.verifyListofElementsWithCountOfWEXDetailsPage("ExperienceOverTimeGraphLines",WEPPulsesCreationPageVariables.Experience_OverTime_graphLines_COUNT),"Experience over time graph lines are not present on the experience details page");

		Assert.assertTrue(WEXDetailsPage.verifyElementsOfWEXDetailsPage("ExperienceScoreWidget"),"Experience score widget is not present");
		Assert.assertTrue(WEXDetailsPage.verifyListofElementsWithCountOfWEXDetailsPage("ExperienceScoreParameters",WEPPulsesCreationPageVariables.Experience_Score_parameter_COUNT),"Experience score parameter is missing");

		Assert.assertTrue(WEXDetailsPage.verifyListofElementsWithCountOfWEXDetailsPage("ExperiencePills",WEPPulsesCreationPageVariables.Experience_Details_Pills_COUNT),"Experience pills are not present at the top of the experience details page");
		Assert.assertTrue(WEXDetailsPage.verifyListofElementsOfWEXDetailsPage("ExperienceDetailstabList"),"Tabs are missing on Experience details page");

		LOGGER.info("Experience details page widgets are validated");

	}

	/**
	 * This method will verify the presence of basic elements on pulse result page
	 *
	 * @throws Exception
	 */

	@Test(priority = 9, groups = { "REGRESSION_EE_SCORE" , "PRODUCTION_PULSES_EE_SUSTAIN" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResult() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		Assert.assertTrue(wexeepulseResultPage.verifyElementsOfWEXEEPulseResultPage("PulseStatus"),"Pulse status is not showing");
		Assert.assertTrue(wexeepulseResultPage.verifyElementsOfWEXEEPulseResultPage("filterIcon"),"Filter Icon is not showing");
	//	Assert.assertTrue(wexeepulseResultPage.VerifyPageTitleWithPulseName("pulseresultpageTitle", "pulseName"),"Pulse name and pulse result page title is not matching");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("PulseResultTabs"),"Tabs missing on pulse result page");
		Assert.assertTrue(wexeepulseResultPage.verifyElementsOfWEXEEPulseResultPage("ActionButton"),"Action button is not showing");
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("ActionButton");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsWithCountOfWEXEEPulseResultPage("ActionButtonOptions", WEPPulsesCreationPageVariables.PulseResult_ActionButton_options_COUNT), "Option under action button is missing");
		LOGGER.info("Pulse result page UI is validated");


	}


	/**
	 * This method will verify the presence of basic elements on Audience tab of pulse result page
	 *
	 * @throws Exception
	 */

	@Test(priority = 10, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResultAudienceTab() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("AudienceTab");
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("AudienceTabTitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_audience")));
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("AudienceLabels"), "Method labels are not displayed");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("AudienceMethodType"), "Method type or reach is not displayed");	
		LOGGER.info("Pulse result page Audience tab UI is validated");
	}


	/**
	 * This method will verify the presence of basic elements on Schedule tab of pulse result page
	 *
	 * @throws Exception
	 */

	@Test(priority = 11, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResultScheduleTab() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("ScheduleTab");
		waitForPageLoaded();
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("ScheduleLabels"), "Method labels are not displayed");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("ScheduleMethodType"), "Method labels are not displayed");				
		List<String> expectedText= new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_duration"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_frequency")));	    
		Assert.assertTrue(wexeepulseResultPage.matchTextsOfPulseResultPage(expectedText, "AudienceTabTitle"),"Schedule tab section labels are missing");
		Assert.assertTrue(wexeepulseResultPage.verifyElementsOfWEXEEPulseResultPage("ScheduletabPriority"), "Priority header not matching");
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("ScheduletabPriorityIcon");
		List<String> expectedsubText= new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "ee.urgentPulse.pulseResult.sideBarInfo.dateBased.standardPriority"),getTextLanguage(LanguageCode, "daas_ui", "ee.urgentPulse.pulseResult.sideBarInfo.dateBased.urgentPriority")));	    
		System.out.println(expectedsubText);
		Assert.assertTrue(wexeepulseResultPage.matchTextsOfPulseResultPage(expectedsubText, "PriorityIconSubHeaders"),"Schedule tab priority section sub headers are missing");
		Assert.assertTrue(wexeepulseResultPage.verifyElementsOfWEXEEPulseResultPage("PriorityIconHeader"), "Priority header is missing");
		LOGGER.info("Pulse result page schedule tab UI is validated");
	}

	/**
	 * This method will verify the overview KPIs on pulse result page
	 *
	 * @throws Exception
	 */

	@Test(priority = 12, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResultOverviewKPI() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		waitForPageLoaded();
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("Overviewtitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_overview")));
		List<String> expectedText= new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_delivered"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_viwed"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_total_responses"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_unique_responses"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_responseRate")));
		Assert.assertTrue(wexeepulseResultPage.matchTextsOfPulseResultPage(expectedText, "OverViewKPIs"));
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("OverviewKPIicon"), "Overview KPI icons are not displayed");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("OverviewKPIcount"), "Overview KPI count are not displayed");
		LOGGER.info("Pulse result page Overview KPIs are validated");
	}


	/**
	 * This method will verify the overview tool tip on pulse result page
	 *
	 * @throws Exception
	 */

	@Test(priority = 13, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResultOverviewTooltip() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("overviewTooltip");
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("OverviewDefinationtitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_overview_definations")));
		List<String> expectedText= new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_delivered"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_viwed"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_total_responses"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_unique_responses"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_responseRate")));
		Assert.assertTrue(wexeepulseResultPage.matchTextsOfPulseResultPage(expectedText, "overviewTooltipKPItitle"));
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("overviewTooltipKPIicon"), "Overview tool tip KPI icons are not displayed");
		List<String> expectedTexttooltip= new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_delivered_kpi_info"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_viewd_kpi_info"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_total_responses_kpi_info"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_unique_responses_kpi_info"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_responseRateFormula")));
		Assert.assertTrue(wexeepulseResultPage.matchTextsOfPulseResultPage(expectedTexttooltip, "overviewTooltipKPIcontent"));
		Assert.assertTrue(wexeepulseResultPage.verifyElementsOfWEXEEPulseResultPage("overviewTooltipclosebtn"), "Close button on overview defination tooltip is not present");
		LOGGER.info("Pulse result page Overview tooltip is validated");
	}
	
	/**
	 * This method will verify questions of sentiment on pulse result page
	 *
	 * @throws Exception
	 */
	
	@Test(priority = 14, groups = { "REGRESSION_EE_SCORE", "PRODUCTION_PULSES_EE_SUSTAIN" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResultquestion() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		waitForPageLoaded();
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("ResultTitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_results")));
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("QuestionTitle"), "Sentiment question title is missing on pulse result page");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("ResponsePill"), "Sentiment question response pill is missing on pulse result page");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("AxisLabel"), "Sentiment question Axis label on highcharts are missing on pulse result page");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("HighChartsGraph"), "Sentiment question Hight charts are missing on pulse result page");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("PulseDetailPills"), "Sentiment questions pills are missing on pulse result page");
	

}
	/**
	 * This method will verify first question of sentiment on pulse result page
	 *
	 * @throws Exception
	 */


	@Test(priority = 15, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResultFirstquestion() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		waitForPageLoaded();
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("ResultTitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_results")));
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("YaxisLabels"), " First Sentiment question Yxis labels are missing");
		Assert.assertEquals(wexeepulseResultPage.getCountOfRowsonWEXEEPulseResultPage("YaxisLabels"), WEPPulsesCreationPageVariables.SENTIMENT_FIRST_YAXIS_LABEL_COUNT);		
        List<String> expectedText= new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_veryDissatisfied"),getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_verySatisfied")));
		Assert.assertTrue(wexeepulseResultPage.matchTextsOfPulseResultPage(expectedText, "StarRatingValue"));
		Assert.assertTrue(wexeepulseResultPage.verifyElementsOfWEXEEPulseResultPage("ResultCountIcon"),"Result icon is not present on first question of sentiment pulse");
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsOfWEXEEPulseResultPage("AverageRatingValue"), " For first Sentiment question average rating value is missing");
		List<String> expectedText2 = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_averageRating"),getTextLanguage(LanguageCode, "daas_ui", "ee_five_star_question_low_rating_text"),getTextLanguage(LanguageCode, "daas_ui", "ee_five_star_question_high_rating_text")));
		Assert.assertTrue(wexeepulseResultPage.matchTextsOfPulseResultPage(expectedText2, "RatingTitletext"));
		
	}
	
	/**
	 * This method will verify second question of sentiment on pulse result page
	 *
	 * @throws Exception
	 */
	
	@Test(priority = 16, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResultSecondquestion() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		waitForPageLoaded();
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("ResultTitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_results")));
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("SecondCommentTitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_other_responses")));
		Assert.assertTrue(wexeepulseResultPage.verifyListofElementsWithCountOfWEXEEPulseResultPage("YaxisLabelstitle",WEPPulsesCreationPageVariables.SENTIMENT_SECOND_YAXIS_LABEL_COUNT), "Y axis labels are missing on sentiment second question");

	}
	
	/**
	 * This method will verify third question of sentiment on pulse result page
	 *
	 * @throws Exception
	 */
	
	@Test(priority = 17, groups = { "REGRESSION_EE_SCORE" }, description = "TestCase ID : C51280599")
	public final void verifySentimentPulseResultThirdquestion() throws Exception {
		login("EE_SCORE_COMPANY_EMAIL", "EE_SCORE_COMPANY_PASSWORD");
		WEXEEPulseResultPage wexeepulseResultPage = new WEXEEPulseResultPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();
		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"))){
			wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}else {
				wexeepulseResultPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
			}
		waitForPageLoaded();
		wexeepulseResultPage.clickOnElementsOfWEXEEPulseResultPage("FirstSentimentPulse");
		waitForPageLoaded();
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("ResultTitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_results")));
		Assert.assertTrue(wexeepulseResultPage.matchTextOfPulseResultPage("ThirdCommentTitle", getTextLanguage(LanguageCode, "daas_ui", "ee_pulse_label_responses")));

	}

}
