package com.testscripts.daasui;

import com.basesource.action.PreDefinedActions;
import com.daasui.pages.WEPPendoGuidesResourecePage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WEPPendoGuidesResourceTest extends CommonTest {
    SoftAssert softAssert = new SoftAssert();

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyResourceCenterSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("Resourcecentertext"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ResourceCenterCloseButton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("EnrollYourFleetTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("EnrollYourFleetDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("EnrollYourFleetArrowButton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("DiscoverMoreTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("DiscoverMoreDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("DiscoverMoreArrowButton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("PrintersTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("PrintersDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("PrintersArrowButton"));
        sleeper(2000);
        softAssert.assertAll();

    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyEnrollyourFleetSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("EnrollYourFleetArrowButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("EnrollYourFleetBackButton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddPCsOption"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddPCsDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddVirtualMachinesOption"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddVirtualMachinesDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddPhysicalAssetsOption"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddPhysicalAssetsDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("OnboardingCompletionStatus"));
        sleeper(2000);
        softAssert.assertAll();
    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyAddPcsInFleetSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("EnrollYourFleetArrowButton");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("AddPCsOption");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddPCTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("OnboardDesktopsText"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowBullet1"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowBullet2"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("StartTourButton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("StartTourButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPCsStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPCsTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPCsMainDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPCsSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("Addpcslink");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManagePCsStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManagePCsTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManagePCsMainDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManagePCsSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ManagePCsAddButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseDeviceTypeStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseDeviceTypeTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseDeviceTypeSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseDeviceTypeCloseButton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("TelemetryDevicesOption");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodMainDescription1"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodMainDescription2"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ChooseMethodDoneButton");
        sleeper(3000);
        softAssert.assertAll();
    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyAddPrintersInFleetSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("EnrollYourFleetArrowButton");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("AddVirtualMachinesOption");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddVirtualMachinesTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("OnboardVirtualMachinesText"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowBullet1"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowBullet2"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("StartTourButton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("StartTourButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToVirtualMachinesStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToVirtualMachinesTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToVirtualMachinesMainDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToVirtualMachinesSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("AddVirtualMachinesLink");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManageVirtualMachinesStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManageVirtualMachinesTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManageVirtualMachinesMainDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManageVirtualMachinesSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ManageVirtualMachinesAddButton");
        sleeper(3000);

        // Continue with Choose Device Type and Choose Method steps similar to PC flow
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseDeviceTypeStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseDeviceTypeTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseDeviceTypeSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("TelemetryDevicesOption");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodMainDescription1"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodMainDescription2"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ChooseMethodDoneButton");
        sleeper(3000);
        
        softAssert.assertAll();
    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyAddPhysicalAssetInFleetSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("EnrollYourFleetArrowButton");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("AddPhysicalAssetsOption");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddPhysicalAssetsTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("OnboardPhysicalAssetsText"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AddPhysicalAssetsStartTourButton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("AddPhysicalAssetsStartTourButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPhysicalAssetsStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPhysicalAssetsTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPhysicalAssetsMainDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPhysicalAssetsSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("AddPhysicalAssetsLink");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManagePhysicalAssetsStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManagePhysicalAssetsTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManagePhysicalAssetsMainDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManagePhysicalAssetsSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ManagePhysicalAssetsAddButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("CSVTemplatestepnumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("CSVTemplateheader"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ChooseMethodPhysicalAssetsDescription1"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("closearrowbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ChooseMethodDoneButton");
        sleeper(3000);
        
        softAssert.assertAll();
    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyManageyourAccountInDiscoverMoreSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("DiscoverMoreArrowButton");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ManageyourAccount");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManageAccountTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManageAccountDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManageAccountPermissionsBullet"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("ManageAccountProfileBullet"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("StartTourButton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("StartTourButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountSubDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("AccountMenuLink"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("AccountMenuLink");
        sleeper(5000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToOverviewStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountoverviewTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountoverviewDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToRolesStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountRolesTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountrolesDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToadminStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountadminDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTolicensesStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountlicensesTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToAccountlicensesDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("donebutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("donebutton");
        sleeper(3000);

        softAssert.assertAll();
    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyGatherEmployeeFeedbackInDiscoverMoreSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("DiscoverMoreArrowButton");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("GatherEmployeeFeedback");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherYourfeedbackTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherYourfeedbackDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatheryourfeedbackPermissionsBullet"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherYourfeedbackProfileBullet"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("StartTourButton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("StartTourButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTopulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTopulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("pulselink"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("pulselink");
        sleeper(5000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaddPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaddpulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaddpulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("Addcustompulses"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("Addcustompulses");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocustomPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocustompulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocustompulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("custompulses"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("custompulses");
        sleeper(3000);

        if(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("osToastniotification")) {
            softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("osToastniotification"));
            WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("osToastniotification");
            sleeper(3000);
        }

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToscratchPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToscratchpulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToscratchpulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("scratchpulses"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("scratchpulses");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocontentPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocontentpulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocontentpulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToschdulePulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToschdulepulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToschdulepulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaudiencePulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaudiencepulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaudiencepulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);


        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTopublishPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTopublishpulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTopublishpulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("donebutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("donebutton");
        sleeper(3000);

        softAssert.assertAll();
    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyImproveExperienceScoreInDiscoverMoreSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("DiscoverMoreArrowButton");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ImproveExperienceScore");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherYourexperienceTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherYourexperienceDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatheryourexperiencePermissionsBullet"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherYourexperienceProfileBullet"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("StartTourButton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("StartTourButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToUnderstandimproveStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToUnderstandimproveTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToUnderstandimproveDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTosystem-wideimproveStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTosystem-wideimproveTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTosystem-wideimproveDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        if(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTorecommendedimproveTitle")){
            softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTorecommendedimproveStepNumber"));
            softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTorecommendedimproveTitle"));
            softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTorecommendedimproveDescription"));
            softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
            softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("donebutton"));
            WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("donebutton");
            sleeper(3000);
        }

        softAssert.assertAll();
    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyTroubleshootPCIssuesInDiscoverMoreSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("DiscoverMoreArrowButton");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("TroubleshootPCIssues");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherTroubleshootTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherTroubleshootDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherTroubleshootBullet"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherTroubleshootBullet2"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("StartTourButton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("StartTourButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("Addpcslink"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("Addpcslink");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateselectPCStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateselectPCTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateselectPCDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("Serialnumberpclick"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("Serialnumberpclick");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCoverviewStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCoverviewTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCoverviewDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCtimelineStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCtimelineTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCtimelineDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(5000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePChealthStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePChealthTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePChealthDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCBIODStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCBIOSTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCBIOSDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCsoftwareStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCsoftwareTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigatePCsoftwareDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("donebutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("donebutton");
        sleeper(3000);

        softAssert.assertAll();
    }

    @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS" }, description = "TestCaseID:C41392653")
    public final void verifyUnderstandWorkforceSentimentInDiscoverMoreSection() throws Exception {
        WEPPendoGuidesResourecePage WEPPendoGuidesResourecePage = new WEPPendoGuidesResourecePage(PreDefinedActions.getDriver());
        login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
        waitForPageLoaded();
        sleeper(4000);
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("ResourceCenterInAppBar");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("DiscoverMoreArrowButton");
        sleeper(3000);

        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("UnderstandWorkforceSentiment");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherSentimentTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherSentimentDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GoodToKnowTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherSentimentBullet"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("GatherSentimentBullet2"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("StartTourButton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("StartTourButton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTosentientPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTosentimentpulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTosentimentpulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("pulselink"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("pulselink");
        sleeper(5000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaddsentimentPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaddsentimentpulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateToaddsentimentpulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("Addcustompulses"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("Addcustompulses");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentPulseStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulseadminTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulseDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("sentimentpulses"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("sentimentpulses");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentPulsecontentStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulsecontentTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulsecontentDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentPulseschduleStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulseschduleTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulseschduleDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentPulseaudienceStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulseaudienceTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulseaudienceDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("nextbutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("nextbutton");
        sleeper(3000);

        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentPulsepublishStepNumber"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulsepublishTitle"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("NavigateTocreatesentimentpulsepublishDescription"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("donebutton"));
        softAssert.assertTrue(WEPPendoGuidesResourecePage.verifyElementsOfWEPPendoGuidesResourecePage("backbutton"));
        WEPPendoGuidesResourecePage.clickOnElementsOfWEPPendoGuidesResourecePage("donebutton");
        sleeper(3000);

        softAssert.assertAll();

    }
    
}
