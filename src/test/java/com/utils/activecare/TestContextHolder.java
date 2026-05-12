package com.utils.activecare;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestContextHolder {



    private static final Logger LOGGER = LogManager.getLogger(TestContextHolder.class);

    private static final ThreadLocal<String> validationVariationValue = new ThreadLocal<>();
    private static final ThreadLocal<String[]> orderType = new ThreadLocal<>();
    private static final ThreadLocal<String> currentOrderType = new ThreadLocal<>();
    private static final ThreadLocal<String> expectedStatus = new ThreadLocal<>();
    private static final ThreadLocal<String> expectedPlans  = new ThreadLocal<>();

    public static String getValidationVariationValue() {
        return validationVariationValue.get();
    }

    public static String[] getOrderType() {
        return orderType.get();
    }

    public static String getCurrentOrderType() {
        return currentOrderType.get();
    }

    public static String getExpectedStatus() {
        return expectedStatus.get();
    }

    public static String getExpectedPlans() {
        return expectedPlans.get();
    }


    public static void setValidationVariationValue(String value) {
        validationVariationValue.set(value);
        LOGGER.info("Thread local validationVariationValue is set to : {}", validationVariationValue.get());
    }

    public static void setOrderType(String[] value) {
        orderType.set(value);
        LOGGER.info("Thread local orderType is set to : {}", orderType.get());
    }

    public static void setCurrentOrderType(String value) {
        currentOrderType.set(value);
        LOGGER.info("Thread local currentOrderType is set to : {}", currentOrderType.get());
    }

    public static void setExpectedStatus(String value) {
        expectedStatus.set(value);
        LOGGER.info("Thread local expectedStatus is set to : {}", expectedStatus.get());
    }

    public static void setExpectedPlans(String value) {
        expectedPlans.set(value);
        LOGGER.info("Thread local expectedPlans is set to : {}", expectedPlans.get());
    }


    public static void clear() {
        validationVariationValue.remove();
        LOGGER.info("ThreadLocal - validationVariationValue, cleared from listener");
        orderType.remove();
        LOGGER.info("ThreadLocal - orderType, cleared from listener");
        currentOrderType.remove();
        LOGGER.info("ThreadLocal - currentOrderType, cleared from listener");
        expectedStatus.remove();
        LOGGER.info("ThreadLocal - expectedStatus, cleared from listener");
        expectedPlans.remove();
        LOGGER.info("ThreadLocal - expectedPlans, cleared from listener");
    }

}
