package com.automation.theinternet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/** Tabla del ejemplo 1 (sin clases útiles): las celdas se ubican por la posición del encabezado. */
public class SortableTablesPage extends TheInternetPage {

    private static final By HEADERS = By.cssSelector("#table1 thead th");
    private static final By ROWS = By.cssSelector("#table1 tbody tr");
    private static final By CELLS = By.tagName("td");

    public SortableTablesPage(WebDriver driver) {
        super(driver);
    }

    public SortableTablesPage open() {
        openPath("/tables");
        return this;
    }

    public SortableTablesPage sortBy(String header) {
        step("Sort table by '" + header + "'");
        allVisible(HEADERS).get(columnIndex(header)).click();
        return this;
    }

    public List<String> column(String header) {
        int index = columnIndex(header);
        return allVisible(ROWS).stream()
                .map(row -> row.findElements(CELLS).get(index).getText().trim())
                .toList();
    }

    /** Valor de {@code header} en la fila cuyo {@code keyHeader} es igual a {@code keyValue}. */
    public String cell(String keyHeader, String keyValue, String header) {
        int keyIndex = columnIndex(keyHeader);
        int valueIndex = columnIndex(header);
        return allVisible(ROWS).stream()
                .map(row -> row.findElements(CELLS))
                .filter(cells -> cells.get(keyIndex).getText().trim().equals(keyValue))
                .findFirst()
                .map(cells -> cells.get(valueIndex).getText().trim())
                .orElseThrow(() -> new NoSuchElementException("No row with " + keyHeader + " = " + keyValue));
    }

    private int columnIndex(String header) {
        List<WebElement> headers = allVisible(HEADERS);
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equals(header)) {
                return i;
            }
        }
        throw new NoSuchElementException("No column named '" + header + "'");
    }
}
