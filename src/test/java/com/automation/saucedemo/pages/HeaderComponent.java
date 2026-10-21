package com.automation.saucedemo.pages;

import com.automation.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Cabecera común a todas las páginas tras el login: badge y enlace del carrito, y menú lateral. */
public class HeaderComponent extends BasePage {

    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By MENU_BUTTON = By.id("react-burger-menu-btn");
    private static final By LOGOUT_LINK = By.id("logout_sidebar_link");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    /** Número de artículos en el icono del carrito; con el carrito vacío el badge no se renderiza. */
    public int cartCount() {
        return isPresent(CART_BADGE) ? Integer.parseInt(textOf(CART_BADGE)) : 0;
    }

    public CartPage openCart() {
        step("Open cart");
        click(CART_LINK);
        return new CartPage(driver);
    }

    public LoginPage logout() {
        step("Log out from side menu");
        click(MENU_BUTTON);
        click(LOGOUT_LINK);
        return new LoginPage(driver);
    }
}
