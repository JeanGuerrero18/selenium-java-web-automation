package com.automation.saucedemo.tests;

import com.automation.saucedemo.pages.CartPage;
import com.automation.saucedemo.pages.InventoryPage;
import org.testng.annotations.Test;

import static com.automation.saucedemo.data.Products.BACKPACK;
import static com.automation.saucedemo.data.Products.BIKE_LIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class CartTests extends SauceDemoTest {

    @Test(groups = {"smoke", "regression"},
            description = "Los productos agregados aparecen en el badge y en el carrito")
    public void addedItemsAppearInCart() {
        InventoryPage inventory = loginAsStandardUser()
                .addToCart(BACKPACK)
                .addToCart(BIKE_LIGHT);

        assertThat(inventory.header().cartCount()).isEqualTo(2);

        CartPage cart = inventory.header().openCart();
        assertThat(cart.itemNames()).containsExactlyInAnyOrder(BACKPACK, BIKE_LIGHT);
    }

    @Test(groups = "regression", description = "Quitar un producto actualiza el badge y el carrito")
    public void removedItemLeavesCart() {
        InventoryPage inventory = loginAsStandardUser()
                .addToCart(BACKPACK)
                .addToCart(BIKE_LIGHT)
                .removeFromCart(BACKPACK);

        assertThat(inventory.header().cartCount()).isEqualTo(1);
        assertThat(inventory.header().openCart().itemNames()).containsExactly(BIKE_LIGHT);
    }
}
