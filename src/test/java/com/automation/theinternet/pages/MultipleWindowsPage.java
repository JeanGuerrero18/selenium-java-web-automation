package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultipleWindowsPage extends TheInternetPage {

    private static final By NEW_WINDOW_LINK = By.cssSelector("a[href='/windows/new']");
    private static final By HEADING = By.tagName("h3");

    public MultipleWindowsPage(WebDriver driver) {
        super(driver);
    }

    public MultipleWindowsPage open() {
        openPath("/windows");
        return this;
    }

    /** Abre el enlace en una ventana nueva y cambia el driver a ella. */
    public MultipleWindowsPage openNewWindow() {
        step("Open link in a new window and switch to it");
        String original = driver.getWindowHandle();
        click(NEW_WINDOW_LINK);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        String newHandle = driver.getWindowHandles().stream()
                .filter(handle -> !handle.equals(original))
                .findFirst()
                .orElseThrow();
        driver.switchTo().window(newHandle);
        return this;
    }

    public String heading() {
        return textOf(HEADING);
    }

    public int windowCount() {
        return driver.getWindowHandles().size();
    }
}
