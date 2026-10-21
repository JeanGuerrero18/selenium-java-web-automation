package com.automation.theinternet.tests;

import com.automation.framework.base.BaseTest;
import com.automation.theinternet.pages.CheckboxesPage;
import com.automation.theinternet.pages.DropdownPage;
import com.automation.theinternet.pages.FileUploadPage;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class FormControlsTests extends BaseTest {

    @Test(groups = "regression", description = "Los checkboxes se pueden poner en un estado explícito")
    public void checkboxesCanBeToggled() {
        CheckboxesPage page = new CheckboxesPage(driver())
                .open()
                .setChecked(0, true)
                .setChecked(1, false);

        assertThat(page.states()).containsExactly(true, false);
    }

    @Test(groups = "regression", description = "El dropdown conserva la opción seleccionada")
    public void dropdownSelectsOption() {
        DropdownPage page = new DropdownPage(driver())
                .open()
                .select("Option 2");

        assertThat(page.selectedOption()).isEqualTo("Option 2");
    }

    @Test(groups = "regression", description = "Se sube un archivo de los recursos de test")
    public void fileIsUploaded() throws URISyntaxException {
        Path file = Path.of(Objects.requireNonNull(
                getClass().getResource("/files/sample-upload.txt"), "sample-upload.txt missing").toURI());

        FileUploadPage page = new FileUploadPage(driver())
                .open()
                .upload(file);

        assertThat(page.uploadedFileName()).isEqualTo("sample-upload.txt");
    }
}
