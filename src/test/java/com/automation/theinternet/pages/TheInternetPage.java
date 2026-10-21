package com.automation.theinternet.pages;

import com.automation.framework.config.Config;
import com.automation.framework.pages.BasePage;
import org.openqa.selenium.WebDriver;

/** Base de los ejemplos de The Internet: cada página vive en su propia ruta bajo un mismo host. */
abstract class TheInternetPage extends BasePage {

    protected TheInternetPage(WebDriver driver) {
        super(driver);
    }

    protected void openPath(String path) {
        step("Open " + path);
        driver.get(Config.get("theinternet.url") + path);
    }
}
