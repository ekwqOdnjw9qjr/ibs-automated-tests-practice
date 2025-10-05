package com.ibs.cucumber.hooks;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.ibs.utill.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.selenide.AllureSelenide;

public class Hooks {

    @Before
    public void setUp() {
        WebDriverFactory.setupDriver();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
    }

    @After
    public void tearDown() {
        Selenide.closeWebDriver();
    }

}