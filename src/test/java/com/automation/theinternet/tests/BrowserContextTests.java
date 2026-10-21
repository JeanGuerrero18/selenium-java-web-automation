package com.automation.theinternet.tests;

import com.automation.framework.base.BaseTest;
import com.automation.theinternet.pages.JavaScriptAlertsPage;
import com.automation.theinternet.pages.MultipleWindowsPage;
import com.automation.theinternet.pages.NestedFramesPage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** Alertas, ventanas y frames: todo lo que necesita driver.switchTo(). */
public class BrowserContextTests extends BaseTest {

    @Test(groups = "regression", description = "La alerta JS se acepta")
    public void alertIsAccepted() {
        JavaScriptAlertsPage page = new JavaScriptAlertsPage(driver()).open().acceptAlert();

        assertThat(page.result()).isEqualTo("You successfully clicked an alert");
    }

    @Test(groups = "regression", description = "El confirm JS se cancela")
    public void confirmIsDismissed() {
        JavaScriptAlertsPage page = new JavaScriptAlertsPage(driver()).open().dismissConfirm();

        assertThat(page.result()).isEqualTo("You clicked: Cancel");
    }

    @Test(groups = "regression", description = "El prompt JS recibe el texto escrito")
    public void promptReceivesText() {
        JavaScriptAlertsPage page = new JavaScriptAlertsPage(driver()).open().answerPrompt("Usuario A");

        assertThat(page.result()).isEqualTo("You entered: Usuario A");
    }

    @Test(groups = "regression",
            description = "El enlace abre una segunda ventana a la que el driver puede cambiar")
    public void newWindowIsOpenedAndFocused() {
        MultipleWindowsPage page = new MultipleWindowsPage(driver()).open().openNewWindow();

        assertThat(page.windowCount()).isEqualTo(2);
        assertThat(page.heading()).isEqualTo("New Window");
    }

    @Test(groups = "regression", description = "El contenido de los frames anidados es accesible")
    public void nestedFramesAreReadable() {
        NestedFramesPage page = new NestedFramesPage(driver()).open();

        assertThat(page.textOfTopFrame("frame-middle")).isEqualTo("MIDDLE");
        assertThat(page.textOfTopFrame("frame-left")).isEqualTo("LEFT");
        assertThat(page.textOfBottomFrame()).isEqualTo("BOTTOM");
    }
}
