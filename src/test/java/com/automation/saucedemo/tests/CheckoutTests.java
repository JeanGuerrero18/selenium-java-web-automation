package com.automation.saucedemo.tests;

import com.automation.saucedemo.data.Customer;
import com.automation.saucedemo.pages.CheckoutCompletePage;
import com.automation.saucedemo.pages.CheckoutInformationPage;
import com.automation.saucedemo.pages.CheckoutOverviewPage;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static com.automation.saucedemo.data.Products.BACKPACK;
import static com.automation.saucedemo.data.Products.BOLT_TSHIRT;
import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutTests extends SauceDemoTest {

    @Test(groups = {"smoke", "regression"},
            description = "Compra de punta a punta, desde el login hasta la confirmación del pedido")
    public void completePurchaseEndToEnd() {
        CheckoutCompletePage complete = loginAsStandardUser()
                .addToCart(BACKPACK)
                .header().openCart()
                .checkout()
                .continueWith(Customer.valid())
                .finish();

        assertThat(complete.confirmationMessage()).isEqualTo("Thank you for your order!");
    }

    @Test(groups = "regression",
            description = "El resumen del pedido cuadra: subtotal de artículos y total con impuestos")
    public void overviewTotalsAreConsistent() {
        CheckoutOverviewPage overview = loginAsStandardUser()
                .addToCart(BACKPACK)
                .addToCart(BOLT_TSHIRT)
                .header().openCart()
                .checkout()
                .continueWith(Customer.valid());

        BigDecimal sumOfItems = overview.itemPrices().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        assertThat(overview.subtotal()).isEqualByComparingTo(sumOfItems);
        assertThat(overview.total()).isEqualByComparingTo(overview.subtotal().add(overview.tax()));
    }

    @Test(groups = "regression", description = "El checkout no puede continuar sin el nombre")
    public void checkoutRequiresFirstName() {
        CheckoutInformationPage information = loginAsStandardUser()
                .addToCart(BACKPACK)
                .header().openCart()
                .checkout()
                .attemptContinueWith(new Customer("", "A", "10001"));

        assertThat(information.errorMessage()).isEqualTo("Error: First Name is required");
    }
}
