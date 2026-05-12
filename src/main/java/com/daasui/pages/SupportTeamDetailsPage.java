package com.daasui.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;

/**
 * This class implements all methods related to support team details page test cases.
 */
public class SupportTeamDetailsPage extends CommonMethod{

	private ObjectReader supportTeamDetailsPagePropertiesReader = new ObjectReader();
	private Properties supportTeamDetailsPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private SupportTeamDetailsPage instance;

	public SupportTeamDetailsPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (SupportTeamDetailsPage.class) {
				if (instance == null) {
					instance = new SupportTeamDetailsPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public SupportTeamDetailsPage(WebDriver driver) throws IOException {
		supportTeamDetailsPageProperties = supportTeamDetailsPagePropertiesReader.getObjectRepository("SupportTeamDetailsPageV3");
	}

	/**
	 * This is the method to verify if an element is present on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is present or not
	 * @throws Exception
	 */
	public final boolean verifyElementsOfSupportTeamDetailsPage(String key) throws Exception {
		return verifyElementIsPresent(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to wait for an element on sales team list page until it is visible on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible or not
	 * @throws Exception
	 */
	public final boolean waitForElementsOfSupportTeamDetailsPage(String key) throws Exception {
		return verifyElementIsVisible(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to match text present on an element with given text on support team details page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be matched
	 * @return - boolean value of whether both the text match
	 * @throws Exception
	 */
	public final boolean matchTextOfSupportTeamDetailsPage(String key, String textToMatch) throws Exception {
		return verifyTextPresentOnElement(supportTeamDetailsPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is the method to verify if any element is enabled on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is enabled or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsEnableOfSupportTeamDetailsPage(String key) throws Exception {
		return verifyElementIsEnable(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to verify if any element is selected on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is selected or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsSelectedOfSupportTeamDetailsPage(String key) throws Exception {
		return verifyElementIsSelected(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get text of an element on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - string value of text on the element
	 * @throws Exception
	 */
	public final String getTextOfSupportTeamDetailsPage(String key) throws Exception {
		return getTextBy(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to get attributes of an element on support team details page
	 * 
	 * @param key - locator of the element
	 * @param desiredValue - desired attribute name
	 * @return - string value of the attribute
	 * @throws Exception
	 */
	public final String getAttributesOfSupportTeamDetailsPage(String key, String desiredValue) throws Exception {
		return getAttribute(supportTeamDetailsPageProperties.getProperty(key), desiredValue);
	}

	/**
	 * This is a method to click on element of support team details page
	 * 
	 * @param key - locator of the element
	 * @throws Exception
	 */
	public final void clickOnElementsOfSupportTeamDetailsPage(String key) throws Exception {
		click(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to enter text in a textbox present on support team details page
	 * 
	 * @param key - locator of the element
	 * @param textToMatch - text to be entered
	 * @throws Exception
	 */
	public final void enterTextForSupportTeamDetailsPage(String key, String textToMatch) throws Exception {
		enterText(supportTeamDetailsPageProperties.getProperty(key), textToMatch);
	}

	/**
	 * This is the method to verify if an element is clickable on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - boolean value of whether the lement is clickable or not
	 * @throws Exception
	 */
	public final boolean verifyElementIsClickableOfSupportTeamDetailsPage(String key) throws Exception {
		return verifyElementIsClickable(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to get a list of elements present on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - list of webelements
	 * @throws Exception
	 */
	public final List<WebElement> getElementsOfSupportTeamDetailsPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to clear text from an element present on support team details page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clearTextFromSupportTeamDetailsPageTextField(String key) throws Exception {
		clearText(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is the method to select role on role assignment dropdown on support team details page
	 * 
	 * @param dropdownId - Dropdown on role assignment popup
	 * @param key - - Locator of elements
	 * @param text - Already assigned role
	 * @return - updated role as a string
	 * @throws Exception
	 */
	public final String selectRoleFromDropDownOfSupportTeamDetailsPage(String dropdownId, String key, String text) throws Exception {
		click(supportTeamDetailsPageProperties.getProperty(dropdownId));
		List<WebElement> roleList = getElementsOfSupportTeamDetailsPage(key);
		String firstRole, secondRole;
		if (roleList.get(0).getText().equals(text)) {
			firstRole = roleList.get(1).getText();
			roleList.get(1).click();
			return firstRole;
		} else {
			secondRole = roleList.get(0).getText();
			roleList.get(0).click();
			return secondRole;
		}

	}

	/**
	 * This is a method to select all incidents subtypes
	 * 
	 * @param allLocator - locator of select all checkbox
	 * @throws Exception
	 */
	public final void selectAllIncidentTypeOfSupportTeamDetailsPage(String allLocator) throws Exception {
		waitForElementsOfSupportTeamDetailsPage(allLocator);
		if (getAttributesOfSupportTeamDetailsPage(allLocator, "class").contains("checked")) {
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
		} else {
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
		}
	}

	/**
	 * This is a method to select single incident subtypes
	 * 
	 * @param allLocator - locator of select all checkbox
	 * @param locator - locator of single subtype
	 * @throws Exception
	 */
	public final void selectSingleIncidentTypeOnSupportTeamDetailsPage(String allLocator, String locator) throws Exception {
		waitForElementsOfSupportTeamDetailsPage(allLocator);
		if (getAttributesOfSupportTeamDetailsPage(allLocator, "class").contains("checked")) {
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
			clickOnElementsOfSupportTeamDetailsPage(locator);
		} else {
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
			clickOnElementsOfSupportTeamDetailsPage(locator);
		}
	}

	/**
	 * This is a method to deselect all incidents subtypes
	 * 
	 * @param allLocator - locator of select all checkbox
	 * @throws Exception
	 */
	public final void deselectAllIncidentTypeOnSupportTeamDetailsPage(String allLocator) throws Exception {
		if (getAttributesOfSupportTeamDetailsPage(allLocator, "class").contains("checked")) {
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
		} else {
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
			clickOnElementsOfSupportTeamDetailsPage(allLocator);
		}
	}

	/**
	 * This is a method to get an element present on support team details page
	 * 
	 * @param key - locator of the element
	 * @return - webelement
	 * @throws Exception
	 */
	public final WebElement getElementOfSupportTeamDetailsPage(String key) throws Exception {
		return getElement(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to scroll on support team details page
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void scrollOnSupportTeamPage(String key) throws Exception {
		scrollTillView(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to compare two arraylists
	 * 
	 * @param key - locator of element to fetch first list
	 * @param subTypes - second list
	 * @return - boolean value of whether both the lists match
	 * @throws Exception
	 */
	public final boolean compareTwoListOfSupportTeamDetailsPage(String key, ArrayList<String> subTypes) throws Exception {
		return compareTwoList(supportTeamDetailsPageProperties.getProperty(key), subTypes);
	}

	/**
	 * This is a method to get text present on a list of elements as a list of strings
	 * 
	 * @param key - locator of element to fetch list
	 * @return - arraylist of strings
	 * @throws Exception
	 */
	public final List<String> getTextOfListOnSupportTeamDetailsPage(String key) throws Exception {
		return getTextOfPresentElementList(supportTeamDetailsPageProperties.getProperty(key));
	}

	/**
	 * This is a method to click on an element by javascript
	 * 
	 * @param key - Locator of element
	 * @throws Exception
	 */
	public final void clickByJavaScriptOnSupportTeamDetailsPage(String key) throws Exception {
		clickByJavaScript(supportTeamDetailsPageProperties.getProperty(key));
	}
	
		/** 
		 * This method verifies email content to add space
		 * @param environment -environment
		 * @param inboxEmailAddress -email address of user who receives the mail
		 * @param incidentID - subject of the mail
		 * @param privateDomain - domain
		 * @return
		 * @throws Exception
		 */
		public String verifyIncidentEmailContentforAddedSpace (String environment, String inboxEmailAddress,
				String incidentID, boolean privateDomain) throws Exception {
			String mailContent;
			Mailinator objMailinator = new Mailinator("",inboxEmailAddress.split("@")[0]);
			sleeper(5000);//required wait because script is breaking over here
			MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(incidentID,
					inboxEmailAddress, privateDomain);
			if (objMailinatorEmail != null) {
				mailContent = objMailinatorEmail.getBody();
				return getHtmlParserMailSpaceThree(mailContent);
			} else {
				LOGGER.error("Mail not found");
				return "";
			}
		}
		
		/**
		 * method to get html parser for email to replace Three spaces
		 * @param mailContent - mailinator mail content
		 * @return
		 * @throws Exception
		 */
		public final String getHtmlParserMailSpaceThree(String mailContent) throws Exception {

			String actualMailContent;
			InputStream in = org.apache.commons.io.IOUtils.toInputStream(mailContent, "UTF-8");
			BodyContentHandler handler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			ParseContext pcontext = new ParseContext();
			HtmlParser htmlparser = new HtmlParser();
			htmlparser.parse(in, handler, metadata, pcontext);
			actualMailContent = handler.toString().replaceAll("\\s{3,}", " ").trim();
			return actualMailContent;
		}
		
		/**
		 * This method will select the multiple roles from Edit Role Popup
		 * 
		 * @throws Exception
		 */
		public final void selectMultipleRolesOnSupportTeamDetailsPage() throws Exception {
			List<WebElement> listOfRoles = getElementsOfSupportTeamDetailsPage("listOfRoles");
			sleeper(1000);
			if(listOfRoles.size()>0)
			{
				for(int i=0;i<3;i++)
				{
				listOfRoles.get(i).click();
				sleeper(500);
				}
			}
			else
				LOGGER.error("Roles list is empty");
		}

	/**
	 * This is the method to scroll to the element on sales team list page until it is visible on support team details page
	 *
	 * @param key - locator of the element
	 * @return - boolean value of whether the element is visible or not
	 * @throws Exception
	 */
	public void scrollToTheElementsOfSupportTeamDetailsPage(String key) throws Exception {
		 scrollTillView(supportTeamDetailsPageProperties.getProperty(key));
	}

}
