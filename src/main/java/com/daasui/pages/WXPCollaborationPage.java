package com.daasui.pages;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.awt.Desktop;
import java.io.InputStream;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
public class WXPCollaborationPage extends CommonMethod {
	private WXPCollaborationPage instance;
    private ObjectReader WXPCollaborationPagePropertiesReader = new ObjectReader();
    private Properties WXPCollaborationPageProperties;
    private final static Logger LOGGER = LogManager.getLogger(WXPCollaborationPage.class);
    public static String environment;
    public static String uiVersion = System.getProperty("uiVersion");

    public WXPCollaborationPage getInstance() throws IOException {
        if (instance == null) {
            synchronized (WXPCollaborationPage.class) {
                if (instance == null) {
                    instance = new WXPCollaborationPage(DRIVER);
                }
            }
        }
        return instance;
    }

    public WXPCollaborationPage(WebDriver driver) throws IOException {
        WXPCollaborationPageProperties = WXPCollaborationPagePropertiesReader.getObjectRepository("WXPCollaborationPage");
    }

    public final boolean verifyElementsOfCollaborationPage(String key) throws Exception {
        return verifyElementIsPresent(WXPCollaborationPageProperties.getProperty(key));
    }

    public final void clickOnElementsOfCollaborationPage(String key) throws Exception {
        click(WXPCollaborationPageProperties.getProperty(key));
    }

    public final void mouseHoverAndClickOfCollaborationPage(String key) throws Exception {
        actionClick(WXPCollaborationPageProperties.getProperty(key));
    }

    public final void actionClickOfCollaborationPage(String key) throws Exception {
        actionClick(WXPCollaborationPageProperties.getProperty(key));
    }

    public final List<WebElement> getElementsOfCollaborationPage(String key) throws Exception {
        return getAllElements(WXPCollaborationPageProperties.getProperty(key));
    }

    public final void clickByJavaScriptOnCollaborationPage(String key) throws Exception {
        clickByJavaScript(WXPCollaborationPageProperties.getProperty(key));
    }

    public final WebElement getElementOfCollaborationPage(String key) throws Exception {
        return getElement(WXPCollaborationPageProperties.getProperty(key));
    }

    public void scrollOnCollaborationPage(String key) throws Exception {
        scrollTillView(WXPCollaborationPageProperties.getProperty(key));
    }

    public final void listMouseHoverOnCollaborationPage(String key) throws Exception {
        listMouseHover(WXPCollaborationPageProperties.getProperty(key));
    }

    public final void enterTextOnCollaborationPage(String key, String Text) throws Exception {
        enterText(WXPCollaborationPageProperties.getProperty(key), Text);
    }

    public final void enterTextWithoutClearOnCollaborationPage(String key, String Text) throws Exception {
        enterTextwithoutclear(WXPCollaborationPageProperties.getProperty(key), Text);
    }

    public final void scrollTillViewCollaborationPage(String key) throws Exception {
        scrollTillView(WXPCollaborationPageProperties.getProperty(key));
    }

    public final void enterTextByJavaScriptOnCollaborationPage(String key, String Text) throws Exception {
        enterTextUsingJavaScript(WXPCollaborationPageProperties.getProperty(key), Text);
    }

    public final boolean verifyTextPresentOnElementCollaborationPage(String key, String Text) throws Exception {
        return verifyTextPresentOnElement(WXPCollaborationPageProperties.getProperty(key), Text);
    }

    public final boolean verifyElementIsDisplayedOfCollaborationPage(String key) throws Exception {
        return verifyElementIsVisible(WXPCollaborationPageProperties.getProperty(key));
    }

    public final void onChangeCalendarEventOfCollaborationPage(String key) throws Exception {
        onChangeCalendarEvent(WXPCollaborationPageProperties.getProperty(key));
    }
	
	/**
	 * This method designed to get the  Text of WebElement from web page
	 * @param WebElement 
	 */
	public final String getTextOfCollaborationPage(String key) throws Exception {
		return getTextBy(WXPCollaborationPageProperties.getProperty(key));
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public void waitForElementsOfCollaborationPage(String string) {
	}
	
	public final void clearTextOfCollaborationPage(String key) throws Exception {
	    clearText(WXPCollaborationPageProperties.getProperty(key));
	}
	/**
	 * This method is used to validate the downloaded PNG file 
	 */
	public void openAndClosePNG(String filePath) throws Exception {
	    File file = new File(filePath);

	    if (!file.exists() || file.length() == 0) {
	        System.err.println("Validation Failed: File missing or empty.");
	        return;
	    }

	    try (InputStream is = new FileInputStream(file)) {
	        byte[] header = new byte[4]; 
	        is.read(header);
	        if (header[0] != (byte)0x89 || header[1] != 'P' || header[2] != 'N' || header[3] != 'G') {
	            System.err.println("Validation Failed: File is not a valid PNG.");
	            return;
	        }
	    }

	    if (ImageIO.read(file) == null) {
	        System.err.println("Validation Failed: PNG is corrupted and cannot be decoded.");
	        return;
	    }

	    if (Desktop.isDesktopSupported()) {
	        Desktop.getDesktop().open(file);
	        System.out.println("Validation Passed. File opened successfully.");
	        Thread.sleep(4000); 
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_ALT);
	        robot.keyPress(KeyEvent.VK_F4);
	        robot.keyRelease(KeyEvent.VK_F4);
	        robot.keyRelease(KeyEvent.VK_ALT);

	        System.out.println("Close signal sent via Robot (Alt+F4).");
	    }
	} 

	public void waitElementsOfCollaborationPage(String string) {
	}
 
	public void waitUntilElementIsInvisibleOfCollaborationPage(String string) {
	}

	public void scrollTillViewOnCollaborationPage(String string) {
	}

	public String isFileExists(String filePath) {
		return null;
	}
	
	public final void mouseHoverOfCollaborationPage(String key) throws Exception {
		mouseHover(WXPCollaborationPageProperties.getProperty(key));
	}

	public void switchToNewTab() {
	}
	
	public void dragAndDropOfCollaborationPage(String sourceKey, String destKey) throws Exception {
	    dragAndDrop(WXPCollaborationPageProperties.getProperty(sourceKey),WXPCollaborationPageProperties.getProperty(destKey));
	}
 
}