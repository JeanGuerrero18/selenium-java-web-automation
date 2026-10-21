package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NestedFramesPage extends TheInternetPage {

    private static final By BODY = By.tagName("body");

    public NestedFramesPage(WebDriver driver) {
        super(driver);
    }

    public NestedFramesPage open() {
        openPath("/nested_frames");
        return this;
    }

    public String textOfTopFrame(String childFrameName) {
        return textOfFrame("frame-top", childFrameName);
    }

    public String textOfBottomFrame() {
        return textOfFrame("frame-bottom");
    }

    /**
     * Recorre toda la ruta de frames desde el documento principal en cada reintento. Cambiar de
     * nivel en nivel puede dejar el driver dentro de un frame padre cuyo documento aún se está
     * reemplazando mientras carga el frameset, lo que volvía inestable este test en paralelo.
     */
    private String textOfFrame(String... framePath) {
        step("Read text of frame " + String.join(" > ", framePath));
        try {
            return wait.until(d -> {
                d.switchTo().defaultContent();
                for (String frame : framePath) {
                    d.switchTo().frame(frame);
                }
                String text = d.findElement(BODY).getText().trim();
                return text.isEmpty() ? null : text;
            });
        } finally {
            driver.switchTo().defaultContent();
        }
    }
}
