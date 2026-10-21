package com.automation.theinternet.tests;

import com.automation.framework.base.BaseTest;
import com.automation.theinternet.pages.DynamicLoadingPage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SynchronizationTests extends BaseTest {

    @Test(groups = {"smoke", "regression"},
            description = "La espera explícita maneja contenido que se renderiza con retraso")
    public void dynamicallyLoadedElementAppears() {
        DynamicLoadingPage page = new DynamicLoadingPage(driver())
                .open()
                .start();

        assertThat(page.loadedText()).isEqualTo("Hello World!");
    }
}
