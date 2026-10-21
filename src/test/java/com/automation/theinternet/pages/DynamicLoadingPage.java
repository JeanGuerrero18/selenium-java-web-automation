package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Ejemplo 2: el elemento de resultado solo se agrega al DOM tras una carga simulada de ~5 s. */
public class DynamicLoadingPage extends TheInternetPage {

    private static final By START_BUTTON = By.cssSelector("#start button");
    private static final By FINISH_TEXT = By.cssSelector("#finish h4");

    public DynamicLoadingPage(WebDriver driver) {
        super(driver);
    }

    public DynamicLoadingPage open() {
        openPath("/dynamic_loading/2");
        return this;
    }

    public DynamicLoadingPage start() {
        step("Start loading");
        click(START_BUTTON);
        return this;
    }

    /** Espera explícita a que aparezca el elemento; sin Thread.sleep. */
    public String loadedText() {
        return textOf(FINISH_TEXT);
    }
}
