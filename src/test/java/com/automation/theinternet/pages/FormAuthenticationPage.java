package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FormAuthenticationPage extends TheInternetPage {

    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.cssSelector("#login button[type='submit']");
    private static final By FLASH = By.id("flash");
    private static final By SECURE_AREA_HEADER = By.cssSelector("#content h2");

    public FormAuthenticationPage(WebDriver driver) {
        super(driver);
    }

    public FormAuthenticationPage open() {
        openPath("/login");
        visible(USERNAME);
        return this;
    }

    public FormAuthenticationPage login(String username, String password) {
        step("Log in with username '" + username + "'");
        type(USERNAME, username);
        type(PASSWORD, password);
        click(LOGIN_BUTTON);
        return this;
    }

    /** Texto del banner flash sin el símbolo de cierre final. */
    public String flashMessage() {
        return textOf(FLASH).replace("×", "").trim();
    }

    public String header() {
        return textOf(SECURE_AREA_HEADER);
    }
}
