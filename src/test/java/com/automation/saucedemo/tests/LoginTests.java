package com.automation.saucedemo.tests;

import com.automation.saucedemo.data.SauceUsers;
import com.automation.saucedemo.pages.InventoryPage;
import com.automation.saucedemo.pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests extends SauceDemoTest {

    @Test(groups = {"smoke", "regression"},
            description = "El usuario estándar llega al catálogo de productos")
    public void validLoginShowsInventory() {
        InventoryPage inventory = loginAsStandardUser();

        assertThat(inventory.title()).isEqualTo("Products");
        assertThat(inventory.productNames()).hasSize(6);
    }

    @Test(groups = "regression", description = "El usuario bloqueado es rechazado con un mensaje específico")
    public void lockedOutUserSeesError() {
        LoginPage login = openLoginPage().attemptLogin(SauceUsers.LOCKED_OUT, SauceUsers.password());

        assertThat(login.errorMessage()).isEqualTo("Epic sadface: Sorry, this user has been locked out.");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"", "any", "Epic sadface: Username is required"},
                {SauceUsers.STANDARD, "", "Epic sadface: Password is required"},
                {SauceUsers.STANDARD, "wrong_password",
                        "Epic sadface: Username and password do not match any user in this service"},
        };
    }

    @Test(dataProvider = "invalidCredentials", groups = "regression",
            description = "Las credenciales inválidas muestran el mensaje de validación correspondiente")
    public void invalidLoginShowsError(String username, String password, String expectedError) {
        LoginPage login = openLoginPage().attemptLogin(username, password);

        assertThat(login.errorMessage()).isEqualTo(expectedError);
    }

    @Test(groups = "regression", description = "Cerrar sesión devuelve al usuario a la página de login")
    public void logoutReturnsToLoginPage() {
        LoginPage login = loginAsStandardUser().header().logout();

        assertThat(login.isDisplayed()).isTrue();
    }
}
