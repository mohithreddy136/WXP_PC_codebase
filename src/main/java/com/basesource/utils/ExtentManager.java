package com.basesource.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.daasui.constants.ConstantPath;

public class ExtentManager {
	private static ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public static String reportFileName;
	public static String environment = System.getProperty("environment");

	public static ExtentReports getInstance() {
		System.setProperty("app.timestamp", new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date()));
		String timeStamp = System.getProperty("app.timestamp");
		reportFileName = ConstantPath.REPORT_PATH + environment+"-Selenium-DaaS-Result-Report-" + timeStamp + "" + ".html";

		htmlReporter = new ExtentHtmlReporter(reportFileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(reportFileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(reportFileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
		return extent;
	}
}
