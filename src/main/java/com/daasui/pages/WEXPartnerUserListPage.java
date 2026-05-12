package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class WEXPartnerUserListPage extends CommonMethod {
	private ObjectReader WEXPartnerUserListPageReader = new ObjectReader();
	private Properties WEXPartnerUserListPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private WEXPartnerUserListPage instance;

	public WEXPartnerUserListPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEXPartnerUserListPage.class) {
				if (instance == null) {
					instance = new WEXPartnerUserListPage(DRIVER);

				}
			}
		}
		return instance;
	}

	public WEXPartnerUserListPage(WebDriver driver) throws IOException {
		WEXPartnerUserListPageProperties = WEXPartnerUserListPageReader.getObjectRepository("WEXPartnerUserListPage");
	}

	public final boolean verifyElementsOfPartnerUserListPage(String key) throws Exception {
		return verifyElementIsPresent(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final void clickOnElementsOfPartnerUserListPage(String key) throws Exception {
		click(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final void mouseHoverOfPartnerUserListPage(String key) throws Exception {
		mouseHover(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final void actionClickOfPartnerUserListPage(String key) throws Exception {
		actionClick(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsOfPartnerUserListPage(String key) throws Exception {
		return getAllElements(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final void clickByJavaScriptOnPartnerUserListPage(String key) throws Exception {
		clickByJavaScript(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final void listMouseHoverOfPartnerUserListPage(String key) throws Exception {
		listMouseHover(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final WebElement getElementOfPartnerUserListPage(String key) throws Exception {
		return getElement(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofPartnerUserListPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final void actionClickOnWEXPartnerUserListPage(String key) throws Exception {
		actionClick(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final boolean matchTextOnWEXPartnerUserListPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(WEXPartnerUserListPageProperties.getProperty(key), Text);
	}

	public final void enterTextOfWEXPartnerUserListPage(String key, String Text) {
		try {
			enterText(WEXPartnerUserListPageProperties.getProperty(key), Text);
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method enterTextForWEXCustomerUsersListPage " + e.getMessage()));
		}
	}

	public final List<WebElement> getElementsTillAllElementsVisibleOnWEXPartnerUserListPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEXPartnerUserListPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWEXPartnerUserListPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEXPartnerUserListPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final boolean waitForElementsOfWEXPartnerUserListPage(String key) {
		try {
			return verifyElementIsVisible(WEXPartnerUserListPageProperties.getProperty(key));
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method waitForElementsOfWEXCustomerUserListPage " + e.getMessage()));
			return false;
		}
	}

	public final boolean removeInactivePartnerUser(String environment, String tenantID, String userID, String environmentURL) {
		try {
			boolean flag = false;
			int code = getStatusCode(environmentURL + ConstantURL.DELETE_WEX_INACTIVE_USER,
					"{\"tenantId\": \"" + tenantID + "\",\"invitedUserIds\": [\"" + userID + "\"]}", "DELETE", environment);
			if (code != CommonVariables.CODEOK) {
				flag = false;
				LOGGER.error("Delete API got failed while removing Users.");
			} else
				flag = true;

			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeNonCompanyUser: " + e.getMessage());
			return false;
		}
	}

	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	public static String PartnerFullName2 = getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_FULLNAME2");



	public final String getExpectedMailContent(String LanguageCode, String PartnerName, String invitationCod, String environment) {
		try {
		String expectedMailContent = (getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.admin.central.invite.hi.there") + " "
				+ getTextLanguage(LanguageCode, "daas_ui", "com.hp.ns.welcome.user.subject").replace("{0}", CommonVariables.APP_NAME) + " "
				+ getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.welcome.user.hello.admin.central").replace("{0}", UserFirstname) + "," + " "
				+ getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.invite.user.mail.body1.one.cloud").replace("{0}", PartnerFullName2).replace("{1}", PartnerName).replace("{2}", CommonVariables.APP_NAME) + " "
				+ getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.invite.user.mail.body3.one.cloud") + " "
				+ CommonVariables.SIGN_IN + " "
				+ getTextLanguage(LanguageCode, "notification_service", "com.hp.ns.admin.central.link.paste") + " "
				+ environment + CommonVariables.INVITATION_LINK + invitationCod + " "
				+ getTextLanguage(LanguageCode, "daas_ui", "adminx.signature_thanks") + " "
				+ CommonVariables.APP_NAME + " " + "Team" + " " + "Ã‚Â"
				+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()));
		System.out.println("expectedMailContent :" + expectedMailContent);
		return expectedMailContent;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getExpectedMailContent " + e.getMessage()));
			return null;
		}
	}

	public String verifyEmailContent (String subject, String environment, String inboxEmailAddress, boolean privateDomain) throws Exception {
		String mailContent;
		Mailinator objMailinator = new Mailinator("",inboxEmailAddress.split("@")[0]);
		sleeper(5000);//required wait because script is breaking over here
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(getEnvironmentSpecificData(System.getProperty("environment"), subject), inboxEmailAddress.split("@")[0], privateDomain);
		if (objMailinatorEmail != null) {
			mailContent = objMailinatorEmail.getBody();
			return getReceivedHtmlParserMailSpaceThree(mailContent);
		} else {
			LOGGER.error("Mail not found");
			return "";
		}
	}

	public final String getActualMailContent(String UserEmail){
		try {
		String actualMailContent = verifyEmailContent("INVITED_USER_EMAIL_SUBJECT", environment, UserEmail,true);
		System.out.println("actualMailContent :"+actualMailContent);
		int count = 0;
		while (count < 5 && actualMailContent == "") {
			sleeper(1000);
			count++;
			System.out.println(count + " : Email not received");
			actualMailContent = verifyEmailContent("INVITED_USER_EMAIL_SUBJECT", environment, UserEmail, true);
		}
		return actualMailContent;
	} catch (Exception e) {
		LOGGER.error(("Exception occured in method getActualMailContent " + e.getMessage()));
		return null;
	}
	}
}
