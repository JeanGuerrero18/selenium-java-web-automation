package com.automation.saucedemo.tests;

import com.automation.saucedemo.pages.InventoryPage;
import com.automation.saucedemo.pages.InventoryPage.SortOption;
import org.testng.annotations.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTests extends SauceDemoTest {

    @Test(groups = "regression",
            description = "Ordenar por precio de menor a mayor ordena el catálogo de forma ascendente")
    public void sortByPriceLowToHigh() {
        InventoryPage inventory = loginAsStandardUser().sortBy(SortOption.PRICE_LOW_TO_HIGH);

        assertThat(inventory.productPrices()).isSorted();
    }

    @Test(groups = "regression",
            description = "Ordenar por nombre de Z a A ordena el catálogo de forma descendente")
    public void sortByNameZToA() {
        InventoryPage inventory = loginAsStandardUser().sortBy(SortOption.NAME_Z_TO_A);

        assertThat(inventory.productNames()).isSortedAccordingTo(Comparator.reverseOrder());
    }
}
