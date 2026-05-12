package com.basesource.utils;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;

/**
 * 
 * This class is generate Company PIN for various purpose. If Company PIN is Not Available/Expired, Using this method user can create/get Company PIN.
 *
 */

public class CompanyPin extends CommonMethod {

	private CompanyPin instance;

	public CompanyPin getInstance() throws IOException {
		if (instance == null) {
			synchronized (CompanyPin.class) {
				if (instance == null) {
					instance = new CompanyPin();
				}
			}
		}
		return instance;
	}

	// This method is return CompanyPIN from Company Details Page.
	public String getCompanyPin() throws Exception {
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		boolean pinPresence = companiesPage.verifyElementsOfCompaniesPage("companyPin");
		String companyPin = null;
		if (pinPresence) {
			companyPin = companiesPage.getTextOfCompaniesPage("companyPin");
			return companyPin;
		} else {
			if (companiesPage.verifyElementsOfCompaniesPage("createCompanyPinButton")) {
				companiesPage.clickOnElementsOfCompaniesPage("createCompanyPinButton");
				if (companiesPage.verifyElementsOfCompaniesPage("expirationDateField")) {
					companiesPage.clickOnElementsOfCompaniesPage("expirationDateField");
					sleeper(1000);
					companiesPage.clickOnElementsOfCompaniesPage("monthDatePickerButton");
					companiesPage.clickOnElementsOfCompaniesPage("yearDatePickerButton");
					companiesPage.clickOnElementsOfCompaniesPage("selectYear");
					companiesPage.clickOnElementsOfCompaniesPage("selectMonth");
					companiesPage.clickOnElementsOfCompaniesPage("selectDate");
					sleeper(1000);
					companiesPage.clickOnElementsOfCompaniesPage("createPinButton");
					sleeper(2000);
					companyPin = companiesPage.getTextOfCompaniesPage("companyPin");
					return companyPin;
				} else {
					Assert.assertFalse(false, "Expiration date class is not available in HTML.");
				}
			} else {
				Assert.assertFalse(false, "Create Pin button is not enable.");
			}
		}
		return companyPin;
	}

	/**
	 * This method is used to generate a Company PIN
	 * 
	 * @param languageCode
	 * @return
	 */
	public String generateCompanyPin(String languageCode) {
		String companyPin = null;
		try {
			CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			Format formatter = new SimpleDateFormat("MMMMM d, YYYY");
			Date futureDate = DateUtils.addDays(new Date(), 1);
			String selectDate = formatter.format(futureDate);
			sleeper(2000);
			if (companiesDetailsPage.getTextOfCompaniesDetailsPage("companyPin").equalsIgnoreCase(getTextLanguage(languageCode, "daas_ui", "companies.details.section.not_configured"))) {
				LOGGER.info("Company PIN is not configured hence creating it.");
				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyPin");
				if (companiesDetailsPage.matchTextOfCompaniesDetailsPage("companyPinHeader", getTextLanguage(languageCode, "daas_ui", "companies.details.section.company_pin"))) {
					companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("calendarIcon");
					companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("calendarIcon");
					companiesDetailsPage.selectDateFromCalenderForCompanyPin(selectDate, "calendarIcon", "month", "rightArrow", "totalDays");
					companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogComPinSaveButton");
					companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification");
				} else {
					LOGGER.error("Company pin pop-up did not opened");
				}
			}
			sleeper(2000);// Takes time to reflect the generated company pin on ui
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("companyPin");
			companyPin = companiesDetailsPage.getTextOfCompaniesDetailsPage("companyPin");
			LOGGER.info("CompanyPin : " + companyPin);
		} catch (Exception e) {
			LOGGER.error("Exception occured in companyPin1" + e.getMessage());
		}
		return companyPin;
	}

}