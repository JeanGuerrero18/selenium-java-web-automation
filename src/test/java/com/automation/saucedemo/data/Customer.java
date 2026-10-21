package com.automation.saucedemo.data;

public record Customer(String firstName, String lastName, String postalCode) {

    public static Customer valid() {
        return new Customer("Usuario", "A", "10001");
    }
}
