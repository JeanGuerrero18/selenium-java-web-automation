package com.automation.saucedemo.pages;

import com.automation.framework.config.Config;
import com.automation.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        step("Open SauceDemo login page");
        driver.get(Config.get("saucedemo.url"));
        visible(USERNAME);
        return this;
    }

    public InventoryPage loginAs(String username, String password) {
        submit(username, password);
        return new InventoryPage(driver);
    }

    /** Envía las credenciales esperando quedarse en la página de login (casos negativos). */
    public LoginPage attemptLogin(String username, String password) {
        submit(username, password);
        return this;
    }

    public String errorMessage() {
        return textOf(ERROR);
    }

    public boolean isDisplayed() {
        return visible(LOGIN_BUTTON).isDisplayed();
    }

    private void submit(String username, String password) {
        // La contraseña nunca se escribe en los logs ni en el reporte.
        step("Log in with username '" + username + "'");
        type(USERNAME, username);
        type(PASSWORD, password);
        click(LOGIN_BUTTON);
    }
}
