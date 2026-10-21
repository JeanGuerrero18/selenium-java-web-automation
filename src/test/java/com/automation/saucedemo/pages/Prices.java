package com.automation.saucedemo.pages;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Interpreta etiquetas de importe como "$29.99" o "Item total: $39.98". */
final class Prices {

    private static final Pattern AMOUNT = Pattern.compile("\\$(\\d+(?:\\.\\d{2})?)");

    private Prices() {
    }

    static BigDecimal parse(String label) {
        Matcher matcher = AMOUNT.matcher(label);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No price found in '" + label + "'");
        }
        return new BigDecimal(matcher.group(1));
    }
}
