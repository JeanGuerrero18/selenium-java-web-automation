package com.automation.framework.base;

import com.automation.framework.config.Config;
import com.automation.framework.driver.DriverFactory;
import com.automation.framework.driver.DriverManager;
import com.automation.framework.listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Da a cada método de test su propio navegador nuevo.
 *
 * <p>El driver no se guarda a propósito en un campo de instancia: con {@code parallel="methods"}
 * TestNG ejecuta en paralelo métodos de la misma instancia de clase, así que un campo se
 * compartiría entre hilos. Usa siempre {@link #driver()}.
 */
@Listeners(TestListener.class)
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void startBrowser() {
        DriverManager.setDriver(DriverFactory.create(Config.browser(), Config.headless()));
    }

    @AfterMethod(alwaysRun = true)
    public void quitBrowser() {
        DriverManager.quitDriver();
    }

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }
}
