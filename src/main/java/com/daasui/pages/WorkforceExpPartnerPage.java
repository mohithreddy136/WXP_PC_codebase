package com.daasui.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class WorkforceExpPartnerPage extends CommonMethod {

	private WorkforceExpPartnerPage instance;
	private ObjectReader WorkforceExpPartnerPagePropertiesReader = new ObjectReader();
	private Properties WorkforceExpPartnerPageProperties;
	public WorkforceExpPartnerPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WorkforceExpPartnerPage.class) {
				if (instance == null) {
					instance = new WorkforceExpPartnerPage(DRIVER);
				}
			}
		}
		return instance;		
	}
	
	public WorkforceExpPartnerPage(WebDriver driver) throws IOException {		
		WorkforceExpPartnerPageProperties = WorkforceExpPartnerPagePropertiesReader.getObjectRepository("WorkforceExpPartnerPage");
	}
	
	/**
	 * This method is used to click on element
	 * @param key
	 * @throws Exception
	 */
	public final void clickOnElementsOfPartnerPage(String key) throws Exception {
		click(WorkforceExpPartnerPageProperties.getProperty(key));
	}	
	
	/**
	 * This is a method to enter text on an element
	 * @param key
	 * @param Text
	 * @throws Exception
	 */
	public final void enterTextForPartnerPage(String key, String Text) throws Exception {
		enterText(WorkforceExpPartnerPageProperties.getProperty(key), Text);
	}
	/**
	 * This is a method to enter text on an element
	 * @param key
	 * @throws Exception
	 */
	public final void actionClickOfDashboardPage(String key) throws Exception {
		actionClick(WorkforceExpPartnerPageProperties.getProperty(key));
	}
	public final boolean verifyElementsOfCustomerDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(WorkforceExpPartnerPageProperties.getProperty(key));
	}
	public final boolean matchTextOnWorkforceExpCustomerDetailsPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WorkforceExpPartnerPageProperties.getProperty(key), Text);
	}

	/**
	 * This is a method to scroll on WorkforceExp Customer Creation Page
	 * @param key
	 */
	public final void scrollOnWorkforceExpAddCustomer(String key) {
		try {
			scrollTillView(WorkforceExpPartnerPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method scrollOnWorkforceExpEEPulsesCreationPage " + e.getMessage()));
		}
	}
	/**
	 * This method is designed to select a IDP value from dropdown
	 * @param DropDownValue
	 * @param DropdownList contains type of user like HPID, AAD
	 */
	public final void selectIDP( String DropDownValue, String DropdownList) {
		try {
		List<WebElement> categoryList = getElementsTillAllElementsVisibleofCustomerDetailpage(DropdownList);
		for (int i = 0; i < categoryList.size(); i++) {
			if (DropDownValue.equals(categoryList.get(i).getText())) {
				categoryList.get(i).click();
			}
		}
		}
			catch (Exception e) {
				LOGGER.error(("Exception occured in method SelectIDP " + e.getMessage()));
			}		
	}
/**
 * This method is designed to wait all elements are visible
 * @param key
 * @return
 */
public final List<WebElement> getElementsTillAllElementsVisibleofCustomerDetailpage(String key) {
	try {
		return getElementsTillAllElementsVisible(WorkforceExpPartnerPageProperties.getProperty(key));
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method getElementsTillAllElementsVisibleofCampaignDetailpage " + e.getMessage()));
		return null;
	}
}

/**
 * This method is designed to wait element is visible
 * @param key
 * @return
 */
public final boolean waitForElementsOfWorkforceExpCustomerPage(String key) {
	try {
		return verifyElementIsVisibleDynamic(WorkforceExpPartnerPageProperties.getProperty(key),120);		
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method waitForElementsOfWorkforceExpEEPulsesCreationPage " + e.getMessage()));
		return false;
	}

}

public final void selectPlan( String DropDownValue, String DropdownList) {
	try {
	List<WebElement> categoryList = getElementsTillAllElementsVisibleofCustomerDetailpage(DropdownList);
	for (int i = 0; i < categoryList.size(); i++) {
		if (DropDownValue.equals(categoryList.get(i).getText())) {
			categoryList.get(i).click();
			break;
		}
	}
	}
		catch (Exception e) {
			LOGGER.error(("Exception occured in method SelectPlan " + e.getMessage()));
		}		
}


}