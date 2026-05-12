package com.basesource.utils.validator;

import java.util.List;

/**
 * Functional interface for validating two values and collecting error messages.
 */
@FunctionalInterface
public interface ValidationFunction<T, U> {
    /**
     * Validates actual vs expected and appends error if not equal.
     *
     * @param actual Actual value
     * @param expected Expected value
     * @param errorMessage Error message format
     * @param errors Error list to append to
     */
    void apply(T actual, U expected, String errorMessage, List<String> errors);
}
