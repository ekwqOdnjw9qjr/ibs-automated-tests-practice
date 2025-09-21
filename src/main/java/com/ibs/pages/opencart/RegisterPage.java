package com.ibs.pages.opencart;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.util.Locale;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
/**
 * @author Mironov Roman
 * Страница регистрации OpenCart.
 */
public class RegisterPage {

    private final SelenideElement firstNameField = $("#input-firstname");
    private final SelenideElement lastNameField = $("#input-lastname");
    private final SelenideElement emailField = $("#input-email");
    private final SelenideElement passwordField = $("#input-password");
    private final SelenideElement newsCheckbox = $("#input-newsletter");
    private final SelenideElement privacyPolicyCheckbox = $x("//input[@name='agree']");
    private final SelenideElement continueButton = $x("//button[@type='submit']");
    private final SelenideElement successMessageHeader =
            $x("//div[@id='content']//h1[normalize-space()='Ваша учетная запись создана!']");

    private final Faker faker = new Faker();

    /**
     * Заполняет указанное поле формы.
     *
     * @param fieldName имя поля (Имя, Фамилия, E-mail, Пароль).
     * @param value значение для заполнения.
     */
    @Step("Заполнить поле '{fieldName}' значением '{value}'")
    public void fillField(String fieldName, String value) {
        switch (fieldName.toLowerCase()) {
            case "имя" -> firstNameField.setValue(value);
            case "фамилия" -> lastNameField.setValue(value);
            case "e-mail" -> emailField.setValue(value);
            case "пароль" -> passwordField.setValue(value);
            default -> throw new IllegalArgumentException("Неизвестное поле: " + fieldName);
        }
    }

    /**
     * Заполняет форму регистрации валидными данными.
     *
     * @param data мапа с данными (Имя, Фамилия, Пароль).
     */
    @Step("Заполнить форму регистрации валидными данными")
    public void fillRegistrationForm(Map<String, String> data) {
        fillField("Имя", data.get("Имя"));
        fillField("Фамилия", data.get("Фамилия"));
        fillField("E-Mail", faker.internet().emailAddress());
        fillField("Пароль", data.get("Пароль"));
    }

    /**
     * Отмечает чекбокс согласия на новости.
     */
    @Step("Согласиться получать новости")
    public void agreeToNewCheckbox() {
        newsCheckbox.click();
    }

    /**
     * Отмечает чекбокс согласия с политикой конфиденциальности.
     */
    @Step("Согласиться с политикой конфиденциальности")
    public void agreeToPrivacyPolicy() {
        privacyPolicyCheckbox.click();
    }

    /**
     * Нажимает кнопку "Продолжить".
     */
    @Step("Нажать кнопку 'Продолжить'")
    public void clickContinue() {
        continueButton.click();
    }

    /**
     * Проверяет сообщение об успешной регистрации.
     *
     * @param expectedMessage ожидаемое сообщение.
     */
    @Step("Проверить, что отображается сообщение об успешной регистрации: '{expectedMessage}'")
    public void checkSuccessMessage(String expectedMessage) {
        successMessageHeader.shouldHave(exactText(expectedMessage));
    }

    /**
     * Проверяет сообщение об ошибке для указанного поля.
     *
     * @param fieldName имя поля.
     * @param expectedMessage ожидаемое сообщение об ошибке.
     */
    @Step("Проверить, что отображается ошибка '{expectedMessage}' под полем '{fieldName}'")
    public void checkErrorMessageForField(String fieldName, String expectedMessage) {
        String errorId = switch (fieldName.toLowerCase()) {
            case "e-mail" -> "error-email";
            case "пароль" -> "error-password";
            default -> throw new IllegalArgumentException("Неизвестное поле для проверки ошибки: " + fieldName);
        };

        $("#" + errorId).shouldBe(visible).shouldHave(text(expectedMessage));
    }


    /**
     * Проверяет, что пользователь остался на странице регистрации.
     */
    @Step("Проверить, что мы остались на странице регистрации")
    public void verifyIsOnRegisterPage() {
        webdriver().shouldHave(urlContaining("route=account/register"));
    }
}