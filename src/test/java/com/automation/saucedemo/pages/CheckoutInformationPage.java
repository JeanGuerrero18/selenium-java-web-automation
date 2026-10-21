package com.automation.saucedemo.pages;

import com.automation.framework.pages.BasePage;
import com.automation.saucedemo.data.Customer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInformationPage extends BasePage {

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By ERROR = By.cssSelector("[data-test='error']");

    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutOverviewPage continueWith(Customer customer) {
        fill(customer);
        return new CheckoutOverviewPage(driver);
    }

    /** Envía el formulario esperando un error de validación. */
    public CheckoutInformationPage attemptContinueWith(Customer customer) {
        fill(customer);
        return this;
    }

    public String errorMessage() {
        return textOf(ERROR);
    }

    private void fill(Customer customer) {
        step("Fill customer information and continue");
        type(FIRST_NAME, customer.firstName());
        type(LAST_NAME, customer.lastName());
        type(POSTAL_CODE, customer.postalCode());
        click(CONTINUE_BUTTON);
    }
}
