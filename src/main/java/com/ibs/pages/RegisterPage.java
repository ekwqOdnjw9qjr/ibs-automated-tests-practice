package com.ibs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * @author Mironov Roman
 * Page Object, представляющий страницу регистрации пользователя.
 */
public class RegisterPage extends BasePage {
    private final By firstNameField = By.id("input-firstname");
    private final By lastNameField = By.id("input-lastname");
    private final By emailField = By.id("input-email");
    private final By passwordField = By.id("input-password");
    private final By subscribeToNews = By.id("input-newsletter");
    private final By privacyPolicyCheckbox = By.xpath("//input[@name='agree']");
    private final By continueButton = By.xpath("//button[@type='submit']");
    private final By emailErrorText = By.id("error-email");
    private final By passwordErrorText = By.id("error-password");
    private final By successPageText = By.xpath("(//div[@id='content']//h1)[1]");

    /**
     * Конструктор страницы регистрации.
     *
     * @param driver Экземпляр WebDriver.
     */
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Вводит значение в поле "Имя".
     *
     * @param firstName Имя для ввода.
     */
    public void setFirstNameField(String firstName) {
        set(firstNameField, firstName);
    }

    /**
     * Вводит значение в поле "Фамилия".
     *
     * @param lastName Фамилия для ввода.
     */
    public void setLastNameField(String lastName) {
        set(lastNameField, lastName);
    }

    /**
     * Вводит значение в поле "Email".
     *
     * @param email Email для ввода.
     */
    public void setEmailField(String email) {
        set(emailField, email);
    }

    /**
     * Вводит значение в поле "Пароль".
     *
     * @param password Пароль для ввода.
     */
    public void setPasswordField(String password) {
        set(passwordField, password);
    }

    /**
     * Устанавливает состояние радиокнопки "Подписаться на новости".
     *
     * @param subscribe {@code true} для выбора "Да", {@code false} для выбора "Нет".
     */
    public void setSubscribeToNews(boolean subscribe) {
        setCheckbox(subscribeToNews, subscribe);
    }

    /**
     * Устанавливает состояние чекбокса "Согласие с политикой конфиденциальности".
     *
     * @param policyCheckbox {@code true} для выбора, {@code false} для снятия выбора.
     */
    public void setPrivacyPolicyCheckbox(boolean policyCheckbox) {
        setCheckbox(privacyPolicyCheckbox, policyCheckbox);
    }

    /**
     * Нажимает на кнопку "Продолжить".
     */
    public void clickContinueButton() {
        click(continueButton);
    }


    /**
     * Проверяет, отображается ли сообщение об ошибке для поля Email.
     *
     * @return {@code true}, если сообщение об ошибке отображается, иначе {@code false}.
     */
    public boolean isEmailErrorDisplayed() {
        return isElementDisplayed(emailErrorText);
    }

    /**
     * Получает текст сообщения об ошибке для поля Email.
     *
     * @return Текст сообщения об ошибке.
     */
    public String getEmailErrorText() {
        return getElementText(emailErrorText);
    }

    /**
     * Проверяет, отображается ли сообщение об ошибке для поля Пароль.
     *
     * @return {@code true}, если сообщение об ошибке отображается, иначе {@code false}.
     */
    public boolean isPasswordErrorDisplayed() {
        return isElementDisplayed(passwordErrorText);
    }

    /**
     * Получает текст сообщения об ошибке для поля Пароль.
     *
     * @return Текст сообщения об ошибке.
     */
    public String getPasswordErrorText() {
        return getElementText(passwordErrorText);
    }

    /**
     * Получает текущий URL страницы.
     *
     * @return Текущий URL.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Получает текст заголовка на странице успешной регистрации.
     *
     * @return Текст заголовка.
     */
    public String getSuccessPageText() {
        return getElementText(successPageText);
    }

    /**
     * Проверяет, находится ли пользователь на странице успешной регистрации.
     *
     * @return {@code true}, если URL содержит маркер успешной регистрации, иначе {@code false}.
     */
    public boolean isOnSuccessPage() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            shortWait.until(ExpectedConditions.urlContains("route=account/success"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}