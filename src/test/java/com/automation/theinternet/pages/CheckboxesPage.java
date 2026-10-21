package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckboxesPage extends TheInternetPage {

    private static final By CHECKBOX = By.cssSelector("#checkboxes input[type='checkbox']");

    public CheckboxesPage(WebDriver driver) {
        super(driver);
    }

    public CheckboxesPage open() {
        openPath("/checkboxes");
        return this;
    }

    /** Garantiza que el checkbox en {@code index} (base 0) quede en el estado pedido. */
    public CheckboxesPage setChecked(int index, boolean checked) {
        step((checked ? "Check" : "Uncheck") + " checkbox " + (index + 1));
        WebElement checkbox = allVisible(CHECKBOX).get(index);
        if (checkbox.isSelected() != checked) {
            checkbox.click();
        }
        return this;
    }

    public List<Boolean> states() {
        return allVisible(CHECKBOX).stream().map(WebElement::isSelected).toList();
    }
}
