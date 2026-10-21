package com.automation.saucedemo.data;

import com.automation.framework.config.Config;

/** Cuentas de prueba publicadas por SauceDemo. Todas comparten la misma contraseña. */
public final class SauceUsers {

    public static final String STANDARD = "standard_user";
    public static final String LOCKED_OUT = "locked_out_user";

    private SauceUsers() {
    }

    public static String password() {
        return Config.get("saucedemo.password");
    }
}
