package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ProductCatalogVariables;

import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;


public class WEPPartnerCatalogueRevampTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEPPartnerCatalogueRevampTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_WEP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		return data;
	}

	/************************************************* Catalog *********************************************************************/

	/**
	 * This method will verify the import of preset catalog for single customers
	 * then validate the Bell notification then validate in catalog list page and delete the catalog from the catalog list page
	 * & again validate the deletion of the catalog
	 */
	@Test(priority = 1, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 4758184, 41160557")
	public final void verifyImportCustomCatalogFunctionality() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		WEPPartnerCatalogueRevampPage wepPartnerCatalogueRevampPage  = new WEPPartnerCatalogueRevampPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("ProductCatalogTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.catalogs")), "Requests tab text is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("ProductCatalogTab"), "Requests tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb",  getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.catalogs")), "Requests tab Breadcrumb is not matching");

		//softAssert.assertTrue(wepPartnerCatalogueRevampPage.verifyElementsOfWEPPartnerCatalogueRevampPage("CatalogTab"), "Catalog tab is not present");
		//softAssert.assertTrue(wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("CatalogTab"), "Not clicked on Catalog tab");
		LOGGER.info("Redirected to Catalog list page");
		wepPartnerCatalogueRevampPage.deletePreuploadedCatalog();
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.getTextOfWEPPartnerCatalogueRevampPage("addCatalogButtonText").equals(wepPartnerCatalogueRevampPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.list.add_catalog")), "Product catalog import title does not match");
		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("addCatalogButton");
		wepPartnerCatalogueRevampPage.verifyElementsOfWEPPartnerCatalogueRevampPage("downloadCatalogSample");

		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("browseButton");
		wepPartnerCatalogueRevampPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_VALID);
		wepPartnerCatalogueRevampPage.waitForElementsOfWEPPartnerCatalogueRevampPage("importButton");
		sleeper(2000);//File upload fails in quick import button click.

		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("importButton");
		LOGGER.info("Clicked on import button");
		wepPartnerCatalogueRevampPage.waitForElementsOfWEPPartnerCatalogueRevampPage("ImportToastNotification");
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.getTextOfWEPPartnerCatalogueRevampPage("ImportToastNotification").equals(wepPartnerCatalogueRevampPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");

		// here my page is refreshing so I am waiting for the page to load
		sleeper(20000);
		// Refresh the page
		getDriver().navigate().refresh();
		waitForPageLoaded();

		// Verify the imported catalog details
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.verifyImportedCatalog(CommonVariables.CUSTOM_CATALOG), "Imported catalog details not verified successfully");
		LOGGER.info("Imported catalog details verified successfully");

		// delete the catalog from the catalog list page
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.deleteCatalogFromList(), "Catalog not deleted successfully");
		LOGGER.info("Catalog deleted successfully");

		softAssert.assertAll();
		LOGGER.info("Verified import of custom catalog successfully");
	}

	/**
	 * This method will verify the import of custom catalog for single customers
	 * then validate the Bell notification then validate in catalog list page and delete the catalog from the catalog list page
	 * & again validate the deletion of the catalog
	 */
	@Test(priority = 2, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 41160555,  41160557")
	public final void verifyAssignCatalogToCustomerFunctionality() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		WEPPartnerCatalogueRevampPage wepPartnerCatalogueRevampPage  = new WEPPartnerCatalogueRevampPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");

		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("ProductCatalogTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.catalogs")), "Catalogs tab text is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("ProductCatalogTab"), "Requests tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb",  getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.catalogs")), "Catalogs tab Breadcrumb is not matching");

		//softAssert.assertTrue(wepPartnerCatalogueRevampPage.verifyElementsOfWEPPartnerCatalogueRevampPage("CatalogTab"), "Catalog tab is not present");
		//softAssert.assertTrue(wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("CatalogTab"), "Not clicked on Catalog tab");
		LOGGER.info("Redirected to Catalog list page");
		wepPartnerCatalogueRevampPage.deletePreuploadedCatalog();
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.getTextOfWEPPartnerCatalogueRevampPage("addCatalogButtonText").equals(wepPartnerCatalogueRevampPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.list.add_catalog")), "Product catalog import title does not match");
		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("addCatalogButton");
		wepPartnerCatalogueRevampPage.verifyElementsOfWEPPartnerCatalogueRevampPage("downloadCatalogSample");

		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("browseButton");
		wepPartnerCatalogueRevampPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_VALID);
		wepPartnerCatalogueRevampPage.waitForElementsOfWEPPartnerCatalogueRevampPage("importButton");
		sleeper(2000);//File upload fails in quick import button click.

		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("importButton");
		LOGGER.info("Clicked on import button");
		wepPartnerCatalogueRevampPage.waitForElementsOfWEPPartnerCatalogueRevampPage("ImportToastNotification");
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.getTextOfWEPPartnerCatalogueRevampPage("ImportToastNotification").equals(wepPartnerCatalogueRevampPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");

		// here my page is refreshing so I am waiting for the page to load
		sleeper(20000);
		// Refresh the page
		getDriver().navigate().refresh();
		waitForPageLoaded();

		// Verify the imported catalog details
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.verifyImportedCatalog(CommonVariables.CUSTOM_CATALOG), "Imported catalog details not verified successfully");
		LOGGER.info("Imported catalog details verified successfully");

		// Manage Catalog Assignments
		wepPartnerCatalogueRevampPage.openManageAssignmentModal();
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.setAsDefaultCatalog(), "Set as default catalog is not working as expected");

		// delete the catalog from the catalog list page
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.deleteCatalogFromList(), "Catalog not deleted successfully");
		LOGGER.info("Catalog deleted successfully");

		softAssert.assertAll();
		LOGGER.info("Catalog set as default functionality verified successfully");
	}

	@Test(priority = 3, groups = { "REGRESSION_PARTNERCX"}, description = "TestCase ID : 41160555,  41160557")
	public final void validateCatalogDetailsPage() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		WEPPartnerCatalogueRevampPage wepPartnerCatalogueRevampPage  = new WEPPartnerCatalogueRevampPage(PreDefinedActions.getDriver()).getInstance();
		WEPPartnerCustomersPage wepPartnerCustomersPage = new WEPPartnerCustomersPage(PreDefinedActions.getDriver()).getInstance();
		WEXPartnerDashboardPage WEXPartnerDashboardPage = new WEXPartnerDashboardPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(wepPartnerCustomersPage.switchToPartnerDashboard(), "Failed to switch to partner dashboard");
		Assert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("ProductCatalogTab", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.catalogs")), "Catalogs tab text is not matching");
		softAssert.assertTrue(WEXPartnerDashboardPage.clickOnElementsOfWEXPartnerDashboardPage("ProductCatalogTab"), "Requests tab is not clickable");
		waitForLoaderIconToDisappear();
		softAssert.assertTrue(WEXPartnerDashboardPage.matchTextOfWEXPartnerDashboardPage("Breadcrumb",  getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.catalogs")), "Catalogs tab Breadcrumb is not matching");

		//softAssert.assertTrue(wepPartnerCatalogueRevampPage.verifyElementsOfWEPPartnerCatalogueRevampPage("CatalogTab"), "Catalog tab is not present");
		//softAssert.assertTrue(wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("CatalogTab"), "Not clicked on Catalog tab");
		LOGGER.info("Redirected to Catalog list page");
		wepPartnerCatalogueRevampPage.deletePreuploadedCatalog();
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.getTextOfWEPPartnerCatalogueRevampPage("addCatalogButtonText").equals(wepPartnerCatalogueRevampPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.list.add_catalog")), "Product catalog import title does not match");
		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("addCatalogButton");
		wepPartnerCatalogueRevampPage.verifyElementsOfWEPPartnerCatalogueRevampPage("downloadCatalogSample");

		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("browseButton");
		wepPartnerCatalogueRevampPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_VALID);
		wepPartnerCatalogueRevampPage.waitForElementsOfWEPPartnerCatalogueRevampPage("importButton");
		sleeper(2000);//File upload fails in quick import button click.

		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("importButton");
		LOGGER.info("Clicked on import button");
		wepPartnerCatalogueRevampPage.waitForElementsOfWEPPartnerCatalogueRevampPage("ImportToastNotification");
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.getTextOfWEPPartnerCatalogueRevampPage("ImportToastNotification").equals(wepPartnerCatalogueRevampPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");

		// here my page is refreshing so I am waiting for the page to load
		sleeper(20000);
		// Refresh the page
		getDriver().navigate().refresh();
		waitForPageLoaded();

		// Verify the imported catalog details
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.verifyImportedCatalog(CommonVariables.CUSTOM_CATALOG), "Imported catalog details not verified successfully");
		LOGGER.info("Imported catalog details verified successfully");

		// Manage Catalog Assignments
		wepPartnerCatalogueRevampPage.openManageAssignmentModal();
		String defaultForCompanyName = wepPartnerCatalogueRevampPage.getCompanyNameCatalogDefaultFor();
		LOGGER.info("defaultForCompanyName:" +defaultForCompanyName);

		// Click on the catalog to open the details page
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.openCatalogDetailsPage(), "Catalog details page was not opened successfully");
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.matchTextOfWEPPartnerCatalogueRevampPage("catalogDetailsTitle", "catalogValid Product Catalog"),"details page title not matching");
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.matchTextOfWEPPartnerCatalogueRevampPage("defaultFor", defaultForCompanyName),"default For customer name not matching");

		// delete the catalog from the catalog details page
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.verifyElementsOfWEPPartnerCatalogueRevampPage("removeCatalogButton"), "Catalog remove button is not present");
		//wepPartnerCatalogueRevampPage.mousehoverOnWEPPartnerCatalogueRevampPage("removeCatalogButton");
		wepPartnerCatalogueRevampPage.clickByJavaScriptOnElementsOnPartnerPage("removeCatalogButton");
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.matchTextOfWEPPartnerCatalogueRevampPage("deleteCatalogTitle", getTextLanguage(LanguageCode, "daas_ui", "catalogs.remove_catalogs")), "Company profile title is incorrect");
		softAssert.assertTrue(wepPartnerCatalogueRevampPage.verifyElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogMessage"), "Catalog delete confirmation message is not present");
		wepPartnerCatalogueRevampPage.clickOnElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogButton");
		wepPartnerCatalogueRevampPage.waitForElementsOfWEPPartnerCatalogueRevampPage("deleteCatalogtoastNotification");

		softAssert.assertTrue(wepPartnerCatalogueRevampPage.matchTextOfWEPPartnerCatalogueRevampPage("deleteCatalogtoastNotification", getTextLanguage("en", "daas_ui", "catalogs.list.toast.remove.success")), "Catalog deletion toast notification text does not match");
		LOGGER.info("Catalog deleted successfully from details page");

		softAssert.assertAll();
		LOGGER.info("Catalog details page verified successfully");
	}
}