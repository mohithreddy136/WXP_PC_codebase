package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Properties;

public class UserProfilePage extends CommonMethod {

    private UserProfilePage instance;
    private ObjectReader userProfilePagePropertiesReader = new ObjectReader();
    private Properties userProfilePageProperties;


    public UserProfilePage getInstance() throws IOException {
        if (instance == null) {
            synchronized (SettingsPage.class) {
                if (instance == null) {
                    instance = new UserProfilePage(DRIVER);

                }
            }
        }
        return instance;
    }

    public final String getTextOfWEPTimeZonePage(String key) {
        try {
            return getTextBy(userProfilePageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
            return null;
        }
    }

    public UserProfilePage(WebDriver driver) throws IOException {
        userProfilePageProperties = userProfilePagePropertiesReader.getObjectRepository("UserProfile");
    }

    public final boolean matchTextOfWEPTimeZonePage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(userProfilePageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementsWEPTimeZonePagePage(String key) throws Exception {
        return verifyElementIsPresent(userProfilePageProperties.getProperty(key));
    }

    public final void clickOnElementsWEPTimeZonePage(String key) throws Exception {
        click(userProfilePageProperties.getProperty(key));
    }

    public final String getAttributesOfWEPTimeZonePage(String key, String desiredValue) {
        try {
            return getAttribute(userProfilePageProperties.getProperty(key), desiredValue);
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfWEXPartnerDashboardPage " + e.getMessage()));
            return null;
        }
    }

}
