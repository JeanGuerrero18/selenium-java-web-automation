package com.automation.framework.driver;

import com.automation.framework.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

/**
 * Construye instancias locales de WebDriver. Selenium Manager (incluido en Selenium 4) resuelve
 * los binarios del driver, así que no hace falta WebDriverManager ni un chromedriver manual.
 */
public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver create(BrowserType browser, boolean headless) {
        return switch (browser) {
            case CHROME -> new ChromeDriver(chromiumOptions(new ChromeOptions(), headless));
            case EDGE -> new EdgeDriver(chromiumOptions(new EdgeOptions(), headless));
            case FIREFOX -> new FirefoxDriver(firefoxOptions(headless));
        };
    }

    private static <T extends ChromiumOptions<T>> T chromiumOptions(T options, boolean headless) {
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=" + width() + "," + height());
        // Desactiva el gestor de contraseñas y los avisos de contraseñas filtradas: sus popups
        // nativos aparecen tras el login en los sitios demo y pueden quitarle el foco a la página.
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        options.addArguments("--width=" + width(), "--height=" + height());
        return options;
    }

    private static int width() {
        return Config.getInt("window.width");
    }

    private static int height() {
        return Config.getInt("window.height");
    }
}
