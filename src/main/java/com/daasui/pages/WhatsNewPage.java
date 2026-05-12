package com.daasui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.basesource.action.CommonMethod;
import com.basesource.utils.ObjectReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WhatsNewPage extends CommonMethod {

	private Properties selectedLanguageProperties;
	private ObjectReader whatsNewPagePropertiesReader = new ObjectReader();
	private Properties whatsNewPageProperties;
	public static String uiVersion = System.getProperty("uiVersion");
	public static String versionTextLatest = null;
	public static String customerTextLatest = null;
	public static String partnerTextLatest = null;
	public static String versionTextFuture = null;
	public static String customerTextFuture = null;
	public static String partnerTextFuture = null;

	private WhatsNewPage instance;

	public WhatsNewPage getInstance() throws IOException {
		if (instance == null) {
			synchronized (WhatsNewPage.class) {
				if (instance == null) {
					instance = new WhatsNewPage(DRIVER);
				}
			}
		}
		return instance;
	}

	public WhatsNewPage(WebDriver driver) throws IOException {
		whatsNewPageProperties = whatsNewPagePropertiesReader.getObjectRepository("WhatsNewPageV3");
	}

	public final String getTextLanguage(String language, String localeFolder, String key) throws Exception {
		selectedLanguageProperties = whatsNewPagePropertiesReader.getLanguageObjectRepository(localeFolder, language);
		return selectedLanguageProperties.getProperty(key);
	}

	public final boolean verifyElementsOfWhatsNewPage(String key) throws Exception {
		return verifyElementIsPresent(whatsNewPageProperties.getProperty(key));
	}

	public final boolean waitForElementsOfWhatsNewPage(String key) throws Exception {
		return verifyElementIsVisible(whatsNewPageProperties.getProperty(key));
	}

	public final boolean matchTextOfWhatsNewPage(String key, String Text) throws Exception {
		return verifyTextPresentOnElement(whatsNewPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsEnableOfWhatsNewPage(String key) throws Exception {
		return verifyElementIsEnable(whatsNewPageProperties.getProperty(key));
	}

	public final boolean verifyElementIsSelectedOfWhatsNewPage(String key) throws Exception {
		return verifyElementIsSelected(whatsNewPageProperties.getProperty(key));
	}

	public final String getTextOfWhatsNewPage(String key) throws Exception {
		return getTextBy(whatsNewPageProperties.getProperty(key));
	}

	public final String getAttributesOfWhatsNewPage(String key, String value) throws Exception {
		return getAttribute(whatsNewPageProperties.getProperty(key), value);
	}

	public final void clickOnElementsOfWhatsNewPage(String key) throws Exception {
		sleeper(2000);
		click(whatsNewPageProperties.getProperty(key));
	}

	public final void enterTextForWhatsNewPage(String key, String Text) throws Exception {
		enterText(whatsNewPageProperties.getProperty(key), Text);
	}

	public final boolean verifyElementIsClickableOfWhatsNewPage(String key) throws Exception {
		return verifyElementIsClickable(whatsNewPageProperties.getProperty(key));
	}

	public final void MoveoverElementForWhatsNewPage(String key) throws Exception {
		moveToElements(whatsNewPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsOfWhatsNewPage(String key) throws Exception {
		return getElementsTillAllElementsPresent(whatsNewPageProperties.getProperty(key));
	}

	public final WebElement getElementOfWhatsNewPage(String key) throws Exception {
		return getElement(whatsNewPageProperties.getProperty(key));
	}

	public final List<WebElement> getElementsTillAllElementsVisibleofWhatsNewPage(String key) throws Exception {
		return getElementsTillAllElementsVisible(whatsNewPageProperties.getProperty(key));
	}

	public final void scrollDownWhatsNew() {
		jsDriver().executeScript("scroll(0, 750);");
	}

	public final void waitUntilElementIsInvisibleOfWhatsNewPage(String key) throws Exception {
		verifyElementIsinvisibile(whatsNewPageProperties.getProperty(key));
	}

	/**
	 * This method provide details for adding future release.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean provideDetailsFutureRelease(String LanguageCode) {
		try {
			boolean flag = false;
			int counter = 0;
			int randomDay = 0;
			int[] randomNumber = new int[3];
			String[] randomString = new String[3];
			Random random = new Random();
			for (int i = 0; i < 3; i++) {
				randomNumber[i] = random.nextInt(100);
				randomString[i] = String.valueOf(randomNumber[i]);
			}
			if (!(getAttributesOfWhatsNewPage("versionDisabledCheck", "class").contains("disabled") || getAttributesOfWhatsNewPage("dateDisabledCheck", "class").contains("disabled"))) {
				enterTextForWhatsNewPage("releaseVersion", randomString[0] + "." + randomString[1] + "." + randomString[2]);
				LOGGER.info("Version is entered.");
				clickOnElementsOfWhatsNewPage("dateField");
				while (counter <= 4) {
					clickOnElementsOfWhatsNewPage("arrowRight");
					counter++;
				}
				List<WebElement> listOfDays = getElementsTillAllElementsVisibleofWhatsNewPage("days");
				randomDay = random.nextInt(6);
				if (listOfDays.size() > 0) {
					listOfDays.get(randomDay).click();
					LOGGER.info("Future Date is entered.");
					enterTextForWhatsNewPage("customerNotes", "Customer notes for release version " + randomString[0] + "." + randomString[1] + "." + randomString[2]);
					LOGGER.info("Customer Notes are entered.");
					enterTextForWhatsNewPage("partnerNotes", "Partner notes for release version " + randomString[0] + "." + randomString[1] + "." + randomString[2]);
					LOGGER.info("Partner Notes are entered.");
					versionTextFuture = getAttributesOfWhatsNewPage("releaseVersion", "value");
					customerTextFuture = getTextOfWhatsNewPage("customerNotes");
					partnerTextFuture = getTextOfWhatsNewPage("partnerNotes");
					clickOnElementsOfWhatsNewPage("save");
					LOGGER.info("Details are saved.");
					sleeper(10000);
					verifyElementsOfWhatsNewPage("successMessage");
					String successMessage = getTextOfWhatsNewPage("successMessage");
					if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "whats_new.toast.new_release"))) {
						sleeper(10000);//Release takes time to display
						//scrollDownWhatsNew();
						refreshPage();
						waitForElementsOfWhatsNewPage("listOfTotalVersions");
						List<WebElement> listOfVersions = getElementsTillAllElementsVisibleofWhatsNewPage("listOfTotalVersions");
						List<WebElement> listOfCustomerNotes = getElementsTillAllElementsVisibleofWhatsNewPage("listOfCustomerNotes");
						List<WebElement> listOfPartnerNotes = getElementsTillAllElementsVisibleofWhatsNewPage("listOfPartnerNotes");
						if (listOfVersions.size() > 0) {
							for (int listOfVersionCounter = 0; listOfVersionCounter < listOfVersions.size();) {
								if (listOfVersions.get(listOfVersionCounter).getText().contains(versionTextFuture)) {
									if (listOfCustomerNotes.get(listOfVersionCounter).getText().contains(customerTextFuture) && listOfPartnerNotes.get(listOfVersionCounter).getText().contains(partnerTextFuture)) {
										flag = true;
										LOGGER.info("Future release is added and reflected back successfully.");
										break;
									}
								}
								listOfVersionCounter++;
							}
						} else {
							LOGGER.error("There are no releases present on Whats New Page.");
						}
					} else {
						LOGGER.error("Release could not be added successfully.");
					}
				} else {
					LOGGER.error("Fields for version and date are disabled.");
				}
			} else {
				LOGGER.error("There are no days being displayed in selected month.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in provideDetailsFutureRelease: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used add release in Future date.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean addNewFutureRelease(String LanguageCode) {
		try {
			boolean flag = false;
			if (verifyElementsOfWhatsNewPage("draftNewReleaseButtonFirst")) {
				sleeper(2000);
				clickOnElementsOfWhatsNewPage("draftNewReleaseButtonFirst");
				LOGGER.info("Modal for Draft a New Release has opened.");
				flag = provideDetailsFutureRelease(LanguageCode);
			} else if (verifyElementsOfWhatsNewPage("draftNewReleaseButton")) {
				sleeper(2000);
				clickOnElementsOfWhatsNewPage("draftNewReleaseButton");
				LOGGER.info("Modal for Draft a New Release has opened.");
				flag = provideDetailsFutureRelease(LanguageCode);
			} else {
				LOGGER.error("Button for adding new Release is not displayed.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in addNewFutureRelease: " + e.getMessage());
			return false;
		}
	}

	/**
	 * @param LanguageCode
	 * @return
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public final boolean provideDetailsLatestRelease(String LanguageCode) {
		try {
			boolean flag = false;
			int[] randomNumber = new int[3];
			String[] randomString = new String[3];
			Random random = new Random();
			for (int randomNumberCounter = 0; randomNumberCounter < 3; randomNumberCounter++) {
				randomNumber[randomNumberCounter] = random.nextInt(100);
				randomString[randomNumberCounter] = String.valueOf(randomNumber[randomNumberCounter]);
			}
			if (!(getAttributesOfWhatsNewPage("versionDisabledCheck", "class").contains("disabled") || getAttributesOfWhatsNewPage("dateDisabledCheck", "class").contains("disabled"))) {
				enterTextForWhatsNewPage("releaseVersion", randomString[0] + "." + randomString[1] + "." + randomString[2]);
				LOGGER.info("Version is entered.");
				clickOnElementsOfWhatsNewPage("dateField");
				clickOnElementsOfWhatsNewPage("todayButton");
				LOGGER.info("Date is entered.");
				enterTextForWhatsNewPage("customerNotes", "Customer notes for release version " + randomString[0] + "." + randomString[1] + "." + randomString[2]);
				LOGGER.info("Customer Notes is entered.");
				enterTextForWhatsNewPage("partnerNotes", "Partner notes for release version " + randomString[0] + "." + randomString[1] + "." + randomString[2]);
				LOGGER.info("Partner Notes is entered.");
				versionTextLatest = getAttributesOfWhatsNewPage("releaseVersion", "value");
				customerTextLatest = getTextOfWhatsNewPage("customerNotes");
				partnerTextLatest = getTextOfWhatsNewPage("partnerNotes");
				//sleeper(2000);
				clickOnElementsOfWhatsNewPage("save");
				LOGGER.info("Details are saved.");
				//waitUntilElementIsInvisibleOfWhatsNewPage("tableOverlay");
				sleeper(10000);
				//verifyElementsOfWhatsNewPage("successMessage");
				String successMessage = getTextOfWhatsNewPage("successMessage");
				if (successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "whats_new.toast.new_release"))) {
					sleeper(15000);//Release takes time to display
					scrollDownWhatsNew();
					refreshPage();
					waitUntilElementIsInvisibleOfWhatsNewPage("tableOverlay");
					waitForElementsOfWhatsNewPage("listOfTotalVersions");
					List<WebElement> listOfVersions = getElementsTillAllElementsVisibleofWhatsNewPage("listOfTotalVersions");
					List<WebElement> listOfCustomerNotes = getElementsTillAllElementsVisibleofWhatsNewPage("listOfCustomerNotes");
					List<WebElement> listOfPartnerNotes = getElementsTillAllElementsVisibleofWhatsNewPage("listOfPartnerNotes");
					if (listOfVersions.size() > 0) {
						for (int listOfVersionCounter = 0; listOfVersionCounter < listOfVersions.size();) {
							if (listOfVersions.get(listOfVersionCounter).getText().contains(randomString[0] + "." + randomString[1] + "." + randomString[2])) {
								if (listOfCustomerNotes.get(listOfVersionCounter).getText().contains(customerTextLatest) && listOfPartnerNotes.get(listOfVersionCounter).getText().contains(partnerTextLatest)) {
									flag = true;
									LOGGER.info("Latest Release is added and reflected back successfully.");
									break;
								}
							}
							listOfVersionCounter++;
						}
					} else {
						LOGGER.error("There are no releases present on Whats New Page.");
					}
				} else {
					LOGGER.error("Release could not be added successfully.");
				}
			} else {
				LOGGER.error("Fields for version and date are disabled.");
			}
			return flag;
		} catch (InterruptedException e) {
			LOGGER.error("Exception occured in provideDetailsLatestRelease: " + e.getMessage());
			return false;
		} catch (Exception e) {
			LOGGER.error("Exception occured in provideDetailsLatestRelease: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used add release in Today's date.
	 * 
	 * @param LanguageCode : Language code is used refer different languages supported by DaaS.
	 * @return
	 * @throws Exception
	 */
	public final boolean addNewLatestRelease(String LanguageCode) {
		try {
			boolean flag = false;
			sleeper(2000);
			if (verifyElementsOfWhatsNewPage("draftNewReleaseButtonFirst")) {
				clickOnElementsOfWhatsNewPage("draftNewReleaseButtonFirst");
				LOGGER.info("Modal for Draft a New Release has opened.");
				flag = provideDetailsLatestRelease(LanguageCode);
			} else if (verifyElementsOfWhatsNewPage("draftNewReleaseButton")) {
				sleeper(2000);
				clickOnElementsOfWhatsNewPage("draftNewReleaseButton");
				LOGGER.info("Modal for Draft a New Release has opened.");
				flag = provideDetailsLatestRelease(LanguageCode);
			} else {
				LOGGER.error("Button for adding new Release is not displayed.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in addNewLatestRelease: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method verifies button presence on What's New page.
	 *
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyReleaseButton() {
		try {
			boolean flag = false;
			if (verifyElementsOfWhatsNewPage("draftNewReleaseButtonFirst") || verifyElementsOfWhatsNewPage("draftNewReleaseButton")) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyReleaseButton: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to verify latest release on MSP's What's new Page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyNewLatestReleaseMSP() {
		try {
			boolean flag = false;
			scrollDownWhatsNew();
			sleeper(3000);
			List<WebElement> listOfVersions = getElementsOfWhatsNewPage("listOfTotalVersions");
			List<WebElement> listOfCustomerNotes = getElementsOfWhatsNewPage("listOfCustomerNotes");
			List<WebElement> listOfPartnerNotes = getElementsOfWhatsNewPage("listOfPartnerNotes");
			if (waitForElementsOfWhatsNewPage("latestReleaseTag")) {
				if (listOfVersions.size() > 0 && listOfCustomerNotes.size() > 0 && listOfPartnerNotes.size() > 0) {
					for (int listOfVersionCounter = 0; listOfVersionCounter < listOfVersions.size();) {
						if (listOfVersions.get(listOfVersionCounter).getText().contains(versionTextLatest)) {
							if (listOfCustomerNotes.get(listOfVersionCounter).getText().contains(customerTextLatest) && listOfPartnerNotes.get(listOfVersionCounter).getText().contains(partnerTextLatest)) {
								flag = true;
								LOGGER.info("Release is reflected back successfully for MSP.");
								break;
							}
						}
						listOfVersionCounter++;
					}
				} else {
					LOGGER.error("There are no releases present on What's New Page of MSP.");
				}
			} else {
				LOGGER.error("Latest Release tag is not present on What's New Page of MSP.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyNewLatestReleaseMSP: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to verify latest release on RA's What's new Page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyNewLatestReleaseRA() {
		try {
			boolean flag = false;
			scrollDownWhatsNew();
			sleeper(3000);
			List<WebElement> listOfVersions = getElementsTillAllElementsVisibleofWhatsNewPage("listOfTotalVersions");
			List<WebElement> listOfCustomerNotes = getElementsTillAllElementsVisibleofWhatsNewPage("listOfCustomerNotes");
			if (!waitForElementsOfWhatsNewPage("listOfPartnerNotes")) {
				if (waitForElementsOfWhatsNewPage("latestReleaseTag")) {
					if (listOfVersions.size() > 0 && listOfCustomerNotes.size() > 0) {
						for (int listOfVersionCounter = 0; listOfVersionCounter < listOfVersions.size();) {
							if (listOfVersions.get(listOfVersionCounter).getText().contains(versionTextLatest)) {
								if (listOfCustomerNotes.get(listOfVersionCounter).getText().contains(customerTextLatest)) {
									flag = true;
									break;
								}
							}
							listOfVersionCounter++;
						}
					} else {
						LOGGER.error("There are no releases present on What's New Page of Company User..");
					}
				} else {
					LOGGER.error("Latest Release tag is not present on What's New Page of Company User.");
				}
			} else {
				LOGGER.error("Partner Notes are displayed to Customers.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyNewLatestReleaseRA: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to verify future release on MSP/RA's What's new Page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyFutureReleaseMspRA() {
		try {
			boolean flag = false;
			scrollDownWhatsNew();
			sleeper(3000);
			if (verifyElementsOfWhatsNewPage("whatsNewDecriptionHeader")) {
				List<WebElement> listOfVersions = getElementsTillAllElementsVisibleofWhatsNewPage("listOfTotalVersions");
				if (listOfVersions.size() > 0) {
					for (int listOfVersionCounter = 0; listOfVersionCounter < listOfVersions.size(); listOfVersionCounter++) {
						if (!listOfVersions.get(listOfVersionCounter).getText().contains(versionTextFuture)) {
							flag = true;
						} else {
							flag = false;
							LOGGER.error("Newly added Future Release reflected back on What's New page of MSP/RA.");
						}
					}
				} else {
					LOGGER.error("There are no releases present on What's New Page of MSP/RA.");
				}
			} else {
				LOGGER.info("What's New Section is not displayed for MSP/RA, since there are no releases made by Root Admin.");
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyFutureReleaseMspRA: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to remove future release on What's new Page.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyRemoveRelease(String LanguageCode, String version) {
		try {
			boolean flag = false;
			scrollDownWhatsNew();
			sleeper(3000);
			List<WebElement> listOfRemoveButtons = getElementsTillAllElementsVisibleofWhatsNewPage("removeButton");
			if (listOfRemoveButtons.size() > 0) {
				for (int listOfVersionCounter = 0; listOfVersionCounter < listOfRemoveButtons.size();) {
					listOfRemoveButtons.get(listOfVersionCounter).click();
					if (getTextOfWhatsNewPage("removeMessageModal").contains(version)) {
						clickOnElementsOfWhatsNewPage("removeButtonModal");
						sleeper(5000);
						verifyElementsOfWhatsNewPage("successMessage");
						String successMessage = getTextOfWhatsNewPage("successMessage");
						String customizedMessage = getTextLanguage(LanguageCode, "daas_ui", "whats_new.toast.release_removed").replaceAll("version", version).toString().replaceAll("[>\\{}]", "").toString();
						if (successMessage.equalsIgnoreCase(customizedMessage)) {
							flag = true;
							break;
						} else {
							LOGGER.error("Release did not get removed successfully.");
						}
					}
					listOfVersionCounter++;
					//clickOnElementsOfWhatsNewPage("cancelButtonModal");
				}
			} else {
				LOGGER.error("There are no recently added Future release present on What's New Page.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyRemoveRelease: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to Edit future release on What's new Page.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyEditFutureRelease(String LanguageCode) {
		try {
			boolean flag = false;
			int temp = 0;
			scrollDownWhatsNew();
			sleeper(3000);
			List<WebElement> listOfEditButtons = getElementsTillAllElementsVisibleofWhatsNewPage("editButton");
			if (listOfEditButtons.size() > 0) {
				for (int listOfVersionCounter = 0; listOfVersionCounter < listOfEditButtons.size();) {
					listOfEditButtons.get(listOfVersionCounter).click();
					String headerText = getTextOfWhatsNewPage("modalHeader");
					String header = getTextLanguage(LanguageCode, "daas_ui", "whats_new.draft_version").replaceAll("draftVersion", versionTextFuture).toString().replaceAll("[>\\{}]", "").toString();
					if (header.equalsIgnoreCase(headerText)) {
						enterTextForWhatsNewPage("customerNotes", "Customer notes for release version " + versionTextFuture);
						LOGGER.info("Customer Notes are entered.");
						enterTextForWhatsNewPage("partnerNotes", "Partner notes for release version " + versionTextFuture);
						LOGGER.info("Partner Notes are entered.");
						clickOnElementsOfWhatsNewPage("save");
						LOGGER.info("Details are saved.");
						verifyElementsOfWhatsNewPage("successMessage");
						String successMessage = getTextOfWhatsNewPage("successMessage");
						String customizedMessage = getTextLanguage(LanguageCode, "daas_ui", "whats_new.toast.release_updated").replaceAll("version", versionTextFuture).toString().replaceAll("[>\\{}]", "").toString();
						if (successMessage.equalsIgnoreCase(customizedMessage)) {
							scrollDownWhatsNew();
							sleeper(3000);
							List<WebElement> listOfVersions = getElementsTillAllElementsVisibleofWhatsNewPage("listOfTotalVersions");
							List<WebElement> listOfCustomerNotes = getElementsTillAllElementsVisibleofWhatsNewPage("listOfCustomerNotes");
							List<WebElement> listOfPartnerNotes = getElementsTillAllElementsVisibleofWhatsNewPage("listOfPartnerNotes");
							if (listOfVersions.size() > 0) {
								for (int i = 0; i < listOfVersions.size();) {
									if (listOfVersions.get(i).getText().contains(versionTextFuture)) {
										if (listOfCustomerNotes.get(i).getText().contains(customerTextFuture) && listOfPartnerNotes.get(i).getText().contains(partnerTextFuture)) {
											flag = true;
											LOGGER.info("Release got edited successfully.");
											temp = 1;
											break;
										}
									}
									i++;
								}
							} else {
								LOGGER.error("There are no releases present on Whats New Page.");
							}
						} else {
							LOGGER.error("Release did not get updated successfully.");
						}
					}
					if (temp == 1) {
						break;
					}
					listOfVersionCounter++;
					clickOnElementsOfWhatsNewPage("cancelButtonModal");
				}
			} else {
				LOGGER.error("There are no recently added Future release present on What's New Page.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyEditFutureRelease: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to Edit Latest release on What's new Page.
	 * 
	 * @param LanguageCode
	 * @return
	 * @throws Exception
	 */
	public final boolean verifyEditLatestRelease(String LanguageCode) {
		try {
			boolean flag = false;
			int temp = 0;
			List<WebElement> listOfEditButtons = getElementsTillAllElementsVisibleofWhatsNewPage("editButton");
			if (listOfEditButtons.size() > 0) {
				for (int listOfVersionCounter = listOfEditButtons.size() - 1; listOfVersionCounter >= 0;) {
					listOfEditButtons.get(listOfVersionCounter).click();
					String headerText = getTextOfWhatsNewPage("modalHeader");
					if (headerText.contains(versionTextLatest)) {
						enterTextForWhatsNewPage("customerNotes", "Customer notes for release version " + versionTextLatest);
						LOGGER.info("Customer Notes are entered.");
						enterTextForWhatsNewPage("partnerNotes", "Partner notes for release version " + versionTextLatest);
						LOGGER.info("Partner Notes are entered.");
						clickOnElementsOfWhatsNewPage("save");
						LOGGER.info("Details are saved.");
						sleeper(10000);
						verifyElementsOfWhatsNewPage("successMessage");
						String successMessage = getTextOfWhatsNewPage("successMessage");
						String customizedMessage = getTextLanguage(LanguageCode, "daas_ui", "whats_new.toast.release_updated").replaceAll("version", versionTextLatest).toString().replaceAll("[>\\{}]", "").toString();
						if (successMessage.equalsIgnoreCase(customizedMessage)) {
							waitForElementsOfWhatsNewPage("listOfTotalVersions");
							verifyElementsOfWhatsNewPage("listOfTotalVersions");
							sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
							List<WebElement> listOfVersions = getElementsOfWhatsNewPage("listOfTotalVersions");
							List<WebElement> listOfCustomerNotes = getElementsOfWhatsNewPage("listOfCustomerNotes");
							List<WebElement> listOfPartnerNotes = getElementsOfWhatsNewPage("listOfPartnerNotes");
							if (listOfVersions.size() > 0) {
								for (int i = 0; i < listOfVersions.size();) {
									if (listOfVersions.get(i).getText().contains(versionTextLatest)) {
										if (listOfCustomerNotes.get(i).getText().contains(customerTextLatest) && listOfPartnerNotes.get(i).getText().contains(partnerTextLatest)) {
											flag = true;
											LOGGER.info("Release got edited successfully.");
											temp = 1;
											break;
										}
									}
									i++;
								}
							} else {
								LOGGER.error("There are no releases present on Whats New Page.");
							}

						} else {
							LOGGER.error("Release did not get updated successfully.");
						}
					}
					if (temp == 1) {
						break;
					}
					listOfVersionCounter--;
					//clickOnElementsOfWhatsNewPage("cancelButtonModal");
				}
			} else {
				LOGGER.error("There are no recently added Future release present on What's New Page.");
			}
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyEditLatestRelease: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to read api data for the Latest Release
	 * 
	 * @param api - URL from you which you want the data
	 * @param body - request body
	 * @param index - label index
	 * @param data - event name required
	 * @throws Exception
	 */
	public final List<String> getLatestReleaseDetails(String api, String body, int index, String id) {
		try {
			List<String> listOfIds = new ArrayList<String>();
			String jsonResponseId = null;
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
			RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken).body(body);
			Response response = httpRequest.get(api);
			String expected = response.asString();
			JSONObject jsonObject, obj;
			jsonObject = new JSONObject(expected);
			JSONArray jsonArray = jsonObject.getJSONArray("resources");
			for (int i = 0; i < jsonArray.length(); i++) {
				obj = new JSONObject(jsonArray.get(i).toString());
				jsonResponseId = new JSONObject(obj.toString()).get(id).toString();
				listOfIds.add(jsonResponseId);
			}
			return listOfIds;
		} catch (Exception e) {
			LOGGER.error("Exception occured in getLatestReleaseDetails: " + e.getMessage());
			return null;
		}
	}

	/**
	 * This method will remove all Releases
	 * 
	 * @param environment
	 * @return
	 * @throws Exception
	 */
	public final boolean removeAllReleases(String environment) {
		try {
			boolean flag = false;
			List<String> listOfIds = getLatestReleaseDetails(environment + ConstantURL.GET_API_WHATSNEW, "{}", 0, "id");
			LOGGER.info(listOfIds);
			if (listOfIds.size() > 0) {
				for (int i = 0; i < listOfIds.size(); i++) {
					int code = getStatusCode(environment + ConstantURL.DELETE_API_WHATSNEW + listOfIds.get(i), "{}", "delete", environment);
					if (code != CommonVariables.CODESUCCESS) {
						flag = false;
						LOGGER.error("Delete API got failed while removing releases.");
						break;
					}
					flag = true;
				}
			} else {
				LOGGER.info("There are no releases present.");
				flag = true;
			}
			refreshPage();
			waitForPageLoaded();
			return flag;
		} catch (Exception e) {
			LOGGER.error("Exception occured in removeAllReleases: " + e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * This method will check if release is deleted
	 * 
	 * @return
	 * @throws Exception
	 */
	public final boolean checkDeletedRelease() throws Exception {
		List<WebElement> listOfVersions = null;
		boolean flag = true;
		if (verifyElementsOfWhatsNewPage("listOfTotalVersions")) {
			listOfVersions = getElementsTillAllElementsVisibleofWhatsNewPage("listOfTotalVersions");
			for (WebElement version : listOfVersions) {
				if(version.getText().equals(versionTextLatest))
					flag = false;
			}
		} else {
			LOGGER.info("No release present");
		}
		if(flag)
		{
			LOGGER.info("Release deleted successfully");
		}
		return flag;

	}
}
