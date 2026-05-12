package com.daasui.pages;

import java.io.IOException;
import java.util.Properties;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.daasui.constants.CommonVariables;

public class WEPAccountsLicensesPage extends CommonMethod {
  private final Properties props;

  public WEPAccountsLicensesPage() throws IOException {
    var reader = new ObjectReader();
    this.props = reader.getObjectRepository("WEPAccountsLicenses");
  }

  public final WebElement getLicensesElement(String key) throws Exception {
    return getElement(props.getProperty(key));
  }

  public final boolean verifyLicensesElementIsPresent(String key) throws Exception {
    return verifyElementIsPresent(props.getProperty(key));
  }

  public final void clickLicensesElement(String key) throws Exception {
    click(props.getProperty(key));
  }

  public final boolean navigateToCustomerLicensesTab() throws Exception {
    leftSideMenuVerification();
    try {
      companyView(CommonVariables.ACCOUNT_MANAGEMENT);
      LOGGER.info("Navigating to licenses tab for IT Admin");
      clickLicensesElement("tabLicenses");
      return true;
    } catch (Exception e) {
      LOGGER.error("Error while navigating to the licenses tab: " + e.getMessage());
      return false;
    }
  }

  public final boolean navigateToRootAdminLicensesTab() throws Exception {
    try {
      clickLicensesElement("tabLicenses");
      return true;
    } catch (Exception e) {
      LOGGER.error("Error while navigation to the licenses tab as root admin: " + e.getMessage());
    }
    return false;
  }

  public final void assertLicensesElements(boolean checkPlanTag, boolean planShouldBeVisible, boolean allowEmptyPlans, String planName, String planShortName)
      throws Exception {
    SoftAssert softAssert = new SoftAssert();
    // User should see placeholder if no plans are available for viewing
    if (checkPlanTag) {
      var planTag = getLicensesElement("planTag");
      softAssert.assertTrue(planTag.isDisplayed(), "Plan tag is present");
      softAssert.assertTrue(planTag.getText().equals(planShortName),
          "Plan tag text is correct");
    }

    var hasPlansDisplayed = verifyLicensesElementIsPresent("licensesList");
    if (!allowEmptyPlans) {
      softAssert.assertTrue(verifyLicensesElementIsPresent("licensesHeader"), "Licenses list header is present");
      softAssert.assertTrue(hasPlansDisplayed, "Licenses list should be displayed");
    }
    if (hasPlansDisplayed) {
      var listItems = getLicensesElement("licensesList").findElements(By.xpath("./child::*"));
      var anyMatchingPlans = false;
      for (var item : listItems) {
        var header = item.findElement(getObject(props.getProperty("licensesListItemHeaderText")));
        if (header.getText().equals(planName)) {
          anyMatchingPlans = true;
          break;
        }
      }
      if (planShouldBeVisible) {
        softAssert.assertTrue(anyMatchingPlans, "License list should have plan displayed");
      } else {
        softAssert.assertFalse(anyMatchingPlans, "License list should not have plan displayed");
      }
    }

    // Validate add licenses modal
    if (verifyLicensesElementIsPresent("licensesPlaceholder")) {
      clickLicensesElement("licensesPlaceholderAddLicensesButton");
    } else {
      clickLicensesElement("licensesAddLicensesButton");
    }
    waitForPageLoaded();
    sleeper(5000);
    assertAddLicensesModal();
    softAssert.assertAll();
  }

  private void assertAddLicensesModal() throws Exception {
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(verifyLicensesElementIsPresent("licensesAddLicensesModalHeader"),
        "'License add' modal header is present");
    softAssert.assertTrue(verifyLicensesElementIsPresent("licensesAddLicensesModalDesc"),
        "'License add' modal description is present");
    softAssert.assertTrue(verifyLicensesElementIsPresent("licensesAddLicensesModalInput"),
        "'License add' modal input field is present");
    softAssert.assertTrue(verifyLicensesElementIsPresent("licensesAddLicensesModalValidateButton"),
        "'License add' modal validate button is present");
    softAssert.assertTrue(verifyLicensesElementIsPresent("licensesAddLicensesModalCancelButton"),
        "'License add' modal cancel button is present");
    clickLicensesElement("licensesAddLicensesModalCancelButton");
    softAssert.assertAll();
  }
}
