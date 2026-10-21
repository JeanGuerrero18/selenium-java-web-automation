package com.automation.saucedemo.tests;

import com.automation.framework.base.BaseTest;
import com.automation.saucedemo.data.SauceUsers;
import com.automation.saucedemo.pages.InventoryPage;
import com.automation.saucedemo.pages.LoginPage;

abstract class SauceDemoTest extends BaseTest {

    protected LoginPage openLoginPage() {
        return new LoginPage(driver()).open();
    }

    protected InventoryPage loginAsStandardUser() {
        return openLoginPage().loginAs(SauceUsers.STANDARD, SauceUsers.password());
    }
}
