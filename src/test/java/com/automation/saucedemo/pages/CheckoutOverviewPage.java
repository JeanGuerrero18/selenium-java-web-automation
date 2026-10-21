package com.automation.saucedemo.pages;

import com.automation.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutOverviewPage extends BasePage {

    private static final By ITEM_PRICE = By.className("inventory_item_price");
    private static final By SUBTOTAL = By.className("summary_subtotal_label");
    private static final By TAX = By.className("summary_tax_label");
    private static final By TOTAL = By.className("summary_total_label");
    private static final By FINISH_BUTTON = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public List<BigDecimal> itemPrices() {
        return textsOf(ITEM_PRICE).stream().map(Prices::parse).toList();
    }

    public BigDecimal subtotal() {
        return Prices.parse(textOf(SUBTOTAL));
    }

    public BigDecimal tax() {
        return Prices.parse(textOf(TAX));
    }

    public BigDecimal total() {
        return Prices.parse(textOf(TOTAL));
    }

    public CheckoutCompletePage finish() {
        step("Finish order");
        click(FINISH_BUTTON);
        return new CheckoutCompletePage(driver);
    }
}
