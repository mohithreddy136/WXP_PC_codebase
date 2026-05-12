package com.utils.activecare;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestMetadata {
    String validationVariationValue() default "";
    String[] orderTypes() default {};
    String expectedStatus() default "";
    String expectedPlans() default "";
    String currentOrderType() default "";

}
