package com.basesource.driver;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.logging.Level;

import com.google.common.base.Strings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
//import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.URL;



public class DriverFactory {
	private final static Logger LOGGER = LogManager.getLogger(DriverFactory.class);
	public static String environment = System.getProperty("environment");
	//public static String buildNumber = System.getProperty("buildNumber");

	public static WebDriver setDriver(String browserType, String appURL) {
		WebDriver driver;
		switch (browserType.toUpperCase()) {
		case "CHROME":
			driver = initChromeDriver(appURL);
			break;
		case "FIREFOX":
			driver = initFirefoxDriver(appURL);
			break;
		case "IE":
			driver = initInternetExplorer(appURL);
			break;
		case "EDGE":
			driver = initEdgeDriver(appURL);
			break;
		case "SAFARI":
			driver = initSafariDriver(appURL);
			break;
		default:
			LOGGER.info("Browser : " + browserType + " is invalid, Launching Chrome as Default browser..");
			driver = initChromeDriver(appURL);
		}
		return driver;
	}

	// Method to open chrome browser
	private static WebDriver initChromeDriver(String appURL) {
		LOGGER.info("Launching chrome browser..");

		/*
		 * if (new File(ConstantPath.CHROME_EXE_PATH).exists()) { // This is for Eclipse
		 * IDE System.setProperty("webdriver.chrome.driver",
		 * ConstantPath.CHROME_EXE_PATH); } else { // This is for Jar ClassLoader
		 * classLoader = DriverFactory.class.getClassLoader(); URL resource =
		 * classLoader.getResource("lib/chromedriver.exe"); File f = new File("lib"); if
		 * (!f.exists()) { f.mkdirs(); }
		 * 
		 * File chromeDriver = new File("lib" + File.separator + "chromedriver.exe"); if
		 * (!chromeDriver.exists()) { try { chromeDriver.createNewFile();
		 * org.apache.commons.io.FileUtils.copyURLToFile(resource, chromeDriver); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 * 
		 * System.setProperty("webdriver.chrome.driver",
		 * chromeDriver.getAbsolutePath()); }
		 */
	//	WebDriverManager.chromedriver().clearDriverCache().setup();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", ConstantPath.DOWNLOAD_FOLDER_PATH);
		chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
		chromePrefs.put("download.prompt_for_download", false);

		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL );
		options.setExperimentalOption("prefs", chromePrefs);
	//	DesiredCapabilities cap = DesiredCapabilities.chrome();
		options.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		options.setCapability(ChromeOptions.CAPABILITY, options);
		options.setCapability("goog:loggingPrefs", logPrefs );
		options.addArguments("--disable-dev-shm-usage");// overcome limited resource problems
/*		if (((environment.equalsIgnoreCase("US-STAGING") || environment.equalsIgnoreCase("EU-STAGING")) && !(Strings.isNullOrEmpty(buildNumber))))
		{
			options.addArguments("--headless");
			options.addArguments("window-size=1200x600");
			options.addArguments("--no-sandbox"); // Bypass OS security model
		}*/
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
		return driver;
	}

	// Method to open Firefox browser
	private static WebDriver initFirefoxDriver(String appURL) {
		LOGGER.info("Launching Firefox browser..");
		if (new File(ConstantPath.FIREFOX_EXE_PATH).exists()) {
			System.setProperty("webdriver.gecko.driver", ConstantPath.FIREFOX_EXE_PATH);
			System.setProperty("webdriver.firefox.marionette", ConstantPath.FIREFOX_EXE_PATH);
			System.setProperty("webdriver.firefox.logfile", ConstantPath.FIREFOX_LOG_PATH);
		} else {
			Assert.assertTrue(false, "firefox.exe File is not found");
		}

		FirefoxOptions firefoxOptions =  new FirefoxOptions();
		//DesiredCapabilities dc = DesiredCapabilities.firefox();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", ConstantPath.DOWNLOAD_FOLDER_PATH);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword, application/csv, application/ris, text/csv, data:image/png, image/png, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.useDownloadDir", true);
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.closeWhenDone", true);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.panel.shown", false);
		firefoxOptions.setProfile(profile);
		
		WebDriver driver = new FirefoxDriver(firefoxOptions);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
		return driver;
	}

	// Method to open IE browser
	private static WebDriver initInternetExplorer(String appURL) {
		LOGGER.info("Launching IE browser..");
		if (new File(ConstantPath.IE_EXE_PATH).exists()) {
			System.setProperty("webdriver.ie.driver", ConstantPath.IE_EXE_PATH);
		} else {
			Assert.assertTrue(false, "IE.exe File is not found");
		}
		WebDriver driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
		return driver;
	}

	// Method to open Edge browser (Keeping this method commented until the new one gets stabilized)
