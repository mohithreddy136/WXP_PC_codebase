package com.basesource.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.daasui.constants.ConstantPath;

public class SNOWInstanceWakeUP extends CommonMethod {

	private static Logger LOGGER = LogManager.getLogger(SNOWInstanceWakeUP.class);
	private ObjectReader SNOWInstanceWakeUPPagepropertiesPagePropertiesReader = new ObjectReader();
	private Properties SNOWInstanceWakeUPPagepropertiesPageProperties;
	static PreDefinedActions preDefinedActions = new PreDefinedActions();

	public final void wakeUpSNOWInstance(String snowURL, String snowEmail, String snowUsername, String snowPassword) throws Exception {
		SNOWInstanceWakeUPPagepropertiesPageProperties = SNOWInstanceWakeUPPagepropertiesPagePropertiesReader.getObjectRepository("SNOWInstanceWakeUPPage");
		System.setProperty("webdriver.chrome.driver", ConstantPath.CHROME_EXE_PATH);
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", ConstantPath.DOWNLOAD_FOLDER_PATH);
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		options.setExperimentalOption("prefs", chromePrefs);
		options.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		//options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(ChromeOptions.CAPABILITY, options);
		WebDriver driver = new ChromeDriver(options);
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.manage().window().maximize();
			driver.navigate().to(snowURL);
			if (getElementFromPage(driver, SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowLoginPage")) != null) {
				LOGGER.info("SNOW Instance login page is displayed hence SNOW Instance is up.");
			} else if (getElementFromPage(driver, SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowUserName")) != null) {
				LOGGER.info("SNOW Instance is in hibernate mode so waking up !!!");
				WebElement objInstanceLoginTextField = getElementFromPage(driver, SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowUserName"));
				objInstanceLoginTextField.sendKeys(snowEmail);
				WebElement objInstanceLoginNext = getElementFromPage(driver, SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowSigninNext"));
				objInstanceLoginNext.click();
				WebElement objInstanceLoginPassword = getElementFromPage(driver, SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowPassword"));
				objInstanceLoginPassword.sendKeys(snowPassword);
				WebElement objInstanceSignInButton = getElementFromPage(driver, SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowSignInButton"));
				objInstanceSignInButton.click();
				WebElement objInstanceClickOnManage = getElementFromPage(driver,
						SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowManageTab"));
				objInstanceClickOnManage.click();
				WebElement objInstanceClick = getElementFromPage(driver,
						SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowInstanceTab"));
				objInstanceClick.click();
				WebElement objWakeupButton = getElementFromPage(driver,
						SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowWakeupButton"));
				objWakeupButton.click();
				WebElement objWaitTimer = getElementFromPage(driver, SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowWakeUpPage"));
				if (objWaitTimer != null) {
					WebElement request = getElementFromPage(driver, SNOWInstanceWakeUPPagepropertiesPageProperties.getProperty("snowRequestButton"));
					if (!request.isDisplayed()) {
						waitForNumberOfWindowsToEqual(driver, 2, 2000);
						LOGGER.info("SNOW Instance is wake up Successfully.");
					}
					else {
						LOGGER.info("SNOW Instance is expired. It's requesting for the new instance.");
					}
				}
				switchToNewOpenTab(driver);
			}
			driver.quit();
		} catch (Exception ex) {
			driver.quit();
			LOGGER.error("Exception thrown in main method:" + ex.toString());
			throw ex;
		}
	}

	public static void waitForNumberOfWindowsToEqual(WebDriver driver, final int numberOfWindows, int timeout) {
		new WebDriverWait(driver, Duration.ofSeconds(timeout)) {
		}.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (driver.getWindowHandles().size() == numberOfWindows);
			}
		});
	}

	public static void switchToNewOpenTab(WebDriver driver) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
	}

	public static WebElement getElementFromPage(WebDriver driver, String locator) {
		try {
			WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement objElement = null;
			objElement = objWait.until(ExpectedConditions.presenceOfElementLocated(preDefinedActions.getObject(locator)));
			return objElement;
		} catch (NoSuchElementException | TimeoutException ex) {
			return null;
		}
	}

}
