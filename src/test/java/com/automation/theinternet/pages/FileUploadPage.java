package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

public class FileUploadPage extends TheInternetPage {

    private static final By FILE_INPUT = By.id("file-upload");
    private static final By SUBMIT = By.id("file-submit");
    private static final By UPLOADED_FILES = By.id("uploaded-files");

    public FileUploadPage(WebDriver driver) {
        super(driver);
    }

    public FileUploadPage open() {
        openPath("/upload");
        return this;
    }

    /** Envía la ruta directamente al input de archivo; no interviene ningún diálogo nativo del SO. */
    public FileUploadPage upload(Path file) {
        step("Upload " + file.getFileName());
        driver.findElement(FILE_INPUT).sendKeys(file.toAbsolutePath().toString());
        click(SUBMIT);
        return this;
    }

    public String uploadedFileName() {
        return textOf(UPLOADED_FILES);
    }
}
