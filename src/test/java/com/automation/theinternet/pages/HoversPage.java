package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HoversPage extends TheInternetPage {

    private static final By FIGURE = By.className("figure");
    private static final By CAPTION_TITLE = By.cssSelector(".figcaption h5");

    public HoversPage(WebDriver driver) {
        super(driver);
    }

    public HoversPage open() {
        openPath("/hovers");
        return this;
    }

    /** Pasa el cursor sobre el avatar en {@code index} (base 0) y devuelve el texto que aparece. */
    public String captionAfterHover(int index) {
        step("Hover over avatar " + (index + 1));
        WebElement figure = allVisible(FIGURE).get(index);
        new Actions(driver).moveToElement(figure).perform();
        WebElement caption = figure.findElement(CAPTION_TITLE);
        return wait.until(ExpectedConditions.visibilityOf(caption)).getText().trim();
    }
}
