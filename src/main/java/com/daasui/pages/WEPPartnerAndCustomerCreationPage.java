package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.action.MailinatorMail;
import com.basesource.utils.Mailinator;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class includes all the methods related to partner list page test cases
 */
public class WEPPartnerAndCustomerCreationPage extends CommonMethod {
	private ObjectReader WEPPartnerAndCustomerCreationPagePropertiesReader = new ObjectReader();
	private Properties WEPPartnerAndCustomerCreationPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	private WEPPartnerAndCustomerCreationPage instance;
	private static Logger log = LogManager.getLogger(WEPPartnerAndCustomerCreationPage.class);

	public WEPPartnerAndCustomerCreationPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WEPPartnerAndCustomerCreationPage.class) {
				if (instance == null) {
					instance = new WEPPartnerAndCustomerCreationPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WEPPartnerAndCustomerCreationPage(WebDriver driver) throws IOException {
		WEPPartnerAndCustomerCreationPageProperties = WEPPartnerAndCustomerCreationPagePropertiesReader.getObjectRepository("WEPPartnerAndCustomerCreationPage");
	}

	public final void actionClickOnWEPCreateCompanyPage(String key) throws Exception {
		actionClick(WEPPartnerAndCustomerCreationPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleOnWEPCreateCompanyPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(WEPPartnerAndCustomerCreationPageProperties.getProperty(key));
	}

	public final String selectFirstValueFromDropdownOnWEPCreateCompanyPage(String dropdownListKey) throws Exception {
		List<WebElement> listOfOptions = getElementsTillAllElementsVisibleOnWEPCreateCompanyPage(dropdownListKey);
		String text = listOfOptions.get(0).getText();
		listOfOptions.get(0).click();
		return text;
	}

	public final void sideMenuSelectionWEPRootLoginPage(String lang, String parentmenu, String childmenu) throws Exception {
		sideMenuSelection(lang, WEPPartnerAndCustomerCreationPageProperties.getProperty(parentmenu), WEPPartnerAndCustomerCreationPageProperties.getProperty(childmenu));
		System.out.println("childmenu : " + childmenu);
	}

	public String verifyEmailContent (String subject, String environment, String inboxEmailAddress, boolean privateDomain) throws Exception {
		String mailContent;
		Mailinator objMailinator = new Mailinator("",inboxEmailAddress.split("@")[0]);
		sleeper(5000);//required wait because script is breaking over here
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(subject, inboxEmailAddress.split("@")[0], privateDomain);
		if (objMailinatorEmail != null) {
			mailContent = objMailinatorEmail.getBody();
			return getReceivedHtmlParserMailSpaceThree(mailContent);
		} else {
			LOGGER.error("Mail not found");
			return "";
		}
	}

	public String invitatioMail(String subject, String environment, String inboxEmailAddress, boolean privateDomain) throws Exception {
		String mailContent;
		Mailinator objMailinator = new Mailinator("", inboxEmailAddress.split("@")[0]);
		sleeper(5000);//required wait because script is breaking over here
        Mailinator.disableSslVerification();
		mailContent = objMailinator.getRawEmailResponse(subject, inboxEmailAddress.split("@")[0], privateDomain);
		if (mailContent != null) {
			return mailContent;}
		else {
			LOGGER.error("Mail not found");
			return "";
		}
	}
	/**
	 * This method is used to get the actual mail content.
	 * @param UserEmail
	 * @return actual mail content
	 */
	public final String getActualMailContent(String UserEmail, String Subject) {
		try {
			String actualMailContent = invitatioMail(Subject, environment, UserEmail, true);
			System.out.println("actualMailContent :" + actualMailContent);
			int count = 0;
			while (count < 5 && actualMailContent == "") {
				sleeper(1000);
				count++;
				System.out.println(count + " : Email not received");
				actualMailContent = invitatioMail(Subject, environment, UserEmail, true);
			}
			return actualMailContent;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getActualMailContent " + e.getMessage()));
			return null;
		}
	}
	/**
	 * This method extracts the link from the email content.
	 * @param UserEmail
	 * @return extracted link
	 */

	public final String extractLink(String actualMailContent) {
        // Define the regex pattern to match the URL
		String regex = "https:\\\\/\\\\/(eustagingms|usstagingms)\\.workforceexperience\\.hp\\.com\\\\/services\\\\/oauth_handler\\\\/onecloud\\\\/accept-invite\\?invitationCode=[a-zA-Z0-9\\-]+";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(actualMailContent);

        // Find and print the URL
        String cleanedUrl = null;
        if (matcher.find()) {
            String extractedLink = matcher.group();
            System.out.println("extractedLink: " + extractedLink);
            cleanedUrl = extractedLink.replace("\\/", "/");
            // Print the cleaned URL
            System.out.println("Cleaned URL: " + cleanedUrl);

        } else {
            System.out.println("No link found.");
        }
        return cleanedUrl;
    }

	public final String getVerificationEmail(String UserEmail, String Subject) {
		try {
			String actualMailContent = verifyEmailContent(Subject, environment, UserEmail, true);
			System.out.println("actualMailContent :" + actualMailContent);
			int count = 0;
			while (count < 5 && actualMailContent == "") {
				sleeper(1000);
				count++;
				System.out.println(count + " : Email not received");
				actualMailContent = verifyEmailContent(Subject, environment, UserEmail, true);
			}
			return actualMailContent;
		} catch (Exception e) {
			LOGGER.error(("Exception occured in method getActualMailContent " + e.getMessage()));
			return null;
		}
	}

	public String extractVerificationCode(String emailContent) {
		// Define the regex pattern to match a 6-digit code
		String regex = "\\b\\d{6}\\b";

		// Compile the pattern
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(emailContent);

		// Find and return the first match
		if (matcher.find()) {
			System.out.println("verification code is: " + matcher.group());
			return matcher.group();
		} else {
			return "Verification code not found.";
		}
	}
}

