package com.utils.activecare;

import com.testscripts.daasui.CommonTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public abstract class BaseConfigurableUtils extends CommonTest {

    private static final Logger LOGGER = LogManager.getLogger(BaseConfigurableUtils.class);


    @BeforeMethod
    public void setUpForEachTest(Method method) {
        // Call parent's BeforeMethod to initialize LanguageCode and other setup
        try {
            super.beforeMethod(method, null, null);
        } catch (Exception e) {
            LOGGER.error("Error in parent beforeMethod: " + e.getMessage());
        }
        if(method.isAnnotationPresent(Test.class)){
            LOGGER.info("Setting up for test method: " + method.getName());
            if (method != null && method.isAnnotationPresent(TestMetadata.class)) {

                String validationVariationValue = method.getAnnotation(TestMetadata.class).validationVariationValue();
                TestContextHolder.setValidationVariationValue(validationVariationValue);

                String currentOrdertype = method.getAnnotation(TestMetadata.class).currentOrderType();
                if (currentOrdertype != null && !currentOrdertype.isEmpty()) {
                    TestContextHolder.setCurrentOrderType(currentOrdertype);
                    LOGGER.info("Current order type set to: " + currentOrdertype);
                } else {
                    LOGGER.warn("TestMetadata annotation present but currentOrderType is null or empty for method: " + method.getName());
                }

                String expectedStatus = method.getAnnotation(TestMetadata.class).expectedStatus();
                TestContextHolder.setExpectedStatus(expectedStatus);

                String expectedPlans = method.getAnnotation(TestMetadata.class).expectedPlans();
                TestContextHolder.setExpectedPlans(expectedPlans);

            } else {
                LOGGER.warn("TestMetadata annotation not present on method: " + method.getName());
            }

        }
    }

    @AfterMethod
    public void clearAfterExecutionTestMethod(ITestResult result, ITestContext context, Method method){
        TestContextHolder.clear();
        
        // Call parent's AfterMethod to ensure proper cleanup (logout, driver quit, network logs, etc.)
        try {
            super.aftermethod(result, context, method);
        } catch (Exception e) {
            LOGGER.error("Error in parent afterMethod: " + e.getMessage());
        }
    }


}
