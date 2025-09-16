package com.ibs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * @author Mironov Roman
 * Page Object, представляющий главную страницу приложения.
 */
public class HomePage extends BasePage {

    /**
     * URL главной страницы.
     */
    private static final String HOME_PAGE_URL = "http://217.74.37.176/?route=common/home&language=ru-ru";

    /**
     * Кнопка "Личный кабинет" для открытия выпадающего меню.
     */
    @FindBy(xpath = "(//span[@class='d-none d-md-inline'])[3]")
    private WebElement openPersonalInfo;

    /**
     * Ссылка "Регистрация" в выпадающем меню личного кабинета.
     */
    @FindBy(xpath = "//a[@class='dropdown-item' and text()='Регистрация']")
    private WebElement registerFormLink;

    /**
     * Конструктор страницы.
     *
     * @param driver Экземпляр WebDriver.
     */
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Открывает главную страницу в браузере.
     */
    public void openPage() {
        driver.get(HOME_PAGE_URL);
    }

    /**
     * Кликает по иконке "Личный кабинет" для вызова выпадающего меню.
     */
    public void openRegisterXd() {
        openPersonalInfo.click();
    }

    /**
     * Кликает по ссылке "Регистрация" в открытом выпадающем меню.
     */
    public void openRegisterForm() {
        wait.until(ExpectedConditions.elementToBeClickable(registerFormLink));
        registerFormLink.click();
    }
}