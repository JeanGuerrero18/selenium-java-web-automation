package com.automation.theinternet.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JavaScriptAlertsPage extends TheInternetPage {

    private static final By ALERT_BUTTON = By.cssSelector("button[onclick='jsAlert()']");
    private static final By CONFIRM_BUTTON = By.cssSelector("button[onclick='jsConfirm()']");
    private static final By PROMPT_BUTTON = By.cssSelector("button[onclick='jsPrompt()']");
    private static final By RESULT = By.id("result");

    public JavaScriptAlertsPage(WebDriver driver) {
        super(driver);
    }

    public JavaScriptAlertsPage open() {
        openPath("/javascript_alerts");
        return this;
    }

    public JavaScriptAlertsPage acceptAlert() {
        step("Open JS alert and accept it");
        click(ALERT_BUTTON);
        waitForAlert().accept();
        return this;
    }

    public JavaScriptAlertsPage dismissConfirm() {
        step("Open JS confirm and dismiss it");
        click(CONFIRM_BUTTON);
        waitForAlert().dismiss();
        return this;
    }

    public JavaScriptAlertsPage answerPrompt(String text) {
        step("Open JS prompt and answer '" + text + "'");
        click(PROMPT_BUTTON);
        Alert prompt = waitForAlert();
        prompt.sendKeys(text);
        prompt.accept();
        return this;
    }

    public String result() {
        return textOf(RESULT);
    }

    private Alert waitForAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }
}