//	private static WebDriver initEdgeDriver(String appURL) {
//		LOGGER.info("Launching Edge browser..");
////		if (new File(ConstantPath.EDGE_PATH).exists()) {
////			System.setProperty("webdriver.edge.driver", ConstantPath.EDGE_PATH);
////		} else {
////			Assert.assertTrue(false, "MicrosoftWebdriver.exe File is not found");
////		}
//		WebDriverManager.edgedriver().clearDriverCache().setup();
//		HashMap<String, Object> edgePrefs = new HashMap<>();
//		edgePrefs.put("profile.default_content_settings.popups", 0);
//		edgePrefs.put("download.default_directory", ConstantPath.DOWNLOAD_FOLDER_PATH);
//		edgePrefs.put("credentials_enable_service", false); // Disable password saving pop-up
//		edgePrefs.put("profile.password_manager_enabled", false); // Disable password manager
//		edgePrefs.put("edge.enhanced_web_experience_enabled", false);
//		edgePrefs.put("edge.personalize_web_experience_enabled", false);
//		if (((environment.equalsIgnoreCase("US-PRODUCTION"))|| (environment.equalsIgnoreCase("EU-PRODUCTION"))))
//		{
//			edgePrefs.put("profile.default_content_settings.cookies", 1); // 1 to allow cookies
//		}
//
//
//		EdgeOptions options = new EdgeOptions();
//		LoggingPreferences logPrefs = new LoggingPreferences();
//		logPrefs.enable(LogType.PERFORMANCE, Level.ALL );
//		options.setExperimentalOption("prefs", edgePrefs);
//		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//		options.setCapability("ms:loggingPrefs", logPrefs );
//		//options.addArguments("window-size=1450x850");
//		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//		options.setExperimentalOption("useAutomationExtension", false);
//		options.addArguments("--disable-dev-shm-usage");// overcome limited resource problems
//		WebDriver driver = new EdgeDriver(options);
//		driver.manage().window().maximize();
//		driver.navigate().to(appURL);
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
//		return driver;
//	}

	private static WebDriver initEdgeDriver(String appURL) {
		LOGGER.info("Launching Edge browser..");

		/*try {
			// Setup EdgeDriver using WebDriverManager with new Microsoft CDN
			WebDriverManager.edgedriver()
					.clearDriverCache()
					.driverRepositoryUrl(new URL("https://msedgedriver.microsoft.com/"))
					.setup();
		} catch (Exception e) {
			throw new RuntimeException("Failed to configure EdgeDriver repository URL", e);
		}*/
		if (new File(ConstantPath.EDGE_PATH).exists()) {
		System.setProperty("webdriver.edge.driver", ConstantPath.EDGE_PATH);
	  } else {
		Assert.assertTrue(false, "MicrosoftWebdriver.exe File is not found");
	  }

	// Edge preferences
	HashMap<String, Object> edgePrefs = new HashMap<>();
	edgePrefs.put("profile.default_content_settings.popups", 0);
	edgePrefs.put("download.default_directory", ConstantPath.DOWNLOAD_FOLDER_PATH);
	edgePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
	edgePrefs.put("download.prompt_for_download", false);
	edgePrefs.put("credentials_enable_service", false);
	edgePrefs.put("profile.password_manager_enabled", false);
	edgePrefs.put("edge.enhanced_web_experience_enabled", false);
	edgePrefs.put("edge.personalize_web_experience_enabled", false);

	if (environment.equalsIgnoreCase("US-PRODUCTION") || environment.equalsIgnoreCase("EU-PRODUCTION")) {
		edgePrefs.put("profile.default_content_settings.cookies", 1);
	}		// Edge options
		EdgeOptions options = new EdgeOptions();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

		options.setExperimentalOption("prefs", edgePrefs);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		options.setCapability("ms:loggingPrefs", logPrefs);
		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--disable-dev-shm-usage");

		WebDriver driver = new EdgeDriver(options);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));

		return driver;
	}




	// Method to open Safari browser
	private static WebDriver initSafariDriver(String appURL) {
		LOGGER.info("Launching Safari browser..");
		WebDriver driver = new SafariDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
		return driver;
	}
}
