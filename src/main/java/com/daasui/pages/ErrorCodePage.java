package com.daasui.pages;


import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;

public class ErrorCodePage extends CommonMethod  {

    private ObjectReader ErrorCodePagePropertiesReader = new ObjectReader();
    private Properties ErrorCodePageProperties;

    public final String getTextOfErrorCodePage(String key) {
        try {
            return getTextBy(ErrorCodePageProperties.getProperty(key));
        } catch (Exception e) {
            LOGGER.error(("Exception occured in method getTextOfSystemRequirementsPage " + e.getMessage()));
            return null;
        }
    }

    public ErrorCodePage(WebDriver driver) throws IOException {
        ErrorCodePageProperties = ErrorCodePagePropertiesReader.getObjectRepository("ErrorCOde");
    }

    public final boolean matchTextOfErrorCodePage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(ErrorCodePageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementsOfErrorCodePage(String key) throws Exception {
        return verifyElementIsPresent(ErrorCodePageProperties.getProperty(key));
    }

}
