package com.automation.framework.driver;

import java.util.Arrays;
import java.util.Locale;

public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE;

    public static BrowserType from(String value) {
        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unsupported browser '" + value + "'. Use one of " + Arrays.toString(values()), e);
        }
    }
}
