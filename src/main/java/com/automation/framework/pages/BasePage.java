package com.automation.framework.pages;

import com.automation.framework.config.Config;
import com.automation.framework.reporting.ExtentTestManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Comportamiento común de los page objects. Cada interacción pasa por una espera explícita;
 * nunca se configuran esperas implícitas, así que ambas no se combinan en timeouts impredecibles.
 */
public abstract class BasePage {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Config.timeout());
    }

    /** Registra un paso de negocio tanto en el log como en el reporte HTML. */
    protected void step(String description) {
        log.info(description);
        ExtentTestManager.info(description);
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> allVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String textOf(By locator) {
        return visible(locator).getText().trim();
    }

    /** Comprobación sin espera, para elementos que pueden no estar presentes legítimamente. */
    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected List<String> textsOf(By locator) {
        return allVisible(locator).stream().map(element -> element.getText().trim()).toList();
    }
}
