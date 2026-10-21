package com.automation.saucedemo.pages;

import com.automation.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private static final By CONFIRMATION_HEADER = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String confirmationMessage() {
        return textOf(CONFIRMATION_HEADER);
    }
}
