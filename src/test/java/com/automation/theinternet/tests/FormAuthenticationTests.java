package com.automation.theinternet.tests;

import com.automation.framework.base.BaseTest;
import com.automation.framework.config.Config;
import com.automation.theinternet.pages.FormAuthenticationPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FormAuthenticationTests extends BaseTest {

    private static String validUsername() {
        return Config.get("theinternet.username");
    }

    private static String validPassword() {
        return Config.get("theinternet.password");
    }

    @Test(groups = {"smoke", "regression"}, description = "Las credenciales válidas abren el área segura")
    public void validLoginOpensSecureArea() {
        FormAuthenticationPage page = new FormAuthenticationPage(driver())
                .open()
                .login(validUsername(), validPassword());

        assertThat(page.flashMessage()).isEqualTo("You logged into a secure area!");
        assertThat(page.header()).isEqualTo("Secure Area");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"unknown_user", validPassword(), "Your username is invalid!"},
                {validUsername(), "wrong_password", "Your password is invalid!"},
        };
    }

    @Test(dataProvider = "invalidCredentials", groups = "regression",
            description = "Las credenciales inválidas muestran el mensaje flash correspondiente")
    public void invalidLoginShowsError(String username, String password, String expectedMessage) {
        FormAuthenticationPage page = new FormAuthenticationPage(driver())
                .open()
                .login(username, password);

        assertThat(page.flashMessage()).isEqualTo(expectedMessage);
    }
}
