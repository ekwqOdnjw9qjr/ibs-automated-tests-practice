package com.ibs.pages.sandbox;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ibs.config.Config;

import static com.codeborne.selenide.Selenide.$;
/**
 * @author Mironov Roman
 * Стартовая страница Sandbox.
 */
public class StartPage {

    private static final String HOME_PAGE_URL = Config.getSandboxBaseUrl();
    private final SelenideElement openSandboxButton = $("#navbarDropdown");
    private final SelenideElement productsLink = $("a[href='/food'].dropdown-item");

    /**
     * Открывает стартовую страницу Sandbox.
     */
    public void openPage() {
        Selenide.open(HOME_PAGE_URL);
    }

    /**
     * Открывает раздел Sandbox.
     */
    public void openSandbox() {
        openSandboxButton.click();
    }

    /**
     * Переходит к странице продуктов.
     *
     * @return объект страницы продуктов {@link ProductPage}.
     */
    public ProductPage goToProducts() {
        productsLink.click();
        return new ProductPage();
    }
}
