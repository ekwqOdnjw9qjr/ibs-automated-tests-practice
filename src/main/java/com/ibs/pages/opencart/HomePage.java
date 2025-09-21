package com.ibs.pages.opencart;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.*;
/**
 * @author Mironov Roman
 * Страница главного меню OpenCart.
 */
public class HomePage {

    private final SelenideElement myAccountDropdown = $x("//span[normalize-space(text())='Личный кабинет']");
    private final SelenideElement registerLink = $x("//a[@class='dropdown-item' and text()='Регистрация']");

    /**
     * Открывает главную страницу по-указанному URL.
     *
     * @param url адрес главной страницы.
     */
    @Step("Открыть главную страницу Opencart")
    public void openPage(String url) {
        open(url);
    }

    /**
     * Переходит на страницу регистрации через меню "Личный кабинет".
     *
     * @return объект страницы регистрации {@link RegisterPage}.
     */
    @Step("Перейти на страницу регистрации")
    public RegisterPage goToRegistration() {
        myAccountDropdown.click();
        registerLink.shouldBe(clickable).click();
        return new RegisterPage();
    }
}