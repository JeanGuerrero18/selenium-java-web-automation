package com.automation.saucedemo.pages;

import com.automation.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CartPage extends BasePage {

    private static final By CART_ITEM = By.className("cart_item");
    private static final By ITEM_NAME = By.className("inventory_item_name");
    private static final By CHECKOUT_BUTTON = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public HeaderComponent header() {
        return new HeaderComponent(driver);
    }

    public List<String> itemNames() {
        visible(CHECKOUT_BUTTON);
        return isPresent(CART_ITEM) ? textsOf(ITEM_NAME) : List.of();
    }

    public CheckoutInformationPage checkout() {
        step("Proceed to checkout");
        click(CHECKOUT_BUTTON);
        return new CheckoutInformationPage(driver);
    }
}
