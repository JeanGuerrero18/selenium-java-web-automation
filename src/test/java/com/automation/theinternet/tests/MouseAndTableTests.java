package com.automation.theinternet.tests;

import com.automation.framework.base.BaseTest;
import com.automation.theinternet.pages.HoversPage;
import com.automation.theinternet.pages.SortableTablesPage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MouseAndTableTests extends BaseTest {

    @Test(groups = "regression", description = "Pasar el cursor sobre un avatar muestra su texto")
    public void hoverRevealsCaption() {
        String caption = new HoversPage(driver()).open().captionAfterHover(0);

        assertThat(caption).isEqualTo("name: user1");
    }

    @Test(groups = "regression",
            description = "Una celda se encuentra por la clave de fila y el encabezado de columna")
    public void tableCellIsFoundByRowAndColumn() {
        SortableTablesPage table = new SortableTablesPage(driver()).open();

        assertThat(table.cell("Last Name", "Bach", "Due")).isEqualTo("$51.00");
    }

    @Test(groups = "regression",
            description = "Hacer clic en un encabezado ordena la columna de forma ascendente")
    public void tableSortsByLastName() {
        SortableTablesPage table = new SortableTablesPage(driver()).open().sortBy("Last Name");

        assertThat(table.column("Last Name")).isSorted();
    }
}
