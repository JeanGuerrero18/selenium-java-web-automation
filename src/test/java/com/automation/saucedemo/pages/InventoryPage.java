package com.automation.saucedemo.pages;

import com.automation.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.util.List;

public class InventoryPage extends BasePage {

    private static final By TITLE = By.className("title");
    private static final By ITEM = By.className("inventory_item");
    private static final By ITEM_NAME = By.className("inventory_item_name");
    private static final By ITEM_PRICE = By.className("inventory_item_price");
    private static final By ITEM_BUTTON = By.tagName("button");
    private static final By SORT_SELECT = By.className("product_sort_container");

    public enum SortOption {
        NAME_A_TO_Z("az"),
        NAME_Z_TO_A("za"),
        PRICE_LOW_TO_HIGH("lohi"),
        PRICE_HIGH_TO_LOW("hilo");

        private final String value;

        SortOption(String value) {
            this.value = value;
        }
    }

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public HeaderComponent header() {
        return new HeaderComponent(driver);
    }

    public String title() {
        return textOf(TITLE);
    }

    public List<String> productNames() {
        return textsOf(ITEM_NAME);
    }

    public List<BigDecimal> productPrices() {
        return textsOf(ITEM_PRICE).stream().map(Prices::parse).toList();
    }

    public InventoryPage sortBy(SortOption option) {
        step("Sort products by " + option);
        new Select(visible(SORT_SELECT)).selectByValue(option.value);
        return this;
    }

    /** El botón de la tarjeta alterna entre "Add to cart" y "Remove". */
    public InventoryPage addToCart(String productName) {
        step("Add '" + productName + "' to cart");
        itemCard(productName).findElement(ITEM_BUTTON).click();
        return this;
    }

    public InventoryPage removeFromCart(String productName) {
        step("Remove '" + productName + "' from cart");
        itemCard(productName).findElement(ITEM_BUTTON).click();
        return this;
    }

    /** Busca la tarjeta de un producto por su nombre visible en lugar de depender de ids generados. */
    private WebElement itemCard(String productName) {
        return allVisible(ITEM).stream()
                .filter(card -> card.findElement(ITEM_NAME).getText().trim().equals(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + productName));
    }
}
