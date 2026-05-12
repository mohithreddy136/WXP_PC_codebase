package com.daasui.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.pages.WEPAlertsDashboardPage.VerificationException;

public class WXPRolesandPermissionPage extends CommonMethod {
	private WXPRolesandPermissionPage instance;
	private ObjectReader WXPRolesandPermissionPagePropertiesReader = new ObjectReader();
	private Properties WXPRolesandPermissionPageProperties;
	private final static Logger LOGGER = LogManager.getLogger(DashboardPage.class);
	public static String environment;
	public static String uiVersion = System.getProperty("uiVersion");

	public WXPRolesandPermissionPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WXPRolesandPermissionPage.class) {
				if (instance == null) {
					instance = new WXPRolesandPermissionPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public WXPRolesandPermissionPage(WebDriver driver) throws IOException {
		WXPRolesandPermissionPageProperties = WXPRolesandPermissionPagePropertiesReader
				.getObjectRepository("WXPRolesandPermissionPage");
	}

	{

	}

	public final boolean verifyElementsOfRolesAndPermissionPage(String key) throws Exception {
		return verifyElementIsPresent(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public final void actionClickOfRolesAndPermissionPage(String key) throws Exception {
		actionClick(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsClickableOfRolesAndPermissionPage(String key) throws Exception {
		return verifyElementIsClickable(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public final void scrollOnRolesAndPermissionPage(String key) throws Exception {
		scrollTillView(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public final String getTextOfRolesAndPermissionPage(String key) throws Exception {
		return getTextBy(WXPRolesandPermissionPageProperties.getProperty(key));

	}

	public final void clickOnElementsOfRolesAndPermissionPage(String key) throws Exception {
		click(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfRolesAndPermissionPage(String key, int waitTime) throws Exception {
		return verifyElementIsVisibleDynamic(WXPRolesandPermissionPageProperties.getProperty(key), waitTime);
	}

	public final boolean verifyElementIsinvisibileOfRolesAndPermissionPage(String key) throws Exception {
		return verifyElementIsinvisibile(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public void switchToIframeofOfRolesAndPermissionPage(String key) throws Exception {
		switchToIframe(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public void switchToDefaultContentofRolesAndPermissionPage() throws Exception {
		switchToDefaultContent();
	}

	public final List<WebElement> getllAllElementsVisibleofRolesAndPermissionPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public final void mouseHoverOnRolesAndPermissionPage(String key) throws Exception {
		mouseHover(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public final List<String> getllAllElementsTextVisibleofRolesAndPermissionPage(String key) throws Exception {
		return getUniqueElementsofStringsFromList(WXPRolesandPermissionPageProperties.getProperty(key));
	}

	public final void enterTextOfRolesAndPermissionPage(String key, String Text) {
		try {
			enterText(WXPRolesandPermissionPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXCustomerUsersDetailsPage " + e.getMessage()));
		}
	}

	public final boolean waitForElementsOffRolesAndPermissionPage(String key) throws Exception {
		return verifyElementIsVisible(WXPRolesandPermissionPageProperties.getProperty(key));
	}
	
	public final String getTextOfSpecificElementOnRolesAndPermissionPage(String key, String i) throws Exception {
		return getTextBy(WXPRolesandPermissionPageProperties.getProperty(key).replace("[i]", i));
	}

	public final boolean verifySpecificElementOnRolesAndPermissionPage(String key, String i) throws Exception {
		return verifyElementIsPresent(WXPRolesandPermissionPageProperties.getProperty(key).replace("[i]", i));
	}

	public final List<WebElement> getllAllElementsVisibleofRolesAndPermissionPage(String key, String i)
			throws Exception {
		return getElementsTillAllElementsVisible(
				WXPRolesandPermissionPageProperties.getProperty(key).replace("[i]", i));
	}

	public final void clickOnSpecificElementsOfRolesAndPermissionPage(String key, String i) throws Exception {
		click(WXPRolesandPermissionPageProperties.getProperty(key).replace("[i]", i));
	}

	public final void scrollOnRolesAndPermissionPage(String key, String i) throws Exception {
		scrollTillView(WXPRolesandPermissionPageProperties.getProperty(key).replace("[i]", i));
	}

	public final String getTextOfSpecificElementOnRolesAndPermissionPage(String key, String i, String j)
			throws Exception {
		String property = WXPRolesandPermissionPageProperties.getProperty(key).replace("[j]", j);
		return getTextBy(property.replace("[i]", i));
	}

	public final boolean verifySpecificElementOnRolesAndPermissionPage(String key, String i, String j)
			throws Exception {
		String property = WXPRolesandPermissionPageProperties.getProperty(key).replace("[j]", j);
		return verifyElementIsPresent(property.replace("[i]", i));
	}

	public final void clickOnSpecificElementsOfRolesAndPermissionPage(String key, String i, String j) throws Exception {
		String property = WXPRolesandPermissionPageProperties.getProperty(key).replace("[j]", j);
		click(property.replace("[i]", i));
	}

	public boolean verifyTemplateCardsOnPage() throws Exception {
		try {
			// Verify all template cards visible
			List<WebElement> allTags = getllAllElementsVisibleofRolesAndPermissionPage("templatesCards");
			for (int i = 1; i <= allTags.size(); i++) {
				verifyTrue(verifySpecificElementOnRolesAndPermissionPage("templatesCardsHeader", "[" + i + "]"),
						"Template header not present for template no " + i);
				verifyTrue(verifySpecificElementOnRolesAndPermissionPage("templatesDescription", "[" + i + "]"),
						"Template description not present for template no " + i);
			}
		} catch (VerificationException e) {
			LOGGER.error("Exception occured in method verifyTemplateCardsOnPage \n" + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean verifyallPermissionAndTogglePresentOnPage() throws Exception {
		try {
			// Verify all permission tag present
			List<WebElement> allPermission = getllAllElementsVisibleofRolesAndPermissionPage("permissions");
			for (int i = 1; i <= allPermission.size(); i++) {
				verifyTrue(verifySpecificElementOnRolesAndPermissionPage("permissionsHeader", "[" + i + "]"),
						"Permission header not present for template no " + i);
				verifyTrue(verifySpecificElementOnRolesAndPermissionPage("permissionsLabel", "[" + i + "]"),
						"Permission label not present for template no " + i);
				
				String permissionHeader = getTextOfSpecificElementOnRolesAndPermissionPage("permissionsHeader", "[" + i + "]");
				int expectedToggleCount = Integer.parseInt(getTextOfSpecificElementOnRolesAndPermissionPage("permissionsLabel", "[" + i + "]").split(" ")[0]);
				
				// Click on permission tag
				scrollOnRolesAndPermissionPage("permissionsHeader", "[" + i + "]");
				clickOnSpecificElementsOfRolesAndPermissionPage("permissionsHeader", "[" + i + "]");
				
				// Verify count of toggle with count present in label
				List<WebElement> allToggles = getllAllElementsVisibleofRolesAndPermissionPage("permissionToggle", "[" + i + "]");
				verifyTrue(expectedToggleCount == allToggles.size(),
						"Count of Toggles present in " + permissionHeader + " not matched with expected count");
				
				// Verify all toggles present under permission tag
				for (int j = 1; j <= allToggles.size(); j++) {
					verifyTrue(verifySpecificElementOnRolesAndPermissionPage("permissionToggleHeader", "[" + i + "]", "[" + j + "]"),
									"PermissionToggle Header not present for permissionToggle no " + i);
					verifyTrue(verifySpecificElementOnRolesAndPermissionPage("permissionToggleButton", "[" + i + "]", "[" + j + "]"),
									"PermissionToggle Button not present for permissionToggle no " + i);
				}
				clickOnSpecificElementsOfRolesAndPermissionPage("permissionsHeader", "[" + i + "]");
			}
		}catch (VerificationException e) {
			LOGGER.error("Exception occured in method verifyallPermissionAndTogglePresentOnPage \n" + e.getMessage());
			return false;
		}
		return true;
	}

	public void verifyTrue(boolean value, String message) throws VerificationException {
		if (!value) {
			throw new VerificationException(message);
		}
	}
	
	public static class VerificationException extends Exception {
		public VerificationException(String message) {
			super(message);
		}
	}
	
	public final boolean verifyFilteredDataOnOnRolesAndPermissionPage(String list, String filteredData) throws Exception {
		List<String> uiList = getTextOfList(WXPRolesandPermissionPageProperties.getProperty(list));
		for (String uis : uiList) {
			if (!filteredData.equalsIgnoreCase(uis)) {
				LOGGER.info("Fails to compare filtered data Actual=" + uis + " Expected=" + filteredData);
				return false;
			}
		}
		return true;
	}
}
