package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage extends TheInternetPage {

    private static final By DROPDOWN = By.id("dropdown");

    public DropdownPage(WebDriver driver) {
        super(driver);
    }

    public DropdownPage open() {
        openPath("/dropdown");
        return this;
    }

    public DropdownPage select(String visibleText) {
        step("Select '" + visibleText + "'");
        dropdown().selectByVisibleText(visibleText);
        return this;
    }

    public String selectedOption() {
        return dropdown().getFirstSelectedOption().getText().trim();
    }

    private Select dropdown() {
        return new Select(visible(DROPDOWN));
    }
}
